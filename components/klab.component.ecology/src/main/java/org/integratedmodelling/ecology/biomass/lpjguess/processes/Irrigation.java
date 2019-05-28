package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.PatchPFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Soil;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.SimProcess;
import org.integratedmodelling.procsim.api.IConfiguration;

public class Irrigation extends SimProcess {

    public Irrigation(IConfiguration configuration) {
        super(configuration);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void process(Patch patch) {
        calculate_irrigation(patch);
    }

    /// Calculate required irrigation according to water deficiency.
    /** Function to be called after canopy_exchange and before soilwater.
     */
    void calculate_irrigation(Patch patch) {

        Soil soil = patch.soil;

        patch.irrigation_d = 0.0;
        if (patch.stand._configuration.getSchedule().isFirstDayOfYear()) {
            patch.irrigation_y = 0.0;
        }
        if (patch.stand.isirrigated) {
            for (PatchPFT ppft : patch.pft)
                for (int i = 0; i < patch.pft.size(); i++) {
                    if (patch.stand.pft.get(ppft.pFT.id).irrigated && ppft.cropphen.growingseason) {
                        patch.irrigation_d += ppft.water_deficit_d;
                    }
                }
            patch.irrigation_y += patch.irrigation_d;
            soil.rain_melt += patch.irrigation_d;
            soil.max_rain_melt += patch.irrigation_d;
        }
    }

}
