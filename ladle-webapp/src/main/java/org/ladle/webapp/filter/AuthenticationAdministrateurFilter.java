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

/**
 * This filter secure access to page with authentification for admins (role=2).
 */
@WebFilter("/admin/*")
public class AuthenticationAdministrateurFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpSession session = httpRequest.getSession(false);

    // TODO gerer le role admin et vérifier le test pour isLoggedIn

    boolean isLoggedIn = ((session != null) && (session.getAttribute("isLoginValid") != null));

    String loginURI = httpRequest.getContextPath() + "/connexion";

    boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

    boolean isLoginPage = httpRequest.getRequestURI().endsWith("connexion.jsp");

    if (isLoggedIn && (isLoginRequest || isLoginPage)) {
      // the admin is already logged in and he's trying to login again
      // then forwards to the page mon-compte.
      RequestDispatcher dispatcher = request.getRequestDispatcher("/mon-compte");
      dispatcher.forward(request, response);

    } else if (isLoggedIn || isLoginRequest) {
      // continues the filter chain
      // allows the request to reach the destination
      chain.doFilter(request, response);

    } else {
      // the admin is not logged in, so authentication is required
      // forwards to the Login page
      RequestDispatcher dispatcher = request.getRequestDispatcher("connexion.jsp");
      dispatcher.forward(request, response);

    }

  }

  @Override
  public void destroy() {
  }

  @Override
  public void init(FilterConfig fConfig) throws ServletException {
  }

}