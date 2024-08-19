package com.jfnavin.codeowners.resolver;

/**
 * Factory interface for factories that can create {@link OwnerResolver} instances.
 */
public interface OwnerResolverFactory<T extends OwnerResolver> {

    /**
     * Create or return an {@link OwnerResolver instance}
     */
    T create();

}
