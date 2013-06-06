package com.tad.arquillian.rest.test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.rest.greeter.GreeterObject;
import com.tad.arquillian.rest.greeter.GreeterResource;
import com.tad.arquillian.rest.greeter.GreeterResourceImpl;
import com.tad.arquillian.rest.local.LocalStorageBean;
import com.tad.arquillian.service.greeter.GreeterService;

@RunWith(Arquillian.class)
@RunAsClient
public class OtherGreeterResourceTest {
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "arquillian-rest.war")
				.addClasses(GreeterResource.class, LocalStorageBean.class,
						GreeterObject.class, GreeterService.class,
						GreeterResourceImpl.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("openejb-jar.xml", "openejb-jar.xml");
	}

	@ArquillianResource
	private URL url;

	private HttpClient httpclient;

	@Before
	public void setup() {
		httpclient = new DefaultHttpClient();
	}

	@After
	public void close() {
		httpclient.getConnectionManager().shutdown();
	}

	@Test
	public void testViaHTTPGet() throws Exception {

		final URL path = new URL(this.url, "/arquillian-rest/greet/Bob");
		final HttpGet get = new HttpGet(path.toURI());
		final ResponseHandler<String> responseHandler = new BasicResponseHandler();
		final String responseBody = httpclient.execute(get, responseHandler);
		Assert.assertEquals("Hello, Bob!", responseBody);

	}

	@Test
	public void testViaHTTPPost() throws Exception {
		final URL path = new URL(this.url, "/arquillian-rest/greet");
		final HttpPost post = new HttpPost(path.toURI());
		final ResponseHandler<String> responseHandler = new BasicResponseHandler();
		post.setEntity(new StringEntity("Hello, Bob!", ContentType.TEXT_PLAIN));
		final String responseBody = httpclient.execute(post, responseHandler);
		Assert.assertEquals("Data sent was Hello, Bob!", responseBody);

	}

	@Test
	public void testViaHTTPGetWithParam() throws Exception {
		final URL path = new URL(this.url,
				"/arquillian-rest/greet?source=Ralph");
		final HttpGet get = new HttpGet(path.toURI());
		get.addHeader("accept", "application/json");
		final ResponseHandler<String> responseHandler = new BasicResponseHandler();
		final String responseBody = httpclient.execute(get, responseHandler);
		Assert.assertEquals(
				"{\"greeterObject\":{\"size\":5,\"value\":\"Ralph\"}}",
				responseBody);
	}

	@Test
	public void testViaHTTPGetWithParamXML() throws Exception {
		final URL path = new URL(this.url,
				"/arquillian-rest/greet?source=Ralph");
		final HttpGet get = new HttpGet(path.toURI());
		get.addHeader("accept", "application/xml");
		final ResponseHandler<String> responseHandler = new BasicResponseHandler();
		final String responseBody = httpclient.execute(get, responseHandler);
		Assert.assertEquals(
				"<greeterObject><size>5</size><value>Ralph</value></greeterObject>",
				responseBody);
	}

}
