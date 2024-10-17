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
	
    /*
     * Calculates the Euclidean distance between source and target. Returns true if there is no distance threshold or the Euclidean distance is under the limit.
     */
    private boolean isRouteInsideDistanceThreshold(double[] sourceCoordinates, double[] targetCoordinates) {
        if (distanceThreshold == null) {
            return true;
        }
        double euclideanDistance = Math.sqrt(Math.pow(sourceCoordinates[0] - targetCoordinates[0], 2)
                + Math.pow(sourceCoordinates[1] - targetCoordinates[1], 2));
        return euclideanDistance <= distanceThreshold ;
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

        connectSourceToTarget(context, sources, targets);

        Logging.INSTANCE.info("Creating " + graph.edgeSet().size() + " "
                + Concepts.INSTANCE.getDisplayName(semantics.getType()) + " routing relationships.");
		return instantiateRelationships(semantics);
	}
	
    private void connectSourceToTarget(IContextualizationScope context, List<IObservation> sources, List<IObservation> targets) {
        Set<IObservation> connected = new HashSet<>();
        //TODO make the filter use this
        boolean useReverse = sources.size() < targets.size();
        for(IObservation source : sources) {
            if (connected.contains(source)) {
                continue;
            }
            // Lambda-filter those that are not inside the range
            double[] coordinates = Valhalla.getCoordinates((IDirectObservation) source, geometryCollapser);
            String isochroneRequest = Valhalla.buildValhallaIsochroneInput(coordinates, transportType.getType(), "time", timeThreshold, useReverse);
            Geometry isochrone = valhalla.isochrone(isochroneRequest);
            List<IObservation> inRangeTargets = targets.stream().filter( t -> {
                double[] targetCoordinates = Valhalla.getCoordinates((IDirectObservation) t, geometryCollapser);
                Geometry coords = new GeometryBuilder().point(targetCoordinates[0], targetCoordinates[1]);
                return isochrone.intersects(coords);
            }).toList();
            for(IArtifact target : inRangeTargets) {
                if (context.getMonitor().isInterrupted()) {
                    return;
                }

                // A direct connection of an instance to itself in the context of routing makes
                // no sense and is thus avoided.
                // Note that closed paths are nevertheless possible.
                if (source.equals(target)) {
                    continue;
                }

                // Avoid calling to Valhalla if we already know that the route is too far away
                double[] sourceCoordinates = Valhalla.getCoordinates((IDirectObservation) source, geometryCollapser);
                double[] targetCoordinates = Valhalla.getCoordinates((IDirectObservation) target, geometryCollapser);
                boolean doesRouteFitInThreshold = isRouteInsideDistanceThreshold(sourceCoordinates, targetCoordinates);
                if (!doesRouteFitInThreshold) {
                    continue;
                }

                // Find the optimal route between target and location.
                String valhallaInput = Valhalla.buildValhallaJsonInput(sourceCoordinates, targetCoordinates, transportType.getType());
                ValhallaOutputDeserializer.OptimizedRoute route;
                IShape trajectory;
                Map<String, Object> stats;
                Parameters<String> routeParameters = null;
                try {
                    route = valhalla.optimized_route(valhallaInput);
                    trajectory = route.getPath().transform(scope.getScale().getSpace().getProjection());
                    stats = route.getSummaryStatistics();
                    routeParameters = new Parameters<String>(stats);
                } catch (ValhallaException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    route = null;
                    stats = null;
                    trajectory = null;
                }

                if (trajectory == null || stats == null) {
                    continue;
                }
                if ((timeThreshold == null || ((Double) stats.get("time") < timeThreshold))
                        && (distanceThreshold == null || ((Double) stats.get("length") < distanceThreshold))) {
                    connect((IDirectObservation) source, (IDirectObservation) target, trajectory, routeParameters);
                    connected.add((IObservation) target);
                    trajectories.put(new Pair<IDirectObservation, IDirectObservation>((IDirectObservation) source,
                            (IDirectObservation) target), trajectory);
                }
            }
        }
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
