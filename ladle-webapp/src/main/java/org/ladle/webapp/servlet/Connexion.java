package org.ladle.webapp.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class MailValidation
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

    LOG.info("doGet()");

    getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.info("doPost()");

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
    HttpSession session = request.getSession();
    session.setAttribute("isLoginValid", isLoginValid);

    // Variable de cookie de connexion
    boolean cookieLoginExist = false;

    // Si la connexion est validée
    if (isLoginValid) {

      // Ajoute la variable des données utilisateur
      Utilisateur utilisateur = userHandler.getUtilisateurOnLogin(login);
      session.setAttribute("utilisateur", utilisateur);

      // Sinon réinitialise l'attribut "utilisateur"
    } else {
      session.setAttribute("utilisateur", null);
    }

    // Si "rester connecté" est coché
    if ("true".equals(stayConnected)) {
      // gestion des cookies de connexion
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("login".equals(cookie.getName())) {
            cookie.setValue(login);
            cookie.setMaxAge(60 * 60 * 24 * 30);
            LOG.debug("Update cookieLogin : {}", cookie.getValue());
            cookieLoginExist = true;
          }
        }
      }

      if (!cookieLoginExist) {
        Cookie cookieLogin = new Cookie("login", login);
        cookieLogin.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookieLogin);
        LOG.debug("Add cookieLogin : {}", cookieLogin.getValue());
      }

    } else {
      // suppression des cookies de connexion
      Cookie[] cookies = request.getCookies();
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          if ("login".equals(cookie.getName())) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            LOG.debug("Delete cookieLogin");
          }
        }
      }
    }

    getServletContext().getRequestDispatcher("/WEB-INF/mon-compte.jsp").forward(request, response);
  }

}
