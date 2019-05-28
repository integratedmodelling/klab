package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.procsim.api.IConfiguration;

///////////////////////////////////////////////////////////////////////////////////////
//PATCHPFT
//State variables common to all individuals of a particular PFT in a particular patch
//Used in individual and cohort modes only.

public class PatchPFT {

	// id code (equal to value of member variable id in corresponding Pft
	// object)
	public int id;

	// reference to corresponding Pft object in PFT list
	public PFT pFT;

	// potential annual net assimilation (leaf-level net photosynthesis) at
	// forest
	// floor (kgC/m2/year)
	public double anetps_ff;

	// water stress parameter (0-1 range; 1=minimum stress)
	public double wscal;

	// running sum (converted to annual mean) for wscal
	public double wscal_mean;

	// potential annual net assimilation at forest floor averaged over
	// establishment interval (kgC/m2/year)
	public double anetps_ff_est;

	// first-year value of anetps_ff_est
	public double anetps_ff_est_initial;

	// annual mean wscal averaged over establishment interval
	public double wscal_mean_est;

	// vegetation phenological state (fraction of potential leaf cover)
	// updated daily
	public double phen;

	// annual sum of daily fractional leaf cover (equivalent number of days with
	// full leaf cover) (reset on expected coldest day of year)
	public double aphen;

	// whether PFT can establish in this patch under current conditions
	public boolean establish;

	// running total for number of saplings of this PFT to establish (cohort
	// mode)
	public double nsapling;

	// leaf-derived litter for PFT on modelled area basis (kgC/m2)
	public double litter_leaf;

	// fine root-derived litter for PFT on modelled area basis (kgC/m2)
	public double litter_root;

	// heartwood and sapwood-derived litter for PFT on modelled area basis
	// (kgC/m2)
	public double litter_wood;
	
	public double litter_sap;
	/// year's sapwood-derived litter for PFT on modelled area basis (kgC/m2)
	public double litter_sap_year;
	/// remaining heartwood-derived litter for PFT on modelled area basis (kgC/m2)
	public double litter_heart;
	/// year's heartwood-derived litter for PFT on modelled area basis (kgC/m2)
	public double litter_heart_year;
	/// litter derived from allocation to reproduction for PFT on modelled area basis (kgC/m2)

	// litter derived from allocation to reproduction for PFT on modelled area
	// basis (kgC/m2)
	public double litter_repr;

	// Variables used by "fast" canopy exchange code (Ben Smith 2002-07)

	// non-FPC-weighted canopy conductance value for PFT under water-stress
	// conditions (mm/s)
	public double gcbase;
	
	/// daily value of the above variable (mm/s)
	public double gcbase_day;

	/// evapotranspirational "supply" function for this PFT today (mm/day)
	double wsupply;
	double wsupply_leafon;
	/// fractional uptake of water from each soil layer today
	//double fwuptake[] = new double[NSOILLAYER];

	// cumulative mean temperature for water stress days this month (deg C)
	public double temp_wstress;

	// cumulative mean PAR for water stress days this month (J/m2/day)
	public double par_wstress;

	// cumulative mean day length for water stress days this month (h)
	public double daylength_wstress;

	// cumulative mean atmospheric CO2 concentration for water stress days this
	// month (ppmv)
	public double co2_wstress;

	// cumulative number of water stress days this month
	public int nday_wstress;

	// mean FPAR at top of grass canopy for days with water stress for this PFT
	// in this patch
	public double fpar_grass_wstress;

	// evapotranspirational "supply" function for this PFT today (mm/day)
	public double supply;

	public double supply_leafon;
	// fractional uptake of water from each soil layer today
	public double[] fuptake = new double[IConfiguration.NSOILLAYER];

	// whether water-stress conditions for this PFT today
	public boolean ifwstress;
	
	/// carbon depository for long-lived products like wood
	public double harvested_products_slow;	
	/// nitrogen depository for long-lived products like wood
	public double harvested_products_slow_nmass; 
	/// first and last day of crop sowing window, calculated in crop_sowing_patch() or Crop_sowing_date_new()
	public int swindow[] = new int[2];
	/// daily value of water deficit, calculated in irrigated_water_uptake()
	public double water_deficit_d;
	/// yearly sum of water deficit
	public double water_deficit_y;
	
	/// leaf-derived nitrogen litter for PFT on modelled area basis (kgN/m2)
	public double nmass_litter_leaf;
	/// root-derived nitrogen litter for PFT on modelled area basis (kgN/m2)
	public double nmass_litter_root;
	/// remaining sapwood-derived nitrogen litter for PFT on modelled area basis (kgN/m2)
	public double nmass_litter_sap;
	/// year's sapwood-derived nitrogen litter for PFT on modelled area basis (kgN/m2)
	public double nmass_litter_sap_year;
	/// remaining heartwood-derived nitrogen litter for PFT on modelled area basis (kgN/m2)
	public double nmass_litter_heart;
	/// year's heartwood-derived nitrogen litter for PFT on modelled area basis (kgN/m2)
	public double nmass_litter_heart_year;
	
	public CropPhen cropphen = new CropPhen();
	

	// lookup table for values of lambda (parameter in photosynthesis
	// calculations)
	// today (see canexch.cpp)
//	public LookupLambda lookup_lambda = new LookupLambda();

	// MEMBER FUNCTIONS:

	public PatchPFT(int i, PFT p) {
		this.id = i;
		this.pFT = p;

		// Constructor: initialises id, pft and data members

		litter_leaf = 0.0;
		litter_root = 0.0;
		litter_wood = 0.0;
		litter_repr = 0.0;
		nday_wstress = 0;
		wscal = 1.0;
		wscal_mean = 0.0;
		anetps_ff = 0.0;
		aphen = 0.0;
	}
	
	public CropPhen get_cropphen() {
		return cropphen;
	}
	
	public void set_cropphen(CropPhen cp) {
		cropphen = cp;
	}
}