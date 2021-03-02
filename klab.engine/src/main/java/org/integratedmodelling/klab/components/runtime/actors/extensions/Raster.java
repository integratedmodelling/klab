package org.integratedmodelling.klab.components.runtime.actors.extensions;

import org.geotools.coverage.grid.GridCoverage2D;
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
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.scale.Scale;

import groovy.lang.GroovyObjectSupport;

/**
 * A raster file backed by Geotools, capable of integrating states computed in sequence by k.Actors
 * scripts.
 * 
 * @author Ferd
 *
 */
public class Raster extends GroovyObjectSupport {

    public static final String WORLD_SHAPE = "s2{bbox=[-180.0 180.0 -90.0 90.0],proj=EPSG:4326}";
    public static final String WORLD_POLYGON = "EPSG:4326 POLYGON ((-180.0 -90.0, 180.0 -90.0, 180.0 90.0, -180.0 90.0, -180.0 -90.0))";

    GridCoverage2D coverage;
    // grid for the overall coverage
    Grid grid;

    public Raster() {

    }

    /**
     * Pass a sensible spatial geometry for the final raster.
     * 
     * @param geometry
     */
    public Raster(String geometry, IQuantity resolution) {
        
        IScale scale = null;
        if ("world".equals(geometry)) {
            IShape shape = Shape.create(WORLD_POLYGON);
            Space space = Space.create(shape, resolution);
            scale = Scale.create(space);
        } else {
            scale = Scale.create(Geometry.create(geometry));
        }
        
        if (scale != null && scale.getSpace() != null && scale.getSpace().isRegular()) {
            this.grid = (Grid) ((Space)scale.getSpace()).getGrid();
        }
        
        if (this.grid == null) {
            throw new KlabIllegalArgumentException("Raster can only be created from a grid geometry");
        }
        
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
                Cell cell = loc.as(Cell.class);
                if (cell != null) {
                    Cell ocell = sgrid.getOriginalCell(cell);
                    // TODO write to coverage at ocell.x, ocell.y
                }
            }
        }
    }

    /*
     * Use a grid/subgrid to write from the state directly to the raster.
     */
    public void export(String file) {
        /*
         * TODO write coverage to geotiff for now, in configured output directory.
         */
    }

}
