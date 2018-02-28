package store.customer.control;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;

import javax.enterprise.inject.Produces;

public class CacheProducer {

    @CustomerCache
    @ConfigureCache("customer-cache")
    @Produces
    public Configuration greetingCache() {
        return new ConfigurationBuilder()
                .eviction().strategy(EvictionStrategy.LRU).size(4)
                .build();
    }

    @Produces
    public Configuration defaultCacheConfiguration() {
        return new ConfigurationBuilder()
                .expiration().lifespan(60000L)
                .build();
    }

}