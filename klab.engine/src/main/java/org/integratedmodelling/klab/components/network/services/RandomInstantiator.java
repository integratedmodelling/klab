package org.integratedmodelling.klab.components.network.services;

import java.util.ArrayList;
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
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.jgrapht.Graph;
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

	private Method method = Method.ErdosRenyi;
	private Graph<IObjectArtifact, DefaultEdge> graph;
	
	// From Unai for directed network:
	// # %% random network directed
	//
	// # %% parameters
	// # n is the number of nodes
	// # k is the average out degree of the network
	//
	// # %% result
	// # a is a list of lists containing the output connections of each node
	//
	// # %% observation
	// # no self-connections allowed
	//
	// a=[]
	// for i in range(n):
	// a.append([])
	// for j in range(n):
	// if int(random.random()+k/(n-1))==1 and i!=j:
	// a[i].append(j)

	public RandomInstantiator() {
		/* to instantiate as expression - do not remove (or use) */}

	public RandomInstantiator(IParameters<String> parameters, IComputationContext context) {

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
	}

	@Override
	public IGeometry getGeometry() {
		// TODO ensure this reflects the spatial and temporal character of the
		// relationships based on the context and choice of parameters.
		return Geometry.create("#");
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		List<IObjectArtifact> ret = new ArrayList<>();
		boolean digraph = semantics.getType().is(IKimConcept.Type.UNIDIRECTIONAL);


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
		
		boolean samePools = 
				(sourceArtifact != null && targetArtifact != null && sourceArtifact.equals(targetArtifact)) || 
				(sourceArtifact == null && targetArtifact == null && sourceConcept.equals(targetConcept));

		// TODO these are the simple methods - enable others separately
		int nSources = CollectionUtils.join(sources).size();
		int nTargets = CollectionUtils.join(targets).size();
		int nNodes = samePools ? nSources : nSources + nTargets;
		
		graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		
		for (IArtifact source : CollectionUtils.join(sources)) {
			for (IArtifact target : CollectionUtils.join(targets)) {
				
				if (!allowSelfConnections && source.equals(target)) {
					continue;
				}

				switch (method) {
				case ErdosRenyi:
					if (random.nextDouble() < probability) {
						connect(source, target, ret);
					}
					break;
				case OutDegree:
					if ((int)(random.nextDouble()+probability/(nNodes-1)) == 1) {
						connect(source, target, ret);
					}
					break;
				default:
					break;
				}
				
			}
		}
		return ret;
	}

	private void connect(IArtifact source, IArtifact target, List<IObjectArtifact> ret) {
		// TODO Auto-generated method stub
		// TODO if not accepting cycles, ensure we don't create loops before adding the
		// rel
		// TODO check reciprocity if not allowed
		// ret.add(context.newRelationship(observable, name, getScale(source, target),
		// source, target, null));
		// TODO add to graph for bookkeeping unless we don't need it
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
