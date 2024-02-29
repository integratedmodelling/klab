package org.integratedmodelling.klab.hub.emails.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.integratedmodelling.klab.hub.api.GenericModel;
import org.integratedmodelling.klab.rest.KlabEmail.EmailType;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for email templates
 * @author Enrico Girotto
 */
@Document(collection = "EmailTemplates")
@CompoundIndexes({
        @CompoundIndex(name = "email_template_unique_index", unique = true, def = "{'name' : 1, 'authorUsername' : 1}")})
public class EmailTemplate extends GenericModel {

    @NotNull
    String authorUsername;

    @Email
    String sender;

    @Email
    String[] recipients;

    String subject;

    String content;

    EmailType type = EmailType.HTML;

    /**
     * @return the id
     */
    public String getId() {
        return super.getId();
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * @return the name
     */
    public String getName() {
        return super.getName();
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        super.setName(name);
    }

    /**
     * @return the author username
     */
    public String getAuthorUsername() {
        return authorUsername;
    }

    /**
     * @param username the username to set
     */
    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the recipients
     */
    public String[] getRecipients() {
        return recipients;
    }

    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    /**
     * @return the object
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param object the object to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the type
     */
    public EmailType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(EmailType type) {
        this.type = type;
    };

    public String toString() {
        return super.getName() + "; Object: " + this.getSubject();
    }

}
