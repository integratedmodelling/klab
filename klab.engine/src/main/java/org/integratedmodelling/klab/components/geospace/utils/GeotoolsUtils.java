package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.Color;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.media.jai.RasterFactory;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.Category;
import org.geotools.coverage.CoverageFactoryFinder;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.geometry.Envelope2D;
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
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.components.geospace.visualization.raster.FloatRasterWrapper;
import org.integratedmodelling.klab.components.geospace.visualization.raster.ReadonlyStateFloatBuffer;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.opengis.filter.expression.Literal;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public enum GeotoolsUtils {

    INSTANCE;

    Map<IConcept, Integer> conceptMap = new HashMap<>();
    GridCoverageFactory rasterFactory = new GridCoverageFactory();

    /**
     * Create a {@link GridCoverage2D} that wraps a {@link IState} object.
     * 
     * @param state the {@link IState} object to get the data from.
     * @param locator the {@link ILocator} object to get the data index from.
     * @param noDataValue optional novalue.
     * @param transformation optional data transformation object.
     * @return the wrapped {@link GridCoverage2D}.
     */
    public GridCoverage2D wrapStateInFloatCoverage( IState state, ILocator locator, Float noDataValue,
            Function<Object, Object> transformation ) {
        IGrid grid = ((Space) state.getSpace()).getGrid();
        int width = (int) grid.getXCells();
        int height = (int) grid.getYCells();
        ComponentSampleModel sm = new ComponentSampleModel(DataBuffer.TYPE_FLOAT, width, height, 1, width, new int[]{0});
        DataBuffer db = new ReadonlyStateFloatBuffer(state, locator, null, width * height, noDataValue);
        Raster raster = Raster.createRaster(sm, db, null);

        FloatRasterWrapper ri = new FloatRasterWrapper(raster);
        double west = grid.getWest();
        double south = grid.getSouth();
        double east = grid.getEast();
        double north = grid.getNorth();
        CoordinateReferenceSystem crs = ((Projection) grid.getProjection()).getCoordinateReferenceSystem();
        Envelope2D writeEnvelope = new Envelope2D(crs, west, south, east - west, north - south);
        GridCoverageFactory factory = CoverageFactoryFinder.getGridCoverageFactory(null);

        GridCoverage2D coverage = factory.create("stateraster", ri, writeEnvelope);
        return coverage;
    }

    public GridCoverage2D stateToCoverage( IState state, ILocator locator, boolean addKey ) {
        return stateToCoverage(state, locator, DataBuffer.TYPE_FLOAT, Float.NaN, addKey);
    }

    public GridCoverage2D stateToCoverage( IState state, ILocator locator, int type, Float noDataValue, boolean addKey ) {
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
    public GridCoverage2D stateToCoverage( IState state, ILocator locator, int type, Float noDataValue, boolean addKey,
            Function<Object, Object> transformation ) {

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
        WritableRaster raster = RasterFactory.createBandedRaster(type, (int) grid.getXCells(), (int) grid.getYCells(), 1, null);

        /*
         * pre-fill with nodata (the thing is filled with 0s).
         */
        for( int x = 0; x < grid.getXCells(); x++ ) {
            for( int y = 0; y < grid.getYCells(); y++ ) {
                raster.setSample(x, y, 0, noDataValue);
            }
        }

        /*
         * only go through active cells. State should have been located through a proxy
         * for other extents.
         */
        for( ILocator position : locator ) {
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
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, (float) state.getDataKey().reverseLookup((IConcept) o));
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

                for( ColorMapEntry entry : symbolizer.getColorMap().getColorMapEntries() ) {

                    Category category = null;
                    Object value = null;
                    Color color = Color.decode(entry.getColor().toString());
                    String label = entry.getLabel();

                    if (entry.getQuantity() instanceof Literal) {
                        value = ((Literal) entry.getQuantity()).getValue();
                    }

                    if (value instanceof Number) {
                        category = new Category(label, color, ((Number) value).doubleValue());
                    } else {
                        System.out.println("CIOIOCIOIOI");
                        pork = true;
                    }

                    categories[i] = category;
                    i++;
                }
                key = new GridSampleDimension("Categories created following k.LAB model specifications", categories,
                        symbolizer.getUnitOfMeasure());
            }

        }

        if (key == null || pork) {
            return rasterFactory.create(state.getObservable().getName(), raster, ((Space) space).getShape().getJTSEnvelope());
        }

        return rasterFactory.create(state.getObservable().getName(), raster, ((Space) space).getShape().getJTSEnvelope(),
                new GridSampleDimension[]{key});

    }

    public GridCoverage2D stateToIntCoverage( IState state, ILocator locator, Integer noDataValue,
            Function<Object, Object> transformation ) {

        ISpace space = state.getScale().getSpace();
        if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
            throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
        }
        Grid grid = (Grid) ((Space) space).getGrid();

        WritableRaster raster = createWritableRaster((int) grid.getXCells(), (int) grid.getYCells(), Integer.class, null,
                noDataValue);

        /*
         * only go through active cells. State should have been located through a proxy
         * for other extents.
         */
        for( ILocator position : locator ) {
            Cell cell = position.as(Cell.class);
            Object o = state.get(position);
            if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, noDataValue);
            } else if (o instanceof Number) {
                if (transformation != null) {
                    o = transformation.apply(o);
                }
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, ((Number) o).intValue());
            } else if (o instanceof Boolean) {
                if (transformation != null) {
                    o = transformation.apply(o);
                }
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, (((Boolean) o) ? 1 : 0));
            } else if (o instanceof IConcept) {
                if (transformation != null) {
                    o = transformation.apply(o);
                }
                int value = state.getDataKey().reverseLookup((IConcept) o);
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, value);
            }
        }

        return rasterFactory.create(state.getObservable().getName(), raster, ((Space) space).getShape().getJTSEnvelope());

    }

    public void coverageToState( GridCoverage2D layer, IState state ) {
        coverageToState(layer, state, null, null);
    }

    public void coverageToState( GridCoverage2D layer, IState state, IScale locator, Function<Double, Double> transformation ) {
        coverageToState(layer, state, locator, transformation, null);
    }

    /**
     * Dump the data from a coverage into a pre-existing state.
     * 
     */
    public void coverageToState( GridCoverage2D layer, IState state, IScale locator, Function<Double, Double> transformation,
            Function<long[], Boolean> coordinateChecker ) {

        ISpace ext = state.getScale().getSpace();

        if (!(ext instanceof Space && ((Space) ext).getGrid() != null)) {
            throw new KlabValidationException("cannot write a gridded state from a non-gridded extent");
        }

//		Geometry geometry = ((Scale) state.getGeometry()).asGeometry();
        IGrid grid = ((Space) ext).getGrid();
        RenderedImage image = layer.getRenderedImage();
        RandomIter itera = RandomIterFactory.create(image, null);

        for( int i = 0; i < grid.getCellCount(); i++ ) {
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
            for( ILocator spp : spl ) {
                state.set(spp, value);
            }
        }
    }

    /**
     * Creates a {@link WritableRaster writable raster}.
     * 
     * @param width width of the raster to create.
     * @param height height of the raster to create.
     * @param dataClass data type for the raster. If <code>null</code>, defaults to double.
     * @param sampleModel the samplemodel to use. If <code>null</code>, defaults to 
     *                  <code>new ComponentSampleModel(dataType, width, height, 1, width, new int[]{0});</code>.
     * @param value value to which to set the raster to. If null, the default of the raster creation is 
     *                  used, which is 0.
     * @return a {@link WritableRaster writable raster}.
     */
    public static WritableRaster createWritableRaster( int width, int height, Class< ? > dataClass, SampleModel sampleModel,
            Object value ) {
        int dataType = DataBuffer.TYPE_DOUBLE;
        if (dataClass != null) {
            if (dataClass.isAssignableFrom(Integer.class)) {
                dataType = DataBuffer.TYPE_INT;
            } else if (dataClass.isAssignableFrom(Float.class)) {
                dataType = DataBuffer.TYPE_FLOAT;
            } else if (dataClass.isAssignableFrom(Byte.class)) {
                dataType = DataBuffer.TYPE_BYTE;
            } else if (dataClass.isAssignableFrom(Short.class)) {
                dataType = DataBuffer.TYPE_SHORT;
            }
        }

        if (sampleModel == null) {
            sampleModel = new ComponentSampleModel(dataType, width, height, 1, width, new int[]{0});
        }

        WritableRaster raster = RasterFactory.createWritableRaster(sampleModel, null);
        if (value != null) {
            // autobox only once
            if (value instanceof Double) {
                Double valueObj = (Double) value;
                double v = valueObj;
                double[] dArray = new double[sampleModel.getNumBands()];
                for( int i = 0; i < dArray.length; i++ ) {
                    dArray[i] = v;
                }
                for( int y = 0; y < height; y++ ) {
                    for( int x = 0; x < width; x++ ) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            } else if (value instanceof Integer) {
                Integer valueObj = (Integer) value;
                int v = valueObj;
                int[] dArray = new int[sampleModel.getNumBands()];
                for( int i = 0; i < dArray.length; i++ ) {
                    dArray[i] = v;
                }
                for( int y = 0; y < height; y++ ) {
                    for( int x = 0; x < width; x++ ) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            } else if (value instanceof Float) {
                Float valueObj = (Float) value;
                float v = valueObj;
                float[] dArray = new float[sampleModel.getNumBands()];
                for( int i = 0; i < dArray.length; i++ ) {
                    dArray[i] = v;
                }
                for( int y = 0; y < height; y++ ) {
                    for( int x = 0; x < width; x++ ) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            } else {
                double v = ((Number) value).doubleValue();
                double[] dArray = new double[sampleModel.getNumBands()];
                for( int i = 0; i < dArray.length; i++ ) {
                    dArray[i] = v;
                }
                for( int y = 0; y < height; y++ ) {
                    for( int x = 0; x < width; x++ ) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            }

        }
        return raster;
    }

//	public Range getRange(IState state) {
//
//		Range ret = new Range();
//
//		Space space = (Space) state.getScale().getSpace();
//		if (space == null || space.getGrid() != null) {
//			throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
//		}
//		Grid grid = (Grid) space.getGrid();
//
//		/*
//		 * TODO raster should be pre-filled with a chosen nodata value TODO use
//		 * activation layer
//		 */
//		// IGrid.Mask act = space.requireActivationLayer(true);
//
//		for (Cell cell : grid) {
//			Object o = state.get(cell);
//			if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
//				// screw it
//			} else if (o instanceof Number) {
//				ret.adapt(((Number) o).doubleValue());
//			} else if (o instanceof Boolean) {
//				ret.adapt(((Boolean) o) ? 1. : 0.);
//			} else if (o instanceof IConcept) {
//				ret.adapt((double) state.getDataKey().reverseLookup((IConcept) o));
//			}
//		}
//
//		return ret;
//	}

}
