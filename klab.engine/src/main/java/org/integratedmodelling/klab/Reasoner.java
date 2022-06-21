package org.integratedmodelling.klab;

import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.services.IReasonerService;
import org.integratedmodelling.klab.extensions.groovy.model.Concept;
import org.integratedmodelling.klab.owl.KlabReasoner;
import org.integratedmodelling.klab.owl.Ontology;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public enum Reasoner implements IReasonerService {

    INSTANCE;

    private KlabReasoner reasoner;
    protected LoadingCache<String, Boolean> reasonerCache;
    protected LoadingCache<String, Boolean> relatedReasonerCache;

    private Reasoner() {
        Services.INSTANCE.registerService(this, IReasonerService.class);
        this.reasonerCache = CacheBuilder.newBuilder().maximumSize(2048).build(new CacheLoader<String, Boolean>(){
            @Override
            public Boolean load(String key) throws Exception {
                String[] split = key.split(";");
                IConcept a = Concepts.c(split[0]);
                IConcept b = Concepts.c(split[1]);
                return a.is(b);
            }
        });
        this.relatedReasonerCache = CacheBuilder.newBuilder().maximumSize(2048).build(new CacheLoader<String, Boolean>(){
            @Override
            public Boolean load(String key) throws Exception {
                String[] split = key.split(";");

                IConcept a = Concepts.c(split[0]);
                IConcept b = Concepts.c(split[1]);

                boolean ret = a.is(b);
                if (!ret && (b.is(Type.PREDICATE))) {
                    // TODO check for adoption
                }
                return ret;
            }
        });
    }

    public void setReasoner(KlabReasoner klabReasoner) {
        this.reasoner = klabReasoner;
    }

    public void addOntology(Ontology ontology) {
        this.reasoner.addOntology(ontology);
    }

    public Ontology getOntology() {
        return (Ontology) reasoner.getOntology();
    }

    @Override
    public boolean isSatisfiable(IConcept concept) {
        return reasoner.isSatisfiable(concept);
    }

    @Override
    public Set<IConcept> getParentClosure(IConcept main) {
        return reasoner.getParentClosure(main);
    }

    @Override
    public Set<IConcept> getSemanticClosure(IConcept main) {
        return reasoner.getSemanticClosure(main);
    }

    @Override
    public boolean implies(IConcept target, IConcept implied) {
        // TODO - and CACHED
        return false;
    }

    @Override
    public boolean implies(IConcept target, IConcept role, IObservation context) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean is(Object c1, Object c2) {
        if (c2 == null || c1 == null) {
            return false;
        }
        if (c1.equals(c2)) {
            return true;
        }
        try {
            return reasonerCache.get((c1 instanceof Concept ? ((Concept) c1).getConcept().toString() : c1.toString()) + ";"
                    + (c2 instanceof Concept ? ((Concept) c2).getConcept().toString() : c2.toString()));
        } catch (ExecutionException e) {
            return false;
        }
    }

    public boolean isRelated(Object c1, Object c2) {
        if (c2 == null || c1 == null) {
            return false;
        }
        try {
            return relatedReasonerCache.get((c1 instanceof Concept ? ((Concept) c1).getConcept().toString() : c1.toString()) + ";"
                    + (c2 instanceof Concept ? ((Concept) c2).getConcept().toString() : c2.toString()));
        } catch (ExecutionException e) {
            return false;
        }
    }

}