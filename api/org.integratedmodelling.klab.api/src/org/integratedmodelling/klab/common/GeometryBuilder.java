package org.integratedmodelling.klab.common;

import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.common.Geometry.DimensionImpl;

/**
 * Builder for geometries. Just time and space for now (numerosity later).
 * 
 * @author ferdinando.villa
 *
 */
public class GeometryBuilder {

	private DimensionImpl space;
	private DimensionImpl time;

	public class TimeBuilder {

		TimeBuilder() {
			if (time == null) {
				time = new DimensionImpl();
				time.setType(Dimension.Type.TIME);
				time.setDimensionality(1);
			}
		}

		public TimeBuilder generic() {
			time.setGeneric(true);
			return this;
		}

		public TimeBuilder regular() {
			time.setRegular(true);
			return this;
		}

		public TimeBuilder start(ITimeInstant start) {
			time.getParameters().put(Geometry.PARAMETER_TIME_COVERAGE_START, start.getMilliseconds());
			return this;
		}

		public TimeBuilder end(ITimeInstant start) {
			time.getParameters().put(Geometry.PARAMETER_TIME_COVERAGE_END, start.getMilliseconds());
			return this;
		}

		public TimeBuilder resolution(ITime.Resolution resolution) {
			time.getParameters().put(Geometry.PARAMETER_TIME_SCOPE, resolution.getMultiplier());
			time.getParameters().put(Geometry.PARAMETER_TIME_SCOPE_UNIT, resolution.getType().name().toLowerCase());
			return this;
		}

		public TimeBuilder size(long n) {
			time.setShape(new long[] { n });
			time.setRegular(true);
			return this;
		}

		public SpaceBuilder space() {
			return new SpaceBuilder();
		}

		public GeometryBuilder build() {
			return GeometryBuilder.this;
		}
	}

	public class SpaceBuilder {

		SpaceBuilder() {
			if (space == null) {
				space = new DimensionImpl();
				space.setType(Dimension.Type.SPACE);
				space.setDimensionality(2);
			}
		}

		public SpaceBuilder generic() {
			space.setGeneric(true);
			return this;
		}

		public SpaceBuilder regular() {
			space.setRegular(true);
			return this;
		}

		public SpaceBuilder size(long x, long y) {
			space.setShape(new long[] { x, y });
			space.setRegular(true);
			return this;
		}

		public SpaceBuilder size(long n) {
			space.setShape(new long[] { n });
			space.setRegular(false);
			return this;
		}

		public GeometryBuilder build() {
			return GeometryBuilder.this;
		}
	}

	/**
	 * Create a spatial region from a resource URN (specifying a polygon). The
	 * string may also specify a WKT polygon using the k.LAB conventions (preceded
	 * by the EPSG: projection). The resulting 
	 *
	 * @param urn
	 * @param resolution a string in the format "1 km"
	 * @return
	 */
	public GeometryBuilder region(String urn) {
		return this;
	}

	/**
	 * Create a spatial grid from a resource URN (specifying a polygon) and a
	 * resolution. The string may also specify a WKT polygon using the k.LAB
	 * conventions (preceded by the EPSG: projection).
	 *
	 * @param urn
	 * @param resolution a string in the format "1 km"
	 * @return
	 */
	public GeometryBuilder grid(String urn, String resolution) {
		return this;
	}

	/**
	 * Create a spatial grid from a lat/lon bounding box and a resolution. The box
	 * is "straight" with the X axis specifying <em>longitude</em>.
	 *
	 * @param resolution a string in the format "1 km"
	 * @return
	 */
	public GeometryBuilder grid(double x1, double x2, double y1, double y2, String resolution) {
		SpaceBuilder builder = space().regular();
		return builder.build();
	}

	/**
	 * Create a temporal extent in years. If one year is passed, build a single-year
	 * extent; otherwise build a yearly grid from the first year to the second.
	 * 
	 * @param years
	 * @return
	 */
	public GeometryBuilder years(int... years) {
		TimeBuilder builder = time();
		return builder.build();
	}

	/**
	 * Use a builder to build a spatial geometry piece by piece. Call build() on the
	 * returned value to obtain the geometry builder back. Calling build() on the
	 * space builder is not necessary for the geometry to be recorded.
	 * 
	 * @return
	 */
	public SpaceBuilder space() {
		return new SpaceBuilder();
	}

	public TimeBuilder time() {
		return new TimeBuilder();
	}

	public Geometry build() {

		Geometry ret = new Geometry();

		if (space != null) {
			ret.addDimension(space);
		}

		if (time != null) {
			ret.addDimension(time);
		}

		return ret;
	}

	public static void main(String[] args) {
		System.out.println(Geometry.builder().space().generic().build());
		System.out.println(Geometry.builder().space().size(200, 339).build());
	}

}
