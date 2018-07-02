package store.common.test.context;

import javax.ejb.*;
import javax.transaction.UserTransaction;
import javax.xml.rpc.handler.MessageContext;
import java.security.Principal;
import java.util.Map;
import java.util.Properties;

public class DummySessionContext implements SessionContext {

    private Principal principal;

    public DummySessionContext() {
        principal = new Principal() {
            @Override
            public String getName() {
                return "test";
            }
        };
    }

    @Override
    public void setRollbackOnly() throws IllegalStateException {
    }

    @Override
    public Object lookup(String arg0) throws IllegalArgumentException {
        return null;
    }

    @Override
    public boolean isCallerInRole(String arg0) {
        return true;
    }

    @Override
    public boolean isCallerInRole(@SuppressWarnings("deprecation") java.security.Identity arg0) {
        return false;
    }

    @Override
    public UserTransaction getUserTransaction() throws IllegalStateException {
        return null;
    }

    @Override
    public TimerService getTimerService() throws IllegalStateException {
        return null;
    }

    @Override
    public boolean getRollbackOnly() throws IllegalStateException {
        return false;
    }

    @Override
    public Properties getEnvironment() {
        return null;
    }

    @Override
    public EJBLocalHome getEJBLocalHome() {
        return null;
    }

    @Override
    public EJBHome getEJBHome() {
        return null;
    }

    @Override
    public Map<String, Object> getContextData() {
        return null;
    }

    @Override
    public Principal getCallerPrincipal() {
        return principal;
    }

    @SuppressWarnings("deprecation")
    @Override
    public java.security.Identity getCallerIdentity() {
        return null;
    }

    @Override
    public boolean wasCancelCalled() throws IllegalStateException {
        return false;
    }

    @Override
    public MessageContext getMessageContext() throws IllegalStateException {
        return null;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class getInvokedBusinessInterface() throws IllegalStateException {
        return null;
    }

    @Override
    public EJBObject getEJBObject() throws IllegalStateException {
        return null;
    }

    @Override
    public EJBLocalObject getEJBLocalObject() throws IllegalStateException {
        return null;
    }

    @Override
    public <T> T getBusinessObject(Class<T> arg0) throws IllegalStateException {
        return null;
    }

}
