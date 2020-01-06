package org.ladle.webapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.dao.RegionDao;
import org.ladle.dao.hibernate.object.Region;
import org.ladle.service.MailHandler;

/**
 * Servlet implementation class Index
 */
@WebServlet("")
public class Index extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(Index.class);
	
	@EJB(name = "RegionDaoImpl")
	RegionDao regionDao;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** ===================================================================================== **/
		
		
		List<Region> regionsToSend = new ArrayList<>();
		
		LOG.info("doGet()");
		
		try {
			regionsToSend = regionDao.getAllRegions();
		} catch (Exception e) {
			LOG.error("Error regionDao.getAllRegions()",e);
		}


		/** ===================================================================================== **/

		try {	

			request.setAttribute("myList", regionsToSend);
			
			//TODO
			MailHandler.sendMail();

			this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );

		} catch (Exception e) {			
			LOG.error("Error building index.jsp",e);
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
