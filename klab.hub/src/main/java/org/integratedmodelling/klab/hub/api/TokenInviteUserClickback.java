package org.integratedmodelling.klab.hub.api;

import java.util.Set;

import org.integratedmodelling.klab.hub.config.LinkConfig;

import com.sun.istack.NotNull;

public class TokenInviteUserClickback extends TokenClickback{

	private static final long serialVersionUID = -7290369988203926731L;
	
	@NotNull
	private Set<GroupEntry> entries;

	public TokenInviteUserClickback(String username) {
        super(username);
    }

    @Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.invite;
    }

	public Set<GroupEntry> getEntries() {
		return entries;
	}

	public void setGroups(Set<GroupEntry> entries) {
		this.entries = entries;
	}
}
