package org.ladle.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class AfficheErreur.
 * Implémente l'affichage des erreurs.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/erreur")
public class AfficheErreur extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AfficheErreur.class);

  /**
   * Affiche les erreurs depuis une liste.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheErreur] -> doGet()");

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building erreur.jsp", e);
    }
  }

  /**
   * Redirige vers la fonction doGet du Servlet.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheErreur] -> doPost()");

    try {
      doGet(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("doGet() failed", e);
    }
  }

}
