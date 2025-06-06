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
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.geotools.coverage.grid.*;
import org.geotools.coverage.GridSampleDimension;
import org.hortonmachine.gears.io.rasterreader.OmsRasterReader;
import org.hortonmachine.gears.io.rasterwriter.OmsRasterWriter;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.modules.HMRaster.HMRasterWritableBuilder;
import org.hortonmachine.gears.utils.RegionMap;
import org.integratedmodelling.klab.Configuration;
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
import org.geotools.process.raster.BandMergeProcess;
import java.awt.image.RenderedImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.BandSelectDescriptor;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.processing.Operations;
import org.geotools.referencing.CRS;
import org.geotools.gce.geotiff.GeoTiffWriter;
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

		WCSService service = WcsAdapter.getService(resource.getParameters().get("serviceUrl", String.class),
				Version.create(resource.getParameters().get("wcsVersion", String.class)));
		WCSLayer layer = service.getLayer(resource.getParameters().get("wcsIdentifier", String.class));
		if (layer != null) {
			encoder.encodeFromCoverage(resource, urnParameters, getCoverage(layer, resource, geometry, interpolation),
					geometry, builder, scope);
		} else {
			scope.getMonitor().warn(
					"Problems accessing WCS layer " + resource.getParameters().get("wcsIdentifier", String.class));
		}
	}

	private GridCoverage getCoverage(WCSLayer layer, IResource resource, IGeometry geometry, String interpolation) {

		File coverageFile = WcsAdapter.getCachedFile(layer.getIdentifier(), geometry.toString());

		if (coverageFile == null) {

			// forcing v1.0.0 for now, while I figure out the pain of WCS requests
			URL getCov = layer.getService().buildRetrieveUrl(layer, Version.create("1.0.0"), geometry, interpolation);

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

			if (Configuration.INSTANCE.isEchoEnabled()) {
				System.out.println("Opening URL " + getCov);
			}
			try (InputStream input = getCov.openStream()) {
				coverageFile = getAdjustedCoverage(input, geometry);
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
	
	
	public static GridCoverage2D getAdjustedCoverage(GridCoverage2D inCoverage, IGeometry geometry) {
		try {
			Dimension space = geometry.getDimension(IGeometry.Dimension.Type.SPACE);
			String rcrs = space.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
			Projection crs = Projection.create(rcrs);
			int cols = (int) space.shape()[0];
			int rows = (int) space.shape()[1];
			System.out.println(rows);
			System.out.println(cols);
			double[] extent = space.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);
			String receivedCRS = "EPSG:"+String.valueOf(CRS.lookupEpsgCode(inCoverage.getCoordinateReferenceSystem(), true));
					
			// If CRS of the Received Raster doesn't match with the expected one
			// we reproject the coverage, so needless to say that some info would always be lost
			// usually, in klab we work with EPSG:4326, but from OpenEO we usually get stuff in EPSG:3035
			// atleast for WEED Project, they do so and if we pass target_epsg: 4326, it fails. 
			if (!receivedCRS.equals(rcrs)) {
				System.out.println("Received CRS: " + receivedCRS);
				System.out.println("Expected CRS: " + rcrs);
				System.out.println("Received CRS and Expected CRS doesn't match...");
				System.out.println("Writring updated Coverage in the Target CRS");
				CoordinateReferenceSystem targetCRS = CRS.decode(rcrs, true);
				// Reproject the raster
		        inCoverage = (GridCoverage2D) Operations.DEFAULT.resample(
		                inCoverage, targetCRS
		        );
		        System.out.println("Sucessfully written updated coverage..");
			}
			
			int numBands = inCoverage.getRenderedImage().getSampleModel().getNumBands();
			System.out.println("Num Bands: "+ numBands);
	        
			Envelope envelope = inCoverage.getEnvelope();
			DirectPosition lowerCorner = envelope.getLowerCorner();
			double[] westSouth = lowerCorner.getCoordinate();
			DirectPosition upperCorner = envelope.getUpperCorner();
			double[] eastNorth = upperCorner.getCoordinate();

			org.locationtech.jts.geom.Envelope requestedExtend = new org.locationtech.jts.geom.Envelope(extent[0],
					extent[1], extent[2], extent[3]);
			
			System.out.println("Requested Extent:");
			System.out.println(extent[0]);
			System.out.println(extent[1]);
			System.out.println(extent[2]);
			System.out.println(extent[3]);

			
			org.locationtech.jts.geom.Envelope recievedExtend = new org.locationtech.jts.geom.Envelope(westSouth[0],
					eastNorth[0], westSouth[1], eastNorth[1]);

			double recievedArea = recievedExtend.getArea();
			System.out.println(recievedArea);
			
			double requestedArea = requestedExtend.getArea();
			double diff = Math.abs(requestedArea - recievedArea);
			// double diff = requestedArea - recievedArea;
			System.out.println(requestedArea);
			System.out.println("Diff b/w Requested and Received:");
			System.out.println(requestedArea - recievedArea);
			if (diff > 0) { // diff in degree angles^2 since we are working with EPSG:4326
				// need to pad
				System.out.println("Padding Stuff...");
				
				HMRaster raster = HMRaster.fromGridCoverage(inCoverage);
				RegionMap region = RegionMap.fromEnvelopeAndGrid(requestedExtend, cols, rows);

				BandMergeProcess process = new BandMergeProcess();
				List<GridCoverage2D> coverageList = new ArrayList<>();
				
				// iterate over each bands, and pad them individually and 
				// get the updated coverage
				for (int b = 0; b < numBands; b++) {
					System.out.println("Doing for band " + b);
					RenderedOp singleBandImage = BandSelectDescriptor.create(
							inCoverage.getRenderedImage(),
		                    new int[]{b},
		                    null
		            );
					
					GridSampleDimension bandSampleDimension = inCoverage.getSampleDimension(b);
					GridCoverageFactory factory = new GridCoverageFactory();

		            GridCoverage2D singleBandCoverage = factory.create(
		                    "Band_" + (b + 1),
		                    singleBandImage,
		                    inCoverage.getEnvelope(),
		                    new GridSampleDimension[]{bandSampleDimension},
		                    null,
		                    null
		            );
		            
					HMRaster bandRaster = HMRaster.fromGridCoverage(singleBandCoverage);
					HMRaster paddedRaster = new HMRasterWritableBuilder().setName("padded").setRegion(region)
							.setCrs(crs.getCoordinateReferenceSystem()).setNoValue(raster.getNovalue()).build();
					
					paddedRaster.mapRaster(null, bandRaster, null);
					GridCoverage2D paddedBandCoverage = paddedRaster.buildCoverage();
					coverageList.add(paddedBandCoverage);	
					System.out.println("Done for band :" + b);
					
				}
				inCoverage = process.execute(coverageList, null, null, null);
			}
			
			numBands = inCoverage.getRenderedImage().getSampleModel().getNumBands();
			System.out.println("Num Bands after Everything.. : "+ numBands);
			return inCoverage;

		} catch (Throwable e) {
			e.printStackTrace();
			throw new KlabIOException(e);
		}
	}

	
	public static File getAdjustedCoverage(InputStream input, IGeometry geometry) {

		try {

			File coverageFile = File.createTempFile("geo", ".tiff");
			FileUtils.copyInputStreamToFile(input, coverageFile);

			Dimension space = geometry.getDimension(IGeometry.Dimension.Type.SPACE);
			String rcrs = space.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
			Projection crs = Projection.create(rcrs);
			int cols = (int) space.shape()[0];
			int rows = (int) space.shape()[1];
			System.out.println(rows);
			System.out.println(cols);
			double[] extent = space.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);
			 
			GridCoverage2D coverage = OmsRasterReader.readRaster(coverageFile.getAbsolutePath());
			String receivedCRS = "EPSG:"+String.valueOf(CRS.lookupEpsgCode(coverage.getCoordinateReferenceSystem(), true));
					
			// If CRS of the Received Raster doesn't match with the expected one
			// we reproject the coverage, so needless to say that some info would always be lost
			// usually, in klab we work with EPSG:4326, but from OpenEO we usually get stuff in EPSG:3035
			// atleast for WEED Project, they do so and if we pass target_epsg: 4326, it fails. 
			if (!receivedCRS.equals(rcrs)) {
				System.out.println("Received CRS: " + receivedCRS);
				System.out.println("Expected CRS: " + rcrs);
				System.out.println("Received CRS and Expected CRS doesn't match...");
				System.out.println("Writring updated Coverage in the Target CRS");
				CoordinateReferenceSystem targetCRS = CRS.decode(rcrs, true);
				// Reproject the raster
		        coverage = (GridCoverage2D) Operations.DEFAULT.resample(
		                coverage, targetCRS
		        );
		        System.out.println("Sucessfully written updated coverage..");
			}
			
			int numBands = coverage.getRenderedImage().getSampleModel().getNumBands();
			System.out.println("Num Bands: "+ numBands);
	        
			Envelope envelope = coverage.getEnvelope();
			DirectPosition lowerCorner = envelope.getLowerCorner();
			double[] westSouth = lowerCorner.getCoordinate();
			DirectPosition upperCorner = envelope.getUpperCorner();
			double[] eastNorth = upperCorner.getCoordinate();

			org.locationtech.jts.geom.Envelope requestedExtend = new org.locationtech.jts.geom.Envelope(extent[0],
					extent[1], extent[2], extent[3]);
			
			System.out.println("Requested Extent:");
			System.out.println(extent[0]);
			System.out.println(extent[1]);
			System.out.println(extent[2]);
			System.out.println(extent[3]);

			
			org.locationtech.jts.geom.Envelope recievedExtend = new org.locationtech.jts.geom.Envelope(westSouth[0],
					eastNorth[0], westSouth[1], eastNorth[1]);

			double recievedArea = recievedExtend.getArea();
			System.out.println(recievedArea);
			
			double requestedArea = requestedExtend.getArea();
			double diff = Math.abs(requestedArea - recievedArea);
			// double diff = requestedArea - recievedArea;
			System.out.println(requestedArea);
			System.out.println("Diff b/w Requested and Received:");
			System.out.println(requestedArea - recievedArea);
			if (diff > 0) { // diff in degree angles^2 since we are working with EPSG:4326
				// need to pad
				System.out.println("Padding Stuff...");
				
				HMRaster raster = HMRaster.fromGridCoverage(coverage);
				RegionMap region = RegionMap.fromEnvelopeAndGrid(requestedExtend, cols, rows);

				BandMergeProcess process = new BandMergeProcess();
				List<GridCoverage2D> coverageList = new ArrayList<>();
				
				// iterate over each bands, and pad them individually and 
				// get the updated coverage
				for (int b = 0; b < numBands; b++) {
					System.out.println("Doing for band " + b);
					RenderedOp singleBandImage = BandSelectDescriptor.create(
							coverage.getRenderedImage(),
		                    new int[]{b},
		                    null
		            );
					
					GridSampleDimension bandSampleDimension = coverage.getSampleDimension(b);
					GridCoverageFactory factory = new GridCoverageFactory();

		            GridCoverage2D singleBandCoverage = factory.create(
		                    "Band_" + (b + 1),
		                    singleBandImage,
		                    coverage.getEnvelope(),
		                    new GridSampleDimension[]{bandSampleDimension},
		                    null,
		                    null
		            );
		            
					HMRaster bandRaster = HMRaster.fromGridCoverage(singleBandCoverage);
					HMRaster paddedRaster = new HMRasterWritableBuilder().setName("padded").setRegion(region)
							.setCrs(crs.getCoordinateReferenceSystem()).setNoValue(raster.getNovalue()).build();
					
					paddedRaster.mapRaster(null, bandRaster, null);
					GridCoverage2D paddedBandCoverage = paddedRaster.buildCoverage();
					coverageList.add(paddedBandCoverage);	
					System.out.println("Done for band :" + b);
					
				}
				coverage = process.execute(coverageList, null, null, null);
				OmsRasterWriter.writeRaster(coverageFile.getAbsolutePath(), coverage);
			}
			
			numBands = coverage.getRenderedImage().getSampleModel().getNumBands();
			System.out.println("Num Bands after Everything.. : "+ numBands);

			FileUtils.forceDeleteOnExit(coverageFile);
			if (Configuration.INSTANCE.isEchoEnabled()) {
				System.out.println("Data have arrived in " + coverageFile);
			}

			return coverageFile;

		} catch (Throwable e) {
			e.printStackTrace();
			throw new KlabIOException(e);
		}
	}

	@Override
	public boolean isOnline(IResource resource, IMonitor monitor) {

		WCSService service = WcsAdapter.getService(resource.getParameters().get("serviceUrl", String.class),
				Version.create(resource.getParameters().get("wcsVersion", String.class)));
		if (service == null) {
			monitor.error("Service " + resource.getParameters().get("serviceUrl", String.class)
					+ " does not exist: likely the service URL is wrong or offline");
			return false;
		}

		WCSLayer layer = service.getLayer(resource.getParameters().get("wcsIdentifier", String.class));

		if (layer == null) {
			monitor.error("Layer " + resource.getParameters().get("wcsIdentifier", String.class)
					+ (service.containsIdentifier(resource.getParameters().get("wcsIdentifier", String.class))
							? "was found in service with a null value"
							: " was not found in service ")
					+ resource.getParameters().get("serviceUrl", String.class)
					+ ": likely the layer ID is wrong or the layer was removed");
		} else if (layer.isError()) {
			monitor.error("Layer " + resource.getParameters().get("wcsIdentifier", String.class)
					+ " has errors: associated message was: " + layer.getMessage());
		}

		return layer != null && !layer.isError();
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
