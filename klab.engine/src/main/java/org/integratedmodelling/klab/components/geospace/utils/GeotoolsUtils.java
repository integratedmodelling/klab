package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.ComponentSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.media.jai.ImageLayout;
import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedOp;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.media.jai.iterator.RectIterFactory;
import javax.media.jai.iterator.WritableRectIter;

import org.eclipse.lsp4j.AbstractTextDocumentRegistrationAndWorkDoneProgressOptions;
import org.geotools.coverage.Category;
import org.geotools.coverage.CoverageFactoryFinder;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.grid.io.AbstractGridCoverageWriter;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffWriteParams;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.Envelope2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.metadata.iso.citation.Citations;
import org.geotools.referencing.CRS;
import org.geotools.styling.ColorMapEntry;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.swing.data.JFileDataStoreChooser;
import org.geotools.util.factory.Hints;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.components.geospace.visualization.raster.FloatRasterWrapper;
import org.integratedmodelling.klab.components.geospace.visualization.raster.ReadonlyStateFloatBuffer;
import org.integratedmodelling.klab.components.geospace.visualization.raster.StorageFloatBuffer;
import org.integratedmodelling.klab.components.geospace.visualization.raster.StorageIntBuffer;
import org.integratedmodelling.klab.data.storage.FileMappedStorage;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.jaitools.tiledimage.DiskMemImage;
import org.opengis.filter.expression.Literal;
import org.opengis.metadata.Identifier;
import org.opengis.metadata.citation.Citation;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValueGroup;
import org.opengis.referencing.FactoryException;
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
    public GridCoverage2D wrapStateInFloatCoverage(IState state, ILocator locator, Float noDataValue,
            Function<Object, Object> transformation) {
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

    /**
     * Get a float coverage matching the grid found in the passed scale, using file-mapped storage.
     * 
     * @param state
     * @return
     */
    public GridCoverage2D getTemporaryFloatStorage(IScale scale) {

        IGrid grid = ((Space) scale.getSpace()).getGrid();
        int width = (int) grid.getXCells();
        int height = (int) grid.getYCells();
        ComponentSampleModel sm = new ComponentSampleModel(DataBuffer.TYPE_FLOAT, width, height, 1, width, new int[]{0});
        DataBuffer db = new StorageFloatBuffer(new FileMappedStorage<Float>(scale, Float.class), scale, scale.initialization(),
                null, width * height, Float.NaN);
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

    /**
     * Get an integer coverage matching the grid found in the passed scale, using file-mapped
     * storage.
     * 
     * @param state
     * @return
     */
    public GridCoverage2D getTemporaryIntStorage(IScale scale) {

        IGrid grid = ((Space) scale.getSpace()).getGrid();
        int width = (int) grid.getXCells();
        int height = (int) grid.getYCells();
        ComponentSampleModel sm = new ComponentSampleModel(DataBuffer.TYPE_INT, width, height, 1, width, new int[]{0});
        DataBuffer db = new StorageIntBuffer(new FileMappedStorage<Integer>(scale, Integer.class), scale, scale.initialization(),
                null, width * height, Integer.MIN_VALUE);
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

    public GridCoverage2D stateToCoverage(IState state, ILocator locator, boolean addKey) {
        return stateToCoverage(state, locator, DataBuffer.TYPE_FLOAT, Float.NaN, addKey);
    }

    public GridCoverage2D stateToCoverage(IState state, ILocator locator, int type, Float noDataValue, boolean addKey) {
        return stateToCoverage(state, locator, type, noDataValue, addKey, null);
    }

    /**
     * Make a coverage from a k.LAB grid space.
     * 
     * @param name a name for the coverage
     * @param space any grid space
     * @param type use {@link DataBuffer} constants
     * @return the opened coverage ready for writing into
     */
    public GridCoverage2D makeCoverage(String name, ISpace space, int type, Object noDataValue) {
        /*
         * build a coverage.
         * 
         * TODO use a raster of the appropriate type - for now there is apparently a bug in geotools
         * that makes it work only with float.
         */
        if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
            throw new KlabIllegalArgumentException("cannot build a coverage from a non-grid space");
        }

        Grid grid = (Grid) ((Space) space).getGrid();
        WritableRaster raster = RasterFactory.createBandedRaster(type, (int) grid.getXCells(), (int) grid.getYCells(), 1, null);

        if (noDataValue instanceof Integer) {
            int ii = (Integer) noDataValue;
            for (int x = 0; x < grid.getXCells(); x++) {
                for (int y = 0; y < grid.getYCells(); y++) {
                    raster.setSample(x, y, 0, ii);
                }
            }
        } else if (noDataValue instanceof Float) {
            float ii = (Float) noDataValue;
            for (int x = 0; x < grid.getXCells(); x++) {
                for (int y = 0; y < grid.getYCells(); y++) {
                    raster.setSample(x, y, 0, ii);
                }
            }
        } else if (noDataValue instanceof Double) {
            double ii = (Double) noDataValue;
            for (int x = 0; x < grid.getXCells(); x++) {
                for (int y = 0; y < grid.getYCells(); y++) {
                    raster.setSample(x, y, 0, ii);
                }
            }
        }

        ReferencedEnvelope jtsEnvelope = checkEnvelope(((Space) space).getShape().getJTSEnvelope());
        return rasterFactory.create(name, raster, jtsEnvelope);
    }

    /**
     * Make an initialized raster image to write on, return it so that
     * {@link #makeCoverage(String, WritableRaster, IScale)} can later be called.
     * 
     * @param name
     * @param space
     * @param type
     * @param noDataValue
     * @return
     */
    public WritableRaster makeRaster(ISpace space, int type, Object noDataValue) {
        /*
         * build a coverage.
         * 
         * TODO use a raster of the appropriate type - for now there is apparently a bug in geotools
         * that makes it work only with float.
         */
        if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
            throw new KlabIllegalArgumentException("cannot build a coverage from a non-grid space");
        }

        Grid grid = (Grid) ((Space) space).getGrid();
        WritableRaster raster = RasterFactory.createBandedRaster(type, (int) grid.getXCells(), (int) grid.getYCells(), 1, null);

        if (noDataValue instanceof Integer) {
            int ii = (Integer) noDataValue;
            for (int x = 0; x < grid.getXCells(); x++) {
                for (int y = 0; y < grid.getYCells(); y++) {
                    raster.setSample(x, y, 0, ii);
                }
            }
        } else if (noDataValue instanceof Float) {
            float ii = (Float) noDataValue;
            for (int x = 0; x < grid.getXCells(); x++) {
                for (int y = 0; y < grid.getYCells(); y++) {
                    raster.setSample(x, y, 0, ii);
                }
            }
        } else if (noDataValue instanceof Double) {
            double ii = (Double) noDataValue;
            for (int x = 0; x < grid.getXCells(); x++) {
                for (int y = 0; y < grid.getYCells(); y++) {
                    raster.setSample(x, y, 0, ii);
                }
            }
        }

        return raster;
    }

    /**
     * Make a coverage from a previously created raster.
     * 
     * @param name
     * @param raster
     * @param scale
     * @return
     */
    public GridCoverage2D makeCoverage(String name, WritableRaster raster, IScale scale) {
        ReferencedEnvelope jtsEnvelope = checkEnvelope(((Space) scale.getSpace()).getShape().getJTSEnvelope());
        return rasterFactory.create(name, raster, jtsEnvelope);
    }

    /**
     * Turn a state into a grid coverage. Assumes the state is scaled so that all the values will be
     * spatial.
     * 
     * @return a Geotools grid coverage
     * @throws IllegalArgumentException if the state is not suitable for a raster representation.
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
         * TODO use a raster of the appropriate type - for now there is apparently a bug in geotools
         * that makes it work only with float.
         */
        WritableRaster raster = RasterFactory.createBandedRaster(type, (int) grid.getXCells(), (int) grid.getYCells(), 1, null);

        /*
         * pre-fill with nodata (the thing is filled with 0s).
         */
        for (int x = 0; x < grid.getXCells(); x++) {
            for (int y = 0; y < grid.getYCells(); y++) {
                raster.setSample(x, y, 0, noDataValue);
            }
        }

        double min = Double.NaN;
        double max = Double.NaN;
        long ndata = 0;

        /*
         * only go through active cells. State should have been located through a proxy for other
         * extents.
         */
        for (ILocator position : locator) {
            Cell cell = position.as(Cell.class);
            if (cell == null) {
                continue;
            }
            Object o = state.get(position);
            if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, noDataValue);
            } else if (o instanceof Number) {
                if (transformation != null) {
                    o = transformation.apply(o);
                }
                float value = ((Number) o).floatValue();
                if (Double.isNaN(min) || min > value) {
                    min = value;
                }
                if (Double.isNaN(max) || max < value) {
                    max = value;
                }
                ndata++;
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, value);
            } else if (o instanceof Boolean) {
                if (transformation != null) {
                    o = transformation.apply(o);
                }
                ndata++;
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, (float) (((Boolean) o) ? 1. : 0.));
            } else if (o instanceof IConcept) {
                if (transformation != null) {
                    o = transformation.apply(o);
                }
                ndata++;
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

                for (ColorMapEntry entry : symbolizer.getColorMap().getColorMapEntries()) {

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
                        pork = true;
                    }

                    categories[i] = category;
                    i++;
                }
                key = new GridSampleDimension("Categories created following k.LAB model specifications", categories,
                        symbolizer.getUnitOfMeasure());
            }

        }

        // TODO stick these somewhere
        Map<String, Object> properties = new HashMap<>();
        properties.put("dataRange", new double[]{min, max});
        properties.put("dataCount", ndata);

        // System.out.println(state.getObservable().getReferenceName() + " raster has range " + min
        // + " to " + max + " (" + ndata + ")");

        ReferencedEnvelope jtsEnvelope = checkEnvelope(((Space) space).getShape().getJTSEnvelope());
        if (key == null || pork) {
            return rasterFactory.create(state.getObservable().getName(), raster, jtsEnvelope);
        }

        return rasterFactory.create(state.getObservable().getName(), raster, jtsEnvelope, new GridSampleDimension[]{key});
    }

    public GridCoverage2D stateToIntCoverage(IState state, ILocator locator, Integer noDataValue,
            Function<Object, Object> transformation) {

        ISpace space = state.getScale().getSpace();
        if (!(space instanceof Space) || ((Space) space).getGrid() == null) {
            throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
        }
        Grid grid = (Grid) ((Space) space).getGrid();

        WritableRaster raster = createWritableRaster((int) grid.getXCells(), (int) grid.getYCells(), Integer.class, null,
                noDataValue);

        if (!(locator instanceof IScale)) {
            locator = state.getScale().at(locator);
        }

        /*
         * only go through active cells. State should have been located through a proxy for other
         * extents.
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

        ReferencedEnvelope jtsEnvelope = checkEnvelope(((Space) space).getShape().getJTSEnvelope());

        return rasterFactory.create(state.getObservable().getName(), raster, jtsEnvelope);
    }

    public ReferencedEnvelope checkEnvelope(ReferencedEnvelope env) {
        CoordinateReferenceSystem crs = env.getCoordinateReferenceSystem();
        crs = checkCrs(crs);
        env = new ReferencedEnvelope(env, crs);
        return env;
    }

    public CoordinateReferenceSystem checkCrs(CoordinateReferenceSystem crs) {
        // looking for EPSG code
        final Set<? extends Identifier> identifiers = crs.getIdentifiers();
        final Iterator<? extends Identifier> it = identifiers.iterator();
        String code = "";
        while(it.hasNext()) {
            final Identifier identifier = it.next();
            final Citation cite = identifier.getAuthority();
            if (Citations.identifierMatches(cite, "EPSG")) {
                code = identifier.getCode();
                break;
            }
        }
        boolean useOriginal = !code.equals("");
        try {
            Integer.parseInt(code);
        } catch (Exception e) {
            useOriginal = false;
        }

        if (!useOriginal) {
            // Is there a good way to find a fix? For now let's guess based on encountered cases.

            // try to rebuild it
            try {
                Integer epsg = CRS.lookupEpsgCode(crs, false);
                if (epsg == null) {
                    epsg = CRS.lookupEpsgCode(crs, true);
                }
                if (epsg != null) {
                    code = "EPSG:" + epsg;
                    crs = CRS.decode(code, true);
                }
            } catch (FactoryException e) {
                e.printStackTrace();
            }
        }
        return crs;
    }

    public void coverageToState(GridCoverage2D layer, IState state) {
        coverageToState(layer, state, null, null);
    }

    public void coverageToState(GridCoverage2D layer, IState state, IScale locator, Function<Double, Double> transformation) {
        coverageToState(layer, state, locator, transformation, null);
    }

    /**
     * Dump the data from a coverage into a pre-existing state.
     * 
     */
    public void coverageToState(GridCoverage2D layer, IState state, IScale locator, Function<Double, Double> transformation,
            Function<long[], Boolean> coordinateChecker) {

        ISpace ext = state.getScale().getSpace();

        double min = Double.NaN;
        double max = Double.NaN;
        long ndata = 0;

        if (!(ext instanceof Space && ((Space) ext).getGrid() != null)) {
            throw new KlabValidationException("cannot write a gridded state from a non-gridded extent");
        }

        // Geometry geometry = ((Scale) state.getGeometry()).asGeometry();
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

            if (Double.isNaN(min) || min > value) {
                min = value;
            }
            if (Double.isNaN(max) || max < value) {
                max = value;
            }

            if (!Double.isNaN(value)) {
                ndata++;
            }

            for (ILocator spp : spl) {
                state.set(spp, value);
            }
        }

        // System.out.println(state.getObservable().getReferenceName() + " raster has range " + min
        // + " to " + max + " (" + ndata + ")");

    }

    /**
     * FIXME remove - snippet for later
     * 
     * Creates a {@link WritableRaster writable raster}.
     * 
     * @param width width of the raster to create.
     * @param height height of the raster to create.
     * @param dataClass data type for the raster. If <code>null</code>, defaults to double.
     * @param sampleModel the samplemodel to use. If <code>null</code>, defaults to
     *        <code>new ComponentSampleModel(dataType, width, height, 1, width, new int[]{0});</code>.
     * @param value value to which to set the raster to. If null, the default of the raster creation
     *        is used, which is 0.
     * @return a {@link WritableRaster writable raster}.
     */
    public static WritableRaster createWritableRaster(int width, int height, Class<?> dataClass, SampleModel sampleModel,
            Object value) {
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
                for (int i = 0; i < dArray.length; i++) {
                    dArray[i] = v;
                }
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            } else if (value instanceof Integer) {
                Integer valueObj = (Integer) value;
                int v = valueObj;
                int[] dArray = new int[sampleModel.getNumBands()];
                for (int i = 0; i < dArray.length; i++) {
                    dArray[i] = v;
                }
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            } else if (value instanceof Float) {
                Float valueObj = (Float) value;
                float v = valueObj;
                float[] dArray = new float[sampleModel.getNumBands()];
                for (int i = 0; i < dArray.length; i++) {
                    dArray[i] = v;
                }
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            } else {
                double v = ((Number) value).doubleValue();
                double[] dArray = new double[sampleModel.getNumBands()];
                for (int i = 0; i < dArray.length; i++) {
                    dArray[i] = v;
                }
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        raster.setPixel(x, y, dArray);
                    }
                }
            }

        }
        return raster;
    }

    /**
     * FIXME remove - snippet for later
     * 
     * @param file
     */
    public void createBigTiff(File file) {

        /*
         * I first decide if tiling must be used and which size must be used (borrowed from
         * ArcGridsImageReader), then I create the image, then I fill all the image with some
         * useless values, and finally try to create a coverage and write this to disk by using
         * GeoTiffWriter.
         */

        /** Minimum size of a certain file source that neds tiling. */
        final int MIN_SIZE_NEED_TILING = 5242880; // 5 MByte
        /** Defaul tile size. */
        final int DEFAULT_TILE_SIZE = 1048576 / 2; // 1 MByte

        // if the imageSize is bigger than MIN_SIZE_NEED_TILING
        // we proceed to image tiling
        boolean isTiled = false;

        /**
         * Tile width for the underlying raster.
         */
        int tileWidth = -1;

        /**
         * Tile height for the underlying raster.
         */
        int tileHeight = -1;

        /** Image Size */
        long imageSize = -1;

        try {
            CoordinateReferenceSystem crs = CRS.decode("EPSG:3035");
            int width = 30000, height = 28000;
            int sampleSizeByte = DataBuffer.getDataTypeSize(DataBuffer.TYPE_FLOAT);
            imageSize = (long) width * (long) height * (long) sampleSizeByte;

            /**
             * Setting Tile Dimensions (If Tiling is supported)
             */
            // if the Image Size is greater than a certain dimension
            // (MIN_SIZE_NEED_TILING), the image needs to be tiled
            if (imageSize >= MIN_SIZE_NEED_TILING) {
                isTiled = true;

                // This implementation supposes that tileWidth is
                // equal to the width
                // of the whole image
                tileWidth = width;

                // actually (need improvements) tileHeight is given by
                // the default tile size divided by the tileWidth
                // multiplied by the
                // sample size (in byte)
                tileHeight = DEFAULT_TILE_SIZE / (tileWidth * sampleSizeByte);

                // if computed tileHeight is zero, it is setted to 1
                // as precaution
                if (tileHeight < 1) {
                    tileHeight = 1;
                }

            } else {
                // If no Tiling needed, I set the tile sizes equal to the image
                // sizes
                tileWidth = width;
                tileHeight = height;
            }

            Envelope2D envelope = new Envelope2D(crs, 0, 0, width, height);
            SampleModel sampleModel = new ComponentSampleModel(DataBuffer.TYPE_FLOAT, tileWidth, tileHeight, 1, tileWidth,
                    new int[]{0});
            DiskMemImage img = new DiskMemImage(width, height, sampleModel);

            WritableRectIter iter = RectIterFactory.createWritable(img, null);
            do {
                int x = 0;
                do {
                    iter.setSample(x / tileWidth);
                } while(!iter.nextPixelDone());
            } while(!iter.nextLineDone());

            GridCoverageFactory factory = CoverageFactoryFinder.getGridCoverageFactory(null);

            GridCoverage2D gc = factory.create("bigtif", img, envelope);

            GeoTiffFormat fmt = new GeoTiffFormat();
            // getting the write parameters
            final GeoTiffWriteParams wp = new GeoTiffWriteParams();

            // setting compression to Deflate
            wp.setCompressionMode(GeoTiffWriteParams.MODE_EXPLICIT);
            wp.setCompressionType("Deflate");
            wp.setCompressionQuality(0.75F);

            // setting the tile size to 256X256
            wp.setTilingMode(GeoToolsWriteParams.MODE_EXPLICIT);
            wp.setTiling(256, 256);

            // setting the write parameters for this geotiff
            final ParameterValueGroup[] params = {fmt.getWriteParameters()};

            params[0].parameter(AbstractGridFormat.GEOTOOLS_WRITE_PARAMS.getName().toString()).setValue(wp);

            AbstractGridCoverageWriter writer = new GeoTiffWriter(file);
            writer.write(gc/* .view(ViewType.GEOPHYSICS) */, params);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Dump one or more states to rasters in the klab configuration folder.
     * 
     * <p>
     * This is executed only if the {@link IConfigurationService#KLAB_MODEL_DUMP_INTERMEDIATE} is
     * set to true. So no need to create a check in the model.
     * 
     * <p>
     * The folder used is currently hardcoded to "intermediate_data_dump_folder". Subfolders based
     * on the timestamp of the context time step are generated.
     * 
     * @param scope the context to use.
     * @param producingModel the model that produces the raster
     * @param states a list of states that are to be dumped to raster.
     */
    public void dumpToRaster(IContextualizationScope scope, String producingModel, IState... states) {
        String dumpIntermediate = Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_MODEL_DUMP_INTERMEDIATE, "false");
        boolean doDump = Boolean.parseBoolean(dumpIntermediate);
        if (!doDump) {
            return;
        }

        File klabFolder = Configuration.INSTANCE.getDataPath();

        File dumpFolder = new File(klabFolder, "intermediate_data_dump_folder");
        if (!dumpFolder.exists()) {
            dumpFolder.mkdirs();
        }

        long ts = scope.getScale().getTime().getStart().getMilliseconds();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");

        // TODO check if it makes sense to also add context subject, it seems to make it less
        // readable.
        // String contextName = "";
        // if (!scope.getRootSubject().equals(scope.getContextSubject())) {
        // contextName = scope.getContextSubject().getName() + "_";
        // }

        for (IState state : states) {
            if (state != null) {
                GridCoverage2D coverage = GeotoolsUtils.INSTANCE.stateToCoverage(state, scope.getScale(), false);
                // String name = contextName + state.getObservable().getName();
                String name = state.getObservable().getName();

                String dateStr = f.format(new Date(ts));
                String fileName = producingModel + "-model__" + name + "-obs.tiff";

                File outFolder = new File(dumpFolder, dateStr);
                if (!outFolder.exists()) {
                    outFolder.mkdir();
                }
                File outfile = new File(outFolder, fileName);

                scope.getMonitor().debug("Dumping state of ts " + dateStr + " to file " + fileName);

                try {
                    final GeoTiffFormat format = new GeoTiffFormat();
                    final GeoTiffWriteParams wp = new GeoTiffWriteParams();
                    wp.setCompressionMode(GeoTiffWriteParams.MODE_DEFAULT);
                    wp.setTilingMode(GeoToolsWriteParams.MODE_DEFAULT);
                    final ParameterValueGroup paramWrite = format.getWriteParameters();
                    paramWrite.parameter(AbstractGridFormat.GEOTOOLS_WRITE_PARAMS.getName().toString()).setValue(wp);
                    GeoTiffWriter gtw = new GeoTiffWriter(outfile,
                            new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
                    gtw.write(coverage, (GeneralParameterValue[]) paramWrite.values().toArray(new GeneralParameterValue[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                    scope.getMonitor().error(e.getMessage());
                }
            }
        }
    }

    /**
     * Dump one or more coverages to a raster in the klab configuration folder. This is called only
     * for special purposes so it disregards the
     * {@link IConfigurationService#KLAB_MODEL_DUMP_INTERMEDIATE} setting and just dumps the file
     * when called.
     * 
     * <p>
     * The folder used is currently hardcoded to "intermediate_data_dump_folder_raw". Subfolders based
     * on the timestamp of the context time step are generated.
     * 
     * @param scope the context to use.
     * @param producingModel the model that produces the raster
     * @param states a list of states that are to be dumped to raster.
     */
    public void dumpToRaster(IContextualizationScope scope, String producingModel, GridCoverage2D... states) {
        // String dumpIntermediate = Configuration.INSTANCE
        // .getProperty(IConfigurationService.KLAB_MODEL_DUMP_INTERMEDIATE, "false");
        // boolean doDump = Boolean.parseBoolean(dumpIntermediate);
        // if (/* ! */doDump) {
        // return;
        // }

        File klabFolder = Configuration.INSTANCE.getDataPath();

        File dumpFolder = new File(klabFolder, "intermediate_data_dump_folder_raw");
        if (!dumpFolder.exists()) {
            dumpFolder.mkdirs();
        }

        long ts = scope.getScale().getTime().getStart().getMilliseconds();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd_HHmmss");

        // TODO check if it makes sense to also add context subject, it seems to make it less
        // readable.
        // String contextName = "";
        // if (!scope.getRootSubject().equals(scope.getContextSubject())) {
        // contextName = scope.getContextSubject().getName() + "_";
        // }

        for (GridCoverage2D coverage : states) {

            // String name = contextName + state.getObservable().getName();
            String name = coverage.getName().toString();

            String dateStr = f.format(new Date(ts));
            String fileName = producingModel + "-model__" + name + "-obs.tiff";

            File outFolder = new File(dumpFolder, dateStr);
            if (!outFolder.exists()) {
                outFolder.mkdir();
            }
            File outfile = new File(outFolder, fileName);

            scope.getMonitor().debug("Dumping state of ts " + dateStr + " to file " + fileName);

            try {
                final GeoTiffFormat format = new GeoTiffFormat();
                final GeoTiffWriteParams wp = new GeoTiffWriteParams();
                wp.setCompressionMode(GeoTiffWriteParams.MODE_DEFAULT);
                wp.setTilingMode(GeoToolsWriteParams.MODE_DEFAULT);
                final ParameterValueGroup paramWrite = format.getWriteParameters();
                paramWrite.parameter(AbstractGridFormat.GEOTOOLS_WRITE_PARAMS.getName().toString()).setValue(wp);
                GeoTiffWriter gtw = new GeoTiffWriter(outfile, new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE));
                gtw.write(coverage, (GeneralParameterValue[]) paramWrite.values().toArray(new GeneralParameterValue[1]));
            } catch (Exception e) {
                e.printStackTrace();
                scope.getMonitor().error(e.getMessage());
            }
        }
    }

    boolean hasData(GridCoverage2D tiff) {

        RenderedImage image = tiff.getRenderedImage();
        RandomIter iterator = RandomIterFactory.create(image, null);

        for (int i = 0; i < tiff.getNumSampleDimensions(); i++) {
            for (int x = tiff.getGridGeometry().getGridRange().getLow(0); x <= tiff.getGridGeometry().getGridRange()
                    .getHigh(0); x++) {
                for (int y = tiff.getGridGeometry().getGridRange().getLow(1); y <= tiff.getGridGeometry().getGridRange()
                        .getHigh(1); y++) {
                    Double value = iterator.getSampleDouble(x, y, i);
                    if (!Double.isNaN(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

class BigCoverage {

    private static final int IMAGE_WIDTH = 10000;
    private static final int IMAGE_HEIGHT = 10000;

    public static void main(String[] args) throws Exception {

        /*
         * TODO this works - use for larger TIFFs instead of current
         */

        JFileDataStoreChooser chooser = new JFileDataStoreChooser("tif");
        chooser.setDialogTitle("Create GeoTiff file");

        File file = null;
        if (chooser.showSaveDialog(null) == JFileDataStoreChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        if (file == null) {
            return;
        }

        ParameterBlockJAI pb = new ParameterBlockJAI("Constant");
        pb.setParameter("width", (float) IMAGE_WIDTH);
        pb.setParameter("height", (float) IMAGE_HEIGHT);
        pb.setParameter("bandValues", new Double[]{0.0d});

        final int tileWidth = 512;

        ImageLayout layout = new ImageLayout();
        layout.setTileWidth(tileWidth);
        layout.setTileHeight(tileWidth);

        RenderingHints hints = new RenderingHints(JAI.KEY_IMAGE_LAYOUT, layout);

        RenderedOp image = JAI.create("Constant", pb, hints);

        GeoTiffWriter writer = new GeoTiffWriter(file, null);
        GridCoverageFactory factory = new GridCoverageFactory();
        ReferencedEnvelope env = new ReferencedEnvelope(new Rectangle(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT),
                Projection.getLatLon().getCoordinateReferenceSystem());
        GridCoverage2D coverage = factory.create("coverage", image, env);
        writer.write(coverage, null);
    }

}
