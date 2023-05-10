package org.integratedmodelling.klab.stac;

import java.util.HashMap;
import java.util.Map;

import org.hortonmachine.gears.io.stac.HMStacCollection;
import org.hortonmachine.gears.io.stac.HMStacManager;

/**
 * Singleton collecting the STACClient/s for all the referenced catalogs.
 * 
 * @author Ferd
 *
 */
public class STAC {

    private Map<String, HMStacManager> clients = new HashMap<>();

    public static void main(String[] args) throws Exception {

        STAC stac = new STAC();
        String[] catalogUrls = {"https://planetarycomputer.microsoft.com/api/stac/v1",
                "https://s3.eu-central-1.wasabisys.com/stac/odse/lcv_landcover.231_lucas.corine.eml/collection.json"};

        for (String curl : catalogUrls) {
            HMStacManager catalog = new HMStacManager(curl, null);
            catalog.open();
            stac.clients.put(curl, catalog);
        }

        for (String cock : stac.clients.keySet()) {
            System.out.println("COCK " + cock);
            for (HMStacCollection collection : stac.clients.get(cock).getCollections()) {
                System.out.println("  " + collection.getId() + ": " + collection.getTemporalBounds());
            }
        }

        // Map<String, Object> diocristo = new HashMap<>();
        // String url = "https://planetarycomputer.microsoft.com/api/stac/v1";
        // diocristo.put(STACDataStoreFactory.LANDING_PAGE.key, url);
        // DataStore cristodio = stac.factory.createDataStore(diocristo);
        // stac.clients.put(url, cristodio);
        // for (Name stores : cristodio.getNames()) {
        // System.out.println(stores);
        // }

    }

}
