package com.example.demo;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomSuperTypeTokenTest {
	
	@Test
	public void test01_HeterogeneousMap() throws Exception {
		SimpleTypeSafeMap simpleTypeSafeMap = new SimpleTypeSafeMap();
		
		// String.class, Integer.class 는 클래스 리터럴이라고 하며,
		// String.class 의 타입은 Class<String>, Integer.class 의 타입은 Class<Integer> 이다.
		// 타입 토큰은 타입을 나타내는 토큰으로, 클래스 리터럴이 타입 토큰으로 사용된다.
		simpleTypeSafeMap.put(String.class, "abcde");
		simpleTypeSafeMap.put(Integer.class, 123);
		
		// 타입 토큰을 이용해서 별도의 캐스팅 없이도 타입 안정성이 확보된다.
		String v1 = simpleTypeSafeMap.get(String.class);
		Integer v2 = simpleTypeSafeMap.get(Integer.class);

		assertTrue(v1 instanceof String);
		assertTrue(v2 instanceof Integer);
		
		System.out.println("Type Token String: " + v1);
		System.out.println("type Token Integer: " + v2);
		
		// 아래와 같은 List<String>.class 라는 클래스 리터럴은 Java언어에서 지원하지 않아 사용불가...
//		simpleTypeSafeMap.put(List<String>.class, Arrays.asList("a", "b", "c"));
	}
	
	@Test
	public void test02_GenericSuperclass() throws Exception {
		// Class.getGenericSuperclass()
		// 바로 위의 수퍼 클래스가 ParameterizedType이면,
		// "실제 타입 파라미터들을 반영한 타입을 반환해야 한다."
		Sub sub = new Sub();
		Type typeOfGenericSuperclass = sub.getClass().getGenericSuperclass();
		
		// ~~$1Super<java.util.List<java.lang.String>> 라고 나온다!!
		System.out.println(typeOfGenericSuperclass);
		
		// 수퍼 클래스가 ParameterizedType 이므로 ParameterizedType으로 캐스팅 가능
		// ParameterizedType의 getActualTypeArguments()으로 실제 타입 파라미터의 정보를 구한다!!
		Type actualType = ((ParameterizedType) typeOfGenericSuperclass).getActualTypeArguments()[0];
		
		// java.util.List<java.lang.String> 가 구해진다!!
		System.out.println(actualType);
		
		SimpleTypeSafeMap simpleTypeSafeMap = new SimpleTypeSafeMap();

		// 근데 여기서 컴파일 에러...
		// The method put(Class<T>, T) in the type SimpleTypeSafeMap is not applicable for the arguments (Type, List<String>)
		// Class<T> 와 Type 은 안맞잖아!
//		simpleTypeSafeMap.put(actualType, Arrays.asList("a", "b", "c"));
		
		// Class<?>만 받을 수 있는 SimpleTypeSafeMap은 이제 퇴장할 때가 된 것 같다.
		// Class<?>보다 더 General한 java.lang.reflect.Type 같은 키도 받을 수 있도록 약간 고도화한 TypeSafeMap을 만날 때가 되었다.
	}
	
	@Test
	public void test03_TypeSafeMap() throws Exception {
		TypeSafeMap typeSafeMap = new TypeSafeMap();
		
		typeSafeMap.put(new TypeReference<String>() {}, "abcde");
		typeSafeMap.put(new TypeReference<Integer>() {}, 123);
		
		// 드디어 List<String>을 쓸 수 있다!
		// new TypeReference<List<String>>() {} 을 사용해서, List<String>.class 와 동일한 효과를!
		typeSafeMap.put(new TypeReference<List<String>>() {}, Arrays.asList("a", "b", "c"));
		
		// List<List<String>> 처럼 중첩된 ParameterizedType로 사용 가능!
		typeSafeMap.put(new TypeReference<List<List<String>>>() {},
				Arrays.asList(Arrays.asList("A", "B", "C"), Arrays.asList("a", "b", "c")));
		
		// Map<K, V> 도 된다!
		Map<String, String> paramStrMap = new HashMap<>();
		paramStrMap.put("key1", "value1");
		paramStrMap.put("key2", "value2");
		typeSafeMap.put(new TypeReference<Map<String, String>>() {}, paramStrMap);
		
		
		/*** 이제부터 get() 을 통해 타입 안전성있게 값을 가져와보자! ***/
		
		// 수퍼 타입 토큰을 이용해서 별도의 캐스팅 없이도 안전하다.
		String v1 = typeSafeMap.get(new TypeReference<String>() {});
		Integer v2 = typeSafeMap.get(new TypeReference<Integer>() {});
		
		assertTrue(v1 instanceof String);
		assertTrue(v2 instanceof Integer);
		
		System.out.println("TypeReference String: " + v1);
		System.out.println("TypeReference Integer: " + v2);
		
		// Java 언어에서 지원하지 않는 클래스 리터럴을 사용하지 않고도
		// List<String>.class 라는 타입을 사용할 수 있다!
		List<String> listString = typeSafeMap.get(new TypeReference<List<String>>() {});
		
		// List<List<String>> 처럼 중첩된 ParameterizedType도 사용 가능!
		List<List<String>> listListString = typeSafeMap.get(new TypeReference<List<List<String>>>() {});
		
		System.out.println("TypeReference List<String> values: " + listString.stream().collect(Collectors.joining(",")).toString());
		System.out.println("TypeReference List<List<String>> values: " + listListString.stream().flatMap(List::stream).collect(Collectors.joining(",")).toString());
	}

}
