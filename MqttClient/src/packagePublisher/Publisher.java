package packagePublisher;
import org.eclipse.paho.client.mqttv3.*;
import java.sql.Timestamp;

public class Publisher extends Thread{
	
MqttClient client;
Callback callback;
MqttMessage message;
MqttTopic topic;
IMqttDeliveryToken token;
MqttConnectOptions options;
Contenitore c ;
String topicString;
int QoS;




public Publisher(String broker,String clientId,Contenitore c2,String topicString,int qos) {
	
	c=c2;
	this.topicString=topicString;
	this.QoS=qos;
	try {
	client= new MqttClient(broker,clientId);
	
	callback=new Callback(clientId,broker);
	client.setCallback(callback);
	options=new MqttConnectOptions();
	options.setMqttVersion(options.MQTT_VERSION_3_1_1);
	System.out.println("Connecting to "+ client.getServerURI() + " with client ID "+client.getClientId());
	client.connect(options);
	System.out.println("Connected");
	
	}catch(MqttException e) {
		e.printStackTrace();	
	}
}

public void run() {
	while(true) {
	Publish(topicString,QoS);
	try {
        Thread.sleep(5000);
     } catch(InterruptedException e) {
         System.out.println(e.getMessage());
     
    }
	}
	
}

public synchronized void Publish(String topicName,int levelOfDelivery) {
	String time = new Timestamp(System.currentTimeMillis()).toString();
	try {
	topic=client.getTopic(topicName);
	message=new MqttMessage();
	message.setPayload(c.get().getBytes());
	message.setQos(levelOfDelivery);
	System.out.println("Publishing msg \""+message.toString()+"\" at: "+time+ " to topic \""+topicName+"\" qos "+levelOfDelivery);
	token=topic.publish(message);
	token.waitForCompletion();
	//client.disconnect();
	//System.out.println("Disconnected");
	
	}catch(MqttException e) {
		e.printStackTrace();
	}
	
}


}
