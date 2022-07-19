package cosmoteserver.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cosmoteserver.web.User;
import cosmoteserver.web.UserDAO;
public class LoginServlet extends HttpServlet {

    @Inject
private DeviceSessionHandler sessionHandler;
	
private RequestDispatcher jsp;
public void init(ServletConfig config) throws ServletException {
ServletContext context = config.getServletContext();
jsp = context.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
}
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
throws ServletException, IOException {

jsp.forward(req, resp);
}
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
throws ServletException, IOException {

String username = req.getParameter("username");
User user = new UserDAO().findByUsername(username);
if (user == null)
{

req.setAttribute("message", "Authentication failed.");
jsp.forward(req, resp);
return;
}
if(user.getLoginstate().equals("ON")) { //user already loged IN
	sessionHandler.removeallforuser(username);
	req.setAttribute("message", "Already Logged IN");
	jsp.forward(req, resp);
	return;	
}
String password = req.getParameter("password");
if (password == null || !user.getPassword().equals(password))
{

req.setAttribute("message", "Authentication failed.");
jsp.forward(req, resp);
return;
}
new UserDAO().updateuserstate(user,"ON");	//user is active
HttpSession session = req.getSession();
Long userId = user.getId();
//session.setAttribute("userId", userId);
session.setAttribute("username", username);

String url = "home";
resp.sendRedirect(url);
}
}

