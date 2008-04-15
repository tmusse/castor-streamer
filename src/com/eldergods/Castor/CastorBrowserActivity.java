package com.eldergods.Castor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Menu.Item;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.eldergods.Castor.Shoutcast.GenreDeserializer;
import com.eldergods.Castor.Shoutcast.StationDeserializer;
import com.eldergods.Castor.Shoutcast.StreamDeserializer;
import com.eldergods.Util.CachedHttpObject;
import com.eldergods.Util.IDeserializer;

public class CastorBrowserActivity extends CastorLayoutActivity {

	CachedHttpObject _currentPage = null;
	
	static protected IDeserializer _genreDeserializer = new GenreDeserializer();
	static protected IDeserializer _streamDeserializer = new StreamDeserializer();
	static protected IDeserializer _stationDeserializer = new StationDeserializer();
	
	CachedHttpObject _genrePage = new CachedHttpObject("http://www.shoutcast.com/sbin/newxml.phtml", _genreDeserializer);
	Map<String,CachedHttpObject> _genreMap = new HashMap<String,CachedHttpObject>();
	
	protected MediaPlayer _player = new MediaPlayer();

    @Override
    public void onCreate(Bundle icicle) 
    {
    	_currentPage = _genrePage;
        super.onCreate(icicle);

    	for(View v : _listViews)
    	{
    		ListView lv = (ListView) v;
    		//lv.setOnItemClickListener(_onClickListener);
    		//lv.setOnItemSelectedListener(_onItemSelectedListener);
    		//lv.setOnKeyListener(_onKeyListener);
    	}
    }

    static int[] _listIds = {R.id.list1, R.id.list2, R.id.list3};
    public void redrawLists()
    {
    	if(_genrePage.isCacheStale())
    	{
    		Object contents = _currentPage.getResult();
    		_dataList = (ArrayList) contents;
    	}

    	super.redrawLists();
    }
    
    public boolean isGenrePage()
    	{return _currentPage == _genrePage;}

    protected OnClickListener _onClickListener = new android.view.View.OnClickListener ()
    {
    	public void onClick(View v) 
    	{
    		View focus = getCurrentFocus();
    		ListView lv = (ListView)focus;
    		if(lv != null)
    		{
    			int selectedPage = getPage() * _pageSize;
    			long selectedId = lv.getSelectedItemId();
    			
    			int i = 0;
    			for(View page : _listViews)
    			{
    				if(lv == page) selectedPage += i * _pageSize;
    				++i;
    			}
    		}
    	}
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	boolean result = super.onCreateOptionsMenu(menu);
    	if(_currentPage == _genrePage)
    	{
    		menu.add(0,0,R.string.do_genre);
    	}
    	else
    	{
    		menu.add(0,1,R.string.do_stream);
    		menu.add(0,2,R.string.do_back);
    	}
    	return result;
    }

    protected boolean onCenterButton()
    {
    	if(_currentPage == _genrePage)
    	{
    		ListView lv = getActiveListView();
    		String genre = (String) lv.getSelectedItem();

    		ArrayList<StreamDeserializer.Station> stationList = null;
    		List prime = null;
    		
    		if(!_genreMap.containsKey(genre))
    			_currentPage = new CachedHttpObject("http://www.shoutcast.com/sbin/newxml.phtml?genre="+genre, _streamDeserializer);
    		else
    			_currentPage = _genreMap.get(genre);
    		
    		_dataList = (List) _currentPage.getResult();;
    		_page = 0;
    		_selected = 0;
    		_selectedColumn = 0;
    		_selectedRow = 0; 
    		redrawLists();
    		redrawCurrent();
    	}
    	else
    	{
    		ArrayList<StreamDeserializer.Station> station = ((StreamDeserializer)_streamDeserializer).getStationList();
    		int id = station.get(_selected).id;
    		_currentPage = new CachedHttpObject("http://www.shoutcast.com/sbin/tunein-station.pls?id="+id,_stationDeserializer);
    		
    		//Uri uri = Uri.parse((String)_currentPage.getResult());
    		//_player = MediaPlayer.create(this, uri);
    		
    		_player.reset();
    		String url = (String)_currentPage.getResult();
    		_player.setAudioStreamType(AudioSystem.STREAM_MUSIC);
    		try{_player.setDataSource(url);}
    		catch(IOException ioe) {}
    		_player.prepareAsync();
    		Log.v(_tag,"About to play "+url);
    		try {Thread.sleep(333);}
    		catch (InterruptedException intEx) {}
    		_player.start();    		
    	}
    	return true;
    }

    protected boolean onBackButton()
    {
    	if(!isGenrePage())
    	{
    		_currentPage = _genrePage;
    		_dataList = (List)_genrePage.getResult();
    		_selectedColumn = 0;
    		_selectedRow = 0;
    		_selected = 0;
    		redrawLists();
    		return true;
    	}
    	return false;
    }

    @Override
    public boolean onOptionsItemSelected(Item item) {
    	return false;
    }
}