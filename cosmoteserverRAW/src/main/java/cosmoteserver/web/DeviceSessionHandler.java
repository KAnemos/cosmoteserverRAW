/*
 * 07122019 critical : at commandlinereceived sub,if not OK received we
 * do not go to IDLE, only if there is a 200OK from STM32.
 * the 2 lines were outside and always running, now they are not.
 * 
 * 14122019 mdfsessionactive if for all AKs per user, this is problem
 * we must have differnet AKlabel for all users otherwise the code
 * for not allowing more than 1 session will be faulty
 */


/*
 *To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;

/**
 *
 * @author koutote
 */

import javax.enterprise.context.ApplicationScoped;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import javax.websocket.Session;



@ApplicationScoped
public class DeviceSessionHandler {
    
                   
	    private final HashSet<Session> sessions = new HashSet<>();// keeps the websocket sessions
        private final HashSet<String> STM32_box = new HashSet<>();// active STM32boxes
        private final HashMap<Session,String> AK_sessions=new HashMap<>();//active AK sessions
        private final HashMap<Session,User> user_sessions=new HashMap<>();//active user session
        private final HashMap<String,HeartBeatTimerTask> heartbeat_timers=new HashMap<>();//heartbeat timers
        private final List<Session> toremove=new ArrayList<Session>();
    
        
      
    
    
    public void addwebsocketSession(Session session) {
        
    	sessions.add(session);
      /*  for (Device device : devices) {
            JsonObject addMessage = createAddMessage(device);
            sendToSession(session, addMessage);
        }*/
    }
//This function is called after a GET from STM32 is received,while was in PENDING
//Just to inform client that the STM32 will execute command (hopefully) and change
//relaystatus accordingly.
        public void STM32_inform(STM32 stm32){
            
            String AKid=stm32.getAK_id();
            String F1O=stm32.getFissa1_Open();String F2O=stm32.getFissa2_Open();String F3O=stm32.getFissa3_Open();
            String F1S=stm32.getFissa1_Short();String F2S=stm32.getFissa2_Short();String F3S=stm32.getFissa3_Short();
            String F1SG=stm32.getFissa1_Signal();String F2SG=stm32.getFissa2_Signal();String F3SG=stm32.getFissa3_Signal();
            String F1N=stm32.getFissa1_Newnet();String F2N=stm32.getFissa2_Newnet();String F3N=stm32.getFissa3_Newnet();
        //String relay_at_server=F1G+F2G+F3G+F1R+F2R+F3R;
            String relay_at_server=F1O+F1S+F1SG+F1N+" "+F2O+F2S+F2SG+F2N+" "+F3O+F3S+F3SG+F3N;
            TTLPAK ttlpak=new TTLPAKDAO().findByAKid(AKid);
            String AKlabel=ttlpak.getAKlabel();
            String ttlpid=ttlpak.getTTLPid();
            User user=new UserDAO().findByTTLPid(ttlpid);
            String message=AKlabel+" STM32isUP "+relay_at_server;
            sendToUserSessions2(user,message);
            sendToAKSessions(AKlabel,message);
            
        }
        
