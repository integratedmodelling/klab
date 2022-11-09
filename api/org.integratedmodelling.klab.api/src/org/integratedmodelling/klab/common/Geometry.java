package org.integratedmodelling.klab.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.rest.ScaleReference;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

public class Geometry implements IGeometry {

	private static final long serialVersionUID = 8430057200107796568L;

	/**
	 * These are useful for common geometry creation from programs. Append to a
	 * space definition within curly brackets.
	 */
	public static final String WORLD_BBOX_PARAMETERS = "bbox=[-180.0 180.0 -90.0 90.0],proj=EPSG:4326";

	/**
	 * An internal descriptor for a locator. The API requires using this to
	 * disambiguate locators that use world coordinates.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public static class DimensionTarget {

		// this and the next null if the target is an entire scale or just offsets
		public Dimension.Type type;
		// the only way to get this is by passing a single Dimension or Extent parameter
		public Dimension extent;
		// if single parameter is a geometry or scale, this isn't null
		public IGeometry geometry;
		// this null if target is scanned in its entirety
		public long[] offsets;

		// this like offsets but only if the numbers are doubles (first number coerces
		// the second). Not handled in Geometry, only in Scale.
		public double[] coordinates;

		// if not null, we got something else and Geometry will throw an error, but
		// Scale
		// may not.
		public Object[] otherLocators;

		private void defineOffsets(List<Number> numbers) {
			if (numbers.size() > 0) {
				if (numbers.get(0) instanceof Double || numbers.get(0) instanceof Float) {
					offsets = new long[numbers.size()];
					int i = 0;
					for (Number l : numbers) {
						coordinates[i++] = l.doubleValue();
					}

				} else {
					offsets = new long[numbers.size()];
					int i = 0;
					for (Number l : numbers) {
						offsets[i++] = l.longValue();
					}
				}
			}
		}

		public void defineOthers(List<Object> others) {
			if (others.size() > 0) {
				this.otherLocators = others.toArray();
			}
		}

		private DimensionTarget() {
		}

		/**
		 * Public constructor for when this is used by a semantic IExtent to
		 * disambiguate a world-coordinate locator.
		 * 
		 * @param type
		 * @param offsets
		 */
		public DimensionTarget(Dimension dimension, long[] offsets) {
			this.type = dimension.getType();
			this.extent = dimension;
			this.offsets = offsets;
		}

