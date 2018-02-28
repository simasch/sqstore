package store.customer.boundary;

import org.infinispan.Cache;
import org.infinispan.CacheCollection;
import store.customer.control.cache.CustomerCache;
import store.customer.entity.Customer;

import javax.cache.annotation.CacheKey;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

/**
 * This class serves as an API to the cache manager.
 * <p>
 * It provides methods to inspect the content of the cache.
 * It is a pure REST resource and not an EJB because we don't need transaction handling.
 */
@Path("cache")
public class CustomerCacheResource {

    /**
     * Injects the Cache.
     * Which cache will be injected depends on the qualifier.
     * In our case the qualifier is {@link CustomerCache}
     */
    @Inject
    @CustomerCache
    private Cache<CacheKey, Customer> cache;

    /**
     * The values in the cache represent the already loaded customers
     *
     * @return collection of {@link Customer}
     */
    @GET
    @Path("entries")
    @Produces(MediaType.APPLICATION_JSON)
    public CacheCollection<Customer> getCacheEntries() {
        return cache.values();
    }

    /**
     * Return the keys to see how infinispan creates a default key.
     *
     * @return Set of cache keys
     */
    @GET
    @Path("keys")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getCacheKeys() {
        Set<String> set = new HashSet<>();
        for (Object defaultCacheKey : cache.keySet().toArray()) {
            set.add(defaultCacheKey.toString());
        }
        return set;
    }

    /**
     * Simply get the name of the cache
     *
     * @return name of the cache
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getName() {
        return cache.getName();
    }
}
