package org.integratedmodelling.klab;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.services.IReasonerService;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.IntelligentMap;
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
    Map<IConcept, Emergence> emergent = new HashMap<>();
    IntelligentMap<Emergence> emergence = new IntelligentMap<>();

    /**
     * An emergence is the appearance of an observation triggered by another, under the assumptions
     * stated in the worldview. It applies to processes and relationships and its emergent
     * observable can be a configuration, subject or process.
     * 
     * @author Ferd
     *
     */
    public class Emergence {
        public Set<IConcept> triggerObservables = new HashSet<>();
        // TODO unclear how to specify the connector if all the trigger specs are in the trigger
        // concepts.
        public LogicalConnector connector = LogicalConnector.UNION;
        public IConcept emergentObservable;
        public String namespaceId;
    }

    class AttributeDescription {
        IObservable observable;
        Object value; // range, concept or number. Could be a complex value eventually.
    }

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
    public Collection<IObservation> getEmergentObservations(IObservation observation, IContextualizationScope scope) {
        return null;
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
    public IConcept getEmergentResolvable(IConcept relationship) {
        return null;
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
            return reasonerCache.get(c1.toString() + ";" + c2.toString());
        } catch (ExecutionException e) {
            return false;
        }
    }

    public boolean isRelated(Object c1, Object c2) {
        if (c2 == null || c1 == null) {
            return false;
        }
        try {
            return relatedReasonerCache.get(c1.toString() + ";" + c2.toString());
        } catch (ExecutionException e) {
            return false;
        }
    }

    public void registerRelationship(IKimConceptStatement statement, IConcept relationship) {
        if (!relationship.isAbstract()) {
            for (IConcept created : Observables.INSTANCE.getCreated(relationship)) {
                if (relationship.is(Type.STRUCTURAL)) {
                    if (created.is(Type.SUBJECT) || created.is(Type.CONFIGURATION)) {
                        if (registerEmergent(created)) {
                            notifyEmergenceTrigger(relationship, created);
                        }
                    } else {
                        throw new KlabValidationException("structural relationships can only create subjects or configurations");
                    }
                } else if (relationship.is(Type.FUNCTIONAL)) {
                    if (created.is(Type.PROCESS) || created.is(Type.CONFIGURATION)) {
                        if (registerEmergent(created)) {
                            notifyEmergenceTrigger(relationship, created);
                        }
                    } else {
                        throw new KlabValidationException("functional relationships can only create processes or configurations");
                    }
                } else {

                }
            }
        }
    }

    /*
     * this just registers the configuration; at this stage no triggers can exist so only the
     * configuration record is stored.
     */
    public boolean registerEmergent(IConcept configuration) {

        if (!configuration.isAbstract()) {

            if (this.emergent.containsKey(configuration)) {
                return true;
            }
            
            Emergence descriptor = new Emergence();
            descriptor.emergentObservable = configuration;
            this.emergent.put(configuration, descriptor);
            return true;
        }
        
        return false;
    }

    /**
     * Called when a quality 'creates' a configuration: ensures that the configuration record is
     * updated and the triggers are registered. 
     * 
     * @param concept
     * @param emergent
     */
    private void notifyEmergenceTrigger(IConcept trigger, IConcept emergent) {

        /*
         * find the record; if not found, exit
         */
        Emergence descriptor = this.emergent.get(emergent);
        if (descriptor == null) {
            return;
        }
        /*
         * update any triggers
         */
        analyzeEmergence(descriptor, trigger);
    }

    /**
     * Update the records that allow us to quickly know what emergent resolution to trigger as
     * observations are made.
     * 
     * @param descriptor
     */
    void analyzeEmergence(Emergence descriptor, IConcept trigger) {

        /*
         * go through the parents to inherit any missing trigger
         */

        /*
         * install missing triggers for detection
         */

    }

    void releaseNamespace(String namespaceId) {
        for (IConcept c : emergence.keySet()) {
            Emergence e = emergence.getValue(c);
            if (e.namespaceId.equals(namespaceId)) {
                emergence.remove(c);
            }
        }
    }

    public void registerQuality(IKimConceptStatement concept, Concept ret) {

        for (IConcept created : Observables.INSTANCE.getCreated(ret)) {
            if (!created.is(Type.CONFIGURATION)) {
                throw new KlabValidationException("qualities can only create configurations");
            }
            notifyEmergenceTrigger(ret, created);
        }

    }

}