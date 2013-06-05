package me.test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class JdbcListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent contextEvent) {
		try {
			Context context = new InitialContext();
			System.out.println(context.lookup("java:comp/env/name"));
			DataSource source = (DataSource) context
					.lookup("java:comp/env/default");
			contextEvent.getServletContext().setAttribute("source", source);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
	}

}
