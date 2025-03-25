package org.integratedmodelling.klab.components.network.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.routing.Valhalla;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.GeometryCollapser;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaConfiguration.TransportType;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaOutputDeserializer.Matrix.PairwiseDistance;
import org.integratedmodelling.klab.components.geospace.routing.ValhallaOutputDeserializer;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabRemoteException;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class MatrixRelationshipInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {
    private String sourceArtifact = null;
    private String targetArtifact = null;

    private Double timeThresholdInSeconds = null;
    private Double distanceThresholdInKilometers = null;
    private Integer maxLocations = 1000;

    private TransportType transportType = TransportType.Auto;
    private GeometryCollapser geometryCollapser = GeometryCollapser.Centroid;
    private String server;
    private IContextualizationScope scope;
    private Valhalla valhalla;
    private Graph<IObjectArtifact, DefaultEdge> graph;

    public MatrixRelationshipInstantiator() {
    /* to instantiate as expression - do not remove (or use) */}

    public MatrixRelationshipInstantiator(Parameters<Object> parameters, IContextualizationScope scope) {
        this.scope = scope;
        this.sourceArtifact = parameters.get("source", String.class);
        this.targetArtifact = parameters.get("target", String.class);
        this.timeThresholdInSeconds = parameters.get("time_limit", Double.class);
        this.distanceThresholdInKilometers = parameters.get("distance_limit", Double.class);
        if (parameters.contains("max_locations")) {
            this.maxLocations = parameters.get("max_locations", Integer.class);
        }

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
        if (Valhalla.isServerOnline(server)) {
            this.valhalla = new Valhalla(server);
        } else {
            throw new KlabRemoteException("The server " + server + " is offline or not a valid Valhalla instance.");
        }
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {
        List<IObjectArtifact> ret = new ArrayList<>();
        
        // TODO Lo que viene aquí es algo que también se da en RoutingRelationshipInstantiator -> fusionar
        IConcept sourceConcept = Observables.INSTANCE.getRelationshipSource(semantics.getType());
        IConcept targetConcept = Observables.INSTANCE.getRelationshipTarget(semantics.getType());

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

        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        AtomicLong nConnections = new AtomicLong(0);
        int iterationSize = maxLocations/2;
        IntStream.range(0, (sources.size() + iterationSize - 1) / iterationSize)
        .mapToObj(i -> sources.subList(i * iterationSize, Math.min((i + 1) * iterationSize, sources.size())))
        .forEach(sourcesChunk -> {
            Map<double[], IObservation> sourceCoordinates = sourcesChunk.stream().collect(Collectors.toMap(s -> Valhalla.getCoordinates((IDirectObservation)s, geometryCollapser), s -> s));

            IntStream.range(0, (targets.size() + iterationSize - 1) / iterationSize)
            .mapToObj(j -> targets.subList(j * iterationSize, Math.min((j + 1) * iterationSize, targets.size())))
            .forEach(targetsChunk -> {
                Map<double[], IObservation> targetCoordinates = targetsChunk.stream().collect(Collectors.toMap(t -> Valhalla.getCoordinates((IDirectObservation)t, geometryCollapser), t -> t));
                String matrixRequest = Valhalla.buildValhallaMatrixInput(sourceCoordinates.keySet().stream().toList(), targetCoordinates.keySet().stream().toList(), transportType.getType());
                ValhallaOutputDeserializer.Matrix response = valhalla.matrix(matrixRequest);

                for (List<PairwiseDistance> connections : response.sourcesToTargets) {
                    for (PairwiseDistance connection : connections) {
                        Parameters<String> routeParameters = new Parameters<String>();
                        if (distanceThresholdInKilometers != null && connection.distance > distanceThresholdInKilometers) {
                            continue;
                        }
                        if (timeThresholdInSeconds != null && connection.time > timeThresholdInSeconds) {
                            continue;
                        }
                        routeParameters.put("distance", connection.distance);
                        routeParameters.put("time", connection.time);

                        IObservation source = sourcesChunk.get(connection.sourceId);
                        IObservation target = sourcesChunk.get(connection.targetId);
                        IShape sourceShape = source.getSpace().getShape();
                        IShape targetShape = target.getSpace().getShape();
                        graph.addVertex((IDirectObservation)source);
                        graph.addVertex((IDirectObservation)target);
                        graph.addEdge((IDirectObservation)source, (IDirectObservation)target,
                                new MatrixEdge(sourceShape, targetShape, routeParameters));

                        ret.add(scope.newRelationship(semantics, semantics.getName() + "_" + nConnections, scope.getScale(), (IDirectObservation)source, (IDirectObservation)target,
                                new Metadata(routeParameters)));
                        nConnections.incrementAndGet();
                    }
                }
            });
        });
        return ret;
    }

    class MatrixEdge extends DefaultEdge {
        private static final long serialVersionUID = 964984629774455337L;

        Parameters<String> routeParameters;
        IShape sourceShape;
        IShape targetShape;

        MatrixEdge() {
        }

        MatrixEdge(IShape s, IShape t, Parameters<String> rp) {
            this.routeParameters = rp;
            this.sourceShape = s;
            this.targetShape = t;
        }

        public Parameters<String> getParameters() {
            return this.routeParameters;
        }

    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) {
        return new MatrixRelationshipInstantiator(Parameters.create(parameters), context);
    }

}
