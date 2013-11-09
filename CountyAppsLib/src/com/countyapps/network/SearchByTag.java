package com.countyapps.network;

public class SearchByTag extends SearchAPI{

	public SearchByTag(String tag_num, String secret_code){
		setRequestparams(tag_num, secret_code);
	}
	
	private void setRequestparams(String tag_num, String secret_code){
		this.URL = com.countyappstest.core.Constants.SearchByTagURL
				+ "tag_number="+tag_num+"&secret_code="+secret_code;
	}
	
}
