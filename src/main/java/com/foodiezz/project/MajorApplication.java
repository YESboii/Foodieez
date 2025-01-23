package com.foodiezz.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//server:localhost:8090/admin
@SpringBootApplication
//@Configuration-class can be used by the spring ioc container as bean definition.
//@EnableAutoConfiguration-auto-configures the beans that are present in class path.
//@ComponentScan-scans a package and all of it's subpackages,looking for classes that can be automatically registered as beans in spring container.
public class MajorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MajorApplication.class, args);
	}

}
