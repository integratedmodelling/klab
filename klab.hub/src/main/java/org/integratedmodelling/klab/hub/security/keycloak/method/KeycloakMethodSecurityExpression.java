package org.integratedmodelling.klab.hub.security.keycloak.method;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

public class KeycloakMethodSecurityExpression extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    
    public KeycloakMethodSecurityExpression(Authentication authentication) {
        super(authentication);
    }

    public boolean isUser(String userName) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) this.authentication;
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) token.getPrincipal();

        String preferredUsername = principal.getKeycloakSecurityContext().getToken().getPreferredUsername();
        if (preferredUsername != null) {
            return preferredUsername.equals(userName);
        }

        return false;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;

    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }
    
    void setThis(Object target) {
        this.target = target;
    }
}