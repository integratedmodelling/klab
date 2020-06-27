package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.Color;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.media.jai.RasterFactory;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.Category;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.styling.ColorMap;
import org.geotools.styling.ColorMapEntry;
import org.geotools.styling.RasterSymbolizer;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.opengis.filter.expression.Literal;

public enum GeotoolsUtils {

	INSTANCE;

	Map<IConcept, Integer> conceptMap = new HashMap<>();
	GridCoverageFactory rasterFactory = new GridCoverageFactory();

//	/**
//	 * Turn a state into a grid coverage.
//	 * 
//	 * @param state
//	 * @return a Geotools grid coverage
//	 * @throws IllegalArgumentException if the state is not suitable for a raster
//	 *                                  representation.
//	 */
//	public GridCoverage2D stateToCoverage(IState state, ILocator locator, float noDataValue) {
//		return stateToCoverage(state.at(locator), DataBuffer.TYPE_FLOAT, noDataValue);
//	}

	public GridCoverage2D stateToCoverage(IState state, ILocator locator, boolean addKey) {
		return stateToCoverage(state, locator, DataBuffer.TYPE_FLOAT, Float.NaN, addKey);
	}

	public GridCoverage2D stateToCoverage(IState state, ILocator locator, int type, Float noDataValue, boolean addKey) {
		return stateToCoverage(state, locator, type, noDataValue, addKey, null);
	}

	/**
	 * Turn a state into a grid coverage. Assumes the state is scaled so that all
	 * the values will be spatial.
	 * 
	 * @return a Geotools grid coverage
	 * @throws IllegalArgumentException if the state is not suitable for a raster
	 *                                  representation.
	 */
	public GridCoverage2D stateToCoverage(IState state, ILocator locator, int type, Float noDataValue, boolean addKey,
			Function<Object, Object> transformation) {

		ISpace space = state.getScale().getSpace();
		if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
			throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
		}
		Grid grid = (Grid) ((Space) space).getGrid();

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
		for (ILocator position : locator) {
			Cell cell = position.as(Cell.class);
			Object o = state.get(position);
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

		GridSampleDimension key = null;
		boolean pork = false;
		if (addKey) {
			Pair<RasterSymbolizer, String> rs = Renderer.INSTANCE.getRasterSymbolizer(state, locator);
			RasterSymbolizer symbolizer = rs == null ? null : rs.getFirst();
			if (symbolizer != null && symbolizer.getColorMap() != null) {
				Category[] categories = new Category[symbolizer.getColorMap().getColorMapEntries().length];
				int i = 0;
				
				for (ColorMapEntry entry : symbolizer.getColorMap().getColorMapEntries()) {

					Category category = null;
					Object value = null;
					Color color = Color.decode(entry.getColor().toString());
					String label = entry.getLabel();
					
					if (entry.getQuantity() instanceof Literal) {
						value = ((Literal)entry.getQuantity()).getValue();
					}
					
					if (value instanceof Number) {
						category = new Category(label, color, ((Number)value).doubleValue());
					} else {
						System.out.println("CIOIOCIOIOI");
						pork = true;
					}
					
					categories[i] = category;
					i++;
				}
				key = new GridSampleDimension("Categories created following k.LAB model specifications", categories, symbolizer.getUnitOfMeasure());
			}
			
		}

		if (key == null || pork) {
			return rasterFactory.create(state.getObservable().getName(), raster,
					((Space) space).getShape().getJTSEnvelope());
		}

		return rasterFactory.create(state.getObservable().getName(), raster,
				((Space) space).getShape().getJTSEnvelope(), new GridSampleDimension[] {key});

	}

	public void coverageToState(GridCoverage2D layer, IState state) {
		coverageToState(layer, state, null, null);
	}

	public void coverageToState(GridCoverage2D layer, IState state, IScale locator,
			Function<Double, Double> transformation) {
		coverageToState(layer, state, locator, transformation, null);
	}

	/**
	 * Dump the data from a coverage into a pre-existing state.
	 * 
	 */
	public void coverageToState(GridCoverage2D layer, IState state, IScale locator,
			Function<Double, Double> transformation, Function<long[], Boolean> coordinateChecker) {

		ISpace ext = state.getScale().getSpace();

		if (!(ext instanceof Space && ((Space) ext).getGrid() != null)) {
			throw new KlabValidationException("cannot write a gridded state from a non-gridded extent");
		}

//		Geometry geometry = ((Scale) state.getGeometry()).asGeometry();
		IGrid grid = ((Space) ext).getGrid();
		RenderedImage image = layer.getRenderedImage();
		RandomIter itera = RandomIterFactory.create(image, null);

		for (int i = 0; i < grid.getCellCount(); i++) {
			long[] xy = grid.getXYOffsets(i);
			Double value = itera.getSampleDouble((int) xy[0], (int) xy[1], 0);
			ILocator spl = locator.at(ISpace.class, xy[0], xy[1]);
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
