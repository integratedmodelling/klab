package org.integratedmodelling.klab.components.geospace.visualization;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;

public class ReadonlyStateFloatBuffer extends DataBuffer {
	public static final float noDataValue = Float.NaN;

	private IState state;
	private Function<Object, Object> transformation;

	private int width;
	private int height;

	private float[] data = null;
	private float[][] bankData = null;

	private Grid grid;

	private ILocator locator;
	private int spaceDimension = 1;

	public ReadonlyStateFloatBuffer(IState state, ILocator locator, Function<Object, Object> transformation, int size) {
		super(DataBuffer.TYPE_FLOAT, size);
		this.state = state;
		this.locator = locator;
		this.transformation = transformation;
		ISpace space = state.getScale().getSpace();
		if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
			throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
		}
		grid = (Grid) ((Space) space).getGrid();
		width = (int) grid.getXCells();
		height = (int) grid.getYCells();
	}

	public float[] getData() {
		return getData(0);
	}

	public float[] getData(int bank) {
		if (data == null) {
			float[] dataArray = new float[height * width];
			int index = 0;
			for (int r = 0; r < height; r++) {
				for (int c = 0; c < width; c++) {
					long spaceOffset = grid.getOffset(c, r);
					Offset off = new Offset(state.getScale(), new long[] { 0, spaceOffset });
					Object o = state.get(off);
					if (o instanceof Number) {
						if (transformation != null) {
							o = transformation.apply(o);
						}
						dataArray[index] = ((Number) o).floatValue();
					} else {
						dataArray[index] = noDataValue;
					}

					dataArray[index] = ((Number) getValue(c, r, state, transformation)).floatValue();
					index++;
				}
			}
			data = dataArray;
		}
		return data;
	}

	public float[][] getBankData() {
		if (bankData == null) {
			Offset ofs = locator.as(Offset.class);

			float[][] dataMatrix = new float[height][width];
			for (int r = 0; r < height; r++) {
				for (int c = 0; c < width; c++) {
//					long spaceOffset = grid.getOffset(c, r);
//					Offset off = new Offset(state.getScale(), new long[] { 0, spaceOffset });
					ofs.set(spaceDimension, grid.getOffset(c, r));

					Object o = state.get(ofs);
					if (o instanceof Number) {
						if (transformation != null) {
							o = transformation.apply(o);
						}
						dataMatrix[r][c] = ((Number) o).floatValue();
					} else {
						dataMatrix[r][c] = noDataValue;
					}
				}
			}
			bankData = dataMatrix;
		}
		return bankData;
	}

	public int getElem(int i) {
		return (int) getValue(i, state, transformation);
	}

	public int getElem(int bank, int i) {
		return (int) getValue(i, state, transformation);
	}

	public float getElemFloat(int i) {
		return (float) getValue(i, state, transformation);
	}

	public float getElemFloat(int bank, int i) {
		return (float) getValue(i, state, transformation);
	}

	public double getElemDouble(int i) {
		return (double) getValue(i, state, transformation);
	}

	public double getElemDouble(int bank, int i) {
		return (double) getValue(i, state, transformation);
	}

	public void setElem(int i, int val) {
		throwReadOnly();
	}

	public void setElem(int bank, int i, int val) {
		throwReadOnly();
	}

	public void setElemFloat(int i, float val) {
		throwReadOnly();
	}

	public void setElemFloat(int bank, int i, float val) {
		throwReadOnly();
	}

	public void setElemDouble(int i, double val) {
		throwReadOnly();
	}

	public void setElemDouble(int bank, int i, double val) {
		throwReadOnly();
	}

	private void throwReadOnly() {
		throw new RuntimeException("The buffer is readonly.");
	}

	// TODO if this works add methods to avoid autoboxing
	public Object getValue(int index, IState state, Function<Object, Object> transformation) {
		int y = (int) (index / width);
		int x = index % width;
		return getValue(x, y, state, transformation);
	}

	public Object getValue(int x, int y, IState state, Function<Object, Object> transformation) {
		long spaceOffset = grid.getOffset(x, y);
		Offset off = new Offset(state.getScale(), new long[] { 0, spaceOffset });
		Object o = state.get(off);
		if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
			return noDataValue;
		} else if (o instanceof Number) {
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
