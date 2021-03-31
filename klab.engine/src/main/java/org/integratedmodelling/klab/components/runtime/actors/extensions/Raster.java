package org.integratedmodelling.klab.components.runtime.actors.extensions;

import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;

import javax.media.jai.RasterFactory;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.integratedmodelling.kactors.model.KActorsQuantity;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.extents.mediators.Subgrid;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;

import groovy.lang.GroovyObjectSupport;
import it.geosolutions.imageio.plugins.tiff.BaselineTIFFTagSet;

/**
 * A raster file backed by Geotools, capable of integrating states computed in sequence by k.Actors
 * scripts.
 * 
 * @author Ferd
 *
 */
public class Raster extends GroovyObjectSupport {

    public static final String WORLD_SHAPE = "s2{bbox=[-180.0 179.99 -90.0 90.0],proj=EPSG:4326}";
    public static final String WORLD_POLYGON = "EPSG:4326 POLYGON ((-180.0 -90.0, 179.99 -90.0, 179.99 90.0, -180.0 90.0, -180.0 -90.0))";

    Grid grid;
    private WritableRaster raster;

    public Raster() {

    }

    /**
     * Pass a sensible spatial geometry for the final raster. Will create a float raster.
     * 
     * TODO pass a type somehow
     * 
     * @param geometry
     */
    public Raster(String geometry, KActorsQuantity resolution) {

        IScale scale = null;
        if ("world".equals(geometry)) {
            IShape shape = Shape.create(WORLD_POLYGON);
            Space space = Space.create(shape, resolution);
            scale = Scale.create(space);
        } else {
            scale = Scale.create(Geometry.create(geometry));
        }

        if (scale != null && scale.getSpace() != null && scale.getSpace().isRegular()) {
            this.grid = (Grid) ((Space) scale.getSpace()).getGrid();
        }

        if (this.grid == null) {
            throw new KlabIllegalArgumentException("Raster can only be created from a grid geometry");
        }

        this.raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_FLOAT, (int) grid.getXCells(), (int) grid.getYCells(), 1, null);

    }

    /**
     * Add a state. Works only if time is not there or has size() == 1.
     * 
     * @param state
     */
    public void merge(IState state) {

        Subgrid sgrid = Subgrid.create(this.grid, (Shape) state.getScale().getSpace().getShape());
        for (ILocator loc : state.getScale()) {
            /*
             * must be a grid space. TODO use a rasterizer for shapes (needs additional
             * parameterization for attributes and translation).
             */
            Object value = state.get(loc);
            if (Observations.INSTANCE.isData(value)) {
                
                if (!(value instanceof Number)) {
                    if (state.getDataKey() != null) {
                        value = state.getDataKey().reverseLookup(value);
                    } else {
                        throw new KlabIllegalArgumentException("cannot write a non-number to a raster unless there is a data key");
                    }
                }
                
                Cell cell = loc.as(Cell.class);
                if (cell != null) {
                    Cell ocell = sgrid.getOriginalCell(cell);
                    raster.setSample((int)ocell.getX(), (int)ocell.getY(), 0, ((Number)value).floatValue());
                }
            }
        }
    }

    /**
     * Export to file
     * 
     * @param file
     */
    public void export(String file) {

        GridCoverage2D coverage = new GridCoverageFactory().create(MiscUtilities.getFileBaseName(file), raster, grid.getShape().getJTSEnvelope());
        java.io.File out = Configuration.INSTANCE.getExportFile(file);
        
        try {
            GeoTiffWriter writer = new GeoTiffWriter(out);
            writer.setMetadataValue(Integer.toString(BaselineTIFFTagSet.TAG_SOFTWARE), "k.LAB (www.integratedmodelling.org)");
            writer.write(coverage, null);
        } catch (Throwable t) {
            throw new KlabIOException(t);
        }
    }

}
