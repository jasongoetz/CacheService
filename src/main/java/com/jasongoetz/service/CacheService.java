package com.jasongoetz.service;

import com.jasongoetz.domain.RequestURL;
import com.jasongoetz.persistence.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheService {

    @Autowired
    private Store store;

    public Integer addURL(RequestURL requestURL) {
        return store.save(requestURL.getUrl());
    }

    public String getContent(Integer id) {
        Object data = store.get(id);
        return (String) data;
    }

}
