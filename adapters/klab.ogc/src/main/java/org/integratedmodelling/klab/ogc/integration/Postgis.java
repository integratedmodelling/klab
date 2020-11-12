package org.integratedmodelling.klab.ogc.integration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.Hints;
import org.geotools.jdbc.JDBCDataStore;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.engine.runtime.SimpleRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.ogc.fscan.FSCANEncoder;
import org.integratedmodelling.klab.ogc.integration.Postgis.PublishedResource.Attribute;
import org.integratedmodelling.klab.ogc.vector.files.VectorValidator;
import org.integratedmodelling.klab.raster.files.RasterValidator;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Parameters;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.postgresql.util.PGobject;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.WKBReader;

public class Postgis {

	private static String DEFAULT_POSTGRES_DATABASE = "klab";
	private boolean useCatalogNames = false;
	private String database = DEFAULT_POSTGRES_DATABASE;
	private String pgurl;
	private boolean active;

	private Postgis(Urn urn) {

		if (urn != null && useCatalogNames) {
			this.database = urn.getCatalog().replaceAll("\\.", "_");
		}

		this.pgurl = "jdbc:postgresql://" + Configuration.INSTANCE.getServiceProperty("postgres", "host");
		this.pgurl += ":" + Configuration.INSTANCE.getServiceProperty("postgres", "port");
		this.pgurl += "/" + this.database;

		boolean hasDatabase = false;

		try (Connection con = DriverManager.getConnection(this.pgurl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT VERSION()")) {

			if (rs.next()) {
				hasDatabase = true;
			}

		} catch (SQLException e) {
			// no database
		}

		boolean ok = hasDatabase;

		if (!ok) {
			ok = createDatabase();
		}

		this.active = ok;
	}

	private boolean createDatabase() {

		boolean ok = false;

		String gurl = "jdbc:postgresql://" + Configuration.INSTANCE.getServiceProperty("postgres", "host");
		if (Configuration.INSTANCE.getServiceProperty("postgres", "port") != null) {
			gurl += ":" + Configuration.INSTANCE.getServiceProperty("postgres", "port") + "/";
		}

		try (Connection con = DriverManager.getConnection(gurl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			st.execute("CREATE DATABASE " + this.database + ";");
			ok = true;

		} catch (SQLException ex) {
			ok = false;
		}
		if (ok) {
			try (Connection con = DriverManager.getConnection(this.pgurl,
					Configuration.INSTANCE.getServiceProperty("postgres", "user"),
					Configuration.INSTANCE.getServiceProperty("postgres", "password"));
					Statement st = con.createStatement()) {

				st.execute("CREATE EXTENSION postgis;");
				st.execute("CREATE EXTENSION postgis_topology;");
				st.execute("CREATE EXTENSION fuzzystrmatch;");
				st.execute("CREATE EXTENSION pointcloud;");
				st.execute("CREATE EXTENSION pointcloud_postgis;");
				st.execute("CREATE EXTENSION pgrouting;");
				st.execute("CREATE EXTENSION postgis_raster;");
				st.execute("CREATE EXTENSION postgis_sfcgal;");
				st.execute("CREATE EXTENSION postgis_tiger_geocoder;");
				st.execute("CREATE EXTENSION ogr_fdw;");
				st.execute("CREATE EXTENSION address_standardizer;");

			} catch (SQLException ex) {
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
		return new Postgis(urn);
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
	}

	/**
	 * Publish a resource to a table; return the table name.
	 * 
	 * @param resource
	 * @param urn
	 * @return
	 * @throws KlabStorageException
	 */
	public PublishedResource publish(File resource, Urn urn) throws KlabStorageException {

		if (new VectorValidator().canHandle(resource, null)) {
			return publishVector(resource, urn);
		} else if (new RasterValidator().canHandle(resource, null)) {
			return publishRaster(resource, urn);
		}
		throw new KlabStorageException("don't know how to publish " + resource + " in Postgis");
	}

	private PublishedResource publishRaster(File resource, Urn urn) {
		// TODO Auto-generated method stub
		String name = urn.getNamespace() + "_" + urn.getResourceId();

		return null;
	}

	/**
	 * Return the simplified shape that best fills the passed envelope using the
	 * previously built indices. The shape will contain the metadata fields
	 * FEATURE_ID and COLLECTION_ID for successive retrieval of the full polygon.
	 * 
	 * @param envelope
	 * @return
	 */
	public IShape getLargestInScale(Urn urn, IEnvelope envelope) {

		String table = urn.getNamespace() + "_" + urn.getResourceId();
		table = table.replaceAll("\\.", "_").toLowerCase();
//		String bbTableName = table + "_bb";
		String smTableName = table + "_sm";

		int rank = envelope.getScaleRank();
		double area = envelope.getHeight() * envelope.getWidth();

		String envSql = "ST_MakeEnvelope(" + envelope.getMinX() + ", " + envelope.getMinY() + ", " + envelope.getMaxX()
				+ ", " + envelope.getMaxY() + ", 4326)";

		/*
		 * this retrieves the best candidate. Should be relatively fast - adjust the
		 * simplification settings for speed vs. precision.
		 */
		String chooseShape = "SELECT gid, table_name, shape_name, geom, level, ABS(1 - (ST_Area(ST_Intersection(geom, "
				+ envSql + "))/" + area + ")) as ared from " + smTableName + "\n" + "WHERE \n" + "	rank BETWEEN "
				+ (rank - 1) + " and " + (rank + 2) + "  \n" + "		AND \n" + "	geom && " + envSql + "\n"
				+ "	ORDER BY ared LIMIT 6;";

		WKBReader wkb = new WKBReader();

		System.out.println(chooseShape);
		
		try {
			try (Connection con = DriverManager.getConnection(this.pgurl,
					Configuration.INSTANCE.getServiceProperty("postgres", "user"),
					Configuration.INSTANCE.getServiceProperty("postgres", "password"));
					Statement st = con.createStatement()) {

				// add the data and compute names. We don't need the name in the main table.
				con.setAutoCommit(false);
				st.setFetchSize(50);
				ResultSet rs = st.executeQuery(chooseShape);

				while (rs.next()) {

					long gid = rs.getLong(1);
					String sourceTable = rs.getString(2);
					String shapeName = rs.getString(3);
					int level = rs.getInt(5);
					
					PGobject thegeom = (PGobject) rs.getObject(4);
					Geometry geometry = wkb.read(WKBReader.hexToBytes(thegeom.getValue()));
					Shape shape = Shape.create(geometry, Projection.getLatLon());
					shape.getMetadata().put(FSCANEncoder.FEATURE_ID, gid);
					shape.getMetadata().put(FSCANEncoder.COLLECTION_ID, sourceTable);
					shape.getMetadata().put(IMetadata.DC_NAME, shapeName);
					shape.getMetadata().put(IMetadata.IM_MIN_SPATIAL_SCALE, level);
					return shape;

//					/*
//					 * take the best candidate and retrieve the correspondent simplified shape
//					 */
//					String getShape = "SELECT shape_name, geom from " + smTableName + " WHERE gid = " + gid
//							+ " AND table_name = '" + sourceTable + "';";
//
//					try (Statement stt = con.createStatement()) {
//						ResultSet rss = stt.executeQuery(getShape);
//						while (rss.next()) {
//							PGobject thegeom = (PGobject) rss.getObject("geom");
//							Geometry geometry = wkb.read(WKBReader.hexToBytes(thegeom.getValue()));
//							Shape shape = Shape.create(geometry, Projection.getLatLon());
//							shape.getMetadata().put(FSCANEncoder.FEATURE_ID, gid);
//							shape.getMetadata().put(FSCANEncoder.COLLECTION_ID, sourceTable);
//							shape.getMetadata().put(IMetadata.DC_NAME, shapeName);
//							return shape;
//						}
//					}
				}
			}
		} catch (Throwable t) {
			Logging.INSTANCE.error(t);
		}

		return null;

	}

	/**
	 * Call this first
	 * 
	 * @param urn
	 */
	public void resetBoundaries(Urn urn) {

		String table = urn.getNamespace() + "_" + urn.getResourceId();
		table = table.replaceAll("\\.", "_").toLowerCase();
//		String table_boundaries = table + "_bb";
		String table_simplified = table + "_sm";

		try (Connection con = DriverManager.getConnection(this.pgurl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {

			for (String tablename : new String[] { /* table_boundaries, */ table_simplified }) {
				st.execute("DROP TABLE IF EXISTS " + tablename + ";");
				st.execute("CREATE TABLE " + tablename
						+ "(gid numeric(10, 0), shape_area numeric, shape_name varchar(512), table_name varchar(128), level integer, rank integer);");
				st.execute("ALTER TABLE " + tablename + " ADD PRIMARY KEY (gid,table_name);");
				// we will force the shape to multipolygon for the simplified shape.
				st.execute("SELECT AddGeometryColumn('public','" + tablename + "', 'geom', 4326, '"
						+ (tablename.endsWith("_bb") ? "POLYGON" : "MULTIPOLYGON") + "', 2, false);");
			}

		} catch (Throwable t) {
			throw new KlabStorageException(t.getMessage());
		}

	}

	public void reindexBoundaries(Urn urn) {

		String table = urn.getNamespace() + "_" + urn.getResourceId();
		table = table.replaceAll("\\.", "_").toLowerCase();
//		String table_boundaries = table + "_bb";
		String table_simplified = table + "_sm";

		try (Connection con = DriverManager.getConnection(this.pgurl,
				Configuration.INSTANCE.getServiceProperty("postgres", "user"),
				Configuration.INSTANCE.getServiceProperty("postgres", "password"));
				Statement st = con.createStatement()) {
			for (String tablename : new String[] { /* table_boundaries, */ table_simplified }) {
				st.execute("CREATE INDEX \"" + tablename + "_gist\" on \"" + tablename + "\" USING GIST (\"geom\");");
				st.execute("COMMIT;");
			}
		} catch (Throwable t) {
			throw new KlabStorageException(t.getMessage());
		}
	}

	/**
	 * Only call AFTER resetBoundaries
	 * 
	 * @param file
	 * @param urn
	 * @param nameExpression
	 * @param level
	 */
	public long indexBoundaries(File file, Urn urn, String nameExpression, int level) {

		String table = urn.getNamespace() + "_" + MiscUtilities.getFileBaseName(file);
		table = table.replaceAll("\\.", "_").toLowerCase();
		long ret = 0;

		Logging.INSTANCE.info("FSCAN ingesting raw data for " + file);

		PublishedResource published = publishVector(file, table);

		String restable = urn.getNamespace() + "_" + urn.getResourceId();
		restable = restable.replaceAll("\\.", "_").toLowerCase();
//		String table_boundaries = restable + "_bb";
		String table_simplified = restable + "_sm";

		IExpression nameCalculator = Extensions.INSTANCE.compileExpression(nameExpression,
				Extensions.DEFAULT_EXPRESSION_LANGUAGE);

		Logging.INSTANCE.info("Start FSCAN indexing of " + file);

		try {

			/*
			 * Bounding box and simplified coverage will contain only the feature ID of each
			 * shape, the display name, its area in projection units, and the level field
			 * for grouping. The schema is the same for both. Each shape computes its scale
			 * rank, stored along with the stated level.
			 */
			try (Connection con = DriverManager.getConnection(this.pgurl,
					Configuration.INSTANCE.getServiceProperty("postgres", "user"),
					Configuration.INSTANCE.getServiceProperty("postgres", "password"));
					Statement st = con.createStatement()) {

				// add the data and compute names. We don't need the name in the main table.
				con.setAutoCommit(false);
				st.setFetchSize(50);

				WKBReader wkb = new WKBReader();
				Projection projection = Projection.create(published.srs);
				Parameters<String> parameters = Parameters.create();
				SimpleRuntimeScope scope = new SimpleRuntimeScope(Klab.INSTANCE.getRootMonitor());

				ResultSet rs = st.executeQuery("SELECT * FROM " + table);
				int n = 0;
				Statement ist = con.createStatement();
				while (rs.next()) {
					ret++;

					// create bounding box and statistics
					// gid is fid in original feature
					PGobject thegeom = (PGobject) rs.getObject("the_geom");
					Geometry geometry = wkb.read(WKBReader.hexToBytes(thegeom.getValue()));
					Shape shape = Shape.create(geometry, projection);
					double shape_area = geometry.getArea();
					IEnvelope envelope = shape.getEnvelope();
					int rank = envelope.getScaleRank();
//					Shape boundingBox = Shape.create(envelope);

					if (!shape.getJTSGeometry().isValid()) {
						continue;
					}
					
					long gid = rs.getLong("fid");

					parameters.clear();
					for (Attribute attribute : published.attributes) {
						if (!"the_geom".equals(attribute.name)) {
							parameters.put(attribute.name, rs.getObject(attribute.name));
						}
					}
					
					parameters.put("ID", "" + gid);
					parameters.put("LEVEL", level + "");
					
					Object name = null;
					try {
						name = nameCalculator.eval(parameters, scope);
					} catch (Throwable e) {
						// TODO just say it

					}
					if (name == null) {
						name = nameExpression + " " + n;
					}

					// TODO use sensible numbers. These work OK with the explorer in most cases, so
					// start here.
					Shape simplified = shape.simplifyIfNecessary(1000, 2000);

					// insert in BOTH tables, add table name and level. The first gets the bounding
					// box + area, the second the simplified shape
					// (with the original area and level, which we won't really use).
//					String sql_bb = "INSERT INTO \"" + table_boundaries + "\" VALUES (" + gid + ", " + shape_area
//							+ ", '" + Escape.forSQL(name.toString()) + "', '" + published.name + "', " + level + ", "
//							+ rank + ", ST_GeomFromText('" + boundingBox.getJTSGeometry() + "', 4326));";

					if (!simplified.getJTSGeometry().isValid()) {
						continue;
					}
					
					String sql_nd = "INSERT INTO \"" + table_simplified + "\" VALUES (" + gid + ", " + shape_area
							+ ", '" + Escape.forSQL(name.toString()) + "', '" + published.name + "', " + level + ", "
							+ rank + ", ST_MakeValid(ST_Multi(ST_GeomFromText('" + simplified.getJTSGeometry().buffer(0) + "', 4326))));";

//					ist.execute(sql_bb);
					ist.execute(sql_nd);
				}
				ist.execute("COMMIT;");
				rs.close();

				Logging.INSTANCE.info("FSCAN indexing of " + file + " completed");

			}
		} catch (Throwable t) {

			Logging.INSTANCE.error("FSCAN indexing of " + file + " aborted: " + t.getMessage());

			throw new KlabStorageException(t.getMessage());
		}

		return ret;
	}

	private PublishedResource publishVector(File resource, Urn urn) throws KlabStorageException {
		String table = urn.getNamespace() + "_" + urn.getResourceId();
		table = table.replaceAll("\\.", "_").toLowerCase();
		return publishVector(resource, table);
	}

	private PublishedResource publishVector(File resource, String table) throws KlabStorageException {

		PublishedResource ret = new PublishedResource();

		ret.name = table;

		try {

			try (Connection con = DriverManager.getConnection(this.pgurl,
					Configuration.INSTANCE.getServiceProperty("postgres", "user"),
					Configuration.INSTANCE.getServiceProperty("postgres", "password"));
					Statement st = con.createStatement()) {
				st.execute("DROP TABLE IF EXISTS " + table + ";");
			}

			Map<String, Object> map = new HashMap<>();
			map.put("url", resource.toURI().toURL().toString());
			DataStore dataStore = DataStoreFinder.getDataStore(map);
			String typeName = dataStore.getTypeNames()[0];
			SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
			SimpleFeatureCollection collection = featureSource.getFeatures();

			ret.srs = Projection.create(featureSource.getSchema().getCoordinateReferenceSystem()).getSimpleSRS();
			ret.geometryType = featureSource.getSchema().getGeometryDescriptor().getType().getName().toString();

			for (AttributeDescriptor ad : featureSource.getSchema().getAttributeDescriptors()) {
				PublishedResource.Attribute attribute = new Attribute();
				attribute.name = ad.getLocalName();
				attribute.binding = ad.getType().getBinding();
				ret.attributes.add(attribute);
			}

			PostgisNGDataStoreFactory factory = new PostgisNGDataStoreFactory();
			Map<String, Object> params = new HashMap<>();
			params.put("dbtype", "postgis");
			params.put("host", Configuration.INSTANCE.getServiceProperty("postgres", "host"));
			params.put("port", Integer.parseInt(Configuration.INSTANCE.getServiceProperty("postgres", "port")));
			params.put("database", this.database);
			params.put("user", Configuration.INSTANCE.getServiceProperty("postgres", "user"));
			params.put("passwd", Configuration.INSTANCE.getServiceProperty("postgres", "password"));
			params.put("schema", "public");

			String encoded = DataUtilities.encodeType(featureSource.getSchema());
			SimpleFeatureType schema = DataUtilities.createType(table, encoded);
			JDBCDataStore datastore = factory.createDataStore(params);
			datastore.createSchema(schema);

			long added = 0, errors = 0;

			try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer = datastore.getFeatureWriterAppend(table,
					Transaction.AUTO_COMMIT)) {
				SimpleFeatureIterator it = collection.features();
				for (; it.hasNext();) {
					SimpleFeature feature = it.next();
					SimpleFeature toWrite = writer.next();
					toWrite.setAttributes(feature.getAttributes());
					toWrite.getUserData().put(Hints.PROVIDED_FID, feature.getID());
					toWrite.getUserData().putAll(feature.getUserData());
					try {
						writer.write();
						added++;
					} catch (Throwable t) {
						// just testing
						if (errors == 0) {
							Logging.INSTANCE.error(t);
						}
						errors++;

					}
				}
				it.close();
			} finally {
				Logging.INSTANCE.info("import finished with " + added + " features and " + errors + "  errors");
				datastore.dispose();
			}
		} catch (Throwable t) {
			throw new KlabStorageException(t.getMessage());
		}

		return ret;
	}

	public static void main(String[] args) {

		if (!Postgis.isEnabled() || !Geoserver.isEnabled()) {
			System.out.println("NOT ENABLED");
			return;
		}

		Postgis pg = Postgis.create();
		System.out.println("DELETING EVERYTHING - SUCA");
		pg.clear();

//		Urn urn = new Urn("im.data:spain:infrastructure:admin");
//
//		// publish table in postgis
//		Postgis postgis = Postgis.create(urn);
//		System.out.println(postgis.isOnline() ? "OK" : "NAAH");
//		String table = postgis.publish(new File("E:\\Dropbox\\Data\\Administrativre\\Spain\\gadm36_ESP_4.shp"), urn);
//		System.out.println("Published table " + table);
//
//		// create datastore for db (if needed) and feature type for table in Geoserver
//		Geoserver geoserver = Geoserver.create();
//		if (geoserver.publishPostgisVector(postgis, "klabtest", table) != null) {
//			System.out.println("Store published to Geoserver as " + "klabtest:" + table);
//		} else {
//			System.out.println("Store publishing to Geoserver failed");
//		}
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

	public boolean clear() {

		boolean ok = false;
		String gurl = "jdbc:postgresql://" + Configuration.INSTANCE.getServiceProperty("postgres", "host");
		if (Configuration.INSTANCE.getServiceProperty("postgres", "port") != null) {
			gurl += ":" + Configuration.INSTANCE.getServiceProperty("postgres", "port") + "/";
		}

		try (Connection con = DriverManager.getConnection(gurl,
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
