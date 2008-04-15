package com.eldergods.Util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public abstract class SaxDeserializer extends DefaultHandler implements IDeserializer 
{
	protected Collection _list = null;
	
	static private String _tag = SaxDeserializer.class.getName();
	
	static SAXParserFactory _saxParserFactory = null; 
	static SAXParser _saxParser = null;
	static XMLReader _xmlReader = null;
	static boolean _init = false;

	public SaxDeserializer()
	{
		if(!_init)
		{
			_init = true;
			_saxParserFactory = SAXParserFactory.newInstance();
			try {
				_saxParser = _saxParserFactory.newSAXParser();
				_xmlReader = _saxParser.getXMLReader();
			} 
			catch (ParserConfigurationException pce) {}
			catch (SAXParseException spe) {}
			catch (SAXException se) {}
		}
	}

	public void startElement(String uri, String name, String qName, Attributes atts) {}
	public void endElement(String uri, String name, String qName) {}
	public void characters(char ch[], int start, int length) {}

	public Object deserializeUrl(String url)
	{
		_list = new ArrayList();
		try
		{;
			_xmlReader.setContentHandler(this);
			_xmlReader.parse(url);
		} 
		catch (IOException e) 
		{
			Log.e(_tag, e.toString());
		} 
		catch (SAXException e) 
		{
			Log.e(_tag, e.toString());
		}
		return _list;		
	}

	public Object deserialize(String serialization)
	{
		_list = new ArrayList(); 
		try
		{
			_xmlReader.setContentHandler(this);
			_xmlReader.parse(new InputSource(new StringReader(serialization)));
		} 
		catch (IOException e) 
		{
			Log.e(_tag, e.toString());
		} 
		catch (SAXException e) 
		{
			Log.e(_tag, e.toString());
		} 
		return _list;		
	}

	public Object deserialize(byte[] buffer)
	{
		_list = new ArrayList();
		try
		{
			_xmlReader.setContentHandler(this);
			//InputStream inStr = new ByteArrayInputStream(buffer);
			StringReader strRdr = new StringReader(new String(buffer,Defaults.Encoding));
			InputSource inSrc = new InputSource(strRdr);
			_xmlReader.parse(inSrc);
		} 
		catch (IOException e) 
		{
			Log.e(_tag, e.toString());
		} 
		catch (SAXException e) 
		{
			Log.e(_tag, e.toString());
		} 
		return _list;
	}
}