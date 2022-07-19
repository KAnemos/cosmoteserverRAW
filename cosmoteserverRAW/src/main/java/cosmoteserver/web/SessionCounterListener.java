/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;



public class SessionCounterListener implements HttpSessionListener {
    
  private DeviceSessionHandler sessionHandler;  

  private static int totalActiveSessions;
	
  public static int getTotalActiveSession(){
	return totalActiveSessions;
  }
	
  @Override
  public void sessionCreated(HttpSessionEvent arg0) {
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	System.out.println(timestamp);
	totalActiveSessions++;
	System.out.println("sessionCreated - add one session into counter");
	HttpSession httpsession = arg0.getSession();
	System.out.println(httpsession.getId());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
	totalActiveSessions--;
	System.out.println("sessionDestroyed - deduct one session from counter");
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	System.out.println(timestamp);
        HttpSession httpsession = se.getSession();
        System.out.println(httpsession.getId());
        httpsession.invalidate();
     //   DeviceSessionHandler sessionhandler=new DeviceSessionHandler();
      //  String message = 
      //  sessionhandler.echoTextMessage(session, message, b);
   //     sessionHandler.removeSession(session);
  }	
}