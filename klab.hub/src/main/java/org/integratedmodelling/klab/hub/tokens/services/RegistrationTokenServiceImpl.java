package org.integratedmodelling.klab.hub.tokens.services;

import java.util.Optional;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.commands.CreateChangePasswordToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateLostPasswordToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateNewUserAccountToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateVerifyAccountToken;
import org.integratedmodelling.klab.hub.tokens.commands.CreateVerifyEmailToken;
import org.integratedmodelling.klab.hub.tokens.commands.DeleteAuthenticationToken;
import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.integratedmodelling.klab.hub.tokens.dto.TokenClickback;
import org.integratedmodelling.klab.hub.tokens.enums.TokenType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class RegistrationTokenServiceImpl implements RegistrationTokenService {
	
	private TokenRepository repository;
	private LinkConfig linkConfig;
	
	RegistrationTokenServiceImpl(TokenRepository repository,
			LinkConfig linkConfig) {
		this.repository = repository;
		this.linkConfig =  linkConfig;
	}

	@Override
	public TokenClickback createToken(String username, TokenType type) {
		if (type.equals(TokenType.verify)) {
			return new CreateVerifyAccountToken(repository, username, linkConfig).execute();
		} else if (type.equals(TokenType.password)) {
			return new CreateChangePasswordToken(repository, username, linkConfig).execute();
		} else if(type.equals(TokenType.lostPassword)) {
			return new CreateLostPasswordToken(repository, username, linkConfig).execute();
		} else if(type.equals(TokenType.verifyEmail)) {
			return new CreateVerifyEmailToken(repository, username, linkConfig).execute();
		} else {
			return null;
		}
	}
	
	@Override
	public TokenClickback createToken(String username, String email, TokenType type) {
		if(type.equals(TokenType.verifyEmail)) {
			return new CreateVerifyEmailToken(repository, username, email, linkConfig).execute();
		} else {
			return null;
		}
	}

	@Override
	public TokenClickback createChildToken(String username, String parentToken, TokenType type) {
		if (type.equals(TokenType.newUser)) {
			return new CreateNewUserAccountToken(repository, username, linkConfig).execute();
		} else {
			return null;
		}
	}

	@Override
	public boolean verifyToken(String username, String id, TokenType type) {
		return repository.findByTokenString(id)
			.filter(token -> token.getPrincipal().equals(username))
			.map(TokenClickback.class::cast)
			.filter(token -> token.getClickbackAction().getTokenType().equals(type))
			.map(token -> setAuthentication(token))
			.isPresent();
	}
	
	@Override
	public TokenAuthentication getAndVerifyToken(String username, String id, TokenType type) {
		return repository.findByTokenString(id)
			.filter(token -> token.getPrincipal().equals(username))
			.map(TokenClickback.class::cast)
			.filter(token -> token.getClickbackAction().getTokenType().equals(type)).orElseGet(null);
		
	}
	
	@Override
	public boolean verifyTokens(String username, String id, TokenType ...types) {
		for (TokenType type: types) {
			if (verifyToken(username, id, type)) {
				return true;
			}
		}
		return false;
	}
	private TokenAuthentication setAuthentication(TokenAuthentication token) {
		PreAuthenticatedAuthenticationToken secureToken = new PreAuthenticatedAuthenticationToken(token.getPrincipal(),token.getCredentials(),token.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(secureToken);
		return token;
	}

	@Override
	public void deleteToken(String tokenString) {
		new DeleteAuthenticationToken(repository, tokenString).execute();
	}

}
