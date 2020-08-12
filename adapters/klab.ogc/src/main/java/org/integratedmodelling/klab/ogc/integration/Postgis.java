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
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.ogc.integration.Postgis.PublishedResource.Attribute;
import org.integratedmodelling.klab.ogc.vector.files.VectorValidator;
import org.integratedmodelling.klab.raster.files.RasterValidator;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.AttributeDescriptor;
import org.postgresql.util.PSQLException;

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

	private PublishedResource publishVector(File resource, Urn urn) throws KlabStorageException {

		String table = urn.getNamespace() + "_" + urn.getResourceId();
		table = table.replaceAll("\\.", "_").toLowerCase();
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

				for (SimpleFeatureIterator it = collection.features(); it.hasNext();) {
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
		System.out.println("DELETING EVERYTHING - CIÖCIA LÉ");
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
