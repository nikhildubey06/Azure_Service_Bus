package ASB_queue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;

public class ASB_receive_topic {
	
	static String topicName = "topic-1";
	static String subscriptionName = "subscription-01";
	static String connectionString = "Endpoint=sb://asb-topic-1.servicebus.windows.net/;SharedAccessKeyName=ConnectionString;SharedAccessKey=X+zKsGUMQsJh8+SE/6HruwqePRL8d2q/x+ASbEjV60E=;EntityPath=topic-1";
	
	public static void main(String[] args) throws InterruptedException {
		receiveMessage();
	}
	
	static void receiveMessage() throws InterruptedException{
		CountDownLatch countDownLatch = new CountDownLatch(1);
		ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
				.connectionString(connectionString)
                .processor()
                .topicName(topicName)
                .subscriptionName(subscriptionName)
                .processMessage(ASB_receive_topic::processMessage)
                .processError(context -> processError(context, countDownLatch))
                .buildProcessorClient();
		
		System.out.println("Starting the processor");
		processorClient.start();
		
		TimeUnit.SECONDS.sleep(10);
	    System.out.println("Stopping and closing the processor");
	    processorClient.close();
		
	}
	
	private static void processMessage(ServiceBusReceivedMessageContext context) {
	    ServiceBusReceivedMessage message = context.getMessage();
	    System.out.printf("Processing message. Session: %s, Sequence #: %s. Contents: %s%n", message.getMessageId(),
	        message.getSequenceNumber(), message.getBody());
	}
	
	private static void processError(ServiceBusErrorContext context, CountDownLatch countDownLatch) {
		
	}
}
