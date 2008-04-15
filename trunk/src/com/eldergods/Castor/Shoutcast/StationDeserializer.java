package com.eldergods.Castor.Shoutcast;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.xml.sax.Attributes;

import android.text.util.Regex;
import android.util.Log;

import com.eldergods.Util.CachedHttpObject;
import com.eldergods.Util.IDeserializer;
import com.eldergods.Util.SaxDeserializer;
import com.google.android.tests.core.RegexTest;

public class StationDeserializer implements IDeserializer
{
	static private String _tag = StationDeserializer.class.toString();
	
	public Object deserialize(byte[] buffer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object deserialize(String serialized) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object deserializeUrl(String url) {
		
		HttpMethod httpMethod = new GetMethod(url);
		HttpClient httpClient = new HttpClient();
		int code = 0;
		int tries = 0;
		String stringResponse = null;
		try
		{
			code = httpClient.executeMethod(httpMethod);
			if(code < 200 || code >= 300)
				throw new IOException("a cow");

			stringResponse = httpMethod.getResponseBodyAsString();
		}
		catch(IOException ioEx)
		{
			Log.v(_tag,ioEx.toString());
			tries++;
		}
		httpMethod.releaseConnection();
		
		if(stringResponse == null) return null;
		int start = stringResponse.indexOf("File1=")+6;
		if(start == -1) return null;
		stringResponse = stringResponse.substring(start,stringResponse.indexOf("\n",start));

		return stringResponse;
	}
}
