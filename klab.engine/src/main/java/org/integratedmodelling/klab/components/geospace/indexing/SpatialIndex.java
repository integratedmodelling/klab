package org.integratedmodelling.klab.components.geospace.indexing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.api.ISpatialIndex;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;

import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;
import com.slimjars.dist.gnu.trove.procedure.TIntProcedure;
import org.locationtech.jts.geom.Geometry;

public class SpatialIndex implements ISpatialIndex {

	RTree rtree = new RTree();
	Map<String, Integer> ids = new HashMap<>();
	Map<Integer, String> names = new HashMap<>();
	Map<String, ISpace> exts = new HashMap<>();
	Map<String, IDirectObservation> id2obs = new HashMap<>();
	ISpace extent;
	int nextId = 1;

	final static private int MAX_FEATURES_TO_COMPARE = 20;

	// scale to convert distance into meters
	private double originalWidth;
	private double widthInMeters;

	public SpatialIndex(ISpace extent) {
		this.extent = extent;
		this.originalWidth = extent.getEnvelope().getMaxX() - extent.getEnvelope().getMinX();
		IProjection utm = Projection.getUTM((Envelope) extent.getEnvelope());
		IEnvelope inmet = extent.getEnvelope().transform(utm, true);
		this.widthInMeters = inmet.getMaxX() - inmet.getMinX();
	}

	@Override
	public void add(IDirectObservation observation) {
		if (observation.getScale().getSpace() != null) {
			rtree.add(getRectangle(extent), getId(observation.getId()));
			exts.put(observation.getId(), observation.getScale().getSpace());
		}
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
		return exts.size();
	}

	@Override
	public double distanceToNearestObjectFrom(ILocator locator, IUnit unit) {
		ISpace sloc = locator.as(ISpace.class);
		if (sloc != null) {
			double[] xy = new double[] { sloc.getEnvelope().standard().getCenterCoordinates()[0],
					sloc.getEnvelope().standard().getCenterCoordinates()[1] };
			// check out the 20 closest features by bounding box
			FeatureFinder finder = new FeatureFinder(xy, MAX_FEATURES_TO_COMPARE);
			List<Integer> ids = finder.find();
			return finder.isOnFeature() ? 0 : convert(getDistance(xy, ids), unit);
		}
		return Double.NaN;
	}

	private double convert(double distance, IUnit unit) {
		double ret = (distance * widthInMeters) / originalWidth;
		return unit.equals(Units.INSTANCE.METERS) ? ret : unit.convert(ret, Units.INSTANCE.METERS).doubleValue();
	}

	private double getDistance(double[] xy, List<Integer> ids) {
		Geometry point = Shape.makePoint(xy[0], xy[1]);
		double distance = Double.POSITIVE_INFINITY;
		for (int id : ids) {
			double dist = point.distance(((Shape) this.exts.get(names.get(id)).getShape()).getJTSGeometry());
			if (distance > dist) {
				distance = dist;
			}
		}
		return distance;
	}

