package packagePublisher;

import java.util.LinkedList;

class Contenitore{
	 LinkedList <String> Elementi;
	 int max;
	 
	 Contenitore(){
	 this(50);
	 }
	 
	 Contenitore(int x){
		 Elementi=new LinkedList<>();
		 max=x;
		 }
	 
	 boolean bufferFull(){
	  return Elementi.size()==max;
	 }
	 boolean vuoto(){
	  return Elementi.size()==0;
	 }
	 
	 synchronized void put(String s){

	  while(bufferFull()){
	    try{
	     wait(); 
	    }catch(InterruptedException e){
	    	e.printStackTrace();
	    }
	  }
	  Elementi.addLast(s);
	  notifyAll(); 
	 }
	 
	 synchronized String get(){
	        String str;
	        while (vuoto()) {
	            try {
	                wait();
	        }catch (InterruptedException e){}
	        }
	        str = Elementi.removeFirst(); //elimina primo elem.
	        notifyAll();
	        return str;
	 }
	 
	 }
