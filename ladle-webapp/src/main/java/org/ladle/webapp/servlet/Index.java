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

/**
 * Servlet implementation class Index
 */
@WebServlet("")
public class Index extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(Index.class);
	
	@EJB(name = "RegionDaoImpl")
	//@Inject
	RegionDao regionDao;

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

	//	RegionDao regionDao = new RegionDaoImpl();
		

		
		List<Region> regionsToSend = new ArrayList<>();
		
		LOG.info("Servlet : index");
		System.out.println("Servlet Println");
		
		try {
			regionsToSend = regionDao.getAllRegions();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		/** ===================================================================================== **/

		try {

			

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
