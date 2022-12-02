package org.integratedmodelling.klab.stac;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.http.SimpleHttpClient;
import org.geotools.stac.client.Collection;
import org.geotools.stac.client.STACClient;
import org.geotools.stac.store.STACDataStore;
import org.geotools.stac.store.STACDataStoreFactory;
import org.opengis.feature.type.Name;

/**
 * Singleton collecting the STACClient/s for all the referenced catalogs.
 * 
 * @author Ferd
 *
 */
public class STAC {

    private STACDataStoreFactory factory = new STACDataStoreFactory();
    
    private Map<String, DataStore> clients = new HashMap<>();

    public static void main(String[] args) throws Exception {
        STAC stac = new STAC();
        Map<String, Object> diocristo = new HashMap<>();
        String url = "https://planetarycomputer.microsoft.com/api/stac/v1";
        diocristo.put(STACDataStoreFactory.LANDING_PAGE.key, url);
        DataStore cristodio = stac.factory.createDataStore(diocristo);
        stac.clients.put(url, cristodio);
        for (Name stores : cristodio.getNames()) {
            System.out.println(stores);
        }
        
    }

}
