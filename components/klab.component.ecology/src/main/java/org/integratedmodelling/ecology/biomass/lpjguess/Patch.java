package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils.REGRes;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;
import org.integratedmodelling.procsim.api.IModelObject;

///////////////////////////////////////////////////////////////////////////////////////
//PATCH
//Stores data for a patch. In cohort and individual modes, replicate patches are
//required in each stand to accomodate stochastic variation; in population mode there
//should be just one Patch object, representing average conditions for the entire
//stand. A reference to the parent Stand object (defined below) is included as a
//member variable.

public class Patch implements IModelObject {

    // id code in range 0-npatch for patch
    public int id;

    // reference to parent Stand object
    public Stand stand;

    // list array [0...npft-1] of Patchpft objects (initialised in constructor)
    public List<PatchPFT> pft = new ArrayList<PatchPFT>();

    // vegetation for this patch
    public Vegetation vegetation;

    // soil for this patch
    public Soil soil;

    // fluxes for this patch
    public Fluxes fluxes;

    // FPAR at top of grass canopy today
    public double fpar_grass;

    // FPAR at soil surface today
    public double fpar_ff;

    // mean growing season PAR at top of grass canopy (J/m2/day)
    public double par_grass_mean;

    // number of days in growing season, estimated from mean vegetation leaf-on
    // fraction (see function fpar in canopy exchange module)
    public int nday_growingseason;

    // total patch FPC
    public double fpc_total;

    // whether patch was disturbed last year
    public boolean disturbed;

    // patch age (years since last disturbance)
    public int age;

    // probability of fire this year
    public double fireprob;

    // guess2008 - DLE - the number of days over which wcont is averaged for
    // this
    // patch, i.e. those days for which daily temp > 5.0 degC

    // Variables used by new hydrology (Dieter Gerten 2002-07)
    public int growingseasondays;

    // interception by vegetation today on patch basis (mm)
    public double intercep;

    // annual sum of AET (mm/year)
    public double aaet;

    // annual sum of soil evaporation (mm/year)
    public double aevap;

    // annual sum of interception (mm/year)
    public double aintercep;

    // annual sum of runoff (mm/year)
    public double arunoff;

    // annual sum of potential evapotranspiration (mm/year)
    public double apet;

    // equilibrium evapotranspiration today, deducting interception (mm)
    public double eet_net_veg;

    /// transpirative demand for patch, patch vegetative area basis (mm/day)
    public double wdemand;
    /// daily average of the above variable (mm/day)
    public double wdemand_day;
    /// transpirative demand for patch assuming full leaf cover today
    /** mm/day, patch vegetative area basis	*/
    public double wdemand_leafon;
    /// rescaling factor to account for spatial overlap between individuals/cohorts populations
    public double fpc_rescale;

    // monthly AET (mm/month)
    public double[] maet = new double[12];

    // monthly soil evaporation (mm/month)
    public double[] mevap = new double[12];

    // monthly interception (mm/month)
    public double[] mintercep = new double[12];

    // monthly runoff (mm/month)
    public double[] mrunoff = new double[12];

    // monthly PET (mm/month)
    public double[] mpet = new double[12];

    // FV make area part of the patch, so we don't have to make it a global
    // variable as in LPJ.
    public double area;

    // / annual nitrogen fertilization (kgN/m2/year)
    public double anfert;
    // / daily nitrogen fertilization (kgN/m2/year)
    public double dnfert;

    /// daily value of irrigation water (mm), set in irrigation(), derived from water_deficit_d
    public double irrigation_d;
    /// yearly sum of irrigation water (mm)
    public double irrigation_y;

    /// whether litter is to be sent to the soil today
    public boolean is_litter_day;

    public int     nharv;
    public boolean isharvestday;

    public boolean managed;

    // TODO ensure the ID is assigned.
    public Patch(Stand s, List<PFT> pftlist, Soiltype st) {

        int ip = 0;
        for (PFT ppft : pftlist) {
            pft.add(new PatchPFT(ip++, ppft));
        }

        this.stand = s;

        this.vegetation = new Vegetation(this);
        this.fluxes = new Fluxes(this);
        this.soil = new Soil(this, st);

        age = 0;
        disturbed = false;

        // guess2008 - initialise
        growingseasondays = 0;
    }

    public Climate getClimate() {
        return stand.getClimate();
    }

    public void dailyAccounting() {

        // DESCRIPTION
        // Updates daily soil parameters including exponential temperature
        // response terms
        // (gtemp, see below). Maintains monthly and longer term records of
        // variation in
        // soil variables. Initialises flux sums at start of simulation year.

        // INPUT AND OUTPUT PARAMETER
        // soil = patch soil
        // fluxes = current and accumulated C fluxes for patch

        // int p;
        Soil soil = this.soil;
        Fluxes fluxes = this.fluxes;

        if (stand._configuration.getSchedule().isFirstDayOfYear()) {

            // Reset fluxes

            fluxes.acflux_soil = 0.0;
            fluxes.acflux_veg = 0.0;
            fluxes.acflux_est = 0.0;
            fluxes.acflux_fire = 0.0;

            this.aaet = 0.0;
            this.aevap = 0.0;
            this.arunoff = 0.0;
            this.aintercep = 0.0;
            this.apet = 0.0;
        }

        if (stand._configuration.getSchedule().dayofmonth() == 0) {

            fluxes.mcflux_veg[stand._configuration.getSchedule().month()] = 0.0;

            this.maet[stand._configuration.getSchedule().month()] = 0.0;
            this.mevap[stand._configuration.getSchedule().month()] = 0.0;
            this.mrunoff[stand._configuration.getSchedule().month()] = 0.0;
            this.mintercep[stand._configuration.getSchedule().month()] = 0.0;
            this.mpet[stand._configuration.getSchedule().month()] = 0.0;

            // guess2008 - reset month C budget arrays each month
            fluxes.mcflux_gpp[stand._configuration.getSchedule().month()] = 0.0;
            fluxes.mcflux_ra[stand._configuration.getSchedule().month()] = 0.0;

        }

        fluxes.dcflux_veg = 0.0;

        // Store daily soil water in upper layer
        soil.dwcontupper[stand._configuration.getSchedule().julianDay()] = soil.wcont[0];

        // Store daily soil water in lower layer - guess2008
        soil.dwcontlower[stand._configuration.getSchedule().julianDay()] = soil.wcont[1];

        // On last day of month, calculate mean content of upper soil layer

        if (stand._configuration.getSchedule().isLastDayOfMonth()) {

            // TODO: Do these need updating? Probably.
            soil.mwcontupper = Utils
                    .mean_from_to(soil.dwcontupper, stand._configuration.getSchedule().julianDay()
                            - stand._configuration.getSchedule().ndaymonth() + 1, stand._configuration
                                    .getSchedule()
                                    .julianDay());

            // guess2008 - record water in lower layer too, and then update
            // mwcont
            soil.mwcontlower = Utils
                    .mean_from_to(soil.dwcontlower, stand._configuration.getSchedule().julianDay()
                            - stand._configuration.getSchedule().ndaymonth() + 1, stand._configuration
                                    .getSchedule()
                                    .ndaymonth());

            soil.mwcont[stand._configuration.getSchedule().month()][0] = soil.mwcontupper;
            soil.mwcont[stand._configuration.getSchedule().month()][1] = soil.mwcontlower;

        }

        // Calculate soil temperatures
        soiltemp(getClimate(), soil);

        // On last day of month, calculate mean soil temperature for last month

        soil.dtemp[stand._configuration.getSchedule().dayofmonth()] = soil.temp;

        if (stand._configuration.getSchedule().isLastDayOfMonth()) {
            soil.mtemp = Utils
                    .mean_from_to(soil.dtemp, 0, stand._configuration.getSchedule().ndaymonth() + 1);
        }
    }

