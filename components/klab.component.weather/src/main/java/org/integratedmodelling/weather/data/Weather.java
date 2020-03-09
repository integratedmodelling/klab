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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * The weather in a location. Also serves as a factory to obtain the weather
 * given a lat/long pair, a frequency of observation, a date range and a set of
 * variables. Will use the GHCN dataset and if necessary a weather generator to
 * fulfill the request, picking the closest record to the requested location.
 * Weather can be modified with point events or bias and trends.
 * 
 * @author Ferd
 *
 */
public class Weather {

	/*
	 * variable keys matched to GHCN codes. There's more. TODO need cloudiness,
	 * insolation/shortwave radiation
	 */
	public final static String PRECIPITATION_MM = "PRCP";
	public final static String SNOWFALL_MM = "SNOW";
	public final static String SNOWDEPTH_MM = "SNDP";
	public final static String MAX_TEMPERATURE_C = "TMAX";
	public final static String MIN_TEMPERATURE_C = "TMIN";

	/**
	 * Used for the undescript one in CRU; will only match the "manual" kind in
	 * GHNC.
	 */
	public final static String CLOUD_COVER_PERCENTAGE = "ACMH";

	/**
	 * Only in CRU
	 */
	public final static String DIURNAL_TEMPERATURE_RANGE_C = "TRAN";

	/**
	 * Only in CRU
	 */
	public final static String FROST_DAYS_IN_MONTH = "FRSD";

	/**
	 * Only in CRU
	 */
	public final static String POTENTIAL_EVAPOTRANSPIRATION_MM = "PETM";

	/**
	 * Only in CRU
	 */
	public final static String RELATIVE_HUMIDITY_PERCENT = "HPER";

	/**
	 * 
	 */
	public final static String SUNSHINE_DURATION_TOTAL_MINUTES = "TSUN";

	/**
	 * 
	 */
	public final static String WET_DAYS_IN_PERIOD = "DWPR";

	/**
	 * CRU mappings:
	 * 
	 * <pre>
	 * cld cloud cover percentage (%) ACMH 
	* dtr diurnal temperature range degrees Celsius TRAN (new) 
	* frs frost day frequency days FRSD (new) 
	* pet potential evapotranspiration millimetres per day PETM (new) 
	* pre precipitation millimetres per month PRCP 
	* rhm relative humidity percentage (%) HPER (new) 
	* ssh sunshine duration hours TSUN (MINUTES!) 
	* tmp daily mean temperature degrees Celsius TAVG 
	* tmn monthly average daily minimum temperature degrees Celsius TMIN 
	* tmx monthly average daily maximum temperature degrees Celsius TMAX 
	* vap vapour pressure hectopascals (hPa) VPPP (new) 
	* wet wet day frequency days DWPR 
	* wnd wind speed metres per second (m/s) AWND
	 * </pre>
	 */

	// this one will (should) get the first available of the 4 choices.
	public final static String WIND_SPEED_M_SEC = "AWND";

	public static Set<String> varCatalog = new HashSet<>();
	public static Set<String> extensiveVars = new HashSet<>();

	static {
		varCatalog.add(PRECIPITATION_MM);
		varCatalog.add(SNOWFALL_MM);
		varCatalog.add(SNOWDEPTH_MM);
		varCatalog.add(MAX_TEMPERATURE_C);
		varCatalog.add(MIN_TEMPERATURE_C);
		varCatalog.add(WIND_SPEED_M_SEC);
		// TODO the rest
		extensiveVars.add(PRECIPITATION_MM);
		extensiveVars.add(SNOWFALL_MM);
	}

	/*
	 * minimum number of stations we'll use before taking those with lots of
	 * no-data. We will use even one station in the end, this is just to filter out
	 * the bad ones if there is enough information in others.
	 */
	private static final int MIN_STATIONS = 5;
	List<WeatherStation> _stations = new ArrayList<WeatherStation>();
	private Collection<Map<String, Object>> stationData;

