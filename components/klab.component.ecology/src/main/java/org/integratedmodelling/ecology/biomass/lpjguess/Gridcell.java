package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.Insolation;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;
import org.locationtech.jts.geom.Point;

/**
 * Status as of 3/2016:
 * 
 * Looks like Gridcell is the basic top-level environment object and that an LPJ
 * process runs one gridcell. There is nothing in the gridcell (except for
 * latitude and longitude, which is a point anyway) that requires this to be a
 * contiguous, square area of space, so I (FV) am using a gridcell to represent
 * an Ecological Response Unit (ERU) which is potentially scattered around and
 * can fit non-areal or areal contexts of different topology.
 * 
 * Creating one gridcell per cell in a raster model is incredibly wasteful, so
 * the basic approach uses a map classifier to reduce the context to a smaller
 * number of ERUs and models each one as a gridcell.
 * 
 * When this is vetted and tested, Gridcell should be renamed
 * EcologicalResponseUnit.
 * 
 */
public class Gridcell {

	private IConfiguration _configuration;

	/**
	 * Area is accumulated along the ERU during initialization.
	 */
	double area = 0;

	/**
	 * Number of subdivisions merged from main space topology. If raster, this can
	 * directly give us a stable metric of size.
	 */
	int nExtents = 0;

	// climate, insolation and CO2 for this grid cell
	public Climate climate;

	// / soil static parameters for this grid cell
	Soiltype soiltype;

	List<GridcellPFT> pft = new ArrayList<>();

	public List<Stand> stands = new ArrayList<>();

	int NLANDCOVERTYPES = LandcoverType.values().length;

	// / The fractions of the different land cover types.
	/**
	 * landcoverfrac is read in from land cover input file or from instruction file
	 * in getlandcover().
	 */
	public double landcoverfrac[] = new double[NLANDCOVERTYPES];

	// / The land cover fractions from the previous year
	/**
	 * Used to keep track of the changes when running with dynamic land cover.
	 */
	double landcoverfrac_old[] = new double[NLANDCOVERTYPES];

	// / Whether the land cover fractions changed for this grid cell this year
	/**
	 * \see landcover_dynamics
	 */
	public boolean LC_updated;

	// / Gridcell-level C flux from slow harvested products
	public double acflux_harvest_slow;
	// / Gridcell-level C flux from harvest associated with landcover change
	public double acflux_landuse_change;
	// / Gridcell-level N flux from slow harvested products
	public double anflux_harvest_slow;
	// / Gridcell-level N flux from harvest associated with landcover change
	public double anflux_landuse_change;
	// / Landcover-level C flux from slow harvested products (donating
	// landcover)
	public double acflux_harvest_slow_lc[] = new double[NLANDCOVERTYPES];
	// / Landcover-level C flux from harvest associated with landcover change
	// (donating landcover)
	public double acflux_landuse_change_lc[] = new double[NLANDCOVERTYPES];
	// / Landcover-level N flux from slow harvested products (donating
	// landcover)
	public double anflux_harvest_slow_lc[] = new double[NLANDCOVERTYPES];
	// / Landcover-level N flux from harvest associated with landcover change
	// (donating landcover)
	public double anflux_landuse_change_lc[] = new double[NLANDCOVERTYPES];
	// / Which landcover types create new stands when area increases.
	public Boolean expand_to_new_stand[] = new Boolean[NLANDCOVERTYPES];

	public boolean pool_to_all_landcovers[] = new boolean[NLANDCOVERTYPES]; // ...from
	// a
	// donor
	// landcover
	// (overrides
	// different
	// landcover
	// targets
	// of
	// different
	// stand
	// types
	// and
	// stands
	// in a
	// landcover)
	boolean pool_from_all_landcovers[] = new boolean[NLANDCOVERTYPES]; // ...to
	// a
	// receptor
	// landcover
	// (crop
	// and
	// pasture
	// stands
	// to
	// new
	// natural
	// stand:
	// pool!)

	// / list array [0...npft-1] of Gridcellpft (initialised in constructor)
	// List<> pft;

	// / Seed for generating random numbers within this Gridcell
	/**
	 * The reason why Gridcell has its own seed, rather than using for instance a
	 * single global seed is to make it easier to compare results when for instance
	 * changing the order in which the simulation proceeds. It also gets serialized
	 * together with the rest of the Gridcell state to make it possible to get
	 * exactly identical results after a restart.
	 *
	 * \see randfrac()
	 */
	long seed;

	public double latitude, longitude;

	/**
	 * Just initialize one per ERU and continue defining in the initialization loop
	 * by submitting data from more spatial locations.
	 * 
	 * @param config
	 */
	public Gridcell(IConfiguration config) {
		this._configuration = config;
		int i = 0;

		/*
		 * CHECK - if I don't do this everything breaks, but who knows if it's what I am
		 * really meant to do
		 * 
		 * TODO this should be done later (at finish()) and only using the PFTs that are
		 * actually represented in land uses for the unit.
		 */
		for (PFT pft : config.getPFTs()) {
			this.pft.add(new GridcellPFT(i++, pft));
		}
	}

	/*
	 * -------------------------------------------------------------------------
	 * ------ these are called during initial setup at each extent found to be part
	 * of this ERU. After the whole context has been scanned, finish() is called to
	 * setup the land cover fractions, define the overall soil type and finalize
	 * initialization.
	 * -------------------------------------------------------------------------
	 * ------
	 */
	int[] lucBuffer = new int[LandcoverType.values().length];
	double minLat = Double.NaN, maxLat = Double.NaN, minLon = Double.NaN, maxLon = Double.NaN;
	Map<Integer, Integer> soilCodeN = new HashMap<>();
	public List<Integer> spaceOffsets = new ArrayList<>();
	private boolean degenerate;

