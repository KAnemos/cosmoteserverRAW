/*
 * 07.12.2019 if not 200OK from STM32 then we stay in PENDING state.
 *            we go back to IDLE only if we receive 200OK from STM32
 *            if 3mins without sign from STM32 heartbittimer will set state to DOWN
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author koutote
 */
public class Mdfmanage extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 
        private RequestDispatcher mdfmanageJsp;
	
	@Override
    public void init(ServletConfig config) throws ServletException {				
	ServletContext context = config.getServletContext();
	mdfmanageJsp = context.getRequestDispatcher("/WEB-INF/jsp/mdfmanage.jsp");
	}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
            HttpSession session = req.getSession();
            //String username=(String) session.getAttribute("username");
            String username=req.getParameter("username");
            String AKlabel=req.getParameter("AKnum");
            String relaystatus=req.getParameter("relaystatus");
            req.setAttribute("username",username);
            req.setAttribute("AKlabel",AKlabel);
            req.setAttribute("relaystatus",relaystatus);
            
            User user = new UserDAO().findByUsername(username);
            String loginstate=user.getLoginstate();
            if(loginstate.equals("OFF")) return;
            TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(AKlabel);
            String mdfsessionactive=ttlpak.getmdfsessionactive();
            if(mdfsessionactive.equals("YES")) return;
            mdfmanageJsp.forward(req, resp);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        HttpSession session = req.getSession();
        String username=req.getParameter("user");
        String aklabel = req.getParameter("ak");
        String msg=req.getParameter("msgvar");
        String[] commands=msg.split("\\s");
        String msgcom=msg.replaceAll("\\s+","");

      //Find TTLP info for this AK label
        TTLPAK ttlpak=new TTLPAKDAO().findByAKlabel(aklabel);
        String akid=ttlpak.getAKid();
      //find the stm32 in this AKid
        STM32 stm32=new STM32DAO().findByAK(akid);
        String boxname=stm32.getbox_label();
        String ip_box=stm32.get_ip();
        
        User user = new UserDAO().findByUsername(username);
        
      //If STM is UP and running
      // set CMD state to PENDING!
        String cpstate=stm32.getCP_state();
        String cmdstate=stm32.getCMD_state();
        if (cpstate.equals("UP")){
           if(cmdstate.equals("IDLE")){
             stm32.setCMD_state("PENDING");
             new STM32DAO().updateSTM32CMD(boxname,"PENDING");
             
            // synchronized(this){ 
             //   new STM32DAO().requeststm32box(user,akid,cpstate,"PENDING",msg);
            // }
             //Prepared the POST command to STM32
             HttpURLConnectionExample huc=new HttpURLConnectionExample();
             int resp_from_STM32=0;
             String relaystatus=""+msgcom.charAt(0)+msgcom.charAt(1)+msgcom.charAt(2)+msgcom.charAt(3)
            		 +msgcom.charAt(4)+msgcom.charAt(5)+msgcom.charAt(6)+msgcom.charAt(7)
            		 +msgcom.charAt(8)+msgcom.charAt(9)+msgcom.charAt(10)+msgcom.charAt(11);
                try{ 
                    resp_from_STM32=huc.sendGet(ip_box,msgcom);}
                catch(Exception ex){ 
                //if IP is faulty connection timeout is received    
                    ex.getStackTrace();
                // We hope that CP will respond in the next command    
                // stm32.setCMD_state("IDLE");
                // new STM32DAO().updateSTM32CMD(boxname,"IDLE");
                    
                    resp.setContentType("text/plain");
                    resp.setCharacterEncoding("utf-8");
                    PrintWriter writer =  resp.getWriter();
                    //convert phonelist to string
                    String data = aklabel+" CPisnotIDLE";
                    try{
                        writer.write(data);
                        writer.close();
                    }
                    catch(Exception ex2)
                    {
                        ex2.getStackTrace();
                    }   
                 }
              //HTTP 200 OK has been received from STM32  
              if(resp_from_STM32==200){
                  //update relay status in db for boxname
                  new STM32DAO().updateSTM32relaystatus(boxname, relaystatus);
                  //inform AK sessions that ordered has been completed (succesfully)
                 // String message=AKlabel+" STM32isUP "+relaystatus;
                  //Inform any AK sessions
                 // if (session.isOpen())sendToAKSessions(AKlabel,message);
                  stm32.setCMD_state("IDLE");
                  new STM32DAO().updateSTM32CMD(boxname,"IDLE");
                  resp.setContentType("text/plain");
                  resp.setCharacterEncoding("utf-8");
                  PrintWriter writer =  resp.getWriter();
                  //convert phonelist to string
                  String data = aklabel+" STM32isUP "+relaystatus; //note that javascript do nothing on this message
                  try{
                      writer.write(data);
                      writer.close();
                  }
                  catch(Exception ex)
                  {
                      ex.getStackTrace();
                  }
                  
              }   

             
            }
           else{ //CMD is PENDING or other, wait....

            resp.setContentType("text/plain");
            resp.setCharacterEncoding("utf-8");
            PrintWriter writer =  resp.getWriter();
            //convert phonelist to string
            String data = aklabel+" CPisnotIDLE";
            try{
                writer.write(data);
                writer.close();
            }
            catch(Exception ex)
            {
                ex.getStackTrace();
            }
               
           } 
 
        }
        else { // CP is ddwn
            resp.setContentType("text/plain");
            resp.setCharacterEncoding("utf-8");
            PrintWriter writer =  resp.getWriter();
            //convert phonelist to string
            String data = aklabel+" CPisDOWN";
            try{
                writer.write(data);
                writer.close();
            }
            catch(Exception ex)
            {
                ex.getStackTrace();
            }
            
        }
        //get phonelist from DB for specific user
  

 
            return;
        }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
