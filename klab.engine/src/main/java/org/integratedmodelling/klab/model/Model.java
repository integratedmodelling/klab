package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Documentation;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.Units.UnitContextualization;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.services.IDocumentationService;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.resolution.ObservationStrategy;
import org.integratedmodelling.klab.resolution.ObservationStrategy.Strategy;
import org.integratedmodelling.klab.resolution.RankedModel;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;

public class Model extends KimObject implements IModel {

	private String id;
	private List<IObservable> observables = new ArrayList<>();
	private List<IObservable> dependencies = new ArrayList<>();
	private Map<String, IObservable> attributeObservables = new HashMap<>();
	private Namespace namespace;
	private Contextualization contextualization;
	private List<IContextualizable> resources = new ArrayList<>();
	private boolean instantiator;
	private boolean reinterpreter;
	private boolean inactive;
	private boolean learning;
	private IObservable archetype;
	private Set<IConcept> requiredTraits = null;
	private Set<IConcept> abstractTraits_ = null;
	private BidiMap<String, String> localNames = new DualHashBidiMap<>();
	private Map<String, IObservable> observablesByReferenceName = new HashMap<>();

	/*
	 * the geometry implicitly declared for the project, gathered from the resources
	 * and the services used in it. Does not include the explicit contextualization
	 * (over space/time) that must be compatible with it at validation.
	 */
	private IGeometry geometry;
	// saved at resource read, to be intersected with own coverage when requested
	private Scale resourceCoverage;
	// own coverage, resulting of own specs if any, namespace's if any, and
	// resource's if any
	private Scale coverage;

	/*
	 * if true, the model is a derived model resulting from observable inference,
	 * which disables some further resolutions.
	 */
	private boolean derived = false;

	// remember resources to be able to reassess their online status
	private List<IResource> resourcesUsed = new ArrayList<>();
	private long lastResourceCheck;
	private boolean available = true;
	private Scope scope = Scope.PUBLIC;
	private Strategy observationStrategy = Strategy.DIRECT;
	private boolean learnsWithinArchetype;
	private boolean distributesLearning;
	private boolean multipleTimes = false;
	private MergedResource mergedResource;
	private IViewModel viewModel = null;

	// only for the delegate RankedModel
	protected Model() {
		super(null);
	}

	/**
	 * @param model
	 * @param namespace
	 * @param monitor
	 * @return a new model
	 */
	public static Model create(IKimModel model, Namespace namespace, IMonitor monitor) {
		return new Model(model, namespace, monitor);
	}

	/**
	 * If the model has abstract predicates that intersects those coming in, return
	 * a new instance of the model concretized to the values in the passed map.
	 * Otherwise preserve the implementation of the incoming model, including any
	 * ranks so that they can become part of the provenance records. FIXME May have
	 * to re-wrap the concretized model into a copy of the ranked wrapper.
	 * 
	 * @param candidate
	 * @param resolvedTraits
	 * @param monitor
	 * @return
	 */
	public static IModel concretize(IModel candidate, Map<IConcept, IConcept> resolvedTraits,
			Map<IConcept, Set<IConcept>> resolvedPredicatesContext, IMonitor monitor) {

		if (resolvedTraits.isEmpty()) {
			return candidate;
		}

		// TODO this is pretty messy and dangerous. Improve the API (and remove the
		// delegate/Model
		// inheritance for the ranked model) when the world starts spinning at a
		// reasonable speed.
		IModel model = candidate;
		if (model instanceof RankedModel) {
			model = ((RankedModel) model).getDelegate();
		}

		boolean needConcretization = false;
		for (IConcept predicate : model.getAbstractTraits()) {
			for (IConcept resolved : resolvedTraits.keySet()) {
				if (predicate.is(resolved)) {
					needConcretization = true;
					break;
				}
			}
			if (needConcretization) {
				break;
			}
		}

		return needConcretization ? new Model((Model) model, resolvedTraits, resolvedPredicatesContext, monitor)
				: candidate;
	}

	private Model(Model model, Map<IConcept, IConcept> resolvedPredicates,
			Map<IConcept, Set<IConcept>> resolvedPredicatesContext, IMonitor monitor) {

		/*
		 * easier to recreate from the statement, then fix observables and resources.
		 * Any "named" clause in the observables must be preserved for internal
		 * reference.
		 */
		this(model.getStatement(), model.namespace, monitor);

		/*
		 * new name will include the predicates to differentiate.
		 */
		List<String> preds = new ArrayList<>();
		for (IConcept p : resolvedPredicates.values()) {
			preds.add(Concepts.INSTANCE.getCodeName(p));
		}
		/*
		 * ensure consistency by sorting - this may be overkill. Need a specialized name
		 * to be correctly handled in dataflow compilation.
		 */
		Collections.sort(preds);

		this.id = this.id + "_" + Utils.join(preds, "_");
		this.observables.clear();
		this.dependencies.clear();

		for (IObservable observable : model.observables) {
			IObservable obs = Observable.concretize(observable, resolvedPredicates, resolvedPredicatesContext);
			this.observables.add(obs);
			this.localNames.put(obs.getReferenceName(), obs.getName());
			this.observablesByReferenceName.put(obs.getReferenceName(), obs);
		}
		for (IObservable dependency : model.dependencies) {
			IObservable obs = Observable.concretize(dependency, resolvedPredicates, resolvedPredicatesContext);
			this.dependencies.add(obs);
			this.localNames.put(obs.getReferenceName(), obs.getName());
			this.observablesByReferenceName.put(obs.getReferenceName(), obs);
		}

	}

