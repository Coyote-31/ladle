package org.ladle.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class SiteSecteur
 */
@WebServlet("/site-secteur")
public class SiteSecteur extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LogManager.getLogger(SiteSecteur.class);

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [SiteSecteur] -> doGet()");

    getServletContext().getRequestDispatcher("/WEB-INF/site-secteur.jsp").forward(request, response);
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
   *      response)
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    LOG.debug("Servlet [SiteSecteur] -> doPost()");

    doGet(request, response);
  }

}
