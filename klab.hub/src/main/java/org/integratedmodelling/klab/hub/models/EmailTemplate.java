package org.integratedmodelling.klab.hub.models;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Model for email templates
 * @author Enrico Girotto
 */
@Document(collection = "Email")
public class EmailTemplate {

	@Id @GeneratedValue
    String id;

    @Indexed(unique = true)
    @NotNull
    String name;
    
    @DBRef
    User author;
    
    @Pattern(regexp = Constraints.EMAIL_PATTERN)
    String sender;
    
    @Pattern(regexp = Constraints.EMAIL_PATTERN)
    String[] recipients;
    
    String subject;
    
    String content;
        
    EmailType type = EmailType.HTML;
    
    enum EmailType {
    	TEXT,
    	HTML
    }

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
		return this.name + "; Object: "+this.getSubject();
	}
    
}
