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
package org.integratedmodelling.klab.raster.files;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.media.jai.Interpolation;
import javax.media.jai.InterpolationBicubic;
import javax.media.jai.InterpolationBicubic2;
import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.geotools.coverage.grid.GeneralGridEnvelope;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.GridFormatFinder;
import org.geotools.coverage.processing.Operations;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.util.factory.Hints;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.ogc.RasterAdapter;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * The {@code RasterEncoder} adapts a raster resource (file-based) to a passed geometry and returns
 * the correspondent IKlabData.
 */
public class RasterEncoder implements IResourceEncoder {
    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry,
            IKlabData.Builder builder, IContextualizationScope scope) {
        encodeFromCoverage(resource, urnParameters, getCoverage(resource, geometry), geometry, builder, scope);
    }

    /**
     * Take a Geotools coverage and do the rest. Separated so that WCS can use it as is.
     * 
     * TODO use URN parameters
     * 
     * @param resource
     * @param coverage
     * @param geometry
     * @param builder
     * @param scope
     */
    public void encodeFromCoverage(IResource resource, Map<String, String> urnParameters, GridCoverage coverage,
            IGeometry geometry, IKlabData.Builder builder, IContextualizationScope scope) {

        /*
         * Set the data from the transformed coverage
         */
        RenderedImage image = coverage.getRenderedImage();
        RandomIter iterator = RandomIterFactory.create(image, null);
        Dimension space = geometry.getDimension(Type.SPACE);
        int band = 0;
        if (urnParameters.containsKey("band")) {
            band = Integer.parseInt(urnParameters.get("band"));
        } else if (!resource.getAdapterType().equals("stac")) {
            resource.getParameters().get("band", 0);
        }
        Set<Double> nodata = getNodata(resource, coverage, band);
        GroovyShell shell = null;
        Binding binding = null;
        Script transformation = null;

        if (resource.getParameters().get("transform") != null
                && !resource.getParameters().get("transform").toString().trim().isEmpty()) {
            binding = new Binding();
            shell = new GroovyShell(binding);
            transformation = shell.parse(resource.getParameters().get("transform").toString());
        }

        /*
         * TODO support band mixer if set in resource
         */
        String bandMixer = null;
        if (resource.getParameters().contains("bandmixer")) {
            bandMixer = resource.getParameters().get("bandmixer", String.class);
            if (!RasterAdapter.bandMixingOperations.contains(bandMixer)) {
                throw new KlabUnsupportedFeatureException("Unsupported band mixing operation " + bandMixer);
            }
        }
        
        /*
         * if so configured, cache the transformed coverage for the space dimension signature
         * 
         * TODO use different methods for non-doubles TODO support multi-band expressions
         */

//        builder = builder.startState(((IRuntimeScope) scope).getTargetName());

        for (long ofs = 0; ofs < space.size(); ofs++) {

            long[] xy = Grid.getXYCoordinates(ofs, space.shape()[0], space.shape()[1]);
            double value = bandMixer == null ? getCellValue(iterator, xy, band) : getCellMixerValue(iterator, xy, bandMixer, band);

            // this is cheeky but will catch most of the nodata and
            // none of the good data
            // FIXME see if this is really necessary
            if (value < -1.0E35 || value > 1.0E35) {
                value = Double.NaN;
            }

            for (double nd : nodata) {
                if (NumberUtils.equal(value, nd)) {
                    value = Double.NaN;
                    break;
                }
            }

            // if (!Double.isNaN(value)) {
            // System.out.println("Setting " + Arrays.toString(xy) + " to " + value + " (ofs
            // = " + ofs + ")");
            // }

            if (transformation != null && Observations.INSTANCE.isData(value)) {
                binding.setVariable("self", value);
                Object o = transformation.run();
                if (o instanceof Number) {
                    value = ((Number) o).doubleValue();
                } else {
                    value = Double.NaN;
                }
            }

            builder.add(value);
        }
//        builder = builder.finishState();
    }

    private double getCellValue(RandomIter iterator, long[] xy, int band) {
        return iterator.getSampleDouble((int) xy[0], (int) xy[1], band);
    }

    private double getCellMixerValue(RandomIter iterator, long[] xy, String mixingOperation, int nBands) {
        if (mixingOperation.equals("max_band")) {
            return getBandOfMaxValue(iterator, xy, nBands);
        }
        if (mixingOperation.equals("min_band")) {
            return getBandOfMinValue(iterator, xy, nBands);
        }
        if (mixingOperation.equals("max_value")) {
            return getMaxCellValue(iterator, xy, nBands);
        }
        if (mixingOperation.equals("min_value")) {
            return getMinCellValue(iterator, xy, nBands);
        }
        if (mixingOperation.equals("avg_value")) {
            return getAvgCellValue(iterator, xy, nBands);
        }
        return Double.NaN;
    }

    private double getBandOfMaxValue(RandomIter iterator, long[] xy, int nBands) {
        double value = Double.NaN;
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < nBands; i++) {
            double currentValue = iterator.getSampleDouble((int) xy[0], (int) xy[1], i);
            if (currentValue == Double.NaN) {
                continue;
            }
            if (currentValue > maxValue) {
                maxValue = currentValue;
                value = i;
            }
        }
        return value;
    }

    private double getBandOfMinValue(RandomIter iterator, long[] xy, int nBands) {
        double value = Double.NaN;
        double minValue = Double.MAX_VALUE;
        for (int i = 0; i < nBands; i++) {
            double currentValue = iterator.getSampleDouble((int) xy[0], (int) xy[1], i);
            if (currentValue == Double.NaN) {
                continue;
            }
            if (currentValue < minValue) {
                minValue = currentValue;
                value = i;
            }
        }
        return value;
    }

    private double getMaxCellValue(RandomIter iterator, long[] xy, int nBands) {
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < nBands; i++) {
            double currentValue = iterator.getSampleDouble((int) xy[0], (int) xy[1], i);
            if (currentValue == Double.NaN) {
                continue;
            }
            if (currentValue > maxValue) {
                maxValue = currentValue;
            }
        }
        return maxValue == Double.MIN_VALUE ? Double.NaN : maxValue;
    }

    private double getMinCellValue(RandomIter iterator, long[] xy, int nBands) {
        double minValue = Double.MAX_VALUE;
        for (int i = 0; i < nBands; i++) {
            double currentValue = iterator.getSampleDouble((int) xy[0], (int) xy[1], i);
            if (currentValue == Double.NaN) {
                continue;
            }
            if (currentValue < minValue) {
                minValue = currentValue;
            }
        }
        return minValue == Double.MAX_VALUE ? Double.NaN : minValue;
    }

    private double getAvgCellValue(RandomIter iterator, long[] xy, int nBands) {
        int validBands = 0;
        double sum = 0.0;
        for (int i = 0; i < nBands; i++) {
            double currentValue = iterator.getSampleDouble((int) xy[0], (int) xy[1], i);
            if (Double.isNaN(currentValue)) {
                continue;
            }
            sum += currentValue;
            validBands++;
        }
        if (validBands == 0) {
            return Double.NaN;
        }
        return sum / validBands;
    }

    private Set<Double> getNodata(IResource resource, GridCoverage coverage, int band) {
        Set<Double> ret = new HashSet<>();
        if (resource.getParameters().contains("nodata")) {
            ret.add(resource.getParameters().get("nodata", Double.class));
        }
        return ret;
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
            throw new KlabInternalErrorException("raster encoder: cannot create grid for raster projection: shape is not a grid");
        }
        GeneralGridEnvelope gridRange = new GeneralGridEnvelope(new int[]{0, 0},
                new int[]{(int) space.shape()[0], (int) space.shape()[1]}, false);

        return new GridGeometry2D(gridRange, envelope);
    }

    /**
     * Coverages with caching. We keep a configurable total of coverages in memory using the session
     * cache, including their transformations indexed by geometry.
     * 
     * @param resource
     * @return a coverage for the untransformed data. Never null
     */
    private GridCoverage getCoverage(IResource resource, IGeometry geometry) {

        GridCoverage coverage = getOriginalCoverage(resource);

        // TODO if we have it in the cache for the principal file + space signature,
        // return that

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
        GridCoverage transformedCoverage = (GridCoverage) Operations.DEFAULT.resample(coverage, envelope, interpolation);

        /*
         * then resample
         */
        transformedCoverage = (GridCoverage) Operations.DEFAULT.resample(transformedCoverage, crs, gridGeometry, interpolation);

        return transformedCoverage;
    }

    private GridCoverage getOriginalCoverage(IResource resource) {

        File mainFile = null;
        File rootPath = Resources.INSTANCE.getFilesystemLocation(resource);

        for (String path : resource.getLocalPaths()) {
            if (RasterAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(path))) {
                mainFile = new File(rootPath + File.separator + path);
                if (mainFile.exists() && mainFile.canRead()) {
                    break;
                }
            }
        }

        if (mainFile == null) {
            throw new KlabResourceNotFoundException("raster resource " + resource.getUrn() + " cannot be accessed");
        }

        return readCoverage(mainFile);
    }

    public GridCoverage readCoverage(File mainFile) {

        GridCoverage2D ret = null;
        AbstractGridFormat format = GridFormatFinder.findFormat(mainFile);
        // this is a bit hackey but does make more geotiffs work
        Hints hints = new Hints();
        if (format instanceof GeoTiffFormat) {
            hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
        }
        GridCoverage2DReader reader = format.getReader(mainFile, hints);
        try {
            ret = reader.read(null);
        } catch (IOException e) {
            throw new KlabIOException(e);
        }

        // TODO caching?

        return ret;
    }

    @Override
    public boolean isOnline(IResource resource, IMonitor monitor) {
        return true;// NetUtilities.urlResponds(resource.getParameters().get("serviceUrl",
                    // String.class));
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
