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
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.CookieHandler;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class Connexion.
 * Permet la connexion d'un utilisateur.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/connexion")
public class Connexion extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(Connexion.class);

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  /**
   * Affiche le formulaire de connexion
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Connexion] -> doGet()");

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building connexion.jsp", e);
    }
  }

  /**
   * Vérifie les informations de connexion et connecte l'utilisateur
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Connexion] -> doPost()");

    HttpSession session = request.getSession();

    // Récupération du formulaire
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

      // Récupère les données utilisateur
      Utilisateur utilisateur = userHandler.getUtilisateurOnLogin(login);

      // Test si l'utilisateur n'a pas encore validé son adresse mail
      if (utilisateur.getEmailSHA() != null) {

        // Annule l'attribut de validité de connexion
        session.setAttribute("isLoginValid", false);

        // Renvoit vers une page d'erreur
        String errorMsg = "Erreur ! Votre compte n'est pas encore validé. <br>"
                          + "Regardez vos mails pour terminer votre inscription !";
        sendToErrorPage(errorMsg, request, response);
        return;
      }

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

      try {
        response.sendRedirect("./");

      } catch (IOException | IllegalStateException e) {
        LOG.error("Error building / (Index)", e);
      }

    } else {
      // Réinitialise l'attribut "utilisateur"
      session.setAttribute("utilisateur", null);
      // Supprime les cookies "login" et "tokenLogin"
      CookieHandler.deleteLogin(request, response);
      // Ajout de la variable d'erreur "errorLoginInvalid"
      request.setAttribute("errorLoginInvalid", true);
      // Renvoit le dernier login dans le formulaire
      request.setAttribute("lastLoginPseudoMail", login);

      try {
        doGet(request, response);

      } catch (ServletException | IOException e) {
        LOG.error("Error doGet()", e);
      }
    }

  }

  /**
   * Renvoit vers une page d'erreur avec un message
   *
   * @param errorMsg : Le message
   * @param request
   * @param response
   */
  void sendToErrorPage(
      String errorMsg,
      HttpServletRequest request,
      HttpServletResponse response) {

    List<String> errorList = new ArrayList<>();
    errorList.add(errorMsg);
    request.setAttribute("errorList", errorList);

    try {
      getServletContext().getRequestDispatcher("/erreur").forward(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("Error getRequestDispatcher to /erreur", e);
    }
  }
}
