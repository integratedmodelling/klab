package org.integratedmodelling.klab.hub.api;

public enum ClickbackAction {
    activate(
            TokenType.verify),
    password(
            TokenType.password),
    groups(
    		TokenType.groups),
    invite(
    		TokenType.invite),
    newUser(
    		TokenType.newUser),
    lostPassword(
    		TokenType.lostPassword);
    
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