package org.ladle.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;

/**
 * Classe utilitaire de gestion des mots de passe.
 * 
 * @author Coyote
 */
public class PasswordHandler {
	
	private static final Logger LOG = LogManager.getLogger(PasswordHandler.class);
	
	// Empêche l'instanciation de la classe.
	private PasswordHandler() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Renvoit le mdp sécurisé en SHA-256 du mot de passe avec le sel.
	 * 
	 * @param user
	 * @return
	 */
	public static String getSecurePassword(User user)
	{
		String generatedPassword = null;

		try {
			// Create MessageDigest instance for SHA-256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			// Add salt bytes to digest
			md.update(user.getSalt());
			// Get the hash's bytes
			byte[] bytes = md.digest(user.getMdp().getBytes());
            //Convert bytes[] to hexadecimal format
			StringBuilder sb = new StringBuilder();
			
			for(int i=0; i< bytes.length ;i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			generatedPassword = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			LOG.error("Erreur de génération du mot de passe sécurisé",e);
		}
		return generatedPassword;
	}

	/**
	 * Renvoit le sel pour la construction du mdp sécurisé.
	 *  
	 * @return un tableau de byte random qui sert de sel.
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getSalt() throws NoSuchAlgorithmException
	{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

}
