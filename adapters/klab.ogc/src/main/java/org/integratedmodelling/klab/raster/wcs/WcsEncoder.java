/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.raster.wcs;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.gears.io.rasterreader.OmsRasterReader;
import org.hortonmachine.gears.io.rasterwriter.OmsRasterWriter;
import org.hortonmachine.gears.io.wcs.ICoverageSummary;
import org.hortonmachine.gears.io.wcs.IWebCoverageService;
import org.hortonmachine.gears.io.wcs.readers.CoverageReaderParameters;
import org.hortonmachine.gears.io.wcs.wcs201.WebCoverageService201;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.modules.HMRaster.HMRasterWritableBuilder;
import org.hortonmachine.gears.utils.RegionMap;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.ogc.WcsAdapter;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.raster.wcs.WCSService.WCSLayer;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.geometry.DirectPosition;
import org.opengis.geometry.Envelope;

/**
 * The Class WcsEncoder.
 */
public class WcsEncoder implements IResourceEncoder {

	/**
	 * The raster encoder that does the actual work after we get our coverage from
	 * the service.
	 */
	RasterEncoder encoder = new RasterEncoder();

	@Override
	public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
			Builder builder, IContextualizationScope scope) {

		String interpolation = urnParameters.get("interpolation");
		
		String serviceUrl = resource.getParameters().get("serviceUrl", String.class);
		String coverageId = null;
		try {
			IWebCoverageService service = WcsCache.INSTANCE.getOrCreate(serviceUrl);
//			IWebCoverageService service = new WebCoverageService201(serviceUrl);  
			coverageId = resource.getParameters().get("wcsIdentifier", String.class);

//		WCSService service = WcsAdapter.getService(serviceUrl,
//				Version.create(resource.getParameters().get("wcsVersion", String.class)));
//		WCSLayer layer = service.getLayer(resource.getParameters().get("wcsIdentifier", String.class));
			ICoverageSummary layer = service.getCoverageSummary(coverageId);
			if (layer != null) {
				GridCoverage coverage = getCoverage(service, layer, resource, geometry, interpolation);
				encoder.encodeFromCoverage(resource, urnParameters, coverage,
						geometry, builder, scope);
			} else {
				scope.getMonitor().warn(
						"Problems accessing WCS layer " + coverageId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			scope.getMonitor().error(
					"An error occurred while accessing WCS layer " + coverageId + "(" + e.getLocalizedMessage() + ")");
		}
	}

	private GridCoverage getCoverage(IWebCoverageService service, ICoverageSummary layer, IResource resource, IGeometry geometry, String interpolation) {

		File coverageFile = WcsAdapter.getCachedFile(layer.getCoverageId(), geometry.toString());

		if (coverageFile == null) {

			// forcing v1.0.0 for now, while I figure out the pain of WCS requests
//			URL getCov = layer.getService().buildRetrieveUrl(layer, Version.create("1.0.0"), geometry, interpolation);

			// URLConnection connection = getCov.openConnection();
			// /*
			// * set configured timeout
			// */
			// if
			// (KLAB.CONFIG.getProperties().containsKey(IConfiguration.KLAB_CONNECTION_TIMEOUT))
			// {
			// int timeout = 1000 * Integer.parseInt(KLAB.CONFIG.getProperties()
			// .getProperty(IConfiguration.KLAB_CONNECTION_TIMEOUT, "10"));
			// connection.setConnectTimeout(timeout);
			// connection.setReadTimeout(timeout);
			// }

//			if (Configuration.INSTANCE.isEchoEnabled()) {
//				System.out.println("Opening URL " + getCov);
//			}
			
			Dimension space = geometry.getDimension(IGeometry.Dimension.Type.SPACE);
	        if (space.shape().length != 2 || !space.isRegular()) {
	            throw new IllegalArgumentException("cannot retrieve  a grid dataset from WCS in a non-grid context");
	        }

	        String rcrs = space.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
	        Projection crs = Projection.create(rcrs);
	        double[] extent = space.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);
	        int xc = (int) space.shape()[0];
	        int yc = (int) space.shape()[1];

	        double west = extent[0];
	        double east = extent[1];
	        double south = extent[2];
	        double north = extent[3];

	        /*
	         * jiggle by the projection's equivalent of a few meters if we're asking for a single point,
	         * so WCS does not go crazy.
	         */
	        if (NumberUtils.equal(west, east)) {
	            double delta = (crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(0).getMaximumValue()
	                    - crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(0).getMinimumValue()) / 3900000.0;
	            west -= delta;
	            east += delta;
	        }

	        if (NumberUtils.equal(north, south)) {
	            double delta = (crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(1).getMaximumValue()
	                    - crs.getCoordinateReferenceSystem().getCoordinateSystem().getAxis(1).getMinimumValue()) / 3900000.0;
	            south -= delta;
	            north += delta;
	        }
			
	        try {
				coverageFile = File.createTempFile("geo", ".tiff");
				CoverageReaderParameters params = new CoverageReaderParameters(service, layer.getCoverageId());
				params.format("image/tiff");
	
				String epsg = crs.getSimpleSRS();
				int srid = Integer.parseInt(epsg.substring(5));
		        params.bbox(new org.locationtech.jts.geom.Envelope(west, east, south, north), srid);
		        params.outputSrid(srid);
		        params.rowsCols(xc, yc);
		        
		        // read the actual coverage
				String urlUsed = service.getCoverage(coverageFile.getAbsolutePath(), params , null);
				
				if (Configuration.INSTANCE.isEchoEnabled()) {
					System.out.println(urlUsed);
				}
				System.out.println(coverageFile.getAbsolutePath());
				
//				coverageFile = getAdjustedCoverage(coverageFile, geometry);
				GridCoverage readCoverage = encoder.readCoverage(coverageFile);
				return readCoverage;
			
//			try (InputStream input = getCov.openStream()) {
//				coverageFile = getAdjustedCoverage(input, geometry);
			} catch (Throwable e) {
				throw new KlabIOException(e);
			}
		}
		
		return encoder.readCoverage(coverageFile);
		
	}

	public static File getAdjustedCoverage(String url, IGeometry geometry) {
		try (InputStream input = new URL(url).openStream()) {
			URL getCov = new URL(url);
			return getAdjustedCoverage(getCov.openStream(), geometry);
		} catch (Throwable e) {
			throw new KlabIOException(e);
		}
	}

	
	public static File getAdjustedCoverage(InputStream input, IGeometry geometry) {
		return null;
	}
	
	public static File getAdjustedCoverage(File coverageFile, IGeometry geometry) {

		try {

			Dimension space = geometry.getDimension(IGeometry.Dimension.Type.SPACE);
			String rcrs = space.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
			Projection crs = Projection.create(rcrs);
			int cols = (int) space.shape()[0];
			int rows = (int) space.shape()[1];
			double[] extent = space.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);

			GridCoverage2D coverage = OmsRasterReader.readRaster(coverageFile.getAbsolutePath());
			Envelope envelope = coverage.getEnvelope();
			DirectPosition lowerCorner = envelope.getLowerCorner();
			double[] westSouth = lowerCorner.getCoordinate();
			DirectPosition upperCorner = envelope.getUpperCorner();
			double[] eastNorth = upperCorner.getCoordinate();

			org.locationtech.jts.geom.Envelope requestedExtend = new org.locationtech.jts.geom.Envelope(extent[0],
					extent[1], extent[2], extent[3]);
			org.locationtech.jts.geom.Envelope recievedExtend = new org.locationtech.jts.geom.Envelope(westSouth[0],
					eastNorth[0], westSouth[1], eastNorth[1]);

			double recievedArea = recievedExtend.getArea();
			double requestedArea = requestedExtend.getArea();
			double diff = Math.abs(requestedArea - recievedArea);
			if (diff > 0.01) {
				// need to pad
				HMRaster raster = HMRaster.fromGridCoverage(coverage);
				RegionMap region = RegionMap.fromEnvelopeAndGrid(requestedExtend, cols, rows);
				HMRaster paddedRaster = new HMRasterWritableBuilder().setName("padded").setRegion(region)
						.setCrs(crs.getCoordinateReferenceSystem()).setNoValue(raster.getNovalue()).build();
				paddedRaster.mapRaster(null, raster, null);
				coverage = paddedRaster.buildCoverage();
				OmsRasterWriter.writeRaster(coverageFile.getAbsolutePath(), coverage);
			}

			FileUtils.forceDeleteOnExit(coverageFile);
			if (Configuration.INSTANCE.isEchoEnabled()) {
				System.out.println("Data have arrived in " + coverageFile);
			}

			return coverageFile;

		} catch (Throwable e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {

		String serviceUrl = resource.getParameters().get("serviceUrl", String.class);
		IWebCoverageService service = null;
		try {
			service = WcsCache.INSTANCE.getOrCreate(serviceUrl);
		
	//		WCSService service = WcsAdapter.getService(serviceUrl,
	//				Version.create(resource.getParameters().get("wcsVersion", String.class)));
			if (service == null) {
				monitor.error("Service " + serviceUrl
						+ " does not exist: likely the service URL is wrong or offline");
				return false;
			}
	
			String wcsIdentifier = resource.getParameters().get("wcsIdentifier", String.class);
	//		WCSLayer layer = service.getLayer(wcsIdentifier);
			List<String> coverageIds = service.getCoverageIds();
	
			if (!coverageIds.stream().anyMatch(cid -> cid.equals(wcsIdentifier))) {
				monitor.error("Layer " + wcsIdentifier + " was not found in service "
						+ resource.getParameters().get("serviceUrl", String.class)
						+ ": likely the layer ID is wrong or the layer was removed");
	//		} else if (layer.isError()) {
	//			monitor.error("Layer " + resource.getParameters().get("wcsIdentifier", String.class)
	//					+ " has errors: associated message was: " + layer.getMessage());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			monitor.error("Service " + serviceUrl
					+ " does not exist: likely the service URL is wrong or offline. (" + e.getLocalizedMessage() + ")");
			return false;
		}

		return true;
	}

	@Override
	public IResource contextualize(IResource resource, IScale scale, IArtifact targetObservation,
			Map<String, String> urnParameters, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return resource;
	}

	@Override
	public ICodelist categorize(IResource resource, String attribute, IMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
		// TODO Auto-generated method stub

	}

}
