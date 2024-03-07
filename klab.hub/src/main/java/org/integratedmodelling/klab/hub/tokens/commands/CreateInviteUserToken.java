package org.integratedmodelling.klab.hub.tokens.commands;

import java.util.Set;

import org.integratedmodelling.klab.hub.groups.dto.GroupEntry;
import org.integratedmodelling.klab.hub.repository.TokenRepository;
import org.integratedmodelling.klab.hub.tokens.dto.TokenInviteUserClickback;

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
