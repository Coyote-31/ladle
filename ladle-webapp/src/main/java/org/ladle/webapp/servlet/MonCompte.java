package org.ladle.webapp.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.jpa.Utilisateur;
import org.ladle.service.TopoHandler;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class MonCompte
 */
@SuppressWarnings("serial")
@WebServlet("/mon-compte")
public class MonCompte extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(MonCompte.class);

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  @EJB(name = "TopoHandler")
  private TopoHandler topoHandler;

  /**
   * Envoit des données pour l'affichage de la page mon-compte
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [MonCompte] -> doGet()");

    // Récupération de l'utilisateur
    HttpSession session = request.getSession();
    Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateur");

    // Envoit de la liste de ses propres topos
    request.setAttribute("ownTopos", topoHandler.getOwnTopos(utilisateur));

    // Envoit de la liste des topos empruntés
    // TODO

    // Envoit de la liste des topos demandés
    request.setAttribute("demandeTopos", topoHandler.getDemandePretTopos(utilisateur));

    // Envoit vers la jsp
    try {
      getServletContext().getRequestDispatcher("/WEB-INF/mon-compte.jsp").forward(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error building mon-compte.jsp", e);
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

    LOG.debug("Servlet [MonCompte] -> doPost()");

    // Redirection depuis un post vers le doGet()
    try {
      doGet(request, response);
    } catch (ServletException | IOException e) {
      LOG.error("Error doGet()", e);
    }
  }

}
