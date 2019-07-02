package packagePublisher;
import java.io.*;

class Input {
    public static String leggiStringa() {
        String s =new String();
        try{
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(r);
        s =in.readLine();
        }
        catch (IOException e){
            System.out.println("hai letto una stringa vuota");
        }
        return s;
}
    public static int leggiIntero() {
        String s = leggiStringa();
        return Integer.parseInt(s);
    }

    public static float leggiFloat() {
        String s = leggiStringa();
        return Float.parseFloat(s);
    }
 }         

public class Main {

    
	public static void main(String[] args) throws InterruptedException {
		Contenitore c=null;
		try{
			System.out.println("Inserisci la dimensione del buffer");
	         int x=Input.leggiIntero();
	         c=new Contenitore(x);
	        }catch(Exception e){
	         c=new Contenitore();
	        }
		
		Parameters param=new Parameters(c);		
		Thread t1=new Thread(param);
	    t1.start();
	    
        Publisher publisher=new Publisher(param.localAddress,param.clientID,c,param.pubTopicString,param.QoS);
        Thread t2=new Thread(publisher);
        
        t2.start();
	    //publisher.ConnectAndPublish(param.pubTopicString,c.get(), param.QoS);
	 
	
       // System.exit(0);
	}

}
 