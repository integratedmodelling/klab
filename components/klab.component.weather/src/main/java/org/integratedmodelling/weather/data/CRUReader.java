package org.integratedmodelling.weather.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.general.IPersistentTable;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * Pass a station, read data if necessary.
 * 
 * @author Ferd
 */
public class CRUReader {

	/*
	 * maps CRU varnames in NetCDF files to "official" GHCND identifiers defined in
	 * WeatherFactory. Below is the "legend" offered by CRU, although not all files
	 * are available online. Some others we ignore due to the impossibility of
	 * interpolating to a meaningful daily series. cld cloud cover percentage (%)
	 * ACMH dtr diurnal temperature range degrees Celsius TRAN (new) frs frost day
	 * frequency days FRSD (new) pet potential evapotranspiration millimetres per
	 * day PETM (new) pre precipitation millimetres per month PRCP rhm relative
	 * humidity percentage (%) HPER (new) ssh sunshine duration hours TSUN
	 * (MINUTES!) tmp daily mean temperature degrees Celsius TAVG tmn monthly
	 * average daily minimum temperature degrees Celsius TMIN tmx monthly average
	 * daily maximum temperature degrees Celsius TMAX vap vapour pressure
	 * hectopascals (hPa) VPPP (new) wet wet day frequency days DWPR wnd wind speed
	 * metres per second (m/s) AWND
	 */
	static Map<String, String> variableMap = new HashMap<>();
	static Map<String, String> fileMap = new HashMap<>();
	static Map<String, NetcdfFile> dataFiles = new HashMap<>();

	/*
	 * vars commented out are advertised as provided but the high-resolution files
	 * are not there, or we decided to not use them: Weather.FROST_DAYS_IN_MONTH
	 * Weather.RELATIVE_HUMIDITY_PERCENT Weather.SUNSHINE_DURATION_TOTAL_MINUTES
	 * Weather.WIND_SPEED_M_SEC
	 */
	public static String[] cruVariables = { Weather.PRECIPITATION_MM, Weather.MIN_TEMPERATURE_C,
			Weather.MAX_TEMPERATURE_C, Weather.CLOUD_COVER_PERCENTAGE, Weather.DIURNAL_TEMPERATURE_RANGE_C,
			Weather.WET_DAYS_IN_PERIOD, Weather.POTENTIAL_EVAPOTRANSPIRATION_MM };

	static {

		variableMap.put(Weather.PRECIPITATION_MM, "pre");
		variableMap.put(Weather.MIN_TEMPERATURE_C, "tmn");
		variableMap.put(Weather.MAX_TEMPERATURE_C, "tmx");
		variableMap.put(Weather.CLOUD_COVER_PERCENTAGE, "cld");
		variableMap.put(Weather.DIURNAL_TEMPERATURE_RANGE_C, "dtr");
		variableMap.put(Weather.WET_DAYS_IN_PERIOD, "wet");
		variableMap.put(Weather.POTENTIAL_EVAPOTRANSPIRATION_MM, "pet");

		// variableMap.put(Weather.FROST_DAYS_IN_MONTH, "frs");
		// variableMap.put(Weather.RELATIVE_HUMIDITY_PERCENT, "rhm");
		// variableMap.put(Weather.SUNSHINE_DURATION_TOTAL_MINUTES, "ssh");
		// variableMap.put(Weather.WIND_SPEED_M_SEC, "wnd");

		fileMap.put(Weather.PRECIPITATION_MM, "cru_ts3.23.1901.2014.pre.dat.nc");
		fileMap.put(Weather.MIN_TEMPERATURE_C, "cru_ts3.23.1901.2014.tmn.dat.nc");
		fileMap.put(Weather.MAX_TEMPERATURE_C, "cru_ts3.23.1901.2014.tmx.dat.nc");
		fileMap.put(Weather.CLOUD_COVER_PERCENTAGE, "cru_ts3.23.1901.2014.cld.dat.nc");
		fileMap.put(Weather.DIURNAL_TEMPERATURE_RANGE_C, "cru_ts3.23.1901.2014.dtr.dat.nc");
		fileMap.put(Weather.WET_DAYS_IN_PERIOD, "cru_ts3.23.01.1901.2014.wet.dat.nc");
		fileMap.put(Weather.POTENTIAL_EVAPOTRANSPIRATION_MM, "cru_ts3.23.1901.2014.pet.dat.nc");

		// fileMap.put(Weather.FROST_DAYS_IN_MONTH, "cru_ts3.23.1901.2014.frs.dat.nc");
		// fileMap.put(Weather.RELATIVE_HUMIDITY_PERCENT,
		// "cru_ts3.23.1901.2014.rhm.dat.nc");
		// fileMap.put(Weather.SUNSHINE_DURATION_TOTAL_MINUTES,
		// "cru_ts3.23.1901.2014.ssh.dat.nc");
		// fileMap.put(Weather.WIND_SPEED_M_SEC, "cru_ts3.23.1901.2014.wnd.dat.nc");

	}

