package org.integratedmodelling.klab.raster.files;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.opengis.geometry.Envelope;

public class RasterValidator implements IResourceValidator {
  
  @Override
  public IResource.Builder validate(URL url, IParameters userData) {
    return null;
  }

  private Envelope getEnvelope(File file) throws IOException {
    Envelope envelope = null;
    try {
        AbstractGridFormat format = GridFormatFinder.findFormat(file);
        AbstractGridCoverage2DReader reader = format.getReader(file);
        GridCoverage2D coverage = reader.read(null);
        // TODO fill out metadata
        envelope = coverage.getEnvelope();
    } catch (NullPointerException e) {
        return null;
    }
    return envelope;
}
}