	private Model(IKimModel model, Namespace namespace, IMonitor monitor) {

		super(model);

		this.id = model.getName();
		this.namespace = namespace;
		this.instantiator = model.isInstantiator();
		this.learning = model.isLearningModel();
		this.scope = model.getScope();
		if (model.isErrors()) {
			this.addError("Syntax errors in k.IM model specifications");
		}
		this.setInactive(model.isInactive());

		setDeprecated(model.isDeprecated() || namespace.isDeprecated());

		IConcept context = null;
		boolean explicitContext = false;
		boolean first = true;

		for (IKimObservable observable : model.getObservables()) {

			Observable obs = Observables.INSTANCE.declare(observable, monitor);

			if (obs == null) {
				String err = "Undefined semantics for observable '" + observable.getDefinition() + "'";
				monitor.error(err, observable);
				addError(err);
			} else {

				if (first) {
					context = obs.is(Type.COUNTABLE) ? obs.getType()
							: Observables.INSTANCE.getContextType(obs.getType());
					explicitContext = context != null
							&& context.equals(Observables.INSTANCE.getDirectContextType(obs.getType()));
					first = false;

					/*
					 * for the first observable, check if there is an explicit context that
					 * specializes the natural observable's, and if so set the specialized flag.
					 */
					IConcept directCtx = Observables.INSTANCE.getDirectContextType(obs.getType());
					if (directCtx != null) {
						IConcept baseObs = Observables.INSTANCE.getBaseObservable(obs.getType());
						IConcept baseCtx = Observables.INSTANCE.getContextType(baseObs);
						/*
						 * second condition should be pre-validated but this excludes erroneous
						 * observables
						 */
						if (baseCtx == null || (!baseCtx.equals(directCtx) && directCtx.is(baseCtx))) {
							((Observable) obs).setSpecialized(true);
						}
					}
				}

				if (observable.hasAttributeIdentifier()) {
					attributeObservables.put(observable.getValue().toString(), obs);
				} else {
					observables.add(obs);
					observablesByReferenceName.put(obs.getReferenceName(), obs);
					localNames.put(obs.getReferenceName(), obs.getName());
				}
			}
		}

		for (IKimObservable dependency : model.getDependencies()) {
			Observable dep = Observables.INSTANCE.declare(dependency, monitor);
			if (dep == null) {
				String err = "Undefined semantics for dependency '" + dependency.getDefinition() + "'";
				monitor.error(err, dependency);
				addError(err);
				continue;
			}
			dependencies.add(dep);
			observablesByReferenceName.put(dep.getReferenceName(), dep);
			localNames.put(dep.getReferenceName(), dep.getName());
		}

		/*
		 * there is an archetype notation
		 */
		boolean hasArchetype = false;
		/*
		 * learner whose main observable is within the archetype, treated specially
		 */
		this.learnsWithinArchetype = false;
		/*
		 * the archetype itself
		 */
		this.archetype = null;

		/*
		 * direct (within) context for the main observable
		 */
		IConcept modelContext = null;

		if (isLearning() && getMainObservable() != null) {

			modelContext = Observables.INSTANCE.getDirectContextType(getObservables().get(0).getType());

			for (IObservable dependency : dependencies) {
				if ((hasArchetype = Annotations.INSTANCE.hasAnnotation(dependency, IModel.ARCHETYPE_ANNOTATION))) {
					archetype = dependency;
					break;
				}
			}

			if (modelContext != null && archetype.is(Type.COUNTABLE) && archetype.is(modelContext)) {
				/*
				 * if the model's archetype is the contextual observable in an explicit
				 * 'within', the primary output becomes "of" it and the learning will be done
				 * within each object and applied to the rest of the context. All predictors
				 * become optional in the context and remain as indication of what to look for
				 * in the archetype. This is assessed early so that we don't precontextualize
				 * the dependencies in this case.
				 */
				learnsWithinArchetype = true;
			}
		}

		int i = 0;
		for (IObservable dependency : dependencies) {
			if (context != null) {
				if (this.instantiator || learnsWithinArchetype) {

					/*
					 * we cannot know the context of resolution beforehand, so it will be
					 * contextualized at query time.
					 */
					((Observable) dependency).setMustContextualizeAtResolution(true);

				} else {
					try {
						dependencies.set(i,
								Observables.INSTANCE.contextualizeTo(dependency, context, explicitContext, monitor));
					} catch (Throwable e) {
						monitor.error(e, dependency);
						addError(ExceptionUtils.getStackTrace(e));
					}
				}
			}
			i++;
		}

		/*
		 * if this is a learning model without an archetype, add it as the dependency
		 * with the annotation and add the core "predicted" attribute to the output.
		 * TODO check behavior with distributed observables
		 */
		if (isLearning() && getMainObservable() != null) {

			this.distributesLearning = Annotations.INSTANCE.hasAnnotation(this, DISTRIBUTE_ANNOTATION);

			if (!hasArchetype) {

				Observable origin = (Observable) getMainObservable();
				Observable obsdep = (Observable) new ObservableBuilder(origin, monitor)
						.withTrait(Resources.INSTANCE.getWorldview()
								.getCoreConcept(Concepts.c(CoreOntology.NS.CORE_PREDICTED_ATTRIBUTE)))
						.buildObservable();

				observables.set(0, obsdep);
				localNames.put(obsdep.getReferenceName(), obsdep.getName());
				observablesByReferenceName.put(obsdep.getReferenceName(), obsdep);

				if (findDependency(origin) != null) {
					origin = (Observable) findDependency(origin);
				} else {
					dependencies.add(origin);
					localNames.put(origin.getReferenceName(), origin.getName());
					observablesByReferenceName.put(origin.getReferenceName(), origin);
				}

				if (origin.getAnnotations() == null) {
					origin.setAnnotations(new ArrayList<IAnnotation>());
				}
				origin.getAnnotations().add(Annotation.create(ARCHETYPE_ANNOTATION));

			} else if (learnsWithinArchetype) {

				/*
				 * switch observable; deactivate the predictors unless the @distribute
				 * annotation requires the model to compute the value over the entire context.
				 * No further action as these are only run explicitly so the resolution
				 * mechanism for the inherent observable in ObservationStrategy won't be
				 * triggered.
				 */
				IObservable inherent = getObservables().get(0).getBuilder(monitor).without(ObservableRole.CONTEXT)
						.of(modelContext).buildObservable();
				this.observables.set(0, inherent);
				this.localNames.put(inherent.getReferenceName(), inherent.getName());
				observablesByReferenceName.put(inherent.getReferenceName(), inherent);
				for (i = 1; i < dependencies.size(); i++) {
					if (Annotations.INSTANCE.hasAnnotation(dependencies.get(i), IModel.PREDICTOR_ANNOTATION)) {
						if (!distributesLearning) {
							((Observable) dependencies.get(i)).setActive(false);
						}
					}
				}
			}
		}

		if (getMainObservable() != null && getMainObservable().is(Type.PROCESS)) {

			Set<IConcept> changed = new HashSet<>();

			/*
			 * Any change that isn't explicitly output should be added
			 */
			for (int oo = 0; i < observables.size(); i++) {
				IObservable obs = observables.get(oo);
				if (obs != null && obs.is(Type.CHANGE)) {
					changed.add(Observables.INSTANCE.getDescribedType(obs.getType()));
				}
			}

			/*
			 * Add change in any secondary qualities that are affected by the process.
			 */
			List<IObservable> toAdd = new ArrayList<>();
			for (int oo = 1; oo < observables.size(); oo++) {
				IObservable obs = observables.get(oo);
				if (obs != null && obs.is(Type.QUALITY) && !changed.contains(obs.getType())) {
					if (Observables.INSTANCE.isAffectedBy(obs, getMainObservable())
							|| Observables.INSTANCE.isCreatedBy(obs, getMainObservable())) {
						IObservable cobs = obs.getBuilder(monitor).as(UnarySemanticOperator.CHANGE).buildObservable();
						toAdd.add(cobs);
						this.localNames.put(cobs.getReferenceName(), cobs.getName());
						observablesByReferenceName.put(cobs.getReferenceName(), cobs);
					} else {
						String err = "observable " + obs.getType().getDefinition()
								+ " output by a process model must be either affected or created by it";
						monitor.error(err, getStatement());
						addError(err);
					}
				}
			}

			this.observables.addAll(toAdd);
		}

		/*
		 * add source(s) in main declaration as computables
		 */
		if (!model.getResourceUrns().isEmpty()) {

			try {

				this.mergedResource = model.getResourceUrns().size() > 1 ? new MergedResource(model, monitor) : null;
				ComputableResource urnResource = validate(new ComputableResource(
						this.mergedResource == null ? model.getResourceUrns().get(0) : this.mergedResource.getUrn(),
						this.isInstantiator() ? Mode.INSTANTIATION : Mode.RESOLUTION), monitor);
				this.resources.add(urnResource);

				if (this.mergedResource != null) {
					this.multipleTimes = this.mergedResource.isDynamic();
				}

			} catch (Throwable t) {
				monitor.error(t.getMessage(), getStatement());
				addError(ExceptionUtils.getStackTrace(t));
			}

		} else if (model.getInlineValue().isPresent()) {
			Optional<Object> o = Optional.of(model.getInlineValue().get());
			if (model.getInlineValue().get() instanceof IKimConcept) {
				IConcept c = Concepts.INSTANCE.declare((IKimConcept) model.getInlineValue().get());
				if (c == null) {
					String err = "Concept literal " + model.getInlineValue().get() + " is unknown";
					monitor.error(err, o);
					addError(err);
				} else {
					o = Optional.of(c);
				}
			}
			this.resources.add(validate(new ComputableResource(o), monitor));
		}

		if (this.resources.size() > 0) {
			for (IObservable o : observables) {
				// let counts w/o units through
				if (o != null && ((Observable) o).isFluidUnits() && !o.is(Type.NUMEROSITY)) {
					String err = "Observables with unspecified units are not allowed in models that produce data through resources";
					monitor.error(err, o);
					addError(err);
				}
			}
		}

		/*
		 * all resources after 'using' or further classification/lookup transformations
		 */
		for (IContextualizable resource : model.getContextualization()) {
			try {
				this.resources.add(validate((ComputableResource) resource, monitor));
			} catch (Throwable e) {
				monitor.error("Model has resource validation errors", getStatement());
				addError("Resource validation error: " + ExceptionUtils.getStackTrace(e));
			}
		}

		this.contextualization = new Contextualization(model.getBehavior(), this);

		/*
		 * post-process the actions so that any accessory variable is tagged as such
		 */
		for (IAction action : this.contextualization) {
			for (IContextualizable ct : action.getComputation()) {
				if (ct.getTargetId() != null) {
					if (isKnownDependency(ct.getTargetId())) {
						monitor.error("Cannot target an action to dependency " + ct.getTargetId()
								+ ": dependencies are read-only in models", getStatement());
					} else {
						((ComputableResource) ct).setVariable(!isKnownObservable(ct.getTargetId()));
					}
				}
			}
		}

		/*
		 * validate typechain, units and final result vs. observable artifact type -
		 * AFTER the behavior has been processed!
		 */
		validateTypechain(monitor);

		/*
		 * validate required inputs from resources
		 */
		validateInputs(monitor);

		/*
		 * actions
		 */

		/*
		 * validate all actions
		 */
		for (IAction action : this.contextualization) {
			validateAction(action, monitor);
		}

		/*
		 * TODO validate final output of typechain vs. observable and mode Update: casts
		 * are now inserted, although any illegal type chain should be a compile error,
		 * or at least a warning if the cast is unlikely.
		 */

		if (model.getMetadata() != null) {
			getMetadata().putAll(model.getMetadata());
		}

		if (model.getDocstring() != null) {
			getMetadata().put(IMetadata.DC_COMMENT, model.getDocstring());
		}

	}

