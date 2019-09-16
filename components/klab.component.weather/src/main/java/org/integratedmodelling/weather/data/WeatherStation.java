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
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.api.data.general.ITable;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpatial;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.geospace.extents.Projection;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.FixedReader;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.URLUtils;
import org.joda.time.DateTime;

import com.vividsolutions.jts.geom.Point;

/**
 * A weather station with all the data known for it. The closest station to any
 * point on the globe can be retrieved using the weather factory. Each Weather
 * has one (and possibly in the future more than one) station associated.
 * 
 * @author Ferd
 */
public class WeatherStation implements ISpatial {

	public class Data {

		String variable;
		Set<Integer> generated = new HashSet<>();
		int startYear = 0;
		int endYear = 0;

		/*
		 * Data are one pair <year, daily data> per year, in increasing year order.
		 * Nodata are NaNs. Array may have either 365 or 366 elements, according to
		 * year.
		 */
		Map<Integer, double[]> data = new HashMap<>();

		public Data(String variable) {
			this.variable = variable;
		}

		public int getEndYear() {
			if (endYear == 0) {
				for (int i : data.keySet()) {
					if (endYear < i) {
						endYear = i;
					}
				}
			}
			return endYear;
		}

		public int getStartYear() {
			if (startYear == 0) {
				startYear = 30000;
				for (int i : data.keySet()) {
					if (startYear > i) {
						startYear = i;
					}
				}
			}
			return startYear;
		}

		public String dump() {
			String s = "";
			for (int y : data.keySet()) {
				s += (s.isEmpty() ? "" : ",") + y;
			}
			return variable + "(" + s + ")";
		}

		public void setNodata(int year) {
			requireYearData(variable, year);
		}
	}

	private static File localGHCNDLocation;

	Map<String, Data> variables = new HashMap<>();
	Metadata metadata = new Metadata();
	WeatherGenerator wg = null;
	boolean wgFailure = false;
	int maxRecordAge = 15;
	String _id;
	double elevation;
	double longitude;
	double latitude;
	String email;
	Map<String, Pair<Integer, Integer>> _provided = new HashMap<>();

	/*
	 * currently: GHNCD, CRU, USER (uploaded) or GEN (for generated). More may be
	 * added.
	 */
	String _source = "GHNCD";
	/*
	 * currently: RAW, INTERPOLATED or SIMULATED
	 */
	String _type = "RAW";

	/**
	 * Generate the variables supported by the weather generator. Only useful if we
	 * have the three variables we need for training. TODO/FIXME: this is using a WG
	 * trained on the full dataset. We should only train it up to the year of
	 * interest.
	 * 
	 * @param year
	 * @throws ThinklabException
	 */
	public void generateWeather(int year) throws KlabException {

		if (wgFailure) {
			return;
		}

		if (wg == null) {
			Logging.INSTANCE.info("generating weather for " + _id);
			wg = new WeatherGenerator(this, 0, year);
		}
		List<double[]> vars = new ArrayList<double[]>();
		for (String variable : new String[] { Weather.PRECIPITATION_MM, Weather.MIN_TEMPERATURE_C,
				Weather.MAX_TEMPERATURE_C }) {
			vars.add(requireYearData(variable, year));
		}
		wg.generateDaily(vars.get(1), vars.get(2), vars.get(0), true);
		variables.get(Weather.PRECIPITATION_MM).generated.add(year);
		variables.get(Weather.MIN_TEMPERATURE_C).generated.add(year);
		variables.get(Weather.MAX_TEMPERATURE_C).generated.add(year);
	}

	private void generateDataFromMostRecentRecord(int year) throws KlabException {

		for (String variable : new String[] { Weather.PRECIPITATION_MM, Weather.MIN_TEMPERATURE_C,
				Weather.MAX_TEMPERATURE_C }) {
			double[] data = generateDataFromMostRecentRecord(variable, year, maxRecordAge);
			if (data != null) {
				setYearData(variable, year, data);
			}
		}
	}

	/**
	 * true if there are data stored up to _maxRecordAge less than the passed year
	 * for all the passed variables.
	 * 
	 * @param variables
	 * @return
	 */
	public boolean hasUsableDataFor(int year, String... variables) {

		for (String v : variables) {
			Pair<Integer, Integer> bnds = _provided.get(v);
			if (bnds == null || (bnds.getSecond() + maxRecordAge) < year) {
				return false;
			}
		}
		return true;
	}

	public boolean hasEnoughDataFor(ITime time, int maxNodataPercentage, String... variables) {
		return hasEnoughDataFor(time.getStart().getMillis(), time.getEnd().getMillis(), maxNodataPercentage, variables);
	}

