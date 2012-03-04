/*
 * To change this template, choose Tools | Templates

 * and open the template in the editor.
 */

package edu.upenn.cis.cis555.webserver;


import java.net.Socket;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import edu.upenn.cis.cis555.webserver.HandleReq;
/**
 *
 * @author VamseeKYarlagadda
 */

public class SocketVecQueue implements Runnable {

	
	/*
	 * Variables that are needed for the execution of the program.
	 */
	
     public static int MAX=1500;
     public static int CURRENT=0;
     static int count=0;
     public static LinkedList<Socket> socQueue=new LinkedList<Socket>();
     Socket client;  
     String met;
     private static Logger logger = Logger.getLogger(SocketVecQueue.class);
    
     /*
 	 * Function to insert the socket(client) into the Qeueue so that oen of the threads can access it and process it.
 	 */

    public boolean enqueue(Socket req)
    {
    	
    	synchronized(socQueue)
    	{
         
           
           socQueue.addLast(req);
           CURRENT++;
           socQueue.notify();
           return true;
           
           
       }
        
    }
   
    /*
	 * Starting function for all the client threads.
	 * The fUNCTION ALLOWS THREADS to wait and get synchronized over the queue and also to process them in HandleReq
	 */
    
    @Override
    public void run() {
   
    	while(true)
       {
         Socket input = null;
  
         		long start= System.currentTimeMillis();
         
         synchronized(socQueue){ 
        	 if(socQueue.isEmpty())
           {
            	      	 
        		 	try 
                	{
                    	socQueue.wait();
                    
                     } catch (InterruptedException ex) 
                     {
                    	 logger.error(ex.toString());
                    	 break;
                     }
              
           }
        
        		 CURRENT--;
        	 input=socQueue.removeFirst();
          }// SYNCHRONIZATION BLOCK

           
           try{
        new HandleReq().parseReq(input);
           }
           catch(Exception e)
           {
        	  // logger.error(e.toString());
      //  	   e.printStackTrace();
           }
           finally
           {
   
           }
           
               
       }
    }
    
    
    
    
}
