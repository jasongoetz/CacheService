package com.jasongoetz.service;

import com.jasongoetz.persistence.Store;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheServiceTest {

    @InjectMocks
    private CacheService subject;

    @Mock
    private Store store;

    @Test
    public void getContentTest() {
        int id = 27;
        String bodyMessage = "This is a message";
        Response response = Response.status(HttpStatus.SC_OK).entity(bodyMessage).build();
        when(store.get(id)).thenReturn(response);
        Response content = subject.getContent(id);
        assertThat(content).isNotNull();
        assertThat(content.getEntity()).isEqualTo(response.getEntity());
    }

}
