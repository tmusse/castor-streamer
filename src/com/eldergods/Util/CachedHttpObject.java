package com.eldergods.Util;

import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
import java.lang.Runnable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import android.os.Handler;
import android.util.Log;

public class CachedHttpObject {

	private String _url;
	private Object _result = null;
	private IDeserializer _deserializer = null;

	private int _lifetime = 360;
	private Calendar _regenTime;
	private AtomicBoolean _working = new AtomicBoolean(false);
	
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

	public CachedHttpObject(String url, IDeserializer deserializer, int lifetime)
	{
		_url = url;
		_lifetime = lifetime;
		_regenTime = Calendar.getInstance();
		_deserializer = deserializer;
	}

	public CachedHttpObject(String url, IDeserializer deserializer)
	{
		this(url,deserializer,_defaultLifetime);
	}

	public Object getResult()
	{
		if(_result == null || isCacheStale())
			_regen();
		return _result;
	}
	
	public boolean isCacheStale()
	{
		if(_result == null)
			return true;
		Calendar now = Calendar.getInstance();
		int compare = now.compareTo(_regenTime);
		return compare > 0;
	}

	public void flushCache()
	{
		if(_working.compareAndSet(false,true))
		{
			_result = null;
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

		_result = _deserializer.deserializeUrl(_url);

		_regenTime = Calendar.getInstance();
		if(_lifetime > 0)
			_regenTime.add(Calendar.SECOND, _lifetime);
		else
			_regenTime.add(Calendar.SECOND, _defaultLifetime);

		_setDelayedFlush();

		_working.set(false);
	}

	
	private Runnable flushMethod = new Runnable(){
		public void run() { flushCache(); }
	};
	
	private void _setDelayedFlush()
	{
		//Handler handler = new Handler();
		//handler.postDelayed(flushMethod,_lifetime*1000);
	}
}
