package org.integratedmodelling.klab.hub.payload;

import java.io.File;
import java.util.Set;

import org.integratedmodelling.klab.hub.config.EmailConfig.EmailType;

public class KlabEmail {
	
	public String from;
	public Set<String> to;
	public Set<String> replayTo;
	public String subject;
	public String content;
	public EmailType type;
	public File[] attachments;

}
