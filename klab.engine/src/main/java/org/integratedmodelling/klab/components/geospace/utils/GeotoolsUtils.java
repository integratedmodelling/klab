package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.media.jai.RasterFactory;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Range;

public enum GeotoolsUtils {

	INSTANCE;

	Map<IConcept, Integer> conceptMap = new HashMap<>();
	GridCoverageFactory rasterFactory = new GridCoverageFactory();

	/**
	 * Turn a state into a grid coverage.
	 * 
	 * @param state
	 * @return a Geotools grid coverage
	 * @throws IllegalArgumentException if the state is not suitable for a raster
	 *                                  representation.
	 */
	public GridCoverage2D stateToCoverage(IState state, ILocator locator, float noDataValue) {
		return stateToCoverage(state.at(locator), DataBuffer.TYPE_FLOAT, noDataValue);
	}

	public GridCoverage2D stateToCoverage(IState state) {
		return stateToCoverage(state, DataBuffer.TYPE_FLOAT, Float.NaN);
	}

	public GridCoverage2D stateToCoverage(IState state, int type, Float noDataValue) {
		return stateToCoverage(state, type, noDataValue, null);
	}

	/**
	 * Turn a state into a grid coverage. Assumes the state is scaled so that all
	 * the values will be spatial.
	 * 
	 * @return a Geotools grid coverage
	 * @throws IllegalArgumentException if the state is not suitable for a raster
	 *                                  representation.
	 */
	public GridCoverage2D stateToCoverage(IState state, int type, Float noDataValue,
			Function<Object, Object> transformation) {

		Space space = (Space) state.getScale().getSpace();
		if (space == null || space.getGrid() == null) {
			throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
		}
		Grid grid = (Grid) space.getGrid();

		/*
		 * build a coverage.
		 * 
		 * TODO use a raster of the appropriate type - for now there is apparently a bug
		 * in geotools that makes it work only with float.
		 */
		WritableRaster raster = RasterFactory.createBandedRaster(type, (int) grid.getXCells(), (int) grid.getYCells(),
				1, null);

		/*
		 * pre-fill with nodata (the thing is filled with 0s).
		 */
		for (int x = 0; x < grid.getXCells(); x++) {
			for (int y = 0; y < grid.getYCells(); y++) {
				raster.setSample(x, y, 0, noDataValue);
			}
		}

		/*
		 * only go through active cells. State should have been located through a proxy
		 * for other extents.
		 */
		for (Cell cell : grid) {
			Object o = state.get(cell);
			if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
				raster.setSample((int) cell.getX(), (int) cell.getY(), 0, noDataValue);
			} else if (o instanceof Number) {
				if (transformation != null) {
					o = transformation.apply(o);
				}
				raster.setSample((int) cell.getX(), (int) cell.getY(), 0, ((Number) o).floatValue());
			} else if (o instanceof Boolean) {
				if (transformation != null) {
					o = transformation.apply(o);
				}
				raster.setSample((int) cell.getX(), (int) cell.getY(), 0, (float) (((Boolean) o) ? 1. : 0.));
			} else if (o instanceof IConcept) {
				if (transformation != null) {
					o = transformation.apply(o);
				}
				raster.setSample((int) cell.getX(), (int) cell.getY(), 0,
						(float) state.getDataKey().reverseLookup((IConcept) o));
			}
		}

		return rasterFactory.create(state.getObservable().getName(), raster, space.getShape().getJTSEnvelope());

	}

	public void coverageToState(GridCoverage2D layer, IState state) {
		coverageToState(layer, state, null, null);
	}

	public void coverageToState(GridCoverage2D layer, IState state, Function<Double, Double> transformation) {
		coverageToState(layer, state, transformation, null);
	}

	/**
	 * Dump the data from a coverage into a pre-existing state.
	 * 
	 */
	public void coverageToState(GridCoverage2D layer, IState state, Function<Double, Double> transformation,
			Function<long[], Boolean> coordinateChecker) {

		ISpace ext = state.getScale().getSpace();

		if (!(ext instanceof Space && ((Space) ext).getGrid() != null)) {
			throw new KlabValidationException("cannot write a gridded state from a non-gridded extent");
		}

		Geometry geometry = ((Scale) state.getGeometry()).asGeometry();
		IGrid grid = ((Space) ext).getGrid();
		RenderedImage image = layer.getRenderedImage();
		RandomIter itera = RandomIterFactory.create(image, null);

		for (int i = 0; i < grid.getCellCount(); i++) {
			long[] xy = grid.getXYOffsets(i);
			Double value = itera.getSampleDouble((int) xy[0], (int) xy[1], 0);
			ILocator spl = geometry.at(ISpace.class, xy[0], xy[1]);
			if (transformation != null) {
				value = transformation.apply(value);
			}
			if (coordinateChecker != null) {
				if (!coordinateChecker.apply(xy)) {
					value = Double.NaN;
				}
			}
			for (ILocator spp : spl) {
				state.set(spp, value);
			}
		}
	}

	public Range getRange(IState state) {

		Range ret = new Range();

		Space space = (Space) state.getScale().getSpace();
		if (space == null || space.getGrid() != null) {
			throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
		}
		Grid grid = (Grid) space.getGrid();

		/*
		 * TODO raster should be pre-filled with a chosen nodata value TODO use
		 * activation layer
		 */
		// IGrid.Mask act = space.requireActivationLayer(true);

		for (Cell cell : grid) {
			Object o = state.get(cell);
			if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
				// screw it
			} else if (o instanceof Number) {
				ret.adapt(((Number) o).doubleValue());
			} else if (o instanceof Boolean) {
				ret.adapt(((Boolean) o) ? 1. : 0.);
			} else if (o instanceof IConcept) {
				ret.adapt((double) state.getDataKey().reverseLookup((IConcept) o));
			}
		}

		return ret;
	}

}
