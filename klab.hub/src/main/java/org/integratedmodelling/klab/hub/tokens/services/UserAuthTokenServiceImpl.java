package org.integratedmodelling.klab.hub.tokens.services;

import java.util.List;

import java.util.Optional;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.payload.LoginResponse;
import org.integratedmodelling.klab.hub.payload.LogoutResponse;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.repository.UserRepository;
import org.integratedmodelling.klab.hub.tokens.AuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.TokenType;
import org.integratedmodelling.klab.hub.tokens.commands.CreateUserAuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.commands.DeleteAuthenticationToken;
import org.integratedmodelling.klab.hub.users.ProfileResource;
import org.integratedmodelling.klab.hub.users.User;
import org.integratedmodelling.klab.hub.users.commands.GetUserProfile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserAuthTokenServiceImpl implements UserAuthTokenService{
	
	private AuthenticationManager authenticationManager;
	
	private UserRepository userRepository;
	
	private TokenRepository tokenRepository;
	
	private ObjectMapper objectMapper;
	
	public UserAuthTokenServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			TokenRepository tokenRepository, ObjectMapper objectMapper) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.objectMapper = objectMapper;
	}

	@Override
	public AuthenticationToken createToken(String username, TokenType type) {
		if(TokenType.auth == type) {
			AuthenticationToken token = null;
			Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
			if(user.isPresent()) {
				token = new CreateUserAuthenticationToken(tokenRepository, user.get()).execute();
			}
			return token;
		} else {
			throw new AuthenticationFailedException("Incorrect workflow for creating token");
		}
	}

	@Override
	public AuthenticationToken createChildToken(String username, String parentToken, TokenType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean verifyToken(String username, String tokenString, TokenType type) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean verifyTokens(String username, String tokenString, TokenType... verify) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteToken(String tokenString) {
		new DeleteAuthenticationToken(tokenRepository, tokenString).execute();
	}
	
	private void deleteExpiredTokens(String username) {
		List<AuthenticationToken> dbTokens = tokenRepository.findByUsername(username);
		for (AuthenticationToken dbToken : dbTokens) {
			if (dbToken.isExpired()) {
				deleteToken(dbToken.getTokenString());
			}
		}
	}

	@Override
	public AuthenticationToken getUserAuthenticationToken(String username, String password) {
		Authentication authRequest = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authRequest = authenticationManager.authenticate(authRequest);
		} catch (AuthenticationException e) {
			throw new BadRequestException("Username or password incorrect.");
		}
		if (!authRequest.isAuthenticated()) {
			String msg = "Something went wrong with authentication. Result.isAuthenticated() == false, but no exception was thrown.";
			throw new KlabException(msg);
		} else {
			deleteExpiredTokens(username);
			AuthenticationToken result = createToken(username, TokenType.auth);
			SecurityContextHolder.getContext().setAuthentication(result);
			return result;
		}
	}

	@Override
	public LoginResponse getAuthResponse(String username, String password) {
		AuthenticationToken token = getUserAuthenticationToken(username, password);
		ProfileResource profile = new GetUserProfile(userRepository, username, objectMapper).execute();
		LoginResponse response = new LoginResponse(token, profile.getSafeProfile());
		return response;
	}

	@Override
	public LogoutResponse getLogoutResponse(String token) {
		deleteToken(token);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return new LogoutResponse(username);
	}

}
