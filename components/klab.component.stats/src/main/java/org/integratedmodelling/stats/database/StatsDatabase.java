package org.integratedmodelling.stats.database;

import org.integratedmodelling.klab.ogc.integration.Postgis;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;

public class StatsDatabase extends Postgis {

    public StatsDatabase(String dbname) {
        super(dbname);
    }

    public void add(ObservationResultStatistics[] stats) {
        // TODO check referential integrity and add a queue of observations in case we get orphan
        // context IDs.
    }

}