    public void soiltemp(Climate climate, Soil soil) {

        // DESCRIPTION
        // Calculation of soil temperature at 0.25 m depth (middle of upper soil
        // layer).
        // Soil temperatures are assumed to follow surface temperatures
        // according to an
        // annual sinusoidal cycle with damped oscillation about a common mean,
        // and a
        // temporal lag.

        // For a sinusoidal cycle, soil temperature at depth z and time t from
        // beginning
        // of cycle given by (Carslaw & Jaeger 1959; Eqn 52; Jury et al 1991):
        //
        // (1) t(z,t) = t_av + a*exp(-z/d)*sin(omega*t - z/d)
        //
        // where
        // t_av = average (base) air/soil temperature
        // a = amplitude of air temp fluctuation
        // exp(-z/d) = fractional amplitude of temp fluctuation at soil depth z,
        // relative to surface temp fluctuation
        // z/d = oscillation lag in angular units at soil depth z
        // z = soil depth
        // d = sqrt(2*k/omega), damping depth
        // k = soil thermal diffusivity
        // omega = 2*PI/tau, angular frequency of oscillation (radians)
        // tau = oscillation period (365 days)
        //
        // Here we assume a sinusoidal cycle, but estimate soil temperatures
        // based on
        // a lag (z/d, converted from angular units to days) relative to air
        // temperature
        // and damping ( exp(-z/d) ) of soil temperature amplitude relative to
        // air
        // temperature amplitude. A linear model for change in air temperature
        // with time
        // during the last 31 days is used to estimate 'lagged' air temperature.
        // Soil
        // temperature today is thus given by:
        //
        // (2) temp_soil = atemp_mean + exp( -alag ) * ( temp_lag - temp_mean )
        //
        // where
        // atemp_mean = mean of monthly mean temperatures for the last year (deg
        // C)
        // alag = oscillation lag in angular units at depth 0.25 m
        // (corresponds to z/d in Eqn 1)
        // temp_lag = air temperature 'lag' days ago (estimated from linear
        // model)
        // where 'lag' = 'alag' converted from angular units to days
        //
        // Soil thermal diffusivity (k) is sensitive to soil water content and
        // is estimated
        // monthly based on mean daily soil water content for the past month,
        // interpolating
        // between estimates for 0, 15% and 100% AWHC (Van Duin 1963; Jury et al
        // 1991,
        // Fig 5.11).

        // conversion factor for soil thermal diffusivity from mm2/s to m2/day
        final double DIFFUS_CONV = 0.0864;

        // corresponds to omega/2 = pi/365 (Eqn 1)
        final double HALF_OMEGA = 8.607E-3;

        // soil depth at which to estimate temperature (m)
        final double DEPTH = stand._configuration.SOILDEPTH_UPPER * 0.0005;

        // conversion factor for oscillation lag from angular units to days
        // (=365/(2*PI))
        final double LAG_CONV = 58.09;

        double a; // regression parameters
        double b;
        double k; // soil thermal diffusivity (m2/day)
        double temp_lag; // air temperature 'lag' days ago (see above; deg C)
        double[] day = {
                0,
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10,
                11,
                12,
                13,
                14,
                15,
                16,
                17,
                18,
                19,
                20,
                21,
                22,
                23,
                24,
                25,
                26,
                27,
                28,
                29,
                30 };

        if (stand._configuration.getSchedule().isFirstYearOfSimulation()
                && stand._configuration.getSchedule().isFirstMonthOfSimulation()
                && !stand._configuration.getSchedule().isLastDayOfMonth()) {

            // First month of simulation, use air temperature for soil
            // temperature

            soil.temp = climate.temp;

        } else {

            if (stand._configuration.getSchedule().isLastDayOfMonth()) {

                // Linearly interpolate soil thermal diffusivity given mean
                // soil water content

                if (soil.mwcontupper < 0.15) {
                    k = ((soil.soiltype.thermdiff_15 - soil.soiltype.thermdiff_0) / 0.15 * soil.mwcontupper
                            + soil.soiltype.thermdiff_0)
                            * DIFFUS_CONV;
                } else {
                    k = ((soil.soiltype.thermdiff_100 - soil.soiltype.thermdiff_15) / 0.85
                            * (soil.mwcontupper - 0.15) + soil.soiltype.thermdiff_15)
                            * DIFFUS_CONV;
                }

                // Calculate parameters alag and exp(-alag) from Eqn 2

                soil.alag = DEPTH / Math.sqrt(k / HALF_OMEGA); // from Eqn 1
                soil.exp_alag = Math.exp(-soil.alag);

            }

            // Every day, calculate linear model for trend in daily air
            // temperatures for the last 31 days: temp_day = a + b * day

            REGRes r = Utils.regress(day, climate.dtemp_31);
            a = r.slope;
            b = r.intercept;

            // Calculate soil temperature

            temp_lag = a + b * (30.0 - soil.alag * LAG_CONV);
            soil.temp = climate.atemp_mean + soil.exp_alag * (temp_lag - climate.atemp_mean);
            // Eqn 2
        }
    }

