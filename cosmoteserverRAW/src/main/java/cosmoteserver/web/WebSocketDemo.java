/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
//import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.PongMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author koutote
 */
@ApplicationScoped
@ServerEndpoint("/echo")

public  class WebSocketDemo  { 

    @Inject
    private DeviceSessionHandler sessionHandler;
    

	
	@OnOpen
    public void open(Session session) {
		sessionHandler.addwebsocketSession(session);
	}
	
	   public void addSession(Session session) {
	    	sessionHandler.addwebsocketSession(session);
	      /*  for (Device device : devices) {
	            JsonObject addMessage = createAddMessage(device);
	            sendToSession(session, addMessage);
	        }*/
	    }
	   @OnClose
       public void close(Session session) {
		   sessionHandler.removeSession(session);
                   //find android sessions connected with that session (if any)
   }
	   

	
    @OnMessage
    public void handleMessage(Session session,String message,boolean last){	
      
    	synchronized (this) {
    		sessionHandler.echoTextMessage(session,message,true);
    	}
    } 
    
    @OnError
    public void error(Session session, Throwable error) { 	
        //Connection error.
        System.out.println("EchoEndpoint on error");
        // printStackTrace method 
        // prints line numbers + call stack 
        error.printStackTrace(); 
          
        // Prints what exception has been thrown 
        System.out.println(error); 
        
        //Something happened in the way to heaven...
        //Phill Collins....
      /*  if(session!=null) {
        	try {
				session.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	sessionHandler.removeSession(session);
        }*/
        
    }

}
