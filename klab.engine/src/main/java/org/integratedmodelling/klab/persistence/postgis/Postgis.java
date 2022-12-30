package org.integratedmodelling.klab.persistence.postgis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
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
			System.err.println(ex.getMessage());
			ok = false;
		}

		return ok;
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

//    /**
//     * Publish a resource to a table; return the table name.
//     * 
//     * @param resource
//     * @param urn
//     * @return
//     * @throws KlabStorageException
//     */
//    public PublishedResource publish(File resource, Urn urn) throws KlabStorageException {
//
//        if (new VectorValidator().canHandle(resource, null)) {
//            return publishVector(resource, urn);
//        } else if (new RasterValidator().canHandle(resource, null)) {
//            return publishRaster(resource, urn);
//        }
//        throw new KlabStorageException("don't know how to publish " + resource + " in Postgis");
//    }

//    private PublishedResource publishRaster(File resource, Urn urn) {
//        // TODO Auto-generated method stub
//        String name = urn.getNamespace() + "_" + urn.getResourceId();
//        return null;
//    }

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
                Configuration.INSTANCE.getServiceProperty("postgres", "password")); Statement st = con.createStatement()) {

            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM " + table + (where == null ? "" : (" WHERE " + where)) + ";");
            while(rs.next()) {
            	ret = rs.getLong(1);
            	break;
            }
        } catch (Throwable t) {
        	Logging.INSTANCE.error("Postgis: " + table +  ": error during COUNT execution");
        }

        return ret;
    }

//    /**
//     * Get all the shapes that compose another or are "subsumed" by it
//     * 
//     * @param urn
//     * @param envelope
//     * @return
//     */
//    public Collection<IShape> getShapesAtLowerLevel(Urn urn, IEnvelope envelope) {
//        IShape largest = getLargestInScale(urn, envelope);
//        if (largest == null) {
//            return new ArrayList<>();
//        }
//        /*
//         * FIXME must clamp level to the existing ones
//         */
//        return getShapesAtLevel(urn, envelope, largest.getMetadata().get(IMetadata.IM_MIN_SPATIAL_SCALE, Integer.class) + 1);
//    }

//    /**
//     * Get shapes in envelope at specified level, simplified as needed for the passed resolution
//     * 
//     * @param urn
//     * @param envelope
//     * @param level
//     * @return
//     */
//    public Collection<IShape> getShapesAtLevel(Urn urn, IEnvelope envelope, int level) {
//        List<IShape> ret = new ArrayList<>();
//        return ret;
//    }

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

