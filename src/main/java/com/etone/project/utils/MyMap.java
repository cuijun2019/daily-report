package com.etone.project.utils;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author <a href="mailto:zhangjianlong@etonetech.com">zhangjianlong</a>
 */
public class MyMap<K,V>
    extends AbstractMap<K,V>
    implements Map<K,V>, Cloneable, Serializable{
    
    private HashMap map;
    
    public MyMap() {
        this.map = new HashMap();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        
        if(key instanceof String) {
            key = key.toString().toUpperCase();
        }
        return this.map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        
        if(key instanceof String) {
            key = key.toString().toUpperCase();
        }
        
        return (V) this.map.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        if(key instanceof String) {
            key = key.toString().toUpperCase();
        }
        
        return this.map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        if(key instanceof String) {
            key = key.toString().toUpperCase();
        }
        return (V) this.map.remove(key);
    }

    @Override
    public void putAll(Map m) {
        this.map.putAll(m);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public Set keySet() {
        return this.map.keySet();
    }

    @Override
    public Collection values() {
        return this.map.values();
    }

    @Override
    public Set entrySet() {
        return this.map.entrySet();
    }

}
