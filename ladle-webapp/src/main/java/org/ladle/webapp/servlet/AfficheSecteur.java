package org.ladle.webapp.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class AfficheSecteur.
 * Implémente l'affichage d'un secteur.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/secteur")
public class AfficheSecteur extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AfficheSecteur.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  /**
   * Affiche le secteur qui est passé en paramètre avec son ID.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheSecteur] -> doGet()");

    // Récupère l'id du Secteur
    String secteurID = request.getParameter("secteurID");
    LOG.debug("secteurID {}", secteurID);

    // Récupère depuis la BDD les informations du secteur
    Secteur secteur = rechercheSiteSecteurHandler.getSecteurByID(secteurID);

    // Si le Secteur n'existe pas renvoit vers une page d'erreur
    if (secteur == null) {
      String errorMsg = "Le Secteur recherché est introuvable !";
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
      getServletContext().getRequestDispatcher("/WEB-INF/secteur.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building secteur.jsp", e);
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
