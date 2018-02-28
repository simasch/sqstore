package store.customer.control.cache;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;

import javax.enterprise.inject.Produces;

/**
 * Configures the CustomerCache.
 */
public class CustomerCacheProducer {

    /**
     * This is producer method (e.g. Factory Method).
     * <p>
     * It creates a cache configuration and produces a cache that can be injected using the
     * {@link CustomerCache} qualifier.
     *
     * @return {@link Configuration}
     */
    @CustomerCache
    @ConfigureCache("customer-cache")
    @Produces
    public Configuration customerCache() {
        return new ConfigurationBuilder()
                .eviction().strategy(EvictionStrategy.LRU).size(4)
                .build();
    }
}