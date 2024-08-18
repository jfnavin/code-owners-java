package com.jfnavin.codeowners.resolver;

import java.util.Optional;

public interface OwnerResolver {

    /**
     * Resolve the owner of the given class.
     * <p>
     * Implementations may use different strategies to resolve the owner.
     */
    Optional<String> resolverOwner(final Class<?> clazz);
}
