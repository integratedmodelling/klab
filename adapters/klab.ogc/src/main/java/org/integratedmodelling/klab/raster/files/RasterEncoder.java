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

import java.awt.image.RenderedImage;
import java.io.File;

import javax.media.jai.Interpolation;
import javax.media.jai.InterpolationBicubic;
import javax.media.jai.InterpolationBicubic2;
import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.grid.GeneralGridEnvelope;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverage.processing.Operations;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData.State;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.ogc.RasterAdapter;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * The {@code RasterEncoder} adapts a raster resource (file-based) to a passed
 * geometry and returns the correspondent IKlabData.
 */
public class RasterEncoder implements IResourceEncoder {

	@Override
	public IKlabData getEncodedData(IResource resource, IGeometry geometry, IMonitor monitor) {

		State.Builder sBuilder = KlabData.State.newBuilder();

		/*
		 * Find and open the files to Geotools coverages. TODO support time-aware
		 * scenarios.
		 */
		GridCoverage coverage = getCoverage(resource, geometry);

		/*
		 * Set the data from the transformed coverage
		 */
		RenderedImage image = coverage.getRenderedImage();
		RandomIter iterator = RandomIterFactory.create(image, null);
		Dimension space = geometry.getDimension(Type.SPACE);
		int band = getRasterBand(resource);
		double[] nodata = getNodata(resource);

		/*
		 * if so configured, cache the transformed coverage for the space dimension
		 * signature
		 * 
		 * TODO use different methods for non-doubles
		 */
		for (long ofs = 0; ofs < space.size(); ofs++) {

			long[] xy = Grid.getXYCoordinates(ofs, space.shape()[0], space.shape()[1]);

			Double value = iterator.getSampleDouble((int) xy[0], (int) xy[1], band);

			// this is cheeky but will catch most of the nodata and
			// none of the good data
			if (value < -1.0E35 || value > 1.0E35) {
				value = Double.NaN;
			}
			
			for (double nd : nodata) {
				if (NumberUtils.equal(value, nd)) {
					value = Double.NaN;
				}
			}
			
			sBuilder.addDoubledata(value);

		}

		return KlabData.newBuilder().setGeometry("S2").setState(sBuilder.build()).build();
	}

	private double[] getNodata(IResource resource) {
		return new double[] {};
	}

	private int getRasterBand(IResource resource) {
		// TODO Auto-generated method stub
		return 0;
	}

	private CoordinateReferenceSystem getCrs(IGeometry geometry) {

		if (geometry instanceof Scale) {
			ISpace space = ((Scale) geometry).getSpace();
			return ((Projection) space.getProjection()).getCoordinateReferenceSystem();
		}

		Dimension space = geometry.getDimension(Type.SPACE);
		String epsg = space.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
		if (epsg != null) {
			try {
				return CRS.decode(epsg);
			} catch (Exception e) {
				// fall back to later exception
			}
		}

		throw new KlabInternalErrorException("raster encoder: cannot create projection from geometry");
	}

	private Interpolation getInterpolation(IMetadata metadata) {

		String method = metadata.get(RasterAdapter.INTERPOLATION_TYPE_FIELD, String.class);
		if (method != null) {
			if (method.equals("bilinear")) {
				return new InterpolationBilinear();
			} else if (method.equals("nearest")) {
				return new InterpolationNearest();
			} else if (method.equals("bicubic")) {
				// TODO CHECK BITS
				return new InterpolationBicubic(8);
			} else if (method.equals("bicubic2")) {
				// TODO CHECK BITS
				return new InterpolationBicubic2(8);
			}
		}
		return new InterpolationNearest();
	}

	private ReferencedEnvelope getEnvelope(IGeometry geometry, CoordinateReferenceSystem crs) {
		if (geometry instanceof Scale) {
			ISpace space = ((Scale) geometry).getSpace();
			return ((Envelope) space.getEnvelope()).getJTSEnvelope();
		}
		return null;
	}

	private GridGeometry2D getGridGeometry(IGeometry geometry, ReferencedEnvelope envelope) {

		Dimension space = geometry.getDimension(Type.SPACE);
		if (space.shape().length != 2 || !space.isRegular()) {
			throw new KlabInternalErrorException(
					"raster encoder: cannot create grid for raster projection: shape is not a grid");
		}
		GeneralGridEnvelope gridRange = new GeneralGridEnvelope(new int[] { 0, 0 },
				new int[] { (int) space.shape()[0], (int) space.shape()[1] }, false);

		return new GridGeometry2D(gridRange, envelope);
	}

	/**
	 * Coverages with caching. We keep a configurable total of coverages in memory
	 * using the session cache, including their transformations indexed by geometry.
	 * 
	 * @param resource
	 * @return a coverage for the untransformed data. Never null
	 */
	private GridCoverage getCoverage(IResource resource, IGeometry geometry) {

		// TODO if we have it in the cache for the principal file + space signature,
		// return that

		GridCoverage coverage = getOriginalCoverage(resource);

		/*
		 * build the needed Geotools context and the interpolation method
		 */
		CoordinateReferenceSystem crs = getCrs(geometry);
		ReferencedEnvelope envelope = getEnvelope(geometry, crs);
		GridGeometry2D gridGeometry = getGridGeometry(geometry, envelope);
		Interpolation interpolation = getInterpolation(resource.getMetadata());

		/*
		 * subset first
		 */
		GridCoverage transformedCoverage = (GridCoverage) Operations.DEFAULT.resample(coverage, envelope,
				interpolation);

		/*
		 * then resample
		 */
		transformedCoverage = (GridCoverage) Operations.DEFAULT.resample(transformedCoverage, crs, gridGeometry,
				interpolation);

		return transformedCoverage;
	}

	private GridCoverage getOriginalCoverage(IResource resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOnline(IResource resource) {

		File base = null;
		if (Urns.INSTANCE.isLocal(resource.getUrn())) {
			base = Resources.INSTANCE.getLocalWorkspace().getRoot();
		} else {
			// TODO
		}

		if (base == null) {
			return false;
		}

		for (String s : resource.getLocalPaths()) {
			File rfile = new File(base + File.separator + s);
			if (!rfile.exists() || !rfile.canRead()) {
				return false;
			}
		}

		return true;
	}

}
