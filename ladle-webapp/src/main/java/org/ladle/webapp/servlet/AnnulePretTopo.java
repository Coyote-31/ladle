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
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Topo;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.TopoHandler;

/**
 * Servlet implementation class AnnulePretTopo.
 * Permet d'annuler le prêt d'un topo.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/annule-pret-topo")
public class AnnulePretTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AnnulePretTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * Implémente l'annulation du prêt d'un topo.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AnnulePretTopo] -> doGet()");

    // ----- Topo -----

    // Récupération du paramètre 'topoID'
    String topoIDStr = request.getParameter("topoID");

    if ((topoIDStr == null) || topoIDStr.isEmpty()) {
      LOG.error("Error null/empty topoIDStr");
      String errorMsg = "Le topo est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Conversion de topoID en Integer
    Integer topoID = null;

    try {
      topoID = Integer.decode(topoIDStr);

    } catch (NumberFormatException e) {
      LOG.error("Error decode topoIDStr : {}", topoIDStr, e);
      String errorMsg = "Le topo est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Récupère le topo depuis la BDD
    Topo topo = topoHandler.getTopoByID(topoID);

    // Si le topo n'existe pas renvoit vers la page d'erreur
    if (topo == null) {
      LOG.error("Can't find topo id : {}", topoIDStr);
      String errorMsg = "Le topo est introuvable !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // ----- Utilisateur -----

    // Récupération de l'utilisateur demandeur d'annulation du pret
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    // ----- Vérification -----

    // Si l'utilisateur n'est ni le propriètaire ni le bénéficiaire du prêt,
    // renvoit vers une page d'erreur.
    if (!topo.getUtilisateur().getUtilisateurID().equals(utilisateur.getUtilisateurID())
        && !topo.getPretUtilisateur().getUtilisateurID().equals(utilisateur.getUtilisateurID())) {

      LOG.error("User doesn't allowed to cancel loan. TopoID: {}, UserID: {}",
          topoIDStr,
          utilisateur.getUtilisateurID());
      String errorMsg = "Vous pouvez annuler le prêt d'un topo que si il vous appartient "
                        + "ou si on vous le prête personnellement !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // ----- Mise à jour du topo -----

    // Supprime l'utilisateur en cours de prêt dans le topo
    // Et change le statut du topo en disponible.
    topoHandler.cancelPretTopo(topo);

    // Renvoit vers la page Mon Compte
    try {
      response.sendRedirect("mon-compte");

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> mon-compte.jsp", e);
    }
  }

  /**
   * doPost inutilisé qui renvoit vers le doGet.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AnnulePretTopo] -> doPost()");
    // Redirection depuis un post vers le doGet()
    try {
      doGet(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error doGet()", e);
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
