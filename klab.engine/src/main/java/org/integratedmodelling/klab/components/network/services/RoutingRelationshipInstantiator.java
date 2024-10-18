package org.integratedmodelling.klab.components.network.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.geotools.geometry.jts.GeometryBuilder;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.routing.Valhalla;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.GeometryCollapser;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.TransportType;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaException;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaOutputDeserializer;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabRemoteException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.locationtech.jts.geom.Geometry;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.jgrapht.Graph;

public class RoutingRelationshipInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {
	private String sourceArtifact = null;
	private String targetArtifact = null;

	private Double timeThreshold = null;
    private Double distanceThreshold = null;

	private TransportType transportType = TransportType.Auto;
	private GeometryCollapser geometryCollapser = GeometryCollapser.Centroid;
	private String server;
	private IContextualizationScope scope;
	private Valhalla valhalla;
	private Graph<IObjectArtifact, SpatialEdge> graph;
	private Map<Pair<IDirectObservation, IDirectObservation>, IShape> trajectories;

	static enum CostingOptions {
		/*
		 * Empty for the time being, it is maybe too much unneeded detail. To be
		 * developed as needed.
		 */
	}
	
	public RoutingRelationshipInstantiator() {
		/* to instantiate as expression - do not remove (or use) */}

    private boolean isValhallaServerOnline(String server) {
        HttpResponse<JsonNode> response;
        try {
            response = Unirest.get(server + "/status").asJson();
        } catch (Exception e) {
            throw new KlabRemoteException("Cannot access Valhalla server. Reason: " + e.getMessage());
        }
        if (response.getStatus() != 200) {
            return false;
        }
        return true;
    }
	
	public RoutingRelationshipInstantiator(IParameters<String> parameters, IContextualizationScope scope) {
		this.scope = scope;
		this.sourceArtifact = parameters.get("source", String.class);
		this.targetArtifact = parameters.get("target", String.class);
		this.timeThreshold = parameters.get("time_limit", Double.class);
		this.distanceThreshold = parameters.get("distance_limit", Double.class);
		
		if (parameters.containsKey("transport")) {
			this.transportType = TransportType.fromValue(Utils.removePrefix(parameters.get("transport", String.class)));
		}
		if (parameters.containsKey("collapse_geometry")) {
			this.geometryCollapser = GeometryCollapser
					.fromValue(Utils.removePrefix(parameters.get("collapse_geometry", String.class)));
		}
		if (parameters.get("server") == null || parameters.get("server", String.class).trim().isEmpty()) {
			throw new KlabIllegalArgumentException("The server for Valhalla has not been defined.");
		}
		this.server = parameters.get("server", String.class);
		if (isValhallaServerOnline(server)) {
			this.valhalla = new Valhalla(server);
		} else {
			throw new KlabRemoteException("The server " + server + " is offline or not a valid Valhalla instance.");
		}
	}
	
    private boolean validateThatAllElementsAreObjectArtifact(List<IObservation> sources, List<IObservation> targets) {
        return Stream.concat(sources.parallelStream(), targets.parallelStream()).allMatch(element -> element instanceof IObjectArtifact);
    }

