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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.integratedmodelling.klab.Configuration;
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
	static Set<String> stationSet;
	// all stations in database, even with no data yet read 
	static Set<String>	stationIds;
	static Map<String, Integer> nansMap;
	static Map<String, Long> datasizeMap;

	public static String[] GHCN_URLS = { "https://www1.ncdc.noaa.gov/pub/data/ghcn/daily",
			"http://150.241.222.1/ghcn" };
	private CRUReader cruReader;
	private final static String LAST_UPDATE_PROPERTY = "last.catalog.update";

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
			File dpath = Configuration.INSTANCE.getDataPath("weather");
			dpath.mkdirs();
			db = DBMaker.fileDB(new File(dpath + File.separator + "stationdata.dat")).closeOnJvmShutdown().make();

			dataMap = db.treeMap("datamap", Serializer.STRING, Serializer.DOUBLE_ARRAY).createOrOpen();
			nansMap = db.treeMap("nansmap", Serializer.STRING, Serializer.INTEGER).createOrOpen();
			stationSet = db.treeSet("stationset", Serializer.STRING).createOrOpen();
			stationIds = db.treeSet("stationids", Serializer.STRING).createOrOpen();
			datasizeMap = db.treeMap("timestamps", Serializer.STRING, Serializer.LONG).createOrOpen();
		}
	}

	/**
	 * Call with the URL (file or http) to the
	 * 
	 * @param catalogFile
	 */
	public void setupGHCND(URL catalogFile, IMonitor monitor) {

		checkStorage();

		Logging.INSTANCE.info("Setting up GHCND dataset");

		try {

			boolean update = true;
			File wpath = Configuration.INSTANCE.getDataPath("ghcnd");
			Properties properties = new Properties();
			File pfile = new File(wpath + File.separator + "ghcnd.properties");
			if (pfile.exists()) {
				try (InputStream inp = new FileInputStream(pfile)) {
					properties.load(inp);
				}
			}

			DateTime now = new DateTime();
			if (properties.getProperty(LAST_UPDATE_PROPERTY) != null) {
				DateTime then = new DateTime(properties.getProperty(LAST_UPDATE_PROPERTY));
				if (Days.daysBetween(now, then).getDays() < 30) {
					update = false;
				}
			}

			if (!update && wbox.count() < 100000) {
				update = true;
			}

			if (update) {

				stationIds.clear();

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
						Logging.INSTANCE.error("shitty station: " + ws);
						continue;
					}

					wbox.store(ws, monitor);

					/*
					 * we peeked already, so reset counter for next inventory line if we have more
					 */
					if (inventory.hasNext()) {
						invLine.reset();
					}

					stationIds.add(id);

					System.out.println(ws);

				}

				db.commit();

				properties.setProperty(LAST_UPDATE_PROPERTY, now.toString());
				try (OutputStream out = new FileOutputStream(pfile)) {
					properties.store(out, null);
				}
			} else {
				Logging.INSTANCE.info("GHCND dataset is up to date");
			}

			/*
			 * TODO spawn a spliterator to check each station (groups of 2/4/X threads).
			 * Each thread should handle a few and they should be staged so they run
			 * regularly at different times.
			 * 
			 * FIXME this sucks because we can't store while things are iterated.
			 */
			Logging.INSTANCE.info("Scanning GHCND weather stations for updates...");

			if (wbox.getDatabase() != null) {
				wbox.getDatabase().deallocateConnection();
			}

//			FileUtils.deleteQuietly(leftover);

		} catch (Throwable e) {
			// I hate you
			throw new KlabIOException(e);
		}

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

	public void setupCRU(File repository, IMonitor monitor) {
		checkStorage();
		this.cruReader = new CRUReader(repository);
		this.cruReader.createStations(wbox, monitor);
	}

