package com.example.demo;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringParameterizedTypeReferenceTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void test01_NonUseParameterizedTypeReference() throws Exception {
		@SuppressWarnings("rawtypes")
		List resultList = this.restTemplate.getForObject("/test", List.class);
		assertNotNull(resultList);
		for(int i=0; i<resultList.size(); i++) {
			Object obj = resultList.get(i);
			// class java.util.LinkedHashMap
			log.info("{} : {}", obj.getClass().toString(), obj.toString());
		}
	}
	
	@Test
	public void test02_UseParameterizedTypeReference() throws Exception {
		List<TestModel> resultList = this.restTemplate
				.exchange("/test", HttpMethod.GET, null, new ParameterizedTypeReference<List<TestModel>>() {})
				.getBody();
		resultList.stream().forEach(m -> log.info(m.toString()));
	}
	
}