        public void STM32_add (STM32 stm32, String name,String relaystatus, String ip_box){
            //if STM32 is already runnning just quit
            short boxinthelist=0;
            for (String boxname : STM32_box) {
                if(boxname.equals(name)){
                    //if CMD is PENDING (IP have been changed and no repsonse from STM32
                    String ip_in_db=stm32.get_ip();
                    if (ip_box.equals(ip_in_db)){
                        //it has been observer even no IP has changed,
                    	//a deadlock in CMD=PENDING, if this is the case,
                    	//just return it to IDLE
                    	//Setup CP/CMD/IP of STM32            
                        stm32.setCP_state("UP");
                        stm32.setCMD_state("IDLE");
                        stm32.set_ip(ip_box);

                        //Update DB
                        new STM32DAO().updateSTM32(name,"UP");
                        //keep alive has been received with virtual 1111...relaystatus
                        //do not update database......
                       // new STM32DAO().updateSTM32relaystatus(name, relaystatus);
                        new STM32DAO().updateSTM32CMD(name,"IDLE");
                    }
                    else{
                        //Setup CP/CMD/IP of STM32            
                        stm32.setCP_state("UP");
                        stm32.setCMD_state("IDLE");
                        stm32.set_ip(ip_box);

                        //Update DB
                        new STM32DAO().updateSTM32(name,"UP");
                        new STM32DAO().updateSTM32relaystatus(name, relaystatus);
                        new STM32DAO().updateSTM32CMD(name,"IDLE");
                        new STM32DAO().updateSTM32ip(name, ip_box);
                    }
                    
                    boxinthelist=1;
                    return;                    
                }              
            }
            // the code below is running only ppppp[[[pppooooo[[[]]
            if(boxinthelist==0) {
                STM32_box.add(name);
            }
            
            String AKid=stm32.getAK_id();
            TTLPAK ttlpak=new TTLPAKDAO().findByAKid(AKid);
            String AKlabel=ttlpak.getAKlabel();
            String ttlpid=ttlpak.getTTLPid();
            User user=new UserDAO().findByTTLPid(ttlpid);

            //Setup CP/CMD/IP of STM32            
            stm32.setCP_state("UP");
            stm32.setCMD_state("IDLE");
            stm32.set_ip(ip_box);

            //Update DB
            new STM32DAO().updateSTM32(name,"UP");
            //keep alive has been received with virtual 1111...relaystatus
            //do not update database......
           // new STM32DAO().updateSTM32relaystatus(name, relaystatus);
            new STM32DAO().updateSTM32CMD(name,"IDLE");
            new STM32DAO().updateSTM32ip(name, ip_box);
            
            
            //start heartbittimer            
            MissedPongs mp=new MissedPongs();
            HeartBeatTimerTask task = new HeartBeatTimerTask(mp);
            Timer heartbittimer = new Timer();
            //tick every 30 secs, mp=3-->2 mins no POST, STM32 is considered DOWN
            heartbittimer.schedule(task, 0,30000); 
            heartbeat_timers.put(AKlabel,task);
            
            //Inform any User and AK sessions about STM32 is UP
            String message=AKlabel+" STM32isUP "+relaystatus;
            sendToUserSessions2(user,message);
            sendToAKSessions(AKlabel,message);
            
            return;
        }
        
        //this subroutine never being called from anyware....
        
        public void STM32_update(STM32 stm32, String name,String relaystatus){
            boolean newrelay=checkrelaystatus(stm32,relaystatus);
            if(!newrelay){ //if they are different
                new STM32DAO().updateSTM32relaystatus(name, relaystatus);
            }
        }

        
    public String getrelaystatus (STM32 stm32){
        String F1O=stm32.getFissa1_Open();String F2O=stm32.getFissa2_Open();String F3O=stm32.getFissa3_Open();
        String F1S=stm32.getFissa1_Short();String F2S=stm32.getFissa2_Short();String F3S=stm32.getFissa3_Short();
        String F1SG=stm32.getFissa1_Signal();String F2SG=stm32.getFissa2_Signal();String F3SG=stm32.getFissa3_Signal();
        String F1N=stm32.getFissa1_Newnet();String F2N=stm32.getFissa2_Newnet();String F3N=stm32.getFissa3_Newnet();
        //String relay_at_server=F1G+F2G+F3G+F1R+F2R+F3R;
        String relay_at_server=F1O+F1S+F1SG+F1N+" "+F2O+F2S+F2SG+F2N+" "+F3O+F3S+F3SG+F3N;
        return relay_at_server;
        
    }    
    public boolean checkrelaystatus(STM32 stm32,String relaystatus_from_STM){
        
    
        String F1O=stm32.getFissa1_Open();String F2O=stm32.getFissa2_Open();String F3O=stm32.getFissa3_Open();
        String F1S=stm32.getFissa1_Short();String F2S=stm32.getFissa2_Short();String F3S=stm32.getFissa3_Short();
        String F1SG=stm32.getFissa1_Signal();String F2SG=stm32.getFissa2_Signal();String F3SG=stm32.getFissa3_Signal();
        String F1N=stm32.getFissa1_Newnet();String F2N=stm32.getFissa2_Newnet();String F3N=stm32.getFissa3_Newnet();
        //String relay_at_server=F1G+F2G+F3G+F1R+F2R+F3R;
        String relay_at_server=F1O+F1S+F1SG+F1N+" "+F2O+F2S+F2SG+F2N+" "+F3O+F3S+F3SG+F3N;
        if(relay_at_server.equals(relaystatus_from_STM)){
            return true; //Server and STM have the same relay state
        }
        else{
            return false; //new relay state came from STM
        }
    }
    //This comes from Login servlet when user is already logged
    //we remove all resources before we allow to login again
    //removing session, user_session and AK_sessions
    
