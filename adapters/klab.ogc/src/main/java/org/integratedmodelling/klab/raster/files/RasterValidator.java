/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
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
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.utils.URLUtils;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * The Class RasterValidator.
 */
public class RasterValidator implements IResourceValidator {

  @Override
  public IResource.Builder validate(URL url, IParameters userData, IMonitor monitor) {

    IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();

    try {

      File file = URLUtils.getFileForURL(url);
      
      ret.setParameter("fileUrl", url);
      ret.setAdapterType("raster");
      
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
