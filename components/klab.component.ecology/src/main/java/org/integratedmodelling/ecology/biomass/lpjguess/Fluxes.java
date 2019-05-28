package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IModelObject;

public class Fluxes implements IModelObject {
	// (all fluxes on stand area basis, kgC/m2)
	
	public void reset() {
		for (int i = 0; i < patch.stand._configuration.getPFTs().size(); i++)
		{
			annual_fluxes_per_pft.add(new double[12]);
		}
	}

	// patch to which this Fluxes object belongs
	public Patch patch;

	// annual flux to vegetation (=total vegetation annual NPP)
	public double acflux_veg;

	// annual carbon flux to atmosphere from burnt vegetation and litter
	public double acflux_fire;

	// annual carbon flux to atmosphere from soil respiration
	public double acflux_soil;

	// annual flux from atmosphere to vegetation associated with establishment
	public double acflux_est;

	// daily carbon flux to atmosphere from soil respiration
	// NB: not implemented by som_dynamics_monthly
	public double dcflux_soil;

	// monthly C flux to atmosphere from soil respiration
	public double[] mcflux_soil = new double[12];

	// monthly C flux to vegetation from atmosphere
	public double[] mcflux_veg = new double[12];

	// daily net carbon flux to vegetation (respiration-assimilation)
	// NB: not implemented by canopy_exchange_monthly
	public double dcflux_veg;

	// guess2008 - new carbon budget arrays

	// monthly GPP
	public double[] mcflux_gpp = new double[12];

	// monthly autotrophic respiration
	public double[] mcflux_ra = new double[12];
	
	/// Stores one flux value per PFT and flux type
	public ArrayList<double[]> annual_fluxes_per_pft = new ArrayList<double[]>();

	/// Stores one flux value per month and flux type
	/** For the fluxes only stored as totals for the whole patch */
	public double monthly_fluxes_patch[][] = new double[12][PerPFTFluxType.values().length];

	/// Stores one flux value per month and flux type
	/** For the fluxes stored per pft for annual values */
	public double monthly_fluxes_pft[][] = new double[12][PerPFTFluxType.values().length];

	/// Stores one flux value per day and flux type
	public double daily_fluxes_patch[][] = new double[365][PerPFTFluxType.values().length];

	/// Stores one flux value per day and flux type
	public double daily_fluxes_pft[][] = new double[365][PerPFTFluxType.values().length];

	public Fluxes(Patch p) {
		this.patch = p;
	}

	// If called following update of annual accumulated fluxes on last day of
	// simulation year, returns annual net ecosystem exchange (NEE)
	public final double anee() {
		return acflux_veg + acflux_fire + acflux_soil + acflux_est;
	}
	
	public void report_flux(PerPFTFluxType flux_type, int pft_id, double value) {
		int month = patch.stand._configuration.getSchedule().month();
		int day = patch.stand._configuration.getSchedule().julianDay();
		
		annual_fluxes_per_pft.get(pft_id)[flux_type.ordinal()] += value;
		monthly_fluxes_pft[month][flux_type.ordinal()] += value;
		daily_fluxes_pft[day][flux_type.ordinal()] += value;	//Var = value ???
	}

	public void report_flux(PerPatchFluxType flux_type, double value) {
		int month = patch.stand._configuration.getSchedule().month();
		int day = patch.stand._configuration.getSchedule().julianDay();
		
		monthly_fluxes_patch[month][flux_type.ordinal()] += value;
		daily_fluxes_patch[day][flux_type.ordinal()] += value;
	}

	@Override
	public IConfiguration getConfiguration() {
		return patch.getConfiguration();
	}
}