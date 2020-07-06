package org.ladle.webapp.servlet;

import java.io.IOException;
import java.sql.SQLDataException;

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
@WebServlet("/inscription")
public class Inscription extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger(Inscription.class);

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Inscription] -> doGet()");

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("inscription.jsp loading failed", e);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    /* Récupération des éléments du formulaire d'inscription */
    LOG.debug("Servlet [Inscription] -> doPost()");

    User user = new User();

    user.setPseudo(request.getParameter("pseudo"));
    user.setGenre(request.getParameter("genre"));
    user.setPrenom(request.getParameter("prenom"));
    user.setNom(request.getParameter("nom"));
    user.setEmail(request.getParameter("email"));
    user.setVille(request.getParameter("ville"));
    user.setMdp(request.getParameter("mdp"));
    user.setMdp2(request.getParameter("mdp2"));

    LOG.debug("Formulaire envoyé : {} | {} | {} | {} | {} | {} | {} | {}",
        user.getPseudo(),
        user.getGenre(),
        user.getPrenom(),
        user.getNom(),
        user.getEmail(),
        user.getVille(),
        user.getMdp(),
        user.getMdp2());

    // Vérification & insertion dans la BDD + Récupération de la liste de validation
    try {
      request.setAttribute("validationList", userHandler.addUser(user));

    } catch (SQLDataException e1) {
      LOG.error("Error from : userHandler.addUser(user)", e1);
      request.setAttribute("internalError", true);
    }

    // Sécurise l'objet user en enlevant les mdp non cryptés
    user.setMdp(null);
    user.setMdp2(null);
    request.setAttribute("user", user);

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error(e);
    }
  }

}
