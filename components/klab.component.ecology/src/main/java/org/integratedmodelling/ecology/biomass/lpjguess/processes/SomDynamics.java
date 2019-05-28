package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.Soil;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.SimProcess;
import org.integratedmodelling.procsim.api.IConfiguration;

///////////////////////////////////////////////////////////////////////////////////////
//REFERENCES
//
//Foley J A 1995 An equilibrium model of the terrestrial carbon budget
//Tellus (1995), 47B, 310-319
//Meentemeyer, V. (1978) Macroclimate and lignin control of litter decomposition
//rates. Ecology 59: 465-472.

public class SomDynamics extends SimProcess {

	// Turnover times (in years, approximate) for litter and SOM fractions at 10
	// deg C with
	// ample moisture (Meentemeyer 1978; Foley 1995)

	public SomDynamics(IConfiguration configuration) {
		super(configuration);
	}

	private static final double TAU_LITTER = 2.85; // Thonicke, Sitch, pers
													// comm, 26/11/01

	/*
	 * T. Hickler: THESE CAN BE ADAPTED to redefine spin-up time
	 */
	private static final double TAU_SOILFAST = 33.0;
	private static final double TAU_SOILSLOW = 1000.0;

	private static final double FASTFRAC = 0.985;
	// fraction of litter decomposition entering fast SOM pool
	private static final double ATMFRAC = 0.7;
	// fraction of litter decomposition entering atmosphere

	// Exponential decay constants for litter and SOM fractions
	// Values set from turnover times (constants above) on first call to
	// decayrates

	private static double k_litter10;
	private static double k_soilfast10;
	private static double k_soilslow10;

	private static boolean firsttime = true;

	// indicates whether function decayrates has been called before

	// /////////////////////////////////////////////////////////////////////////////////////
	// SETCONSTANTS
	// Internal function (do not call directly from framework)

