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
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.CookieHandler;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/connexion")
public class Connexion extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger(Connexion.class);

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Connexion] -> doGet()");

    getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Connexion] -> doPost()");

    HttpSession session = request.getSession();

    // Récupération du formulaire
    // TODO vérif des champs vides
    String login = request.getParameter("login_InputPseudoEmail");
    LOG.debug("getParam login : {}", login);

    String pwd = request.getParameter("login_InputPassword");
    LOG.debug("getParam pwd : {}", pwd);

    String stayConnected = request.getParameter("login_CheckStayConnected");
    LOG.debug("getParam stayConnected : {}", stayConnected);

    // Test de validation du formulaire
    boolean isLoginValid = userHandler.isLoginValid(login, pwd);
    LOG.debug("isLoginValid : {}", isLoginValid);

    // Update l'attribut de session isLoginValid
    session.setAttribute("isLoginValid", isLoginValid);

    // Si la connexion est validée
    if (isLoginValid) {

      // Ajoute la variable des données utilisateur
      Utilisateur utilisateur = userHandler.getUtilisateurOnLogin(login);

      // Si "rester connecté" est coché
      if ("true".equals(stayConnected)) {

        // Création du token de login
        utilisateur.setTokenLogin(userHandler.generateTokenLogin());

        // Mise à jour de l'utilisateur dans la session et la BDD
        userHandler.updateUtilisateur(utilisateur);
        session.setAttribute("utilisateur", utilisateur);

        // Add/Update les cookies "login" et "tokenLogin"
        CookieHandler.updateLogin(login, utilisateur.getTokenLogin(), request, response);

      } else {
        // Supprime les cookies "login" et "tokenLogin"
        CookieHandler.deleteLogin(request, response);

        // Mise à jour de l'utilisateur dans la session
        session.setAttribute("utilisateur", utilisateur);
      }

    } else {
      // Réinitialise l'attribut "utilisateur"
      session.setAttribute("utilisateur", null);
      // Supprime les cookies "login" et "tokenLogin"
      CookieHandler.deleteLogin(request, response);
    }

    getServletContext().getRequestDispatcher("/").forward(request, response);
  }

}