    public PatchPFT getPFT(int id) {
        return pft.get(id);
    }

    @Override
    public IConfiguration getConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

    public void dailyAccountingLC() {
        if (stand._configuration.getSchedule().isFirstDayOfYear()) {
            boolean ifslowharvestpool = true; // TODO: Make this configurable
            if (ifslowharvestpool) {
                for (PFT pft : stand._configuration.getPFTs()) { // NB. also
                                                                 // unactive
                                                                 // pft's
                    PatchPFT patchpft = this.pft.get(pft.id);

                    stand.getGridcell().acflux_harvest_slow += patchpft.harvested_products_slow
                            * pft.turnover_harv_prod * stand.get_gridcell_fraction() / stand.npatch();
                    // flux from slow pool in receiving landcover after land use
                    // change (1)
                    stand.getGridcell().acflux_harvest_slow_lc[stand.landcover
                            .ordinal()] += patchpft.harvested_products_slow
                                    * pft.turnover_harv_prod * stand.get_gridcell_fraction() / stand.npatch();
                    // flux from slow pool in donating landcover after land use
                    // change (2)
                    // patch.stand.gridcell.acflux_harvest_slow_lc[pft.landcover]+=patchpft.harvested_products_slow*pft.turnover_harv_prod*patch.stand.get_gridcell_fraction()/(double)patch.stand.nobj;
                    patchpft.harvested_products_slow = patchpft.harvested_products_slow
                            * (1 - pft.turnover_harv_prod);

                    stand.getGridcell().anflux_harvest_slow += patchpft.harvested_products_slow_nmass
                            * pft.turnover_harv_prod * stand.get_gridcell_fraction() / stand.npatch();
                    stand.getGridcell().anflux_harvest_slow_lc[stand.landcover
                            .ordinal()] += patchpft.harvested_products_slow_nmass
                                    * pft.turnover_harv_prod * stand.get_gridcell_fraction()
                                    / stand.npatch();
                    patchpft.harvested_products_slow_nmass = patchpft.harvested_products_slow_nmass
                            * (1 - pft.turnover_harv_prod);
                }
            }
        }

    }

    public void crop_nfert() {
        Gridcell gridcell = stand.getGridcell();

        // Loop through PFTs
        for (PFT pft : stand._configuration.getPFTs()) {
            PatchPFT patchpft = this.pft.get(pft.id);
            GridcellPFT gridcellpft = gridcell.pft.get(pft.id);

            if (stand.pft.get(pft.id).active && pft.phenology == Phenology.CROPGREEN) {

                CropPhen ppftcrop = patchpft.get_cropphen();

                double nfert = pft.N_appfert;
                if (gridcellpft.Nfert_read >= 0.0) {
                    nfert = gridcellpft.Nfert_read;
                }
                if (!ppftcrop.fertilised[0] && ppftcrop.dev_stage > 0.0 && ppftcrop.growingseason) {
                    dnfert = nfert * (1.0 - pft.fertrate[0] - pft.fertrate[1]);
                    ppftcrop.fertilised[0] = true;
                } else if (!ppftcrop.fertilised[1] && ppftcrop.dev_stage > pft.fert_stages[0]
                        && ppftcrop.growingseason) {
                    dnfert = nfert * pft.fertrate[0];
                    ppftcrop.fertilised[1] = true;
                } else if (!ppftcrop.fertilised[2] && ppftcrop.dev_stage > pft.fert_stages[1]
                        && ppftcrop.growingseason) {
                    dnfert = nfert * (pft.fertrate[1]);
                    ppftcrop.fertilised[2] = true;
                } else {
                    dnfert = 0.0;
                }
                // if(date.day == ppftcrop.bicdate)
                // patch.dnfert = 0.003;
                anfert += dnfert;
            }
        }

    }

