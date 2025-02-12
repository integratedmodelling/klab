package org.integratedmodelling.klab.components.network.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.integratedmodelling.klab.utils.Pair;
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

    private TransportType transportType = TransportType.Auto;
    private GeometryCollapser geometryCollapser = GeometryCollapser.Centroid;
    private String server;
    private IContextualizationScope scope;
    private Valhalla valhalla;
    private Graph<IObjectArtifact, MatrixEdge> graph;
    private Map<Pair<IDirectObservation, IDirectObservation>, IShape> trajectories;

    public MatrixRelationshipInstantiator() {
    /* to instantiate as expression - do not remove (or use) */}

    public MatrixRelationshipInstantiator(Parameters<Object> parameters, IContextualizationScope scope) {
        this.scope = scope;
        this.sourceArtifact = parameters.get("source", String.class);
        this.targetArtifact = parameters.get("target", String.class);
        this.timeThresholdInSeconds = parameters.get("time_limit", Double.class);
        this.distanceThresholdInKilometers = parameters.get("distance_limit", Double.class);

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
        int i = 1;
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

//        // all artifacts must be non-null and objects
//        if (!validateThatAllElementsAreObjectArtifact(sources, targets)) {
//            throw new IllegalArgumentException(
//                    "klab.networks.routing: at least one source or target artifact does not exist or is not an object artifact");
//        }
        List<double[]> sourcesCoordinates = sources.stream().map(s -> Valhalla.getCoordinates((IDirectObservation) s, geometryCollapser)).toList();
        List<double[]> targetsCoordinates = targets.stream().map(t -> Valhalla.getCoordinates((IDirectObservation) t, geometryCollapser)).toList();

        String matrixRequest = Valhalla.buildValhallaMatrixInput(sourcesCoordinates, targetsCoordinates, transportType.getType());
        ValhallaOutputDeserializer.Matrix response = valhalla.matrix(matrixRequest);

        graph = new DefaultDirectedGraph<>(MatrixEdge.class);
        for (List<PairwiseDistance> connections : response.sourcesToTargets) {
            for (PairwiseDistance connection : connections) {
                IObservation source = sources.get(connection.sourceId);
                IObservation target = targets.get(connection.targetId);

                Parameters<String> routeParameters = new Parameters<String>();
                routeParameters.put("distance", connection.distance);
                routeParameters.put("time", connection.time);
                
                graph.addVertex((IDirectObservation)source);
                graph.addVertex((IDirectObservation)target);
                graph.addEdge((IDirectObservation)source, (IDirectObservation)target,
                        new MatrixEdge(routeParameters));

                ret.add(scope.newRelationship(semantics, semantics.getName() + "_" + i, scope.getScale(), (IDirectObservation)source, (IDirectObservation)target,
                        new Metadata(routeParameters)));
                i++;
            }
        }

        return ret;
    }

    class MatrixEdge extends DefaultEdge {
        private static final long serialVersionUID = 964984629774455337L;

        Parameters<String> routeParameters;

        MatrixEdge() {
        }

        MatrixEdge(Parameters<String> rp) {
            this.routeParameters = rp;
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
