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
import org.ladle.beans.jpa.Departement;
import org.ladle.beans.jpa.Region;
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
import org.ladle.beans.jpa.Ville;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class RechercheSiteSecteur.
 * Permet la recherche d'un site ou d'un secteur d'escalade.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/recherche-site-secteur")
public class RechercheSiteSecteur extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(RechercheSiteSecteur.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  /**
   * Affiche le formulaire de recherche.
   *
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
   * Permet l'affichage du resultat de la recherche à partir du formulaire.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [RechercheSiteSecteur] -> doPost()");

    // Création du nom des constantes pour renvoit des infos
    // déjà remplit du form vers la jsp
    final String SELECTED_REGION = "selectedRegion";
    final String SELECTED_DEPT = "selectedDepartement";
    final String INPUTED_CP = "inputedCodePostal";
    final String SELECTED_VILLE = "selectedVille";
    final String SELECTED_COTA_EQUAL = "selectedCotaEqual";
    final String SELECTED_COTA_NUM = "selectedCotaNum";
    final String SELECTED_COTA_CHAR = "selectedCotaChar";
    final String SELECTED_SECT_EQUAL = "selectedSectEqual";
    final String SELECTED_SECT_NUM = "selectedSectNum";
    final String SELECTED_OFFICIEL = "selectedOfficiel";
    final String INPUTED_VILLE = "inputedVille";

    // Création des constantes pour l'index de l'Objet de la liste de résultat
    final int INDEX_REGION = 0;
    final int INDEX_DEPARTEMENT = 1;
    final int INDEX_VILLE = 2;
    final int INDEX_SITE = 3;
    final int INDEX_SECTEUR = 4;

    // Récupération des données du post
    String selectedRegion = request.getParameter("inputGroupSelectRegion");
    String selectedDepartement = request.getParameter("inputGroupSelectDepartement");
    String inputedCodePostal = request.getParameter("inputCodePostal");
    if ("".equals(inputedCodePostal)) {
      inputedCodePostal = null;
    }

    // Gestion de la ville
    String inputedVille = request.getParameter("inputTextVille");
    request.setAttribute(INPUTED_VILLE, inputedVille);
    String selectedVille = request.getParameter("inputGroupSelectVille");

    // Variable de détection des changements sur le formulaire
    String formChangeOn = request.getParameter("formChangeOn");
    LOG.debug("getParam formChangeOn : {}", formChangeOn);

    // Génération de la liste de sélection des "Régions"
    request.setAttribute("regions", rechercheSiteSecteurHandler.getAllRegions());
    // Génération d'une liste de ville vide
    List<Ville> villes = null;

    // Gestion des champs du formulaire "cotation" :
    String selectedCotaEqual = request.getParameter("inputGroupSelectCotaEqual");
    request.setAttribute(SELECTED_COTA_EQUAL, selectedCotaEqual);
    LOG.debug("selectedCotaEqual : {}", selectedCotaEqual);
    String selectedCotaNum = request.getParameter("inputGroupSelectCotaNum");
    request.setAttribute(SELECTED_COTA_NUM, selectedCotaNum);
    LOG.debug("selectedCotaNum : {}", selectedCotaNum);
    String selectedCotaChar = request.getParameter("inputGroupSelectCotaChar");
    request.setAttribute(SELECTED_COTA_CHAR, selectedCotaChar);
    LOG.debug("selectedCotaChar : {}", selectedCotaChar);
    String selectedCotaNumChar = selectedCotaNum + selectedCotaChar;
    LOG.debug("selectedCotaNumChar : {}", selectedCotaNumChar);

    // Gestion des champs du formulaire "nombre de secteurs" :
    String selectedSectEqual = request.getParameter("inputGroupSelectSectEqual");
    request.setAttribute(SELECTED_SECT_EQUAL, selectedSectEqual);
    LOG.debug("selectedSectEqual : {}", selectedSectEqual);
    String selectedSectNum = request.getParameter("inputGroupSelectSectNum");
    request.setAttribute(SELECTED_SECT_NUM, selectedSectNum);
    LOG.debug("selectedSectNum : {}", selectedSectNum);

    // Gestion du champ du formulaire "officiel" :
    String selectedOfficiel = request.getParameter("inputGroupSelectOfficiel");
    request.setAttribute(SELECTED_OFFICIEL, selectedOfficiel);
    LOG.debug("selectedOfficiel : {}", selectedOfficiel);

    // Si changement de Région
    switch (formChangeOn) {
      case "region":

        // Renvoit de la Région sélectionnée à la jsp
        request.setAttribute(SELECTED_REGION, selectedRegion);

        // Gestion du cas de sélection : toutes les regions
        if ("all".equals(selectedRegion)) {
          request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());
          request.setAttribute(SELECTED_DEPT, selectedDepartement);

          // Gestion du cas de sélection : une seule région
        } else {
          List<Departement> departements = rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion);
          request.setAttribute("departements", departements);

          // Si le Dept sélectionné est contenu dans la liste de résultats
          if (departements.stream().anyMatch(d -> d.getDepartementCode().equals(selectedDepartement))) {
            request.setAttribute(SELECTED_DEPT, selectedDepartement);
            // Sinon reinitialise à 'all' la sélection du departement
          } else {
            request.setAttribute(SELECTED_DEPT, "all");
          }
        }
        break;

      // Si changement de Département
      case "departement":

        request.setAttribute(SELECTED_REGION, selectedRegion);
        request.setAttribute(SELECTED_DEPT, selectedDepartement);

        // Si la Région sélectionné est "tous" recharge la liste complète
        if ("all".equals(selectedRegion)) {
          request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());
          // Sinon récupère les departements de la region spécifique
        } else {
          request.setAttribute("departements", rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion));
        }
        break;

      // Si changement de code-postal
      case "code-postal":

        // Récupère la liste des villes avec ce CP
        villes = rechercheSiteSecteurHandler.getVillesByCP(inputedCodePostal);

        // Si le CP est valide et contient des villes
        if (!villes.isEmpty()) {

          // active is-valid
          request.setAttribute("cpIsValid", true);

          // Si le critère région était "all"
          if ("all".equals(selectedRegion)) {

            // Resélectionne le critère région "all"
            request.setAttribute(SELECTED_REGION, "all");

            // Récupère la liste complète des départements
            request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());

          } else {
            // Récupère le codeRegion de la 1ere ville de la liste
            String regionCode = villes.get(0).getDepartement().getRegion().getRegionCode();

            // Sélectionne la région correspondant au CP
            request.setAttribute(SELECTED_REGION, regionCode);

            // Récupère la liste des départements de la région
            request.setAttribute("departements", rechercheSiteSecteurHandler.getDepartementsByRegionCode(regionCode));
          }
          // Sélectionne le département correspondant au CP
          request.setAttribute(SELECTED_DEPT, villes.get(0).getDepartement().getDepartementCode());

          // Réaffiche le code postal
          request.setAttribute(INPUTED_CP, inputedCodePostal);

          // Si le CP est invalide
        } else {

          // active is-invalid
          request.setAttribute("cpIsValid", false);

          // Réaffiche le code postal
          request.setAttribute(INPUTED_CP, inputedCodePostal);

          // Resélectionne la région et le département précédent
          request.setAttribute(SELECTED_REGION, selectedRegion);
          request.setAttribute(SELECTED_DEPT, selectedDepartement);

          // Si la région sélectionné est "all"
          // recharge la liste complète des départements
          if ("all".equals(selectedRegion)) {
            request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());

            // Sinon récupère les departements de la region spécifique
          } else {
            request.setAttribute("departements",
                rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion));
          }
        }

        // Renvoit à la jsp la liste des villes
        request.setAttribute("villes", villes);

        break;

      // Si recherche de ville
      case "searchVille":

        // Recherche de la ville dans la bdd
        List<Ville> villesByNom = rechercheSiteSecteurHandler.getVillesByNom(inputedVille);
        LOG.debug("For InputedVille : {} -> {} results", inputedVille, villesByNom.size());

        // Si il n'y a pas de résultat
        if (villesByNom.isEmpty()) {
          // Renvoit l'erreur à la jsp
          request.setAttribute("villeNameError", true);

          // Sinon revoit les villes et focus le select de ville
        } else {
          request.setAttribute("villes", villesByNom);
          request.setAttribute("villeSelectHighlight", true);
        }

        // Renvoit les sélections et les listes region/departement
        request.setAttribute(SELECTED_REGION, selectedRegion);
        request.setAttribute(SELECTED_DEPT, selectedDepartement);

        // Si le critère région était "all"
        if ("all".equals(selectedRegion)) {
          // Récupère la liste complète des départements
          request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());

          // Sinon récupère la liste des départements de la région sélectionnée
        } else {
          request.setAttribute("departements",
              rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion));
        }

        break;

      // Si sélection d'une ville dans la liste
      case "selectVille":

        // Si selectedVille est "all"
        if ("all".equals(selectedVille)) {
          // Sélection des critères précédents (region/departement/cp)
          request.setAttribute(SELECTED_REGION, selectedRegion);
          request.setAttribute("departements",
              rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion));
          request.setAttribute(SELECTED_DEPT, selectedDepartement);
          request.setAttribute(INPUTED_CP, inputedCodePostal);
          request.setAttribute(SELECTED_VILLE, null);

          // Renvoit de la liste de villes :
          // Si le champs inputTextVille n'est pas vide recherche depuis le nom recherché
          if (!inputedVille.isEmpty()) {
            request.setAttribute("villes", rechercheSiteSecteurHandler.getVillesByNom(inputedVille));
            LOG.debug("List villes load from getVillesByNom({})", inputedVille);

            // Sinon fait la recherche depuis le code postal
          } else {
            request.setAttribute("villes", rechercheSiteSecteurHandler.getVillesByCP(inputedCodePostal));
            LOG.debug("List villes load from getVillesByCP({})", inputedCodePostal);
          }

          // Sinon c'est que selectedVille est un id de ville
        } else {
          Ville villeByID;
          villeByID = rechercheSiteSecteurHandler.getVilleByID(selectedVille);
          // Sélection automatique des critères précédents (region/departement/cp)
          request.setAttribute(SELECTED_REGION, villeByID.getDepartement().getRegion().getRegionCode());
          request.setAttribute("departements",
              rechercheSiteSecteurHandler
                  .getDepartementsByRegionCode(villeByID.getDepartement().getRegion().getRegionCode()));
          request.setAttribute(SELECTED_DEPT, villeByID.getDepartement().getDepartementCode());
          request.setAttribute(INPUTED_CP, villeByID.getCp());
          request.setAttribute(SELECTED_VILLE, selectedVille);

          // Renvoit de la liste de villes :
          // Si le champs inputTextVille n'est pas vide recherche depuis le nom recherché
          if (!inputedVille.isEmpty()) {
            request.setAttribute("villes", rechercheSiteSecteurHandler.getVillesByNom(inputedVille));
            LOG.debug("List villes load from getVillesByNom({})", inputedVille);

            // Sinon fait la recherche depuis le code postal de la ville sélectionnée
          } else {
            request.setAttribute("villes", rechercheSiteSecteurHandler.getVillesByCP(villeByID.getCp()));
            LOG.debug("List villes load from getVillesByCP({})", villeByID.getCp());
          }

        }

        break;

      // Si pas de changement c'est un post du formulaire complet
      default:

        // Recharge les listes de critères :

        // Si la région sélectionné est "all"
        // recharge la liste complète des départements
        if ("all".equals(selectedRegion)) {
          request.setAttribute("departements", rechercheSiteSecteurHandler.getAllDepartements());

          // Sinon récupère les departements de la region spécifique
        } else {
          request.setAttribute("departements",
              rechercheSiteSecteurHandler.getDepartementsByRegionCode(selectedRegion));
        }
        // Si le CP est valide récupère la liste des villes avec le CP
        // et renvoit à la jsp la liste des villes
        if (inputedCodePostal != null) {
          villes = rechercheSiteSecteurHandler.getVillesByCP(inputedCodePostal);
          request.setAttribute("villes", villes);

          // Si le CP est valide et contient des villes
          // boolean is-valid
          request.setAttribute("cpIsValid", !villes.isEmpty());
        }

        // recharge les critères de recherches
        request.setAttribute(SELECTED_REGION, selectedRegion);
        request.setAttribute(SELECTED_DEPT, selectedDepartement);
        request.setAttribute(INPUTED_CP, inputedCodePostal);
        if ("all".equals(selectedVille)) {
          selectedVille = null;
        }
        request.setAttribute(SELECTED_VILLE, selectedVille);

        //// Récupération et mise en forme du résultat pour l'envoit à la JSP

        // Récupère la liste des secteurs trouvés (row sql)
        List<Object[]> searchResults = rechercheSiteSecteurHandler.searchByForm(
            selectedRegion,
            selectedDepartement,
            inputedCodePostal,
            selectedVille,
            selectedCotaEqual,
            selectedCotaNumChar,
            selectedSectEqual,
            selectedSectNum,
            selectedOfficiel);

        // Test si le résultat est vide
        if (searchResults.isEmpty()) {
          request.setAttribute("searchResultEmpty", true);
        }

        // Création et envoit de la liste des différentes régions
        List<Region> searchResultRegions = new ArrayList<>();

        for (Object[] searchResult : searchResults) {
          // Si la région du résultat n'est pas encore présente la rajoute à la liste
          if (!searchResultRegions.contains(searchResult[INDEX_REGION])) {
            searchResultRegions.add((Region) searchResult[INDEX_REGION]);
          }
        }
        LOG.debug("searchResultRegions.size() : {}", searchResultRegions.size());
        request.setAttribute("searchResultRegions", searchResultRegions);

        // Création de la liste des différents départements
        List<Departement> searchResultDepartements = new ArrayList<>();

        for (Object[] searchResult : searchResults) {
          // Si le département du résultat n'est pas encore présent le rajoute à la liste
          if (!searchResultDepartements.contains(searchResult[INDEX_DEPARTEMENT])) {
            searchResultDepartements.add((Departement) searchResult[INDEX_DEPARTEMENT]);
          }
        }
        LOG.debug("searchResultDepartements.size() : {}", searchResultDepartements.size());
        request.setAttribute("searchResultDepartements", searchResultDepartements);

        // Création de la liste des différents villes
        List<Ville> searchResultVilles = new ArrayList<>();

        for (Object[] searchResult : searchResults) {
          // Si le site du résultat n'est pas encore présent le rajoute à la liste
          if (!searchResultVilles.contains(searchResult[INDEX_VILLE])) {
            searchResultVilles.add((Ville) searchResult[INDEX_VILLE]);
          }
        }
        LOG.debug("searchResultVilles.size() : {}", searchResultVilles.size());
        request.setAttribute("searchResultVilles", searchResultVilles);

        // Création de la liste des différents sites
        List<Site> searchResultSites = new ArrayList<>();

        for (Object[] searchResult : searchResults) {
          // Si le site du résultat n'est pas encore présent le rajoute à la liste
          if (!searchResultSites.contains(searchResult[INDEX_SITE])) {
            searchResultSites.add((Site) searchResult[INDEX_SITE]);
          }
        }
        LOG.debug("searchResultSites.size() : {}", searchResultSites.size());
        request.setAttribute("searchResultSites", searchResultSites);

        // Création de la liste des différents secteurs
        List<Secteur> searchResultSecteurs = new ArrayList<>();

        for (Object[] searchResult : searchResults) {
          // Si le site du résultat n'est pas encore présent le rajoute à la liste
          if (!searchResultSecteurs.contains(searchResult[INDEX_SECTEUR])) {
            searchResultSecteurs.add((Secteur) searchResult[INDEX_SECTEUR]);
          }
        }
        LOG.debug("searchResultSecteurs.size() : {}", searchResultSecteurs.size());
        request.setAttribute("searchResultSecteurs", searchResultSecteurs);
    }

    getServletContext().getRequestDispatcher("/WEB-INF/recherche-site-secteur.jsp").forward(request, response);
  }

}
