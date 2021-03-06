/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.weather.data;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.DataType;
import org.integratedmodelling.klab.api.data.general.IPersistentTable;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.table.persistent.PersistentTable;
import org.integratedmodelling.klab.data.table.persistent.PersistentTable.PersistentTableBuilder;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.FixedReader;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.URLUtils;
import org.integratedmodelling.klab.utils.UntarUtils;
import org.integratedmodelling.klab.utils.ZipUtils;
import org.integratedmodelling.weather.WeatherComponent;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import com.google.common.io.Files;
import com.ibm.icu.util.Calendar;

public enum WeatherFactory {

	INSTANCE;

	// MapDB-backed storage
	static DB db;
	static Map<String, double[]> dataMap;
	// stations with read data (may not be up to date)
	static Set<String> stationWithData;
	// all stations in database, even with no data yet read
	static Set<String> stationIds;
	static Map<String, Integer> nansMap;
	static Map<String, Long> datasizeMap;

	public static String[] ghcnd_archive_urls = new String[] {
			"https://www1.ncdc.noaa.gov/pub/data/ghcn/daily/ghcnd_all.tar.gz" };

	public static String[] cru_archive_urls = new String[] { "http://www.integratedmodelling.org/downloads/crugz.zip" };

	public static String[] GHCN_URLS = { "https://www1.ncdc.noaa.gov/pub/data/ghcn/daily" };

	private CRUReader cruReader;

	private final static String GHNCD_LAST_UPDATE_PROPERTY = "ghncd.catalog.update";

	/**
	 * If set in org.integratedmodelling.weather.properties, this points to the
	 * location of an uncompressed and unpacked ghncd_all.tar.gz, which will be used
	 * only during the initialization of the database.
	 */
	public final static String GHNCD_CATALOG_LOCATION = "ghncd.catalog.location";
	public final static String CRU_CATALOG_LOCATION = "cru.catalog.location";

	/*
	 * the DB
	 */
	PersistentTable<String, WeatherStation> wbox;

	private WeatherFactory() {
		wbox = new PersistentTableBuilder<String, WeatherStation>("weatherstations", String.class, WeatherStation.class)
				.column("id", DataType.TEXT, 32).column("elevation", DataType.DOUBLE)
				.column("latitude", DataType.DOUBLE).column("longitude", DataType.DOUBLE)
				.column("provided_vars", DataType.TEXT, 1024).column("provided_start", DataType.TEXT, 1024)
				.column("provided_end", DataType.TEXT, 1024).column("location", DataType.SHAPE, true)
				.build((station) -> {
					return station.asData();
				}, (data) -> {
					return new WeatherStation(data);
				}, (station) -> {
					return station.getId();
				});
		checkStorage();
	}

	public static void checkStorage() {

		if (db == null) {

			File dpath = Configuration.INSTANCE.getDataPath(WeatherComponent.ID);
			dpath.mkdirs();

			/**
			 * Use transactions. Memory mapping for now disabled as f'ing Win makes it
			 * almost impossible to use correctly.
			 */
			db = DBMaker.fileDB(new File(dpath + File.separator + "stationdata.dat")).transactionEnable()
					/* .fileMmapEnable() */.closeOnJvmShutdown().make();

			dataMap = db.treeMap("datamap", Serializer.STRING, Serializer.DOUBLE_ARRAY).createOrOpen();
			nansMap = db.treeMap("nansmap", Serializer.STRING, Serializer.INTEGER).createOrOpen();
			stationWithData = db.treeSet("stationset", Serializer.STRING).createOrOpen();
			stationIds = db.treeSet("stationids", Serializer.STRING).createOrOpen();
			datasizeMap = db.treeMap("timestamps", Serializer.STRING, Serializer.LONG).createOrOpen();
		}
	}

	/**
	 * Try getting the events file and putting it somewhere so we don't need to set
	 * up from the outside.
	 * 
	 * @return
	 */
	public static boolean retrieveEventDatabase() {

		boolean ret = false;
		for (String url : ghcnd_archive_urls) {
			try {
				File output = File.createTempFile("ghcnd", ".tar.gz");
				Logging.INSTANCE.info("Attempting retrieval of ~5GB source data from " + url + " into " + output);
				Logging.INSTANCE.info("Expect several minutes of wait or more.");
				URLUtils.copyChanneled(new URL(url), output);
				// TODO uncompress and unpack
//				Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID).setProperty(TRMM_EVENTS_LOCATION,
//						output.toURI().toURL().toString());
//				Extensions.INSTANCE.saveComponentProperties(WeatherComponent.ID);
				Logging.INSTANCE.info("Data retrieval successful");
				ret = true;
				break;
			} catch (IOException e) {
			}
		}

		return ret;
	}

