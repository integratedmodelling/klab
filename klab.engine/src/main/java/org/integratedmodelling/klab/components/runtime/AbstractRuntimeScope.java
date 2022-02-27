package org.integratedmodelling.klab.components.runtime;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.dataflow.Actuator.Status;
import org.integratedmodelling.klab.dataflow.ContextualizationStrategy;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Provides the state needed at runtime that was previously in (stateful) actuators. All other
 * functionalities are in the derived classes.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractRuntimeScope extends Parameters<String> implements IRuntimeScope {

    Dataflow dataflow;
    Scale resolutionScale;
    ResolutionScope resolutionScope;
    boolean autoStartTransitions = false;
    Map<IActuator, IScale> runtimeScales;
    Map<IActuator, IScale> partialScales;
    Map<IActuator, Status> actuatorStatus;
    Map<IActuator, Set<IObservation>> actuatorProducts;
    private Graph<IObservedConcept, DefaultEdge> dependencyGraph;
    ContextualizationStrategy contextualizationStrategy;
    IMonitor monitor;

    protected AbstractRuntimeScope(Dataflow dataflow, IResolutionScope resolutionScope, IMonitor monitor) {
        this.resolutionScope = (ResolutionScope) resolutionScope;
        this.dataflow = dataflow;
        this.monitor = monitor;
        this.runtimeScales = Collections.synchronizedMap(new HashMap<>());
        this.partialScales = Collections.synchronizedMap(new HashMap<>());
        this.actuatorStatus = Collections.synchronizedMap(new HashMap<>());
        this.actuatorProducts = Collections.synchronizedMap(new HashMap<>());
        this.contextualizationStrategy = ((ResolutionScope) resolutionScope).getContextualizationStrategy();
    }

    protected AbstractRuntimeScope(AbstractRuntimeScope scope) {
        this.putAll(scope);
        this.resolutionScale = scope.resolutionScale;
        this.resolutionScope = scope.resolutionScope;
        this.autoStartTransitions = scope.autoStartTransitions;
        this.partialScales = scope.partialScales;
        this.actuatorStatus = scope.actuatorStatus;
        this.actuatorProducts = scope.actuatorProducts;
        this.dependencyGraph = scope.dependencyGraph;
        this.dataflow = scope.dataflow;
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
    public void setRuntimeScale(IActuator actuator, IScale scale) {
        this.runtimeScales.put(actuator, scale);
    }

    @Override
    public IScale getRuntimeScale(IActuator actuator) {
        return this.runtimeScales.get(actuator);
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

    @Override
    public void setDependencyGraph(Graph<IObservedConcept, DefaultEdge> graph) {
        this.dependencyGraph = graph;
    }

    @Override
    public Graph<IObservedConcept, DefaultEdge> getDependencyGraph() {
        return this.dependencyGraph;
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
    public ContextualizationStrategy getContextualizationStrategy() {
        return contextualizationStrategy;
    }

}
