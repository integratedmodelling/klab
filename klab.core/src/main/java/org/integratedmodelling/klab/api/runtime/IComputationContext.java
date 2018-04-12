/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.runtime;

import java.util.Collection;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Pair;

// TODO: Auto-generated Javadoc
/**
 * The runtime context holds all information about the computation being run and
 * the artifacts computed this far. It is passed to dataflows and down to
 * actuators and contextualizers, customized to reflect names, states and
 * locators as each actuator expects them.
 *
 * The {@link org.integratedmodelling.kim.api.IParameters} methods access user-defined parameters, including any
 * passed to the calling functions or URNs and, if appropriate,
 * context-localized POD values for states (e.g. the specific values at the
 * point of computation). The actual input and output artifacts are always
 * available through {@link #getArtifact(String)}.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IComputationContext extends IParameters {

	/**
	 * The namespace of reference in this context. Usually that of the running model
	 * or observer.
	 *
	 * @return the namespace of reference. Null in empty contexts or during
	 *         non-semantic computations.
	 */
	INamespace getNamespace();

	/**
	 * <p>getProvenance.</p>
	 *
	 * @return the provenance graph. Null in an empty context.
	 */
	IProvenance getProvenance();

	/**
	 * <p>getEventBus.</p>
	 *
	 * @return the event bus. Null in an empty context.
	 */
	IEventBus getEventBus();

	/**
	 * Inspect the network graph of the current context, returning all relationships
	 * that have the passed subject as target.
	 *
	 * @param observation a {@link org.integratedmodelling.klab.api.observations.ISubject} object.
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<IRelationship> getOutgoingRelationships(ISubject observation);

	/**
	 * Inspect the network graph of the current context, returning all relationships
	 * that have the passed subject as target.
	 *
	 * @param observation a {@link org.integratedmodelling.klab.api.observations.ISubject} object.
	 * @return a {@link java.util.Collection} object.
	 */
	Collection<IRelationship> getIncomingRelationships(ISubject observation);

	/**
	 * Even computations that have more than one output have a single target
	 * artifact, which corresponds to the primary observable of the model that has
	 * defined the computation. If the computation is an instantiation, the target
	 * artifact is null.
	 *
	 * @return the target artifact, or null in instantiation computations.
	 */
	IArtifact getTargetArtifact();

	/**
	 * Get the resolved {@link IArtifact object} corresponding to the passed local
	 * name. Use {@link IParameters IParameters get methods} to retrieve
	 * contextualized values for states or parameters.
	 *
	 * @param localName a {@link java.lang.String} object.
	 * @return the artifact, null if not found.
	 */
	IArtifact getArtifact(String localName);

	/**
	 * Get the resolved {@link IArtifact object} corresponding to the passed local
	 * name as an object of the passed class. If the artifact is not there or it is
	 * not of a compatible type, return null with no error.
	 *
	 * @param localName a {@link java.lang.String} object.
	 * @param cls a {@link java.lang.Class} object.
	 * @return the artifact, null if not found or not of passed class.
	 * @param <T> a T object.
	 */
	<T extends IArtifact> T getArtifact(String localName, Class<T> cls);

	/**
	 * Return all known artifacts of the passed class along with their ID in this
	 * context. For example, all data artifacts known at the time of computation can
	 * be retrieved using <code>getData(IDataArtifact.class)</code>.
	 *
	 * @param type a {@link java.lang.Class} object.
	 * @return a collection of pair <name, artifact>, possibly empty, never null.
	 * @param <T> a T object.
	 */
	<T extends IArtifact> Collection<Pair<String, T>> getArtifacts(Class<T> type);

	/**
	 * Return a valid monitor for any communication.
	 *
	 * @return the monitor for this computation. Never null.
	 */
	IMonitor getMonitor();

	/**
	 * Return the concept type describing the artifact being computed.
	 *
	 * @return the type of the observation
	 */
	IKimConcept.Type getArtifactType();

	/**
	 * Return the geometry for the computation (in k.LAB typically a
	 * {@link org.integratedmodelling.klab.api.observations.scale.IScale}).
	 *
	 * @return the current geometry. Should never be null.
	 */
	public IScale getScale();

	/**
	 * Get the names of all inputs for this computation. The correspondent semantics
	 * can be accessed using {@link #getSemantics(String)}; the corresponding
	 * artifact can be accessed using {@link #getArtifact(String)}.
	 *
	 * @return the names of all inputs in this context
	 */
	public Collection<String> getInputs();

	/**
	 * Get the names of all outputs expected from this computation. The
	 * correspondent semantics can be accessed using {@link #getSemantics(String)};
	 * the corresponding artifact can be accessed using
	 * {@link #getArtifact(String)}.
	 *
	 * @return the names of all outputs in this context
	 */
	public Collection<String> getOutputs();

	/**
	 * Get the semantics for the passed identifier, which must be one of those returned by either
	 * {@link #getInputs()} or {@link #getOutputs()}.
	 *
	 * @param identifier the identifier
	 * @return the observable linked to the identifier
	 * @throws java.lang.IllegalArgumentException if the identifier is unknown
	 */
	public IObservable getSemantics(String identifier);

	/**
	 * Create and resolve a new observation of the specified countable observable
	 * and with the specified geometry. Use in {@link IInstantiator instantiators}
	 * to create new objects. Use
	 * {@link #newRelationship(IObservable, IScale, IObjectArtifact, IObjectArtifact)}
	 * to create a relationship.
	 * <p>
	 * While any k.LAB-aware implementation will receive a {@link org.integratedmodelling.klab.api.observations.scale.IScale} instead of
	 * a {@link org.integratedmodelling.kim.api.data.IGeometry} and return a {@link org.integratedmodelling.klab.api.observations.ICountableObservation} rather than
	 * just {@link org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact}, we keep the basic, non-semantic types in the
	 * signature for consistency with derived APIs of remote services and other
	 * non-semantic computations.
	 * <p>
	 * As the runtime provider is responsible for creating the
	 * {@code IComputationContext}, this is where it can control the type and
	 * features of any new object created.
	 * <p>
	 *
	 * @param observable a {@link org.integratedmodelling.klab.api.knowledge.IObservable} object.
	 * @param name a {@link java.lang.String} object.
	 * @param scale a {@link org.integratedmodelling.klab.api.observations.scale.IScale} object.
	 * @return a new observation for the observable and geometry
	 * @throws org.integratedmodelling.klab.exceptions.KlabException
	 *             from the resolution
	 * @throw IllegalArgumentException if the observable describes a non-countable
	 *        or a relationship.
	 */
	IObjectArtifact newObservation(IObservable observable, String name, IScale scale) throws KlabException;

	/**
	 * Create and resolve a new observation of the specified relationship with with
	 * the specified geometry, source and target subjects. Use in
	 * {@link IInstantiator relationship instantiators} to create new objects.
	 * <p>
	 * See {@link #newObservation(IObservable, String, IScale)} for API design
	 * choices.
	 * <p>
	 *
	 * @param observable a {@link org.integratedmodelling.klab.api.knowledge.IObservable} object.
	 * @param scale a {@link org.integratedmodelling.klab.api.observations.scale.IScale} object.
	 * @param source a {@link org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact} object.
	 * @param target a {@link org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact} object.
	 * @return a new observation for the observable and geometry
	 * @throw IllegalArgumentException if the observable does not describe a
	 *        relationship.
	 */
	IObjectArtifact newRelationship(IObservable observable, IScale scale, IObjectArtifact source,
			IObjectArtifact target);

	/**
	 * Get the semantics for the primary target of the computation.
	 *
	 * @return observable being described
	 */
	IObservable getTargetSemantics();

}
