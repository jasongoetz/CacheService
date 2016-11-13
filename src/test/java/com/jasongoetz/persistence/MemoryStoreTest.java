package com.jasongoetz.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class MemoryStoreTest {

    private MemoryStore memoryStore = new MemoryStore();

    @Test
    public void idGeneratorTest() {
        assertThat(memoryStore.get(999)).isNull();
    }

    @Test
    public void saveAndRetrieve() {
        Object data = new Object();
        Integer key = memoryStore.save(data);
        Object returnedData = memoryStore.get(key);
        assertThat(returnedData).isEqualTo(data);
    }

}
