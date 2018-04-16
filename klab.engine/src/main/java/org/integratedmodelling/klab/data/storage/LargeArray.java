package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.utils.Pair;
import org.nd4j.linalg.api.ndarray.INDArray;

/**
 * A large array of arbitrary POD type, initialized through a geometry. Uses an independent ND4J to
 * hold the value of each temporal slice, and only builds a new slice upon assignment of a value
 * different from the same value in the most recent one. Each slice is off-heap and memory mapped
 * to use virtual memory. This may be conditioned to a parameter in the future. 
 * <p>
 * Storage in each slice is limited to {@link Integer#MAX_VALUE} elements as ND4J'API uses ints for
 * indexing. This should change in the future, but 2B+ should be plenty of states for a non-temporal
 * slice. Indexing in the array is long-based.
 * <p>
 * A slice is an array of ND4J arrays, typically containing one element. It can auto-promote to hold
 * multiple values for a state when an object is passed that is not POD and can be split up into
 * multiple PODs and reconstructed on read.
 * <p>
 * 
 * @author ferdinando.villa
 *
 * @param <T>
 */
public class LargeArray<T> {

  class Slice {
    INDArray[] data;
  }

  private IGeometry geometry;
  private Class<T> representation;
  private List<Pair<Long, Slice>> data = new ArrayList<>();
  
  public LargeArray(IGeometry geometry, Class<T> cls) {
    this.geometry = geometry;
    this.representation = cls;
  }
  
  public T get(long index) {
    // TODO find slice
    // TODO get/reconstruct with non-temporal offset
    return null;
  }

  public long set(long index, Object value) {
    // TODO find time
    // TODO set needSlice <- time is > current && most current value exists or differs
    // TODO if slice didn't exist or needSlice, create slice
    // TODO set value with non-temporal offset
    return 0;
  }
  

}
