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
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.factory.GeoTools;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.processing.GeometrySanitizer;
import org.integratedmodelling.klab.components.geospace.processing.Rasterizer;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

/**
 * The Class RasterEncoder.
 */
public class VectorEncoder implements IResourceEncoder {

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder, IComputationContext context) {
        FeatureSource<SimpleFeatureType, SimpleFeature> features = getFeatureSource(resource, geometry);
        encodeFromFeatures(features, resource, urnParameters, geometry, builder, context);
    }

    protected FeatureSource<SimpleFeatureType, SimpleFeature> getFeatureSource(IResource resource, IGeometry geometry) {

        File mainFile = null;

        for (String path : resource.getLocalPaths()) {
            if (VectorAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(path))) {
                mainFile = new File(Resources.INSTANCE.getLocalWorkspace().getRoot() + File.separator + path);
                if (mainFile.exists() && mainFile.canRead()) {
                    break;
                }
            }
        }

        if (mainFile == null) {
            throw new KlabResourceNotFoundException("raster resource " + resource.getUrn()
                    + " cannot be accessed");
        }

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("url", mainFile.toURI().toURL().toString());
            DataStore dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];
            return dataStore.getFeatureSource(typeName);

        } catch (Exception e) {
            throw new KlabIOException(e);
        }

    }

    // TODO use URN parameters
    private void encodeFromFeatures(FeatureSource<SimpleFeatureType, SimpleFeature> source, IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder, IComputationContext context) {

        Filter filter = null;

        /*
         * TODO
         * merge urn params with resource params: if attr=x, use filter, if just value=x and we have a 
         * nameAttribute filter, else add to parameters
         */
        String idRequested = urnParameters.containsKey(Urns.SINGLE_PARAMETER_KEY) && urnParameters.size() == 1
                ? urnParameters.get(Urns.SINGLE_PARAMETER_KEY)
                : null;
                
        String geomName = source.getSchema().getGeometryDescriptor().getName().toString();

        Map<String, Class<?>> attributes = new HashMap<>();
        for (AttributeDescriptor ad : source.getSchema().getAttributeDescriptors()) {
            if (!ad.getLocalName().equals(geomName)) {
                attributes.put(ad.getLocalName(), ad.getType().getBinding());
            }
        }

        /*
         * TODO would be nicer to check the request geometry for the data - which may
         * not be the scale of the result! If it's IRREGULAR MULTIPLE we want objects,
         * otherwise we want a state. I don't think there is a way to check that at the
         * moment - the scale will be that of contextualization, not the geometry for
         * the actuator, which may depend on context.
         */
        boolean rasterize = context.getTargetSemantics() != null
                && context.getTargetSemantics().is(Type.QUALITY);

        if (resource.getParameters().contains("filter")) {
            try {
                filter = ECQL.toFilter(resource.getParameters().get("filter", String.class));
            } catch (CQLException e) {
                // shouldn't happen as filter was validated previously
                throw new KlabValidationException(e);
            }
        }

        Scale scale = Scale.create(resource.getGeometry());

        FeatureCollection<SimpleFeatureType, SimpleFeature> fc;
        try {
            fc = source.getFeatures();
        } catch (IOException e) {
            throw new KlabIOException(e);
        }

        Projection originalProjection = Projection.create(fc.getSchema().getCoordinateReferenceSystem());
        IEnvelope envelopeInOriginalProjection = scale.getSpace().getEnvelope()
                .transform(originalProjection, true);

        FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(GeoTools.getDefaultHints());

        Filter bbfilter = ff
                .bbox(ff.property(geomName), ((Envelope) envelopeInOriginalProjection).getJTSEnvelope());
        if (filter != null) {
            bbfilter = ff.and(bbfilter, filter);
        }

        Rasterizer<Object> rasterizer = null;
        if (rasterize) {
            builder = builder.startState(context.getTargetName());
            // TODO instantiate rasterizer with target artifact
        }

        String nameAttribute = resource.getParameters().get("nameAttribute", String.class);
        if (nameAttribute == null && attributes.get("NAME") != null) {
            nameAttribute = "NAME";
        }

        int n = 1;
        FeatureIterator<SimpleFeature> it = fc.subCollection(bbfilter).features();

        while (it.hasNext()) {

            SimpleFeature feature = it.next();
            Object shape = feature.getDefaultGeometryProperty().getValue();
            if (shape instanceof com.vividsolutions.jts.geom.Geometry) {

                if (resource.getParameters().get("sanitize", false)) {
                    shape = GeometrySanitizer.sanitize((com.vividsolutions.jts.geom.Geometry) shape);
                }

                if (rasterize) {
                    // TODO compute value for shape
                    // TODO rasterizer.add (shape, function to pass value)
                } else {

                    IShape objectShape = Shape
                            .create((com.vividsolutions.jts.geom.Geometry) shape, originalProjection)
                            .transform(scale.getSpace().getProjection())
                            .intersection(scale.getSpace().getShape());

                    IScale objectScale = Scale.createLike(context.getScale(), objectShape);
                    String objectName = null;
                    if (nameAttribute != null) {
                        Object nattr = feature.getAttribute(nameAttribute);
                        if (nattr == null) {
                            nattr = feature.getAttribute(nameAttribute.toUpperCase());
                        }
                        if (nattr == null) {
                            nattr = feature.getAttribute(nameAttribute.toLowerCase());
                        }
                        if (nattr != null) {
                            objectName = nattr.toString();
                        }
                    }
                    if (objectName /* still */ == null) {
                        objectName = fc.getSchema().getTypeName() + "_" + (n++);
                    }

                    builder = builder.startObject(context.getTargetName(), objectName, objectScale);

                    // TODO make object
                    // TODO process from metadata
                    for (String key : attributes.keySet()) {
                        builder.withMetadata(key.toLowerCase(), attributes.get(key));
                    }

                    builder = builder.finishObject();

                }
            }
        }

        it.close();

        if (rasterize) {
            // TODO rasterizer.finish(call builder)
            builder = builder.finishState();
        }

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
