package com.jfnavin.codeowners.resolver;

/**
 * Factory for constructing shared instances of {@link ReflectionResolver}.
 * <p>
 * Can be retrieved via {@link java.util.ServiceLoader<OwnerResolverFactory>}.
 */
public class ReflectionResolverFactory implements OwnerResolverFactory<ReflectionResolver> {

    private ReflectionResolver instance;

    @Override
    public ReflectionResolver create() {
        if (instance == null) {
            instance = new ReflectionResolver();
        }

        return instance;
    }
}
