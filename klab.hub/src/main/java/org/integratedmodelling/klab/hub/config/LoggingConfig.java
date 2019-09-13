package org.integratedmodelling.klab.hub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {
	@Value("${logging.file}")
	private String LOGGING_FILE;

	public String getLOGGING_FILE() {
		return LOGGING_FILE;
	}
}
