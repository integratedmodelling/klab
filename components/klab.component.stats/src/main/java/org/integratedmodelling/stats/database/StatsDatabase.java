package org.integratedmodelling.stats.database;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.persistence.postgis.Postgis;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.rest.ObservationAssetStatistics;
import org.integratedmodelling.klab.rest.ObservationAssetStatistics.Type;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.klab.rest.ScaleStatistics;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.stats.reporting.StatsReport;

public class StatsDatabase extends Postgis {

	/* @formatter:off */
	/*
	 * For some assets it makes sense to compute unit costs as a function of size as well
	 * 
	 * TODO use SQRT(scale_size)?
	 */
	private static final String ASSET_UNIT_COST_CALCULATOR = 
			"SELECT assets.name, assets.asset_type, avg(assets.total_time_sec/((assets.total_passes + 1) * contexts.scale_size)) as unit_cost, count(assets)\n"
			+ "	FROM assets, contexts \n"
			+ "	WHERE assets.outcome = 'Success' AND contexts.id = assets.context_id \n"
			+ "	GROUP BY assets.name, assets.asset_type\n"
			+ "	ORDER BY assets.asset_type, unit_cost;";
	
	/*
	 * This one outputs a beautiful GeoJSON feature collection of all observations - add a template for 
	 * other conditions and possibly another for assets. Remove the ST_Centroid to obtain bounding boxes.
	 */
	private static final String GEOJSON_OBSERVATION_COLLECTION = 
			"SELECT\n"
			+ "  json_build_object(\n"
			+ "    'type', 'FeatureCollection',\n"
			+ "    'features', json_agg(ST_AsGeoJSON(t.*)::json)\n"
			+ "  ) as json\n"
			+ "FROM (\n"
			+ "  SELECT\n"
			+ "	   queries.observable as observation,\n"
			+ "    coalesce(contexts.application, 'k.Explorer') as application,\n"
			+ "    contexts.scale_size,\n"
			+ "	   queries.outcome,\n"
			+ "	   ST_Centroid(contexts.geom)\n"
			+ "  FROM\n"
			+ "    contexts, queries\n"
			+ "  WHERE \n"
			+ "	    contexts.id = queries.context_id\n"
			+ "     {RESTRICTIONS}" // AND template here
			+ ") AS t";
	
	/*
	 * one submission at a time to ensure referential integrity of
	 * context IDs
	 */
	private Executor executor = Executors.newSingleThreadExecutor();
	
    public static final String[] structuralStatsStatements = {
    		
            "DROP TYPE IF EXISTS status CASCADE;", // shouldn't be necessary
            "CREATE TYPE status AS ENUM ('Success', 'Exception', 'Error', 'Interrupt');",
            "CREATE TABLE contexts(\n"
            + "   id VARCHAR(64) PRIMARY KEY NOT NULL,\n"
            + "   observable VARCHAR(512),\n"
            + "   scenarios VARCHAR(2048),\n"
            + "   created BIGINT,\n"
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
            + "   context_start BIGINT,\n"
            + "   context_end BIGINT,\n"
            + "   time_resolution VARCHAR(48),\n"
            + "   space_resolution VARCHAR(48),\n"
            + "   context_name VARCHAR(512),\n"
            + "   outcome status\n"
            + ");",
            
            /*
             * the query table also hosts downloads of previously computed observations, with 
             * is_download = true and dataflow_complexity = download size in bytes
             */
            "CREATE TABLE queries(\n"
            + "   id INTEGER NOT NULL,\n"
            + "   context_id CHAR(64) NOT NULL,\n"
            + "   observable VARCHAR(512),\n"
            + "   scenarios VARCHAR(2048),\n"
            + "   total_time_sec FLOAT,\n"
            + "   dataflow_complexity BIGINT,\n"
            + "   resolved_coverage FLOAT,\n"
            + "   outcome status,\n"
            + "   dataflow_id VARCHAR(512),\n"              // only collected for special cases
            + "   start_time BIGINT,\n" 
            + "   is_download BOOLEAN,\n" 
            + "   PRIMARY KEY(id, context_id)\n"
            + ");",
            "DROP TYPE IF EXISTS asset_type CASCADE;", // shouldn't be necessary
            "CREATE TYPE asset_type AS ENUM ('ResolvedObservable', 'Model', 'Resource', 'Operation', 'Export');",
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
            + "   name VARCHAR(512),\n"
            + "   asset_type asset_type,\n"
            + "   unit_cost FLOAT,\n"
            + "   cost_formula VARCHAR(1024),\n"
            + "   PRIMARY KEY(name, asset_type)\n"
            + ");",

            "ALTER TABLE contexts ADD COLUMN geom geometry(Polygon,4326);",
            "ALTER TABLE assets ADD COLUMN geom geometry(Polygon,4326);",
    };

