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
import org.ladle.beans.SiteForm;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class EditeSite.
 * Permet l'édition d'un site.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/edition-site")
public class EditeSite extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(EditeSite.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  @EJB(name = "EditeSiteSecteurHandler")
  private EditeSiteSecteurHandler editeSiteSecteurHandler;

  /**
   * Envoie à la jsp les informations pour l'édition des données
   * du site dans un formulaire.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [EditeSite] -> doGet()");

    // Récupère l'id du Site
    String siteID = request.getParameter("siteID");
    LOG.debug("siteIDParam {}", siteID);

    // Vérifie que l'ID est de type integer
    if (!siteID.matches("^[1-9][0-9]*$")) {
      // Renvoit vers une page d'erreur
      String errorMsg = "Le Site à éditer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupère depuis la BDD les informations du site
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Si le Site n'existe pas renvoit vers une page d'erreur
    if (site == null) {
      // Renvoit vers une page d'erreur
      String errorMsg = "Le Site à éditer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Si le Site est tag officiel vérifie que l'utilisateur a les droits
    HttpSession session = request.getSession(true);
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    LOG.debug("Site officiel : {}", site.isOfficiel());
    LOG.debug("Role utilisateur : {}", utilisateur.getRole());
    if (site.isOfficiel() && (utilisateur.getRole() < 1)) {
      // Renvoit vers une page d'erreur
      String errorMsg = "Vous ne pouvez pas éditer un site officiel LADLE "
                        + "sans être membre de l'association !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Envoit le site à la jsp
    request.setAttribute("site", site);

    // Envoit vers la jsp
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/edition-site.jsp").forward(request, response);
    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building edition-site.jsp", e);
    }
  }

  /**
   * Récupère les données du site depuis le formulaire
   * pour le mettre à jour dans la BDD.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [EditeSite] -> doPost()");

    // Récupère l'ID du site
    Integer siteID;
    try {
      siteID = Integer.decode(request.getParameter("siteID"));
      LOG.debug("siteID : {}", siteID);

    } catch (NumberFormatException e) {
      LOG.error("Error decoding siteID parameter", e);
      // Renvoit vers une page d'erreur
      String errorMsg = "Le Site à éditer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupère le site depuis la BDD
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID.toString());

    // Si le Site n'existe pas renvoit vers une page d'erreur
    if (site == null) {
      // Renvoit vers une page d'erreur
      String errorMsg = "Le Site à éditer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Si le Site est tag officiel vérifie que l'utilisateur a les droits
    HttpSession session = request.getSession(true);
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");
    if (site.isOfficiel() && (utilisateur.getRole() < 1)) {
      // Renvoit vers une page d'erreur
      String errorMsg = "Vous ne pouvez pas éditer un site officiel LADLE "
                        + "sans être membre de l'association !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // ----------------------------------------------
    // Met à jour les données dans l'objet 'siteForm'
    // Qui test les données en interne.
    // ----------------------------------------------

    SiteForm siteForm = new SiteForm();

    // Ville
    siteForm.setVille(site.getVille());

    // Nom
    siteForm.setNom(request.getParameter("siteNom"));

    // Officiel
    if (utilisateur.getRole() >= 1) {
      String siteOfficielSTR = request.getParameter("siteOfficiel");
      siteForm.setOfficiel("true".equals(siteOfficielSTR));
    }

    // Date MAJ
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    siteForm.setDateLastMaj(currentDate);

    // Descriptif
    siteForm.setDescriptif(request.getParameter("siteDescriptif"));

    // Acces
    siteForm.setAcces(request.getParameter("siteAcces"));

    // Secteurs
    siteForm.setSecteurs(site.getSecteurs());

    // -----------------------
    // Test du contenu de siteForm
    // -----------------------

    boolean validSiteUpdated = siteForm.isValid();

    if (!validSiteUpdated) {

      // Initialisation de la liste d'erreurs
      List<String> errorList = new ArrayList<>();

      // Génération de la liste des erreurs

      // Erreurs du site :
      final String ERR_SITE_NOM = "Le nom du site est limité à 80 caractères.";
      final String ERR_SITE_DESCRIPTIF = "Le descriptif du site est limité à 2000 caractères.";
      final String ERR_SITE_ACCES = "L'accès au site est limité à 2000 caractères.";

      if (siteForm.isNomErr()) {
        errorList.add(ERR_SITE_NOM);
      }
      if (siteForm.isDescriptifErr()) {
        errorList.add(ERR_SITE_DESCRIPTIF);
      }
      if (siteForm.isAccesErr()) {
        errorList.add(ERR_SITE_ACCES);
      }

      // Renvoit des données du formulaire et la liste d'erreurs à la jsp
      request.setAttribute("site", siteForm);
      request.setAttribute("errorList", errorList);

      // Recharge la jsp avec les données utilisateur et les erreurs
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/edition-site.jsp").forward(request, response);

      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to edition-site.jsp", e);
      }
      return;
    }

    // -------------------------------------------------------
    // Met à jour les données dans l'objet 'siteUpdated'
    // -------------------------------------------------------

    Site siteUpdated = new Site();

    // ID
    siteUpdated.setSiteID(site.getSiteID());

    // Ville
    siteUpdated.setVille(site.getVille());

    // Nom
    siteUpdated.setNom(siteForm.getNom());

    // Officiel
    siteUpdated.setOfficiel(siteForm.isOfficiel());

    // Date MAJ
    siteUpdated.setDateLastMaj(siteForm.getDateLastMaj());

    // Descriptif
    siteUpdated.setDescriptif(siteForm.getDescriptif());

    // Acces
    siteUpdated.setAcces(siteForm.getAcces());

    // Secteurs
    for (Secteur secteur : siteForm.getSecteurs()) {
      siteUpdated.addSecteur(secteur);
    }

    // ----------------------------------------

    // Met à jour l'objet siteUpdated dans la BDD
    editeSiteSecteurHandler.update(siteUpdated);

    // ----------------------------------------

    // Renvoit vers la page "site.jsp" correspondante
    try {
      response.sendRedirect("site?siteID=" + siteID.toString());

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> site.jsp", e);
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