	/**
	 * similar check to hasUsableDataFor, but looks at the percentage of no-data
	 * instead of the existence of the record. Coded as a separate method because
	 * this one needs the dataset to have been stored by storeData(), which is not
	 * necessarily the general situation. If storeData() was not called, this will
	 * return false even if enough data exist.
	 * 
	 * @param variables
	 * @return
	 */
	public boolean hasEnoughDataFor(long start, long end, int maxNodataPercentage, String... variables) {

		for (int year : Time.yearsBetween(start, end)) {

			for (String v : variables) {
				if (WeatherFactory.dataMap == null || !WeatherFactory.dataMap.containsKey(_id + ":" + v + "@" + year)) {
					return false;
				}
				if (WeatherFactory.nansMap.get(_id + ":" + v + "@" + year) > maxNodataPercentage) {
					return false;
				}
			}
		}
		return true;
	}

	private double[] generateDataFromMostRecentRecord(String var, int year, int maxYearsBehind) throws KlabException {
		double[] ret = null;
		int olderYear = 0;
		int maxAllowed = year - (maxYearsBehind < 0 ? _provided.get(var).getFirst() : maxYearsBehind);
		for (int i = year; i >= maxAllowed; i--) {
			if ((ret = getDataFromDB(var, i)) != null) {
				olderYear = i;
				Logging.INSTANCE.info(_id + " using " + var + " data from " + olderYear + " to represent year " + year);
				break;
			}
		}
		return Time.adjustLeapDays(ret, year, olderYear);
	}

	/**
	 * Get the data series for the passed variable and year. If the station hasn't
	 * been seen before, store all of its data in the DB. If null is returned, the
	 * station doesn't have the requested data.
	 * 
	 * @param variable
	 * @param year
	 * @return
	 */
	double[] getDataFromDB(String variable, int year) throws KlabException {
		cacheData();
		String id = _id + ":" + variable + "@" + year;
		return WeatherFactory.dataMap.get(id);
	}

	/**
	 * Cache the data; return true if anything new was written to the database.
	 * 
	 * @return
	 * @throws KlabException
	 */
	synchronized boolean cacheData() throws KlabException {

		boolean ret = false;

		if (_source.equals("GHNCD")) {
			/*
			 * check if we have different data available; if so, download and read again
			 */
			for (String gurl : WeatherFactory.GHCN_URLS) {

				try {

					File dataFile = null;
					boolean isTemporary = false;
					long size = -1;
					
					if (localGHCNDLocation != null && !WeatherFactory.stationWithData.contains(_id)) {
						dataFile = new File(localGHCNDLocation + File.separator + _id + ".dly");
						if (dataFile.exists()) {
							size = dataFile.length();
						}
					}

					if (dataFile == null || !dataFile.exists()) {
						
						URL dataUrl = new URL(gurl + "/all/" + _id + ".dly");
						long storedSize = WeatherFactory.datasizeMap.containsKey(_id)
								? WeatherFactory.datasizeMap.get(_id)
								: 0;
								
						size = URLUtils.getFileSize(dataUrl);

						if ((size > 0 && storedSize < size) || !WeatherFactory.stationWithData.contains(_id)) {
							dataFile = File.createTempFile("station", ".dly");
							URLUtils.copyChanneled(dataUrl, dataFile);
							isTemporary = true;
						}
					}

					if (dataFile != null && dataFile.exists()) {
						storeGHCNData(dataFile, size);
						if (isTemporary) {
							FileUtils.deleteQuietly(dataFile);
						}
						ret = true;
					} else {
						Logging.INSTANCE.error("Cannot access data file for station " + _id);
					}
					
					
					break;
				} catch (Throwable e) {
					// do nothing, go through other URLs
					Logging.INSTANCE.error(
							"read of weather data for station " + _id + " failed on " + gurl + ": " + e.getMessage());
				}
			}

		} else if (!WeatherFactory.stationWithData.contains(_id)) {

			/*
			 * if this is a CRU station, use CRU strategy
			 */
			if (_source.equals("CRU") && WeatherFactory.INSTANCE.getCRUReader() != null) {
				storeCRUData();
				ret = true;
			}
		}

		return ret;
	}

	private void storeCRUData() throws KlabException {

		Logging.INSTANCE.info("interpolating and storing CRU data for " + _id);

		if (!WeatherFactory.dataMap.containsKey(_id + ":" + Weather.PRECIPITATION_MM + "@" + _lastKnownYear)) {
			for (int year = Math.max(_firstKnownYear, 1970); year <= _lastKnownYear; year++) {
				Map<String, double[]> data = WeatherFactory.INSTANCE.getCRUReader().readData(latitude, longitude, year);
				for (String variable : data.keySet()) {
					String id = _id + ":" + variable + "@" + year;
					WeatherFactory.dataMap.put(id, data.get(variable));
					WeatherFactory.nansMap.put(id, 0);
				}
			}

			WeatherFactory.stationWithData.add(_id);
			WeatherFactory.datasizeMap.put(_id,
					WeatherFactory.INSTANCE.getCRUReader().getDataFile(Weather.PRECIPITATION_MM).lastModified());
			WeatherFactory.db.commit();
			
			Logging.INSTANCE
					.info("stored CRU data for " + _id + " (" + _firstKnownYear + " to " + _lastKnownYear + ")");
		}

	}

