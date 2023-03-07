package org.integratedmodelling.klab.knowledge;

import org.integratedmodelling.klab.api.knowledge.KSemantics;
import org.integratedmodelling.klab.api.knowledge.SemanticType;

public class Observable implements KSemantics {

    private static final long serialVersionUID = 6188649888474774359L;

    private Concept semantics;

    @Override
    public String getUrn() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KSemantics semantics() {
        return semantics;
    }

    @Override
    public KSemantics domain() {
        // TODO Auto-generated method stub
        return null;
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
    public boolean isAbstract() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getReferenceName() {
        // TODO Auto-generated method stub
        return null;
    }
}
