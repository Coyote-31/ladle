package org.ladle.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;

/**
 * Classe utilitaire de gestion des mots de passe.
 * 
 * @author Coyote
 */
public final class PasswordHandler {

  private static final Logger LOG = LogManager.getLogger(PasswordHandler.class);

  // Empêche l'instanciation de la classe.
  private PasswordHandler() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Renvoit le mdp sécurisé en SHA-256 du mot de passe avec le sel de
   * <code>user</code>.
   * 
   * @param user doit contenir <code>mdp</code> et <code>salt</code>
   * @return Le mot de passe en hexadecimal
   */
  public static String getSecurePassword(User user) {
    String generatedPassword = null;

    try {
      // Create MessageDigest instance for SHA-256
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      // Add salt bytes to digest
      md.update(user.getSalt());
      // Get the hash's bytes
      byte[] bytes = md.digest(Base64.getDecoder().decode(user.getMdp()));

      generatedPassword = Hex.encodeHexString(bytes);

    } catch (NoSuchAlgorithmException e) {
      LOG.error("Erreur de génération du mot de passe sécurisé", e);
    }
    return generatedPassword;
  }

  /**
   * Renvoit le mdp sécurisé en SHA-256 d'un <code>pwd + salt</code>.
   * 
   * @param pwd  le mdp non sécurisé
   * @param salt le sel pour l'encodage
   * 
   * @return Le mot de passe en hexadecimal
   */
  public static String getSecurePassword(String pwd, byte[] salt) {
    String generatedPassword = null;

    try {
      // Create MessageDigest instance for SHA-256
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      // Add salt bytes to digest
      md.update(salt);
      // Get the hash's bytes
      byte[] bytes = md.digest(Base64.getDecoder().decode(pwd));

      generatedPassword = Hex.encodeHexString(bytes);

    } catch (NoSuchAlgorithmException e) {
      LOG.error("Erreur de génération du mot de passe sécurisé", e);
    }
    return generatedPassword;
  }

  /**
   * Renvoit le sel pour la construction du mdp sécurisé.
   * 
   * @return un tableau de byte random qui sert de sel.
   * @throws NoSuchAlgorithmException
   */
  public static byte[] getSalt() throws NoSuchAlgorithmException {

    final int BYTES_SIZE = 16;

    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[BYTES_SIZE];
    sr.nextBytes(salt);

    return salt;
  }

}
