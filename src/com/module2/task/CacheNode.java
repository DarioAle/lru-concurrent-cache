package com.module2.task;



public class CacheNode {

    private final String key;
    private StringWrapper value;
    private int frequency;

    public String getKey() {
        return key;
    }

    public CacheNode(String key, StringWrapper value, int frequency) {
        this.key = key;
        this.value = value;
        this.frequency = frequency;
    }

    public StringWrapper getValue() {
        return value;
    }

    public void setValue(StringWrapper value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
