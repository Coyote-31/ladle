package org.ladle.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ladle.beans.User;

/**
 * Classe de gestion des mails de l'application.
 *
 * @author Coyote
 */
public final class MailHandler {

  private static final Logger LOG = LogManager.getLogger(MailHandler.class);

  private MailHandler() {
    throw new IllegalStateException("Utility class");
  }

  public static void sendValidationMail(User user) {

    // Récupération des données de connexion :
    Properties mailProps = new Properties();
    try {
      InputStream input = MailHandler.class.getResourceAsStream("mail-resources.xml");
      mailProps.loadFromXML(input);
    } catch (InvalidPropertiesFormatException e1) {
      LOG.error("Chargement des propriétés : Invalid Format Exception !", e1);
    } catch (FileNotFoundException e1) {
      LOG.error("Chargement des propriétés : File Not Found !", e1);
    } catch (IOException e1) {
      LOG.error("Chargement des propriétés : Erreur IO !", e1);
    }

    final String username = mailProps.getProperty("mailUsername");
    final String password = mailProps.getProperty("mailPassword");

    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "465");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.socketFactory.port", "465");
    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    prop.put("mail.smtp.ssl.checkserveridentity", true);

    Session session = Session.getInstance(prop,
        new javax.mail.Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    final String URL_LADLE = "http://localhost:8080/ladle/email-validation";

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(
          Message.RecipientType.TO,
          InternetAddress.parse(user.getEmail()));
      message.setSentDate(new Date());
      message.setSubject("Validation LADLE pour " + user.getPseudo());

      // Send HTML message
      message.setContent(
          "<h1>Validation de l'inscription :</h1>"
                         + "<a href='"
                         + URL_LADLE
                         + "?id="
                         + user.getEmailSHA()
                         + "'>Valider votre compte</a>",
          "text/html");

      Transport.send(message);

      LOG.info("Email envoyé à {}", user.getEmail());

    } catch (MessagingException e) {
      LOG.error("Erreur : Email non envoyé !", e);
    }
  }
}
