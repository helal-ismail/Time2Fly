package com.countyapps.network;


public class SearchByName extends SearchAPI{
	
	
	public SearchByName(String name, int page, boolean append){
		setRequestparams(name, page, append);
	}
	
	private void setRequestparams(String name, int page, boolean append){
		name = name.replace(" ", "%20");
		this.URL = com.countyappstest.core.Constants.SearchByNameURL
				+ "value="+name+"&page="+page;
		this.append = append;
	}

}
