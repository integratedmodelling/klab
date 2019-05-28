package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IDailyProcess;

public class LeafPhenology extends PlantGrowth implements IDailyProcess {

	public LeafPhenology(IConfiguration configuration) {
		super(configuration);
	}

	@Override
	public void process(Patch p) {
		leaf_phenology(p);
	}
}