	@Override
	public double distanceToNearestObjectFrom(IDirectObservation observation, IUnit unit) {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getId(String name) {
		Integer ret = ids.get(name);
		if (ret == null) {
			ret = nextId++;
			ids.put(name, ret);
			names.put(ret, name);
		}
		return ret;
	}

	private Rectangle getRectangle(ISpace extent) {
		ReferencedEnvelope env = ((Shape) extent.getShape()).getEnvelope().getJTSEnvelope();
		return new Rectangle((float) env.getMinX(), (float) env.getMinY(), (float) env.getMaxX(),
				(float) env.getMaxY());
	}

	class FeatureFinder {

		List<Integer> idx = new ArrayList<>();
		private com.infomatiq.jsi.Point point;
		private int maxReturned;
		private boolean onFeature;
		private int count = 0;

		FeatureFinder(double[] xy, final int maxReturned) {
			this.point = new com.infomatiq.jsi.Point((float) xy[0], (float) xy[1]);
			this.maxReturned = maxReturned;
		}

		/**
		 * True if the passed point is directly on an indexed feature.
		 * 
		 * @return
		 */
		boolean isOnFeature() {
			return onFeature;
		}

		List<Integer> find() {

			rtree.nearestNUnsorted(point, new TIntProcedure() {

				@Override
				public boolean execute(int arg0) {
					if (!((Shape) exts.get(names.get(arg0)).getShape()).getJTSGeometry()
							.covers(Shape.makePoint(point.x, point.y))) {
						idx.add(arg0);
						return (count++) < maxReturned;
					}
					onFeature = true;
					return false;
				}
			}, maxReturned, Float.POSITIVE_INFINITY);

			return idx;
		}
	}

	class FeatureIntersector {

		Set<Integer> idx = new HashSet<>();
		private com.infomatiq.jsi.Point point;

		FeatureIntersector(double[] xy) {
			this.point = new com.infomatiq.jsi.Point((float) xy[0], (float) xy[1]);
		}

		Set<Integer> find() {

			rtree.intersects(new Rectangle(point.x, point.y, point.x, point.y), new TIntProcedure() {

				@Override
				public boolean execute(int arg0) {
					if (((Shape) exts.get(names.get(arg0)).getShape()).getJTSGeometry()
							.contains(Shape.makePoint(point.x, point.y))) {
						idx.add(arg0);
					}
					return true;
				}
			});

			return idx;
		}
	}
	//
	// @Override
	// public int size() {
	// return ids.size();
	// }
	//
	// @Override
	// public void add(IDirectObservation observation) {
	// if (observation.getScale().getSpace() != null) {
	// add(observation.getScale().getSpace(), ((Observation)
	// observation).getInternalId());
	// }
	// id2obs.put(((Observation) observation).getInternalId(), observation);
	// }
	//
	// @Override
	// public Pair<IDirectObservation, Double> getNearestObject(IDirectObservation
	// obs) {
	// List<Pair<IDirectObservation, Double>> dist = getNear(obs);
	// return dist.isEmpty() ? null : dist.get(0);
	// }
	//
	// @Override
	// public Pair<IDirectObservation, Double> getNearestObject(ILocator sfs) {
	// List<Pair<IDirectObservation, Double>> dist = getNear(sfs);
	// return dist.isEmpty() ? null : dist.get(0);
	// }
	//
	// @Override
	// public List<Pair<IDirectObservation, Double>> getNear(IDirectObservation obs)
	// {
	// if (obs.getScale().getSpace() != null) {
	// Point centroid = ((Shape)
	// obs.getScale().getSpace().getShape()).getJTSGeometry()
	// .getCentroid();
	// return getNear(SpaceLocator.get(centroid.getY(), centroid.getX()), obs);
	// }
	// return new ArrayList<>();
	// }
	//
	// @Override
	// public List<Pair<IDirectObservation, Double>> getNear(ILocator sfs) {
	// return getNear(sfs, null);
	// }
	//
	// private List<Pair<IDirectObservation, Double>> getNear(ILocator sfs,
	// IDirectObservation obs) {
	//
	// List<Pair<IDirectObservation, Double>> ret = new ArrayList<>();
	//
	// if (!((SpaceLocator) sfs).isLatLon()) {
	// // for now fuck, later convert using extent.getGrid()
	// throw new KlabRuntimeException("unimplemented conversion from grid
	// coordinates to latlon in spatial index");
	// }
	//
	// for (IDirectObservation o : id2obs.values()) {
	//
	// // don't add the one we're measuring from if it's defined.
	// if (obs != null && o.equals(obs)) {
	// continue;
	// }
	// double dist = getDistance(new double[] { ((SpaceLocator) sfs).lat,
	// ((SpaceLocator) sfs).lon }, o
	// .getScale().getSpace());
	// if (!Double.isNaN(dist)) {
	// ret.add(new Pair<>(o, dist));
	// }
	// }
	//
	// if (!ret.isEmpty()) {
	// ret.sort(new Comparator<Pair<IDirectObservation, Double>>() {
	// @Override
	// public int compare(Pair<IDirectObservation, Double> o1,
	// Pair<IDirectObservation, Double> o2) {
	// return Double.compare(o1.getSecond(), o2.getSecond());
	// }
	// });
	// }
	//
	// return ret;
	// }
}
