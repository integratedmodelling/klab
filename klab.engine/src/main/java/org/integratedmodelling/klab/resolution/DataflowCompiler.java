package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.mediation.IUnit.Contextualization;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
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
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ResolutionScope.Link;
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

	/*
	 * keep the observables of each merged model to create proper references.
	 */
	private Set<IObservable> mergedCatalog = new HashSet<>();

	Graph<IResolvable, ResolutionEdge> resolutionGraph = new DefaultDirectedGraph<>(ResolutionEdge.class);
	Map<Model, ModelD> modelCatalog = new HashMap<>();

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
	Map<String, Observable> sources = new HashMap<>();

	static class ResolutionEdge {

		Coverage coverage;
		IResolutionScope.Mode mode;
		boolean isPartition = false;

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
		}

		public String toString() {
			return "resolves";
		}
	}

	public DataflowCompiler(String name, IResolutionScope scope) {
		this.name = name;
		this.scope = scope;
		this.context = (DirectObservation) scope.getContext();
	}

	public Dataflow compile(IMonitor monitor) {

		if (!System.getProperty("visualize", "false").equals("false") && resolutionGraph.vertexSet().size() > 1) {
			Graphs.show(resolutionGraph, "Resolution graph");
		}

		Dataflow ret = new Dataflow(monitor.getIdentity().getParentIdentity(ISession.class));

		ret.setName(this.name);
		ret.setReferenceName(this.name);
		ret.setContext(this.context);
		ret.setResolutionScope((ResolutionScope) scope);

		for (IResolvable root : getRootResolvables(resolutionGraph)) {

			modelCatalog.clear();
			Node node = compileActuator(root, scope.getMode(), resolutionGraph,
					this.context == null ? null : this.context.getScale(), monitor);
			node.root = true;

			Actuator actuator = node.getActuatorTree(ret, monitor, new HashSet<>(), 0);
			ret.getActuators().add(actuator);

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
			if (sources.containsKey(actuator.getName()) && root instanceof Observable) {
				for (IComputableResource mediator : computeMediators(sources.get(actuator.getName()), node.observable,
						node.scale)) {
					actuator.addComputation(mediator);
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
		if (ret.getActuators().isEmpty() && ((ResolutionScope) scope).getObservable().is(IKimConcept.Type.COUNTABLE)
				&& scope.getMode() == Mode.RESOLUTION) {

			Actuator actuator = Actuator.create(ret, Mode.RESOLUTION);
			actuator.setObservable(((ResolutionScope) scope).getObservable());
			actuator.setType(Type.OBJECT);
			actuator.setNamespace(((ResolutionScope) scope).getResolutionNamespace());
			actuator.setName(((ResolutionScope) scope).getObservable().getName());
			setModelContext(((ResolutionScope) scope).getContextModel(), actuator, ret);
			ret.getActuators().add(actuator);
			ret.setNamespace(actuator.getNamespace());
		}

		return ret;
	}

	private void setModelContext(IModel contextModel, Actuator actuator, Dataflow dataflow) {

		if (contextModel != null && contextModel.isInstantiator() && actuator.getMode() == Mode.RESOLUTION) {

			/*
			 * recover any output states with static initializers
			 */
			for (int i = 1; i < contextModel.getObservables().size(); i++) {
				if (((Observable) contextModel.getObservables().get(i)).isResolved()) {
					Actuator child = Actuator.create(dataflow, Mode.RESOLUTION);
					child.setObservable(new Observable((Observable) contextModel.getObservables().get(i)));
					child.setName(contextModel.getObservables().get(i).getName());
					child.setReferenceName(contextModel.getObservables().get(i).getName());
					child.setType(contextModel.getObservables().get(i).getArtifactType());
					child.addComputation(ComputableResource.create(contextModel.getObservables().get(i).getValue()));
					actuator.getActuators().add(child);
				}
			}

			/*
			 * recover any instantiation actions
			 */
			for (IAction action : contextModel.getBehavior().getActions(Trigger.INSTANTIATION)) {
				for (IComputableResource resource : action.getComputation(Time.INITIALIZATION)) {
					actuator.addComputation(((ComputableResource) resource).copy());
				}
			}
		}
	}

	static class ModelD {

		Model model;
		// how many nodes reference this model's observables
		int useCount;
		// this is null unless the model covers only a part of the context
		Coverage coverage;
		// if true, model is resolved by partitions, even if there is just one
		boolean hasPartitions = false;

		public ModelD(Model model, boolean hasPartitions) {
			this.model = model;
			this.hasPartitions = hasPartitions;
		}

		@Override
		public int hashCode() {
			return model.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof ModelD && model.equals(((ModelD) obj).model);
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
		Observer observer;
		IResolutionScope.Mode mode;
		// it's vital that the order of inclusion of the models is preserved.
		Set<ModelD> models = new LinkedHashSet<>();
		List<Node> children = new ArrayList<>();
		Scale scale;
		String alias;
		Object inlineValue;
		ResolvedArtifact resolvedArtifact;

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

			} else if (resolvable instanceof Observer) {

				this.observer = (Observer) resolvable;
				this.observable = this.observer.getObservable();

			} else if (resolvable instanceof ResolvedArtifact) {

				this.resolvedArtifact = (ResolvedArtifact) resolvable;
				this.observable = (Observable) resolvedArtifact.getObservable();
				sources.put(this.resolvedArtifact.getArtifactId(),
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

			ret.setObservable(observable);
			ret.setName(observable.getReferenceName());
			ret.setAlias(observable.getName());

			switch (observable.getDescription()) {
			case CATEGORIZATION:
				ret.setType(Type.CONCEPT);
				break;
			case DETECTION:
			case INSTANTIATION:
				ret.setType(Type.OBJECT);
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
				break;
			}

			if (observer != null) {

				ret.setNamespace(observer.getNamespace());
				ret.setReferenceName(observer.getId());

			} else if (resolvedArtifact != null /* && artifactAdapters == null */) {
				/*
				 * Different situations if we ARE the artifact or we USE it for something. If we
				 * have artifact adapters, we must compile an import as a child and use our own
				 * observable, done below.
				 */
				ret.setInput(true);
				ret.setReferenceName(resolvedArtifact.getObservable().getName());

			} else {
				ret.setReferenceName(observable.getName());
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
				ret.setReferenceName(theModel.model.getObservables().get(0).getName());
				defineActuator(ret, root ? observable.getName() : theModel.model.getLocalNameFor(observable), theModel,
						generated);

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

					// rename and set the target name as partitioned. Number is the priority if
					// known.
					String name = modelDesc.model.getLocalNameFor(observable) + "_" + index;
					partial.setPartitionedTarget(observable.getName());

					partial.setObservable(observable);
					partial.setType(ret.getType());
					defineActuator(partial, name, modelDesc, generated);

					// no reference partials as our final result is the merged artifact
					if (partial.isReference()) {
						continue;
					}

					reference = false;
					partial.setCoverage(modelDesc.coverage);
					modelIds.add(name);

					ret.getActuators().add(partial);

				}

				ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(observable));

			} else if (inlineValue != null) {
				ret.addComputation(ComputableResource.create(inlineValue));
			}

			if (partials) {
				if (reference) {
					ret.setReference(true);
				} else {
					mergedCatalog.add(observable);
				}
			}

			return ret;
		}

		private void defineActuator(Actuator ret, String name, ModelD theModel, Set<ModelD> generated) {

			Model model = theModel.model;
			ret.setModel(model);

			if (!generated.contains(theModel)) {

				generated.add(theModel);
				for (IComputableResource resource : getModelComputation(model, ret.getType(), Time.INITIALIZATION)) {
					ret.addComputation(resource);
				}

				ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(observable, model));
				for (IDocumentation documentation : model.getDocumentation()) {
					ret.addDocumentation(documentation);
				}

				setModelContext(((ResolutionScope) scope).getContextModel(), ret, ret.getDataflow());

				actuatorCatalog.put(ret.getReferenceName(), ret);

			} else {
				ret.setReference(true);
			}
		}

		/*
		 * get the finished actuator with all the children and the mediation strategy;
		 * add any last mediation for the root observable if needed
		 */
		Actuator getActuatorTree(Dataflow dataflow, IMonitor monitor, Set<ModelD> generated, int level) {

//			System.out.println(StringUtils.spaces(level * 3) + this);

			Actuator ret = createActuator(dataflow, monitor, generated);
			if (!ret.isReference()) {

//				if (Units.INSTANCE.needsUnits(this.observable)) {
//					System.out.println(StringUtils.spaces(level * 3) + "UNITS BEFORE:" + this.observable.getUnit()
//							+ (this.observable.isFluidUnits() ? ", fluid" : ", fixed"));
//				}

				// collect units from dependent models to ensure consistency across unspecified
				// ones
				Map<String, IUnit> chosenUnits = new HashMap<>();

				for (Node child : sortChildren()) {

					// this may be a new actuator or a reference to an existing one.
					Actuator achild = child.getActuatorTree(dataflow, monitor, generated, level + 1);

					if (achild.isFilter()) {

						ret.adoptFilter(achild, actuatorCatalog);

					} else {

						ret.getActuators().add(achild);
						recordUnits(achild, chosenUnits);
						if (sources.containsKey(achild.getName())) {
							for (IComputableResource mediator : computeMediators(sources.get(achild.getName()),
									achild.getObservable(), scale)) {
								ret.addMediation(mediator, achild);
							}
						}
					}
				}

				inferUnits(ret, chosenUnits);

//				if (Units.INSTANCE.needsUnits(this.observable)) {
//					System.out.println(StringUtils.spaces(level * 3) + "UNITS AFTER:" + this.observable.getUnit()
//							+ " using " + chosenUnits);
//				}

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

			if (Units.INSTANCE.needsUnits(observable)) {

				for (ModelD md : models) {

					Model model = md.model;

					modelObservable = model.getCompatibleOutput(ret.getObservable());
					if (modelObservable == null) {
						continue;
					}

					IUnit baseUnit = Units.INSTANCE.getDefaultUnitFor(observable);
					if (observable.isFluidUnits() && observable.getUnit() == null) {

						if (modelObservable.getUnit() != null) {
							observable.withUnit(modelObservable.getUnit());
							chosenUnits.put(baseUnit.toString(), modelObservable.getUnit());
						} else {
							if (!chosenUnits.containsKey(baseUnit.toString())) {
								if (Units.INSTANCE.needsUnitScaling(observable)) {
									Contextualization contextualization = Units.INSTANCE
											.getContextualization(modelObservable, scale, null);
									observable.withUnit(contextualization.getChosenUnit());
								} else {
									observable.withUnit(baseUnit);
								}
								chosenUnits.put(baseUnit.toString(), observable.getUnit());
							} else {
								observable.withUnit(chosenUnits.get(baseUnit.toString()));
							}
						}
					} else if (observable.getUnit() == null) {
						observable.withUnit(modelObservable.getUnit() == null ? baseUnit : modelObservable.getUnit());
						chosenUnits.put(baseUnit.toString(), observable.getUnit());
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

			/**
			 * Record the source in the catalog only after any fluid units have been
			 * resolved.
			 */
			if (modelObservable != null && !sources.containsKey(ret.getName())) {
				sources.put(ret.getName(), modelObservable);
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
		 * any references to them. Ensure that filters are output last.
		 */
		private List<Node> sortChildren() {
			List<Node> ret = new ArrayList<>(children);
			Collections.sort(ret, new Comparator<Node>() {

				@Override
				public int compare(DataflowCompiler.Node o1, DataflowCompiler.Node o2) {
					if (o1.observable.getFilteredObservable() != null
							&& o2.observable.getFilteredObservable() == null) {
						return 1;
					}
					if (o1.observable.getFilteredObservable() == null
							&& o2.observable.getFilteredObservable() != null) {
						return -1;
					}
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
			Graph<IResolvable, ResolutionEdge> graph, Scale scale, IMonitor monitor) {

		Node ret = new Node(resolvable, mode);

		if (scale == null && resolvable instanceof Observer) {
			scale = (Scale.create(((Observer) resolvable).getBehavior().getExtents(monitor)));
		}

		ret.scale = scale;

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
				Set<ResolutionEdge> sources = graph.incomingEdgesOf(source);
				if (sources.size() == 1) {
					source = graph.getEdgeSource(sources.iterator().next());
				}
			}

			if (source instanceof ResolvedArtifact) {

				ret.resolvedArtifact = (ResolvedArtifact) source;
				sources.put(ret.resolvedArtifact.getArtifactId(),
						new Observable((Observable) ret.resolvedArtifact.getArtifact().getObservable()));

				for (ResolutionEdge o : graph.incomingEdgesOf(source)) {
					ret.children.add(compileActuator(graph.getEdgeSource(o), o.mode, graph,
							o.coverage == null ? scale : o.coverage, monitor));
				}

			} else if (source instanceof Model) {

				Model model = (Model) source;

				Observable compatibleOutput = model.getCompatibleOutput(ret.observable);
				if (compatibleOutput == null) {
					// only happens when the observable is resolved indirectly
					compatibleOutput = ret.observable;
				} else {
					compatibleOutput = new Observable(compatibleOutput);
				}
				// observableCatalog.put(compatibleOutput.getName(), compatibleOutput);

				ModelD md = compileModel(model, /* d.indirectAdapters, */ d.isPartition && honorPartitions);
				for (ResolutionEdge o : graph.incomingEdgesOf(model)) {
					ret.children.add(compileActuator(graph.getEdgeSource(o), o.mode, graph,
							o.coverage == null ? scale : o.coverage, monitor));
				}

				if (md.hasPartitions) {
					try {
						md.coverage = Coverage.full(model.getCoverage(monitor));
					} catch (KlabException e) {
						monitor.error("error computing model coverage: " + e.getMessage());
					}
				}

				ret.models.add(md);
			}
		}

		return ret;
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
	public List<IComputableResource> getModelComputation(Model model, IArtifact.Type targetType, ILocator iLocator) {
		List<IComputableResource> ret = new ArrayList<>(model.getComputation(iLocator));
		int lastDirectPosition = -1;
		IArtifact.Type lastDirectType = null;
		int i = 0;
		for (IComputableResource resource : ret) {
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
			IComputableResource cast = Klab.INSTANCE.getRuntimeProvider().getCastingResolver(lastDirectType,
					targetType);
			if (cast != null) {
				ret.add(lastDirectPosition + 1, cast);
			}
		}
		return ret;
	}

	private Type getResourceType(IComputableResource resource) {

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
	 * Must create different descriptor for different indirect usages.
	 * 
	 * @param model
	 * @param indirectAdapters
	 * @return
	 */
	ModelD compileModel(Model model, boolean hasPartitions) {
		ModelD ret = modelCatalog.get(model);
		if (ret == null) {
			ret = new ModelD(model, hasPartitions);
			modelCatalog.put(model, ret);
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
	public List<IComputableResource> computeMediators(Observable from, Observable to, IScale scale) {

		if (OWL.INSTANCE.isSemantic(from)) {
			if (!((Observable) to).canResolve((Observable) from)) {
				throw new IllegalArgumentException(
						"cannot compute mediators from an observable to another that does not resolve it: " + from
								+ " can not mediate to " + to);
			}
		}

		if (Units.INSTANCE.needsUnits(from) && from.getUnit() == null /* && to.getUnit() == null */) {
			throw new IllegalStateException("Observables need units but have none: " + from + " mediating to " + to);
		}

		List<IComputableResource> ret = new ArrayList<>();
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

}
