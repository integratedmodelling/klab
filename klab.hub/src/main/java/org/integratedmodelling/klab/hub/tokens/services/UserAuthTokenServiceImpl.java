package org.integratedmodelling.klab.hub.tokens.services;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.licenses.dto.JwtToken;
import org.integratedmodelling.klab.hub.payload.EngineProfileResource;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tokens.commands.CreateUserAuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.commands.DeleteAuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.integratedmodelling.klab.hub.tokens.enums.TokenType;
import org.integratedmodelling.klab.hub.tokens.exceptions.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.users.commands.GetUserProfile;
import org.integratedmodelling.klab.hub.users.dto.ProfileResource;
import org.integratedmodelling.klab.hub.users.dto.User;
import org.integratedmodelling.klab.hub.users.payload.LoginResponse;
import org.integratedmodelling.klab.hub.users.payload.LogoutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;

@Service
public class UserAuthTokenServiceImpl implements UserAuthTokenService {

    protected static final Logger logger = LoggerFactory.getLogger(UserAuthTokenServiceImpl.class);

//    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private TokenRepository tokenRepository;

    private ObjectMapper objectMapper;

    public UserAuthTokenServiceImpl(UserRepository userRepository, TokenRepository tokenRepository, ObjectMapper objectMapper) {
        super();
//        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.objectMapper = objectMapper;
    }

    private static final JwtToken JWT_TOKEN_FACTORY = new JwtToken();

    @Override
    public TokenAuthentication createToken(String username, TokenType type) {
        if (TokenType.auth == type) {
            TokenAuthentication token = null;
            Optional<User> user = userRepository.findByNameIgnoreCase(username);
            if (user.isPresent()) {
                token = new CreateUserAuthenticationToken(tokenRepository, user.get()).execute();
            }
            return token;
        } else {
            throw new AuthenticationFailedException("Incorrect workflow for creating token");
        }
    }

    @Override
    public void deleteToken(String tokenString) {
        try {
            new DeleteAuthenticationToken(tokenRepository, tokenString).execute();
        } catch (MongoException e) {
            logger.error(e.getMessage(), e);
            throw new KlabException("Error deleting token, " + e.getMessage(), e);
        }
    }

    private void deleteExpiredTokens(String username) {
        List<TokenAuthentication> dbTokens = tokenRepository.findByUsername(username);
        for(TokenAuthentication dbToken : dbTokens) {
            if (dbToken.isExpired()) {
                deleteToken(dbToken.getTokenString());
            }
        }
    }

    @Override
    public TokenAuthentication getUserAuthenticationToken(String username, String password) {
        Authentication authRequest = new UsernamePasswordAuthenticationToken(username, password);
//        try {
//            authRequest = authenticationManager.authenticate(authRequest);
//        } catch (AuthenticationException e) {
//            throw new LoginFailedExcepetion(username);
//        }
//        if (!authRequest.isAuthenticated()) {
//            String msg = "Something went wrong with authentication. Result.isAuthenticated() == false, but no exception was thrown.";
//            throw new KlabException(msg);
//        } else {
            deleteExpiredTokens(username);
            TokenAuthentication result = createToken(username, TokenType.auth);
            PreAuthenticatedAuthenticationToken secureToken = new PreAuthenticatedAuthenticationToken(result.getPrincipal(),
                    result.getCredentials(), result.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(secureToken);
            return result;
//        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public LoginResponse< ? > getAuthResponse(String username, String password, boolean remote) {
        TokenAuthentication token = getUserAuthenticationToken(username, password);
        ProfileResource profile = new GetUserProfile(userRepository, username, objectMapper).execute();
         if (remote) {
            profile.setJwtToken(JWT_TOKEN_FACTORY.createEngineJwtToken(profile));
            return new LoginResponse<EngineProfileResource>(token, new EngineProfileResource(profile));
        } else {
            return new LoginResponse<ProfileResource>(token, profile.getSafeProfile());
        }
    }

    @Override
    public LogoutResponse getLogoutResponse(String token) {
        deleteToken(token);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new LogoutResponse(username);
    }

}
