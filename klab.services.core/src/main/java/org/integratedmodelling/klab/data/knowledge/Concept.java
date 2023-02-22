package org.integratedmodelling.klab.data.knowledge;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.knowledge.IConcept;

/**
 * Client-side, serializable IConcept supported by a semantic service.
 * 
 * @author Ferd
 *
 */
public class Concept implements /* IConcept, */ Serializable {
    
    private static final long serialVersionUID = -777359428688048997L;

    private long id; // definition hash code
    private String definition;
    private EnumSet<IKimConcept.Type> type;
    

    public long getId() {
        return -1;
    }
    
    public String getDefinition() {
        return null;
    }
    
    public Collection<IKimConcept.Type> getType() {
        return null;
    }

    public boolean is(IConcept other) {
        // use the reasoner service, cache the result vs. the definitions
        return false;
    }

    public boolean is(IKimConcept.Type type) {
        return getType().contains(type);
    }

}