	File cruPath;
	/*
	 * all CRU file must have same temporal span, which has been the case so far.
	 */
	DateTime start;
	DateTime end;

	public CRUReader(File file) {
		this.cruPath = file;
	}

	public NetcdfFile getFile(String variable) throws KlabException {

		if (dataFiles.containsKey(variable)) {
			return dataFiles.get(variable);
		}

		String filename = fileMap.get(variable);

		if (filename == null) {
			throw new KlabValidationException("cannot map variable " + variable + " to CRU dataset");
		}

		File file = new File(this.cruPath + File.separator + filename);
		if (!file.exists()) {
			throw new KlabValidationException(
					"CRU data for variable " + variable + " expected in non-existent CRU file " + filename);
		}

		try {

			NetcdfFile nc = NetcdfFile.open(file.toString());
			dataFiles.put(variable, nc);
			return nc;

		} catch (IOException e) {
			throw new KlabIOException(e);
		}

	}

	/**
	 * If files are left open, ensure that this is called at shutdown.
	 */
	public void closeAll() {
		for (NetcdfFile file : dataFiles.values()) {
			try {
				file.close();
			} catch (IOException e) {
				Logging.INSTANCE.warn("error closing NetCDF file " + file);
			}
		}
	}

	public void getTemporalBoundaries() throws KlabException {

		if (this.start == null) {
			try {

				NetcdfFile nc = getFile(Weather.PRECIPITATION_MM);
				Variable vtime = nc.findVariable("time");

				Array firstTime = vtime.read(new int[] { 0 }, new int[] { 1 });
				Array lastTime = vtime.read(new int[] { vtime.getDimension(0).getLength() - 1 }, new int[] { 1 });

				this.start = new DateTime(1900, 1, 1, 0, 0).plusDays(firstTime.getInt(0));
				this.end = new DateTime(1900, 1, 1, 0, 0).plusDays(lastTime.getInt(0));

			} catch (IOException | InvalidRangeException e) {
				throw new KlabIOException(e);
			}
		}
	}

