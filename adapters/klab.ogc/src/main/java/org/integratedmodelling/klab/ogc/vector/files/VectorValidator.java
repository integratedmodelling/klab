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
package org.integratedmodelling.klab.ogc.vector.files;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.xtext.util.Arrays;
import org.geotools.data.DataAccessFactory;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.IResource.Builder;
import org.integratedmodelling.klab.api.data.IResourceCatalog;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IActivity.Description;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.rest.ResourceCRUDRequest;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Utils;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import org.locationtech.jts.geom.Lineal;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.Puntal;
import org.locationtech.jts.io.WKBWriter;
import org.locationtech.jts.simplify.TopologyPreservingSimplifier;

/**
 * The Class RasterValidator.
 */
public class VectorValidator implements IResourceValidator {

	@Override
	public IResource.Builder validate(URL url, IParameters<String> userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();

		try {

			ret.withParameter("fileUrl", url).withLocalName(MiscUtilities.getFileName(url.getFile()));
			Map<String, Object> map = new HashMap<>();
			map.put("url", url);

			if (userData.contains("filter")) {
				try {
					ECQL.toFilter(userData.get("filter", String.class));
				} catch (CQLException e) {
					ret.addError(
							"CQL filter expression '" + userData.get("filter", String.class) + "' has syntax errors");
				}
			}

			// TODO check and honor any charset in the resource. This could be the default.
			map.put(ShapefileDataStoreFactory.DBFCHARSET.key, "UTF-8");
			
			DataStore dataStore = DataStoreFinder.getDataStore(map);
			String typeName = dataStore.getTypeNames()[0];
			FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);

			/*
			 * Assume data will come with straight projection.
			 */
			validateCollection(source, ret, userData, false, monitor);

		} catch (Throwable e) {
			ret.addError("Error validating " + e.getMessage());
		}

