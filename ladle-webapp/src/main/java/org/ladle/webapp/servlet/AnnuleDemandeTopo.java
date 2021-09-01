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
 * Servlet implementation class AnnuleDemandeTopo.
 * Permet l'annulation d'une demande de topo.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/annule-demande-topo")
public class AnnuleDemandeTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(AnnuleDemandeTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * Implémente l'annulation d'une demande de topo.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [AnnuleDemandeTopo] -> doGet()");

    // ----- Topo -----

    // Récupération du paramètre 'topoID'
    String topoIDStr = request.getParameter("topoID");

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

    // ----- Utilisateur demandeur d'annulation -----

    // Récupération de l'utilisateur demandeur d'annulation du topo
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    // Vérification que l'utilisateur demandeur est dans la liste de demande
    boolean isAskingUserInList = false;

    isAskingUserInList = topo.getDemandePretUtilisateurs().stream()
        .anyMatch(user -> user.getUtilisateurID().equals(utilisateur.getUtilisateurID()));

    if (!isAskingUserInList) {
      LOG.error("Can't find userID : {} in demand list of topoID = {}",
          utilisateur.getUtilisateurID(),
          topo.getTopoID());

      String errorMsg = "Vous n'êtes pas dans la liste de demande pour ce topo !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Retire l'utilisateur demandeur de la liste de demande du topo
    topoHandler.refuseDemandTopo(topo, utilisateur);

    // Renvoit vers la page Mon Compte
    try {
      response.sendRedirect("mon-compte");

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> mon-compte.jsp", e);
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
