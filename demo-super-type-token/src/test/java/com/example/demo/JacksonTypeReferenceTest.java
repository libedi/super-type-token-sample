package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JacksonTypeReferenceTest {
	
	private ObjectMapper mapper;
	private List<TestModel> list;
	
	@Before
	public void before() {
		mapper = new ObjectMapper();
		list = Arrays.asList(TestModel.builder().idx(1).name("name1").build(),
				TestModel.builder().idx(2).name("name2").build(),
				TestModel.builder().idx(3).name("name3").build(),
				TestModel.builder().idx(4).name("name4").build());
	}
	
	@Test
	public void test01_NonUseTypeReference() throws Exception {
		String str = this.mapper.writeValueAsString(list);
		System.out.println(str);
		
		// List<TestModel>.class 를 사용할 수 없다.
		@SuppressWarnings("rawtypes")
		List rawtypesList = this.mapper.readValue(str, List.class);
		for(int i=0; i<rawtypesList.size(); i++) {
			Object obj = rawtypesList.get(i);
			// class java.util.LinkedHashMap 이 찍힌다.
			System.out.println(obj.getClass().toString() + " : " + obj.toString());
		}
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> genericsList = this.mapper.readValue(str, List.class);
		genericsList.stream().forEach(System.out::println);
	}
	
	@Test
	public void test02_UseTypeReference() throws Exception {
		String str = this.mapper.writeValueAsString(list);
		System.out.println(str);
		
		// com.fasterxml.jackson.core.type.TypeReference 을 사용하여 List<TestModel>.class 와 동일한 효과를 얻었다!
		List<TestModel> typeSafeList = this.mapper.readValue(str, new TypeReference<List<TestModel>>() {});
		typeSafeList.stream().forEach(System.out::println);
	}

}
