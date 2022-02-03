package org.integratedmodelling.klab.components.network.algorithms;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.linearref.LengthIndexedLine;

/**
 * Euclidean pathfinder.
 * 
 * @author Gilles Vuidel
 */
public class EuclidePathFinder implements SpacePathFinder {

    protected Project project;

    /**
     * Creates a new Euclidean pathfinder
     * @param project the current project
     */
	public EuclidePathFinder(Project project) {
        this.project = project;
    }

    @Override
    public List<double[]> calcPaths(Coordinate p, List<Coordinate> dests) {
        List<double[]> distances = new ArrayList<>();
        for(Coordinate dest : dests) {
            double d = p.distance(dest);
            distances.add(new double[]{d, d});
        }
        return distances;
    }
    
    @Override
    public List<double[]> calcPathsInsidePatch(Coordinate p, List<Coordinate> dests) {
        return calcPaths(p, dests);
    }
    
    @Override
    public HashMap<DefaultFeature, Path> calcPaths(Coordinate p, double maxCost, boolean realPath) {
        return calcPaths(new GeometryFactory().createPoint(p), maxCost, realPath);
    }
    
    @Override
    public HashMap<DefaultFeature, Path> calcPaths(Geometry geom, double maxCost, boolean realPath) {
        Collection<DefaultFeature> nearPatches = project.getPatches();
        if(maxCost > 0) {
            Envelope env = new Envelope(geom.getEnvelopeInternal());
            env.expandBy(maxCost);
            nearPatches = (List<DefaultFeature>)project.getPatchIndex().query(env);
        }
       
        DefaultFeature geomPatch = new DefaultFeature(geom.getCentroid().getCoordinate().toString(), geom);
        HashMap<DefaultFeature, Path> paths = new HashMap<>();
        for(DefaultFeature patch : nearPatches) {
            double d = patch.getGeometry().distance(geom);
            if(maxCost == 0 || d <= maxCost) {
                if(realPath) {
                    paths.put(patch, createPath(geomPatch, patch));
                } else {
                    paths.put(patch, new Path(geomPatch, patch, d, d));
                }
            }
        }
        
        return paths;
    }

    @Override
    public double[] calcPathNearestPatch(Point p) {
        Feature patch = nearestPatch(p);
        double dist = p.distance(patch.getGeometry());
        return new double[] {((Number)patch.getId()).doubleValue(), dist, dist};
    }

    private DefaultFeature nearestPatch(Point p) {
        DefaultFeature nearestPatch = null;
        double dist = project.getResolution();
        double min = Double.MAX_VALUE;
        while(min == Double.MAX_VALUE) {
            dist *= 2;
            Envelope env = new Envelope(p.getCoordinate());
            env.expandBy(dist);
            List items = project.getPatchIndex().query(env);
            for(Object item : items) {
                DefaultFeature patch = (DefaultFeature) item;
                double d = patch.getGeometry().distance(p);
                if(d < min && d <= dist) {
                    min = d;
                    nearestPatch = patch;
                }

            }
        }
        return nearestPatch;
    }
    
    /**
     * Creates a path between two patches for euclidean linkset.
     * Calculates the shortest straight line between the two patches
     * @param patch1 a patch
     * @param patch2 another patch
     * @return a path connecting the two patches with the shortest straight line
     */
    public Path createPath(Feature patch1, Feature patch2) {
        Geometry g1 = patch1.getGeometry();
        Geometry g2 = patch2.getGeometry();
        LineString path;
        if(g1 instanceof Point || g2 instanceof Point) {
            if(g1 instanceof Point && g2 instanceof Point) {
                path = g1.getFactory().createLineString(new Coordinate[] {
                    g1.getCoordinate(), g2.getCoordinate()});
            } else {
                Geometry g = g1 instanceof Point ? g2 : g1;
                Point p = (Point) (g1 instanceof Point ? g1 : g2);
                LengthIndexedLine linearRef = new LengthIndexedLine(g.getBoundary());
                Coordinate c = linearRef.extractPoint(linearRef.project(p.getCoordinate()));
                path = g1.getFactory().createLineString(new Coordinate[] {
                    p.getCoordinate(), c});
            }
        } else {
            Coordinate [] coords = DistanceOp.nearestPoints(g1, g2);    
            path = g1.getFactory().createLineString(coords);
        }

        double dist = path.getLength();
        return new Path(patch1, patch2, dist, path);
    }
}
