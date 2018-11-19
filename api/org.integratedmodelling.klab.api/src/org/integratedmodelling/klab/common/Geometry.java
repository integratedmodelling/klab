package org.integratedmodelling.klab.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

public class Geometry implements IGeometry {

	private static final long serialVersionUID = 8430057200107796568L;

	/**
	 * Bounding box as a double[]{minX, maxX, minY, maxY}
	 */
	public static final String PARAMETER_SPACE_BOUNDINGBOX = "bbox";

	/**
	 * Latitude,longitude as a double[]{lon, lat}
	 */
	public static final String PARAMETER_SPACE_LONLAT = "latlon";
	
	/**
	 * Projection code
	 */
	public static final String PARAMETER_SPACE_PROJECTION = "proj";

	/**
	 * Grid resolution as a string "n unit"
	 */
	public static final String PARAMETER_SPACE_GRIDRESOLUTION = "sgrid";

	/**
	 * Shape specs in WKB
	 */
	public static final String PARAMETER_SPACE_SHAPE = "shape";

	/**
	 * Time period as a long[]{startMillis, endMillis}
	 */
	public static final String PARAMETER_TIME_PERIOD = "period";

	/**
	 * time period as a long
	 */
	public static final String PARAMETER_TIME_GRIDRESOLUTION = "tgrid";

	public static Geometry create(String geometry) {
		return makeGeometry(geometry, 0);
	}

	private static Geometry emptyGeometry = new Geometry();
	private static Geometry scalarGeometry = makeGeometry("*", 0);

	/**
	 * The empty geometry.
	 * 
	 * @return the empty geometry
	 */
	public static Geometry empty() {
		return emptyGeometry;
	}

	/**
	 * The scalar geometry.
	 * 
	 * @return the scalar geometry
	 */
	public static Geometry scalar() {
		return scalarGeometry;
	}

	/**
	 * Encode into a string representation. Keys in parameter maps are sorted so the
	 * results can be compared for equality.
	 * 
	 * @return the string representation for the geometry
	 */
	public String encode() {

		if (isEmpty()) {
			return "X";
		}

		if (isScalar()) {
			return "*";
		}

		String ret = granularity == Granularity.MULTIPLE ? "#" : "";
		for (Dimension dim : dimensions) {
			ret += dim.getType() == Type.SPACE ? (dim.isRegular() ? "S" : "s")
					: (dim.getType() == Type.TIME ? (dim.isRegular() ? "T" : "t") : /* TODO others */ "");
			ret += dim.getDimensionality();
			if (dim.shape() != null && !isUndefined(dim.shape())) {
				ret += "(";
				for (int i = 0; i < dim.shape().length; i++) {
					ret += (i == 0 ? "" : ",") + dim.shape()[i];
				}
				ret += ")";
			}
			if (!dim.getParameters().isEmpty()) {
				ret += "{";
				boolean first = true;
				List<String> keys = new ArrayList<>(dim.getParameters().keySet());
				Collections.sort(keys);
				for (String key : keys) {
					ret += (first ? "" : ",") + key + "=" + encodeVal(dim.getParameters().get(key));
					first = false;
				}
				ret += "}";
			}
		}
		if (child != null) {
			ret += "," + child.encode();
		}
		return ret;
	}

	private boolean isUndefined(long[] shape) {
		for (long l : shape) {
			if (l < 0) {
				return true;
			}
		}
		return false;
	}

	private String encodeVal(Object val) {
		String ret = "";
		if (val.getClass().isArray()) {
			ret = "[";
			if (double[].class.isAssignableFrom(val.getClass())) {
				for (double d : (double[]) val) {
					ret += (ret.length() == 1 ? "" : " ") + d;
				}
			} else if (int[].class.isAssignableFrom(val.getClass())) {
				for (int d : (int[]) val) {
					ret += (ret.length() == 1 ? "" : " ") + d;
				}
			} else {
				for (Object d : (Object[]) val) {
					ret += (ret.length() == 1 ? "" : " ") + d;
				}
			}
			ret += "]";
		} else {
			ret = val.toString();
		}
		return ret;
	}

	@Override
	public String toString() {
		return encode();
	}

	/*
	 * dictionary for the IDs of any dimension types that are not space or time.
	 */
	// private static Map<Character, Integer> dimDictionary = new HashMap<>();

