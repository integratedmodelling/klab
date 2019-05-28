package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.Climate;
import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.Soil;
import org.integratedmodelling.ecology.biomass.lpjguess.Vegetation;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.SimProcess;
import org.integratedmodelling.procsim.api.IConfiguration;

public class SoilWater extends SimProcess {

	public SoilWater(IConfiguration configuration) {
		super(configuration);
	}

	private static final boolean ifevap = true; // whether to model evaporation
												// from soil surface

	/*
	 * shuttles for stupid pass-by-reference
	 */
	class Hres {
		double wcont_evap;
		double runoff;
	}

	class Sres {
		double snowpack;
		double rain;
		double melt;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// SNOW
	// Internal function (do not call directly from framework)

	private Sres snow(double prec, double temp, double snowpack, double rain, double melt) {

		// DESCRIPTION
		// Daily calculation of snowfall and rainfall from precipitation and
		// snow melt from
		// snow pack; update of snow pack with new snow and snow melt and snow
		// melt

		// INPUT PARAMETERS
		// prec = precipitation today (mm)
		// temp = air temperature today (deg C)

		// INPUT AND OUTPUT PARAMETER
		// snowpack = stored snow (rainfall mm equivalents)

		// OUTPUT PARAMETERS
		// rain = rainfall today (mm)
		// melt = snow melt today (mm)

		final double TSNOW = 0.0;
		// maximum temperature for precipitation as snow (deg C)
		// previously 2 deg C; new value suggested by Dieter Gerten 2002-12
		final double KM = 3.0;
		// coefficient in snowmelt function
		// changed from 0.7, S. Sitch, pers. comm, 2001-11-28
		final double SNOWPACK_MAX = 10000.0;
		// maximum size of snowpack (mm) (S. Sitch, pers. comm. 2001-11-28)

		double inc;

		if (temp < TSNOW) // snowing today
		{
			inc = Math.min(prec, SNOWPACK_MAX - snowpack);
			snowpack += inc;
			rain = prec - inc;
			melt = 0.0;
		} else // melting today
		{
			rain = prec;

			// New snow melt formulation
			// Dieter Gerten 021121
			// Ref: Choudhury et al 1998

			melt = Math.min((1.5 + 0.007 * prec) * (temp - TSNOW), snowpack);
			snowpack -= melt;
		}

		Sres ret = new Sres();

		ret.snowpack = snowpack;
		ret.melt = melt;
		ret.rain = rain;

		return ret;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// HYDROLOGY
	// Internal function (do not call directly from framework)

	private Hres hydrology_lpjf(Patch patch, double pet, double rain, double melt, double perc_base, double perc_exp,
			double[] awc, double fevap, double[] awcont, double[] wcont, double wcont_evap, double runoff,
			double snowpack) {

		// DESCRIPTION
		// Daily update of water content for each soil layer given snow melt,
		// rainfall,
		// evapotranspiration from vegetation (AET) and percolation between
		// layers;
		// calculation of runoff

		// guess2008 - DLE - new function signature. We now take awcont[] as an
		// argument

		// INPUT PARAMETERS
		// pet = potential evapotranspiration today (mm)
		// rain = rainfall today (mm)
		// melt = snowmelt today (mm)
		// perc_base = coefficient in percolation calculation (K in Eqn 31,
		// Haxeltine
		// & Prentice 1996)
		// perc_exp = exponent in percolation calculation (=4 in Eqn 31,
		// Haxeltine &
		// Prentice 1996)
		// awc = array containing available water holding capacity of soil
		// layers (mm rainfall) [0=upper layer]
		// fevap = fraction of modelled area (grid cell or patch) subject to
		// evaporation from soil surface

		// INPUT AND OUTPUT PARAMETERS
		// wcont = array containing water content of soil layers [0=upper layer]
		// as
		// fraction of available water holding capacity (AWC)
		// wcont_evap = water content of evaporation sublayer at top of upper
		// soil layer
		// as fraction of available water holding capacity (AWC)
		// awcont = wcont averaged over the growing season - guess2008

		// OUTPUT PARAMETER
		// runoff = total daily runoff from all soil layers (mm/day)

		final double SOILDEPTH_EVAP = 200.0;
		// depth of sublayer at top of upper soil layer, from which evaporation
		// is
		// possible (NB: must not exceed value of global constant
		// SOILDEPTH_UPPER)
		final double BASEFLOW_FRAC = 0.5;
		// Fraction of standard percolation amount from lower soil layer that is
		// diverted to baseflow runoff
		final double K_DEPTH = 0.4;
		final double K_AET = 0.52;
		// Fraction of total (vegetation) AET from upper soil layer that is
		// derived
		// from the top K_DEPTH (fraction) of the upper soil layer
		// (parameters for calculating K_AET_DEPTH below)
		final double K_AET_DEPTH = (_configuration.SOILDEPTH_UPPER / SOILDEPTH_EVAP - 1.0) * (K_AET / K_DEPTH - 1.0)
				/ (1.0 / K_DEPTH - 1.0) + 1.0;
		// Weighting coefficient for AET flux from evaporation layer, assuming
		// active
		// root density decreases with soil depth
		// Equates to 1.3 given SOILDEPTH_EVAP=200 mm, SOILDEPTH_UPPER=500 mm,
		// K_DEPTH=0.4, K_AET=0.52

		int s;
		double aet; // AET for a particular layer and individual (mm)
		double[] aet_layer = new double[IConfiguration.NSOILLAYER]; // total AET
																	// for each
																	// soil
		// layer (mm)

		double aet_total; // total AET (mm)
		double runoff_surf; // runoff from upper soil layer (mm)
		double runoff_drain; // runoff (drainage) from lower soil layers (mm)
		double runoff_baseflow; // base flow (mm)
		double evap; // evaporation from soil surface (mm)
		double perc;
		double perc_frac;
		double perc_baseflow;
		double influx; // inward water flux to soil today (rain + snowmelt) (mm)

		// Retrieve Vegetation for this patch
		Vegetation vegetation = patch.vegetation;

		for (s = 0; s < IConfiguration.NSOILLAYER; s++) {
			aet_layer[s] = 0.0;
		}
		aet_total = 0.0;

		// Sum AET for across all vegetation individuals
		for (Individual indiv : vegetation) {

			for (s = 0; s < IConfiguration.NSOILLAYER; s++) {
				aet = patch.pft.get(indiv.pft.id).fuptake[s] * indiv.aet;
				aet_layer[s] += aet;
				aet_total += aet;
			}
		}

		// Evaporation from soil surface

		// guess2008 - changed to wcont_evap**2, as in LPJ-mL
		// - see Bondeau et al. (2007), Rost et al. (2008)
		// Added the snowdepth restriction too.
		if (snowpack < 10.0) // i.e. evap only if snow depth < 10mm
		{
			evap = pet * IConfiguration.PRIESTLEY_TAYLOR * wcont_evap * wcont_evap * fevap;
		} else {
			evap = 0.0;
		}

		// Implement in- and outgoing fluxes to upper soil layer
		// BLARP: water content can become negative, though apparently only very
		// slightly
		// - quick fix implemented here, should be done better later

		wcont[0] += (rain + melt - aet_layer[0] - evap) / awc[0];
		if (wcont[0] != 0.0 && wcont[0] < 0.0001) // guess2008 - bugfix
		{
			wcont[0] = 0.0;
		}

		// Surface runoff

		if (wcont[0] > 1.0) {
			runoff_surf = (wcont[0] - 1.0) * awc[0];
			wcont[0] = 1.0;
		} else {
			runoff_surf = 0.0;
		}

		// Update water content in evaporation layer for tomorrow

		influx = rain + melt;

		wcont_evap += (influx - aet_layer[0] * SOILDEPTH_EVAP * K_AET_DEPTH / IConfiguration.SOILDEPTH_UPPER - evap)
				/ awc[0];

		if (wcont_evap > wcont[0]) {
			wcont_evap = wcont[0];
		}

		// Percolation from evaporation layer

		if (influx > 0.1) {
			perc = Math.min(
					SOILDEPTH_EVAP / IConfiguration.SOILDEPTH_UPPER * perc_base * Math.pow(wcont_evap, perc_exp),
					influx);
		} else {
			perc = 0.0;
		}

		wcont_evap -= perc / awc[0];

		// Percolation and fluxes to and from lower soil layer(s)

		// Transfer percolation between soil layers
		// Excess water transferred to runoff
		// Eqns 26, 27, 31, Haxeltine & Prentice 1996

		runoff_drain = 0.0;

		for (s = 1; s < IConfiguration.NSOILLAYER; s++) {

			// Percolation
			// Allow only on days with rain or snowmelt (Dieter Gerten, 021216)

			if (influx >= 0.1) {
				perc = Math.min(perc_base * Math.pow(wcont[s - 1], perc_exp), influx);
			} else {
				perc = 0.0;
			}

			perc_frac = Math.min(perc / awc[s - 1], wcont[s - 1]);

			wcont[s - 1] -= perc_frac;
			wcont[s] += perc_frac * awc[s - 1] / awc[s];
			if (wcont[s] > 1.0) {
				runoff_drain += (wcont[s] - 1.0) * awc[s];
				wcont[s] = 1.0;
			}

			// Deduct AET from this soil layer
			// BLARP! Quick fix here to prevent negative soil water

			wcont[s] -= aet_layer[s] / awc[s];
			if (wcont[s] < 0.0) {
				wcont[s] = 0.0;
			}
		}

		// Baseflow runoff (Dieter Gerten 021216) (rain or snowmelt days only)

		if (influx >= 0.1) {
			perc_baseflow = BASEFLOW_FRAC * perc_base * Math.pow(wcont[IConfiguration.NSOILLAYER - 1], perc_exp);
			// guess2008 - Added "&& influx >= runoff_surf" to guarantee
			// nonnegative baseflow.
			if (perc_baseflow > influx - runoff_surf && influx >= runoff_surf) {
				perc_baseflow = influx - runoff_surf;
			}

			// Deduct from water content of bottom soil layer

			perc_frac = Math.min(perc_baseflow / awc[IConfiguration.NSOILLAYER - 1],
					wcont[IConfiguration.NSOILLAYER - 1]);
			wcont[IConfiguration.NSOILLAYER - 1] -= perc_frac;
			runoff_baseflow = perc_frac * awc[IConfiguration.NSOILLAYER - 1];
		} else {
			runoff_baseflow = 0.0;
		}

		runoff = runoff_surf + runoff_drain + runoff_baseflow;

		patch.arunoff += runoff;
		patch.aaet += aet_total;
		patch.aevap += evap;

		patch.maet[getSchedule().month()] += aet_total;
		patch.mevap[getSchedule().month()] += evap;
		patch.mrunoff[getSchedule().month()] += runoff;

		// guess2008 - DLE - update awcont
		// Original algorithm by Thomas Hickler
		for (s = 0; s < IConfiguration.NSOILLAYER; s++) {

			// Reset the awcont array on the first day of every year
			if (getSchedule().julianDay() == 0) {
				awcont[s] = 0.0;
				if (s == 0) {
					patch.growingseasondays = 0;
				}
			}

			// If it's warm enough for growth, update awcont with this day's
			// wcont
			if (patch.getClimate().temp > 5.0) {
				awcont[s] += wcont[s];
				if (s == 0) {
					patch.growingseasondays++;
				}
			}

			// Do the averaging on the last day of every year
			if (getSchedule().isLastDayOfYear()) {
				awcont[s] /= patch.growingseasondays;
			}

			// In case it's never warm enough:
			if (patch.growingseasondays < 1) {
				awcont[s] = 1.0;
			}
		}

		Hres ret = new Hres();
		ret.runoff = runoff;
		ret.wcont_evap = wcont_evap;

		return ret;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// SOIL WATER
	// Call this function each simulation day for each patch,
	// following
	// calculation of vegetation production and evapotranspiration and before
	// soil organic
	// matter and litter dynamics

	public void process(Patch patch) {

		Climate climate = patch.getClimate();

		// DESCRIPTION
		// Performs daily accounting of soil water and snow pack

		double rain = 0; // rainfall today (mm)
		double melt = 0; // snow melt today (mm)
		double fpc_phen_total; // phenology-weighted FPC sum for patch

		// Retrieve Vegetation and Soil objects for patch

		Vegetation vegetation = patch.vegetation;
		Soil soil = patch.soil;

		// Update snowpack and derive actual water input to soil, taking into
		// account
		// interception and snowmelt

		Sres sres = snow(climate.prec - patch.intercep, climate.temp, soil.snowpack, rain, melt);
		soil.snowpack = sres.snowpack;
		rain = sres.rain;
		melt = sres.melt;

		// Sum vegetation phenology-weighted FPC

		fpc_phen_total = 0.0;

		// Loop through individuals
		for (Individual indiv : vegetation) {
			fpc_phen_total += indiv.fpc * indiv.phen;
		}

		// Hydrology
		// Fraction of grid cell subject to evaporation from soil surface is
		// complement of summed vegetation projective cover (FPC)

		// guess2008 - DLE - added soil.awcont & soil.snowpack to the function
		// call
		Hres hres = hydrology_lpjf(patch, climate.eet, rain, melt, soil.soiltype.perc_base, soil.soiltype.perc_exp,
				soil.soiltype.awc, Math.max(1.0 - fpc_phen_total, 0.0), soil.awcont, soil.wcont, soil.wcont_evap,
				soil.runoff, soil.snowpack);

		soil.wcont_evap = hres.wcont_evap;
		soil.runoff = hres.runoff;

	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// REFERENCES
	//
	// Haxeltine A & Prentice IC 1996 BIOME3: an equilibrium terrestrial
	// biosphere
	// model based on ecophysiological constraints, resource availability, and
	// competition among plant functional types. Global Biogeochemical Cycles
	// 10:
	// 693-709

	// guess2008 - new references:

	// BONDEAU, A., SMITH, P. C., ZAEHLE, S., SCHAPHOFF, S., LUCHT, W., CRAMER,
	// W., GERTEN, D.,
	// LOTZE-CAMPEN, H., M�LLER, C., REICHSTEIN, M. and SMITH, B. (2007),
	// Modelling the role of agriculture for the 20th century global terrestrial
	// carbon balance.
	// Global Change Biology, 13: 679�706. doi: 10.1111/j.1365-2486.2006.01305.x

	// Rost, S., D. Gerten, A. Bondeau, W. Luncht, J. Rohwer, and S. Schaphoff
	// (2008),
	// Agricultural green and blue water consumption and its influence on the
	// global
	// water system, Water Resour. Res., 44, W09405, doi:10.1029/2007WR006331

}
