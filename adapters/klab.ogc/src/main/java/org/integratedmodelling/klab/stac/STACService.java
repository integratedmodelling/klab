package org.integratedmodelling.klab.stac;

import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacManager;
import org.hortonmachine.gears.libs.monitor.LogProgressMonitor;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

import kong.unirest.json.JSONObject;

public class STACService {
    private HMStacManager catalog;
    private HMStacCollection collection;

    public STACService(String collectionUrl) {
        JSONObject collectionData = STACUtils.requestMetadata(collectionUrl, "collection");
        String collectionId = STACCollectionParser.readCollectionId(collectionData);
        String catalogUrl = STACUtils.getCatalogUrl(collectionData);

        LogProgressMonitor lpm = new LogProgressMonitor();
        this.catalog = new HMStacManager(catalogUrl, lpm);
        try {
            this.catalog.open();
        } catch (Exception e) {
            throw new KlabInternalErrorException("Error at STAC service. Cannot read catalog at '" + catalogUrl + "'.");
        }

        try {
            this.collection = catalog.getCollectionById(collectionId);
        } catch (Exception e) {
            throw new KlabInternalErrorException("Error at STAC service. Cannot read collection at '" + collectionUrl + "'.");
        }
        if (collection == null) {
            throw new KlabInternalErrorException("Error at STAC service. Endpoint '" + catalogUrl + "' has no collection '" + collectionId + "'.");
        }
    }

    public HMStacCollection getCollection() {
        return collection;
    }
}
