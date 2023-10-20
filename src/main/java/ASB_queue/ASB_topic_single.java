package ASB_queue;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

public class ASB_topic_single {
	
	static String connectionString = "Endpoint=sb://asb-topic-1.servicebus.windows.net/;SharedAccessKeyName=ConnectionString;SharedAccessKey=X+zKsGUMQsJh8+SE/6HruwqePRL8d2q/x+ASbEjV60E=;EntityPath=topic-1";
	static String topicName = "topic-1";
	
	public static void main(String[] args) {
		sendMessageToTopic();
	}

	static void sendMessageToTopic() {
		
		ServiceBusSenderClient serviceBusSenderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .topicName(topicName)
                .buildClient();

serviceBusSenderClient.sendMessage(new ServiceBusMessage("Hello ! Welcome to new topic."));
System.out.println("Message has been sent to topic: " + topicName);
	}
}
