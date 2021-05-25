package org.ladle.webapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Récupère l'id du Site
    String siteID = request.getParameter("siteID");
    LOG.debug("siteID {}", siteID);

    // Récupère depuis la BDD les informations du site
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Si le Site n'existe pas renvoit vers une page d'erreur
    if (site == null) {
      errorList.add("Le Site recherché est introuvable !");
      request.setAttribute("errorList", errorList);
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to erreur.jsp", e);
      }
      return;
    }

    // Envoit le site à la jsp
    request.setAttribute("site", site);

    // Récupère les id des secteurs non-filtrés
    String[] secteursID = request.getParameterValues("secteursID");
    String debugStringSecteursID = Arrays.toString(secteursID);
    LOG.debug("secteursID : {}", debugStringSecteursID);
    List<String> listSecteursID = new ArrayList<>();

    // Créé la liste à partir du resultat de la recherche
    if ((secteursID != null) && !debugStringSecteursID.isEmpty()) {
      for (String secteurID : secteursID) {
        if (!secteurID.isEmpty()) {
          listSecteursID.add(secteurID);
        }
      }

      LOG.debug("listSecteursID : {}", listSecteursID);
      request.setAttribute("listFilterSecteursID", listSecteursID);
    }

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
