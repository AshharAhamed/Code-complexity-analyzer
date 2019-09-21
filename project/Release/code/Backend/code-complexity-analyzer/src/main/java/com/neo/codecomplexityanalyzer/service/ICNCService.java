package com.neo.codecomplexityanalyzer.service;

import java.util.HashMap;

public interface ICNCService {
	
	public /*int*/HashMap<Integer, Integer> getScore();

	public HashMap<Integer, Integer> getNestedIfControlScore();
	
	public /*int*/HashMap<Integer, Integer> getNestedForScore();
	
	public /*int*/HashMap<Integer, Integer> getNestedWhileScore();
	
	public /*int*/HashMap<Integer, Integer> getNestedDoWhileScore();
	
	
}