	public boolean addExtent(ISpace extent, int spaceOffset, IConcept landcovertype, int soilCode) {

		LandcoverType type = Configuration.getLPJLandcover(landcovertype);

		if (type != null) {
			nExtents++;
			area += extent.getShape().getArea(Units.INSTANCE.SQUARE_METERS);
			lucBuffer[type.ordinal()]++;
			Point point = ((Shape) extent.getShape()).getStandardizedGeometry().getCentroid();
			if (Double.isNaN(minLat) || minLat > point.getY()) {
				minLat = point.getY();
			}
			if (Double.isNaN(maxLat) || maxLat < point.getY()) {
				maxLat = point.getY();
			}
			if (Double.isNaN(minLon) || minLon > point.getX()) {
				minLon = point.getX();
			}
			if (Double.isNaN(maxLon) || maxLon < point.getX()) {
				maxLon = point.getX();
			}

			spaceOffsets.add(spaceOffset);

			/*
			 * use a majority rule although we should ensure that the soil code is unique in
			 * a ERU.
			 */
			if (soilCodeN.containsKey(soilCode)) {
				soilCodeN.put(soilCode, soilCodeN.get(soilCode) + 1);
			} else {
				soilCodeN.put(soilCode, 1);
			}

			return true;
		}
		return false;
	}

	/**
	 * Compute statistics after all data are in and return true if the ERU should be
	 * used.
	 * 
	 * @return
	 */
	public boolean finish() {

		this.latitude = minLat + (maxLat - minLat) / 2.0;
		this.longitude = minLon + (maxLon - minLon) / 2.0;

		int max = -1, chosen = -1;
		for (Integer i : soilCodeN.keySet()) {
			if (soilCodeN.get(i) > max) {
				max = soilCodeN.get(i);
				chosen = i;
			}
		}

		if (chosen <= 0) {
			return false;
		}

		this.soiltype = Soiltype.getLPJSoiltype(chosen);

		double totcl = 0;
		for (int n : lucBuffer) {
			totcl += n;
		}
		setLCFractions(lucBuffer[0] / totcl, lucBuffer[1] / totcl, lucBuffer[2] / totcl, lucBuffer[3] / totcl,
				lucBuffer[4] / totcl, lucBuffer[5] / totcl);

		this.climate = new Climate((Configuration) _configuration, latitude);

		return true;
	}

	public Gridcell(IConfiguration config, Soiltype soiltype, double latitude, double longitude) {

		this._configuration = config;
		this.soiltype = soiltype;
		this.latitude = latitude;
		this.longitude = longitude;
		int i = 0;

		/*
		 * CHECK - if I don't do this everything breaks, but who knows if it's what I am
		 * really meant to do
		 * 
		 * FIXME see above
		 */
		for (PFT pft : config.getPFTs()) {
			this.pft.add(new GridcellPFT(i++, pft));
		}
	}

	public IConfiguration getConfiguration() {
		return _configuration;
	}

	public void dailyAccounting(List<PFT> pftlist) {

		/*
		 * Updates daily climate parameters including growing degree day sums and
		 * exponential temperature response term (gtemp, see below). Maintains monthly
		 * and longer term records of variation in climate variables. PFT-specific
		 * degree-day sums in excess of damaging temperatures are also calculated here.
		 */

		final double W11DIV12 = 11.0 / 12.0;
		final double W1DIV12 = 1.0 / 12.0;

		int d;
		int y;
		int startyear;

		// guess2008 - changed this from an int to a double
		double mtemp_last;

		Climate climate = this.climate;

		// On first day of year ...

		// On the first day of the simulation, and on the first day of all
		// subsequent years...
		if (_configuration.getSchedule().isFirstDayOfSimulation() || _configuration.getSchedule().isFirstDayOfYear()) {

			// ... reset annual GDD5 counter
			climate.agdd5 = 0.0;

			if (_configuration.getSchedule().isFirstDayOfSimulation()) {

				// First day of simulation - initialise running annual mean
				// temperature
				// and daily temperatures for the last month

				for (d = 0; d < 31; d++) {
					climate.dtemp_31[d] = climate.temp;
				}
				climate.atemp_mean = climate.temp;
			}
		} else if (climate.lat >= 0.0
				&& _configuration.getSchedule().julianDay() == IConfiguration.COLDEST_DAY_NHEMISPHERE
				|| climate.lat < 0.0
						&& _configuration.getSchedule().julianDay() == IConfiguration.COLDEST_DAY_SHEMISPHERE) {

			// In midwinter, reset GDD counter for summergreen phenology

			climate.gdd5 = 0.0;
			climate.ifsensechill = false; // guess2008 - CHILLDAYS
		}

		// Update GDD counters and chill day count

		climate.gdd5 += Math.max(0.0, climate.temp - 5.0);
		climate.agdd5 += Math.max(0.0, climate.temp - 5.0);
		if (climate.temp < 5.0 && climate.chilldays <= 365) {
			climate.chilldays++;
		}

		// Save yesterday's mean temperature for the last month
		mtemp_last = climate.mtemp;

		// Update daily temperatures, and mean overall temperature, for last 31
		// days

		climate.mtemp = climate.temp;
		for (d = 0; d < 30; d++) {
			climate.dtemp_31[d] = climate.dtemp_31[d + 1];
			climate.mtemp += climate.dtemp_31[d];
		}
		climate.dtemp_31[30] = climate.temp;
		climate.mtemp /= 31.0;

		// Reset GDD and chill day counter if mean monthly temperature falls
		// below base
		// temperature

		if (mtemp_last >= 5.0 && climate.mtemp < 5.0 && climate.ifsensechill) // guess2008
		// -
		// CHILLDAYS
		{
			climate.gdd5 = 0.0;
			climate.chilldays = 0;
		}

		// On last day of month ...

		if (_configuration.getSchedule().isLastDayOfMonth()) {

			// Update mean temperature for the last 12 months
			// atemp_mean_new = atemp_mean_old * (11/12) + mtemp * (1/12)

			climate.atemp_mean = climate.atemp_mean * W11DIV12 + climate.mtemp * W1DIV12;

			// Record minimum and maximum monthly temperatures

			if (_configuration.getSchedule().month() == 0) {
				climate.mtemp_min = climate.mtemp;
				climate.mtemp_max = climate.mtemp;
			} else {
				if (climate.mtemp < climate.mtemp_min) {
					climate.mtemp_min = climate.mtemp;
				}
				if (climate.mtemp > climate.mtemp_max) {
					climate.mtemp_max = climate.mtemp;
				}
			}

			// On 31 December update records of minimum monthly temperatures for
			// the last
			// 20 years and find minimum monthly temperature for the last 20
			// years

			if (_configuration.getSchedule().isLastMonthOfYear()) {
				startyear = 20 - Math.min(19, _configuration.getSchedule().year());
				climate.mtemp_min20 = climate.mtemp_min;
				climate.mtemp_max20 = climate.mtemp_max;
				for (y = startyear; y < 20; y++) {
					climate.mtemp_min_20[y - 1] = climate.mtemp_min_20[y];
					climate.mtemp_min20 += climate.mtemp_min_20[y];
					climate.mtemp_max_20[y - 1] = climate.mtemp_max_20[y];
					climate.mtemp_max20 += climate.mtemp_max_20[y];
				}
				climate.mtemp_min20 /= 21 - startyear;
				climate.mtemp_max20 /= 21 - startyear;
				climate.mtemp_min_20[19] = climate.mtemp_min;
				climate.mtemp_max_20[19] = climate.mtemp_max;
			}
		}
	}

