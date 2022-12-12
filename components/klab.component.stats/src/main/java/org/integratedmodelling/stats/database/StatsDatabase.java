package org.integratedmodelling.stats.database;

import org.integratedmodelling.klab.ogc.integration.Postgis;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;

public class StatsDatabase extends Postgis {

    public static final String[] structuralStatements = {
            "CREATE TABLE context(\n"
            + "   ID char(64) PRIMARY KEY NOT NULL,\n"
            + "   column2 datatype,\n"
            + "   column3 datatype,\n"
            + "   .....\n"
            + "   columnN datatype\n"
            + ");",
            "CREATE TABLE queries(\n"
            + "   ID INTEGER NOT NULL,\n"
            + "   CONTEXT_ID CHAR(64) NOT NULL,\n"
            + "   column3 datatype,\n"
            + "   .....\n"
            + "   columnN datatype,\n"
            + "   PRIMARY KEY(ID, CONTEXT_ID)\n"
            + ");",
            "CREATE TABLE assets(\n"
            + "   column1 datatype,\n"
            + "   column2 datatype,\n"
            + "   column3 datatype,\n"
            + "   .....\n"
            + "   columnN datatype,\n"
            + "   PRIMARY KEY( one or more columns )\n"
            + ");",
            
            // "price" of model and resource assets in k.LAB credits, defaulting at 0 
            // when a new asset is encountered. The admin API should enable modifying the "cost in k.LAB credits" table and
            // associating a price per credit to users
            "CREATE TABLE asset_value(\n"
            + "   NAME VARCHAR(512) PRIMARY KEY NOT NULL,\n"
            + "   FLOAT credits\n"
            + ");"
    };

    public StatsDatabase() {
        super("klab_stats");
    }

    @Override
    protected boolean createDatabase() {
        if (super.createDatabase()) {
            for (String sql : structuralStatements) {
                if (!execute(sql)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void add(ObservationResultStatistics[] stats) {
        // TODO check referential integrity and add a queue of observations in case we get orphan
        // context IDs.
    }

}
