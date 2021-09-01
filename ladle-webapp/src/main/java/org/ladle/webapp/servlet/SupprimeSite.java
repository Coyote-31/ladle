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
import org.ladle.beans.jpa.Site;
import org.ladle.service.EditeSiteSecteurHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class SupprimeSite.
 * Permet la suppression d'un site.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/supprime-site")
public class SupprimeSite extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(SupprimeSite.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  @EJB(name = "EditeSiteSecteurHandler")
  private EditeSiteSecteurHandler editeSiteSecteurHandler;

  /**
   * Implémente la suppression d'un site.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [SupprimeSite] -> doPost()");

    // Récupère l'ID du site à supprimer
    String siteID = request.getParameter("siteID");
    LOG.debug("siteID {}", siteID);

    // Vérifie que l'ID est de type integer
    if (!siteID.matches("^[1-9][0-9]*$")) {

      // Renvoit vers une page d'erreur
      String errorMsg = "Le site à supprimer est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupère le site à supprimer depuis la BDD
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Supprime le site de la BDD
    editeSiteSecteurHandler.remove(site);

    // Renvoit vers l'accueil
    try {
      response.sendRedirect(".");

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error building index.jsp", e);
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
