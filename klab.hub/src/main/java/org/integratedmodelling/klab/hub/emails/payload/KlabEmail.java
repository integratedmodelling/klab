package org.integratedmodelling.klab.hub.emails.payload;

import java.io.File;
import java.util.Set;

import org.integratedmodelling.klab.rest.KlabEmail.EmailType;

public class KlabEmail {
	
	public String from;
	public Set<String> to;
	public Set<String> replayTo;
	public String subject;
	public String content;
	public EmailType type;
	public File[] attachments;

}
