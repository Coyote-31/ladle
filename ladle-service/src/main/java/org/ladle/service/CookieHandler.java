package org.ladle.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CookieHandler {

  private static final Logger LOG = LogManager.getLogger(CookieHandler.class);

  private static final String LOGIN = "login";
  private static final String TOKEN_LOGIN = "tokenLogin";

  // Durée de vie des cookies = 30 jours
  private static final int MAX_AGE = 60 * 60 * 24 * 30;

  // Empêche l'instanciation de la classe.
  private CookieHandler() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Met à jour les cookies de connexion
   *
   * @param login
   * @param tokenLogin
   * @param request
   * @param response
   */
  public static void updateLogin(String login, String tokenLogin,
      HttpServletRequest request, HttpServletResponse response) {

    boolean cookieLoginExist = false;
    boolean cookieTokenLoginExist = false;

    // Récupération des cookies
    Cookie[] cookies = request.getCookies();

    // Met à jour les cookies si ils existent déjà
    if (cookies != null) {
      for (Cookie cookie : cookies) {

        // Si cookie "login"
        if (LOGIN.equals(cookie.getName())) {
          cookie.setValue(login);
          cookie.setMaxAge(MAX_AGE);
          response.addCookie(cookie);
          LOG.debug("Update cookie login : {}", cookie.getValue());
          cookieLoginExist = true;
        }

        // Si cookie "tokenLogin"
        if (TOKEN_LOGIN.equals(cookie.getName())) {
          cookie.setValue(tokenLogin);
          cookie.setMaxAge(MAX_AGE);
          response.addCookie(cookie);
          LOG.debug("Update cookie tokenLogin : {}", cookie.getValue());
          cookieTokenLoginExist = true;
        }

      }
    }

    // Ajoute le cookie "login" si il n'existe pas encore
    if (!cookieLoginExist) {
      Cookie cookieLogin = new Cookie(LOGIN, login);
      cookieLogin.setMaxAge(MAX_AGE);
      response.addCookie(cookieLogin);
      LOG.debug("Add cookie login : {}", cookieLogin.getValue());
    }

    // Ajoute le cookie "tokenLogin" si il n'existe pas encore
    if (!cookieTokenLoginExist) {
      Cookie cookieTokenLogin = new Cookie(TOKEN_LOGIN, tokenLogin);
      cookieTokenLogin.setMaxAge(MAX_AGE);
      response.addCookie(cookieTokenLogin);
      LOG.debug("Add cookie tokenLogin : {}", cookieTokenLogin.getValue());
    }
  }

  /**
   * Retourne un tableau avec en index 0 le login index 1 le tokenLogin
   */
  public static String[] getLogin(HttpServletRequest request) {

    // Récupération des cookies
    Cookie[] cookies = request.getCookies();
    String login = null;
    String tokenLogin = null;

    if (cookies != null) {
      for (Cookie cookie : cookies) {

        // Récupération du cookie "login"
        if (LOGIN.equals(cookie.getName())) {
          login = cookie.getValue();
          LOG.debug("Get cookie login : {}", login);
        }

        // Récupération du cookie "tokenLogin"
        if (TOKEN_LOGIN.equals(cookie.getName())) {
          tokenLogin = cookie.getValue();
          LOG.debug("Get cookie loginToken : {}", tokenLogin);
        }
      }
    }

    return new String[] { login, tokenLogin };
  }

  /**
   * Supprime les cookies de connexion
   *
   * @param request
   * @param response
   */
  public static void deleteLogin(HttpServletRequest request, HttpServletResponse response) {

    // Récupération des cookies
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {

        // Suppression du cookie "login"
        if (LOGIN.equals(cookie.getName())) {
          cookie.setValue(null);
          cookie.setMaxAge(0);
          response.addCookie(cookie);
          LOG.debug("Delete cookie : login");
        }

        // Suppression du cookie "tokenLogin"
        if (TOKEN_LOGIN.equals(cookie.getName())) {
          cookie.setValue(null);
          cookie.setMaxAge(0);
          response.addCookie(cookie);
          LOG.debug("Delete cookie : tokenLogin");
        }
      }
    }
  }

}
