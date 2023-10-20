package ASB_queue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusErrorContext;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;

public class ASB_receive_queue {
	
	static String connectionString= "Endpoint=sb://servicebus-queue-1.servicebus.windows.net/;"+"SharedAccessKeyName=ConnectionString;"+"SharedAccessKey=Z5X4Hxz7uJxv25l/Ctq+qhSraQtRrt6+i+ASbDINFyE=;EntityPath=first-queue";
	static String queueName= "first-queue";
	
	public static void main(String[] args) throws InterruptedException {
		receiveMessage();
	}
	
	static void receiveMessage() throws InterruptedException{
		CountDownLatch countDownLatch = new CountDownLatch(1);
		ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .processor()
                .queueName(queueName)
                .processMessage(ASB_receive_queue::processMessage)
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
