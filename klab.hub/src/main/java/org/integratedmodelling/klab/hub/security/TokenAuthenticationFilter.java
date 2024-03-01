package org.integratedmodelling.klab.hub.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;


public class TokenAuthenticationFilter implements Filter {

    
    @Autowired
    private TokenRepository tokenRepository;

    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    		throws IOException, ServletException {
        try {
            
        	String tokenString = ((HttpServletRequest) request)
                    .getHeader(WebSecurityConfig.AUTHENTICATION_TOKEN_HEADER_NAME);
            if (tokenString != null) {
            	Optional<TokenAuthentication> token = tokenRepository.findByTokenString(tokenString);
            	if(token.isPresent()) {
            		TokenAuthentication storedToken = token.get();
                    if (storedToken.isAuthenticated()) {
                    	PreAuthenticatedAuthenticationToken authToken = new PreAuthenticatedAuthenticationToken(storedToken.getPrincipal()
                    			,storedToken.getCredentials(),storedToken.getAuthorities());
                        // successful match. token should contain everything the security context needs.
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
            	}
            }
        } catch (Throwable e) {
          Logging.INSTANCE.error("Could not set user authentication in security context " + e.toString());
        } finally {
        	SecurityContextHolder.getContext();
        	chain.doFilter(request, response);
		}

    }
    
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Initialized TokenHeaderProcessingFilter");
    }

    
}