	/**
	 * Read a year of all vars, interpolating from monthly values. Return map of
	 * double[] with interpolated and corrected data.
	 * 
	 * @param ws
	 * @param variables
	 * @throws KlabException
	 */
	public Map<String, double[]> readData(double lat, double lon, int year) throws KlabException {

		Map<String, double[]> ret = new HashMap<>();

		/*
		 * find the time start for the year
		 */
		getTemporalBoundaries();

		if (this.start.getYear() > year || this.end.getYear() < year) {
			throw new KlabValidationException("access to non-existing CRU data for year " + year);
		}

		DateTime startPeriod = new DateTime(year, 1, 1, 0, 0);
		PeriodType month = PeriodType.months();
		Period difference = new Period(this.start, startPeriod, month);
		int timeIdx = difference.getMonths();
		int days = new DateTime(startPeriod.getYear(), 12, 31, 23, 59).getDayOfYear();

		/*
		 * find the cell coordinates for the station
		 */
		int lonIdx = (int) (720 * (lon + 180.0) / 360.0);
		int latIdx = (int) (360 * (lat + 90.0) / 180.0);

		/*
		 * fill in monthly data for all vars
		 */
		Map<String, double[]> monthlyData = new HashMap<>();

		for (String variable : cruVariables) {

			String varname = variableMap.get(variable);
			NetcdfFile nc = getFile(variable);
			Variable var = nc.findVariable(varname);

			double[] data = new double[12];
			try {

				Array value = var.read(new int[] { timeIdx, latIdx, lonIdx }, new int[] { 12, 1, 1 });
				for (int i = 0; i < 12; i++) {
					data[i] = value.getDouble(i);
				}
				monthlyData.put(variable, data);

			} catch (IOException | InvalidRangeException e) {
				throw new KlabIOException(e);
			}
		}

		double[] precData = new double[days];
		double[] wetData = new double[days];
		double[] tminData = new double[days];
		double[] tmaxData = new double[days];
		double[] clouData = new double[days];
		double[] petData = new double[days];
		double[] tranData = new double[days];

		/*
		 * distribute precipitation and wet days
		 */
		prdaily(startPeriod, monthlyData.get(Weather.PRECIPITATION_MM), precData, wetData,
				monthlyData.get(Weather.WET_DAYS_IN_PERIOD), -1, false);

		ret.put(Weather.PRECIPITATION_MM, precData);
		ret.put(Weather.WET_DAYS_IN_PERIOD, wetData);

		/*
		 * do the rest
		 */
		interp_monthly_means_conserve(startPeriod, monthlyData.get(Weather.MIN_TEMPERATURE_C), tminData,
				-Double.MAX_VALUE, Double.MAX_VALUE);
		interp_monthly_means_conserve(startPeriod, monthlyData.get(Weather.MAX_TEMPERATURE_C), tmaxData,
				-Double.MAX_VALUE, Double.MAX_VALUE);

		/*
		 * unfortunately this may happen, so at least make it "right"
		 */
		for (int i = 0; i < days; i++) {
			double tmin = tminData[i];
			double tmax = tmaxData[i];
			if (tmin > tmax) {
				tminData[i] = tmax;
				tmaxData[i] = tmin;
			}
		}

		interp_monthly_means_conserve(startPeriod, monthlyData.get(Weather.CLOUD_COVER_PERCENTAGE), clouData, 0, 100);
		interp_monthly_means_conserve(startPeriod, monthlyData.get(Weather.DIURNAL_TEMPERATURE_RANGE_C), tranData,
				-Double.MAX_VALUE, Double.MAX_VALUE);
		interp_monthly_totals_conserve(startPeriod, monthlyData.get(Weather.POTENTIAL_EVAPOTRANSPIRATION_MM), petData,
				-Double.MAX_VALUE, Double.MAX_VALUE);

		ret.put(Weather.MIN_TEMPERATURE_C, tminData);
		ret.put(Weather.MAX_TEMPERATURE_C, tmaxData);
		ret.put(Weather.CLOUD_COVER_PERCENTAGE, clouData);
		ret.put(Weather.DIURNAL_TEMPERATURE_RANGE_C, tranData);
		ret.put(Weather.POTENTIAL_EVAPOTRANSPIRATION_MM, petData);

		return ret;
	}

