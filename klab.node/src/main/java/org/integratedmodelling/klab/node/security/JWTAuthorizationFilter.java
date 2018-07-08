package org.integratedmodelling.klab.node.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.integratedmodelling.klab.node.auth.AuthenticationToken;
import org.integratedmodelling.klab.node.auth.NodeAuth;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        String tokenString = req.getHeader(HttpHeaders.WWW_AUTHENTICATE);
        if (tokenString != null) {
            try {
                AuthenticationToken token = NodeAuth.INSTANCE.validateJwt(tokenString);
                // TODO validateJwt() will always either succeed or throw, right? if so, don't need another if()
                if (token != null && token.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            } catch (Throwable e) {
                logger.error("Failed to extract JWT token: ", e);
            }
        }
        chain.doFilter(req, res);
//        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
//            chain.doFilter(req, res);
//            return;
//        }
//
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {

//            String user = Jwts.parser().setSigningKey(NodeAuth.INSTANCE.getSecret().getBytes())
//                    .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();

            // TODO get group IDs from body, set into token as credentials, add authorities

//            if (user != null) {
//                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//            }
            return null;
        }
        return null;
    }
}