	public boolean isKnownDependency(String targetId) {
		for (IObservable observable : dependencies) {
			if (observable == null) {
				// only in error
				continue;
			}
			if (targetId.equals(observable.getName())) {
				return true;
			}
		}
		return false;
	}

	public boolean isKnownObservable(String targetId) {

		for (IObservable observable : observables) {
			if (targetId.equals(observable.getName())) {
				return true;
			}
		}

		if (getMainObservable() != null && getMainObservable().is(Type.CHANGE)) {
			IObservable iho = Observable.promote(Observables.INSTANCE.getDescribedType(getMainObservable().getType()));
			if (targetId.equals(iho.getName())) {
				return true;
			}
		}

		return false;
	}

	private IObservable getMainObservable() {
		return observables.size() > 0 && observables.get(0) != null ? observables.get(0) : null;
	}

	private void validateInputs(IMonitor monitor) {

		Map<String, IArtifact.Type> required = new HashMap<>();

		for (IContextualizable resource : resources) {
			if (resource.getUrn() != null) {
				IResource res = Resources.INSTANCE.resolveResource(resource.getUrn());
				if (res != null) {
					for (Pair<String, IArtifact.Type> input : resource.getInputs()) {
						required.put(input.getFirst(), input.getSecond());
					}
				}
			} else if (resource.getServiceCall() != null) {
				IPrototype prototype = resource.getServiceCall().getPrototype();
				if (prototype != null) {
					for (Argument arg : prototype.listImports()) {
						if (arg.isArtifact() && !arg.isOptional()) {
							required.put(arg.getName(), arg.getType());
						}
					}
				}
			} else if (resource.getLookupTable() != null) {
				// TODO
			}
		}

		for (String s : required.keySet()) {
			boolean ok = false;
			for (IObservable dependency : dependencies) {
				if (s.equals(dependency.getName()) && dependency.getArtifactType() == required.get(s)) {
					ok = true;
					break;
				}
			}

			if (!ok) {
				String err = "a dependency named " + s + " of type " + required.get(s) + " is required by a resource";
				monitor.error(err, this.getStatement());
				addError(err);
			}
		}
	}

