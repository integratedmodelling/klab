package org.integratedmodelling.ecology.biomass.lpjguess;

public class CropRotation {

	public CropRotation() {
		ncrops = 1;
		nyears = 1.0;
		firstrotyear = 0;
		multicrop = false;
	}

	
	/// Number of crops in rotation
	public int ncrops;
	/// Rotation period in years
	public double nyears;
	public int firstrotyear;
	public Boolean multicrop;
}
