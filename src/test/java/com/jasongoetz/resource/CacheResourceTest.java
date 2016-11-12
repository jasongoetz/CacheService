package com.jasongoetz.resource;

import com.jasongoetz.domain.RequestURL;
import com.jasongoetz.service.CacheService;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheResourceTest {

    @InjectMocks
    private CacheResource subject;

    @Mock
    private CacheService cacheService;

    private int id = 55;
    private String googleURL = "http://www.google.com";

    @Test
    public void postTest() throws URISyntaxException {
        RequestURL requestURL = new RequestURL();
        requestURL.setUrl(googleURL);
        when(cacheService.addURL(requestURL)).thenReturn(55);
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn((new JerseyUriBuilder()).uri("http://localhost:8080"));
        Response response = subject.addToCache(requestURL, uriInfo);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_CREATED);
    }

    @Test
    public void emptyUrlTest() {
        RequestURL requestURL = new RequestURL();
        Response response = subject.addToCache(requestURL, mock(UriInfo.class));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void nullRequestObjectTest() {
        Response response = subject.addToCache(null, mock(UriInfo.class));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    public void getTest() {
        when(cacheService.getContent(id)).thenReturn(googleURL);
        Response response = subject.getFromCache(id);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_OK);
        assertThat(response.getEntity()).isEqualTo(googleURL);
    }

    @Test
    public void getWithNullResponseTest() {
        when(cacheService.getContent(id)).thenReturn(null);
        Response response = subject.getFromCache(id);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.SC_NOT_FOUND);
        assertThat(response.getEntity()).isNull();
    }

}