//    /**
//     * Use {@link #getCoveringShapes(Urn, IEnvelope)} on the shape's envelope and discard any shapes
//     * whose coverage is less than a minimum proportion of the shape's area.
//     * 
//     * @param urn
//     * @param shape
//     * @param minCoverage
//     * @return
//     */
//    public List<IShape> getCoveringShapes(Urn urn, IShape shape, double minCoverage, double buffer) {
//
//        List<IShape> ret = new ArrayList<>();
//        for (IShape space : getCoveringShapes(urn, shape.getEnvelope())) {
//
//            IShape commn = space.intersection(shape);
//            double coverage = commn.getStandardizedArea() / space.getStandardizedArea();
//            if (coverage >= minCoverage) {
//                if (buffer > 0) {
//                    IMetadata metadata = space.getMetadata();
//                    space = space.buffer(buffer);
//                    space.getMetadata().putAll(metadata);
//                }
//                ret.add(space);
//            }
//        }
//        return ret;
//    }
//
//    public List<IShape> getCoveringShapes(Urn urn, IEnvelope envelope) {
//        List<IShape> ret = new ArrayList<>();
//
//        IShape shape = getLargestInScale(urn, envelope);
//
//        if (shape != null) {
//
//            int level = shape.getMetadata().get(IMetadata.IM_MIN_SPATIAL_SCALE, Integer.class);
//            double area = envelope.getHeight() * envelope.getWidth();
//            double coverage = shape.getMetadata().get(FSCANEncoder.AREA_COVERAGE_FACTOR, Double.class);
//
//            /**
//             * If shape covers the area entirely, go down one level
//             */
//
//            /*
//             * return all shapes at the given level in bounding box
//             */
//            String table = urn.getNamespace() + "_" + urn.getResourceId();
//            table = table.replaceAll("\\.", "_").toLowerCase();
//            String smTableName = table + "_sm";
//
//            String envSql = "ST_MakeEnvelope(" + envelope.getMinX() + ", " + envelope.getMinY() + ", " + envelope.getMaxX() + ", "
//                    + envelope.getMaxY() + ", 4326)";
//            String chooseShape = "SELECT gid, table_name, shape_name, ST_AsBinary(geom) as geom, level from " + smTableName + "\n" + "WHERE \n"
//                    + "   level = " + level + "  \n" + "       AND \n" + " geom && " + envSql + ";";
//
//            WKBReader wkb = new WKBReader();
//
//            System.out.println(chooseShape);
//
//            try {
//                try (Connection con = DriverManager.getConnection(this.pgurl,
//                        Configuration.INSTANCE.getServiceProperty("postgres", "user"),
//                        Configuration.INSTANCE.getServiceProperty("postgres", "password"));
//                        Statement st = con.createStatement()) {
//
//                    // add the data and compute names. We don't need the name in the main table.
//                    con.setAutoCommit(false);
//                    st.setFetchSize(50);
//                    ResultSet rs = st.executeQuery(chooseShape);
//
//                    while(rs.next()) {
//
//                        long gid = rs.getLong(1);
//                        String sourceTable = rs.getString(2);
//                        String shapeName = rs.getString(3);
//
//                        Geometry geometry = wkb.read(rs.getBytes("geom"));
//
//                        Shape sh = Shape.create(geometry, Projection.getLatLon());
//                        sh.getMetadata().put(FSCANEncoder.FEATURE_ID, gid);
//                        sh.getMetadata().put(FSCANEncoder.COLLECTION_ID, sourceTable);
//                        sh.getMetadata().put(IMetadata.IM_FEATURE_URN, urn + "#id=" + sourceTable + "." + gid);
//                        sh.getMetadata().put(IMetadata.DC_NAME, shapeName);
//                        sh.getMetadata().put(IMetadata.IM_MIN_SPATIAL_SCALE, level);
//
//                        ret.add(sh);
//                    }
//                }
//            } catch (Throwable t) {
//                Logging.INSTANCE.error(t);
//            }
//        }
//
//        return ret;
//    }