	/**
	 * @param variable
	 * @param year
	 * @param useWeatherGenerator
	 * @param returnNoData
	 * @return
	 */
	double[] requireYearData(String variable, int year) {

		double[] ret = null;
		Data dr = variables.get(variable);
		if (dr == null) {
			dr = new Data(variable);
			dr.variable = variable;
			// dr.startYear = dr.endYear = year;
			ret = new double[Time.daysInYear(year)];
			for (int i = 0; i < ret.length; i++)
				ret[i] = Double.NaN;
			dr.data.put(year, ret);
			variables.put(variable, dr);
		} else {

			if (dr.data.containsKey(year)) {
				ret = dr.data.get(year);
			} else {
				ret = new double[Time.daysInYear(year)];
				for (int i = 0; i < ret.length; i++)
					ret[i] = Double.NaN;
				dr.data.put(year, ret);
			}
		}

		return ret;
	}

	void setYearData(String variable, int year, double[] data) {

		Data dr = variables.get(variable);
		if (dr == null) {
			dr = new Data(variable);
			dr.variable = variable;
			variables.put(variable, dr);
		}
		dr.data.put(year, data);
	}

	/*
	 * set to the distance from the original request when this is coming from a
	 * nearest neighbor search.
	 */
	double _distanceKm = -1;

	IShape _location;

	int _firstKnownYear;
	int _lastKnownYear;

	/**
	 * Does not read data and expects to find all data in CRU files.
	 * 
	 * @param name
	 * @param lon
	 * @param lat
	 */
	public WeatherStation(String name, double lon, double lat, int startYear, int endYear) {

		this._id = name;
		this._type = "INTERPOLATED";
		this._source = "CRU";
		this.elevation = 0;

		longitude = lon;
		latitude = lat;
		_location = Shape.create(lon, lat, Projection.getLatLon());
		_firstKnownYear = startYear;
		_lastKnownYear = endYear;

		/*
		 * record all variables in CRU with their data ranges
		 */
		for (String var : CRUReader.cruVariables) {
			_provided.put(var, new Pair<>(startYear, endYear));
		}
		
		WeatherFactory.stationIds.add(_id);
//		WeatherFactory.db.commit();
	}

	public WeatherStation(Map<String, Object> data) {

		Parameters<String> rs = Parameters.wrap(data);
		this._id = rs.get("id", String.class);
		this.elevation = rs.get("elevation", Double.class);
		this.latitude = rs.get("latitude", Double.class);
		this.longitude = rs.get("longitude", Double.class);
		String pvar = rs.get("provided_vars", String.class);
		String psta = rs.get("provided_start", String.class);
		String pend = rs.get("provided_end", String.class);

		parseVarsDescriptors(pvar, psta, pend);

		if (_id.startsWith("CRU_")) {
			this._type = "INTERPOLATED";
			this._source = "CRU";
		}
	}

	public Map<String, Object> asData() {
		String[] provided = getProvidedVarsDescriptors();
		Map<String, Object> ret = new HashMap<>();
		ret.put("id", _id);
		ret.put("elevation", elevation);
		ret.put("latitude", latitude);
		ret.put("longitude", longitude);
		ret.put("provided_vars", provided[0]);
		ret.put("provided_start", provided[1]);
		ret.put("provided_end", provided[2]);
		ret.put("location", _location);
		return ret;
	}

	public static Set<String> ids() {
		WeatherFactory.checkStorage();
		return WeatherFactory.stationIds;
	}