	/**
	 * Call with the URL (file or http) to the catalog file. Will initialize from
	 * the local fileset if the GHNCD_CATALOG_LOCATION property is set in the
	 * component's properties. Returns whether the database is being initialized or
	 * not.
	 * 
	 * @param catalogFile
	 */
	public boolean setupGHCNDStation(URL catalogFile, IMonitor monitor) {

		boolean initializing = false;

		checkStorage();

		Logging.INSTANCE.info("Setting up GHCND dataset");

		Properties properties = Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID);

		try {

			boolean update = true;

			DateTime now = new DateTime();
			if (properties.getProperty(GHNCD_LAST_UPDATE_PROPERTY) != null) {
				DateTime then = new DateTime(properties.getProperty(GHNCD_LAST_UPDATE_PROPERTY));
				if (Days.daysBetween(now, then).getDays() < 30) {
					update = false;
				}
			}

			if (!update && wbox.count() < 100000) {
				update = true;
				initializing = true;
			}

			if (update) {

//				stationIds.clear();

				Logging.INSTANCE.info("Reading GHCND inventory");

				URL fInventory = new URL(catalogFile + "/ghcnd-inventory.txt");
				URL fStations = new URL(catalogFile + "/ghcnd-stations.txt");

				File tempStations = File.createTempFile("stations", ".txt");
				File tempInventory = File.createTempFile("inventory", ".txt");

				tempStations.deleteOnExit();
				tempInventory.deleteOnExit();

				URLUtils.copyChanneled(fInventory, tempInventory);
				URLUtils.copyChanneled(fStations, tempStations);

				List<FixedReader> stations = FixedReader.parse(tempStations,
						new int[] { 0, 12, 21, 31, 38, 41, 72, 76, 80 });
				List<FixedReader> inventor = FixedReader.parse(tempInventory, new int[] { 0, 12, 21, 31, 35, 40, 45 });

				Iterator<FixedReader> inventory = inventor.iterator();
				FixedReader invLine = inventory.next();

				if (wbox.getDatabase() != null)
					wbox.getDatabase().preallocateConnection();

				/**
				 * Format of GHCN station file (ghcnd-stations.txt)
				 * 
				 * <pre>
				 * ------------------------------ 
				 * Variable Columns Type
				 * ------------------------------ 
				 * ID 1-11 Character 
				 * LATITUDE 13-20 Real
				 * LONGITUDE 22-30 Real 
				 * ELEVATION 32-37 Real 
				 * STATE 39-40 Character 
				 * NAME 42-71 Character 
				 * GSNFLAG 73-75 Character 
				 * HCNFLAG 77-79 Character 
				 * WMOID 81-85 Character 
				 * ------------------------------
				 * </pre>
				 */
				int i = 0;
				for (FixedReader fr : stations) {

					String id = fr.nextString().trim();
					double lat = fr.nextDouble();
					double lon = fr.nextDouble();
					double alt = fr.nextDouble();

					/*
					 * DO NOT REMOVE - side effects of nextString() are crucial.
					 */
					/* String state = */ fr.nextString()/* .trim() */;
					/* String name = */ fr.nextString()/* .trim() */;

					/*
					 * make a new object and store it
					 */
					WeatherStation ws = new WeatherStation(catalogFile.toString(), id, lat, lon, alt);

					try {
						/*
						 * read the available data series from the inventory.
						 */
						while (invLine.nextString().trim().equals(id)) {

							invLine.nextDouble();
							invLine.nextDouble();
							String varId = invLine.nextString();
							int firstYear = invLine.nextInt();
							int lastYear = invLine.nextInt();

							ws._provided.put(varId, new Pair<Integer, Integer>(firstYear, lastYear));

							if (inventory.hasNext()) {
								invLine = inventory.next();
							}
						}

					} catch (Exception e) {
						Logging.INSTANCE.error("bad station data line for " + ws + ": check newlines");
						continue;
					}

					wbox.store(ws, monitor);

					/*
					 * we peeked already, so reset counter for next inventory line if we have more
					 */
					if (inventory.hasNext()) {
						invLine.reset();
					}

					System.out.println(ws);

				}

				db.commit();

				properties.setProperty(GHNCD_LAST_UPDATE_PROPERTY, now.toString());
				Extensions.INSTANCE.saveComponentProperties(WeatherComponent.ID);

			} else {
				Logging.INSTANCE.info("GHCND dataset is up to date");
			}

			if (wbox.getDatabase() != null) {
				wbox.getDatabase().deallocateConnection();
			}

		} catch (Throwable e) {
			// I hate you
			throw new KlabIOException(e);
		}