//    /**
//     * Return the simplified shape that best fills the passed envelope using the previously built
//     * indices. The shape will contain the metadata fields FEATURE_ID and COLLECTION_ID for
//     * successive retrieval of the full polygon.
//     * 
//     * @param envelope
//     * @return
//     */
//    public IShape getLargestInScale(Urn urn, IEnvelope envelope) {
//
//        String table = urn.getNamespace() + "_" + urn.getResourceId();
//        table = table.replaceAll("\\.", "_").toLowerCase();
//        // String bbTableName = table + "_bb";
//        String smTableName = table + "_sm";
//
//        int rank = envelope.getScaleRank();
//        double area = envelope.getHeight() * envelope.getWidth();
//
//        String envSql = "ST_MakeEnvelope(" + envelope.getMinX() + ", " + envelope.getMinY() + ", " + envelope.getMaxX() + ", "
//                + envelope.getMaxY() + ", 4326)";
//
//        /*
//         * this retrieves the best candidate. Should be relatively fast - adjust the simplification
//         * settings for speed vs. precision.
//         */
//        String chooseShape = "SELECT gid, table_name, shape_name, ST_AsBinary(geom) as geom, level, ABS(1 - (ST_Area(ST_Intersection(geom, " + envSql
//                + "))/" + area + ")) as ared from " + smTableName + "\n" + "WHERE \n" + "	rank BETWEEN " + (rank - 1) + " and "
//                + (rank + 2) + "  \n" + "		AND \n" + "	geom && " + envSql + "\n" + "	ORDER BY ared LIMIT 6;";
//
//        WKBReader wkb = new WKBReader();
//
//        // System.out.println(chooseShape);
//
//        try {
//            try (Connection con = DriverManager.getConnection(this.pgurl,
//                    Configuration.INSTANCE.getServiceProperty("postgres", "user"),
//                    Configuration.INSTANCE.getServiceProperty("postgres", "password")); Statement st = con.createStatement()) {
//
//                // add the data and compute names. We don't need the name in the main table.
//                con.setAutoCommit(false);
//                st.setFetchSize(50);
//                ResultSet rs = st.executeQuery(chooseShape);
//
//                while(rs.next()) {
//
//                    long gid = rs.getLong(1);
//                    String sourceTable = rs.getString(2);
//                    String shapeName = rs.getString(3);
//                    int level = rs.getInt(5);
//                    double factor = rs.getDouble(6);
//
//                    Geometry geometry = wkb.read(rs.getBytes("geom"));
//                    Shape shape = Shape.create(geometry, Projection.getLatLon());
//                    shape.getMetadata().put(FSCANEncoder.FEATURE_ID, gid);
//                    shape.getMetadata().put(FSCANEncoder.COLLECTION_ID, sourceTable);
//                    shape.getMetadata().put(IMetadata.IM_FEATURE_URN, urn + "#id=" + sourceTable + "." + gid);
//                    shape.getMetadata().put(IMetadata.DC_NAME, shapeName);
//                    shape.getMetadata().put(IMetadata.IM_MIN_SPATIAL_SCALE, level);
//                    shape.getMetadata().put(FSCANEncoder.AREA_COVERAGE_FACTOR, factor);
//                    return shape;
//
//                    // /*
//                    // * take the best candidate and retrieve the correspondent simplified shape
//                    // */
//                    // String getShape = "SELECT shape_name, geom from " + smTableName + " WHERE gid
//                    // = " + gid
//                    // + " AND table_name = '" + sourceTable + "';";
//                    //
//                    // try (Statement stt = con.createStatement()) {
//                    // ResultSet rss = stt.executeQuery(getShape);
//                    // while (rss.next()) {
//                    // PGobject thegeom = (PGobject) rss.getObject("geom");
//                    // Geometry geometry = wkb.read(WKBReader.hexToBytes(thegeom.getValue()));
//                    // Shape shape = Shape.create(geometry, Projection.getLatLon());
//                    // shape.getMetadata().put(FSCANEncoder.FEATURE_ID, gid);
//                    // shape.getMetadata().put(FSCANEncoder.COLLECTION_ID, sourceTable);
//                    // shape.getMetadata().put(IMetadata.DC_NAME, shapeName);
//                    // return shape;
//                    // }
//                    // }
//                }
//            }
//        } catch (Throwable t) {
//            Logging.INSTANCE.error(t);
//        }
//
//        return null;
//
//    }

//    /**
//     * Call this first
//     * 
//     * @param urn
//     */
//    public void resetBoundaries(Urn urn) {
//
//        String table = urn.getNamespace() + "_" + urn.getResourceId();
//        table = table.replaceAll("\\.", "_").toLowerCase();
//        // String table_boundaries = table + "_bb";
//        String table_simplified = table + "_sm";
//
//        try (Connection con = DriverManager.getConnection(this.pgurl,
//                Configuration.INSTANCE.getServiceProperty("postgres", "user"),
//                Configuration.INSTANCE.getServiceProperty("postgres", "password")); Statement st = con.createStatement()) {
//
//            for (String tablename : new String[]{ /* table_boundaries, */ table_simplified}) {
//                st.execute("DROP TABLE IF EXISTS " + tablename + ";");
//                st.execute("CREATE TABLE " + tablename
//                        + "(gid numeric(10, 0), shape_area numeric, shape_name varchar(512), table_name varchar(128), level integer, rank integer);");
//                st.execute("ALTER TABLE " + tablename + " ADD PRIMARY KEY (gid,table_name);");
//                // we will force the shape to multipolygon for the simplified shape.
//                st.execute("SELECT AddGeometryColumn('public','" + tablename + "', 'geom', 4326, '"
//                        + (tablename.endsWith("_bb") ? "POLYGON" : "MULTIPOLYGON") + "', 2, false);");
//            }
//
//        } catch (Throwable t) {
//            throw new KlabStorageException(t.getMessage());
//        }
//
//    }

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

