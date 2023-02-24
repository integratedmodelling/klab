package org.integratedmodelling.klab.api.services.reasoner.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.integratedmodelling.klab.api.data.KMetadata;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.services.KReasoner;

public class Concept implements Serializable, KConcept {

    private static final long serialVersionUID = -6871573029225503370L;
    private long id;
    private String urn;
    private KMetadata metadata;
    private Set<SemanticType> type;
    
    transient private KReasoner reasonerService;

    @Override
    public String getUrn() {
        return urn;
    }

    @Override
    public Collection<SemanticType> getType() {
        return type;
    }

    @Override
    public boolean is(KConcept other) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean is(SemanticType type) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KMetadata getMetadata() {
        return metadata;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public void setMetadata(KMetadata metadata) {
        this.metadata = metadata;
    }

    public void setType(Set<SemanticType> type) {
        this.type = type;
    }

}