	/**
	 * This one is for the service - the resulting weather won't have any spatial
	 * interpolation but contain data for all usable stations, precisely resampled
	 * and ready to ship through a StationData object.
	 *
	 * @throws ThinklabException
	 */
	public Weather(Collection<WeatherStation> stations, long start, long end, long step, int maxYearsBack,
			String[] variables, int maxAcceptableNodataPercentage, boolean interpolateNodata) throws KlabException {

		int year = Time.getYear(start);
		ArrayList<WeatherStation> rejected = new ArrayList<>();

		for (WeatherStation s : stations) {
			s.setMaxYearsBack(maxYearsBack);
			try {
				
				/*
				 * FIXME use vars, not these
				 */
				if (s.hasUsableDataFor(year, PRECIPITATION_MM, MAX_TEMPERATURE_C, MIN_TEMPERATURE_C)) {

					// fast check
					s.cacheData(true);

					/*
					 * FIXME use vars, not the predefined ones
					 */
					if (!s.hasEnoughDataFor(start, end, maxAcceptableNodataPercentage, PRECIPITATION_MM,
							MAX_TEMPERATURE_C, MIN_TEMPERATURE_C)) {
						/*
						 * temporarily discard; will get them later if we have no other option.
						 */
						rejected.add(s);
					} else {
						_stations.add(s);
					}
				}
			} catch (Throwable e) {
				// just don't use it.
			}
		}

		if (_stations.size() < MIN_STATIONS) {
			int needed = Math.min(MIN_STATIONS, rejected.size()) - _stations.size();
			for (int i = 0; i < needed; i++) {
				/* TODO mark these somehow */
				_stations.add(rejected.get(i));
			}
		}

		stationData = new ArrayList<>();

		for (WeatherStation ws : _stations) {

			Map<String, Object> wmap = new HashMap<>();

			wmap.put("id", ws.getId());
			wmap.put("lat", ws.getLatitude());
			wmap.put("lon", ws.getLongitude());
			wmap.put("elevation", ws.getElevation());

			for (String var : variables) {
				List<Double> vdata = new ArrayList<>();
				for (long current = start; current <= end; current += step) {
					vdata.add(getAggregatedData(ws, var, current, current + step));
				}
				wmap.put(var, copyData(vdata));
			}
			stationData.add(wmap);
		}
	}

	/**
	 * Get all the stations we retained from the original set.
	 * 
	 * @return
	 */
	public List<WeatherStation> getStations() {
		return _stations;
	}
	
	/**
	 * Return the result of aggregating the daily data between two timepoints. If
	 * the first and last day partially overlap the interval, adjust the values
	 * according to their physical nature.
	 * 
	 * @param variable
	 * @param start
	 * @param end
	 * @return
	 * @throws ThinklabException
	 */
	public double getAggregatedData(WeatherStation ws, String variable, long start, long end) throws KlabException {

		Interval span = new Interval(start, end);

		double ret = Double.NaN;
		double wsum = 0;

		for (int year : Time.yearsBetween(start, end)) {
			double[] data = ws.getYearData(variable, year, false, true, true).data.get(year);
			for (int day : Time.daysBetween(start, end, year)) {

				double dayData = data[day];

				if (Double.isNaN(dayData)) {
					if (extensiveVars.contains(variable)) {
						// no way for extensive variables to be estimated if a value is NaN
						return Double.NaN;
					} else {
						// averaging what we have if intensive
						continue;
					}
				}

				DateTime begDay = Time.dateAt(year, day);
				Interval daySpan = new Interval(begDay.getMillis(), begDay.plusDays(1).getMillis());
				Interval overlap = span.overlap(daySpan);
				if (overlap == null) {
					continue;
				}
				float ratio = (float) (overlap.getEndMillis() - overlap.getStartMillis())
						/ (float) (daySpan.getEndMillis() - daySpan.getStartMillis());

				if (Double.isNaN(ret)) {
					ret = ratio * dayData;
				} else {
					ret += ratio * dayData;
				}

				wsum += ratio;
			}
		}

		if (!Double.isNaN(ret) && wsum > 0 && !extensiveVars.contains(variable)) {
			ret /= wsum;
		}

		return ret;

	}

	private double[] copyData(List<Double> vdata) {
		double[] ret = new double[vdata.size()];
		for (int i = 0; i < vdata.size(); i++) {
			ret[i] = vdata.get(i);
		}
		return ret;
	}

	public Collection<Map<String, Object>> getStationData() {
		return stationData;
	}

}
