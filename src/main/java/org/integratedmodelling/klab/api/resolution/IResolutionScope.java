/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.resolution;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.owl.Observable;

/**
 * The resolution scope contains all the contextual information gathered during resolution,
 * including scale, traits and resolution criteria for all models being contextualized. During
 * resolution, any new condition spawns a child scope that is merged with the parent upon successful
 * resolution of the state. The resolution contexts compute the total coverage, build the provenance
 * graph and harmonize the merged scale as new models are accepted.
 * 
 * Created and passed around during resolution, notably to the model query so that it can be used to
 * rank the outputs. Model query on network nodes gets passed enough information to build a
 * mock-scope locally.
 * 
 * @author ferdinando.villa
 *
 */
public interface IResolutionScope extends ICoverage {

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
   * True if the observable has been resolved satisfactorily at the current scale, either directly
   * or indirectly.
   * 
   * @param observable
   * @return
   */
  boolean resolves(Observable observable);

  /**
   * Return the context in which this resolution is happening. Null for scopes
   * that resolve a root context.
   * 
   * @return the context, or null
   */
  IDirectObservation getContext();

}
