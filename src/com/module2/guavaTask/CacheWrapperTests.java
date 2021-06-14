package com.module2.guavaTask;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CacheWrapperTests {
    @Test
    public void whenCacheMiss_thenValueIsComputed() {
        CacheWrapper cache = new CacheWrapper();

        assertEquals(0, cache.size());
        cache.put("hello");
        assertEquals("HELLO", cache.get("hello").toString());
        assertEquals(1, cache.size());
    }

}
