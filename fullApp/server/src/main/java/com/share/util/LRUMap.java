package com.share.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap<K,V> extends LinkedHashMap<K,V>{
    private final int MAX_NUM ;
   
    public LRUMap( int capacity){
        super(capacity);
        MAX_NUM = capacity;
    }
   
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest){
        return size() > MAX_NUM ; 
    }
}