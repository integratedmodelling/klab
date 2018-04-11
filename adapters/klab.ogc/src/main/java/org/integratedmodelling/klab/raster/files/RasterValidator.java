package org.integratedmodelling.klab.raster.files;

import java.io.File;
import java.net.URL;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.URLUtils;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

public class RasterValidator implements IResourceValidator {

  @Override
  public IResource.Builder validate(URL url, IParameters userData, IMonitor monitor) {

    IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();

    try {

      File file = URLUtils.getFileForURL(url);
      AbstractGridFormat format = GridFormatFinder.findFormat(file);
      AbstractGridCoverage2DReader reader = format.getReader(file);
      GridCoverage2D coverage = reader.read(null);
      Envelope envelope = coverage.getEnvelope();
      CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem();
      GridGeometry2D grid = coverage.getGridGeometry();

      if (crs == null) {
        ret.addError("Coverage has no coordinate reference system");
      } else
        try {
          CRS.findMathTransform(crs, DefaultGeographicCRS.WGS84);
        } catch (Throwable e) {
          ret.addError(
              "Coverage projection failed reprojection test (check Bursa-Wolfe parameters)");
        }

      if (!ret.hasErrors()) {
        
        Geometry geometry = Geometry.create("S2")
            .withBoundingBox(envelope.getMinimum(0), envelope.getMaximum(0), envelope.getMinimum(1),
                envelope.getMaximum(1))
            .withProjection(CRS.toSRS(crs))
            .withSpatialShape((long) grid.getGridRange().getSpan(0),(long) grid.getGridRange().getSpan(1));

        ret.setGeometry(geometry);
      }

    } catch (Throwable e) {
      ret.addError("Error validating " + e.getMessage());
    }

    return ret;
  }
}
