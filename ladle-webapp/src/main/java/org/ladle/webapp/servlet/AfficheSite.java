package org.ladle.webapp.servlet;

import java.io.IOException;
import java.sql.Timestamp;
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
 * Servlet implementation class AfficheSite.
 * Implémente l'affichage d'un site,
 * avec ses commentaires.
 *
 * @author Coyote
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
   * Envoit les informations du site passé en paramètre,
   * ainsi que ses commentaires.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AfficheSite] -> doGet()");

    // Récupère l'id du Site
    String siteID = request.getParameter("siteID");
    LOG.debug("doGet() siteID {}", siteID);

    // Récupère depuis la BDD les informations du site
    Site site = rechercheSiteSecteurHandler.getSiteByID(siteID);

    // Si le Site n'existe pas renvoit vers une page d'erreur
    if (site == null) {
      String errorMsg = "Le Site recherché est introuvable !";
      sendToErrorPage(errorMsg, request, response);
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
      return;
    }

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/site.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building site.jsp", e);
    }
  }

  /**
   * Gère les commentaire sur le site passé en paramètre.
   *
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
      String errorMsg = "Le Site est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupération de l'utilisateur
    HttpSession session = request.getSession(true);
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    // Si l'utilisateur n'est pas connecté renvoit vers une page d'erreur
    if (utilisateur == null) {
      String errorMsg = "Vous devez être connecté pour laisser un commentaire !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    /**
     * Cas supprime un commentaire
     */

    String commentaireID = request.getParameter("commentaireID");
    String deleteBtn = request.getParameter("delete-btn");

    if ((deleteBtn != null) && (commentaireID != null) && !commentaireID.isEmpty()) {
      LOG.debug("Suppression du commentaire ID : {}", commentaireID);

      // Si l'utilisateur n'est pas membre ou admin renvoit vers une page d'erreur
      if (utilisateur.getRole() < 1) {
        String errorMsg = "Seul un membre de l'association ou un administrateur peut supprimer un commentaire !";
        sendToErrorPage(errorMsg, request, response);
        return;
      }

      // Suppression du commentaire depuis son ID
      try {
        commentaireHandler.removeCommentaireByID(Integer.decode(commentaireID));
      } catch (NumberFormatException e) {
        LOG.error("NumberFormatException Error ! With commentaireID = {}", commentaireID, e);
        return;
      }

      try {
        response.sendRedirect("site?siteID=" + siteID.toString());
        return;

      } catch (IOException | IllegalStateException e) {
        LOG.error("Error sendRedirect -> site.jsp", e);
        return;
      }
    }

    /**
     * Cas modification d'un commentaire
     */

    // Récupère la valeur du boutton name="submit-update-btn"
    String submitUpdateBtn = request.getParameter("submit-update-btn");

    // Si c'est bien le boutton de modification de commentaire qui est envoyé
    if ("update".equals(submitUpdateBtn)) {

      // Vérification des droits utilisateur
      // Si non autorisé renvoit vers une page d'erreur
      if (utilisateur.getRole() < 1) {
        String errorMsg = "Seul un membre de l'association ou un administrateur peut modifier un commentaire !";
        sendToErrorPage(errorMsg, request, response);
        return;
      }

      // Récupération de l'ID du commentaire à modifier
      String updatedCommentaireIDStr = request.getParameter("commentaireID");
      Integer updatedCommentaireID;
      LOG.debug("Updating commentaire ID : {}", updatedCommentaireIDStr);
      try {
        updatedCommentaireID = Integer.decode(updatedCommentaireIDStr);
      } catch (NumberFormatException e) {
        LOG.error("NumberFormatException Error ! With commentaireID = {}", updatedCommentaireIDStr, e);
        return;
      }

      // Récupération de l'input Textarea du commentaire à modifier
      String updatedCommentaireTextarea = request.getParameter("updateFormCommentTextarea");

      // Test de la validité de la modification du commentaire :
      // Initialisation de la liste d'erreurs
      List<String> errorListUpdatedCommentaire = new ArrayList<>();

      // Si le commentaire est vide
      if ((updatedCommentaireTextarea.length() <= 0)
          || updatedCommentaireTextarea.matches("^\\s+$")) {
        errorListUpdatedCommentaire.add("Un commentaire ne peut pas être vide !");
      }
      // Si le commentaire excède 2000 charactères
      if (updatedCommentaireTextarea.length() > 2000) {
        errorListUpdatedCommentaire.add("Un commentaire ne peut excèder 2000 caractères !");
      }

      // Si pas d'erreur met à jour le commentaire dans la BDD
      if (errorListUpdatedCommentaire.size() == 0) {

        // Récupération du commentaire à modifier depuis la BDD
        Commentaire updatedCommentaire = commentaireHandler.getCommentaireByID(updatedCommentaireID);

        // Modification du contenu du commentaire
        updatedCommentaire.setContenu(updatedCommentaireTextarea);

        // Mise à jour dans la BDD du commentaire modifié
        commentaireHandler.updateCommentaire(updatedCommentaire);

        // Si la modification est valide redirection vers le site
        try {
          response.sendRedirect("site?siteID=" + siteID.toString());
          return;

        } catch (IOException | IllegalStateException e) {
          LOG.error("Error sendRedirect -> site.jsp", e);
          return;
        }

        // Si il y a une erreur
      } else {
        // Envoit de l'ID du commentaire en cours de modification
        request.setAttribute("updatedCommentaireID", updatedCommentaireID);
        // Envoit de la liste des erreurs du commentaire à modifier
        request.setAttribute("errorListUpdatedCommentaire", errorListUpdatedCommentaire);
        // Renvoit du champs input Textarea du commentaire à modifier
        request.setAttribute("updatedCommentaireTextarea", updatedCommentaireTextarea);

        try {
          doGet(request, response);
          return;

        } catch (ServletException | IOException e) {
          LOG.error("doGet() failed", e);
          return;
        }
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
    if ((commentaire.getContenu().length() <= 0) || commentaire.getContenu().matches("^\\s+$")) {
      errorListCommentaire.add("Un commentaire ne peut pas être vide !");
    }
    // Si le commentaire excède 2000 charactères
    if (commentaire.getContenu().length() > 2000) {
      errorListCommentaire.add("Un commentaire ne peut excèder 2000 caractères !");
    }

    // Si pas d'erreur persiste le commentaire dans la BDD
    if (errorListCommentaire.size() == 0) {

      // Date
      Timestamp currentDate = new Timestamp(System.currentTimeMillis());
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

    // Si le commentaire est valide redirection vers le site
    try {
      response.sendRedirect("site?siteID=" + siteID.toString());

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
