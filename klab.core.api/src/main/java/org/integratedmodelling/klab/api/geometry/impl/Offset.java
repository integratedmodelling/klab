package org.integratedmodelling.klab.api.geometry.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.integratedmodelling.klab.api.exceptions.KValidationException;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.KGeometry.Dimension;
import org.integratedmodelling.klab.api.geometry.KLocator;
import org.integratedmodelling.klab.api.knowledge.observation.scale.KScale;
import org.integratedmodelling.klab.api.knowledge.observation.scale.space.KSpace;
import org.integratedmodelling.klab.api.knowledge.observation.scale.time.KTime;

/**
 * The simplest locator, using only offsets and initializable through string specifications so that
 * it can be used across connections. Can always convert its located dimensions to dimensions or
 * located geometries using {@link #at(ILocator)}; actual extents or scales can also be obtained
 * from it as long as the geometry identified in the locator is a scale.
 * <p>
 * TODO document the string specification: examples below for now.
 * <ul>
 * <li>n,m</li>
 * <li>n,(x,y)</li>
 * <li>*,(x,y)</li>
 * <li>S2(10,10)@(1,4)</li>
 * <li>T1(3)S2(10,10)@1,(1,4)</li>
 * <li>T1(3)S2(10,10)@1,*</li>
 * </ul>
 * The offset can in turn be iterated to obtain more offsets (and those only).
 * 
 * @author Ferd
 *
 */
public class Offset implements KLocator {

    /**
     * Position as offsets with the dimensionality of the target. If a scale, one linear offset per
     * extent. If an extent, one per dimension.
     */
    public long[] pos;

    /**
     * Linear offset, only different from pos[0] if length > 1 and negative if pos == null or
     * !scalar.
     */
    public long linear = -1;

    /**
     * True if offsets point to a single value and iteration only yields self; false if one or more
     * of the offsets is negative, i.e. iteration produces all offsets along the unlocated
     * dimensions.
     */
    public boolean scalar = true;

    /**
     * Coverage
     */
    public double coverage = 1.0;

    /**
     * Dimensionality of the offsets, or 0 if undefined.
     */
    public int length;

    /**
     * The located geometry, possibly a IScale, never null.
     */
    private KGeometry geometry = null;

    private MultidimensionalCursor cursor;

    Offset() {
    }

    /**
     * A scanner for the entire geometry. All dimensions are unlocked unless their size is 1.
     * 
     * @param geometry
     */
    public Offset(KGeometry geometry) {
        this.geometry = geometry;
        this.pos = new long[geometry.getDimensions().size()];
        int i = 0;
        for (Dimension dimension : geometry.getDimensions()) {
            this.pos[i++] = dimension.size() == 1 ? 0 : -1;
        }
        this.length = this.pos.length;
        this.linear = 0;
        this.scalar = geometry.size() == 1;
    }

    public Offset(KGeometry geometry, long[] pos, double coverage) {
        this(geometry, pos);
        this.coverage = coverage;
    }

    public MultidimensionalCursor getCursor() {
        if (this.cursor == null) {
            this.cursor = new MultidimensionalCursor(geometry);
        }
        return this.cursor;
    }

    public long computeOffset(long[] pos, KGeometry geometry) {
        return getCursor().getElementOffset(pos);
    }

    public long[] computeOffsets(long pos, KGeometry geometry) {
        return getCursor().getElementIndexes(pos);
    }

    /**
     * A scanner for the passed offsets of the passed geometry. Dimensions with pos < 0 are
     * unlocked.
     * 
     * @param geometry
     */
    public Offset(KGeometry geometry, long[] pos) {

        this.geometry = geometry;
        if (pos.length == 1 && geometry.getDimensions().size() > 1) {
            pos = computeOffsets(pos[0], geometry);
        }

        if (pos.length == geometry.getDimensions().size()) {
            this.pos = pos;
            this.length = pos.length;
            for (long l : pos) {
                if (l < 0) {
                    this.scalar = false;
                    break;
                }
            }
            if (this.scalar) {
                this.linear = computeOffset(this.pos, geometry);
            }
        } else {
            throw new IllegalStateException(geometry.getDimensions().size()
                    + "-dimensional geometry cannot be initialized with offsets of length " + pos.length);
        }
    }
    //
    // public Offset(IGrid.Cell cell) {
    // // TODO Auto-generated constructor stub
    // }

    /**
     * Pass a geometry to be located. If the specifications contain a geometry component and the
     * passed geometry is not null, the geometry in the specification is ignored.
     * 
     * @param spec
     * @param geometry
     * @return
     */
    public static Offset create(String spec, KGeometry geometry) {
        Offset ret = new Offset();
        int at = spec.indexOf('@');
        if (at > 0) {
            spec = spec.substring(at + 1);
        }
        ret.pos = read(spec, geometry);
        ret.scalar = true;
        for (long l : ret.pos) {
            if (l < 0) {
                ret.scalar = false;
                break;
            }
        }
        ret.geometry = geometry;
        ret.length = ret.pos.length;
        ret.linear = ret.computeOffset(ret.pos, ret.geometry);
        return ret;
    }