	/*
	 * This is an adapted copy of the instantiate method of the configurable
	 * relationship instantiator.
	 */
	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context)
			throws KlabException {
		IConcept sourceConcept = Observables.INSTANCE.getRelationshipSource(semantics.getType());
		IConcept targetConcept = Observables.INSTANCE.getRelationshipTarget(semantics.getType());

		/*
		 * recover artifacts according to parameterization or lack thereof. Source and
		 * target artifacts may be the same artifact.
		 */
		List<IObservation> sources = new ArrayList<>();
		if (sourceArtifact == null) {
			sources.addAll(context.getObservations(sourceConcept));
		} else {
			IArtifact src = context.getArtifact(sourceArtifact);
			if (src instanceof IObservationGroup) {
				for (IArtifact a : src) {
					sources.add((IObservation) a);
				}
			}
		}

		List<IObservation> targets = new ArrayList<>();
		if (targetArtifact == null) {
			targets.addAll(context.getObservations(targetConcept));
		} else {
			IArtifact src = context.getArtifact(targetArtifact);
			if (src instanceof IObservationGroup) {
				for (IArtifact a : src) {
					targets.add((IObservation) a);
				}
			}
		}

        // all artifacts must be non-null and objects
        if (!validateThatAllElementsAreObjectArtifact(sources, targets)) {
            throw new IllegalArgumentException(
                    "klab.networks.routing: at least one source or target artifact does not exist or is not an object artifact");
        }

		graph = new DefaultDirectedGraph<>(SpatialEdge.class);
		trajectories = new HashMap<>();

        connectSourcesToTargets(context, sources, targets);

        Logging.INSTANCE.info("Creating " + graph.edgeSet().size() + " "
                + Concepts.INSTANCE.getDisplayName(semantics.getType()) + " routing relationships.");
		return instantiateRelationships(semantics);
	}
	
    private boolean connectSourceToTarget(IArtifact source, IArtifact target) {
        // A direct connection of an instance to itself in the context of routing makes no sense and is thus avoided.
        // Note that closed paths are nevertheless possible.
        if (source.equals(target)) {
            return false;
        }
        // Find the optimal route between target and location.
        double[] sourceCoordinates = Valhalla.getCoordinates((IDirectObservation) source, geometryCollapser);
        double[] targetCoordinates = Valhalla.getCoordinates((IDirectObservation) target, geometryCollapser);
        String valhallaInput = Valhalla.buildValhallaJsonInput(sourceCoordinates, targetCoordinates, transportType.getType());
        ValhallaOutputDeserializer.OptimizedRoute route;
        try {
            route = valhalla.optimized_route(valhallaInput);
        } catch (ValhallaException e) {
            return false;
        }

        IShape trajectory = route.getPath().transform(scope.getScale().getSpace().getProjection());
        Map<String, Object> stats = route.getSummaryStatistics();
        if (!isRouteInsideTheThresholds(stats)) {
            return false;
        }

        Parameters<String> routeParameters = new Parameters<String>(stats);
        connect((IDirectObservation) source, (IDirectObservation) target, trajectory, routeParameters);
        trajectories.put(new Pair<IDirectObservation, IDirectObservation>((IDirectObservation) source, (IDirectObservation) target), trajectory);
        return true;
    }

    private Geometry getIsochrone(IDirectObservation node, boolean isReverse) {
        double[] coordinates = Valhalla.getCoordinates(node, geometryCollapser);
        String isochroneRequest = Valhalla.buildValhallaIsochroneInput(coordinates, transportType.getType(), "time", timeThreshold, isReverse);
        return valhalla.isochrone(isochroneRequest);
    }

    private void connectSourcesToTargets(IContextualizationScope context, List<IObservation> sources, List<IObservation> targets) {
        Set<IObservation> connected = new HashSet<>();
        // If there are less targets than sources, the optimal way of using isochrones is by defining reverse isochrones from the targets and exclude the sources outside the polygon
        boolean useReverseIsochrones = targets.size() < sources.size();
        boolean iterateOverSourcesFirst = !useReverseIsochrones;
        List<IObservation> firstNodes = iterateOverSourcesFirst ? sources : targets;
        List<IObservation> secondNodes = iterateOverSourcesFirst ? targets : sources;
        for (IObservation node1 : firstNodes) {
            if (iterateOverSourcesFirst && connected.contains(node1)) {
                continue;
            }
            // Filter those nodes that are not inside the range
            Geometry isochrone = getIsochrone((IDirectObservation) node1, useReverseIsochrones);
            List<IObservation> inRangeNodes = filterNodesInRange(secondNodes, isochrone);
            for(IArtifact node2 : inRangeNodes) {
                if (context.getMonitor().isInterrupted()) {
                    return;
                }
                boolean isConnected = iterateOverSourcesFirst ? connectSourceToTarget(node1, node2) : connectSourceToTarget(node2, node1);
                if (isConnected) {
                    if (iterateOverSourcesFirst) {
                        connected.add((IObservation) node1);

                    }
                    connected.add((IObservation) node2);
                }
            }
        }
    }

    private List<IObservation> filterNodesInRange(List<IObservation> nodes, Geometry isochrone) {
        return nodes.stream().filter( t -> {
            double[] coordinates = Valhalla.getCoordinates((IDirectObservation) t, geometryCollapser);
            Geometry point = new GeometryBuilder().point(coordinates[0], coordinates[1]);
            return isochrone.intersects(point);
        }).toList();
    }

    private boolean isRouteInsideTheThresholds(Map<String, Object> stats) {
        // We define the time using minutes, but receive the stats in seconds
        return (timeThreshold == null || ((Double) stats.get("time") < timeThreshold * 60))
                && (distanceThreshold == null || ((Double) stats.get("length") < distanceThreshold));
    }

	private List<IObjectArtifact> instantiateRelationships(IObservable observable) {
		int i = 1;
		List<IObjectArtifact> ret = new ArrayList<>();
		// build from graph
		for (SpatialEdge edge : graph.edgeSet()) {

			IDirectObservation source = (IDirectObservation) graph.getEdgeSource(edge);
			IDirectObservation target = (IDirectObservation) graph.getEdgeTarget(edge);
			Parameters<String> routeParameters = edge.getParameters();

			IScale scale = Scale.substituteExtent(scope.getScale(),
					trajectories.get(new Pair<IDirectObservation, IDirectObservation>((IDirectObservation) source,
							(IDirectObservation) target)));

			ret.add(scope.newRelationship(observable, observable.getName() + "_" + i, scale, source, target,
					new Metadata(routeParameters)));

			i++;
		}
		return ret;
	}
	
	private void connect(IDirectObservation source, IDirectObservation target, ISpace spatialConnection,
			Parameters<String> routeParameters) {
		// add to graph for bookkeeping unless we don't need it
		graph.addVertex(source);
		graph.addVertex(target);
		graph.addEdge(source, target, new SpatialEdge(null,
				spatialConnection == null ? null : spatialConnection.getShape(), routeParameters));
	}
	
	class SpatialEdge extends DefaultEdge {
		private static final long serialVersionUID = -6448417928592670704L;

		IShape sourceShape;
		IShape targetShape;

		Parameters<String> routeParameters;

		SpatialEdge() {
		}

		SpatialEdge(IShape s, IShape t, Parameters<String> rp) {
			this.sourceShape = s;
			this.targetShape = t;
			this.routeParameters = rp;
		}

		public Parameters<String> getParameters() {
			return this.routeParameters;
		}

	}
	
	
	@Override
	public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
		return new RoutingRelationshipInstantiator(Parameters.create(parameters), context);
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

}
