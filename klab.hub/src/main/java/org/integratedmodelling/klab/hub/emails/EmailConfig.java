package org.integratedmodelling.klab.hub.emails;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// @Component is not valid here because it needs to be on the concrete class
@Configuration
public class EmailConfig {
    @Value("${email.server.hostname}")
    private String EMAIL_HOSTNAME;

    @Value("${email.server.port:25}")
    private int EMAIL_PORT;

    @Value("${email.server.username}")
    private String EMAIL_USERNAME;

    @Value("${email.server.password}")
    private String EMAIL_PASSWORD;

    @Value("${email.replyable.general.emailaddress}")
    private String EMAIL_REPLYABLE_GENERAL;
    
    @Value("${email.replyable.support.emailaddress}")
    private String EMAIL_REPLYABLE_SUPPORT;
        
    @Value("${email.replyable.admin.emailaddress}")
    private String EMAIL_REPLYABLE_ADMIN;
    
    @Value("${email.replyable.noreply.emailaddress}")
    private String EMAIL_NOREPLY;
    
    public enum EmailType {
    	TEXT,
    	HTML
    }
    
    @Bean
    public JavaMailSender getEmailSender() {
        JavaMailSenderImpl result = new JavaMailSenderImpl();
        result.setHost(EMAIL_HOSTNAME);
        result.setPort(EMAIL_PORT);
        result.setUsername(EMAIL_USERNAME);
        result.setPassword(EMAIL_PASSWORD);
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.auth", "true");
        javaMailProperties.setProperty("mail.smtp.starttls.enable", "true");
        result.setJavaMailProperties(javaMailProperties);
        return result;
    }
    
    public String senderEmail() {
    	return EMAIL_USERNAME;
    }

    public String replyableGeneralEmailAddress() {
        return EMAIL_REPLYABLE_GENERAL;
    }

    public String replyableSupportEmailAddress() {
        return EMAIL_REPLYABLE_SUPPORT;
    }
    
    public String replyableAdminEmailAddress() {
        return EMAIL_REPLYABLE_ADMIN;
    }

    public String noreplyEmailAddress() {
        return EMAIL_NOREPLY;
    }
    
    public String[] getAuthorizedEmailAddresses() {
    	return new String[] { replyableGeneralEmailAddress(), replyableSupportEmailAddress(), replyableAdminEmailAddress(), noreplyEmailAddress() };
    }

}