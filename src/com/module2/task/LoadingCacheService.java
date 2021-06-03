package com.module2.task;


import com.google.common.util.concurrent.AtomicDouble;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class LoadingCacheService {

    private final int MAX_CAPACITY;
    private final LinkedList<FrequencyList> frequencies = new LinkedList<>();
    private final Map<String, CacheNode> cache = new ConcurrentHashMap<>();
    private final SubCacheLoader loader;
    public ScheduledExecutorService service;

    private final AtomicDouble averageTimeSpentLoading;
    private final AtomicLong numberOfCacheEvictions;
    private final AtomicLong numberOfPuts;

    public LoadingCacheService(int capacity, SubCacheLoader cl) {
        this.MAX_CAPACITY = capacity;
        this.loader = cl;
        this.service = Executors.newScheduledThreadPool(capacity);

        this.averageTimeSpentLoading = new AtomicDouble();
        this.numberOfCacheEvictions = new AtomicLong();
        this.numberOfPuts = new AtomicLong();
    }

    public StringWrapper get(String key) {
        if(cache.containsKey(key)) {
            CacheNode cn = cache.get(key);

            updateFrequency(cn);

            return cn.getValue();
        }
        return null;
    }

    public void put(String __key) {
        // key must be
        long start = System.currentTimeMillis();
        if(!cache.containsKey(__key)) {
            if(cache.size() > this.MAX_CAPACITY)
                evict();


            StringWrapper value =  this.loader.load(__key);

            CacheNode tmp = new CacheNode(__key, value, 0);
            cache.put(__key, tmp);
            addEntry(tmp);

        } else {
            CacheNode cn = cache.get(__key);
            updateFrequency(cn);
        }
        this.service.schedule(
                () -> {
                    removeEntry(this.cache.get(__key));
                    this.cache.remove(__key);
                    numberOfCacheEvictions.incrementAndGet();
                    System.out.println("Removing " + __key + " due to time expiration");
        }, 5, TimeUnit.SECONDS);
        long end = System.currentTimeMillis();
        this.cumulativeMovingAverage(end - start);
    }

    private synchronized void cumulativeMovingAverage(double x) {
        final long n = this.numberOfPuts.get();
        final double average = this.averageTimeSpentLoading.get();

        double newAverage = (x + n * average) / (n + 1);

        this.averageTimeSpentLoading.set(newAverage);
    }

    private synchronized void removeEntry(CacheNode cn) {
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
    }

    private synchronized void addEntry(@NotNull CacheNode cn) {
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
            this.frequencies.get(i).getFreqList().add(cn);
        } else {
            // else create the new list and put it in the correct frequency list.

            // in case i is bigger than the size make the adjustment
            ConcurrentLinkedQueue<CacheNode> ll = new ConcurrentLinkedQueue<>();
            ll.add(cn);
            FrequencyList newFrList = new FrequencyList(cn.getFrequency(), ll);
            if(i >= this.frequencies.size())
                this.frequencies.add(newFrList);
            else
                this.frequencies.add(i, newFrList);
        }

    }

    private synchronized void evict() {
        // Retrieved lest frequently used elements at the beginning
        FrequencyList lowestFrequencyList = this.frequencies.getFirst();

        // the first frequency list will always have a value, otherwise it wouldn't be there.
        CacheNode cn = lowestFrequencyList.getFreqList().poll();

        if(lowestFrequencyList.getFreqList().size() == 0) {
            this.frequencies.remove(lowestFrequencyList);
        }

        this.cache.remove(cn.getKey());
        numberOfCacheEvictions.incrementAndGet();
        System.out.println("Removing " + cn.getValue() + " because cache size was exceeded" );
    }

    private void updateFrequency(CacheNode cn) {
        removeEntry(cn);
        // add to new position
        addEntry(cn);
    }

    public void printCacheStats() {
        StringBuilder sb = new StringBuilder();

        sb.append("Average loading time when an element is inserted in the cache: ");
        sb.append(this.averageTimeSpentLoading.get());
        sb.append(" \n");

        sb.append("Total number of evictions: ");
        sb.append(this.numberOfCacheEvictions.get());
        sb.append("ms. \n");

        System.out.println(sb);
    }

    public synchronized int size() {
        return this.cache.size();
    }
}
