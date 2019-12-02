package org.ladle.webapp.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LogManager.getLogger(Inscription.class);

	@EJB(name = "UserHandler")
	UserHandler userHandler;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {


			LOG.info("Servlet : Inscription");
			System.out.println("Servlet : Inscription");

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
		LOG.debug("Début de post...");
		System.out.println("Début de post...");
		
		User user = new User();
		
		System.out.println(request.getParameter("prenom"));
		
		user.setPseudo(request.getParameter("pseudo"));
		user.setGenre(request.getParameter("genre"));
		user.setPrenom(request.getParameter("prenom"));
		user.setNom(request.getParameter("nom"));
		user.setEmail(request.getParameter("email"));
		user.setVille(request.getParameter("ville"));
		user.setMdp(request.getParameter("mdp"));
		user.setMdp2(request.getParameter("mdp2"));

		LOG.debug("Formulaire envoyé");
		System.out.println(user.getPrenom());
		System.out.println("Formulaire envoyé");
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
		//UserHandler userHandler = new UserHandler();
		
		
		request.setAttribute("validationList", userHandler.addUser(user));

		doGet(request, response);
	}

}
