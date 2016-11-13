package com.jasongoetz.service;

import com.jasongoetz.domain.RequestURL;
import com.jasongoetz.persistence.Store;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Component
public class CacheService {

    @Autowired
    private Store store;

    public Integer addURL(RequestURL requestURL) throws IOException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(requestURL.getUrl()).request().get();
        try {
            InputStream input = (InputStream)response.getEntity();
            byte[] bytes = IOUtils.toByteArray(input);
            Response responseToCache = Response.status(response.getStatus()).entity(bytes).encoding("utf-8").replaceAll(response.getHeaders()).build();
            return store.save(responseToCache);
        } finally {
            response.close();
        }
    }

    public Response getContent(Integer id) {
        Response response = (Response) store.get(id);
        return response == null ? null : cloneResponse(response); //Clone the response so we can make repeated reads
    }

    private Response cloneResponse(Response response) {
        return Response.status(response.getStatus()).entity(response.getEntity()).replaceAll(response.getHeaders()).build();
    }

}
