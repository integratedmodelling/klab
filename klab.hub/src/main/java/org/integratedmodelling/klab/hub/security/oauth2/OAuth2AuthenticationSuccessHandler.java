//package org.integratedmodelling.klab.hub.security.oauth2;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.integratedmodelling.klab.hub.config.LinkConfig;
//import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
//import org.integratedmodelling.klab.hub.tokens.enums.TokenType;
//import org.integratedmodelling.klab.hub.tokens.services.UserAuthTokenService;
//import org.integratedmodelling.klab.hub.users.dto.ProfileResource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Component
//public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    UserAuthTokenService userAuthTokenService;
//
//    @Autowired
//    LinkConfig tokenClickbackConfig;
//
//    @Autowired
//    OAuth2AuthenticationSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
//        this.objectMapper = messageConverter.getObjectMapper();
//    }
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//            throws IOException, ServletException {
//        OAuth2AuthenticationToken oAuthToken = (OAuth2AuthenticationToken) authentication;
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json");
//        ProfileResource profile = (ProfileResource) authentication.getPrincipal();
//        TokenAuthentication token = userAuthTokenService.createToken(profile.getUsername(), TokenType.auth);
//        String profileString = objectMapper.writeValueAsString(profile);
//        response.setHeader("Authorization", token.getTokenString());
//        String redirect = String.format("%s/%s?token=%s", tokenClickbackConfig.getCallbackUrl().toString(),
//                oAuthToken.getAuthorizedClientRegistrationId(), token.getTokenString());
//        response.sendRedirect(redirect);
//        PrintWriter out = response.getWriter();
//        out.print(profileString);
//        out.flush();
//    }
//
//}