	public void setClimate(double temp, double prec, double perc_sunshine) {
		/**
		 * Set the climate for the day
		 */
		this.climate.temp = temp;
		this.climate.prec = prec;
		this.climate.insol = perc_sunshine;
		this.climate.instype = Insolation.SUNSHINE;
	}

	public void setLCFractions(double urban, double crop, double pasture, double forest, double natural,
			double peatland) {
		// TODO: This currently sets static LC fractions once only, must change
		// it to set
		// it more configurably, and update with the whole LC dynamics stuff

		// TODO: This will need extra logic to deal with the fractions not
		// summing to 1.0
		// see all of the complicated stuff in landcoverinput.cpp:241 (in
		// original LPJ code)
		this.landcoverfrac[0] = urban;
		this.landcoverfrac[1] = crop;
		this.landcoverfrac[2] = pasture;
		this.landcoverfrac[3] = forest;
		this.landcoverfrac[4] = natural;
		this.landcoverfrac[5] = peatland;

		// Also setting previous LC frac here, but TODO: this needs updating
		this.landcoverfrac_old[0] = urban;
		this.landcoverfrac_old[1] = crop;
		this.landcoverfrac_old[2] = pasture;
		this.landcoverfrac_old[3] = forest;
		this.landcoverfrac_old[4] = natural;

		// Actually do the initialisation based upon these values
		landcover_init();
	}

	// / Creates a new Stand in this grid cell
	void create_stand(LandcoverType landcover, int numpatch) {

		/*
		 * FIXME use the PFTs we actually have. Do we need those not belonging to this
		 * landcover?
		 */

		Stand s = new Stand(this, _configuration, _configuration.getPFTs(), this.soiltype, landcover, numpatch);

		this.stands.add(s);
	}

	// / Creates new stand and initiates land cover settings
	void create_stand_lu(StandType st, double fraction, int numpatch) {

		/*
		 * FIXME use the PFTs we actually have. Do we need those not belonging to this
		 * landcover?
		 */
		LandcoverType lc = st.landcover;
		Stand s = new Stand(this, _configuration, _configuration.getPFTs(), this.soiltype, lc, numpatch);
		s.init_stand_lu(st, fraction);

		this.stands.add(s);
	}

	private void landcover_init() {

		// Set CFT-specific members of gridcellpft:
		for (int p = 0; p < this.pft.size(); p++) {

			GridcellPFT gcpft = this.pft.get(p);

			if (this.getLat() >= 0.0) {
				gcpft.sdate_default = gcpft.pft.sdatenh;
				gcpft.hlimitdate_default = gcpft.pft.hlimitdatenh;
			} else {
				gcpft.sdate_default = gcpft.pft.sdatesh;
				gcpft.hlimitdate_default = gcpft.pft.hlimitdatesh;
			}
			// double cropping in China and Japan.
			if (!gcpft.pft.name.startsWith("TrRi") && this.getLon() >= 60.0 && this.getLat() <= 30.0) {
				gcpft.multicrop = true;
			}
		}

		for (StandType st : _configuration.getSTs()) {
			if (((Configuration) _configuration).nst_lc[st.landcover.ordinal()] == 1) {
				st.frac = 1.0;
			} else {
				st.frac = 1.0 / ((Configuration) _configuration).nst_lc[st.landcover.ordinal()];
			}

			// Create the stands here!
			if (st.frac > 0.0) {
				create_stand_lu(st, st.frac * landcoverfrac[st.landcover.ordinal()], 0);
			}
		}
	}

	public double getLon() {
		return longitude;
	}

	public double getLat() {
		return latitude;
	}

	// / Monitors whether temperature limits have been attained for this pft
	// this day
	/**
	 * Called from crop_sowing_gridcell() each day
	 */
	public void check_crop_temp_limits(GridcellPFT gridcellpft) {

		PFT pft = gridcellpft.pft;
		int day = _configuration.getSchedule().julianDay();

		// check if spring conditions are present this day:
		if (pft.ifsdspring && climate.temp > pft.tempspring && climate.dtemp_31[29] <= pft.tempspring) { // NB.
																											// after
																											// updating
																											// dtemp_31
																											// with
																											// today's
																											// value
			// TeWW,TeCo,TeSf,TeRa: 12,14,13,12 (NB 5,14,15,5 in Bondeau 2007);
			if (climate.lat >= 0.0 && day > 300)
				gridcellpft.last_springdate = day - 365;
			else
				gridcellpft.last_springdate = day;
			gridcellpft.springoccurred = true;
		}

		// check if autumn conditions are present this day:
		if (pft.ifsdautumn) {

			if (climate.temp < pft.tempautumn && climate.dtemp_31[29] >= pft.tempautumn
					&& !gridcellpft.autumnoccurred) { // TeWW,TeRa:
														// 12,17
				if (climate.lat >= 0.0 && day < 100)
					gridcellpft.first_autumndate = day + 365;
				else
					gridcellpft.first_autumndate = day;
				gridcellpft.autumnoccurred = true;
			}

			// check if vernilisation conditions are present this day:

			if (climate.temp < pft.trg && climate.dtemp_31[29] >= pft.trg && !gridcellpft.vernstartoccurred) // TeWW,TeRa:
																												// 12,12
				gridcellpft.vernstartoccurred = true;

			if (climate.temp > pft.trg && climate.dtemp_31[29] <= pft.trg) { // TeWW,TeRa:
																				// 12,12
				if (climate.lat >= 0.0 && day > 300)
					gridcellpft.last_verndate = day - 365;
				else
					gridcellpft.last_verndate = day;
				gridcellpft.vernendoccurred = true;
			}
		}
	}

