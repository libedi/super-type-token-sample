package com.example.demo;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiController {

	@GetMapping("/test")
	public List<TestModel> getTestModelList() {
		return Arrays.asList(TestModel.builder().idx(1).name("name1").build(),
				TestModel.builder().idx(2).name("name2").build(),
				TestModel.builder().idx(3).name("name3").build(),
				TestModel.builder().idx(4).name("name4").build());
	}
}
