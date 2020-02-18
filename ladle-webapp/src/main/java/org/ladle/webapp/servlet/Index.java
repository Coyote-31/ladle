package org.ladle.webapp.servlet;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.dao.RegionDao;
import org.ladle.dao.hibernate.object.Region;

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
