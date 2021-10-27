package org.integratedmodelling.klab.components.runtime.actors.extensions;

import org.integratedmodelling.kactors.model.KActorsQuantity;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.data.storage.MergingState;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;
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
    public static final String WORLD_POLYGON = "EPSG:4326 POLYGON ((-180 -90, -180 90, 180 90, 180 -90, -180 -90))";
    MergingState state = null;
    IScale scale = null;

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

        if ("world".equals(geometry)) {
            IShape shape = Shape.create(WORLD_POLYGON);
            Space space = Space.create(shape, resolution);
            scale = Scale.create(space);
        } else {
            scale = Scale.create(Geometry.create(geometry));
        }

        if (scale == null || scale.getSpace() == null || !scale.getSpace().isRegular()) {
            throw new KlabIllegalArgumentException("Raster can only be created from a grid geometry");
        }

    }

    /**
     * Add a state.
     * 
     * @param state
     */
    public void merge(IState state) {
        if (this.state == null) {
            this.state = new MergingState(state.getObservable(), this.scale, (IRuntimeScope) state.getScope());
        }
        this.state.add(state);
    }

    /**
     * Export to file
     * 
     * @param file
     */
    public void export(String file) {
        for (ExportFormat format : Observations.INSTANCE.getExportFormats(this.state)) {
            if ("tiff".equals(format.getValue())) {
                java.io.File output = Configuration.INSTANCE.getExportFile(file);
                Observations.INSTANCE.export(this.state, this.scale, output, format.getValue(), format.getAdapter(), this.state.getMonitor());
                break;
            }
        }
    }

}