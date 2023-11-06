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
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.hortonmachine.gears.io.wcs.ICoverageSummary;
import org.hortonmachine.gears.io.wcs.IDescribeCoverage;
import org.hortonmachine.gears.io.wcs.IWebCoverageService;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.ogc.WcsAdapter;
import org.integratedmodelling.klab.raster.wcs.WCSService.WCSLayer;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.locationtech.jts.geom.Envelope;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

/**
 * The Class WcsValidator.
 */
public class WcsValidator implements IResourceValidator {

    @Override
    public Builder validate(String urn, URL url, IParameters<String> userData, IMonitor monitor) {

		if (!canHandle(null, userData)) {
			throw new IllegalArgumentException("WCS specifications are invalid or incomplete");
		}

		int band = userData.get("band", 0);

		String serviceUrl = userData.get("serviceUrl", String.class);
		IWebCoverageService wcs;
		try {
			wcs = WcsCache.INSTANCE.getOrCreate(serviceUrl);

			userData.put("wcsVersion", wcs.getVersion());

//		WCSService service = WcsAdapter.getService(serviceUrl,
//                Version.create(userData.get("wcsVersion", String.class)));

			String layerId = userData.get("wcsIdentifier", String.class);
//        if (service.TRANSLATE_DOUBLEUNDERSCORE_TO_NAMESPACE_SEPARATOR) {
//            layerId = layerId.replaceAll(":", "__");
//        }
//
//        WCSLayer layer = service.getLayer(layerId);

			ICoverageSummary layer = wcs.getCoverageSummary(layerId);
			if (layer == null) {
				throw new KlabResourceNotFoundException(
						"WCS layer " + userData.get("wcsIdentifier") + " not found on server");
			}

			/*
			 * Substitute user identifier with official one from layer, validating the layer
			 * at the same time.
			 */
			String identifier = layer.getCoverageId();

//        if (layer.isError()) {
//            throw new KlabResourceNotFoundException(
//                    "WCS layer " + userData.get("wcsIdentifier") + " is available but has errors");
//        }

			userData.put("wcsIdentifier", identifier);
			// TODO get novalue
//        if (!layer.getNodata(band).isEmpty()) {
//            userData.put("nodata", layer.getNodata(band).iterator().next());
//        }
			Envelope env = layer.getBoundingBox();
			Integer srid = layer.getBoundingBoxSrid();
			if(env == null) {
				IDescribeCoverage describeCoverage = wcs.getDescribeCoverage(identifier);
				env = describeCoverage.getCoverageEnvelope();
				srid = describeCoverage.getCoverageEnvelopeSrid();
			}
			
			IGeometry geometry = getGeometry(env, srid);

			return new ResourceBuilder(urn).withParameters(userData).withParameter("transform", "")
					.withType(Type.NUMBER).withGeometry(geometry).withSpatialExtent(getSpatialExtent(env, srid));
		} catch (Exception e) {
			throw new KlabResourceNotFoundException(
					"Unable to create wcs service for url: " + serviceUrl + ". (" + e.getLocalizedMessage() + ")");
		}
    }
    
    private SpatialExtent getSpatialExtent(Envelope env, Integer srid) throws Exception {
    	
    	ReferencedEnvelope rEnv = new ReferencedEnvelope(env, CRS.decode("EPSG:" + srid));
    	if(srid!=4326) {
    		rEnv = rEnv.transform(DefaultGeographicCRS.WGS84	,true);
    	}
//        ReferencedEnvelope wgs84envelope = summary.getWgs84BoundingBox();
//		if (wgs84envelope == null) {
//            return null;
//        }
        SpatialExtent ret = new SpatialExtent();
        ret.setWest(rEnv.getMinX());
        ret.setEast(rEnv.getMaxX());
        ret.setSouth(rEnv.getMinY());
        ret.setNorth(rEnv.getMaxY());
        return ret;
    }
    
    /**
     * Build and return the geometry for the layer. If the layer comes from WCS 1.x it won't
     * have a grid shape. The envelope comes in the original projection unless that flips
     * coordinates, in which case EPSG:4326 is used.
     * 
     * @return the geometry.
     */
    private IGeometry getGeometry(Envelope env, Integer srid) {

        Geometry ret = Geometry.create("S2");

//        if (gridShape != null) {
//            ret = ret.withSpatialShape((long) gridShape[0], (long) gridShape[1]);
//        }

        
        ret = ret.withBoundingBox(env.getMinX(), env.getMaxX(), env.getMinY(), env.getMaxY())
        		.withProjection("EPSG:"+srid);
        
//        if (originalProjection != null && originalEnvelope != null) {
//            if (originalProjection.flipsCoordinates()) {
//                // use the WGS84
//                ret = ret.withBoundingBox(wgs84envelope.getMinX(), wgs84envelope.getMaxX(), wgs84envelope.getMinY(),
//                        wgs84envelope.getMaxY()).withProjection(Projection.DEFAULT_PROJECTION_CODE);
//            } else {
//                ret = ret.withBoundingBox(originalEnvelope.getMinX(), originalEnvelope.getMaxX(), originalEnvelope.getMinY(),
//                        originalEnvelope.getMaxY()).withProjection(originalProjection.getSimpleSRS());
//            }
//        } else if (wgs84envelope != null) {
//            ret = ret.withBoundingBox(wgs84envelope.getMinX(), wgs84envelope.getMaxX(), wgs84envelope.getMinY(),
//                    wgs84envelope.getMaxY()).withProjection(Projection.DEFAULT_PROJECTION_CODE);
//        }
//
//        if (this.temporalExtension != null) {
//            // TODO add the change timepoints
//            ret = ret.withTemporalStart(this.temporalExtension.getStart()).withTemporalEnd(this.temporalExtension.getEnd())
//                    .withTemporalTransitions(this.temporalExtension.getTimestamps());
//        }

        return ret;
    }

    @Override
    public boolean canHandle(File resource, IParameters<String> parameters) {
        return resource == null && parameters.contains("wcsVersion") && parameters.contains("serviceUrl")
                && parameters.contains("wcsIdentifier");
    }

    @Override
    public Collection<File> getAllFilesForResource(File file) {
        throw new IllegalStateException("the WCS adapter does not handle files");
    }

    @Override
    public List<Operation> getAllowedOperations(IResource resource) {
        List<Operation> ret = new ArrayList<>();
        return ret;
    }

    @Override
    public IResource performOperation(IResource resource, String operationName, IParameters<String> parameters,
            IResourceCatalog catalog, IMonitor monitor) {
        throw new KlabUnimplementedException("resource operations unimplemented");
    }

    @Override
    public Map<String, Object> describeResource(IResource resource) {
        Map<String, Object> ret = new LinkedHashMap<>();
        // TODO
        return ret;
    }

    @Override
    public IResource update(IResource resource, ResourceCRUDRequest updateData) {
        ((Resource) resource).update(updateData);
        return resource;
    }

    @Override
    public boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters, Description description) {
        // TODO Auto-generated method stub
        return false;
    }

}
