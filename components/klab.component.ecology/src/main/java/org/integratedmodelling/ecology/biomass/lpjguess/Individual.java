package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.ecology.biomass.lpjguess.common.CropIndiv;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;
import org.integratedmodelling.procsim.api.IModelObject;

///////////////////////////////////////////////////////////////////////////////////////
//INDIVIDUAL
//State variables for a vegetation individual. In population mode this is the average
//individual of a PFT population; in cohort mode: the average individual of a cohort;
//in individual mode: an individual plant. Each grass PFT is represented as a single
//individual in all modes. Individual objects are collected within list arrays of
//class Vegetation (defined below), of which there is one for each patch, and include
//a reference to their 'parent' Vegetation object. Use the createobj member function
//of class Vegetation to add new individuals.

public class Individual implements IModelObject {

    // reference to Pft object containing static parameters for this individual
    public PFT pft;

    // reference to Vegetation object to which this Individual belongs
    // STORE
    public Vegetation vegetation;

    // id code (0-based, sequential)
    public int id;

    // leaf C biomass on modelled area basis (kgC/m2)
    public double cmass_leaf;
    public double cmass_root;
    // fine root C biomass on modelled area basis (kgC/m2)
    public double cmass_sap;
    // sapwood C biomass on modelled area basis (kgC/m2)
    public double cmass_heart;
    // heartwood C biomass on modelled area basis (kgC/m2)
    public double cmass_debt;
    // C "debt" (retrospective storage) (kgC/m2)

    public double cmass_tot_luc;

    public double cmass_leaf_post_turnover;
    public double cmass_root_post_turnover;
    public int    last_turnover_day;

    // / leaf N biomass on modelled area basis (kgN/m2)
    public double nmass_leaf;
    // / root N biomass on modelled area basis (kgN/m2)
    public double nmass_root;
    // / sap N biomass on modelled area basis (kgN/m2)
    public double nmass_sap;
    // / heart N biomass on modelled area basis (kgN/m2)
    public double nmass_heart;

    // / leaf N biomass on modelled area basis saved on first day of land use
    // change year
    public double nmass_leaf_luc;
    // / root N biomass on modelled area basis on first day of land use change
    // year
    public double nmass_root_luc;
    // / sap N biomass on modelled area basis on first day of land use change
    // year
    public double nmass_sap_luc;
    // / heart N biomass on modelled area basis on first day of land use change
    // year
    public double nmass_heart_luc;
    public double nmass_tot_luc;

    public double fpc;
    // foliar projective cover (FPC) under full leaf cover as fraction of
    // modelled
    // area

    // / foliar projective cover (FPC) this day as fraction of modelled area
    public double fpc_daily;
    public double fpar;
    // fraction of PAR absorbed by foliage over projective area today, taking
    // account of leaf phenological state
    public double densindiv;
    // average density of individuals over patch (indiv/m2)
    public double phen;
    // vegetation phenological state (fraction of potential leaf cover)
    public double aphen;
    // annual sum of daily fractional leaf cover (equivalent number of days with
    // full leaf cover) (population mode only; reset on expected coldest day of
    // year)
    public int    aphen_raingreen;
    // annual number of days with full leaf cover) (raingreen PFTs only; reset
    // on
    // 1 January)
    public double assim;
    // daily net assimilation (GPP-leaf respiration) on modelled area basis
    // (kgC/m2/day)
    public double resp;
    // daily maintenance respiration (not including leaf respiration) and growth
    // respiration on modelled area basis (kgC/m2/day)
    public double anpp;
    // accumulated NPP over modelled area (kgC/m2/year); = annual NPP following
    // call to growth module on last day of simulation year
    public double aet;
    // actual evapotranspiration over projected area (mm/day)
    public double ltor;
    // leaf to root mass ratio
    public double height;
    // plant height (m)
    public double crownarea;
    // plant crown area (m2)
    public double deltafpc;
    // increment in fpc since last simulation year
    public double wscal;
    // water stress parameter (0-1 range; 1=minimum stress) (updated daily)
    public double wscal_mean;
    // running sum (converted to annual mean) for wscal
    public double boleht;
    // bole height, i.e. height above ground of bottom of crown cylinder (m)
    // (individual and cohort modes only)
    public double lai;
    // patch-level lai for this individual or cohort (function fpar)
    public double lai_layer;
    // patch-level lai for cohort in current vertical layer (function fpar)
    public double lai_indiv;
    // individual leaf area index (individual and cohort modes only)

