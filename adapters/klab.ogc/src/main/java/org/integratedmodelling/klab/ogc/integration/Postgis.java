package org.integratedmodelling.klab.ogc.integration;

import java.io.File;
import java.sql.Connection;

import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.exceptions.KlabStorageException;
import org.integratedmodelling.klab.ogc.vector.files.VectorValidator;
import org.integratedmodelling.klab.raster.files.RasterValidator;

public class Postgis {

	private static String DEFAULT_POSTGRES_DATABASE = "klab";

	private String database = DEFAULT_POSTGRES_DATABASE;

	// CREATE DATABASE my_spatial_db TEMPLATE=template_postgis OR
	/*
	 * CREATE EXTENSION postgis; 
	 * -- CREATE EXTENSION fuzzystrmatch; 
	 * -- CREATE EXTENSION postgis_tiger_geocoder; 
	 * --this one is optional if you want to use the rules based standardizer (pagc_normalize_address) 
	 * -- CREATE EXTENSION address_standardizer;
	 */
	public static boolean isEnabled() {
		// TODO check for configuration and handshake
		return false;
	}

	/**
	 * Return a postgis instance tuned to the passed URN, i.e. with a database
	 * already created (either for the catalog or a single one for all tables). Only
	 * call after checking for isEnabled().
	 * 
	 * @param urn
	 */
	public static Postgis create(Urn urn) {
		return null;
	}

	/**
	 * Return the database that we have chosen for the passed URN.
	 * 
	 * @return
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * Publish a resource to a table; return the table name.
	 * 
	 * @param resource
	 * @param urn
	 * @return
	 * @throws KlabStorageException
	 */
	public String publish(File resource, Urn urn) throws KlabStorageException {

		if (new VectorValidator().canHandle(resource, null)) {
			return publishVector(resource, urn);
		} else if (new RasterValidator().canHandle(resource, null)) {
			return publishRaster(resource, urn);
		}
		throw new KlabStorageException("don't know how to publish " + resource + " in Postgis");
	}

	private String publishRaster(File resource, Urn urn) {
		// TODO Auto-generated method stub
		String ret = urn.getNamespace() + "_" + urn.getResourceId();

		return ret;
	}

	private String publishVector(File resource, Urn urn) {
		// TODO Auto-generated method stub
		String ret = urn.getNamespace() + "_" + urn.getResourceId();

		return ret;
	}

	void placeholder() {

		Connection conn;
//
//		  try { 
//		    /* 
//		    * Load the JDBC driver and establish a connection. 
//		    */
//		    Class.forName("org.postgresql.Driver"); 
//		    String url = "jdbc:postgresql://localhost:5432/database"; 
//		    conn = DriverManager.getConnection(url, "postgres", ""); 
//		    /* 
//		    * Add the geometry types to the connection. Note that you 
//		    * must cast the connection to the pgsql-specific connection 
//		    * implementation before calling the addDataType() method. 
//		    */
//		    ((org.postgresql.PGConnection)conn).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
//		    ((org.postgresql.PGConnection)conn).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));
//
//		    /* 
//		    * Create a statement and execute a select query. 
//		    */ 
//		    Statement s = conn.createStatement(); 
//		    ResultSet r = s.executeQuery("select geom,id from geomtable"); 
//		    while( r.next() ) { 
//		      /* 
//		      * Retrieve the geometry as an object then cast it to the geometry type. 
//		      * Print things out. 
//		      */ 
//		      PGgeometry geom = (PGgeometry)r.getObject(1); 
//		      int id = r.getInt(2); 
//		      System.out.println("Row " + id + ":");
//		      System.out.println(geom.toString()); 
//		    } 
//		    s.close(); 
//		    conn.close(); 
//		  } 
//		catch( Exception e ) { 
//		  e.printStackTrace(); 
//		  } 
//		} 
	}

}
