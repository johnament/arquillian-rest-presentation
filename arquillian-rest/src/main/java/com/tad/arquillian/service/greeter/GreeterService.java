package com.tad.arquillian.service.greeter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreeterService {
	private static final String GREET_FORMAT = "Hello, %s!";

	public String greet(String name) {
		return String.format(GREET_FORMAT, name);
	}
}