		return ret;
	}

	protected void validateCollection(FeatureSource<SimpleFeatureType, SimpleFeature> source, Builder ret,
			IParameters<String> userData, boolean swapLatlonAxes, IMonitor monitor) throws IOException {

		Filter filter = Filter.INCLUDE;
		FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);
		String geomName = source.getSchema().getGeometryDescriptor().getName().toString();

		ReferencedEnvelope envelope = source.getBounds();
		CoordinateReferenceSystem crs = collection.getSchema().getCoordinateReferenceSystem();
		swapLatlonAxes = swapLatlonAxes && crs != null && crs.equals(DefaultGeographicCRS.WGS84);

		if (envelope == null) {
			// we only do this when importing, so let's go through them
			envelope = collection.getBounds();
		}
		if (envelope.getCoordinateReferenceSystem() == null) {
			ret.addError("vector resource is unprojected");
			return;
		}

		/**
		 * Description and other info go in metadata
		 */
		if (source.getInfo().getTitle() != null && !source.getInfo().getTitle().trim().isEmpty()) {
			ret.withMetadata(IMetadata.DC_TITLE, source.getInfo().getTitle());
		}
		if (source.getInfo().getDescription() != null && !source.getInfo().getDescription().trim().isEmpty()) {
			ret.withMetadata(IMetadata.DC_COMMENT, source.getInfo().getDescription());
		}
		if (source.getInfo().getKeywords() != null && !source.getInfo().getKeywords().isEmpty()) {
			ret.withMetadata(IMetadata.IM_KEYWORDS, StringUtils.join(source.getInfo().getKeywords(), ","));
		}

		ret.withSpatialExtent(Envelope.create(envelope, swapLatlonAxes).asShape().getExtentDescriptor());

		Map<String, Class<?>> attributeTypes = new HashMap<>();

		int shapeDimension = 0;
		for (AttributeDescriptor ad : source.getSchema().getAttributeDescriptors()) {

			if (ad.getLocalName().equals(geomName)) {
				// set shape dimensionality from geometry type: 0 = point, 1 = line, 2 = polygon
				if (org.locationtech.jts.geom.Geometry.class.isAssignableFrom(ad.getType().getBinding())) {
					if (Arrays.contains(ad.getType().getBinding().getInterfaces(), Lineal.class)) {
						shapeDimension = 1;
					} else if (Arrays.contains(ad.getType().getBinding().getInterfaces(), Polygonal.class)) {
						shapeDimension = 2;
					} else if (Arrays.contains(ad.getType().getBinding().getInterfaces(), Puntal.class)) {
						shapeDimension = 0;
					} else {
						ret.addError("cannot establish geometry dimensionality for vector resource");
					}
				} else {
					ret.addError("cannot establish geometry type for vector resource");
				}
			} else {
				// store attribute ID and type
				attributeTypes.put(ad.getName().toString(), ad.getType().getBinding());
				ret.withAttribute(ad.getName().toString(), Utils.getArtifactType(ad.getType().getBinding()), false,
						true);
			}
		}

		// TODO if attributes are requested, validate their type and name

		// TODO if attributes are requested, set the type in the builder accordingly
		ret.withType(IArtifact.Type.OBJECT);

		// Compute union or convex hull if requested
		if (userData.get("computeUnion", false) || userData.get("computeHull", false)) {
			if (!swapLatlonAxes) {
				org.locationtech.jts.geom.Geometry geometry = null;
				try (FeatureIterator<SimpleFeature> features = collection.features()) {
					while (features.hasNext()) {
						SimpleFeature feature = features.next();
						Object shape = feature.getDefaultGeometryProperty().getValue();
						if (shape instanceof org.locationtech.jts.geom.Geometry) {
							geometry = geometry == null ? (org.locationtech.jts.geom.Geometry) shape
									: geometry.union(( org.locationtech.jts.geom.Geometry) shape);
						}
					}
				}
				if (geometry != null) {
					if (userData.get("computeHull", false)) {
						geometry = geometry.convexHull();
					}
					geometry = TopologyPreservingSimplifier.simplify(geometry, 0.01);
					ret.withParameter("shape", WKBWriter.toHex(new WKBWriter().write(geometry)));
				}
			} else {
				monitor.warn("unimplemented feature: shape unions not computed in lat/lon order with swapped axes");
			}
		}

		String crsCode = null;
		if (crs == null) {
			ret.addError("Coverage has no coordinate reference system");
		} else {

			crsCode = CRS.toSRS(crs);

			monitor.debug("Running projection tests...");

			try {

				monitor.debug("Testing reprojection to WGS84...");

				CRS.findMathTransform(crs, DefaultGeographicCRS.WGS84);

				Projection utmProjection = Projection.getUTM(Envelope.create(envelope));
				if (!crs.equals(utmProjection.getCoordinateReferenceSystem())) {
					monitor.info("Testing reprojection to best-guess UTM " + utmProjection + "...");
					CRS.findMathTransform(crs, utmProjection.getCoordinateReferenceSystem());
				}
				String crsId = CRS.lookupIdentifier(crs, true);
				if (crsId != null && crsId.startsWith("EPSG:")) {
					// use the simpler identifier
					crsCode = crsId;
				}

			} catch (Throwable e) {
				ret.addError(
						"Coverage projection failed reprojection test (check Bursa-Wolfe parameters): EPSG code reported is "
								+ crsCode);
			}
		}
		if (!ret.hasErrors()) {

			Geometry geometry = Geometry
					.create("#s" + shapeDimension).withBoundingBox(envelope.getMinimum(0), envelope.getMaximum(0),
							envelope.getMinimum(1), envelope.getMaximum(1))
					.withProjection(crsCode).withSpatialShape(collection.size());

			ret.withGeometry(geometry);
		}
	}

	@Override
	public boolean canHandle(File resource, IParameters<String> parameters) {

		if (resource == null) {
			return false;
		}
		String extension = MiscUtilities.getFileExtension(resource);
		if (extension != null) {
			return
			// TODO other vector formats understandable by geotools
			extension.toLowerCase().equals("shp");
		}

		return false;
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		return FileUtils.getSidecarFiles(file, VectorAdapter.secondaryFileExtensions);
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
	public boolean isObservationAllowed(IResource resource, Map<String, String> urnParameters,
			Description description) {
		// TODO Auto-generated method stub
		return false;
	}

}
