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
import java.net.MalformedURLException;
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
import org.integratedmodelling.weather.WeatherComponent;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

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

	public static String[] GHCN_URLS = { "https://www1.ncdc.noaa.gov/pub/data/ghcn/daily",
			"http://150.241.222.1/ghcn" };
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

		String query = "SELECT * FROM weatherstations WHERE location && '" + ((Shape) context).getStandardizedGeometry()
				+ "'";
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

		Properties properties = Extensions.INSTANCE.getComponentProperties(WeatherComponent.ID);

		if (properties.containsKey(CRU_CATALOG_LOCATION)) {
			setupCRUStations(new File(properties.getProperty(CRU_CATALOG_LOCATION)), null);
		}

		try {
			INSTANCE.setupGHCNDStation(new URL(GHCN_URLS[0]), null);
		} catch (MalformedURLException e1) {
			// give me a break
		}

		/**
		 * This should be in a thread of its own
		 */
		setupTRRMEvents();
		
		
		setupContributedStations();

		/**
		 * Local data are only used at initialization
		 */
		if (properties.containsKey(GHNCD_CATALOG_LOCATION)) {
			WeatherStation.setLocalGHCNDLocation(new File(properties.getProperty(GHNCD_CATALOG_LOCATION)));
		}

		/**
		 * This should be timed by a scheduler and split
		 */
		for (String id : stationIds) {
			WeatherStation ws = INSTANCE.wbox.retrieve(id);
			try {
				if (ws.cacheData()) {
					Logging.INSTANCE.info("Weather station " + ws.getId() + " has updated data");
				}
			} catch (Throwable e) {
				Logging.INSTANCE.error("Weather station " + ws.getId() + " data read failed: " + e.getMessage());
			}
		}
		
		// reentrant for repeated execution
		WeatherStation.setLocalGHCNDLocation(null);

	}
	
	public static void main(String[] args) throws Exception {
		INSTANCE.setup();
	}

	public long getStationsCount() {
		return wbox.count();
	}

	public IPersistentTable<String, WeatherStation> getDatabase() {
		return wbox;
	}

}
