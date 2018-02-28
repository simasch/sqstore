package store.customer.boundry;

import org.infinispan.Cache;
import org.infinispan.CacheCollection;
import store.customer.control.CustomerCache;
import store.customer.entity.Customer;

import javax.cache.annotation.CacheKey;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("cache")
@Stateless
public class CustomerCacheResource {

    @Inject
    @CustomerCache
    private Cache<CacheKey, Customer> cache;

    @GET
    @Path("entries")
    @Produces(MediaType.APPLICATION_JSON)
    public CacheCollection<Customer> getCacheEntries() {
        return cache.values();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getName() {
        return cache.getName();
    }
}
