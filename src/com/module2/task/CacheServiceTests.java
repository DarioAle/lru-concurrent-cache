package com.module2.task;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class CacheServiceTests {
    @Test
    public void loading_when_is_not_present() throws Exception{

        LoadingCacheService cache;
        cache = new LoadingCacheService(10, new SubCacheLoader() {
            @Override
            public StringWrapper load(String key) {
                return super.load(key.toUpperCase());
            }
        });
        StringWrapper sw = new StringWrapper("HELLO");

        assertEquals(0, cache.size());
        cache.put("hello");
        assertEquals("HELLO", cache.get("hello").toString());
        assertEquals(1, cache.size());
        cache.printCacheStats();
        Thread.sleep(10_000);

        cache.printCacheStats();
        cache.service.shutdown();
    }

    public static void main(String [] args) throws Exception{
        ScheduledExecutorService service
                = Executors.newSingleThreadScheduledExecutor();
        Callable<String> task1 = () -> { System.out.println("Hello Zoo"); return "fe" ; };
        Callable<String> task2 = () -> { System.out.println("Monkey"); return "Monkey"; };
        ScheduledFuture<?> r1 = service.schedule(task1, 10, TimeUnit.SECONDS);
        ScheduledFuture<?> r2 = service.schedule(task2, 5,  TimeUnit.SECONDS);
        r1.get();
        r2.get();
        System.out.println("Main thread");
        service.shutdown();
    }
}
