package org.integratedmodelling.klab.components.geospace.indexing;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.operation.distance.DistanceOp;

public class DistanceCalculator {

	List<Geometry> exts;
	ISpace extent;
	Geometry geometry;
	boolean isEmpty = true;

	// scale to convert distance into meters
	private double originalWidth;
	private double widthInMeters;

	public DistanceCalculator(ISpace extent, int nGeometries) {
		this.extent = extent;
		this.originalWidth = extent.getEnvelope().getMaxX() - extent.getEnvelope().getMinX();
		IProjection utm = Projection.getUTM((Envelope) extent.getEnvelope());
		IEnvelope inmet = extent.getEnvelope().transform(utm, true);
		this.widthInMeters = inmet.getMaxX() - inmet.getMinX();
		this.exts = new ArrayList<>(nGeometries);
	}

	Geometry getFinalGeometry() {
		if (geometry == null) {
			geometry = Geospace.gFactory.createGeometryCollection(exts.toArray(new Geometry[exts.size()]));
		}
		return geometry;
	}

	public void add(IDirectObservation observation) {
		if (geometry != null) {
			throw new IllegalStateException(
					"cannot add geometries to a distance calculator after the first distance has been computed");
		}
		if (observation.getScale().getSpace() != null) {
			exts.add(((Shape) observation.getScale().getSpace().getShape()).getJTSGeometry());
			isEmpty = false;
		}
	}

	public double distanceToNearestObjectFrom(ILocator locator, IUnit unit) {

		if (!isEmpty) {
			ISpace sloc = locator.as(ISpace.class);
			if (sloc != null) {
				return convert(getDistance(new double[] { sloc.getEnvelope().standard().getCenterCoordinates()[0],
						sloc.getEnvelope().standard().getCenterCoordinates()[1] }), unit);
			}
		}
		return Double.NaN;
	}

	public double[] getNearestPoint(double[] xy) {

		if (!isEmpty) {
			Coordinate[] nearest = new DistanceOp(Shape.makePoint(xy[0], xy[1]), getFinalGeometry()).nearestPoints();
			if (nearest.length > 1) {
				return new double[] { nearest[1].x, nearest[1].y };
			}
		}
		return null;
	}

	private double convert(double distance, IUnit unit) {
		if (Double.isNaN(distance)) {
			return distance;
		}
		double ret = (distance * widthInMeters) / originalWidth;
		return unit.equals(Units.INSTANCE.METERS) ? ret : unit.convert(ret, Units.INSTANCE.METERS).doubleValue();
	}

	private double getDistance(double[] xy) {

		if (isEmpty) {
			return Double.NaN;
		}

		Geometry point = Shape.makePoint(xy[0], xy[1]);
		return getFinalGeometry().distance(point);
	}
}