		return initializing;

	}

	/**
	 * Get weather station in the spatial extent provided. Expand the bounding box
	 * by the spatial extent provided (if 0, leave it alone and use the actual
	 * geometry; if >0, use the bounding box and look as many times around in lenght
	 * and width). If variables are passed, return only stations that provide them.
	 *
	 * @param space
	 * @param expandFactor distance for buffering around shape before searching
	 * @param variables
	 * @return
	 * @throws ThinklabException
	 */
	public List<WeatherStation> around(ISpace space, double expandFactor, String source, String... variables)
			throws KlabException {

		Shape shape = (Shape) space.getShape();
		if (expandFactor > 0) {
			shape = (Shape) shape.buffer(expandFactor);
		}
		return within(shape, source, variables);
	}

	public List<WeatherStation> within(IShape context, String source, boolean expand, String... variables) {

		double EXPAND_INCREMENT = 0.15;
		double expandFactor = Math.max(context.getEnvelope().getWidth(), context.getEnvelope().getHeight())
				* EXPAND_INCREMENT;

		if (expand) {
			for (int i = 0;; i++) {
				List<WeatherStation> ret = within(context, source, variables);
				if (ret.isEmpty()) {
					if (i == 20) {
						return ret;
					}
					context = context.buffer(expandFactor);
				} else {
					return ret;
				}
			}
		}
		return within(context, source, variables);
	}

	/**
	 * Do everything possible to return at least one station with the required data.
	 * First try the user-submitted stations, then the NOAA, with a reasonable
	 * buffer range around the shape (50% in each direction). If neither gives at
	 * least one station back, do the same with CRU stations. If the regular
	 * strategy doesn't work with CRU, return the 4 closest CRU stations around the
	 * shape.
	 * 
	 * @param context
	 * @param variables
	 * @return
	 */
	public List<WeatherStation> requireWithin(IShape context, String... variables) {
		final List<WeatherStation> ret = new ArrayList<>();
		return ret;
	}

	/**
	 * Return all weather stations in the passed geometry (using the intersect
	 * operator), optionally restricting to those providing the variables passed.
	 * 
	 * @param context
	 * @param source    pass "ALL" or null for everything or a specific source type
	 *                  (currently GHCND, CRU, USER).
	 * @param variables
	 * @return
	 * @throws ThinklabException
	 */
	public List<WeatherStation> within(IShape context, String source, String... variables) throws KlabException {

		final List<WeatherStation> ret = new ArrayList<>();

		String query = "SELECT * FROM weatherstations WHERE ST_Intersects('"
				+ ((Shape) context).getStandardizedGeometry() + "', location)";
		if (source != null && !source.equals("ALL")) {
			switch (source) {
			case "CRU":
				query += " AND id LIKE 'CRU_%'";
				break;
			case "USER":
				query += " AND id LIKE 'USER_%'";
				break;
			default:
				query += " AND id NOT LIKE 'USER_%' AND id NOT LIKE 'CRU_%'";
				break;
			}
		}
		if (variables != null) {
			for (String v : variables) {
				query += " AND provided_vars LIKE '%" + v + "%'";
			}
		}
		query += ";";

		for (WeatherStation ws : wbox.query(query)) {
			ret.add(ws);
		}

		return ret;
	}

	public void setupCRUStations(File repository, IMonitor monitor) {
		checkStorage();
		this.cruReader = new CRUReader(repository);
		this.cruReader.createStations(wbox, monitor);
		db.commit();
	}

	public void setupTRRMEvents() {
		// TODO hostia
	}

	public void setupContributedStations() {
		// TODO hostia
	}

	/**
	 * Compute day length in hours based on day of the year and latitude.
	 * 
	 * @param time
	 * @param latitude
	 * @return
	 */
	public static double dayLength(Calendar time, double latitude) {

		final int[] DAYS = { 15, 45, 74, 105, 135, 166, 196, 227, 258, 288, 319, 349 };

		int month = time.get(java.util.Calendar.MONTH);

		double dayl = DAYS[month] - 80.;
		if (dayl < 0.0) {
			dayl = 285. + DAYS[month];
		}

		double decr = 23.45 * Math.sin(dayl / 365. * 6.2832) * 0.017453;
		double alat = latitude * 0.017453;
		double csh = (-0.02908 - Math.sin(decr) * Math.sin(alat)) / (Math.cos(decr) * Math.cos(alat));

		return 24.0 * (1.570796 - Math.atan(csh / Math.sqrt(1. - csh * csh))) / Math.PI;
	}

	public CRUReader getCRUReader() {
		return cruReader;
	}

	public void setup() {
		setupCRUStations();
		setupGHCNDStations();
		setupTRRMEvents();
		setupContributedStations();
		cruReader.finalizeRead();
	}

	public static void main(String[] args) throws Exception {
		INSTANCE.setupCRUStations();
	}

	public long getStationsCount() {
		return wbox.count();
	}

	public IPersistentTable<String, WeatherStation> getDatabase() {
		return wbox;
	}

	public boolean isOnline() {
		return wbox.count() > 100000;
	}

	public void setupCRUStations() {

		Properties properties = Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID);
		File cruDir = null;
		if (!properties.containsKey(CRU_CATALOG_LOCATION)) {
			cruDir = Configuration.INSTANCE.getDataPath("cru");
			for (String url : cru_archive_urls) {
				try {
					File archive = File.createTempFile("cru", ".zip");
					archive.deleteOnExit();
					Logging.INSTANCE.info("Retrieving most recent CRU data archive from integratedmodelling.org");
					URLUtils.copyChanneled(new URL(url), archive);
					ZipUtils.unzip(archive, cruDir);
					Logging.INSTANCE.info("CRU data archives locally available in " + cruDir);
					properties.setProperty(CRU_CATALOG_LOCATION, cruDir.toString());
					Extensions.INSTANCE.saveComponentProperties(WeatherComponent.ID);
					break;
				} catch (Throwable t) {
					Logging.INSTANCE.error(t);
				}
			}
		} else {
			cruDir = new File(properties.getProperty(CRU_CATALOG_LOCATION));
		}

		setupCRUStations(cruDir, null);
	}

	public void setupGHCNDStations() {

		Properties properties = Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID);
		File catalogDir = null;
		if (!properties.containsKey(GHNCD_CATALOG_LOCATION)) {
			catalogDir = Configuration.INSTANCE.getDataPath("ghncd");
			for (String url : ghcnd_archive_urls) {
				try {

					Logging.INSTANCE.info("Retrieving GHCND data from NOAA");
					String siteUrl = url.substring(0, url.lastIndexOf('/'));
					URLUtils.copyChanneled(new URL(siteUrl + "/ghcnd-inventory.txt"),
							new File(catalogDir + File.separator + "ghcnd-inventory.txt"));
					URLUtils.copyChanneled(new URL(siteUrl + "/ghcnd-stations.txt"),
							new File(catalogDir + File.separator + "ghcnd-stations.txt"));
					Logging.INSTANCE.info("Retrieved GHCND data inventory");
					File destination = File.createTempFile("ghncd", ".tar.gz");
					URLUtils.copyChanneled(new URL(url), destination);
					Logging.INSTANCE.info("Retrieved GHCND daily data archive; unpacking in klab directory");
					destination.deleteOnExit();
					File destDir = Files.createTempDir();
					destDir.deleteOnExit();
					UntarUtils.unpack(destination, destDir, catalogDir);
					properties.setProperty(GHNCD_CATALOG_LOCATION, catalogDir.toString());
					Extensions.INSTANCE.saveComponentProperties(WeatherComponent.ID);
					WeatherStation.setLocalGHCNDLocation(catalogDir);

					/**
					 * This should be timed by a scheduler and split. Commit every 50 stations for
					 * speed.
					 * 
					 * CHECK this will also try to setup the CRU stations, so they should have been
					 * generated before.
					 */

					Logging.INSTANCE.info("Building GHCND station database...");

					setupGHCNDStation(catalogDir.toURI().toURL(), null);

					Logging.INSTANCE.info("Storing weather data from daily observation files...");

					int i = 0;
					for (String id : stationIds) {
						WeatherStation ws = INSTANCE.wbox.retrieve(id);
						try {
							if (ws.cacheData(false)) {
								Logging.INSTANCE.info(
										"Data for station " + ws.getId() + " updated to " + ws.getLastKnownYear());
							}
						} catch (Throwable e) {
							Logging.INSTANCE.error("Weather station " + id + " data read failed: " + e.getMessage());
						}

						i++;

						if ((i % 50) == 0) {
							db.commit();
						}
					}
					db.commit();

					Logging.INSTANCE.info("All stations up to date.");

					// don't use these any longer, go online for updates.
					WeatherStation.setLocalGHCNDLocation(null);

					break;
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			}
		} else {

			catalogDir = new File(properties.getProperty(GHNCD_CATALOG_LOCATION));
			Logging.INSTANCE.info("GHCND data have been initialized before. Checking for updates.");

			int i = 0;
			for (String id : stationIds) {
				WeatherStation ws = INSTANCE.wbox.retrieve(id);
				try {
					if (ws.cacheData(false)) {
						Logging.INSTANCE
								.info("Data for station " + ws.getId() + " updated to " + ws.getLastKnownYear());
					}
				} catch (Throwable e) {
					Logging.INSTANCE.error("Weather station " + id + " data read failed: " + e.getMessage());
				}

				i++;

				if ((i % 50) == 0) {
					db.commit();
				}
			}

			db.commit();

			Logging.INSTANCE.info("All stations up to date.");

		}

	}

}
