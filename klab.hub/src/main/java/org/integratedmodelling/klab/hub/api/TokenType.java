package org.integratedmodelling.klab.hub.api;

public enum TokenType {
    verify(
    		TokenVerifyAccountClickback.class),
    auth(
            TokenAuthentication.class),
    engine(
            TokenEngine.class),
    password(
            TokenChangePasswordClickback.class),
    groups(
    		GroupsClickbackToken.class),
	invite(
			TokenInviteUserClickback.class),
	newUser(
			TokenNewUserClickback.class),
	lostPassword(
			TokenLostPasswordClickback.class);

    private final Class<? extends TokenAuthentication> clazz;

    public Class<? extends TokenAuthentication> getClazz() {
        return clazz;
    }

    TokenType(Class<? extends TokenAuthentication> clazz) {
        this.clazz = clazz;
    }
}
