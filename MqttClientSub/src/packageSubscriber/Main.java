package packageSubscriber;

import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class Main {

	
	
	public static void main(String[] args) throws MqttSecurityException {
		Parameters param;
		Subscriber subscriber;
		
		param=new Parameters();
		
		subscriber=new Subscriber(param.localAddress, param.clientID,param.cleanSession);
	    subscriber.ConnectAndSubscribe(param.subTopicString, param.QoS);
	}

}
