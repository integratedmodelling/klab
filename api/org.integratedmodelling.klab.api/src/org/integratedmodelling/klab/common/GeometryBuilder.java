package org.integratedmodelling.klab.common;

import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
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

		public TimeBuilder size(long n) {
			time.setShape(new long[] { n });
			time.setRegular(true);
			return this;
		}

		public SpaceBuilder space() {
			return new SpaceBuilder();
		}
		
		public Geometry build() {
			return GeometryBuilder.this.build();
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

		public TimeBuilder time() {
			return new TimeBuilder();
		}
		
		public Geometry build() {
			return GeometryBuilder.this.build();
		}
	}
	
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
