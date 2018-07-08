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
import java.util.Collection;

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
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.ogc.RasterAdapter;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.URLUtils;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * The Class RasterValidator.
 */
public class RasterValidator implements IResourceValidator {

	@Override
	public IResource.Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder().withType(IArtifact.Type.NUMBER);

		try {

			File file = URLUtils.getFileForURL(url);

			monitor.info("Validating raster resource " + url);

			ret.withParameter("fileUrl", url);

			monitor.info("Running access tests...");
			AbstractGridFormat format = GridFormatFinder.findFormat(file);
			AbstractGridCoverage2DReader reader = format.getReader(file);
			GridCoverage2D coverage = reader.read(null);
			Envelope envelope = coverage.getEnvelope();
			CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem();
			GridGeometry2D grid = coverage.getGridGeometry();

			String crsCode = null;
			if (crs == null) {
				ret.addError("Coverage has no coordinate reference system");
			} else {

				try {
					monitor.info("Testing reprojection to WGS84...");
					CRS.findMathTransform(crs, DefaultGeographicCRS.WGS84);
					Projection utmProjection = Projection
							.getUTM(org.integratedmodelling.klab.components.geospace.extents.Envelope.create(envelope,
									Projection.create(crs)));
					if (!crs.equals(utmProjection.getCoordinateReferenceSystem())) {
						monitor.info("Testing reprojection to UTM " + utmProjection + "...");
						CRS.findMathTransform(crs, utmProjection.getCoordinateReferenceSystem());
					}

					crsCode = CRS.lookupIdentifier(crs, true);

				} catch (Throwable e) {
					ret.addError("Coverage projection failed reprojection test (check Bursa-Wolfe parameters)");
				}

				if (crsCode == null) {
					ret.addError("Projection CRS code cannot be assessed");
				}

				monitor.info("Running band tests...");
				int numBands = coverage.getNumSampleDimensions();
				if (numBands > 1 && !userData.contains("band") && !userData.contains("bandmixer")) {
					ret.addError("raster coverage " + coverage.getName()
							+ " has multiple bands but no band selector or band mixer expression are specified");
				} else if (userData.contains("band")) {
					if (numBands < userData.get("band", Integer.class)) {
						ret.addError("raster coverage " + coverage.getName() + " has " + numBands + "  bands but band "
								+ userData.get("band", Integer.class) + " is requested");
					}
				}

				/*
				 * TODO we could (maybe on demand) compute the shape of the covered region
				 */

				/*
				 * If userdata do not contain nodata values, add them
				 */
			}

			if (!ret.hasErrors()) {

				monitor.info("Raster file is valid: resource is OK");
				Geometry geometry = Geometry.create("S2")
						.withBoundingBox(envelope.getMinimum(0), envelope.getMaximum(0), envelope.getMinimum(1),
								envelope.getMaximum(1))
						.withProjection(crsCode)
						.withSpatialShape((long) grid.getGridRange().getSpan(0), (long) grid.getGridRange().getSpan(1));

				ret.withGeometry(geometry);
				
			} else {
				monitor.info("Raster file is invalid; resource has errors");
			}

		} catch (Throwable e) {
			ret.addError("Errors validating resource: " + e.getMessage());
		}

		return ret;
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {

		if (resource == null) {
			return false;
		}
		String extension = MiscUtilities.getFileExtension(resource);
		if (extension != null) {
			return
			// TODO other raster formats understandable by geotools
			extension.toLowerCase().equals("tif") || extension.toLowerCase().equals("tiff")
					|| extension.toLowerCase().equals("geotiff");
		}

		return false;
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		return FileUtils.getSidecarFiles(file, RasterAdapter.secondaryFileExtensions);
	}
}
