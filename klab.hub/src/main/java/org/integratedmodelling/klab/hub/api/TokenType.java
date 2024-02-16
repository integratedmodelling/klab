package org.integratedmodelling.klab.hub.api;

public enum TokenType {
    verify(
    		TokenVerifyAccountClickback.class),
    auth(
            TokenAuthentication.class),
    password(
            TokenChangePasswordClickback.class),
	invite(
			TokenInviteUserClickback.class),
	newUser(
			TokenNewUserClickback.class),
	lostPassword(
			TokenLostPasswordClickback.class),
	lever(
			TokenLever.class), 
	verifyEmail(
			TokenVerifyEmailClickback.class);

    private final Class<? extends TokenAuthentication> clazz;

    public Class<? extends TokenAuthentication> getClazz() {
        return clazz;
    }

    TokenType(Class<? extends TokenAuthentication> clazz) {
        this.clazz = clazz;
    }
}
