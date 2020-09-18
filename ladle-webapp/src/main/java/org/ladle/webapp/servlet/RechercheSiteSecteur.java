package org.ladle.webapp.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Departement;
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

    // Création des constantes
    final String SELECTED_REGION = "selectedRegion";
    final String SELECTED_DEPT = "selectedDepartement";
    final String INPUTED_CP = "inputedCodePostal";

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
      request.setAttribute(SELECTED_REGION, selectedRegion);

      // Génération des listes de sélection
      request.setAttribute("regions", rechercheSiteSecteurHandler.getAllRegions());
      // Gestion du cas de sélection : toutes les regions
      if ("all".equals(selectedRegion)) {
        request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());
        request.setAttribute(SELECTED_DEPT, selectedDepartement);

        // Gestion du cas de sélection : une seule région
      } else {
        List<Departement> departements = rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion);
        request.setAttribute("departements", rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion));

        // Si le Dept sélectionné est "tous" ou est contenu dans la liste de résultats
        if ("all".equals(selectedDepartement)
            || departements.stream().anyMatch(d -> d.getDepartementCode().equals(selectedDepartement))) {
          request.setAttribute(SELECTED_DEPT, selectedDepartement);
        } else {
          request.setAttribute(SELECTED_DEPT, "all");
        }
      }

      getServletContext().getRequestDispatcher("/WEB-INF/recherche-site-secteur.jsp").forward(request, response);
      return;
    }

    doGet(request, response);
  }

}
