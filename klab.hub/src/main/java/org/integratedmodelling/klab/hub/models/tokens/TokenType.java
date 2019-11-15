package org.integratedmodelling.klab.hub.models.tokens;

public enum TokenType {
    activate(
            ActivateAccountClickbackToken.class),
    auth(
            AuthenticationToken.class),
    engine(
            EngineToken.class),
    password(
            ChangePasswordClickbackToken.class),
    groups(
    		GroupsClickbackToken.class),
	invite(
			InviteUserClickbackToken.class),
	newUser(
			NewUserClickbackToken.class),
	lostPassword(
			LostPasswordClickbackToken.class);

    private final Class<? extends AuthenticationToken> clazz;

    public Class<? extends AuthenticationToken> getClazz() {
        return clazz;
    }

    TokenType(Class<? extends AuthenticationToken> clazz) {
        this.clazz = clazz;
    }
}
