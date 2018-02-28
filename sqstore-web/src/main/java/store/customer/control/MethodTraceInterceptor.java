package store.customer.control;

import org.apache.log4j.Logger;
import org.jboss.logging.MDC;

import javax.annotation.Resource;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.OptimisticLockException;

public class MethodTraceInterceptor {

    @AroundInvoke
    public Object logCall(InvocationContext context) throws Exception {
        long start = System.currentTimeMillis();

        Logger log = Logger.getLogger(context.getTarget().getClass());

        if (log.isInfoEnabled()) {
            String message = context.getMethod().getName() + "(" + extractParameters(context) + ")";
            log.info(message);
        }

        Object object = context.proceed();
        if (log.isTraceEnabled()) {
            String timingMessage = context.getMethod().getName() + " took " + (System.currentTimeMillis() - start) + " ms";
            log.trace(timingMessage);
        }

        return object;
    }

    private String extractParameters(InvocationContext context) {
        StringBuilder params = new StringBuilder();
        if (context != null && context.getParameters() != null) {
            boolean first = true;
            for (Object param : context.getParameters()) {
                if (!first) {
                    params.append(", ");
                }
                params.append("" + param);
                first = false;
            }
        }
        return params.toString();
    }

}
