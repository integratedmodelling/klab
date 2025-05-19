package org.integratedmodelling.klab.hub.oauth;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;

public class KeycloakTokenUtils {
    /**
     * Retrieves a specific claim from the Keycloak token.
     *
     * @param httpRequest the HTTP request
     * @param claimName the name of the claim to retrieve
     * @return the claim value or "unknown" if not found
     */
    public static String getClaim(HttpServletRequest httpRequest, String claimName) {
        final Principal userPrincipal = httpRequest.getUserPrincipal();

        if (userPrincipal instanceof KeycloakAuthenticationToken) {
            KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) userPrincipal;
            KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) keycloakAuthenticationToken.getPrincipal();

            // Extract the Keycloak security context
            KeycloakSecurityContext keycloakSecurityContext = kp.getKeycloakSecurityContext();
            
            // Get all other claims
            Map<String, Object> otherClaims = keycloakSecurityContext.getToken().getOtherClaims();
            
            // Check if the claim exists
            if (otherClaims.containsKey(claimName)) {
                return otherClaims.get(claimName).toString();
            }
        }
        
        return null;  // If the claim is not found
    }
}