	/**
	 * Reads in the template file according to the WSTemplate.xlsx in resources.
	 * 
	 * @param table
	 * @throws KlabException
	 */
	public WeatherStation(String name, File file) throws KlabException {

		this._type = "RAW";
		this._source = "USER";

		List<ITable<Object>> tableset = new ArrayList<>(); // TableFactory.open(file);

		if (tableset == null || tableset.size() < 1) {
			throw new KlabException("cannot open content as a table");
		}

		for (ITable<Object> table : tableset) {

			ITable.Column<Object> vNames = null, vValues = null;
			ITable.Column<Object> date = null;
			List<ITable.Column<Object>> vars = new ArrayList<>();
			int mandatory = 0;

			for (ITable.Column<Object> column : table.getColumns()) {

				switch (column.getName()) {
				case "VARIABLES":
					vNames = column;
					break;
				case "VALUES":
					vValues = column;
					break;
				case "DATE":
					date = column;
					break;
				default:
					if (Weather.varCatalog.contains(column.getName())) {
						vars.add(column);
						if (column.getName().equals(Weather.PRECIPITATION_MM)
								|| column.getName().equals(Weather.MAX_TEMPERATURE_C)
								|| column.getName().equals(Weather.MIN_TEMPERATURE_C)) {
							mandatory++;
						}
					}
					break;
				}
			}

			if (date == null || vNames == null || vValues == null || mandatory < 3) {
				throw new KlabValidationException("mandatory columns are missing: check instructions in the template");
			}

			/*
			 * setup variables
			 */
			int i = 0;
			boolean hid = false, hlat = false, hlon = false, helev = false, hemail = false;

			for (Object z : vNames.getValues()) {

				/*
				 * we stop at the first empty cell.
				 */
				if (z == null || z.toString().trim().isEmpty()) {
					break;
				}

				switch (z.toString().trim()) {
				case "ID":
					this._id = checkNull(vValues.getValue(i, Object.class), z.toString());
					hid = true;
					break;
				case "LAT":
					this.latitude = Double.parseDouble(checkNull(vValues.getValue(i, Object.class), z.toString()));
					hlat = true;
					break;
				case "LON":
					this.longitude = Double.parseDouble(checkNull(vValues.getValue(i, Object.class), z.toString()));
					hlon = true;
					break;
				case "ELEVATION":
					this.elevation = Double.parseDouble(checkNull(vValues.getValue(i, Object.class), z.toString()));
					helev = true;
					break;
				case "EMAIL":
					this.email = checkNull(vValues.getValue(i, Object.class), z.toString());
					hemail = true;
					break;
				case "CONTRIBUTOR":
					break;
				case "DESCRIPTION":
					break;
				case "DATE":
					break;
				case "NOTES":
					break;
				}
				i++;
			}

			if (!helev || !hid || !hlat || !hlon || !hemail) {
				throw new KlabValidationException(
						"one or more mandatory variables (ID, ELEVATION, LAT, LON, EMAIL) are missing in the uploaded file");
			}

			if (!this._id.equals(name)) {
				throw new KlabValidationException(
						"the ID within the file (" + this._id + ") does not match the provided " + name);
			}

			_location = Shape.create(longitude, latitude, Projection.getLatLon());

			/*
			 * set up storage for yearly data as appropriate - we read the whole thing in
			 * once, then store the years we have. Key for the hash is VAR@YEAR.
			 */
			Map<String, double[]> data = new HashMap<>();

			for (i = 1; i < date.getValueCount(); i++) {

				Object dt = date.getValue(i, Object.class);
				if (dt == null) {
					continue;
				}
				String dat = dt.toString().trim();
				if (dat.isEmpty()) {
					continue;
				}

				String[] ddt = dat.split("/");
				if (ddt.length != 3) {
					throw new KlabValidationException("invalid date format: expecting dd/mm/yyyy: " + dat
							+ ". Make sure the date column is formatted as text.");
				}

				int day, year, month, yearDay;
				DateTime dm;
				try {
					day = Integer.parseInt(ddt[0]);
					month = Integer.parseInt(ddt[1]);
					year = Integer.parseInt(ddt[2]);
					dm = new DateTime(year, month, day, 0, 0);
				} catch (Throwable e) {
					throw new KlabValidationException("invalid date format: expecting dd/mm/yyyy: " + dat
							+ ". Make sure the date column is formatted as text.");
				}

				if (_lastKnownYear < year) {
					_lastKnownYear = year;
				}

				if (_firstKnownYear == 0 || _firstKnownYear > year) {
					_firstKnownYear = year;
				}

				yearDay = dm.getDayOfYear();

				for (ITable.Column<Object> dc : vars) {

					Object vl = dc.getValue(i, Object.class);
					if (vl == null || vl.toString().trim().isEmpty()) {
						continue;
					}

					double dval;
					try {
						dval = Double.parseDouble(vl.toString().trim());
					} catch (Throwable e) {
						throw new KlabValidationException(
								"invalid value for " + dc.getName() + ": expecting floating point number: " + vl);
					}

					String var = dc.getName();

					/*
					 * check provided info
					 */
					Pair<Integer, Integer> provided = _provided.get(var);
					if (provided == null) {
						provided = new Pair<Integer, Integer>(year, year);
						_provided.put(var, provided);
					} else {
						if (provided.getFirst() > year) {
							provided.setFirst(year);
						}
						if (provided.getSecond() < year) {
							provided.setSecond(year);
						}
					}

					/*
					 * insert data for later storage
					 */
					String key = var + "@" + year;
					double[] ydata = data.get(key);
					if (ydata == null) {
						ydata = new double[Time.daysInYear(year)];
						Arrays.fill(ydata, Double.NaN);
						data.put(key, ydata);
					}

					ydata[yearDay - 1] = dval;
				}

			}

			WeatherFactory.checkStorage();

			/*
			 * Store everything in local cache TODO store flags and an index of what's
			 * available + statistics etc.
			 */
			for (String s : data.keySet()) {
				double[] dd = data.get(s);
				int nans = 0;
				for (int j = 0; j < dd.length; j++) {
					if (Double.isNaN(dd[j])) {
						nans++;
					}
				}
				int pnans = (int) (((double) nans / (double) dd.length) * 100);
				WeatherFactory.dataMap.put(_id + ":" + s, data.get(s));
				WeatherFactory.nansMap.put(_id + ":" + s, pnans);
			}

			WeatherFactory.stationIds.add(_id);
			WeatherFactory.stationWithData.add(_id);
			WeatherFactory.datasizeMap.put(_id, file.lastModified());
			WeatherFactory.db.commit();

		}

	}

