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
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
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
	Map<Model, ModelD> modelCatalog = new HashMap<>();
	// maps the original name on each non-reference actuator to the original
	// observable coming out of the model. Used to set up mediators in models that
	// depend on them.
	Map<String, Observable> observableCatalog = new HashMap<>();

	static class ResolutionEdge {

		Coverage coverage;

		// if not null, the computation will adapt the source to the target and they may
		// be of incompatible types.
		List<IComputableResource> indirectAdapters;

		ResolutionEdge(Coverage coverage, List<IComputableResource> indirectAdapters) {
			this.coverage = coverage;
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
		ret.setResolutionScope(scope);

		for (IResolvable root : getRootResolvables(resolutionGraph)) {

			modelCatalog.clear();

			Node node = compileActuator(root, resolutionGraph, this.context == null ? null : this.context.getScale(),
					monitor);
			Actuator actuator = node.getActuatorTree(monitor, new HashSet<>());
			actuator.setCreateObservation(root instanceof IObserver
					|| !((Observable) root).is(org.integratedmodelling.kim.api.IKimConcept.Type.COUNTABLE));
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
			if (observableCatalog.containsKey(actuator.getName())) {
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

			Actuator actuator = Actuator.create();
			actuator.setObservable(((ResolutionScope) scope).getObservable());
			actuator.setCreateObservation(true);
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
			return obj instanceof ModelD && model.equals(((ModelD) obj).model);
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
		Set<ModelD> models = new HashSet<>();
		List<Node> children = new ArrayList<>();
		Scale scale;
		boolean definesScale;
		String alias;

		public Node(IResolvable observable) {
			if (observable instanceof Observable) {
				this.observable = (Observable) observable;
			} else if (observable instanceof Observer) {
				this.observer = (Observer) observable;
				this.observable = this.observer.getObservable();
			}
		}

		/*
		 * get the actuator in the node, ignoring the children
		 */
		Actuator createActuator(IMonitor monitor, Set<Model> generated) {

			/*
			 * create the original actuator
			 */
			Actuator ret = Actuator.create();

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
			} else {
				ret.setName(observable.getLocalName());
			}

			if (models.size() == 1) {

				ModelD theModel = models.iterator().next();
				defineActuator(ret, theModel.model.getLocalNameFor(observable), theModel.model,
						theModel.indirectAdapters, generated);

			} else if (models.size() > 1) {

				/*
				 * output the independent actuators
				 */
				int i = 1;
				List<String> modelIds = new ArrayList<>();
				for (ModelD modelDesc : models) {

					Actuator partial = Actuator.create();
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
					defineActuator(partial, name, modelDesc.model, modelDesc.indirectAdapters, generated);
					partial.setCoverage(modelDesc.coverage);

					modelIds.add(name);

					ret.getActuators().add(partial);
				}
			}

			return ret;
		}

		private void defineActuator(Actuator ret, String name, Model model, List<IComputableResource> indirectAdapters,
				Set<Model> generated) {

			ret.setName(name);

			if (!generated.contains(model)) {
				generated.add(model);
				for (IComputableResource resource : model.getComputation(ITime.INITIALIZATION)) {
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
				ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(model));
			} else {
				ret.setReference(true);
			}

		}

		/*
		 * get the finished actuator with all the children and the mediation strategy
		 * TODO must add any last mediation for the root observable if needed
		 */
		Actuator getActuatorTree(IMonitor monitor, Set<Model> generated) {

			Actuator ret = createActuator(monitor, generated);
			for (Node child : sortChildren()) {
				// this may be a new actuator or a reference to an existing one
				Actuator achild = child.getActuatorTree(monitor, generated);
				ret.getActuators().add(achild);
				for (IComputableResource mediator : Observables.INSTANCE
						.computeMediators(observableCatalog.get(achild.getName()), achild.getObservable())) {
					ret.addMediation(mediator, achild);
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
	private Node compileActuator(IResolvable resolvable, Graph<IResolvable, ResolutionEdge> graph, Scale scale,
			IMonitor monitor) {

		Node ret = new Node(resolvable);

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

			Model model = (Model) graph.getEdgeSource(d);

			Observable compatibleOutput = model.getCompatibleOutput(ret.observable);
			if (compatibleOutput == null) {
				// only happens when the observable is resolved indirectly
				compatibleOutput = ret.observable;
			}
			observableCatalog.put(ret.observable.getLocalName(), compatibleOutput);

			ModelD md = compileModel(model);
			for (ResolutionEdge o : graph.incomingEdgesOf(model)) {
				ret.children.add(compileActuator(graph.getEdgeSource(o), graph, o.coverage == null ? scale : o.coverage,
						monitor));
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

		return ret;
	}

	ModelD compileModel(Model model) {
		ModelD ret = modelCatalog.get(model);
		if (ret == null) {
			ret = new ModelD(model);
			modelCatalog.put(model, ret);
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
			List<IComputableResource> computations) {
		resolutionGraph.addVertex(source);
		resolutionGraph.addVertex(target);
		resolutionGraph.addEdge(source, target, new ResolutionEdge((Coverage) coverage, computations));
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
