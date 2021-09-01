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
import org.ladle.beans.jpa.Secteur;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class SupprimeSecteur.
 * Permet la suppression d'un secteur.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/supprime-secteur")
public class SupprimeSecteur extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(SupprimeSecteur.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  @EJB(name = "EditeSiteSecteurHandler")
  private EditeSiteSecteurHandler editeSiteSecteurHandler;

  /**
   * Gère la suppression d'un secteur depuis son ID
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [SupprimeSecteur] -> doPost()");

    // Récupère l'ID du site contenant le secteur à supprimer
    String siteID = request.getParameter("siteID");
    LOG.debug("siteID {}", siteID);

    // Vérifie que l'ID est de type integer
    if (!siteID.matches("^[1-9][0-9]*$")) {

      // Renvoit vers une page d'erreur
      String errorMsg = "Le Site contenant le secteur à supprimer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupère l'ID du secteur à supprimer
    String secteurID = request.getParameter("secteurID");
    LOG.debug("secteurID {}", secteurID);

    // Vérifie que l'ID est de type integer
    if (!secteurID.matches("^[1-9][0-9]*$")) {

      // Renvoit vers une page d'erreur
      String errorMsg = "Le secteur à supprimer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupère le secteur à supprimer depuis la BDD
    Secteur secteur = rechercheSiteSecteurHandler.getSecteurByID(secteurID);

    // Supprime le secteur de la BDD
    editeSiteSecteurHandler.remove(secteur);

    // Renvoit vers la jsp d'affichage du site d'où provenait le secteur supprimé
    try {
      response.sendRedirect("site?siteID=" + siteID);

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
