package org.integratedmodelling.klab.persistence.postgis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKBReader;

public class Postgis {

	private static String DEFAULT_POSTGRES_DATABASE = "klab";
	private String database = DEFAULT_POSTGRES_DATABASE;
	private String databaseUrl;
	private String pgadminUrl;
	private boolean active;

	protected Postgis(Urn urn, boolean useCatalogNames) {
		this(urn != null && useCatalogNames ? urn.getCatalog().replaceAll("\\.", "_") : "klab");
	}

	protected Postgis(String database) {

	    if (database == null) {
	        database = "klab";
	    }
	    
		this.database = database;

		this.pgadminUrl = "jdbc:postgresql://" + Configuration.INSTANCE.getServiceProperty("postgres", "host");
		this.pgadminUrl += ":" + Configuration.INSTANCE.getServiceProperty("postgres", "port");
		this.pgadminUrl += "/";
		this.databaseUrl = this.pgadminUrl + this.database;

		boolean hasDatabase = false;

		try (Connection con = DriverManager.getConnection(this.databaseUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT VERSION()")) {

			if (rs.next()) {
				hasDatabase = true;
			}

		} catch (SQLException e) {
			// no database
			Logging.INSTANCE.info("Database " + database + " does not exist: creating....");
		}

		boolean ok = hasDatabase;

		if (!ok) {
			ok = createDatabase();
		}

		this.active = ok;
	}

	protected boolean execute(String sql) {

		boolean ok = true;
		try (Connection con = DriverManager.getConnection(this.databaseUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			st.execute(sql);
			ok = true;

		} catch (SQLException ex) {
			Logging.INSTANCE.error(ex);
			ok = false;
		}

		return ok;
	}

	public long scan(String query, Consumer<Parameters<String>> handler) {

		try (Connection con = DriverManager.getConnection(this.databaseUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			ResultSet rs = st.executeQuery(query);
			long n = 0;
			while (rs.next()) {
				handler.accept(asMap(rs));
				n++;
			}
			return n;

		} catch (SQLException ex) {
			Logging.INSTANCE.error(ex.getMessage() + "\n" + query);
		}

		return -1;
	}

	private Parameters<String> asMap(ResultSet rs) {
		Parameters<String> ret = Parameters.create();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				ret.put(rsmd.getColumnName(i), rs.getObject(i));
			}
		} catch (SQLException e) {
			// f'ock
		}
		return ret;
	}

	protected boolean createDatabase() {

		boolean ok = false;

		try (Connection con = DriverManager.getConnection(this.pgadminUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {
			st.execute("CREATE DATABASE " + this.database + " ENCODING 'UTF-8';");
			ok = true;

		} catch (SQLException ex) {
			Logging.INSTANCE.error(ex);
			ok = false;
		}
		if (ok) {
			try (Connection con = DriverManager.getConnection(this.databaseUrl,
					Configuration.INSTANCE.getServiceProperty("postgres", "user"),
					Configuration.INSTANCE.getServiceProperty("postgres", "password"));
					Statement st = con.createStatement()) {

				st.execute("CREATE EXTENSION postgis;");
				st.execute("CREATE EXTENSION postgis_topology;");
				st.execute("CREATE EXTENSION fuzzystrmatch;");
//				st.execute("CREATE EXTENSION pointcloud;");
//				st.execute("CREATE EXTENSION pointcloud_postgis;");
				st.execute("CREATE EXTENSION pgrouting;");
				st.execute("CREATE EXTENSION postgis_raster;");
				st.execute("CREATE EXTENSION postgis_sfcgal;");
//				st.execute("CREATE EXTENSION postgis_tiger_geocoder;");
				st.execute("CREATE EXTENSION ogr_fdw;");
//				st.execute("CREATE EXTENSION address_standardizer;");

			} catch (SQLException ex) {
				Logging.INSTANCE.error(ex);
				removeDatabase();
				ok = false;
			}
		}

		return ok;
	}

	public boolean isOnline() {
		return this.active;
	}

	/**
	 * Doesn't mean it's online, it means there is enough configuration for it to
	 * try and get online.
	 * 
	 * @return true if DB operations can be attempted.
	 */
	public static boolean isEnabled() {
		return Configuration.INSTANCE.getServiceProperty("postgres", "host") != null
				&& Configuration.INSTANCE.getServiceProperty("postgres", "port") != null
				&& Configuration.INSTANCE.getServiceProperty("postgres", "user") != null
				&& Configuration.INSTANCE.getServiceProperty("postgres", "password") != null;
	}

	/**
	 * Return a postgis instance tuned to the passed URN, i.e. with a database
	 * already created (either for the catalog or a single one for all tables). Only
	 * call after checking for isEnabled().
	 * 
	 * @param urn
	 */
	public static Postgis create(Urn urn) {
		return new Postgis(urn, false);
	}

	/**
	 * Return a postgis instance tuned to the passed database name, i.e. with a
	 * database already created (either for the catalog or a single one for all
	 * tables). Only call after checking for isEnabled().
	 * 
	 * @param urn
	 */
	public static Postgis create(String databaseId) {
		return new Postgis(databaseId);
	}

	/**
	 * Create an instance of postgis connected to the default 'klab' database.
	 * 
	 * @return
	 */
	public static Postgis create() {
		return new Postgis(null);
	}

	/**
	 * Return the database that we have chosen for the passed URN.
	 * 
	 * @return
	 */
	public String getDatabase() {
		return database;
	}

	public static class PublishedResource {

		public static class Attribute {
			public String name;
			Class<?> binding;
		}

		/**
		 * Name of table or coverage
		 */
		public String name;
		/**
		 * Projection in EPSG:xxxx format
		 */
		public String srs;

		/**
		 * Attributes with their Java binding
		 */
		public List<Attribute> attributes = new ArrayList<>();

		/**
		 * The geometry type in the resource
		 */
		public String geometryType;

		public String geometryAttribute;
	}

	/**
	 * Report on the number of stored shapes associated with this resource.
	 * 
	 * @param urn
	 * @return
	 */
	public Map<String, Object> describeContents(Urn urn) {

		Map<String, Object> ret = new LinkedHashMap<>();
		String table = urn.getNamespace() + "_" + urn.getResourceId();
		table = table.replaceAll("\\.", "_").toLowerCase();
		String smTableName = table + "_sm";

		ret.put("simplified_table_name", smTableName);

		try (Connection con = DriverManager.getConnection(this.databaseUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			ResultSet rs = st.executeQuery("SELECT COUNT('gid'), level FROM " + smTableName + " GROUP BY level");
			long n = 0;
			while (rs.next()) {
				ret.put("simplified_count_level_" + rs.getObject(2), rs.getLong(1));
				n += rs.getLong(1);
			}
			ret.put("total_shapes", n);

		} catch (Throwable t) {
			ret.put("error during query", ExceptionUtils.getStackTrace(t));
		}

		return ret;
	}

	public long count(String table, String where) {

		long ret = 0;
		try (Connection con = DriverManager.getConnection(this.databaseUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			ResultSet rs = st
					.executeQuery("SELECT COUNT(*) FROM " + table + (where == null ? "" : (" WHERE " + where)) + ";");
			while (rs.next()) {
				ret = rs.getLong(1);
				break;
			}
		} catch (Throwable t) {
			Logging.INSTANCE.error("Postgis: " + table + ": error during COUNT execution");
		}

		return ret;
	}

	/**
	 * Get the full shape
	 * 
	 * @param tableName
	 * @param featureId
	 * @return
	 */
	public IShape getShape(String tableName, long featureId) {

		try (Connection con = DriverManager.getConnection(this.databaseUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			// add the data and compute names. We don't need the name in the main table.
			con.setAutoCommit(false);
			st.setFetchSize(50);
			ResultSet rs = st.executeQuery("SELECT fid, ST_AsBinary(the_geom) as the_geom FROM \"" + tableName
					+ "\" WHERE fid = " + featureId + ";");

			if (rs.next()) {

				WKBReader wkb = new WKBReader();

				Geometry geometry = wkb.read(rs.getBytes("the_geom"));
				return Shape.create(geometry, Projection.getLatLon());
			}

		} catch (Throwable t) {
			Logging.INSTANCE.error(t);
		}

		return null;
	}

	public void removeFeatures(Urn urn) {

		String table = urn.getNamespace() + "_" + urn.getResourceId();
		table = table.replaceAll("\\.", "_").toLowerCase();
		// String table_boundaries = table + "_bb";
		String table_simplified = table + "_sm";

		try (Connection con = DriverManager.getConnection(this.databaseUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			// remove all individual pieces
			List<String> tables = new ArrayList<>();
			ResultSet rs = st.executeQuery("SELECT DISTINCT table_name from  \"" + table_simplified + "\";");
			while (rs.next()) {
				tables.add(rs.getString(1));
			}

			for (String t : tables) {
				st.execute("DROP TABLE IF EXISTS \"" + t + "\";");
			}

			for (String tablename : new String[] { /* table_boundaries, */ table_simplified }) {
				st.execute("DROP TABLE IF EXIST \"" + tablename + ";");
				st.execute("COMMIT;");
			}
		} catch (Throwable t) {
			throw new KlabStorageException(t.getMessage());
		}
	}

	public String getPort() {
		return Configuration.INSTANCE.getServiceProperty("postgres", "port");
	}

	public String getHost() {
		return Configuration.INSTANCE.getServiceProperty("postgres", "host");
	}

	public String getUsername() {
		return Configuration.INSTANCE.getServiceProperty("postgres", "user");
	}

	public String getPassword() {
		return Configuration.INSTANCE.getServiceProperty("postgres", "password");
	}

	public String getUrl() {
		return this.databaseUrl;
	}

	public boolean removeDatabase() {
		boolean ok = false;
		try (Connection con = DriverManager.getConnection(this.pgadminUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			st.execute("DROP DATABASE " + this.database + ";");

			ok = true;

		} catch (SQLException ex) {
			Logging.INSTANCE.error(ex);
		}
		return ok;
	}

	public boolean clearDatabase() {
		boolean ok = false;
		try (Connection con = DriverManager.getConnection(this.pgadminUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			st.execute("DROP DATABASE " + this.database + ";");

			ok = true;

		} catch (SQLException ex) {
			Logging.INSTANCE.error(ex);
		}
		if (ok) {
			createDatabase();
		}
		return ok;
	}

	/**
	 * Same as clearDatabase but also disconnect any users and terminate backend.
	 * 
	 * @return
	 */
	public boolean clear() {

		boolean ok = false;
		try (Connection con = DriverManager.getConnection(this.pgadminUrl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			/**
			 * Force disconnection of any user
			 */
			st.execute("UPDATE pg_database SET datallowconn = 'false' WHERE datname = '" + this.database + "';");
			st.execute(
					"SELECT pg_terminate_backend(pid) FROM pg_stat_activity WHERE datname = '" + this.database + "';");
			st.execute("DROP DATABASE " + this.database + ";");

			ok = true;

		} catch (SQLException ex) {
			Logging.INSTANCE.error(ex);
		}
		if (ok) {
			createDatabase();
		}

		return ok;
	}

}
