package com.jasongoetz.persistence;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MemoryStore implements Store {

    private final AtomicInteger sequence = new AtomicInteger();

    private int getId() {
        return sequence.incrementAndGet();
    }

    ConcurrentHashMap<Integer, Object> store = new ConcurrentHashMap<>();

    @Override
    public Integer save(Object data) {
        Integer id = getId();
        store.put(id, data);
        return id;
    }

    @Override
    public Object get(Integer id) {
        return store.get(id);
    }
}
