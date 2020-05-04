package org.ladle.webapp.filter;

import java.io.IOException;

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

/**
 * This filter secure access to page with authentification for users (role=0).
 */
@WebFilter("/*")
public class AuthenticationUtilisateurFilter implements Filter {

  private static final Logger LOG = LogManager.getLogger(AuthenticationUtilisateurFilter.class);

  private HttpServletRequest httpRequest;

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

    HttpSession session = httpRequest.getSession(false);

    // TODO Vérifier le test isLoggedIn

    boolean isLoggedIn = false;
    if ((session != null) && (session.getAttribute("isLoginValid") != null)) {

      isLoggedIn = (session.getAttribute("isLoginValid").equals(true));
    }

    String loginURI = httpRequest.getContextPath() + "/connexion";
    boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
    boolean isLoginPage = httpRequest.getRequestURI().endsWith("connexion.jsp");

    if (isLoggedIn && (isLoginRequest || isLoginPage)) {
      // the user is already logged in and he's trying to login again
      // then forward to the mon-compte page
      httpRequest.getRequestDispatcher("/mon-compte").forward(request, response);

    } else if (!isLoggedIn && isLoginRequired()) {
      // the user is not logged in, and the requested page requires
      // authentication, then forward to the connexion page
      String loginPage = "/connexion";
      RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);

      dispatcher.forward(request, response);
    } else {
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