	// / Updates 20-year mean of dates when temperature limits obtained, used
	// for sowing date calculation
	/**
	 * Called from crop_sowing_gridcell() once a year
	 */
	public void calc_crop_dates_20y_mean(GridcellPFT gridcellpft) {

		int y, startyear;
		PFT pft = gridcellpft.pft;

		int day = _configuration.getSchedule().julianDay();
		int year = _configuration.getSchedule().year();

		// ///////////////////////////////////////////////////////////////////////////////
		// Check if spring and frost conditions occurred during the past year.
		// //
		// If not, set this year's date to either sdate_default or
		// climate.coldestday: //
		// ///////////////////////////////////////////////////////////////////////////////

		// if no spring occured during last year
		if (pft.ifsdspring && !gridcellpft.springoccurred) { // TeWW,TeCo,TeSf,TeRa

			if (climate.temp <= pft.tempspring)
				gridcellpft.last_springdate = day;
			else
				gridcellpft.last_springdate = climate.coldestday;
		}

		// if no autumn occured during last year
		if (pft.ifsdautumn && !gridcellpft.autumnoccurred) { // TeWW,TeRa

			if (climate.maxtemp < pft.tempautumn
					|| climate.maxtemp >= pft.tempautumn && climate.mtemp_min < pft.tempautumn) {

				gridcellpft.first_autumndate = day;
			} else { // too warm

				gridcellpft.first_autumndate = climate.coldestday;

				if (climate.lat >= 0.0 && gridcellpft.first_autumndate < 180)
					gridcellpft.first_autumndate += 365;
			}
		}

		// if temperature did not pass above the vernalisation temperature last
		// year
		if (pft.ifsdautumn && !gridcellpft.vernendoccurred) // TeWW,TeRa
			gridcellpft.last_verndate = gridcellpft.last_springdate + 60; // to
																			// avoid
																			// last_verndate20
																			// to
																			// precede
																			// last_springdate20
																			// (Bondeau
																			// used
																			// coldest
																			// day)
																			// 60
																			// days
																			// is
																			// the
																			// maximum
																			// number
																			// of
																			// vernalization
																			// days

		// /////////////////////////////////////////////////////////////////////////////////////
		// Update spring and frost date 20-year arrays and calculate 20 years
		// average means: //
		// /////////////////////////////////////////////////////////////////////////////////////

		// 1) this year
		if (pft.ifsdspring) // TeWW,TeCo,TeSf,TeRa
			gridcellpft.last_springdate20 = gridcellpft.last_springdate;

		if (pft.ifsdautumn) { // TeWW,TeRa

			gridcellpft.first_autumndate20 = gridcellpft.first_autumndate;
			if (year == 1 && climate.lat >= 0.0) // No autumn first half of
													// first year, set value to
													// same as for second year
				gridcellpft.first_autumndate_20[19] = gridcellpft.first_autumndate;

			gridcellpft.last_verndate20 = gridcellpft.last_verndate;
		}

		// 2) starting year (1st of 20 or less)
		startyear = 20 - Math.min(19, year);

		// 3) past 20 years or less
		for (y = startyear; y < 20; y++) {
			if (pft.ifsdspring) { // TeWW,TeCo,TeSf,TeRa

				gridcellpft.last_springdate_20[y - 1] = gridcellpft.last_springdate_20[y];
				gridcellpft.last_springdate20 += gridcellpft.last_springdate_20[y];
			}
			if (pft.ifsdautumn) { // TeWW,TeRa

				gridcellpft.first_autumndate_20[y - 1] = gridcellpft.first_autumndate_20[y];
				gridcellpft.first_autumndate20 += gridcellpft.first_autumndate_20[y];

				gridcellpft.last_verndate_20[y - 1] = gridcellpft.last_verndate_20[y];
				gridcellpft.last_verndate20 += gridcellpft.last_verndate_20[y];
			}
		}

		// 4) 20 years average means:
		if (pft.ifsdspring) { // TeWW,TeCo,TeSf,TeRa
			gridcellpft.last_springdate20 /= Math.min(20, year + 1);
			if (gridcellpft.last_springdate20 < 0)
				gridcellpft.last_springdate20 += 365;
			gridcellpft.last_springdate_20[19] = gridcellpft.last_springdate;
		}

		if (pft.ifsdautumn) { // TeWW,TeRa
			gridcellpft.first_autumndate20 /= Math.min(20, year + 1);
			if (gridcellpft.first_autumndate20 > 364)
				gridcellpft.first_autumndate20 -= 365;
			gridcellpft.first_autumndate_20[19] = gridcellpft.first_autumndate;

			gridcellpft.last_verndate20 /= Math.min(20, year + 1);
			if (gridcellpft.last_verndate20 < 0)
				gridcellpft.last_verndate20 += 365;
			gridcellpft.last_verndate_20[19] = gridcellpft.last_verndate;
		}
	}

