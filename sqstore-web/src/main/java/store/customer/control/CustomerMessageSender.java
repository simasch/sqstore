package store.customer.control;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class CustomerMessageSender {

    @Inject
    private JMSContext context;

    @Resource(mappedName = "java:/jms/queue/CustomerQueue")
    private Queue queue;

    public void sendMessage(String message) {
        context.createProducer().send(queue, message);
    }
}