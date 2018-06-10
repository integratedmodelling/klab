package org.integratedmodelling.klab.engine.runtime.api;

import java.util.Collection;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.IConfigurationDetector;
import org.integratedmodelling.klab.api.runtime.IRuntimeProvider;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.provenance.Provenance;
import org.jgrapht.Graph;

/**
 * This API extends {@link IComputationContext} to add setters and other
 * functionalities that are needed at runtime. It is used within common
 * computation code so that any {@link IRuntimeProvider} may implement the
 * computation contexts as needed.
 * 
 * @author Ferd
 *
 */
public interface IRuntimeContext extends IComputationContext {

	/**
	 * A context is created for the root observation, and this information never
	 * changes.
	 * 
	 * @return the root observation. Can only be null at the beginning of the
	 *         lifecycle of this context, when the root obs has not been created
	 *         yet.
	 */
	ISubject getRootSubject();

	/**
	 * Return any of the observations created within the context of the root
	 * observation. Must be fast. Relied upon by all methods that retrieve
	 * observations.
	 * 
	 * @param id
	 * @return the requested observation or null.
	 */
	IObservation getObservation(String observationId);

	/**
	 * Create a child context for the passed actuator, containing a new target
	 * observation implementation for the artifact type and geometry specified in
	 * the actuator. The observation is created with the same scale of the current
	 * target and the current target is set as its parent.
	 * 
	 * @param scale
	 * @param target
	 * @param scope
	 * @param monitor
	 * @param context
	 *            the context for the resolution
	 * 
	 * @return the child context that will resolve the target
	 */
	public IRuntimeContext createChild(IScale scale, IActuator target, IResolutionScope scope, IMonitor monitor);

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
	 * Each context that handles a temporal scale must expose a configuration
	 * detector.
	 * 
	 * @return the configuration detector for the context
	 */
	IConfigurationDetector getConfigurationDetector();

	/**
	 * Produce a deep copy of this context, with a new resource catalog, leaving
	 * only the original instances of the context-wide members such as provenance,
	 * configuration detector, event bus and structures. This is meant to be able to
	 * rename elements without harm.
	 * 
	 * @return an identical context with a rewriteable object catalog and
	 *         parameters.
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
	 * Set the main target of the computation being carried on by the actuator. Used
	 * by Actuator.compute().
	 * 
	 * @param target
	 */
	void setTarget(IArtifact target);

	/**
	 * The API must be able to reset the geometry for downstream indirectAdapters.
	 * 
	 * @param geometry
	 */
	void setScale(IScale geometry);

	/**
	 * Called after successful computation passing each annotation that was defined
	 * for the model.
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
	 * Return the context structure (all father-child relationships) as a JGraphT graph.
	 * 
	 * @return the structure
	 */
	Graph<? extends IArtifact, ?> getStructure();

	/**
	 * Return all the children of an artifact in the structural graph that match a
	 * certain class.
	 * 
	 * @param artifact
	 * @param cls
	 * @return the set of all children of class cls
	 */
	<T extends IArtifact> Collection<T> getChildren(IArtifact artifact, Class<T> cls);

	/**
	 * Build the link between a parent and a child artifact. Should only be used in
	 * the few cases when observations are created by hand, using pre-built
	 * instances such as rescaling states, instead of through
	 * {@link #newObservation(org.integratedmodelling.klab.api.knowledge.IObservable, String, IScale)}
	 * or
	 * {@link #newRelationship(org.integratedmodelling.klab.api.knowledge.IObservable, String, IScale, org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact, org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact)}.
	 * 
	 * @param parent
	 * @param child
	 */
	void link(IArtifact parent, IArtifact child);

}