	// / Calculates sdatecalc_temp
	/**
	 * Called from crop_sowing_gridcell() once a year
	 */
	public void set_sdatecalc_temp(GridcellPFT gridcellpft) {
		PFT pft = gridcellpft.pft;

		if (pft.ifsdautumn && pft.forceautumnsowing != ForceAutumnSowing.SPRINGSOWING) { // TeWW,TeRa:

			// Use autumn sowing if first_autumndate20 is set (autumn conditions
			// met during the past 20 years):
			if (!((gridcellpft.first_autumndate20 == climate.testday_temp
					|| gridcellpft.first_autumndate20 == climate.coldestday)
					&& gridcellpft.first_autumndate % 365 == gridcellpft.first_autumndate20)) {

				gridcellpft.sdatecalc_temp = gridcellpft.first_autumndate20;
				gridcellpft.wintertype = true;
			}
			// if not, use spring sowing
			else { // if(gridcellpft.first_autumndate20==climate.coldestday)

				if (!((gridcellpft.last_springdate20 == climate.testday_temp
						|| gridcellpft.last_springdate20 == climate.coldestday)
						&& gridcellpft.last_springdate == gridcellpft.last_springdate20)
						&& pft.forceautumnsowing != ForceAutumnSowing.AUTUMNSOWING) {

					gridcellpft.sdatecalc_temp = gridcellpft.last_springdate20;
					gridcellpft.wintertype = false;
				} else { // If neither spring nor autumn occurred during the
							// past 20 years.

					if (climate.maxtemp < pft.tempspring) // Too cold to sow at
															// all.
						gridcellpft.sdatecalc_temp = -1;
					else { // Too warm; avoid warmest period.

						gridcellpft.sdatecalc_temp = climate.coldestday;
						gridcellpft.wintertype = true;
					}
				}
			}

			// If autumn first_autumndate20 is earlier than hlimitdate, use
			// last_springdate20 (winter is too long):
			if (Utils.dayinperiod(gridcellpft.sdatecalc_temp, climate.testday_temp, gridcellpft.hlimitdate_default)
					&& pft.forceautumnsowing != ForceAutumnSowing.AUTUMNSOWING) {

				gridcellpft.sdatecalc_temp = gridcellpft.last_springdate20; // use
																			// last_springdate20
																			// disregarding
																			// earlier
																			// choices
				gridcellpft.wintertype = false;
			}

			// Forced sowing date read from input file.
			// Calculated value used if value for pft not found in file.
			if (pft.readsowingdate && gridcellpft.sdate_force >= 0) {

				if ((Math.abs(gridcellpft.sdate_force - gridcellpft.first_autumndate20) <= Math
						.abs(gridcellpft.sdate_force - gridcellpft.last_springdate20)))
					gridcellpft.wintertype = true;
				else
					gridcellpft.wintertype = false;
				gridcellpft.sdatecalc_temp = gridcellpft.sdate_force;
			}
		} else if (pft.ifsdspring) { // TeCo,TeSf

			if (pft.name.startsWith("TeCo"))
				gridcellpft.sdatecalc_temp = (int) (60.0 / 85.0 * (gridcellpft.last_springdate20 - climate.adjustlat)
						+ 29.5 + climate.adjustlat);
			else if (pft.name.startsWith("TeSf"))
				gridcellpft.sdatecalc_temp = gridcellpft.last_springdate20;
			else
				gridcellpft.sdatecalc_temp = gridcellpft.last_springdate20;
		}
		// Climatic limits for TeWW growth:
		if (pft.name.startsWith("TeWW") && climate.mtemp_min20 > 15.0)
			// if(!strncmp(pft.name,"TeWW", strlen("TeWW")) &&
			// climate.mtemp_min20 > -20.0) //Test continuous grass
			gridcellpft.sdatecalc_temp = -1;

		gridcellpft.springoccurred = false;
		gridcellpft.vernstartoccurred = false;
		gridcellpft.vernendoccurred = false;
		gridcellpft.autumnoccurred = false;
	}

	// / Updates various climate 20-year means, used for sowing date calculation
	/**
	 * Called from crop_sowing_gridcell() once a year
	 */
	public void calc_m_climate_20y_mean() {
		int m, y;
		int year = _configuration.getSchedule().year();

		int startyear = 20 - Math.min(19, year);
		double var_temp = 0, var_prec = 0;
		double mtemp20kelvin[] = new double[12];
		double prec_pet_ratio20[] = new double[12];
		double mprec_petmin_thisyear = 1.0;
		double mprec_petmax_thisyear = 0.0;

		climate.aprec = 0.0;

		for (m = 0; m < 12; m++) {

			// 1) this year
			climate.mtemp20[m] = climate.mtemp_year[m];
			climate.mprec20[m] = climate.mprec_year[m];
			// historic
			climate.mtemp20[m] = climate.hmtemp_20[m].lastadd();
			climate.mprec20[m] = climate.hmprec_20[m].lastadd();
			climate.aprec += climate.hmprec_20[m].lastadd();
			climate.mpet_year[m] = climate.hmeet_20[m].lastadd() * _configuration.PRIESTLEY_TAYLOR;
			//
			climate.mpet20[m] = climate.mpet_year[m];
			if (climate.mpet_year[m] > 0.0)
				climate.mprec_pet20[m] = climate.mprec_year[m] / climate.mpet_year[m];
			else
				climate.mprec_pet20[m] = 0.0;

			/*
			 * if(climate.mprec_year[m] / climate.mpet_year[m] < mprec_petmin_thisyear)
			 * mprec_petmin_thisyear = climate.mprec_year[m] / climate.mpet_year[m];
			 * if(climate.mprec_year[m] / climate.mpet_year[m] > mprec_petmax_thisyear)
			 * mprec_petmax_thisyear = climate.mprec_year[m] / climate.mpet_year[m];
			 */
			if (climate.hmprec_20[m].lastadd() / climate.mpet_year[m] < mprec_petmin_thisyear)
				mprec_petmin_thisyear = climate.hmprec_20[m].lastadd() / climate.mpet_year[m];
			if (climate.hmprec_20[m].lastadd() / climate.mpet_year[m] > mprec_petmax_thisyear)
				mprec_petmax_thisyear = climate.hmprec_20[m].lastadd() / climate.mpet_year[m];

			// 2) past 20 years or less
			for (y = startyear; y < 20; y++) {
				climate.mtemp_20[y - 1][m] = climate.mtemp_20[y][m];
				climate.mtemp20[m] += climate.mtemp_20[y][m];

				climate.mprec_20[y - 1][m] = climate.mprec_20[y][m];
				climate.mprec20[m] += climate.mprec_20[y][m];

				climate.mpet_20[y - 1][m] = climate.mpet_20[y][m];
				climate.mpet20[m] += climate.mpet_20[y][m];

				climate.mprec_pet_20[y - 1][m] = climate.mprec_pet_20[y][m];
				climate.mprec_pet20[m] += climate.mprec_pet_20[y][m];
			}
			// 3) 20 years average means:
			climate.mtemp20[m] /= Math.min(20, year + 1);
			climate.mprec20[m] /= Math.min(20, year + 1);
			climate.mpet20[m] /= Math.min(20, year + 1);
			climate.mprec_pet20[m] /= Math.min(20, year + 1);

			/*
			 * climate.mtemp_20[19][m] = climate.mtemp_year[m]; climate.mprec_20[19][m] =
			 * climate.mprec_year[m];
			 */
			climate.mtemp_20[19][m] = climate.hmtemp_20[m].lastadd();
			climate.mprec_20[19][m] = climate.hmprec_20[m].lastadd();

			climate.mpet_20[19][m] = climate.mpet_year[m];
			if (climate.mpet_year[m] > 0.0)
				// climate.mprec_pet_20[19][m] = climate.mprec_year[m] /
				// climate.mpet_year[m];
				climate.mprec_pet_20[19][m] = climate.hmprec_20[m].lastadd() / climate.mpet_year[m];
			else
				climate.mprec_pet_20[19][m] = 0.0;
		}

		climate.mprec_petmin20 = mprec_petmin_thisyear;
		climate.mprec_petmax20 = mprec_petmax_thisyear;
		for (y = startyear; y < 20; y++) {
			climate.mprec_petmin_20[y - 1] = climate.mprec_petmin_20[y];
			climate.mprec_petmin20 += climate.mprec_petmin_20[y];
			climate.mprec_petmax_20[y - 1] = climate.mprec_petmax_20[y];
			climate.mprec_petmax20 += climate.mprec_petmax_20[y];
		}
		climate.mprec_petmin20 /= Math.min(20, year + 1);
		climate.mprec_petmin_20[19] = mprec_petmin_thisyear;
		climate.mprec_petmax20 /= Math.min(20, year + 1);
		climate.mprec_petmax_20[19] = mprec_petmax_thisyear;
	}

