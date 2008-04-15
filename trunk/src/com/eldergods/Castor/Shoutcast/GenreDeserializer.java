package com.eldergods.Castor.Shoutcast;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import com.eldergods.Util.SaxDeserializer;

public class GenreDeserializer extends SaxDeserializer
{
	static private String _tag = GenreDeserializer.class.toString();

	public void startElement(String uri, String name, String qName, Attributes atts)
	{
		if("genre".equals(name))
			for(int i = 0; i < atts.getLength(); i++)
				if("name".equals(atts.getLocalName(i)))
					_list.add( atts.getValue(i));
	}	
}