	private void validateTypechain(IMonitor monitor) {

		if (observables.size() == 0 || observables.get(0) == null) {
			return;
		}

		Map<String, IArtifact.Type> typechain = new HashMap<>();

		/*
		 * start from a scalar geometry after merging any @intensive annotations. Gather
		 * the model's annotations by passing the first observable.
		 */
		this.geometry = Geometry.scalar();
		Map<ExtentDimension, ExtentDistribution> modelConstraints = getExtentConstraints(this.getObservables().get(0),
				monitor);
		for (Entry<ExtentDimension, ExtentDistribution> entry : modelConstraints.entrySet()) {
			if (entry.getValue() == ExtentDistribution.INTENSIVE) {
				mergeGeometry(Geometry.distributedIn(entry.getKey()), monitor);
			}
		}

		/*
		 * FIXME instantiators require the OUTPUTS to match the collapsed geometry of
		 * EACH output artifact, not that of the context.
		 */

		for (IContextualizable resource : resources) {

			if (this.observables.get(0).getDescriptionType() == IActivity.Description.CHARACTERIZATION
					|| this.observables.get(0).getDescriptionType() == IActivity.Description.CLASSIFICATION) {
				// must be a filter
				if (!isFilter(resource)) {
					monitor.error("all computations in attribute contextualizers must be filters", this.getStatement());
				}
			}

			String target = resource.getTargetId() == null ? this.observables.get(0).getName() : resource.getTargetId();
			IArtifact.Type type = Resources.INSTANCE.getType(resource);
			IGeometry geometry = Resources.INSTANCE.getGeometry(resource);

			if (type != null && !isFilter(resource)) {
				if (typechain.containsKey(target)) {
					// TODO check that the resource can take the current type
				}
				typechain.put(target, type);
			}
			mergeGeometry(geometry, monitor);
		}

		Scale cov = getCoverage(monitor);
		if (cov != null) {
			mergeGeometry(cov.asGeometry(), monitor);
		}

		if (geometry == null || geometry.isEmpty()) {
			geometry = Geometry.scalar();
		}

		for (IObservable observable : CollectionUtils.join(observables, dependencies)) {
			validateUnits(observable, monitor);
		}

		// check final type of observable against typechain
		for (IObservable observable : observables) {
			if (typechain.containsKey(observable.getName())) {
				IArtifact.Type required = observable.getArtifactType();
				if (required == IArtifact.Type.OBJECT && !this.isInstantiator()) {
					required = IArtifact.Type.VOID;
				}
				if (!IArtifact.Type.isCompatible(required, typechain.get(observable.getName()))) {
					String err = "the computation chain produces output of type " + typechain.get(observable.getName())
							+ " for " + observable.getName() + " when " + required + " is expected";
					monitor.error(err, this.getStatement());
					addError(err);
				}
			}
		}
	}

	private boolean isFilter(IContextualizable resource) {
		if (resource.getServiceCall() != null) {
			IPrototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
			if (prototype != null && prototype.isFilter()) {
				return true;
			}

		} else if (getMainObservable() != null
				&& getMainObservable().getDescriptionType() == Description.CHARACTERIZATION) {
			if (resource.getLiteral() instanceof IConcept) {

				/*
				 * characterizers using a literal are filters
				 */
				return true;

			} else if (resource.getUrn() != null) {

				/*
				 * May be a filter if the resource produces a concept with these parameters
				 */
				IResource res = Resources.INSTANCE.resolveResource(resource.getUrn());
				if (res != null) {
					IResourceAdapter adapter = Resources.INSTANCE.getResourceAdapter(res.getAdapterType());
					if (adapter != null) {
						Urn urn = new Urn(resource.getUrn());
						return adapter.getValidator().isObservationAllowed(res, urn.getParameters(),
								getMainObservable().getDescriptionType());
					}
				}

			}
		}
		return false;
	}

	private void validateUnits(IObservable observable, IMonitor monitor) {

		// for debugging only
		if (observable == null) {
			return;
		}

		((Observable) observable).setOriginatingModelId(this.getName());

		if (!Units.INSTANCE.needsUnits(observable) && observable.getUnit() != null) {
			/*
			 * this is pretty much guaranteed to result from rescaling, as the validator
			 * should prevent any other situation.
			 */
			String err = "Observable " + observable.getName() + " should not have units";
			monitor.error(err, observable);
			addError(err);
			return;
		}

		if (observable.is(Type.RATIO)) {
			// we don't do anything to avoid insanity
			return;
		}

		if (Units.INSTANCE.needsUnitScaling(observable)) {

			IUnit statedUnit = observable.getUnit();

			/*
			 * this will happen for transformed observables, e.g. normalized
			 */
			if (statedUnit == null) {
				return;
			}

			IUnit baseUnit = null;
			try {
				baseUnit = Units.INSTANCE.getDefaultUnitFor(observable);
			} catch (KlabValidationException e) {
				String err = "Observable " + observable.getName() + " default unit attribution raised an error: " + e;
				monitor.error(err, observable);
				addError(err);
				return;
			}

			if (baseUnit == null) {
				String err = "Cannot establish base unit for " + observable.getName()
						+ ": remove the unit or any transformations that do not preserve observation semantics";
				monitor.error(err, observable);
				baseUnit = Units.INSTANCE.getDefaultUnitFor(observable);
				addError(err);
				return;
			}

			Map<ExtentDimension, ExtentDistribution> constraints = getExtentConstraints(observable, monitor);
			UnitContextualization contextualization = Units.INSTANCE.getContextualization(baseUnit, this.geometry,
					constraints);

			/*
			 * if it's the same as the expected, everything's OK; inherit any aggregation
			 */
			if (statedUnit.isCompatible(contextualization.getChosenUnit())) {
				statedUnit.getAggregatedDimensions()
						.putAll(contextualization.getChosenUnit().getAggregatedDimensions());
				return;
			}

			/*
			 * if it's one of the others, add the extents, warn appropriately and return
			 */
			for (IUnit unit : contextualization.getCandidateUnits()) {
				if (statedUnit.isCompatible(unit)) {
					statedUnit.getAggregatedDimensions().putAll(unit.getAggregatedDimensions());
					monitor.warn("This observable's unit implies " + unit.getAggregatedDimensions()
							+ " aggregation over a " + ((Geometry) this.geometry).getLabel()
							+ " context. If this is intentional, add @extensive/@intensive annotations to the "
							+ (getObservables().get(0).equals(observable) ? "model" : "observable")
							+ " to remove this warning.", observable);
					return;
				}
			}

			/*
			 * if we get here, we have an incompatible unit
			 */
			String err = getName() + ": unit " + statedUnit + " is incompatible with a "
					+ ((Geometry) this.geometry).getLabel() + " context"
					+ (baseUnit.isCompatible(contextualization.getChosenUnit())
							? ". You may add @intensive/@extensive annotations to force dimensionality."
							: "");
			addError(err);
			monitor.error(err, observable);
		}

	}

	private Map<ExtentDimension, ExtentDistribution> getExtentConstraints(IObservable observable, IMonitor monitor) {

		Map<ExtentDimension, ExtentDistribution> ret = new HashMap<>();

		if (observable == null) {
			return ret;
		}

		boolean isModel = observable.equals(this.getObservables().get(0));

		/*
		 * model annotations act as default unless there are specific annotations on
		 * each observable. If so, they completely replace the annotation set (there is
		 * no inheritance).
		 */
		Collection<IAnnotation> annotations = filterAnnotations(isModel ? Annotations.INSTANCE.collectAnnotations(this)
				: Annotations.INSTANCE.collectAnnotations(observable));
		if (!isModel && annotations.isEmpty()) {
			annotations = filterAnnotations(Annotations.INSTANCE.collectAnnotations(this));
		}

		for (IAnnotation annotation : annotations) {

			for (Object o : annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME, List.class)) {
				switch (o.toString()) {
				case "space":
				case "area":
					ret.put(ExtentDimension.AREAL,
							annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
									: ExtentDistribution.INTENSIVE);
					break;
				case "line":
					ret.put(ExtentDimension.LINEAL,
							annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
									: ExtentDistribution.INTENSIVE);
					break;
				case "volume":
					ret.put(ExtentDimension.VOLUMETRIC,
							annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
									: ExtentDistribution.INTENSIVE);
					break;
				case "time":
					ret.put(ExtentDimension.TEMPORAL,
							annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
									: ExtentDistribution.INTENSIVE);
					break;
				case "numerosity":
					ret.put(ExtentDimension.CONCEPTUAL,
							annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
									: ExtentDistribution.INTENSIVE);
					break;
				default:
					if (monitor != null) {
						monitor.error(
								"Illegal extent in " + annotation.getName() + " annotation: " + o
										+ ": allowed are space|area, line, volume, time and numerosity",
								getStatement());
					}
				}
			}
		}

