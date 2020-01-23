package org.integratedmodelling.klab.hub.tokens;

import java.util.List;

import org.integratedmodelling.klab.hub.config.LinkConfig;
import org.integratedmodelling.klab.hub.users.GroupEntry;

public class GroupsClickbackToken extends ClickbackToken {

    private static final long serialVersionUID = -6813740740798681807L;
        
    List<GroupEntry> groups;

	public GroupsClickbackToken(String username) {
        super(username);
    }

    @Override
    public String getSuccessUrl(LinkConfig tokenClickbackConfig) {
        return null;
    }

    @Override
    public ClickbackAction getClickbackAction() {
        return ClickbackAction.groups;
    }

	public List<GroupEntry> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupEntry> groups) {
		this.groups = groups;
	}
    
}