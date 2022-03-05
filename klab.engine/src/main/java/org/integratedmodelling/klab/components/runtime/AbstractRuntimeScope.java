package org.integratedmodelling.klab.components.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.dataflow.Actuator.Status;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.dataflow.DataflowHandler;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Provides the state needed at runtime that was previously in (stateful) actuators. All other
 * functionalities are in the derived classes.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractRuntimeScope extends DataflowHandler implements IRuntimeScope {

    Dataflow dataflow;
    Scale resolutionScale;
    ResolutionScope resolutionScope;
    boolean autoStartTransitions = false;
    Map<IActuator, IScale> partialScales;
    Map<IActuator, Status> actuatorStatus;
    Map<IActuator, Set<IObservation>> actuatorProducts;
    IMonitor monitor;
    /**
     * This is used by characterizing models to report the result of characterization during their
     * own contextualization, which happens during <em>resolution</em> of dependencies with abstract
     * predicates.
     */
    Map<IConcept, Collection<IConcept>> concreteIdentities;

    // observables for which change is not specified but may change if they
    // depend on changed observations
    Set<IObservedConcept> implicitlyChangingObservables;

    // cache for IS operator in groovy expressions, both for proper subsumption and
    // correlations such as "adopts role/trait"
    protected LoadingCache<String, Boolean> reasonerCache;
    protected LoadingCache<String, Boolean> relatedReasonerCache;

    protected AbstractRuntimeScope(Dataflow dataflow, IResolutionScope resolutionScope, IMonitor monitor) {
        this.resolutionScope = (ResolutionScope) resolutionScope;
        this.dataflow = dataflow;
        this.monitor = monitor;
        this.partialScales = Collections.synchronizedMap(new HashMap<>());
        this.actuatorStatus = Collections.synchronizedMap(new HashMap<>());
        this.actuatorProducts = Collections.synchronizedMap(new HashMap<>());
        this.implicitlyChangingObservables = Collections.synchronizedSet(new HashSet<>());
        this.concreteIdentities = Collections.synchronizedMap(new HashMap<>());

        // cache for groovy IS operator in this context
        this.reasonerCache = CacheBuilder.newBuilder().maximumSize(2048)
                .build(new CacheLoader<String, Boolean>(){
                    @Override
                    public Boolean load(String key) throws Exception {
                        String[] split = key.split(";");
                        IConcept a = Concepts.c(split[0]);
                        IConcept b = Concepts.c(split[1]);
                        return a.is(b);
                    }
                });

        this.relatedReasonerCache = CacheBuilder.newBuilder().maximumSize(2048)
                .build(new CacheLoader<String, Boolean>(){
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

    protected AbstractRuntimeScope(AbstractRuntimeScope scope) {
        super(scope);
        this.putAll(scope);
        this.resolutionScale = scope.resolutionScale;
        this.resolutionScope = scope.resolutionScope;
        this.autoStartTransitions = scope.autoStartTransitions;
        this.partialScales = scope.partialScales;
        this.actuatorStatus = scope.actuatorStatus;
        this.actuatorProducts = scope.actuatorProducts;
        this.dataflow = scope.dataflow;
        this.implicitlyChangingObservables = scope.implicitlyChangingObservables;
        this.reasonerCache = scope.reasonerCache;
        this.relatedReasonerCache = scope.relatedReasonerCache;
    }

    @Override
    public IResolutionScope getResolutionScope() {
        return resolutionScope;
    }

    @Override
    public void setMergedScale(IActuator actuator, IScale scale) {
        this.partialScales.put(actuator, scale);
    }

    @Override
    public IScale getMergedScale(IActuator actuator) {
        return this.partialScales.get(actuator);
    }

    @Override
    public IScale getResolutionScale() {

        if (this.resolutionScale == null && resolutionScope != null) {
            this.resolutionScale = resolutionScope.getScale();
            if (dataflow.occurs() && this.resolutionScale.getTime() != null) {
                ITime time = this.resolutionScale.getTime();
                if (time.isGeneric() || time.size() == 1) {

                    if (time.getStart() == null || time.getEnd() == null) {
                        throw new KlabContextualizationException(
                                "cannot contextualize occurrents (processes and events) without a specified temporal extent");
                    }

                    /*
                     * Turn time into a 1-step grid (so size = 2). The scheduler will do the rest.
                     */
                    this.resolutionScale = Scale.substituteExtent(this.resolutionScale,
                            ((Time) time).upgradeForOccurrents());
                }

                // set the dataflow to autostart transitions if we only have one
                if (this.resolutionScale.getTime().size() >= 2) {
                    autoStartTransitions = true;
                }
            }
        }
        return this.resolutionScale;
    }

    @Override
    public Status getStatus(IActuator actuator) {
        Status ret = actuatorStatus.get(actuator);
        if (ret == null) {
            ret = new Status();
            actuatorStatus.put(actuator, ret);
        }
        return ret;
    }

    // @Override
    // public void setDependencyGraph(Graph<IObservedConcept, DefaultEdge> graph) {
    // this.dependencyGraph = graph;
    // }

    @Override
    public Graph<IObservedConcept, DefaultEdge> getDependencyGraph() {
        return this.dataflow == null
                ? new DefaultDirectedGraph<>(DefaultEdge.class)
                : this.dataflow.getDependencyGraph();
    }

    @Override
    public Collection<IObservation> getActuatorProducts(IActuator actuator) {
        Set<IObservation> ret = this.actuatorProducts.get(actuator);
        if (ret == null) {
            ret = new LinkedHashSet<>();
            this.actuatorProducts.put(actuator, ret);
        }
        return ret;
    }

    @Override
    public Set<IObservedConcept> getImplicitlyChangingObservables() {
        return implicitlyChangingObservables;
    }

    public Element findDataflowElement(String nodeId) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getNodeId(IContextualizable resource) {
        // TODO Auto-generated method stub
        return null;
    }

}