	static class DimensionImpl implements Dimension {

		private Type type;
		private boolean regular;
		private int dimensionality;
		private long[] shape;
		private Parameters<String> parameters = new Parameters<>();

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public boolean isRegular() {
			return regular;
		}

		@Override
		public int getDimensionality() {
			return dimensionality;
		}

		@Override
		public long size() {
			return shape == null ? UNDEFINED : product(shape);
		}

		private long product(long[] shape2) {
			long ret = 1;
			for (long l : shape) {
				ret *= l;
			}
			return ret;
		}

		@Override
		public long[] shape() {
			return shape == null ? Utils.newArray(UNDEFINED, dimensionality) : shape;
		}

		public long computeOffsets(long... offsets) {
			// TODO
			return 0;
		}

		@Override
		public long getOffset(ILocator index) {
			throw new IllegalArgumentException("getOffset() is not implemented on basic geometry dimensions");
		}

		@Override
		public IParameters<String> getParameters() {
			return parameters;
		}
	}

	private List<DimensionImpl> dimensions = new ArrayList<>();
	private Granularity granularity = Granularity.SINGLE;
	private Geometry child;
	private boolean scalar;

	// only used to compute offsets if requested
	transient private MultidimensionalCursor cursor = null;

	@Override
	public IGeometry getChild() {
		return child;
	}

	@Override
	public List<Dimension> getDimensions() {
		// must do this to leave concrete class in list and keep silly Jackson happy.
		List<Dimension> ret = new ArrayList<>();
		for (DimensionImpl d : dimensions) {
			ret.add(d);
		}
		return ret;
	}

	@Override
	public Granularity getGranularity() {
		return granularity;
	}

	@Override
	public boolean isScalar() {
		return scalar;
	}

	/**
	 * 
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 * @return this
	 */
	public Geometry withBoundingBox(double minX, double maxX, double minY, double maxY) {
		Dimension space = getDimension(Type.SPACE);
		if (space == null) {
			throw new IllegalStateException("cannot set spatial parameters on a geometry without space");
		}
		space.getParameters().put(PARAMETER_SPACE_BOUNDINGBOX, new double[] { minX, maxX, minY, maxY });
		return this;
	}

	/**
	 * 
	 * @param shapeSpecs
	 * @return this
	 */
	public Geometry withShape(String shapeSpecs) {
		Dimension space = getDimension(Type.SPACE);
		if (space == null) {
			throw new IllegalStateException("cannot set spatial parameters on a geometry without space");
		}
		space.getParameters().put(PARAMETER_SPACE_SHAPE, shapeSpecs);
		return this;
	}

	/**
	 * 
	 * @param gridResolution
	 * @return this
	 */
	public Geometry withGridResolution(String gridResolution) {
		Dimension space = getDimension(Type.SPACE);
		if (space == null) {
			throw new IllegalStateException("cannot set spatial parameters on a geometry without space");
		}
		space.getParameters().put(PARAMETER_SPACE_GRIDRESOLUTION, gridResolution);
		return this;
	}

	/**
	 * 
	 * @param shape
	 * @return this
	 */
	public Geometry withSpatialShape(long... shape) {
		Dimension space = getDimension(Type.SPACE);
		if (space == null) {
			throw new IllegalStateException("cannot set spatial parameters on a geometry without space");
		}
		((DimensionImpl) space).shape = shape;
		return this;
	}

	/**
	 * 
	 * @param timeResolution
	 * @return this
	 */
	public Geometry withTemporalResolution(String timeResolution) {
		Dimension time = getDimension(Type.TIME);
		if (time == null) {
			throw new IllegalStateException("cannot set temporal parameters on a geometry without time");
		}
		time.getParameters().put(PARAMETER_TIME_GRIDRESOLUTION, timeResolution);
		return this;
	}

	/**
	 * 
	 * @param n
	 * @return this
	 */
	public Geometry withTemporalShape(long n) {
		Dimension time = getDimension(Type.TIME);
		if (time == null) {
			throw new IllegalStateException("cannot set temporal parameters on a geometry without time");
		}
		((DimensionImpl) time).shape = new long[] { n };
		return this;
	}