    public void removeallforuser(String username) {
     synchronized(this){
    	User user = new UserDAO().findByUsername(username);        
        for(HashMap.Entry<Session,User> entry:user_sessions.entrySet()){
        	Session key = (Session)entry.getKey();
            User user2 = entry.getValue();
            String user2name=user2.getUsername();
            if(user2name.equals(username)){                
                user_sessions.remove(key,user);
                sessions.remove(key); //remove from master also 
                new UserDAO().updateuserstate(user2,"OFF"); //user is idle
                new UserDAO().updateuserhomesessionactive(user2, "NO"); 
                //close session:
            	try {
    				key.close();
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                break;
            } 
        } 
        //check now all AKs belong to user
    	String TTLP=new UserDAO().findTTLP(username); 	
        //List<String> AKlist=new UserDAO().findAllAK(TTLP);
        List<String> labelAKlist=new UserDAO().findAllAKLabels(TTLP);
          
        String AKlabel;
        System.out.println(AK_sessions.size());
        for(int i=0;i<labelAKlist.size();i++) {
          AKlabel=labelAKlist.get(i);
          //Check now if it is AK websocket session
          for(HashMap.Entry<Session,String> entry:AK_sessions.entrySet()){
          	Session key = (Session)entry.getKey();
            String AKlabel2 = entry.getValue();
              if(AKlabel2.equals(AKlabel)){
            	 //no need for removing from hashmaps
            	 //this will be done after onclose websocket
            	 // event will be received!
            	  toremove.add(key);
                  //AK_sessions.remove(key,AKlabel2);
                  //sessions.remove(key); //remove from master also 
                  TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel2);
                  new TTLPAKDAO().updatettlpakmdfsessionactive(ttlpak, "NO");

              } 
          }        
        }
        for(Session key: toremove) {
      	 try {
				key.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          //  AK_sessions.remove(key);
          //  sessions.remove(key);              
        }
        toremove.clear();
     }//synchronise this
    }  
    
    //This comes from WebSocket Sessions (User or AK session)
    public void removeSession(Session session) {
        sessions.remove(session);
              
       //first check if it is a user-client session
        for(HashMap.Entry<Session,User> entry:user_sessions.entrySet()){
        	Session key = (Session)entry.getKey();
            User user = entry.getValue();
            if(key.equals(session)){                
                user_sessions.remove(key,user);
                new UserDAO().updateuserstate(user,"OFF"); //user is idle
                new UserDAO().updateuserhomesessionactive(user, "NO");  
                return;
            } 
        } 
        //Check now if it is AK websocket session
        for(HashMap.Entry<Session,String> entry:AK_sessions.entrySet()){
        	Session key = (Session)entry.getKey();
            String AKlabel = entry.getValue();
            if(key.equals(session)){
                AK_sessions.remove(key,AKlabel);
                TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel);
                new TTLPAKDAO().updatettlpakmdfsessionactive(ttlpak, "NO");
                return;
            } 
        }          
    }

    public void sendToUserSessions2 (User user, String message){
        String userfrom=user.getUsername();
        for(HashMap.Entry<Session,User> entry:user_sessions.entrySet()){
        	Session key = (Session)entry.getKey();
            User user2 = entry.getValue();
            String user2name=user2.getUsername();
            if(user2name.equals(userfrom)){
                //message already is "STM32 disconnected"
                sendToSession(key, message);
            } 
            
        }
        
    }
    public void sendToAKSessions(String AKlabel, String message){
     synchronized (this) {
        for(HashMap.Entry<Session,String> entry:AK_sessions.entrySet()){
        	Session key = (Session)entry.getKey();
            String AKlabel2 = entry.getValue();
            if(AKlabel2.equals(AKlabel)){
                //message already is "STM32 disconnected"
                sendToSession(key, message);
            } 
            
        }
      }
        
    }


    
    private void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            sessions.remove(session);
          //  Logger.getLogger(DeviceSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void STM32_pongMessageReceived (String name){
                STM32 stm32=new STM32DAO().findByBoxName(name);
                //STM32 stm32=getstm32fromboxname(name);
                String AKid=stm32.getAK_id();
                TTLPAK ttlpak=new TTLPAKDAO().findByAKid(AKid);
                String AKlabel=ttlpak.getAKlabel();
        for(HashMap.Entry<String,HeartBeatTimerTask> entryheartbeat:heartbeat_timers.entrySet()){ 
                    String AKname = (String)entryheartbeat.getKey();
                    HeartBeatTimerTask timertask1 = entryheartbeat.getValue();
                    if(AKlabel.equals(AKname)){
                      synchronized(this){
                          //restart timer
                          MissedPongs mp1;
                          mp1=timertask1.mp;
                          mp1.missedpongs=0;
                        //  timertask1.cancel();
                        //  heartbeat_timers.remove(boxname);
                        //  HeartBeatTimerTask task = new HeartBeatTimerTask();
                        //  Timer heartbittimer = new Timer();
                        //  heartbittimer.schedule(task, 0,60000); //every 60 secs
                        //  heartbeat_timers.put(name,task); 
                      }
                    }
         }
    }

        