    // / Sowing date method from Waha et al. 2010
    /**
     * Enters here every day when growingseason==false
     */
    private void Crop_sowing_date_new(PFT pft) {

        int day = stand._configuration.getSchedule().julianDay();

        PatchPFT patchpft = this.pft.get(pft.id);
        CropPhen ppftcrop = patchpft.get_cropphen();
        Gridcell gridcell = stand.getGridcell();
        GridcellPFT gridcellpft = gridcell.pft.get(pft.id);
        StandPFT standpft = stand.pft.get(pft.id);
        Climate climate = gridcell.climate;
        SeasonalityType seasonality = climate.seasonality;
        int length_growseas_def;
        boolean temp_sdate = false, prec_sdate = false, def_sdate = false;

        // Different sowing date options for irrigated crops at sites with
        // climate.seasonality == SEASONALITY_PRECTEMP:
        // 1. use temperature-dependent sowing limits (define
        // IRRIGATED_USE_TEMP_SDATE)
        // 2. use precipitation-triggered sowing (IRRIGATED_USE_TEMP_SDATE
        // undefined)
        // Option not to constrain sowing to the sowing date window (as in the
        // old sowing date method); SD_TEMP_WINDOW undefied

        // Determine, based upon site climate seasonality and sowing preferences
        // for irrigated crops, if sowing should be triggered by
        // temperature or precipitation, or whether to use a default sowing
        // date.
        if (seasonality == SeasonalityType.SEASONALITY_TEMP
                || seasonality == SeasonalityType.SEASONALITY_TEMPPREC)
            temp_sdate = true;
        else if ((seasonality == SeasonalityType.SEASONALITY_PREC
                || seasonality == SeasonalityType.SEASONALITY_PRECTEMP)
                && climate.prec_range != PrecSeasonalityType.WET)
            prec_sdate = true;
        else
            // if(seasonality == SEASONALITY_NO) || (seasonality ==
            // SEASONALITY_PREC || seasonality == SEASONALITY_PRECTEMP) &&
            // climate.prec_range == WET)
            def_sdate = true;

        // adjust sowing window if too close to hlimitdate (before)
        if (temp_sdate && patchpft.swindow[0] != -1) {

            if (Utils.dayinperiod(patchpft.swindow[0], Utils
                    .stepfromdate(ppftcrop.hlimitdate, -100), ppftcrop.hlimitdate)) {

                patchpft.swindow[0] = ppftcrop.hlimitdate + 1;
                if (Utils.dayinperiod(patchpft.swindow[1], Utils
                        .stepfromdate(ppftcrop.hlimitdate, -100), ppftcrop.hlimitdate))
                    patchpft.swindow[1] = ppftcrop.hlimitdate + 1;
            }
        }

        // monitor climate triggers within the sowing window
        if (Utils.dayinperiod(day, patchpft.swindow[0], patchpft.swindow[1])) {

            if (day != patchpft.swindow[1]) {

                if (temp_sdate) {
                } else if (prec_sdate) {
                    if (climate.prec > 0.1 || standpft.irrigated)
                        ppftcrop.sdate = day;
                } else
                    // if(def_sdate)
                    ppftcrop.sdate = day; // first day of sowing window
            } else
                // last day of sowing window
                ppftcrop.sdate = day;
        }

        //
        if (standpft.sdate_force >= 0)
            patchpft.cropphen.sdate = standpft.sdate_force;

        // calculation of hucountend (last day of heat unit sampling period);
        // sdate is first day
        // NB. currently the sampling periods (which roughly correspond to
        // growing periods) are unrealistically long,
        // Since shorter growing periods result in lower yield, a revision of
        // this section will also make a revision of crop productivity necessary
        if (day == ppftcrop.sdate) {

            if (gridcellpft.sdate_default > gridcellpft.hlimitdate_default)
                length_growseas_def = gridcellpft.hlimitdate_default + 365 - gridcellpft.sdate_default;
            else
                length_growseas_def = gridcellpft.hlimitdate_default - gridcellpft.sdate_default;

            // if(stlist[patch.stand.stid].rotation.multicrop &&
            // gridcellpft.multicrop)
            if (stand._configuration.getSTs().get(stand.stid).rotation.multicrop)
                length_growseas_def = 150;

            if (prec_sdate)
                ppftcrop.hlimitdate = Utils.stepfromdate(day, length_growseas_def);
            else
                ppftcrop.hlimitdate = gridcellpft.hlimitdate_default;

            length_growseas_def = Math.min(length_growseas_def, 245); // set an
                                                                      // upper
                                                                      // limit
                                                                      // of
                                                                      // 245
                                                                      // for
                                                                      // the
                                                                      // growing
                                                                      // season

            if (pft.ifsdautumn) { // winter crops
                // if(pft.ifsdautumn && gridcellpft.wintertype) { // winter crops:
                // try this

                if (temp_sdate)
                    ppftcrop.hucountend = Utils.stepfromdate(ppftcrop.hlimitdate, -20);
                else if (prec_sdate
                        && climate.prec_seasonality.ordinal() <= PrecSeasonalityType.DRY_WET.ordinal()) { // dry
                                                                                                          // some
                                                                                                          // time
                                                                                                          // during
                                                                                                          // the
                                                                                                          // year
                    if (standpft.irrigated)
                        ppftcrop.hucountend = Utils.stepfromdate(day, Math.min(length_growseas_def, 230));
                    else
                        ppftcrop.hucountend = Utils.stepfromdate(day, Math.min(length_growseas_def, 210)); // shorter
                                                                                                           // growing
                                                                                                           // period
                                                                                                           // when
                                                                                                           // risk
                                                                                                           // for
                                                                                                           // water
                                                                                                           // stress.
                } else if (def_sdate)
                    ppftcrop.hucountend = Utils.stepfromdate(day, 230);
            } else if (pft.name.startsWith("TrRi")) // rice
                ppftcrop.hucountend = Utils.stepfromdate(day, Math.min(length_growseas_def, 230));
            else { // all other crops
                if (prec_sdate
                        && climate.prec_seasonality.ordinal() <= PrecSeasonalityType.DRY_WET.ordinal()) { // dry
                                                                                                          // some
                                                                                                          // time
                                                                                                          // during
                                                                                                          // the
                                                                                                          // year

                    if (standpft.irrigated)
                        ppftcrop.hucountend = Utils.stepfromdate(day, length_growseas_def);
                    else
                        ppftcrop.hucountend = Utils.stepfromdate(day, Math.min(length_growseas_def, 210)); // shorter
                                                                                                           // growing
                                                                                                           // period
                                                                                                           // when
                                                                                                           // risk
                                                                                                           // for
                                                                                                           // water
                                                                                                           // stress.
                } else
                    ppftcrop.hucountend = Utils.stepfromdate(day, length_growseas_def);
            }

            if (standpft.sdate_force >= 0) {
                if (standpft.hdate_force >= 0) {

                    patchpft.cropphen.hlimitdate = standpft.hdate_force;
                    patchpft.cropphen.hucountend = standpft.hdate_force;
                }
            }
        }
    }

