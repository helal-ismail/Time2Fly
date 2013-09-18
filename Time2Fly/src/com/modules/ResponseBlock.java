package com.modules;

public class ResponseBlock {
	public int num;
	public boolean result;
	public String unix_ts;
	public int update_rate;
	public String version;

	public ResponseBlock(String ts, int rate, String ver) {
		unix_ts = ts;
		update_rate = rate;
		version = ver;
	}
}


