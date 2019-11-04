package org.ladle.webapp.listener;

import javax.persistence.EntityManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jboss.logging.Logger;
import org.ladle.dao.JPAUtility;

/**
 * Web application lifecycle listener.
 * @author Coyote
 */
@WebListener
public class WebAppServletListener implements ServletContextListener {

	private static final Logger LOG = Logger.getLogger(WebAppServletListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		EntityManager em = JPAUtility.getEntityManager();
		sce.getServletContext().setAttribute("entityManager", em);
		
		LOG.info("Context initialized");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
		EntityManager em = (EntityManager) sce.getServletContext().getAttribute("entityManager");
		em.close();
		
		JPAUtility.close();
		
		LOG.info("Context destroyed");

	}

}
