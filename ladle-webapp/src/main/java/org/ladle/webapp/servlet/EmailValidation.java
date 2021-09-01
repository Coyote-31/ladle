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
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class EmailValidation
 * Permet de valider l'email d'un compte
 */
@SuppressWarnings("serial")
@WebServlet("/email-validation")
public class EmailValidation extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(EmailValidation.class);

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  /**
   * Permet de valider l'email d'inscription.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [EmailValidation] -> doGet()");

    // Récupération du SHA du mail de validation
    String emailSHA = request.getParameter("id");
    LOG.debug("Mail SHA : {}", emailSHA);

    // Vérification de la présence dans la bdd
    // et validation du mail
    if (emailSHA != null) {
      boolean emailValide = userHandler.emailValidation(emailSHA);
      // Envoit à la jsp de la réponse
      request.setAttribute("emailValide", emailValide);
    }

    getServletContext().getRequestDispatcher("/WEB-INF/email-validation.jsp").forward(request, response);
  }

}
