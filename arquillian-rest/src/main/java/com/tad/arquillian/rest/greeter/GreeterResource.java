package com.tad.arquillian.rest.greeter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.tad.arquillian.rest.local.LocalStorageBean;
import com.tad.arquillian.service.greeter.GreeterService;

@RequestScoped
@Path("/greet")
public class GreeterResource {
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
}
