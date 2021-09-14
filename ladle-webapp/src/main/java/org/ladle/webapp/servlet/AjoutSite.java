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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Site;
import org.ladle.beans.jpa.Ville;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class AjoutSite.
 * Permet l'ajout d'un nouveau site.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/ajout-site")
public class AjoutSite extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AjoutSite.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  @EJB(name = "EditeSiteSecteurHandler")
  private EditeSiteSecteurHandler editeSiteSecteurHandler;

  /**
   * doGet d'affichage du formulaire de création du site
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AjoutSite] -> doGet()");

    // Envoit vers la page d'ajout d'un nouveau site
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/ajout-site.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building ajout-site.jsp", e);
    }
  }

  /**
   * doPost de gestion du formulaire de création du site
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AjoutSite] -> doPost()");

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Création du nom des constantes pour renvoit des infos
    // déjà remplit du form vers la jsp
    final String INPUTED_VILLE = "inputedVille";
    final String SELECTED_VILLE = "selectedVille";
    final String INPUTED_SITE_NAME = "inputedSiteName";

    // Récupération des inputs du formulaire
    String inputedVille = request.getParameter("inputTextVille");
    String selectedVille = request.getParameter("inputGroupSelectVille");
    String inputedSiteName = request.getParameter("siteNom");

    // ---------------------
    // BTN : Recherche de la ville
    // ---------------------
    if (request.getParameter("btn-searchVille") != null) {

      // Création de la liste de ville empty
      List<Ville> villesByNom = new ArrayList<>();

      // Recherche de la ville dans la bdd si il n'est pas null
      if ((inputedVille != null) && !inputedVille.isEmpty()) {
        villesByNom = rechercheSiteSecteurHandler.getVillesByNom(inputedVille);
        LOG.debug("For InputedVille : {} -> {} results", inputedVille, villesByNom.size());
      }

      // Si il n'y a pas de résultat
      if (villesByNom.isEmpty()) {
        // Renvoit l'erreur à la jsp
        request.setAttribute("villeNameError", true);
        errorList.add("Aucune ville ne correspond à la recherche !");

        // Sinon revoit les villes et focus le select de ville
      } else {
        request.setAttribute("villes", villesByNom);
        request.setAttribute("villeSelectHighlight", true);
      }

      // Renvoit les infos vers le formulaire
      request.setAttribute(INPUTED_VILLE, inputedVille);
      request.setAttribute(SELECTED_VILLE, selectedVille);
      request.setAttribute(INPUTED_SITE_NAME, inputedSiteName);
      request.setAttribute("errorList", errorList);

      // Renvoit vers la page du formulaire
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/ajout-site.jsp").forward(request, response);

      } catch (ServletException | IOException e) {
        LOG.error("Error building ajout-site.jsp", e);
      }

      // ------------------------
      // BTN : Création du nouveau site
      // ------------------------
    } else if (request.getParameter("btn-submit") != null) {

      // Création de la liste de ville empty
      List<Ville> villesByNom = new ArrayList<>();

      // Recherche de la ville dans la bdd si il n'est pas null
      if ((inputedVille != null) && !inputedVille.isEmpty()) {
        villesByNom = rechercheSiteSecteurHandler.getVillesByNom(inputedVille);
        LOG.debug("For InputedVille : {} -> {} results", inputedVille, villesByNom.size());
      }

      // Envoit de la liste des villes
      request.setAttribute("villes", villesByNom);

      // Récupération de la ville
      Ville ville = rechercheSiteSecteurHandler.getVilleByID(selectedVille);

      // Si la ville est introuvable
      if (ville == null) {

        // Création du message d'erreur
        request.setAttribute("villeSelectError", true);
        errorList.add("La ville est introuvable ! Sélectionnez une ville valide.");
        request.setAttribute("errorList", errorList);

        // Renvoit les infos vers le formulaire
        request.setAttribute(INPUTED_VILLE, inputedVille);
        request.setAttribute(SELECTED_VILLE, selectedVille);
        request.setAttribute(INPUTED_SITE_NAME, inputedSiteName);

        // Renvoit vers la page du formulaire
        try {
          getServletContext().getRequestDispatcher("/WEB-INF/ajout-site.jsp").forward(request, response);
          return;

        } catch (ServletException | IOException e) {
          LOG.error("Error building ajout-site.jsp", e);
        }
      }

      // Création et vérification des champs du site
      Site site = new Site();
      // Ville
      site.setVille(ville);
      // Date
      Timestamp currentDate = new Timestamp(System.currentTimeMillis());
      site.setDateLastMaj(currentDate);
      // Non-officiel par defaut
      site.setOfficiel(false);
      // Nom ( de 1 à 80 chars max)
      if (!inputedSiteName.isEmpty() && (inputedSiteName.length() <= 80)) {
        site.setNom(inputedSiteName);

        // Si le nom du site est null ou > 80 charactères : renvoit l'erreur
      } else {

        // Création du message d'erreur
        request.setAttribute("siteNameError", true);
        errorList.add("Le nom du site est requis et doit être de 80 caratères maximum !");
        request.setAttribute("errorList", errorList);

        // Renvoit les infos vers le formulaire
        request.setAttribute(INPUTED_VILLE, inputedVille);
        request.setAttribute(SELECTED_VILLE, selectedVille);
        request.setAttribute(INPUTED_SITE_NAME, inputedSiteName);

        // Renvoit vers la page du formulaire
        try {
          getServletContext().getRequestDispatcher("/WEB-INF/ajout-site.jsp").forward(request, response);
          return;

        } catch (ServletException | IOException e) {
          LOG.error("Error building ajout-site.jsp", e);
        }
      }

      // Persistance du site dans la BDD
      Integer siteID = editeSiteSecteurHandler.persist(site);

      // Renvoit vers l'affichage du site
      try {
        response.sendRedirect("site?siteID=" + siteID.toString());

      } catch (IOException | IllegalStateException e) {
        LOG.error("Error sendRedirect -> site.jsp", e);
      }

    }

  }

}
