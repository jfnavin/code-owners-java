package com.jfnavin.codeowners.resolver;

import com.jfnavin.codeowners.model.Owner;

import java.util.Optional;

public interface OwnerResolver {

    /**
     * Resolve the owner of the given class.
     * <p>
     * Implementations may use different strategies to resolve the owner.
     */
    Optional<Owner> resolverOwner(final Class<?> clazz);
}