//Message coming from USER and AK sessions via WEBSOCKETS!
    public void echoTextMessage(Session session, String message, boolean b) {
               
        String smsg[]=message.split(" ");
        String username=smsg[0];
        String action=smsg[1];
        String msgcom="";
        
        //find user from username
        User user = new UserDAO().findByUsername(username);
        
        if (smsg.length > 3){ //that is there is command
            msgcom=message.substring(message.length()-24); //last 24 chars hold the command
        }
        if(action.equals("LOGOUT")){//from mdf client
        	String AKlabel=smsg[2];
        	AK_sessions.remove(session,AKlabel);
            TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel);
            new TTLPAKDAO().updatettlpakmdfsessionactive(ttlpak, "NO");
        	//new UserDAO().updateusermdfsessionactive(user, "NO");
        	try {
				session.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return;
        }
        if(action.equals("LOGOUTUSER")){ //from user client
        	//26/01/2020 we remove all sessions user and mdf
        	removeallforuser(username);
        	return;
        	/* user_sessions.remove(session,user);
        	new UserDAO().updateuserhomesessionactive(user, "NO");
        	//new UserDAO().updateusermdfsessionactive(user, "NO");
        	new UserDAO().updateuserstate(user, "OFF");
        	try {
				session.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
            return;*/
        }
        
        //If KEEPALIVE from an AK session has been received
        if(action.equals("KEEPALIVE")){
        	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        	System.out.println(timestamp);
        	System.out.println("KEEPALIVE has been received");
            return;
        }
        

      //If Command from an AK session has been received
        if(action.equals("COMMAND")){
            String AKlabel=smsg[2];
            CommandFromClientReceived(session,user,AKlabel,msgcom);
            return;
        }
        
       //from client MDF page for specific AK session ie KALAMARIA or BYZANTIO or Rysio etc    
        if(action.equals("UPDATE2")){ 
            String AKlabel=smsg[2];
            AK_sessions.put(session,AKlabel);
            //find the stm32 by box name and check state of it
            TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel);
            new TTLPAKDAO().updatettlpakmdfsessionactive(ttlpak, "YES");
            String akid=ttlpak.getAKid();
            STM32 stm32=new STM32DAO().findByAK(akid);
            String boxname=stm32.getbox_label();
            String cpstate=stm32.getCP_state();
            String cpstatemsg="";
            if(cpstate.equals("DOWN")){
                cpstatemsg=" STM32disconnected ";
            }
            else{
                cpstatemsg=" STM32isUP ";
            }
            String relayatserver=getrelaystatus(stm32);    
            
            String msg=AKlabel+cpstatemsg+relayatserver;
            //the following synchronization added because we observed
            //concurrent modification excemption  
            //the syncro moved inside subroutine
            if (session.isOpen())sendToAKSessions(AKlabel,msg);
        }
