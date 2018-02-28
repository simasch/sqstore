package store.customer.control.cache;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 * CDI {@see Qualifier} that can be used when producing and injecting the cache.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
@Documented
public @interface CustomerCache {
}