	private void store(ObservationResultStatistics stat, String user, String groups) {

		long nextQueryId = count("queries", "context_id = '" + stat.getContextId() + "'");
		
		if (stat.isRoot()) {
			
			ScaleStatistics scale = stat.getScaleStatistics();
			
			/*
			 * new context 
			 */
			String sql = "INSERT INTO contexts VALUES ("
			+ cn(stat.getContextId()) + ", " // "   id VARCHAR(64) PRIMARY KEY NOT NULL,\n"
            + cn(stat.getObservable()) + ", " // "   observable VARCHAR(512),\n"
            + cn(StringUtil.join(stat.getScenarios(), ",")) + ", " // "   scenarios VARCHAR(2048),\n"
            + stat.getStartTime() + ", " // "   created DATE,\n"
            + cn(stat.getEngineName()) + ", " // "   engine_type VARCHAR(48),\n"
            + cn(stat.getApplication()) + ", " // "   application VARCHAR(48),\n"
            + cn(stat.getNodeVersion()) + ", " // "   node_version VARCHAR(48),\n"
            + cn(stat.getEngineVersion()) + ", " // "   engine_version VARCHAR(48),\n"
            + cn(Version.CURRENT) + ", " // "   stats_version VARCHAR(48),\n"
            + cn(user) + ", " // "   principal VARCHAR(512),\n"
            + cn(groups) + ", " // "   groups VARCHAR(512),\n"
            + scale.getSize() + ", " // "   scale_size BIGINT,\n"
            + scale.getSpaceSize() + ", " // "   space_size BIGINT,\n"
            + cnan(scale.getSpaceComplexity()) + ", " // "   space_complexity FLOAT,\n"
            + cnan(scale.getSpaceCoverage()) + ", " // "   space_bbox_coverage FLOAT,\n" // context area vs. bounding box area
            + scale.getTimeSize() + ", " // "   time_size BIGINT,\n"
            + scale.getTimeStart() + ", " // "   context_start DATE,\n"
            + scale.getTimeEnd() + ", " // "   context_end DATE,\n"
            + cn(scale.getTimeResolution()) + ", " // "   time_resolution VARCHAR(48),\n"
            + cn(scale.getSpaceResolution()) + ", " // "   space_resolution VARCHAR(48),\n"
            + cn(stat.getObservationName()) + ", "  // context_name VARCHAR(512),
            + status(stat.getStatus()) + ", " // "   outcome status\n"
            + boundingBox(scale) // "   geom \n"
			+ ");";
			
			execute(sql);
		}
		
		String dataflowId = null;
		if (stat.getDataflow() != null) {
			// TODO store dataflow and establish dataflow ID
		}
		
		String sql = "INSERT INTO queries VALUES ("
			    + nextQueryId + ", " // "   id INTEGER NOT NULL,\n"
				+ cn(stat.getContextId()) + ", " // "   context_id CHAR(64) NOT NULL,\n"
				+ cn(stat.getObservable()) + ", " // "   observable VARCHAR(512),\n"
				+ cn(StringUtil.join(stat.getScenarios(), ",")) + ", " // "   scenarios VARCHAR(2048),\n"
				+ cnan(stat.getDurationSeconds()) + ", " // "   total_time_sec FLOAT,\n"
				+ stat.getDataflowComplexity() + ", " // "   dataflow_complexity BIGINT,\n"
				+ cnan(stat.getResolvedCoverage()) + ", " // "   resolved_coverage FLOAT,\n"
				+ status(stat.getStatus()) + ", " // "   outcome status,\n"
				+ cn(dataflowId) + "," // "   dataflow_id VARCHAR(512),\n"              // only collected for special cases
				+ stat.getStartTime() + "," // start time
				+ (stat.isExport() ? "TRUE" : "FALSE")
				+ ");";
		
		execute(sql);

		for (ObservationAssetStatistics asset : stat.getAssets()) {
			
			ScaleStatistics scale = asset.getScaleStatistics();
			
			sql = "INSERT INTO assets VALUES ("
					+ cn(asset.getName()) + ", " // "   name VARCHAR(512),\n"
				    + nextQueryId + ", " // "   query_id INTEGER NOT NULL,\n"
					+ cn(stat.getContextId()) + ", " // "   context_id CHAR(64) NOT NULL,\n"
					+ asset(asset.getType()) + ", " // "   asset_type asset_type,\n"
					+ cnan(asset.getComputationTime()) + ", " // "   total_time_sec FLOAT,\n"
					+ asset.getSize() + ", " // "   total_byte_size BIGINT,\n"
					+ asset.getSchedules() + ", " // "   scheduled_steps BIGINT,\n"
					+ asset.getComputations() + ", " // "   total_passes INTEGER,\n"
					+ status(asset.getStatus()) + ", " // "   outcome status,\n"
					+ cn(asset.getSource()) + ", " // "   source_node_id VARCHAR(64)"
					+ boundingBox(scale) // "   geom \n"
			+ ");";
		
			execute(sql);
		}
		
//		/*
//		 * fill in the asset price table for all yet unencountered assets
//		 * NO - this is done explicitly in an update operation along with the computation of the 
//		 * unit costs.
//		 */
//		for (Pair<String, String> asset : assets) {
//			sql = "INSERT INTO asset_value VALUES ("
//				+ cn(asset.getSecond()) + ", "// "   name VARCHAR(512),\n"
//				+ cn(asset.getFirst()) + ", "   // "   asset_type asset_type,\n"
//				+ "0.0" + ", "     // "   unit_cost FLOAT,\n"
//				+ "'0'"    // "   cost_formula VARCHAR(1024),\n"
//			+ ") ON CONFLICT DO NOTHING;";
//			
//			execute(sql);
//		}
		
	}

