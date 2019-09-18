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
import org.integratedmodelling.klab.hub.models.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;


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
            	Optional<AuthenticationToken> token = tokenRepository.findByTokenString(tokenString);
            	if(token.isPresent()) {
            		AuthenticationToken storedToken = token.get();
                    if (storedToken.isAuthenticated()) {
                        // successful match. token should contain everything the security context needs.
                        SecurityContextHolder.getContext().setAuthentication(storedToken);
                    }
            	}
            }
        } catch (Throwable e) {
          Logging.INSTANCE.error("Could not set user authentication in security context " + e.toString());
        } finally {
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