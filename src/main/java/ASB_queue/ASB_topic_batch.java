package ASB_queue;

import java.util.Arrays;
import java.util.List;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

public class ASB_topic_batch {
	
	static String connectionString = "Endpoint=sb://asb-topic-1.servicebus.windows.net/;SharedAccessKeyName=ConnectionString;SharedAccessKey=X+zKsGUMQsJh8+SE/6HruwqePRL8d2q/x+ASbEjV60E=;EntityPath=topic-1";
	static String topicName = "topic-1";
	
	public static void main(String[] args) {
		receiveMessageToTopic();
	}
		
		static List<ServiceBusMessage> createMessages(){
			
			ServiceBusMessage[] messages = {
					new ServiceBusMessage("Message topic 1"),
					new ServiceBusMessage("Message topic 2"),
					new ServiceBusMessage("Message topic 3")
			};
			
			return Arrays.asList(messages);
		}
		
		static void receiveMessageToTopic() {
			
			ServiceBusSenderClient serviceBusSenderClient = new ServiceBusClientBuilder()
	                                                        .connectionString(connectionString)
	                                                        .sender()
	                                                        .queueName(topicName)
	                                                        .buildClient();
			
			ServiceBusMessageBatch messageBatch = serviceBusSenderClient.createMessageBatch();
			
			List<ServiceBusMessage> listOfMessages = createMessages();
			
			for(ServiceBusMessage message : listOfMessages ) {
				messageBatch.tryAddMessage(message);
			}
			serviceBusSenderClient.sendMessages(messageBatch);
			System.out.println("Messages sent !!!");
			serviceBusSenderClient.close();
	}
}
