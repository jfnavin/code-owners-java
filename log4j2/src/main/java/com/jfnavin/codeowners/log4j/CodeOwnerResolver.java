package com.jfnavin.codeowners.log4j;

import com.jfnavin.codeowners.resolver.OwnerResolver;
import com.jfnavin.codeowners.resolver.OwnerResolverFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.layout.template.json.resolver.EventResolver;
import org.apache.logging.log4j.layout.template.json.util.JsonWriter;
import org.apache.logging.log4j.status.StatusLogger;

import java.util.Optional;
import java.util.ServiceLoader;

/**
 * A template resolver that resolves the code owner based on logger name.
 * <p>
 * If the logger name corresponds to a {@code Class}, the resolver will use a {@link ServiceLoader}
 * to find an implementation of {@link OwnerResolver} from the classpath to resolve the code owner.
 * <p>
 * Uses a {@link OwnerResolverFactory} retrieved via {@link ServiceLoader}. If no suitable implementation can
 * be found on the classpath will log {@code null} owners.
 *
 * <h2>Examples</h2>
 * <pre>
 * "owner": {
 *     "$resolver": "owner"
 *   }
 * </pre>
 *
 */
public class CodeOwnerResolver implements EventResolver {

    private static final Logger LOGGER = StatusLogger.getLogger();

    private static final OwnerResolver EMPTY_RESOLVER = (clazz) -> Optional.empty();

    static String getName() {
        return "owner";
    }

    private final OwnerResolver ownerResolver;

    public CodeOwnerResolver() {
        this.ownerResolver = loadOwnerResolver();
    }

    private OwnerResolver loadOwnerResolver() {
        return ServiceLoader.load(OwnerResolverFactory.class)
                .findFirst()
                .map(OwnerResolverFactory::create)
                .orElseGet(() -> {
                    LOGGER.warn("No OwnerResolverFactory found. Ensure you have added an appropriate implementation to the classpath.");
                    return EMPTY_RESOLVER;
                });
    }

    @Override
    public void resolve(final LogEvent value,
                        final JsonWriter jsonWriter) {
        final var loggerName = value.getLoggerName();
        if (loggerName == null || loggerName.isEmpty()) {
            jsonWriter.writeNull();
            return;
        }

        try {
            final var clazz = Class.forName(loggerName);
            ownerResolver.resolverOwner(clazz).ifPresentOrElse(
                    jsonWriter::writeString,
                    jsonWriter::writeNull
            );
        } catch (ClassNotFoundException e) {
            jsonWriter.writeNull();
        }
    }
}
