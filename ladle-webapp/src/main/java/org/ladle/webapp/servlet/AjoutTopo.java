package org.ladle.webapp.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.TopoHandler;

/**
 * Servlet implementation class AjoutTopo.
 * Permet la création d'un nouveau Topo.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/ajout-topo")
public class AjoutTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AjoutTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * Affiche le formulaire de création d'un nouveau topo.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Envoit de la liste des régions
    request.setAttribute("regions", topoHandler.getAllRegions());

    // Envoit vers la page de création du topo
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/ajout-topo.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building ajout-topo.jsp", e);
    }
  }

  /**
   * Ajoute le nouveau topo dans la BDD.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Récupération des données du formulaire
    String selectRegionIDStr = request.getParameter("inputGroupSelectRegion");
    String inputLieu = request.getParameter("inputLieu");
    String inputNom = request.getParameter("inputNom");
    String checkboxDispoStr = request.getParameter("checkboxDispo");
    String textareaDescription = request.getParameter("textareaDescription");

    // -------------------------------
    // Test des données du formulaire
    // -------------------------------

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Region :
    // Conversion de l'id en Integer
    Integer selectRegionID = null;
    try {
      selectRegionID = Integer.decode(selectRegionIDStr);
    } catch (NumberFormatException e) {
      LOG.error("Error decode selectRegionIDStr : {}", selectRegionIDStr, e);
      String errorMsg = "La région est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupération de la région
    Region selectRegion = topoHandler.getRegionByID(selectRegionID);

    // Si la région est introuvable renvoit vers la page d'erreur
    if (selectRegion == null) {
      String errorMsg = "La région est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Lieu :
    if (inputLieu.length() > 80) {
      errorList.add("Le lieu ne doit pas dépasser 80 caractères.");
      request.setAttribute("topoLieuError", true);
    }

    // Nom :
    if (inputNom.length() > 80) {
      errorList.add("Le nom ne doit pas dépasser 80 caractères.");
      request.setAttribute("topoNomError", true);
    }

    // Disponibilité :
    boolean checkboxDispo = "on".equals(checkboxDispoStr);

    // Description :
    if (textareaDescription.length() > 2000) {
      errorList.add("La description ne doit pas dépasser 2000 caractères.");
      request.setAttribute("topoDescriptionError", true);
    }

    // Si il y a des erreurs renvoit vers le formulaire
    if (!errorList.isEmpty()) {
      // Envoit de la liste d'erreurs
      request.setAttribute("errorList", errorList);
      // Renvoit des inputs utilisateur
      request.setAttribute("selectedRegion", selectRegionID);
      request.setAttribute("inputedTopoLieu", inputLieu);
      request.setAttribute("inputedTopoNom", inputNom);
      request.setAttribute("checkedboxDispo", checkboxDispo);
      request.setAttribute("textedareaDescription", textareaDescription);

      // Renvoit vers le doGet() pour l'initialisation du formulaire
      try {
        doGet(request, response);
        return;
      } catch (ServletException | IOException e) {
        LOG.error("Error : doGet()", e);
      }
    }

    // Si il n'y a pas d'erreur création du topo pour le persist dans la BDD
    Topo topo = new Topo();

    // Récupère les données de l'utilisateur
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    topo.setUtilisateur(utilisateur);

    // Remplit le reste des données du topo depuis le formulaire
    topo.setRegion(selectRegion);
    topo.setLieu(inputLieu);
    topo.setNom(inputNom);
    topo.setDisponible(checkboxDispo);
    topo.setDescription(textareaDescription);

    // Création de la date de parution
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    topo.setParutionDate(currentDate);

    // Persistance du nouveau topo dans la BDD
    topoHandler.persist(topo);

    // Renvoit vers la page Mon Compte
    try {
      response.sendRedirect("mon-compte");

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> mon-compte.jsp", e);
    }
  }

  /**
   * Renvoit vers une page d'erreur avec un message
   *
   * @param errorMsg : Le message
   * @param request
   * @param response
   */
  void sendToErrorPage(
      String errorMsg,
      HttpServletRequest request,
      HttpServletResponse response) {

    List<String> errorList = new ArrayList<>();
    errorList.add(errorMsg);
    request.setAttribute("errorList", errorList);

    try {
      getServletContext().getRequestDispatcher("/erreur").forward(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("Error getRequestDispatcher to /erreur", e);
    }
  }

}
