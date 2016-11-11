package com.jasongoetz.rest;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/cache")
@Component
public class CacheResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public String test() {
        return "I am a cache";
    }
}
