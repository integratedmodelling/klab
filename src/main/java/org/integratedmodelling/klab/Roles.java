package org.integratedmodelling.klab;

import java.util.Collection;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.services.IRoleService;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.owl.OWL;

public enum Roles implements IRoleService {

    INSTANCE;

    @Override
    public Collection<IConcept> getRoles(IConcept concept) {
        return OWL.INSTANCE.getRestrictedClasses(concept, Concepts.p(NS.HAS_ROLE_PROPERTY));
    }

    @Override
    public boolean hasRole(IConcept type, IConcept role) {
        for (IConcept c : getRoles(type)) {
            if (c.is(role)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean hasParentRole(IConcept type, IConcept role) {

        for (IConcept c : getRoles(type)) {
            if (role.is(c)) {
                return true;
            }
        }

        return false;
    }
}
