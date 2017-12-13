/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other authors listed
 * in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular, collaborative,
 * integrated development of interoperable data and model components. For details, see
 * http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the Affero
 * General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without even the
 * implied warranty of merchantability or fitness for a particular purpose. See the Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. The license
 * is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.resolution;

import java.util.Collection;

import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IScale;
import org.integratedmodelling.klab.api.provenance.IProvenance;

/**
 * The resolution scope contains all the contextual information gathered during resolution, including scale,
 * traits and resolution criteria for all models being contextualized. The resolution contexts compute the
 * total coverage, build the provenance graph and harmonize the merged scale as new models are accepted.
 * 
 * Created and passed around during resolution, notably to the model query so that it can be used to rank the
 * outputs. Model query on network nodes gets passed enough information to build a mock-scope locally.
 * 
 * @author ferdinando.villa
 *
 */
public interface IResolutionScope {

    /**
     * Scale of resolution. This may change as new constraints are brought in by each resolved model, although
     * it should remain fully included in the original scale and maximal in resolution.
     * 
     * @return the scale of resolution
     */
    IScale getScale();

    /**
     * Scenarios we're resolving into. These are set in the root context and passed around to child contexts
     * without modification.
     * 
     * @return the scenarios of resolution
     */
    Collection<String> getScenarios();

    /**
     * The model being resolved should only be null when resolving the "root" subject level.
     * 
     * @return the model being resolved
     */
    IModel getModel();

    /**
     * Return an appropriate prioritizer to choose a model among many.
     * 
     * @return the prioritizer used to choose models
     */
    IModelPrioritizer<IModel> getPrioritizer();

    /**
     * Return the namespace of reference for this context. It should never be null; if we're resolving a
     * model's dependency, it should be the model's namespace, otherwise it should be that of the subject or
     * concept we're resolving. The namespace provides semantic distance, ranking criteria, white/blacklist
     * for resolution, etc.
     * 
     * @return the resolution namespace
     */
    INamespace getResolutionNamespace();

    /**
     * The direct observation being resolved; if we're resolving a quality, the subject that the quality is
     * inherent to.
     * 
     * @return the subject providing the resolution context
     */
    IDirectObservation getSubject();

    /**
     * Return the coverage for this resolution.
     * 
     * @return the current coverage
     */
    ICoverage getCoverage();

    /**
     * The provenance artifact that caused the resolution (either an observation or an observable). Never
     * null.
     * 
     * @return
     */
    IProvenance.Artifact getProvenanceArtifact();

    /**
     * Checks if the passed observable is required as a dependency by any models resolved so far.
     * 
     * @param observable
     * @return
     */
    boolean isUsed(IObservable observable);

    /**
     * Checks if the passed observable is required in the passed scope, i.e. must be produced because the
     * model being run tags it as a required output or another model or the user has requested it. If this
     * returns false, producing a given output should be optional - the model may elicit to produce it if
     * considered essential for understanding the results, but otherwise it should not produce or publish it.
     * 
     * Checks the outputs of the current model if any, and compounds it with the result of {@link #isUsed}.
     * 
     * @param observable
     * @return
     */
    boolean isRequired(IObservable observable);

    /**
     * If true, this context is trying to resolve an observable for direct observations that have not been
     * instantiated, i.e. it will be resolved by models that instantiate them ('model each' models). If false,
     * we already have an instance, and we're looking for a model that can complete its observation.
     * 
     * @return true if instantiation of the observable is required in this scope
     */
    boolean isForInstantiation();

    /**
     * true if we're downstream of an optional dependency.
     * 
     * @return whether the resolution context is an optional dependency
     */
    boolean isOptional();

    /**
     * If true, we're resolving interactively, which implies giving the user a choice over values of editable
     * parameters and optional outputs. Whenever these are available, the resolver will stop and ask the user
     * for input through the engine notification bus.
     * 
     * @return
     */
    boolean isInteractive();

    /**
     * If true, the context we're resolving is for non-abstract countable observables that we want to
     * instantiate extensively - i.e., all the subtypes, leaving the base type last if the subtypes don't
     * provide full coverage.
     * 
     * @return
     */
    boolean isGeneric();

    /**
     * The observable being resolved in this specific context.
     * 
     * @return
     */
    IObservable getObservable();

}
