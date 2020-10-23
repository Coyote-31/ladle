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
import org.ladle.beans.jpa.Site;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class AfficheSite
 */
@SuppressWarnings("serial")
@WebServlet("/site")
public class AfficheSite extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AfficheSite.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheSite] -> doGet()");

    // Récupère l'id du Site
    String siteID = request.getParameter("siteID");
    LOG.debug("siteID {}", siteID);

    // Récupère depuis la BDD les informations du site
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Envoit le secteur à la jsp
    request.setAttribute("site", site);

    // Récupère les id des secteurs non-filtrés
    // String[] secteursID = request.getParameterValues("secteursID");

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/site.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building site.jsp", e);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheSite] -> doPost()");

    try {
      doGet(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("doGet() failed", e);
    }
  }

}
