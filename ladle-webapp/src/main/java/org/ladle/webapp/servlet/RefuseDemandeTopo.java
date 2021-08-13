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
 * Servlet implementation class RefuseDemandeTopo.
 * Gère le refus d'une demande de prêt.
 */
@SuppressWarnings("serial")
@WebServlet("/refuse-demande-topo")
public class RefuseDemandeTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(RefuseDemandeTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * Implémente le refus d'une demande de prêt.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

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
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Récupère le topo depuis la BDD
    Topo topo = topoHandler.getTopoByID(topoID);

    // Si le topo n'existe pas renvoit vers la page d'erreur
    if (topo == null) {
      LOG.error("Can't find topo id : {}", topoIDStr);
      String errorMsg = "Le topo est introuvable !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Si l'utilisateur n'est pas le propriétaire du topo
    // renvoit vers la page d'erreur
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    if (!topo.getUtilisateur().getUtilisateurID().equals(utilisateur.getUtilisateurID())) {
      LOG.error("Topo not own by user! User:{}, TopoID:{}", utilisateur.getPseudo(), topo.getTopoID());
      String errorMsg = "Vous devez posséder le topo pour annuler une demande de prêt !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // ----- Utilisateur demandeur -----

    // Récupération de l'utilisateur demandeur du topo
    String askingUserIDStr = request.getParameter("userID");

    // Conversion en Integer
    Integer askingUserID = null;
    try {
      askingUserID = Integer.decode(askingUserIDStr);

    } catch (NumberFormatException e) {
      LOG.error("Error decode askingUserIDStr : {}", askingUserIDStr, e);
      String errorMsg = "L'utilisateur est introuvable !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Récupère l'utilisateur depuis la BDD
    Utilisateur askingUser = topoHandler.getUserByID(askingUserID);

    // Si l'utilisateur n'existe pas renvoit vers la page d'erreur
    if (askingUser == null) {
      LOG.error("Can't find user ID : {}", askingUserID);
      String errorMsg = "L'utilisateur est introuvable !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Vérification que l'utilisateur demandeur est dans la liste de demande
    boolean isAskingUserInList = false;

    isAskingUserInList = topo.getDemandePretUtilisateurs().stream()
        .anyMatch(user -> user.getUtilisateurID().equals(askingUser.getUtilisateurID()));

    if (!isAskingUserInList) {
      LOG.error("Can't find userID : {} in demand list of topoID = {}", askingUserID, topo.getTopoID());
      String errorMsg = "L'utilisateur n'est pas dans la liste de demande pour ce topo !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Retire l'utilisateur demandeur de la liste de demande du topo
    topoHandler.refuseDemandTopo(topo, askingUser);

    // Renvoit vers la page Mon Compte
    try {
      response.sendRedirect("mon-compte");

    } catch (IOException | IllegalStateException e) {
      LOG.error("Error sendRedirect -> mon-compte.jsp", e);
    }
  }

  /**
   * doGet inutilisé
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [RefuseDemandeTopo] -> doPost()");

    // Redirection depuis un post vers le doGet()
    try {
      doGet(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error doGet()", e);
    }
  }

  void sendToErrorPage(
      String errorMsg,
      List<String> errorList,
      HttpServletRequest request,
      HttpServletResponse response) {

    errorList.clear();
    errorList.add(errorMsg);
    request.setAttribute("errorList", errorList);
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/erreur.jsp").forward(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error getRequestDispatcher to erreur.jsp", e);
    }
  }

}