	private String checkNull(Object value, String key) throws KlabValidationException {
		if (value == null || value.toString().isEmpty()) {
			throw new KlabValidationException("value for " + key + " not provided or invalid");
		}
		return value.toString();
	}

	/**
	 * GHCND weather station, reading data from file.
	 * 
	 * @param baseURL
	 * @param id
	 * @param lat
	 * @param lon
	 * @param alt
	 */
	public WeatherStation(String baseURL, String id, double lat, double lon, double alt) {

		this._source = "GHCND";
		this._type = "RAW";

		WeatherFactory.checkStorage();

		_id = id;
		elevation = alt;
		longitude = lon;
		latitude = lat;

		_location = Shape.create(lon, lat, Projection.getLatLon());
		
		WeatherFactory.stationIds.add(_id);
//		WeatherFactory.db.commit();
		
	}

	public Point getPoint() {
		return (Point) _location.getGeometry();
	}

	void storeGHCNData(File file, long datasize) throws KlabException {

		WeatherFactory.checkStorage();

		/*
		 * set up storage for yearly data as appropriate - we read the whole thing in
		 * once, then store the years we have. Key for the hash is VAR|YEAR.
		 */
		Map<String, double[]> data = new HashMap<>();

		for (FixedReader fr : FixedReader.parse(file,
				new int[] { 0, 11, 15, 17, 21, 26, 27, 28, 29, 34, 35, 36, 37, 261, 266, 267, 268 })) {

			String ID = fr.nextString();
			int YEAR = fr.nextInt();
			int MONTH = fr.nextInt();
			String ELEMENT = fr.nextString();

			for (int day = 1; day <= 31; day++) {

				int VALUE = fr.nextInt(5);
				String MFLAG = fr.nextString(1);
				String QFLAG = fr.nextString(1);
				String SFLAG = fr.nextString(1);

				/*
				 * determine if this is a real date; skip if not
				 */
				int yearDay = -1;
				try {
					DateTime dm = new DateTime(YEAR, MONTH, day, 0, 0);
					yearDay = dm.getDayOfYear();
				} catch (Throwable e) {
					// not a real date, continue
				}

				if (yearDay < 0) {
					continue;
				}

				double value = Double.NaN;
				if (VALUE != -9999) {

					/*
					 * process data to final representation according to type
					 */
					if (ELEMENT.equals(Weather.PRECIPITATION_MM) || ELEMENT.equals(Weather.MAX_TEMPERATURE_C)
							|| ELEMENT.equals(Weather.MIN_TEMPERATURE_C) || ELEMENT.equals(Weather.WIND_SPEED_M_SEC)
					// TODO whatever else we want
					) {
						value = VALUE / 10.0;
					} else {
						value = VALUE;
					}

				}

				/*
				 * set month data in year dataseries
				 */
				double[] dd = data.get(ELEMENT + "@" + YEAR);
				if (dd == null) {
					dd = new double[Time.daysInYear(YEAR)];
					for (int i = 0; i < dd.length; i++) {
						dd[i] = Double.NaN;
					}
					data.put(ELEMENT + "@" + YEAR, dd);
				}
				dd[yearDay - 1] = value;
			}
		}

		/*
		 * Store everything in local cache TODO store flags and an index of what's
		 * available + statistics etc.
		 */
		for (String s : data.keySet()) {
			double[] dd = data.get(s);
			int nans = 0;
			for (int i = 0; i < dd.length; i++) {
				if (Double.isNaN(dd[i])) {
					nans++;
				}
			}
			int pnans = (int) (((double) nans / (double) dd.length) * 100);
			WeatherFactory.dataMap.put(_id + ":" + s, data.get(s));
			WeatherFactory.nansMap.put(_id + ":" + s, pnans);
		}
		WeatherFactory.stationWithData.add(_id);
		WeatherFactory.datasizeMap.put(_id, datasize);
		WeatherFactory.db.commit();
	}

