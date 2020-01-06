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

public class MailHandler {

	private static final Logger LOG = LogManager.getLogger(MailHandler.class);
	
	private MailHandler() {
	    throw new IllegalStateException("Utility class");
	  }
	
	public static void sendMail() {
		
        final String username = "coyote@gmail.com";
        final String password = "";

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
            message.setSubject("Testing Gmail SSL");
            message.setText("Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!");

            Transport.send(message);

            LOG.info("Email envoyé");

        } catch (MessagingException e) {
            LOG.error("Erreur : Email non envoyé !", e);
        }
	}
}
