package org.ladle.webapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Topo;
import org.ladle.service.TopoHandler;

/**
 * Servlet implementation class RechercheTopo
 */
@SuppressWarnings("serial")
@WebServlet("/recherche-topo")
public class RechercheTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(RechercheTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [RechercheTopo] -> doGet()");

    // Envoit de la liste des regions
    request.setAttribute("regions", topoHandler.getAllRegions());

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/recherche-topo.jsp").forward(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error building recherche-topo.jsp", e);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [RechercheTopo] -> doPost()");

    // Récupération des critères de recherche
    String selectedRegionIDStr = request.getParameter("inputGroupSelectRegion");
    String inputedPseudo = request.getParameter("inputPseudo");
    String inputedKeywords = request.getParameter("inputKeywords");

    // Vérification des inputs :
    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Vérifie la conversion de l'ID de la région
    if ("all".equals(selectedRegionIDStr)) {
      selectedRegionIDStr = "0";
    }

    Integer selectedRegionID = 0;
    try {
      selectedRegionID = Integer.decode(selectedRegionIDStr);
    } catch (NumberFormatException e) {
      LOG.error("Error on convert regionID : {} to Integer", selectedRegionIDStr);
      errorList.add("Impossible de trouver la région correspondante !");
    }

    // Vérifie la longueur du pseudo
    if (inputedPseudo.length() > 30) {
      errorList.add("Le pseudo ne peut pas excéder 30 caractères !");
    }

    // Vérifie la longueur des mots-clés
    if (inputedKeywords.length() > 80) {
      errorList.add("Les mots-clés ne peuvent pas excéder 80 caractères !");
    }

    // Si la liste d'erreur n'est pas vide renvoit vers la page d'erreur
    if (!errorList.isEmpty()) {
      request.setAttribute("errorList", errorList);
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to erreur.jsp", e);
      }
      return;
    }

    // Récupération de la liste de résultat depuis la BDD
    List<Topo> resultTopos = topoHandler.searchTopos(selectedRegionID, inputedPseudo, inputedKeywords);

    // Renvoit de la liste de résultat vers la jsp
    request.setAttribute("topos", resultTopos);

    // Gestion d'un résultat vide
    request.setAttribute("emptyResult", resultTopos.isEmpty());

    // Renvoit des inputs utilisateur du formulaire
    request.setAttribute("selectedRegion", selectedRegionIDStr);
    request.setAttribute("inputedPseudo", inputedPseudo);
    request.setAttribute("inputedKeywords", inputedKeywords);

    // Renvoit vers le doGet pour la finalisation de la recherche de topo
    try {
      doGet(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error doGet()", e);
    }
  }

}
