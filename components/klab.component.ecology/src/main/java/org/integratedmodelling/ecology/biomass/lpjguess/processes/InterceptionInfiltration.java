package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.Climate;
import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.Soil;
import org.integratedmodelling.ecology.biomass.lpjguess.Vegetation;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.SimProcess;
import org.integratedmodelling.procsim.api.IConfiguration;

public class InterceptionInfiltration extends SimProcess {

	public InterceptionInfiltration(IConfiguration configuration) {
		super(configuration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Patch patch) {
		interception(patch);
		initial_infiltration(patch);
	}

	private void snow(double prec, double temp, double snowpack, double rain_melt) {

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
		// rain_melt = rainfall and snow melt today (mm)

		double TSNOW = 0.0;
		// maximum temperature for precipitation as snow (deg C)
		// previously 2 deg C; new value suggested by Dieter Gerten 2002-12
		double SNOWPACK_MAX = 10000.0;
		// maximum size of snowpack (mm) (S. Sitch, pers. comm. 2001-11-28)

		double melt;
		if (temp < TSNOW) { // snowing today
			melt = -Math.min(prec, SNOWPACK_MAX - snowpack);
		} else { // raining today
			// New snow melt formulation
			// Dieter Gerten 021121
			// Ref: Choudhury et al 1998
			melt = Math.min((1.5 + 0.007 * prec) * (temp - TSNOW), snowpack);
		}
		snowpack -= melt;
		rain_melt = prec + melt;
	}

	private void snow_ninput(double prec, double snowpack_after, double rain_melt, double dndep, double dnfert,
			double snowpack_nmass, double ninput) {

		// calculates this day melt and original snowpack size
		double melt = Math.max(0.0, rain_melt - prec);
		double snowpack = melt + snowpack_after;

		// snow exist
		if (!Utils.negligible(snowpack)) {

			// if some snow is melted, fraction of nitrogen in snowpack
			// will go to soil available nitrogen pool
			if (melt > 0.0) {
				double frac_melt = melt / snowpack;
				double melt_nmass = frac_melt * snowpack_nmass;
				ninput = melt_nmass + dndep + dnfert;
				snowpack_nmass -= melt_nmass;
			}
			// if no snow is melted, then add daily nitrogen deposition
			// and fertilization to snowpack nitrogen pool
			else {
				snowpack_nmass += (dndep + dnfert);
				ninput = 0.0;
			}
		} else {
			ninput = dndep + dnfert;
		}
	}

	private void initial_infiltration(Patch patch) {
		Soil soil = patch.soil;
		Climate climate = patch.stand.getClimate();

		snow(climate.prec - patch.intercep, climate.temp, soil.snowpack, soil.rain_melt);
		snow_ninput(climate.prec - patch.intercep, soil.snowpack, soil.rain_melt, climate.dndep, patch.dnfert,
				soil.snowpack_nmass, soil.ninput);

		soil.percolate = soil.rain_melt >= 0.1;
		soil.max_rain_melt = soil.rain_melt;

		if (soil.percolate) {
			soil.wcont[0] += soil.rain_melt / soil.soiltype.awc[0];

			if (soil.wcont[0] > 1) {
				soil.rain_melt = (soil.wcont[0] - 1) * soil.soiltype.awc[0];
				soil.wcont[0] = 1;
			} else {
				soil.rain_melt = 0;
			}

			soil.wcont_evap = soil.wcont[0];
		}
	}

	private void interception(Patch patch) {
		// Calculates daily loss of water and energy through evaporation of
		// rainfall
		// intercepted by the vegetation canopy

		double scap; // canopy storage capacity (mm)
		double fwet; // fraction of day that canopy is wet (Kergoat 1996)
		double pet; // potential evapotranspiration (mm)

		Climate climate = patch.stand.getClimate();

		pet = climate.eet * _configuration.PRIESTLEY_TAYLOR;

		// Retrieve Vegetation object
		Vegetation vegetation = patch.vegetation;

		patch.intercep = 0.0;

		// Loop through individuals ...

		for (Individual indiv : vegetation) {
			// For this individual ...
			if (!Utils.negligible(pet)) {

				// Storage capacity for precipitation by canopy (point scale)
				scap = climate.prec * Math.min(indiv.lai_indiv_today() * indiv.pft.intc, 0.999);

				// Fraction of day that canopy remains wet
				fwet = Math.min(scap / pet, patch.fpc_rescale);

				// Calculate interception by this individual, and increment
				// patch total

				indiv.intercep = fwet * pet * indiv.fpc;
				patch.intercep += indiv.intercep;
			} else {

				indiv.intercep = 0.0;
				patch.intercep = 0.0;
			}
		}

		// Calculate net EET for vegetated parts of patch (deducting loss to
		// interception)

		patch.eet_net_veg = Math.max(climate.eet - patch.intercep, 0.0);

		// Interception accounting for patch
		patch.aintercep += patch.intercep;
		patch.mintercep[_configuration.getSchedule().month()] += patch.intercep;
	}

}