	private void setconstants() {

		// DESCRIPTION
		// Calculate exponential decay constants (annual basis) for litter and
		// SOM fractions first time function decayrates is called

		k_litter10 = 1.0 / TAU_LITTER;
		k_soilfast10 = 1.0 / TAU_SOILFAST;
		k_soilslow10 = 1.0 / TAU_SOILSLOW;
		firsttime = false;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// DECAYRATES
	// Internal function (do not call directly from framework)

	class DRes {
		double k_soilfast;
		double k_soilslow;
		double fr_litter;
		double fr_soilfast;
		double fr_soilslow;
	}

	private DRes decayrates(double wcont, double gtemp_soil, double k_soilfast, double k_soilslow, double fr_litter,
			double fr_soilfast, double fr_soilslow) {

		// DESCRIPTION
		// Calculation of fractional decay amounts for litter and fast and slow
		// SOM
		// fractions given current soil moisture and temperature

		// INPUT PARAMETERS
		// wcont = water content of upper soil layer (fraction of AWC)
		// gtemp_soil = respiration temperature response incorporating damping
		// of Q10
		// response due to temperature acclimation (Eqn 11, Lloyd & Taylor
		// 1994)

		// OUTPUT PARAMETERS
		// k_soilfast = adjusted daily decay constant for fast SOM fraction
		// k_soilslow = adjusted daily decay constant for slow SOM fraction
		// fr_litter = litter fraction remaining following today's decomposition
		// fr_soilfast = fast SOM fraction remaining following today's
		// decomposition
		// fr_soilslow = slow SOM fraction remaining following today's
		// decomposition

		double moist_response; // moisture modifier of decomposition rate

		// On first call only: set exponential decay constants

		if (firsttime) {
			setconstants();
		}

		// Calculate response of soil respiration rate to moisture content of
		// upper soil layer
		// Foley 1995 Eqn 19

		moist_response = 0.25 + 0.75 * wcont;

		// Calculate litter and SOM fractions remaining following today's
		// decomposition
		// (Sitch et al 2000 Eqn 71) adjusting exponential decay constants by
		// moisture and
		// temperature responses and converting from annual to daily basis
		// NB: Temperature response (gtemp; Lloyd & Taylor 1994) set by
		// framework

		k_soilfast = k_soilfast10 * gtemp_soil * moist_response / 365.0;
		k_soilslow = k_soilslow10 * gtemp_soil * moist_response / 365.0;

		fr_litter = Math.exp(-k_litter10 * gtemp_soil * moist_response / 365.0);
		fr_soilfast = Math.exp(-k_soilfast);
		fr_soilslow = Math.exp(-k_soilslow);

		DRes ret = new DRes();

		ret.fr_litter = fr_litter;
		ret.fr_soilfast = fr_soilfast;
		ret.fr_soilslow = fr_soilslow;
		ret.k_soilfast = k_soilfast;
		ret.k_soilslow = k_soilslow;

		return ret;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// DECAYRATES
	// Should be called by framework on last day of simulation year, following
	// call to
	// som_dynamics, once annual litter production and vegetation PFT
	// composition are close
	// to their long term equilibrium (typically 500-1000 simulation years).
	// NB: should be called ONCE ONLY during simulation for a particular grid
	// cell

	private void equilsom(Soil soil) {

		// DESCRIPTION
		// Analytically solves differential flux equations for fast and slow SOM
		// pools
		// assuming annual litter inputs close to long term equilibrium

		// INPUT PARAMETER (class defined in framework header file)
		// soil = current soil status

		double nyear;
		// number of years over which decay constants and litter inputs averaged

		nyear = soil.soiltype.solvesom_end - soil.soiltype.solvesom_begin + 1;

		soil.decomp_litter_mean /= nyear;
		soil.k_soilfast_mean /= nyear;
		soil.k_soilslow_mean /= nyear;

		soil.cpool_fast = (1.0 - ATMFRAC) * FASTFRAC * soil.decomp_litter_mean / soil.k_soilfast_mean;
		soil.cpool_slow = (1.0 - ATMFRAC) * (1.0 - FASTFRAC) * soil.decomp_litter_mean / soil.k_soilslow_mean;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// SOM DYNAMICS
	// To be called each simulation day for each modelled area or patch,
	// following update
	// of soil temperature and soil water.

	@Override
	public void process(Patch patch) {

		// DESCRIPTION
		// Calculation of soil decomposition and transfer of C between litter
		// and soil
		// organic matter pools.
		//
		// NB: The global variable 'ifdailydecomp' determines whether soil
		// decomposition
		// calculations are performed every day, or on the last day of each
		// month, based on
		// average conditions for the month (the latter mode is much faster).
		// Daily flux
		// values are never valid in monthly mode. If you require daily output,
		// use daily
		// mode

		double k_soilfast = 0; // adjusted daily decay constant for fast SOM
								// fraction
		double k_soilslow = 0; // adjusted daily decay constant for slow SOM
								// fraction
		double fr_litter = 0;
		// litter fraction remaining following one day's/one month's
		// decomposition
		double fr_soilfast = 0;
		// fast SOM fraction remaining following one day's/one month's
		// decomposition
		double fr_soilslow = 0;
		// slow SOM fraction remaining following one day's/one month's
		// decomposition
		double decomp_litter = 0; // litter decomposition today/this month
									// (kgC/m2)
		double cflux = 0; // accumulated C flux to atmosphere today/this month
							// (kgC/m2)
		int p;

		// Obtain reference to Soil object
		Soil soil = patch.soil;

		if (getConfiguration().isDecompositionDaily()) {

			// "DAILY" MODE

			// Calculate respiration temperature response if not yet done for
			// this day

			if (soil.last_gtemp != getSchedule().julianDay()) {
				soil.gtemp = Utils.respiration_temperature_response(soil.temp, soil.gtemp);
				soil.last_gtemp = getSchedule().julianDay();
			}

			// Calculate decay constants and rates given today's soil moisture
			// and
			// temperature

			DRes res = decayrates(soil.wcont[0], soil.gtemp, k_soilfast, k_soilslow, fr_litter, fr_soilfast,
					fr_soilslow);

			k_soilfast = res.k_soilfast;
			k_soilslow = res.k_soilslow;
			fr_litter = res.fr_litter;
			fr_soilfast = res.fr_soilfast;
			fr_soilslow = res.fr_soilslow;

			// From year soil.solvesom_begin, update running means for later
			// solution
			// (at year soil.solvesom_end) of equilibrium SOM pool sizes

			if (getSchedule().year() >= soil.soiltype.solvesom_begin) {
				soil.k_soilfast_mean += k_soilfast;
				soil.k_soilslow_mean += k_soilslow;
			}
		} else if (getSchedule().isLastDayOfMonth()) {

			// "MONTHLY" MODE (last day of month only)

			// Calculate respiration temperature response if not yet done for
			// this month

			if (soil.last_mgtemp != getSchedule().month()) {
				soil.mgtemp = Utils.respiration_temperature_response(soil.mtemp, soil.mgtemp);
				soil.last_mgtemp = getSchedule().month();
			}

			// Calculate decay constants and rates given monthly means

			DRes res = decayrates(soil.mwcontupper, soil.mgtemp, k_soilfast, k_soilslow, fr_litter, fr_soilfast,
					fr_soilslow);

			k_soilfast = res.k_soilfast;
			k_soilslow = res.k_soilslow;
			fr_litter = res.fr_litter;
			fr_soilfast = res.fr_soilfast;
			fr_soilslow = res.fr_soilslow;

			// From year soil.solvesom_begin, update running means for later
			// solution
			// (at year soil.solvesom_end) of equilibrium SOM pool sizes

			if (getSchedule().year() >= soil.soiltype.solvesom_begin) {
				soil.k_soilfast_mean += k_soilfast * getSchedule().ndaymonth();
				soil.k_soilslow_mean += k_soilslow * getSchedule().ndaymonth();
			}

			// Convert fractional scalars from daily to monthly basis

			fr_litter = Math.pow(fr_litter, getSchedule().ndaymonth());
			fr_soilfast = Math.pow(fr_soilfast, getSchedule().ndaymonth());
			fr_soilslow = Math.pow(fr_soilslow, getSchedule().ndaymonth());
		}

		// DAILY AND MONTHLY MODES
		// Only on last day of month if monthly mode

		// Reduce litter and SOM pools, sum C flux to atmosphere from
		// decomposition
		// and transfer correct proportions of litter decomposition to fast and
		// slow
		// SOM pools

		if (getConfiguration().isDecompositionDaily() || getSchedule().isLastDayOfMonth()) {

			// Reduce individual litter pools and calculate total litter
			// decomposition
			// for today/this month

			decomp_litter = 0.0;

			// Loop through PFTs

			for (p = 0; p < getConfiguration().getPFTs().size(); p++) {

				// For this PFT ...

				decomp_litter += (patch.pft.get(p).litter_leaf + patch.pft.get(p).litter_root
						+ patch.pft.get(p).litter_wood + patch.pft.get(p).litter_repr)
						* (1.0 - fr_litter);

				patch.pft.get(p).litter_leaf *= fr_litter;
				patch.pft.get(p).litter_root *= fr_litter;
				patch.pft.get(p).litter_wood *= fr_litter;
				patch.pft.get(p).litter_repr *= fr_litter;
			}

			if (getSchedule().year() >= soil.soiltype.solvesom_begin) {
				soil.decomp_litter_mean += decomp_litter;
			}

			// Partition litter decomposition among fast and slow SOM pools
			// and flux to atmosphere

			// flux to atmosphere
			cflux = decomp_litter * ATMFRAC;

			// remaining decomposition - goes to ...
			decomp_litter -= cflux;

			// ... fast SOM pool ...
			soil.cpool_fast += decomp_litter * FASTFRAC;

			// ... and slow SOM pool
			soil.cpool_slow += decomp_litter * (1.0 - FASTFRAC);

			// Increment C flux to atmosphere by SOM decomposition
			cflux += soil.cpool_fast * (1.0 - fr_soilfast) + soil.cpool_slow * (1.0 - fr_soilslow);

			// Monthly C flux

			if (getConfiguration().isDecompositionDaily()) {
				if (getSchedule().dayofmonth() == 0) {
					patch.fluxes.mcflux_soil[getSchedule().month()] = cflux;
				} else {
					patch.fluxes.mcflux_soil[getSchedule().month()] += cflux;
				}
			} else {
				patch.fluxes.mcflux_soil[getSchedule().month()] = cflux;
			}

			// Reduce SOM pools
			soil.cpool_fast *= fr_soilfast;
			soil.cpool_slow *= fr_soilslow;

			// Updated daily and annual fluxes
			patch.fluxes.dcflux_soil = cflux;
			patch.fluxes.acflux_soil += cflux;

			// Solve SOM pool sizes at end of year given by soil.solvesom_end
			if (getSchedule().year() == soil.soiltype.solvesom_end && getSchedule().isLastDayOfYear()) {
				equilsom(soil);
			}
		}
	}

}
