package com.platform.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.tracker.dao.StepDao;
import com.tracker.entities.Step;
import com.tracker.utility.IdGenerator;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracker")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {

    @Inject
    private StepDao stepDao;

    @Path("/sample")
    @GET
    @Timed(absolute = true,name="com.tracker.com.platform.bootstrap.resource.SampleResource.sample")
    @UnitOfWork
    public Response sample() {
        Step step=new Step();
        step.setDescription("tesitngn");
        step.setExternalStepId(IdGenerator.generateStepId());
        stepDao.create(step);
        return Response.ok("this is test").build();
    }
}

