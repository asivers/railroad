package railroad.messaging;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import railroad.service.TrainService;

@Component
public class MessageReceiver {

	private static final String ORDER_RESPONSE_QUEUE = "order-response-queue";

	@Autowired
	TrainService trainService;

	@JmsListener(destination = ORDER_RESPONSE_QUEUE)
	public void receiveMessage(final Message<String> message) throws JMSException {
		String response = message.getPayload();
		String[] dataReceived = response.split("/");
		trainService.trainsByStationTB(dataReceived[0], Integer.parseInt(dataReceived[1]));
	}
}
