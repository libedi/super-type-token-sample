package com.example.demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * SimpleTypeSafeMap 개선 : Class<?> 에서 TypeReference 로
 * @author Sang jun, Park
 *
 */
public class TypeSafeMap {

	// key로 사용되던 Class<?> 대신 Type 으로 변경
	private Map<Type, Object> map = new HashMap<>();
	
	public <T> void put(TypeReference<T> k, T v) {
		this.map.put(k.getType(), v);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(TypeReference<T> k) {
		if(k.getType() instanceof ParameterizedType) {
			return ((Class<T>) ((ParameterizedType) k.getType()).getRawType()).cast(map.get(k.getType()));
		} else {
			return ((Class<T>) k.getType()).cast(map.get(k.getType()));
		}
	}
}