		return ret;
	}

	private Collection<IAnnotation> filterAnnotations(Collection<IAnnotation> object) {
		List<IAnnotation> ret = new ArrayList<>();
		for (IAnnotation annotation : object) {
			if ("intensive".equals(annotation.getName()) || "extensive".equals(annotation.getName())) {
				ret.add(annotation);
			}
		}
		return ret;
	}

	private void mergeGeometry(IGeometry geometry, IMonitor monitor) {

		if (geometry != null) {
			if (this.geometry == null) {
				this.geometry = geometry;
			} else if (!geometry.isEmpty()) {
				this.geometry = ((Geometry) this.geometry).merge(geometry);
				if (this.geometry == null) {
					String err = "model " + getName()
							+ " uses inconsistent space/time geometries across the computational chain";
					monitor.error(err, this.getStatement());
					addError(err);
				}
			}
		}
	}

	/**
	 * Used to resolve a derived strategy for the main observable through
	 * observation of the candidate observables and execution of the associated
	 * computational strategy.
	 * 
	 * @param mainObservable
	 * @param candidateObservable
	 * @param scope
	 */
	public Model(Observable mainObservable, ObservationStrategy candidateObservable, ResolutionScope scope) {
		super(null);
		this.derived = true;

		/*
		 * differentiate the name if there are resolved predicates
		 */
		String preds = "";
		List<String> pdescs = new ArrayList<>();
		for (IConcept c : mainObservable.getResolvedPredicates().values()) {
			pdescs.add(Concepts.INSTANCE.getCodeName(c));
		}
		Collections.sort(pdescs);
		for (String s : pdescs) {
			preds += (preds.isEmpty() ? "" : "_") + s;
		}

		this.id = mainObservable.getName() + (preds.isEmpty() ? "" : ("_" + preds)) + "_derived";
		this.localNames.put(mainObservable.getReferenceName(), mainObservable.getName());
		this.observablesByReferenceName.put(mainObservable.getReferenceName(), mainObservable);
		this.namespace = scope.getResolutionNamespace();
		this.contextualization = new Contextualization(null, this);
		this.observables.add(mainObservable);
		this.observationStrategy = candidateObservable.getStrategy();
		this.dependencies.addAll(candidateObservable.getObservables());
		for (IObservable dep : this.dependencies) {
			this.localNames.put(dep.getReferenceName(), dep.getName());
			this.observablesByReferenceName.put(dep.getReferenceName(), dep);
		}
		if (candidateObservable.getComputation() != null) {
			this.resources.addAll(candidateObservable.getComputation());
		}
		if (mainObservable.is(Type.COUNTABLE)
				|| mainObservable.getDescriptionType() == IActivity.Description.CLASSIFICATION) {
			this.instantiator = true;
		}
	}

	/**
	 * Create a model for the changed values from a resolver or instantiator of the
	 * passed observable, resolved by the merged resource that has been already
	 * checked for coverage of the temporal scope. The resulting model will have the
	 * resource and all the computations from the original one. If we pass a null
	 * merged resource, we use the original one instead, as it's been established to
	 * be able to handle time changes.
	 * 
	 * @param mainObservable
	 * @param resource
	 * @param scope
	 */
	public Model(IObservable mainObservable, MergedResource resource, IModel originalModel,
			ResolutionScope scope) {
		
		super(null);
		if (originalModel instanceof RankedModel) {
			// computables must be validated by the original model. This is pretty ugly of
			// course.
			originalModel = ((RankedModel) originalModel).getDelegate();
		}

		this.derived = true;
		this.id = mainObservable.getName() + "_resolved_change";
		IObservable changeObservable = mainObservable.getBuilder(scope.getMonitor()).as(UnarySemanticOperator.CHANGE)
				.buildObservable();
		this.namespace = scope.getResolutionNamespace();
		this.contextualization = new Contextualization(null, this);
		this.observables.add(changeObservable);
		this.localNames.put(changeObservable.getReferenceName(), changeObservable.getName());
		this.observablesByReferenceName.put(changeObservable.getReferenceName(), changeObservable);
		this.coverage = ((Model)originalModel).coverage;

		if (resource != null) {
			this.resources.add(new ComputableResource(resource.getUrn(), Mode.RESOLUTION));
		}
		for (int i = (resource == null ? 0 : 1); i < ((Model) originalModel).getComputation().size(); i++) {
			ComputableResource computation = (ComputableResource) ((Model) originalModel).getComputation().get(i);
			this.resources.add(((Model) originalModel).validate(computation.copy(), scope.getMonitor()));
		}
	}

	/**
	 * Create a model for the changed values from a resolver or instantiator of the
	 * passed observable, resolved by the merged resource that has been already
	 * checked for coverage of the temporal scope. The resulting model will have the
	 * resource and all the computations from the original one. If we pass a null
	 * merged resource, we use the original one instead, as it's been established to
	 * be able to handle time changes.
	 * 
	 * FIXME/CHECK: UNUSED
	 * 
	 * @param mainObservable
	 * @param resource
	 * @param scope
	 */
	public Model(IObservable changeObservable, IModel originalModel, ResolutionScope scope) {
		super(null);
		if (originalModel instanceof RankedModel) {
			originalModel = ((RankedModel) originalModel).getDelegate();
		}
		this.derived = true;
		this.id = changeObservable.getName() + "_resolved_change";
		this.namespace = scope.getResolutionNamespace();
		this.contextualization = new Contextualization(null, this);
		this.observables.add(changeObservable);
		this.localNames.put(changeObservable.getReferenceName(), changeObservable.getName());
		this.observablesByReferenceName.put(changeObservable.getReferenceName(), changeObservable);
		this.coverage = scope.getScale();
		for (int i = 0; i < ((Model) originalModel).getComputation().size(); i++) {
			ComputableResource computation = (ComputableResource) ((Model) originalModel).getComputation().get(i);
			this.resources.add(((Model) originalModel).validate(computation.copy(), scope.getMonitor()));
		}
	}

