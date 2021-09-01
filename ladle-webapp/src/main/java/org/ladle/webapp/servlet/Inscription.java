package org.ladle.webapp.servlet;

import java.io.IOException;
import java.sql.SQLDataException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;
import org.ladle.service.UserHandler;

/**
 * Servlet implementation class Inscription.
 * Permet l'inscription d'un utilisateur.
 */
@SuppressWarnings("serial")
@WebServlet("/inscription")
public class Inscription extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(Inscription.class);

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  /**
   * Envoit vers l'affichage du formulaire d'inscription.
   *
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [Inscription] -> doGet()");

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("inscription.jsp loading failed", e);
    }
  }

  /**
   * Gère le formulaire pour l'inscription.
   *
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    /* Récupération des éléments du formulaire d'inscription */
    LOG.debug("Servlet [Inscription] -> doPost()");

    User user = new User();

    user.setPseudo(request.getParameter("pseudo"));
    user.setGenre(request.getParameter("genre"));
    user.setPrenom(request.getParameter("prenom"));
    user.setNom(request.getParameter("nom"));
    user.setEmail(request.getParameter("email"));
    user.setCp(request.getParameter("cp"));
    // Gère le cas de l'ID ville (String -> Integer)
    Integer decodedVilleID = 0;
    String stringVilleID = request.getParameter("ville");
    LOG.debug("stringVilleID = {}", stringVilleID);
    if (stringVilleID != null) {
      try {
        decodedVilleID = Integer.decode(stringVilleID);
      } catch (NumberFormatException eDecodeVilleID) {
        LOG.error("Failed to decode {}, to integer", request.getParameter("ville"));
        LOG.trace(eDecodeVilleID);
      }
    }
    user.setVilleID(decodedVilleID);
    user.setMdp(request.getParameter("mdp"));
    user.setMdp2(request.getParameter("mdp2"));

    LOG.debug("Formulaire envoyé : {} | {} | {} | {} | {} | {} | {} | {} | {}",
        user.getPseudo(),
        user.getGenre(),
        user.getPrenom(),
        user.getNom(),
        user.getEmail(),
        user.getCp(),
        user.getVilleID(),
        user.getMdp(),
        user.getMdp2());

    // Si c'est une recherche de ville depuis le cp
    if ("code-postal".equals(request.getParameter("formChangeOn"))) {

      // Test si le cp est valide sinon renvoit une erreur à la jsp
      boolean cpIsValid;
      if (userHandler.testCpValid(user) == 1) {
        cpIsValid = true;
      } else {
        cpIsValid = false;
      }
      request.setAttribute("villeSelectHighlight", cpIsValid);
      request.setAttribute("cpIsValid", cpIsValid);

      // Sinon c'est un post du formulaire complet :
      // Vérification & insertion dans la BDD + Récupération de la liste de validation
    } else {

      Map<String, Integer> validationList = null;

      try {
        validationList = userHandler.addUser(user);
        request.setAttribute("validationList", validationList);

        // Attribut pour l'affichage de la validation de l'inscription
        request.setAttribute("isInscriptionValid", !validationList.containsValue(0));

      } catch (SQLDataException e1) {
        LOG.error("Error from : userHandler.addUser(user)", e1);
        request.setAttribute("internalError", true);
      }
    }

    // Sécurise l'objet user en enlevant les mdp non cryptés
    user.setMdp(null);
    user.setMdp2(null);
    request.setAttribute("user", user);

    // Renvoit la liste des villes depuis le CP du user
    request.setAttribute("villes", userHandler.getVillesByCp(user.getCp()));

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/inscription.jsp").forward(request, response);
    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error(e);
    }
  }

}
