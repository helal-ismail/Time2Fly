package com.countyappstest.core;

import java.util.ArrayList;

import com.countyapps.model.Record;

public class Cache {

	private static Cache instance = new Cache();
	public static Cache getInstance(){
		return instance;
	}
	
	public ArrayList<Record> searchResults = new ArrayList<Record>();
	public ListAdapter adapter;
	public int page = 1;
	public String name;
	
}