//	public Model(IObservable mainObservable, String resolvedChangingObservationName, ResolutionScope scope) {
//		super(null);
//		this.derived = true;
//		this.id = mainObservable.getName() + "_resolved_change";
//		this.namespace = scope.getResolutionNamespace();
//		this.contextualization = new Contextualization(null, this);
//		this.observables.add(mainObservable);
//		this.localNames.put(mainObservable.getReferenceName(), mainObservable.getName());
//		this.observablesByReferenceName.put(mainObservable.getReferenceName(), mainObservable);
//		this.coverage = scope.getScale();
//		this.resources.add(
//				Klab.INSTANCE.getRuntimeProvider().getChangeResolver(mainObservable, resolvedChangingObservationName));
//	}

	public Model(IViewModel view) {
		super(null);
		// Observable is the void concept (non-semantic artifact); all the independent
		// observables in the view as dependencies, the view compilation as code
		this.derived = true;
		this.viewModel = view;
		this.getAnnotations().addAll(view.getAnnotations());
		this.namespace = (Namespace) view.getNamespace();
		Observable mainObservable = Observable.promote(Concepts.c(NS.CORE_VOID));
		this.observables.add(mainObservable);
		this.localNames.put(mainObservable.getReferenceName(), mainObservable.getName());
		this.observablesByReferenceName.put(mainObservable.getReferenceName(), mainObservable);

		this.id = view.getId() + "_resolver";
		this.contextualization = new Contextualization(null, this);
		this.dependencies.addAll(view.getObservables());
		for (IObservable obs : this.dependencies) {
			this.localNames.put(obs.getReferenceName(), obs.getName());
			this.observablesByReferenceName.put(obs.getReferenceName(), obs);
		}
		this.resources.add(Klab.INSTANCE.getRuntimeProvider().getViewResolver(view));
	}

	/**
	 * Validate URNs, tables, classifications, inline values against network,
	 * observables etc. Called in sequence so it should maintain the chain of
	 * processing and validate each step, until the final type that should be
	 * validated by the upstream constructor.
	 * <p>
	 * Functions should validate all required parameters vs. outputs and
	 * dependencies
	 * 
	 * @param resource
	 * @return
	 */
	private ComputableResource validate(ComputableResource resource, IMonitor monitor) {

		if (resource.isVariable()) {
			// these are just fine as they are
			return resource;
		}

		if (resource.getClassification() != null) {

			Classification classification = new Classification(resource.getClassification());

			/*
			 * TODO validate that all concepts are children of the described concept in the
			 * main observable (if TYPE) - URGENTLY because the team is already mixing
			 * landcover with mayonnaise and motorcycles
			 */

			resource.setValidatedResource(classification);

		} else if (resource.getLookupTable() != null) {

			resource.setValidatedResource(new LookupTable(resource.getLookupTable()));

		} else if (resource.getAccordingTo() != null) {

			IClassification classification = Types.INSTANCE
					.createClassificationFromMetadata(getObservables().get(0).getType(), resource.getAccordingTo());
			resource.setValidatedResource(classification);

		} else if (resource.getUrn() != null) {

			// ensure resource is online; turn model off if not
			IResource res = Resources.INSTANCE.resolveResource(resource.getUrn());
			if (res == null) {
				monitor.error("resource " + resource.getUrn() + " is unknown or has errors", getStatement());
				this.setInactive(true);
			}
			if (res != null) {

				if (res instanceof Resource) {
					for (INotification notification : ((Resource) res).getNotifications()) {
						switch (notification.getLevel()) {
						case "SEVERE":
							monitor.error(notification.getMessage(), getStatement());
							break;
						case "WARNING":
							monitor.warn(notification.getMessage(), getStatement());
							break;
						// info and debug do not appear
						}
					}
				}

				if (res.hasErrors()) {
					this.setInactive(true);
				}

				// store resource
				resourcesUsed.add(res);

				// store geometry from resource, to be merged with model's when coverage is
				// asked for
				this.resourceCoverage = Scale.create(res.getGeometry());

				if (this.resourceCoverage.getTime() != null
						&& this.resourceCoverage.getTime().getTimeType() == ITime.Type.GRID) {
					this.multipleTimes = true;
				}

				// if resource is local, make the namespace unpublishable
				if (Urns.INSTANCE.isLocal(resource.getUrn())) {
					namespace.setPublishable(false);
				}
			}
		} else if (resource.getServiceCall() != null) {
			IPrototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
			if (prototype != null) {
				for (Argument argument : prototype.listArguments()) {
					if (argument.isArtifact()) {
						IObservable dependency = findDependency(argument.getName());
						if (dependency == null && !argument.isOptional()) {
							String err = "contextualizer " + prototype.getName() + " requires a dependency named "
									+ argument.getName();
							monitor.error(err, getStatement());
							addError(err);
						} else if (dependency != null
								&& !IArtifact.Type.isCompatible(argument.getType(), dependency.getArtifactType())) {
							String err = "contextualizer " + prototype.getName() + " requires type "
									+ argument.getType().name().toLowerCase() + "for dependency " + argument.getName()
									+ ":  " + dependency.getArtifactType().name().toLowerCase() + " was supplied";
							monitor.error(err, getStatement());
							addError(err);
						}
					}
				}
			} else {
				String err = "unknown contextualizer function: " + resource.getServiceCall().getName();
				monitor.error(err, resource.getServiceCall());
				addError(err);
			}
		} /*
			 * TODO bring this back as a merged geometry for the merging resource else if
			 * (resource.getMergedUrns() != null) { // intersect resource coverage Scale
			 * rscale = null; for (String murn : resource.getMergedUrns()) { if
			 * (murn.contains(":")) { IResource res =
			 * Resources.INSTANCE.resolveResource(murn); if (res == null) { //
			 * monitor.send(new CompileNo); this.setInactive(true); } else { rscale =
			 * Scale.create(res.getGeometry()); } } else { IKimObject obj =
			 * Resources.INSTANCE.getModelObject(murn); if (obj instanceof Model) { rscale =
			 * ((Model) obj).getCoverage(monitor); } } if (rscale != null) { if
			 * (this.resourceCoverage == null) { this.resourceCoverage = rscale; } else {
			 * this.resourceCoverage = this.resourceCoverage.merge(rscale,
			 * LogicalConnector.INTERSECTION); } } else {
			 * monitor.error("unknown resource or model " + murn + " in merging statement",
			 * getStatement()); } } // set it in the resource so we have it
			 * resource.setMergedGeometry(this.resourceCoverage.getGeometry()); }
			 */

		return resource;
	}

	/**
	 * Find a dependency by name.
	 * 
	 * @param name
	 * @return
	 */
	public IObservable findDependency(String name) {
		for (IObservable dependency : dependencies) {
			if (dependency.getName().equals(name)) {
				return dependency;
			}
		}
		return null;
	}

	/**
	 * Find a dependency by concept. Uses resolves == 0 for the check.
	 * 
	 * @param name
	 * @return
	 */
	public IObservable findDependency(IConcept concept) {
		for (IObservable dependency : dependencies) {
			if (dependency.getType().getSemanticDistance(concept) == 0) {
				return dependency;
			}
		}
		return null;
	}

	/**
	 * Find a dependency by equating observables. Uses resolves == 0 for the check.
	 * 
	 * @param name
	 * @return
	 */
	public IObservable findDependency(IObservable concept) {
		for (IObservable dependency : dependencies) {
			if (((Observable) concept).resolvesStrictly((Observable) dependency)) {
				return dependency;
			}
		}
		return null;
	}

	/**
	 * Find an output by equating observables. Uses resolves == 0 for the check.
	 * 
	 * @param name
	 * @return
	 */
	public IObservable findOutput(IObservable concept) {
		for (IObservable observable : observables) {
			if (((Observable) concept).resolvesStrictly((Observable) observable)) {
				return observable;
			}
		}
		return null;
	}

	/**
	 * Find an output by concept. Uses resolves == 0 for the check.
	 * 
	 * @param name
	 * @return
	 */
	public IObservable findOutput(IConcept concept) {
		for (IObservable output : observables) {
			if (output.getType().getSemanticDistance(concept) == 0) {
				return output;
			}
		}
		return null;
	}

	/**
	 * @param action
	 * @param monitor
	 */
	private void validateAction(IAction action, IMonitor monitor) {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return "[" + getName() + "]";
	}

	@Override
	public List<IObservable> getObservables() {
		return observables;
	}

	@Override
	public Map<String, IObservable> getAttributeObservables() {
		return attributeObservables;
	}

	@Override
	public String getLocalNameFor(IObservable observable) {
		return observable == null ? null : this.localNames.get(observable.getReferenceName());
	}

	@Override
	public boolean isResolved() {

		// TODO all resources have no parameters or all parameters are resolved through
		// resources with
		// no parameters.
		// TODO also check 'change to' status on main observable. And maybe geometry
		// (vs. context?
		// Should we check in context?)
		if (dependencies.size() > 0) {
			return false;
		}

		for (IContextualizable resource : resources) {
			// TODO TODO this is a temp fix to make the tests run.
			if (!resource.getInputs().isEmpty()) {
				return false;
			}
		}
		// if (resource != null) {
		// return true;
		// }
		// if (contextualizerResource != null) {
		// return !contextualizerResource.get().getGeometry().isEmpty();
		// }
		// TODO temp rationale: empty resource set = pass-through model
		return !resources.isEmpty();
	}

	@Override
	public boolean isInstantiator() {
		return instantiator;
	}

	@Override
	public boolean isReinterpreter() {
		// TODO Auto-generated method stub
		return reinterpreter;
	}

	@Override
	public boolean isAvailable() {

		long now = System.currentTimeMillis();
		if ((now - this.lastResourceCheck) > Configuration.INSTANCE.getResourceRecheckIntervalMs()) {
			this.available = true;
			for (IResource resource : resourcesUsed) {
				if (!Resources.INSTANCE.isResourceOnline(resource)) {
					this.available = false;
					break;
				}
			}
		}
		return this.available;
	}

	@Override
	public Collection<IDocumentation> getDocumentation() {

		List<IDocumentation> ret = new ArrayList<>();

		/*
		 * TODO collect docs from tables and the like. Use scan annotations on the
		 * syntactic peers. Annotations.INSTANCE.collectAnnotations(model)
		 */

		for (IAnnotation annotation : getAnnotations()) {
			if (annotation.getName().equals(IDocumentationService.DOCUMENTED_ANNOTATION_ID)) {
				String docId = annotation.get("value", String.class);
				if (docId == null) {
					docId = annotation.get("id", String.class);
				}

				if (docId != null && this.getNamespace().getProject() != null) {
					ret.add(Documentation.INSTANCE.getDocumentation(docId, annotation,
							this.getNamespace().getProject()));
				}
			}
		}
		return ret;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.namespace.getName() + "." + id;
	}

	@Override
	public INamespace getNamespace() {
		return namespace;
	}

	@Override
	public List<IObservable> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<IObservable> dependencies) {
		this.dependencies = dependencies;
	}

	public void setObservables(List<IObservable> observables) {
		this.observables = observables;
	}

	public void setAttributeObservables(Map<String, IObservable> attributeObservables) {
		this.attributeObservables = attributeObservables;
	}

	public void setNamespace(Namespace namespace) {
		this.namespace = namespace;
	}

	@Override
	public Contextualization getContextualization() {
		return contextualization;
	}

	@Override
	public Scope getScope() {
		return this.scope;
	}

	/**
	 * Build and return the scale, if any, specified for the model (possibly along
	 * with any constraints in the namespace it contains).
	 * 
	 * @param monitor
	 * @return a new scale, possibly empty, never null.
	 * @throws KlabException
	 */
	public Scale getCoverage(IMonitor monitor) throws KlabException {

		if (this.coverage == null) {

			Set<Dimension.Type> dims = new HashSet<>();

			try {
				Collection<IExtent> extents = new ArrayList<>();
				if (contextualization != null) {
					extents.addAll(contextualization.getExtents(monitor));
					for (IExtent extent : extents) {
						dims.add(extent.getType());
					}
				}

				for (IAnnotation annotation : getAnnotations()) {
					if ("space".equals(annotation.getName())) {
						if (dims.contains(Dimension.Type.SPACE)) {
							monitor.error("cannot specify spatial extent in more than one way");
						} else {
							extents.add(Space.create(annotation));
						}
					} else if ("time".equals(annotation.getName())) {
						if (dims.contains(Dimension.Type.TIME)) {
							monitor.error("cannot specify temporal extent in more than one way");
						} else {
							extents.add(Time.create(annotation));
						}
					}
				}

				this.coverage = Scale.create(extents);
				if (resourceCoverage != null) {
					this.coverage = this.coverage.merge(resourceCoverage, LogicalConnector.INTERSECTION);
				}
				Scale nsScale = namespace.getCoverage(monitor);
				if (nsScale != null) {
					this.coverage = this.coverage.merge(nsScale, LogicalConnector.INTERSECTION);
				}
			} catch (Throwable e) {
				monitor.error(e);
				this.inactive = true;
			}
		}
		return this.coverage;
	}

	/**
	 * Return all the computational steps required to compute the model. If there
	 * are annotations that define parameters (possibly interactive), add them to
	 * the computables.
	 * 
	 * @param transition the transition to be computed
	 * @return the indirectAdapters for the model at the transition
	 */
	@Override
	public List<IContextualizable> getComputation() {

		List<IAnnotation> parameters = new ArrayList<>();
		for (IAnnotation annotation : getAnnotations()) {
			if (annotation.getName().equals("parameter")) {
				parameters.add(annotation);
			}
		}

		List<IContextualizable> ret = new ArrayList<>();
		for (IContextualizable resource : resources) {
			ComputableResource res = ((ComputableResource) resource).copy();
			if (parameters.size() > 0) {
				res.addParameters(parameters);
			}
			ret.add(res);
		}
		for (Trigger trigger : Trigger.values()) {
			for (IAction action : contextualization.getActions(trigger)) {
				for (IContextualizable resource : action.getComputation()) {
					ComputableResource res = ((ComputableResource) resource).copy();
					if (parameters.size() > 0) {
						res.addParameters(parameters);
					}
					ret.add(res);
				}
			}
		}
		return ret;
	}

	/**
	 * Get the output that can satisfy this observable, possibly with mediation. If
	 * we are matching a dereified output of an instantiator, give it back its
	 * inherency before returning it.
	 * 
	 * @param observable
	 * @return an existing output observable or null
	 */
	public Observable getCompatibleOutput(Observable observable, IConcept context, IMonitor monitor) {

		for (IObservable output : observables) {
			if (output.resolves(observable, context)) {
				return (Observable) output;
			}
		}
		for (String key : attributeObservables.keySet()) {
			IObservable output = attributeObservables.get(key);
			if (output.resolves(observable, context)) {
				return (Observable) output.getBuilder(monitor).of(getMainObservable().getType())
						.withDereifiedAttribute(key).buildObservable();
			}
		}

//        /*
//         * predicate observables are mandatorily contextualized during resolution but the query may
//         * not contain the context inherency, so we can be lenient and check the mere observable
//         * using is() rather than resolves().
//         * 
//         * FIXME for now we also let X of C resolve X within C - not sure if the transposition to
//         * inherency for the model observable should be removed, but it's a big logics revision and
//         * should be done within a full reassessment.
//         */
//        if (observable.getType().is(Type.PREDICATE)) {
//            for (IObservable output : observables) {
//                if (output.getType().is(observable.getType())) {
//                    return (Observable) output;
//                }
//            }
//            // if we get here, no resolution happened, try with switched inherency FIXME remove
//            for (IObservable output : observables) {
//                if (output.getType().is(Type.PREDICATE) && Observables.INSTANCE.getDirectInherentType(output.getType()) != null) {
//                    IConcept inherent = Observables.INSTANCE.getDirectInherentType(output.getType());
//                    IObservable alternative = observable.getBuilder(monitor).without(ObservableRole.CONTEXT).of(inherent).buildObservable();
//                    Observable ret = getCompatibleOutput((Observable)alternative, context, monitor);
//                    if (ret != null) {
//                        return ret;
//                    }
//                }
//            }
//        }

		return null;
	}

	/**
	 * Get the input that can satisfy this observable, possibly with mediation.
	 * 
	 * @param observable
	 * @return an existing output observable or null
	 */
	public Observable getCompatibleInput(Observable observable) {
		for (IObservable input : dependencies) {
			if (input.getType().getSemanticDistance(observable.getType()) >= 0) {
				return (Observable) input;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Model && getName().equals(((Model) obj).getName());
	}

	@Override
	public List<IContextualizable> getResources() {
		return resources;
	}

	@Override
	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}

	@Override
	public IKimModel getStatement() {
		return (IKimModel) super.getStatement();
	}

	@Override
	public boolean isSemantic() {
		return getStatement().isSemantic();
	}

	@Override
	public boolean isLearning() {
		return learning;
	}

	public boolean isDerived() {
		return derived;
	}

	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	/**
	 * Utility for clarity in resolution
	 * 
	 * @return
	 */
	public List<IObservable> getArchetypes() {

		List<IObservable> ret = new ArrayList<>();
		for (IObservable dependency : getDependencies()) {
			if (Annotations.INSTANCE.hasAnnotation(dependency, IModel.ARCHETYPE_ANNOTATION)) {
				ret.add(dependency);
			}
		}
		return ret;
	}

	/**
	 * Utility for clarity in resolution
	 * 
	 * @return
	 */
	public List<IObservable> getPredictors() {

		List<IObservable> ret = new ArrayList<>();
		for (IObservable dependency : getDependencies()) {
			if (Annotations.INSTANCE.hasAnnotation(dependency, IModel.PREDICTOR_ANNOTATION)) {
				ret.add(dependency);
			}
		}
		return ret;
	}

	/**
	 * The observation strategy implemented by this model. This will always be
	 * DIRECT unless the model comes from a derived observation, in which case it
	 * may also be FILTERING or DEREIFYING.
	 * 
	 * @return
	 */
	public Strategy getObservationStrategy() {
		return observationStrategy;
	}

	/**
	 * True if the model is a learning model for a quality that is learned within a
	 * countable. In this case, the dependencies will only serve to define the
	 * predictors.
	 * 
	 * @return
	 */
	public boolean learnsWithinArchetype() {
		return learnsWithinArchetype;
	}

	/**
	 * If there's an archetype, return it. Does not handle well the situation where
	 * there could be > 1.
	 * 
	 * @return
	 */
	public IObservable getArchetype() {
		return this.archetype;
	}

	/**
	 * Model learns within an archetype but then builds a state for the entire
	 * context with the learned quality.
	 * 
	 * @return
	 */
	public boolean distributesLearning() {
		return this.distributesLearning;
	}

	@Override
	public boolean changesIn(Dimension.Type dimension, IScale scale) {
		if (dimension == Dimension.Type.TIME) {
			// TODO handle scale
			return multipleTimes;
		}
		// TODO handle space when we need it
		return false;
	}

	public MergedResource getMergedResource() {
		return this.mergedResource;
	}

	/**
	 * View models are only created on observation with the specific constructor and
	 * this method is non-API.
	 * 
	 * @return
	 */
	public IViewModel getViewModel() {
		return this.viewModel;
	}

	@Override
	public Collection<IConcept> getRequiredTraits() {
		if (this.requiredTraits == null) {
			this.requiredTraits = new HashSet<>();
			for (IObservable observable : observables) {
				if (observable != null) {
					for (IConcept role : Roles.INSTANCE.getRoles(observable.getType())) {
						if (role.isAbstract()) {
							this.requiredTraits.add(role);
						}
					}
				}
			}
			for (IObservable observable : dependencies) {
				if (observable != null) {
					if (observable != null) {
						for (IConcept role : Roles.INSTANCE.getRoles(observable.getType())) {
							if (role.isAbstract()) {
								this.requiredTraits.add(role);
							}
						}
					}
				}
			}
		}
		return this.requiredTraits;
	}

	@Override
	public boolean isAbstract() {
		return !getAbstractTraits().isEmpty();
	}

	@Override
	public Collection<IConcept> getAbstractTraits() {
		if (abstractTraits_ == null) {
			abstractTraits_ = new HashSet<>();
			for (IObservable observable : observables) {
				if (observable != null) {
					abstractTraits_.addAll(observable.getAbstractPredicates());
				}
			}
			for (IObservable observable : dependencies) {
				if (observable != null && observable.getType() != null && !observable.isGeneric()) {
					if (observable != null) {
						abstractTraits_.addAll(observable.getAbstractPredicates());
					}
				}
			}
		}
		return abstractTraits_;
	}

	public org.integratedmodelling.klab.rest.DocumentationNode.Model getBean() {
		org.integratedmodelling.klab.rest.DocumentationNode.Model ret = new org.integratedmodelling.klab.rest.DocumentationNode.Model();
		// TODO
		return ret;
	}

	@Override
	public IObservable getObservableFor(String localName) {
		String referenceName = localNames.inverseBidiMap().get(localName);
		return referenceName == null ? null : observablesByReferenceName.get(referenceName);
	}

}
