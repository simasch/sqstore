package store.customer.boundry;

import org.apache.log4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "CustomerQueue")})
public class CustomerMessageConsumer implements MessageListener {

    private final static Logger LOGGER = Logger.getLogger(CustomerMessageConsumer.class);

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
