package me.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.xml.XmlConfiguration;

public class JettyServer {

	public static void main(String[] args) throws Exception {
		Server server = new Server(80);
		
		WebAppContext context = new WebAppContext();
		context.setContextPath("/");
		context.setResourceBase("src/main/webapp/");
		context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		context.setParentLoaderPriority(true);
		context.setClassLoader(Thread.currentThread().getContextClassLoader());
		
		XmlConfiguration jndi = new XmlConfiguration("src/main/webapp/WEB-INF/jetty-env.xml");
		jndi.configure(context);
		/*EnvEntry entry = new EnvEntry("", "name", 4000, true);
		entry.bindToENC("name");*/
		
		server.setHandler(context);
		
		server.start();
		server.join();
	}
}
