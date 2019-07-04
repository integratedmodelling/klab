package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Documentation;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
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
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IDocumentationService;
import org.integratedmodelling.klab.common.CompileNotification;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.CompatibleObservable;
import org.integratedmodelling.klab.resolution.ObservableReasoner.CandidateObservable;
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
	private List<IComputableResource> resources = new ArrayList<>();
	private boolean isPrivate;
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

	Map<ExtentDimension, ExtentDistribution> declaredDimensionality;

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
		this.isPrivate = model.isPrivate();
		this.instantiator = model.isInstantiator();
		this.learning = model.isLearningModel();
		this.setErrors(model.isErrors());
		this.setInactive(model.isInactive());

		setDeprecated(model.isDeprecated() || namespace.isDeprecated());

		// TODO report observable errors (e.g. UNITS) that are currently ignored (they
		// just suppress other errors)

		for (IKimObservable observable : model.getObservables()) {
			if (observable.hasAttributeIdentifier()) {
				attributeObservables.put(observable.getValue().toString(),
						Observables.INSTANCE.declare(observable, monitor));
			} else {
				observables.add(Observables.INSTANCE.declare(observable, monitor));
			}
		}

		for (IKimObservable dependency : model.getDependencies()) {
			dependencies.add(Observables.INSTANCE.declare(dependency, monitor));
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

		/*
		 * all resources after 'using' or further classification/lookup transformations
		 */
		for (IComputableResource resource : model.getContextualization()) {
			this.resources.add(validate((ComputableResource) resource, monitor));
		}

		/*
		 * TODO validate typechain, units and final result vs. observable artifact type
		 */
		validateTypechain(monitor);

		/*
		 * actions
		 */
		this.behavior = new Behavior(model.getBehavior(), this);

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

		Map<String, IArtifact.Type> typechain = new HashMap<>();

		for (IComputableResource resource : resources) {

			String target = resource.getTarget() == null ? this.observables.get(0).getName()
					: resource.getTarget().getName();
			IArtifact.Type type = Resources.INSTANCE.getType(resource);
			IGeometry geometry = Resources.INSTANCE.getGeometry(resource);

			if (type != null) {
				if (typechain.containsKey(target)) {
					// TODO check that the resource can take the current type
				}
				typechain.put(target, type);
			}
			mergeGeometry(geometry, monitor);
		}

		mergeGeometry(getCoverage(monitor).asGeometry(), monitor);

		if (geometry == null) {
			geometry = Geometry.scalar();
		}

		/*
		 * check units against geometry
		 */
		for (IObservable observable : CollectionUtils.join(observables, dependencies)) {

			if (observable.is(Type.QUALITY)) {

				CompileNotification notification = Units.INSTANCE.validateUnit(observable, geometry,
						Annotations.INSTANCE.getAnnotation(this, "extensive"));

				if (notification != null) {

					if (notification.getLevel().equals(Level.WARNING)) {
						monitor.warn(notification.getMessage(), this.getStatement());
					} else if (notification.getLevel().equals(Level.SEVERE)) {
						monitor.error(notification.getMessage(), this.getStatement());
						setErrors(true);
					}
				}
			}
			
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

	private void mergeGeometry(IGeometry geometry, IMonitor monitor) {

		if (geometry != null) {
			if (this.geometry == null) {
				this.geometry = geometry;
			} else if (!geometry.isEmpty()) {
				this.geometry = ((Geometry) this.geometry).merge(geometry);
				if (this.geometry == null) {
					monitor.error("the model uses inconsistent space/time geometries across the computational chain",
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
	public Model(Observable mainObservable, CandidateObservable candidateObservable, ResolutionScope scope) {
		super(null);
		this.derived = true;
		this.id = mainObservable.getName() + "_derived";
		this.namespace = scope.getResolutionNamespace();
		this.behavior = new Behavior(null, this);
		this.observables.add(mainObservable);
		this.dependencies.addAll(candidateObservable.observables);
		if (candidateObservable.computation != null) {
			this.resources.addAll(candidateObservable.computation);
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

		for (IComputableResource resource : resources) {
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
	public boolean isPrivate() {
		return isPrivate || namespace.isPrivate() || !isSemantic();
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
			this.coverage = Scale.create(behavior == null ? new ArrayList<>() : behavior.getExtents(monitor));
			if (resourceCoverage != null) {
				this.coverage = this.coverage.merge(resourceCoverage, LogicalConnector.INTERSECTION);
			}
			Scale nsScale = namespace.getCoverage(monitor);
			if (nsScale != null) {
				this.coverage = this.coverage.merge(nsScale, LogicalConnector.INTERSECTION);
			}
		}
		return this.coverage;
	}

	/**
	 * Return all the computational steps required to compute the model. If there
	 * are annotations that define parameters (possibly interactive), add them to
	 * the computables.
	 * 
	 * @param transition
	 *            the transition to be computed
	 * @return the indirectAdapters for the model at the transition
	 */
	@Override
	public List<IComputableResource> getComputation(ILocator transition) {

		List<IAnnotation> parameters = new ArrayList<>();
		for (IAnnotation annotation : getAnnotations()) {
			if (annotation.getName().equals("parameter")) {
				parameters.add(annotation);
			}
		}

		List<IComputableResource> ret = new ArrayList<>();
		for (IComputableResource resource : resources) {
			ComputableResource res = ((ComputableResource) resource).copy();
			if (parameters.size() > 0) {
				res.addParameters(parameters);
			}
			ret.add(res);
		}
		for (Trigger trigger : Dataflows.INSTANCE.getActionTriggersFor(transition)) {
			for (IAction action : behavior.getActions(trigger)) {
				for (IComputableResource resource : action.getComputation(transition)) {
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
			if (new CompatibleObservable((Observable) output).equals(new CompatibleObservable(observable))) {
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
			if (new CompatibleObservable((Observable) input).equals(new CompatibleObservable(observable))) {
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
	public List<IComputableResource> getResources() {
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

	@Override
	public Map<ExtentDimension, ExtentDistribution> getAssertedDimensionality() {
		return getAssertedDimensionality(null);
	}

	public Map<ExtentDimension, ExtentDistribution> getAssertedDimensionality(IMonitor monitor) {

		if (this.declaredDimensionality == null) {

			this.declaredDimensionality = new HashMap<>();

			if (geometry != null) {

				for (Dimension dimension : geometry.getDimensions()) {
					if (dimension.getType() == Dimension.Type.SPACE) {
						this.declaredDimensionality.put(ExtentDimension.spatial(dimension.getDimensionality()),
								dimension.isRegular() || dimension.size() > 0 ? ExtentDistribution.INTENSIVE
										: ExtentDistribution.EXTENSIVE);
					} else if (dimension.getType() == Dimension.Type.TIME) {
						this.declaredDimensionality.put(ExtentDimension.TEMPORAL,
								dimension.isRegular() || dimension.size() > 0 ? ExtentDistribution.INTENSIVE
										: ExtentDistribution.EXTENSIVE);
					} else if (dimension.getType() == Dimension.Type.NUMEROSITY) {
						this.declaredDimensionality.put(ExtentDimension.CONCEPTUAL,
								dimension.isRegular() || dimension.size() > 0 ? ExtentDistribution.INTENSIVE
										: ExtentDistribution.EXTENSIVE);
					}
				}
			}

			for (IAnnotation annotation : new IAnnotation[] { Annotations.INSTANCE.getAnnotation(this, "extensive"),
					Annotations.INSTANCE.getAnnotation(this, "intensive") }) {
				if (annotation != null) {
					for (Object o : annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME, List.class)) {
						switch (o.toString()) {
						case "space":
						case "area":
							this.declaredDimensionality.put(ExtentDimension.AREAL,
									annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
											: ExtentDistribution.INTENSIVE);
							break;
						case "line":
							this.declaredDimensionality.put(ExtentDimension.LINEAL,
									annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
											: ExtentDistribution.INTENSIVE);
							break;
						case "volume":
							this.declaredDimensionality.put(ExtentDimension.VOLUMETRIC,
									annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
											: ExtentDistribution.INTENSIVE);
							break;
						case "time":
							this.declaredDimensionality.put(ExtentDimension.VOLUMETRIC,
									annotation.getName().equals("extensive") ? ExtentDistribution.EXTENSIVE
											: ExtentDistribution.INTENSIVE);
						case "numerosity":
							this.declaredDimensionality.put(ExtentDimension.VOLUMETRIC,
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
		}
		return this.declaredDimensionality;

	}

}
