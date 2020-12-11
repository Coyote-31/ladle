package org.ladle.webapp.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

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
 * Servlet implementation class AfficheSecteur
 */
@SuppressWarnings("serial")
@WebServlet("/secteur")
public class AfficheSecteur extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AfficheSecteur.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  /**
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
      getServletContext().getRequestDispatcher("/WEB-INF/secteur.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building secteur.jsp", e);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheSecteur] -> doPost()");

    try {
      doGet(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("doGet() failed", e);
    }
  }

}