	@Override
	public String toString() {

		/*
		 * list variables
		 */
		String vdesc = "[";
		for (String d : _provided.keySet()) {
			vdesc += (vdesc.length() == 1 ? "" : ",") + d;
		}
		vdesc += "]";

		return _id + " @" + latitude + "," + longitude + " " + vdesc
				+ (_distanceKm < 0.0 ? "" : (" (" + _distanceKm + "km away)"));

	}

//    @Override
	public IMetadata getMetadata() {
		return metadata;
	}

	public double getElevation() {
		return elevation;
	}

	/**
	 * This point is the one that we wanted originally; compute the distance in km
	 * to record accuracy from the original request.
	 * 
	 * @param location
	 */
	public void setRequestedPoint(IShape location) {
		_distanceKm = location.getStandardizedDistance(_location) / 1000.0;
	}

	/**
	 * Load up every year of the passed variable from storage.
	 * 
	 * @param variable
	 * @return
	 * @throws ThinklabException
	 */
	Data fetch(String variable) throws KlabException {

		if (variables.containsKey(variable)) {
			return variables.get(variable);
		}
		if (!provides(variable)) {
			return null;
		}
		Data ret = new Data(variable);
		for (int year = _provided.get(variable).getFirst(); year <= _provided.get(variable).getSecond(); year++) {
			double[] data = getDataFromDB(variable, year);
			if (data != null) {
				ret.data.put(year, data);
			}
		}
		variables.put(variable, ret);

		return ret;
	}

	public double[][] getYearlyData(boolean wholeYears, boolean interpolateMissing, boolean skipLeapDays,
			boolean consecutiveYears, String... variables) throws KlabException {

		Data[] records = new Data[variables.length];
		int i = 0;
		for (String v : variables) {
			Data dr = fetch(v);
			if (dr == null)
				return null;
			records[i++] = dr;
		}

		/*
		 * Determine years where all variables have records.
		 */
		int minYear = 3000, maxYear = 0;
		for (Data dr : records) {
			if (dr.getStartYear() < minYear) {
				minYear = dr.getStartYear();
			}
			if (dr.getEndYear() > maxYear) {
				maxYear = dr.getEndYear();
			}
		}

		List<Integer> years = new ArrayList<Integer>();
		int prev = -1;
		for (int y = minYear; y <= maxYear; y++) {

			if (consecutiveYears && prev != -1 && y != prev + 1)
				break;

			prev = y;
			int n = 0;
			for (Data dr : records) {

				if (!dr.data.containsKey(y))
					continue;

				if (wholeYears) {
					/*
					 * TODO skip non-whole years - this is taken care of by interpolate() using the
					 * ratio criterion.
					 */
				}
				n++;
			}
			if (n == records.length) {
				years.add(y);
			}
		}

		/*
		 * build dataset. First collect the data arrays, skipping any year when one or
		 * more is null due to too many no-data or not matching expectations.
		 */
		ArrayList<Integer> yearsFinal = new ArrayList<Integer>();
		ArrayList<ArrayList<double[]>> dataFinal = new ArrayList<ArrayList<double[]>>();

		for (Integer year : years) {
			ArrayList<double[]> dholder = new ArrayList<double[]>();
			for (int v = 0; v < records.length; v++) {
				double[] data = records[v].data.get(year);
				if (interpolateMissing) {
					data = interpolate(data);
				}
				if (data != null && skipLeapDays) {
					data = skipLeapDay(data);
				}
				if (data != null) {
					dholder.add(data);
				}
			}

			if (dholder.size() == variables.length) {
				yearsFinal.add(year);
				dataFinal.add(dholder);
			}
		}

		/*
		 * now create the final
		 */
		double[][] ret = null;
		int len = 0;
		for (Integer year : yearsFinal) {
			len += skipLeapDays ? 365 : Time.daysInYear(year);
		}

		ret = new double[len][variables.length];
		int ofs = 0;
		for (int y = 0; y < yearsFinal.size(); y++) {

			int year = yearsFinal.get(y);
			int days = skipLeapDays ? 365 : Time.daysInYear(year);
			ArrayList<double[]> dholder = dataFinal.get(y);

			for (int v = 0; v < records.length; v++) {
				for (int day = 0; day < days; day++) {
					ret[ofs + day][v] = dholder.get(v)[day];
				}
			}

			ofs += days;
		}

		return ret;
	}

