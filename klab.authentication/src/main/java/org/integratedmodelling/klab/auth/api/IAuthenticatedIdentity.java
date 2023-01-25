package org.integratedmodelling.klab.auth.api;

import java.util.Collection;

import org.integratedmodelling.klab.auth.Role;

/**
 * Convenience interface for an identity that carries roles.
 * 
 * @author Ferd
 *
 */
public interface IAuthenticatedIdentity {

    /**
     * The set of roles played by this identity.
     * 
     * @return
     */
    Collection<Role> getRoles();
}
