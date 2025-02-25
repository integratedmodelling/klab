package org.integratedmodelling.klab.components.network.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
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
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.indexing.DistanceCalculator;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;
import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class ConfigurableRelationshipInstantiator extends AbstractContextualizer implements IExpression, IInstantiator {

    private String sourceArtifact = null;
    private String targetArtifact = null;
    private Random random = new Random();
    private double probability = 0.01;
    private boolean allowSelfConnections;
    private boolean allowReciprocal;
    private boolean allowCycles;
    Descriptor selectorDescriptor = null;

    class SpatialEdge extends DefaultEdge {

        private static final long serialVersionUID = -6448417928592670704L;

        IShape sourceShape;
        IShape targetShape;

        SpatialEdge() {
        }

        SpatialEdge(IShape s, IShape t) {
            this.sourceShape = s;
            this.targetShape = t;
        }

    }

    static enum Method {
        ErdosRenyi, OutDegree, Closest
        // TODO add others - small world particularly useful, others not sure
    }

    static enum SpaceType {
        Default, None, Line, LineCentroid, LineEdge, ConvexHull, SpaceBetween
    }

    private Method method = Method.ErdosRenyi;
    private SpaceType spaceType = SpaceType.Default;
    private Graph<IObjectArtifact, DefaultEdge> graph;
    private IContextualizationScope scope;
    private DistanceCalculator distanceCalculator;

    public ConfigurableRelationshipInstantiator() {
        /* to instantiate as expression - do not remove (or use) */}

    public ConfigurableRelationshipInstantiator(IParameters<String> parameters, IContextualizationScope scope) {

        this.scope = scope;
        this.sourceArtifact = parameters.get("source", String.class);
        this.targetArtifact = parameters.get("target", String.class);
        this.allowSelfConnections = parameters.get("selfconnections", Boolean.FALSE);
        this.allowReciprocal = parameters.get("reciprocal", Boolean.FALSE);
        this.allowCycles = parameters.get("cycles", Boolean.TRUE);
        boolean exhaustive = false;

        if (parameters.containsKey("select")) {
            Object expression = parameters.get("select");
            boolean forceScalar = false;
            if (expression instanceof IKimExpression) {
                forceScalar = ((IKimExpression) expression).isForcedScalar();
                expression = ((IKimExpression) expression).getCode();
            }
            this.selectorDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE).describe(
                    expression.toString(), scope.getExpressionContext().scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded));
        }

        if (parameters.contains("seed")) {
            random.setSeed(parameters.get("seed", Number.class).longValue());
        }
        if (parameters.contains("method")) {
            this.method = Method.valueOf(Utils.removePrefix(parameters.get("method", String.class)));
            if (this.method == Method.Closest/* TODO other spatial random methods */ && scope.getScale().getSpace() == null) {
                throw new IllegalStateException("Cannot instantiate spatial relationships in a non-spatial context");
            }
            if (this.method == Method.Closest) {
                exhaustive = true;
            }
        }
        if (parameters.contains("space")) {
            this.spaceType = SpaceType.valueOf(Utils.removePrefix(parameters.get("space", String.class)));
        }

        this.probability = parameters.get("p", (exhaustive || parameters.containsKey("select")) ? 1.0 : 0.01);

    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {

        // force this if we are instantiating bonds
        if (semantics.getType().is(IKimConcept.Type.BIDIRECTIONAL)) {
            allowReciprocal = false;
        }

        /**
         * We make a new one at each instantiation. TODO if this becomes part of a process, tie to
         * the change of the target artifact(s).
         */
        this.distanceCalculator = null;

        IConcept sourceConcept = Observables.INSTANCE.getRelationshipSource(semantics.getType());
        IConcept targetConcept = Observables.INSTANCE.getRelationshipTarget(semantics.getType());

        /*
         * recover artifacts according to parameterization or lack thereof. Source and target
         * artifacts may be the same artifact.
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
        for (List<?> co : new List[]{sources, targets}) {
            for (Object o : co) {
                if (!(o instanceof IObjectArtifact)) {
                    throw new IllegalArgumentException(
                            "klab.networks.random: at least one source or target artifact does not exist or is not an object artifact");
                }
            }
        }

        ILanguageExpression selector = null;
        Parameters<String> parameters = new Parameters<>();
        if (selectorDescriptor != null) {
            selector = selectorDescriptor.compile();
        }

        boolean samePools = (sourceArtifact != null && targetArtifact != null && sourceArtifact.equals(targetArtifact))
                || (sourceArtifact == null && targetArtifact == null && sourceConcept.equals(targetConcept));

        // TODO these are the simple methods - enable others separately
        Collection<IObservation> allSources = CollectionUtils.joinObservations(sources);
        Collection<IObservation> allTargets = CollectionUtils.joinObservations(targets);
        int nSources = allSources.size();
        int nTargets = allTargets.size();
        int nNodes = samePools ? nSources : nSources + nTargets;

        graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        Set<IObservation> connected = new HashSet<>();

        for (IObservation source : allSources) {

            if (context.getMonitor().isInterrupted()) {
                break;
            }
            
            if (connected.contains(source)) {
                continue;
            }

            if (method == Method.Closest) {

                if (this.distanceCalculator == null) {
                    this.distanceCalculator = new DistanceCalculator(scope.getScale().getSpace(), nTargets);
                    for (IArtifact target : allTargets) {

                        if (context.getMonitor().isInterrupted()) {
                            break;
                        }

                        this.distanceCalculator.add((IDirectObservation) target);
                    }
                }

                Pair<Shape, IDirectObservation> closest = findClosest(source, allTargets);
                if (closest != null) {
                    connect((IDirectObservation) source, closest.getSecond(), closest.getFirst());
                    connected.add(closest.getSecond());
                }

            } else {

                for (IArtifact target : allTargets) {

                    if (context.getMonitor().isInterrupted()) {
                        break;
                    }

                    if (!allowSelfConnections && source.equals(target)) {
                        continue;
                    }

                    if (!(source instanceof IDirectObservation)) {
                        throw new IllegalArgumentException("source observations are not direct observations");
                    }

                    if (!(target instanceof IDirectObservation)) {
                        throw new IllegalArgumentException("target observations are not direct observations");
                    }

                    if (selector != null) {

                        Object o = selector.eval(context, parameters, "source", source, "target", target);

                        if (o == null) {
                            o = Boolean.FALSE;
                        }
                        if (!(o instanceof Boolean)) {
                            throw new KlabValidationException(
                                    "relationship instantiator: selector expression must return true/false");
                        }

                        if (!(Boolean) o) {
                            continue;
                        }
                    }

                    if (probability == 1) {
                        connect((IDirectObservation) source, (IDirectObservation) target, null);
                        connected.add((IObservation) target);
                    } else {
                        switch(method) {
                        case ErdosRenyi:
                            if (random.nextDouble() < probability) {
                                connect((IDirectObservation) source, (IDirectObservation) target, null);
                                connected.add((IObservation) target);
                            }
                            break;
                        case OutDegree:
                            if ((int) (random.nextDouble() + probability / (nNodes - 1)) == 1) {
                                connect((IDirectObservation) source, (IDirectObservation) target, null);
                                connected.add((IObservation) target);
                            }
                            break;
                        default:
                            break;
                        }
                    }
                }
            }
        }

        context.getMonitor()
                .info("creating " + graph.edgeSet().size() + " " + Concepts.INSTANCE.getDisplayName(semantics.getType())
                        + " relationships [" + (allowCycles ? "" : "no ") + "cycles, " + (allowSelfConnections ? "" : "no ")
                        + "self connections]");

        return instantiateRelationships(semantics);
    }

    private Pair<Shape, IDirectObservation> findClosest(IObservation source, Collection<IObservation> targets) {

        ISpace sspace = source.getScale().getSpace();
        if (sspace != null) {
            double[] xy = this.distanceCalculator.getNearestPoint(sspace.getShape().getCenter(false));
            if (xy != null) {
                for (IObservation target : targets) {
                    // ehm - the closest is apparently itself.
                    if (target.equals(source)) {
                        continue;
                    }
                    ISpace tspace = target.getScale().getSpace();
                    if (tspace != null) {
                        if (tspace.getDimensionality() < 2 || tspace.getShape().contains(xy)) {
                            return new Pair<Shape, IDirectObservation>(
                                    Shape.create(xy[0], xy[1], (Projection) tspace.getShape().getProjection()),
                                    (IDirectObservation) target);
                        }
                    }
                }
            }
        }
        return null;
    }

    private List<IObjectArtifact> instantiateRelationships(IObservable observable) {

        int i = 1;
        List<IObjectArtifact> ret = new ArrayList<>();
        // build from graph
        for (DefaultEdge edge : graph.edgeSet()) {
            IDirectObservation source = (IDirectObservation) graph.getEdgeSource(edge);
            IDirectObservation target = (IDirectObservation) graph.getEdgeTarget(edge);
            IScale scale = getScale(source, target, edge instanceof SpatialEdge ? ((SpatialEdge) edge).targetShape : null);
            // TODO find a better way of setting the metadata value
            Metadata metadata = new Metadata();
            metadata.put("cost", 1.0);
            ret.add(scope.newRelationship(observable, observable.getName() + "_" + i, scale, source, target, metadata));
            i++;
        }
        return ret;
    }

    private IScale getScale(IDirectObservation source, IDirectObservation target, IShape connectionPoint) {

        SpaceType spt = spaceType;
        IShape targetShape = connectionPoint == null
                ? (target.getSpace() == null ? null : target.getSpace().getShape())
                : connectionPoint;

        if (spt == SpaceType.Default) {
            spt = (source.getSpace() != null && targetShape != null)
                    ? ((source.getSpace().getDimensionality() > 1 && targetShape.getDimensionality() > 1)
                            ? SpaceType.ConvexHull
                            : SpaceType.LineEdge)
                    : SpaceType.None;
        }

        Shape shape = null;

        if (spt != SpaceType.None) {

            IShape ss = source.getSpace().getShape();

            switch(spt) {
            case ConvexHull:
                shape = Shape.join(ss, targetShape, IShape.Type.POLYGON, true);
                break;
            case SpaceBetween:
                shape = Shape.join(ss, targetShape, IShape.Type.POLYGON, false);
                break;
            case Line:
            case LineCentroid:
                shape = Shape.join(ss, targetShape, IShape.Type.LINESTRING, true);
                break;
            case LineEdge:
                shape = Shape.join(ss, targetShape, IShape.Type.LINESTRING, false);
                break;
            default:
                break;
            }
        }

        return shape == null
                ? ((Scale) scope.getScale()).removeExtent(IGeometry.Dimension.Type.SPACE)
                : Scale.substituteExtent(scope.getScale(), shape);
    }

    private void connect(IDirectObservation source, IDirectObservation target, ISpace spatialConnection) {

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
        graph.addEdge(source, target, new SpatialEdge(null, spatialConnection == null ? null : spatialConnection.getShape()));
    }

    /*
     * ACHTUNG: this assumes that the current graph has no cycles as it's only called if we don't
     * accept cycles, so it's not a general solution.
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
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new ConfigurableRelationshipInstantiator(Parameters.create(parameters), context);
    }

    @Override
    public Type getType() {
        return Type.OBJECT;
    }

}
