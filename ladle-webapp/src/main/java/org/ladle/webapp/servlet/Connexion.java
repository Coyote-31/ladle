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
import org.ladle.beans.User;
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

    // Test de validation du formulaire
    boolean isLoginValid = userHandler.isLoginValid(login, pwd);
    LOG.debug("isLoginValid : {}", isLoginValid);

    // Update l'attribut de session isLoginValid
    HttpSession session = request.getSession();
    session.setAttribute("isLoginValid", isLoginValid);

    // TODO renvoyer user.login pour la reconnexion

    // Si la connexion est validée
    if (isLoginValid) {

      User user = userHandler.getUserOnLogin(login, pwd);
      session.setAttribute("user", user);
    }

    getServletContext().getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
  }

}
