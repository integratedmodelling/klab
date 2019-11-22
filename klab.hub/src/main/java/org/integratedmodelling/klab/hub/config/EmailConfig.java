package org.integratedmodelling.klab.hub.config;

import java.util.HashMap;
import java.util.Map;
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
    private String EMAIL_REPLYABLE_GENERAL_KEY = "general";

    @Value("${email.replyable.support.emailaddress}")
    private String EMAIL_REPLYABLE_SUPPORT;
    private String EMAIL_REPLYABLE_SUPPORT_KEY = "support";
    
    @Value("${email.replyable.admin.emailaddress}")
    private String EMAIL_REPLYABLE_ADMIN;
    private String EMAIL_REPLYABLE_ADMIN_KEY = "admin";

    @Value("${email.noreply.emailaddress}")
    private String EMAIL_NOREPLY;
    private String EMAIL_NOREPLY_KEY = "noreplay";

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
    
    public Map<String, String> getAuthorizedEmailAddresses() {
    	HashMap<String, String> addresses = new HashMap<String, String>(4);
    	addresses.put(EMAIL_REPLYABLE_GENERAL_KEY, EMAIL_REPLYABLE_GENERAL);
    	addresses.put(EMAIL_REPLYABLE_SUPPORT_KEY, EMAIL_REPLYABLE_SUPPORT);
    	addresses.put(EMAIL_REPLYABLE_ADMIN_KEY, EMAIL_REPLYABLE_ADMIN);
    	addresses.put(EMAIL_NOREPLY_KEY, EMAIL_NOREPLY);
    	// { replyableGeneralEmailAddress(), replyableSupportEmailAddress(), replyableAdminEmailAddress(), noreplyEmailAddress() };
    	return addresses;
    }

}