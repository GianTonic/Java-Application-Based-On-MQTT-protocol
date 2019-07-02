package packageSubscriber;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;

import org.eclipse.paho.client.mqttv3.*;

public class Callback implements MqttCallback {

	String instance;
	String brokerUrl;
	FileOutputStream file;
	
	public Callback(String clientId,String broker) {
		instance=clientId;
		brokerUrl= broker;
		try {
			file = new FileOutputStream("save.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		String text = new String(message.getPayload());
		PrintStream write=new PrintStream(file);
		
		write.println(text+";"+time+";");
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
