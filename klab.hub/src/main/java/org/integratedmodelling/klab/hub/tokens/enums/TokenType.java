package org.integratedmodelling.klab.hub.tokens.enums;

import org.integratedmodelling.klab.hub.tokens.dto.TokenAuthentication;
import org.integratedmodelling.klab.hub.tokens.dto.TokenChangePasswordClickback;
import org.integratedmodelling.klab.hub.tokens.dto.TokenInviteUserClickback;
import org.integratedmodelling.klab.hub.tokens.dto.TokenLostPasswordClickback;
import org.integratedmodelling.klab.hub.tokens.dto.TokenNewUserClickback;
import org.integratedmodelling.klab.hub.tokens.dto.TokenVerifyAccountClickback;
import org.integratedmodelling.klab.hub.tokens.dto.TokenVerifyEmailClickback;

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
