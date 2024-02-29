package org.integratedmodelling.klab.rest;

import java.io.File;
import java.util.Set;
public class KlabEmail {

    public enum EmailType {
        TEXT, HTML
    }

    public enum EmailStatus {
        SENDING, SENT, ERROR,
    }

    public String from;
    public Set<String> to;
    public Set<String> replayTo;
    public String subject;
    public String content;
    public EmailType type;
    public File[] attachments;

    public KlabEmail() {
    }

    /**
     * Send an email using HUB
     * @param from the sender, if null, it will use the default sender
     *        	   Currently, the only email that can be used is the one used for SMTP,
     *        	   therefore no customization is available
     * @param to the recipients
     * @param replayTo the replay to field if necessary
     * @param subject the subject
     * @param content the content
     * @param type the type (TEXT or HTML can be used)
     * @param attachments attachments
     */
    public KlabEmail(String from, Set<String> to, Set<String> replayTo, String subject, String content, EmailType type,
            File[] attachments) {
        super();
        this.from = from;
        this.to = to;
        this.replayTo = replayTo;
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.attachments = attachments;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Set<String> getTo() {
        return to;
    }

    public void setTo(Set<String> to) {
        this.to = to;
    }

    public Set<String> getReplayTo() {
        return replayTo;
    }

    public void setReplayTo(Set<String> replayTo) {
        this.replayTo = replayTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public File[] getAttachments() {
        return attachments;
    }

    public void setAttachments(File[] attachments) {
        this.attachments = attachments;
    }
}
