package org.integratedmodelling.klab.services.reasoner;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.KSemantics;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement;
import org.integratedmodelling.klab.api.services.KReasoner;
import org.integratedmodelling.klab.api.services.KResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReasonerService implements KReasoner, KReasoner.Admin {

    @Autowired
    KResources resourceService;
    
    @Override
    public KConcept resolveConcept(String definition) {
        return null;
    }

    @Override
    public KObservable resolveObservable(String definition) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> operands(KSemantics target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> children(KSemantics target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> parents(KSemantics target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> allChildren(KSemantics target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> allParents(KSemantics target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> closure(KSemantics target) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int semanticDistance(KSemantics target) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int semanticDistance(KSemantics target, KSemantics context) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public KConcept coreObservable(KConcept first) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Pair<KConcept, List<SemanticType>> splitOperators(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<KConcept> traits(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int assertedDistance(KConcept kConcept, KConcept t) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean hasTrait(KConcept concept, KConcept t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Collection<KConcept> roles(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasRole(KConcept concept, KConcept t) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public KConcept directContext(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept context(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directInherent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept inherent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directGoal(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept goal(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directCooccurrent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directCausant(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directCaused(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directAdjacent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directCompresent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept directRelativeTo(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept cooccurrent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept causant(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept caused(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept adjacent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept compresent(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KConcept relativeTo(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object displayLabel(KSemantics concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object codeName(KSemantics concept) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String style(KConcept concept) {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public Capabilities getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public KConcept addConcept(KKimConceptStatement statement) {
        // TODO Auto-generated method stub
        return null;
    }
}
