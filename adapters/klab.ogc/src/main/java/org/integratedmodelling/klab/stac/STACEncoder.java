package org.integratedmodelling.klab.stac;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.geotools.api.data.FeatureSource;
import org.geotools.api.feature.simple.SimpleFeature;
import org.geotools.api.feature.simple.SimpleFeatureType;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.processing.Operations;
import org.geotools.data.memory.MemoryDataStore;
import org.geotools.geojson.geom.GeometryJSON;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.hortonmachine.gears.io.stac.HMStacAsset;
import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacItem;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.io.stac.auth.HMStacAccess;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.libs.modules.HMRaster.HMRasterWritableBuilder;
import org.hortonmachine.gears.libs.modules.HMRaster.MergeMode;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.hortonmachine.gears.utils.RegionMap;
import org.hortonmachine.gears.utils.crs.CrsUtilities;
import org.hortonmachine.gears.utils.crs.HMCrsRegistry;
import org.hortonmachine.gears.utils.crs.HMCrsTransformer;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.knowledge.ICodelist;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.exceptions.KlabContextualizationException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceAccessException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.ogc.OpenEOAdapter;
import org.integratedmodelling.klab.ogc.STACAdapter;
import org.integratedmodelling.klab.ogc.vector.files.VectorEncoder;
import org.integratedmodelling.klab.openeo.OpenEO;
import org.integratedmodelling.klab.openeo.OpenEO.OpenEOFuture;
import org.integratedmodelling.klab.openeo.OpenEO.Process;
import org.integratedmodelling.klab.raster.files.RasterEncoder;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.stac.extensions.COGAssetExtension;
import org.integratedmodelling.klab.stac.extensions.STACFeatureExtension;
import org.integratedmodelling.klab.stac.extensions.STACIIASAExtension;
import org.integratedmodelling.klab.stac.extensions.WEEDModelSTACExtension;
import org.integratedmodelling.klab.utils.JsonUtils;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.operation.union.UnaryUnionOp;
import org.locationtech.jts.simplify.TopologyPreservingSimplifier;


import kong.unirest.json.JSONObject;

public class STACEncoder implements IResourceEncoder {

    /**
     * The raster or vector encoder that does the actual work after we get our coverage from the service.
     */
    IResourceEncoder encoder;

