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
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

public class Geometry implements IGeometry {

    private static final long  serialVersionUID               = 8430057200107796568L;

    /**
     * Bounding box as a double[]{minX, maxX, minY, maxY}
     */
    public static final String PARAMETER_SPACE_BOUNDINGBOX    = "bbox";

    /**
     * Latitude,longitude as a double[]{lon, lat}
     */
    public static final String PARAMETER_SPACE_LONLAT         = "latlon";

    /**
     * Projection code
     */
    public static final String PARAMETER_SPACE_PROJECTION     = "proj";

    /**
     * Grid resolution as a string "n unit"
     */
    public static final String PARAMETER_SPACE_GRIDRESOLUTION = "sgrid";

    /**
     * Shape specs in WKB
     */
    public static final String PARAMETER_SPACE_SHAPE          = "shape";

    /**
     * Time period as a long[]{startMillis, endMillis}
     */
    public static final String PARAMETER_TIME_PERIOD          = "period";

    /**
     * time period as a long
     */
    public static final String PARAMETER_TIME_GRIDRESOLUTION  = "tgrid";

    /**
     * Time representation: one of generic, specific, grid or real.
     */
    public static final String PARAMETER_TIME_REPRESENTATION  = "ttype";

    /**
     * Time scope: integer or floating point number of PARAMETER_TIME_SCOPE_UNITs.
     */
    public static final String PARAMETER_TIME_SCOPE           = "tscope";

    /**
     * Time scope unit: one of millennium, century, decade, year, month, week, day,
     * hour, minute, second, millisecond, nanosecond; use lowercase values of
     * {@link Resolution}.
     */
    public static final String PARAMETER_TIME_SCOPE_UNIT      = "tunit";

    public static Geometry create(String geometry) {
        return makeGeometry(geometry, 0);
    }

    public static GeometryBuilder builder() {
        return new GeometryBuilder();
    }

    private static Geometry emptyGeometry  = new Geometry();

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
    public String encode() {

        if (isEmpty()) {
            return "X";
        }

        if (isScalar()) {
            return "*";
        }

        String ret = granularity == Granularity.MULTIPLE ? "#" : "";
        for (Dimension dim : dimensions) {
            ret += dim.getType() == Type.SPACE ? (dim.isGeneric() ? "\u03c3" : (dim.isRegular() ? "S" : "s"))
                    : (dim.getType() == Type.TIME
                            ? (dim.isGeneric() ? "\u03c4" : (dim.isRegular() ? "T" : "t"))
                            : /* TODO others */ "");
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

        private Type               type;
        private boolean            regular;
        private int                dimensionality;
        private long[]             shape;
        private Parameters<String> parameters = new Parameters<>();
        private boolean            generic;

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
			
			if (generic && !dimension.isGeneric() || !generic && dimension.isGeneric()) {
				return false;
			}
			if (regular && !dimension.isRegular() || !regular && dimension.isRegular()) {
				return false;
			}
			
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

    }

    private List<DimensionImpl>              dimensions  = new ArrayList<>();
    private Granularity                      granularity = Granularity.SINGLE;
    private Geometry                         child;
    private boolean                          scalar;

    // only used to compute offsets if requested
    transient private MultidimensionalCursor cursor      = null;

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
            ((DimensionImpl) space).shape = space.getDimensionality() == 2 ? new long[] { 1, 1 }
                    : new long[] { 1 };
        } else if (space != null) {
            space.getParameters().put(PARAMETER_SPACE_GRIDRESOLUTION, gridResolution);
        }
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
            } else if ((c >= 'A' && c <= 'z') || c == 0x03C3 || c == 0x03C4) {
                DimensionImpl dimensionality = ret.newDimension();
                if (c == 'S' || c == 's' || c == 0x03C3) {
                    dimensionality.type = Type.SPACE;
                    if (c == 0x03C3) {
                        dimensionality.generic = true;
                    }
                } else if (c == 'T' || c == 't' || c == 0x03C4) {
                    dimensionality.type = Type.TIME;
                    if (c == 0x03C4) {
                        dimensionality.generic = true;
                    }
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
        Geometry g1 = create("S2");
        Geometry g2 = create("#S2T1,#T1");
        Geometry g3 = create("S2(200,100)");
        Geometry g4 = create("T1(23)S2(200,100)");
        Geometry g5 = create("S2(200,100){srid=EPSG:3040,bounds=[23.3 221.0 25.2 444.4]}T1(12)");
        System.out.println("hla");
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
        throw new IllegalArgumentException("geometry cannot use locator of type "
                + locator.getClass().getCanonicalName());
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

    @Override
    public boolean isGeneric() {
        for (Dimension dimension : dimensions) {
            if (dimension.isGeneric()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Merge in the passed geometry and modify our data accordingly. If the passed geometry
     * is incompatible, return null without error.
     * 
     * In general, any geometry can merge in a scalar geometry (an empty one will become 
     * scalar); other dimensions can be inherited if they are not present, or they must 
     * have the same dimensionality in order to be kept without error.
     * 
     * If we have a dimension that the other doesn't, just leave it there in the result.
     * 
     * @param geometry
     * @return
     */
    public Geometry merge(IGeometry geometry) {
    	
    	if (this.isEmpty()) {
    		return create(geometry.encode());
    	}
    	
    	if (geometry.isScalar()) {
    		return this;
    	}
    	
    	List<Dimension> add = new ArrayList<>();
    	for (Dimension dimension : geometry.getDimensions()) {
    		if (getDimension(dimension.getType()) == null) {
    			add.add(((DimensionImpl)dimension).copy());
    		} else if (!((DimensionImpl)getDimension(dimension.getType())).isCompatible(dimension)) {
    			return null;
    		} else {
    			Dimension myDimension = getDimension(dimension.getType());
    			if (myDimension.size() == 0 && dimension.size() > 0) {
    				// merging a distributed dimension makes us distributed
    				((DimensionImpl)myDimension).shape = ((DimensionImpl)dimension).shape.clone();
    			}
    			if (myDimension.isRegular() && !dimension.isRegular() && dimension.size() > 1) {
    				// merging an irregular dimension makes us irregular
    				((DimensionImpl)myDimension).regular = false;    
    			}
    		}
    	}
    	
    	Geometry ret = create(geometry.encode());
    	
    	for (Dimension dimension : add) {
    		ret.dimensions.add((DimensionImpl)dimension);
    	}
    	
    	return ret;
    }
    
    public String getLabel() {
        // TODO handle numerosity
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
    
}
