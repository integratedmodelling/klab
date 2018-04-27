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
package org.integratedmodelling.klab.api.resolution;

import java.util.Collection;

import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

// TODO: Auto-generated Javadoc
/**
 * The resolution scope contains all the contextual information gathered during resolution,
 * including scale with coverage, traits and resolution criteria for all models being
 * contextualized. During resolution, any new condition spawns a child scope that is merged with the
 * parent upon successful resolution of the state. The resolution contexts compute the total
 * coverage, build the provenance graph and harmonize the merged scale as new models are accepted.
 *
 * Created and passed around during resolution, notably to the model query so that it can be used to
 * rank the outputs. Model query on network nodes gets passed enough information to build a
 * mock-scope at the query endpoint.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IResolutionScope {

  /**
   * The Enum Mode.
   */
  public enum Mode {
    /**
     * this context is resolving a model for a single instance that may already exist (if a
     * countable created by an instantiator) or will be created upon successful resolution (a
     * non-countable).
     */
    RESOLUTION,
    /**
     * this context is trying to resolve an observable for direct observations that have not been
     * instantiated, i.e. it will be resolved by models that instantiate them ('model each' models).
     */
    INSTANTIATION
  }

  /**
   * IDs of any scenarios we're resolving into. These are set in the root scope and inherited by all
   * child scopes.
   *
   * @return the scenarios of resolution
   */
  Collection<String> getScenarios();

  /**
   * Return the namespace of reference for this context. It should never be null; if we're resolving
   * a model's dependency, it should be the model's namespace, otherwise it should be that of the
   * subject or concept we're resolving. The namespace provides semantic distance, ranking criteria,
   * white/blacklist for resolution, etc.
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
   * editable parameters and optional outputs. Whenever these are available, the resolver will stop
   * and ask the user for input through the engine notification bus.
   *
   * @return whether the resolution is interactive
   */
  boolean isInteractive();

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

}