    public void crop_sowing() {
        Gridcell gridcell = stand.getGridcell();
        Climate climate = gridcell.climate;

        // Loop through PFTs
        for (PFT pft : stand._configuration.getPFTs()) {
            PatchPFT patchpft = this.pft.get(pft.id);
            GridcellPFT gridcellpft = gridcell.pft.get(pft.id);

            if (stand.pft.get(pft.id).active && pft.phenology == Phenology.CROPGREEN) {

                CropPhen ppftcrop = patchpft.get_cropphen();

                int day = stand._configuration.getSchedule().julianDay();
                if (day == climate.testday_temp) {

                    if (stand.pft.get(pft.id).irrigated) {
                        patchpft.swindow[0] = gridcellpft.swindow_irr[0];
                        patchpft.swindow[1] = gridcellpft.swindow_irr[1];
                    } else {
                        patchpft.swindow[0] = gridcellpft.swindow[0];
                        patchpft.swindow[1] = gridcellpft.swindow[1];
                    }
                }

                if (stand.pftid == pft.id && !ppftcrop.growingseason) {

                    // copy sowing window from gridcellpft
                    if (day == Utils.stepfromdate(ppftcrop.hdate, 1) && ppftcrop.hdate != -1
                            || day == climate.testday_temp) {

                        if (gridcellpft.swindow[0] == -1) {
                            gridcellpft.sowing_restriction = true;
                            ppftcrop.hdate = -1;
                            ppftcrop.eicdate = -1; // redundant
                            stand.isrotationday = true;
                        } else {
                            if (!stand.infallow)
                                gridcellpft.sowing_restriction = false;
                            else if (stand.ndays_inrotation > 180)
                                stand.isrotationday = true;
                        }
                    }

                    if (!gridcellpft.sowing_restriction) {

                        // new sowing date method (Waha et al. 2010)
                        Crop_sowing_date_new(pft);
                    }
                }

                // set eicdate (last intercrop day)
                if (ppftcrop.sdate != -1) {

                    if (!ppftcrop.growingseason)
                        ppftcrop.eicdate = Utils.stepfromdate(ppftcrop.sdate, -15);

                    if (Utils.dayinperiod(day, ppftcrop.eicdate, ppftcrop.sdate)) {

                        if (ppftcrop.intercropseason)
                            ppftcrop.eicdate = day;
                        else if (ppftcrop.bicdate == ppftcrop.sdate)
                            ppftcrop.bicdate = -1;
                    }
                }
            }
        }

    }

    /// Calculation of development stage
    /** Accumulation of development during sampling period. TODO Add reference
     */
    private void calc_ds(PFT pft) {
        int day = stand._configuration.getSchedule().julianDay();

        PatchPFT patchpft = this.pft.get(pft.id);

        CropPhen ppftcrop = patchpft.get_cropphen();
        Climate climate = getClimate();
        double T = climate.temp;

        // account for vernalization if needs for vernalization not yet satisfied //trg=tb for crops other
        // than TeWW and TeRa and don't enter here
        if (ppftcrop.vdsum_alloc < 1) {

            if (T > pft.T_vn_min && T < pft.T_vn_max) {
                double alpha_v = Math.log(2.0)
                        / (Math.log((pft.T_vn_max - pft.T_vn_min) / (pft.T_vn_opt - pft.T_vn_min)));
                double fT_v = (2.0 * Math.pow((T - pft.T_vn_min), alpha_v)
                        * Math.pow((pft.T_vn_opt - pft.T_vn_min), alpha_v)
                        - Math.pow((T - pft.T_vn_min), 2.0 * alpha_v))
                        / Math.pow((pft.T_vn_opt - pft.T_vn_min), 2.0 * alpha_v);
                ppftcrop.vd = ppftcrop.vd + fT_v;
                ppftcrop.vdsum_alloc = Math.min(1.0, Math.pow(ppftcrop.vd, 5.0)
                        / (Math.pow(22.5, 5.0) + Math.pow(ppftcrop.vd, 5.0)));
            }
        }

        double e = 2.71828183;
        double P = climate.daylength_save[day];
        double fP = 0;

        if (pft.photo[2] > 0) // short day plant
        {
            if (P < pft.photo[0])
                fP = 1;
            else
                fP = Math.min(1.0, Math.pow(e, (-pft.photo[1] * (P - pft.photo[0]))));
        } else // long day plant
        {
            if (P < pft.photo[0])
                fP = 0;
            else
                fP = Math.min(1.0, 1.0 - Math.pow(e, (-pft.photo[1] * (P - pft.photo[0]))));
        }
        double fT = 0.0;
        double T_min = pft.T_veg_min;
        double T_opt = pft.T_veg_opt;
        double T_max = pft.T_veg_max;

        if (ppftcrop.dev_stage >= 1) {
            T_min = pft.T_rep_min;
            T_opt = pft.T_rep_opt;
            T_max = pft.T_rep_max;
        }

        double alpha = Math.log(2.0) / (Math.log((T_max - T_min) / (T_opt - T_min)));

        if (T > T_min && T < T_max)
            fT = Math.min(1.0, (2.0 * Math.pow((T - T_min), alpha) * Math.pow((T_opt - T_min), alpha)
                    - Math.pow((T - T_min), 2.0 * alpha)) / Math.pow((T_opt - T_min), 2.0 * alpha));

        double DR = 0.0;
        if (ppftcrop.dev_stage < 1.0)
            DR = pft.dev_rate_veg * ppftcrop.vdsum_alloc * fP * fT;
        else
            DR = pft.dev_rate_rep * fT;

        ppftcrop.dev_stage = Math.min(2.0, ppftcrop.dev_stage + DR);
    }

    /// Calculation of harvest index
    /** Based on fphu. Restricted by water stress.
     *  Equations are from Neitsch et al. 2002.
     */
    private void calc_hi(PFT pft) {

        double wdf, fwdf, hi_save;
        PatchPFT patchpft = this.pft.get(pft.id);
        CropPhen ppftcrop = patchpft.get_cropphen();

        ppftcrop.hi = pft.hiopt * 100 * ppftcrop.fphu
                / (100 * ppftcrop.fphu + Math.exp(11.1 - 10.0 * ppftcrop.fphu)); // SWAT 5:2.4.1
        ppftcrop.fhi_phen = ppftcrop.hi / pft.hiopt;

        // Correction of HI according to water stress:
        ppftcrop.demandsum_crop += wdemand;
        if (patchpft.wsupply > wdemand)
            ppftcrop.supplysum_crop += wdemand;
        else
            ppftcrop.supplysum_crop += patchpft.wsupply;

        if (ppftcrop.demandsum_crop > 0.0)
            wdf = 100.0 * ppftcrop.supplysum_crop / ppftcrop.demandsum_crop; // SWAT 5:3.3.2 : aetsum/petsum
        else
            wdf = 100.0;

        fwdf = wdf / (wdf + Math.exp(6.13 - 0.0883 * wdf)); // (SWAT 5:3.3.1)

        hi_save = ppftcrop.hi;

        ppftcrop.hi = ppftcrop.fhi_phen * ((pft.hiopt - pft.himin) * fwdf + pft.himin);

        if (ppftcrop.hi > 0.0 && hi_save > 0.0)
            ppftcrop.fhi_water = ppftcrop.hi / hi_save;

        ppftcrop.fhi = ppftcrop.fhi_phen * ppftcrop.fhi_water;
    }

