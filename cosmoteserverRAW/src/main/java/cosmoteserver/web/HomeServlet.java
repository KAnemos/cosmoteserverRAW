/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cosmoteserver.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//import org.apache.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.websocket.Session;





public class HomeServlet extends HttpServlet {
	
	private Session session;
	private static final long serialVersionUID = 1L;
	private static final String GOOGLE_SERVER_KEY = "AIzaSyB2aLO5STAmGa0Lngcoq46FJ2TYTn2O0JE";
	static final String REGISTER_NAME = "name";
	static final String MESSAGE_KEY = "message";
	static final String TO_NAME = "toName";
	String info;

	//private Logger logger = Logger.getLogger(this.getClass());
	private RequestDispatcher homeJsp;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
	/*wsold  public void init() throws ServletException {
       String path = this.getServletContext().getContextPath() +"/android";
        try
        {
            URI uri = new URI("ws", "localhost:8080", path, null, null);
            this.session = ContainerProvider.getWebSocketContainer()
                    .connectToServer(this, uri);
        }
        catch(URISyntaxException | IOException | DeploymentException e)
        {
            throw new ServletException("Cannot connect to " + path + ".", e);
        }*/
				
		ServletContext context = config.getServletContext();
		homeJsp = context.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
               // homeJsp = context.getRequestDispatcher("/WEB-INF/jsp/newjsp.jsp");
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	//	logger.debug("Returning message: bye");
		doPost(req, resp);
/*wsold	    {
	        String message=req.getQueryString();
	        try(OutputStream output = this.session.getBasicRemote().getSendStream();
	            ObjectOutputStream stream = new ObjectOutputStream(output))
	        {
	            stream.writeObject(message);
	        }
	        resp.getWriter().append("OK");
	    }*/
		
		
		
		
		
	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		
			HttpSession session = req.getSession();
                        String username=(String) session.getAttribute("username");
                        String AK_label = req.getParameter("AK");
                        
                        //get user from DB
                        User user = new UserDAO().findByUsername(username);
                        String loginstate=user.getLoginstate();
                        if(loginstate.equals("OFF")) return;
                        //if there is already an active home session!
                        String homesessionactive=user.gethomesessionactive();
                        if(homesessionactive.equals("YES")) return;
                        //get phonelist from DB for specific user
                        String TTLP=new UserDAO().findTTLP(username);
                        List<String> AKlist=new UserDAO().findAllAK(TTLP);
                        List<String> labelAKlist=new UserDAO().findAllAKLabels(TTLP);
                        
                        if(AKlist.size()>0){
                            user.setAKlist(AKlist);
                        }
                        if(labelAKlist.size()>0){
                            user.setAKlabellist(labelAKlist);
                        }
                        
                        
                        if (AK_label==null){
                            req.setAttribute("message", "Welcome to Cloud Field Services");
                            req.setAttribute("username",username);
                            homeJsp.forward(req, resp);
                            return;
                        }
                        
                        if (AK_label.equals("UPDATE")){
                            req.setAttribute("message", "Welcome to Cloud Field Services");
                            req.setAttribute("username",username);
                            resp.setContentType("text/plain"); 
                            resp.setCharacterEncoding("utf-8");
            
                            PrintWriter writer =  resp.getWriter();
                            String AKlistString = "";
                          /*\  for (String s : phonelist){
                                phonelistString += s + " ";
                            }*/
                            for (String s : labelAKlist){
                                AKlistString += s + " ";
                            }
                           // req.setAttribute("phonelist",phonelistString);
                            try{ 
                                writer.write(AKlistString);
                                writer.close();
                            }
                            catch(Exception ex)
                            { 
                                 ex.getStackTrace();
                            }
                            //communicating with CP
                        /*     HttpURLConnectionExample huc=new HttpURLConnectionExample();
                            try{ 
                             huc.sendGet();
                            }
                            catch(Exception ex)
                            { 
                              ex.getStackTrace();
                            }*/

                            
                            
                            return;
                        
			//req.setAttribute("xcoord", "40.644186");
			//req.setAttribute("ycoord","22.919935");
			//homeJsp.forward(req, resp);
                        
			//return;
                        }

			
		
	}	
	
/*wsold	   @OnMessage
	    public void onMessage(InputStream input)
	    {
		   
	        try(ObjectInputStream stream = new ObjectInputStream(input))
	        {
	            String message = (String) stream.readObject();
	            System.out.println(message);
	        }
	        catch(IOException | ClassNotFoundException e)
	        {
	            e.printStackTrace();
	        }
	    }
	
    @Override
    public void destroy()
    {
        try
        {
            this.session.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }*/
}

