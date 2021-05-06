package org.ladle.webapp.servlet;

import java.io.IOException;
import java.sql.Date;
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
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class AjoutSecteur
 * Permet l'ajout d'un nouveau secteur dans la BDD.
 */
@SuppressWarnings("serial")
@WebServlet("/ajout-secteur")
public class AjoutSecteur extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AjoutSecteur.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  @EJB(name = "EditeSiteSecteurHandler")
  private EditeSiteSecteurHandler editeSiteSecteurHandler;

  /**
   * doGet inutilisé qui renvoit vers l'accueil
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Envoit vers la page d'accueil
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building index.jsp", e);
    }
  }

  /**
   * Gère l'ajout d'un nouveau secteur avec l'ID du site et le nom du secteur.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Récupère l'ID du site
    String siteID = request.getParameter("siteID");
    LOG.debug("siteID {}", siteID);

    // Vérifie que l'ID est de type integer
    if (!siteID.matches("^[1-9][0-9]*$")) {
      errorList.add("Le Site pour ajouter le secteur est introuvable !");
      request.setAttribute("errorList", errorList);
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to erreur.jsp", e);
      }
      return;
    }

    // Récupère depuis la BDD les informations du site
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Récupère le nom du secteur
    String secteurNom = request.getParameter("secteurNom");

    // Créer le nouveau secteur
    Secteur secteur = new Secteur();

    // Ajoute le site
    secteur.setSite(site);

    // Ajoute le nom
    secteur.setNom(secteurNom);

    // Ajoute la date de MAJ
    Date currentDate = new Date(System.currentTimeMillis());
    secteur.setDateLastMaj(currentDate);

    // Ajoute le secteur dans la BDD et récupère l'ID
    Integer secteurID = editeSiteSecteurHandler.persist(secteur);

    // Renvoit vers la page d'édition du secteur
    try {
      response.sendRedirect("edition-secteur?secteurID=" + secteurID.toString());

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> edition-secteur.jsp", e);
    }

  }

}
