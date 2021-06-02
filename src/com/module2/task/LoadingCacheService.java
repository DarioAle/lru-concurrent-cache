package com.module2.task;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.util.*;

public class LoadingCacheService {
    private final int MAX_CAPACITY;
    private LinkedList<FrequencyList> frequencies = new LinkedList<>();
    private Map<Integer, CacheNode> cache = new HashMap<>();

    public LoadingCacheService(int capacity) {
        this.MAX_CAPACITY = capacity;
    }

    public StringWrapper get(int key) {
        if(cache.containsKey(key)) {
            CacheNode cn = cache.get(key);


            updateFrequency(cn);

            return cn.getValue();
        }
        return null;
    }

    public void put(int key, StringWrapper value) {
        if(!cache.containsKey(key)) {
            if(cache.size() > this.MAX_CAPACITY)
                evict();

            CacheNode tmp = new CacheNode(key, value, 0);
            cache.put(key, tmp);
            addEntry(tmp);

        } else {
            CacheNode cn = cache.get(key);
            cn.setValue(value);

            updateFrequency(cn);
        }
    }

    private void addEntry(@NotNull CacheNode cn) {
        // update frequency of this cache node
        cn.setFrequency(cn.getFrequency() + 1);

        // MAYBE_TODO Iterate over all the frequencies with a binary search
        // although it might not improve performance since it's a LinkedList
        // and not an array list.

        // if not present insert the new list on its place

        int i = 0;
        boolean isEqual = false;
        for(var freq : this.frequencies) {
            if(cn.getFrequency() <= freq.getFrequency()) {
                if(cn.getFrequency() == freq.getFrequency())
                    isEqual = true;

                break;
            }
            i++;
        }

        // get list on index i if frequency was equal
        if(isEqual) {
            // append to the end of the first frequency list
            this.frequencies.get(i).getFreqList().addLast(cn);
        } else {
            // else create the new list and put it in the correct frequency list.

            // in case i is bigger than the size make the adjustment
            LinkedList<CacheNode> ll = new LinkedList<>();
            ll.add(cn);
            FrequencyList newFrList = new FrequencyList(cn.getFrequency(), ll);
            if(i >= this.frequencies.size())
                this.frequencies.add(newFrList);
            else
                this.frequencies.add(i, newFrList);
        }

    }

    private void evict() {
        // Retrieved lest frequently used elements at the beginning
        FrequencyList lowestFrequencyList = this.frequencies.getFirst();

        CacheNode cn = lowestFrequencyList.getFreqList().removeFirst();

        if(lowestFrequencyList.getFreqList().size() == 0) {
            this.frequencies.remove(lowestFrequencyList);
        }

        this.cache.remove(cn.getKey());
    }

    private void updateFrequency(CacheNode cn) {
        // remove from current position
        int f = cn.getFrequency();

        // find index of the frequency
        int i = 0;
        for(var freq : this.frequencies) {
            if(freq.getFrequency() == f)
                break;
            i++;
        }

        // remove from index
        FrequencyList fl = this.frequencies.get(i);
        fl.getFreqList().remove(cn);

        // add to new position
        addEntry(cn);
    }

    public int size() {
        return this.cache.size();
    }
}
