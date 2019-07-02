package packageSubscriber;

import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Subscriber {
	
 Callback callback;
 MqttConnectOptions options;
 MqttClient client;
 IMqttToken token; 
 
 public Subscriber(String broker, String clientId,boolean cleanSession)throws MqttSecurityException{ 
	 try {
		 client= new MqttClient(broker,clientId);
		 
	 callback=new Callback(clientId,broker);
	 client.setCallback(callback);
			 options = new MqttConnectOptions();
			options.setMqttVersion(options.MQTT_VERSION_3_1_1);
			 options.setCleanSession(cleanSession);
			 options.setKeepAliveInterval(2000);
			System.out.println("Connecting to "+ client.getServerURI() + " with client ID "+client.getClientId());  
			token=client.connectWithResult(options);
			token.waitForCompletion(5000);
			 System.out.println("Connected");
	 }catch(MqttException e) {
		 System.out.println(e.toString());
		
	 }
 }
 
 void ConnectAndSubscribe(String topicName, int levelOfDelivery){
	 String time = new Timestamp(System.currentTimeMillis()).toString();
	 try {
	 client.subscribe(topicName, levelOfDelivery);
	 System.out.println("Subscribing at: "+time+ " to topic \""+topicName+"\" qos "+levelOfDelivery);
	 }catch(MqttException e) {
	  e.printStackTrace();
	 }
	 }
	 
 
}
