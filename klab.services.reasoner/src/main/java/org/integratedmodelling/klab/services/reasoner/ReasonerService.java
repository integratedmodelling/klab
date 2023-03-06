package org.integratedmodelling.klab.services.reasoner;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.KSemantics;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement;
import org.integratedmodelling.klab.api.lang.kim.KKimObservable;
import org.integratedmodelling.klab.api.lang.kim.KKimScope;
import org.integratedmodelling.klab.api.services.KReasoner;
import org.integratedmodelling.klab.api.services.KResources;
import org.integratedmodelling.klab.services.reasoner.internal.SemanticTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class ReasonerService implements KReasoner, KReasoner.Admin {

    @Autowired
    KResources resourceService;

    @Autowired
    SemanticTranslator semanticTranslator;

    /**
     * Caches for concepts and observables, linked to the URI in the corresponding
     * {@link KKimScope}.
     */
    LoadingCache<String, KConcept> concepts = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, KConcept>(){
                public KConcept load(String key) {
                    KKimConcept parsed = resourceService.resolveConcept(key);
                    return semanticTranslator.defineConcept(parsed);
                }
            });

    LoadingCache<String, KObservable> observables = CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, KObservable>(){
                public KObservable load(String key) { // no checked exception
                    KKimObservable parsed = resourceService.resolveObservable(key);
                    return semanticTranslator.defineObservable(parsed);
                }
            });

    @Autowired
    public ReasonerService(KResources resourceService, SemanticTranslator semanticTranslator) {
        this.resourceService = resourceService;
    }

    @Override
    public KConcept addConcept(KKimConceptStatement statement) {
        return null;
    }

    @Override
    public KConcept resolveConcept(String definition) {
        try {
            return concepts.get(definition);
        } catch (ExecutionException e) {
            return errorConcept(definition);
        }
    }

    @Override
    public KObservable resolveObservable(String definition) {
        try {
            return observables.get(definition);
        } catch (ExecutionException e) {
            return errorObservable(definition);
        }
    }

    private KObservable errorObservable(String definition) {
        // TODO Auto-generated method stub
        return null;
    }

    private KConcept errorConcept(String definition) {
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

}
