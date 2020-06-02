//package org.integratedmodelling.engine.geospace.layers;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.geotools.geometry.jts.ReferencedEnvelope;
//import org.integratedmodelling.api.configurations.ISpatialNetwork;
//import org.integratedmodelling.api.knowledge.IConcept;
//import org.integratedmodelling.api.modelling.IDirectObservation;
//import org.integratedmodelling.api.modelling.ISubject;
//import org.integratedmodelling.api.space.ISpatialExtent;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.space.IGeometricShape;
//import org.integratedmodelling.common.vocabulary.NS;
//import org.integratedmodelling.engine.geospace.literals.ShapeValue;
//import org.integratedmodelling.engine.visualization.GeometryViewer;
//import org.integratedmodelling.exceptions.KlabRuntimeException;
//import org.jgrapht.alg.DijkstraShortestPath;
//import org.jgrapht.graph.DefaultDirectedGraph;
//
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.Envelope;
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.LineString;
//import com.vividsolutions.jts.geom.MultiLineString;
//import com.vividsolutions.jts.geom.MultiPoint;
//import com.vividsolutions.jts.geom.MultiPolygon;
//import com.vividsolutions.jts.geom.Point;
//import com.vividsolutions.jts.geom.Polygon;
//import com.vividsolutions.jts.index.strtree.STRtree;
//import com.vividsolutions.jts.linearref.LinearLocation;
//import com.vividsolutions.jts.linearref.LocationIndexedLine;
//import com.vividsolutions.jts.operation.linemerge.LineMergeEdge;
//import com.vividsolutions.jts.operation.linemerge.LineMergeGraph;
//import com.vividsolutions.jts.planargraph.DirectedEdge;
//import com.vividsolutions.jts.planargraph.Node;
//
///**
// * Pseudograph is undirected and allows loops and multiple edges. Not sure this is the way
// * to go.
// * 
// * @author Ferd
// *
// */
//public class SpatialNetwork extends AbstractLayer implements ISpatialNetwork {
//
//    private static final long                      serialVersionUID = -169803355002096347L;
//
//    STRtree                                        index            = new STRtree();
//    ReferencedEnvelope                             bounds;
//    List<Geometry>                                 polygons         = new ArrayList<>();
//    List<Geometry>                                 lines            = new ArrayList<>();
//    DefaultDirectedGraph<SpatialNode, SpatialLink> graph;
//    static GeometryFactory                         gf               = new GeometryFactory();
//
//    private double                                 MAX_SEARCH_DISTANCE;
//
//    /*
//     * must use this as the original does not expose the geometry.
//     */
//    static class IndexedLine extends LocationIndexedLine {
//
//        private Geometry g;
//
//        public IndexedLine(Geometry linearGeom) {
//            super(linearGeom);
//            this.g = linearGeom;
//        }
//
//        public Geometry getGeometry() {
//            return g;
//        }
//
//    }
//
//    public SpatialNetwork(ISubject context, IConcept linkType) {
//        this.graph = new DefaultDirectedGraph<SpatialNode, SpatialLink>(SpatialLink.class);
//        this.bounds = ((ShapeValue) context.getScale().getSpace().getShape()).getEnvelope();
//        this.MAX_SEARCH_DISTANCE = bounds.getSpan(0) / 10.0;
//
//        if (NS.isRelationship(linkType)) {
//            // TODO subjects should be the nodes - very hard if noding is done later.
//        } else if (NS.isThing(linkType)) {
//            for (ISubject s : context.getSubjects()) {
//                if (!s.getObservable().getType().is(linkType)) {
//                    continue;
//                }
//                ISpatialExtent spc = s.getScale().getSpace();
//                if (spc != null) {
//                    Geometry geom = ((IGeometricShape) spc).getGeometry();
//                    geom.setUserData(s);
//                    insert(geom);
//                }
//            }
//        }
//
//        initialize();
//    }
//
//    private void initialize() {
//
//        /*
//         * build the connections to and within polygons
//         */
//        for (Geometry polygon : polygons) {
//
//            /*
//             * we assume that if we add a polygon to the network, it can be traversed. So
//             * we find all line-polygon boundary intersections and create a line for each
//             * possible connection, attributing the original object to each line. Sort of
//             * a system of airways for the interior of the polygon. Then we can construct
//             * the graph using only the noded lines.
//             */
//            Geometry polygonBoundary = polygon.getBoundary();
//            for (Geometry line : lines) {
//                Geometry intersections = polygonBoundary.intersection(line);
//                if (!intersections.isEmpty()) {
//
//                    Coordinate[] nodes = getCoordinates(intersections);
//                    /*
//                     * join all the points we found with lines and attribute the objects.
//                     * Later, the noding will simplify and clean up the graph.
//                     */
//                    for (int i = 0; i < nodes.length; i++) {
//                        for (int j = 0; j < nodes.length; j++) {
//                            if (i != j) {
//                                /*
//                                 * draw a line and attribute the polygon's data
//                                 * (technically if we're close to the edge we could use a
//                                 * path but that depends on what's moving); insert it into
//                                 * the lines array
//                                 */
//                                Geometry connection = gf
//                                        .createLineString(new Coordinate[] { nodes[i], nodes[j] });
//                                connection.setUserData(polygon);
//                                lines.add(connection);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        // node the lines
//        if (!lines.isEmpty()) {
//
//            GeometryViewer.view("Original lines", lines);
//            LineMergeGraph graph = new LineMergeGraph();
//
//            for (Geometry l : lines) {
//                for (LineString ls : explodeLines(l)) {
//                    graph.addEdge(ls);
//                }
//            }
//
//            if (true /* KLAB.CONFIG.isDebug() */) {
//
//                /*
//                 * we have a graph with all edges containing their subject. Let's view it.
//                 */
//                List<Geometry> geoms = new ArrayList<>();
//                for (Object o : graph.getEdges()) {
//                    LineMergeEdge edge = (LineMergeEdge) o;
//                    geoms.add(edge.getLine());
//                }
//                for (Object o : graph.getNodes()) {
//                    Node edge = (Node) o;
//                    geoms.add(gf.createPoint(edge.getCoordinate()));
//                }
//                GeometryViewer.view("Routing graph", geoms);
//            }
//
//            /*
//             * build network and throw away the merged graph.
//             */
//            Map<Coordinate, SpatialNode> hash = new HashMap<>();
//            for (Object o : graph.getEdges()) {
//
//                LineMergeEdge edge = (LineMergeEdge) o;
//                LineString ls = edge.getLine();
//
//                Envelope env = ls.getEnvelopeInternal();
//                if (env.isNull()) {
//                    continue;
//                }
//
//                List<Object> lineData = new ArrayList<>();
//
//                for (int i = 0; i < 2; i++) {
//                    DirectedEdge de = edge.getDirEdge(i);
//                    SpatialNode from = getNode(de.getFromNode(), hash);
//                    SpatialNode to = getNode(de.getToNode(), hash);
//                    SpatialLink link = new SpatialLink((IDirectObservation) ls.getUserData(), ls);
//                    if (this.graph.addEdge(from, to, link)) {
//                        lineData.add(link);
//                    }
//                }
//
//                /*
//                 * record each segment in the index
//                 */
//                if (!lineData.isEmpty()) {
//                    lineData.add(0, ls.getUserData());
//                    ls.setUserData(lineData);
//                    index.insert(env, new IndexedLine(ls));
//                }
//            }
//        }
//    }
//
//    private SpatialNode getNode(Node node, Map<Coordinate, SpatialNode> hash) {
//
//        if (hash.containsKey(node.getCoordinate())) {
//            return hash.get(node.getCoordinate());
//        }
//
//        SpatialNode ret = new SpatialNode(node.getCoordinate());
//        hash.put(node.getCoordinate(), ret);
//        graph.addVertex(ret);
//        return ret;
//    }
//
//    /*
//     * Split into individual linestrings if applicable. If not applicable, call an
//     * exorcist.
//     */
//    static Collection<LineString> explodeLines(Geometry lsn) {
//        List<LineString> ret = new ArrayList<>();
//        if (lsn instanceof LineString) {
//            ret.add((LineString) lsn);
//        } else if (lsn instanceof MultiLineString) {
//            for (int i = 0; i < lsn.getNumGeometries(); i++) {
//                Geometry line = lsn.getGeometryN(i);
//                ret.addAll(explodeLines(line));
//            }
//        } // TODO get lines for polygons, too
//
//        return ret;
//    }
//
//    void insert(Geometry geom) {
//
//        if (geom instanceof LineString || geom instanceof MultiLineString) {
//            lines.add(geom);
//        } else if (geom instanceof Polygon || geom instanceof MultiPolygon) {
//            polygons.add(geom);
//        } else {
//            // fucky fucky
//            throw new KlabRuntimeException("cannot add a " + geom.getClass().getCanonicalName()
//                    + " to a spatial network");
//        }
//    }
//
//    /*
//     * explode a line/polygon intersection into its intersection points.
//     */
//    private Coordinate[] getCoordinates(Geometry intersections) {
//        /*
//         * if the intersection is a line, get the midpoint coordinate; if it's a polygon,
//         * call the exorcist.
//         */
//        if (intersections instanceof Point || intersections instanceof MultiPoint) {
//            return intersections.getCoordinates();
//        } else if (intersections instanceof LineString) {
//            return new Coordinate[] {
//                    ((LineString) intersections).getCoordinateN(intersections.getCoordinates().length / 2) };
//        } else {
//            KLAB.warn("mysterious line/polygon intersection: " + intersections);
//        }
//        return new Coordinate[] {};
//    }
//
//    /**
//     * Route an object to another using the network, return trajectory. Snap object to the
//     * closest path.
//     * 
//     * @param from
//     * @param to
//     * @return
//     */
//    @Override
//    public Trajectory route(IDirectObservation from, IDirectObservation to) {
//
//        if (from.getScale().getSpace() == null || to.getScale().getSpace() == null) {
//            return null;
//        }
//
//        Point pfrom = ((IGeometricShape) from.getScale().getSpace().getShape()).getGeometry().getCentroid();
//        Point pto = ((IGeometricShape) to.getScale().getSpace().getShape()).getGeometry().getCentroid();
//
//        return route(pfrom, pto);
//    }
//
//    /**
//     * Route between two locations using the network, return trajectory. Snap locations to
//     * the closest path that works.
//     * 
//     * TODO also the start node should be chosen using the same logics.
//     * 
//     * @param from
//     * @param to
//     * @return
//     */
//    public Trajectory route(Point from, Point to) {
//
//        List<EntryPoint> starts = snap(from);
//        List<EntryPoint> ends = snap(to);
//
//        if (starts.isEmpty() || ends.isEmpty()) {
//            return null;
//        }
//
//        for (EntryPoint end : ends) {
//            for (EntryPoint start : starts) {
//
//                SpatialNode source = null;
//                SpatialNode destination = null;
//
//                List<?> sdata = start.getUserData() instanceof List
//                        ? (List<?>) start.getUserData() : null;
//                List<?> edata = end.getUserData() instanceof List
//                        ? (List<?>) end.getUserData() : null;
//
//                /*
//                 * determine direction to target and choose the nodes that go that way:
//                 */
//                if (sdata != null && sdata.size() > 1) {
//                    source = chooseEndpoint(sdata, start.getJoinPoint(), end.getJoinPoint(), true);
//                }
//                if (edata != null && edata.size() > 1) {
//                    destination = chooseEndpoint(edata, start.getJoinPoint(), end.getJoinPoint(), false);
//                }
//
//                /*
//                 * route using Dijkstra's algorithm
//                 */
//                if (source != null && destination != null) {
//                    List<SpatialLink> path = DijkstraShortestPath.findPathBetween(graph, source, destination);
//                    if (path != null && !path.isEmpty()) {
//                        return new Trajectory(this, path, start, end);
//                    }
//                }
//
//            }
//        }
//
//        return null;
//    }
//
//    /*
//     * Start node of start path should be farther from destination than end node; End node
//     * of end path should be farther from source than start node.
//     * 
//     * TODO this should return ranked alternatives, not one choice.
//     */
//    private SpatialNode chooseEndpoint(List<?> linkdata, Point first, Point last, boolean isStart) {
//
//        SpatialNode ret = null;
//        SpatialNode def = null;
//        SpatialLink link = null;
//        double distanceToMaximize = Double.NaN;
//        double distanceToTop = Double.NaN;
//        for (int i = 1; i < linkdata.size(); i++) {
//            link = (SpatialLink) linkdata.get(i);
//            SpatialNode source = graph.getEdgeSource(link);
//            SpatialNode target = graph.getEdgeTarget(link);
//            if (def == null) {
//                // take the first choice if we can't find anything better
//                def = isStart ? source : target;
//            }
//            if (isStart) {
//                distanceToMaximize = last.distance(gf.createPoint(source.location));
//                distanceToTop = last.distance(gf.createPoint(target.location));
//            } else {
//                distanceToMaximize = first.distance(gf.createPoint(target.location));
//                distanceToTop = first.distance(gf.createPoint(source.location));
//            }
//
//            if (distanceToMaximize > distanceToTop) {
//                ret = isStart ? source : target;
//            }
//        }
//        return ret == null ? def : ret;
//    }
//
//    /**
//     * Return all possible points for the location to join the network, in increasing
//     * distance order. Each result includes the snap point, the geometry of the first
//     * segment (where the snap point is), and the distance to the passed location.
//     * 
//     * @param location
//     * @return
//     */
//    public List<EntryPoint> snap(Point location) {
//
//        List<EntryPoint> ret = new ArrayList<>();
//        Coordinate pt = location.getCoordinate();
//        Envelope search = new Envelope(pt);
//        search.expandBy(MAX_SEARCH_DISTANCE);
//
//        /*
//         * Query the spatial index for objects within the search envelope. Note that this
//         * just compares the point envelope to the line envelopes so it is possible that
//         * the point is actually more distant than MAX_SEARCH_DISTANCE from a line.
//         */
//        List<LocationIndexedLine> lines = index.query(search);
//
//        // Initialize the minimum distance found to our maximum acceptable
//        // distance plus a little bit
//        double minDist = MAX_SEARCH_DISTANCE + 1.0e-6;
//        Coordinate minDistPoint = null;
//
//        for (LocationIndexedLine line : lines) {
//            LinearLocation here = line.project(pt);
//            Coordinate point = line.extractPoint(here);
//            double dist = point.distance(pt);
//            if (dist < minDist) {
//                minDist = dist;
//                minDistPoint = point;
//                ret.add(new EntryPoint(location, gf.createPoint(minDistPoint), line, here));
//            }
//        }
//
//        ret.sort(new Comparator<EntryPoint>() {
//            @Override
//            public int compare(EntryPoint o1, EntryPoint o2) {
//                return Double.compare(o1.getDistance(), o2.getDistance());
//            }
//        });
//
//        return ret;
//
//    }
//}