	private double[] skipLeapDay(double[] data) {
		if (data.length == 365)
			return data;

		double[] ret = new double[365];
		int n = 0;
		for (int i = 0; i < 365; i++) {
			ret[n++] = i <= 60 ? data[i] : data[i + 1];
		}
		return ret;
	}

	/**
	 * Return a map with all the data available for this station just as they've
	 * been stored.
	 * 
	 * @param variables if no variables passed, return every variable
	 * @param years     if null or empty, all years; if only one value, that year;
	 *                  if two years, in that range (inclusive)
	 * @return
	 * @throws ThinklabException
	 */
	public Map<String, Map<Integer, double[]>> getAllData(String[] variables, int[] years) throws KlabException {

		Map<String, Map<Integer, double[]>> ret = new HashMap<>();
		Set<String> vv = null;
		if (variables != null && variables.length > 0) {
			vv = new HashSet<>();
			for (String v : variables) {
				vv.add(v);
			}
		}

		for (String s : _provided.keySet()) {
			if (vv == null || vv.isEmpty() || vv.contains(s)) {
				Data data = fetch(s);
				if (data != null) {
					if (years == null || years.length == 0) {
						ret.put(s, data.data);
					} else {
						Map<Integer, double[]> wsd = new HashMap<>();
						for (int year : NumberUtils.scanRange(years)) {
							double[] dd = data.data.get(year);
							if (dd != null) {
								wsd.put(year, dd);
							}
						}
						if (!wsd.isEmpty()) {
							ret.put(s, wsd);
						}
					}
				}
			}
		}

		return ret;
	}

	private double[] interpolate(double[] data) {

		int nans = 0;
		double firstValid = Double.NaN, lastValid = Double.NaN;
		for (double d : data) {
			if (Double.isNaN(d)) {
				nans++;
			} else {
				if (Double.isNaN(firstValid))
					firstValid = d;
				lastValid = d;
			}
		}

		if (nans == 0)
			return data;

		/*
		 * if last point is a NaN, set it to the first valid point to enable
		 * interpolation. Do same for first point.
		 */

		if (Double.isNaN(data[0])) {
			nans--;
		}
		if (Double.isNaN(data[data.length - 1])) {
			nans--;
		}

		/*
		 * accept only 10% missing data max FIXME using 90% (no less) because Malawi is
		 * the first case study :(
		 */
		double nanProportion = (double) nans / (double) data.length;
		if (nanProportion > 0.9)
			return null;

		double[] x = new double[data.length - nans];
		double[] y = new double[data.length - nans];

		int n = 0;
		for (int i = 0; i < data.length; i++) {
			double val = data[i];
			if (Double.isNaN(val)) {
				if (i == 0) {
					val = lastValid;
				} else if (i == data.length - 1) {
					val = firstValid;
				}
			}

			if (!Double.isNaN(val)) {
				int idx = n++;
				x[idx] = i;
				y[idx] = val;
			}
		}

		UnivariateInterpolator interpolator = new SplineInterpolator();
		UnivariateFunction function = interpolator.interpolate(x, y);

		double[] ret = new double[data.length];
		for (int i = 0; i < data.length; i++) {
			if (Double.isNaN(data[i])) {
				ret[i] = function.value(i);
			} else {
				ret[i] = data[i];
			}
		}

		return ret;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof WeatherStation && ((WeatherStation) o)._id.equals(_id);
	}

	@Override
	public int hashCode() {
		return _id.hashCode();
	}

