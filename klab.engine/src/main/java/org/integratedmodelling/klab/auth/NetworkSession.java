package org.integratedmodelling.klab.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.rest.NodeReference;
import org.integratedmodelling.klab.Auth;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.INodeIdentity;
import org.integratedmodelling.klab.api.auth.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Network session. Implements UserDetails to be directly usable as a principal in Spring security.
 * 
 * @author ferdinando.villa
 *
 */
public class NetworkSession implements INetworkSessionIdentity, UserDetails {

    private static final long serialVersionUID = -6806989886331624218L;

    INodeIdentity parent;
    String token;
    List<INodeIdentity> nodes = new ArrayList<>();
    Set<GrantedAuthority> authorities = new HashSet<>();
    
    public NetworkSession(String token, List<NodeReference> nodes, Node node) {
        this.token = token;
        this.parent = node;
        for (NodeReference n : nodes) {
            this.nodes.add(new Node(n, Auth.INSTANCE.requirePartner(n.getPartner())));
        }
        this.authorities.add(new SimpleGrantedAuthority(Roles.NETWORK_SESSION));
    }

    @Override
    public String getId() {
        return token;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.NETWORK_SESSION;
    }


    @Override
    public <T extends IIdentity> T getParentIdentity(Class<T> type) {
        return IIdentity.findParent(this, type);
    }

    @Override
    public INodeIdentity getParentIdentity() {
        return parent;
    }

    @Override
    public Collection<INodeIdentity> getNodes() {
        return nodes;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return getId();
    }

    @Override
    public String getUsername() {
        return getParentIdentity(INodeIdentity.class).getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
