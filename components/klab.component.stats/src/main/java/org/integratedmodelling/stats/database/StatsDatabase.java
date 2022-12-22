//package org.integratedmodelling.stats.database;
//
//import org.integratedmodelling.klab.ogc.integration.Postgis;
//import org.integratedmodelling.klab.rest.ObservationResultStatistics;
//
//public class StatsDatabase extends Postgis {
//
//    public static final String[] structuralStatements = {
//            "CREATE TYPE status AS ENUM ('Success', 'Exception', 'Error', 'Interrupt');",
//            "CREATE TABLE context(\n"
//            + "   ID VARCHAR(64) PRIMARY KEY NOT NULL,\n"
//            + "   OBSERVABLE VARCHAR(512),\n"
//            + "   CREATED DATE,\n"
//            + "   PRINCIPAL VARCHAR(64),\n"
//            + "   GROUPS VARCHAR(512),\n"
//            + "   SCALE_SIZE LONG INTEGER,\n"
//            + "   SPACE_SIZE LONG INTEGER,\n"
//            + "   SPACE_COMPLEXITY FLOAT,\n"
//            + "   TIME_SIZE LONG INTEGER,\n"
//            + "   CONTEXT_START DATE INTEGER,\n"
//            + "   CONTEXT_END DATE,\n"
//            + "   STATUS status,\n"
//            + ");",
//            "CREATE TABLE queries(\n"
//            + "   ID INTEGER NOT NULL,\n"
//            + "   CONTEXT_ID CHAR(64) NOT NULL,\n"
//            + "   OBSERVABLE VARCHAR(512),\n"
//            + "   TOTAL_TIME_SEC FLOAT,\n"
//            + "   DATAFLOW_COMPLEXITY LONG INTEGER,\n"
//            + "   STATUS status,\n"
//            + "   DATAFLOW_ID dataflow,\n"              // only collected for special cases
//            + "   PRIMARY KEY(ID, CONTEXT_ID)\n"
//            + ");",
//            "CREATE TYPE asset_type AS ENUM ('ResolvedObservable', 'Model', 'Resource', 'Export');",
//            "CREATE TABLE assets(\n"
//            + "   NAME VARCHAR(512),\n"
//            + "   QUERY_ID INTEGER NOT NULL,\n"
//            + "   CONTEXT_ID CHAR(64) NOT NULL,\n"
//            + "   ASSET_TYPE asset_type,\n"
//            + "   TOTAL_DURATION FLOAT,\n"
//            + "   SCHEDULED_STEPS LONG INTEGER,\n"
//            + "   TOTAL_PASSES LONG INTEGER,\n"
//            + "   STATUS status,\n"
//            + "   NODE_ID VARCHAR(64),\n"
//            + "   column3 datatype,\n"
//            + "   .....\n"
//            + "   columnN datatype\n"
//            + ");",
//            
//            // "price" of model and resource assets in k.LAB credits, defaulting at 0 
//            // when a new asset is encountered and enabling a formula using scale and usage variables
//            // to compute the associated cost in credits. The admin API should enable modifying the \
//            // "cost in k.LAB credits" table and associating a price per credit to users, 
//            "CREATE TABLE asset_value(\n"
//            + "   NAME VARCHAR(512) PRIMARY KEY NOT NULL,\n"
//            + "   ASSET_TYPE asset_type,\n"
//            + "   COST_FORMULA VARCHAR(1024)\n"
//            + ");",
//
//            "SELECT AddGeometryColumn('public','context', 'geom', 4326, 'POLYGON', 2, false);",
//            "SELECT AddGeometryColumn('public','assets', 'geom', 4326, 'POLYGON', 2, false);"
//
//    
//    };
//
//    public StatsDatabase() {
//        super("klab_stats");
//    }
//
//    @Override
//    protected boolean createDatabase() {
//        if (super.createDatabase()) {
//            for (String sql : structuralStatements) {
//                if (!execute(sql)) {
//                    return false;
//                }
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public void add(ObservationResultStatistics[] stats) {
//        // TODO check referential integrity and add a queue of observations in case we get orphan
//        // context IDs.
//    }
//
//}