	/// Determines climate seasonality of gridcell
	/**
	 * Called from crop_sowing_gridcell() once a year
	 */
	void calc_seasonality() {

		double var_temp = 0, var_prec = 0;
		double TEMPMIN = 10.0; // temperature limit of coldest month used to
								// determine type of temperature
								// seasonality
		int NMONTH = 12;

		double mtempKelvin[] = new double[NMONTH];
		double prec_pet_ratio20[] = new double[NMONTH];
		double maxprec_pet20 = 0.0;
		double minprec_pet20 = 1000;

		// calculate absolute temperature and prec/pet ratio for each month this
		// year
		for (int i = 0; i < NMONTH; ++i) {
			// The temperature has got to be in Kelvin, the limit 0.010 is based
			// on that.
			mtempKelvin[i] = climate.mtemp20[i] + 273.15;
			// Calculate precipitation/PET ratio if monthly PET is above zero
			prec_pet_ratio20[i] = (climate.mpet20[i] > 0) ? climate.mprec20[i] / climate.mpet20[i] : 0;
		}

		// calculate variation coeffecients of temperature and prec/pet ratio
		// for this year
		var_temp = Utils.variation_coefficient(mtempKelvin, NMONTH);
		var_prec = Utils.variation_coefficient(prec_pet_ratio20, NMONTH);

		climate.var_prec = var_prec;
		climate.var_temp = var_temp;

		if (var_prec <= 0.4 && var_temp <= 0.010) // no seasonality
			climate.seasonality_lastyear = SeasonalityType.SEASONALITY_NO; // 0
		else if (var_prec > 0.4) {

			if (var_temp <= 0.010) // precipitation seasonality only
				climate.seasonality_lastyear = SeasonalityType.SEASONALITY_PREC; // 1
			else if (var_temp > 0.010) {

				if (climate.mtemp_min20 > TEMPMIN) // both seasonalities, but
													// "weak" temperature
													// seasonality
													// (coldest month > 10degC)
					climate.seasonality_lastyear = SeasonalityType.SEASONALITY_PRECTEMP; // 2
				else if (climate.mtemp_min20 < TEMPMIN) // both seasonalities,
														// but temperature most
														// important
					climate.seasonality_lastyear = SeasonalityType.SEASONALITY_TEMPPREC; // 4
			}
		} else if (var_prec <= 0.4) {

			if (var_temp > 0.010) // Temperature seasonality only
				climate.seasonality_lastyear = SeasonalityType.SEASONALITY_TEMP; // 3

			/*
			 * // SEASONALITY_TEMPWARM currently not used, default sdate value is coldest
			 * day anyway when always above PFT limit. if(gridcell.climate.mtemp_min20 <
			 * TEMPMIN) // Temperature seasonality only climate.seasonality_lastyear =
			 * SEASONALITY_TEMP; // 3 else if(gridcell.climate.mtemp_min20 >= TEMPMIN)) //
			 * Temperature seasonality, always above 10 degrees climate.seasonality_lastyear
			 * = SEASONALITY_TEMPWARM; // 5
			 */
		}

		for (int m = 0; m < 12; m++) {
			if (climate.mprec_pet20[m] > maxprec_pet20)
				maxprec_pet20 = climate.mprec_pet20[m];
			if (climate.mprec_pet20[m] < minprec_pet20)
				minprec_pet20 = climate.mprec_pet20[m];
		}

		if (minprec_pet20 <= 0.5 && maxprec_pet20 <= 0.5) // Extremes of monthly
															// means
			climate.prec_seasonality_lastyear = PrecSeasonalityType.DRY; // 0
		else if (minprec_pet20 <= 0.5 && maxprec_pet20 > 0.5 && maxprec_pet20 <= 1.0)
			climate.prec_seasonality_lastyear = PrecSeasonalityType.DRY_INTERMEDIATE; // 1
		else if (minprec_pet20 <= 0.5 && maxprec_pet20 > 1.0)
			climate.prec_seasonality_lastyear = PrecSeasonalityType.DRY_WET; // 2
		else if (minprec_pet20 > 0.5 && minprec_pet20 <= 1.0 && maxprec_pet20 > 0.5 && maxprec_pet20 <= 1.0)
			climate.prec_seasonality_lastyear = PrecSeasonalityType.INTERMEDIATE; // 3
		else if (minprec_pet20 > 1.0 && maxprec_pet20 > 1.0)
			climate.prec_seasonality_lastyear = PrecSeasonalityType.WET; // 5
		else if (minprec_pet20 > 0.5 && minprec_pet20 <= 1.0 && maxprec_pet20 > 1.0)
			climate.prec_seasonality_lastyear = PrecSeasonalityType.INTERMEDIATE_WET; // 4

		if (climate.mprec_petmin20 <= 0.5 && climate.mprec_petmax20 <= 0.5) // Average
																			// of
																			// extremes
			climate.prec_range_lastyear = PrecSeasonalityType.DRY; // 0
		else if (climate.mprec_petmin20 <= 0.5 && climate.mprec_petmax20 > 0.5 && climate.mprec_petmax20 <= 1.0)
			climate.prec_range_lastyear = PrecSeasonalityType.DRY_INTERMEDIATE; // 1
		else if (climate.mprec_petmin20 <= 0.5 && climate.mprec_petmax20 > 1.0)
			climate.prec_range_lastyear = PrecSeasonalityType.DRY_WET; // 2
		else if (climate.mprec_petmin20 > 0.5 && climate.mprec_petmin20 <= 1.0 && climate.mprec_petmax20 > 0.5
				&& climate.mprec_petmax20 <= 1.0)
			climate.prec_range_lastyear = PrecSeasonalityType.INTERMEDIATE; // 3
		else if (climate.mprec_petmin20 > 1.0 && climate.mprec_petmax20 > 1.0)
			climate.prec_range_lastyear = PrecSeasonalityType.WET; // 5
		else if (climate.mprec_petmin20 > 0.5 && climate.mprec_petmin20 <= 1.0 && climate.mprec_petmax20 > 1.0)
			climate.prec_range_lastyear = PrecSeasonalityType.INTERMEDIATE_WET; // 4

		if (climate.mtemp_max20 <= 10)
			climate.temp_seasonality_lastyear = TempSeasonalityType.COLD; // 0
		else if (climate.mtemp_min20 <= 10 && climate.mtemp_max20 > 10 && climate.mtemp_max20 <= 30)
			climate.temp_seasonality_lastyear = TempSeasonalityType.COLD_WARM; // 1
		else if (climate.mtemp_min20 <= 10 && climate.mtemp_max20 > 30)
			climate.temp_seasonality_lastyear = TempSeasonalityType.COLD_HOT; // 2
		else if (climate.mtemp_min20 > 10 && climate.mtemp_max20 <= 30)
			climate.temp_seasonality_lastyear = TempSeasonalityType.WARM; // 3
		else if (climate.mtemp_min20 > 30)
			climate.temp_seasonality_lastyear = TempSeasonalityType.HOT; // 5
		else if (climate.mtemp_min20 > 10 && climate.mtemp_max20 > 30)
			climate.temp_seasonality_lastyear = TempSeasonalityType.WARM_HOT; // 4
	}

