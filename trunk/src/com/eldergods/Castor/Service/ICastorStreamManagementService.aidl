package com.eldergods.Castor.Service;


interface ICastorStreamBrowserService 
{
	int getStation(String url);
	
	void AddToFavorites(int newFavorite); 
}