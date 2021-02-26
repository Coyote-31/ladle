package org.ladle.webapp.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Voie;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class EditeSecteur
 */
@SuppressWarnings("serial")
@WebServlet("/edition-secteur")
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
    LOG.debug("secteurID {}", secteurID);

    // Récupère depuis la BDD les informations du secteur
    Secteur secteur = rechercheSiteSecteurHandler.getSecteurByID(secteurID);

    // Envoit le secteur à la jsp
    request.setAttribute("secteur", secteur);

    if (!secteur.getPlan().isEmpty()) {
      // Récupère les dimensions de l'image du secteur (si elle existe)
      try {
        BufferedImage bufferedSecteurPlan = ImageIO
            .read(new ByteArrayInputStream(Base64.getDecoder().decode(secteur.getPlan())));

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
      // Si il y a une erreur renvoit à la page d'accueil
      try {
        response.sendRedirect("./");
      } catch (IOException e2) {
        LOG.error("Error sendRedirect to index", e2);
      }
      return;
    }

    // Récupère le secteur depuis la BDD
    Secteur secteur = rechercheSiteSecteurHandler.getSecteurByID(secteurID.toString());

    // Met à jour les données dans l'objet secteurUpdated
    Secteur secteurUpdated = new Secteur();
    secteurUpdated.setSecteurID(secteurID);
    secteurUpdated.setSite(secteur.getSite());
    secteurUpdated.setNom(request.getParameter("secteurNom"));
    Date currentDate = new Date(System.currentTimeMillis());
    secteurUpdated.setDateLastMaj(currentDate);
    secteurUpdated.setDescriptif(request.getParameter("secteurDescriptif"));
    secteurUpdated.setAcces(request.getParameter("secteurAcces"));
    // secteurUpdated.setPlan(secteur.getPlan()); //TODO Change setter

    // Récupère les IDs des voies du secteur de la BDD
    List<Integer> voiesIDsBDD = new ArrayList<>();
    for (Voie voie : secteur.getVoies()) {
      voiesIDsBDD.add(voie.getVoieID());
    }

    // Liste le numéro dans le formulaire de chaque voie
    List<Integer> voiesFormNum = new ArrayList<>();

    Enumeration<String> enumParameterNames = request.getParameterNames();
    while (enumParameterNames.hasMoreElements()) {
      String paramVoieID = enumParameterNames.nextElement();

      if (paramVoieID.matches("^voieID[0-9]+$")) {

        int currentVoieNum = Integer.decode((paramVoieID).substring(6));
        voiesFormNum.add(currentVoieNum);
      }
    }

    // Met à jour les voies dans secteurUpdated

    for (Integer voieFormNum : voiesFormNum) {

      // Nouvelle voie avec les infos du formulaire
      Voie voie = new Voie();
      voie.setVoieID(Integer.decode(request.getParameter("voieID" + voieFormNum)));
      voie.setNumero(request.getParameter("numVoie" + voieFormNum));
      voie.setCotation(request.getParameter("cotationVoie" + voieFormNum));
      voie.setNom(request.getParameter("nomVoie" + voieFormNum));
      voie.setHauteur(Integer.decode(request.getParameter("hauteurVoie" + voieFormNum)));
      voie.setDegaine(Integer.decode(request.getParameter("degaineVoie" + voieFormNum)));
      voie.setRemarque(request.getParameter("remarqueVoie" + voieFormNum));

      // Ajoute la voie au secteur mis à jour
      secteurUpdated.addVoie(voie);
    }

    // Met à jour l'objet secteurUpdated dans la BDD

    // Renvoit vers la page "secteur.jsp" correspondante

    try {
      response.sendRedirect("secteur?secteurID=" + secteurID.toString());

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> secteur.jsp", e);
    }
  }

}