    // / patch-level individual leaf area index (individual and cohort modes
    // only)
    public double   lai_daily;
    // / daily individual leaf area
    public double   lai_indiv_daily;
    public double[] greff_5 = new double[IConfiguration.NYEARGREFF];
    // growth efficiency (NPP/leaf area) for each of the last five simulation
    // years
    // (kgC/m2/yr)
    public double   age;
    // individual/cohort age (years)
    public double[] mnpp    = new double[12];
    // monthly NPP (kgC/m2/month)
    public double[] mlai    = new double[12];
    // monthly LAI (including phenology component)

    public double[] mgpp = new double[12];
    // monthly GPP-leafresp (kgC/m2/month)
    public double[] mra  = new double[12];
    // monthly respiration

    // Variables used by "fast" canopy exchange code (Ben Smith 2002-07)

    public double fpar_wstress;
    // FPAR for days with water stress (see canopy exchange module)
    public double fpar_leafon;
    // FPAR assuming full leaf cover for all vegetation
    public double lai_leafon_layer;
    // LAI for current layer in canopy (cohort/individual mode; see function
    // fpar)
    public double gp_leafon;
    // non-water-stressed canopy conductance on FPC basis (mm/s)
    public double demand;
    // transpirative demand on FPC basis (mm/day)
    public double demand_leafon;
    // transpirative demand assuming full leaf cover on FPC basis (mm/day)
    public double supply;
    // supply function of AET, FPC basis (mm/day)
    public double supply_leafon;
    // supply function of AET assuming full leaf cover, FPC basis (mm/day)
    public double intercep;
    // interception associated with this individual today (patch basis)

    // Monthly sums (converted to means) maintained by function canopy_exchange

    public double phen_mean;
    // accumulated mean fraction of potential leaf cover

    // Means for driving parameters of photosynthesis required for "individual"
    // demand
    // mode (see canexch.cpp)

    public double  temp_wstress;      // temperature (deg C)
    public double  par_wstress;       // PAR (J/m2/day)
    public double  daylength_wstress; // daylength (h)
    public double  co2_wstress;       // CO2 (ppmv)
    public int     nday_wstress;      // number of water-stress days for month
    public boolean ifwstress;         // whether individual subject to water stress
                                      // today

    // / leaf nitrogen that is photosyntetic active
    public double  nactive;
    // / Nitrogen extinction scalar
    /**
     * Scalar to account for leaf nitrogen not following the optimal light
     * extinction, but is shallower.
     */
    public double  nextin;
    // / long-term storage of labile nitrogen
    public double  nstore_longterm;
    // / storage of labile nitrogen
    public double  nstore_labile;
    // / long-term storage of labile nitrogen saved on first day of land use
    // change year
    public double  nstore_longterm_luc;
    // / storage of labile nitrogen saved on first day of land use change year
    public double  nstore_labile_luc;
    // / daily total nitrogen demand
    public double  ndemand;
    // / fraction of individual nitrogen demand available for uptake
    public double  fnuptake;
    // / annual nitrogen uptake
    public double  anuptake;
    // / maximum size of nitrogen storage
    public double  max_n_storage;
    // / scales annual npp to maximum nitrogen storage
    public double  scale_n_storage;
    // / annual nitrogen limitation on vmax
    public double  avmaxnlim;
    // / annual optimal leaf C:N ratio
    public double  cton_leaf_aopt;
    // / annual average leaf C:N ratio
    public double  cton_leaf_aavr;
    // / plant mobile nitrogen status
    public double  cton_status;
    // / total carbon in compartments before growth
    public double  cmass_veg;
    // / total nitrogen in compartments before growth
    public double  nmass_veg;
    // / whether individual subject to nitrogen stress
    public boolean nstress;
    // / daily leaf nitrogen demand calculated from Vmax (kgN/m2)
    public double  leafndemand;
    // / daily root nitrogen demand based on leafndemand
    public double  rootndemand;
    // / daily sap wood nitrogen demand based on leafndemand
    public double  sapndemand;
    // / daily labile nitrogen demand based on npp
    public double  storendemand;
    // / daily harvestable organ nitrogen demand
    public double  hondemand;
    // / leaf fraction of total nitrogen demand
    public double  leaffndemand;
    // / root fraction of total nitrogen demand
    public double  rootfndemand;
    // / sap fraction of total nitrogen demand
    public double  sapfndemand;
    // / store fraction of total nitrogen demand
    public double  storefndemand;
    // / daily leaf nitrogen demand over possible uptake (storage demand)
    public double  leafndemand_store;
    // / daily root nitrogen demand over possible uptake (storage demand)
    public double  rootndemand_store;

