package com.eldergods.Castor.Shoutcast;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import com.eldergods.Util.CachedHttpObject;
import com.eldergods.Util.SaxDeserializer;

public class StreamDeserializer extends SaxDeserializer
{
	static private String _tag = StreamDeserializer.class.toString();
	
	protected ArrayList<Station> _stationList = new ArrayList<Station>();

	public void startElement(String uri, String name, String qName, Attributes atts)
	{
		/*
		 *  <station 
		 *    name=".977 The Hitz Channel" 
		 *    mt="audio/mpeg" 
		 *    id="1025" 
		 *    br="128" 
		 *    genre="Pop Rock Top 40" 
		 *    ct="Leona Lewis - Bleeding Love" 
		 *    lc="4206" />
		 * 
		 */
		if("station".equals(name))
		{
			Station newStation = new Station();
			for(int i = 0; i < atts.getLength(); i++)
			{
				String key = atts.getLocalName(i);
				String value = atts.getValue(i);
				if("name".equals(key))
				{
					_list.add(value);
					newStation.name = value;
				}
				else if ("id".equals(key))
				{
					newStation.id = new Integer(value).intValue();
				}
			}
			_stationList.add(newStation);
		}
	}

	public ArrayList<Station> getStationList()
	{
		return _stationList;
	}

	public void clearStationList()
	{
		_stationList = new ArrayList<Station>();
	}

	public class Station
	{
		public String name;
		public int id;
		CachedHttpObject page = null;
	}
}
