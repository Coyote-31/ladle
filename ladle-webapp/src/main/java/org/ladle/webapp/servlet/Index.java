package org.ladle.webapp.servlet;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.ladle.dao.JPAUtility;
import org.ladle.dao.hibernate.object.Region;

/**
 * Servlet implementation class Index
 */
@WebServlet("")
public class Index extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Index.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Index() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** ===================================================================================== **/

		EntityManager em = JPAUtility.getEntityManager();
				//(EntityManager) getServletContext().getAttribute("entityManager");
		em.getTransaction().begin();
		
		List<Region> regionsToSend = em.createQuery( "from Region", Region.class ).getResultList();

		for ( Region region : regionsToSend ){
			System.out.print("ID:" + region.getRegionID()); 
			System.out.print(" Nom:" + region.getNom()); 
			System.out.println(" Soundex:" + region.getSoundex());
		}

		em.getTransaction().commit();
		

		/** ===================================================================================== **/

		try {

			Logger logger = Logger.getLogger(Index.class);
			logger.info("Servlet : index");

			request.setAttribute("myList", regionsToSend);

			this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			doGet(request, response);

		} catch (Exception e) {
			LOG.error("Error : doPost() in Index.java", e);
		}
	}

}
