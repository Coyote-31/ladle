package org.ladle.webapp.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.SecteurForm;
import org.ladle.beans.VoieForm;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Voie;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class EditeSecteur.
 * Permet l'édition d'un secteur.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/edition-secteur")
@MultipartConfig
public class EditeSecteur extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(EditeSecteur.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  @EJB(name = "EditeSiteSecteurHandler")
  private EditeSiteSecteurHandler editeSiteSecteurHandler;

  /**
   * Envoie à la jsp les informations pour l'édition des données
   * du secteur dans un formulaire.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [EditeSecteur] -> doGet()");

    // Récupère l'id du Secteur
    String secteurID = request.getParameter("secteurID");
    LOG.debug("secteurIDParam {}", secteurID);

    if (secteurID == null) {
      SecteurForm secteurFormAttr = (SecteurForm) request.getAttribute("secteur");
      secteurID = secteurFormAttr.getSecteurID();
    }

    // Récupère depuis la BDD les informations du secteur
    Secteur secteur = rechercheSiteSecteurHandler.getSecteurByID(secteurID);

    // Si le Secteur n'existe pas renvoit vers une page d'erreur
    if (secteur == null) {
      String errorMsg = "Le Secteur à éditer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Envoit le secteur à la jsp
    request.setAttribute("secteur", secteur);

    if ((secteur.getPlanBase64() != null) && !secteur.getPlanBase64().isEmpty()) {
      // Récupère les dimensions de l'image du secteur (si elle existe)
      try {
        BufferedImage bufferedSecteurPlan = ImageIO
            .read(new ByteArrayInputStream(secteur.getPlan()));

        Integer secteurPlanWidth = bufferedSecteurPlan.getWidth();
        LOG.debug("secteurPlanWidth : {}", secteurPlanWidth);
        request.setAttribute("secteurPlanWidth", secteurPlanWidth);

        Integer secteurPlanHeight = bufferedSecteurPlan.getHeight();
        LOG.debug("secteurPlanHeight : {}", secteurPlanHeight);
        request.setAttribute("secteurPlanHeight", secteurPlanHeight);

      } catch (IOException e) {
        LOG.error("Error IO on BufferedImage to get heigth and width", e);
      }
    }

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/edition-secteur.jsp").forward(request, response);
    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building edition-secteur.jsp", e);
    }
  }

  /**
   * Récupère les données du secteur depuis le formulaire
   * pour le mettre à jour dans la BDD.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [EditeSecteur] -> doPost()");

    // Récupère l'ID du secteur
    Integer secteurID;
    try {
      secteurID = Integer.decode(request.getParameter("secteurID"));
      LOG.debug("secteurID : {}", secteurID);

    } catch (NumberFormatException e) {
      LOG.error("Error decoding secteurID parameter", e);
      // Si il y a une erreur renvoit à la page d'erreur
      String errorMsg = "Le Secteur à éditer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupère le secteur depuis la BDD
    Secteur secteur = rechercheSiteSecteurHandler.getSecteurByID(secteurID.toString());

    // Si le Secteur n'existe pas renvoit vers une page d'erreur
    if (secteur == null) {
      String errorMsg = "Le Secteur à éditer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // -------------------------------------------------------
    // Met à jour les données dans l'objet 'secteurForm'
    // Qui test les données en interne.
    // -------------------------------------------------------

    SecteurForm secteurForm = new SecteurForm();

    // Site
    secteurForm.setSite(secteur.getSite());

    // SecteurID
    secteurForm.setSecteurID(secteurID.toString());

    // Nom
    secteurForm.setNom(request.getParameter("secteurNom"));

    // Date dernière maj
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
    secteurForm.setDateLastMaj(currentDate);

    // Descriptif
    secteurForm.setDescriptif(request.getParameter("secteurDescriptif"));

    // Acces
    secteurForm.setAcces(request.getParameter("secteurAcces"));

    // Plan
    Part secteurPlanPart;
    try {
      secteurPlanPart = request.getPart("secteurPlan");
      final int MAX_PLAN_SIZE = 5 * 1024 * 1024;

      // Si un nouveau plan est fourni dans le formulaire
      if ((secteurPlanPart != null) && (secteurPlanPart.getSize() != 0)) {

        // Si il respecte la taille et le nom d'extension
        if ((secteurPlanPart.getSize() < MAX_PLAN_SIZE)
            && FilenameUtils.getExtension(secteurPlanPart.getSubmittedFileName()).matches("^png|jpg|jpeg$")) {

          secteurForm.setPlan(IOUtils.toByteArray(secteurPlanPart.getInputStream()));
        } else {
          secteurForm.setPlanErr(true);
          // Si un plan existe déjà
          if ((secteur.getPlanBase64() != null) && !secteur.getPlanBase64().isEmpty()) {
            secteurForm.setPlanBase64(secteur.getPlanBase64());
            // Sinon il n'y a pas de plan
          } else {
            secteurForm.setPlanBase64(null);
          }
        }

        // Si un plan existe déjà
      } else if ((secteur.getPlanBase64() != null) && !secteur.getPlanBase64().isEmpty()) {
        secteurForm.setPlanBase64(secteur.getPlanBase64());
        // Sinon il n'y a pas de plan
      } else {
        secteurForm.setPlanBase64(null);
      }

    } catch (IOException | ServletException | IllegalArgumentException e) {
      LOG.error("Error request.getPart() of secteurPlan", e);

      // Si un plan existe déjà
      if ((secteur.getPlanBase64() != null) && !secteur.getPlanBase64().isEmpty()) {
        secteurForm.setPlanBase64(secteur.getPlanBase64());
        // Sinon il n'y a pas de plan
      } else {
        secteurForm.setPlanBase64(null);
      }
    }

    // Cas de la suppression du plan
    if ("true".equals(request.getParameter("supprimePlan"))) {
      secteurForm.setPlanBase64(null);
    }

    // --------------------------------------------------

    // Récupère les IDs des voies du secteur depuis la BDD
    List<Integer> voiesIDsBDD = new ArrayList<>();
    for (Voie voie : secteur.getVoies()) {
      voiesIDsBDD.add(voie.getVoieID());
    }

    // Liste le numéro de l'input du formulaire de chaque voie
    List<Integer> voiesFormNum = new ArrayList<>();

    Enumeration<String> enumParameterNames = request.getParameterNames();
    while (enumParameterNames.hasMoreElements()) {
      String paramVoieID = enumParameterNames.nextElement();

      if (paramVoieID.matches("^voieID[0-9]+$")) {

        int currentVoieNum;
        try {
          currentVoieNum = Integer.decode((paramVoieID).substring(6));
        } catch (NumberFormatException e) {
          LOG.error("Erreur du Integer.decode sur paramVoieID.substring(6)", e);
          // Renvoit vers une page d'erreur
          String errorMsg = "Une erreur est survenue lors de la récupération des voies !";
          sendToErrorPage(errorMsg, request, response);
          return;
        }
        voiesFormNum.add(currentVoieNum);
      }
    }

    // ----------------------------------------
    // Met à jour les voies dans 'secteurForm'
    // ----------------------------------------

    for (Integer voieFormNum : voiesFormNum) {

      // Récupère l'ID de la voie
      Integer voieID;
      String voieIDParam = request.getParameter("voieID" + voieFormNum);

      try {
        if (voieIDParam.isEmpty()) {
          voieID = null;
        } else {
          voieID = Integer.decode(voieIDParam);
        }
      } catch (NumberFormatException e) {
        LOG.error("Erreur du Integer.decode pour voidID + voieFormNum", e);
        // Renvoit vers une page d'erreur
        String errorMsg = "Une erreur est survenue lors de la récupération des voies !";
        sendToErrorPage(errorMsg, request, response);
        return;
      }

      // Si la voie n'a pas d'ID ou que l'ID existe dans la BDD
      if ((voieID == null) || voiesIDsBDD.contains(voieID)) {
        // Nouvelle voie avec les infos du formulaire
        VoieForm voieForm = new VoieForm();

        // Voie ID
        if (voieID == null) {
          voieForm.setVoieID(null);
        } else {
          voieForm.setVoieID(voieID.toString());
        }

        // Voie Numero
        voieForm.setNumero(request.getParameter("numVoie" + voieFormNum));

        // Voie Cotation
        voieForm.setCotation(request.getParameter("cotationVoie" + voieFormNum));

        // Voie Nom
        voieForm.setNom(request.getParameter("nomVoie" + voieFormNum));

        // Voie Hauteur
        voieForm.setHauteur(request.getParameter("hauteurVoie" + voieFormNum));

        // Voie Dégaines
        voieForm.setDegaine(request.getParameter("degaineVoie" + voieFormNum));

        // Voie Remarque
        voieForm.setRemarque(request.getParameter("remarqueVoie" + voieFormNum));

        LOG.debug("Add to voieForm ID:{}, Num:{}, Cot:{}, Nom:{}, Haut:{}, Deg:{}, Rem:{}",
            voieForm.getVoieID(),
            voieForm.getNumero(),
            voieForm.getCotation(),
            voieForm.getNom(),
            voieForm.getHauteur(),
            voieForm.getDegaine(),
            voieForm.getRemarque());

        // Ajoute la voie au secteur mis à jour
        secteurForm.addVoie(voieForm);
      }
    }

    // -----------------------
    // Test global de voieForm
    // -----------------------

    // Si l'envoit du formulaire n'est pas valide
    if (!secteurForm.isValid()) {

      // Initialisation de la liste d'erreurs
      List<String> errorList = new ArrayList<>();

      // Génération de la liste des erreurs

      // Erreurs du secteur :
      final String ERR_SECTEUR_NOM = "Le nom du secteur est limité à 80 caractères.";
      final String ERR_SECTEUR_DESCRIPTIF = "Le descriptif du secteur est limité à 2000 caractères.";
      final String ERR_SECTEUR_ACCES = "L'accès au secteur est limité à 2000 caractères.";
      final String ERR_SECTEUR_PLAN = "L'image doit être au format png, jpg ou jpeg et être inferieure à 5 Mo.";

      if (secteurForm.isNomErr()) {
        errorList.add(ERR_SECTEUR_NOM);
      }
      if (secteurForm.isDescriptifErr()) {
        errorList.add(ERR_SECTEUR_DESCRIPTIF);
      }
      if (secteurForm.isAccesErr()) {
        errorList.add(ERR_SECTEUR_ACCES);
      }
      if (secteurForm.isPlanErr()) {
        errorList.add(ERR_SECTEUR_PLAN);
      }

      // Erreurs des voies :
      final String ERR_VOIE_NUMERO = "Le numéro d'une voie va de 1 à 999 et peut être suivi de bis ou ter. Ex: 42bis";
      final String ERR_VOIE_COTATION = "La cotation va de 3 à 9 et peut être suivie de "
                                       + "la lettre a, b ou c suivit ou non de +. Ex: 4 ou 4b+";
      final String ERR_VOIE_NOM = "Le nom d'une voie est limité à 45 caractères.";
      final String ERR_VOIE_HAUTEUR = "La hauteur d'une voie est en mètres et va de 1 à 999. Ex: 42";
      final String ERR_VOIE_DEGAINE = "Le nombre de dégaines de la voie va de 0 à 99. Ex: 12";
      final String ERR_VOIE_REMARQUE = "La remarque pour une voie est limitée à 255 caractères.";

      // Boucle sur les voies et vérifie que l'erreur n'est pas déjà dans la liste
      for (VoieForm voie : secteurForm.getVoies()) {

        if (voie.isNumeroErr() && !errorList.contains(ERR_VOIE_NUMERO)) {
          errorList.add(ERR_VOIE_NUMERO);
        }
        if (voie.isCotationErr() && !errorList.contains(ERR_VOIE_COTATION)) {
          errorList.add(ERR_VOIE_COTATION);
        }
        if (voie.isNomErr() && !errorList.contains(ERR_VOIE_NOM)) {
          errorList.add(ERR_VOIE_NOM);
        }
        if (voie.isHauteurErr() && !errorList.contains(ERR_VOIE_HAUTEUR)) {
          errorList.add(ERR_VOIE_HAUTEUR);
        }
        if (voie.isDegaineErr() && !errorList.contains(ERR_VOIE_DEGAINE)) {
          errorList.add(ERR_VOIE_DEGAINE);
        }
        if (voie.isRemarqueErr() && !errorList.contains(ERR_VOIE_REMARQUE)) {
          errorList.add(ERR_VOIE_REMARQUE);
        }
      }

      // Renvoit des données du formulaire et la liste d'erreurs à la jsp
      request.setAttribute("secteur", secteurForm);
      request.setAttribute("errorList", errorList);

      // Recharge la jsp avec les données utilisateur et les erreurs
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/edition-secteur.jsp").forward(request, response);

      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to edition-secteur.jsp", e);
      }
      return;
    }

    // --------------------------------------------------
    // Met à jour les données dans l'objet 'secteurUpdated'
    // --------------------------------------------------

    Secteur secteurUpdated = new Secteur();

    // --- Secteur ID ---
    secteurUpdated.setSecteurID(secteurID);

    // --- Secteur Site ---
    secteurUpdated.setSite(secteur.getSite());

    // --- Secteur Nom ---
    secteurUpdated.setNom(secteurForm.getNom());

    // --- Secteur Date ---
    secteurUpdated.setDateLastMaj(currentDate);

    // --- Secteur Descriptif ---
    secteurUpdated.setDescriptif(secteurForm.getDescriptif());

    // --- Secteur Acces ---
    secteurUpdated.setAcces(secteurForm.getAcces());

    // --- Secteur Plan ---
    secteurUpdated.setPlan(secteurForm.getPlan());

    // ----------------------------------------
    // Met à jour les voies dans secteurUpdated
    // ----------------------------------------

    // Boucle sur les voies de voieForm pour remplir secteurUpdated
    for (VoieForm voieForm : secteurForm.getVoies()) {

      // Nouvelle voie avec les infos de voieForm
      Voie voie = new Voie();

      try {
        if ((voieForm.getVoieID() != null) && !voieForm.getVoieID().isEmpty()) {
          voie.setVoieID(Integer.decode(voieForm.getVoieID()));
        }
        voie.setNumero(voieForm.getNumero());
        voie.setCotation(voieForm.getCotation());
        voie.setNom(voieForm.getNom());
        if (voieForm.getHauteur().isEmpty()) {
          voie.setHauteur(null);
        } else {
          voie.setHauteur(Integer.decode(voieForm.getHauteur()));
        }
        if (voieForm.getDegaine().isEmpty()) {
          voie.setDegaine(null);
        } else {
          voie.setDegaine(Integer.decode(voieForm.getDegaine()));
        }
        voie.setRemarque(voieForm.getRemarque());

      } catch (NumberFormatException e) {
        LOG.error("Erreur du Integer.decode sur secteurUpdated", e);
        // Renvoit vers une page d'erreur
        String errorMsg = "Une erreur est survenue lors de la récupération des voies !";
        sendToErrorPage(errorMsg, request, response);
        return;
      }

      LOG.debug("Add voie ID:{}, Num:{}, Cot:{}, Nom:{}, Haut:{}, Deg:{}, Rem:{}",
          voie.getVoieID(),
          voie.getNumero(),
          voie.getCotation(),
          voie.getNom(),
          voie.getHauteur(),
          voie.getDegaine(),
          voie.getRemarque());

      // Ajoute la voie au secteur mis à jour
      secteurUpdated.addVoie(voie);
    }

    // ----------------------------------------

    // Met à jour l'objet secteurUpdated dans la BDD
    editeSiteSecteurHandler.update(secteurUpdated);

    // ----------------------------------------

    // Renvoit vers la page d'édition du secteur (cas supprime plan)
    if ("true".equals(request.getParameter("supprimePlan"))) {

      try {
        response.sendRedirect("edition-secteur?secteurID=" + secteurID.toString());

      } catch (IOException | IllegalStateException e) {
        LOG.error("Error sendRedirect -> edition-secteur.jsp", e);
      }
      return;
    }

    // Sinon renvoit vers la page "secteur.jsp" correspondante
    try {
      response.sendRedirect("secteur?secteurID=" + secteurID.toString());

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> secteur.jsp", e);
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