		@Override
		public String toString() {
			return "<L " + (type == null ? "" : type.name()) + (extent == null ? "" : extent.toString())
					+ (geometry == null ? "" : geometry.toString()) + (offsets == null ? "" : Arrays.toString(offsets))
					+ ">";
		}
	}

	public static List<DimensionTarget> separateTargets(Object... locators) {
		List<DimensionTarget> ret = new ArrayList<>();
		DimensionTarget current = null;
		List<Number> numbers = new ArrayList<>();
		List<Object> others = new ArrayList<>();

		boolean haveGeometry = false;
		int nExtents = 0;

		if (locators != null) {
			for (int i = 0; i < locators.length; i++) {
				Object o = locators[i];
				if (o instanceof Number) {

					if (current == null) {
						current = new DimensionTarget();
					}

					numbers.add((Number) o);

				} else if (o instanceof int[]) {
					for (int of : (int[]) o) {
						numbers.add(of);
					}
				} else if (o instanceof long[]) {
					for (long of : (long[]) o) {
						numbers.add(of);
					}
				} else {

					if (current != null) {
						current.defineOffsets(numbers);
						current.defineOthers(others);
						ret.add(current);
					}
					current = new DimensionTarget();
					numbers.clear();
					others.clear();

					if (o instanceof Dimension.Type) {
						current.type = (Dimension.Type) o;
					} else if (o instanceof Dimension) {
						nExtents++;
						current.extent = (Dimension) o;
						current.type = ((Dimension) o).getType();
					} else if (o instanceof IScale) {
						current.geometry = (IScale) o;
					} else if (o instanceof IGeometry) {
						haveGeometry = true;
						current.geometry = (IGeometry) o;
					} else if (o instanceof Class<?>) {
						if (ISpace.class.isAssignableFrom((Class<?>) o)) {
							current.type = Dimension.Type.SPACE;
						} else if (ITime.class.isAssignableFrom((Class<?>) o)) {
							current.type = Dimension.Type.TIME;
						} else {
							throw new KlabIllegalArgumentException("illegal locator definition: " + o);
						}
					} else {
						// add to 'weird' stuff for other APIs to process
						others.add(o);
					}
				}
			}

			if (current != null) {
				current.defineOffsets(numbers);
				ret.add(current);
			}
		}

		if (ret.isEmpty() && !numbers.isEmpty()) {
			current = new DimensionTarget();
			current.defineOffsets(numbers);
			ret.add(current);
		}

		if (haveGeometry && ret.size() > 1) {
			throw new KlabIllegalArgumentException(
					"locators defined through a concrete geometry cannot have any other locator parameter");
		}
		if (nExtents > 0 && ret.size() != nExtents) {
			throw new KlabIllegalArgumentException(
					"locators defined through concrete extents cannot have non-extent locator parameters");
		}

		return ret;
	}

	/**
	 * Bounding box as a double[]{minX, maxX, minY, maxY}
	 */
	public static final String PARAMETER_SPACE_BOUNDINGBOX = "bbox";

	/**
	 * Latitude,longitude as a double[]{lon, lat}
	 */
	public static final String PARAMETER_SPACE_LONLAT = "latlon";

	/**
	 * Authority specifier for generic enumerated extent (may be D or S)
	 */
	public static final String PARAMETER_ENUMERATED_AUTHORITY = "authority";

	/**
	 * Base identity specifier for generic enumerated extent (may be D or S)
	 */
	public static final String PARAMETER_ENUMERATED_BASE_IDENTITY = "baseidentity";

	/**
	 * Concrete identity for enumerated extent (D or S)
	 */
	public static final String PARAMETER_ENUMERATED_IDENTIFIER = "identifier";

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
	 * Resource URN to retrieve the space extent
	 */
	public static final String PARAMETER_SPACE_RESOURCE_URN = "urn";

	/**
	 * Time period as a long[]{startMillis, endMillis}
	 */
	public static final String PARAMETER_TIME_PERIOD = "period";

	/**
	 * time period as a long
	 */
	public static final String PARAMETER_TIME_GRIDRESOLUTION = "tgrid";

	/**
	 * time period as a long
	 */
	public static final String PARAMETER_TIME_START = "tstart";

	/**
	 * time period as a long
	 */
	public static final String PARAMETER_TIME_END = "tend";

	/**
	 * Time representation: one of generic, specific, grid or real.
	 */
	public static final String PARAMETER_TIME_REPRESENTATION = "ttype";

	/**
	 * Irregular grid transition points, start to end.
	 */
	public static final String PARAMETER_TIME_TRANSITIONS = "transitions";

	/**
	 * Time scope: integer or floating point number of PARAMETER_TIME_SCOPE_UNITs.
	 */
	public static final String PARAMETER_TIME_SCOPE = "tscope";

	/**
	 * Specific time location, for locator geometries. Expects a long, a date or a
	 * ITimeInstant.
	 */
	public static final String PARAMETER_TIME_LOCATOR = "time";

	/**
	 * Time scope unit: one of millennium, century, decade, year, month, week, day,
	 * hour, minute, second, millisecond, nanosecond; use lowercase values of
	 * {@link Resolution}.
	 */
	public static final String PARAMETER_TIME_SCOPE_UNIT = "tunit";

	public static final String PARAMETER_TIME_COVERAGE_UNIT = "coverageunit";

	public static final String PARAMETER_TIME_COVERAGE_START = "coveragestart";

	public static final String PARAMETER_TIME_COVERAGE_END = "coverageend";

	public static Geometry create(String geometry) {
		return makeGeometry(geometry, 0);
	}

	private static String encodeDimension(Dimension dim) {

		String ret = "";
		ret += dim.getType() == Type.SPACE
				? (dim.isGeneric() ? (dim.isRegular() ? "\u03a3" : "\u03c3") : (dim.isRegular() ? "S" : "s"))
				: (dim.getType() == Type.TIME
						? (dim.isGeneric() ? (dim.isRegular() ? "\u03a4" : "\u03c4") : (dim.isRegular() ? "T" : "t"))
						: /* TODO others */ "");
		ret += dim.getDimensionality();
		if (dim.shape() != null && !isUndefined(dim.shape())) {
			ret += "(";
			for (int i = 0; i < dim.shape().length; i++) {
				ret += (i == 0 ? "" : ",") + (dim.shape()[i] == INFINITE_SIZE ? "\u221E" : ("" + dim.shape()[i]));
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
		return ret;
	}

	/**
	 * Create a geometry from the structured session bean but don't add a shape if
	 * it's there.
	 * 
	 * @param scaleRef
	 * @return
	 */
	public static Geometry createGrid(ScaleReference scaleRef) {

		String spec = scaleRef.getSpaceUnit() == null ? "S1" : "S2";
		boolean hasTime = false;

		Geometry ret = Geometry.create(spec).withProjection("EPSG:4326").withBoundingBox(scaleRef.getEast(),
				scaleRef.getWest(), scaleRef.getSouth(), scaleRef.getNorth());

		if (scaleRef.getSpaceUnit() != null) {
			ret = ret.withGridResolution(scaleRef.getSpaceResolution() + " " + scaleRef.getSpaceUnit());
		}

		if (scaleRef.getStart() > 0) {
			ret = ret.withTemporalStart(scaleRef.getStart());
			hasTime = true;
		}
		if (scaleRef.getEnd() > 0) {
			ret = ret.withTemporalEnd(scaleRef.getEnd());
			hasTime = true;
		}
		if (scaleRef.getTimeResolutionDescription() != null) {
			ret = ret.withTemporalResolution(scaleRef.getTimeResolutionMultiplier() + "." + scaleRef.getTimeUnit());
			hasTime = true;
		}

		if (scaleRef.getTimeType() != null) {
			ret = ret.withTimeType(scaleRef.getTimeType());
		} else if (hasTime) {
			ret = ret.withTimeType("LOGICAL");
		}

		for (String key : scaleRef.getMetadata().keySet()) {
			ret = ret.withSpatialParameter(key, scaleRef.getMetadata().get(key));
		}

		return ret;
	}

	/**
	 * Create a geometry from a structured bean. Assumes time is there
	 * 
	 * @param bean
	 * @return
	 */
	public static Geometry create(ScaleReference scaleRef) {

		String spec = scaleRef.getSpaceUnit() == null ? "S1" : "S2";
		boolean hasTime = false;

		// if (scaleRef.getTimeGeometry() != null) {
		// spec = scaleRef.getTimeGeometry() + spec;
		// hasTime = true;
		// }

		Geometry ret = Geometry.create(spec).withProjection("EPSG:4326").withBoundingBox(scaleRef.getEast(),
				scaleRef.getWest(), scaleRef.getSouth(), scaleRef.getNorth());

		if (scaleRef.getSpaceUnit() != null) {
			ret = ret.withGridResolution(scaleRef.getSpaceResolution() + " " + scaleRef.getSpaceUnit());
		}

		if (scaleRef.getShape() != null) {
			ret = ret.withShape(scaleRef.getShape());
		}

		// if (scaleRef.getTimeGeometry() == null) {
		if (scaleRef.getStart() > 0) {
			ret = ret.withTemporalStart(scaleRef.getStart());
			hasTime = true;
		}
		if (scaleRef.getEnd() > 0) {
			ret = ret.withTemporalEnd(scaleRef.getEnd());
			hasTime = true;
		}
		if (scaleRef.getTimeResolutionDescription() != null) {
			ret = ret.withTemporalResolution(scaleRef.getTimeResolutionMultiplier() + "." + scaleRef.getTimeUnit());
			hasTime = true;
		}
		// }

		if (scaleRef.getTimeType() != null) {
			ret = ret.withTimeType(scaleRef.getTimeType());
		} else if (hasTime) {
			ret = ret.withTimeType("LOGICAL");
		}

		for (String key : scaleRef.getMetadata().keySet()) {
			ret = ret.withSpatialParameter(key, scaleRef.getMetadata().get(key));
		}

		return ret;
	}

	private static Geometry create(Iterable<Dimension> dims) {
		Geometry ret = new Geometry();
		for (Dimension dim : dims) {
			ret.scalar = false;
			ret.dimensions.add((DimensionImpl) dim);
		}
		return ret;
	}

	public static GeometryBuilder builder() {
		return new GeometryBuilder();
	}

	// if this comes from a scale, hold it here so we can produce it back when
	// asked.
	private IScale originalScale;

	private static Geometry emptyGeometry = new Geometry();

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
		Geometry ret = new Geometry();
		ret.scalar = true;
		return ret;
	}

	public static Geometry distributedIn(ExtentDimension key) {
		switch (key) {
		case AREAL:
			return create("S2");
		case LINEAL:
			return create("S1");
		case PUNTAL:
			return create("S0");
		case TEMPORAL:
			return create("T1");
		case VOLUMETRIC:
			return create("S3");
		case CONCEPTUAL:
		default:
			break;
		}
		return empty();
	}

	/**
	 * Encode into a string representation. Keys in parameter maps are sorted so the
	 * results can be compared for equality.
	 * 
	 * @return the string representation for the geometry
	 */
	public String encode(Encoding... options) {

		if (isEmpty()) {
			return "X";
		}

		if (isScalar()) {
			return "*";
		}

		// put time first
		List<Dimension> dims = new ArrayList<>(dimensions);
		dims.sort(new Comparator<Dimension>() {

			@Override
			public int compare(Dimension o1, Dimension o2) {
				return o1.getType() == Type.TIME ? -1 : 0;
			}
		});

		String ret = granularity == Granularity.MULTIPLE ? "#" : "";
		for (Dimension dim : dims) {
			ret += encodeDimension(dim);
		}
		if (child != null) {
			ret += "," + child.encode();
		}
		return ret;
	}

	private static boolean isUndefined(long[] shape) {
		for (long l : shape) {
			if (l < 0) {
				return true;
			}
		}
		return false;
	}

	private static String encodeVal(Object val) {
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
			} else if (long[].class.isAssignableFrom(val.getClass())) {
				for (long d : (long[]) val) {
					ret += (ret.length() == 1 ? "" : " ") + d;
				}
			} else if (float[].class.isAssignableFrom(val.getClass())) {
				for (float d : (float[]) val) {
					ret += (ret.length() == 1 ? "" : " ") + d;
				}
			} else if (boolean[].class.isAssignableFrom(val.getClass())) {
				for (boolean d : (boolean[]) val) {
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
		private boolean generic;
		private double coverage = 1.0;

		@Override
		public Type getType() {
			return type;
		}

		public DimensionImpl copy() {
			DimensionImpl ret = new DimensionImpl();
			ret.type = type;
			ret.regular = regular;
			ret.dimensionality = dimensionality;
			ret.shape = this.shape == null ? null : this.shape.clone();
			ret.parameters = new Parameters<>(this.parameters);
			ret.generic = this.generic;
			return ret;
		}

		@Override
		public boolean isRegular() {
			return regular;
		}

		@Override
		public boolean isGeneric() {
			return generic;
		}

		@Override
		public int getDimensionality() {
			return dimensionality;
		}

		public double getCoverage() {
			return coverage;
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

		@Override
		public String encode(Encoding... options) {
			return encodeDimension(this);
		}

		// @Override
		public long getOffset(long... offsets) {

			if (offsets == null) {
				return 0;
			}
			if (offsets.length != dimensionality) {
				throw new KlabIllegalArgumentException("geometry: cannot address a " + dimensionality
						+ "-dimensional extent with an offset array of lenght " + offsets.length);
			}
			if (shape == null) {
				throw new KlabIllegalArgumentException("geometry: cannot address a geometry with no shape");
			}

			if (offsets.length == 1) {
				return offsets[0];
			}

			if (this.type == Type.SPACE && offsets.length == 2) {

				/*
				 * TODO this is arbitrary and repeats the addressing in Grid. I just can't go
				 * over the entire codebase to just use this one at this moment. Should have a
				 * centralized offsetting strategy and use that everywhere, configuring it
				 * according to and extent types.
				 */
				return ((shape[1] - offsets[1] - 1) * shape[0]) + offsets[0];
			}

			return 0;
		}

		// @Override
		// public long getOffset(ILocator index) {
		// throw new IllegalArgumentException("getOffset() is not implemented on basic
		// geometry
		// dimensions");
		// }

		@Override
		public IParameters<String> getParameters() {
			return parameters;
		}

		public long[] getShape() {
			return shape;
		}

		public void setShape(long[] shape) {
			this.shape = shape;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public void setRegular(boolean regular) {
			this.regular = regular;
		}

		public void setDimensionality(int dimensionality) {
			this.dimensionality = dimensionality;
		}

		public void setGeneric(boolean generic) {
			this.generic = generic;
		}

		public boolean isCompatible(Dimension dimension) {

			if (type != dimension.getType()) {
				return false;
			}

			if ((generic && !dimension.isGeneric()) /* || (!generic && dimension.isGeneric()) */) {
				return false;
			}

			// TODO must enable a boundary shape to cut any geometry, regular or not, as
			// long
			// as the dimensionality agrees

			// if (regular && !(dimension.isRegular() || dimension.size() == 1)
			// || !regular && (dimension.isRegular() || dimension.size() == 1)) {
			// return false;
			// }

			return true;
		}

		@Override
		public ExtentDimension getExtentDimension() {
			switch (this.type) {
			case NUMEROSITY:
				return ExtentDimension.CONCEPTUAL;
			case SPACE:
				return ExtentDimension.spatial(this.dimensionality);
			case TIME:
				return ExtentDimension.TEMPORAL;
			default:
				break;
			}
			return null;
		}

		@Override
		public boolean isDistributed() {
			return size() > 1 || isRegular()
					|| (this.type == Type.TIME && "GRID".equals(parameters.get(PARAMETER_TIME_REPRESENTATION)));
		}

	}

	private List<DimensionImpl> dimensions = new ArrayList<>();
	private Granularity granularity = Granularity.SINGLE;
	private Geometry child;
	private boolean scalar;
	private Double coverage = null;

	private MultidimensionalCursor cursor;

	@Override
	public double getCoverage() {
		if (this.coverage == null) {
			this.coverage = 1.0;
			for (Dimension dim : getDimensions()) {
				this.coverage *= ((DimensionImpl) dim).coverage;
			}
		}
		return this.coverage;
	}

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

	void addDimension(DimensionImpl dimension) {
		dimensions.add(dimension);
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
			throw new KlabIllegalStateException("cannot set spatial parameters on a geometry without space");
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
		if (shapeSpecs == null && space != null) {
			space.getParameters().remove(PARAMETER_SPACE_SHAPE);
		} else if (space != null) {
			space.getParameters().put(PARAMETER_SPACE_SHAPE, shapeSpecs);
		}
		return this;
	}

	/**
	 * 
	 * @param gridResolution
	 * @return this
	 */
	public Geometry withGridResolution(String gridResolution) {
		Dimension space = getDimension(Type.SPACE);
		if (gridResolution == null && space != null) {
			space.getParameters().remove(PARAMETER_SPACE_GRIDRESOLUTION);
			((DimensionImpl) space).shape = space.getDimensionality() == 2 ? new long[] { 1, 1 } : new long[] { 1 };
		} else if (space != null) {
			space.getParameters().put(PARAMETER_SPACE_GRIDRESOLUTION, gridResolution);
		}
		return this;
	}

	public Geometry withSpatialParameter(String key, String value) {
		Dimension space = getDimension(Type.SPACE);
		if (space != null) {
			space.getParameters().put(key, value);
		}
		return this;
	}

	/**
	 * Return self if we have space, otherwise create a spatial dimension according
	 * to parameters and return the merged geometry.
	 * 
	 * @return
	 */
	public Geometry spatial(int dimensions, boolean regular) {
		if (getDimension(Type.SPACE) != null) {
			return this;
		}
		DimensionImpl space = new DimensionImpl();
		space.coverage = 1.0;
		space.dimensionality = dimensions;
		space.generic = false;
		space.regular = regular;
		space.type = Type.SPACE;
		return new Geometry(this, space);
	}

	/**
	 * Return self if we have space, otherwise create a spatial dimension according
	 * to parameters and return the merged geometry.
	 * 
	 * @return
	 */
	public Geometry temporal(boolean regular) {
		if (getDimension(Type.TIME) != null) {
			return this;
		}
		DimensionImpl space = new DimensionImpl();
		space.coverage = 1.0;
		space.dimensionality = 1;
		space.generic = false;
		space.regular = regular;
		space.type = Type.TIME;
		return new Geometry(this, space);
	}

	/**
	 * 
	 * @param shape
	 * @return this
	 */
	public Geometry withSpatialShape(long... shape) {
		Dimension space = getDimension(Type.SPACE);
		if (space == null) {
			space = addLogicalSpace(this);
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
		if (timeResolution == null && time != null) {
			time.getParameters().remove(PARAMETER_TIME_GRIDRESOLUTION);
			((DimensionImpl) time).shape = new long[] { 1 };
		} else if (time != null) {
			time.getParameters().put(PARAMETER_TIME_GRIDRESOLUTION, timeResolution);
		}
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
			time = addLogicalTime(this);
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
			space = addLogicalSpace(this);
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
			throw new KlabIllegalStateException("cannot set temporal parameters on a geometry without time");
		}
		time.getParameters().put(PARAMETER_TIME_PERIOD, new long[] { start, end });
		return this;
	}

	Geometry() {
	}

	private Geometry(Geometry geometry, DimensionImpl dimension) {
		this.scalar = false;
		LinkedHashMap<Dimension.Type, DimensionImpl> hash = new LinkedHashMap<>();
		for (DimensionImpl dim : geometry.dimensions) {
			hash.put(dim.type, dim);
		}
		hash.put(dimension.type, dimension);
		this.dimensions.addAll(hash.values());
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
			} else if ((c >= 'A' && c <= 'z') || c == 0x03C3 || c == 0x03C4 || c == 0x03A3 || c == 0x03A4) {
				DimensionImpl dimensionality = ret.newDimension();
				if (c == 'S' || c == 's' || c == 0x03C3 || c == 0x03A3) {

					dimensionality.type = Type.SPACE;
					if (c == 0x03C3 || c == 0x03A3) {
						dimensionality.generic = true;
					}
					dimensionality.regular = (c == 'S' || c == 0x03A3);

				} else if (c == 'T' || c == 't' || c == 0x03C4 || c == 0x03A4) {

					dimensionality.type = Type.TIME;
					if (c == 0x03C4 || c == 0x03A4) {
						dimensionality.generic = true;
					}
					dimensionality.regular = (c == 'T' || c == 0x03A4);

				} else {
					throw new KlabIllegalArgumentException("unrecognized geometry dimension identifier " + c);
					// if (dimDictionary.containsKey(Character.toLowerCase(c))) {
					// dimensionality.type = dimDictionary.get(Character.toLowerCase(c));
					// } else {
					// int n = dimDictionary.size() + 2;
					// dimDictionary.put(Character.toLowerCase(c), n);
					// dimensionality.type = n;
					// }
				}

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
					String[] dims = shape.toString().trim().split(",");
					long[] sdimss = new long[dims.length];
					for (int d = 0; d < dims.length; d++) {
						String dimspec = dims[d].trim();
						long dsize = NONDIMENSIONAL;
						if (!dimspec.isEmpty()) {
							dsize = dimspec.equals("\u221E") ? INFINITE_SIZE : Long.parseLong(dimspec);
						}
						sdimss[d] = dsize;
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
					if (!shape.toString().isEmpty()) {
						dimensionality.parameters.putAll(readParameters(shape.toString()));
					}
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
				throw new KlabIllegalArgumentException("wrong key/value pair in geometry definition: " + kvp);
			}
			String key = kk[0].trim();
			String val = kk[1].trim();
			val = decodeForSerialization(val);
			Object v = null;
			if (val.startsWith("[") && val.endsWith("]")) {
				v = NumberUtils.podArrayFromString(val, "\\s+", getParameterPODType(key));
			} else if (!PARAMETER_SPACE_SHAPE.equals(key) && NumberUtils.encodesLong(val)) {
				// This way all integers will be longs and the next won't be called - check if
				// that's OK. Must avoid shape parameters with WKB values that should stay
				// strings.
				v = Long.parseLong(val);
			} else if (!PARAMETER_SPACE_SHAPE.equals(key) && NumberUtils.encodesInteger(val)) {
				v = Integer.parseInt(val);
			} else if (!PARAMETER_SPACE_SHAPE.equals(key) && NumberUtils.encodesDouble(((String) val))) {
				v = Double.parseDouble(val);
			} else {
				v = val;
			}

			ret.put(key, v);

		}
		return ret;
	}

	/**
	 * Turn encoded entities back into separators used in geometries that would
	 * cause conflict in parameters transmitted as text.
	 * 
	 * @param val
	 * @return
	 */
	public static String decodeForSerialization(String val) {
		return val.replaceAll("&comma;", ",").replaceAll("&eq;", "=");
	}

	/**
	 * Translate separators used in geometries into harmless entities so that they
	 * won't cause conflict in parameters transmitted as text.
	 * 
	 * @param val
	 * @return
	 */
	public static String encodeForSerialization(String val) {
		return val.replaceAll(",", "&comma;").replaceAll("=", "&eq;");
	}

	private static Class<?> getParameterPODType(String kvp) {
		switch (kvp) {
		case PARAMETER_TIME_PERIOD:
			return Long.class;
		}
		return null;
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

		// System.out.println(separateTargets(ITime.class, 1, Dimension.Type.SPACE, 2,
		// 3));

		Geometry gg = create("σ1(866){authority=IMF.CL_AREA}");
		System.out.println(gg.toString());
		// System.out.println(separateTargets(ITime.class, Dimension.Type.SPACE, 2, 3));
	}

	@Override
	public boolean isEmpty() {
		return !scalar && dimensions.isEmpty() && child == null;
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
			if (dimension.size() != INFINITE_SIZE) {
				ret *= dimension.size();
			}
		}
		return ret;
	}

	public static boolean hasShape(IGeometry geometry) {
		for (Dimension dimension : geometry.getDimensions()) {
			if (dimension.shape() == null) {
				return false;
			}
		}
		return true;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ILocator> T as(Class<T> cls) {
		if (IScale.class.isAssignableFrom(cls)) {
			return (T) originalScale;
		} else if (IGeometry.class.isAssignableFrom(cls)) {
			return (T) this;
		} else if (Offset.class.isAssignableFrom(cls)) {
			if (!hasShape(this)) {
				throw new KlabIllegalStateException(
						"cannot see a geometry as an offset locator unless shape is specified for all extents");
			}
			return (T) new Offset(this);
		}
		throw new KlabIllegalArgumentException("cannot translate a simple geometry into a " + cls.getCanonicalName());
	}

	@Override
	public ILocator at(Object... locators) {
		return at(separateTargets(locators));
	}

	private ILocator at(List<DimensionTarget> targets) {

		if (!hasShape(this)) {
			throw new KlabIllegalStateException("Geometry has no specified shape: cannot create locators");
		}

		/*
		 * dimension-specific targets cannot be combined with an overall targets.
		 */
		DimensionTarget overall = null;
		for (DimensionTarget target : targets) {
			if (target.type == null) {
				overall = target;
				break;
			}
		}

		if (overall != null && targets.size() > 1) {
			throw new KlabIllegalStateException(
					"Geometry cannot be located with both dimension-specific and overall locators");
		}

		if (overall != null) {
			return new Offset(this, overall.offsets);
		}

		if (!targets.isEmpty()) {
			long[] pos = new long[this.dimensions.size()];
			int i = 0;
			for (Dimension dimension : this.dimensions) {
				boolean found = false;
				for (DimensionTarget target : targets) {

					if (target.coordinates != null || target.otherLocators != null) {
						// we don't handle these here
						throw new KlabIllegalStateException("Unrecognized locators for a Geometry: check usage");
					}

					if (target.type != null && target.type == dimension.getType()) {
						found = true;
						if (target.offsets != null) {
							if (target.offsets.length > 1) {
								pos[i] = dimension.getOffset(target.offsets);
							} else {
								pos[i] = target.offsets[0];
							}
						} else {
							pos[i] = dimension.size() == 1 ? 0 : -1;
						}
					}
					if (!found) {
						pos[i] = dimension.size() == 1 ? 0 : -1;
					}
				}
				i++;
			}

			return new Offset(this, pos);
		}

		// no arguments: locate this with this
		return this;
	}

	@Override
	public MultidimensionalCursor getCursor() {
		if (this.cursor == null) {
			this.cursor = new MultidimensionalCursor(this);
		}
		return this.cursor;
	}

	public static long computeOffset(long[] pos, IGeometry geometry) {
		return geometry.getCursor().getElementOffset(pos);
	}

	public static long[] computeOffsets(long pos, IGeometry geometry) {
		return geometry.getCursor().getElementIndexes(pos);
	}

	@Override
	public Iterator<ILocator> iterator() {
		return new GeometryIterator(this);
	}

	@Override
	public boolean isGeneric() {
		for (Dimension dimension : dimensions) {
			if (!dimension.isGeneric()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Merge in the passed geometry and modify our data accordingly. If the passed
	 * geometry is incompatible, return null without error.
	 * 
	 * In general, any geometry can merge in a scalar geometry (an empty one will
	 * become scalar); other dimensions can be inherited if they are not present, or
	 * they must have the same dimensionality in order to be kept without error. If
	 * the receiver has a generic dimension and the incoming dimension is not
	 * generic, the result adopts the specific one.
	 * 
	 * If we have a dimension that the other doesn't, just leave it there in the
	 * result.
	 * 
	 * @param geometry
	 * @return
	 */
	public Geometry merge(IGeometry geometry) {

		if (this.isEmpty()) {
			return create(geometry.encode());
		}

		if (geometry.isScalar() && geometry.isGeneric()) {
			return this;
		}

		Map<Dimension.Type, Dimension> res = new LinkedHashMap<>();
		for (Dimension dimension : getDimensions()) {
			res.put(dimension.getType(), ((DimensionImpl) dimension).copy());
		}

		// List<Dimension> result = new ArrayList<>();
		for (Dimension dimension : geometry.getDimensions()) {
			if (getDimension(dimension.getType()) == null) {
				res.put(dimension.getType(), ((DimensionImpl) dimension).copy());
			} else if (getDimension(dimension.getType()).isGeneric() && !dimension.isGeneric()) {
				// a specific dimension trumps a generic one
				res.put(dimension.getType(), ((DimensionImpl) dimension).copy());
			} else if (!((DimensionImpl) getDimension(dimension.getType())).isCompatible(dimension)) {
				return null;
			} else {
				Dimension myDimension = ((DimensionImpl) getDimension(dimension.getType())).copy();
				if (myDimension.size() == 0 && dimension.size() > 0) {
					// merging a distributed dimension makes us distributed
					((DimensionImpl) myDimension).shape = ((DimensionImpl) dimension).shape.clone();
				}
				if (myDimension.isRegular() && !dimension.isRegular() && dimension.size() > 1) {
					// merging an irregular dimension makes us irregular
					((DimensionImpl) myDimension).regular = false;
				}
				res.put(myDimension.getType(), myDimension);
			}
		}

		return create(res.values());

	}

	/**
	 * Return a copy of this geometry after removing the passed dimension. If the
	 * dimension isn't there, return a copy of this.
	 * 
	 * @param geometry
	 * @return
	 */
	public Geometry without(Dimension.Type dim) {

		List<Dimension> add = new ArrayList<>();
		for (Dimension dimension : getDimensions()) {
			if (dimension.getType() != dim) {
				add.add(((DimensionImpl) dimension).copy());
			}
		}

		Geometry ret = new Geometry();

		for (Dimension dimension : add) {
			ret.scalar = false;
			ret.dimensions.add((DimensionImpl) dimension);
		}

		return ret;
	}

	/**
	 * Return another geometry where all dimensions that are in the passed one
	 * override any we have of the same type.
	 * 
	 * @param geometry
	 * @return
	 */
	public Geometry override(IGeometry geometry) {

		List<Dimension> add = new ArrayList<>();
		for (Dimension dimension : getDimensions()) {
			if (geometry.getDimension(dimension.getType()) == null) {
				add.add(((DimensionImpl) dimension).copy());
			}
		}
		for (Dimension dimension : geometry.getDimensions()) {
			add.add(((DimensionImpl) dimension).copy());
		}

		Geometry ret = new Geometry();

		for (Dimension dimension : add) {
			ret.scalar = false;
			ret.dimensions.add((DimensionImpl) dimension);
		}

		return ret;
	}

	public String getLabel() {

		String prefix = "";
		if (size() > 0) {
			prefix = "distributed ";
		} else {
			for (Dimension d : dimensions) {
				if (d.isRegular()) {
					prefix = "distributed ";
					break;
				}
			}
		}
		if (scalar) {
			return "scalar";
		} else if (getDimension(Type.SPACE) != null && getDimension(Type.TIME) != null) {
			return prefix + "spatio-temporal";
		} else if (getDimension(Type.SPACE) != null) {
			return prefix + "spatial";
		} else if (getDimension(Type.TIME) != null) {
			return prefix + "temporal";
		}
		return "empty";
	}

	@Override
	public boolean isInfiniteTime() {
		return getDimension(Dimension.Type.TIME) != null && getDimension(Dimension.Type.TIME).size() == INFINITE_SIZE;
	}

	public void setOriginalScale(IScale originalScale) {
		this.originalScale = originalScale;
	}

	@Override
	public IGeometry getGeometry() {
		return originalScale;
	}

	/**
	 * Based on conventions, extract any parameters from a dimension that can
	 * provide location within a scale. Used internally in
	 * {@link IScale#at(Object...)}
	 * 
	 * @param extent
	 * @return
	 */
	public static Object[] getLocatorParameters(Dimension extent) {
		if (extent.getType() == Type.TIME && extent.getParameters().containsKey(PARAMETER_TIME_LOCATOR)) {
			return new Object[] { extent.getParameters().get(PARAMETER_TIME_LOCATOR) };
		} else if (extent.getType() == Type.SPACE && extent.getParameters().containsKey(PARAMETER_SPACE_LONLAT)) {
			double[] latlon = extent.getParameters().get(PARAMETER_SPACE_LONLAT, double[].class);
			return Utils.boxArray(latlon);
		} // TODO others maybe
		return null;
	}

	@Override
	public boolean is(String string) {

		/*
		 * compares dimension type and dimensionality; if both have a shape, shape is
		 * also compared.
		 */
		Geometry other = create(string);
		if (other.dimensions.size() == this.dimensions.size()) {
			for (int i = 0; i < other.dimensions.size(); i++) {
				if (other.dimensions.get(i).type != this.dimensions.get(i).type
						|| other.dimensions.get(i).dimensionality != this.dimensions.get(i).dimensionality
						|| other.dimensions.get(i).regular != this.dimensions.get(i).regular) {
					return false;
				}
				if (other.dimensions.get(i).shape != null || this.dimensions.get(i).shape != null) {
					if (!Arrays.equals(other.dimensions.get(i).shape, this.dimensions.get(i).shape)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public Geometry withGridResolution(IKimQuantity value) {
		Dimension space = getDimension(Type.SPACE);
		if (space == null) {
			space = addLogicalSpace(this);
		}
		space.getParameters().put(PARAMETER_SPACE_GRIDRESOLUTION, value.toString());
		return this;
	}

	public Geometry withTemporalEnd(Object value) {
		Dimension time = getDimension(Type.TIME);
		if (time == null) {
			time = addLogicalTime(this);
		}
		time.getParameters().put(PARAMETER_TIME_END, value);
		return this;
	}

	public Geometry withTimeType(String value) {
		Dimension time = getDimension(Type.TIME);
		if (time == null) {
			time = addLogicalTime(this);
		}
		time.getParameters().put(PARAMETER_TIME_REPRESENTATION, value);
		return this;
	}

	public Geometry withTemporalStart(Object value) {
		Dimension time = getDimension(Type.TIME);
		if (time == null) {
			time = addLogicalTime(this);
		}
		time.getParameters().put(PARAMETER_TIME_START, value);
		return this;
	}

	public Geometry withTemporalTransitions(long[] transitionPoints) {
		Dimension time = getDimension(Type.TIME);
		if (time == null) {
			time = addLogicalTime(this);
		}
		time.getParameters().put(PARAMETER_TIME_REPRESENTATION, ITime.Type.GRID);
		time.getParameters().put(PARAMETER_TIME_TRANSITIONS, transitionPoints);
		return this;
	}

	private Dimension addLogicalTime(Geometry geometry) {
		DimensionImpl ret = new DimensionImpl();
		ret.type = Dimension.Type.TIME;
		ret.dimensionality = 1;
		ret.coverage = 1.0;
		ret.generic = false;
		ret.regular = true;
		geometry.dimensions.add(0, ret);
		return ret;
	}

	private Dimension addLogicalSpace(Geometry geometry) {
		DimensionImpl ret = new DimensionImpl();
		ret.type = Dimension.Type.SPACE;
		ret.dimensionality = 2;
		ret.coverage = 1.0;
		ret.generic = false;
		ret.regular = true;
		geometry.dimensions.add(ret);
		return ret;
	}

}