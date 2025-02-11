package org.integratedmodelling.klab.hub.security.keycloak.service;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("securityService")
public class KeycloakSecurityService {
    
    public boolean isUser(String userName) {
        KeycloakAuthenticationToken authentication = 
                (KeycloakAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) token.getPrincipal();

        String preferredUsername = principal.getKeycloakSecurityContext().getToken().getPreferredUsername();
        if (preferredUsername != null) {
            return preferredUsername.equals(userName.toLowerCase());
        }

        return false;
    }
    
    public boolean isUserId(String userId) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
//        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
//        KeycloakPrincipal<KeycloakSecurityContext> principal = (KeycloakPrincipal<KeycloakSecurityContext>) token.getPrincipal();
//
//        String preferredUsername = principal.getKeycloakSecurityContext().getToken().getPreferredUsername();
        if (id != null) {
            return id.equals(userId);
        }

        return false;
    }

}
