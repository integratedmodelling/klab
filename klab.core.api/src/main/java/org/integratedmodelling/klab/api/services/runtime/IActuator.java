/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.services.runtime;

import java.util.List;

import org.integratedmodelling.klab.api.knowledge.IArtifact;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.lang.IContextualizable;
import org.integratedmodelling.klab.api.provenance.IPlan;
import org.integratedmodelling.klab.api.services.resolver.ICoverage;

/**
 * Actuators are first-class nodes in dataflows. In other workflow systems (e.g.
 * Ptolemy), an actuator corresponds to a composite actor dedicated to an
 * artifact and representing a k.IM model (explicit or derived by the k.LAB AI).
 * The "atomic" actors are contextualizers, represented by k.LAB
 * {@link org.integratedmodelling.kim.api.IServiceCall}s that serve as entry
 * points into the runtime.
 * <p>
 * Some actuators may be references, corresponding to "input ports" in other
 * workflow systems. In a k.LAB computation, references are always resolved and
 * the implementing which case the original actuator will always be serialized
 * before any references to it.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IActuator extends IPlan {

	/**
	 * All actuators have a name that corresponds 1-to-1 to the semantics it was
	 * created to resolve (observable reference name). References may provide a
	 * different name for the same actuator.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * The local name and "user-level" name for the actuator, correspondent to the
	 * simple name associated with the semantics or to its renaming through 'named'
	 * when used as a dependency.
	 *
	 * @return the alias or null
	 */
	String getAlias();

	/**
	 * Return the name with which the passed observable is known within this
	 * actuator, or null if the observable is not referenced in it.
	 * 
	 * @param observable
	 * @return
	 */
	String getAlias(IObservable observable);

	/**
	 * Each actuator reports the artifact type of the observation it produces. Pure
	 * resolvers (e.g. the resolver for an object) are de facto void, but report the
	 * special RESOLVE type; VOID actuators resolve views or predicates. Actuators
	 * whose type defines an occurrent are not run at initialization but are just
	 * called upon to schedule temporal actions.
	 * 
	 * @return
	 */
	IArtifact.Type getType();

	/**
	 * Return all child actuators in order of declaration in the dataflow. This may
	 * not correspond to the order of contextualization, which is computed by the
	 * runtime, although it is expected that child actuators at the same level
	 * without mutual dependencies have a non-random order which should be honored.
	 * In a runtime context, the child list may contain other dataflows.
	 *
	 * @return all the internal actuators in order of declaration.
	 */
	public List<IActuator> getChildren();

	/**
	 * Return the subset of actuators that are not resolved and must reference
	 * others in the same dataflow. These serialize with the modifier
	 * <code>import</code> in k.DL.
	 *
	 * @return all imported actuators, including the sub-dataflows. Use
	 *         {@link #getActuators()} to only retrieve the true actuators that make
	 *         up the computable set.
	 */
	List<IActuator> getInputs();

	/**
	 * Return all the computable actuators in the children, thus excluding those
	 * with type RESOLVE and the sub-dataflows.
	 * 
	 * @return
	 */
	List<IActuator> getActuators();

	/**
	 * Return all the dataflows in this actuator's children. These are meant to
	 * contextualize objects instantiated by this one or to concretize predicates
	 * before the others are computed. Dataflows loaded from serialized resolutions
	 * will not report any dataflow.
	 * 
	 * @return
	 */
	List<IDataflow<?>> getDataflows();

	/**
	 * Return all actuators that have been declared as exported, i.e. represent
	 * outputs of this actuator. These serialize with the modifier
	 * <code>export</code> in k.DL.
	 *
	 * @return all exported actuators
	 */
	List<IActuator> getOutputs();

	/**
	 * Return the list of all computations in this actuator, or an empty list. If
	 * the actuator is a reference, the list should be empty: any mediations are
	 * part of the referencing actuator's computations.
	 *
	 * @return all computations. Never null, possibly empty.
	 */
	List<IContextualizable> getComputation();

	/**
	 * If true, this actuator represents a named input that will need to be
	 * connected to an artifact from the computation context.
	 * 
	 * @return
	 */
	boolean isInput();

	/**
	 * If true, this actuator is a filter for an artifact, modifying the same
	 * artifact (and potentially returning a new one, or the same, at the discretion
	 * of the contextualizers). It must have a first 'import' parameter and its type
	 * will match that of the filtered input.
	 * 
	 * @return
	 */
	boolean isFilter();

	/**
	 * True if this actuator computes anything. Used when building dependencies (a
	 * computed actuator depends on its children, which can otherwise be executed in
	 * parallel).
	 *
	 * @return a boolean.
	 */
	boolean isComputed();

	/**
	 * If true, this actuator is a reference to another which has been defined
	 * before it in order of computation, and has produced its observation by the
	 * time this actuator is called into a contextualization. It only serves as a
	 * placeholder with a possibly different alias to define the local identifier of
	 * the original observation. Reference actuators are otherwise empty, with no
	 * children and no computation.
	 * 
	 * @return
	 */
	boolean isReference();

	/**
	 * The actuator reports the coverage of its <em>entire</em> computable scale,
	 * i.e. the native coverage of its model (or the merged coverage if >1 models
	 * are used). Dealing with different coverages within a model is the
	 * responsibility of the runtime. A null or empty coverage returned here means
	 * universal coverage, as no actuators are output by a resolution that does not
	 * succeed.
	 * 
	 * @return the merged coverage of all models in or below this actuator.
	 */
	ICoverage getCoverage();

}
