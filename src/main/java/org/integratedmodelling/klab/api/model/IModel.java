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
package org.integratedmodelling.klab.api.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;

/**
 * A Model is a statement that produces a computed observation. It has at least one observable. The k.LAB
 * runtime looks for models when resolving a concept that has been requested to be observed within a context
 * (the root context is always acknowledged through an {@link IObserver}).
 * 
 * Models may be unresolved (extensional, i.e. they leave their observable specified only at a semantic level)
 * or resolved (intensional, i.e. they can build their observations as they are and provide observation
 * semantics for it). Models may need to make additional observations, which are specified as pure semantics
 * through dependencies (see {@link #getDependencies()}.
 * 
 */
public interface IModel extends IActiveKimObject, INamespaceQualified {

    /**
     * Return the semantics of all observables we are observing. The first in the list is the actual
     * observable and must exist; the others are expected side-effects of observing the first, which must be
     * connected to semantics (i.e. they are specified along with observers in the language, or have
     * correspondent, unambiguous models in the same namespace).
     * 
     * Secondary observables must be qualities even in agent models.
     * 
     * @return all the observables in order of declaration.
     */
    List<IObservable> getObservables();

    /**
     * If the model has any inline data, such as a value, function or URN, return the correspondent resolved
     * resource, which will not, by now, have been fetched.
     * 
     * The resource also contains any metadata for created objects, such as their name.
     * 
     * @return
     */
    Optional<IResource> getResource();

    /**
     * The asserted semantics for any observation needed in order to produce observations of the observables.
     * More dependencies may be added at resolution through context-dependent inference.
     * 
     * @return all observables required.
     */
    List<IObservable> getDependencies();

    /**
     * This will only be called in models that produce objects (isReificationModel() == true) and have defined
     * observers for attributes of the objects produced. It is used to create de-reifying data models by
     * "painting" the object attributes over the context, ignoring the identity of the objects. For each
     * attribute name returned, the method getAttributeObserver() must return a valid observer. Some of the
     * attributes may be internally generated: for example, it is always possible to infer 'presence of' an
     * object from an observation of the object itself.
     * 
     * @param addInherency
     *            if true, add the model subject's inherent type to the observer type.
     * 
     * @return the list of dereified attributes with their observers
     */
    Map<String, IObservable> getAttributeObservables();

    /**
     * Get the name with which the passed observable is known within this model. The passed observable's name
     * reported by {@link IObservable#getPippaName()} may be different as the same observation could come from a
     * different model.
     * 
     * @param observable
     * @return
     */
    String getLocalNameFor(IObservable observable);

    /**
     * Return true if this model can be computed on its own and has associated data. Normally this amounts to
     * having a data/object source or an instantiator with getDependencies().size() == 0, but implementations
     * may provide faster ways to inquire (e.g. without fetching the resource).
     * 
     * Should really be named isIntensional() but let's stop the good terminology at reification to keep the
     * API readable by the non-philosopher.
     * 
     * @return true if model is a leaf in a dependency tree.
     */
    boolean isResolved();

    /**
     * True if the model is an instantiator, i.e. produces objects ('model each' in k.IM). If it is not, it's
     * an 'explanatory' model, which explains/computes an existing observation by providing a narrative for
     * its observation, rather than producing new observations.
     * 
     * @return true if model creates direct observations
     */
    boolean isInstantiator();

    /**
     * True if the model is expected to contextually reinterpret its observable using a role. If the role is
     * abstract, the model will try to establish the correspondent concrete role from the chain of provenance
     * at runtime. Observations will be made using an independently resolved, non-roled model unless the model
     * is already resolved.
     * 
     * @return true if the model reinterprets the observable through a role.
     */
    boolean isReinterpreter();

    /**
     * Called by the resolver before a model is used so that it has a chance to verify runtime availability
     * before being chosen for resolution.
     * 
     * @return true if everything is OK for computation
     */
    boolean isAvailable();

    /**
     * Models may have special documentation templates tied to contextualization events. If so, they are
     * exposed as metadata, where the key for each template defines the event tied to the reporting.
     * 
     * @return the declared documentation, or null if none exists. In state models, documentation may be
     *         created or filled in from metadata.
     */
    Optional<IDocumentation> getDocumentation();

    /**
     * Metadata can be associated to models in k.IM.
     * 
     * @return metadata (never null, possibly empty).
     */
    IMetadata getMetadata();

    /**
     * If there is a contextualizer clause ('using') and it is not used simply as an alias for a primary
     * resource (in which case it will be returned by {@link #getResource()}, return it here. Having a
     * resource here usually means a post-processing call, classification, lookup table or URN.
     * 
     * @return the post-processor resource, if any.
     */
    Optional<IResource> getContextualizerResource();

}