    public double daily_cmass_leafloss;
    public double daily_nmass_leafloss;
    public double daily_cmass_rootloss;
    public double daily_nmass_rootloss;

    // / Number of days with non-Utils.Utils.negligible phenology this month
    public int nday_leafon;

    public boolean alive;

    public double dnpp;

    public CropIndiv cropindiv;

    // guess2008 - whether this individual is truly alive. Set to false for
    // first year
    // after the Individual object is created, then true.

    public Individual(PFT p, Vegetation v) {
        // DataRecorder.get().info("Created individual");

        this.pft = p;
        this.vegetation = v;

        anpp = 0.0;
        fpc = 0.0;
        densindiv = 0.0;
        cmass_leaf = 0.0;
        cmass_root = 0.0;
        cmass_sap = 0.0;
        cmass_heart = 0.0;
        cmass_debt = 0.0;
        wscal = 1.0;
        phen = 0.0;
        aphen = 0.0;
        deltafpc = 0.0;
        fpar_wstress = 0.0;
        assim = 0.0;

        // guess2008 - additional initialisation
        age = 0.0;
        fpar = 0.0;
        aphen_raingreen = 0;
        demand = 0.0;
        supply = 0.0;
        intercep = 0.0;
        phen_mean = 0.0;
        temp_wstress = 0.0;
        par_wstress = 0.0;
        daylength_wstress = 0.0;
        co2_wstress = 0.0;
        nday_wstress = 0;
        ifwstress = false;
        lai = 0.0;
        lai_layer = 0.0;
        lai_indiv = 0.0;
        alive = false;

        int m;
        for (m = 0; m < 12; m++) {
            mnpp[m] = mlai[m] = mgpp[m] = mra[m] = 0.0;
        }

    }

    @Override
    public IConfiguration getConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

    // / Total carbon wood biomass
    public double cmass_wood() {
        return cmass_sap + cmass_heart - cmass_debt;
    }

    // / Total nitrogen wood biomass
    public double nmass_wood() {
        return nmass_sap + nmass_heart;
    }

    public double cton_sap() {

        Stand stand = vegetation.patch.stand;

        if (pft.lifeform == LifeForm.TREE) {
            if (stand.ifnlim_stand()) {
                if (!Utils.negligible(cmass_sap) && !Utils.negligible(nmass_sap))
                    return cmass_sap / nmass_sap;
                else
                    return pft.cton_sap_max;
            } else {
                return pft.cton_sap_avr;
            }
        } else {
            return 1.0;
        }
    }

    public PatchPFT getPatchPFT() {
        return vegetation.patch.pft.get(pft.id);
    }

    public boolean growingseason() {
        try {
            return getPatchPFT().cropphen.growingseason;
        } catch (Exception e) {
            return true;
        }

    }

    public double lai_indiv_today() {
        if (pft.phenology == Phenology.CROPGREEN) {

            if (getPatchPFT().cropphen.growingseason)
                return lai_indiv_daily;
            else
                return 0.0;
        } else
            return lai_indiv * phen;
    }

    public CropIndiv getCropIndiv() {
        return cropindiv;
    }

    public void setCropIndiv(CropIndiv cropindiv) {
        this.cropindiv = cropindiv;
    }

