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
package org.integratedmodelling.klab.api.resolution;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservedConcept;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * The resolution scope contains all the contextual information gathered during resolution,
 * including scale with coverage, traits and resolution criteria for all models being
 * contextualized. During resolution, any change spawns a child scope that is merged with the parent
 * upon its successful resolution. The resolution contexts compute the total coverage, build the
 * provenance graph and harmonize the merged scale as new models are accepted. Successful resolution
 * means a <em>satisfactory</em> coverage, with rules that can be changed by the user.
 * <p>
 * Created and passed around during resolution, notably to the model query so that it can be used to
 * rank the outputs. Model query on network nodes gets passed enough information to build a
 * mock-scope at the query endpoint.
 * <p>
 * The official API is small and opaque, except for a small set of methods. The implemented
 * resolution scope will normally contain complex logics and many more methods.
 * 
 * @author ferdinando.villa
 */
public interface IResolutionScope {

    /**
     * Resolution mode is the shallowest level of description for the observation activity that can
     * resolve an observable. The deeper level (according to odo:Description) are made explicit in
     * {@link IActivity#Description}.
     * <p>
     * The resolution scope knows the mode while the observable knows that and the remaining
     * semantics.
     */
    public enum Mode {
        /**
         * this context is resolving a model for a single instance that may already exist (if a
         * countable created by an instantiator) or will be created upon successful resolution (a
         * non-countable).
         */
        RESOLUTION,
        /**
         * this context is trying to resolve an observable for direct observations that have not
         * been instantiated, i.e. it will be resolved by models that instantiate them ('model each'
         * models).
         */
        INSTANTIATION
    }

    /**
     * Scope of resolution. For now only used to report the unchanging original scope.
     * 
     * @author Ferd
     *
     */
    public enum Scope {
        OBSERVABLE, OBSERVER, MODEL
    }

    /**
     * Available after resolution, this is the entire scale covered by all the actuators and models
     * compiled in, bounded by the context.
     * 
     * @return
     */
    IScale getScale();

    /**
     * IDs of any scenarios we're resolving into. These are set in the root scope and inherited by
     * all child scopes.
     *
     * @return the scenarios of resolution
     */
    Collection<String> getScenarios();

    /**
     * Return the namespace of reference for this context. It should never be null; if we're
     * resolving a model's dependency, it should be the model's namespace, otherwise it should be
     * that of the subject or concept we're resolving. The namespace affects semantic distance,
     * ranking criteria, white/blacklist for resolution, etc.
     *
     * @return the resolution namespace
     */
    INamespace getResolutionNamespace();

    /**
     * Return the mode of resolution - whether we're looking for an instantiator or a resolver.
     *
     * @return the mode of resolution
     */
    Mode getMode();

    /**
     * If true, we're resolving interactively, which implies giving the user a choice over values of
     * editable parameters and optional outputs. Whenever these are available, the resolver will
     * stop and ask the user for input through the engine notification bus.
     *
     * @return whether the resolution is interactive
     */
    boolean isInteractive();

    /**
     * True if there is occurrence, i.e. a physical view of time, in this resolution. This is set
     * globally across a tree of scopes, i.e. the first occurrence makes the whole scope occur. If
     * true, the dataflow must take physical time into account and allow for an initialization
     * followed by temporal contextualization. Occurrence depends on having resolved any occurrent
     * (event or process) and/or having an occurrent scale (the time extent is physical and
     * distributed).
     * 
     * @return true if the resolution implies overall occurrence
     */
    boolean isOccurrent();

    /**
     * True if the passed observable occurs in this scope.
     * 
     * @param observable
     * @return
     */
	boolean occursInScope(IObservedConcept observable);
	
    /**
     * Resolution is controlled by a task or script monitor.
     *
     * @return the monitor
     */
    IMonitor getMonitor();

    /**
     * Return the context in which this resolution is happening. Null for scopes that resolve a root
     * context.
     *
     * @return the context, or null
     */
    IDirectObservation getContext();

    /**
     * The scale of the resolution, including how much the resolution process managed to cover it.
     *
     * @return the coverage
     */
    ICoverage getCoverage();

    /**
     * If this scope is resolving a relationship, it must know the source and target subject for it.
     * 
     * @return the source subject for the relationship being resolved
     */
    ISubject getRelationshipSource();

    /**
     * If this scope is resolving a relationship, it must know the source and target subject for it.
     * 
     * @return the target subject for the relationship being resolved
     */
    ISubject getRelationshipTarget();

    /**
     * The scope this resolution was started into. Doesn't change across the resolution graph.
     * 
     * @return the original scope
     */
    Scope getOriginalScope();

    /**
     * Return the specific observable that resolves the passed one, or null. Resolution requires
     * identity of concept but not of name or mediators.
     * 
     * @param observable
     * @param mode
     * @return
     */
    IObservable getResolvedObservable(IObservable observable, Mode mode);

    /**
     * Return all known role settings in the current resolution context. Roles may be set explicitly
     * by users or applications in the session state, in addition to being implied by specific
     * observables being resolved (processes or context observations). At any moment during the
     * resolution, dependencies for observables tagged with roles will be resolved against the
     * current roles.
     * 
     * @return a collection of pairs where the first element is the role and the second a collection
     *         of all the observables to which the role has been assigned.
     */
    Map<IConcept, Collection<IConcept>> getRoles();

    /**
     * When abstract predicates in observables are resolved, the resolved mappings are stored here
     * so that they can be propagated downstream during resolution. This stores predicates that have
     * been already defined, possibly because a previous scope had non-empty getRoles(): if this
     * contains values, these should be used instead of looked for in either the scope or the model
     * space.
     * 
     * @return
     */
    Map<IConcept, IConcept> getResolvedPredicates();

    /**
     * If the target artifact was resolved previously, return it here.
     * 
     * @return
     */
    IResolvable getResolvedArtifact();



}
