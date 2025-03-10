package org.integratedmodelling.klab.hub.security.keycloak.methodHandler;

import java.io.Serializable;

import org.aopalliance.intercept.MethodInvocation;
import org.integratedmodelling.klab.hub.security.keycloak.method.KeycloakMethodSecurityExpression;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class KeycloakMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler implements PermissionEvaluator {

        @Override
        protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
                Authentication authentication, MethodInvocation invocation) {
            KeycloakMethodSecurityExpression root = new KeycloakMethodSecurityExpression(authentication);
            
            root.setPermissionEvaluator(getPermissionEvaluator());
            root.setTrustResolver(new AuthenticationTrustResolverImpl());
            root.setRoleHierarchy(getRoleHierarchy());
            return root;
        }

            @Override
            public boolean hasPermission(
              Authentication auth, Object targetDomainObject, Object permission) {
                if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)){
                    return false;
                }
                String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
                
                return hasPrivilege(auth, targetType, permission.toString().toUpperCase());
            }

            @Override
            public boolean hasPermission(
              Authentication auth, Serializable targetId, String targetType, Object permission) {
                if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
                    return false;
                }
                return hasPrivilege(auth, targetType.toUpperCase(), 
                  permission.toString().toUpperCase());
            }
            
            private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
                for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
                    if (grantedAuth.getAuthority().startsWith(targetType) && 
                      grantedAuth.getAuthority().contains(permission)) {
                        return true;
                    }
                }
                return false;
            }

}
