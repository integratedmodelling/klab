package org.integratedmodelling.klab.hub.api;

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