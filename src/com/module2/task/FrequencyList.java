package com.module2.task;

import java.util.LinkedList;


public class FrequencyList {
    private int frequency;
    private LinkedList<CacheNode> freqList;

    public FrequencyList(int frequency, LinkedList<CacheNode> freqList) {
        this.frequency = frequency;
        this.freqList = freqList;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public LinkedList<CacheNode> getFreqList() {
        return freqList;
    }

    public void setFreqList(LinkedList<CacheNode> freqList) {
        this.freqList = freqList;
    }
}
