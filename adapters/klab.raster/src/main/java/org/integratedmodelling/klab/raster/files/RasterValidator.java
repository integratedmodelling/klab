package org.integratedmodelling.klab.raster.files;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.utils.URLUtils;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class RasterValidator implements IResourceValidator {

  @Override
  public IResource.Builder validate(URL url, IParameters userData) {
    
    IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();
    
    try {
      File file = URLUtils.getFileForURL(url);
      AbstractGridFormat format = GridFormatFinder.findFormat(file);
      AbstractGridCoverage2DReader reader = format.getReader(file);
      GridCoverage2D coverage = reader.read(null);
      
      Envelope envelope = coverage.getEnvelope();
      // TODO check envelope
      
      CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem();
      
      // TODO crs must be not null, known and parseable by k.LAB
      // TODO stated nodata
      // TODO encode extents and resolution
      
    } catch (Exception e) {
      // TODO add errors to builder
      return null;
    }
    
    return ret;
  }
}
