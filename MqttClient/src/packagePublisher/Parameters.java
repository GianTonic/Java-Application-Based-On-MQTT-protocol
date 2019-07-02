package packagePublisher;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/*
class Input {
    public static String leggiStringa() throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(r);
        String s =in.readLine();
    return s;
}
}
*/

public class Parameters extends Thread {
	
String localAddress ="tcp://localhost:1883";
String remoteAddress="tcp://test.mosquitto.org:1883";
String clientID="ID01";
String pubTopicString="root/temperature";
String subTopicString="root/temperature";
Contenitore c=null;
float temperature;
Boolean cleanSession=true;
Boolean flag=true;
int QoS=0;
Random random= new Random();

public Parameters(Contenitore con) {
	c=con;
	
}

@Override
 public void run() {
	

	   while(flag) {
          
	          /* new Runnable() {
           @Override
           public void run() {
           }                 
       };*/
		   
           GenerateDate();            
           try {
               Thread.sleep(5000);
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
            
           }
       }
}


public synchronized void GenerateDate(){
	temperature=0+(45-0)*random.nextFloat();
	
	System.out.println("generata temp:"+temperature);
	String stringa=String.valueOf(temperature);
	c.put(stringa);
	
}

}
