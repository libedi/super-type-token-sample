package com.example.demo;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpringParameterizedTypeReferenceTest {
	
	private final String REMOTE_URI = "http://localhost:8080/test";

	@Autowired
	private RestTemplate restTemplate;
	
	@Test
	public void test01_NonUseParameterizedTypeReference() throws Exception {
		String resultList = this.restTemplate.getForObject(this.REMOTE_URI, String.class);
		assertNotNull(resultList);
	}
	
}
