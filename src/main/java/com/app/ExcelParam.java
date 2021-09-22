package com.app;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelParam {
	
	private HashMap<String,Object> map;
	public ExcelParam() {
			this.map = new HashMap<String, Object>();
	}

	public void put(String key, Object value) {
		Pattern regex = Pattern.compile("([A-Z][0-9]+)");
		Matcher m = regex.matcher(key);
		if(!m.find()) 
			throw new IllegalArgumentException("format false");
		String keytmp = "_"+key;
		map.put(keytmp, value);
	}

	public HashMap<String,Object> convertToMap(){
		return map;
	}
	
	public void clear() {
		this.map.clear();
	}
	
}
