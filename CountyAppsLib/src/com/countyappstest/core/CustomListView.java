package com.countyappstest.core;

import com.countyapps.network.SearchByName;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class CustomListView extends ListView implements OnScrollListener{

	public CustomListView(Context context) {
		super(context);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		if(visibleItemCount == totalItemCount && Constants.isSearchByName)
		{
			Cache.getInstance().page++;
			SearchByName request = new SearchByName(Cache.getInstance().name, Cache.getInstance().page, true);
			request.execute();
		}
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
	}
	
	

}
