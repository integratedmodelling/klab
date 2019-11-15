package org.integratedmodelling.klab.hub.models.tokens;

public enum ClickbackAction {
    activate(
            TokenType.activate),
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

    public Class<? extends AuthenticationToken> getClazz() {
        return tokenType.getClazz();
    }

    ClickbackAction(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}