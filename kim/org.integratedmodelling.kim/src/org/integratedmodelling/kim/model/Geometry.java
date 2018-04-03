package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.utils.MultidimensionalCursor;
import org.integratedmodelling.kim.utils.Utils;

public class Geometry implements IGeometry {

  private static final long serialVersionUID = 8430057200107796568L;

  public static Geometry create(String geometry) {
    return makeGeometry(geometry, 0);
  }

  private static Geometry emptyGeometry  = new Geometry();
  private static Geometry scalarGeometry = makeGeometry("*", 0);

  /**
   * Create and return an empty geometry.
   * 
   * @return
   */
  public static Geometry empty() {
    return emptyGeometry;
  }

  public static Geometry scalar() {
    return scalarGeometry;
  }

  /*
   * dictionary for the IDs of any dimension types that are not space or time.
   */
  // private static Map<Character, Integer> dimDictionary = new HashMap<>();

  static class DimensionImpl implements Dimension {

    private Type    type;
    private boolean regular;
    private int     dimensionality;
    private long[]  shape;

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

  }

  private List<DimensionImpl> dimensions  = new ArrayList<>();
  private Granularity         granularity = Granularity.SINGLE;
  private Geometry            child;
  private boolean             scalar;
  
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

  protected Geometry() {}

  /*
   * read the geometry defined starting at the i-th character
   */
  private static Geometry makeGeometry(String geometry, int i) {

    Geometry ret = new Geometry();

    if (geometry.equals("*")) {
      ret.scalar = true;
      return ret;
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
//          if (dimDictionary.containsKey(Character.toLowerCase(c))) {
//            dimensionality.type = dimDictionary.get(Character.toLowerCase(c));
//          } else {
//            int n = dimDictionary.size() + 2;
//            dimDictionary.put(Character.toLowerCase(c), n);
//            dimensionality.type = n;
//          }
        }

        dimensionality.regular = Character.isUpperCase(c);

        idx++;
        if (geometry.charAt(idx) == '.') {
          dimensionality.dimensionality = NONDIMENSIONAL;
        } else {
          dimensionality.dimensionality = Integer.parseInt("" + geometry.charAt(idx));
        }
        
        if (geometry.length() > (idx+1) && geometry.charAt(idx+1) == '(') {
          idx += 2;
          String shape = "";
          while (geometry.charAt(idx) != ')') {
            shape += geometry.charAt(idx);
            idx++;
          }
          String[] dims = shape.split(",");
          long[] sdimss = new long[dims.length];
          for (int d = 0; d < dims.length; d++) {
            sdimss[d] = Long.parseLong(dims[d].trim());
          }
          dimensionality.dimensionality = sdimss.length;
          dimensionality.shape = sdimss;
        }

        ret.dimensions.add(dimensionality);

      } else if (c == ',') {
        ret.child = makeGeometry(geometry, idx + 1);
        break;
      }
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
    System.out.println("ha");
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
   * The simplest locator possible in a non-semantic geometry: offsets along a dimension.
   * 
   * @param dimensionType
   * @param offsets
   * @return
   */
  public ILocator locate(long... offsets) {
    return new OffsetLocator(offsets);
  }

  @Override
  public ILocator at(ILocator locator) {
    if (locator instanceof OffsetLocator) {
      
    }
    throw new IllegalArgumentException("geometry cannot use locator of type " + locator.getClass().getCanonicalName());
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

    public long getOffset() {
      return offset;
    }
    
    @Override
    public ILocator at(ILocator locator) {
      throw new IllegalArgumentException("offset locator cannot be further located");
    }
  }

  public long computeOffset(long[] offsets) {
    
    // trivial case
    if (offsets.length == 1 && dimensions.size() == 1 && dimensions.get(0).shape.length == 1) {
      return offsets[0];
    }
    if (this.cursor == null) {
      
    }
    return UNDEFINED;
  }

  @Override
  public long getOffset(ILocator index) {
    if (index instanceof OffsetLocator) {
      return ((OffsetLocator)index).getOffset();
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
}
