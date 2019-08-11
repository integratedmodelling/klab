package org.integratedmodelling.klab.common;

import java.util.StringTokenizer;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

/**
 * A simple locator that uses only offsets and can be initialized through a
 * string specification. Can always convert its located dimensions to dimensions
 * or located geometries using {@link #at(ILocator)}; actual extents or scales
 * can also be obtained as long as the geometry identified is a scale.
 * 
 * @author Ferd
 *
 */
public class GeometryLocator implements ILocator {

	private IGeometry geometry = null;
	private Long[] offsets;

	private GeometryLocator() {
	}

	/**
	 * Pass a geometry to be located. If the specifications contain a geometry
	 * component and the passed geometry is not null, the geometry in the
	 * specification is ignored.
	 * 
	 * @param spec
	 * @param geometry
	 * @return
	 */
	public static GeometryLocator create(String spec, IGeometry geometry) {
		GeometryLocator ret = new GeometryLocator();
		int at = spec.indexOf('@');
		if (at > 0) {
			spec = spec.substring(at + 1);
		}
		ret.offsets = read(spec, geometry);
		ret.geometry = geometry;
		return ret;
	}

	private static Long[] read(String spec, IGeometry geometry) {
		Long[] sret = new Long[geometry.getDimensions().size()];
		StringTokenizer tokenizer = new StringTokenizer(spec, "(,)*\u221E", true);

		int i = 0, ii = 0;
		long[] internal = null;

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.equals("(")) {
				internal = new long[geometry.getDimensions().get(i).getDimensionality()];
			} else if (token.equals(")")) {

				long nl = 0;
				if (internal.length > 0) {
					Dimension d = geometry.getDimensions().get(i);
					nl = internal.length == 1 ? internal[0] : d.getOffset(internal);
				}
				if (i < sret.length) {
					sret[i++] = nl;
				} else {
					throw new KlabValidationException("locator mismatches the size of the dimensions");

				}
				internal = null;
				ii = 0;

			} else if (!token.equals(",")) {
				Long l = token.equals("*") ? -1l
						: (token.equals("\u221E") ? Long.MAX_VALUE : Long.parseUnsignedLong(token));
				if (internal != null) {
					internal[ii++] = l;
				} else if (i < sret.length) {
					sret[i++] = l;
				} else {
					throw new KlabValidationException("locator mismatches the size of the dimensions");

				}
			}
		}
		return sret;
	}

	/**
	 * Expects a full locator string including a geometry.
	 * 
	 * @param spec
	 * @return
	 */
	public static GeometryLocator create(String spec) {
		GeometryLocator ret = new GeometryLocator();
		int at = spec.indexOf('@');
		if (at > 0) {
			ret.geometry = Geometry.create(spec.substring(0, at));
			ret.offsets = read(spec.substring(at + 1), ret.geometry);
		} else {
			throw new KlabValidationException(
					"locator string must contain both a geometry and a locator, separated by @");
		}
		return ret;
	}

	@Override
	public ILocator at(ILocator locator) {
		throw new IllegalStateException("offset-based locators cannot be further located");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T as(Class<T> cls) {
		// TODO Auto-generated method stub
		if (cls.isAssignableFrom(Long[].class)) {
			return (T) offsets;
		} else if (cls.isAssignableFrom(IScale.class)) {
			// return a scale
		} else if (cls.isAssignableFrom(IGeometry.class)) {

		} else if (cls.isAssignableFrom(ISpace.class)) {

		} else if (cls.isAssignableFrom(ITime.class)) {

		}
		return null;
	}

	public IGeometry getGeometry() {
		return geometry;
	}

	public void setGeometry(IGeometry geometry) {
		this.geometry = geometry;
	}

	public String toString() {
		String oofs = "";
		for (int i = 0; i < offsets.length; i++) {
			if (!oofs.isEmpty()) {
				oofs += ",";
			}
			oofs += (offsets[i] == Geometry.INFINITE_SIZE ? "\u221E" : ("" + offsets[i]));
		}
		return geometry + "@" + oofs;
	}

	public static void main(String[] args) {

		String g1 = "S2(431999,27599){bbox=[-179.99999999999966 179.99999999999835 59.99999999999997 82.99999999999997],proj=EPSG:4326}@(30,40)";
		String g2 = "T1(3)S2(431999,27599){bbox=[-179.99999999999966 179.99999999999835 59.99999999999997 82.99999999999997],proj=EPSG:4326}@2,(30,40)";

		System.out.println(create(g1));
		System.out.println(create(g2));

	}
}