	void update_seasonality() {
		climate.seasonality = climate.seasonality_lastyear;
		climate.temp_seasonality = climate.temp_seasonality_lastyear;
		climate.prec_seasonality = climate.prec_seasonality_lastyear;
		climate.prec_range = climate.prec_range_lastyear;
	}

	/// Calculates sowing window for each crop pft
	/**
	 * Called from crop_sowing_gridcell() once a year
	 */
	public void calc_sowing_windows() {
		SeasonalityType seasonality = climate.seasonality;
		int sow_month = 0;

		// Find the wettest month
		if (climate.seasonality == SeasonalityType.SEASONALITY_PREC
				|| climate.seasonality == SeasonalityType.SEASONALITY_PRECTEMP) {

			double max = 0.0;
			double sum = 0.0;

			for (int m = 0; m < 12; m++) {
				sum = 0.0;
				for (int i = 0; i < 4; i++) {
					int mm = m + i;
					if (mm >= 12)
						mm -= 12;

					// Implement a check later to see if it makes any difference
					// to to use the precipitation
					// only
					if (true) {
						if (climate.mpet20[mm] > 0.0)
							sum += climate.mprec20[mm] / climate.mpet20[mm];
					} else
						sum += climate.mprec20[mm];
				}

				if (sum > max) {
					max = sum;
					// Months are stored as, 0-11
					sow_month = m;
				}
			}
		}

		for (PFT pft : _configuration.getPFTs()) {

			GridcellPFT gridcellpft = this.pft.get(pft.id);

			if (pft.phenology == Phenology.CROPGREEN) {

				int swindow_temp[] = new int[2];
				int swindow_prec[] = new int[2];

				// Calculate temperature-dependent sowing windows
				if (gridcellpft.sdatecalc_temp != -1) {

					// Set sowing window around sdatecalc_temp
					swindow_temp[0] = Utils.stepfromdate(gridcellpft.sdatecalc_temp, -15);
					swindow_temp[1] = Utils.stepfromdate(gridcellpft.sdatecalc_temp, 15);

					if (!gridcellpft.wintertype && Utils.dayinperiod(swindow_temp[0],
							Utils.stepfromdate(climate.coldestday, -100), climate.coldestday)) {

						swindow_temp[0] = climate.coldestday;
						// swindow_temp[0] = gridcellpft.sdatecalc_temp; //gives
						// better yields, but sdate
						// transition not smooth
						if (Utils.dayinperiod(swindow_temp[1], Utils.stepfromdate(climate.coldestday, -100),
								climate.coldestday))
							swindow_temp[1] = climate.coldestday;
					}

					if (gridcellpft.wintertype && Utils.dayinperiod(swindow_temp[1], climate.coldestday,
							Utils.stepfromdate(climate.coldestday, 100))) {

						swindow_temp[1] = climate.coldestday;
						// swindow_temp[1] = gridcellpft.sdatecalc_temp; //gives
						// better yields, but sdate
						// transition not smooth
						if (Utils.dayinperiod(swindow_temp[0], climate.coldestday,
								Utils.stepfromdate(climate.coldestday, 100)))
							swindow_temp[0] = climate.coldestday;
					}
				}

				// Calculate precipitation-dependent sowing windows
				Utils.monthdates(swindow_prec[0], swindow_prec[1], sow_month);
				// A conservative choice to expand the sowing window
				swindow_prec[0] = Utils.stepfromdate(swindow_prec[0], -15);

				// Determine, based upon site climate seasonality, if sowing in
				// rainfed stands should be
				// triggered by
				// temperature or precipitation, or whether to use a default
				// sowing date.

				boolean temp_sdate = false, prec_sdate = false, def_sdate = false;

				if (seasonality == SeasonalityType.SEASONALITY_TEMP
						|| seasonality == SeasonalityType.SEASONALITY_TEMPPREC)
					temp_sdate = true;
				else if ((seasonality == SeasonalityType.SEASONALITY_PREC
						|| seasonality == SeasonalityType.SEASONALITY_PRECTEMP)
						&& climate.prec_range != PrecSeasonalityType.WET)
					prec_sdate = true;
				else // if(seasonality == SEASONALITY_NO) || (seasonality ==
						// SEASONALITY_PREC || seasonality
						// == SEASONALITY_PRECTEMP) && climate.prec_range ==
						// WET)
					def_sdate = true;

				if (temp_sdate) {
					gridcellpft.swindow[0] = swindow_temp[0];
					gridcellpft.swindow[1] = swindow_temp[1];
				} else if (prec_sdate) {
					gridcellpft.swindow[0] = swindow_prec[0];
					gridcellpft.swindow[1] = swindow_prec[1];
				} else if (def_sdate) {
					gridcellpft.swindow[0] = gridcellpft.sdate_default;
					gridcellpft.swindow[1] = Utils.stepfromdate(gridcellpft.sdate_default, 15);
				}

				// Rules for irrigated crops:

				// Different sowing date options for irrigated crops at sites
				// with climate.seasonality ==
				// SEASONALITY_PRECTEMP:
				// 1. use temperature-dependent sowing limits (define
				// IRRIGATED_USE_TEMP_SDATE)
				// 2. use precipitation-triggered sowing
				// (IRRIGATED_USE_TEMP_SDATE undefined)

				boolean irr_use_temp_sdate = false;

				if (irr_use_temp_sdate) {
					gridcellpft.swindow_irr[0] = swindow_temp[0];
					gridcellpft.swindow_irr[1] = swindow_temp[1];
				} else {
					gridcellpft.swindow_irr[0] = gridcellpft.swindow[0];
					gridcellpft.swindow_irr[1] = gridcellpft.swindow[1];
				}

				// Includes all temperature limits for sowing set in
				// set_sdatecalc_temp() also for sites with
				// any type of temperature seasonality
				if (gridcellpft.sdatecalc_temp == -1) {
					gridcellpft.swindow[0] = -1;
					gridcellpft.swindow[1] = -1;
				}

			}
		}
	}

