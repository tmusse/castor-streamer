package com.eldergods.Castor;

import java.util.Collection;

import android.app.Activity;
import android.os.Bundle;
import android.test.PerformanceTestCase;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Menu.Item;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.eldergods.Util.CachedHttpObject;
import com.eldergods.Util.CachedHttpPage;

public class CastorLayoutActivity extends Activity {

	protected Collection _dataList;

	protected int _page = 0;
	protected int _pageCount = 0;
	protected int _selected = 0;
	protected int _selectedRow = 0;
	protected int _selectedColumn = 0;
	protected ListView _listViews[] = new ListView[3];
	
	protected boolean _eventHandling = false;

	static int _pageSize = 20;

	static int[] _listIds = {R.id.list1, R.id.list2, R.id.list3};

	static String _tag = CastorLayoutActivity.class.getName();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.list);

    	_listViews[0] = (ListView) findViewById(_listIds[0]);
    	_listViews[1] = (ListView) findViewById(_listIds[1]);
    	_listViews[2] = (ListView) findViewById(_listIds[2]);

        redrawLists();
    }
    
    public void onPostCreate(Bundle icicle) 
    {
    	redrawCurrent();
    	super.onPostCreate(icicle);
    }

    public int getPage()
    {
    	return _page;
    }

    public void setPage(int newPage)
    {
    	_page = newPage;
    }

    public void redrawLists()
    {
    	int page = 0;
    	String pageTexts[][] = new String[3][];

		int size = _dataList.size();
		    _pageCount = ((size % _pageSize == 0) ? 0 : 1) + (size/_pageSize) - 2;
		if(_pageCount < 0) _pageCount = 0;
		int start = _page * _pageSize;
		int len = (size - start > _pageSize) ? _pageSize : size - start;

		String[] pageText = new String[len];
		int i = 0;
		for(Object j : _dataList)
		{
			if(i < start) 
			{ 
				i++;
				continue;
			}
			
			pageText[i-start] = j.toString();
			
			i++;
			if(i % _pageSize == 0 || i == size)
			{
				pageTexts[page] = pageText;
				if(++page > 2) break;
				start = ((page + _page)* _pageSize);
				len = (size - start > _pageSize) ? _pageSize : size - start;
				if(len < 0) len = 0;
				pageText = new String[len];				
			}
		}
		if(pageTexts[1] == null) pageTexts[1] = new String[0];
		if(pageTexts[2] == null) pageTexts[2] = new String[0];

    	for(page = 0; page < 3; page++)
    	{
    		pageText = pageTexts[page];
    		ListAdapter pageListAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.row_item,pageText);
    		ListView listView = _listViews[page];
    		listView.setAdapter(pageListAdapter);
    		listView.setFocusableInTouchMode(false);
    		listView.setOnFocusChangeListener(_onFocusChangeListener);
    		listView.setOnKeyListener(_onKeyListener);
    		listView.setOnItemClickListener(_onItemClickListener);
    		listView.setOnItemSelectedListener(_onItemSelectedListener);
    	}
    	
    }
    
    protected void redrawCurrent()
    {
		int selectedOffset = _selected - (_page * _pageSize);
		
		int page = selectedOffset / _pageSize;
		ListView listView = _listViews[page];
		selectedOffset -= _pageSize * page;
		for(int i = 0; i < _listViews.length; i++)
			if(i == page)
				listView.setSelection(selectedOffset);
			else
				listView.setSelection(-1);
		listView.bringToFront();
    }

    protected ListView getActiveListView()
    {
    	return _listViews[_selectedColumn];
    }
    
    protected OnFocusChangeListener _onFocusChangeListener = new View.OnFocusChangeListener()
    {
		public void onFocusChanged(View v,boolean hasFocus)
		{
			Log.v(_tag,"focus changed on "+v.getId()+"; hasFocus "+hasFocus);
			for(int i = 0; i < 3; i++)
				if(v == _listViews[i])
				{
					_selectedColumn = i;
					_selectedRow = ((ListView)v).getSelectedItemPosition();
					_selected = (_page + _selectedColumn) * _pageSize + _selectedRow;
					break;
				}
			redrawCurrent();
    	}
    };

    protected OnItemSelectedListener _onItemSelectedListener = new OnItemSelectedListener()
    {
    	public void onItemSelected(AdapterView parent, View v, int position, long id)
    		{Log.v(_tag,"item selected"); itemChange(parent,v,position,id);}
    	public void onNothingSelected(AdapterView praent)
    		{}
    };

    protected OnItemClickListener _onItemClickListener = new OnItemClickListener()
    {
    	public void onItemClick(AdapterView parent, View v, int position, long id)
    	   {Log.v(_tag,"item clicked"); itemChange(parent,v,position,id);}
    };

    protected void itemChange(AdapterView parent, View v, int position, long id)
    {
    	if(_eventHandling) return;
    	_eventHandling = true;
    	
    	ListView lv = null;
    	if(v instanceof ListView) lv = (ListView)v;
    	if(lv == null && parent instanceof ListView) lv = (ListView)parent;

    	int i;
    	for(i = 0; i < _listIds.length; i++)
    		if(lv == _listViews[i] && lv.hasFocus())
    		{
    			Log.v(_tag, "select column "+i+" at "+position+","+id);
    			_selectedColumn = i;
    		}

    	_selectedRow = position;
    	_selected = (_page + _selectedColumn) * _pageSize + _selectedRow;
    	_eventHandling = false;
    }

    protected OnKeyListener _onKeyListener = new OnKeyListener()
    {
    	public boolean onKey(View v, int keyCode, KeyEvent event) 
    	{
    		if(event.getAction() != KeyEvent.ACTION_DOWN) return false;
    		Log.v(_tag,"item key down");
    		int originalPage = _page;
    		if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
    		{
    			if(_selectedColumn == 2)
    			{
    				if(_page == _pageCount) return true;
    				else ++_page;
    			} else
    				++_selectedColumn;
    			_selected = (_page + _selectedColumn) * _pageSize + _selectedRow;
    		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
    		{
    			if(_selectedColumn == 0)
    			{
    				if(_page == 0) return true;
    				else --_page;
    			} else
    				--_selectedColumn;
    			_selected = (_page + _selectedColumn) * _pageSize + _selectedRow;
    		} else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
    		{
    			return onCenterButton();
    		} else if (keyCode == KeyEvent.KEYCODE_BACK)
    		{
    			return onBackButton();
    		}

    		if(originalPage != _page) redrawLists();
    		return false;
    	}
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(Item item) {
    	return super.onOptionsItemSelected(item);
    }
    
    protected boolean onCenterButton()
    	{ return false; }

    protected boolean onBackButton()
	{ return false; }
}
