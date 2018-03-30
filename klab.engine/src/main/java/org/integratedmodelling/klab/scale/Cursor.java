package org.integratedmodelling.klab.scale;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.integratedmodelling.kim.utils.MultidimensionalCursor;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Mask;
import org.integratedmodelling.klab.engine.api.ICursor;

public class Cursor implements ICursor {

    long[]                 dimensions;
    long                   dmax = -1;
    int                    dind = -1;
    int                    type = 0;      // 1 = temporal; 2 = spatial
    // IExtent extent;
    Iterable<Long>         cursor;
    MultidimensionalCursor originalCursor;
    Mask                   mask;

    public Cursor(List<IExtent> extents, Iterable<Long> dimensionScanner, MultidimensionalCursor originalCursor,
            int dimIndex) {

        /*
         * TODO dimIndex may be -1, in which case we only specify ONE point and must behave properly.
         */

        this.cursor = dimensionScanner;
        this.originalCursor = originalCursor;
        dind = dimIndex;

        int i = 0;
        for (IExtent e : extents) {
            if (i == dind) {
                dmax = e.size();
                if (e instanceof ITime) {
                    type = 1;
                } else if (e instanceof ISpace) {
                    type = 2;
                }
                // extent = e;
                break;
            }
            i++;
        }
    }

    /*
     * creates an index that only has one offset
     */
    public Cursor(long elementOffset) {
        this.cursor = Collections.singleton(elementOffset);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Cursor && Arrays.equals(dimensions, ((Cursor) obj).dimensions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(dimensions);
    }

    long getOffsetFor(int originalOffset) {
        dimensions[dind] = originalOffset;
        return originalCursor.getElementOffset(dimensions);
    }

    @Override
    public Iterator<Long> iterator() {
        return cursor.iterator();
    }

    @Override
    public long size() {
        return dmax;
    }

    @Override
    public boolean isSpatial() {
        return type == 1;
    }

    @Override
    public boolean isTemporal() {
        return type == 2;
    }

    @Override
    public long[] getOffsets() {
        long[] ret = dimensions.clone();
        ret[dind] = -1;
        return ret;
    }

    @Override
    public boolean isActive(long offset) {
        return mask == null ? true : mask.isActive(offset);
    }

    @Override
    public boolean isScalar() {
        return size() == 1;
    }
}