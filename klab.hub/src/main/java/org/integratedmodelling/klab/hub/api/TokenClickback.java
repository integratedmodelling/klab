package org.integratedmodelling.klab.hub.api;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;


public abstract class TokenClickback extends TokenAuthentication {

    private static final long serialVersionUID = -8819538677554609017L;

    private static final int TOKEN_TTL_SECONDS = 60 * 60 * 48; // 48 hours

    private static final Collection<GrantedAuthority> ROLES = new ArrayList<GrantedAuthority>(
            Arrays.asList(Role.ROLE_CLICKBACK));

    @Transient
    private URL callbackUrl;

    public TokenClickback(String username) {
        super(username);
        expiration = DateTime.now().plusSeconds(TOKEN_TTL_SECONDS);
    }
    
	@Override
    public Collection<GrantedAuthority> getAuthorities() {
        return ROLES;
    }

    @Override
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    }

    /**
     * override this for token types that can hit server clickback URLs directly (i.e. GET from email click)
     */
    public void setCallbackUrl(LinkConfig tokenClickbackConfig) {
    	setCallbackUrl(tokenClickbackConfig.getFrontendCallbackUrl(getPrincipal(), getClickbackAction(), getTokenString()));
    }

    protected void setCallbackUrl(URL callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public URL getCallbackUrl() {
        return callbackUrl;
    }

    public abstract ClickbackAction getClickbackAction();


    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return tokenClickbackConfig.getSiteUrl().toString();
    }
}