//    public void reindexBoundaries(Urn urn) {
//
//        String table = urn.getNamespace() + "_" + urn.getResourceId();
//        table = table.replaceAll("\\.", "_").toLowerCase();
//        // String table_boundaries = table + "_bb";
//        String table_simplified = table + "_sm";
//
//        try (Connection con = DriverManager.getConnection(this.pgurl,
//                Configuration.INSTANCE.getServiceProperty("postgres", "user"),
//                Configuration.INSTANCE.getServiceProperty("postgres", "password")); Statement st = con.createStatement()) {
//            for (String tablename : new String[]{ /* table_boundaries, */ table_simplified}) {
//                st.execute("CREATE INDEX \"" + tablename + "_gist\" on \"" + tablename + "\" USING GIST (\"geom\");");
//                st.execute("COMMIT;");
//            }
//        } catch (Throwable t) {
//            throw new KlabStorageException(t.getMessage());
//        }
//    }

//    /**
//     * Only call AFTER resetBoundaries
//     * 
//     * @param file
//     * @param urn
//     * @param nameExpression
//     * @param level
//     */
//    public long indexBoundaries(File file, Urn urn, String nameExpression, int level) {
//
//        String table = urn.getNamespace() + "_" + MiscUtilities.getFileBaseName(file);
//        table = table.replaceAll("\\.", "_").toLowerCase();
//        long ret = 0;
//
//        Logging.INSTANCE.info("FSCAN ingesting raw data for " + file);
//
//        PublishedResource published = publishVector(file, table);
//
//        String restable = urn.getNamespace() + "_" + urn.getResourceId();
//        restable = restable.replaceAll("\\.", "_").toLowerCase();
//        // String table_boundaries = restable + "_bb";
//        String table_simplified = restable + "_sm";
//
//        IExpression nameCalculator = Extensions.INSTANCE.compileExpression(nameExpression,
//                Extensions.DEFAULT_EXPRESSION_LANGUAGE);
//
//        Logging.INSTANCE.info("Start FSCAN indexing of " + file);
//
//        try {
//
//            /*
//             * Bounding box and simplified coverage will contain only the feature ID of each shape,
//             * the display name, its area in projection units, and the level field for grouping. The
//             * schema is the same for both. Each shape computes its scale rank, stored along with
//             * the stated level.
//             */
//            try (Connection con = DriverManager.getConnection(this.pgurl,
//                    Configuration.INSTANCE.getServiceProperty("postgres", "user"),
//                    Configuration.INSTANCE.getServiceProperty("postgres", "password")); Statement st = con.createStatement()) {
//
//                // add the data and compute names. We don't need the name in the main table.
//                con.setAutoCommit(false);
//                st.setFetchSize(50);
//
//                WKBReader wkb = new WKBReader();
//                Projection projection = Projection.create(published.srs);
//                Parameters<String> parameters = Parameters.create();
//                SimpleRuntimeScope scope = new SimpleRuntimeScope(Klab.INSTANCE.getRootMonitor());
//
//                ResultSet rs = st.executeQuery("SELECT * FROM " + table);
//                int n = 0;
//                Statement ist = con.createStatement();
//                while(rs.next()) {
//                    ret++;
//
//                    // create bounding box and statistics
//                    // gid is fid in original feature
//                    PGobject thegeom = (PGobject) rs.getObject(published.geometryAttribute);
//                    Geometry geometry = wkb.read(WKBReader.hexToBytes(thegeom.getValue()));
//                    Shape shape = Shape.create(geometry, projection);
//                    double shape_area = geometry.getArea();
//                    IEnvelope envelope = shape.getEnvelope();
//                    int rank = envelope.getScaleRank();
//                    // Shape boundingBox = Shape.create(envelope);
//
//                    if (!shape.getJTSGeometry().isValid()) {
//                        shape = shape.fixInvalid();
//                    }
//
//                    long gid = rs.getLong("fid");
//
//                    parameters.clear();
//                    for (Attribute attribute : published.attributes) {
//                        if (!published.geometryAttribute.equals(attribute.name)) {
//                            parameters.put(attribute.name, rs.getObject(attribute.name));
//                        }
//                    }
//
//                    parameters.put("ID", "" + gid);
//                    parameters.put("LEVEL", level + "");
//
//                    Object name = null;
//                    try {
//                        name = nameCalculator.eval(scope, parameters);
//                    } catch (Throwable e) {
//                        // TODO just say it
//
//                    }
//                    if (name == null) {
//                        name = nameExpression + " " + n;
//                    }
//
//                    // TODO use sensible numbers. These work OK with the explorer in most cases, so
//                    // start here.
//                    Shape simplified = shape.simplifyIfNecessary(1000, 2000);
//
//                    // insert in BOTH tables, add table name and level. The first gets the bounding
//                    // box + area, the second the simplified shape
//                    // (with the original area and level, which we won't really use).
//                    // String sql_bb = "INSERT INTO \"" + table_boundaries + "\" VALUES (" + gid +
//                    // ", " + shape_area
//                    // + ", '" + Escape.forSQL(name.toString()) + "', '" + published.name + "', " +
//                    // level + ", "
//                    // + rank + ", ST_GeomFromText('" + boundingBox.getJTSGeometry() + "', 4326));";
//
//                    if (!simplified.getJTSGeometry().isValid()) {
//                        simplified = simplified.fixInvalid();
//                    }
//
//                    String sql_nd = "INSERT INTO \"" + table_simplified + "\" VALUES (" + gid + ", " + shape_area + ", '"
//                            + Escape.forSQL(name.toString()) + "', '" + published.name + "', " + level + ", " + rank
//                            + ", ST_MakeValid(ST_Multi(ST_GeomFromText('" + simplified.getJTSGeometry().buffer(0)
//                            + "', 4326))));";
//
//                    // ist.execute(sql_bb);
//                    ist.execute(sql_nd);
//                }
//                ist.execute("COMMIT;");
//                rs.close();
//
//                Logging.INSTANCE.info("FSCAN indexing of " + file + " completed");
//
//            }
//        } catch (Throwable t) {
//
//            Logging.INSTANCE.error("FSCAN indexing of " + file + " aborted: " + t.getMessage());
//
//            throw new KlabStorageException(t.getMessage());
//        }
//
//        return ret;
//    }