    @Override
    public boolean isOnline(IResource resource, IMonitor monitor) {
        String collectionUrl = STACUtils.getCollectionUrl(resource.getParameters());
        if (collectionUrl == null) {
            monitor.error("Resource is lacking a proper schema. Try to reimport the STAC collection.");
            return false;
        }

        STACService service = STACAdapter.getService(collectionUrl);
        if (service == null) {
            monitor.error("Connection with collection " + collectionUrl
                    + " cannot be opened: likely the service URL is wrong or offline");
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

    private Time refitTime(Time contextTime, Time resourceTime) {
        if (resourceTime.getCoveredExtent() < contextTime.getCoveredExtent()) {
            throw new KlabContextualizationException(
                    "Current observation is outside the bounds of the STAC resource and cannot be reffitted.");
        }
        if (contextTime.getStart().isBefore(resourceTime.getStart())) {
            ITimeInstant newEnd = TimeInstant.create(resourceTime.getStart().getMilliseconds() + contextTime.getLength());
            return Time.create(resourceTime.getStart().getMilliseconds(), newEnd.getMilliseconds());
        }
        if (contextTime.getEnd().isAfter(resourceTime.getEnd())) {
            ITimeInstant newStart = TimeInstant.create(resourceTime.getEnd().getMilliseconds() - contextTime.getLength());
            return Time.create(newStart.getMilliseconds(), resourceTime.getEnd().getMilliseconds());
        }
        throw new KlabContextualizationException(
                "Current observation is outside the bounds of the STAC resource and cannot be reffitted.");
    }

    /**
     * Validates that the temporal dimension of the context can be supported by the resource.
     * Due to the nature of the STAC search query, time can be refitted if needed.
     * @param contextTime
     * @param resourceTime
     * @return validated time for the request
     */
    private Time validateTemporalDimension(Time contextTime, Time resourceTime) {
        if (!resourceTime.contains(contextTime)) {
            return refitTime(contextTime, resourceTime);
        }
        return contextTime;
    }

    private HMRaster.MergeMode chooseMergeMode(Map<String, String> urnParameters, IObservable targetSemantics, IMonitor monitor) {
    	
    	if (urnParameters.containsKey("aggregation")) {
    		monitor.info("Fetching Merge Mode from URN Paramter");
    		
    		switch (urnParameters.get("aggregation").toString().toLowerCase()) {
	    		case "sum":
	    			return HMRaster.MergeMode.SUM;
	    		case "avg":
	    			return HMRaster.MergeMode.AVG;
	    		case "substitute":
	    			return HMRaster.MergeMode.SUBSTITUTE;
	    		case "most_popular_value":
	    			return HMRaster.MergeMode.MOST_POPULAR_VALUE;
	    		default:
	    			monitor.info("Couldn't find suitable Mergemode from the URN Parameter, using Senatics to Infer");
    		}
    	}
    	
        if (targetSemantics == null) {
            monitor.debug("Using average as merge mode");
            return HMRaster.MergeMode.AVG;
        }
        
        switch (targetSemantics.getDescriptionType().toString()) {
        
        	case "QUANTIFICATION":
        		monitor.info("Using Substitute as Merge Mode for QUANTIFICATION based on Semantics");
        		return HMRaster.MergeMode.SUBSTITUTE;
        		
        	default:
        		monitor.info("Using Most Popular Value for CHARACTERIZATION and CATEGORIZATION based on Semantics");
        		return HMRaster.MergeMode.MOST_POPULAR_VALUE;
        }
        
    }

    /*
        Helper to Sort Items (of type HMStacItem) based on their timestamp
     */
    private void sortByDate(List<HMStacItem> items, IMonitor monitor) {
        if (items.stream().anyMatch(i -> i.getTimestamp() == null)) {
            throw new KlabIllegalStateException("STAC items are lacking a timestamp and could not be sorted by date.");
        }
        items.sort((i1, i2) -> i1.getTimestamp().compareTo(i2.getTimestamp()));
        monitor.debug("Ordered STAC items. First: [" + items.get(0).getTimestamp() + "]; Last ["
                + items.get(items.size() - 1).getTimestamp() + "]");
    }

    private boolean isDateWithinRange(Time rangeTime, Date date) {
        Date start = new Date(rangeTime.getStart().getMilliseconds());
        Date end = new Date(rangeTime.getEnd().getMilliseconds());
        return date.after(start) && date.before(end);
    }

    @Override
    public void getEncodedData(IResource resource, Map<String, String> urnParameters, IGeometry geometry, Builder builder,
            IContextualizationScope scope) {

        String COGURL = null;
        // also the resources imported with the old schema (catalogUrl + collectionId)
        String collectionUrl = STACUtils.getCollectionUrl(resource.getParameters());
        JSONObject collectionData = STACUtils.requestMetadata(collectionUrl, "collection");
        String collectionId = collectionData.getString("id");
        String catalogUrl = STACUtils.getCatalogUrl(collectionUrl, collectionId, collectionData);
        JSONObject catalogData = STACUtils.requestMetadata(catalogUrl, "catalog");
        Integer bandIndex = resource.getParameters().get("band", Integer.class);
        String assetId = resource.getParameters().get("asset", String.class);
        boolean hasSearchOption = STACUtils.containsLinkTo(catalogData, "search");
        DefaultGeographicCRS wgs84CRS = DefaultGeographicCRS.WGS84;
        final boolean allowTransform = true;
        
        Space space = (Space) geometry.getDimensions().stream().filter(d -> d instanceof Space).findFirst().orElseThrow();
        IEnvelope envelope = space.getEnvelope();
        List<Double> bbox = List.of(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
        IGrid grid = space.getGrid();
        
        LogProgressMonitor lpm = new LogProgressMonitor();
        // the authentications of the user (URL_AUTH_KEYS) for catalog, items and assets, s3 included
        HMStacAccess access = STACAccess.forResource(resource.getParameters());
        HMStacManager manager = new HMStacManager(catalogUrl, lpm);
        manager.setAccess(access);
        HMStacCollection collection = null;
        CoordinateReferenceSystem targetCRS = null;
        
        try {
        	targetCRS = HMCrsRegistry.INSTANCE.getCrs("4326");
        } catch (Exception e) {
        	closeManagerWithEx(manager);
        	throw new KlabInternalErrorException("Unable to fetch CRS: 4326");
        }

        // Only for Backward Compatiability
        // A new COG Adapter would be added
        if (resource.getParameters().get("cog") != null) {
        	try {
	            COGURL = resource.getParameters().get("cog", String.class);
	            scope.getMonitor().info("Getting requested extent from the COG Asset from url" + COGURL);
	            GridCoverage2D coverage = COGAssetExtension.getCOGWindowCoverage(bbox, COGURL);
	
	            String rcrs = geometry.getDimension(IGeometry.Dimension.Type.SPACE).getParameters()
	                    .get(org.integratedmodelling.klab.common.Geometry.PARAMETER_SPACE_PROJECTION, String.class);
	
	            org.locationtech.jts.geom.Envelope requestedExtend = new org.locationtech.jts.geom.Envelope(bbox.get(0), bbox.get(1),
	                    bbox.get(2), bbox.get(3));
	
	            HMRaster raster = HMRaster.fromGridCoverage(coverage);
	     
	            if (!HMCrsRegistry.crsEquals(raster.getCrs(),targetCRS)) {
	            	var transformer = new HMCrsTransformer(raster.getCrs(), targetCRS);
	            	transformer.setAcceptLenientDatumShift(true);
	            	raster = transformer.transform(raster);
	            }
	            
				HMRaster paddedRaster = new HMRasterWritableBuilder()
						.setName("padded")
						.setRegion(RegionMap.fromEnvelopeAndGrid(requestedExtend, (int) space.shape()[0], (int) space.shape()[1]))
						.setCrs(targetCRS)
						.setNoValue(raster.getNovalue()).build();
				paddedRaster.mapRaster(null, raster, null);
				coverage = paddedRaster.buildCoverage();
                encoder = new RasterEncoder();
                ((RasterEncoder) encoder).encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
                return;
            } catch (Exception e) {
            	closeManagerWithEx(manager);
                throw new KlabResourceAccessException("Cannot build COG Output " + e.getMessage());
            }
        }
        
        // This is part of a WIP that will be removed in the future
        if (catalogUrl.contains("iiasa.blob")) {
            FeatureSource<SimpleFeatureType, SimpleFeature> source;
            try {
                source = STACIIASAExtension.getFeatures(collectionData, bbox);
            } catch (IOException e) {
            	closeManagerWithEx(manager);
                throw new KlabResourceAccessException("Cannot extract features from IIASA catalog - " + e.getMessage());
            }
            encoder = new VectorEncoder();
            ((VectorEncoder) encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
            closeManagerWithEx(manager);
            return;
        }
        
        /*
        Select the Predicate based on the assetId, JSONSelector Query, and the JSONValue
         */

        Predicate<HMStacAsset> assetPredicate = null;
        if (assetId != null) {
            assetPredicate = new Predicate<HMStacAsset>(){
                @Override
                public boolean test(HMStacAsset asset) { // Assuming for now that "eo:bands" would
                                                         // be there, adding support for customised
                                                         // predicates
                    var bands = asset.getAssetNode().get("eo:bands");
                    if (bands != null && bands.isArray()) {
                        for(var bandNode : bands) {
                            String bandName = bandNode.get("name").asString();
                            if (bandName.equals(assetId)) { // under eo:band it's one of the band
                                return true;
                            }
                        }
                    }
                             
                    return asset.getId().equals(assetId); // case this would be like the asset key i.e. Id
                }
            };
        }
        
        if (resource.getParameters().get("jsonSelector", String.class) != null) {
            // based on the JSON Expression on JSONSelector and JSONValue;
            try {
            	if (assetPredicate != null) {
            		assetPredicate = assetPredicate.and(getAssetPredicateFromJSONSelector(resource)); // Predicate based on JSONPath AND the AssetID
            	} else {
            		assetPredicate = getAssetPredicateFromJSONSelector(resource);
            	}
            } catch (Exception e) {
            	closeManagerWithEx(manager);
                throw new KlabResourceAccessException("Couldn't form a predicate with the JSON Expressions");
            }

        }
        
     // Set a context from features in a STAC
        if (grid == null) { // While getting features for setting context (i.e. context is not yet set), otherwise this can't be true
        	try {
                manager.open();
                collection = manager.getCollectionByURL(collectionUrl);

                if (collection == null) {
                    scope.getMonitor()
                            .error("Collection " + resource.getParameters().get("collection", String.class) + " cannot be found.");
                    manager.close();
                    throw new KlabResourceAccessException("Cannot access to STAC collection " + collectionUrl); // Fail fast                                                                                          
                }
                
                scope.getMonitor().info("Getting all the features before applying the filter");
                collection.setBboxFilter(new double[] {
                		bbox.get(0),
                		bbox.get(2),
                		bbox.get(1),
                		bbox.get(3)
                });
                
                List<HMStacItem> items = collection.searchItems();
                var assets = items.stream()
                	    .flatMap(item -> item.getAssets().stream())
                	    .filter(assetPredicate)
                	    .toList();
                scope.getMonitor().debug("Found " + assets.size() + " Assets to get the requested geometry");
                var source = STACFeatureExtension.getFeatures(assets);
                encoder = new VectorEncoder();
                ((VectorEncoder) encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
                manager.close();
                return;
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        }

        Time ctxTime = (Time) geometry.getDimensions().stream().filter(d -> d instanceof Time).findFirst().orElseThrow();
        Time resourceTime = (Time) Scale.create(resource.getGeometry()).getDimension(Type.TIME);

        Time effectiveTime = ctxTime;
        if (resourceTime != null && resourceTime.getStart() != null && resourceTime.getEnd() != null
                && resourceTime.getCoveredExtent() > 0) {

            effectiveTime = validateTemporalDimension(ctxTime, resourceTime);
        }

        // These are the static STAC catalogue
        if (!hasSearchOption) {
            // the item geometries are lon/lat (WGS84) by the STAC spec, the context may be in another projection
            final Geometry contextLatLon = toLatLon(space);
            Envelope contextEnvelopeLL = contextLatLon.getEnvelopeInternal();

            // the items are found by the HortonMachine static search: following the links of the catalog,
            // filtered on the context, with the asset hrefs resolved and the authentications of the user
            List<HMStacItem> allItems;
            try {
                manager.open();
                collection = manager.getCollectionByURL(collectionUrl);
                if (collection == null) {
                    throw new KlabResourceAccessException("Cannot access to STAC collection " + collectionUrl);
                }
                collection.setBboxFilter(new double[]{contextEnvelopeLL.getMinX(), contextEnvelopeLL.getMinY(),
                        contextEnvelopeLL.getMaxX(), contextEnvelopeLL.getMaxY()});
                allItems = collection.searchItems();
            } catch (KlabResourceAccessException e) {
                throw e;
            } catch (Exception e) {
                throw new KlabResourceAccessException(
                        "Cannot read the items of STAC collection " + collectionId + ": " + e.getMessage());
            }
            // refined filter on the geometry, that is supposed to be geojson in 4326
            allItems = allItems.stream().filter(item -> {
                Object itemGeometry = item.getFeature() == null ? null : item.getFeature().getDefaultGeometry();
                return !(itemGeometry instanceof Geometry) || ((Geometry) itemGeometry).intersects(contextLatLon);
            }).toList();
            if (allItems.isEmpty()) {
                throw new KlabResourceAccessException(
                        "No items of STAC collection " + collectionId + " intersect the context " + contextEnvelopeLL);
            }

            var time2 = effectiveTime;
            // the same temporal selection as for the searchable catalogs
            List<HMStacItem> items = selectItemsInTime(allItems, assetPredicate, time2.getStart().getMilliseconds(),
                    time2.getEnd().getMilliseconds(), scope.getMonitor());
            if (items.isEmpty()) {
                throw new KlabResourceAccessException("None of the " + allItems.size() + " items of STAC collection "
                        + collectionId + " intersecting the context has the requested asset for the time range "
                        + time2.getStart() + " - " + time2.getEnd());
            }

            if (resource.getType() == IArtifact.Type.OBJECT) {
                // vectors (only the vector imports keep the OBJECT type): the features of the vector assets,
                // or the items themselves if no asset is selected
                try {
                    FeatureSource<SimpleFeatureType, SimpleFeature> source;
                    if (assetPredicate != null) {
                        var assets = items.stream().flatMap(item -> item.getAssets().stream()).filter(assetPredicate).toList();
                        if (assets.isEmpty()) {
                            throw new KlabIllegalStateException("No STAC assets found for this context");
                        }
                        scope.getMonitor().debug("Found " + assets.size() + " assets for the requested geometry");
                        source = STACFeatureExtension.getFeatures(assets);
                    } else {
                        List<SimpleFeature> features = items.stream().map(HMStacItem::getFeature).toList();
                        SimpleFeatureType featureType = features.get(0).getFeatureType();
                        MemoryDataStore dataStore = new MemoryDataStore(featureType);
                        dataStore.addFeatures(features);
                        source = dataStore.getFeatureSource(featureType.getTypeName());
                    }
                    encoder = new VectorEncoder();
                    ((VectorEncoder) encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
                    return;
                } catch (KlabIllegalStateException e) {
                    throw e;
                } catch (Exception e) {
                    throw new KlabResourceAccessException(
                            "Cannot extract features from static collection " + collectionId + ": " + e.getMessage());
                }
            }

            GridCoverage2D coverage = null;

            try {
                // the collection has been opened to search the items
                // the predicate of the asset id and/or the json selector, as for the searchable catalogs
                if (assetPredicate == null) {
                    throw new KlabIllegalArgumentException("Either asset or both jsonSelector and jsonValue must be provided");
                }
                Predicate<HMStacAsset> predicate = assetPredicate;
                RegionMap region = RegionMap.fromBoundsAndGrid(space.getEnvelope().getMinX(), space.getEnvelope().getMaxX(),
                        space.getEnvelope().getMinY(), space.getEnvelope().getMaxY(), (int) grid.getXCells(), (int) grid.getYCells());

                ReferencedEnvelope regionEnvelope = new ReferencedEnvelope(region.toEnvelope(),
                        space.getProjection().getCoordinateReferenceSystem());
                // readRasterBandOnRegion takes the region in lat/lon (WGS84), as in the searchable catalogs
                ReferencedEnvelope regionEnvelopeLL = regionEnvelope.transform(wgs84CRS, true);
                RegionMap regionTransformedLL = RegionMap.fromEnvelopeAndGrid(regionEnvelopeLL, (int) grid.getXCells(),
                        (int) grid.getYCells());
                HMRaster outRaster = collection.readRasterBandOnRegion(regionTransformedLL, predicate, items, true,
                        MergeMode.SUBSTITUTE, lpm);
                coverage = toContextCoverage(outRaster, space, grid, MergeMode.SUBSTITUTE);
                if (bandIndex != null) { // Which means theat it's a Multi Band COG
                    coverage = (GridCoverage2D) Operations.DEFAULT.selectSampleDimension(coverage, new int[]{bandIndex});
                }
                
            } catch (Exception e) {
                throw new KlabResourceAccessException(
                        "Cannot build output for static collection " + collectionId + ". Reason: " + e.getLocalizedMessage());
            }
                
            encoder = new RasterEncoder();
            ((RasterEncoder) encoder).encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
            return;
        }

        try {
            manager.open();
            collection = manager.getCollectionByURL(collectionUrl);

            if (collection == null) {
                scope.getMonitor()
                        .error("Collection " + resource.getParameters().get("collection", String.class) + " cannot be found.");
                manager.close();
                throw new KlabResourceAccessException("Cannot access to STAC collection " + collectionUrl); // Fail
                                                                                                            // fast
            }
            
            IObservable targetSemantics = scope.getTargetArtifact() instanceof Observation
                    ? ((Observation) scope.getTargetArtifact()).getObservable()
                    : null;
            HMRaster.MergeMode mergeMode = chooseMergeMode(urnParameters, targetSemantics, scope.getMonitor());
            Envelope env = new Envelope(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(), envelope.getMaxY());
            String code = envelope.getProjection().getCode();
            CoordinateReferenceSystem fromCrs = HMCrsRegistry.INSTANCE.getCrs(code);
            HMCrsTransformer crsTransformer = new HMCrsTransformer(fromCrs, DefaultGeographicCRS.WGS84);
            Envelope envLL = crsTransformer.transform(env);
            List<Double> bboxLL = List.of(envLL.getMinX(), envLL.getMaxX(), envLL.getMinY(), envLL.getMaxY());
            
            //            Polygon poly = GeometryUtilities.createPolygonFromEnvelope(env);
            // Date(end.getMilliseconds())); --> Filter later :)

            GridCoverage2D coverage = null;
            
            collection.setBboxFilter(new double[] {
            		bboxLL.get(0),
            		bboxLL.get(2),
            		bboxLL.get(1),
            		bboxLL.get(3)
            });
            
            if (assetPredicate == null || 
            		resource.getType() != org.integratedmodelling.klab.api.provenance.IArtifact.Type.NUMBER) {
            	// If it was raster, then it necessarily should have been number, 
                // let's assume object means vector
                scope.getMonitor().debug("Query STAC " + collectionUrl + "to get the features");
                // Only get the features from STAC Collection, no need to interact with Rasters
                FeatureSource<SimpleFeatureType, SimpleFeature> source;
                if (assetPredicate == null) {
                	try {
                        source = STACFeatureExtension.getFeatures(catalogData, collectionId, List.of(bboxLL.get(0),
                        		bboxLL.get(2),
                        		bboxLL.get(1),
                        		bboxLL.get(3)), effectiveTime.getStart(),
                                effectiveTime.getEnd());
                    } catch (Exception e) {
                        manager.close();
                        throw new KlabResourceAccessException("Cannot extract features from STAC Collection - " + e.getMessage());
                    }
                    encoder = new VectorEncoder();
                    ((VectorEncoder) encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
                    manager.close();
                } else {            	
                	List<HMStacItem> items = collection.searchItems();
                    if (items.isEmpty()) {
                        manager.close();
                        throw new KlabIllegalStateException("No STAC items found for this context, check the Spatial/ Temporal bounds of items and the Context");
                    }
          
                    var assets = items.stream()
                    	    .flatMap(item -> item.getAssets().stream())
                    	    .filter(assetPredicate)
                    	    .toList();
                    scope.getMonitor().debug("Found " + assets.size() + " Assets to get the requested geometry");
                    try {
                        source = STACFeatureExtension.getFeatures(assets);
                        encoder = new VectorEncoder();
                        ((VectorEncoder) encoder).encodeFromFeatures(source, resource, urnParameters, geometry, builder, scope);
                    } catch (Exception e) {
                        manager.close();
                        throw new KlabResourceAccessException("Cannot extract features from STAC Collection - " + e.getMessage());
                    }
                    manager.close();
                }
                return;   
            }
            
            
            List<HMStacItem> items = searchItemsWithRetry(collection, scope.getMonitor());
            
            if (mergeMode == HMRaster.MergeMode.SUBSTITUTE && !items.isEmpty()) {
                sortByDate(items, scope.getMonitor());
            }

            // the s3 assets are read through the access of the manager (also the legacy s3EndpointUrl credentials)
            var time = effectiveTime;
            // Filter here based on time, since in some STAC collections they don't yet support
            // temporal filtering :( like ECDC
            
            var pred2 = assetPredicate;
            
            List<HMStacItem> itemsWithinTime = items.stream()
                    .filter(item -> isWithinRange(
                            item,
                            time.getStart().getMilliseconds(),
                            time.getEnd().getMilliseconds()))
                    .filter(item -> item.getAssets().stream()
                            .anyMatch(pred2))
                    .collect(Collectors.toList());
            
            if (itemsWithinTime.isEmpty()) {
            	scope.getMonitor().debug("Couldn't find items satisfying temporal and asset based constraints"
            			+ " within the specified time range, Applying Temporal Mediation");
            	
            	Map<String, List<HMStacItem>> grouped = items.stream()
                        .filter(item -> item.getAssets().stream()
                                .anyMatch(pred2))
                        .collect(Collectors.groupingBy(
                                item -> geometryKey(item.getGeometry())
                        ));

                items = grouped.values().stream()
                        .map(group -> group.stream()
                                .min(Comparator.comparingLong(
                                        groupItem -> checkDuration(
                                                groupItem,
                                                time.getEnd().getMilliseconds())))
                                .orElseThrow())
                        .collect(Collectors.toList());
            	 
            } else {
            	items = itemsWithinTime;
            }

            // Once the support for customized predicate is added, we can apply for features as well

            var pred = assetPredicate;
            Set<Integer> EPSGAtAssets = items.stream()
                    .flatMap(item -> item.getAssets().stream().filter(pred).findFirst()
                            .map(asset -> asset.getEpsg() != null ? asset.getEpsg() : item.getEpsg()).stream())
                    .collect(Collectors.toUnmodifiableSet());

            if (EPSGAtAssets.size() > 1) {
                scope.getMonitor().warn("Multiple EPSGs found on the assets in items " + EPSGAtAssets.toString() + "."
                        + "The transformation process could affect the data.");
            }
            
            boolean slowReqtriggered = false;
            
            // Specific Implementation for the Slow Requests flow in WEED 
            if (collection.getId().contains("IUCNGET")
            		|| collection.getId().contains("EUNIS2021plus") // Update this to the actual RB STAC for EUNIS
            		|| collection.getId().contains("RuleBased")) { // Update this to the actual RB IUCN STAC { 
            		//&& resource.getUrn().contains("im.resources-main")) { 
            
            	Geometry unionStacInference = UnaryUnionOp.union(
            	        items.stream()
            	        	.map(item -> toCRS(item, wgs84CRS))
            	             .filter(Objects::nonNull)
            	             .collect(Collectors.toList()));
            	
            	String wktContext = space.getShape().toString();
            	
            	Pattern pattern = Pattern.compile("^EPSG:(\\d+)\\s+");
            	Matcher matcher = pattern.matcher(wktContext);

            	String klabContextEpsg = null;

            	if (matcher.find()) {
            		klabContextEpsg = matcher.group(1);
            	    wktContext = wktContext.substring(matcher.end());
            	}
            	Geometry klabContextGeom = new WKTReader().read(wktContext);
            	
            	if (!klabContextEpsg.equals("4326")) {
            		scope.getMonitor().info("Reprojecting the Set context in kLAB to 4326");
            		CoordinateReferenceSystem sourceCRS = HMCrsRegistry.INSTANCE.getCrs("EPSG:" + klabContextEpsg, true);
            		klabContextGeom = toCRS(klabContextGeom, sourceCRS, targetCRS);
            	}
            	
				
				if (collection.getId().toLowerCase().contains("rule")) { 
					// Rule Based Logic
					
					if (unionStacInference == null || !unionStacInference.contains(klabContextGeom)) {
						
						slowReqtriggered = true;
						OpenEO service = OpenEOAdapter.getClient("openeo_weed.dataspace.copernicus.eu");
	            		List<Process> processes = new ArrayList<>();
	            		String processNamespace = "https://raw.githubusercontent.com/ESA-WEED-project/OpenEO-UDP-UDF-catalogue/refs/heads/main/UDP/json/udp_starter_RB.json";
	            		String processID = "udp_starter";
	            		
	            		Process process = JsonUtils.load(new URL(processNamespace),
								Process.class);
						process.encodeSelf(processNamespace);

	            		scope.getMonitor().warn("The requested extend for Rule Based inferences is not completely contained in STAC, Starting Rule Based Inference Request");
						processes.add(process);
						
						 // IUCN exists only for RB
						 String scenarioId = "V502";
						 String digitalId = "RuleBased";
						 
						 StringWriter out = new StringWriter();
						 klabContextGeom = TopologyPreservingSimplifier.simplify(klabContextGeom, 0.0001);
						 new GeometryJSON(6).write(klabContextGeom, out);       // 6 max decimal places
						 JSONObject ctxgeojson = new JSONObject(out.toString());
						 var modelIds = WEEDModelSTACExtension.GetRBModelIDs(bboxLL, scope.getMonitor());
						 
						 for (var modelId:modelIds) {
							 
							 JSONObject arguments = new JSONObject()
								.put("bbox", ctxgeojson) 
								.put("digitalId", digitalId)  // Forms the STAC coordinate later
								.put("scenarioId", scenarioId) // Forms the STAC coordinate later 
								.put("year", ctxTime.getStart().getYear())
								.put("modelId", modelId)
								.put("userId", Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class).getUsername())
								.put("dt_url", "https://services.integratedmodelling.org/runtime/main/api/v1/dt/ESA_INSTITUTIONAL.hzo55ie1vj"); 
						
							OpenEOFuture job = service.submit(processID, arguments,
		    						scope.getMonitor(), processes.toArray(new Process[processes.size()]));
							
							if (job.isCancelled()) {
								scope.getMonitor().warn("job canceled");
							} else if (job.getError() != null) {
								scope.getMonitor().error(job.getError());
							} else {
								scope.getMonitor().info("Inference Request has been submitted to the RB Workflows");
							}
						 }
					}
				} else {
					// Machine Learning Logic
					if (unionStacInference == null || !unionStacInference.contains(klabContextGeom)) {
	            		
	            		// If its null this means there is no inference whatsoever
	            		
						slowReqtriggered = true;
	            		scope.getMonitor().info("Fetching Model IDs to pass to the Slow Request UDP");
	            		List<String >modelIds = null;
	            		try {
							modelIds = WEEDModelSTACExtension.GetONNXModelIDs(bboxLL, scope.getMonitor(), collection.getId().toLowerCase());
							if (modelIds == null || modelIds.size() == 0) {
								throw new Exception("No ONNX Models were found over the specified context");
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new KlabIllegalStateException("Error occured while getting ONNX Model info over the specified context");
						}
	            		OpenEO service = OpenEOAdapter.getClient("openeo_weed.dataspace.copernicus.eu");
	            		List<Process> processes = new ArrayList<>();
	            		String processNamespace = "https://raw.githubusercontent.com/ESA-WEED-project/OpenEO-UDP-UDF-catalogue/refs/heads/main/UDP/json/udp_starter.json";
	            		String processID = "udp_starter";
	            		
	            		Process process = JsonUtils.load(new URL(processNamespace),
								Process.class);
						process.encodeSelf(processNamespace);

	            		scope.getMonitor().warn("The requested extend for ML inferences is not completely contained in STAC, Starting ML Inference Request");
						processes.add(process);
						
						String scenarioId = null;
						String digitalId = null;
						
						 if (collection.getId().contains("IUCNGET")) {
							 scenarioId = "V330";
							 digitalId = "MachineLearning-IUCNGET";
									 
						 } else {
							 scenarioId = "V320";
							 digitalId = "MachineLearning-EUNIS2021plus";
						 }
						
						 StringWriter out = new StringWriter();
						 klabContextGeom = TopologyPreservingSimplifier.simplify(klabContextGeom, 0.0001);
						 new GeometryJSON(6).write(klabContextGeom, out);       // 6 max decimal places
						 JSONObject ctxgeojson = new JSONObject(out.toString());
						 
						 
						 
						for (var modelId:modelIds) { // triggering multiple UDPs parallely
							JSONObject arguments = new JSONObject()
								.put("bbox", ctxgeojson) 
								.put("digitalId", digitalId)  // Forms the STAC coordinate later
								.put("scenarioId", scenarioId) // Forms the STAC coordinate later 
								.put("year", ctxTime.getStart().getYear())
								.put("onnx_model", modelId) // Hardcoding for now only for Europe, until the "BEST" model is decided!
								.put("userId", Authentication.INSTANCE.getAuthenticatedIdentity(IUserIdentity.class).getUsername())
								.put("dt_url", "https://services.integratedmodelling.org/runtime/main/api/v1/dt/ESA_INSTITUTIONAL.hzo55ie1vj"); 
							
							OpenEOFuture job = service.submit(processID, arguments,
		    						scope.getMonitor(), processes.toArray(new Process[processes.size()]));
							
							if (job.isCancelled()) {
								scope.getMonitor().warn("job canceled");
							} else if (job.getError() != null) {
								scope.getMonitor().error(job.getError());
							} else {
								scope.getMonitor().info("Inference Request has been submitted to the ML Workflows");
							}
						}
	            	}
				}                
            } 
            
            
            if (items.size() == 0) {
            	manager.close();
            	
            	if (slowReqtriggered) {
                    scope.getMonitor().warn("Inferences over the Spatial and Temporal Context has been triggered and will be notified");
                    return;
            	}
                throw new KlabIllegalStateException(
                        "No STAC items were satifying constraints and Couldn't apply Temporal Mediation");
            } else {
                scope.getMonitor().debug("Found " + items.size() + " STAC items for generating the observation.");
            }
            
            
            RegionMap region = RegionMap.fromBoundsAndGrid(space.getEnvelope().getMinX(), space.getEnvelope().getMaxX(),
                    space.getEnvelope().getMinY(), space.getEnvelope().getMaxY(), (int) grid.getXCells(), (int) grid.getYCells());
            
            ReferencedEnvelope regionEnvelope = new ReferencedEnvelope(region.toEnvelope(),
                    space.getProjection().getCoordinateReferenceSystem());
            RegionMap regionTransformed = RegionMap.fromEnvelopeAndGrid(regionEnvelope, (int) grid.getXCells(),
                    (int) grid.getYCells());
            
			var regionEnvelopeLL = regionEnvelope.transform(wgs84CRS, true);
            var regionTransformedLL = RegionMap.fromEnvelopeAndGrid(regionEnvelopeLL, (int) grid.getXCells(),
                    (int) grid.getYCells());
            
        	HMRaster outRaster = collection.readRasterBandOnRegion(regionTransformedLL, assetPredicate, items, allowTransform,
                    mergeMode, lpm); 
            if (outRaster == null) {
                scope.getMonitor().error("Unable to build the output from the STAC Resource");
                throw new KlabIllegalStateException("Unable to build the output from the STAC Resource");
            }
            coverage = toContextCoverage(outRaster, space, grid, mergeMode);
			
			if (bandIndex != null) { // Which means theat it's a Multi Band COG
                coverage = (GridCoverage2D) Operations.DEFAULT.selectSampleDimension(coverage, new int[]{bandIndex});
            }
      
            manager.close();
            encoder = new RasterEncoder();
            ((RasterEncoder) encoder).encodeFromCoverage(resource, urnParameters, coverage, geometry, builder, scope);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KlabInternalErrorException("Cannot build STAC raster output. Reason " + e.getMessage());
        }
    }

	private void closeManagerWithEx(HMStacManager manager) {
		if(manager!=null)
			try {
				manager.close();
			} catch (Exception e1) {
				throw new KlabResourceAccessException("Unable to close manager: " + e1.getMessage());
			}
	}
    
    private static String geometryKey(Geometry geom) {
        Geometry normalized = geom.copy();
        normalized.normalize(); 
        return normalized.toText();
    }

    private Predicate<HMStacAsset> getAssetPredicateFromJSONSelector(IResource resource) {
        String jsonSelector = resource.getParameters().get("jsonSelector", String.class);
        String jsonValue = resource.getParameters().get("jsonValue", String.class);
        if (jsonSelector != null && !jsonSelector.isBlank() && jsonValue != null) {
            try {
                return STACPathExpression.STACAssetPredicate.fromHMStacAsset(jsonSelector, jsonValue);
            } catch (IllegalArgumentException e) {
                throw new KlabIllegalArgumentException("Invalid STAC asset JSON selector: " + jsonSelector);
            }
        } else {
            throw new KlabIllegalArgumentException("Either asset or both jsonSelector and jsonValue must be provided");
        }
    }

    /*
        To check if an Item (of type HMStacItem) is within a time range
     */
    private boolean isWithinRange(HMStacItem item, long startMillis, long endMillis) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startTimestamp = item.getStartTimestamp();
        String endTimestamp = item.getEndTimestamp();

        if (startTimestamp == null || endTimestamp == null) {
            return true; // Assume the time part is ok
        }

        try {

            long itemStart = LocalDateTime.parse(item.getStartTimestamp(), formatter).atZone(ZoneOffset.UTC).toInstant()
                    .toEpochMilli();

            long itemEnd = LocalDateTime.parse(item.getEndTimestamp(), formatter).atZone(ZoneOffset.UTC).toInstant()
                    .toEpochMilli();

            return (startMillis >= itemStart || Math.abs(startMillis - itemStart) < 10000)  
            		&& (endMillis <= itemEnd || Math.abs(endMillis - itemEnd) < 10000); // Allow some small tolerance

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private long checkDuration(HMStacItem item, long endMillis) {
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (item.getEndTimestamp() == null) { // assume like it's the correct data
        	return 0;
        }
        
        try {
            long itemEnd = LocalDateTime.parse(item.getEndTimestamp(), formatter).atZone(ZoneOffset.UTC).toInstant()
                    .toEpochMilli();
            long itemStart = LocalDateTime.parse(item.getEndTimestamp(), formatter).atZone(ZoneOffset.UTC).toInstant()
                    .toEpochMilli();
            
            if (itemStart > endMillis) { // Deprioritize future items since it doesn't make a lot of sense
            	return Long.MAX_VALUE;
            }
            
            return Math.abs(itemEnd - endMillis);
        } catch(Exception e) {
        	e.printStackTrace();
        	return Long.MAX_VALUE; // return an arbitrary large number
        }
    }
    
    
    /**
     * Select the items for a time range, as done for the searchable catalogs: the items whose time covers the
     * range, else the item closest to the end of the range.
     *
     * @param assetPredicate if not null, only the items with a matching asset are selected.
     */
    private List<HMStacItem> selectItemsInTime(List<HMStacItem> items, Predicate<HMStacAsset> assetPredicate,
            long startMillis, long endMillis, IMonitor monitor) {
        Predicate<HMStacItem> hasAsset = item -> assetPredicate == null
                || item.getAssets().stream().anyMatch(assetPredicate);
        List<HMStacItem> itemsWithinTime = items.stream().filter(item -> isWithinRange(item, startMillis, endMillis))
                .filter(hasAsset).collect(Collectors.toList());
        if (!itemsWithinTime.isEmpty()) {
            return itemsWithinTime;
        }
        monitor.debug("Couldn't find items satisfying temporal and asset based constraints"
                + " within the specified time range, Applying Temporal Mediation");
        Map<String, List<HMStacItem>> grouped = items.stream().filter(hasAsset)
                .filter(item -> item.getGeometry() != null)
                .collect(Collectors.groupingBy(item -> geometryKey(item.getGeometry())));
        return grouped.values().stream()
                .map(group -> group.stream().min(Comparator.comparingLong(item -> checkDuration(item, endMillis)))
                        .orElseThrow())
                .collect(Collectors.toList());
    }

    /**
     * Map a raster read by HortonMachine on the grid of the context, in the crs of the context. The coverage is
     * then copied cell by cell by {@link RasterEncoder#encodeFromCoverage}, which doesn't resample.
     *
     * <p>The values are mapped by coordinates, transforming the cell centres if the crs differ; no reprojection
     * at all if the data are in the crs of the context (HM internals).</p>
     */
    private static GridCoverage2D toContextCoverage(HMRaster raster, Space space, IGrid grid, MergeMode mergeMode)
            throws Exception {
        CoordinateReferenceSystem contextCrs = space.getProjection().getCoordinateReferenceSystem();
        IEnvelope envelope = space.getEnvelope();
        RegionMap contextRegion = RegionMap.fromBoundsAndGrid(envelope.getMinX(), envelope.getMaxX(), envelope.getMinY(),
                envelope.getMaxY(), (int) grid.getXCells(), (int) grid.getYCells());
        HMRaster contextRaster = new HMRasterWritableBuilder().setNoValue(raster.getNovalue()).setName("context")
                .setRegion(contextRegion).setCrs(contextCrs).build();
        contextRaster.mapRaster(null, raster, mergeMode);
        return contextRaster.buildCoverage();
    }

    /**
     * @return the shape of the context in lat/lon (WGS84), the reference system of the STAC item geometries.
     */
    private static Geometry toLatLon(Space space) {
        try {
            return space.getShape().transform(Projection.getLatLon()).getJTSGeometry();
        } catch (Exception e) {
            throw new KlabResourceAccessException(
                    "Cannot transform the context to lat/lon to find the STAC items: " + e.getMessage());
        }
    }

    private List<HMStacItem> searchItemsWithRetry(HMStacCollection collection, IMonitor monitor) throws IOException {
        int maxRetries = 5;
        long backoffMillis = 10000; // start at 10s

        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                return collection.searchItems();
            } catch (Exception e) {
                boolean isRateLimited = isRateLimitError(e);
                if (!isRateLimited || attempt == maxRetries) {
                    throw e instanceof IOException ? (IOException) e : new IOException(e);
                } 

                try {
                    Thread.sleep(backoffMillis);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted while backing off after 429", ie);
                }

                backoffMillis *= 2; // exponential: 10s, 20s, 40s, 80s, 160s
                monitor.debug("Search Request throttled; Retrying Search after: " + backoffMillis + " Miliseconds");
            }
        }
        throw new IOException("Unreachable");
    }

    private boolean isRateLimitError(Throwable e) {
        while (e != null) {
            if (e.getMessage() != null && e.getMessage().contains("429")) {
                return true;
            }
            e = e.getCause();
        }
        return false;
    }
    
    private Geometry toCRS(HMStacItem item, CoordinateReferenceSystem targetCRS) {
        try {
        	CoordinateReferenceSystem sourceCRS = HMCrsRegistry.INSTANCE.getCrs("EPSG:" + item.getEpsg(), true);
            var transformer = new HMCrsTransformer(sourceCRS, targetCRS);
        	transformer.setAcceptLenientDatumShift(true);
        	return transformer.transform(item.getGeometry());
        } catch (Exception e) {
            return null;
        }
    }
    
    private Geometry toCRS(Geometry geom, CoordinateReferenceSystem sourceCRS, CoordinateReferenceSystem targetCRS) {
        try {
            var transformer = new HMCrsTransformer(sourceCRS, targetCRS);
        	transformer.setAcceptLenientDatumShift(true);
        	return transformer.transform(geom);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void listDetail(IResource resource, OutputStream stream, boolean verbose, IMonitor monitor) {
        // TODO Auto-generated method stub
    }

}