    private static long[] read(String spec, KGeometry geometry) {

        long[] sret = new long[geometry.getDimensions().size()];
        StringTokenizer tokenizer = new StringTokenizer(spec, "(,)*\u221E", true);

        int i = 0, ii = 0;
        long[] internal = null;

        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.equals("(")) {
                internal = new long[geometry.getDimensions().get(i).getDimensionality()];
            } else if (token.equals(")")) {

                long nl = 0;
                if (internal.length > 0) {
                    Dimension d = geometry.getDimensions().get(i);
                    nl = internal.length == 1 ? internal[0] : d.offset(internal);
                }
                if (i < sret.length) {
                    sret[i++] = nl;
                } else {
                    throw new KValidationException("locator mismatches the size of the dimensions");

                }
                internal = null;
                ii = 0;

            } else if (!token.equals(",")) {
                Long l = token.equals("*") ? -1l : (token.equals("\u221E") ? Long.MAX_VALUE : Long.parseUnsignedLong(token));
                if (internal != null) {
                    internal[ii++] = l;
                } else if (i < sret.length) {
                    sret[i++] = l;
                } else {
                    throw new KValidationException("locator mismatches the size of the dimensions");

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
    public static Offset create(String spec) {
        Offset ret = new Offset();
        int at = spec.indexOf('@');
        if (at > 0) {
            ret.geometry = Geometry.create(spec.substring(0, at));
            ret.pos = read(spec.substring(at + 1), ret.geometry);
            ret.linear = ret.computeOffset(ret.pos, ret.geometry);
        } else {
            throw new KValidationException("locator string must contain both a geometry and a locator, separated by @");
        }
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends KLocator> T as(Class<T> cls) {
        if (Offset.class.isAssignableFrom(cls)) {
            return (T) this;
        } else if (KGeometry.class.isAssignableFrom(cls)) {
            return (T) geometry;
        } else if (geometry instanceof KScale) {
            if (KScale.class.isAssignableFrom(cls)) {
                return (T) geometry;
            } else if (KSpace.class.isAssignableFrom(cls)) {
                if (((KScale) geometry).getSpace() != null) {
                    return (T) ((KScale) geometry).getSpace();
                }
            } else if (KTime.class.isAssignableFrom(cls)) {
                if (((KScale) geometry).getTime() != null) {
                    return (T) ((KScale) geometry).getTime();
                }
            }
        }
        throw new IllegalArgumentException("cannot adapt this offset to a " + cls.getCanonicalName());
    }

    @Override
    public KGeometry geometry() {
        return geometry;
    }

    public void setGeometry(KGeometry geometry) {
        this.geometry = geometry;
    }

    public String toString() {
        String oofs = "";
        for (int i = 0; i < pos.length; i++) {
            if (!oofs.isEmpty()) {
                oofs += ",";
            }
            oofs += (pos[i] == Geometry.INFINITE_SIZE ? "\u221E" : ("" + pos[i]));
        }
        return oofs + " in " + geometry;
    }

    /**
     * Return the linear offset for the passed dimension of the original geometry, or -1
     * 
     * @return
     */
    public long getOffset(Dimension.Type dimension) {

        for (int i = 0; i < geometry.getDimensions().size(); i++) {
            if (geometry.getDimensions().get(i).getType() == dimension) {
                return pos[i];
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        String g1 = "S2(431999,27599){bbox=[-179.99999999999966 179.99999999999835 59.99999999999997 82.99999999999997],proj=EPSG:4326}@(30,40)";
        String g2 = "T1(3)S2(431999,27599){bbox=[-179.99999999999966 179.99999999999835 59.99999999999997 82.99999999999997],proj=EPSG:4326}@2,(30,40)";

        System.out.println(create(g1));
        System.out.println(create(g2));

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((geometry == null) ? 0 : geometry.hashCode());
        result = prime * result + (int) (linear ^ (linear >>> 32));
        result = prime * result + Arrays.hashCode(pos);
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
        Offset other = (Offset) obj;
        if (geometry == null) {
            if (other.geometry != null)
                return false;
        } else if (!geometry.equals(other.geometry))
            return false;
        if (linear != other.linear)
            return false;
        if (!Arrays.equals(pos, other.pos))
            return false;
        return true;
    }

    @Override
    public Iterator<KLocator> iterator() {
        return new GeometryIterator(geometry, this);
    }

    public static Offset empty() {
        return new Offset();
    }

    @Override
    public double getCoverage() {
        return coverage;
    }

    /**
     * Allow setting of the offset at a given spaceDimension.
     * 
     * <p>
     * This allows for example to use one single offset object to loop through a complete state
     * object at a given time by just changing, for example, the space index.
     * 
     * @param spaceDimension the dimension at which to set the offset.
     * @param offset the new offset for the object.
     */
    public void set(int spaceDimension, long offset) {
        pos[spaceDimension] = offset;
    }

    public KLocator at(long... offsets) {
        for (int i = 0; i < offsets.length; i++) {
            this.pos[i] = offsets[i];
        }
        this.linear = this.pos.length == 1 ? this.pos[0] : computeOffset(this.pos, geometry);

        return this;
    }

    /**
     * Pick the dimensions that are represented in the passed geometry from a locator using a
     * higher-dimensional one. The result is in the passed geometry, no check of conformance is
     * done.
     * 
     * @param geometry
     * @return
     */
    public Offset reduceTo(KGeometry geometry) {
        Offset ret = new Offset();
        ret.geometry = geometry;
        List<Long> positions = new ArrayList<>();
        int i = 0;
        for (Dimension dimension : this.geometry.getDimensions()) {
            if (geometry.dimension(dimension.getType()) != null) {
                positions.add(this.pos[i]);
            }
            i++;
        }
        ret.pos = new long[positions.size()];
        i = 0;
        for (Long l : positions) {
            ret.pos[i++] = l;
        }

        ret.linear = ret.pos.length == 1 ? ret.pos[0] : computeOffset(ret.pos, geometry);
        ret.length = ret.pos.length;
        ret.scalar = geometry.size() == 1;

        return ret;
    }

}
