package cosmoteserver.web;

import java.io.IOException;
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

public class RegisterServlet extends HttpServlet {

	private RequestDispatcher jsp;
	
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		jsp = context.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				jsp.forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username = req.getParameter("urn");
		String password1 = req.getParameter("p1");
		String password2 = req.getParameter("p2");
//1. check if empty
		if(username.equals("")){
			req.setAttribute("message", "User Empty.");
			jsp.forward(req, resp);
			return;	
		}
//2. check if passwords match
		if(password1.equals(password2)){
			//do nothing
		}
		else{
			req.setAttribute("message", "Passwords do NOT match.");
			jsp.forward(req, resp);
			return;	
		}
//3. check if another user is already registered with the same passwd.
		User user = new User();
		//user.setId(id);
		user.setUsername(username);
		user.setPassword(password1);
		User user2 = new UserDAO().findByUsername(username);
		if (user2 == null){
			//OK there is no other user alredy registered			
		}
		else{
			req.setAttribute("message", "Not a valid username, please make a new selection");
			jsp.forward(req, resp);
			return;	
		}
		// here all checks ok, register user	
		
		new UserDAO().create(user);

		HttpSession session = req.getSession();
		Long userId = user.getId();
		session.setAttribute("userId", userId);

		String url = "home";
		resp.sendRedirect(url);
}
}



