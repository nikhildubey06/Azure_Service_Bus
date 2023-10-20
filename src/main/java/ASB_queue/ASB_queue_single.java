package ASB_queue;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

public class ASB_queue_single {

	static String connectionString = "Endpoint=sb://servicebus-queue-1.servicebus.windows.net/;"+ "SharedAccessKeyName=ConnectionString;"+ "SharedAccessKey=Z5X4Hxz7uJxv25l/Ctq+qhSraQtRrt6+i+ASbDINFyE=;EntityPath=first-queue";
	static String queueName = "first-queue";

	public static void main(String args[]) {
		sendMessageToQueue();
	}

	static void sendMessageToQueue() {

		// service bus sender client to send message
		ServiceBusSenderClient serviceBusSenderClient = new ServiceBusClientBuilder()
				                                        .connectionString(connectionString)
				                                        .sender()
				                                        .queueName(queueName)
				                                        .buildClient();

		// send one message to the queue
		serviceBusSenderClient.sendMessage(new ServiceBusMessage("It was nice talking to you."));
		
		System.out.println("Message has been sent : It was nice talking to you.");
	}
}