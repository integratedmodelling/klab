package org.integratedmodelling.klab.components.network.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IPattern;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.owl.Concept;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.BlockCutpointGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import groovy.lang.GroovyObjectSupport;

/**
 * The base pattern is made of qualities and contains a bunch of observations,
 * used by models as they please (it can store objects as properties). It's also
 * conversant with Groovy so it can be used in expressions.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class Pattern extends GroovyObjectSupport implements IPattern {

	protected Map<String, Object> data = new HashMap<>();
	protected Collection<IObservation> observations = null;
	protected IRuntimeScope scope;
	protected IObservation emergentObservation;
	protected Metadata metadata;
	protected String name = "UNNAMED_PATTERN";
	protected IScale scale;

	private static class Edge extends DefaultEdge {
		private static final long serialVersionUID = -1371809747850681739L;
		public IRelationship relationship;

		public Edge(IRelationship relationship) {
			this.relationship = relationship;
		}
	}

	/**
	 * Create the appropriate pattern type for the passed observations. Pass groups
	 * for relationships, not individual ones, or lists of qualities.
	 * 
	 * @param observations
	 * @return
	 */
	public static Collection<Pattern> create(Collection<IObservation> observations, IConcept destination,
			IRuntimeScope scope) {

		/*
		 * should be all relationships or all qualities
		 */
		Type type = null;
		for (IObservation observation : observations) {
			Type otype = Kim.getModelableType(((Concept) observation.getObservable().getType()).getTypeSet());
			if (type == null) {
				type = otype;
			} else if (type != otype) {
				throw new KlabIllegalStateException(destination.getDefinition()
						+ ": emergent patterns cannot result from observations of different fundamental observables");
			}
		}

		List<Pattern> ret = new ArrayList<>();
		if (type == Type.RELATIONSHIP) {

			if (destination.is(Type.COUNTABLE)) {
				// a network per connected component
				Graph<IObservation, Edge> network = new DefaultDirectedGraph<IObservation, Edge>(Edge.class);
				for (IObservation observation : observations) {
					for (IArtifact artifact : observation) {
						if (artifact instanceof IRelationship) {
							IDirectObservation source = scope.getSourceSubject((IRelationship) artifact);
							IDirectObservation target = scope.getTargetSubject((IRelationship) artifact);
							network.addEdge(source, target, new Edge((IRelationship) artifact));
						}
					}
				}

				BlockCutpointGraph<IObservation, Edge> inspector = new BlockCutpointGraph<>(network);
				for (Graph<IObservation, Edge> subgraph : inspector.vertexSet()) {
					ret.add(new Network(
							new HashSet<IObservation>(subgraph.edgeSet().stream().map((e) -> e.relationship).toList()),
							scope));
				}

			} else {
				ret.add(new Network(observations, scope));
			}

		} else {

			if (destination.is(Type.COUNTABLE)) {
				throw new KlabIllegalStateException(destination.getDefinition()
						+ ": cannot make an observable pattern from qualities" );
			}

			// one quality pattern for lazy evaluation by a connected model
			ret.add(new Pattern(observations, scope));
		}
		return ret;
	}

	public Pattern(Collection<IObservation> observations, IRuntimeScope scope) {
		this.observations = observations;
		this.scope = scope;
	}

	public IObservation getEmergentObservation() {
		return emergentObservation;
	}

	public void setEmergentObservation(IObservation emergentObservation) {
		this.emergentObservation = emergentObservation;
	}

	@Override
	public Iterator<IObservation> iterator() {
		return observations.iterator();
	}

	@Override
	public void update(IObservation trigger) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getProperty(String propertyName) {
		if (this.data.containsKey(propertyName)) {
			return this.data.get(propertyName);
		}
		return super.getProperty(propertyName);
	}

	@Override
	public void setProperty(String propertyName, Object newValue) {
		this.data.put(propertyName, newValue);
	}

	@Override
	public IScale getScale(IScale embodyingScale) {
		return this.scale == null ? embodyingScale : this.scale;
	}

	@Override
	public IMetadata getMetadata() {
		return this.metadata;
	}

	@Override
	public String getName() {
		return this.name;
	}

}
