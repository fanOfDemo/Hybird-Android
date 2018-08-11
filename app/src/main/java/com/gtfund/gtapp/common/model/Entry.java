package com.gtfund.gtapp.common.model;

import java.io.Serializable;

/**
 * 自定义Entry
 *
 * Created by enzexue on 2018/8/9.
 */
public class Entry<K, V> implements Serializable{
    private static final long serialVersionUID = 1L;

    public K key;
    public V value;

    public Entry() {
        //default
    }
    public Entry(K key) {
        this(key, null);
    }
    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }


    public K getKey() {
        return key;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public V getValue() {
        return value;
    }
    public void setValue(V value) {
        this.value = value;
    }
}
