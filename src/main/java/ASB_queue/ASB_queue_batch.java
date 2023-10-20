package ASB_queue;

import java.util.*;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

public class ASB_queue_batch {

	static String connectionString= "Endpoint=sb://servicebus-queue-1.servicebus.windows.net/;"+"SharedAccessKeyName=ConnectionString;"+"SharedAccessKey=Z5X4Hxz7uJxv25l/Ctq+qhSraQtRrt6+i+ASbDINFyE=;EntityPath=first-queue";
	static String queueName= "first-queue";

	public static void main(String args[]) {
		sendBatchMessages();
	}
	
	// create a list of messages and return it to the caller
	static List<ServiceBusMessage> createMessages(){
		
		ServiceBusMessage[] messages = {
				new ServiceBusMessage("My name is Nikhil"),
				new ServiceBusMessage("I am learning now"),
				new ServiceBusMessage("It is a great experience")
		};
		
		return Arrays.asList(messages);
	}
	
	// create a Service Bus Sender client for the queue
	static void sendBatchMessages() {
		
		ServiceBusSenderClient serviceBusSenderClient = new ServiceBusClientBuilder()
                                                        .connectionString(connectionString)
                                                        .sender()
                                                        .queueName(queueName)
                                                        .buildClient();
		
		// Creates an ServiceBusMessageBatch.
		ServiceBusMessageBatch messageBatch = serviceBusSenderClient.createMessageBatch();
		
		// create a list of messages
		List<ServiceBusMessage> listOfMessages = createMessages();
		
		for(ServiceBusMessage message : listOfMessages ) {
			messageBatch.tryAddMessage(message);
		}
		
		serviceBusSenderClient.sendMessages(messageBatch);
		System.out.println("Messages sent !!!");
		
		//close the client
		serviceBusSenderClient.close();
	}
}
