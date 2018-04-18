package org.integratedmodelling.klab.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.IPartnerIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.rest.resources.NodeReference;

public class Node implements INodeIdentity {

    String name;
    IPartnerIdentity parent;
    List<String> urls = new ArrayList<>();
    Set<Permission> permissions = new HashSet<>();
    Date bootTime = new Date();
    boolean online;

    public Node(String name, IPartnerIdentity owner) {
        this.name = name;
        this.parent = owner;
    }

    public Node(NodeReference partnerNode, Partner partner) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Date getBootTime() {
        return bootTime;
    }

    @Override
    public IMonitor getMonitor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        return type == INodeIdentity.TYPE;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        return IIdentity.findParent(this, type);
    }

    @Override
    public IPartnerIdentity getParentIdentity() {
        return parent;
    }

    @Override
    public boolean isOnline() {
        // TODO set at beginning, remember last check, decide quarantine period and recheck intervals
        return online;
    }

    @Override
    public Set<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public Collection<String> getUrls() {
        return urls;
    }

    public void setOnline(boolean b) {
        this.online = b;
    }
}
