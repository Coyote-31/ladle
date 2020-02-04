package org.ladle.webapp.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class MailValidation
 */
@WebServlet("/connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(Connexion.class);
       
	@EJB(name = "UserHandler")
	UserHandler userHandler;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		LOG.info("Servlet [Connexion.java] -> doGet()");
		
		// exemple session
		HttpSession session = request.getSession();
		session.setAttribute("test", 1);
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/connexion.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);		
	}

}
