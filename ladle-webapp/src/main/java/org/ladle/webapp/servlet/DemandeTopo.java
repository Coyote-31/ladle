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
 * Servlet implementation class DemandeTopo.
 * Permet d'effectuer une demande de prêt en ajoutant l'utilisateur
 * à la liste de demande pour le topo avec son id en paramètre.
 *
 * @author Coyote
 */
@SuppressWarnings("serial")
@WebServlet("/demande-topo")
public class DemandeTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(DemandeTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * Ajoute l'utilisateur à la liste des demandes pour le topo passé en paramètre.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [DemandeTopo] -> doGet()");

    // Récupère l'id du topo
    String topoIDStr = request.getParameter("id");
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

    // Si l'utilisateur est le propriétaire du topo renvoit vers la page d'erreur
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    if (topo.getUtilisateur().getUtilisateurID().equals(utilisateur.getUtilisateurID())) {
      LOG.error("Ask for his own topo! User : {}", utilisateur.getPseudo());
      String errorMsg = "Vous ne pouvez pas faire de demande pour vos propres topos !";
      sendToErrorPage(errorMsg, request, response);
      return;
    }

    // Ajoute l'utilisateur à la liste des demandeurs pour ce topo dans la BDD
    topoHandler.addDemandePret(topo, utilisateur);

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
