package org.integratedmodelling.klab.api.services;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.IConcept;

public interface IRoleService {

    /**
     * Retrieve any roles that were stated with the concept ('with role'). This only
     * applies to observables declared in models, as there is no other legal place to
     * declare observables with roles.
     * 
     * @param concept
     * @return any role adopted
     */
    Collection<IConcept> getRoles(IConcept concept);

    /**
     * Check for adoption of a particular role
     * @param type
     * @param role
     * @return true if type has the role
     */
    boolean hasRole(IConcept type, IConcept role);

    /**
     * True if k has a role R so that the passed role is-a R.
     * @param type 
     * @param role 
     *
     * @return true if type has a role of the passed type
     */
    boolean hasParentRole(IConcept type, IConcept role);

}