//	/*
//	 * ensures the passed kbox contains the full index to the weather stations.
//	 * CAUTION: long-running operation - about 3h on a powerful desktop. Better done
//	 * on a real server with fast disks and used through REST.
//	 */
//	public void checkDatabase() throws KlabException {
//
//		String baseURL = "http://150.241.222.1/ghcn";
//
//		/*
//		 * check paths first
//		 */
//		File fPath = Configuration.INSTANCE.getDataPath();
//		File dataPath = new File(fPath + File.separator + "ghcnd");
//		File cruPath = new File(fPath + File.separator + "cru");
//
//		if (fPath.exists() && fPath.isDirectory() && fPath.canRead()) {
//			try {
//				baseURL = dataPath.toURI().toURL().toString();
//			} catch (MalformedURLException e) {
//				// shouldn't happen, but continue with previous URL.
//			}
//		} else {
//			throw new KlabException("wmengine application is misconfigured: no accessible path at "
//					+ Configuration.INSTANCE.getDataPath());
//		}
//
//		if (!fPath.canWrite()) {
//			throw new KlabException(
//					"application path at " + Configuration.INSTANCE.getDataPath() + " must be writable");
//		}
//
//		/*
//		 * check out CRU data first. These are fairly quick to initialize.
//		 */
//		long stationCount = 0;
//		if (cruPath.exists() && fPath.isDirectory() && fPath.canRead()) {
////			initializeCRUData(cruPath);
//			stationCount = wbox.count();
//		}
//
//		if (!(dataPath.exists() && dataPath.isDirectory() && dataPath.canRead())) {
//			Logging.INSTANCE.info("GHCND data directory not found at " + dataPath + ": no GHCND stations available");
//			return;
//		}
//
//		File leftover = new File(dataPath + File.separator + ".ws_lasts");
//		long wscount = wbox.count();
//
//		if (wscount > (stationCount + 100) && !leftover.exists()) {
//			Logging.INSTANCE.warn("setup was not performed: no lock file and " + (wscount - stationCount)
//					+ " GHCND stations are present in the database");
//			return;
//		}
//
//		File fInv = new File(dataPath + File.separator + "ghcnd-inventory.txt");
//		File fSta = new File(dataPath + File.separator + "ghcnd-stations.txt");
//
//		/**
//		 * Try to get these locally, to avoid keeping a URL connection open too long.
//		 */
//		try {
//			if (!fSta.exists()) {
//				URLUtils.copy(new URL(baseURL + "/ghcnd-stations.txt"), fSta);
//			}
//			if (!fInv.exists()) {
//				URLUtils.copy(new URL(baseURL + "/ghcnd-inventory.txt"), fInv);
//			}
//		} catch (MalformedURLException e1) {
//			// do nothing, try directly.
//		}
//
//		List<FixedReader> stations = null;
//		List<FixedReader> inventor = null;
//		int startAt = 0;
//		if (leftover.exists()) {
//			try {
//				startAt = Integer.parseInt(FileUtils.readFileToString(leftover).trim());
//			} catch (NumberFormatException | IOException e) {
//				throw new KlabIOException(e);
//			}
//		}
//
//		if (fInv.exists() && fSta.exists()) {
//			stations = FixedReader.parse(fSta, new int[] { 0, 12, 21, 31, 38, 41, 72, 76, 80 });
//			inventor = FixedReader.parse(fInv, new int[] { 0, 12, 21, 31, 35, 40, 45 });
//		} else {
//			stations = FixedReader.parse(baseURL + "/ghcnd-stations.txt",
//					new int[] { 0, 12, 21, 31, 38, 41, 72, 76, 80 });
//			inventor = FixedReader.parse(baseURL + "/ghcnd-inventory.txt", new int[] { 0, 12, 21, 31, 35, 40, 45 });
//		}
//
//		// reader for the inventory file; the following depends on it being ordered
//		// exactly like
//		// the stations file.
//		Iterator<FixedReader> inventory = inventor.iterator();
//		FixedReader invLine = inventory.next();
//
//		wbox.getDatabase().preallocateConnection();
//
//		int i = 0;
//		for (FixedReader fr : stations) {
//
//			String id = fr.nextString().trim();
//			double lat = fr.nextDouble();
//			double lon = fr.nextDouble();
//			double alt = fr.nextDouble();
//
//			/*
//			 * DO NOT REMOVE - side effects of nextString() are crucial.
//			 */
//			String state = fr.nextString().trim();
//			String name = fr.nextString().trim();
//
//			/*
//			 * make a new object and store it
//			 */
//			WeatherStation ws = new WeatherStation(baseURL, id, lat, lon, alt);
//
//			try {
//				/*
//				 * read the available data series from the inventory.
//				 */
//				while (invLine.nextString().trim().equals(id)) {
//
//					invLine.nextDouble();
//					invLine.nextDouble();
//					String varId = invLine.nextString();
//					int firstYear = invLine.nextInt();
//					int lastYear = invLine.nextInt();
//
//					ws._provided.put(varId, new Pair<Integer, Integer>(firstYear, lastYear));
//
//					if (inventory.hasNext()) {
//						invLine = inventory.next();
//					}
//				}
//			} catch (Exception e) {
//				throw new KlabIOException(e);
//			}
//
//			/*
//			 * we peeked already, so reset counter for next inventory line if we have more
//			 */
//			if (inventory.hasNext()) {
//				invLine.reset();
//			}
//
//			if (i >= startAt) {
//				i++;
//				try {
//					// don't re-do it unless the data are not there. To force
//					// cleanup of the stored data, remove the <datadir>/data
//					// directory.
//					ws.cacheData();
//
//				} catch (Throwable e) {
//					Logging.INSTANCE.error("Data for station " + i + "th station: " + ws + " are unreadable; skipping");
//					continue;
//				}
//				Logging.INSTANCE.info("Storing " + i + "th station: " + ws);
//				try {
//					wbox.store(ws, null);
//				} catch (Exception e) {
//					Logging.INSTANCE.error(e);
//				}
//				try {
//					FileUtils.writeStringToFile(leftover, "" + i);
//				} catch (IOException e) {
//					throw new KlabIOException(e);
//				}
//			} else {
//				Logging.INSTANCE.info("Skipping " + i++ + "th station: " + ws);
//			}
//		}
//
//		wbox.getDatabase().deallocateConnection();
//
//		FileUtils.deleteQuietly(leftover);
//	}

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

	public static void main(String[] args) throws Exception {

//		INSTANCE.setupGHCND(new URL(GHCN_URLS[0]), null);
		INSTANCE.setupCRU(new File("C:\\CRU"), null);

		for (String id : stationIds) {
			WeatherStation ws = INSTANCE.wbox.retrieve(id);
			if (ws.cacheData()) {
				Logging.INSTANCE.info("Weather station " + ws.getId() + " has updated data");
			}
		}
	}

	public long getStationsCount() {
		return wbox.count();
	}

	public IPersistentTable<String, WeatherStation> getDatabase() {
		return wbox;
	}

}
