package org.integratedmodelling.klab.components.geospace.extents;


import javax.measure.Unit;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.geotools.referencing.CRS.AxisOrder;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.components.geospace.utils.UTM;
import org.integratedmodelling.klab.components.geospace.utils.WGS84;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;

public class Projection implements IProjection {

	private String code;
	CoordinateReferenceSystem crs;

	public static final String DEFAULT_METERS_PROJECTION = "EPSG:3005";
	public static final String DEFAULT_PROJECTION_CODE = "EPSG:4326";
	public static final String LATLON_PROJECTION_CODE = "EPSG:4326";
	private static Projection defaultProjection;
	private static Projection latlonProjection;
	private static Projection[][] utmProjections = new Projection[2][60];

	/**
	 * Obtain the projection corresponding to the passed EPSG (or other supported
	 * authority) code, in the format "EPSG:nnnn".
	 * 
	 * @param code
	 * @return the projection, which may be invalid.
	 */
	public static Projection create(String code) {
		return new Projection(code);
	}

	public static Projection create(String authority, int code) {
		return new Projection(authority + ":" + code);
	}

	public static Projection getDefault() {
		if (defaultProjection == null) {
			defaultProjection = Projection.create(DEFAULT_PROJECTION_CODE);
		}
		return defaultProjection;
	}

	public static Projection getLatLon() {
		if (latlonProjection == null) {
			latlonProjection = Projection.create(LATLON_PROJECTION_CODE);
		}
		return latlonProjection;
	}

	@Override
	public String getSimpleSRS() {
		try {
			Integer integer = CRS.lookupEpsgCode(crs, true);
			return "EPSG:" + integer;
		} catch (FactoryException e) {
			// shut up
		}
		return null;
	}

	/**
	 * Get the UTM projection most appropriate to geolocate the passed envelope,
	 * which can be in any projection.
	 * 
	 * @param envelope
	 * @return the projection corresponding to the best UTM zone for this envelope
	 */
	public static Projection getUTM(Envelope envelope) {

		Envelope standardized = envelope.transform(getLatLon(), true);
		double[] xy = standardized.getCenterCoordinates();
		// check longitude, in OpenLayer is possible to get out of range
		if (xy[0] > 180 || xy[0] < -180) {
			throw new IllegalArgumentException("Longitude is out of range (-180/180)");
		}
		if (xy[1] > 90 || xy[1] < -90) {
			throw new IllegalArgumentException("Latitude is out of range (90/-90)");
		}
		WGS84 wgs = new WGS84(xy[1], xy[0]);
		UTM utm = new UTM(wgs);

		int idx = 0;
		if (wgs.getHemisphere() == 'S') {
			idx = 1;
		}

		if (utmProjections[idx][utm.getZone() - 1] == null) {
			String code = "EPSG:32" + (wgs.getHemisphere() == 'S' ? "7" : "6") + String.format("%02d", utm.getZone());
			utmProjections[idx][utm.getZone() - 1] = create(code);
		}

		return utmProjections[idx][utm.getZone() - 1];
	}

	private Projection(String code) {
		this.code = code;
		try {
			this.crs = CRS.decode(this.code, true);
		} catch (FactoryException e) {
			throw new KlabValidationException(e);
		}
	}

	private Projection(String code, CoordinateReferenceSystem crs) {
		this.code = code;
		this.crs = crs;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projection other = (Projection) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public CoordinateReferenceSystem getCoordinateReferenceSystem() {
		return crs;
	}

	public static Projection create(CoordinateReferenceSystem coordinateReferenceSystem) {
		try {
			String code = CRS.lookupIdentifier(coordinateReferenceSystem, true);
			return new Projection(code, coordinateReferenceSystem);
		} catch (FactoryException e) {
			throw new KlabValidationException(e);
		}
	}

	// /**
	// * The haversine formula calculates great-circle distance between two points
	// on
	// * a sphere from their longitudes and latitudes.
	// *
	// * From http://rosettacode.org/wiki/Haversine_formula#Java
	// *
	// * @param lat1
	// * PointOne latitude
	// * @param lon1
	// * PointOne longitude
	// * @param lat2
	// * PointTwo latitude
	// * @param lon2
	// * PointTwo longitude
	// * @return distance in meters
	// */
	// public static double haversine(double lat1, double lon1, double lat2, double
	// lon2) {
	// double R = 6372800; // in m
	// double dLat = Math.toRadians(lat2 - lat1);
	// double dLon = Math.toRadians(lon2 - lon1);
	// lat1 = Math.toRadians(lat1);
	// lat2 = Math.toRadians(lat2);
	//
	// double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	// + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
	// double c = 2 * Math.asin(Math.sqrt(a));
	// return R * c;
	// }

	private static final int EARTH_RADIUS = 6372800; // Approx Earth radius in m
	private static final int EARTH_CIRCUMFERENCE = 40007860;

	public static double distance(double startLat, double startLong, double endLat, double endLong) {

		// dumb fix for the fact that these situations (more or less sensibly) return no
		// distance.
		// there must be a smarter way.
		if ((endLong - startLong) > 359 && startLat == endLat) {
			return EARTH_CIRCUMFERENCE;
		}

		double dLat = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c; // <-- d
	}

	public static double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

	/**
	 * Transform the passed coordinate from the passed projection to this.
	 * 
	 * @param coordinate
	 * @param other
	 * @return
	 */
	public double[] transformCoordinate(double[] coordinate, IProjection other) {
		double[] ret = coordinate;
		if (!this.equals(other)) {
			try {
				MathTransform transform = CRS.findMathTransform(((Projection) other).crs, this.crs);
				DirectPosition position = transform.transform(new DirectPosition2D(ret[0], ret[1]), null);
				ret = new double[] { position.getCoordinate()[0], position.getCoordinate()[1] };
			} catch (Exception e) {
				throw new KlabInternalErrorException(e);
			}
		}
		return ret;
	}

	public CoordinateReferenceSystem getCRS() {
		return crs;
	}
	
	public int getSRID() {
		return Integer.parseInt(code.split(":")[1]);
	}

	@Override
	public boolean isMeters() {
		return getUnits().equals("m");
	}

	@Override
	public boolean flipsCoordinates() {
		return !CRS.getAxisOrder(this.crs).equals(AxisOrder.EAST_NORTH);
	}

	@Override
	public String getUnits() {
		return getUnit().toString();
	}

	public Unit<?> getUnit() {
		return getCoordinateReferenceSystem().getCoordinateSystem().getAxis(0).getUnit();
	}

	public String getWKTDefinition() {
		return crs.toWKT();
	}

}
