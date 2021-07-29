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
 * Servlet implementation class SupprimeTopo
 * Permet la suppression d'un topo que l'utilisateur possède.
 */
@SuppressWarnings("serial")
@WebServlet("/supprime-topo")
public class SupprimeTopo extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(SupprimeTopo.class);

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // Initialisation de la liste d'erreurs
    List<String> errorList = new ArrayList<>();

    // Récupère l'id du topo
    String topoIDStr = request.getParameter("id");
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
      LOG.error("Ask for his own topo! User : {}", utilisateur.getPseudo());
      String errorMsg = "Vous ne pouvez pas supprimer un topo qui ne vous appartient pas !";
      sendToErrorPage(errorMsg, errorList, request, response);
      return;
    }

    // Supprime le topo de la BDD
    topoHandler.removeTopo(topo);

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

    // Renvoit vers le doGet
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
