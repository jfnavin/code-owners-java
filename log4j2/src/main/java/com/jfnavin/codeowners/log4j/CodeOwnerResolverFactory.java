package com.jfnavin.codeowners.log4j;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.layout.template.json.resolver.EventResolverContext;
import org.apache.logging.log4j.layout.template.json.resolver.EventResolverFactory;
import org.apache.logging.log4j.layout.template.json.resolver.TemplateResolver;
import org.apache.logging.log4j.layout.template.json.resolver.TemplateResolverConfig;
import org.apache.logging.log4j.layout.template.json.resolver.TemplateResolverFactory;

@Plugin(name = "CodeOwnerResolverFactory", category = TemplateResolverFactory.CATEGORY)
public final class CodeOwnerResolverFactory implements EventResolverFactory {

    private static final CodeOwnerResolverFactory INSTANCE = new CodeOwnerResolverFactory();

    @PluginFactory
    public static CodeOwnerResolverFactory getInstance() {
        return INSTANCE;
    }

    private CodeOwnerResolverFactory() {}

    @Override
    public String getName() {
        return CodeOwnerResolver.getName();
    }

    @Override
    public TemplateResolver<LogEvent> create(final EventResolverContext context,
                                             final TemplateResolverConfig config) {
        return new CodeOwnerResolver();
    }
}
