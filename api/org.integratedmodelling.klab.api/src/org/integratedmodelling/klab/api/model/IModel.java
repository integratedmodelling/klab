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
package org.integratedmodelling.klab.api.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.resolution.IComputationProvider;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * A Model is a statement that produces a computed observation. It has at least one observable. The
 * k.LAB runtime looks for models when resolving a concept that has been requested to be observed
 * within a context (the root context is always acknowledged through an
 * {@link org.integratedmodelling.klab.api.model.IAcknowledgement}).
 *
 * Models may be unresolved (extensional, i.e. they leave their observable specified only at a
 * semantic level) or resolved (intensional, i.e. they can build their observations as they are and
 * provide observation semantics for it). Models may need to make additional observations, which are
 * specified as pure semantics through {@link #getDependencies() dependencies}.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IModel extends IActiveKimObject, INamespaceQualified, IResolvable, IComputationProvider {

    /**
     * Annotation marking the predictor in learning models.
     */
    public static final String PREDICTOR_ANNOTATION = "predictor";

    /**
     * Annotation marking the archetype in learning models. If missing, the archetype is created
     * from the observable.
     */
    public static final String ARCHETYPE_ANNOTATION = "archetype";

    /**
     * Annotation requesting a learner for a quality "within" a countable to also compute the
     * learned quality over the context of learning, requiring the predictors to become dependencies
     * in it.
     */
    public static final String DISTRIBUTE_ANNOTATION = "distribute";

    /**
     * Return the semantics of all observables we are observing. The first in the list is the actual
     * observable and must exist; the others are expected side-effects of observing the first, which
     * must be connected to semantics (i.e. they are specified along with observers in the language,
     * or have correspondent, unambiguous models in the same namespace).
     *
     * Secondary observables must be qualities even in agent models.
     *
     * @return all the observables in order of declaration.
     */
    List<IObservable> getObservables();

    /**
     * If the model is a semantic annotation for a resource, such as a value, service or URN, return
     * the resources as computables for later resolution. These include anything described directly
     * (model resource as ...) and any others given in the 'using' statement. Since 0.10.0 resources
     * may be multiple, be identified by aliases, and cross-reference each other in computation.
     *
     * The resource also contains any metadata for created objects, such as their name, no-data
     * values etc. Resources given in the 'using' clause may have requirements (stated syntactically
     * as alias names); all need to be fully resolved within the model by dependencies or other
     * resources.
     *
     * @return the resources that this model provides semantics for. Possibly empty, never null.
     */
    List<IContextualizable> getResources();

    /**
     * The asserted semantics for any observation needed in order to produce observations of the
     * observables. More dependencies may be added at resolution through context-dependent
     * inference.
     *
     * @return all observables required.
     */
    List<IObservable> getDependencies();

    /**
     * This will only be called in models that produce objects (isInstantiator() == true) and have
     * defined observers for attributes of the objects produced. For each attribute name returned,
     * the method getAttributeObserver() must return a valid observer. Some of the attributes may be
     * internally generated: for example, it is always possible to infer 'presence of' an object
     * from an observation of the object itself.
     *
     * @return the list of dereified attributes with their observers
     */
    Map<String, IObservable> getAttributeObservables();

    /**
     * Return the name that the passed observable is known by in this model, or null if it's not
     * known at all.
     * 
     * @param observable
     * @return
     */
    String getLocalNameFor(IObservable observable);

    /**
     * Return the observable that corresponds to the passed local name, or null if the observable
     * isn't known.
     * 
     * @param localName
     * @return
     */
    IObservable getObservableFor(String localName);

    /**
     * Return true if this model can be computed on its own and has associated data. Normally this
     * amounts to having a data/object source or an instantiator with getDependencies().size() == 0,
     * but implementations may provide faster ways to inquire (e.g. without fetching the resource).
     *
     * Should really be named isIntensional() but let's stop the good terminology at reification to
     * keep the API readable by the non-philosopher.
     *
     * @return true if model is a leaf in a dependency tree.
     */
    boolean isResolved();

    /**
     * True if the model is an instantiator, i.e. produces objects ('model each' in k.IM). If it is
     * not, it's an 'explanatory' model, which explains/computes an existing observation by
     * providing a narrative for its observation, rather than producing new observations.
     *
     * @return true if model creates direct observations
     */
    boolean isInstantiator();

    /**
     * True if the model is expected to contextually reinterpret its observable using a role. If the
     * role is abstract, the model will try to establish the correspondent concrete role from the
     * chain of provenance at runtime. Observations will be made using an independently resolved,
     * non-roled model unless the model is already resolved.
     *
     * @return true if the model reinterprets the observable through a role.
     */
    boolean isReinterpreter();

    /**
     * A learning model must produce a model as its primary artifact.
     * 
     * @return
     */
    boolean isLearning();

    /**
     * Called by the resolver before a model is used so that it has a chance to verify runtime
     * availability before being chosen for resolution.
     *
     * @return true if everything is OK for computation
     */
    boolean isAvailable();

    /**
     * Models may have special documentation templates tied to contextualization events. If so, they
     * are exposed as metadata, where the key for each template defines the event tied to the
     * reporting. Each model can only have one template, but it can also collect templates from
     * other objects it uses, such as lookup tables or observables.
     *
     * @return the declared documentation, or an empty collection if none exists.
     */
    Collection<IDocumentation> getDocumentation();

    /**
     * Metadata can be associated to models in k.IM.
     *
     * @return metadata (never null, possibly empty).
     */
    IMetadata getMetadata();

    /**
     * If not PUBLIC, the model or the namespace containing it are private to its declared scope,
     * and only models in the same scope can use it for resolution.
     *
     * @return true if private
     */
    Scope getScope();

    /**
     * If true, the model is semantically and syntactically valid but has been deactivated (voided)
     * either explicitly by the user or by the validator after finding unavailable resources, and
     * will not be used, selected or validated further.
     * 
     * @return true if inactive
     */
    boolean isInactive();

    /**
     * True if model has concepts associated. Only false for the private non-semantic models.
     * 
     * @return true if semantic
     */
    boolean isSemantic();

    /**
     * The geometry implicitly declared for the model, gathered from the resources and the services
     * used in it. Does not include the explicit contextualization (over space/time) that comes from
     * the behavior and must be compatible with it at validation.
     * 
     * @return the implicit geometry from the resources, or null.
     */
    IGeometry getGeometry();

    /**
     * If the result collection isn't empty, the model can only be used to resolve its observable if
     * all the returned traits are assigned in the resolution scope. This corresponds to having any
     * dependency or observable defined in terms of a non-generic ("any") abstract role, which will
     * be redefined to its concrete counterpart(s) before resolution. This does <i>not</i> involve
     * the determination of abstract status described by {@link #isAbstract()} and
     * {@link #getAbstractTraits()}: models with required traits can be resolved as they are (and
     * their dependencies will be concretized during resolution, including potentially being
     * removed). Models with abstract traits must be reincarnated for each combination of traits
     * before resolving.
     * 
     * @return
     */
    Collection<IConcept> getRequiredTraits();

    /**
     * An abstract model uses abstract traits in dependencies or observables, so it can only be run
     * after resolving all of those to their concrete incarnations. This is done on a model-wide
     * basis, so it does not involve generic dependencies ('any') which are resolved <i>after</i>
     * picking the model for resolution. If a model is abstract, the traits it's abstract in (which
     * currently can only be roles or identities) are returned by {@link #getAbstractTraits()}, and
     * a different model must be produced for each combination of these traits that can be resolved
     * in the context. Such traits can be produced by explicit setting (only for roles) or by
     * running characterizing models.
     * 
     * @return
     */
    boolean isAbstract();

    /**
     * Non-empty if {@link #isAbstract()} is true. The mechanics of producing models with these
     * incarnated to their concrete counterparts is left to the implementation.
     * 
     * @return
     */
    Collection<IConcept> getAbstractTraits();

    /**
     * True if the model annotates >1 resources that represent different extents in the passed
     * dimension. If the dimension is time, returning true makes this model also handle change after
     * it has been accepted to resolve its observable. The model is not registered as a resolver of
     * change in its own right, so that we can only use it to handle change <b>after</b> it has been
     * used to initialize the observation. This way we ensure that the resource set is used
     * consistently throughout the contextualization.
     * 
     * @return true if there is more than one resource and the annotated resources represent several
     *         extents in the passed dimension.
     */
    boolean changesIn(Type dimension, IScale scale);

}
