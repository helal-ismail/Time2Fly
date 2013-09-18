package com.modules;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

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
	
	public Object[] exportSortedList(){
		tabs =  values().toArray();
		Arrays.sort(tabs);
		return tabs;
	}
	

	
	
	
	
	

}
