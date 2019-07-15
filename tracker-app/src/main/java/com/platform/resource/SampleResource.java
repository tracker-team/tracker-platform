package com.platform.resource;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracker")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {

    @Path("/sample")
    @GET
    @Timed(absolute = true,name="om.tracker.com.platform.bootstrap.resource.SampleResource.sample")
    public Response sample() {
        return Response.ok("this is test").build();
    }
}

