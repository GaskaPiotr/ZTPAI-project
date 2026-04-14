package com.github.GaskaPiotr.spring_boot_boilerplate;

import org.springframework.boot.SpringApplication;

public class TestSpringBoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringBoilerplateApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
