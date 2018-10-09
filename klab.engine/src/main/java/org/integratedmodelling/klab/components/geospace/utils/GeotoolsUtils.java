package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.media.jai.RasterFactory;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabIOException;
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
     * @throws IllegalArgumentException
     *             if the state is not suitable for a raster representation.
     */
    public GridCoverage2D stateToCoverage(IState state, ILocator locator) {
        return stateToCoverage(state.at(locator));
    }

    /**
     * Turn a state into a grid coverage. Assumes the state is scaled so that all the values will be spatial.
     * 
     * @param state
     * @return a Geotools grid coverage
     * @throws IllegalArgumentException
     *             if the state is not suitable for a raster representation.
     */
    public GridCoverage2D stateToCoverage(IState state) {

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
        WritableRaster raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_FLOAT, (int) grid.getXCells(),
                (int) grid.getYCells(), 1, null);

        /*
         * pre-fill with nodata (the thing is filled with 0s).
         */
        for (int x = 0; x < grid.getXCells(); x++) {
            for (int y = 0; y < grid.getYCells(); y++) {
                raster.setSample(x, y, 0, Float.NaN);
            }
        }

        /*
         * only go through active cells.
         */
        for (Cell cell : grid) {
            Object o = state.get(cell);
            if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, Float.NaN);
            } else if (o instanceof Number) {
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, ((Number) o).floatValue());
            } else if (o instanceof Boolean) {
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0, (float) (((Boolean) o) ? 1. : 0.));
            } else if (o instanceof IConcept) {
                raster.setSample((int) cell.getX(), (int) cell.getY(), 0,
                        (float) state.getDataKey().reverseLookup((IConcept) o));
            }
        }

        return rasterFactory.create(state.getObservable().getLocalName(), raster, space.getShape().getJTSEnvelope());

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

    public File exportToTempFile(IState state, ILocator locator, String outputFormat) {

        GridCoverage2D coverage = stateToCoverage(state, locator);
        
        if (outputFormat.equalsIgnoreCase("tiff")) {
            try {
                File out = File.createTempFile("klab", ".tiff");
                out.deleteOnExit();
                GeoTiffWriter writer = new GeoTiffWriter(out);
                writer.write(coverage, null);
                return out;
            } catch (IOException e) {
                throw new KlabIOException(e);
            }

        }
        return null;
    }
}