//    private PublishedResource publishVector(File resource, Urn urn) throws KlabStorageException {
//        String table = urn.getNamespace() + "_" + urn.getResourceId();
//        table = table.replaceAll("\\.", "_").toLowerCase();
//        return publishVector(resource, table);
//    }

//    private PublishedResource publishVector(File resource, String table) throws KlabStorageException {
//
//        PublishedResource ret = new PublishedResource();
//
//        ret.name = table;
//
//        try {
//
//            try (Connection con = DriverManager.getConnection(this.pgurl,
//                    Configuration.INSTANCE.getServiceProperty("postgres", "user"),
//                    Configuration.INSTANCE.getServiceProperty("postgres", "password")); Statement st = con.createStatement()) {
//                st.execute("DROP TABLE IF EXISTS " + table + ";");
//            }
//
//            //steve changed this without understanding what it could mean further down
//            Map<String, String> map = new HashMap<>();
//            map.put("url", resource.toURI().toURL().toString());
//            DataStore dataStore = DataStoreFinder.getDataStore(map);
//            if (dataStore instanceof ShapefileDataStore) {
//                // TODO use encoding from resource, init at UTF-8 unless available in original file
//                ((ShapefileDataStore) dataStore).setCharset(Charset.forName("UTF-8"));
//            }
//            String typeName = dataStore.getTypeNames()[0];
//            SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
//            SimpleFeatureCollection collection = featureSource.getFeatures();
//
//            ret.srs = Projection.create(featureSource.getSchema().getCoordinateReferenceSystem()).getSimpleSRS();
//            ret.geometryType = featureSource.getSchema().getGeometryDescriptor().getType().getName().toString();
//            ret.geometryAttribute = featureSource.getSchema().getGeometryDescriptor().getName().toString();
//            
//            for (AttributeDescriptor ad : featureSource.getSchema().getAttributeDescriptors()) {
//                PublishedResource.Attribute attribute = new Attribute();
//                attribute.name = ad.getLocalName();
//                attribute.binding = ad.getType().getBinding();
//                ret.attributes.add(attribute);
//            }
//
//            PostgisNGDataStoreFactory factory = new PostgisNGDataStoreFactory();
//            Map<String, Object> params = new HashMap<>();
//            params.put("dbtype", "postgis");
//            params.put("host", Configuration.INSTANCE.getServiceProperty("postgres", "host"));
//            params.put("port", Integer.parseInt(Configuration.INSTANCE.getServiceProperty("postgres", "port")));
//            params.put("database", this.database);
//            params.put("user", Configuration.INSTANCE.getServiceProperty("postgres", "user"));
//            params.put("passwd", Configuration.INSTANCE.getServiceProperty("postgres", "password"));
//            params.put("schema", "public");
//
//            String encoded = DataUtilities.encodeType(featureSource.getSchema());
//            SimpleFeatureType schema = DataUtilities.createType(table, encoded);
//            JDBCDataStore datastore = factory.createDataStore(params);
//            datastore.createSchema(schema);
//
//            long added = 0, errors = 0;
//
//            try (FeatureWriter<SimpleFeatureType, SimpleFeature> writer = datastore.getFeatureWriterAppend(table,
//                    Transaction.AUTO_COMMIT)) {
//                SimpleFeatureIterator it = collection.features();
//                for (; it.hasNext();) {
//                    SimpleFeature feature = it.next();
//                    SimpleFeature toWrite = writer.next();
//                    toWrite.getUserData().put(Hints.PROVIDED_FID, feature.getID());
//                    toWrite.getUserData().put(Hints.GEOMETRY_VALIDATE, Boolean.FALSE);
//                    toWrite.setAttributes(feature.getAttributes());
//                    toWrite.getUserData().putAll(feature.getUserData());
//                    try {
//                        writer.write();
//                        added++;
//                    } catch (Throwable t) {
//                        // just testing
//                        Logging.INSTANCE.error(t);
//                        errors++;
//
//                    }
//                }
//                it.close();
//            } finally {
//                Logging.INSTANCE.info("import finished with " + added + " features and " + errors + "  errors");
//                datastore.dispose();
//            }
//        } catch (Throwable t) {
//            throw new KlabStorageException(t.getMessage());
//        }
//
//        return ret;
//    }

//    public static void main(String[] args) {
//
//        if (!Postgis.isEnabled() || !Geoserver.isEnabled()) {
//            System.out.println("NOT ENABLED");
//            return;
//        }
//
//        Postgis pg = Postgis.create();
//        System.out.println("DELETING EVERYTHING - SUCA");
//        pg.clear();
//
//        // Urn urn = new Urn("im.data:spain:infrastructure:admin");
//        //
//        // // publish table in postgis
//        // Postgis postgis = Postgis.create(urn);
//        // System.out.println(postgis.isOnline() ? "OK" : "NAAH");
//        // String table = postgis.publish(new
//        // File("E:\\Dropbox\\Data\\Administrativre\\Spain\\gadm36_ESP_4.shp"), urn);
//        // System.out.println("Published table " + table);
//        //
//        // // create datastore for db (if needed) and feature type for table in Geoserver
//        // Geoserver geoserver = Geoserver.create();
//        // if (geoserver.publishPostgisVector(postgis, "klabtest", table) != null) {
//        // System.out.println("Store published to Geoserver as " + "klabtest:" + table);
//        // } else {
//        // System.out.println("Store publishing to Geoserver failed");
//        // }
//    }

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