	/**
	 * 
	 * @param projection
	 * @return this
	 */
	public Geometry withProjection(String projection) {
		Dimension space = getDimension(Type.SPACE);
		if (space == null) {
			throw new IllegalStateException("cannot set spatial parameters on a geometry without space");
		}
		space.getParameters().put(PARAMETER_SPACE_PROJECTION, projection);
		return this;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * @return this
	 */
	public Geometry withTemporalBoundaries(long start, long end) {
		Dimension time = getDimension(Type.TIME);
		if (time == null) {
			throw new IllegalStateException("cannot set temporal parameters on a geometry without time");
		}
		time.getParameters().put(PARAMETER_TIME_PERIOD, new long[] { start, end });
		return this;
	}

	protected Geometry() {
	}

	/*
	 * read the geometry defined starting at the i-th character
	 */
	private static Geometry makeGeometry(String geometry, int i) {

		Geometry ret = new Geometry();

		if (geometry == null || geometry.equals("X")) {
			return empty();
		}

		if (geometry.equals("*")) {
			return scalar();
		}

		for (int idx = i; idx < geometry.length(); idx++) {
			char c = geometry.charAt(idx);
			if (c == '#') {
				ret.granularity = Granularity.MULTIPLE;
			} else if (c >= 'A' && c <= 'z') {
				DimensionImpl dimensionality = ret.newDimension();
				if (c == 'S' || c == 's') {
					dimensionality.type = Type.SPACE;
				} else if (c == 'T' || c == 't') {
					dimensionality.type = Type.TIME;
				} else {
					throw new IllegalArgumentException("unrecognized geometry dimension identifier " + c);
					// if (dimDictionary.containsKey(Character.toLowerCase(c))) {
					// dimensionality.type = dimDictionary.get(Character.toLowerCase(c));
					// } else {
					// int n = dimDictionary.size() + 2;
					// dimDictionary.put(Character.toLowerCase(c), n);
					// dimensionality.type = n;
					// }
				}

				dimensionality.regular = Character.isUpperCase(c);

				idx++;
				if (geometry.charAt(idx) == '.') {
					dimensionality.dimensionality = NONDIMENSIONAL;
				} else {
					dimensionality.dimensionality = Integer.parseInt("" + geometry.charAt(idx));
				}

				if (geometry.length() > (idx + 1) && geometry.charAt(idx + 1) == '(') {
					idx += 2;
					StringBuffer shape = new StringBuffer(geometry.length());
					while (geometry.charAt(idx) != ')') {
						shape.append(geometry.charAt(idx));
						idx++;
					}
					String[] dims = shape.toString().split(",");
					long[] sdimss = new long[dims.length];
					for (int d = 0; d < dims.length; d++) {
						sdimss[d] = Long.parseLong(dims[d].trim());
					}
					dimensionality.dimensionality = sdimss.length;
					dimensionality.shape = sdimss;
				}
				if (geometry.length() > (idx + 1) && geometry.charAt(idx + 1) == '{') {
					idx += 2;
					StringBuffer shape = new StringBuffer(geometry.length());
					while (geometry.charAt(idx) != '}') {
						shape.append(geometry.charAt(idx));
						idx++;
					}
					dimensionality.parameters.putAll(readParameters(shape.toString()));
				}

				ret.dimensions.add(dimensionality);

			} else if (c == ',') {
				ret.child = makeGeometry(geometry, idx + 1);
				break;
			}
		}
		return ret;
	}

	private static Map<String, Object> readParameters(String kvs) {
		Map<String, Object> ret = new HashMap<>();
		for (String kvp : kvs.trim().split(",")) {
			String[] kk = kvp.trim().split("=");
			if (kk.length != 2) {
				throw new IllegalArgumentException("wrong key/value pair in geometry definition: " + kvp);
			}
			String key = kk[0].trim();
			String val = kk[1].trim();
			Object v = null;
			if (val.startsWith("[") && val.endsWith("]")) {
				v = NumberUtils.podArrayFromString(val, "\\s+");
			} else if (NumberUtils.encodesDouble(((String) val))) {
				v = Double.parseDouble(val);
			} else if (NumberUtils.encodesInteger(val)) {
				v = Integer.parseInt(val);
			} else {
				v = val;
			}

			ret.put(key, v);

		}
		return ret;
	}

	private DimensionImpl newDimension() {
		return new DimensionImpl();
	}

	public static void main(String[] args) {
		// Geometry g1 = create("S2");
		// Geometry g2 = create("#S2T1,#T1");
		// Geometry g3 = create("S2(200,100)");
		// Geometry g4 = create("T1(23)S2(200,100)");
		// Geometry g5 = create("S2(200,100){srid=EPSG:3040,bounds=[23.3 221.0 25.2
		// 444.4]}T1(12)");
		// System.out.println("hla");
	}

	@Override
	public boolean isEmpty() {
		return dimensions.isEmpty() && child == null;
	}

	@Override
	public Dimension getDimension(Type type) {
		for (Dimension dimension : dimensions) {
			if (dimension.getType() == type) {
				return dimension;
			}
		}
		return null;
	}

	@Override
	public long size() {
		long ret = 1;
		for (Dimension dimension : dimensions) {
			if (dimension.size() == UNDEFINED) {
				return UNDEFINED;
			}
			ret *= dimension.size();
		}
		return ret;
	}

	/**
	 * The simplest locator possible in a non-semantic geometry: offsets along a
	 * dimension.
	 * 
	 * @param offsets
	 * @return a new offset locator
	 */
	public OffsetLocator locate(long... offsets) {
		return new OffsetLocator(offsets);
	}

	public OffsetLocator locate(int... offsets) {
		long[] ofs = new long[offsets.length];
		for (int i = 0; i < offsets.length; i++) {
			ofs[i] = offsets[i];
		}
		return new OffsetLocator(ofs);
	}
	
	@Override
	public ILocator at(ILocator locator) {
		if (locator instanceof OffsetLocator) {

		}
		throw new IllegalArgumentException(
				"geometry cannot use locator of type " + locator.getClass().getCanonicalName());
	}

	@Override
	public Iterable<ILocator> over(Type dimension) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ILocator at(Type dimension, long... offsets) {
		// TODO Auto-generated method stub
		return null;
	}

	public class OffsetLocator implements ILocator {

		long offset;

		public OffsetLocator(long[] offsets) {
			this.offset = computeOffset(offsets);
		}

		OffsetLocator() {
		}

		public long getOffset() {
			return offset;
		}

		@Override
		public ILocator at(ILocator locator) {
			throw new IllegalArgumentException("offset locator cannot be further located");
		}

		@Override
		public <T extends ILocator> T as(Class<T> cls) {
			throw new IllegalArgumentException("offset locator cannot be further located");
		}
	}

	public long computeOffset(long[] offsets) {

		if (offsets[0] > 0) {
			System.out.println("COCK");
		}
		
		// trivial case
		if (offsets.length == 1 && dimensions.size() == 1 && dimensions.get(0).shape.length == 1) {
			return offsets[0];
		}
		if (this.cursor == null) {
			this.cursor = new MultidimensionalCursor(this);
		}
		return this.cursor.getElementOffset(offsets);
	}

	public ILocator getLocator(long offset) {
		OffsetLocator ret = new OffsetLocator();
		ret.offset = offset;
		return ret;
	}

	@Override
	public long getOffset(ILocator index) {
		if (index instanceof OffsetLocator) {
			return ((OffsetLocator) index).getOffset();
		}
		throw new IllegalArgumentException("cannot use " + index + " as a scale locator");
	}

	@Override
	public long[] shape(Type dimensionType) {
		Dimension dim = getDimension(dimensionType);
		if (dim == null) {
			throw new IllegalArgumentException("geometry does not have dimension " + dimensionType);
		}
		return dim.shape();
	}

	@Override
	public int hashCode() {
		return encode().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Geometry other = (Geometry) obj;
		return other.encode().equals(encode());
	}

	@Override
	public long getOffset(long globalOffset, Type dimensionType) {
		int dimIndex = -1;
		for (int i = 0; i < dimensions.size(); i++) {
			if (dimensions.get(i).type == dimensionType) {
				dimIndex = i;
				break;
			}
		}
		if (dimIndex < 0) {
			return -1;
		}
		if (this.cursor == null) {
			this.cursor = new MultidimensionalCursor(this);
		}
		return cursor.getElementIndexes(globalOffset)[dimIndex];
	}

	@Override
	public <T extends ILocator> T as(Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

}
