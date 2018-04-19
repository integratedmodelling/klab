package org.integratedmodelling.klab.engine.runtime.api;

import java.util.Collection;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.provenance.Provenance;

/**
 * This API extends {@link IComputationContext} to add setters and other functionalities that are
 * needed at runtime. It is used within common computation code so that any {@link IRuntimeProvider}
 * may implement the computation contexts as needed.
 * 
 * @author Ferd
 *
 */
public interface IRuntimeContext extends IComputationContext {

    /**
     * Create a child context for the passed actuator, containing a new target observation
     * implementation for the artifact type and geometry specified in the actuator. The observation is
     * created with the same scale of the current target and the current target is set as its parent.
     * 
     * @param scale 
     * @param target
     * @param scope 
     * 
     * @return the child context that will resolve the target
     */
    public IRuntimeContext createChild(IScale scale, IActuator target, IResolutionScope scope);

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
     * Each context that handles a temporal scale must expose a configuration detector.
     * 
     * @return the configuration detector for the context
     */
    IConfigurationDetector getConfigurationDetector();

    /**
     * Produce a deep copy of this context, with a new resource catalog, leaving only the original
     * instances of the context-wide members such as provenance, configuration detector, event bus and
     * structures. This is meant to be able to rename elements without harm.
     * 
     * @return an identical context with a rewriteable object catalog and parameters.
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
     * The target artifact of the passed actuator's computation. In k.LAB usually an IObservation.
     * External computations may use non-semantic artifacts. These should be created if necessary; if
     * they haven't been already, they should be created as non-semantic artifacts.
     * 
     * @param actuator
     * 
     * @return the target artifact for the passed actuator.
     */
    IArtifact getTargetArtifact(IActuator actuator);

    /**
     * Set the main target of the computation being carried on by the actuator. Used by
     * Actuator.compute().
     * 
     * @param target
     */
    void setTarget(IArtifact target);

    /**
     * The API must be able to reset the geometry for downstream computations.
     * 
     * @param geometry
     */
    void setScale(IScale geometry);

    /**
     * Called after successful computation passing each annotation that was defined for the model.
     * 
     * @param annotation
     */
    void processAnnotation(IKimAnnotation annotation);

    /**
     * Specialize the provenance so we can use setting methods on it.
     * 
     */
    @Override
    Provenance getProvenance();

    /**
     * Return all the children of an artifact in the structural graph that match a
     * certain class.
     * 
     * @param artifact
     * @param cls
     * @return the set of all children of class cls
     */
    <T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls);

}
