package org.ladle.webapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Web application lifecycle listener.
 * @author Coyote
 */
@WebListener
public class WebAppServletListener implements ServletContextListener {

	private static final Logger LOG = LogManager.getLogger(WebAppServletListener.class);
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
			
		LOG.info("WebAppServletListener : Context initialized");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		LOG.info("WebAppServletListener : Context destroyed");

	}

}
