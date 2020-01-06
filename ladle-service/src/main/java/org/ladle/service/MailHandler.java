package org.ladle.service;

import java.util.Date;
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

public class MailHandler {

	private static final Logger LOG = LogManager.getLogger(MailHandler.class);
	
	private MailHandler() {
	    throw new IllegalStateException("Utility class");
	  }
	
	public static void sendValidationMail(User user) {
		
        final String username = "@gmail.com";
        final String password = "";
        final String urlLADLE = "http://localhost:8080/ladle-webapp/email-validation";

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

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("ladle-user1@trash-mail.com")
            );
            message.setSentDate(new Date());
            message.setSubject("Validation LADLE pour " + user.getPseudo());
            
     	   // Send HTML message
     	   message.setContent(
                   "<h1>Cliquez pour valider l'inscription</h1>"
                   + "<a href='"+ urlLADLE + "?id=" + user.getEmailSHA() +"'>Valider votre compte</a>",
                  "text/html");

            Transport.send(message);

            LOG.info("Email envoyé");

        } catch (MessagingException e) {
            LOG.error("Erreur : Email non envoyé !", e);
        }
	}
}
