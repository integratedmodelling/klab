package org.integratedmodelling.ecology.biomass.lpjguess.common;

public class SOMPool {

	public SOMPool() {
		
		// Initialise pool
		
		cmass = 0.0;
		nmass = 0.0;
		ligcfrac = 0.0;
		delta_cmass = 0.0;
		delta_nmass = 0.0;
		fracremain = 0.0;
		litterme = 0.0;
		fireresist = 0.0;

		for (int m = 0; m < 12; m++) {
			mfracremain_mean[m] = 0.0;
		}
	}

	/// C mass in pool kgC/m2
	public double cmass;
	/// Nitrogen mass in pool kgN/m2
	public double nmass;
	/// (potential) decrease in C following decomposition today (kgC/m2)
	public double cdec; 
	/// (potential) decrease in nitrogen following decomposition today (kgN/m2)
	public double ndec; 
	/// daily change in carbon and nitrogen
	public double delta_cmass,delta_nmass;
	/// lignin fractions
	public double ligcfrac;
	/// fraction of pool remaining after decomposition
	public double fracremain;
	/// nitrogen to carbon ratio
	public double ntoc;

	// Fire
	/// soil litter moisture flammability threshold (fraction of AWC)
	public double litterme;
	/// soil litter fire resistance (0-1)
	public double fireresist;

	// Fast SOM spinup variables

	/// monthly mean fraction of carbon pool remaining after decomposition
	public double mfracremain_mean[] = new double[12];

}
