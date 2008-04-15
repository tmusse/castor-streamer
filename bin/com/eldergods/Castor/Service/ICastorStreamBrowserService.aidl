package com.eldergods.Castor.Service;

import com.eldergods.Castor.Service.ICastorStation;

interface ICastorStreamBrowserService 
{
	String[] getGenres();
	ICastorStation getStations(String genre);
}