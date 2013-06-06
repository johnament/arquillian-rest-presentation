package com.tad.arquillian.rest.greeter;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/greet")
public interface GreeterResource {
	@Produces("text/plain")
	@GET
	@Path("/{name}")
	public String greet(@PathParam("name") String name);
	
	@Produces("text/plain")
	@Consumes("text/plain")
	@POST
	public String greet(InputStream is);
	
	@Produces({"application/json","application/xml"})
	@GET
	public GreeterObject readParam(@QueryParam("source") String source);
}
