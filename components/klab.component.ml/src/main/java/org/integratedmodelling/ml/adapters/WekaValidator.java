package org.integratedmodelling.ml.adapters;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.URLUtils;

import weka.classifiers.bayes.BayesNet;

public class WekaValidator implements IResourceValidator {

	@Override
	public Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder().withType(IArtifact.Type.VALUE);

		try {

			File file = URLUtils.getFileForURL(url);
			monitor.info("Validating " + file + " as WEKA resource");
			ret.withParameter("fileUrl", url).withLocalName(MiscUtilities.getFileName(url.getFile()));

			if (file.toString().endsWith("xdsl")) {
				monitor.info("Importing GENIE file " + file + " as a WEKA resource");
				String wekaClass = userData.get("classifier", BayesNet.class.getCanonicalName());
				if (!wekaClass.equals(BayesNet.class.getCanonicalName())) {
					ret.addError("Cannot import a XDSL file as a classifier that is not a BayesNet");
				} else {
					
				}
			} else if (file.toString().endsWith("bif")) {
				monitor.info("Importing BIF file " + file + " as a WEKA resource");
				String wekaClass = userData.get("classifier", BayesNet.class.getCanonicalName());
				if (!wekaClass.equals(BayesNet.class.getCanonicalName())) {
					ret.addError("Cannot import a XDSL file as a classifier that is not a BayesNet");
				} else {
					
				}
				
			} else {
				monitor.info("Importing WEKA classifier from " + file + " as a WEKA resource");
				
			}
			
			
//			monitor.info("Running access tests...");
//			AbstractGridFormat format = GridFormatFinder.findFormat(file);
//			AbstractGridCoverage2DReader reader = format.getReader(file);
//			GridCoverage2D coverage = reader.read(null);
//			Envelope envelope = coverage.getEnvelope();
//			CoordinateReferenceSystem crs = coverage.getCoordinateReferenceSystem();
//			GridGeometry2D grid = coverage.getGridGeometry();
//			org.integratedmodelling.klab.components.geospace.extents.Envelope refenv = org.integratedmodelling.klab.components.geospace.extents.Envelope
//					.create(envelope, Projection.create(crs));
//			ret.withSpatialExtent(refenv.asShape().getExtentDescriptor());
//
//			String crsCode = null;
//			if (crs == null) {
//				ret.addError("Coverage has no coordinate reference system");
//			} else {
//
//				try {
//					monitor.info("Testing reprojection to WGS84...");
//					CRS.findMathTransform(crs, DefaultGeographicCRS.WGS84);
//					Projection utmProjection = Projection
//							.getUTM(org.integratedmodelling.klab.components.geospace.extents.Envelope.create(envelope,
//									Projection.create(crs)));
//					if (!crs.equals(utmProjection.getCoordinateReferenceSystem())) {
//						monitor.info("Testing reprojection to UTM " + utmProjection + "...");
//						CRS.findMathTransform(crs, utmProjection.getCoordinateReferenceSystem());
//					}
//
//					crsCode = CRS.lookupIdentifier(crs, true);
//
//				} catch (Throwable e) {
//					ret.addError("Coverage projection failed reprojection test (check Bursa-Wolfe parameters)");
//				}
//
//				if (crsCode == null) {
//					ret.addError("Projection CRS code cannot be assessed");
//				}
//
//				monitor.info("Running band tests...");
//				int numBands = coverage.getNumSampleDimensions();
//				if (numBands > 1 && !userData.contains("band") && !userData.contains("bandmixer")) {
//					ret.addError("raster coverage " + coverage.getName()
//							+ " has multiple bands but no band selector or band mixer expression are specified");
//				} else if (userData.contains("band")) {
//					if (numBands < userData.get("band", Integer.class)) {
//						ret.addError("raster coverage " + coverage.getName() + " has " + numBands + "  bands but band "
//								+ userData.get("band", Integer.class) + " is requested");
//					}
//				}
//				
//				int band = userData.get("band", 0);
//				
//				/*
//				 * Nodata value for band. TODO make it a list, which requires moving to
//				 * top level or JSON will complain.
//				 */
//				SampleDimension sdim = coverage.getSampleDimension(band);
//				if (sdim.getNoDataValues() != null) {
//					for (double d : sdim.getNoDataValues()) {
//						ret.withParameter("nodata", d);
//					}
//				}
//
//			}

			if (!ret.hasErrors()) {

//				monitor.info("Raster file is valid: resource is OK");
//				Geometry geometry = Geometry.create("S2")
//						.withBoundingBox(envelope.getMinimum(0), envelope.getMaximum(0), envelope.getMinimum(1),
//								envelope.getMaximum(1))
//						.withProjection(crsCode)
//						.withSpatialShape((long) grid.getGridRange().getSpan(0), (long) grid.getGridRange().getSpan(1));
//
//				ret.withGeometry(geometry);

			} else {
				monitor.info("WEKA resource has errors: validation failed");
			}

		} catch (Throwable e) {
			ret.addError("Errors validating resource: " + e.getMessage());
		}

		return ret;
	}

	@Override
	public List<Operation> getAllowedOperations(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResource performOperation(IResource resource, String operationName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {
		if (resource == null) {
			return false;
		}
		String extension = MiscUtilities.getFileExtension(resource);
		if (extension != null) {
			return
			extension.toLowerCase().equals("bif") || extension.toLowerCase().equals("xdsl")
					|| extension.toLowerCase().equals("model");
		}

		return false;
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		// TODO Auto-generated method stub
		return null;
	}

}
