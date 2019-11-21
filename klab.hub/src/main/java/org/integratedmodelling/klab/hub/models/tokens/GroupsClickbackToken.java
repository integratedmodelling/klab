package org.integratedmodelling.klab.hub.models.tokens;

import java.util.List;

import org.integratedmodelling.klab.hub.config.TokenClickbackConfig;

public class GroupsClickbackToken extends ClickbackToken {

    private static final long serialVersionUID = -6813740740798681807L;
        
    List<String> groups;

	public GroupsClickbackToken(String username) {
        super(username);
    }

    @Override
    public String getSuccessUrl(TokenClickbackConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.groups;
    }

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
    
}