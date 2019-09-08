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

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Triple;

/**
 * Ported from C++ weathergen package on http://weathergen.sourceforge.net
 * 
 * See: Birt et al (2010): A simple stochastic weather generator for ecological
 * modeling. Env. Mod. Software 25: 1252-1255
 * 
 * @author Ferd
 *
 */
public class WeatherGenerator {

	final private static double EPS = 0.00001;

	final static int GENERATEYEARS = 1; // number
										// of
										// years
										// to
										// be
										// generated
	final static int NUMDAYS = 366; // number
									// of
									// days.
	// Data members are stored with indices 1-365 (day 0 is always blank).
	// The program currently throws out Feb. 29 of a leap year,
	// but if a leap year were dsired, change this value to 367.
	// (Daymet website already excludes Feb. 29.)
	final static int NUMMONTHS = 12; // number
										// of
										// months
	final static int NUMINTERVALS = 10; // for
										// semi-empirical
										// distributions

	final int jul[] = new int[] { 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 }; // last
																						// julian
																						// day
																						// for
																						// each
																						// month
	final int daysPerMonth[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	// stores daily stats for each of 365 days
	class DailyData {
		int wetDays;
		int dryDays;
		double wetMinTempSum;
		double wetMaxTempSum;
		double dryMinTempSum;
		double dryMaxTempSum;
		double wetMinTempStdDev;
		double wetMaxTempStdDev;
		double dryMinTempStdDev;
		double dryMaxTempStdDev;
	};

	DailyData[] dailyStats = new DailyData[NUMDAYS];

	WeatherStation _station; // name of
								// historical
								// data file
	int m_yearCount; // number of
						// years of
						// historical
						// data used

	double initMinTemp, initMaxTemp;

	// following are the various correlation coefficients
	double m_rhoMin; // between
						// minimum
						// temperatures
						// on
						// consecutive
						// days
	double m_rhoMax; // between
						// maximum
						// temperatures
						// on
						// consecutive
						// days
	double m_rhoDaily; // between
						// min and
						// max
						// temperatures
						// on the
						// same day
	double m_rhoMinMax; // between
						// min
						// temperature
						// and max
						// temperature
						// on the
						// next day
	double m_rhoMaxMin; // between
						// max
						// temperature
						// and min
						// temperature
						// on the
						// next day

	// temperature data from the end of the year to start next year, if desired
	// first two are for rain data, next four are temperature data
	boolean m_wetSeries;
	int m_seriesLength;
	double m_minPrevResidual;
	double m_minPrevStdDev;
	double m_maxPrevResidual;
	double m_maxPrevStdDev;

	// the following vectors contain the temperature parameters the fourier
	// transform
	double[] m_minWetParameters = new double[7];
	double[] m_maxWetParameters = new double[7];
	double[] m_minDryParameters = new double[7];
	double[] m_maxDryParameters = new double[7];
	double[] m_minWetStdDevParameters = new double[7];
	double[] m_maxWetStdDevParameters = new double[7];
	double[] m_minDryStdDevParameters = new double[7];
	double[] m_maxDryStdDevParameters = new double[7];

	double[] minTempWetParameters = new double[7];
	double[] maxTempWetParameters = new double[7];
	double[] minTempDryParameters = new double[7];
	double[] maxTempDryParameters = new double[7];
	double[] minTempWetStdDevParameters = new double[7];
	double[] maxTempWetStdDevParameters = new double[7];
	double[] minTempDryStdDevParameters = new double[7];
	double[] maxTempDryStdDevParameters = new double[7];

	int[][] wetLengthFreq = new int[NUMMONTHS][NUMINTERVALS];
	double[][] wetLengthBounds = new double[NUMMONTHS][NUMINTERVALS + 1];
	int[][] dryLengthFreq = new int[NUMMONTHS][NUMINTERVALS];
	double[][] dryLengthBounds = new double[NUMMONTHS][NUMINTERVALS + 1];
	int[][] rainFreq = new int[NUMMONTHS][NUMINTERVALS];
	double[][] rainBounds = new double[NUMMONTHS][NUMINTERVALS + 1];
	double rhoMinMin; // correlation
						// between
						// minimum
						// temperatures
						// on
						// subsequent
						// days
	double rhoMaxMax; // correlation
						// between
						// maximum
						// temperatures
						// on
						// subsequent
						// days
	double rhoDaily; // correlation
						// between
						// min and
						// max
						// temperatures
						// on the
						// same day
	double rhoMinMax; // correlation
						// of the min
						// temperature
						// with the
						// max
						// temperature
						// on the
						// next day
	double rhoMaxMin; // correlation
						// of the max
						// temperature
						// with the
						// min
						// temperature
						// on the
						// next day
	double ssqMin, ssqMax;

	// Bernoulli distribution giving prob rain on Dec 31
	// used in case of random start
	Random.Bernoulli m_probRain;
	// normal distribution for temperature
	Random.Normal m_tempDist;
	// semi-empirical distributions for length of wet & dry series and rain
	Random.SemiEmpirical[] m_wetDist = new Random.SemiEmpirical[NUMMONTHS];
	Random.SemiEmpirical[] m_dryDist = new Random.SemiEmpirical[NUMMONTHS];
	Random.SemiEmpirical[] m_rainDist = new Random.SemiEmpirical[NUMMONTHS];

	double[] rainMax = new double[12]; // stores max
										// amount of
										// rain in cm
										// for a
										// given
										// month
	double[] xTwetMinTemp = new double[7]; // X'*Y -- '
											// denotes
											// transpose
	double[] xTwetMaxTemp = new double[7];
	double[] xTdryMinTemp = new double[7];
	double[] xTdryMaxTemp = new double[7];
	double[] xTdryMinTempStdDev = new double[7];
	double[] xTdryMaxTempStdDev = new double[7];
	double[] xTwetMinTempStdDev = new double[7];
	double[] xTwetMaxTempStdDev = new double[7];

	int[] maxConsWetDays = new int[NUMMONTHS]; // stores max
												// # of
												// consecutive
												// wet days
												// for a
												// given
												// month
	int[] maxConsDryDays = new int[NUMMONTHS]; // stores max
												// # of
												// consecutive
												// dry days
												// for a
												// given
												// month
	double[][] wetTempStdDevRegMatrix = new double[7][7]; // X'*X
															// matrix for
															// the
															// regressions
															// on
															// standard
															// deviations
	double[][] dryTempStdDevRegMatrix = new double[7][7];
	double[][] wetRegMatrix = new double[7][7]; // X'*X
												// matrix for
												// the
												// regressions
												// on
												// standard
												// deviations
	double[][] dryRegMatrix = new double[7][7];

	double Cinv[][] = new double[3][3];

	// IMonitor _monitor;

	public WeatherGenerator(WeatherStation station) throws KlabException {
		this(station, 0, 0);
	}

	public WeatherGenerator(WeatherStation station, int strm, int yearUpTo) throws KlabException {

		for (int i = 0; i < NUMDAYS; i++) {
			dailyStats[i] = new DailyData();
		}

		// _monitor = monitor;

		// name = filename;
		double holdVal, holdMean, finalMin = 0.0, finalMax = 0.0;
		int holdN;

		_station = station;

		Triple<Integer, Double, Double> zio = readData(station, finalMin, finalMax, true);

		if (zio == null) {
			throw new KlabException("cannot find enough data to simulate weather in station " + station);
		}

		m_yearCount = zio.getFirst();
		finalMin = zio.getSecond();
		finalMax = zio.getThird();

		// finish filling out the symmetric regression matrices for wet and dry series.
		constructRegMatrix(wetRegMatrix);
		constructRegMatrix(dryRegMatrix);

		// initialize arrays for bounds and frequencies of the semi-empirical
		// distributions
		initializeBounds();

		// process regression matrices for min and max temperature
		inv_in_place(wetRegMatrix, 7);
		inv_in_place(dryRegMatrix, 7);

		// calulate a0, a1, a2 regression coefficients for wet & dry, min & max mean
		// temperatures
		calcParameters(minTempWetParameters, wetRegMatrix, xTwetMinTemp);
		calcParameters(maxTempWetParameters, wetRegMatrix, xTwetMaxTemp);
		calcParameters(minTempDryParameters, dryRegMatrix, xTdryMinTemp);
		calcParameters(maxTempDryParameters, dryRegMatrix, xTdryMaxTemp);

		// calculate the daily stan dev. store by day into dailyStats. // historical
		// data
		for (int i = 1; i < NUMDAYS; ++i) {
			holdN = dailyStats[i].wetDays;
			if (holdN > 1) {
				holdMean = fourierFit(minTempWetParameters, i);
				holdVal = dailyStats[i].wetMinTempStdDev + holdN * holdMean * holdMean;
				holdVal -= 2.0 * dailyStats[i].wetMinTempSum * holdMean;
				dailyStats[i].wetMinTempStdDev = Math.sqrt(holdVal / (holdN - 1));
				holdMean = fourierFit(maxTempWetParameters, i);
				holdVal = dailyStats[i].wetMaxTempStdDev + holdN * holdMean * holdMean;
				holdVal -= 2.0 * dailyStats[i].wetMaxTempSum * holdMean;
				dailyStats[i].wetMaxTempStdDev = Math.sqrt(holdVal / (holdN - 1));
			} else {
				dailyStats[i].wetMinTempStdDev = -1.0;
				dailyStats[i].wetMaxTempStdDev = -1.0;
			}
			holdN = dailyStats[i].dryDays;
			if (holdN > 1) {
				holdMean = fourierFit(minTempDryParameters, i);
				holdVal = dailyStats[i].dryMinTempStdDev + holdN * holdMean * holdMean;
				holdVal -= 2.0 * dailyStats[i].dryMinTempSum * holdMean;
				dailyStats[i].dryMinTempStdDev = Math.sqrt(holdVal / (holdN - 1));
				holdMean = fourierFit(maxTempDryParameters, i);
				holdVal = dailyStats[i].dryMaxTempStdDev + holdN * holdMean * holdMean;
				holdVal -= 2.0 * dailyStats[i].dryMaxTempSum * holdMean;
				dailyStats[i].dryMaxTempStdDev = Math.sqrt(holdVal / (holdN - 1));
			} else {
				dailyStats[i].dryMinTempStdDev = -1.0;
				dailyStats[i].dryMaxTempStdDev = -1.0;
			}
		}

		for (int i = 1; i < NUMDAYS; i++) {
			if (dailyStats[i].wetMinTempStdDev != -1) {
				constructRegMatrix(wetTempStdDevRegMatrix, xTwetMinTempStdDev, xTwetMaxTempStdDev, i,
						dailyStats[i].wetMinTempStdDev, dailyStats[i].wetMaxTempStdDev);
			}
			if (dailyStats[i].dryMinTempStdDev != -1) {
				constructRegMatrix(dryTempStdDevRegMatrix, xTdryMinTempStdDev, xTdryMaxTempStdDev, i,
						dailyStats[i].dryMinTempStdDev, dailyStats[i].dryMaxTempStdDev);
			}
		}

		// finish filling in symmetric regression matrices
		constructRegMatrix(wetTempStdDevRegMatrix);
		constructRegMatrix(dryTempStdDevRegMatrix);

		inv_in_place(wetTempStdDevRegMatrix, 7);
		inv_in_place(dryTempStdDevRegMatrix, 7);

		// calulate a0, a1, a2 regression coefficients for wet & dry, min & max
		// temperature standard
		// deviations
		calcParameters(minTempWetStdDevParameters, wetTempStdDevRegMatrix, xTwetMinTempStdDev);
		calcParameters(maxTempWetStdDevParameters, wetTempStdDevRegMatrix, xTwetMaxTempStdDev);
		calcParameters(minTempDryStdDevParameters, dryTempStdDevRegMatrix, xTdryMinTempStdDev);
		calcParameters(maxTempDryStdDevParameters, dryTempStdDevRegMatrix, xTdryMaxTempStdDev);

		Triple<Double, Integer, Boolean> pio = readAgain(station, m_seriesLength, m_wetSeries);
		double prob = pio.getFirst();
		m_seriesLength = pio.getSecond();
		m_wetSeries = pio.getThird();

		m_probRain = new Random.Bernoulli(prob, strm);
		// calculate 5 different correlations to be used
		holdVal = Math.sqrt(ssqMax * ssqMin);
		rhoMinMin /= ssqMin;
		rhoMaxMax /= ssqMax;
		rhoDaily /= holdVal;
		rhoMinMax /= holdVal;
		rhoMaxMin /= holdVal;

		/* To check correlations */
		// cout << "correlation for min temperatures: " << rhoMinMin << endl;
		// cout << "correlation for max temperatures: " << rhoMaxMax << endl;
		// cout << "daily correlation for temperatures: " << rhoDaily << endl; //should
		// be close to 0.6
		// cout << "correlation for minmax temperatures: " << rhoMinMax << endl;
		// cout << "correlation for maxmin temperatures: " << rhoMaxMin << endl;

		// determine data to start new year in case that is desired
		if (m_wetSeries) {
			m_minPrevResidual = finalMin - fourierFit(minTempWetParameters, NUMDAYS - 1);
			m_minPrevStdDev = fourierFit(minTempWetStdDevParameters, NUMDAYS - 1);
			m_maxPrevResidual = finalMax - fourierFit(maxTempWetParameters, NUMDAYS - 1);
			m_maxPrevStdDev = fourierFit(maxTempWetStdDevParameters, NUMDAYS - 1);
		} else {
			m_minPrevResidual = finalMin - fourierFit(minTempDryParameters, NUMDAYS - 1);
			m_minPrevStdDev = fourierFit(minTempDryStdDevParameters, NUMDAYS - 1);
			m_maxPrevResidual = finalMax - fourierFit(maxTempDryParameters, NUMDAYS - 1);
			m_maxPrevStdDev = fourierFit(maxTempDryStdDevParameters, NUMDAYS - 1);
		}

		int i;
		m_tempDist = new Random.Normal(strm);
		for (i = 0; i < NUMMONTHS; ++i) {
			m_wetDist[i] = new Random.SemiEmpirical(wetLengthBounds[i], wetLengthFreq[i], NUMINTERVALS, strm);
			m_dryDist[i] = new Random.SemiEmpirical(dryLengthBounds[i], dryLengthFreq[i], NUMINTERVALS, strm);
			m_rainDist[i] = new Random.SemiEmpirical(rainBounds[i], rainFreq[i], NUMINTERVALS, strm);
		}
		for (i = 0; i < 7; ++i) {
			m_minWetParameters[i] = minTempWetParameters[i];
			m_maxWetParameters[i] = maxTempWetParameters[i];
			m_minDryParameters[i] = minTempDryParameters[i];
			m_maxDryParameters[i] = maxTempDryParameters[i];
			m_minWetStdDevParameters[i] = minTempWetStdDevParameters[i];
			m_maxWetStdDevParameters[i] = maxTempWetStdDevParameters[i];
			m_minDryStdDevParameters[i] = minTempDryStdDevParameters[i];
			m_maxDryStdDevParameters[i] = maxTempDryStdDevParameters[i];
		}
		m_rhoMin = rhoMinMin;
		m_rhoMax = rhoMaxMax;
		m_rhoDaily = rhoDaily;
		m_rhoMinMax = rhoMinMax;
		m_rhoMaxMin = rhoMaxMin;

		// seriesLength, at end of historical data, represents length of final
		// cycle, we need to convert it to length remaining, thus, generate
		// the conditional length given it is at least as long from historical data
		// seriesLength, at end of historical data, represents length of final
		// cycle, we need to convert it to length remaining, thus, generate
		// the conditional length given it is at least as long from historical data
		if (m_wetSeries)
			m_seriesLength = (int) (m_wetDist[11].draw(m_seriesLength) + 0.5) - m_seriesLength;
		else
			m_seriesLength = (int) (m_dryDist[11].draw(m_seriesLength) + 0.5) - m_seriesLength;
	}

	private Triple<Double, Integer, Boolean> readAgain(WeatherStation station, int length, boolean wet)
			throws KlabException {

		double residualMin, oldMin = 0;
		double residualMax, oldMax = 0;
		int sumRain = 0, kount = 0;
		int currentMonth;
		int monthOrigin = 0; // stores the month in which the current series started
		int lengthWetSeries = 0; // incrementor counting length of a wet series
		int lengthDrySeries = 0; // incrementor counting length of dry series
		int interval; // stores interval in which a data member should be placed

		boolean firstDataPoint = true;

		double[][] data = station.getYearlyData(true, true, true, false, Weather.PRECIPITATION_MM,
				Weather.MIN_TEMPERATURE_C, Weather.MAX_TEMPERATURE_C);

		for (int day = 0; day < data.length; day++) {

			double holdRain = data[day][0];
			double holdMin = data[day][1];
			double holdMax = data[day][2];

			if (Double.isNaN(holdMin) || Double.isNaN(holdMax))
				System.out.println("porcodio");

			currentMonth = getMonth(day % 365);

			int dayOfYear = day % 365;

			if (holdRain > EPS) {
				lengthWetSeries++;
				if (lengthDrySeries > 0) {
					interval = GetInterval(lengthDrySeries, dryLengthBounds[monthOrigin]);
					dryLengthFreq[monthOrigin][interval]++;
					lengthDrySeries = 0;
					monthOrigin = getMonth(dayOfYear);
				}
				interval = GetInterval(holdRain, rainBounds[currentMonth]);
				rainFreq[currentMonth][interval]++;
				if ((dayOfYear < 5) || (dayOfYear > 360)) {
					++sumRain;
					++kount;
				}
			} else {
				lengthDrySeries++;
				if (lengthWetSeries > 0) {
					interval = GetInterval(lengthWetSeries, wetLengthBounds[monthOrigin]);
					wetLengthFreq[monthOrigin][interval]++;
					lengthWetSeries = 0;
					monthOrigin = getMonth(dayOfYear);
				}
				if ((dayOfYear < 5) || (dayOfYear > 360)) {
					++kount;
				}
			}

			// now build sums for temperature correlations
			residualMin = calcResiduals(dayOfYear, holdMin, holdRain, true);
			residualMax = calcResiduals(dayOfYear, holdMax, holdRain, false);
			if (firstDataPoint) {
				firstDataPoint = false;
				ssqMin = residualMin * residualMin;
				ssqMax = residualMax * residualMax;
				rhoDaily = residualMin * residualMax;
				rhoMinMin = 0.0;
				rhoMaxMax = 0.0;
				rhoMinMax = 0.0;
				rhoMaxMin = 0.0;
			} else {
				ssqMin += residualMin * residualMin;
				ssqMax += residualMax * residualMax;
				rhoDaily += residualMin * residualMax;
				rhoMinMin += oldMin * residualMin;
				rhoMaxMax += oldMax * residualMax;
				rhoMinMax += oldMin * residualMax;
				rhoMaxMin += oldMax * residualMin;
			}
			oldMin = residualMin;
			oldMax = residualMax;
			initMinTemp = holdMin;
			initMaxTemp = holdMax;

		}

		if (lengthWetSeries > 0) {
			length = lengthWetSeries;
			wet = true;
		} else if (lengthDrySeries > 0) {
			length = lengthDrySeries;
			wet = false;
		} else {
			throw new KlabException("ERROR:  cannot end with seriesLength=0 (see ReadAgain()).");
		}
		return new Triple<Double, Integer, Boolean>((double) sumRain / (double) kount, length, wet);
	}

	private Triple<Integer, Double, Double> readData(WeatherStation station, double finalMin, double finalMax,
			boolean b) throws KlabException {

		int consWetDays = 0; // counter for consecutive wet days
		int consDryDays = 0; // counter for consecutive dry days

		int holdD, yearCount = 0;
		int monthOrigin = 0;

		double[][] data = station.getYearlyData(true, true, true, false, Weather.PRECIPITATION_MM,
				Weather.MIN_TEMPERATURE_C, Weather.MAX_TEMPERATURE_C);

		if (data == null)
			return null;

		for (int day = 0; day < data.length; day++) {

			double holdRain = data[day][0];
			double holdMin = data[day][1];
			double holdMax = data[day][2];

			if (Double.isNaN(holdMin) || Double.isNaN(holdMax)) {
				System.out.println("porcodio");
			}

			int dayOfYear = day % 365;

			if (day == 365) {
				++yearCount;
			}

			if (holdMax < holdMin) {
				// happens if interpolated
				holdMax = holdMin;

				// throw new ThinklabRuntimeException(
				// "Error: maximum temperature is reported below minimum temperature.");
			}
			if (holdRain < 0.0) {
				holdRain = 0.0;
				// throw new ThinklabRuntimeException("Error: negative precipitation
				// reported.");
			}
			finalMin = holdMin;
			finalMax = holdMax;

			// calculate maximum values to be used as upper bounds in processing phase for
			// the semi-empirical
			// distributions
			if (holdRain > EPS) {
				constructRegMatrix(wetRegMatrix, xTwetMinTemp, xTwetMaxTemp, dayOfYear, holdMin, holdMax);
				consWetDays++;
				// if the number of consecutive dry days is nonzero, then it must be the start
				// of a new
				// series. Therefore, change the month of origin to the current month and set
				// consDryDays to
				// 0.
				if (consDryDays > 0) {
					monthOrigin = getMonth(dayOfYear);
					consDryDays = 0;
				}
				// store in the maxConsWetDays array for a given month if the series is the
				// longest instance
				// ever in that month.
				if (consWetDays > maxConsWetDays[monthOrigin])
					maxConsWetDays[monthOrigin] = consWetDays;

				// accumulate for daily standard deviations
				dailyStats[dayOfYear].wetMinTempSum += holdMin;
				dailyStats[dayOfYear].wetMaxTempSum += holdMax;
				dailyStats[dayOfYear].wetMinTempStdDev += holdMin * holdMin;
				dailyStats[dayOfYear].wetMaxTempStdDev += holdMax * holdMax;
				++dailyStats[dayOfYear].wetDays;
			} else {
				constructRegMatrix(dryRegMatrix, xTdryMinTemp, xTdryMaxTemp, dayOfYear, holdMin, holdMax);
				consDryDays++;
				// if the number of consecutive wet days is nonzero, then it must be the start
				// of a new
				// series. Therefore, change the month of origin to the current month and set
				// consWetDays to
				// 0.
				if (consWetDays > 0) {
					monthOrigin = getMonth(dayOfYear);
					consWetDays = 0;
				}
				// store in the maxConsWetDays array for a given month if the series is the
				// longest instance
				// ever in that month.
				if (consDryDays > maxConsDryDays[monthOrigin])
					maxConsDryDays[monthOrigin] = consDryDays;

				// accumulate for daily standard deviations
				dailyStats[dayOfYear].dryMinTempSum += holdMin;
				dailyStats[dayOfYear].dryMaxTempSum += holdMax;
				dailyStats[dayOfYear].dryMinTempStdDev += holdMin * holdMin;
				dailyStats[dayOfYear].dryMaxTempStdDev += holdMax * holdMax;
				++dailyStats[dayOfYear].dryDays;
			}

			// if rain amount is on this day is greater than the most rain on this month,
			// store in rainMax
			// array
			holdD = getMonth(dayOfYear);
			if (holdRain > rainMax[holdD]) {
				rainMax[holdD] = holdRain;
			}
		}

		return new Triple<Integer, Double, Double>(yearCount, finalMin, finalMax);

	}

	private int getMonth(int dayOfYear) // date is julian date
	// month 0 is Jan, month 11 is Dec
	{
		for (int i = 0; i < 11; ++i)
			if (dayOfYear <= jul[i])
				return i;
		return 11;
	}

	public void generateDaily(double[] minT, double[] maxT, double[] rain, boolean randomStart) {
		int day = 1;
		int year = 0;
		int month, d, i;
		double newMinMean, newMaxMean, newMinStdev, newMaxStdev;
		double newMinVar, newMaxVar;
		double ratio, term1, term2;
		double[] Tvect = new double[3]; // T vector used in calculating maximum temperature mean following
										// method described in Scheuer and Stroller (1962)
		double[] VCinv = new double[3]; // result of vector V times matrix Cinv
		double minTempMean; // stores minimum temperature mean
		double minTempStdDev; // stores minimum temperature standard deviation
		double minTemp; // stores generated minimum temperature ~N(minTempMean, minTempStdDev)
		double maxTempMean; // stores maximum temperature mean
		double maxTempStdDev; // stores maximum temperature standard deviation
		double maxTemp; // stores generated maximum temperature ~N(maxTempMean, maxTempStdDev)
		// generate weather
		// choose method via boolean value newBeginning
		// newBeginning = true: start generating weather on Jan. 1, deriving
		// temeperature and series length
		// data from the last year of input data.
		// newBeginning = false: start generating weather on Dec. 1 of the previous year
		// to introduce
		// randomness into the model. In other words, calculate any year, not just the
		// year after the input
		// data
		if (randomStart) {
			// generate random stuff on Dec 31 for starting new year
			// will assume that Dec is first day of new series of random length
			if (m_probRain.draw()) {
				m_wetSeries = true;
				m_seriesLength = (int) (m_wetDist[11].draw() + 0.5);
				newMinMean = fourierFit(m_minWetParameters, NUMDAYS - 1);
				newMinStdev = fourierFit(m_minWetStdDevParameters, NUMDAYS - 1);
				newMaxMean = fourierFit(m_maxWetParameters, NUMDAYS - 1);
				newMaxStdev = fourierFit(m_maxWetStdDevParameters, NUMDAYS - 1);
			} else {
				m_wetSeries = false;
				m_seriesLength = (int) (m_dryDist[11].draw() + 0.5);
				newMinMean = fourierFit(m_minDryParameters, NUMDAYS - 1);
				newMinStdev = fourierFit(m_minDryStdDevParameters, NUMDAYS - 1);
				newMaxMean = fourierFit(m_maxDryParameters, NUMDAYS - 1);
				newMaxStdev = fourierFit(m_maxDryStdDevParameters, NUMDAYS - 1);
			}

			m_minPrevResidual = m_tempDist.draw(newMinMean, newMinStdev) - newMinMean;
			ratio = newMaxStdev / newMinStdev;
			maxTempMean = newMaxMean + m_rhoDaily * ratio * m_minPrevResidual;
			maxTempStdDev = newMaxStdev * Math.sqrt(1.0 - m_rhoDaily * m_rhoDaily);
			m_maxPrevResidual = m_tempDist.draw(maxTempMean, maxTempStdDev) - newMaxMean;
			m_minPrevStdDev = newMinStdev;
			m_maxPrevStdDev = newMaxStdev;
			--m_seriesLength;
		} // end random start

		// generate weather using SemiEmpirical distrbutions and minTemp/maxTemp
		// parameters
		// to introduce randomness on Jan 1, start generating from December 31st of the
		// previous year
		for (year = 0; year < GENERATEYEARS; ++year) {
			day = 0; // 1; FIXME CHECK
			for (month = 0; month < 12; ++month) {
				for (d = 0; d < daysPerMonth[month]; ++d) {
					// month = FindMonth(day);
					// if there are no more days left in a series,
					// then switch to a series of the different type and generate the new series
					// length
					if (m_seriesLength <= 0) {
						m_wetSeries = !m_wetSeries;
						if (m_wetSeries)
							m_seriesLength = (int) (m_wetDist[month].draw() + 0.5);
						else
							m_seriesLength = (int) (m_dryDist[month].draw() + 0.5);
					}
					// generate min & max temperature according to a normal distribution
					// means and standard deviations calculated by a method described in
					// "On the Generation of Normal Random Vectors" by Scheuer & Stoller (1962)
					// generates precipation according to a SemiEmpirical distribution

					if (m_wetSeries) {
						rain[day] = m_rainDist[month].draw();
						// mean and stdev if no correlation - wet
						newMinMean = fourierFit(m_minWetParameters, day);
						newMaxMean = fourierFit(m_maxWetParameters, day);
						newMinStdev = fourierFit(m_minWetStdDevParameters, day);
						newMaxStdev = fourierFit(m_maxWetStdDevParameters, day);
					} else {
						rain[day] = 0.0;
						// mean and stdev if no correlation - dry
						newMinMean = fourierFit(m_minDryParameters, day);
						newMaxMean = fourierFit(m_maxDryParameters, day);
						newMinStdev = fourierFit(m_minDryStdDevParameters, day);
						newMaxStdev = fourierFit(m_maxDryStdDevParameters, day);
					}
					newMinVar = newMinStdev * newMinStdev;
					newMaxVar = newMaxStdev * newMaxStdev;
					// min mean with correlation
					ratio = newMinStdev / (1.0 - m_rhoDaily * m_rhoDaily);
					term1 = (m_rhoMaxMin - m_rhoDaily * m_rhoMin) * (m_maxPrevResidual / m_maxPrevStdDev);
					term2 = (m_rhoMin - m_rhoDaily * m_rhoMaxMin) * (m_minPrevResidual / m_minPrevStdDev);
					minTempMean = newMinMean + (term1 + term2) * ratio;
					// min stdev with correlation
					ratio = newMinVar / (1.0 - m_rhoDaily * m_rhoDaily);
					term1 = m_rhoMaxMin * (m_rhoMaxMin - m_rhoDaily * m_rhoMin);
					term2 = m_rhoMin * (m_rhoMin - m_rhoDaily * m_rhoMaxMin);
					minTempStdDev = Math.sqrt(newMinVar - (term1 + term2) * ratio);

					minTemp = m_tempDist.draw(minTempMean, minTempStdDev);

					// max mean and stdev with correlation
					createTvect(Tvect, minTemp, newMinMean, m_minPrevResidual, m_maxPrevResidual);
					maxTempStdDev = newMaxVar - createV_Cinv(VCinv, newMinStdev, newMaxStdev, m_minPrevStdDev,
							m_maxPrevStdDev, m_rhoDaily, m_rhoMin, m_rhoMax, m_rhoMinMax, m_rhoMaxMin);
					// note that when the matrix product V * Cinv is created, it also returns
					// the value of V*Cinv*V-transpose
					maxTempStdDev = Math.sqrt(maxTempStdDev);
					// maxTempMean = newMaxMean + matrixProduct(Vvect, CinvMatrix, Tvect);
					maxTempMean = newMaxMean;
					for (i = 0; i < 3; ++i)
						maxTempMean += VCinv[i] * Tvect[i];
					// maxTempStdDev = sqrt(newMaxVar - matrixProduct(Vvect, CinvMatrix, Vvect));

					maxTemp = m_tempDist.draw(maxTempMean, maxTempStdDev);

					if (maxTemp < minTemp) {
						maxTemp = (minTemp + maxTemp) / 2.0;
						minTemp = maxTemp;
					}
					m_minPrevResidual = minTemp - newMinMean;
					m_maxPrevResidual = maxTemp - newMaxMean;
					m_minPrevStdDev = newMinStdev;
					m_maxPrevStdDev = newMaxStdev;
					// store in arrays
					minT[day] = minTemp;
					maxT[day] = maxTemp;

					// decrement series length, go to the next day
					m_seriesLength--;
					day++;
				}
			}
		}
	}

	private void constructRegMatrix(double[][] regMatrix, double[] xTymin, double[] xTymax, int day, double ymin,
			double ymax)
	// constructs regression matrices X'*Y and X'*X for arrays declared in the main
	// program. ' denotes a
	// transposed matrix.
	// it is an unchecked runtime error for regMatrix, xTymin, and xTymax to be
	// smaller than 3x3, 3x1, and 3x1
	// matrices, respectively
	{
		if (Double.isNaN(xTymax[0])) {
			System.out.println("cpcp");
		}

		regMatrix[0][0] += 1.0;
		regMatrix[0][1] += Math.cos(2.0 * Math.PI * day / 365.0);
		regMatrix[0][2] += Math.sin(2.0 * Math.PI * day / 365.0);
		regMatrix[0][3] += Math.cos(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[0][4] += Math.sin(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[0][5] += Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[0][6] += Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[1][1] += Math.cos(2.0 * Math.PI * day / 365.0) * Math.cos(2.0 * Math.PI * day / 365.0);
		regMatrix[1][2] += Math.cos(2.0 * Math.PI * day / 365.0) * Math.sin(2.0 * Math.PI * day / 365.0);
		regMatrix[1][3] += Math.cos(2.0 * Math.PI * day / 365.0) * Math.cos(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[1][4] += Math.cos(2.0 * Math.PI * day / 365.0) * Math.sin(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[1][5] += Math.cos(2.0 * Math.PI * day / 365.0) * Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[1][6] += Math.cos(2.0 * Math.PI * day / 365.0) * Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[2][2] += Math.sin(2.0 * Math.PI * day / 365.0) * Math.sin(2.0 * Math.PI * day / 365.0);
		regMatrix[2][3] += Math.sin(2.0 * Math.PI * day / 365.0) * Math.cos(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[2][4] += Math.sin(2.0 * Math.PI * day / 365.0) * Math.sin(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[2][5] += Math.sin(2.0 * Math.PI * day / 365.0) * Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[2][6] += Math.sin(2.0 * Math.PI * day / 365.0) * Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[3][3] += Math.cos(2 * 2.0 * Math.PI * day / 365.0) * Math.cos(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[3][4] += Math.cos(2 * 2.0 * Math.PI * day / 365.0) * Math.sin(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[3][5] += Math.cos(2 * 2.0 * Math.PI * day / 365.0) * Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[3][6] += Math.cos(2 * 2.0 * Math.PI * day / 365.0) * Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[4][4] += Math.sin(2 * 2.0 * Math.PI * day / 365.0) * Math.sin(2 * 2.0 * Math.PI * day / 365.0);
		regMatrix[4][5] += Math.sin(2 * 2.0 * Math.PI * day / 365.0) * Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[4][6] += Math.sin(2 * 2.0 * Math.PI * day / 365.0) * Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[5][5] += Math.cos(3 * 2.0 * Math.PI * day / 365.0) * Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[5][6] += Math.cos(3 * 2.0 * Math.PI * day / 365.0) * Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		regMatrix[6][6] += Math.sin(3 * 2.0 * Math.PI * day / 365.0) * Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		xTymin[0] += ymin;
		xTymin[1] += ymin * Math.cos(2.0 * Math.PI * day / 365.0);
		xTymin[2] += ymin * Math.sin(2.0 * Math.PI * day / 365.0);
		xTymin[3] += ymin * Math.cos(2 * 2.0 * Math.PI * day / 365.0);
		xTymin[4] += ymin * Math.sin(2 * 2.0 * Math.PI * day / 365.0);
		xTymin[5] += ymin * Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		xTymin[6] += ymin * Math.sin(3 * 2.0 * Math.PI * day / 365.0);
		xTymax[0] += ymax;
		xTymax[1] += ymax * Math.cos(2.0 * Math.PI * day / 365.0);
		xTymax[2] += ymax * Math.sin(2.0 * Math.PI * day / 365.0);
		xTymax[3] += ymax * Math.cos(2 * 2.0 * Math.PI * day / 365.0);
		xTymax[4] += ymax * Math.sin(2 * 2.0 * Math.PI * day / 365.0);
		xTymax[5] += ymax * Math.cos(3 * 2.0 * Math.PI * day / 365.0);
		xTymax[6] += ymax * Math.sin(3 * 2.0 * Math.PI * day / 365.0);

		if (Double.isNaN(xTymax[0])) {
			System.out.println("cpcp");
		}

	}

	private void constructRegMatrix(double[][] regMatrix)
	// finishes constructing a symmetric regression matrix
	{
		regMatrix[1][0] = regMatrix[0][1];
		regMatrix[2][0] = regMatrix[0][2];
		regMatrix[2][1] = regMatrix[1][2];
		regMatrix[3][0] = regMatrix[0][3];
		regMatrix[3][1] = regMatrix[1][3];
		regMatrix[3][2] = regMatrix[2][3];
		regMatrix[4][0] = regMatrix[0][4];
		regMatrix[4][1] = regMatrix[1][4];
		regMatrix[4][2] = regMatrix[2][4];
		regMatrix[4][3] = regMatrix[3][4];
		regMatrix[5][0] = regMatrix[0][5];
		regMatrix[5][1] = regMatrix[1][5];
		regMatrix[5][2] = regMatrix[2][5];
		regMatrix[5][3] = regMatrix[3][5];
		regMatrix[5][4] = regMatrix[4][5];
		regMatrix[6][0] = regMatrix[0][6];
		regMatrix[6][1] = regMatrix[1][6];
		regMatrix[6][2] = regMatrix[2][6];
		regMatrix[6][3] = regMatrix[3][6];
		regMatrix[6][4] = regMatrix[4][6];
		regMatrix[6][5] = regMatrix[5][6];
	}

	private void initializeBounds()// (double wetLengthBounds[][NUMINTERVALS+1], double
									// dryLengthBounds[][NUMINTERVALS+1], double rainBounds[][NUMINTERVALS+1],
									// int maxConsWetDays[], int maxConsDryDays[], double rainMax[])
	// initializes the bounds array for the semi-empirical distribution
	// bounds for series lengths can either be the result of a slight modification
	// of our algorithm for rain or an algorithm that duplicates bounds used by
	// LARS-WG.
	// this option may be toggled by de-commenting, commenting the appropriate
	// function calls within this function.
	// for rain: to avoid coarse resolution of lower values,
	// determine bounds in the follwing manner: for a given month,
	// take the maximum rain ever occurred for that month and set it as the highest
	// bound.
	// Let n = highest bound / 10.
	// starting at zero, bounds are incremented in the following fashion:
	// 1/2*n, 1/2*n, 3/4*n, 3/4*n, n, n, 5/4*n, 5/4*n, 3/2*n, 3/2*n
	{
		for (int i = 0; i < NUMMONTHS; i++) {
			// set lower and upper bounds
			wetLengthBounds[i][0] = 0.5;
			dryLengthBounds[i][0] = 0.5;
			rainBounds[i][0] = 0;
			rainBounds[i][NUMINTERVALS] = rainMax[i];

			/* method to obtain LARS-WG bounds */
			assignLARSbounds(wetLengthBounds[i], maxConsWetDays[i]);
			assignLARSbounds(dryLengthBounds[i], maxConsDryDays[i]);

			// assign intervals for rain using the methodology described above
			for (int j = 1; j < NUMINTERVALS; j++)
				rainBounds[i][j] = rainBounds[i][j - 1] + (0.5 + 0.25 * ((j - 1) / 2)) * rainMax[i] / NUMINTERVALS;
		}
	}

	private void assignLARSbounds(double[] bounds, int maxConsDays)
	// takes in maximum amount of consecutive wet or dry days and stores bound
	// values in bounds[]
	// each bound is slightly larger than the preceeding bound, typically one or two
	// units wider than the
	// preceeding boumd
	{
		int adjustment = 0; // keeps track of how much needs to be added to NUMINTERVALS to include
							// maxConsDays
		int finalAdj = 0; // stores the width of the last interval's adjustment (i.e., if finalAdj is 6,
							// interval NUMINTERVALS will have a width of 7 provided iterations is 0)
		int iterations = 0; // keeps track of how many times finalAdj reaches NUMINTERVALS-1, the maximum
							// amount of adjustment in one step allowed by the algorithm
		while (NUMINTERVALS + adjustment < maxConsDays) {
			// increment finalAdj and add it to the cumulative adjustment
			adjustment += ++finalAdj;
			// if the maximum one-step finalAdj is reached, increment iterations and reset
			// finalAdj
			if (finalAdj >= NUMINTERVALS - 1) {
				iterations++;
				finalAdj = 0;
			}
		}
		// for each entry, add 1) the previous entry, 2) the base difference of 1, 3) an
		// adjustment based on
		// any iterations that may have occured, 4) an adjustment based on final
		// adjustment
		for (int j = 1; j < NUMINTERVALS + 1; j++)
			bounds[j] = bounds[j - 1] + 1 + iterations * (j - 1) + Math.max(finalAdj + (j - NUMINTERVALS), 0);
	}

	private void calcParameters(double[] parameters, double[][] regMatrix, double[] xTy)
	// calculates a0, a1, and a2 parameters for the regression given regMatrix
	// (X'*X) and a given X'*Y.
	// results stored in double parameters[3]
	// it is an unchecked runtime error for any matrix to have a dimension smaller
	// than 3
	{
		for (int i = 0; i < 7; i++)
			parameters[i] = regMatrix[i][0] * xTy[0] + regMatrix[i][1] * xTy[1] + regMatrix[i][2] * xTy[2]
					+ regMatrix[i][3] * xTy[3] + regMatrix[i][4] * xTy[4] + regMatrix[i][5] * xTy[5]
					+ regMatrix[i][6] * xTy[6];
	}

	private double standardize(double temp, double[] meanPara, double[] stdDevPara, int day)
	// returns a normalized residual given the day and
	// regression parameters for the mean and standard deviation
	{
		double mean = fourierFit(meanPara, day);
		double stdDev = fourierFit(stdDevPara, day);
		return (temp - mean) / stdDev;
	}

	private double calcResiduals(int day, double temp, double rain, boolean minimum)
	// calculates normalized residuals of temperatures for every day. takes in flag
	// boolean that indicates
	// whether to calculate parameters for maximum or minimum temperatures.
	{
		if (rain > EPS) {
			if (minimum)
				return standardize(temp, minTempWetParameters, minTempWetStdDevParameters, day);
			else
				return standardize(temp, maxTempWetParameters, maxTempWetStdDevParameters, day);
		} else {
			if (minimum)
				return standardize(temp, minTempDryParameters, minTempDryStdDevParameters, day);
			else
				return standardize(temp, maxTempDryParameters, maxTempDryStdDevParameters, day);
		}
	}

	private double fourierFit(double[] parameters, int index)
	// returns a regression mean given the regression parameters and an index (for
	// our purposes, the day)
	{
		return parameters[0] + parameters[1] * Math.cos(2.0 * Math.PI * index / 365.0)
				+ parameters[2] * Math.sin(2.0 * Math.PI * index / 365.0)
				+ parameters[3] * Math.cos(2 * 2.0 * Math.PI * index / 365.0)
				+ parameters[4] * Math.sin(2 * 2.0 * Math.PI * index / 365.0)
				+ parameters[5] * Math.cos(3 * 2.0 * Math.PI * index / 365.0)
				+ parameters[6] * Math.sin(3 * 2.0 * Math.PI * index / 365.0);
	}

	private void createTvect(double[] Tvect, double minTemp, double minMean, double minTempPrevResidual,
			double maxTempPrevResidual)
	// creates Tvect, the vector of temperature residuals that is later used to
	// calculate
	// the mean of the max temperature
	{
		Tvect[0] = minTemp - minMean;
		Tvect[1] = maxTempPrevResidual;
		Tvect[2] = minTempPrevResidual;
	}

	private double createV_Cinv(double VC[], double newMinStd, double newMaxStd, double minPrevStd, double maxPrevStd,
			double rhoDaily, double rhoMin, double rhoMax, double rhoMinMx, double rhoMxMn)
	// creates V matrix, then C matrix, takes inverse of C matrix, finally
	// multiplies V*Cinv
	// VC[] is output vector containing results (3 components)
	// returns the value of V times Cinv times V-transpose
	{
		double[] vec = new double[3];
		double rc = 0.0;
		vec[0] = rhoDaily * newMinStd * newMaxStd;
		vec[1] = rhoMax * maxPrevStd * newMaxStd;
		vec[2] = rhoMinMx * minPrevStd * newMaxStd;

		Cinv[0][0] = newMinStd * newMinStd;
		Cinv[1][1] = maxPrevStd * maxPrevStd;
		Cinv[2][2] = minPrevStd * minPrevStd;
		Cinv[0][1] = rhoMxMn * maxPrevStd * newMinStd;
		Cinv[0][2] = rhoMin * minPrevStd * newMinStd;
		Cinv[1][2] = rhoDaily * maxPrevStd * minPrevStd;
		Cinv[1][0] = Cinv[0][1];
		Cinv[2][0] = Cinv[0][2];
		Cinv[2][1] = Cinv[1][2];
		inv_in_place(Cinv, 3);
		int i, j;
		for (j = 0; j < 3; ++j) {
			VC[j] = 0.0;
			for (i = 0; i < 3; ++i)
				VC[j] += vec[i] * Cinv[i][j];
			rc += VC[j] * vec[j];
		}
		return rc;
	}

	final static private double ZERO = 1.0e-7;

	private void inv_in_place(double[][] mat, int dim)
	// the input matrix "a" will be destroyed and its inverse
	// will be placed in "a". The matrix is of dimension dim x dim
	{
		// int[] indxc = new int[dim];
		int[] indxr = new int[dim];
		int[] ipiv = new int[dim];

		int i, jrow = 0, j, k;
		double big, dum, pivinv, anorm;

		anorm = 0;
		for (i = 0; i < dim; ++i)
			for (j = 0; j < dim; ++j) {
				big = Math.abs(mat[i][j]);
				if (big > anorm)
					anorm = big;
			}

		for (j = 0; j < dim; j++)
			ipiv[j] = 0; // sets all pivots to zero
		for (k = 0; k < dim; k++) { // row index
			big = Math.abs(mat[k][k]);
			jrow = k;
			for (i = k + 1; i < dim; i++) {
				if (Math.abs(mat[i][k]) > big) {
					big = Math.abs(mat[i][k]);
					jrow = i;
				}
			} // found largest element in col.

			if (big <= ZERO) {
				throw new KlabException("singularity or near-singularity in matrix inversion");
			}

			indxr[k] = jrow;
			if (jrow > k)
				for (j = 0; j < dim; ++j) {
					double temp = mat[k][j];
					mat[k][j] = mat[jrow][j];
					mat[jrow][j] = temp;
				}
			// now transformation
			pivinv = 1.0 / mat[k][k];
			mat[k][k] = 1.0;
			for (j = 0; j < dim; ++j)
				mat[k][j] *= pivinv;
			for (i = 0; i < dim; i++)
				if (i != k) {
					dum = mat[i][k];
					mat[i][k] = 0.0;
					for (j = 0; j < dim; j++)
						mat[i][j] -= mat[k][j] * dum;
				}
		} // end all pivot operations
		for (j = dim - 1; j >= 0; --j) {
			if (j != indxr[j])
				for (i = 0, k = indxr[j]; i < dim; ++i) {
					double temp = mat[i][j];
					mat[i][j] = mat[i][k];
					mat[i][k] = temp;
				}
		}
	}

	private int GetInterval(int member, double[] bounds) // places data member into within appropriate bounds
	{
		// for (int i=1; i<NUMINTERVALS+1; i++)
		// cout << bounds[i] << endl;
		for (int i = 1; i < NUMINTERVALS + 1; i++)
			if (member <= bounds[i])
				return i - 1;
		throw new KlabException("Error: data member out of bounds in determining paramters for temperatures--");
		// return 9;
	}

	private int GetInterval(double member, double bounds[]) // places data member into within appropriate
															// bounds
	{
		for (int i = 1; i < NUMINTERVALS + 1; i++)
			if (member <= bounds[i])
				return i - 1;
		throw new KlabException("Error: data member out of bounds during parameter setting for temperatures.");
		// return 9;
	}

}
