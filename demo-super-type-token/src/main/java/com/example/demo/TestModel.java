package com.example.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestModel {
	private int idx;
	private String name;
}
