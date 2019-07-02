package packageSubscriber;

import org.eclipse.paho.client.mqttv3.MqttClient;

public class Parameters {
	
	String localAddress ="tcp://localhost:1883";
	String remoteAddress="tcp://test.mosquitto.org:1883";
	String clientID;
	String pubTopicString="root/temperature";
	String subTopicString="root/temperature";
	String publication="temperature: 17.5";
	Boolean cleanSession=true;
	int QoS=0;

	public Parameters() {
		clientID=MqttClient.generateClientId();
	}

	}
