package org.integratedmodelling.klab.dataflow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.Units.UnitContextualization;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Acknowledgement;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.owl.ObservableBuilder;
import org.integratedmodelling.klab.resolution.ObservationStrategy.Strategy;
import org.integratedmodelling.klab.resolution.RankedModel;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.resolution.ResolutionScope.Link;
import org.integratedmodelling.klab.resolution.ResolvedArtifact;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class DataflowCompiler {

	private String name;
	private DirectObservation context;
	private IResolutionScope scope;
	private Actuator parentDataflow;

	/*
	 * keep the observables of each merged model to create proper references.
	 */
	private Set<IObservable> mergedCatalog = new HashSet<>();

	Graph<IResolvable, ResolutionEdge> resolutionGraph = new DefaultDirectedGraph<>(ResolutionEdge.class);

	/*
	 * add any true actuator so that we can find it given a reference. Needed to
	 * implement filters, which are merged with computations rather than compiled
	 * independently.
	 */
	Map<String, Actuator> actuatorCatalog = new HashMap<>();

	/*
	 * index the original observables as they come out of models that compute them,
	 * using the name of the actuator that does the job.
	 */
	Map<ObservedConcept, Observable> sources = new HashMap<>();

	static class ResolutionEdge {

		Coverage coverage;
		IResolutionScope.Mode mode;
		boolean isPartition = false;
		// deferred resolution
		boolean deferred = false;

		/*
		 * order of computation, relevant for scaling with partitioned resolvers
		 */
		int order;

		ResolutionEdge() {
		}

		public ResolutionEdge(Link link) {
			this.coverage = link.getTarget().getCoverage();
			this.mode = link.getTarget().getMode();
			this.order = link.getOrder();
			this.isPartition = link.isPartition();
			this.deferred = link.getTarget().isDeferred();
		}

		public String toString() {
			return "resolves";
		}
	}

	public DataflowCompiler(String name, IResolutionScope scope, Actuator parentDataflow) {
		this.name = name;
		this.scope = scope;
		this.context = (DirectObservation) scope.getContext();
		this.parentDataflow = parentDataflow;
	}

	public Dataflow compile(IMonitor monitor) {

		if (!Configuration.INSTANCE.getProperty("visualize", "false").equals("false")
				&& resolutionGraph.vertexSet().size() > 1) {
			Graphs.show(resolutionGraph, "Resolution graph");
		}

		Dataflow ret = new Dataflow(parentDataflow);

		ret.setName(this.name);

		for (IResolvable root : getRootResolvables(resolutionGraph)) {

			resetModelCatalog();
			Node node = compileActuator(root, scope.getMode(), resolutionGraph,
					this.context == null ? (Scale) scope.getScale() : this.context.getScale(), new HashMap<>(),
					monitor);
			node.root = true;

			Actuator actuator = node.getActuatorTree(ret, monitor, new HashSet<>(), 0);
			ret.getChildren().add(actuator);

			// compute coverage
			try {
				Scale cov = node.computeCoverage(null);
				if (cov != null) {
					ret.setCoverage(Coverage.full(cov));
				}
			} catch (KlabException e) {
				monitor.error("error computing dataflow coverage: " + e.getMessage());
			}

			/*
			 * Any mediators still needed between the root observable and the source node
			 * are added at the end of the final computation.
			 */
			if (sources.containsKey(actuator.getObservedConcept()) && root instanceof Observable
					&& ((Observable) root).getType().is(IKimConcept.Type.QUALITY)) {
				for (IContextualizable mediator : computeMediators(sources.get(actuator.getObservedConcept()),
						node.observable, node.scale)) {
					actuator.addComputation(mediator,
							scope.getMonitor().getIdentity().getParentIdentity(ISession.class));
				}
			}

			/*
			 * this will overwrite scale and namespace - another way of saying that these
			 * should either be identical or we shouldn't even allow more than one root
			 * resolvable.
			 */
			ret.setNamespace(actuator.getNamespace());
		}

		/**
		 * This happens when we resolved a countable observable (from a previous
		 * instantiator calls) without an observer and resolution did not find any
		 * models.
		 */
		if (ret.getChildren().isEmpty() && ((ResolutionScope) scope).getObservable().is(IKimConcept.Type.COUNTABLE)
				&& scope.getMode() == Mode.RESOLUTION) {

			Actuator actuator = Actuator.create(ret, Mode.RESOLUTION);
			actuator.setObservable(((ResolutionScope) scope).getObservable());
			actuator.setType(Type.RESOLVE);
			actuator.setNamespace(((ResolutionScope) scope).getResolutionNamespace());
			actuator.setName(((ResolutionScope) scope).getObservable().getReferenceName());
			setModelContext(((ResolutionScope) scope).getContextModel(), actuator, ret);
			ret.getChildren().add(actuator);
			ret.setNamespace(actuator.getNamespace());
		}

		/*
		 * as a last step, recursively notify all local names defined in the call chain,
		 * so all artifacts can be properly addressed when the actuators are run in
		 * order of dependency.
		 */
		ret.computeLocalNames();
		
		monitor.debug((scope.isOccurrent() ? "Occurrent" : "Continuant") + " dataflow compiled");

		return ret;
	}

	private void setModelContext(IModel contextModel, Actuator actuator, Dataflow dataflow) {

		if (contextModel != null && contextModel.isInstantiator() && actuator.getMode() == Mode.RESOLUTION) {

			/*
			 * recover any output states with static initializers NB: where expressions and
			 * the like are added
			 */
			for (int i = 1; i < contextModel.getObservables().size(); i++) {
				if (((Observable) contextModel.getObservables().get(i)).isResolved()) {
					Actuator child = Actuator.create(dataflow, Mode.RESOLUTION);
					child.setObservable(new Observable((Observable) contextModel.getObservables().get(i)));
					child.setName(contextModel.getObservables().get(i).getReferenceName());
					child.setAlias(contextModel.getObservables().get(i).getName());
					child.setType(contextModel.getObservables().get(i).getArtifactType());
					child.addComputation(ComputableResource.create(contextModel.getObservables().get(i).getValue()),
							scope.getMonitor().getIdentity().getParentIdentity(ISession.class));
					actuator.getChildren().add(child);
				}
			}

			/*
			 * recover any instantiation actions
			 */
			for (IAction action : contextModel.getContextualization().getActions(Trigger.INSTANTIATION)) {
				for (IContextualizable resource : action.getComputation()) {
					actuator.addComputation(((ComputableResource) resource).copy(),
							scope.getMonitor().getIdentity().getParentIdentity(ISession.class));
				}
			}
		}
	}

	static class ModelD {

		Model model;
		/* how many nodes reference this model's observables */
		int useCount;
		/* this is null unless the model covers only a part of the context */
		Coverage coverage;
		/* if true, model is resolved by partitions, even if there is just one */
		boolean hasPartitions = false;
		/*
		 * the specific concretization of traits we're being used for. Null unless the
		 * model is part of the resolution of a concretized abstract observable. This
		 * makes each descriptor different even if it references the same model.
		 */
		Map<IConcept, IConcept> concreteTraits;

		public ModelD(Model model, boolean hasPartitions, Map<IConcept, IConcept> concreteTraits) {
			this.model = model;
			this.hasPartitions = hasPartitions;
			this.concreteTraits = new HashMap<>(concreteTraits);
		}

		/*
		 * this constructor is only for use as key in modelCatalog
		 */
		ModelD(Model model, Map<IConcept, IConcept> concreteTraits) {
			this.model = model;
			this.concreteTraits = new HashMap<>(concreteTraits);
		}

		/*
		 * ACHTUNG if this is changed, the modelCatalog implementation must take it into
		 * account.
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((concreteTraits == null) ? 0 : concreteTraits.hashCode());
			result = prime * result + ((model == null) ? 0 : model.hashCode());
			return result;
		}

		/*
		 * ACHTUNG if this is changed, the modelCatalog implementation must take it into
		 * account.
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ModelD other = (ModelD) obj;
			if (concreteTraits == null) {
				if (other.concreteTraits != null)
					return false;
			} else if (!concreteTraits.equals(other.concreteTraits))
				return false;
			if (model == null) {
				if (other.model != null)
					return false;
			} else if (!model.equals(other.model))
				return false;
			return true;
		}

	}

	/**
	 * Each node represents one use of a model or modelset to compute one
	 * observable. Each node will compute an actuator (a true one the first use, a
	 * reference afterwards).
	 * 
	 * If there is more than one model, they will have to be computed individually
	 * in their own scale and merged before any other indirectAdapters are called.
	 * 
	 * The final actuator hierarchy is built by calling
	 * {@link #getActuatorTree(IMonitor)} on the root node.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	class Node {

		boolean root;
		Observable observable;
		Acknowledgement observer;
		IResolutionScope.Mode mode;
		// it's vital that the order of inclusion of the models is preserved.
		Set<ModelD> models = new LinkedHashSet<>();
		List<Node> children = new ArrayList<>();
		Scale scale;
		String alias;
		Object inlineValue;
		ResolvedArtifact resolvedArtifact;
		Strategy strategy = Strategy.DIRECT;

		public String toString() {
			return (root ? "ROOT " : "") + ("[" + children.size() + "]")
					+ ("{" + (models.size() > 0 ? models.iterator().next().model : "") + " #" + models.size() + "}")
					+ (" " + mode + " ") + (observer != null ? ("OBSERVER " + observer) : "")
					+ (resolvedArtifact != null ? ("RESOLVED " + resolvedArtifact) : "")
					+ (observable != null
							? ("" + observable + " from " + ((Observable) observable).getOriginatingModelId())
							: "");
		}

		/*
		 * True if the children are partitions (even if there is just one child which
		 * covers the context partially).
		 */
		boolean hasPartitions = false;

		public Node(IResolvable resolvable, IResolutionScope.Mode mode) {

			this.mode = mode;

			if (resolvable instanceof Observable) {

				this.observable = (Observable) resolvable;
				this.inlineValue = observable.getValue();

			} else if (resolvable instanceof Acknowledgement) {

				this.observer = (Acknowledgement) resolvable;
				this.observable = this.observer.getObservable();

			} else if (resolvable instanceof ResolvedArtifact) {

				this.resolvedArtifact = (ResolvedArtifact) resolvable;
				this.observable = (Observable) resolvedArtifact.getObservable();
				sources.put(new ObservedConcept(this.resolvedArtifact.getObservable()),
						(Observable) this.resolvedArtifact.getArtifact().getObservable());
			}
		}

		/*
		 * get the actuator in the node, ignoring the children
		 */
		Actuator createActuator(Dataflow dataflow, IMonitor monitor, Set<ModelD> generated) {

			/*
			 * create the original actuator
			 */
			Actuator ret = Actuator.create(dataflow, mode);

			IObservable modelObservable = null;
			String attributeId = null;
			if (!models.isEmpty()) {
				modelObservable = models.iterator().next().model.getObservables().get(0);
				// FIXME this must use the observable for the abstract predicates
				if (!modelObservable.resolves(this.observable, getDataflowContext())) {

					/**
					 * may be an attribute, in which case we already have the observation, nothing
					 * is needed and we return null and get ignored.
					 */
					IObservable inherentAttribute = null;
					for (String key : models.iterator().next().model.getAttributeObservables().keySet()) {
						IObservable attribute = models.iterator().next().model.getAttributeObservables().get(key);
						if (attribute.getType().resolves(this.observable.getType(), getDataflowContext())) {
							attributeId = key;
							if (generated.contains(models.iterator().next())) {
								return null;
							} else {
								inherentAttribute = attribute;
								break;
							}
						}
					}
					if (inherentAttribute != null) {

						/*
						 * Resolve the secondary output as the primary target of the actuator.
						 */

					} else if (!generated.contains(models.iterator().next())) {

						/**
						 * Secondary output! We may be already part of the actuator for this (in which
						 * case we just add our empty actuator to create the observation and leave it to
						 * the outer actuator) or we may not, in which case we must create the outer
						 * actuator and put the empty actuator in it.
						 */
						Actuator child = Actuator.create(dataflow,
								this.observable.is(IKimConcept.Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
						child.setObservable(this.observable);
						child.setName(observable.getReferenceName());
						if (models.size() > 0) {
							IObservable compatibleOutput = models.iterator().next().model.getCompatibleOutput(
									inherentAttribute == null ? observable : (Observable) inherentAttribute,
									getDataflowContext(), monitor);
							if (compatibleOutput != null) {
								// null in derived observables such as "change in"
								child.setAlias(compatibleOutput.getName());
							}
						}
						child.setType(this.observable.getArtifactType());
						child.setExport(true);
						child.setNamespace(((ResolutionScope) scope).getResolutionNamespace());
						ret.actuators.add(child);

						this.observable = (Observable) modelObservable;
					}
				}
			}

			ret.setObservable(observable);
			ret.getObservable().setDereifiedAttribute(attributeId);
			ret.setName(observable.getReferenceName());
			ret.setAlias(observable.getName());

			/*
			 * FIXME this condition is silly; also there will be more problems due to this
			 * check. It should check for an ADDED inherency stored in the observable along
			 * with the original observable, rather than playing with the concept as is, as
			 * it's hard to know where the inherency was assigned.
			 */
			if (!observable.is(IKimConcept.Type.CHANGE)
					&& Observables.INSTANCE.getDirectContextType(observable.getType()) != null) {
				if (models.size() > 0 && models.iterator().next().model.isLearning()) {
					/*
					 * A learning model for a directly inherent quality will create a void actuator
					 * - no state should be generated in the context.
					 */
					ret.setType(Type.VOID);
				} else {
					/*
					 * if not learning and not explicitly inherent, we remove the inherency in the
					 * dataflow as it was needed to resolve the inherent observable, but the model
					 * is run in an object's context and we don't maintain the inherency when the
					 * semantics is local to the object.
					 */
					ret.setObservable((Observable) ObservableBuilder.getBuilder(this.observable, monitor)
							.without(ObservableRole.CONTEXT).buildObservable());

					if (ret.getType() == null) {
						assignType(ret, this.observable);
					}
				}
			} else {
				assignType(ret, this.observable);
			}

			if (observer != null) {

				ret.setNamespace(observer.getNamespace());
				ret.setName(observer.getId());

			} else if (resolvedArtifact != null) {
				/*
				 * Different situations if we ARE the artifact or we USE it for something. If we
				 * have artifact adapters, we must compile an import as a child and use our own
				 * observable, done below.
				 */
				ret.setInput(true);
				ret.setName(resolvedArtifact.getObservable().getReferenceName());

			} else {
				ret.setName(observable.getReferenceName());
			}

			/*
			 * if this is a reference to something that contains partials, the partials will
			 * be references and this won't, and it won't pass through defineActuator(): we
			 * detect that situation and force a reference.
			 */
			boolean reference = false;
			boolean partials = false;

			if (models.size() == 1 && !this.hasPartitions) {

				ModelD theModel = models.iterator().next();
				String referenceName = theModel.model.getObservables().get(0).getReferenceName();
				/*
				 * if we're incarnating traits, we may have a different observable altogether.
				 * Must switch to the observable name to avoid this actuator being interpreted
				 * as a reference.
				 */
				if (!observable.getResolvedPredicates().isEmpty()
						&& !theModel.model.getObservables().get(0).getType().equals(observable.getType())) {
					referenceName = observable.getReferenceName();
				}
				ret.setName(referenceName);
				defineActuator(ret, theModel, generated);

			} else if (this.hasPartitions) {

				/*
				 * output the independent actuators, detecting the situation where all children
				 * are partial references.
				 */
				int i = 1;
				reference = true;
				partials = true;
				List<String> modelIds = new ArrayList<>();
				
				for (ModelD modelDesc : models) {

					Actuator partial = Actuator.create(dataflow, mode);
					int index = i++;
					if (modelDesc.model instanceof RankedModel) {
						partial.setPriority(((RankedModel) modelDesc.model).getPriority());
						index = ((RankedModel) modelDesc.model).getPriority();
					}

					/*
					 * rename and set the target name as partitioned. Number is the priority if
					 * known.
					 */
					String name = observable.getReferenceName() + "_" + index;
					partial.setPartitionedTarget(ret.getName());
					partial.setName(name);
					partial.setObservable(observable);
					partial.setType(ret.getType());
					defineActuator(partial, modelDesc, generated);

					// no reference partials as our final result is the merged artifact
					if (partial.isReference()) {
						continue;
					}

					reference = false;
					partial.setCoverage(modelDesc.coverage);
					modelIds.add(name);
					
					ret.getChildren().add(partial);

				}
				
				ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(observable));

			} else if (inlineValue != null) {
				ret.addComputation(ComputableResource.create(inlineValue),
						scope.getMonitor().getIdentity().getParentIdentity(ISession.class));
			}

			if (partials) {
				if (reference) {
					ret.setReference(true);
				} else {
					mergedCatalog.add(observable);
				}
			}

			/*
			 * if this is deferring observables, it's meant to create a trans-reified
			 * quality, so wrap the dereified actuator into another that computes it for the
			 * host context and set a dereifying contextualizer in it.
			 * 
			 * FIXME this shouldn't be a list. Ignoring any element > 1.
			 */
			for (Observable deferred : observable.getDeferredObservables()) {

				/*
				 * remember the dereification so that we don't schedule it, which would require
				 * that an entire model setup is available for the observable.
				 */

				Observable dereified = null;

				if (((Observable) deferred).getDeferredTarget() == null) {
					dereified = (Observable) deferred.getBuilder(monitor).of(observable.getType()).setDereified()
							.buildObservable();
				} else {
					/*
					 * the one that was being resolved in a distributed specialized resolution; no
					 * need to add "of" inherency
					 */
					dereified = deferred = ((Observable) deferred).getDeferredTarget();
				}

				Actuator outer = Actuator.create(dataflow,
						dereified.is(IKimConcept.Type.COUNTABLE) ? Mode.INSTANTIATION : Mode.RESOLUTION);
				outer.setObservable(dereified);
				outer.setName(dereified.getReferenceName());
				outer.setAlias(dereified.getName());
				assignType(outer, dereified);

				/*
				 * add a dereifying contextualizer to the computation
				 */
				outer.addComputation(
						Klab.INSTANCE.getRuntimeProvider().getDereifyingResolver(observable.getType(),
								deferred.getType(), dereified.getArtifactType()),
						scope.getMonitor().getIdentity().getParentIdentity(ISession.class));

				outer.actuators.add(ret);
				ret = outer;

				/*
				 * break for now; may need to do something for any further dereified outputs,
				 * but this should be done by the resolver beforehand.
				 */
				break;

			}

			return ret;
		}

		private void assignType(Actuator ret, Observable observable) {

			switch (observable.getDescriptionType()) {
			case CATEGORIZATION:
				ret.setType(Type.CONCEPT);
				break;
			case DETECTION:
			case INSTANTIATION:
				ret.setType(ret.getMode() == Mode.RESOLUTION ? IArtifact.Type.RESOLVE : observable.getArtifactType());
				break;
			case QUANTIFICATION:
				ret.setType(Type.NUMBER);
				break;
			case SIMULATION:
				ret.setType(Type.PROCESS);
				break;
			case VERIFICATION:
				ret.setType(Type.BOOLEAN);
				break;
			case CHARACTERIZATION:
			case CLASSIFICATION:
				ret.setType(Type.VOID);
				break;
			default:
				// hits this in tables and other views
				ret.setType(Type.VOID);
				break;
			}
		}

		private void defineActuator(Actuator ret, ModelD theModel, Set<ModelD> generated) {

			Model model = theModel.model;
			ret.setModel(model);

			/* filters are regenerated every time as their computation needs to be added */
			if (!generated.contains(theModel) || ret.isFilter()) {

				generated.add(theModel);
				for (IContextualizable resource : getModelComputation(model, ret.getType(), true)) {
					ret.addComputation(resource, scope.getMonitor().getIdentity().getParentIdentity(ISession.class));
				}

				ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(observable, model));
				for (IDocumentation documentation : model.getDocumentation()) {
					ret.addDocumentation(documentation);
				}

				setModelContext(((ResolutionScope) scope).getContextModel(), ret, ret.getDataflow());

				actuatorCatalog.put(ret.getName(), ret);

			} else {
				ret.setReference(true);
			}
		}

		/*
		 * get the finished actuator with all the children and the mediation strategy;
		 * add any last mediation for the root observable if needed
		 */
		Actuator getActuatorTree(Dataflow dataflow, IMonitor monitor, Set<ModelD> generated, int level) {

			Actuator ret = createActuator(dataflow, monitor, generated);

			if (ret == null) {
				/* no actuator needed: observation was predefined */
				return ret;
			}

			if (!ret.isReference()) {

				/*
				 * collect units from dependent models to ensure consistency across unspecified
				 * ones
				 */
				Map<String, IUnit> chosenUnits = new HashMap<>();

				/*
				 * the dataflow won't compile actuators for the dependencies of a directly
				 * contextualized observable model (model xxxx within yyyy) unless they must be
				 * resolved from the context. So if the resolution graph contains the
				 * dependencies with the same EXPLICIT "within" that is also explicit in the
				 * primary observable, these have been resolved from an instantiator and they do
				 * not need to be compiled in.
				 */
				IConcept directContext = Observables.INSTANCE.getDirectContextType(this.observable.getType());

				if (this.strategy == Strategy.FILTERING) {

					/*
					 * compile in the child providing the filtered observable, then add the
					 * dependencies and computations in the others.
					 */
					List<Actuator> observ = new ArrayList<>();
					List<Actuator> filters = new ArrayList<>();
					for (Node child : sortChildren()) {
						Actuator achild = child.getActuatorTree(dataflow, monitor, generated, level + 1);
						if (achild.isFilter()) {
							filters.add(achild);
						} else {
							observ.add(achild);
						}
					}

					if (observ.size() != 1) {
						throw new KlabInternalErrorException("unexpected >1 observables in filtering actuator");
					}

					ret.actuators.add(observ.get(0));

					for (Actuator filter : filters) {

						/*
						 * adopt any dependencies from the filter; if the dependency exists in the
						 * passed catalog and we don't already have it, compile in a reference to it,
						 * otherwise put it in here.
						 */
						for (IActuator dependency : filter.actuators) {
							if (((Actuator) ret).hasDependency(dependency)) {
								continue;
							}
							ret.actuators.add(dependency);
						}

						/* compile in all mediations as they are */
						for (Pair<IServiceCall, IContextualizable> mediator : filter.mediationStrategy) {
							ret.mediationStrategy.add(mediator);
						}

						/*
						 * compile in all filter computations, making a copy and ensuring the target is
						 * our filtered observable. These can only be filters by virtue of validation.
						 * Uses the reference name (name of the actuator), not the localized, because
						 * the original filter model does not have the dependency.
						 */
						for (Pair<IServiceCall, IContextualizable> computation : filter.computationStrategy) {
							ret.computationStrategy.add(
									new Pair<>(ret.setFilteredArgument(computation.getFirst(), observ.get(0).getName()),
											ret.setFilteredArgument(computation.getSecond(), observ.get(0).getName())));
						}
					}

				} else {

					for (Node child : sortChildren()) {

						IConcept childContext = Observables.INSTANCE.getDirectContextType(child.observable.getType());

						if (directContext != null && directContext.equals(childContext)
								&& !scope.getContext().getObservable().is(directContext)) {
							/*
							 * can only be resolved through the instantiator of the object. TODO we should
							 * ensure that a dependency for the primary observable is included, in the
							 * ObservableReasoner of course.
							 */
							continue;
						}

						/* this may be a new actuator or a reference to an existing one. */
						Actuator achild = child.getActuatorTree(dataflow, monitor, generated, level + 1);

						if (achild == null) {
							// null if the observation is already there, i.e. it was an attribute
							continue;
						}

						ret.getChildren().add(achild);
						recordUnits(achild, chosenUnits);
						if (sources.containsKey(achild.getObservedConcept())) {
							for (IContextualizable mediator : computeMediators(sources.get(achild.getObservedConcept()),
									achild.getObservable(), scale)) {
								ret.addMediation(mediator, achild,
										scope.getMonitor().getIdentity().getParentIdentity(ISession.class));
							}
						}
					}
				}

				inferUnits(ret, chosenUnits);

			}
			return ret;
		}

		private void recordUnits(Actuator achild, Map<String, IUnit> chosenUnits) {
			if (Units.INSTANCE.needsUnits(achild.getObservable()) && achild.getObservable().getUnit() != null) {
				IUnit baseUnit = Units.INSTANCE.getDefaultUnitFor(achild.getObservable());
				if (!chosenUnits.containsKey(baseUnit.toString())) {
					chosenUnits.put(baseUnit.toString(), achild.getObservable().getUnit());
				}
			}
		}

		public void inferUnits(Actuator ret, Map<String, IUnit> chosenUnits) {

			/*
			 * TODO we should also inherit currencies and ranges if we don't have them
			 */

			Observable modelObservable = null;
			boolean assigned = false;

			if (Units.INSTANCE.needsUnits(observable)) {

				for (ModelD md : models) {

					Model model = md.model;

					modelObservable = model.getCompatibleOutput(ret.getObservable(), getDataflowContext(),
							scope.getMonitor());
					if (modelObservable == null) {
						continue;
					}

					IUnit baseUnit = Units.INSTANCE.getDefaultUnitFor(observable);
					if (observable.isFluidUnits() && observable.getUnit() == null) {

						if (modelObservable.getUnit() != null) {
							observable.withUnit(modelObservable.getUnit());
							chosenUnits.put(baseUnit.toString(), modelObservable.getUnit());
							assigned = true;
						} else {
							if (!chosenUnits.containsKey(baseUnit.toString())) {
								if (Units.INSTANCE.needsUnitScaling(observable)) {
									UnitContextualization contextualization = Units.INSTANCE
											.getContextualization(modelObservable, scale, null);
									observable.withUnit(contextualization.getChosenUnit());
								} else {
									observable.withUnit(baseUnit);
								}
								chosenUnits.put(baseUnit.toString(), observable.getUnit());
							} else {
								observable.withUnit(chosenUnits.get(baseUnit.toString()));
							}
							assigned = true;
						}
					} else if (observable.getUnit() == null) {
						observable.withUnit(modelObservable.getUnit() == null ? baseUnit : modelObservable.getUnit());
						chosenUnits.put(baseUnit.toString(), observable.getUnit());
						assigned = true;
					}
				}

				if (modelObservable != null && modelObservable.getUnit() == null) {
					/*
					 * it's a fluid unit; find it in the unit catalog and use a new observable with
					 * the unit to compute mediations. If it's not in there, mediator computation
					 * will throw an exception.
					 */
					IUnit baseUnit = Units.INSTANCE.getDefaultUnitFor(observable);
					modelObservable = new Observable(modelObservable).withUnit(chosenUnits.get(baseUnit.toString()));
				}
			}

			/*
			 * Fix the units if they were assigned here and there are countable aggregators
			 * that interfere with the spatial or temporal distribution of the original
			 * units.
			 */
			if (assigned) {
				Observables.INSTANCE.contextualizeUnitsForAggregation(this.observable, context.getScale());
			}

			/**
			 * Record the source in the catalog only after any fluid units have been
			 * resolved.
			 */
			if (modelObservable != null && !sources.containsKey(ret.getObservedConcept())) {
				sources.put(ret.getObservedConcept(), modelObservable);
			}

		}

		Scale computeCoverage(Scale current) throws KlabException {

			Scale myCov = null;
			for (ModelD model : models) {
				Scale scale = model.model.getCoverage(scope.getMonitor());
				if (myCov == null) {
					myCov = scale.collapse();
				} else {
					myCov = myCov.merge(scale.collapse(), LogicalConnector.UNION);
				}
			}
			if (myCov != null) {
				if (current == null) {
					current = myCov;
				} else {
					current = current.merge(myCov, LogicalConnector.INTERSECTION);
				}
			}

			for (Node child : children) {
				current = child.computeCoverage(current);
			}

			return current;
		}

		/*
		 * sort by reverse refcount of model, so that actuators are always output before
		 * any references to them.
		 */
		private List<Node> sortChildren() {
			List<Node> ret = new ArrayList<>(children);
			Collections.sort(ret, new Comparator<Node>() {

				@Override
				public int compare(DataflowCompiler.Node o1, DataflowCompiler.Node o2) {

					if (o2.models.isEmpty() && o1.models.isEmpty()) {
						return 0;
					}
					if (!o2.models.isEmpty() && o1.models.isEmpty()) {
						return 1;
					}
					if (o2.models.isEmpty() && !o1.models.isEmpty()) {
						return -1;
					}
					return Integer.compare(o2.models.iterator().next().useCount, o1.models.iterator().next().useCount);
				}
			});
			return ret;
		}
	}

	/**
	 * The simple compilation strategy keeps a catalog of models and a builds a tree
	 * of models usage for each observable. The nodes are scanned from the root and
	 * an actuator is built the first time a model is encountered; a reference to
	 * the same actuator is built from the second time onwards. If the model is only
	 * used once and for a single observable, the original actuator for a model is
	 * given the name of its use and the mediators, if any, are compiled directly in
	 * it; otherwise, a link is created and mediators are put in the reference
	 * import.
	 */
	private Node compileActuator(IResolvable resolvable, IResolutionScope.Mode mode,
			Graph<IResolvable, ResolutionEdge> graph, Scale scale, Map<IConcept, IConcept> resolvedPredicates,
			IMonitor monitor) {

		Node ret = new Node(resolvable, mode);

		if (scale == null && resolvable instanceof Acknowledgement) {
			scale = (Scale.create(((Acknowledgement) resolvable).getContextualization().getExtents(monitor)));
		}

		ret.scale = scale;

		if (resolvable instanceof Observable) {
			resolvedPredicates = new HashMap<>(resolvedPredicates);
			resolvedPredicates.putAll(((Observable) resolvable).getResolvedPredicates());
		}

		/*
		 * go through models
		 */
		List<ResolutionEdge> resolvers = getResolvers(graph, resolvable);
		boolean honorPartitions = true;

		/*
		 * check if we need to honor partitions or we're in a situation where the
		 * context is covered partially by one model that still resolves the context
		 * boundary.
		 */
		if (resolvers.size() == 1 && resolvers.get(0).coverage != null
				&& resolvers.get(0).coverage.coversBoundaries(scale)) {
			honorPartitions = false;
		}

		for (ResolutionEdge d : resolvers) {

			if (d.isPartition && honorPartitions) {
				ret.hasPartitions = true;
			}

			IResolvable source = graph.getEdgeSource(d);

			if (source instanceof IObservable) {

				if (d.deferred) {
					/*
					 * Add the additional resolution step to the node, to be merged into the
					 * actuator.
					 */
					ret.observable.getDeferredObservables().add((Observable) source);

				} else {

					Set<ResolutionEdge> sources = graph.incomingEdgesOf(source);
					if (sources.size() == 1) {
						source = graph.getEdgeSource(sources.iterator().next());
					}
				}
			}

			if (source instanceof ResolvedArtifact) {

				ret.resolvedArtifact = (ResolvedArtifact) source;
				sources.put(new ObservedConcept(ret.resolvedArtifact.getObservable()),
						new Observable((Observable) ret.resolvedArtifact.getArtifact().getObservable()));

				for (ResolutionEdge o : graph.incomingEdgesOf(source)) {
					ret.children.add(compileActuator(graph.getEdgeSource(o), o.mode, graph,
							o.coverage == null ? scale : o.coverage, resolvedPredicates, monitor));
				}

			} else if (source instanceof Model) {

				Model model = (Model) source;

				Observable compatibleOutput = model.getCompatibleOutput(ret.observable, getDataflowContext(), monitor);
				if (compatibleOutput == null) {
					// only happens when the observable is resolved indirectly
					compatibleOutput = ret.observable;
				} else {
					compatibleOutput = new Observable(compatibleOutput);
				}

				ModelD md = compileModel(model, d.isPartition && honorPartitions, resolvedPredicates);
				for (ResolutionEdge o : graph.incomingEdgesOf(model)) {
					ret.children.add(compileActuator(graph.getEdgeSource(o), o.mode, graph,
							o.coverage == null ? scale : o.coverage, resolvedPredicates, monitor));
				}

				if (md.hasPartitions) {
					try {
						md.coverage = Coverage.full(model.getCoverage(monitor));
					} catch (KlabException e) {
						monitor.error("error computing model coverage: " + e.getMessage());
					}
				}

				ret.models.add(md);
				ret.strategy = model.getObservationStrategy();
			}
		}

		return ret;
	}

	public IConcept getDataflowContext() {
		return this.context == null ? null : this.context.getObservable().getType();
	}

	/**
	 * Return the edges in order of resolution, so that any partials have the
	 * required priority.
	 * 
	 * @param graph
	 * @param resolvable
	 * @return
	 */
	private List<ResolutionEdge> getResolvers(Graph<IResolvable, ResolutionEdge> graph, IResolvable resolvable) {
		List<ResolutionEdge> ret = new ArrayList<>();
		ret.addAll(graph.incomingEdgesOf(resolvable));
		ret.sort(new Comparator<ResolutionEdge>() {

			@Override
			public int compare(ResolutionEdge o1, ResolutionEdge o2) {
				return Integer.compare(o1.order, o2.order);
			}
		});
		return ret;
	}

	/**
	 * Return all the stated computations for the passed model, inserting any
	 * necessary cast transformer in case the types need to be converted.
	 * 
	 * @param model
	 * @param iLocator
	 * @return
	 */
	public List<IContextualizable> getModelComputation(Model model, IArtifact.Type targetType, boolean initialization) {
		List<IContextualizable> ret = new ArrayList<>(model.getComputation());
		int lastDirectPosition = -1;
		IArtifact.Type lastDirectType = null;
		int i = 0;
		for (IContextualizable resource : ret) {
			if (((ComputableResource) resource).getTarget() == null) {
				Type resType = getResourceType(resource);
				if (resType != null && resType != Type.VOID) {
					lastDirectPosition = i;
					lastDirectType = resType;
				}
			}
			i++;
		}

		if (lastDirectType != null && lastDirectType != targetType && lastDirectType != IArtifact.Type.VALUE) {
			IContextualizable cast = Klab.INSTANCE.getRuntimeProvider().getCastingResolver(lastDirectType, targetType);
			if (cast != null) {
				ret.add(lastDirectPosition + 1, cast);
			}
		}
		return ret;
	}

	private Type getResourceType(IContextualizable resource) {

		if (resource.getClassification() != null || resource.getAccordingTo() != null) {
			return Type.CONCEPT;
		}

		if (resource.getLookupTable() != null) {
			return resource.getLookupTable().getLookupType();
		}
		if (resource.getUrn() != null) {
			IResource res = Resources.INSTANCE.resolveResource(resource.getUrn());
			if (res != null) {
				return res.getType();
			}
		}
		if (resource.getServiceCall() != null) {
			IPrototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
			if (prototype != null) {
				return prototype.getType();
			}
		}
		if (resource.getExpression() != null) {
			return Type.VALUE;
		}
		return null;
	}

	/**
	 * Must create different descriptor for different indirect usages and
	 * concretizations.
	 * 
	 * @param model
	 * @param indirectAdapters
	 * @return
	 */
	ModelD compileModel(Model model, boolean hasPartitions, Map<IConcept, IConcept> concreteTraits) {
		ModelD ret = getModelDescriptor(model, concreteTraits);
		if (ret == null) {
			ret = new ModelD(model, hasPartitions, concreteTraits);
			addModelToCatalog(model, concreteTraits, ret);
		}
		ret.useCount++;
		return ret;
	}

	private List<IResolvable> getRootResolvables(Graph<IResolvable, ResolutionEdge> graph) {
		List<IResolvable> ret = new ArrayList<>();
		for (IResolvable res : graph.vertexSet()) {
			if (graph.outgoingEdgesOf(res).size() == 0) {
				ret.add(res);
			}
		}

		if (!graph.vertexSet().isEmpty() && ret.isEmpty()) {
			scope.getMonitor()
					.warn("The resolution graph has no root node: review your model chain for circular dependencies.");
		}

		return ret;
	}

	/*
	 * this is only called with an observer
	 */
	public DataflowCompiler withResolvable(IResolvable resolvable) {
		resolutionGraph.addVertex(resolvable);
		return this;
	}

	/*
	 * this is the main method that builds the resolution graph. Because we may have
	 * a different observable with the same name already in the subject's runtime
	 * context, we must ensure that we change names to a non-existing one so that
	 * they don't conflict, without compromising the graph's structure.
	 */
	public DataflowCompiler withResolution(Link link) {

		IResolvable source = link.getSource().getResolvable();
		IResolvable target = link.getTarget().getResolvable();

		resolutionGraph.addVertex(target);
		resolutionGraph.addVertex(source);
		resolutionGraph.addEdge(target, source, new ResolutionEdge(link));

		return this;
	}

	/*
	 * This holds all resolvables that had the same names as those in the existing
	 * catalog but have different concepts.
	 * 
	 * TODO still issues observing e.g. geography:Slope -> presence of earth:Slope.
	 */
	List<Pair<String, Observable>> ambiguous = new ArrayList<>();

	/**
	 * Compute mediators, ensuring that two observables declared with fluid units
	 * get the same unit if they share the same base unit.
	 * 
	 * @param from
	 * @param to
	 * @param scale
	 * @param chosenUnits
	 * @return
	 */
	public List<IContextualizable> computeMediators(Observable from, Observable to, IScale scale) {

		if (OWL.INSTANCE.isSemantic(from)) {
			if (((Concept) from.getType()).getSemanticDistance(to.getType(), null, false,
					to.getResolvedPredicates()) < 0) {
				throw new IllegalArgumentException(
						"cannot compute mediators from an observable to another that does not resolve it: " + from
								+ " can not mediate to " + to);
			}
		}

		if (Units.INSTANCE.needsUnits(from) && from.getUnit() == null /* && to.getUnit() == null */) {
			if (!from.is(IKimConcept.Type.NUMEROSITY)) {
				// FIXME fine for counts to have no units, although this should depend on
				// context
				throw new IllegalStateException(
						"Observables need units but have none: " + from + " mediating to " + to);
			}
		}

		List<IContextualizable> ret = new ArrayList<>();
		IObservable current = from;

		if (current.getType().equals(to.getType())) {

			/*
			 * can only be a mediator issue, and if we get here, mediators are compatible
			 */
			if (current.getCurrency() != null && to.getCurrency() != null
					&& !current.getCurrency().equals(to.getCurrency())) {
				ret.add(new ComputableResource(current.getCurrency(), to.getCurrency()));
			} else if (current.getUnit() != null && to.getUnit() != null && !current.getUnit().equals(to.getUnit())) {
				ret.add(new ComputableResource(current.getUnit(), to.getUnit()));
			}
			if (current.getRange() != null && to.getRange() != null && !current.getRange().equals(to.getRange())) {
				ret.add(new ComputableResource(current.getRange(), to.getRange()));
			}
		}

		return ret;
	}

	/*
	 * model catalog to check which models have been output already. Bit weird as we
	 * use ModelD as both key and value, because we need to distinguish descriptors
	 * that represent different concretizations of the same abstract model, but we
	 * store ModelD values that are created by the compiler, as they hold more
	 * information than what is used to check equality.
	 */

	private Map<ModelD, ModelD> modelCatalog = new HashMap<>();

	private void addModelToCatalog(Model model, Map<IConcept, IConcept> concreteTraits, ModelD ret) {
		modelCatalog.put(new ModelD(model, concreteTraits), ret);
	}

	private void resetModelCatalog() {
		modelCatalog.clear();
	}

	private ModelD getModelDescriptor(Model model, Map<IConcept, IConcept> concreteTraits) {
		return modelCatalog.get(new ModelD(model, concreteTraits));
	}

}
