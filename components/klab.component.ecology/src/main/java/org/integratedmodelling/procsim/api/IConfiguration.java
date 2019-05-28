package org.integratedmodelling.procsim.api;

import java.util.List;

import org.integratedmodelling.ecology.biomass.lpjguess.LandcoverType;
import org.integratedmodelling.ecology.biomass.lpjguess.PFT;
import org.integratedmodelling.ecology.biomass.lpjguess.StandType;
import org.integratedmodelling.klab.exceptions.KlabException;


/**
 * TODO refactor all the silly names when the rest of the code is aligned.
 *
 * @author Ferd
 *
 */
public interface IConfiguration {

    // guess2008 - new default SOM values
    // number of soil layers modelled
    public final int NSOILLAYER = 2;

    // soil upper layer depth (mm)
    public final double SOILDEPTH_UPPER = 500.0;

    // soil lower layer depth (mm)
    public final double SOILDEPTH_LOWER = 1000.0;

    // year at which to calculate equilibrium soil carbon
    public final int SOLVESOM_END = 400;

    // year at which to begin documenting means for calculation of equilibrium
    // soil carbon
    public final int SOLVESOM_BEGIN = 350;

    // Lambert-Beer extinction coefficient (Prentice et al 1993; Monsi & Saeki
    // 1953)
    public final double LAMBERTBEER_K = 0.50;

    // number of years to average growth efficiency over in function mortality
    public final int NYEARGREFF = 5;

    // day at which to start counting GDD's and leaf-on days for summergreen
    // phenology
    // in N hemisphere (January 15)
    public final int COLDEST_DAY_NHEMISPHERE = 14;

    // day at which to start counting GDD's and leaf-on days for summergreen
    // phenology
    // in S hemisphere (July 15)
    public final int COLDEST_DAY_SHEMISPHERE = 195;

    // maximum number of age classes in age structure plots produced by function
    // outannual
    public final int OUTPUT_MAXAGECLASS = 2000;

    // guess2008 - this is now a global, constant variable Previously, we had
    // duplicate definitions in
    // both canexch.cpp and soilwater.cpp
    public final double PRIESTLEY_TAYLOR = 1.32;

    /**
     * Get the initialized schedule to be run.
     *
     * @return schedule
     */
    ISchedule getSchedule();

    /**
     * Get all configured plant functional types (PFTs)
     *
     * @return PFTs
     */
    List<PFT> getPFTs();

    /**
     * @return true if water uptake is species specific
     */
    boolean isIfspeciesspecificwateruptake();

    /**
     *
     * @return vegetation mode (population, cohort or individual)
     */
    VegetationMode getVegmode();

    // boolean ifbgestab = true; // whether background establishment enabled
    // (individual, cohort mode)
    // boolean ifsme = true;
    // // whether spatial mass effect enabled for establishment (individual,
    // cohort mode)
    // boolean ifstochestab = true; // whether establishment stochastic
    // (individual, cohort mode)
    // boolean ifstochmort = true; // whether mortality stochastic (individual,
    // cohort mode)
    // boolean iffire = true; // whether fire enabled
    // boolean ifdisturb = true;
    // // whether "generic" patch-destroying disturbance enabled (individual,
    // cohort mode)
    // boolean ifcalcsla = true; // whether SLA calculated from leaf longevity
    // (alt: prescribed)
    // int estinterval = 5; // establishment interval in cohort mode (years)
    // double distinterval = 100;
    // // generic patch-destroying disturbance interval (individual, cohort
    // mode)
    // boolean iffast = false; // (??)
    // boolean ifcdebt = true;
    //
    // // guess2008 - new inputs from the .ins file
    // boolean ifsmoothgreffmort = true; // smooth growth efficiency mortality
    // boolean ifdroughtlimitedestab = false; // whether establishment affected
    // by growing season drought
    // boolean ifrainonwetdaysonly = true; // rain on wet days only (1, true),
    // or a little every day (0, false);
    // boolean ifspeciesspecificwateruptake = false; //

    // public int nst_lc[] = null;

    /**
     *
     * @return whether NPP calculations are performed daily
     */
    boolean isNPPDaily();

    /**
     *
     * @return whether soil decomposition calculations are performed daily
     *         (monthly if false)
     */
    boolean isDecompositionDaily();

    /**
     *
     * @return enablement of background establishment
     */
    boolean isBackgroundEstablishmentEnabled();

    boolean isSpatialMassEffectEnabled();

    boolean isEstablishmentStochastic();

    boolean isMortalityStochastic();

    boolean isFireEnabled();

    boolean isDisturbanceEnabled();

    /**
     * Specific Leaf Area (SLA) is the ratio of one-sided leaf surface area to
     * leaf carbon mass (k2 kg C-1)
     *
     * @return whether SLA is calculated from leaf longevity re: Reich et al.
     *         1997 (false = prescribed)
     */
    boolean isSLAComputed();

    int getEstablishmentInterval();

    double getDisturbanceInterval();

    boolean isIffast();

    boolean isIfcdebt();

    boolean isGrowthEfficiencySmoothed();

    boolean isEstablishmentDroughtLimited();

    boolean isIfrainonwetdaysonly();

    enum DemandPatchType {

        /**
         * DEMAND_PATCH = a single demand calculated for the entire patch / grid
         * cell
         */
        DEMAND_PATCH, // default in LPJ-GUESS

        /**
         * DEMAND_INDIV = a separate demand calculated for each individual (as
         * in LPJF)
         */
        DEMAND_INDIVIDUAL
    }

    DemandPatchType getDemandPatchType();

    enum AETMonteithType {

