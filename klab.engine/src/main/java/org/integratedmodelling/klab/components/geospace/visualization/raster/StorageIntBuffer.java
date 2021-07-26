package org.integratedmodelling.klab.components.geospace.visualization.raster;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IStorage;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;

/**
 * A {@link DataBuffer data buffer} implementation to handle int type gridded {@link IState state} data.
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
public class StorageIntBuffer extends DataBuffer {
    private int noDataValue = Integer.MIN_VALUE;

    private IStorage<?> state;
    private Function<Object, Object> transformation;

    private int width;
    private int height;

    private int[] data = null;
    private int[][] bankData = null;

    private Grid grid;
    private IScale scale;

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
    public StorageIntBuffer(IStorage<?> state, IScale scale, ILocator locator, Function<Object, Object> transformation, int size,
            Integer noValue ) {
        super(DataBuffer.TYPE_INT, size);
        this.state = state;
        this.scale = scale;
        this.locator = locator;
        this.transformation = transformation;
        if (noValue != null) {
            noDataValue = noValue;
        }
        ISpace space = scale.getSpace();
        if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
            throw new IllegalArgumentException("Only gridded state objects are accepted for this buffer object.");
        }
        grid = (Grid) ((Space) space).getGrid();
        width = (int) grid.getXCells();
        height = (int) grid.getYCells();
    }

    public int[] getData() {
        return getData(0);
    }

    public int[] getData( int bank ) {
        if (data == null) {
            Offset ofs = locator.as(Offset.class);
            int[] dataArray = new int[height * width];
            int index = 0;
            for( int r = 0; r < height; r++ ) {
                for( int c = 0; c < width; c++ ) {
                    ofs.set(spaceDimension, grid.getOffset(c, r));
                    Object o = state.get(ofs);
                    if (o instanceof Number && !Observations.INSTANCE.isNodata(o)) {
                        if (transformation != null) {
                            o = transformation.apply(o);
                        }
                        dataArray[index] = ((Number) o).intValue();
                    } else if (o instanceof Boolean) {
                        if (transformation != null) {
                            o = transformation.apply(o);
                        }
                        dataArray[index] = (int) (((Boolean) o) ? 1. : 0.);
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

    public int[][] getBankData() {
        if (bankData == null) {
            getData(0);
            bankData = new int[][]{data};
        }
        return bankData;
    }

    public int getElem( int i ) {
        return (int) getValue(i, state, transformation);
    }

    public int getElem( int bank, int i ) {
        return (int) getValue(i, state, transformation);
    }

    public int getElemInt( int i ) {
        return getValue(i, state, transformation);
    }

    public int getElemInt( int bank, int i ) {
        return getValue(i, state, transformation);
    }

    public double getElemDouble( int i ) {
        return getValue(i, state, transformation);
    }

    public double getElemDouble( int bank, int i ) {
        return getValue(i, state, transformation);
    }

    public void setElem( int i, int val ) {
        setValue(val, i, state, transformation);
    }

    public void setElem( int bank, int i, int val ) {
        setValue(val, i, state, transformation);
    }

    public void setElemFloat( int i, int val ) {
        setValue(val, i, state, transformation);
    }

    public void setElemFloat( int bank, int i, int val ) {
        setValue(val, i, state, transformation);
    }

    public void setElemDouble( int i, double val ) {
        setValue((int)val, i, state, transformation);
    }

    public void setElemDouble(int bank, int i, double val ) {
        setValue((int)val, i, state, transformation);
    }

    private int getValue( int index, IStorage<?> state, Function<Object, Object> transformation ) {
        int y = (int) (index / width);
        int x = index % width;
        return getValue(x, y, state, transformation);
    }
    
    private void setValue(int value, int index, IStorage<?> state, Function<Object, Object> transformation ) {
        int y = (int) (index / width);
        int x = index % width;
        setValue(value, x, y, state, transformation);
    }

    private int getValue( int x, int y, IStorage<?> state, Function<Object, Object> transformation ) {
        long spaceOffset = grid.getOffset(x, y);
        Offset off = new Offset(scale, new long[]{0, spaceOffset});
        Object o = state.get(off);
        if (o instanceof Number && !Observations.INSTANCE.isNodata(o)) {
            if (transformation != null) {
                o = transformation.apply(o);
            }
            return ((Number) o).intValue();
        } else if (o instanceof Boolean) {
            if (transformation != null) {
                o = transformation.apply(o);
            }
            return (int) (((Boolean) o) ? 1. : 0.);
        }
        return noDataValue;
    }
    
    private void setValue(int value, int x, int y, IStorage<?> state, Function<Object, Object> transformation ) {
        long spaceOffset = grid.getOffset(x, y);
        Offset off = new Offset(scale, new long[]{0, spaceOffset});
        Object o = ((IStorage<Object>)state).put(value, off);
    }
}
