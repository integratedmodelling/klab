package org.integratedmodelling.klab.components.geospace.visualization;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.util.function.Function;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;

public class StateRaster extends Raster {

	private IScale scale;
	private IState state;
	private Function<Object, Object> transformation = null;

	public StateRaster(IState state) {
		super(getSampleModel(state), getOrigin(state));
		this.state = state;

		ISpace space = state.getScale().getSpace();
		if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
			throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
		}
		Grid grid = (Grid) ((Space) space).getGrid();
		minX = 0;
		minY = 0;
		width = (int) grid.getXCells();
		height = (int) grid.getYCells();

		scale = state.getScale();

	}

	private static Point getOrigin(IState state) {
		return new Point(0, 0);
	}

	private static SampleModel getSampleModel(IState state) {
		int dataType = getType();

		ISpace space = state.getScale().getSpace();
		if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
			throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
		}
		Grid grid = (Grid) ((Space) space).getGrid();

		int width = (int) grid.getXCells();
		int height = (int) grid.getYCells();
		return new ComponentSampleModel(dataType, width, height, 1, width, new int[] { 0 });
	}

	private static int getType() {
		// TODO make this work once needed, for now float is only type
//		int dataType = DataBuffer.TYPE_DOUBLE;
//		
//		if (dataClass != null) {
//			if (dataClass.isAssignableFrom(Integer.class)) {
//				dataType = DataBuffer.TYPE_INT;
//			} else if (dataClass.isAssignableFrom(Float.class)) {
//				dataType = DataBuffer.TYPE_FLOAT;
//			} else if (dataClass.isAssignableFrom(Byte.class)) {
//				dataType = DataBuffer.TYPE_BYTE;
//			} else if (dataClass.isAssignableFrom(Short.class)) {
//				dataType = DataBuffer.TYPE_SHORT;
//			}
//		}

		return DataBuffer.TYPE_FLOAT;
	}

	protected StateRaster(SampleModel sampleModel, Point origin) {
		super(sampleModel, origin);
	}

	/**
	 * Returns the bounding Rectangle of this Raster. This function returns the same
	 * information as getMinX/MinY/Width/Height.
	 * 
	 * @return the bounding box of this <code>Raster</code>.
	 */
	public Rectangle getBounds() {
		return new Rectangle(minX, minY, width, height);
	}

	public DataBuffer getDataBuffer() {
		return dataBuffer;
	}

	public SampleModel getSampleModel() {
		return sampleModel;
	}

	public Object getDataElements(int x, int y, Object outData) {
//		return ReadonlyStateBuffer.getValue(x - sampleModelTranslateX, y - sampleModelTranslateY, state,
//				transformation);
		return null;
	}

	public Object getDataElements(int x, int y, int w, int h, Object outData) {
		return sampleModel.getDataElements(x - sampleModelTranslateX, y - sampleModelTranslateY, w, h, outData,
				dataBuffer);
	}

	public int[] getPixel(int x, int y, int iArray[]) {
		return sampleModel.getPixel(x - sampleModelTranslateX, y - sampleModelTranslateY, iArray, dataBuffer);
	}

	public float[] getPixel(int x, int y, float fArray[]) {
		return sampleModel.getPixel(x - sampleModelTranslateX, y - sampleModelTranslateY, fArray, dataBuffer);
	}

	public double[] getPixel(int x, int y, double dArray[]) {
		return sampleModel.getPixel(x - sampleModelTranslateX, y - sampleModelTranslateY, dArray, dataBuffer);
	}

	public int[] getPixels(int x, int y, int w, int h, int iArray[]) {
		return sampleModel.getPixels(x - sampleModelTranslateX, y - sampleModelTranslateY, w, h, iArray, dataBuffer);
	}

	public float[] getPixels(int x, int y, int w, int h, float fArray[]) {
		return sampleModel.getPixels(x - sampleModelTranslateX, y - sampleModelTranslateY, w, h, fArray, dataBuffer);
	}

	public double[] getPixels(int x, int y, int w, int h, double dArray[]) {
		return sampleModel.getPixels(x - sampleModelTranslateX, y - sampleModelTranslateY, w, h, dArray, dataBuffer);
	}

	public int getSample(int x, int y, int b) {
		return sampleModel.getSample(x - sampleModelTranslateX, y - sampleModelTranslateY, b, dataBuffer);
	}

	public float getSampleFloat(int x, int y, int b) {
		return sampleModel.getSampleFloat(x - sampleModelTranslateX, y - sampleModelTranslateY, b, dataBuffer);
	}

	public double getSampleDouble(int x, int y, int b) {
		return sampleModel.getSampleDouble(x - sampleModelTranslateX, y - sampleModelTranslateY, b, dataBuffer);
	}

	public int[] getSamples(int x, int y, int w, int h, int b, int iArray[]) {
		return sampleModel.getSamples(x - sampleModelTranslateX, y - sampleModelTranslateY, w, h, b, iArray,
				dataBuffer);
	}

	public float[] getSamples(int x, int y, int w, int h, int b, float fArray[]) {
		return sampleModel.getSamples(x - sampleModelTranslateX, y - sampleModelTranslateY, w, h, b, fArray,
				dataBuffer);
	}

	public double[] getSamples(int x, int y, int w, int h, int b, double dArray[]) {
		return sampleModel.getSamples(x - sampleModelTranslateX, y - sampleModelTranslateY, w, h, b, dArray,
				dataBuffer);
	}

}
