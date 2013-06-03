package com.tad.arquillian.rest.greeter;

import org.junit.Assert;
import org.junit.Test;

import com.tad.arquillian.rest.greeter.GreeterResource;
import com.tad.arquillian.rest.local.LocalStorageBean;
import com.tad.arquillian.service.greeter.GreeterService;

public class GreeterUnitTest {
	@Test
	public void testGreeterResource() {
		GreeterService gs = new GreeterService();
		LocalStorageBean lsb = new LocalStorageBean();
		lsb.init();
		GreeterResource gr = new GreeterResource();
		gr.setGreeterService(gs);
		gr.setLocalStorage(lsb);
		Assert.assertEquals("Hello, Bob!",gr.greet("Bob"));
	}
}
