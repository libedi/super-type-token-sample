package com.example.demo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

// new TypeReference<List<String>>() {} 형식으로 생성하기 위해서 abstract class 로 선언.
public abstract class TypeReference<T> {

	private Type type;
	
	protected TypeReference() {
		Type superClassType = this.getClass().getGenericSuperclass();
		if(superClassType instanceof ParameterizedType == false) {
			throw new IllegalArgumentException("TypeReference는 항상 실제 타입 파라미터 정보와 함께 생성되어야 합니다.");
		}
		this.type = ((ParameterizedType) superClassType).getActualTypeArguments()[0];
	}
	
	public Type getType() {
		return this.type;
	}
}
