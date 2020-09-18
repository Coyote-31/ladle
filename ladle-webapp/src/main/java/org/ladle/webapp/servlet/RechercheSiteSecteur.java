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
 * Servlet implementation class RechercheSiteSecteur
 */
@WebServlet("/recherche-site-secteur")
public class RechercheSiteSecteur extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger(RechercheSiteSecteur.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [RechercheSiteSecteur] -> doGet()");

    request.setAttribute("regions", rechercheSiteSecteurHandler.getAllRegions());
    request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());

    getServletContext().getRequestDispatcher("/WEB-INF/recherche-site-secteur.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [RechercheSiteSecteur] -> doPost()");

    // Récupération des données du post
    String selectedRegion = request.getParameter("inputGroupSelectRegion");
    String selectedDepartement = request.getParameter("inputGroupSelectDepartement");
    String inputedCodePostal = request.getParameter("inputCodePostal");

    // Variable de détection des changements sur le formulaire
    String formChangeOn = request.getParameter("formChangeOn");
    LOG.debug("getParam formChangeOn : {}", formChangeOn);

    // Si changement de Région
    if ("region".equals(formChangeOn)) {

      // Renvoit des données à la jsp
      request.setAttribute("selectedRegion", selectedRegion);
      request.setAttribute("inputedCodePostal", inputedCodePostal);

      // Génération des listes de sélection
      request.setAttribute("regions", rechercheSiteSecteurHandler.getAllRegions());
      // Gestion du cas de sélection : toutes les regions
      if ("all".equals(selectedRegion)) {
        request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());
      } else {
        request.setAttribute("departements", rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion));
      }

      getServletContext().getRequestDispatcher("/WEB-INF/recherche-site-secteur.jsp").forward(request, response);
      return;
    }

    doGet(request, response);
  }

}
