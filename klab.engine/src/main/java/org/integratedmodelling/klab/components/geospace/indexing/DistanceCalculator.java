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
import org.integratedmodelling.klab.api.observations.scale.space.ISpaceLocator;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;

import com.vividsolutions.jts.geom.Geometry;

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
			exts.add(((Shape) observation.getScale().getSpace()).getJTSGeometry());
			isEmpty = false;
		}
	}

	public double distanceToNearestObjectFrom(ILocator locator, IUnit unit) {

		if (!isEmpty) {
			ISpaceLocator sloc = locator.as(ISpaceLocator.class);
			if (sloc != null) {
				return convert(getDistance(new double[] { sloc.getXCoordinate(), sloc.getYCoordinate() }), unit);
			}
		}
		return Double.NaN;
	}

	private double convert(double distance, IUnit unit) {
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