	/**
	 * Create a CRU station at each land grid cell, which will cache their data on
	 * demand.
	 * 
	 * @throws Exception
	 */
	public void createStations(IPersistentTable<String, WeatherStation> wbox, IMonitor monitor) throws KlabException {

		/*
		 * if we have this, the last one built by the algorithm below, we have them all.
		 */
		String LAST_STATION_ID = "CRU_719_322";
		WeatherStation ws = wbox.retrieve(LAST_STATION_ID);
		if (ws != null) {
			return;
		}

		/*
		 * ensure we have start/end set
		 */
		getTemporalBoundaries();

		File precfile = new File(this.cruPath + File.separator + fileMap.get(Weather.PRECIPITATION_MM));
		NetcdfFile nc;
		try {
			nc = NetcdfFile.open(precfile.toString());
		} catch (IOException e) {
			throw new KlabIOException(e);
		}

		Variable var = nc.findVariable(variableMap.get(Weather.PRECIPITATION_MM));
		Variable vlat = nc.findVariable("lat");
		Variable vlon = nc.findVariable("lon");

		// just pick this. If we have NaNs in legitimate data cells, we'll need a much
		// more complicated strategy.
		int time = 990;
		int nStations = 0;

		for (int lonIdx = 0; lonIdx < 720; lonIdx++) {
			for (int latIdx = 0; latIdx < 360; latIdx++) {

				try {
					/*
					 * get precipitation for some point in time and continue if NaN
					 */
					Array value = var.read(new int[] { time, latIdx, lonIdx }, new int[] { 1, 1, 1 });

					/*
					 * assumptions, assumptions. We know CRU uses Double.MAX_VALUE for NaNs, so
					 * being this precipitation, this should be a fine NaN test.
					 */
					if (value.getDouble(0) < 10000) {

						String stationId = "CRU_" + lonIdx + "_" + latIdx;

						Array vvlat = vlat.read(new int[] { latIdx }, new int[] { 1 });
						Array vvlon = vlon.read(new int[] { lonIdx }, new int[] { 1 });

						double lat = vvlat.getDouble(0);
						double lon = vvlon.getDouble(0);

						ws = new WeatherStation(stationId, lon, lat, start.getYear(), end.getYear());

						wbox.store(ws, monitor);

						Logging.INSTANCE.info("CRU station created: " + ws);

						nStations++;
					}
				} catch (IOException | InvalidRangeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Logging.INSTANCE.info("Created " + nStations + " CRU weather stations");

		if (nc != null) {
			try {
				nc.close();
			} catch (IOException e) {
				// OK, screw it
			}
		}
	}

	/**
	 * Distribution of monthly precipitation totals to quasi-daily values. From LPJ
	 * code, method in Dieter & Gerten 2002.
	 * 
	 * @param mval_prec total rainfall (mm) for month
	 * @param dval_prec actual rainfall (mm) for each day of year
	 * @param mval_wet  expected number of rain days for month
	 * @param seed      seed for generating random numbers. Pass -1 for
	 *                  non-repeatable.
	 * @param truncate  if set to true the function will set small daily values (<
	 *                  0.1) to zero.
	 */
	void prdaily(DateTime yeardate, double[] mval_prec, double[] dval_prec, double[] dval_wet, double[] mval_wet,
			long seed, boolean truncate) {

		// Distribution of monthly precipitation totals to quasi-daily values
		// (From Dieter Gerten 021121)

		java.util.Random random = seed < 0 ? new java.util.Random() : new java.util.Random(seed);

		double c1 = 1.0; // normalising coefficient for exponential distribution
		double c2 = 1.2; // power for exponential distribution

		int m, d, dy, dyy, dy_hold;
		int daysum;
		double prob_rain; // daily probability of rain for this month
		double mprec; // average rainfall per rain day for this month
		double mprec_sum; // cumulative sum of rainfall for this month
		// (= mprecip in Dieter's code)
		double prob;

		dy = 0;
		daysum = 0;

		for (m = 0; m < 12; m++) {

			DateTime date = new DateTime(yeardate.getYear(), m + 1, 1, 0, 0);

			if (mval_prec[m] < 0.1) {

				// Special case if no rainfall expected for month

				for (d = 0; d < date.dayOfMonth().getMaximumValue(); d++) {
					dval_prec[dy] = 0.0;
					dval_wet[dy] = 0.0;
					dy++;
				}
			} else {

				mprec_sum = 0.0;

				mval_wet[m] = Math.max(mval_wet[m], 1.0);
				// force at least one rain day per month

				// rain on wet days (should be at least 0.1)
				mprec = Math.max(mval_prec[m] / mval_wet[m], 0.1);
				mval_wet[m] = mval_prec[m] / mprec;

				prob_rain = mval_wet[m] / (double) date.dayOfMonth().getMaximumValue();

				dy_hold = dy;

				while (negligible(mprec_sum)) {

					dy = dy_hold;

					for (d = 0; d < date.dayOfMonth().getMaximumValue(); d++) {

						// Transitional probabilities (Geng et al 1986)

						if (dy == 0) { // first day of year only
							prob = 0.75 * prob_rain;
						} else {
							if (dval_prec[dy - 1] < 0.1)
								prob = 0.75 * prob_rain;
							else
								prob = 0.25 + (0.75 * prob_rain);
						}

						// Determine wet days randomly and use Krysanova/Cramer estimates
						// of
						// parameter values (c1,c2) for an exponential distribution

						if (random.nextDouble() > prob)
							dval_prec[dy] = 0.0;
						else {
							double x = random.nextDouble();
							dval_prec[dy] = Math.pow(-Math.log(x), c2) * mprec * c1;
							if (dval_prec[dy] < 0.1) {
								dval_prec[dy] = 0.0;
							} else {
								dval_wet[dy] = 1;
							}
						}

						mprec_sum += dval_prec[dy];
						dy++;
					}

					// Normalise generated precipitation by prescribed monthly totals

					if (!negligible(mprec_sum)) {
						for (d = 0; d < date.dayOfMonth().getMaximumValue(); d++) {
							dyy = daysum + d;
							dval_prec[dyy] *= mval_prec[m] / mprec_sum;
							if (truncate && dval_prec[dyy] < 0.1)
								dval_prec[dyy] = 0.0;
						}
					}
				}
			}

			daysum += date.dayOfMonth().getMaximumValue();
		}
	}

	/**
	 * May be called from input/output module to generate daily climate values when
	 * raw data are on monthly basis. The generated daily values will have the same
	 * monthly means as the input. \param mvals The monthly means \param dvals The
	 * generated daily values
	 */
	void interp_monthly_means_conserve(DateTime yeardate, double[] mvals, double[] dvals, double minimum,
			double maximum) {

		int start_of_month = 0;

		for (int m = 0; m < 12; m++) {

			DateTime date = new DateTime(yeardate.getYear(), m + 1, 1, 0, 0);

			// Index of previous and next month, with wrap-around
			int next = (m + 1) % 12;
			int prev = (m + 11) % 12;

			// If a monthly mean value is outside of the allowed limits for daily
			// values (for instance negative radiation), we'll fail to make sure
			// the user knows the forcing data is broken.
			if (mvals[m] < minimum || mvals[m] > maximum) {
				throw new KlabException("interp_monthly_means_conserve: Invalid monthly value given " + mvals[m]
						+ ", min = " + minimum + ", max = " + maximum);
			}

			interp_single_month(mvals[prev], mvals[m], mvals[next], date.dayOfMonth().getMaximumValue(), dvals,
					start_of_month, minimum, maximum);

			start_of_month += date.dayOfMonth().getMaximumValue();
		}

	}

	/**
	 * May be called from input/output module to generate daily climate values when
	 * raw data are on monthly basis. The generated daily values will have the same
	 * monthly totals as the input. \param mvals The monthly totals \param dvals The
	 * generated daily values
	 */
	void interp_monthly_totals_conserve(DateTime yeardate, double[] mvals, double[] dvals, double minimum,
			double maximum) {
		// Convert monthly totals to mean daily values
		double[] mvals_daily = new double[12];
		for (int m = 0; m < 12; m++) {
			DateTime date = new DateTime(yeardate.getYear(), m + 1, 1, 0, 0);
			mvals_daily[m] = mvals[m] / (double) date.dayOfMonth().getMaximumValue();
		}
		interp_monthly_means_conserve(yeardate, mvals_daily, dvals, minimum, maximum);
	}

	/**
	 * The dry component is simply spread out over all days, the wet deposition is
	 * distributed over days with precipitation (or evenly over all days if there is
	 * no precipitation). \see distribute_ndep \param ndry Dry N deposition (monthly
	 * mean of daily deposition) \param nwet Wet N deposition (monthly mean of daily
	 * deposition) \param time_steps Number of days in the month \param dprec Array
	 * of precipitation values \param dndep Output, total N deposition for each day
	 */
	void distribute_ndep_single_month(double ndry, double nwet, int time_steps, double[] dprec, double[] dndep,
			int offset) {

		// First count number of days with precipitation
		int raindays = 0;

		for (int i = 0; i < time_steps; i++) {
			if (!negligible(dprec[i + offset])) {
				raindays++;
			}
		}

		// Distribute the values
		for (int i = 0; i < time_steps; i++) {

			// ndry is included in all days
			dndep[i + offset] = ndry;

			if (raindays == 0) {
				dndep[i + offset] += nwet;
			} else if (!negligible(dprec[i + offset])) {
				dndep[i + offset] += (nwet * time_steps) / raindays;
			}
		}
	}

	/**
	 * See distribute_ndep_single_month for details about how the distribution is
	 * done.
	 *
	 * @param mndry Monthly means of daily dry N deposition
	 * @param mnwet Monthly means of daily wet N deposition
	 * @param dprec Daily precipitation data
	 * @param dndep Output, total N deposition for each day
	 */
	void distribute_ndep(DateTime yeardate, double[] mndry, double[] mnwet, double[] dprec, double[] dndep) {

		int start_of_month = 0;

		for (int m = 0; m < 12; m++) {
			DateTime date = new DateTime(yeardate.getYear(), m + 1, 1, 0, 0);
			distribute_ndep_single_month(mndry[m], mnwet[m], date.dayOfMonth().getMaximumValue(), dprec, dndep,
					start_of_month);

			start_of_month += date.dayOfMonth().getMaximumValue();
		}
	}

	/**
	 * The generated daily values will conserve the monthly mean. The daily values
	 * are generated by first choosing values for the beginning, middle and end of
	 * the month, and interpolating linearly between them. The end points will be
	 * chosen by taking the surrounding months into account, and the mid point is
	 * then chosen so that we conserve the mean. Could be used for other
	 * interpolations than only monthly to daily, but comments assume monthly to
	 * daily to avoid being too abstract.
	 *
	 * @param preceding_mean  Mean value for preceding month
	 * @param this_mean       Mean value for the current month
	 * @param succeeding_mean Mean value for the succeeding month
	 * @param time_steps      Number of days in the current month
	 * @param result          The generated daily values (array expected to hold at
	 *                        least time_steps values)
	 * @param offset          offset into result
	 */
	void interp_single_month(double preceding_mean, double this_mean, double succeeding_mean, int time_steps,
			double[] result, int offset, double minimum, double maximum) {

		// The values for the beginning and the end of the month are determined
		// from the average of the two adjacent monthly means
		double first_value = mean(this_mean, preceding_mean);
		double last_value = mean(this_mean, succeeding_mean);

		// The mid-point value is computed as offset from the mean, so that the
		// average deviation from the mean of first_value and last_value
		// is compensated for.
		// E.g., if the two values at beginning and end of the month are on average
		// 2 degrees cooler than the monthly mean, the mid-monthly value is
		// determined as monthly mean + 2 degrees, so that the monthly mean is
		// conserved.
		double average_deviation = mean(first_value - this_mean, last_value - this_mean);

		double middle_value = this_mean - average_deviation;
		double half_time = time_steps / 2.0;

		double first_slope = (middle_value - first_value) / half_time;
		double second_slope = (last_value - middle_value) / half_time;

		double sum = 0;
		int i = 0;

		// Interpolate the first half
		for (; i < time_steps / 2; ++i) {
			double current_time = i + 0.5; // middle of day i
			result[i + offset] = first_value + first_slope * current_time;
			sum += result[i + offset];
		}

		// Special case for dealing with the middle day if time_steps is odd
		if (time_steps % 2 == 1) {
			// In this case we can't use the value corresponding to the middle
			// of the day. We'll simply skip it and calculate it based on
			// whatever the other days sum up to.
			++i;
		}

		// Interpolate the other half
		for (; i < time_steps; ++i) {
			double current_time = i + 0.5; // middle of day i
			result[i + offset] = middle_value + second_slope * (current_time - half_time);
			sum += result[i + offset];
		}

		if (time_steps % 2 == 1) {
			// Go back and set the middle value to whatever is needed to
			// conserve the mean
			result[(time_steps / 2) + offset] = time_steps * this_mean - sum;
		}

		// Go through all values and make sure they're all above the minimum
		double added = 0;
		double sum_above = 0;

		for (i = 0; i < time_steps; ++i) {
			if (result[i + offset] < minimum) {
				added += minimum - result[i + offset];
				result[i + offset] = minimum;
			} else {
				sum_above += result[i + offset] - minimum;
			}
		}

		double fraction_to_remove = sum_above > 0 ? added / sum_above : 0;

		for (i = 0; i < time_steps; ++i) {
			if (result[i + offset] > minimum) {
				result[i + offset] -= fraction_to_remove * (result[i + offset] - minimum);

				// Needed (only) due to limited precision in floating point arithmetic
				result[i + offset] = Math.max(result[i + offset], minimum);
			}
		}

		// Go through all values and make sure they're all below the maximum
		double removed = 0;
		double sum_below = 0;

		for (i = 0; i < time_steps; ++i) {
			if (result[i + offset] > maximum) {
				removed += result[i + offset] - maximum;
				result[i + offset] = maximum;
			} else {
				sum_below += maximum - result[i + offset];
			}
		}

		double fraction_to_add = sum_below > 0 ? removed / sum_below : 0;

		for (i = 0; i < time_steps; ++i) {
			if (result[i + offset] < maximum) {
				result[i + offset] += fraction_to_add * (maximum - result[i + offset]);

				// Needed (only) due to limited precision in floating point arithmetic
				result[i + offset] = Math.min(result[i + offset], maximum);
			}
		}
	}

	public static boolean negligible(double dval) {
		final double EPSILON = 1.0e-30;
		if (dval > EPSILON) {
			return false;
		}
		if (dval < 0.0 && dval < -EPSILON) {
			return false;
		}
		return true;
	}

	public static double mean(double d1, double d2) {
		return (d1 + d2) / 2;
	}

	public File getDataFile(String variable) {
		return new File(cruPath + File.separator + fileMap.get(variable));
	}
}
