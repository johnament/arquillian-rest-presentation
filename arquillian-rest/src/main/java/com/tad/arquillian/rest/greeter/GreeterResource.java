package com.tad.arquillian.rest.greeter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.tad.arquillian.rest.local.LocalStorageBean;

@RequestScoped
@Path("/greet")
public class GreeterResource {
	@EJB
	private LocalStorageBean localStorage;
	
	@Produces("text/plain")
	@GET
	@Path("/{name}")
	public String greet(@PathParam("name") String name) {
		String msg = String.format("Hello, %s!",name);
		localStorage.addMessage("Sent back "+msg);
		return msg;
	}
}
