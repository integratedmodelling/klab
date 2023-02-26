package org.integratedmodelling.klab.api.services.reasoner.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.integratedmodelling.klab.api.data.KMetadata;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KSemantics;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.services.KReasoner;

public class Concept implements Serializable, KConcept {

    private static final long serialVersionUID = -6871573029225503370L;
    private long id;
    private String urn;
    private KMetadata metadata;
    private Set<SemanticType> type;
    private String namespace;
    
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
    public boolean is(KSemantics other) {
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

    @Override
    public KSemantics semantics() {
        return this;
    }

    @Override
    public boolean isAbstract() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<KConcept> operands() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> children() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> parents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> allChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> allParents() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> closure() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KSemantics domain() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept parent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

}
