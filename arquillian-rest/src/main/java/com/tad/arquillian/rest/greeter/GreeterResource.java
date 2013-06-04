package com.tad.arquillian.rest.greeter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/greet")
public interface GreeterResource {
	@Produces("text/plain")
	@GET
	@Path("/{name}")
	public String greet(@PathParam("name") String name);
}
