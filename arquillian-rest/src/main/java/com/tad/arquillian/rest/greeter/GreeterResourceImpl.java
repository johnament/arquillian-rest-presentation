package com.tad.arquillian.rest.greeter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.tad.arquillian.rest.local.LocalStorageBean;
import com.tad.arquillian.service.greeter.GreeterService;

@RequestScoped
@Path("/greet")
public class GreeterResourceImpl implements GreeterResource {
	@Inject
	private GreeterService greeterService;
	
	@EJB
	private LocalStorageBean localStorage;
	
	@Produces("text/plain")
	@GET
	@Path("/{name}")
	public String greet(@PathParam("name") String name) {
		String msg = greeterService.greet(name);
		localStorage.addMessage("Sent back "+msg);
		return msg;
	}
	
	void setGreeterService(GreeterService gs) {
		this.greeterService = gs;
	}
	void setLocalStorage(LocalStorageBean lsb) {
		this.localStorage = lsb;
	}

	@Override
	@Produces("text/plain")
	@Consumes("text/plain")
	@POST
	public String greet(InputStream is) {
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		try {
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Data sent was "+sb.toString();
	}

	@Override
	@Produces({"application/json","application/xml"})
	@GET
	public GreeterObject readParam(@QueryParam("source") String source) {
		GreeterObject go = new GreeterObject();
		go.setValue(source);
		go.setSize(source.length());
		return go;
	}
}