    // / Checks C mass and zeroes any negative value, balancing by adding to npp
    // and reducing respiration
    public double check_C_mass() {

        if (pft.landcover != LandcoverType.CROP)
            return 0;

        double negative_cmass = 0.0;

        if (cropindiv.grs_cmass_leaf < 0.0) {
            negative_cmass -= cropindiv.grs_cmass_leaf;
            cropindiv.ycmass_leaf -= cropindiv.grs_cmass_leaf;
            cropindiv.grs_cmass_plant -= cropindiv.grs_cmass_leaf;
            cropindiv.grs_cmass_leaf = 0.0;
        }
        if (cropindiv.grs_cmass_root < 0.0) {
            negative_cmass -= cropindiv.grs_cmass_root;
            cropindiv.ycmass_root -= cropindiv.grs_cmass_root;
            cropindiv.grs_cmass_plant -= cropindiv.grs_cmass_root;
            cropindiv.grs_cmass_root = 0.0;
        }
        if (cropindiv.grs_cmass_ho < 0.0) {
            negative_cmass -= cropindiv.grs_cmass_ho;
            cropindiv.ycmass_ho -= cropindiv.grs_cmass_ho;
            cropindiv.grs_cmass_plant -= cropindiv.grs_cmass_ho;
            cropindiv.grs_cmass_ho = 0.0;
        }
        if (cropindiv.grs_cmass_agpool < 0.0) {
            negative_cmass -= cropindiv.grs_cmass_agpool;
            cropindiv.ycmass_agpool -= cropindiv.grs_cmass_agpool;
            cropindiv.grs_cmass_plant -= cropindiv.grs_cmass_agpool;
            cropindiv.grs_cmass_agpool = 0.0;
        }
        if (cropindiv.grs_cmass_dead_leaf < 0.0) {
            negative_cmass -= cropindiv.grs_cmass_dead_leaf;
            cropindiv.ycmass_dead_leaf -= cropindiv.grs_cmass_dead_leaf;
            cropindiv.grs_cmass_plant -= cropindiv.grs_cmass_dead_leaf;
            cropindiv.grs_cmass_dead_leaf = 0.0;
        }
        if (cropindiv.grs_cmass_stem < 0.0) {
            negative_cmass -= cropindiv.grs_cmass_stem;
            cropindiv.ycmass_stem -= cropindiv.grs_cmass_stem;
            cropindiv.grs_cmass_plant -= cropindiv.grs_cmass_stem;
            cropindiv.grs_cmass_stem = 0.0;
        }

        if (negative_cmass > 1.0e-14) {
            anpp += negative_cmass;
            report_flux(PerPFTFluxType.NPP, negative_cmass);
            report_flux(PerPFTFluxType.RA, -negative_cmass);
        }

        return negative_cmass;
    }

    public boolean istruecrop_or_intercropgrass() {
        return (pft.landcover == LandcoverType.CROP
                && (pft.phenology == Phenology.CROPGREEN || cropindiv.isintercropgrass));
    }

    public void report_flux(PerPFTFluxType flux_type, double value) {
        if (alive || istruecrop_or_intercropgrass()) {
            vegetation.patch.fluxes.report_flux(flux_type, pft.id, value);
        }
    }

    public void report_flux(PerPatchFluxType flux_type, double value) {
        if (alive || istruecrop_or_intercropgrass()) {
            vegetation.patch.fluxes.report_flux(flux_type, value);
        }
    }

    double ncont() {
        return ncont(1.0, false);
    }

    double ncont(double scale_indiv, boolean luc) {

        double ncont = 0.0;

        if (luc) {

            ncont += nmass_leaf - nmass_leaf_luc * (1.0 - scale_indiv);
            ncont += nmass_root - nmass_root_luc * (1.0 - scale_indiv);
            ncont += nmass_sap - nmass_sap_luc * (1.0 - scale_indiv);
            ncont += nmass_heart - nmass_heart_luc * (1.0 - scale_indiv);
            ncont += nstore_longterm - nstore_longterm_luc * (1.0 - scale_indiv);
            ncont += nstore_labile - nstore_labile_luc * (1.0 - scale_indiv);
        } else {
            ncont += nmass_leaf * scale_indiv;
            ncont += nmass_root * scale_indiv;
            ncont += nmass_sap * scale_indiv;
            ncont += nmass_heart * scale_indiv;
            ncont += nstore_longterm * scale_indiv;
            ncont += nstore_labile * scale_indiv;
        }

        if (pft.landcover == LandcoverType.CROP) {

            if (luc) {
                ncont += cropindiv.nmass_ho - cropindiv.nmass_ho_luc * (1.0 - scale_indiv);
                ncont += cropindiv.nmass_agpool - cropindiv.nmass_agpool_luc * (1.0 - scale_indiv);
                ncont += cropindiv.nmass_dead_leaf - cropindiv.nmass_dead_leaf_luc * (1.0 - scale_indiv);
            } else {
                ncont += cropindiv.nmass_ho * scale_indiv;
                ncont += cropindiv.nmass_agpool * scale_indiv;
                ncont += cropindiv.nmass_dead_leaf * scale_indiv;
            }
        }

        return ncont;
    }

