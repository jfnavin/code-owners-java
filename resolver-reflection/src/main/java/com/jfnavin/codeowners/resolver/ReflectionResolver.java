package com.jfnavin.codeowners.resolver;

import com.jfnavin.codeowners.model.Owner;

import java.util.Optional;

/**
 * A {@link OwnerResolver} that uses reflection to scan for the "closest" {@link com.jfnavin.codeowners.annotation.Owner} annotation
 * to denote the owner for a given class.
 * <p>
 * Scanning starts at the given class, then works upwards through packages looking for annotations on
 * {@code package-info.java}. If no annotations can be found, an {@code empty()} result is returned.
 * <p>
 * This implementation caches results in an in-memory cache. Cache size defaults to {@value DEFAULT_CACHE_SIZE},
 * but can be configured by the constructor param or by setting the sysprop {@value DEFAULT_CACHE_SIZE_OVERRIDE_KEY}.
 * Caching can be disabled by setting cache size to 0.
 * <p>
 */
public class ReflectionResolver implements OwnerResolver {

    private static final int DEFAULT_CACHE_SIZE = 1024;
    private static final String DEFAULT_CACHE_SIZE_OVERRIDE_KEY = "codeowners.cache.size";

    private final LRUCache cache;

    public ReflectionResolver() {
        this(getDefaultCacheSize());
    }

    public ReflectionResolver(int cacheSize) {
        if (cacheSize < 0) {
            throw new IllegalArgumentException("Cache size cannot be negative");
        } else if (cacheSize == 0) {
            cache = null;
        } else {
            cache = new LRUCache(cacheSize);
        }
    }

    public Optional<Owner> resolverOwner(final Class<?> clazz) {
        if (cache != null && cache.has(clazz)) {
            return Optional.ofNullable(cache.get(clazz));
        }

        // Check for an annotation on the class itself
        var annotation = clazz.getAnnotation(com.jfnavin.codeowners.annotation.Owner.class);
        if (annotation != null) {
            final var value = new Owner(annotation.value());
            if (cache != null) {
                cache.put(clazz, value);
            }
            return Optional.of(value);
        }

        // If none, scan upwards looking for annotations on package-info
        var packageName = clazz.getPackageName();
        while (!packageName.isBlank()) {
            try {
                final var c = Class.forName(packageName + ".package-info");
                annotation = c.getAnnotation(com.jfnavin.codeowners.annotation.Owner.class);
                if (annotation != null) {
                    final var value = new Owner(annotation.value());
                    if (cache != null) {
                        cache.put(clazz, value);
                    }
                    return Optional.of(value);
                }
            } catch (ClassNotFoundException ignored) {
                // This is expected when packages don't contain a package-info.java
            }

            final var parentPackage = parentPackage(packageName);
            if (parentPackage == null || parentPackage.isEmpty()) {
                if (cache != null) {
                    cache.put(clazz, null);
                }
                return Optional.empty();
            }

            packageName = parentPackage;
        }

        if (cache != null) {
            cache.put(clazz, null);
        }
        return Optional.empty();
    }

    private String parentPackage(final String packageName) {
        final int separatorIndex = packageName.lastIndexOf('.');
        if (separatorIndex < 0) {
            return null;
        }
        return packageName.substring(0, separatorIndex);
    }

    private static int getDefaultCacheSize() {
        return Integer.getInteger(DEFAULT_CACHE_SIZE_OVERRIDE_KEY, DEFAULT_CACHE_SIZE);
    }

}