    public void crop_phenology() {
        // / Handles heat unit and harvest index calculation and identifies
        // harvest, senescence and intercrop events.
        /**
         * Accumulation of heat units during sampling period used for
         * calculation of dynamic phu if DYNAMIC_PHU defined. Sets
         * patchpft.cropphen variables growingseason, hdate, intercropseason and
         * senescence
         */
        int day = stand._configuration.getSchedule().julianDay();

        for (PatchPFT patchpft : this.pft) {
            PFT pft = patchpft.pFT;
            StandPFT standpft = stand.pft.get(pft.id);
            Gridcell gridcell = stand.getGridcell();
            Climate climate = gridcell.climate;
            GridcellPFT gridcellpft = gridcell.pft.get(pft.id);
            double hu = 0.0;

            if (stand.pft.get(pft.id).active) {
                if (pft.phenology == Phenology.CROPGREEN) {

                    CropPhen ppftcrop = patchpft.get_cropphen();
                    ppftcrop.growingseason_ystd = ppftcrop.growingseason;

                    // resets on first day of the year:
                    if (day == 0) {
                        ppftcrop.fphu_harv = -1.0;
                        ppftcrop.fhi_harv = -1.0;
                        ppftcrop.sdate_harv = -1;
                        ppftcrop.nsow = 0;
                        ppftcrop.sendate = -1;
                        ppftcrop.nharv = 0;

                        ppftcrop.sownlastyear = false;

                        for (int i = 0; i < 2; i++) {
                            ppftcrop.sdate_harvest[i] = -1;
                            ppftcrop.hdate_harvest[i] = -1;
                            // ppftcrop.fphu_harvest[i] = -1.0;
                            // ppftcrop.fhi_harvest[i] = -1.0;
                            ppftcrop.sdate_thisyear[i] = -1;
                        }
                    }

                    // initiations on sowing day:
                    if (day == ppftcrop.sdate) {
                        ppftcrop.fphu = 0.0;
                        ppftcrop.fhi = 0.0;
                        ppftcrop.fhi_phen = 0.0;
                        ppftcrop.fhi_water = 1.0;
                        ppftcrop.hdate = -1;
                        ppftcrop.bicdate = -1;

                        ppftcrop.growingseason = true;
                        ppftcrop.growingdays = 0;
                        ppftcrop.nsow++;

                        if (ppftcrop.nsow == 1)
                            ppftcrop.sdate_thisyear[0] = ppftcrop.sdate;
                        else if (ppftcrop.nsow == 2)
                            ppftcrop.sdate_thisyear[1] = ppftcrop.sdate;

                        // calculate pvd, phu & tb
                        phu_init(ppftcrop, gridcellpft);
                    }

                    // Calculation of accumulated heat units and harvest index
                    // from sowing to maturity
                    if (ppftcrop.growingseason) {
                        ppftcrop.senescence_ystd = ppftcrop.senescence;
                        ppftcrop.hi_ystd = ppftcrop.hi;
                        ppftcrop.intercropseason = false;
                        ppftcrop.growingdays++;

                        // check if harvest is prescribed
                        boolean force_harvest = day == standpft.hdate_force;

                        // before maturity is reached
                        boolean pre_maturity = (stand._configuration.isNLimitedLC(LandcoverType.CROP))
                                ? ppftcrop.dev_stage < 2.0
                                : ppftcrop.husum < ppftcrop.phu;

                        if (pre_maturity
                                && Utils.dayinperiod(day, ppftcrop.sdate, Utils
                                        .stepfromdate(ppftcrop.hlimitdate, -1))
                                && !force_harvest) {

                            // count accumulated heat units after sowing date
                            calc_hu(pft);

                            if (stand._configuration.isNLimitedLC(LandcoverType.CROP))
                                calc_ds(pft);

                            // test for senescence
                            if (ppftcrop.fphu >= pft.fphusen) {

                                if (ppftcrop.senescence_ystd == false)
                                    ppftcrop.sendate = day;
                                ppftcrop.senescence = true;
                            }

                            // calculated harvest index
                            calc_hi(pft);

                        } else { // harvest

                            // save today as harvest day
                            ppftcrop.hdate = day;

                            ppftcrop.growingseason = false;
                            ppftcrop.intercropseason = false;
                            ppftcrop.senescence = false;

                            ppftcrop.fertilised[0] = false;
                            ppftcrop.fertilised[1] = false;
                            ppftcrop.fertilised[2] = false;

                            // set start of intercrop grass growth
                            ppftcrop.bicdate = Utils.stepfromdate(ppftcrop.hdate, 15);

                            // count number of harvest events this year
                            ppftcrop.nharv++;

                            // Save phenological values and dates at harvest:
                            ppftcrop.fphu_harv = ppftcrop.fphu;
                            ppftcrop.fhi_harv = ppftcrop.fhi;
                            ppftcrop.sdate_harv = ppftcrop.sdate;
                            ppftcrop.lgp = ppftcrop.growingdays;

                            // allowing saving at two harvests per year
                            if (ppftcrop.nharv == 1) {
                                ppftcrop.sdate_harvest[0] = ppftcrop.sdate;
                                ppftcrop.hdate_harvest[0] = day;
                                // ppftcrop.fphu_harvest[0] = ppftcrop.fphu;
                                // ppftcrop.fhi_harvest[0] = ppftcrop.fhi;
                                if (ppftcrop.sdate > day)
                                    ppftcrop.sownlastyear = true;
                            } else if (ppftcrop.nharv == 2) {
                                ppftcrop.sdate_harvest[1] = ppftcrop.sdate;
                                ppftcrop.hdate_harvest[1] = day;
                                // ppftcrop.fphu_harvest[1] = ppftcrop.fphu;
                                // ppftcrop.fhi_harvest[1] = ppftcrop.fhi;
                            }

                            ppftcrop.demandsum_crop = 0.0;
                            ppftcrop.supplysum_crop = 0.0;

                            if (pft.ifsdprec) { // TeCo,TrMi,TrMa,TrPe; with
                                                // NEWSOWINGDATE: all crops
                                ppftcrop.sdate = -1;
                                ppftcrop.eicdate = -1;
                            }

                        } // end harvest
                    } // from sowing has taken place until harvest day

                    // TODO: Make configurable
                    boolean ifcalcdynamic_phu = true;
                    // continue sampling heat units from hdate until last
                    // sampling date
                    if (ifcalcdynamic_phu && ppftcrop.growingseason == false && ppftcrop.hu_samplingperiod)
                        calc_hu(pft);

                    if (stand.pftid == pft.id
                            && stand._configuration.getSTs()
                                    .get(stand.stid).intercrop == IntercropType.NATURALGRASS) {

                        if (!ppftcrop.intercropseason && day == ppftcrop.bicdate)
                            ppftcrop.intercropseason = true;

                        if (day == ppftcrop.eicdate) {
                            ppftcrop.intercropseason = false;
                        }
                    }
                } else if (pft.phenology == Phenology.ANY) { // crop grasses
                                                             // using
                                                             // standard
                                                             // guess
                                                             // phenology
                                                             // calculation

                    if (stand.pftid != pft.id) {
                        CropPhen ppftcrop = patchpft.get_cropphen();

                        if (day == 0)
                            ppftcrop.nharv = 0;

                        if (day == this.pft.get(stand.pftid).cropphen.bicdate)
                            ppftcrop.growingseason = true;
                        else if (day == this.pft.get(stand.pftid).cropphen.eicdate)
                            ppftcrop.growingseason = false;
                    }
                }
            }
        }
    }

