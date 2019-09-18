package com.neo.codecomplexityanalyzer.service;

import java.util.HashMap;

public interface ICNCService {

	public HashMap<Integer, Integer> getNestedIfControlScore();
	
	public int getNestedForScore();
	
	public int getNestedWhileScore();
	
	
}