    // / Checks N mass and zeroes any negative value, balancing by reducing N
    // mass of other organs and (if needed) reducing anflux_landuse_change
    public double check_N_mass() {

        if (pft.landcover != LandcoverType.CROP && pft.landcover != LandcoverType.PASTURE)
            return 0;

        double negative_nmass = 0.0;

        if (nmass_leaf < 0.0) {
            negative_nmass -= nmass_leaf;
            if (cropindiv != null)
                cropindiv.ynmass_leaf -= nmass_leaf;
            nmass_leaf = 0.0;
        }
        if (nmass_root < 0.0) {
            negative_nmass -= nmass_root;
            if (cropindiv != null)
                cropindiv.ynmass_root -= nmass_root;
            nmass_root = 0.0;
        }
        if (cropindiv != null) {
            if (cropindiv.nmass_ho < 0.0) {
                negative_nmass -= cropindiv.nmass_ho;
                cropindiv.ynmass_ho -= cropindiv.nmass_ho;
                cropindiv.nmass_ho = 0.0;
            }
            if (cropindiv.nmass_agpool < 0.0) {
                negative_nmass -= cropindiv.nmass_agpool;
                cropindiv.ynmass_agpool -= cropindiv.nmass_agpool;
                cropindiv.nmass_agpool = 0.0;
            }
            if (cropindiv.nmass_dead_leaf < 0.0) {
                negative_nmass -= cropindiv.nmass_dead_leaf;
                cropindiv.ynmass_dead_leaf -= cropindiv.nmass_dead_leaf;
                cropindiv.nmass_dead_leaf = 0.0;
            }
        }
        if (nstore_labile < 0.0) {
            negative_nmass -= nstore_labile;
            nstore_labile = 0.0;
        }
        if (nstore_longterm < 0.0) {
            negative_nmass -= nstore_longterm;
            nstore_longterm = 0.0;
        }

        if (negative_nmass > 1.0e-14) {
            double pos_nmass = ncont();
            if (pos_nmass > negative_nmass) {
                nmass_leaf -= negative_nmass * nmass_leaf / pos_nmass;
                nmass_root -= negative_nmass * nmass_root / pos_nmass;
                if (cropindiv != null) {
                    cropindiv.nmass_ho -= negative_nmass * cropindiv.nmass_ho / pos_nmass;
                    cropindiv.nmass_agpool -= negative_nmass * cropindiv.nmass_agpool / pos_nmass;
                    cropindiv.nmass_dead_leaf -= negative_nmass * cropindiv.nmass_dead_leaf / pos_nmass;
                }
            } else {
                vegetation.patch.stand.getGridcell().anflux_landuse_change -= (negative_nmass - pos_nmass)
                        * vegetation.patch.stand.get_gridcell_fraction();
                nmass_leaf = 0.0;
                nmass_leaf = 0.0;
                if (cropindiv != null) {
                    cropindiv.nmass_ho = 0.0;
                    cropindiv.nmass_agpool = 0.0;
                    cropindiv.nmass_dead_leaf = 0.0;
                }
            }
        }

        return negative_nmass;
    }

    public double cmass_leaf_today() {
        if (istruecrop_or_intercropgrass()) {
            if (getPatchPFT().cropphen.growingseason)
                return cropindiv.grs_cmass_leaf;
            else
                return 0.0;
        } else
            return cmass_leaf * phen;
    }

