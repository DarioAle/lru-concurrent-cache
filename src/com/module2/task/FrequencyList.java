package com.module2.task;

import java.util.concurrent.ConcurrentLinkedQueue;


public class FrequencyList {

    private final int frequency;
    private final ConcurrentLinkedQueue<CacheNode> freqList;

    public FrequencyList(int frequency, ConcurrentLinkedQueue<CacheNode> freqList) {
        this.frequency = frequency;
        this.freqList = freqList;
    }

    public int getFrequency() {
        return frequency;
    }

    public ConcurrentLinkedQueue<CacheNode> getFreqList() {
        return freqList;
    }

}
