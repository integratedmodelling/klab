package org.integratedmodelling.klab.components.geospace.visualization.raster;

import java.awt.Rectangle;
import java.awt.image.Raster;

import org.geotools.coverage.grid.GridCoverage2D;

import com.sun.media.jai.iterator.WrapperRI;

/**
 * RenderedImage wrapper for {@link Raster}s.
 * 
 * <p>Implements the {@link #getData(Rectangle)} method which is missing in the 
 * parent implementation but is necessary for {@link GridCoverage2D}'s tile handling.
 * 
 * @author Andrea Antonello
 *
 */
public class FloatRasterWrapper extends WrapperRI {

    private Raster ras;

    public FloatRasterWrapper( Raster ras ) {
        super(ras);
        this.ras = ras;
    }

    public Raster getData() {
        return ras;
    }

    public Raster getData( Rectangle rect ) {
        return ras.createChild(rect.x, rect.y, rect.width, rect.height, rect.x, rect.y, null);
    }
}
