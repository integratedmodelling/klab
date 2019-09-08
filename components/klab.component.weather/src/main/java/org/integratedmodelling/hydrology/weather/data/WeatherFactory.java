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
package org.integratedmodelling.hydrology.weather.data;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.FixedReader;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.URLUtils;

import com.ibm.icu.util.Calendar;

public class WeatherFactory {

	public static String baseURL = "http://150.241.222.1/ghcn";
	private static WeatherFactory _this;
	static Log logger = LogFactory.getLog(WeatherFactory.class);

	public static WeatherFactory get() {
		if (_this == null) {
			_this = new WeatherFactory();
		}
		return _this;
	}

	/**
	 * Pass the base URL that will find the GHCN dataset right under it. Do not add
	 * a slash at the end. If this is not used prior to calling checkDatabase(), the
	 * default (slow) will be used.
	 * 
	 * @param url
	 */
	public static void setBaseURL(String url) {
		baseURL = url;
	}

	private CRUReader cruReader;

	/*
	 * ensures the passed kbox contains the full index to the weather stations.
	 * CAUTION: long-running operation - about 3h on a powerful desktop. Better done
	 * on a real server with fast disks and used through REST.
	 */
	public void checkDatabase() throws KlabException {

		/*
		 * check paths first
		 */
		File fPath = Configuration.INSTANCE.getDataPath();
		File dataPath = new File(fPath + File.separator + "ghcnd");
		File cruPath = new File(fPath + File.separator + "cru");

		if (fPath.exists() && fPath.isDirectory() && fPath.canRead()) {
			try {
				baseURL = dataPath.toURI().toURL().toString();
			} catch (MalformedURLException e) {
				// shouldn't happen, but continue with previous URL.
			}
		} else {
			throw new KlabException("wmengine application is misconfigured: no accessible path at "
					+ Configuration.INSTANCE.getDataPath());
		}

		if (!fPath.canWrite()) {
			throw new KlabException(
					"application path at " + Configuration.INSTANCE.getDataPath() + " must be writable");
		}

		/*
		 * check out CRU data first. These are fairly quick to initialize.
		 */
		long stationCount = 0;
		if (cruPath.exists() && fPath.isDirectory() && fPath.canRead()) {
			initializeCRUData(cruPath);
			stationCount = WeatherKbox.INSTANCE.count();
		}

		if (!(dataPath.exists() && dataPath.isDirectory() && dataPath.canRead())) {
			logger.info("GHCND data directory not found at " + dataPath + ": no GHCND stations available");
			return;
		}

		File leftover = new File(dataPath + File.separator + ".ws_lasts");
		long wscount = WeatherKbox.INSTANCE.count();

		if (wscount > (stationCount + 100) && !leftover.exists()) {
			logger.warn("setup was not performed: no lock file and " + (wscount - stationCount)
					+ " GHCND stations are present in the database");
			return;
		}

		File fInv = new File(dataPath + File.separator + "ghcnd-inventory.txt");
		File fSta = new File(dataPath + File.separator + "ghcnd-stations.txt");

		/**
		 * Try to get these locally, to avoid keeping a URL connection open too long.
		 */
		try {
			if (!fSta.exists()) {
				URLUtils.copy(new URL(baseURL + "/ghcnd-stations.txt"), fSta);
			}
			if (!fInv.exists()) {
				URLUtils.copy(new URL(baseURL + "/ghcnd-inventory.txt"), fInv);
			}
		} catch (MalformedURLException e1) {
			// do nothing, try directly.
		}

		List<FixedReader> stations = null;
		List<FixedReader> inventor = null;
		int startAt = 0;
		if (leftover.exists()) {
			try {
				startAt = Integer.parseInt(FileUtils.readFileToString(leftover).trim());
			} catch (NumberFormatException | IOException e) {
				throw new KlabIOException(e);
			}
		}

		/*
		 * Format of GHCN station file (ghcnd-stations.txt)
		 * ------------------------------ Variable Columns Type
		 * ------------------------------ ID 1-11 Character LATITUDE 13-20 Real
		 * LONGITUDE 22-30 Real ELEVATION 32-37 Real STATE 39-40 Character NAME 42-71
		 * Character GSNFLAG 73-75 Character HCNFLAG 77-79 Character WMOID 81-85
		 * Character ------------------------------
		 */
		if (fInv.exists() && fSta.exists()) {
			stations = FixedReader.parse(fSta, new int[] { 0, 12, 21, 31, 38, 41, 72, 76, 80 });
			inventor = FixedReader.parse(fInv, new int[] { 0, 12, 21, 31, 35, 40, 45 });
		} else {
			stations = FixedReader.parse(baseURL + "/ghcnd-stations.txt",
					new int[] { 0, 12, 21, 31, 38, 41, 72, 76, 80 });
			inventor = FixedReader.parse(baseURL + "/ghcnd-inventory.txt", new int[] { 0, 12, 21, 31, 35, 40, 45 });
		}

		// reader for the inventory file; the following depends on it being ordered
		// exactly like
		// the stations file.
		Iterator<FixedReader> inventory = inventor.iterator();
		FixedReader invLine = inventory.next();

		WeatherKbox.INSTANCE.getDatabase().preallocateConnection();

		int i = 0;
		for (FixedReader fr : stations) {

			String id = fr.nextString().trim();
			double lat = fr.nextDouble();
			double lon = fr.nextDouble();
			double alt = fr.nextDouble();

			/*
			 * DO NOT REMOVE - side effects of nextString() are crucial.
			 */
			String state = fr.nextString().trim();
			String name = fr.nextString().trim();

			/*
			 * make a new object and store it
			 */
			WeatherStation ws = new WeatherStation(baseURL, id, lat, lon, alt);

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
				throw new KlabIOException(e);
			}

			/*
			 * we peeked already, so reset counter for next inventory line if we have more
			 */
			if (inventory.hasNext()) {
				invLine.reset();
			}

			if (i >= startAt) {
				i++;
				try {
					// don't re-do it unless the data are not there. To force
					// cleanup of the stored data, remove the <datadir>/data
					// directory.
					ws.cacheData();

				} catch (Throwable e) {
					logger.error("Data for station " + i + "th station: " + ws + " are unreadable; skipping");
					continue;
				}
				logger.info("Storing " + i + "th station: " + ws);
				try {
					WeatherKbox.INSTANCE.store(ws);
				} catch (Exception e) {
					logger.error(e);
				}
				try {
					FileUtils.writeStringToFile(leftover, "" + i);
				} catch (IOException e) {
					throw new KlabIOException(e);
				}
			} else {
				logger.info("Skipping " + i++ + "th station: " + ws);
			}
		}

		WeatherKbox.INSTANCE.getDatabase().deallocateConnection();

		FileUtils.deleteQuietly(leftover);
	}

	private void initializeCRUData(File cruPath) throws KlabException {

		if (new File(cruPath + File.separator + CRUReader.fileMap.get(Weather.PRECIPITATION_MM)).exists()) {
			this.cruReader = new CRUReader(cruPath);
			this.cruReader.createStations();
		}
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

	public long countStations() {
		return WeatherKbox.INSTANCE.count();
	}

	public CRUReader getCRUReader() {
		return cruReader;
	}

}
