//package org.integratedmodelling.ecology.biomass.lpjguess.drivers;
//
//import org.integratedmodelling.ecology.biomass.lpjguess.Gridcell;
//import org.integratedmodelling.ecology.biomass.lpjguess.LandcoverType;
//import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
//import org.integratedmodelling.ecology.biomass.lpjguess.Stand;
//import org.integratedmodelling.ecology.biomass.lpjguess.common.Output;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.CanopyExchange;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.Growth;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.GrowthDaily;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.InterceptionInfiltration;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.Irrigation;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.LeafPhenology;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.PlantGrowth;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.SoilWater;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.SomDynamics;
//import org.integratedmodelling.ecology.biomass.lpjguess.processes.VegDynamics;
//import org.integratedmodelling.procsim.api.IConfiguration;
//
///**
// * Main simulation driver. Create a configuration, pass it to the constructor
// * and call run().
// * TODO create and pass an output configuration too.
// * // // LPJF refers to the original FORTRAN implementation of LPJ as described
// * by Sitch // et al 2000 // Fulton, MR 1991 Adult recruitment rate as a
// * function of juvenile growth in size- // structured plant populations. Oikos
// * 61: 102-105. // Haxeltine A & Prentice IC 1996 BIOME3: an equilibrium
// * terrestrial biosphere // model based on ecophysiological constraints,
// * resource availability, and // competition among plant functional types.
// * Global Biogeochemical Cycles 10: // 693-709 // Lloyd, J & Taylor JA 1994 On
// * the temperature dependence of soil respiration // Functional Ecology 8:
// * 315-323 // Monsi M & Saeki T 1953 Ueber den Lichtfaktor in den
// * Pflanzengesellschaften und // seine Bedeutung fuer die Stoffproduktion.
// * Japanese Journal of Botany 14: 22-52 // Prentice, IC, Sykes, MT & Cramer W
// * (1993) A simulation model for the transient // effects of climate change on
// * forest landscapes. Ecological Modelling 65: 51-70. // Reich, PB, Walters MB &
// * Ellsworth DS 1997 From tropics to tundra: global // convergence in plant
// * functioning. Proceedings of the National Academy of Sciences // USA 94:
// * 13730-13734. // Sitch, S, Prentice IC, Smith, B & Other LPJ Consortium
// * Members (2000) LPJ - a // coupled model of vegetation dynamics and the
// * terrestrial carbon cycle. In: // Sitch, S. The Role of Vegetation Dynamics in
// * the Control of Atmospheric CO2 // Content, PhD Thesis, Lund University, Lund,
// * Sweden. // Sykes, MT, Prentice IC & Cramer W 1996 A bioclimatic model for the
// * potential // distributions of north European tree species under present and
// * future climates. // Journal of Biogeography 23: 209-233.
// *
// * @author Robin Wilson
// * @author Ferdinando Villa
// */
//public class LPJ {
//
//    private Gridcell         gridcell;
//    private LPJWeatherDriver weatherDriver;
//    private LPJOutputDriver  outputDriver;
//
//    public LPJ(Gridcell g, LPJWeatherDriver weather, LPJOutputDriver output) {
//        this.gridcell = g;
//        this.weatherDriver = weather;
//        this.outputDriver = output;
//    }
//
//    // Priestley-Taylor coefficient (conversion factor from equilibrium
//    // evapotranspiration to PET)
//
//    public static void fail(String string, Object o) {
//        // TODO Auto-generated method stub
//        System.out.println("XIOPORTO " + string);
//    }
//
//    // public void setRandomClimate() {
//    // Random r = new Random();
//    // double temp = r.nextDouble() * 30;
//    // double prec = r.nextDouble() * 100;
//    // double perc_sunshine = r.nextDouble() * 100;
//    //
//    // setClimate(temp, prec, perc_sunshine);
//    // }
//
//    public Output getOutputs() {
//        // At the moment only works if we have ONE and ONLY ONE stand in each
//        // instance of LPJ
//        // TODO: Fix this, or make it clear that this is how we have to use it!
//        Output o = gridcell.stands.get(0).output;
//        return o;
//    }
//
//    public void run() {
//
//        /*
//         * set up climate
//         */
//        weatherDriver.setClimate(gridcell);
//        
//        // Always run the daily stuff
//        run_daily();
//
//        if (gridcell.getConfiguration().getSchedule().isLastDayOfMonth()) {
//            // If it's the last day of the month then do the monthly stuff
//            run_monthly();
//
//            if (gridcell.getConfiguration().getSchedule().isLastMonthOfYear()) {
//                // If it's the last month of the year as well then do the
//                // yearly stuff
//                run_yearly();
//            }
//        }
//    }
//
//    public double run_daily() {
//        
//        // Do daily processes here
//        double tot_apet = 0.0;
//
//        // Update daily climate drivers etc
//        gridcell.dailyAccounting(gridcell.getConfiguration().getPFTs());
//
//        // Update crop sowing date calculation framework
//        gridcell.crop_sowing();
//
//        // Calculate daylength, insolation and potential
//        // evapotranspiration
//        gridcell.climate.dayLengthInsolEET();
//
//        // TODO: Put dynamic LC stuff here
//
//        for (Stand stand : gridcell.stands) {
//
//            for (Patch patch : stand.getPatches()) {
//                // DataRecorder.get().info("Doing run_daily for a patch");
//                // Update daily soil drivers including soil temperature
//                patch.dailyAccounting();
//                patch.dailyAccountingLC();
//
//                if (stand.landcover == LandcoverType.CROP) {
//                    patch.crop_nfert();
//                    patch.crop_sowing();
//                    patch.crop_phenology();
//                    patch.update_fpc();
//                }
//
//                // Leaf phenology for PFTs and individuals
//                new LeafPhenology(gridcell.getConfiguration()).process(patch);
//
//                // Interception
//                new InterceptionInfiltration(gridcell.getConfiguration()).process(patch);
//
//                // Photosynthesis, respiration, evapotranspiration
//                new CanopyExchange(gridcell.getConfiguration()).process(patch);
//
//                new Irrigation(gridcell.getConfiguration()).process(patch);
//
//                new GrowthDaily(gridcell.getConfiguration()).process(patch);
//
//                // Soil water accounting, snow pack accounting
//                new SoilWater(gridcell.getConfiguration()).process(patch);
//
//                // Soil organic matter and litter dynamics
//                new SomDynamics(gridcell.getConfiguration()).process(patch);
//
//                // DataRecorder.get().info(patch.apet);
//                tot_apet += patch.apet;
//
//                // // TODO: Bad idea!
//                // new PlantGrowth(_configuration).process(patch);
//                //
//                // // TODO: Bad idea!
//                // new VegDynamics(_configuration).process(patch);
//            }
//
//            // Update crop rotation status
//            stand.doCropRotation();
//        }
//
//        return tot_apet;
//    }
//
//    public void run_monthly() {
//
//        for (Stand stand : gridcell.stands) {
//            for (Patch patch : stand.getPatches()) {
//                new PlantGrowth(gridcell.getConfiguration()).process(patch);
//            }
//        }
//    }
//
//    public void run_yearly() {
//
//        for (Stand stand : gridcell.stands) {
//            for (Patch patch : stand.getPatches()) {
//                new Growth(gridcell.getConfiguration()).process(patch);
//                new VegDynamics(gridcell.getConfiguration()).process(patch);
//                // outannual(stand);
//            }
//        }
//    }
//
//}
