package org.ladle.webapp.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Commentaire;
import org.ladle.beans.jpa.Site;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.CommentaireHandler;
import org.ladle.service.RechercheSiteSecteurHandler;

/**
 * Servlet implementation class AfficheSite
 */
@SuppressWarnings("serial")
@WebServlet("/site")
public class AfficheSite extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AfficheSite.class);

  @EJB(name = "RechercheSiteSecteurHandler")
  private RechercheSiteSecteurHandler rechercheSiteSecteurHandler;

  @EJB(name = "CommentaireHandler")
  private CommentaireHandler commentaireHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheSite] -> doGet()");

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Récupère l'id du Site
    String siteID = request.getParameter("siteID");
    LOG.debug("doGet() siteID {}", siteID);

    // Récupère depuis la BDD les informations du site
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Si le Site n'existe pas renvoit vers une page d'erreur
    if (site == null) {
      errorList.add("Le Site recherché est introuvable !");
      request.setAttribute("errorList", errorList);
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to erreur.jsp", e);
      }
      return;
    }

    // Envoit le site à la jsp
    request.setAttribute("site", site);

    // Récupère les id des secteurs non-filtrés
    String[] secteursID = request.getParameterValues("secteursID");
    String debugStringSecteursID = Arrays.toString(secteursID);
    LOG.debug("secteursID : {}", debugStringSecteursID);
    List<String> listSecteursID = new ArrayList<>();

    // Créé la liste à partir du resultat de la recherche
    if ((secteursID != null) && !debugStringSecteursID.isEmpty()) {
      for (String secteurID : secteursID) {
        if (!secteurID.isEmpty()) {
          listSecteursID.add(secteurID);
        }
      }

      LOG.debug("listSecteursID : {}", listSecteursID);
      request.setAttribute("listFilterSecteursID", listSecteursID);
    }

    // Récupère les commentaires du site et envoit à la jsp
    try {
      request.setAttribute("commentaires", commentaireHandler.getCommentairesBySiteID(Integer.decode(siteID)));
    } catch (NumberFormatException e) {
      LOG.error("NumberFormatException Error ! With siteID = {}", siteID, e);
    }

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/site.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building site.jsp", e);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheSite] -> doPost()");

    // Récupération de l'ID du site
    String siteID = request.getParameter("siteID");
    LOG.debug("doPost() siteID {}", siteID);

    // Récupère le site depuis la BDD
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Si le Site n'existe pas renvoit vers une page d'erreur
    if (site == null) {
      // Initialisation de la liste d'erreurs
      List<String> errorList = new ArrayList<>();
      errorList.add("Le Site est introuvable !");
      request.setAttribute("errorList", errorList);
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to erreur.jsp", e);
      }
      return;
    }

    // Récupération de l'utilisateur
    HttpSession session = request.getSession(true);
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    // Si l'utilisateur n'est pas connecté renvoit vers une page d'erreur
    if (utilisateur == null) {
      // Initialisation de la liste d'erreurs
      List<String> errorList = new ArrayList<>();
      errorList.add("Vous devez être connecté pour laisser un commentaire !");
      request.setAttribute("errorList", errorList);
      try {
        getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
      } catch (ServletException | IOException e) {
        LOG.error("Error getRequestDispatcher to erreur.jsp", e);
      }
      return;
    }

    /**
     * Cas supprime un commentaire
     */

    String commentaireID = request.getParameter("commentaireID");

    if ((commentaireID != null) && !commentaireID.isEmpty()) {
      LOG.debug("Supprime le commentaire ID : {}", commentaireID);

      try {
        doGet(request, response);
        return;

      } catch (ServletException | IOException e) {
        LOG.error("doGet() failed", e);
        return;
      }
    }

    /**
     * Cas ajout d'un commentaire
     */

    // Récupération du commentaire
    Commentaire commentaire = new Commentaire();
    commentaire.setContenu(request.getParameter("commentFormText"));

    // Initialisation de la liste d'erreurs
    List<String> errorListCommentaire = new ArrayList<>();

    // Test de la validité du commentaire
    // Si le commentaire est vide
    if ((commentaire.getContenu().length() <= 0) || commentaire.getContenu().matches("^\s+$")) {
      errorListCommentaire.add("Un commentaire ne peut pas être vide !");
    }
    // Si le commentaire excède 2000 charactères
    if (commentaire.getContenu().length() > 2000) {
      errorListCommentaire.add("Un commentaire ne peut excèder 2000 caractères !");
    }

    // Si pas d'erreur persiste le commentaire dans la BDD
    if (errorListCommentaire.size() == 0) {

      // Date
      Date currentDate = new Date(System.currentTimeMillis());
      commentaire.setDateCreation(currentDate);

      // Utilisateur
      commentaire.setUtilisateur(utilisateur);

      // Site
      commentaire.setSite(site);

      // Persistance dans la BDD
      commentaireHandler.persistCommentaire(commentaire);
      LOG.debug("Comment to persist = length:{} userID:{} siteID:{} error:{}",
          commentaire.getContenu().length(),
          commentaire.getUtilisateur().getUtilisateurID(),
          commentaire.getSite().getSiteID(),
          errorListCommentaire.size());

      // Si il y a une erreur
    } else {
      // Envoit de la liste des erreurs de commentaire
      request.setAttribute("errorListCommentaire", errorListCommentaire);
      // Renvoit du champs inputText
      request.setAttribute("inputedCommentaire", commentaire.getContenu());

      try {
        doGet(request, response);
        return;

      } catch (ServletException | IOException e) {
        LOG.error("doGet() failed", e);
        return;
      }
    }

    try {
      // doGet(request, response);
      response.sendRedirect("site?siteID=" + siteID.toString());

      // } catch (ServletException | IOException e) {
    } catch (IOException | IllegalStateException e) {
      // LOG.error("doGet() failed", e);
      LOG.error("Error sendRedirect -> site.jsp", e);
    }
  }

}
