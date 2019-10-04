package org.ladle.webapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;
import org.ladle.dao.hibernate.object.Utilisateur;

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
	@Override
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
	@Override
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
		String email = request.getParameter("email");
		String ville = request.getParameter("ville");
		String mdp = request.getParameter("mdp");
		String mdp2 = request.getParameter("mdp2");

		LOG.info("Formulaire : " 
				+ pseudo + " / "
				+ genre + " / "
				+ prenom + " / "
				+ nom + " / "
				+ email + " / "
				+ ville + " / "
				+ mdp + " / "
				+ mdp2
				);

		Integer villeID = 666;
		byte[] salt = {69};
		byte role = 0;
		

		addUtilisateur(villeID, pseudo, genre, nom, prenom, email, mdp, salt, role);



		doGet(request, response);
	}
	
	/* Création d'un nouvel utilisateur dans la BDD */
	public void addUtilisateur(Integer villeID, String pseudo, String genre, 
			String nom, String prenom, String email, String mdp, byte[] salt, byte role){
		
		Session session = factory.openSession();
		Transaction tx = null;
		Integer utilisateurID = null;

		try {
			tx = session.beginTransaction();
			Utilisateur utilisateur = new Utilisateur(villeID, pseudo, genre, nom, prenom,	
					email, mdp, salt, role);
			utilisateurID = (Integer) session.save(utilisateur);
			tx.commit();
		} catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		} finally {
			session.close(); 
		}
		LOG.info(utilisateurID);
	}

}
