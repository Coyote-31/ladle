package org.ladle.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.ladle.beans.User;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
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

		/* Récupération des éléments du formulaire d'inscription */
		
		User user = new User();
		
		user.setPseudo(request.getParameter("pseudo"));
		user.setGenre(request.getParameter("genre"));
		user.setPrenom(request.getParameter("prenom"));
		user.setNom(request.getParameter("nom"));
		user.setEmail(request.getParameter("email"));
		user.setVille(request.getParameter("ville"));
		user.setMdp(request.getParameter("mdp"));
		user.setMdp2(request.getParameter("mdp2"));

		LOG.info("Formulaire : " 
				+ user.getPseudo() + " / "
				+ user.getGenre() + " / "
				+ user.getPrenom() + " / "
				+ user.getNom() + " / "
				+ user.getEmail() + " / "
				+ user.getVille() + " / "
				+ user.getMdp() + " / "
				+ user.getMdp2()
				);

		/* vérification & insertion dans la BDD + Récupération de la liste de validation */
		UserHandler userHandler = new UserHandler();
		request.setAttribute("validationList", userHandler.addUser(user));

		doGet(request, response);
	}

}
