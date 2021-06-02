package com.module2.task;

public abstract class SubCacheLoader {

    protected SubCacheLoader() { }

    public abstract String load(String key) throws Exception;
}
