package store.common.interceptor;

import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * An interceptor is used to add a kind of aspect oriented programming to Java EE:
 * https://en.wikipedia.org/wiki/Aspect-oriented_programming
 * <p>
 * To see the log messages set the logger to DEBUG level.
 * To turn on the timing set the logger to TRACE level.
 */
public class MethodTraceInterceptor {

    /**
     * The method annotated with {@link AroundInvoke} will be called to intercept a method.
     * <p>
     * To inspect the method call and to proceed with it's execution the {@link InvocationContext} is used.
     *
     * @param context
     * @return the result object
     * @throws Exception
     */
    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception {
        Logger log = Logger.getLogger(context.getTarget().getClass());

        try {
            long start = System.currentTimeMillis();

            if (log.isDebugEnabled()) {
                String message = context.getMethod().getName() + "(" + extractParameters(context) + ")";
                log.info(message);
            }

            Object object = context.proceed();
            if (log.isTraceEnabled()) {
                String timingMessage = context.getMethod().getName() + " took " + (System.currentTimeMillis() - start) + " ms";
                log.trace(timingMessage);
            }

            return object;

        } catch (Exception e) {
            // Always call the Log4J error method like this!
            // If you only call log.error(e) the stacktrace will no be printed!
            log.error(e.getMessage(), e);

            throw e;
        }
    }

    private String extractParameters(InvocationContext context) {
        StringBuilder params = new StringBuilder();
        if (context != null && context.getParameters() != null) {
            boolean first = true;
            for (Object param : context.getParameters()) {
                if (!first) {
                    params.append(", ");
                }
                params.append(param);
                first = false;
            }
        }
        return params.toString();
    }

}
