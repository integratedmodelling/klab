package org.integratedmodelling.klab.model;

import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.api.knowledge.IConcept;

public class ConceptStatement extends KimObject {

    private static final long serialVersionUID = -1590834207034743591L;

    IConcept concept;
    
    public ConceptStatement(IKimStatement statement, IConcept concept) {
        super(statement);
        this.concept = concept;
    }

    public IConcept getConcept() {
        return this.concept;
    }
    
}
