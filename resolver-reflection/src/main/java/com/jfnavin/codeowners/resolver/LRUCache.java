package com.jfnavin.codeowners.resolver;

import com.jfnavin.codeowners.model.Owner;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple LRU cache used to hold results of the owner resolution
 */
class LRUCache {

    final Map<String, Owner> cache;

    LRUCache(final int size) {
        this.cache = new LinkedHashMap<>(size + 1, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Owner> eldest) {
                return size() > size;
            }
        };
    }

    synchronized void put(final Class<?> clazz, final Owner value) {
        put(clazz.getName(), value);
    }

    synchronized void put(final String key, final Owner value) {
        this.cache.put(key, value);
    }

    synchronized boolean has(final Class<?> clazz) {
        return has(clazz.getName());
    }

    synchronized boolean has(final String key) {
        return this.cache.containsKey(key);
    }

    synchronized Owner get(final Class<?> clazz) {
        return get(clazz.getName());
    }

    synchronized Owner get(final String key) {
        return this.cache.get(key);
    }
}
