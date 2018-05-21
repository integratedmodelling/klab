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
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.URLUtils;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * The Class RasterValidator.
 */
public class VectorValidator implements IResourceValidator {

	@Override
	public IResource.Builder validate(URL url, IParameters userData, IMonitor monitor) {

		IResource.Builder ret = Resources.INSTANCE.createResourceBuilder();

		try {

			ret.setParameter("fileUrl", url);

			Map<String, Object> map = new HashMap<>();
			map.put("url", url);

			DataStore dataStore = DataStoreFinder.getDataStore(map);
			String typeName = dataStore.getTypeNames()[0];

			FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);

			Filter filter = Filter.INCLUDE; // ECQL.toFilter("BBOX(THE_GEOM, 10,20,30,40)")
			FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures(filter);

			ReferencedEnvelope envelope = source.getBounds();
			CoordinateReferenceSystem crs = collection.getSchema().getCoordinateReferenceSystem();
			if (envelope == null) {
				// we only do this when importing, so let's go through them
				envelope = collection.getBounds();
			}

			int shapeDimension = 0;
			for (AttributeDescriptor ad : source.getSchema().getAttributeDescriptors()) {
				if (ad.getLocalName().equals("the_geom")) {
					// TODO set shape dimensionality from geometry type: 0 = point, 1 = line, 2 = polygon
				} else {
					// TODO store attribute ID and type
				}
			}

			// really?
			try (FeatureIterator<SimpleFeature> features = collection.features()) {
				while (features.hasNext()) {
					SimpleFeature feature = features.next();
					System.out.print(feature.getID());
					System.out.print(": ");
					System.out.println(feature.getDefaultGeometryProperty().getValue());
				}
			}

			String crsCode = null;
			if (crs == null) {
				ret.addError("Coverage has no coordinate reference system");
			} else
				monitor.info("Running projection tests...");

			try {

				monitor.info("Testing reprojection to WGS84...");

				CRS.findMathTransform(crs, DefaultGeographicCRS.WGS84);
				Projection utmProjection = Projection.getUTM(Envelope.create(envelope));

				if (!crs.equals(utmProjection.getCoordinateReferenceSystem())) {
					monitor.info("Testing reprojection to UTM " + utmProjection + "...");
					CRS.findMathTransform(crs, utmProjection.getCoordinateReferenceSystem());
				}
				crsCode = CRS.lookupIdentifier(crs, true);

			} catch (Throwable e) {
				ret.addError("Coverage projection failed reprojection test (check Bursa-Wolfe parameters)");
			}

			if (!ret.hasErrors()) {

				Geometry geometry = Geometry.create("#s" + shapeDimension)
						.withBoundingBox(envelope.getMinimum(0), envelope.getMaximum(0), envelope.getMinimum(1),
								envelope.getMaximum(1))
						.withProjection(CRS.toSRS(crs)).withSpatialShape(collection.size());

				ret.setGeometry(geometry);
			}

		} catch (Throwable e) {
			ret.addError("Error validating " + e.getMessage());
		}

		return ret;
	}

	@Override
	public boolean canHandle(File resource, IParameters parameters) {

		if (resource == null) {
			return false;
		}
		String extension = MiscUtilities.getFileExtension(resource);
		if (extension != null) {
			return
			// TODO other raster formats understandable by geotools
			extension.toLowerCase().equals("shp");
		}

		return false;
	}

	@Override
	public Collection<File> getAllFilesForResource(File file) {
		return FileUtils.getSidecarFiles(file, VectorAdapter.secondaryFileExtensions);
	}
}
