package org.integratedmodelling.klab.components.geospace.indexing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.api.ISpatialIndex;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.utils.Pair;

import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;
import com.slimjars.dist.gnu.trove.procedure.TIntProcedure;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.operation.distance.DistanceOp;

public class SpatialIndex implements ISpatialIndex {

    RTree                           rtree  = new RTree();
    Map<String, Integer>            ids    = new HashMap<>();
    Map<Integer, String>            names  = new HashMap<>();
    Map<String, ISpace>             exts   = new HashMap<>();
    Map<String, IDirectObservation> id2obs = new HashMap<>();
    ISpace                          extent;
    int                             nextId = 1;

    public SpatialIndex(ISpace extent) {
        this.extent = extent;
//        this.rtree.init(new Properties());
    }

	@Override
	public void add(IDirectObservation observation) {
		// TODO Auto-generated method stub
	}

	@Override
	public IPair<IDirectObservation, Double> getNearestObject(IDirectObservation obs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPair<IDirectObservation, Double>> getNear(IDirectObservation obs, int maxResults) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object distanceToNearestObjectFrom(ILocator locator, IUnit unit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double distanceToNearestObjectFrom(IDirectObservation observation, IUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

//    @Override
//    public void add(ISpace extent, String name) {
//        exts.put(name, extent);
//        rtree.add(getRectangle(extent), getId(name));
//    }
//
//    private int getId(String name) {
//        Integer ret = ids.get(name);
//        if (ret == null) {
//            ret = nextId++;
//            ids.put(name, ret);
//            names.put(ret, name);
//        }
//        return ret;
//    }
//
//    private Rectangle getRectangle(ISpace extent) {
//        // Geometry shape = ((Shape) extent.getShape()).getJTSGeometry();
//        ReferencedEnvelope env = ((Shape) extent.getShape()).getEnvelope().getJTSEnvelope();
//        return new Rectangle((float) env.getMinX(), (float) env.getMinY(), (float) env
//                .getMaxX(), (float) env.getMaxY());
//    }
//
////    @Override
//    public double distanceToNearestObjectFrom(ILocator position) {
//        return distanceToNearestObjectFrom(extent.locate(position));
//    }
//
//    @Override
//    public double distanceToNearestObjectFrom(int offset) {
//
//        double[] xy = getCoordinates(offset);
//        int id = new FeatureFinder(xy).find();
//        return getDistance(xy, exts.get(names.get(id)));
//    }
//
//    private double getDistance(double[] xy, ISpace extent) {
//
//        if (extent == null) {
//            return Double.NaN;
//        }
//
//        /*
//         * we're on the feature, distance is 0. Intersector does check for actual point on
//         * actual feature.
//         */
//        if (!new FeatureIntersector(xy).find().isEmpty()) {
//            return 0;
//        }
//
//        if (extent.getShape().getGeometryType() == Type.POINT) {
//            Point point = (Point) ((Shape) extent.getShape()).getJTSGeometry();
//            return Projection.distance(xy[0], xy[1], point.getX(), point.getY())
//                    * 1000.0;
//        }
//
//        /*
//         * complicated intersection finding between extent and extent boundary. Let JTS
//         * help.
//         */
//        Coordinate[] coords = DistanceOp.nearestPoints(Shape
//                .makePoint(xy[0], xy[1]), ((Shape) extent.getShape()).getJTSGeometry());
//
//        if (coords == null || coords.length < 2) {
//            return Double.NaN;
//        }
//
//        return Projection.distance(coords[0].x, coords[0].y, coords[1].x, coords[1].y) * 1000.0;
//    }
//
////    @Override
//    public Collection<Pair<String, ISpace>> getNearest(ILocator position, int maxResults) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    double[] getCoordinates(int ofs) {
//        ISpace loc = extent.getExtent(ofs);
//        Shape sh = Shape.create(((Shape) loc.getShape()).getStandardizedGeometry(), Projection.getDefault());
//        Point point = ((Point) sh.getCentroid().getJTSGeometry());
//        return new double[] { point.getX(), point.getY() };
//    }
//
//    @Override
//    public ISpace getExtent() {
//        return extent;
//    }
//
//    @Override
//    public double distanceBetween(int offset, String objectId) {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    @Override
//    public boolean contains(String objectId) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
////    @Override
//    public double distanceBetween(ILocator position, String objectId) {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    class FeatureFinder {
//
//        int                                idx;
//        private com.infomatiq.jsi.Point point;
//
//        FeatureFinder(double[] xy) {
//            this.point = new com.infomatiq.jsi.Point((float) xy[0], (float) xy[1]);
//        }
//
//        int find() {
//
//            rtree.nearest(point, new TIntProcedure() {
//
//                @Override
//                public boolean execute(int arg0) {
//                    idx = arg0;
//                    return true;
//                }
//            }, Float.POSITIVE_INFINITY);
//
//            return idx;
//        }
//    }
//
//    class FeatureIntersector {
//
//        Set<Integer>                       idx = new HashSet<>();
//        private com.infomatiq.jsi.Point point;
//
//        FeatureIntersector(double[] xy) {
//            this.point = new com.infomatiq.jsi.Point((float) xy[0], (float) xy[1]);
//        }
//
//        Set<Integer> find() {
//
//            rtree.intersects(new Rectangle(point.x, point.y, point.x, point.y), new TIntProcedure() {
//
//                @Override
//                public boolean execute(int arg0) {
//                    if (Shape.makePoint(point.x, point.y)
//                            .overlaps(((Shape) exts.get(names.get(arg0)).getShape())
//                                    .getJTSGeometry())) {
//                        idx.add(arg0);
//                    }
//                    return true;
//                }
//            });
//
//            return idx;
//        }
//    }
//
//    @Override
//    public int size() {
//        return ids.size();
//    }
//
//    @Override
//    public void add(IDirectObservation observation) {
//        if (observation.getScale().getSpace() != null) {
//            add(observation.getScale().getSpace(), ((Observation) observation).getInternalId());
//        }
//        id2obs.put(((Observation) observation).getInternalId(), observation);
//    }
//
//    @Override
//    public Pair<IDirectObservation, Double> getNearestObject(IDirectObservation obs) {
//        List<Pair<IDirectObservation, Double>> dist = getNear(obs);
//        return dist.isEmpty() ? null : dist.get(0);
//    }
//
//    @Override
//    public Pair<IDirectObservation, Double> getNearestObject(ILocator sfs) {
//        List<Pair<IDirectObservation, Double>> dist = getNear(sfs);
//        return dist.isEmpty() ? null : dist.get(0);
//    }
//
//    @Override
//    public List<Pair<IDirectObservation, Double>> getNear(IDirectObservation obs) {
//        if (obs.getScale().getSpace() != null) {
//            Point centroid = ((Shape) obs.getScale().getSpace().getShape()).getJTSGeometry()
//                    .getCentroid();
//            return getNear(SpaceLocator.get(centroid.getY(), centroid.getX()), obs);
//        }
//        return new ArrayList<>();
//    }
//
//    @Override
//    public List<Pair<IDirectObservation, Double>> getNear(ILocator sfs) {
//        return getNear(sfs, null);
//    }
//
//    private List<Pair<IDirectObservation, Double>> getNear(ILocator sfs, IDirectObservation obs) {
//
//        List<Pair<IDirectObservation, Double>> ret = new ArrayList<>();
//
//        if (!((SpaceLocator) sfs).isLatLon()) {
//            // for now fuck, later convert using extent.getGrid()
//            throw new KlabRuntimeException("unimplemented conversion from grid coordinates to latlon in spatial index");
//        }
//
//        for (IDirectObservation o : id2obs.values()) {
//
//            // don't add the one we're measuring from if it's defined.
//            if (obs != null && o.equals(obs)) {
//                continue;
//            }
//            double dist = getDistance(new double[] { ((SpaceLocator) sfs).lat, ((SpaceLocator) sfs).lon }, o
//                    .getScale().getSpace());
//            if (!Double.isNaN(dist)) {
//                ret.add(new Pair<>(o, dist));
//            }
//        }
//
//        if (!ret.isEmpty()) {
//            ret.sort(new Comparator<Pair<IDirectObservation, Double>>() {
//                @Override
//                public int compare(Pair<IDirectObservation, Double> o1, Pair<IDirectObservation, Double> o2) {
//                    return Double.compare(o1.getSecond(), o2.getSecond());
//                }
//            });
//        }
//
//        return ret;
//    }
}
