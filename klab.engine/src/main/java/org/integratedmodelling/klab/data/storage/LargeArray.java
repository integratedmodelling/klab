package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.utils.MultidimensionalCursor;
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
public abstract class LargeArray<T> {

    class Slice {

        INDArray[] data;
    }

    // this may be higher than the time offset for the passed slice as we only store slices for
    // values that are different.
    private long maxTimeOffsetReferenced = -1;
    int timeOffset = -1;
    private IGeometry geometry;
    private Class<T> representation;
    private MultidimensionalCursor cursor;
    private List<Pair<Long, Slice>> data = new ArrayList<>();

    public LargeArray(IGeometry geometry, Class<T> cls) {
        this.geometry = geometry;
        int i = 0;
        for (Dimension dim : geometry.getDimensions()) {
            if (dim.getType() == Type.TIME) {
                this.timeOffset = i;
                break;
            }
            i++;
        }
        this.representation = cls;
        this.cursor = new MultidimensionalCursor(geometry);
    }

    public T get(long index) {

        if (timeOffset < 0) {
            return getObject(data.get(0).getSecond().data, index);
        }

        long ofs[] = cursor.getElementIndexes(index);
        long tfs = ofs[timeOffset];
        
        // TODO find time offset. If slice isn't there check that time was used before in a set operation.
        // TODO get/reconstruct with non-temporal offset

        return null;
    }

    /**
     * Reconstruct (if necessary) and return the object at the passed offset in the passed array set.
     * Array of data will contain one array unless an unpackageable non-POD is or was passed in this slice.
     * 
     * @param data
     * @return
     */
    protected abstract T getObject(INDArray[] data, long offset);

    /**
     * Put the passed object in the passed array set at the passed offset.
     * Array of data will contain one array unless an unpackageable non-POD is or was passed in this
     * slice.
     * 
     * @param value
     * @param data
     * @param offset
     */
    protected abstract void setObject(Object value, INDArray[] data, long offset);

    public boolean set(long index, Object value) {
        
        if (timeOffset < 0) {
            setObject(value, data.get(0).getSecond().data, index);
        }

        if (get(index).equals(value)) {
            return false;
        }
        
        long ofs[] = cursor.getElementIndexes(index);
        long tfs = ofs[timeOffset];

        // TODO find time offset
        // TODO set needSlice <- time is > current && most current value exists or differs
        // TODO if slice didn't exist or needSlice, create slice
        // TODO set value with non-temporal offset

        if (this.maxTimeOffsetReferenced < tfs) {
            this.maxTimeOffsetReferenced = tfs;
        }
        
        return true;
    }

}
