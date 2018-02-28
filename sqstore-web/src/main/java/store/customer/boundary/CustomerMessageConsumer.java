package store.customer.boundary;

import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * {@link MessageDriven} EJB are used to consume messages from a {@link javax.jms.Queue} or a {@link javax.jms.Topic}
 * <p>
 * Queues and Topics can be configured in the standalone-full.xml in the subsystem urn:jboss:domain:messaging-activemq:2.0
 * <p>
 * To start the server with the full profile add the following system property (Vm option) to your IntelliJ run configuration
 * -Djboss.server.default.config=standalone-full.xml
 * <p>
 * They must implement {@link MessageListener}
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "CustomerQueue")})
public class CustomerMessageConsumer implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(CustomerMessageConsumer.class);

    /**
     * This method is called when a message arrives in the CustomerQueue.
     * <p>
     * The method shouldn't contain business logic. Instead you should delegate to a business service.
     *
     * @param message the message. A message can have several types {@see Message}
     */
    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;

            LOGGER.info(textMessage.getText());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
