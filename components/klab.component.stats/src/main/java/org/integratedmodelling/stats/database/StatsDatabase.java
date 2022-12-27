package org.integratedmodelling.stats.database;

import org.integratedmodelling.klab.persistence.postgis.Postgis;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;

public class StatsDatabase extends Postgis {

    public static final String[] structuralStatsStatements = {
    		
            "DROP TYPE IF EXISTS status CASCADE;", // shouldn't be necessary
            "CREATE TYPE status AS ENUM ('Success', 'Exception', 'Error', 'Interrupt');",
            "CREATE TABLE contexts(\n"
            + "   id VARCHAR(64) PRIMARY KEY NOT NULL,\n"
            + "   observable VARCHAR(512),\n"
            + "   scenarios VARCHAR(2048),\n"
            + "   created DATE,\n"
            + "   engine_type VARCHAR(48),\n"
            + "   application VARCHAR(48),\n"
            + "   node_version VARCHAR(48),\n"
            + "   engine_version VARCHAR(48),\n"
            + "   stats_version VARCHAR(48),\n"
            + "   principal VARCHAR(512),\n"
            + "   groups VARCHAR(512),\n"
            + "   scale_size BIGINT,\n"
            + "   space_size BIGINT,\n"
            + "   space_complexity FLOAT,\n"
            + "   space_bbox_coverage FLOAT,\n" // context area vs. bounding box area
            + "   time_size BIGINT,\n"
            + "   context_start DATE,\n"
            + "   context_end DATE,\n"
            + "   time_resolution VARCHAR(48),\n"
            + "   space_resolution VARCHAR(48),\n"
            + "   outcome status\n"
            + ");",
            "CREATE TABLE queries(\n"
            + "   id INTEGER NOT NULL,\n"
            + "   context_id CHAR(64) NOT NULL,\n"
            + "   observable VARCHAR(512),\n"
            + "   scenarios VARCHAR(2048),\n"
            + "   total_time_sec FLOAT,\n"
            + "   dataflow_complexity BIGINT,\n"
            + "   total_coverage FLOAT,\n"
            + "   outcome status,\n"
            + "   dataflow_id VARCHAR(512),\n"              // only collected for special cases
            + "   PRIMARY KEY(id, context_id)\n"
            + ");",
            "DROP TYPE IF EXISTS asset_type CASCADE;", // shouldn't be necessary
            "CREATE TYPE asset_type AS ENUM ('ResolvedObservable', 'Model', 'Resource', 'Export');",
            "CREATE TABLE assets(\n"
            + "   name VARCHAR(512),\n"
            + "   query_id INTEGER NOT NULL,\n"
            + "   context_id CHAR(64) NOT NULL,\n"
            + "   asset_type asset_type,\n"
            + "   total_time_sec FLOAT,\n"
            + "   total_byte_size BIGINT,\n"
            + "   scheduled_steps BIGINT,\n"
            + "   total_passes INTEGER,\n"
            + "   outcome status,\n"
            + "   source_node_id VARCHAR(64)"
            + ");",
            
            // "price" of model and resource assets in k.LAB credits, defaulting at 0 
            // when a new asset is encountered and enabling a formula using scale and usage variables
            // to compute the associated cost in credits. The admin API should enable modifying the \
            // "cost in k.LAB credits" table and associating a price per credit to users, 
            "CREATE TABLE asset_value(\n"
            + "   NAME VARCHAR(512) PRIMARY KEY NOT NULL,\n"
            + "   ASSET_TYPE asset_type,\n"
            + "   COST_FORMULA VARCHAR(1024)\n"
            + ");",

            "ALTER TABLE contexts ADD COLUMN geom geometry(Polygon,4326);",
            "ALTER TABLE assets ADD COLUMN geom geometry(Polygon,4326);",

    
    };

    public StatsDatabase() {
        super("klab_stats");
    }

    @Override
    protected boolean createDatabase() {
        if (super.createDatabase()) {
            for (String sql : structuralStatsStatements) {
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
