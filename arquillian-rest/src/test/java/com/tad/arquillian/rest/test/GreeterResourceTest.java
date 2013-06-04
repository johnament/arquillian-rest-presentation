package com.tad.arquillian.rest.test;

import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.rest.greeter.GreeterResource;
import com.tad.arquillian.rest.greeter.GreeterResourceImpl;
import com.tad.arquillian.rest.local.LocalStorageBean;
import com.tad.arquillian.service.greeter.GreeterService;

@RunWith(Arquillian.class)
public class GreeterResourceTest {
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "arquillian-rest.war")
				.addClasses(GreeterResource.class,LocalStorageBean.class,
						GreeterService.class,GreeterResourceImpl.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Inject
	private GreeterResource gr;
	
	@EJB
	private LocalStorageBean lsb;
	
	@Test
	@InSequence(1)
	public void testGreeting() {
		Assert.assertEquals("Hello, Bob!",gr.greet("Bob"));
	}
	
	@Test
	@InSequence(2)
	public void testAnotherGreeting() {
		Assert.assertEquals("Hello, Sam!",gr.greet("Sam"));
	}
	
	@Test
	@InSequence(3)
	public void testMessages() {
		List<String> strings = lsb.getEntries();
		Assert.assertEquals(2,strings.size());
		Assert.assertEquals("Sent back Hello, Bob!",strings.get(0));
		Assert.assertEquals("Sent back Hello, Sam!",strings.get(1));
	}
	
}
