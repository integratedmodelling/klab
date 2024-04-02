package org.integratedmodelling.klab.hub.tokens.enums;

import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;

public enum ClickbackAction {
    activate(
            TokenType.verify),
    password(
            TokenType.password),
    invite(
    		TokenType.invite),
    newUser(
    		TokenType.newUser),
    lostPassword(
    		TokenType.lostPassword),
    changeEmail(
    		TokenType.verifyEmail);
    
    private final TokenType tokenType;

    public TokenType getTokenType() {
        return tokenType;
    }

    public Class<? extends TokenAuthentication> getClazz() {
        return tokenType.getClazz();
    }

    ClickbackAction(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}