package org.integratedmodelling.klab.components.geospace.visualization.raster;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;

/**
 * A {@link DataBuffer data buffer} implementation to handle float type gridded {@link IState state} data.
 * 
 * Following assumptions are currently made on the state object and its locator companion:
 * 
 * <ul>
 * <li>the state represents a grid object</li>
 * <li>the values contained are {@link Number} objects</li>
 * <li>the locator object is used as starting {@link Offset} object to 
 *      ensure the right choice of index for the time dimension, while the 
 *      space position is updated from the {@link Grid} object using row/col</li>
 * <li>the raster has a single bank (0)</li>
 * </ul>
 * 
 * @author Andrea Antonello
 */
public class ReadonlyStateFloatBuffer extends DataBuffer {
    private float noDataValue = Float.NaN;

    private IState state;
    private Function<Object, Object> transformation;

    private int width;
    private int height;

    private float[] data = null;
    private float[][] bankData = null;

    private Grid grid;

    private ILocator locator;
    private int spaceDimension = 1;

    /**
     * Constructor of the buffer.
     * 
     * @param state the {@link IState} object to get the data from.
     * @param locator the {@link ILocator} object to get the data index from.
     * @param transformation optional data transformation object.
     * @param size the size of the data. In case of gridded data that is rows*cols.
     * @param noValue optional novalue.
     */
    public ReadonlyStateFloatBuffer( IState state, ILocator locator, Function<Object, Object> transformation, int size,
            Float noValue ) {
        super(DataBuffer.TYPE_FLOAT, size);
        this.state = state;
        this.locator = locator;
        this.transformation = transformation;
        if (noValue != null) {
            noDataValue = noValue;
        }
        ISpace space = state.getScale().getSpace();
        if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
            throw new IllegalArgumentException("Only gridded state objects are accepted for this buffer object.");
        }
        grid = (Grid) ((Space) space).getGrid();
        width = (int) grid.getXCells();
        height = (int) grid.getYCells();
    }

    public float[] getData() {
        return getData(0);
    }

    public float[] getData( int bank ) {
        if (data == null) {
            Offset ofs = locator.as(Offset.class);
            float[] dataArray = new float[height * width];
            int index = 0;
            for( int r = 0; r < height; r++ ) {
                for( int c = 0; c < width; c++ ) {
                    ofs.set(spaceDimension, grid.getOffset(c, r));
                    Object o = state.get(ofs);
                    if (o instanceof Number && !Observations.INSTANCE.isNodata(o)) {
                        if (transformation != null) {
                            o = transformation.apply(o);
                        }
                        dataArray[index] = ((Number) o).floatValue();
                    } else if (o instanceof Boolean) {
                        if (transformation != null) {
                            o = transformation.apply(o);
                        }
                        dataArray[index] = (float) (((Boolean) o) ? 1. : 0.);
                    } else if (o instanceof IConcept) {
                        if (transformation != null) {
                            o = transformation.apply(o);
                        }
                        dataArray[index] = (float) state.getDataKey().reverseLookup((IConcept) o);
                    } else {
                        dataArray[index] = noDataValue;
                    }
                    index++;
                }
            }
            data = dataArray;
        }
        return data;
    }

    public float[][] getBankData() {
        if (bankData == null) {
            getData(0);
            bankData = new float[][]{data};
        }
        return bankData;
    }

    public int getElem( int i ) {
        return (int) getValue(i, state, transformation);
    }

    public int getElem( int bank, int i ) {
        return (int) getValue(i, state, transformation);
    }

    public float getElemFloat( int i ) {
        return getValue(i, state, transformation);
    }

    public float getElemFloat( int bank, int i ) {
        return getValue(i, state, transformation);
    }

    public double getElemDouble( int i ) {
        return getValue(i, state, transformation);
    }

    public double getElemDouble( int bank, int i ) {
        return getValue(i, state, transformation);
    }

    public void setElem( int i, int val ) {
        throwReadOnly();
    }

    public void setElem( int bank, int i, int val ) {
        throwReadOnly();
    }

    public void setElemFloat( int i, float val ) {
        throwReadOnly();
    }

    public void setElemFloat( int bank, int i, float val ) {
        throwReadOnly();
    }

    public void setElemDouble( int i, double val ) {
        throwReadOnly();
    }

    public void setElemDouble( int bank, int i, double val ) {
        throwReadOnly();
    }

    private void throwReadOnly() {
        throw new RuntimeException("The buffer is readonly.");
    }

    private float getValue( int index, IState state, Function<Object, Object> transformation ) {
        int y = (int) (index / width);
        int x = index % width;
        return getValue(x, y, state, transformation);
    }

    private float getValue( int x, int y, IState state, Function<Object, Object> transformation ) {
        long spaceOffset = grid.getOffset(x, y);
        Offset off = new Offset(state.getScale(), new long[]{0, spaceOffset});
        Object o = state.get(off);
        if (o instanceof Number && !Observations.INSTANCE.isNodata(o)) {
            if (transformation != null) {
                o = transformation.apply(o);
            }
            return ((Number) o).floatValue();
        } else if (o instanceof Boolean) {
            if (transformation != null) {
                o = transformation.apply(o);
            }
            return (float) (((Boolean) o) ? 1. : 0.);
        } else if (o instanceof IConcept) {
            if (transformation != null) {
                o = transformation.apply(o);
            }
            return (float) state.getDataKey().reverseLookup((IConcept) o);
        }
        return noDataValue;
    }
}
