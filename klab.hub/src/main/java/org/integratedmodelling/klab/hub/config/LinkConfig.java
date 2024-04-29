package org.integratedmodelling.klab.hub.config;

import java.net.MalformedURLException;
import java.net.URL;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.tokens.enums.ClickbackAction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkConfig {
	
    @Value("${site.fullname}")
    private String SITE_FULLNAME;

    @Value("${site.shortname}")
    private String SITE_SHORTNAME;

    @Value("${site.url}")
    private String SITE_URL;

    @Value("${site.server.id}")
    private String SITE_SERVER_ID;

    @Value("${engine.url}")
    private String ENGINE_URL;
    
    @Value("${site.clickback}")
	private String CLICKBACK;
    
    @Value("${site.callback}")
    private String CALLBACK;
	
    
    public String getSiteName() {
    	return SITE_FULLNAME;
    };

    public URL getSiteUrl() {
        try {
            return new URL(SITE_URL);
        } catch (MalformedURLException e) {
            String msg = String.format(
                    "Unable to create site URL for '%s'! (there is probably a configuration problem.)", SITE_URL);
            throw new KlabException(msg, e);
        }
    }
    
    public URL getCallbackUrl() {
        try {
            return new URL(CALLBACK);
        } catch (MalformedURLException e) {
            String msg = String.format(
                    "Unable to create site URL for '%s'! (there is probably a configuration problem.)", CALLBACK);
            throw new KlabException(msg, e);
        }
    }

    public URL getEngineUrl() {
        try {
            return new URL(ENGINE_URL);
        } catch (MalformedURLException e) {
            String msg = String.format(
                    "Unable to create engine URL for '%s'! (there is probably a configuration problem.)", ENGINE_URL);
            throw new KlabException(msg, e);
        }
    }
    
    public URL getFrontendCallbackUrl(String userId, ClickbackAction action, String token) {
        try {
            return new URL(String.format("%s/%s?user=%s&token=%s", getCallbackUrl(), action, userId, token));
        } catch (MalformedURLException e) {
            String msg = "Unable to create clickback URL! (there is probably a configuration problem.)";
            throw new KlabException(msg, e);
        }
    }

    public String getSiteUniqueShortname() {
    	return SITE_SHORTNAME;
    };

    public String getVersion() {
        return "0.1.0";
    }

    public final String getApplicationId() {
        return getSiteUniqueShortname() + "-" + getVersion();
    }

    public String getSiteServerId() {
    	return SITE_SERVER_ID;
    };
    
    public String getClickback() {
    	return CLICKBACK;
    };
}