    public double cmass_root_today() {
        if (istruecrop_or_intercropgrass()) {

            if (getPatchPFT().cropphen.growingseason)
                return cropindiv.grs_cmass_root;
            else
                return 0.0;
        } else
            return cmass_root * phen;
    }

    public double cton_leaf(boolean use_phen) {
        Stand stand = vegetation.patch.stand;

        if (stand.ifnlim_stand()) {

            if (stand.is_true_crop_stand() && !Utils.negligible(cmass_leaf_today())
                    && !Utils.negligible(nmass_leaf)) { // Detta
                                                        // kan
                                                        // möjligen
                                                        // tas
                                                        // bort
                return cmass_leaf_today() / nmass_leaf;
            } else if (!stand.is_true_crop_stand() && !Utils.negligible(cmass_leaf)
                    && !Utils.negligible(nmass_leaf)) {
                if (use_phen) {
                    if (!Utils.negligible(phen)) {
                        return cmass_leaf_today() / nmass_leaf;
                    } else {
                        return pft.cton_leaf_avr;
                    }
                } else {
                    return cmass_leaf / nmass_leaf;
                }
            } else {
                return pft.cton_leaf_max;
            }
        } else {
            return pft.cton_leaf_avr;
        }
    }

    public double cton_root(boolean use_phen) {
        Stand stand = vegetation.patch.stand;

        if (stand.ifnlim_stand()) {
            if (!Utils.negligible(cmass_root) && !Utils.negligible(nmass_root)) {
                if (use_phen) {
                    if (!Utils.negligible(cmass_root_today())) {
                        return cmass_root_today() / nmass_root;
                    } else {
                        return pft.cton_root_avr;
                    }
                } else {
                    return cmass_root / nmass_root;
                }
            } else {
                return pft.cton_root_max;
            }
        } else {
            return pft.cton_root_avr;
        }
    }

    public boolean has_daily_turnover() {
        boolean harvest_grsc = true; // TODO: Make configurable
        if (harvest_grsc) {
            return istruecrop_or_intercropgrass();
        } else {
            return false;
        }
    }

    public double wscal_mean() {
        return getPatchPFT().wscal_mean;
    }

    public boolean continous_grass() {
        Stand stand = vegetation.patch.stand;

        if (pft.landcover == LandcoverType.CROP) {

            StandType st = stand._configuration.getSTs().get(stand.stid);
            boolean sowing_restriction = true;

            for (int i = 0; i < st.rotation.ncrops; i++) {
                int pftid = stand._configuration.getPFTByName(st.management.get(i).pftname).id;
                if (!stand.getGridcell().pft.get(pftid).sowing_restriction)
                    sowing_restriction = false;
            }

            if (cropindiv.isintercropgrass && sowing_restriction)
                return true;
            else
                return false;
        } else
            return false;
    }

    public boolean is_turnover_day() {
        if (getPatchPFT().cropphen != null && getPatchPFT().cropphen.growingseason) {

            Climate climate = vegetation.patch.getClimate();

            if (vegetation.patch.stand._configuration.getSchedule().julianDay() == climate.testday_prec)
                return true;
            else
                return false;
        } else
            return false;
    }

    public void kill() {
        kill(false);
    }

