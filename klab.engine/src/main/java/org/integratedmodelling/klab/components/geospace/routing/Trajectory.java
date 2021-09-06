//package org.integratedmodelling.engine.geospace.layers;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.integratedmodelling.api.configurations.ITrajectory;
//import org.integratedmodelling.api.modelling.IDirectObservation;
//import org.integratedmodelling.api.space.ISpatialExtent;
//import org.integratedmodelling.common.space.IGeometricShape;
//import org.integratedmodelling.engine.geospace.Geospace;
//import org.integratedmodelling.engine.geospace.literals.ShapeValue;
//
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.Geometry;
//import org.locationtech.jts.geom.LineString;
//import org.locationtech.jts.geom.Point;
//import org.locationtech.jts.operation.union.UnaryUnionOp;
//
//public class Trajectory extends AbstractLayer implements ITrajectory {
//
//    List<SpatialLink>  path;
//    int                currentLink                 = 0;
//    double             pathProportionTraveledStart = 1;
//    double             pathProportionTraveledEnd   = 1;
//    Point              currentPoint                = null;
//    SpatialNetwork     spatialNetwork;
//    double             length                      = Double.NaN;
//    private double     distanceToStart             = 0;
//    private double     distanceFromEnd             = 0;
//    private EntryPoint entry;
//    private EntryPoint exit;
//
//    public Trajectory(IDirectObservation o1, IDirectObservation o2) {
//        // no network, just a line straight from centroid to centroid.
//        Point pfrom = ((IGeometricShape) o1.getScale().getSpace().getShape()).getGeometry().getCentroid();
//        Point pto = ((IGeometricShape) o2.getScale().getSpace().getShape()).getGeometry().getCentroid();
//        LineString line = SpatialNetwork.gf
//                .createLineString(new Coordinate[] { pfrom.getCoordinate(), pto.getCoordinate() });
//        SpatialLink link = new SpatialLink(null, line);
//        path = new ArrayList<>();
//        path.add(link);
//        currentPoint = pfrom;
//    }
//    
//    public ISpatialExtent asExtent() {
//        
//        /*
//         * merge all linestrings into a multilinestring
//         */
//        List<LineString> lineStrings = new ArrayList<>();
//        for (SpatialLink link : path) {
//            for (LineString ls : SpatialNetwork.explodeLines(link.geometry)) {
//                lineStrings.add(ls);
//            }
//        }
//        Geometry line = new UnaryUnionOp(lineStrings).union();
//
//        /*
//         * make this a shapevalue and return it as an extent
//         */
//        return new ShapeValue(line, Geospace.get().getDefaultCRS()).asExtent();
//    }
//    
//    public Trajectory(SpatialNetwork spatialNetwork, List<SpatialLink> path, EntryPoint start,
//            EntryPoint end) {
//
//        this.path = path;
//        this.spatialNetwork = spatialNetwork;
//        this.currentPoint = start.getStartingPoint();
//        this.entry = start;
//        this.exit = end;
//
//        /*
//         * cut out the path segment as necessary
//         */
//        if (path.size() == 1) {
//            // snip the only segment in two places
//            Geometry line = start.getWay().extractLine(start.getJoinLocation(), end.getJoinLocation());
//            line.setUserData(start.getUserData());
//            path.get(0).geometry = line;
//        } else if (path.size() > 0) {
//            // snip first segment from starting point
//            Geometry line = start.getWay().extractLine(start.getJoinLocation(), start.getWay().getEndIndex());
//            line.setUserData(start.getUserData());
//            path.get(0).geometry = line;
//        }
//
//        if (path.size() > 1) {
//            // snip last segment to end point
//            Geometry line = end.getWay().extractLine(end.getWay().getStartIndex(), end.getJoinLocation());
//            line.setUserData(end.getUserData());
//            path.get(path.size() - 1).geometry = line;
//        }
//
//        /*
//         * add the join paths
//         */
//        if (entry.getDistance() > 0) {
//            Geometry line = SpatialNetwork.gf
//                    .createLineString(new Coordinate[] {
//                            start.getStartingPoint().getCoordinate(),
//                            start.getJoinPoint().getCoordinate() });
//            path.add(0, new SpatialLink(null, line));
//        }
//
//        if (exit.getDistance() > 0) {
//            Geometry line = SpatialNetwork.gf
//                    .createLineString(new Coordinate[] {
//                            end.getJoinPoint().getCoordinate(),
//                            end.getStartingPoint().getCoordinate() });
//            path.add(new SpatialLink(null, line));
//        }
//
//    }
//
//    public Point getCurrentPosition() {
//        return currentPoint;
//    }
//
//    public IDirectObservation getCurrentLocation() {
//        return path.get(currentLink).subject;
//    }
//
//    public void move(double distance) {
//        // compute new position and update both link and current point
//    }
//
//    public double getDistance(Point point) {
//        // TODO
//        return 0;
//    }
//
//    /**
//     * Get the list of subjects traversed. If some links have no 
//     * subjects, the resulting list will contain nulls.
//     * 
//     * @return
//     */
//    public List<SpatialLink> getDirections() {
//        return path;
//    }
//
//    public double getRemainingDistance(Point point) {
//        // distance in m from current point to point
//        return 0;
//    }
//
//    public double getDistanceFromStartToNetwork() {
//        return distanceToStart;
//    }
//
//    public double getDistanceFromNetworkToEnd() {
//        return distanceFromEnd;
//    }
//
//    public double getLength() {
//        if (Double.isNaN(this.length)) {
//            this.length = distanceToStart + distanceFromEnd;
//            for (SpatialLink link : path) {
//                this.length += link.getLength();
//            }
//        }
//        return this.length;
//    }
//
//    @Override
//    public ITrajectory route(IDirectObservation from, IDirectObservation to) {
//        return this;
//    }
//
//}
