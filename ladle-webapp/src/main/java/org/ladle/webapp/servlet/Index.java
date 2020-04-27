package org.ladle.webapp.servlet;

import java.io.IOException;
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
import org.ladle.beans.jpa.Region;
import org.ladle.dao.RegionDao;

/**
 * Servlet implementation class Index
 */
@SuppressWarnings("serial")
@WebServlet("")
public class Index extends HttpServlet {

  private static final Logger LOG = LogManager.getLogger(Index.class);

  @EJB(name = "RegionDaoImpl")
  private RegionDao regionDao;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("doGet()");

    // ================================================
    // Test un SELECT avec l'objet Region sur la bdd
    // ================================================
    List<Region> regionsToSend;
    regionsToSend = regionDao.getAllRegions();
    request.setAttribute("myList", regionsToSend);

    HttpSession session = request.getSession();

    // Si action = deconnexion déconnecte l'utilisateur
    LOG.trace("Action value {}", request.getParameter("action"));

    if ("deconnexion".equals(request.getParameter("action"))) {
      LOG.debug("Inside deconnexion action");

      session.setAttribute("isLoginValid", false);
    }

    // ================================================

    try {
      getServletContext().getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);

    } catch (ServletException | IOException | IllegalStateException e) {
      LOG.error("Error building index.jsp", e);
    }
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("doPost()");

    try {
      doGet(request, response);

    } catch (ServletException | IOException e) {
      LOG.error("doGet() failed", e);
    }
  }

}
