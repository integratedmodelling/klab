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
package org.integratedmodelling.klab.ogc.vector.files;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.filter.text.ecql.ECQL;
import org.geotools.util.factory.GeoTools;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.processing.Rasterizer;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.geospace.utils.SpatialDisplay;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.ogc.VectorAdapter;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Utils;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * The Class RasterEncoder.
 */
public class VectorEncoder implements IResourceEncoder {

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope scope) {
        FeatureSource<SimpleFeatureType, SimpleFeature> features = getFeatureSource(resource, geometry);
        encodeFromFeatures(features, resource, urnParameters, geometry, builder, scope);
    }

    protected FeatureSource<SimpleFeatureType, SimpleFeature> getFeatureSource(IResource resource, IGeometry geometry) {

        File mainFile = null;
        File rootPath = Resources.INSTANCE.getFilesystemLocation(resource);

        for (String path : resource.getLocalPaths()) {
            if (VectorAdapter.fileExtensions.contains(MiscUtilities.getFileExtension(path))) {
                mainFile = new File(rootPath + File.separator + path);
                if (mainFile.exists() && mainFile.canRead()) {
                    break;
                }
            }
        }

        if (mainFile == null) {
            throw new KlabResourceNotFoundException("vector resource " + resource.getUrn() + " cannot be accessed");
        }

        Map<String, Object> map = new HashMap<>();
        try {
            map.put("url", mainFile.toURI().toURL());
            // TODO check and honor any charset in the resource. This could be the default.
            map.put(ShapefileDataStoreFactory.DBFCHARSET.key, "UTF-8");
            DataStore dataStore = DataStoreFinder.getDataStore(map);
            String typeName = dataStore.getTypeNames()[0];
            SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
            return featureSource;

        } catch (Exception e) {
            throw new KlabIOException(e);
        }

    }

    /**
     * URN parameters: for now
     * 
     *  intersect=[true|false]  -> return shapes intersected with the bounding box
     *  filter=[CQL filter]     -> use filter in extraction
     *  presence=[true]         -> only return metadata, with present={true|false} if at least one shape intersects the bounding box.
     * 
     * @param source
     * @param resource
     * @param urnParameters
     * @param geometry
     * @param builder
     * @param scope
     */
    private void encodeFromFeatures(FeatureSource<SimpleFeatureType, SimpleFeature> source, IResource resource,
            Map<String, String> urnParameters, IGeometry geometry, Builder builder, IContextualizationScope scope) {

        Filter filter = null;
        FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2(GeoTools.getDefaultHints());
        Scale requestScale = geometry instanceof Scale ? (Scale) geometry : Scale.create(geometry);

        /*
         * merge urn params with resource params: if attr=x, use filter, if just value=x and we have
         * a nameAttribute filter, else add to parameters
         */
        String idRequested = urnParameters.containsKey(Urn.SINGLE_PARAMETER_KEY) && urnParameters.size() == 1
                ? urnParameters.get(Urn.SINGLE_PARAMETER_KEY)
                : null;
        
        String geomName = source.getSchema().getGeometryDescriptor().getName().toString();
        boolean intersect = urnParameters.containsKey("intersect") ? Boolean.parseBoolean(urnParameters.get("intersect")) : true;
        boolean presence = urnParameters.containsKey("presence") ? Boolean.parseBoolean(urnParameters.get("presence")) : false;
        
        Map<String, Class<?>> attributes = new HashMap<>();
        Map<String, String> attributeNames = new HashMap<>();

        for (AttributeDescriptor ad : source.getSchema().getAttributeDescriptors()) {
            if (!ad.getLocalName().equals(geomName)) {
                attributes.put(ad.getLocalName(), ad.getType().getBinding());
                attributeNames.put(ad.getLocalName().toLowerCase(), ad.getLocalName());
                if (idRequested == null && (urnParameters.containsKey(ad.getLocalName().toLowerCase())
                        || urnParameters.containsKey(ad.getLocalName().toUpperCase()))) {
                    try {
                        // use syntax dependent on attribute type
                        if (ad.getType().getBinding() == String.class) {
                            filter = ECQL.toFilter(
                                    ad.getLocalName() + " = '" + Utils.getIgnoreCase(urnParameters, ad.getLocalName()) + "'");
                        } else {
                            filter = ECQL
                                    .toFilter(ad.getLocalName() + " = " + Utils.getIgnoreCase(urnParameters, ad.getLocalName()));
                        }
                    } catch (CQLException e) {
                        // shouldn't happen as filter was validated previously
                        throw new KlabValidationException(e);
                    }
                }
            }
        }

        /*
         * TODO would be nicer to check the request geometry for the data - which may not be the
         * scale of the result! If it's IRREGULAR MULTIPLE we want objects, otherwise we want a
         * state. I don't think there is a way to check that at the moment - the scale will be that
         * of contextualization, not the geometry for the actuator, which may depend on context.
         */
        boolean rasterize = (idRequested != null || (scope.getTargetSemantics() != null
                && (scope.getTargetSemantics().is(Type.QUALITY) || scope.getTargetSemantics().is(Type.TRAIT))))
                && requestScale.getSpace() instanceof Space && ((Space) requestScale.getSpace()).getGrid() != null;

        /*
         * situations like urn#filter=xxxx" - can't contain an equal sign
         */
        if (urnParameters.containsKey("filter")) {
            try {
                Filter pfilter = ECQL.toFilter(urnParameters.get("filter"));
                filter = filter == null ? pfilter : ff.and(filter, pfilter);
            } catch (CQLException e) {
                // shouldn't happen as filter was validated previously
                throw new KlabValidationException(e);
            }
        }
        
        /*
         * filters set into the resource parameters
         */
        if (resource.getParameters().contains("filter") && !resource.getParameters().get("filter").toString().trim().isEmpty()) {
            try {
                Filter pfilter = ECQL.toFilter(resource.getParameters().get("filter", String.class).trim());
                filter = filter == null ? pfilter : ff.and(filter, pfilter);
            } catch (CQLException e) {
                // shouldn't happen as filter was validated previously
                throw new KlabValidationException(e);
            }
        }

        FeatureCollection<SimpleFeatureType, SimpleFeature> fc;
        try {
            fc = source.getFeatures();
        } catch (IOException e) {
            throw new KlabIOException(e);
        }

        CoordinateReferenceSystem crs = fc.getSchema().getCoordinateReferenceSystem();
        crs = GeotoolsUtils.INSTANCE.checkCrs(crs);
        Projection originalProjection = Projection.create(crs);
        IEnvelope envelopeInOriginalProjection = requestScale.getSpace().getEnvelope().transform(originalProjection, true);

        Filter bbfilter = ff.bbox(ff.property(geomName), ((Envelope) envelopeInOriginalProjection).getJTSEnvelope());
        if (filter != null) {
            bbfilter = ff.and(bbfilter, filter);
        }

        Rasterizer<Object> rasterizer = null;
        if (rasterize) {
//            String name = scope.getTargetName();
//            String unit = scope.getTargetSemantics() != null && scope.getTargetSemantics().getUnit() != null
//                    ? scope.getTargetSemantics().getUnit().toString()
//                    : null;
//            builder = builder.startState(name, unit, scope);
            rasterizer = new Rasterizer<Object>(((Space) requestScale.getSpace()).getGrid());
        }

        String nameAttribute = resource.getParameters().get("nameAttribute", String.class);
        if (nameAttribute == null && attributes.get("NAME") != null) {
            nameAttribute = "NAME";
        }

//        SpatialDisplay display = new SpatialDisplay(requestScale);
        
        int n = 1;
        FeatureIterator<SimpleFeature> it = fc.subCollection(bbfilter).features();
        while(it.hasNext()) {

            if (presence) {
                builder = builder.withMetadata("presence", Boolean.TRUE);
                it.close();
                return;
            }
            
            SimpleFeature feature = it.next();
            Object shape = feature.getDefaultGeometryProperty().getValue();
            if (shape instanceof org.locationtech.jts.geom.Geometry) {

                if (((org.locationtech.jts.geom.Geometry) shape).isEmpty()) {
                    continue;
                }

                if ("true".equals(resource.getParameters().get("sanitize", "false").toString())) {
                    // shape = GeometrySanitizer.sanitize((org.org.locationtecheom.Geometry) shape);
                    if (!((org.locationtech.jts.geom.Geometry) shape).isValid()) {
                        shape = ((org.locationtech.jts.geom.Geometry) shape).buffer(0);
                    }
                }

                IShape objectShape = Shape.create((org.locationtech.jts.geom.Geometry) shape, originalProjection)
                        .transform(requestScale.getSpace().getProjection());

                if (intersect) {
                    objectShape = objectShape.intersection(requestScale.getSpace().getShape());
                }

//                display.add(objectShape);
                

                if (objectShape.isEmpty()) {
                    continue;
                }

                if (rasterize) {

                    Object value = Boolean.TRUE;

                    if (idRequested != null) {
                        String attrName = attributeNames.get(idRequested);
                        if (attrName != null) {
                            value = feature.getAttribute(attrName);
                        }
                    }

                    value = Utils.asType(value, Utils.getClassForType(resource.getType()));

                    final Object vval = value;
                    rasterizer.add(objectShape, (s) -> vval);

                } else if (!presence) {

                    IScale objectScale = Scale.createLike(scope.getScale(), objectShape);
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

                    builder = builder.startObject(scope.getTargetName(), objectName, objectScale);
                    for (String key : attributes.keySet()) {
                        Object nattr = feature.getAttribute(key);
                        if (nattr == null) {
                            nattr = feature.getAttribute(key.toUpperCase());
                        }
                        if (nattr == null) {
                            nattr = feature.getAttribute(key.toLowerCase());
                        }
                        if (nattr != null) {
                            builder.withMetadata(key.toLowerCase(), nattr);
                        }
                    }

                    builder = builder.finishObject();

                }
            }
        }

        it.close();

//        display.show();
        
        if (presence) {
            builder = builder.withMetadata("presence", Boolean.FALSE);
        }
        
        if (rasterize) {
            final Builder stateBuilder = builder;
            rasterizer.finish((b, xy) -> {
                stateBuilder.set(b, requestScale.at(ISpace.class, xy));
            });
//            builder = builder.finishState();
        }

    }

    @Override
    public boolean isOnline(IResource resource, IMonitor monitor) {

        File base = null;
        if (Urns.INSTANCE.isLocal(resource.getUrn())) {
            IProject project = Resources.INSTANCE.getProject(resource.getLocalProjectName());
            base = project == null ? null : project.getRoot().getParentFile();
        } else {
            base = new File(resource.getLocalPath());
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
