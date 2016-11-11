package com.jasongoetz.rest;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CacheResourceTest {

    CacheResource subject = new CacheResource();

    @Test
    public void Test() {
        String message = subject.test();
        assertThat(message).isEqualTo("I am a cache");
    }

}