        /**
         * AET_MONTEITH_EXPONENTIAL = exponential parameterisation (Monteith
         * 1995)
         */
        EXPONENTIAL,

        /**
         * AET_MONTEITH_HYPERBOLIC = hyperbolic parameterisation (Huntington &
         * Monteith 1998)
         */
        HYPERBOLIC // default in LPJ-guess
    }

    AETMonteithType getAETMonteithType();

    enum WaterUptakeType {

        /**
         * WR_WCONT = uptake rate coupled to water content and vertical root
         * distribution (as in earlier versions of LPJ-GUESS and LPJF)
         */
        WCONT,

        /**
         * WR_ROOTDIST = uptake rate independent of water content (to wilting
         * point) but with fractional uptake from different layers according to
         * prescribed root distribution
         */
        ROOTDIST, // default in LPJ-GUESS

        /**
         * WR_SMART = uptake rate independent of water content (to wilting
         * point), fractional uptake from different layers according to layer
         * water content for trees, according to prescribed root distribution
         * for grasses
         */
        SMART,

        /**
         * WR_SPECIESSPECIFIC = uptake rate is species specific, with more
         * drought tolerance species = (lower species_drought_tolerance values)
         * having greater relative uptake rates.
         */
        SPECIES_SPECIFIC
    }

    WaterUptakeType getWaterUptakeType();

    // Life form class for PFTs (trees, grasses)
    public enum LifeForm {
        NOLIFEFORM,
        TREE,
        GRASS,
        CROP;

        public int getValue() {
            return this.ordinal();
        }

        public static LifeForm forValue(int value) {
            return values()[value];
        }

        public static LifeForm get(String s) {

            if (s.equalsIgnoreCase("CROP")) {
                return CROP;
            } else if (s.equalsIgnoreCase("GRASS")) {
                return GRASS;
            } else if (s.equalsIgnoreCase("NOLIFEFORM")) {
                return NOLIFEFORM;
            } else if (s.equalsIgnoreCase("TREE")) {
                return TREE;
            }

            throw new KlabException("wrong lifeform name: " + s);
        }
    }

    // Phenology class for PFTs
    public enum Phenology {
        NOPHENOLOGY,
        EVERGREEN,
        RAINGREEN,
        SUMMERGREEN,
        CROPGREEN,
        ANY;

        public int getValue() {
            return this.ordinal();
        }

        public static Phenology forValue(int value) {
            return values()[value];
        }

        public static Phenology get(String s) {

            if (s.equalsIgnoreCase("NOPHENOLOGY")) {
                return NOPHENOLOGY;
            } else if (s.equalsIgnoreCase("EVERGREEN")) {
                return EVERGREEN;
            } else if (s.equalsIgnoreCase("RAINGREEN")) {
                return RAINGREEN;
            } else if (s.equalsIgnoreCase("SUMMERGREEN")) {
                return SUMMERGREEN;
            } else if (s.equalsIgnoreCase("CROPGREEN")) {
                return CROPGREEN;
            } else if (s.equalsIgnoreCase("ANY")) {
                return ANY;
            }

            throw new KlabException("wrong phenology name: " + s);

        }
    }

    // Biochemical pathway for photosynthesis (C3 or C4)
    public enum PhotosynthesisPathway {
        NOPATHWAY,
        C3,
        C4;

        public int getValue() {
            return this.ordinal();
        }

        public static PhotosynthesisPathway forValue(int value) {
            return values()[value];
        }

        public static PhotosynthesisPathway get(String s) {
            switch (s.toUpperCase()) {
            case "NOPATHWAY":
                return NOPATHWAY;
            case "C3":
                return C3;
            case "C4":
                return C4;
            }
            throw new KlabException("unknown photosynthesis pathway: " + s);
        }
    }

    // Units for insolation driving data (percentage sunshine, net instantaneous
    // downward shortwave radiation flux [W/m2], total [i.e. with no correction
    // for
    // surface albedo] instantaneous downward shortwave radiation flux [W/m2])
    public enum Insolation {
        // SUNSHINE = percentage of full sunshine
        // NETSWRAD = net downward shortwave radiation flux (albedo corrected)
        // (W/m2)
        // SWRAD = total downward shortwave radiation flux (W/m2)
        NOINSOL,
        SUNSHINE,
        NETSWRAD,
        SWRAD;

        public int getValue() {
            return this.ordinal();
        }

        public static Insolation forValue(int value) {
            return values()[value];
        }

        public static Insolation get(String s) {
            switch (s.toUpperCase()) {
            case "NOINSOL":
                return NOINSOL;
            case "SUNSHINE":
                return SUNSHINE;
            case "NETSWRAD":
                return NETSWRAD;
            case "SWRAD":
                return SWRAD;
            }
            throw new KlabException("unknown insolation: " + s);

        }
    }

    /*
     *
     */
    public enum VegetationMode {

        NOVEGMODE,
        INDIVIDUAL,
        COHORT,
        POPULATION;

        public int getValue() {
            return this.ordinal();
        }

        public static VegetationMode forValue(int value) {
            return values()[value];
        }

        public static VegetationMode get(String s) {
            switch (s.toUpperCase()) {
            case "NOVEGMODE":
                return NOVEGMODE;
            case "INDIVIDUAL":
                return INDIVIDUAL;
            case "COHORT":
                return COHORT;
            case "POPULATION":
                return POPULATION;
            }
            throw new KlabException("unknown vegetation mode: " + s);
        }
    }

    int getNpatch();

    void setNpatch(int npatch);

    public Boolean ifintercropgrass = false;

    PFT getPFTByName(String name);

    int getNStandTypes();

    List<StandType> getSTs();

    boolean isNLimitedLC(LandcoverType lc);

    double getNRelocFrac();
}
