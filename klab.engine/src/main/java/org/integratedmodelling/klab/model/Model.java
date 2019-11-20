package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Documentation;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.mediation.IUnit.Contextualization;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.ExtentDistribution;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.provenance.IActivity;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IDocumentationService;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ObservationStrategy;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionUtils;

public class Model extends KimObject implements IModel {

	private String id;
	private List<IObservable> observables = new ArrayList<>();
	private List<IObservable> dependencies = new ArrayList<>();
	private Map<String, IObservable> attributeObservables = new HashMap<>();
	private Namespace namespace;
	private Behavior behavior;
	private List<IContextualizable> resources = new ArrayList<>();
	private boolean instantiator;
	private boolean reinterpreter;
	private boolean inactive;
	private boolean learning;

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

	// only for the delegate RankedModel
	protected Model() {
		super(null);
	}

	/**
	 * 
	 * @param model
	 * @param namespace
	 * @param monitor
	 * @return a new model
	 */
	public static Model create(IKimModel model, Namespace namespace, IMonitor monitor) {
		return new Model(model, namespace, monitor);
	}

	private Model(IKimModel model, Namespace namespace, IMonitor monitor) {

		super(model);

		this.id = model.getName();
		this.namespace = namespace;
		this.instantiator = model.isInstantiator();
		this.learning = model.isLearningModel();
		this.scope = model.getScope();
		this.setErrors(model.isErrors());
		this.setInactive(model.isInactive());

		setDeprecated(model.isDeprecated() || namespace.isDeprecated());

		IConcept context = null;
		boolean explicitContext = false;
		boolean first = true;
		
		for (IKimObservable observable : model.getObservables()) {

			Observable obs = Observables.INSTANCE.declare(observable, monitor);

			if (first) {
				context = obs.is(Type.COUNTABLE) ? obs.getType()
						: Observables.INSTANCE.getContextType(obs.getType());
				explicitContext = context != null && context.equals(Observables.INSTANCE.getDirectContextType(obs.getType()));
				first = false;
			}

			if (observable.hasAttributeIdentifier()) {
				attributeObservables.put(observable.getValue().toString(), obs);
			} else {
				observables.add(obs);
			}
		}

		for (IKimObservable dependency : model.getDependencies()) {
			Observable dep = Observables.INSTANCE.declare(dependency, monitor);
			if (context != null) {
				if (this.instantiator) {
					
					/*
					 * we cannot know the context of resolution beforehand, so it will be
					 * contextualized at query time.
					 */
					dep.setMustContextualizeAtResolution(true);

				} else {
					try {
						dep = Observables.INSTANCE.contextualizeTo(dep, context, explicitContext, monitor);
					} catch (Throwable e) {
						monitor.error(e, dependency);
						setErrors(true);
					}
				}
			}
			dependencies.add(dep);
		}

		/*
		 * add source(s) in main declaration as computables
		 */
		if (!model.getResourceUrns().isEmpty()) {
			ComputableResource urnResource = validate(new ComputableResource(model.getResourceUrns().get(0),
					this.isInstantiator() ? Mode.INSTANTIATION : Mode.RESOLUTION), monitor);
			for (int i = 1; i < model.getResourceUrns().size(); i++) {
				urnResource.chainResource(validate(new ComputableResource(model.getResourceUrns().get(i),
						this.isInstantiator() ? Mode.INSTANTIATION : Mode.RESOLUTION), monitor));
			}
			this.resources.add(urnResource);
		} else if (model.getResourceFunction().isPresent()) {
			this.resources.add(validate(new ComputableResource(model.getResourceFunction().get(),
					this.isInstantiator() ? Mode.INSTANTIATION : Mode.RESOLUTION), monitor));
		} else if (model.getInlineValue().isPresent()) {
			this.resources.add(validate(new ComputableResource(model.getInlineValue()), monitor));
		}

		if (this.resources.size() > 0) {
			for (IObservable o : observables) {
				if (o != null && ((Observable) o).isFluidUnits()) {
					monitor.error(
							"Observables with unspecified units are not allowed in models that produce data through resources",
							o);
					setErrors(true);
				}
			}
		}

		if (model.isResourceMerger()) {
			// turn all resources into a merged one, after validation
			List<IResource> ress = new ArrayList<>();
			for (IContextualizable r : resources) {
				String urn = ((ComputableResource)r).getUrn();
				if (urn == null) {
					monitor.error("Cannot use anything but URNs in a 'merging' clause", getStatement());
					setErrors(true);
				}
				ress.add(Resources.INSTANCE.resolveResource(urn));
			}
			try {
				this.resources.clear();
				IResource resource = Resources.INSTANCE.createMergedTemporalResource(ress);
				if (resource != null) {
					this.resources.add(new ComputableResource(resource.getUrn(), Mode.RESOLUTION));
				}
			} catch (Throwable e) {
				monitor.error("Model has resource validation errors", getStatement());
				setErrors(true);
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
				setErrors(true);
			}
		}

		this.behavior = new Behavior(model.getBehavior(), this);

		/*
		 * post-process the actions so that any accessory variable is tagged as such
		 */
		for (IAction action : this.behavior) {
			for (IContextualizable ct : action.getComputation()) {
				if (ct.getTargetId() != null) {
					/*
					 * check if this is an output or affected quality
					 */
				}
			}
		}
		
		/*
		 * validate typechain, units and final result vs. observable artifact type -
		 * AFTER the behavior has been processed!
		 */
		validateTypechain(monitor);

		/*
		 * actions
		 */

		/*
		 * validate all action
		 */
		for (IAction action : this.behavior) {
			validateAction(action, monitor);
		}

		/*
		 * TODO validate final output of typechain vs. observable and mode
		 * 
		 * Update: casts are now inserted, although any illegal type chain should be a
		 * compile error, or at least a warning if the cast is unlikely.
		 */

		if (model.getMetadata() != null) {
			getMetadata().putAll(model.getMetadata().getData());
		}

		if (model.getDocstring() != null) {
			getMetadata().put(IMetadata.DC_COMMENT, model.getDocstring());
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

			if (this.observables.get(0).getDescription() == IActivity.Description.CHARACTERIZATION
					|| this.observables.get(0).getDescription() == IActivity.Description.CLASSIFICATION) {
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
				if (!IArtifact.Type.isCompatible(required, typechain.get(observable.getName()))) {
					monitor.error("the computation produces output of type " + typechain.get(observable.getName())
							+ " for " + observable.getName() + " when " + required + " is expected",
							this.getStatement());
					setErrors(true);
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
			monitor.error("Observable " + observable.getName() + " should not have units", observable);
			setErrors(true);
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

			IUnit baseUnit = Units.INSTANCE.getDefaultUnitFor(observable);

			if (baseUnit == null) {
				monitor.error(
						"Cannot establish base unit for " + observable.getName()
								+ ": remove the unit or any transformations that do not preserve observation semantics",
						observable);
				setErrors(true);
				return;
			}

			Contextualization contextualization = Units.INSTANCE.getContextualization(baseUnit, this.geometry,
					getExtentConstraints(observable, monitor));

			/*
			 * if it's the same as the expected, everything's OK; inherit any aggregation
			 */
			if (statedUnit.isCompatible(contextualization.getChosenUnit())) {
				statedUnit.getAggregatedDimensions()
						.addAll(contextualization.getChosenUnit().getAggregatedDimensions());
				return;
			}

			/*
			 * if it's one of the others, add the extents, warn appropriately and return
			 */
			for (IUnit unit : contextualization.getCandidateUnits()) {
				if (statedUnit.isCompatible(unit)) {
					statedUnit.getAggregatedDimensions().addAll(unit.getAggregatedDimensions());
					monitor.warn("This observable's unit implies " + unit.getAggregatedDimensions()
							+ " aggregation over a " + ((Geometry) this.geometry).getLabel()
							+ " context. If this is intentional, add an @extensive annotation to the "
							+ (getObservables().get(0).equals(observable) ? "model" : "observable")
							+ " to remove this warning.", observable);
					return;
				}
			}

			/*
			 * if we get here, we have an incompatible unit
			 */
			setErrors(true);
			monitor.error("Unit " + statedUnit + " is incompatible with this observable in a "
					+ ((Geometry) this.geometry).getLabel() + " context"
					+ (baseUnit.isCompatible(contextualization.getChosenUnit())
							? ". You may add an @intensive annotation to the model to force its dimensionality."
							: ""),
					observable);
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
		Collection<IAnnotation> annotations = isModel ? Annotations.INSTANCE.collectAnnotations(this)
				: Annotations.INSTANCE.collectAnnotations(observable);
		if (!isModel && annotations.isEmpty()) {
			annotations = Annotations.INSTANCE.collectAnnotations(this);
		}

		for (IAnnotation annotation : annotations) {
			if (annotation.getName().equals("extensive") || annotation.getName().equals("intensive")) {
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
					monitor.error(
							"model " + getName()
									+ " uses inconsistent space/time geometries across the computational chain",
							this.getStatement());
					setErrors(true);
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
		this.id = mainObservable.getName() + "_derived";
		this.namespace = scope.getResolutionNamespace();
		this.behavior = new Behavior(null, this);
		this.observables.add(mainObservable);
		this.dependencies.addAll(candidateObservable.getObservables());
		if (candidateObservable.getComputation() != null) {
			this.resources.addAll(candidateObservable.getComputation());
		}
		if (mainObservable.is(Type.COUNTABLE)
				|| mainObservable.getDescription() == IActivity.Description.CLASSIFICATION) {
			this.instantiator = true;
		}
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

		if (resource.getClassification() != null) {

			resource.setValidatedResource(new Classification(resource.getClassification()));

		} else if (resource.getLookupTable() != null) {

			resource.setValidatedResource(new LookupTable(resource.getLookupTable()));

		} else if (resource.getAccordingTo() != null) {

			IClassification classification = Types.INSTANCE
					.createClassificationFromMetadata(observables.get(0).getType(), resource.getAccordingTo());
			resource.setValidatedResource(classification);

		} else if (resource.getUrn() != null) {

			// ensure resource is online; turn model off if not
			IResource res = Resources.INSTANCE.resolveResource(resource.getUrn());
			if (res == null) {
				// monitor.send(new CompileNo);
				this.setInactive(true);
			}
			if (res != null) {

				// store resource
				resourcesUsed.add(res);

				// store geometry from resource, to be merged with model's when coverage is
				// asked for
				this.resourceCoverage = Scale.create(res.getGeometry());

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
							monitor.error("contextualizer " + prototype.getName() + " requires a dependency named "
									+ argument.getName(), getStatement());
							setErrors(true);
						} else if (dependency != null
								&& !IArtifact.Type.isCompatible(argument.getType(), dependency.getArtifactType())) {
							monitor.error("contextualizer " + prototype.getName() + " requires type "
									+ argument.getType().name().toLowerCase() + "for dependency " + argument.getName()
									+ ":  " + dependency.getArtifactType().name().toLowerCase() + " was supplied",
									getStatement());
							setErrors(true);
						}
					}
				}
			} else {
				monitor.error("unknown contextualizer function: " + resource.getServiceCall().getName(),
						resource.getServiceCall());
				setErrors(true);
			}
		}

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
			if (dependency.getType().resolves(concept) == 0) {
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
			if (((Observable) concept).canResolve((Observable) dependency)) {
				return dependency;
			}
		}
		return null;
	}

	/**
	 * 
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
		IObservable obs = getCompatibleOutput((Observable) observable);
		if (obs != null) {
			return obs.getName();
		}
		obs = getCompatibleInput((Observable) observable);
		/**
		 * The observable's local name is returned in case we don't have it; this
		 * happens when an indirect observable is used to resolve a different one. If we
		 * returned null here, the resulting actuator will have a null name.
		 */
		return obs == null ? observable.getName() : obs.getName();
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
	public Behavior getBehavior() {
		return behavior;
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

			Collection<IExtent> extents = new ArrayList<>();
			if (behavior != null) {
				extents.addAll(behavior.getExtents(monitor));
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

			try {
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
			for (IAction action : behavior.getActions(trigger)) {
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
	 * Get the output that can satisfy this observable, possibly with mediation.
	 * 
	 * @param observable
	 * @return an existing output observable or null
	 */
	public Observable getCompatibleOutput(Observable observable) {
		for (IObservable output : observables) {
			if (output.getType().resolves(observable.getType()) >= 0) {
				return (Observable) output;
			}
		}
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
			if (input.getType().resolves(observable.getType()) > 0) {
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

}
