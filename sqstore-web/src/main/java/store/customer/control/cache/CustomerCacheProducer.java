package store.customer.control.cache;

import org.infinispan.cdi.ConfigureCache;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;

import javax.enterprise.inject.Produces;

/**
 * Configures the CustomerCache.
 * <p>
 * Caching is a common performance optimization strategy.
 * But as every performance optimization strategy caching has advantages and disadvantage.
 * Some of the disadvantages are the lost of statelessness. That will break distribution and scaling that are
 * very important in the age of cloud computing.
 * <p>
 * As Donald Knutt said in 1974:
 * "Premature optimization is the root of all evil..."
 * <p>
 * So think twice before you start using caching. Usually the database is fast enough if you use it in a
 * optimal way like reducing SQL statements by using DTOs instead of entities and imprving data access
 * with indexing.
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