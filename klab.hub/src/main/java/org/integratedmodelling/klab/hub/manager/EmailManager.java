package org.integratedmodelling.klab.hub.manager;

import java.io.File;
import java.net.URL;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.config.EmailConfig;
import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailManager {
	
	private static final String LOGIN_ROUTE = "/login";

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private TokenClickbackConfig tokenClickbackConfig;
    
    @Autowired
    private JavaMailSender mailSender;
    
    
    public void sendVerifyEmailClickback(String to, URL clickbackUrl) throws MessagingException {
        String subject = String.format("Email verify for %s", tokenClickbackConfig.getSiteName());
        String msg = String.format(
                "To verify this email address for %s, click on the following link: %s\n\n"
                        + "If the link does not work, try copying and pasting it into your browser.\n\n"
                        + "If you did not request that this email be used for this site, "
                        + "please let us know by replying to this email.",
                        tokenClickbackConfig.getSiteName(), clickbackUrl);
        //logger.info("Sending email verification email to " + to + "...");
        send(emailConfig.replyableAdminEmailAddress(), to, subject, msg);
    }

	public void sendPasswordChangeConfirmation(String to) {
        String subject = String.format("Your password for %s has been changed", tokenClickbackConfig.getSiteName());
        String msg = String.format(
                "Your password for %s has been changed.\n\n"
                        + "If you did not initiate this password change, then you should reset your password as soon as possible.\n\n"
                        + "Log in at: %s/#%s",
                tokenClickbackConfig.getSiteName(), tokenClickbackConfig.getSiteUrl(), LOGIN_ROUTE);
        //logger.info("Sending password change confirmation email to " + to + "...");
		send(emailConfig.replyableGeneralEmailAddress(), to, subject, msg);
	}
    
    private void send(String from, String to, String subject, String msg, File... attachments) {
    	try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom(new InternetAddress(from));
			message.setRecipients(RecipientType.TO, to);
			message.setSubject(subject);
			message.setText(msg);
			mailSender.send(message);
    	} catch (MessagingException e) {
    		throw new KlabException("[send]: Unable to send email");
		}
    }

    public void sendNewUser(String to, String username, URL clickbackUrl) {
        String subject = String.format("Welcome to %s!", tokenClickbackConfig.getSiteName());
        String msg = String.format(
                "You have successfully registered at %s. Your username is %s."
                        + "\n\nTo activate your account, click on the following link: %s"
                        + "\n\nIf you did not create an account here, please let us know by replying to this email.",
                        tokenClickbackConfig.getSiteName(), username, clickbackUrl.toExternalForm());
        //logger.info("Sending new user email to " + to + "...");
        send(emailConfig.replyableAdminEmailAddress(), to, subject, msg);
    }

    public void sendFromMainEmailAddress(String to, String subject, String msg, File... attachments)
            throws MessagingException {
        send(emailConfig.replyableGeneralEmailAddress(), to, subject, msg, attachments);
    }
    
    public void sendGroupRequestEmail(String username, URL clickbackUrl, String groups) {
    	String subject = String.format("New Group Request by %s", username);
    	String msg = String.format(
    			"%s would like to join %s groups."
    				+ "\n\nPlease make sure those groups are appropriate for %s" 
    				+ "and if so click on the following link: %s",
    			username, groups, username,clickbackUrl);
    	send(emailConfig.replyableGeneralEmailAddress(), emailConfig.replyableGeneralEmailAddress(), subject,msg);
    }
    
    public void sendInviteWithGroupsEmail(String email, URL clickbackUrl) throws MessagingException {
    	String Subject = "Join the Integrated Modelling Team";
    	String msg = String.format(
    			"We would like to welcome you into the Integrated Modelling team." +
    					"  If you would like to continue your work with us please click the attached link." +  
    					"  After registering you will have been added to the default groups and the associated " +
    					"resources will be made available to your account.  Thank your for your interest and we look " +
    					"forward to working with you!" + 
    					"\n\n Please click the following link: %s",
    			clickbackUrl);
    	sendFromMainEmailAddress(email, Subject, msg);
    }

    
}
