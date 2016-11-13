package com.jasongoetz.resource;

import com.jasongoetz.domain.RequestURL;
import com.jasongoetz.service.CacheService;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;

@Path("/cache")
@Component
public class CacheResource {

    @Autowired
    private CacheService cacheService;

    @POST
    public Response addToCache(RequestURL requestURL, @Context UriInfo uriInfo) {
        if (requestURL == null || requestURL.getUrl() == null) {
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
        try {
            Integer cacheId = cacheService.addURL(requestURL);
            URI location = (cacheId != null) ? uriInfo.getAbsolutePathBuilder().path(cacheId.toString()).build() : null;
            return Response.created(location).build();
        } catch (IOException e) {
            return Response.status(HttpStatus.SC_BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getFromCache(@PathParam("id") final Integer id) {
        Response cachedResponse = cacheService.getContent(id);
        if (cachedResponse == null) {
            return Response.status(HttpStatus.SC_NOT_FOUND).build();
        }
        return cachedResponse;
    }


}
