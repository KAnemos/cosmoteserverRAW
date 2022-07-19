package cosmoteserver.web;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class Init implements ServletContextListener {

public void contextDestroyed(ServletContextEvent sce) {
	
}
private void contextInitialized2(ServletContext servletContext)
throws Exception {
InitialContext enc = new InitialContext();
Context compContext = (Context) enc.lookup("java:comp/env");//Apache Tomcat
DataSource dataSource = (DataSource) compContext.lookup("jdbc/COSMOTEdb");//Apache Tomcat/ linux
//DataSource dataSource = (DataSource) compContext.lookup("java:/jdbc/myDatasource1");
//DataSource dataSource = (DataSource) enc.lookup("java:jboss/jdbc/myDatasource2");
DataAccessObject.setDataSource(dataSource); 

new STM32DAO().initboxes();
new UserDAO().initusers();
new TTLPAKDAO().initaks();
}
public void contextInitialized(ServletContextEvent sce) {
ServletContext servletContext = sce.getServletContext();
try {
contextInitialized2(servletContext);
}
catch (Exception e)
{
//logger.error("Initialization failed.", e);
throw new RuntimeException(e);
}
//logger.debug("Initialization succeeded.");
}
}