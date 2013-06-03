package com.tad.arquillian.rest.test;

import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.rest.greeter.GreeterResource;
import com.tad.arquillian.rest.local.LocalStorageBean;
import com.tad.arquillian.service.greeter.GreeterService;

@RunWith(Arquillian.class)
@RunAsClient
public class OtherGreeterResourceTest {
	@Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "arquillian-rest.war")
                .addClasses(GreeterResource.class, LocalStorageBean.class,GreeterService.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @ArquillianResource
    private URL url;

    @Test
    public void testViaHTTPGet() throws Exception {
        final HttpClient httpclient = new DefaultHttpClient();
        try {
            final URL path = new URL(this.url, "/arquillian-rest/greet/Bob");
            final HttpGet get = new HttpGet(path.toURI());
            final ResponseHandler<String> responseHandler = new BasicResponseHandler();
            final String responseBody = httpclient.execute(get, responseHandler);
            Assert.assertEquals("Hello, Bob!", responseBody);
        }
        finally {
            httpclient.getConnectionManager().shutdown();
        }
    }
}
