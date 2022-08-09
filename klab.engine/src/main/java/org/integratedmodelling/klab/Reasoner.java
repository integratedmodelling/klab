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
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.services.IReasonerService;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
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
    Map<IConcept, ConfigurationDescriptor> configurations = new HashMap<>();
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
        public Set<IObservable> triggerObservable;
        public LogicalConnector connector = null;
        public IObservable emergentObservable;
        public String namespaceId;
    }

    /**
     * These get recorded in context observations and updated by runtime scopes, which should call
     * update() on each of the notified configurations at each new observation in each context.
     * 
     * @author Ferd
     *
     */
    public class Configuration {

        Configuration configuration;
        Map<IConcept, IObservation> matched = new HashMap<>();
        boolean covered = false;
    }

    /**
     * These describe the configuration from the worldview, and get notified of all the triggers and
     * enablements connected to it by models.
     * 
     * @author Ferd
     *
     */
    class ConfigurationDescriptor {
        Set<IConcept> triggers = new HashSet<>();
        LogicalConnector connector = null;
        IConcept configuration;
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

                    } else {
                        throw new KlabValidationException("structural relationships can only create subjects or configurations");
                    }
                } else if (relationship.is(Type.FUNCTIONAL)) {
                    if (created.is(Type.PROCESS) || created.is(Type.CONFIGURATION)) {

                    } else {
                        throw new KlabValidationException("functional relationships can only create processes or configurations");
                    }
                } else {

                }
            }
        }
    }

    public void registerConfiguration(IKimConceptStatement statement, IConcept configuration) {

        if (!configuration.isAbstract()) {

            IConcept inherent = Observables.INSTANCE.getInherency(configuration);
            if (inherent != null) {

                ConfigurationDescriptor descriptor = new ConfigurationDescriptor();
                descriptor.configuration = configuration;

                if (inherent.is(Type.UNION)) {
                    descriptor.connector = LogicalConnector.UNION;
                    for (IConcept component : inherent.getOperands()) {
                        descriptor.triggers.add(component);
                    }
                } else if (inherent.is(Type.INTERSECTION)) {
                    descriptor.connector = LogicalConnector.INTERSECTION;
                    for (IConcept component : inherent.getOperands()) {
                        descriptor.triggers.add(component);
                    }
                } else {
                    descriptor.triggers.add(inherent);
                }

                this.configurations.put(configuration, descriptor);
            }
        }
    }

    /**
     * Call at each new observation (groups for instantiators). If a DirectObservation is passed,
     * this will use the cache in it to store partial matches and resolve them. If non-null is
     * returned, there is a new configuration and nothing is done in the observation except removing
     * any partial cache entries and avoiding multiple notifications.
     * 
     * @param context
     * @param newObservation
     * @param scope
     * @return
     */
    public Configuration detectConfiguration(IDirectObservation context, IObservation newObservation, IRuntimeScope scope) {

        Map<String, Configuration> cache = null;
        if (context instanceof DirectObservation) {
            cache = scope.getConfigurationCache();
        }
        return null;
    }

    void releaseNamespace(String namespaceId) {
        for (IConcept c : emergence.keySet()) {
            Emergence e = emergence.getValue(c);
            if (e.namespaceId.equals(namespaceId)) {
                emergence.remove(c);
            }
        }
    }

}