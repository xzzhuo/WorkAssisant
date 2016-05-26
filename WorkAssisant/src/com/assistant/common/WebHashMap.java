package com.assistant.common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class WebHashMap {

	static private WebHashMap mInstance = new WebHashMap();
	private Map<String, Integer> mFileHasMap = new HashMap<String, Integer>();
	
	private WebHashMap()
	{
		
	}
	
	public boolean CheckFile(String path)
	{
		boolean result = true;
		
		File file = new File(path);
		if (mFileHasMap.containsKey(file.getName()))
		{
			Integer hash = mFileHasMap.get(file.getName());
			result = (hash == file.hashCode());
		}
		else
		{
			mFileHasMap.put(file.getName(), file.hashCode());
		}
		
		return result;
	}
	
	static WebHashMap getInstance()
	{
		return mInstance;
	}
}