	/* @formatter:on */

	private String cnan(double x) {
		return Double.isNaN(x) ? "'NaN'" : ("" + x);
	}

	private String boundingBox(ScaleStatistics scale) {
		if (scale == null || scale.getBboxWkt() == null) {
			return "NULL";
		}
		return "ST_GeomFromText('" + scale.getBboxWkt() + "', 4326)";
	}

	public String getGeoJSONObservations(String span, boolean polygons, boolean errors) {

		String restrictions = "";
		if (span != null && !span.isEmpty()) {
			Pair<Long, Long> s = StatsReport.parseSpan(span.split(","));
			restrictions += "AND contexts.created >= " + s.getFirst() + " AND contexts.created < " + s.getSecond();
		}
		if (errors) {
			restrictions += (restrictions.isEmpty() ? "" : " ")
					+ "AND (queries.outcome = 'Error' || queries.outcome = 'Exception')";
		} else {
			restrictions += (restrictions.isEmpty() ? "" : " ") + "AND queries.outcome = 'Success'";
		}

		String query = GEOJSON_OBSERVATION_COLLECTION.replace("{RESTRICTIONS}", restrictions);
		if (polygons) {
			query.replace("ST_Centroid(contexts.geom)", "contexts.geom");
		}

		AtomicReference<String> ret = new AtomicReference<>(null);
		scan(query, (result) -> {
			ret.set(result.get("json", String.class));
		});

		return ret.get();
	}

	private String asset(Type type) {

		switch (type) {
		case Export:
		case Model:
		case Operation:
		case ResolvedObservable:
		case Resource:
			return "'" + type.name() + "'";
		}
		return "NULL";
	}

	private String status(Status status) {

		switch (status) {
		case ABORTED:
			return "'Exception'";
		case FINISHED:
			return "'Success'";
		case INTERRUPTED:
			return "'Interrupt'";
		case STARTED:
		case WAITING:
		case CHANGED:
			return "'Error'";
		}
		return "NULL";
	}

	private String cn(String s) {
		return s == null ? "NULL" : ("'" + Escape.forSQL(s) + "'");
	}

	public StatsDatabase() {
		super("klab_stats");
	}

	@Override
	protected boolean createDatabase() {
		if (super.createDatabase()) {
			Logging.INSTANCE.info("Creating statistics database");
			for (String sql : structuralStatsStatements) {
				if (!execute(sql)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public void add(ObservationResultStatistics[] stats, String user, String groups) {
		for (ObservationResultStatistics stat : stats) {
			executor.execute(() -> {
				store(stat, user, groups);
			});
		}
	}

}