    public void kill(boolean harvest) {
        // TODO: Make configurable
        boolean ifslowharvestpool = true;

        PatchPFT ppft = getPatchPFT();

        double charvest_flux = 0.0;
        double charvested_products_slow = 0.0;

        double nharvest_flux = 0.0;
        double nharvested_products_slow = 0.0;

        double harv_eff = 0.0;
        double harvest_slow_frac = 0.0;
        double res_outtake = 0.0;

        // The function always deals with harvest, but the harvest
        // fractions are zero when there is no harvest.
        if (harvest) {
            harv_eff = pft.harv_eff;

            if (ifslowharvestpool) {
                harvest_slow_frac = pft.harvest_slow_frac;
            }

            res_outtake = pft.res_outtake;
        }

        // C doesn't return to litter/harvest if the Individual isn't alive
        if (alive || istruecrop_or_intercropgrass()) {

            // For leaf and root, catches small, negative values too

            // Leaf: remove residue outtake and send the rest to litter
            if (has_daily_turnover() && cropindiv != null) {

                if (pft.lifeform == LifeForm.GRASS && pft.phenology != Phenology.CROPGREEN) {
                    charvest_flux += cropindiv.grs_cmass_leaf * harv_eff;
                    cropindiv.grs_cmass_leaf *= (1 - harv_eff);
                }

                ppft.litter_leaf += cropindiv.grs_cmass_leaf * (1 - res_outtake);
                charvest_flux += cropindiv.grs_cmass_leaf * res_outtake;
            } else {

                if (pft.lifeform == LifeForm.GRASS && pft.phenology != Phenology.CROPGREEN) {
                    charvest_flux += cmass_leaf * harv_eff;
                    cmass_leaf *= (1 - harv_eff);
                }
                ppft.litter_leaf += cmass_leaf * (1 - res_outtake);
                charvest_flux += cmass_leaf * res_outtake;
            }
            // Root: all goes to litter
            if (has_daily_turnover() && cropindiv != null)
                ppft.litter_root += cropindiv.grs_cmass_root;
            else
                ppft.litter_root += cmass_root;

            if (pft.landcover == LandcoverType.CROP) {

                if (has_daily_turnover()) {

                    charvest_flux += cropindiv.grs_cmass_ho * harv_eff;
                    cropindiv.grs_cmass_ho *= (1 - harv_eff);

                    if (pft.aboveground_ho) {
                        ppft.litter_leaf += cropindiv.grs_cmass_ho * (1 - res_outtake);
                        charvest_flux += cropindiv.grs_cmass_ho * res_outtake;
                    } else {
                        ppft.litter_root += cropindiv.grs_cmass_ho;
                    }
                    ppft.litter_leaf += cropindiv.grs_cmass_agpool * (1 - res_outtake);
                    charvest_flux += cropindiv.grs_cmass_agpool * res_outtake;

                    ppft.litter_leaf += cropindiv.grs_cmass_dead_leaf * (1 - res_outtake);
                    charvest_flux += cropindiv.grs_cmass_dead_leaf * res_outtake;

                    ppft.litter_leaf += cropindiv.grs_cmass_stem * (1 - res_outtake);
                    charvest_flux += cropindiv.grs_cmass_stem * res_outtake;
                } else {

                    charvest_flux += cropindiv.cmass_ho * harv_eff;
                    cropindiv.cmass_ho *= (1 - harv_eff);

                    if (pft.aboveground_ho) {
                        ppft.litter_leaf += cropindiv.cmass_ho * (1 - res_outtake);
                        charvest_flux += cropindiv.cmass_ho * res_outtake;
                    } else {
                        ppft.litter_root += cropindiv.cmass_ho;
                    }
                    ppft.litter_leaf += cropindiv.cmass_agpool * (1 - res_outtake);
                    charvest_flux += cropindiv.cmass_agpool * res_outtake;
                }
            }

            // Deal with the wood biomass and carbon debt for trees
            if (pft.lifeform == LifeForm.TREE) {

                // debt smaller than existing wood biomass
                if (cmass_debt <= cmass_sap + cmass_heart) {

                    // before partitioning the biomass into litter and harvest,
                    // first get rid of the debt so we're left with only
                    // sap and heart
                    double to_partition_sap = 0.0;
                    double to_partition_heart = 0.0;

                    if (cmass_heart >= cmass_debt) {
                        to_partition_sap = cmass_sap;
                        to_partition_heart = cmass_heart - cmass_debt;
                    } else {
                        to_partition_sap = cmass_sap + cmass_heart - cmass_debt;
                    }

                    double clitter_sap = 0, clitter_heart = 0, cwood_harvest = 0;

                    partition_wood_biomass(to_partition_sap, to_partition_heart, harv_eff, harvest_slow_frac, res_outtake, clitter_sap, clitter_heart, cwood_harvest, charvested_products_slow);

                    ppft.litter_sap += clitter_sap;
                    ppft.litter_heart += clitter_heart;

                    charvest_flux += cwood_harvest;
                }
                // debt larger than existing wood biomass
                else {
                    double debt_excess = cmass_debt - (cmass_sap + cmass_heart);
                    report_flux(PerPFTFluxType.NPP, debt_excess);
                    report_flux(PerPFTFluxType.RA, -debt_excess);
                }
            }
        }

        // Nitrogen always return to soil litter
        if (pft.lifeform == LifeForm.TREE) {

            double nlitter_sap = 0, nlitter_heart = 0, nwood_harvest = 0;

            // Transfer nitrogen storage to sapwood nitrogen litter/harvest
            partition_wood_biomass(nmass_sap
                    + nstore(), nmass_heart, harv_eff, harvest_slow_frac, res_outtake, nlitter_sap, nlitter_heart, nwood_harvest, nharvested_products_slow);

            ppft.nmass_litter_sap += nlitter_sap;
            ppft.nmass_litter_heart += nlitter_heart;

            nharvest_flux += nwood_harvest;
        } else {
            // Transfer nitrogen storage to root nitrogen litter
            ppft.nmass_litter_root += nstore();
        }

        // Leaf: remove residue outtake and send the rest to litter
        ppft.nmass_litter_leaf += nmass_leaf * (1 - res_outtake);
        nharvest_flux += nmass_leaf * res_outtake;

        // Root: all goes to litter
        ppft.nmass_litter_root += nmass_root;

        if (pft.landcover == LandcoverType.CROP) {
            if (pft.aboveground_ho) {
                ppft.nmass_litter_leaf += cropindiv.nmass_ho * (1 - res_outtake);
                nharvest_flux += cropindiv.nmass_ho * res_outtake;
            } else
                ppft.litter_root += cropindiv.nmass_ho;
            ppft.nmass_litter_leaf += cropindiv.nmass_agpool * (1 - res_outtake);
            nharvest_flux += cropindiv.nmass_agpool * res_outtake;
            ppft.nmass_litter_leaf += cropindiv.nmass_dead_leaf * (1 - res_outtake);
            nharvest_flux += cropindiv.nmass_dead_leaf * res_outtake;
        }

        // Report harvest fluxes
        report_flux(PerPatchFluxType.HARVESTC, charvest_flux);
        report_flux(PerPatchFluxType.HARVESTN, nharvest_flux);

        // Add to biomass depositories for long-lived products
        ppft.harvested_products_slow += charvested_products_slow;
        ppft.harvested_products_slow_nmass += nharvested_products_slow;
    }

