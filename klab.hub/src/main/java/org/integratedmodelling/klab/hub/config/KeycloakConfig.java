package org.integratedmodelling.klab.hub.config;

import org.integratedmodelling.klab.hub.security.keycloak.methodHandler.KeycloakMethodSecurityExpressionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,
securedEnabled= true)
public class KeycloakConfig extends GlobalMethodSecurityConfiguration {

        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            KeycloakMethodSecurityExpressionHandler expressionHandler = 
              new KeycloakMethodSecurityExpressionHandler();            
            //expressionHandler.setPermissionEvaluator(new KeycloakMethodSecurityExpressionHandler());
            return expressionHandler;
        }
    }

