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
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.TopoHandler;

/**
 * Servlet implementation class EditeTopo
 * Permet l'édition d'un topo de l'utilisateur
 */
@SuppressWarnings("serial")
@WebServlet("/edition-topo")
public class EditeTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(EditeTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * Affiche le formulaire pour l'édition du topo.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Envoit de la liste des régions
    request.setAttribute("regions", topoHandler.getAllRegions());

    // Récupération du topo à éditer
    String topoIDStr = request.getParameter("id");
    Integer topoID = null;
    try {
      topoID = Integer.decode(topoIDStr);

    } catch (NumberFormatException e) {
      LOG.error("Error decode topoIDStr : {}", topoIDStr, e);
      String errorMsg = "La topo est introuvable !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    Topo topo = topoHandler.getTopoByID(topoID);

    // Vérification que l'utilisateur possède le topo
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    // Si l'utilisateur n'est pas le propriétaire renvoit vers une page d'erreur
    if (!topo.getUtilisateur().getUtilisateurID().equals(utilisateur.getUtilisateurID())) {

      String errorMsg = "Seul le propriétaire du topo peut le modifier !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Envoit de l'ID du topo
    request.setAttribute("topoID", topoID);

    // Envoit des données à éditer
    request.setAttribute("selectedRegion", topo.getRegion().getRegionID());
    request.setAttribute("inputedTopoLieu", topo.getLieu());
    request.setAttribute("inputedTopoNom", topo.getNom());
    request.setAttribute("checkedboxDispo", topo.isDisponible());
    request.setAttribute("textedareaDescription", topo.getDescription());

    // Envoit vers la page d'édition du topo
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/edition-topo.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building edition-topo.jsp", e);
    }
  }

  /**
   * Met à jour le topo dans la BDD.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Récupération du topo à éditer
    String topoIDStr = request.getParameter("topoID");
    Integer topoID = null;
    try {
      topoID = Integer.decode(topoIDStr);

    } catch (NumberFormatException e) {
      LOG.error("Error decode topoIDStr : {}", topoIDStr, e);
      String errorMsg = "La topo est introuvable !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    Topo topo = topoHandler.getTopoByID(topoID);

    // Vérification que l'utilisateur possède le topo
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    // Si l'utilisateur n'est pas le propriétaire renvoit vers une page d'erreur
    if (!topo.getUtilisateur().getUtilisateurID().equals(utilisateur.getUtilisateurID())) {

      String errorMsg = "Seul le propriétaire du topo peut le modifier !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Récupération des données du formulaire
    String selectRegionIDStr = request.getParameter("inputGroupSelectRegion");
    String inputLieu = request.getParameter("inputLieu");
    String inputNom = request.getParameter("inputNom");
    String checkboxDispoStr = request.getParameter("checkboxDispo");
    String textareaDescription = request.getParameter("textareaDescription");

    // -------------------------------
    // Test des données du formulaire
    // -------------------------------

    // Region :
    // Conversion de l'id en Integer
    Integer selectRegionID = null;
    try {
      selectRegionID = Integer.decode(selectRegionIDStr);
    } catch (NumberFormatException e) {
      LOG.error("Error decode selectRegionIDStr : {}", selectRegionIDStr, e);
      String errorMsg = "La région est introuvable !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Récupération de la région
    Region selectRegion = topoHandler.getRegionByID(selectRegionID);

    // Si la région est introuvable renvoit vers la page d'erreur
    if (selectRegion == null) {
      String errorMsg = "La région est introuvable !";
      sendToErrorPage(errorMsg, errorList, request, response);
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
      // Envoit de la liste des régions
      request.setAttribute("regions", topoHandler.getAllRegions());
      // Renvoit des inputs utilisateur
      request.setAttribute("selectedRegion", selectRegionID);
      request.setAttribute("inputedTopoLieu", inputLieu);
      request.setAttribute("inputedTopoNom", inputNom);
      request.setAttribute("checkedboxDispo", checkboxDispo);
      request.setAttribute("textedareaDescription", textareaDescription);

      // Envoit vers la page d'édition du topo
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/edition-topo.jsp").forward(request, response);
        return;

      } catch (ServletException | IOException | IllegalStateException e) {
        LOG.error("Error building edition-topo.jsp", e);
      }
    }

    // Si il n'y a pas d'erreur mise à jour du topo dans la BDD
    Topo topoEdited = new Topo();

    // Remplit l'ID du topo
    topoEdited.setTopoID(topo.getTopoID());

    // Remplit les données de l'utilisateur
    topoEdited.setUtilisateur(utilisateur);

    // Remplit la date de parution
    topoEdited.setParutionDate(topo.getParutionDate());

    // Remplit le reste des données du topo depuis le formulaire
    topoEdited.setRegion(selectRegion);
    topoEdited.setLieu(inputLieu);
    topoEdited.setNom(inputNom);
    topoEdited.setDisponible(checkboxDispo);
    topoEdited.setDescription(textareaDescription);

    // Mise à jour du topo édité dans la BDD
    topoHandler.update(topoEdited);

    // Renvoit vers la page Mon Compte
    try {
      response.sendRedirect("mon-compte");

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> mon-compte.jsp", e);
    }
  }

  void sendToErrorPage(
      String errorMsg,
      List<String> errorList,
      HttpServletRequest request,
      HttpServletResponse response) {

    errorList.clear();
    errorList.add(errorMsg);
    request.setAttribute("errorList", errorList);
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error getRequestDispatcher to erreur.jsp", e);
    }
  }

}
