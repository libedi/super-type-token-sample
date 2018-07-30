package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestModel {
	private int idx;
	private String name;
}
