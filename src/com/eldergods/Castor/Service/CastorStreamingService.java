package com.eldergods.Castor.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.eldergods.Util.CachedHttpPage;


public class CastorStreamingService extends Service {

	private NotificationManager _nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	
	@Override
	protected void onCreate() 
	{
		// load all scheduled
		// start any waiting
	}
	
	@Override
	protected void onDestroy()
	{
		// stop any streams
		// dump
	}
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
        // Select the interface to return.
		/*
        if (ICastorStreamM  .class.getName().equals(intent.getAction())) 
        {
            return _serviceStation;
        } 
        else if (ICastorStreamBrowserService.class.getName().equals(intent.getAction())) 
        {
            return _serviceBrowser;
        }
        
        */
        return null;
        
		
	}

    CachedHttpPage _genres = new CachedHttpPage("http://www.shoutcast.com/sbin/newxml.phtml",21600);
    private final ICastorStreamBrowserService.Stub _serviceBrowser = new ICastorStreamBrowserService.Stub() 
    {
    	public String[] getGenres()
    	{
    		String genrePage = _genres.getResponseBodyAsString();
    		
    		return null;
    	}

    	public ICastorStation getStations(String genre)
    	{
    		return null;
    	}
    };

    
    
}



