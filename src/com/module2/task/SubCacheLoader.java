package com.module2.task;

public class SubCacheLoader {

    protected SubCacheLoader() { }

    public  StringWrapper load(String key) {
        return new StringWrapper(key);
    }
}
