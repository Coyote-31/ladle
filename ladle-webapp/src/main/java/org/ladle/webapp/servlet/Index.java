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
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class Index.
 * Page d'accueil du site LADLE.
 * Affiche une présentation de l'association
 * et les 3 derniers secteurs maj.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("")
public class Index extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(Index.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  /**
   * Implémente l'affichage de la page d'accueil du site LADLE.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Index] -> doGet()");

    // Envoit la liste des 3 derniers secteurs maj.
    request.setAttribute("secteurs", rechercheSiteSecteurHandler.getLast3SecteursUpdated());

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building index.jsp", e);
    }
  }

}