	public void crop_sowing() {
		int d;

		if (_configuration.getSchedule().isFirstDayOfSimulation()) {

			for (d = 0; d < 10; d++)
				climate.dprec_10[d] = climate.prec;
			for (d = 0; d < 2; d++)
				climate.sprec_2[d] = climate.prec;
		}

		climate.sprec_2[0] = climate.sprec_2[1];
		climate.sprec_2[1] = climate.prec;
		for (d = 0; d < 9; d++) {
			climate.dprec_10[d] = climate.dprec_10[d + 1];
			climate.sprec_2[1] += climate.dprec_10[d];
		}
		climate.dprec_10[9] = climate.prec;

		if (climate.temp > climate.maxtemp) // To know if temperature rises over
											// vernalization limit
			climate.maxtemp = climate.temp;

		// Loop through PFTs
		for (PFT pft : _configuration.getPFTs()) {
			GridcellPFT gridcellpft = this.pft.get(pft.id);

			if (pft.landcover == LandcoverType.CROP) {

				// If NEWSOWINGDATE is defined, all pft:s enter this code
				if (pft.ifsdcalc) { // TeWW,TrRi,TeCo,TrMi,TrMa,TeSf,TrPe,TeRa;
									// sdate set in getgridcell() kept for the
									// rest; (no code here for TrRi)

					if (pft.ifsdtemp) { // TeWW,TeCo,TeSf,TeRa

						// Check whether temperature limits have been attained
						// today
						check_crop_temp_limits(gridcellpft);

						// June 30(180) in the north, December 31(364) in the
						// south
						if (_configuration.getSchedule().julianDay() == climate.testday_temp) {

							// Update 20-year mean of dates when temperature
							// limits obtained
							calc_crop_dates_20y_mean(gridcellpft);

							// Determine sdatecalc_temp:
							set_sdatecalc_temp(gridcellpft);

							// Reset maxtemp to today's value
							climate.maxtemp = climate.temp;
						}
					}
				}
			}
		}

		if (_configuration.getSchedule().isLastDayOfYear()) {
			// Update various climate 20-year means
			calc_m_climate_20y_mean();
			// Determines climate seasonality of gridcell
			calc_seasonality();
		}

		if (_configuration.getSchedule().julianDay() == climate.testday_temp) { // day
																				// 180/364

			update_seasonality();

			// Calculate sowing window for each crop pft
			calc_sowing_windows();
		}

	}

	/**
	 * Set all the passed
	 * 
	 * @param state
	 * @param value
	 */
//    public void setState(IState state, Object value, IAggregation aggregation) {
//
//    }

//    public Object getState(IState state, IAggregation aggregation) {
//        return null;
//    }

};
