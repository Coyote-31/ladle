package org.ladle.webapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static SessionFactory factory;
	private static final Logger LOG = Logger.getLogger(Inscription.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Inscription() {
		super();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {


			LOG.info("Servlet : Inscription");

			this.getServletContext().getRequestDispatcher( "/WEB-INF/inscription.jsp" ).forward( request, response );

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** ===================================================================================== **/
		try {

			factory = new Configuration().configure().buildSessionFactory();

		} catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}

		/* Récupération des éléments du formulaire d'inscription */
		String pseudo = request.getParameter("pseudo");
		String genre = request.getParameter("genre");
		String prenom = request.getParameter("prenom");
		String nom = request.getParameter("nom");
		String mail = request.getParameter("mail");
		String ville = request.getParameter("ville");
		String mdp = request.getParameter("mdp");
		String mdp2 = request.getParameter("mdp2");
		
		LOG.info("Formulaire : " 
				+ pseudo + " / "
				+ genre + " / "
				+ prenom + " / "
				+ nom + " / "
				+ mail + " / "
				+ ville + " / "
				+ mdp + " / "
				+ mdp2
				);




		doGet(request, response);
	}

}
