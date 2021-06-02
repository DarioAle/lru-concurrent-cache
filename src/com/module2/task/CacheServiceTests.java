package com.module2.task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CacheServiceTests {
    @Test
    public void whenCacheMiss_thenValueIsComputed() {
//        CacheLoader<String, String> loader;
//        loader = new CacheLoader<String, String>() {
//            @Override
//            public String load(String key) {
//                return key.toUpperCase();
//            }
//        };

        LoadingCacheService cache;
        cache = new LoadingCacheService(10);
        StringWrapper sw = new StringWrapper("HELLO");

        assertEquals(0, cache.size());
        cache.put(1, sw);
        assertEquals("HELLO", cache.get(1).toString());
        assertEquals(1, cache.size());
    }
}
