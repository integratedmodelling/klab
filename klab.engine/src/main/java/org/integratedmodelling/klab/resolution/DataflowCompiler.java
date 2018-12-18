package org.integratedmodelling.klab.resolution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.resolution.ICoverage;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;
import org.integratedmodelling.klab.api.resolution.IResolvable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.runtime.observations.DirectObservation;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Dataflow;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Observer;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Coverage;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.graph.Graphs;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class DataflowCompiler {

	private String name;
	private DirectObservation context;
	private IResolutionScope scope;

	Graph<IResolvable, ResolutionEdge> resolutionGraph = new DefaultDirectedGraph<>(ResolutionEdge.class);
	Map<Model, List<ModelD>> modelCatalog = new HashMap<>();
	// maps the original name on each non-reference actuator to the original
	// observable coming out of the model. Used to set up mediators in models that
	// depend on them.
	Map<String, Observable> observableCatalog = new HashMap<>();

	static class ResolutionEdge {

		Coverage coverage;
		IResolutionScope.Mode mode;

		/*
		 * if not null, the computation will adapt the source to the target and they may
		 * be of incompatible types. FIXME: there is only one Model per model, and it
		 * may be used more than once with different transformations.
		 */
		List<IComputableResource> indirectAdapters;

		ResolutionEdge(Coverage coverage, IResolutionScope.Mode mode, List<IComputableResource> indirectAdapters) {
			this.coverage = coverage;
			this.mode = mode;
			this.indirectAdapters = indirectAdapters;
		}

		ResolutionEdge() {
		}

		public String toString() {
			return "resolves" + (indirectAdapters == null ? "" : " indirectly");
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

		Dataflow ret = new Dataflow();
		ret.setName(this.name);
		ret.setContext(this.context);
		ret.setResolutionScope((ResolutionScope) scope);

		for (IResolvable root : getRootResolvables(resolutionGraph)) {

			modelCatalog.clear();
//			boolean isResolver = root instanceof IObserver
//					|| !((Observable) root).is(org.integratedmodelling.kim.api.IKimConcept.Type.COUNTABLE);
			Node node = compileActuator(root,
//					isResolver ? IResolutionScope.Mode.RESOLUTION : IResolutionScope.Mode.INSTANTIATION, 
					scope.getMode(),
					resolutionGraph,
					this.context == null ? null : this.context.getScale(), monitor);
			Actuator actuator = node.getActuatorTree(ret, monitor, new HashSet<>());
//			actuator.setCreateObservation(scope.getMode() == Mode.RESOLUTION);
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
			 * if needed and applicable, finish the computational chain with any mediators
			 * needed to turn the modeled observable into the requested one.
			 */
			if (observableCatalog.containsKey(actuator.getName()) && root instanceof Observable) {
				for (IComputableResource mediator : Observables.INSTANCE
						.computeMediators(observableCatalog.get(actuator.getName()), (Observable) root)) {
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
		 * This happens when we resolved a subject observable (from a previous
		 * instantiator calls) without an observer and resolution did not find any
		 * models.
		 */
		if (ret.getActuators().isEmpty() && ((ResolutionScope) scope).getObservable().is(IKimConcept.Type.SUBJECT)
				&& scope.getMode() == Mode.RESOLUTION) {

			Actuator actuator = Actuator.create(ret, Mode.RESOLUTION);
			actuator.setObservable(((ResolutionScope) scope).getObservable());
//			actuator.setCreateObservation(true);
			actuator.setType(Type.OBJECT);
			actuator.setNamespace(((ResolutionScope) scope).getResolutionNamespace());
			actuator.setName(((ResolutionScope) scope).getObservable().getLocalName());

			ret.getActuators().add(actuator);
			ret.setNamespace(actuator.getNamespace());
		}

		return ret;
	}

	static class ModelD {

		Model model;
		// how many nodes reference this model's observables
		int useCount;
		// this is null unless the model covers only a part of the context
		Coverage coverage;
		// this is null unless the model resolves the observable indirectly
		List<IComputableResource> indirectAdapters;

		public ModelD(Model model) {
			this.model = model;
		}

		@Override
		public int hashCode() {
			return model.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof ModelD && model.equals(((ModelD) obj).model)
					&& hasSameAdapters(((ModelD) obj).indirectAdapters);
		}

		public boolean hasSameAdapters(List<IComputableResource> adapters) {
			if (adapters == null) {
				return this.indirectAdapters == null;
			}
			if (this.indirectAdapters != null) {
				return this.indirectAdapters.equals(adapters);
			}
			return false;
		}

	}

	/**
	 * Each node represents one use of a model to compute one observable. Each node
	 * will compute an actuator (a true one the first use, a reference afterwards).
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

		Observable observable;
		Observer observer;
		IResolutionScope.Mode mode;
		Set<ModelD> models = new HashSet<>();
		List<Node> children = new ArrayList<>();
		Scale scale;
		boolean definesScale;
		String alias;
		ResolvedArtifact resolvedArtifact;
		public List<IComputableResource> artifactAdapters;

		public Node(IResolvable resolvable, IResolutionScope.Mode mode) {
			this.mode = mode;
			if (resolvable instanceof Observable) {
				this.observable = (Observable) resolvable;
			} else if (resolvable instanceof Observer) {
				this.observer = (Observer) resolvable;
				this.observable = this.observer.getObservable();
			} else if (resolvable instanceof ResolvedArtifact) {
				this.resolvedArtifact = (ResolvedArtifact) resolvable;
				this.observable = (Observable) resolvedArtifact.getObservable();
				observableCatalog.put(this.resolvedArtifact.getArtifactId(),
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
			ret.setDefinesScale(definesScale);
			ret.setAlias(observable.getLocalName());

			switch (observable.getObservationType()) {
			case CLASSIFICATION:
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
			}

			if (observer != null) {

				ret.setNamespace(observer.getNamespace());
				ret.setName(observer.getId());

			} else if (resolvedArtifact != null && artifactAdapters == null) {
				/*
				 * Different situations if we ARE the artifact or we USE it for something. If we
				 * have artifact adapters, we must compile an import as a child and use our own
				 * observable, done below.
				 */
				ret.setName(resolvedArtifact.getArtifactId());
				ret.setInput(true);

			} else {
				ret.setName(observable.getLocalName());
			}

			if (models.size() == 1) {

				ModelD theModel = models.iterator().next();
				defineActuator(ret, theModel.model.getLocalNameFor(observable), theModel, generated);

			} else if (models.size() > 1) {

				/*
				 * output the independent actuators
				 */
				int i = 1;
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
					partial.setPartitionedTarget(modelDesc.model.getLocalNameFor(observable));

					partial.setType(ret.getType());
					partial.setObservable(observable);
					partial.setDefinesScale(true);
					defineActuator(partial, name, modelDesc, generated);
					partial.setCoverage(modelDesc.coverage);

					modelIds.add(name);

					ret.getActuators().add(partial);
				}
			} else if (resolvedArtifact != null && artifactAdapters != null) {

				// /*
				// * check if any mediation is needed
				// */
				// List<IComputableResource> mediators = Observables.INSTANCE.computeMediators(
				// resolvedArtifact.getArtifact().getObservable(),
				// resolvedArtifact.getObservable());

				/*
				 * we are adapting the resolved artifact, so we compile in the import and add
				 * the adapters to our own computation
				 */
				Actuator resolved = Actuator.create(dataflow, mode);
				resolved.setObservable(resolvedArtifact.getObservable());
				resolved.setInput(true);
				resolved.setAlias(resolvedArtifact.getArtifactId());
				resolved.setName(resolvedArtifact.getArtifactId());
				resolved.setType(resolvedArtifact.getObservable().getArtifactType());
				if (artifactAdapters != null) {
					for (IComputableResource adapter : artifactAdapters) {
						ret.addComputation(adapter);
					}
				}

				/*
				 * add any mediation needed
				 */
				// for (IComputableResource mediator : mediators) {
				// ret.addMediation(mediator, ret);
				// }

				resolved.getAnnotations()
						.addAll(Annotations.INSTANCE.collectAnnotations(observable, resolvedArtifact.getArtifact()));

				ret.getActuators().add(resolved);
			}

			return ret;
		}

		private void defineActuator(Actuator ret, String name, ModelD theModel, Set<ModelD> generated) {

			Model model = theModel.model;
			List<IComputableResource> indirectAdapters = theModel.indirectAdapters;
			ret.setName(name);
			ret.setModel(model);

			if (!generated.contains(theModel)) {
				generated.add(theModel);
				for (IComputableResource resource : getModelComputation(model, ret.getType(), ITime.INITIALIZATION)) {
					ret.addComputation(resource);
					if (indirectAdapters != null && resource.getTarget() == null) {
						/*
						 * redirect the computation to compute the indirect target artifact
						 */
						((ComputableResource) resource).setTarget(model.getObservables().get(0));
					}
				}
				if (indirectAdapters != null) {
					for (IComputableResource adapter : indirectAdapters) {
						ret.addComputation(adapter);
					}
				}
				ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(observable, model));
				for (IDocumentation documentation : model.getDocumentation()) {
					ret.addDocumentation(documentation);
				}
			} else {
				ret.setReference(true);
			}
		}

		/*
		 * get the finished actuator with all the children and the mediation strategy
		 * TODO must add any last mediation for the root observable if needed
		 */
		Actuator getActuatorTree(Dataflow dataflow, IMonitor monitor, Set<ModelD> generated) {

			Actuator ret = createActuator(dataflow, monitor, generated);
			for (Node child : sortChildren()) {
				/*
				 * ACHTUNG the pre-resolved observable (from the original artifact) is not in
				 * the catalog, so mediators for external inputs do not get generated.
				 */
				// this may be a new actuator or a reference to an existing one
				Actuator achild = child.getActuatorTree(dataflow, monitor, generated);
				ret.getActuators().add(achild);
				if (observableCatalog.containsKey(achild.getName())) {
					for (IComputableResource mediator : Observables.INSTANCE
							.computeMediators(observableCatalog.get(achild.getName()), achild.getObservable())) {
						ret.addMediation(mediator, achild);
					}
				}
			}
			return ret;
		}

		Scale computeCoverage(Scale current) throws KlabException {

			Scale myCov = null;
			for (ModelD model : models) {
				if (model.model.getBehavior().hasScale()) {
					if (myCov == null) {
						myCov = Scale.create(model.model.getBehavior().getExtents(scope.getMonitor())).collapse();
					} else {
						myCov = myCov.merge(
								Scale.create(model.model.getBehavior().getExtents(scope.getMonitor())).collapse(),
								LogicalConnector.UNION);
					}
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
			Graph<IResolvable, ResolutionEdge> graph, Scale scale, IMonitor monitor) {

		Node ret = new Node(resolvable, mode);

		if (scale == null && resolvable instanceof Observer) {
			scale = (Scale.create(((Observer) resolvable).getBehavior().getExtents(monitor)));
			ret.definesScale = true;
		}

		ret.scale = scale;

		/*
		 * go through models
		 */
		boolean hasPartials = graph.incomingEdgesOf(resolvable).size() > 1;
		for (ResolutionEdge d : graph.incomingEdgesOf(resolvable)) {

			IResolvable source = graph.getEdgeSource(d);
			
			if (source instanceof IObservable) {
				Set<ResolutionEdge> sources = graph.incomingEdgesOf(source);
				if (sources.size() == 1) {
					source = graph.getEdgeSource(sources.iterator().next());
				}
			}

			if (source instanceof ResolvedArtifact) {

				ret.resolvedArtifact = (ResolvedArtifact) source;
				observableCatalog.put(ret.resolvedArtifact.getArtifactId(),
						(Observable) ret.resolvedArtifact.getArtifact().getObservable());
				ret.artifactAdapters = d.indirectAdapters;

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
				}
				observableCatalog.put(compatibleOutput.getLocalName(), compatibleOutput);

				ModelD md = compileModel(model, d.indirectAdapters);
				for (ResolutionEdge o : graph.incomingEdgesOf(model)) {
					ret.children.add(compileActuator(graph.getEdgeSource(o), o.mode, graph,
							o.coverage == null ? scale : o.coverage, monitor));
				}

				md.indirectAdapters = d.indirectAdapters;

				if (hasPartials) {
					try {
						md.coverage = Coverage.full(Scale.create(model.getBehavior().getExtents(monitor)));
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
	 * Must create different descriptor for different indirect usages
	 * 
	 * @param model
	 * @param indirectAdapters
	 * @return
	 */
	ModelD compileModel(Model model, List<IComputableResource> indirectAdapters) {
		ModelD ret = null;
		List<ModelD> list = modelCatalog.get(model);
		if (list == null) {
			list = new ArrayList<>();
			ret = new ModelD(model);
			list.add(ret);
			modelCatalog.put(model, list);
		} else {
			for (ModelD md : modelCatalog.get(model)) {
				if (md.hasSameAdapters(indirectAdapters)) {
					ret = md;
					break;
				}
			}
			if (ret == null) {
				ret = new ModelD(model);
				list.add(ret);
			}
		}
		ret.useCount++;
		return ret;
	}

	/**
	 * 
	 * @param source
	 * @param target
	 * @param coverage
	 * @param indirectAdapters
	 *            if not empty, the source is the result of an alternative
	 *            resolution of the target observable and its type will be
	 *            translated into the target's by the computation.
	 * @return the compiler itself
	 */
	public DataflowCompiler withResolution(IResolvable source, IResolvable target, ICoverage coverage,
			IResolutionScope.Mode mode, List<IComputableResource> computations) {
		resolutionGraph.addVertex(source);
		resolutionGraph.addVertex(target);
		resolutionGraph.addEdge(source, target, new ResolutionEdge((Coverage) coverage, mode, computations));
		return this;
	}

	private List<IResolvable> getRootResolvables(Graph<IResolvable, ResolutionEdge> graph) {
		List<IResolvable> ret = new ArrayList<>();
		for (IResolvable res : graph.vertexSet()) {
			if (graph.outgoingEdgesOf(res).size() == 0) {
				ret.add(res);
			}
		}
		return ret;
	}

	public DataflowCompiler withResolvable(IResolvable resolvable) {
		resolutionGraph.addVertex(resolvable);
		return this;
	}

}
