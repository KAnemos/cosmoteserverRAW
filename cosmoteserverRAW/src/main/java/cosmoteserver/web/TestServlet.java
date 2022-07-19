/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;
import java.io.PrintWriter;
import java.io.IOException;

import javax.inject.Inject;
//import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author koutote
 */

public class TestServlet extends HttpServlet {

    @Inject
    private DeviceSessionHandler sessionHandler;
   // private STM32SessionHandler stm32sessionHandler;

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
        
        //For the moment we do not expect any GET from STM32
         doPost(req, resp);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    	
				
//			}
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
     //   String user = request.getParameter("user");
     //   String userphone = request.getParameter("userphone");
     //   String message=request.getParameter("regId");
        
            String ip_box=request.getRemoteAddr();
            String name=request.getParameter("name");
            String relaystatus=request.getParameter("relaystatus");
            String message=request.getParameter("message");
            STM32 stm32;

            if(message.equals("UP")) {
                stm32=new STM32DAO().findByBoxName(name);
                sessionHandler.STM32_add(stm32,name,relaystatus,ip_box);
                sessionHandler.STM32_pongMessageReceived(name);
            }
            
            message="cmd=xxPOST";
            //use the code below only if you want to trigger a firmware upgrade!
           // message="cmd=xflash";
            response.setContentType("text/plain"); 
            response.setCharacterEncoding("utf-8");
            PrintWriter writer =  response.getWriter();
            try{ 
            
//            json
            writer.write(message);
            writer.close();
                            }
            catch(Exception ex)
             { 
              ex.getStackTrace();
             }

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

//For first time when STM32 is UP and after for pingpong timer service    
      @Override
    protected void doHead(HttpServletRequest req1, HttpServletResponse resp1)
          throws ServletException, IOException {
        

     
        
     doGet(req1, resp1);
  }

}