	/**
	 * TODO return true if records for the passed variable are available. May be a
	 * multiple one, if so they're in OR.
	 * 
	 * @param var
	 * @return
	 */
	public boolean provides(String var) {

		String[] vv = var.split(",");

		for (String v : vv) {
			if (_provided.containsKey(v)) {
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}

	public int getLastKnownYear() {
		return _lastKnownYear;
	}

	public int getFirstKnownYear() {
		return _firstKnownYear;
	}

	public String getId() {
		return _id;
	}

	/**
	 * Get a data record that may or is guaranteed to contain the given var for the
	 * given year, according to parameters.
	 * 
	 * @param var
	 * @param year
	 * @param useWeatherGenerator
	 * @param returnNoData
	 * @param useMostRecentData
	 * @return never null if returnNoData is passed.
	 * @throws ThinklabException
	 */
	public Data getYearData(String var, int year, boolean useWeatherGenerator, boolean returnNoData,
			boolean useMostRecentData) throws KlabException {

		Data ret = fetch(var);

		if (ret.data.get(year) == null && useWeatherGenerator) {
			/*
			 * try with the weather generator, and see if after that we have the variable.
			 */
			try {
				generateWeather(year);
			} catch (Throwable e) {
				// can't generate weather: just log error and use nodata if allowed
				// downstream.
				Logging.INSTANCE.error(
						"weather generation for year " + year + " in " + _id + " failed: data may be insufficient");
				wg = null;
				wgFailure = true;
			}
		}

		if (ret.data.get(year) == null && useMostRecentData) {
			generateDataFromMostRecentRecord(year);
		}

		if (ret.data.get(year) == null && returnNoData) {
			ret.setNodata(year);
		}

		return ret;
	}

	public void setMaxYearsBack(int maxYearsBack) {
		maxRecordAge = maxYearsBack;
	}

	// for the DB serializer
	String[] getProvidedVarsDescriptors() {

		String names = "", start = "", end = "";

		for (String s : _provided.keySet()) {
			Pair<Integer, Integer> yse = _provided.get(s);
			names += (names.isEmpty() ? "" : ",") + s;
			start += (start.isEmpty() ? "" : ",") + yse.getFirst();
			end += (end.isEmpty() ? "" : ",") + yse.getSecond();
		}

		return new String[] { names, start, end };
	}

	// for the DB deserializer
	void parseVarsDescriptors(String names, String start, String end) {

		if (names.isEmpty()) {
			return;
		}

		String[] n = names.split(",");
		String[] s = start.split(",");
		String[] e = end.split(",");

		_provided.clear();
		for (int i = 0; i < n.length; i++) {

			int ss = Integer.parseInt(s[i]);
			int ee = Integer.parseInt(e[i]);

			_provided.put(n[i], new Pair<Integer, Integer>(ss, ee));

			if (_firstKnownYear == 0 || _firstKnownYear > ss) {
				_firstKnownYear = ss;
			}
			if (_lastKnownYear == 0 || _lastKnownYear < ee) {
				_lastKnownYear = ee;
			}
		}
	}

	public Collection<String> getProvidedVariables() {
		return _provided.keySet();
	}

	public void removeData() {

		WeatherFactory.checkStorage();

		for (String s : _provided.keySet()) {
			WeatherFactory.dataMap.remove(_id + ":" + s);
			WeatherFactory.nansMap.remove(_id + ":" + s);
		}
		WeatherFactory.stationWithData.remove(_id);
		WeatherFactory.db.commit();
	}

	public double getLongitude() {
		return getPoint().getX();
	}

	public double getLatitude() {
		return getPoint().getY();
	}

	public String getType() {
		return _type;
	}

	public String getSource() {
		return _source;
	}

	public static void setLocalGHCNDLocation(File file) {
		localGHCNDLocation = file;
	}

	@Override
	public IShape getShape() {
		return _location;
	}

    /**
     * Train a weather generator to represent the year range passed, and from this point
     * on use it to produce any numbers requested (changing the type to SIMULATED). When
     * calling this, we should be sure that there are no NaNs in the data for the years
     * (guaranteed with CRU data) and of course that the data for the training years are
     * available.
     * 
     * @param startYear
     * @param endYear
     * @throws KlabException 
     */
    public void train(int startYear, int endYear) throws KlabException {
        this.wg = new WeatherGenerator(this, startYear, endYear);
    }
	
    // var -> local yearly data, set externally from data to use for matching to other
    // locations
    public Map<String, Double>    refData = new HashMap<>();

    /**
     * If we have initialized the station in a temporal context, this
     * will contain the data available in it, either for weather computation
     * or for creating the state of a weather station subject.
     */
    private Map<String, double[]> data    = new HashMap<>();
    
    /**
     * Use previous training to generate data for one year. Note that this will
     * only generate one year and leave whatever training data were in the data
     * buffer after that. So ensure that only the first year of data is accessed
     * after this is called.
     * 
     * @param year currently not used
     * @param variables unused, either - wg only generates prec, mint and maxt.
     */
    public void generateData(int year, String[] variables) {
        
        if (wg == null) {
            throw new KlabValidationException("cannot generate data in untrained weather station");
        }
        double[] rain = data.get(Weather.PRECIPITATION_MM);
        double[] minT = data.get(Weather.MIN_TEMPERATURE_C);
        double[] maxT = data.get(Weather.MAX_TEMPERATURE_C);
        
        if (rain == null) {
            rain = new double[366];
            data.put(Weather.PRECIPITATION_MM, rain);
        }

        if (minT == null) {
            minT = new double[366];
            data.put(Weather.MIN_TEMPERATURE_C, minT);
        }

        if (maxT == null) {
            maxT = new double[366];
            data.put(Weather.MAX_TEMPERATURE_C, maxT);
        }

        wg.generateDaily(minT, maxT, rain, false);
    }
    
}
