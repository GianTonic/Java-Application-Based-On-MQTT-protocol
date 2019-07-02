package packagePublisher;

import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.*;

public class Callback implements MqttCallback {

	String instance;
	String brokerUrl;
	
	public Callback(String clientId,String broker) {
		instance=clientId;
		brokerUrl= broker;
	}
	
	public void connectionLost(Throwable cause) {
		// Called when the connection to the server has been lost.
		// An application may choose to implement reconnection
		// logic at this point. This sample simply exits.
		System.out.println("Connection beetwen "+instance+" and " + brokerUrl + " lost!" + cause);
		System.exit(1);
    }
	


	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		String time = new Timestamp(System.currentTimeMillis()).toString();
		System.out.println("Time:" +time +
                           "  Topic:" + topic +
                           "  Message:" + new String(message.getPayload()) +
"  QoS:\t" + message.getQos());
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		try {
			System.out.println("Delivery token \"" + token.hashCode()
			+ "\" received by instance \"" + instance + "\"");
			} catch (Exception e) {
			e.printStackTrace();
			}
	}

	
	
}
