package com.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MyHash extends HashMap<String, Tab>{
	Object[] tabs = null;
	
	public int searchByTitle(String title){
		if(tabs == null)
			return -1;
		
		String callSign = title.split(" | ")[0];
		for(int i = 0 ; i < tabs.length ; i ++)
		{
			
			if(((Tab)tabs[i]).callSign.equalsIgnoreCase(callSign))
				return i;
		}
		return -1;
	}
	
	
	public Object[] search(String callSign){
		Object[] array = values().toArray();
		ArrayList<Tab> list = new ArrayList<Tab>();
		for(int i = 0 ; i < array.length ; i ++)
		{
			Tab t = (Tab)array[i];
			if(t.callSign.toLowerCase().contains(callSign.toLowerCase()))
				list.add(t);
		}
		return list.toArray();
	}
	
	public Object[] exportSortedList(Object[] tabs){
		this.tabs = tabs;
		Arrays.sort(tabs);
		return tabs;
	}
	

	
	
	
	
	

}
