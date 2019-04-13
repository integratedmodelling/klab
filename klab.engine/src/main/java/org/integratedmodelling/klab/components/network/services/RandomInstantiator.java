package org.integratedmodelling.klab.components.network.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class RandomInstantiator implements IExpression, IInstantiator {

	private String sourceArtifact = null;
	private String targetArtifact = null;
	private Random random = new Random();
	private double probability = 0.01;
	private boolean allowSelfConnections;
	private boolean allowReciprocal;
	private boolean allowCycles;

	enum Method {
		ErdosRenyi, OutDegree
		// TODO add others
	}

	enum SpaceType {
		None, Line, LineCentroid, LineEdge, ConvexHull
	}

	private Method method = Method.ErdosRenyi;
	private SpaceType spaceType = SpaceType.None;
	private Graph<IObjectArtifact, DefaultEdge> graph;
	private IComputationContext context;

	public RandomInstantiator() {
		/* to instantiate as expression - do not remove (or use) */}

	public RandomInstantiator(IParameters<String> parameters, IComputationContext context) {

		this.context = context;
		this.sourceArtifact = parameters.get("source", String.class);
		this.targetArtifact = parameters.get("target", String.class);
		this.probability = parameters.get("p", 0.01);
		this.allowSelfConnections = parameters.get("selfconnections", Boolean.FALSE);
		this.allowReciprocal = parameters.get("reciprocal", Boolean.FALSE);
		this.allowCycles = parameters.get("cycles", Boolean.TRUE);

		if (parameters.contains("seed")) {
			random.setSeed(parameters.get("seed", Number.class).longValue());
		}
		if (parameters.contains("method")) {
			this.method = Method.valueOf(parameters.get("method", String.class));
		}
		if (parameters.contains("space")) {
			this.spaceType = SpaceType.valueOf(parameters.get("space", String.class));
		}
	}

	@Override
	public IGeometry getGeometry() {
		// TODO ensure this reflects the spatial and temporal character of the
		// relationships based on the context and choice of parameters.
		return Geometry.create("#");
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		// force this if we are instantiating bonds
		if (semantics.getType().is(IKimConcept.Type.BIDIRECTIONAL)) {
			allowReciprocal = false;
		}
		
		IConcept sourceConcept = Observables.INSTANCE.getRelationshipSource(semantics.getType());
		IConcept targetConcept = Observables.INSTANCE.getRelationshipTarget(semantics.getType());

		/*
		 * recover artifacts according to parameterization or lack thereof. Source and
		 * target artifacts may be the same artifact.
		 */
		List<IArtifact> sources = new ArrayList<>();
		if (sourceArtifact == null) {
			sources.addAll(context.getArtifact(sourceConcept));
		} else {
			sources.add(context.getArtifact(sourceArtifact));
		}

		List<IArtifact> targets = new ArrayList<>();
		if (targetArtifact == null) {
			targets.addAll(context.getArtifact(targetConcept));
		} else {
			targets.add(context.getArtifact(targetArtifact));
		}

		// all artifacts must be non-null and objects
		for (List<?> co : new List[] { sources, targets }) {
			for (Object o : co) {
				if (!(o instanceof IObjectArtifact)) {
					throw new IllegalArgumentException(
							"klab.networks.random: at least one source or target artifact does not exist or is not an object artifact");
				}
			}
		}

		boolean samePools = (sourceArtifact != null && targetArtifact != null && sourceArtifact.equals(targetArtifact))
				|| (sourceArtifact == null && targetArtifact == null && sourceConcept.equals(targetConcept));

		// TODO these are the simple methods - enable others separately
		Collection<IArtifact> allSources = CollectionUtils.joinArtifacts(sources);
		Collection<IArtifact> allTargets = CollectionUtils.joinArtifacts(sources);
		int nSources = allSources.size();
		int nTargets = allTargets.size();
		int nNodes = samePools ? nSources : nSources + nTargets;

		graph = new DefaultDirectedGraph<>(DefaultEdge.class);

		for (IArtifact source : allSources) {
			for (IArtifact target : allTargets) {

				if (!allowSelfConnections && source.equals(target)) {
					continue;
				}

				if (!(source instanceof IDirectObservation)) {
					throw new IllegalArgumentException("source observations are not direct observations");
				}

				if (!(target instanceof IDirectObservation)) {
					throw new IllegalArgumentException("target observations are not direct observations");
				}

				switch (method) {
				case ErdosRenyi:
					if (random.nextDouble() < probability) {
						connect((IDirectObservation) source, (IDirectObservation) target);
					}
					break;
				case OutDegree:
					if ((int) (random.nextDouble() + probability / (nNodes - 1)) == 1) {
						connect((IDirectObservation) source, (IDirectObservation) target);
					}
					break;
				default:
					break;
				}
			}
		}
		
		context.getMonitor().info("creating " + graph.edgeSet().size() + " relationships of type " + semantics.getType().getDefinition());
		
		return instantiateRelationships();
	}

	private List<IObjectArtifact> instantiateRelationships() {
		
		List<IObjectArtifact> ret = new ArrayList<>();
		// TODO build from graph
		for (DefaultEdge edge : graph.edgeSet()) {
			
		}
		return ret;
	}

	private void connect(IDirectObservation source, IDirectObservation target) {

		// if not accepting cycles, ensure we don't create loops before adding the rel
		if (!allowCycles && createsCycles(source, target)) {
			return;
		}

		// check reciprocity if not allowed
		if (!allowReciprocal) {
			if (graph.containsEdge(target, source)) {
				return;
			}
		}
		
		// add to graph for bookkeeping unless we don't need it
		graph.addVertex(source);
		graph.addVertex(target);
		graph.addEdge(source, target);
	}

	/*
	 * ACHTUNG: this assumes that the current graph has no cycles as it's only
	 * called if we don't accept cycles, so it's not a general solution.
	 */
	private boolean createsCycles(IDirectObservation source, IDirectObservation target) {

		boolean hadSource = graph.containsVertex(source);
		boolean hadTarget = graph.containsVertex(target);

		if (hadSource && hadTarget) {
			// already seen, no need to check
			return false;
		}
		if (!hadSource) {
			graph.addVertex(source);
		}
		if (!hadTarget) {
			graph.addVertex(target);
		}
		
		// try it
		graph.addEdge(source, target);
		boolean ret = new CycleDetector<IObjectArtifact, DefaultEdge>(graph).detectCycles();

		// clean up
		graph.removeEdge(source, target);
		if (!hadSource) {
			graph.removeVertex(source);
		}
		if (!hadTarget) {
			graph.removeVertex(target);
		}
		
		return ret;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new RandomInstantiator(parameters, context);
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

}
