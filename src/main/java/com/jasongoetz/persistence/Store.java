package com.jasongoetz.persistence;

public interface Store {

    Integer save(Object data);

    Object get(Integer id);

}