    // / Total storage of nitrogen
    public double nstore() {
        return nstore_longterm + nstore_labile;
    }

    /// Help function for kill(), partitions wood biomass into litter and harvest
    /** 
     *  Wood biomass (either C or N) is partitioned into litter pools and
     *  harvest, according to PFT specific harvest fractions.
     *
     *  Biomass is sent in as sap and heart, any debt should already have been
     *  subtracted from these before calling this function.
     *
     *  \param mass_sap          Sapwood
     *  \param mass_heart        Heartwood
     *  \param harv_eff          Harvest efficiency (fraction of biomass harvested)
     *  \param harvest_slow_frac Fraction of harvested products that goes into slow depository
     *  \param res_outtake       Fraction of residue outtake at harvest
     *  \param litter_sap        Biomass going to sapwood litter pool
     *  \param litter_heart      Biomass going to heartwood litter pool
     *  \param fast_harvest      Biomass going to harvest flux
     *  \param slow_harvest      Biomass going to slow depository
     */
    void partition_wood_biomass(double mass_sap, double mass_heart, double harv_eff, double harvest_slow_frac, double res_outtake, double litter_sap, double litter_heart, double fast_harvest, double slow_harvest) {

        double sap_left = mass_sap;
        double heart_left = mass_heart;

        // Remove harvest
        double total_wood_harvest = harv_eff * (sap_left + heart_left);

        sap_left *= 1 - harv_eff;
        heart_left *= 1 - harv_eff;

        // Partition wood harvest into slow and fast
        slow_harvest = total_wood_harvest * harvest_slow_frac;
        fast_harvest = total_wood_harvest * (1 - harvest_slow_frac);

        // Remove residue outtake
        fast_harvest += res_outtake * (sap_left + heart_left);

        sap_left *= 1 - res_outtake;
        heart_left *= 1 - res_outtake;

        // The rest goes to litter
        litter_sap = sap_left;
        litter_heart = heart_left;
    }

}