    private void calc_hu(PFT pft) {
        // TODO Auto-generated method stub
        // / Calculation of accumulated of heat units
        /**
         * Accumulation of heat units during sampling period used for
         * calculation of dynamic phu if DYNAMIC_PHU defined. Equation is from
         * Neitsch et al. 2002.
         */
        int day = stand._configuration.getSchedule().julianDay();

        double hu;
        PatchPFT patchpft = this.pft.get(pft.id);
        CropPhen ppftcrop = patchpft.get_cropphen();
        Climate climate = getClimate();

        // calculation av fphu:
        hu = Math.max(0.0, climate.temp - ppftcrop.tb);

        // account for vernalization if needs for vernalization not yet
        // satisfied //trg=tb for crops other than TeWW and TeRa and don't enter
        // here
        if (climate.temp < pft.trg && ppftcrop.vdsum < ppftcrop.pvd) { // trg=12
                                                                       // for
                                                                       // TeWW
                                                                       // &
                                                                       // TeRa

            ppftcrop.vdsum++;
            ppftcrop.vrf = Math.min(1.0, (double) ppftcrop.vdsum / (double) ppftcrop.pvd);

            // vernalisation reduction factor has no effect once temp>trg even
            // if needs are not satisfied
            // no effect as well if temp>trg at the beginning of the growing
            // season...
            hu *= ppftcrop.vrf;
        }

        // account for response to photoperiod
        ppftcrop.prf = (1 - pft.psens)
                * Math.min(1.0, Math.max(0.0, (climate.daylength_save[day] - pft.pb) / (pft.ps - pft.pb)))
                + pft.psens;
        hu *= ppftcrop.prf;

        if (day == ppftcrop.sdate)
            ppftcrop.husum = 0.0;

        // Accumulate heat units during growing period
        if (ppftcrop.growingseason) {
            // daily effective temperature sum (degree-days)
            ppftcrop.husum += hu;

            // phenological scale (fraction of growing season)
            ppftcrop.fphu = Math.min(1.0, ppftcrop.husum / ppftcrop.phu); // SWAT
                                                                          // 5:2.1.11
        }

        // TODO: Make configurable
        boolean ifcalcdynamic_phu = true;
        // Sample heat units for dynamic phu calculation
        if (ifcalcdynamic_phu) {

            if (day == ppftcrop.sdate) {
                ppftcrop.hu_samplingperiod = true;
                ppftcrop.hu_samplingdays = 0;
                ppftcrop.husum_sampled = 0;
            }

            if (ppftcrop.hu_samplingperiod) {

                ppftcrop.husum_sampled += hu;
                ppftcrop.hu_samplingdays++;

                if (day == ppftcrop.hucountend) {

                    ppftcrop.husum_sampled -= hu; // Don't count the hu's on
                                                  // last day
                    ppftcrop.husum_max = ppftcrop.husum_sampled;

                    ppftcrop.hu_samplingperiod = false;
                }
            }
        }

    }

