package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Types;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.knowledge.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.data.classification.Classification;
import org.integratedmodelling.klab.data.table.LookupTable;
import org.integratedmodelling.klab.documentation.Documentation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.CompatibleObservable;
import org.integratedmodelling.klab.scale.Scale;

public class Model extends KimObject implements IModel {

	private String id;
	private Optional<IDocumentation> documentation = Optional.empty();
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
	// saved at resource read, to be intersected with own coverage when requested
	private Scale resourceCoverage;
	// own coverage, resulting of own specs if any, namespace's if any, and
	// resource's if any
	private Scale coverage;

	// remember resources to be able to reassess their online status
	private List<IResource> resourcesUsed = new ArrayList<>();
	private long lastResourceCheck;
	private boolean available = true;

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
		this.setInactive(model.isInactive());

		setDeprecated(model.isDeprecated() || namespace.isDeprecated());

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
		 * TODO establish typechain for progressive checking until final action
		 */
		if (!model.getResourceUrns().isEmpty()) {
			ComputableResource urnResource = validate(new ComputableResource(model.getResourceUrns().get(0)), monitor);
			for (int i = 1; i < model.getResourceUrns().size(); i++) {
				urnResource.chainResource(validate(new ComputableResource(model.getResourceUrns().get(i)), monitor));
			}
			this.resources.add(urnResource);
		} else if (model.getResourceFunction().isPresent()) {
			this.resources.add(validate(new ComputableResource(model.getResourceFunction().get()), monitor));
		} else if (model.getInlineValue().isPresent()) {
			this.resources.add(validate(new ComputableResource(model.getInlineValue()), monitor));
		}

		/*
		 * resources
		 */
		for (IComputableResource resource : model.getContextualization()) {
			this.resources.add(validate((ComputableResource) resource, monitor));
		}

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
		 */

		if (model.getMetadata() != null) {
			setMetadata(new Metadata(model.getMetadata()));
		}

		/*
		 * documentation
		 */
		if (model.getDocumentationMetadata() != null) {
			this.documentation = Optional.of(new Documentation(model.getDocumentationMetadata()));
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

			IClassification classification = Types.INSTANCE.createClassificationFromMetadata(observables.get(0),
					resource.getAccordingTo());
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
		}

		return resource;
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
			return obs.getLocalName();
		}
		obs = getCompatibleInput((Observable) observable);
		/**
		 * The observable's local name is returned in case we don't have it; this
		 * happens when an indirect observable is used to resolve a different one. If we
		 * returned null here, the resulting actuator will have a null name.
		 */
		return obs == null ? observable.getLocalName() : obs.getLocalName();
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
			if (!resource.getRequiredResourceNames().isEmpty()) {
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
	public Optional<IDocumentation> getDocumentation() {
		return documentation;
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

	public void setDocumentation(Optional<IDocumentation> documentation) {
		this.documentation = documentation;
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
			this.coverage = Scale.create(behavior.getExtents(monitor));
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
	 * Return all the computational steps required to compute the model, encoded as
	 * function calls.
	 * 
	 * @param transition
	 *            the transition to be computed
	 * @return the indirectAdapters for the model at the transition
	 */
	@Override
	public List<IComputableResource> getComputation(ILocator transition) {
	    
		List<IComputableResource> ret = new ArrayList<>();
		for (IComputableResource resource : resources) {
		    ret.add(((ComputableResource)resource).copy());
		}
		for (Trigger trigger : Dataflows.INSTANCE.getActionTriggersFor(transition)) {
			for (IAction action : behavior.getActions(trigger)) {
			    for (IComputableResource resource : action.getComputation(transition)) {
			        ret.add(((ComputableResource)resource).copy());
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

}
