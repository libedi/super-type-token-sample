package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class SimpleTypeSafeMap {
	
	private Map<Class<?>, Object> map = new HashMap<>();
	
	public <T> void put(Class<T> k, T v) {
		map.put(k, v);
	}
	
	public <T> T get(Class<T> k) {
		return k.cast(map.get(k));
	}

}
