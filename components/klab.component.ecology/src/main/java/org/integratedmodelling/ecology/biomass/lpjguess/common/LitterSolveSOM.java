package org.integratedmodelling.ecology.biomass.lpjguess.common;

import org.integratedmodelling.ecology.biomass.lpjguess.SOMPoolType;

public class LitterSolveSOM {

	public LitterSolveSOM() {
		clear();
	}
	
	/// Clears all members
	public void clear() {
		for (int p = 0; p < SOMPoolType.NSOMPOOL.ordinal(); p++) {
			clitter[p] = 0.0;
			nlitter[p] = 0.0;
		}
	}

	/// Add litter
    public void add_litter(double cvalue, double nvalue, int pool) {
		clitter[pool] += cvalue;
		nlitter[pool] += nvalue;
    }

	double get_clitter(int pool) {
		return clitter[pool];
	}
	double get_nlitter(int pool) {
		return nlitter[pool];
	}

	/// Carbon litter
	double clitter[] = new double[SOMPoolType.NSOMPOOL.ordinal()];
	
	/// Nitrogen litter
	double nlitter[] = new double[SOMPoolType.NSOMPOOL.ordinal()];

}