    /// Initiation of potential heat unit calculation
    /** Calculates pvd (required vernalising days), tb (base temperature)
     *  and phu (potential heat units) based on Bondeau et al. 2007.
     *  Dynamic phu calculation based on Lindeskog et al. 2013.
     *  Called on sowing date.
     */
    public void phu_init(CropPhen ppftcrop, GridcellPFT gridcellpft) {
        int year = stand._configuration.getSchedule().year();

        PFT pft = gridcellpft.pft;
        Climate climate = getClimate();
        double phu_last_year = ppftcrop.phu;

        ppftcrop.husum = 0.0;
        ppftcrop.vrf = 1.0;
        ppftcrop.vdsum = 0;
        ppftcrop.prf = 1.0;

        ppftcrop.pvd = pft.pvd; // default; kept for TrMi, TePu, TeSb, TrMa, TeSo, TrPe
        ppftcrop.phu = pft.phu;
        ppftcrop.tb = pft.tb;

        ppftcrop.vdsum_alloc = 0.0;
        ppftcrop.vd = 0.0;
        ppftcrop.dev_stage = 0.0;

        if (pft.ifsdautumn) { // TeWW,TeRa

            if (gridcellpft.wintertype) { // Autumn sowing
                // if neither spring or winter conditions for the past 20 years
                if ((gridcellpft.first_autumndate20 == climate.testday_temp
                        || gridcellpft.first_autumndate20 == climate.coldestday)
                        && gridcellpft.first_autumndate % 365 == gridcellpft.first_autumndate20
                        && (gridcellpft.last_springdate20 == climate.testday_temp
                                || gridcellpft.last_springdate20 == climate.coldestday)
                        && gridcellpft.last_springdate == gridcellpft.last_springdate20) {

                    ppftcrop.pvd = pft.pvd;
                    ppftcrop.phu = pft.phu;
                }
                // not all past 20 years without vernendoccurred: too cold
                else if (!(gridcellpft.last_verndate20 == gridcellpft.last_springdate20 + 60
                        && gridcellpft.last_verndate == gridcellpft.last_verndate20)) {

                    // pvd:
                    // vernalization (below 12 degrees) is supposed to occur directly at sowing
                    // (trg=tempautumn) for TeWW, for TeRa a 20-day lag (tempautumn=17) ??
                    // first_autumndate20 occurred before last_verndate20
                    if ((ppftcrop.sdate < 180 || gridcellpft.last_verndate20 >= 180) && climate.lat >= 0.0
                            || climate.lat < 0.0) {
                        if (!pft.name.startsWith("TeWW"))
                            ppftcrop.pvd = Math.min(60, gridcellpft.last_verndate20 - ppftcrop.sdate);
                        else if (!pft.name.startsWith("TeRa"))
                            ppftcrop.pvd = Math
                                    .max(0, Math.min(60, gridcellpft.last_verndate20 - ppftcrop.sdate - 20));
                    }
                    // first_autumndate20 occurred after last_verndate20
                    else if (!pft.name.startsWith("TeWW"))
                        ppftcrop.pvd = Math.min(60, gridcellpft.last_verndate20 + 365 - ppftcrop.sdate);
                    // first_autumndate20 occurred after last_verndate20
                    else if (!pft.name.startsWith("TeRa"))
                        ppftcrop.pvd = Math.max(0, Math
                                .min(60, gridcellpft.last_verndate20 + 365 - ppftcrop.sdate - 20));

                    // phu:
                    if (ppftcrop.sdate < 184 + climate.adjustlat) {
                        if (!pft.name.startsWith("TeWW"))
                            ppftcrop.phu = Math
                                    .max(1700.0, -0.1081 * Math.pow(ppftcrop.sdate - climate.adjustlat, 2)
                                            + 3.1633 * (ppftcrop.sdate - climate.adjustlat) + 2876.9);
                        else if (!pft.name.startsWith("TeRa"))
                            ppftcrop.phu = Math
                                    .max(2100.0, -0.1081 * Math.pow(ppftcrop.sdate - climate.adjustlat, 2)
                                            + 3.1633 * (ppftcrop.sdate - climate.adjustlat) + 3279.7);
                    } else {
                        if (!pft.name.startsWith("TeWW")) {
                            ppftcrop.phu = Math
                                    .max(1700.0, -0.1081 * Math.pow((double) ppftcrop.sdate - 365, 2)
                                            + 3.1633 * ((double) ppftcrop.sdate - 365) + 2876.9);
                            ppftcrop.phu *= 0.8;
                        } else if (!pft.name.startsWith("TeRa"))
                            ppftcrop.phu = Math
                                    .max(2100.0, -0.1081 * Math.pow((double) ppftcrop.sdate - 365, 2)
                                            + 3.1633 * ((double) ppftcrop.sdate - 365) + 3279.7);
                    }
                }
            } else { // spring sowing
                // If last_verndate has occurred during the past 20 year (or too warm):
                if (!(gridcellpft.last_verndate20 == ppftcrop.sdate + 60
                        && gridcellpft.last_verndate == gridcellpft.last_verndate20)) {
                    ppftcrop.pvd = Math.min(60, gridcellpft.last_verndate20 - ppftcrop.sdate);
                    ppftcrop.phu = 1300.0;
                }
                // If no last_verndate occurred during the past 20 year (too cold):
                else {
                    ppftcrop.pvd = 30;
                    ppftcrop.phu = 1500.0;
                }
            }
        } else if (!pft.name.startsWith("TrRi") || !pft.name.startsWith("TeSf")) {

            if (!pft.name.startsWith("TeSf"))
                ppftcrop.phu = Math.min(2000.0, Math
                        .max(1300.0, -700.0 / 90.0 * (ppftcrop.sdate - climate.adjustlat) + 2460.0));

            if (!pft.name.startsWith("TrRi") && year <= 1) {
                if (stand.getGridcell().getLon() < 60.0 || stand.getGridcell().getLat() > 30.0)
                    ppftcrop.phu = 1600.0;
            }
        }

        // TODO: Make configurable
        boolean ifcalcdynamic_phu = true;
        // Calculation of potential heat units according to local climate.
        if (ifcalcdynamic_phu) {

            // add last year's husum_max to running mean
            if (ppftcrop.husum_max > 0) {
                ppftcrop.nyears_hu_sample++;
                int years = Math.min(ppftcrop.nyears_hu_sample, 10);
                ppftcrop.husum_max_10 = (ppftcrop.husum_max_10 * (years - 1) + ppftcrop.husum_max) / years;
            }
            ppftcrop.husum_max = 0.0;

            ppftcrop.phu_old = ppftcrop.phu; // phu_old for printout

            // set phu according to running mean
            if (ppftcrop.nyears_hu_sample > 0)
                ppftcrop.phu = Math.max(900.0, 0.9 * ppftcrop.husum_max_10);
        }
    }

    public void update_fpc() {
        if (stand.landcover == LandcoverType.CROP) {
            fpc_total = 0.0;

            for (Individual indiv : vegetation) {
                if (indiv.growingseason())
                    fpc_total += indiv.fpc;
            }
            // Calculate rescaling factor to account for overlap between populations/
            // cohorts/individuals (i.e. total FPC > 1)
            // necessary to undate here after growingseason updated
            fpc_rescale = 1.0 / Math.max(fpc_total, 1.0);
        }
    }
}
