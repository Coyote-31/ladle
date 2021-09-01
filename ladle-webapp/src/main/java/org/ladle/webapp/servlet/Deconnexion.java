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
import org.ladle.service.CookieHandler;
import org.ladle.service.UserHandler;

/**
 * Permet à l'utilisateur de se deconnecter
 * Servlet implementation class Deconnexion
 */
@SuppressWarnings("serial")
@WebServlet("/deconnexion")
public class Deconnexion extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(Deconnexion.class);

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Deconnexion] -> doGet()");

    HttpSession session = request.getSession();

    // Reinitialise les attributs de session
    session.setAttribute("isLoginValid", false);
    session.setAttribute("utilisateur", null);

    // Supprime les cookies
    CookieHandler.deleteLogin(request, response);

    try {
      getServletContext().getRequestDispatcher("/").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building / (index)", e);
    }
  }

}
