package org.integratedmodelling.klab.api.model.contextualization;

import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * A contextualizer builds the observation of an observable in a context based on what the provenance model
 * implies. There are two types of contextualizers: those that <i>explain</i> a single instance of an
 * observation and those that <i>instantiate</> zero or more observations (to be explained by other
 * contextualizers). These have additional methods and correspond to {@link IResolver} and
 * {@link IInstantiator} respectively.
 * 
 * Contextualizers can be contributed by components and are managed by {@link IActuator}s during the execution
 * of {@link IDataflow}s. In KDL dataflow specifications, contextualizers are called in <code> compute </code>
 * statements.
 * 
 * To provide a new contextualizer, extend one of the non-abstract child interfaces and provide the
 * specifications of its k.LAB identity using a KDL file specifying a component definition.
 * 
 * The engine implementation is expected to provide appropriate instantiators and resolvers for every observation
 * type.
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface IContextualizer {

    /**
     * Called once when the contextualizer is instantiated. It is the first function called in any instance of
     * a contextualizer.
     * 
     * @param actuator
     *            the actuator that uses this contextualizer, giving access to inputs and outputs with their
     *            expected dependency names. If this is called, all required dependencies are validated.
     * @param provenance
     *            the provenance node, giving access to the observable and the model.
     * @param monitor
     *            a monitor for communicating with the view.
     */
    void setRuntimeContext(IActuator actuator, IProvenance.Artifact provenance, IMonitor monitor);

}
