package org.integratedmodelling.klab.hub.commands;

import java.util.Set;

import org.integratedmodelling.klab.hub.api.GroupEntry;
import org.integratedmodelling.klab.hub.api.TokenInviteUserClickback;
import org.integratedmodelling.klab.hub.repository.TokenRepository;

public class CreateInviteUserToken extends CreateTokenCommand {

	private TokenRepository repo;
	private String invitee;
	private Set<GroupEntry> entries;
	
	public CreateInviteUserToken(TokenRepository repo, String invitee, Set<GroupEntry> entries) {
		super();
		this.repo = repo;
		this.invitee = invitee;
		this.entries = entries;
	}

	@Override
	public TokenInviteUserClickback execute() {
		TokenInviteUserClickback token = new TokenInviteUserClickback(invitee);
		token.setGroups(entries);
		return token;
	}

}
