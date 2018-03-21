package org.integratedmodelling.klab.engine.runtime.api;

import org.integratedmodelling.klab.api.data.raw.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;

/**
 * This API extends {@link IComputationContext} to add setters and other functionalities that is
 * needed at runtime. It is provided to allow any {@link IRuntimeProvider} to define contexts as
 * they need.
 * 
 * @author Ferd
 *
 */
public interface IRuntimeContext extends IComputationContext {

    /**
     * Create a child context for the passed observable, containing a new target observation
     * implementation. The observation is created with the same scale of the current target and the
     * current target is set as its parent.
     * 
     * @param target
     * @param namespace namespace where the target was resolved
     * @return the child context that will resolve the target
     */
    public IRuntimeContext createChild(IObservable target, INamespace namespace);

    /**
     * Create a child context for a previously instantiated observation, setting it as a target for
     * the computation. The passed observation must have been created using
     * {@link #newObservation(IObservable, org.integratedmodelling.kim.api.data.IGeometry)} or
     * {@link #newRelationship(IObservable, org.integratedmodelling.kim.api.data.IGeometry, IObjectArtifact, IObjectArtifact)}.
     * 
     * @param target
     * @param namespace namespace where the target was instantiated
     * @return the child context to resolve the target
     */
    public IRuntimeContext createChild(IObservation target, INamespace namespace);

    /**
     * Set the passed data object in the symbol table.
     * 
     * @param name
     * @param data
     */
    void setData(String name, IArtifact data);

    /**
     * Set POD data or parameters.
     * 
     * @param name
     * @param value
     */
    void set(String name, Object value);

    /**
     * 
     * @return
     */
    IConfigurationDetector getConfigurationDetector();

    /**
     * Produce an exact copy of this context.
     * 
     * @return
     */
    IRuntimeContext copy();

    /**
     * Rename the passed observation data as the passed alias.
     * 
     * @param name
     * @param alias
     */
    void rename(String name, String alias);

    /**
     * Export the network structure to a GEFX file.
     * 
     * @param outFile
     */
    void exportNetwork(String outFile);

    /**
     * The target artifact of the passed actuator's computation. In k.LAB usually an IObservation. External
     * computations may use non-semantic artifacts. These should be created if necessary; if
     * they haven't been already, they should be created as non-semantic artifacts.
     * @param actuator 
     * 
     * @return the target artifact for the passed actuator.
     */
    IArtifact getTarget(IActuator actuator);

}
