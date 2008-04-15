package com.eldergods.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import android.util.Log;

public class CachedHttpPage 
{
	static private int _defaultLifetime = 300; // 5 minutes
	static private String _tag = CachedHttpPage.class.getName();
	static private String _ua = "CastorStreamingService 1.0";
	static private String _encoding = "UTF8";

	static public void setDefaultLifetime(int lifetimeInSeconds)
	{
		_defaultLifetime = lifetimeInSeconds;
	}

	static public int getDefaultLifetime()
	{
		return _defaultLifetime;
	}



	private String _url;

	private String _stringResponse = null;
	private byte[] _byteResponse = null;
	private int _code = 0;

	private int _lifetime = 360;
	private Calendar _regenTime;
	private AtomicBoolean _working = new AtomicBoolean(false);

	private int _hasRequestedString = 0;
	private int _hasRequestedByte = 0;

	public CachedHttpPage(String url, int lifetime)
	{
		_url = url;
		_lifetime = lifetime;
		_regenTime = Calendar.getInstance();
	}

	public CachedHttpPage(String url)
	{
		this(url,_defaultLifetime);
	}

	public byte[] getResponseBody()
	{
		++_hasRequestedByte;
		_regen();
		return _byteResponse;
	}

	public String getResponseBodyAsString()
	{
		++_hasRequestedString;
		_regen();
		return _stringResponse;
	}

	public boolean isCacheStale()
	{
		Calendar now = Calendar.getInstance();
		int compare = now.compareTo(_regenTime);
		return compare > 0;
		//return Calendar.getInstance().compareTo(_regenTime) <= 0;
	}

	public void flushCache()
	{
		if(_working.compareAndSet(false,true))
		{
			_stringResponse = null;
			_byteResponse = null;
			_working.set(false);
		}
	}

	private void _regen()
	{
		if(!isCacheStale())
			return;

		boolean isMe = false;
		while(_working.get() == false)
			isMe = _working.compareAndSet(false, true);

		// someone else is working, block 
		// when we're done blocking, they'll have finished regen, so return
		if(!isMe)
		{
			while(_working.get() == true)
				try
				{
					Log.d(_tag, _url + " waiting on another thread to regen");
					Thread.sleep(333);
				}
				catch(InterruptedException intEx) 
				{
					Log.d(_tag, _url + " was interrupted while sleeping");
				}
			return;
		}

		// begin regenerate

		// strictly speaking another thread could have picked up after the while check
		// and finished an update before compareAndSet but it is so unlikely it is not
		// worth checking for that case here, so we'll just run the sequential regen.
		// the only harm is would be the minor additional wait.

		// clear existing cache
		_byteResponse = null;
		_stringResponse = null;
		_code = 0;

		HttpMethod httpMethod = new GetMethod(_url);
		HttpClient httpClient = new HttpClient();
		int status = 0;
		int tries = 0;
		byte[] byteResponse = null;
		while(_code == 0 && tries < 4)
		{
			Log.v(_tag, _url + " regenerating page");
			try
			{
				httpMethod.setRequestHeader("User-Agent", _ua);
				status = httpClient.executeMethod(httpMethod);
				if(status >= 200 && status < 300)
					throw new IOException();
				byteResponse = httpMethod.getResponseBody();
				_code = status;
				
			}
			catch(IOException ioEx)
			{
				tries++;
			}
		}
		httpMethod.releaseConnection();

		if(tries == 4)
		{
			Log.e(_tag, _url + " failed to retrieve");
		}
		else
		{
			if(_hasRequestedByte > 0) _byteResponse = byteResponse;
			if(_hasRequestedString > 0)
				try
				{
					_stringResponse = new String(byteResponse,_encoding);
				} 
				catch (UnsupportedEncodingException unsupEncEx) 
				{
					Log.e(_tag, "unsupported encoding");
				}
		
			_regenTime = Calendar.getInstance();
			if(_lifetime > 0)
				_regenTime.add(Calendar.SECOND, _lifetime);
			else
				_regenTime.add(Calendar.SECOND, _defaultLifetime);
		
		}

		_working.set(false);
	}
}