//from  user session when first logins or refresh/ not from mdf  page for each!        
       if(action.equals("UPDATE")){ 
        //might be several AKs under the same user! ie KALAMARIA, BYZANTIO etc
        String numofak=smsg[2]; 
        new UserDAO().updateuserhomesessionactive(user, "YES"); //homesession is active
        user_sessions.put(session,user);
        //find the stm32 by box name
        //For each AKlabel owned by the user Int.
        for (int  j= 0; j < Integer.parseInt(numofak); j++) {       
            String AKlabel=smsg[j+3];            
            TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel);
            String akid=ttlpak.getAKid();
            STM32 stm32=new STM32DAO().findByAK(akid);
            String boxname=stm32.getbox_label();
            String cpstate=stm32.getCP_state();
            String cpstatemsg="";
            if(cpstate.equals("DOWN")){
             cpstatemsg=" STM32disconnected ";
            }
            else{
             cpstatemsg=" STM32isUP ";
            }
            String relayatserver=getrelaystatus(stm32);
            String xlong=ttlpak.getAKxlong();
            String ylat=ttlpak.getAKylat();
            
            String msg=AKlabel+cpstatemsg+relayatserver+" "+xlong+" "+ylat;
            sendToUserSessions2(user,msg);            
        } // for each AK for the same user! KALAMARIA  BYZANTIO etc
       }
    }
    
    //this subroutine never executes, the command from client is coming
    //from POST to MDFmanage, not from websockets.....
    public void CommandFromClientReceived(Session session,User user,String AKlabel,String msgcom){

        //replace whitespaces from message command
        msgcom=msgcom.replaceAll("\\s+","");
       //Find TTLP info for this AK label
        TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel);
        String akid=ttlpak.getAKid();
        //find the stm32 in this AKid
        STM32 stm32=new STM32DAO().findByAK(akid);
        String boxname=stm32.getbox_label();
        String ip_box=stm32.get_ip();
        //If STM is UP and running
        // set CMD state to PENDING!
        String cpstate=stm32.getCP_state();
        String cmdstate=stm32.getCMD_state();
        if (cpstate.equals("UP")){
           if(cmdstate.equals("IDLE")){
             //set CMD as PENDING and update DB..  
             stm32.setCMD_state("PENDING");      
             new STM32DAO().updateSTM32CMD(boxname,"PENDING");
             
             //Prepared the POST command to STM32
             HttpURLConnectionExample huc=new HttpURLConnectionExample();
             int resp_from_STM32=0;
             String relaystatus=""+msgcom.charAt(0)+msgcom.charAt(4)+msgcom.charAt(8);
                try{ 
                    resp_from_STM32=huc.sendGet(ip_box,msgcom);}
                catch(Exception ex){ 
                	ex.printStackTrace();
                //if IP is faulty connection timeout is received    
                    ex.getStackTrace();
                // We hope that CP will respond in the next command    
               // stm32.setCMD_state("IDLE"); 07/12/2109
              //  new STM32DAO().updateSTM32CMD(boxname,"IDLE");
                 }
              //HTTP 200 OK has been received from STM32  
              if(resp_from_STM32==200){
                  //update relay status in db for boxname
                  new STM32DAO().updateSTM32relaystatus(boxname, relaystatus);
                  //inform AK sessions that ordered has been completed (succesfully)
                  String message=AKlabel+" STM32isUP "+relaystatus;
                  //Inform any AK sessions
                  if (session.isOpen())sendToAKSessions(AKlabel,message);
                  stm32.setCMD_state("IDLE");
                  new STM32DAO().updateSTM32CMD(boxname,"IDLE");             
              }  

             
            }
           else{ //CMD is PENDING or other, wait....
            String data = AKlabel+" CPisnotIDLE";
            if (session.isOpen())sendToAKSessions(AKlabel,data);  
           } 
 
        }
        else { // CP is DOWN
            String data = AKlabel+" CPisDOWN";
            if (session.isOpen())sendToAKSessions(AKlabel,data);
        }       
    }
    
    public class HeartBeatTimerTask extends TimerTask{
        
        MissedPongs mp;
        public HeartBeatTimerTask(MissedPongs mp) {
         this.mp = mp;
        }
	@Override
	public void run() {
                synchronized(this){
                    mp.missedpongs++;
                
               
                String AKlabel=null;
                //find  session
                for(HashMap.Entry<String,HeartBeatTimerTask> entryheartbeat:heartbeat_timers.entrySet()){        
                    HeartBeatTimerTask timertask1 = entryheartbeat.getValue();
                    if(this.equals(timertask1)){
                       AKlabel = (String)entryheartbeat.getKey();
                    }
                }
                   //STM32 is down
                   //find the STM32
                if(AKlabel!=null){
                  if(mp.missedpongs>2) {
                    //stop timer and remove
                    this.cancel();
                    heartbeat_timers.remove(AKlabel);
                    // inform database
                     //find the stm32 by box name
                    TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel);
                    String akid=ttlpak.getAKid();
                    String ttlpid=ttlpak.getTTLPid();
                    STM32 stm32=new STM32DAO().findByAK(akid);
                    String boxname=stm32.getbox_label();
                    String cpstate="DOWN";
                    stm32.setCP_state(cpstate);
                    new STM32DAO().updateSTM32(boxname,cpstate);
                    //Also CMD state is set to IDLE
                    new STM32DAO().updateSTM32CMD(boxname,"IDLE");
                   


                    // inform User and AK sessions (if any)
                    User user=new UserDAO().findByTTLPid(ttlpid);
                    String message=AKlabel+" STM32disconnected";
                    sendToUserSessions2(user,message);
                    sendToAKSessions(AKlabel,message);
                    //remove name
                    STM32_box.remove(boxname);
                  }
                }
               }
                return;            
        }
    }

    private class MissedPongs {
        short missedpongs=0;
    }    

}
