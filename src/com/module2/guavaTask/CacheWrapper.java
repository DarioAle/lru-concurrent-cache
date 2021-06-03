package com.module2.guavaTask;


import com.google.common.cache.*;
import com.module2.task.StringWrapper;

import java.util.concurrent.TimeUnit;

public class CacheWrapper {
    private final LoadingCache<String, StringWrapper> cache;

    public CacheWrapper() {
        CacheLoader<String, StringWrapper> loader;
        loader = new CacheLoader<String, StringWrapper>() {
            @Override
            public StringWrapper load(String key)  {
                return new StringWrapper(key.toUpperCase());
            }
        };
        RemovalListener<String, StringWrapper> listener;
        listener = new RemovalListener<String, StringWrapper>() {
            @Override
            public void onRemoval(RemovalNotification<String, StringWrapper> n) {
                if(n.wasEvicted()) {
                    String cause = n.getCause().name();
                    System.out.println("Evicting from cache because " + cause + " \n");
                }
            }
        };


        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .maximumSize(100)
                .removalListener(listener)
                .recordStats()
                .build(loader);
    }

    public void put(String k) {
        this.cache.getUnchecked(k);
    }

    public StringWrapper get(String k) {
        return this.cache.getIfPresent(k);
    }

    public void printCacheStats() {
        System.out.println(this.cache.stats().toString());

        StringBuilder sb = new StringBuilder();

        sb.append("Average loading time when an element is inserted in the cache: ");
        sb.append(this.cache.stats().evictionCount()).append(" \n");

        sb.append("Total number of evictions: ");
        /*return value is in nanoseconds and we want milliseconds*/
        sb.append( this.cache.stats().averageLoadPenalty() / 1_000L).append("ms. \n");

        System.out.println(sb);
    }

}
