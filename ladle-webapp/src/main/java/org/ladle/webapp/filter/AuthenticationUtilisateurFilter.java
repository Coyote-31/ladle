package org.ladle.webapp.filter;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.service.CookieHandler;
import org.ladle.service.UserHandler;

/**
 * This filter secure access to page with authentification for users (role=0).
 */
@WebFilter("/*")
public class AuthenticationUtilisateurFilter implements Filter {

  private static final Logger LOG = LogManager.getLogger(AuthenticationUtilisateurFilter.class);

  private HttpServletRequest httpRequest;

  @EJB(name = "UserHandler")
  private UserHandler userHandler;

  private static final String[] loginRequiredURLs = {
      "/mon-compte", "/login"
  };

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    httpRequest = (HttpServletRequest) request;

    // Récupere la request
    String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

    LOG.debug("getRequestURI = {}", httpRequest.getRequestURI());
    LOG.debug("getContextPath = {}", httpRequest.getContextPath());

    // Exclude admin security level pages
    if (path.startsWith("/admin/")) {
      chain.doFilter(request, response);
      return;
    }

    HttpSession session = httpRequest.getSession(true);

    boolean isLoggedIn = false;

    // Put "isLoginValid" in "isLoggedIn"
    if ((session != null) && (session.getAttribute("isLoginValid") != null)) {

      isLoggedIn = session.getAttribute("isLoginValid").equals(true);
    }

    // Connexion paths
    String loginURI = httpRequest.getContextPath() + "/connexion";
    boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
    boolean isLoginPage = httpRequest.getRequestURI().endsWith("connexion.jsp");

    boolean isLoginPath;

    if (isLoginRequest || isLoginPage) {
      isLoginPath = true;
    } else {
      isLoginPath = false;
    }

    // Inscription paths
    String inscriptionURI = httpRequest.getContextPath() + "/inscription";
    boolean isInscriptionRequest = httpRequest.getRequestURI().equals(inscriptionURI);
    boolean isInscriptionPage = httpRequest.getRequestURI().endsWith("inscription.jsp");

    boolean isInscriptionPath;

    if (isInscriptionRequest || isInscriptionPage) {
      isInscriptionPath = true;
    } else {
      isInscriptionPath = false;
    }

    if (isLoggedIn && (isLoginPath || isInscriptionPath)) {
      // the user is already logged in and he's trying to login again
      // or go on 'inscription' then forward to the mon-compte page
      LOG.debug("Inside : close 1");
      httpRequest.getRequestDispatcher("/mon-compte").forward(request, response);

    } else if (!isLoggedIn && isLoginRequired()) {
      // User is not logged in and the requested page requires authentication,

      LOG.debug("Inside : close 2");
      String[] loginArray = CookieHandler.getLogin(httpRequest);

      if (userHandler.isValidTokenLogin(loginArray[0], loginArray[1])) {

        // met à jours les variables de connexion
        if (session != null) {
          session.setAttribute("isLoginValid", true);
          session.setAttribute("utilisateur", userHandler.getUtilisateurOnLogin(loginArray[0]));
          LOG.debug("{} is now connected", loginArray[0]);
        }

        // continue la requete
        chain.doFilter(request, response);

      } else {
        // forward to the connexion page
        String loginPage = "/connexion";
        RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);

        dispatcher.forward(request, response);
      }

    } else if (!isLoggedIn) {
      // other request page where the user is not logged
      // try to loggin him from cookies
      if (session == null) {
        LOG.debug("session = null");
      } else {
        LOG.debug("session = !null");
      }
      LOG.debug("Inside : close 3");
      String[] loginArray = CookieHandler.getLogin(httpRequest);

      // met à jours les variables de connexion
      if (userHandler.isValidTokenLogin(loginArray[0], loginArray[1]) && (session != null)) {

        session.setAttribute("isLoginValid", true);
        session.setAttribute("utilisateur", userHandler.getUtilisateurOnLogin(loginArray[0]));
        LOG.debug("{} is now connected", loginArray[0]);
      }
      // continue la requete
      chain.doFilter(request, response);

    } else {
      LOG.debug("Inside : close 4");
      // for other requested pages that do not require authentication
      // or the user is already logged in, continue to the destination
      chain.doFilter(request, response);
    }
  }

  private boolean isLoginRequired() {
    String requestURL = httpRequest.getRequestURL().toString();

    for (String loginRequiredURL : loginRequiredURLs) {
      if (requestURL.contains(loginRequiredURL)) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void destroy() {
  }

  @Override
  public void init(FilterConfig fConfig) throws ServletException {
  }

}