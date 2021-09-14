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
import org.ladle.beans.jpa.Secteur;
import org.ladle.beans.jpa.Site;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class AjoutSecteur
 * Permet l'ajout d'un nouveau secteur dans la BDD.
 *
 * @author Coyote
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
   * Gère l'ajout d'un nouveau secteur avec l'ID du site et le nom du secteur.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AjoutSecteur] -> doPost()");

    // Récupère l'ID du site
    String siteID = request.getParameter("siteID");
    LOG.debug("siteID {}", siteID);

    // Vérifie que l'ID est de type integer
    if (!siteID.matches("^[1-9][0-9]*$")) {
      String errorMsg = "Le Site pour ajouter le secteur est introuvable !";
      sendToErrorPage(errorMsg, request, response);
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
    Timestamp currentDate = new Timestamp(System.currentTimeMillis());
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
