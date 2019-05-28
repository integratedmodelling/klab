package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.List;

public class StandType {

	/// id code (should be zero based and sequential, 0...nst-1)
	public int id;
	public String name;

	/// specifies type of landcover
	// FV: initialize to NATURAL which seems to be the default (commented out in
	/// all PFTs that
	// would otherwise have no landcover at all). Seems to make sense as we're
	/// talking about
	// vegetation.
	public LandcoverType landcover = LandcoverType.NATURAL;

	// hydrology (RAINFED,IRRIGATED)
	// hydrologytype hydrology;
	///
	// NOTE: the number of crops in rotation must correspond to the managements;
	// management[i]
	// is for the i-th crop (suuuucks).
	// FIXME: this is never assigned, used all the time.
	public CropRotation rotation;
	// List of management types in a rotation cycle
	// FIXME: code expects at least one valid Management object in here.
	// FV made it a list from an array sized NROTATIONPERIODS_MAX and filled
	// with nulls.
	public List<Management> management = new ArrayList<>();

	/// intercrop (NOINTERCROP,NATURALGRASS)
	public IntercropType intercrop;
	/// whether natural pft:s are allowed to grow in stand type
	public NaturalVeg naturalveg; // or NONE, GRASSONLY, ALL
	/// whether natural grass pft:s are allowed to grow in stand type
	public boolean naturalgrass;
	// whether only pft:s defined in management are allowed (plus intercrop or
	// naturalveg/grass)
	public boolean restrictpfts;

	/// fraction of this stand type relative to the gridcell
	public double frac;
	/// old fraction of this stand type relative to the gridcell before update
	public double frac_old;

	public double protected_frac;

	/// net fraction change
	public double frac_change;

	public double gross_frac_increase;
	public double gross_frac_decrease;

	// current number of stands of this stand type
	public int nstands;

	private boolean isValid;

	public StandType(String n) {
		name = n;
		frac = 1.0;
		frac_old = 0.0;
		protected_frac = 0.0;
		frac_change = 0.0;
		gross_frac_increase = 0.0;
		gross_frac_decrease = 0.0;
		nstands = 0;
		intercrop = IntercropType.NOINTERCROP;
		naturalveg = NaturalVeg.NONE;
		naturalgrass = false;
		restrictpfts = false;
	}

	public boolean isValid() {
		return isValid;
	}

	/// Returns position of crop in rotation list if present. Returns -1 if not.
	int pftinrotation(String name) {

		int cropno = -1;
		for (int i = 0; i < rotation.ncrops; i++) {
			if (name == management.get(i).pftname)
				cropno = i;
		}

		return cropno;
	}

	public void validate() {

		isValid = true;
		// TODO Auto-generated method stub
		for (Management m : management) {
			m.validate();
			if (!m.isValid()) {
				isValid = false;
			}
		}
	}

}
