package org.integratedmodelling.klab.hub.emails.services;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.config.EmailConfig;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.exception.SendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailManager {
	
	private static final String LOGIN_ROUTE = "/login";

    @Autowired
    private EmailConfig emailConfig;

    @Autowired
    private LinkConfig linkConfig;
    
    @Autowired
    private JavaMailSender mailSender;
    
    
    public void sendVerifyEmailClickback(String to, URL clickbackUrl) throws MessagingException {
        String subject = String.format("Email verify for %s", linkConfig.getSiteName());
        String msg = String.format(
                "To verify this email address for %s, click on the following link: %s\n\n"
                        + "If the link does not work, try copying and pasting it into your browser.\n\n"
                        + "If you did not request that this email be used for this site, "
                        + "please let us know by replying to this email.",
                        linkConfig.getSiteName(), clickbackUrl);
        //logger.info("Sending email verification email to " + to + "...");
        sendInternalEmail(emailConfig.senderEmail(), to, subject, msg);
    }

	public void sendPasswordChangeConfirmation(String to) {
        String subject = String.format("Your password for %s has been changed", linkConfig.getSiteName());
        String msg = String.format(
                "Your password for %s has been changed.\n\n"
                        + "If you did not initiate this password change, then you should reset your password as soon as possible.\n\n"
                        + "Log in at: %s/#%s",
                        linkConfig.getSiteName(), linkConfig.getSiteUrl(), LOGIN_ROUTE);
        //logger.info("Sending password change confirmation email to " + to + "...");
        sendInternalEmail(emailConfig.senderEmail(), to, subject, msg);
	}
    
    public void sendNewUser(String to, String username, URL clickbackUrl) {
        String subject = String.format("Welcome to %s!", linkConfig.getSiteName());
        String msg = String.format(
                "You have successfully registered at %s. Your username is %s."
                        + "\n\nTo activate your account, click on the following link: %s"
                        + "\n\nIf you did not create an account here, please let us know by replying to this email.",
                        linkConfig.getSiteName(), username, clickbackUrl.toExternalForm());
        //logger.info("Sending new user email to " + to + "...");
        sendInternalEmail(emailConfig.senderEmail(), to, subject, msg);
    }

    public void sendFromMainEmailAddress(String to, String subject, String msg)
            throws MessagingException {
    	sendInternalEmail(emailConfig.senderEmail(), to, subject, msg);
    }
    
    public void sendGroupRequestEmail(String username, URL clickbackUrl, String groups) {
    	String subject = String.format("New Group Request by %s", username);
    	String msg = String.format(
    			"%s would like to join %s groups."
    				+ "\n\nPlease make sure those groups are appropriate for %s" 
    				+ "and if so click on the following link: %s",
    			username, groups, username,clickbackUrl);
    	sendInternalEmail(emailConfig.senderEmail(), emailConfig.noreplyEmailAddress(), subject,msg);
    }
    
    public void sendInviteWithGroupsEmail(String to, URL clickbackUrl) throws MessagingException {
    	String subject = "Join the Integrated Modelling Team";
    	String msg = String.format(
    			"We would like to welcome you into the Integrated Modelling team." +
    					"  If you would like to continue your work with us please click the attached link." +  
    					"  After registering you will have been added to the default groups and the associated " +
    					"resources will be made available to your account.  Thank your for your interest and we look " +
    					"forward to working with you!" + 
    					"\n\n Please click the following link: %s",
    			clickbackUrl);
    	sendInternalEmail(emailConfig.senderEmail(), to, subject, msg);
    }

	public void sendLostPasswordEmail(String to, URL clickbackUrl) throws MessagingException {
		String subject = "New Password Request for you Integrated Modelling Account";
		String msg = String.format(
				"You have requested a new password for your Integrated Modelling Account" +
						"Please click the following link: %s \n\n" +
						"If you did not request a change of password please ignore this email. \n\n",
				clickbackUrl);
		sendInternalEmail(emailConfig.senderEmail(), to, subject, msg);
		
	}
	
	public void expiredLicenseEmail(String to) throws MessagingException {
		String subject = "Integrated Modelling Certificate has Expired";
		String msg = String.format(
				"You have attempted to authenticate with an expired license.  " +
						"Please click the following link: %s \n\n" +
						"and sign in to download a new certificate.", linkConfig.getSiteUrl()
				);
		sendInternalEmail(emailConfig.senderEmail(), to, subject, msg);
		
	}
	
	public void sendNewGroupRequest(String user, List<String> groups) {
	    String subject = "[AUTOMATIC] - New group request";
        StringBuffer msg = new StringBuffer().append("<p>New group(s) renewal request received from user <strong>").append(user).append("</strong></p><p>Groups: </p>").append("<ul>");
        for(String group: groups) {
            msg.append("<li>").append(group).append("</li>");
        }
        msg.append("</ul>");
        sendInternalEmail(emailConfig.senderEmail(), emailConfig.replyableSupportEmailAddress(), subject, msg.toString(), true);
	}
	
	private void sendInternalEmail(String from, String to, String subject, String message, boolean isHtml) {
	    Set<String> receipts = new HashSet<>(Arrays.asList(to));
        send(from, receipts, null, subject, message, isHtml);
	}
	
	private void sendInternalEmail(String from, String to, String subject, String message) {
	    sendInternalEmail(from, to, subject, message, false);
	}
	
	/**
	 * Send an email
	 * @param from from email address
	 * @param to one or more receipts
	 * @param subject the subject
	 * @param msg the message
	 * @param attachments the attachments
	 */
	public void send(String from, Set<String> to, Set<String> replayTo, String subject, String msg, boolean isHtml, File... attachments) {
    	try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom(new InternetAddress(from));
			for (String recipient: to) {
				message.setRecipients(RecipientType.TO, recipient);
			}
			if (replayTo != null && replayTo.size() > 0) {
				InternetAddress[] replayReceipts = new InternetAddress[replayTo.size()];
				int i = 0;
				for (String rr: replayTo) {
					replayReceipts[i++] = new InternetAddress(rr);
				}
				message.setReplyTo(replayReceipts);
			}
			message.setSubject(subject);
			if (isHtml) {
				message.setContent(msg, "text/html");
			} else {
				message.setText(msg);
			}
			mailSender.send(message);
    	} catch (MessagingException | MailException e) {
    	    StringBuffer error = new StringBuffer("Unable to send email.\n")
    	            .append("from: [").append(from).append("]\nto: ");
    	    for (String receipt : to) {
    	        error.append("[").append(receipt).append("]");
    	    }
    	    error.append("\nsubject: [").append(subject).append("]");
    	    //    .append("\nmessage: [").append(msg).append("]");
    	    Logging.INSTANCE.error(error.toString(), e);
    		throw new SendEmailException("[send]: Unable to send email.  Plase check email address and message");
		}
    }

    
}
