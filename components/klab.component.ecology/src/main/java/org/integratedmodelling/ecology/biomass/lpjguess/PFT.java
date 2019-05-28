package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;
import org.integratedmodelling.procsim.api.IConfiguration.PhotosynthesisPathway;

///////////////////////////////////////////////////////////////////////////////////////
//PFT
//Holds static functional parameters for a plant functional type (PFT). There should
//be one Pft object for each potentially occurring PFT. The same Pft object may be
//referenced (via the pft member of the Individual object; see below) by different
//average individuals. Member functions are included for initialising SLA given leaf
//longevity, and for initialising sapling/regen characteristics (required for
//population mode).

public class PFT {

    // id code (should be zero based and sequential, 0...npft-1)
    public int                   id;
    // name of PFT
    public String                name;
    // life form (tree or grass)
    public LifeForm              lifeform;
    // leaf phenology (raingreen, summergreen, evergreen, rain+summergreen)
    public Phenology             phenology;
    // growing degree sum on 5 degree base required for full leaf cover
    public double                phengdd5ramp;
    // water stress threshold for leaf abscission (range 0-1; raingreen PFTs)
    public double                wscal_min;
    // biochemical pathway for photosynthesis (C3 or C4)
    public PhotosynthesisPathway pathway;
    // approximate low temperature limit for photosynthesis (deg C)
    public double                pstemp_min;
    // approximate lower range of temperature optimum for photosynthesis (deg C)
    public double                pstemp_low;
    // approximate upper range of temperature optimum for photosynthesis (deg C)
    public double                pstemp_high;
    // maximum temperature limit for photosynthesis (deg C)
    public double                pstemp_max;
    // non-water-stressed ratio of intercellular to ambient CO2 partial pressure
    public double                lambda_max;
    // vegetation root profile (array containing fraction of roots in each soil
    // layer, [0=upper layer])
    public double[]              rootdist = new double[IConfiguration.NSOILLAYER];
    // canopy conductance component not associated with photosynthesis (mm/s)
    public double                gmin;
    // maximum evapotranspiration rate (mm/day)
    public double                emax;
    // maintenance respiration coefficient (0-1)
    public double                respcoeff;

    // leaf C:N mass ratio
    public double cton_leaf;
    /// minimum leaf C:N mass ratio allowed when nitrogen demand is determined
    public double cton_leaf_min;
    /// maximum leaf C:N mass ratio allowed when nitrogen demand is determined
    public double cton_leaf_max;
    /// average leaf C:N mass ratio (between min and max)
    public double cton_leaf_avr;
    /// average fine root C:N mass ratio (connected cton_leaf_avr)
    public double cton_root_avr;
    /// maximum fine root C:N mass ratio (used when mass is negligible)
    public double cton_root_max;
    /// average sapwood C:N mass ratio (connected cton_leaf_avr)
    public double cton_sap_avr;
    /// maximum sapwood C:N mass ratio (used when mass is negligible)
    public double cton_sap_max;
    /// reference fine root C:N mass ratio
    public double cton_root;
    /// reference sapwood C:N mass ratio
    public double cton_sap;
    /// Maximum nitrogen (NH4+ and NO3- seperatly) uptake per fine root [kgN kgC-1 day-1]
    public double nuptoroot;
    /// coefficient to compensate for vertical distribution of fine root on nitrogen uptake
    public double nupscoeff;
    /// fraction of sapwood (root for herbaceous pfts) that can be used as a nitrogen longterm storage scalar
    public double fnstorage;

    /// Michaelis-Menten kinetic parameters
    /** Half saturation concentration for N uptake [kgN l-1] (Rothstein 2000) */
    public double km_volume;

    // fraction of NPP allocated to reproduction
    public double reprfrac;
    // annual leaf turnover as a proportion of leaf C biomass
    public double turnover_leaf;
    // annual fine root turnover as a proportion of fine root C biomass
    public double turnover_root;
    // annual sapwood turnover as a proportion of sapwood C biomass
    public double turnover_sap;
    // sapwood and heartwood density (kgC/m3)
    public double wooddens;
    // maximum tree crown area (m2)
    public double crownarea_max;
    // constant in allometry equations
    public double k_allom1;
    // constant in allometry equations
    public double k_allom2;
    // constant in allometry equations
    public double k_allom3;
    // constant in allometry equations
    public double k_rp;
    // tree leaf to sapwood area ratio
    public double k_latosa;
    // specific leaf area (m2/kgC)
    public double sla;
    // leaf longevity (years)
    public double leaflong;
    // leaf to root mass ratio under non-water-stressed conditions
    public double ltor_max;
    // litter moisture flammability threshold (fraction of AWC)
    public double litterme;
    // fire resistance (0-1)
    public double fireresist;
    // minimum forest-floor PAR level for growth (grasses) or establishment
    // (trees)
    // (J/m2/day) (individual and cohort modes)
    public double parff_min;
    // parameter capturing non-linearity in recruitment rate relative to
    // understorey growing conditions for trees (Fulton 1991) (individual and
    // cohort modes)
    public double alphar;
    // maximum sapling establishment rate (saplings/m2/year) (individual and
    // cohort
    // modes)
    public double est_max;
    // constant used in calculation of sapling establishment rate when spatial
    // mass effect enabled (individual and cohort modes)
    public double kest_repr;
    // constant affecting amount of background establishment (when enabled)
    // (individual and cohort modes)
    public double kest_bg;
    // constant used in calculation of sapling establishment rate when spatial
    // mass effect disabled (individual and cohort modes)
    public double kest_pres;
    // expected longevity under non-stressed conditions (individual and cohort
    // modes)
    public double longevity;
    // threshold growth efficiency for imposition of growth suppression
    // mortality
    // (kgC/m2 leaf/year) (individual and cohort modes)
    public double greff_min;

    // Bioclimatic limits (all temperatures deg C)

    // minimum 20-year coldest month mean temperature for survival
    public double   tcmin_surv;
    // maximum 20-year coldest month mean temperature for establishment
    public double   tcmax_est;
    // minimum degree day sum on 5 deg C base for establishment
    public double   gdd5min_est;
    // minimum 20-year coldest month mean temperature for establishment
    public double   tcmin_est;
    // minimum warmest month mean temperature for establishment
    public double   twmin_est;
    // continentality parameter for boreal summergreen trees
    public double   twminusc;
    // constant in equation for budburst chilling time requirement (Sykes et al
    // 1996)
    public double   k_chilla;
    // coefficient in equation for budburst chilling time requirement
    public double   k_chillb;
    // exponent in equation for budburst chilling time requirement
    public double   k_chillk;
    // array containing values for GDD0(c) given c=number of chill days (0-366)
    // (Sykes et al 1996, Eqn 1)
    public double[] gdd0 = new double[367];
    // interception coefficient (unitless)
    public double   intc;

    // the amount of N that is applied (kg N m-2)
    public double  N_appfert;
    // 0 - 1 how much of the fertiliser is applied the first date, default 1.
    public double  fertrate[]    = new double[2];
    // dates relative to sowing date
    public int     fertdates[]   = new int[2];
    public double  fert_stages[] = new double[2];
    public Boolean fertilised[]  = new Boolean[2];

    /// development stage
    public double dev_stage;

    public double T_vn_min;
    public double T_vn_opt;
    public double T_vn_max;

    public double T_veg_min;
    public double T_veg_opt;
    public double T_veg_max;

    public double T_rep_min;
    public double T_rep_opt;
    public double T_rep_max;

    public double photo[] = new double[3];

    public double dev_rate_veg;
    public double dev_rate_rep;

    public double a1, b1, c1, d1, a2, b2, c2, d2, a3, b3, c3, d3;
    public double cton_stem_avr;
    public double cton_stem_max;

    /// specifies type of landcover
    /** \see landcovertype */
    public LandcoverType landcover; // specifies type of landcover (0 = URBAN, 1 = CROP, 2 = PASTURE, 3 =
                                    // FOREST, 4 = NATURAL, 5 = PEATLAND); initialized in constructor

    // Crop-related parameters

    /// fraction of residue outtake at harvest
    public double            res_outtake;
    /// harvest efficiency
    public double            harv_eff;
    /// harvest efficiency of public intercrop grass
    public double            harv_eff_ic;
    /// fraction of harvested products that goes public into patchpft.harvested_products_slow
    public double            harvest_slow_frac;
    /// yearly turnover fraction of patchpft.harvested_products_slow (goes to gridcell.acflux_harvest_slow)
    public double            turnover_harv_prod;
    /// whether pft may grow as cover crop
    public boolean           isintercropgrass;
    /// whether sowing date is calculated
    public boolean           ifsdcalc;
    /// whether temperature dependent sowing date is calculated
    public boolean           ifsdtemp;
    /// whether autumn temperature dependent sowing date is calculated
    public boolean           ifsdautumn;
    /// whether spring temperature dependent sowing date is calculated
    public boolean           ifsdspring;
    /// whether precipitation dependent sowing date is calculated
    public boolean           ifsdprec;
    /// upper temperature limit for autumn sowing
    public double            tempautumn;
    /// lower temperature limit for spring sowing
    public double            tempspring;
    /// default sowing date in the northern hemisphere (julian day)
    public int               sdatenh;
    /// default sowing date in the southern hemisphere
    public int               sdatesh;
    /// latest date for harvesting in the northern hemisphere
    public int               hlimitdatenh;
    /// latest date for harvesting in the southern hemisphere
    public int               hlimitdatesh;
    /// default base temperature (°C) for heat unit (hu) calculation
    public double            tb;
    /// early sowing limit for precipitation-limited crops (northern hemisphere); only used in
    /// Crop_sowing_date_prec()
    public int               firstsowdatenh_prec;
    /// early sowing limit for precipitation-limited crops (southern hemisphere); only used in
    /// Crop_sowing_date_prec()
    public int               firstsowdatesh_prec;
    /// temperature under which vernalisation is possible (°C)
    public double            trg;
    /// default number of vernalising days required
    public int               pvd;
    /// sensitivity to the photoperiod effect [0-1]
    public double            psens;
    /// basal photoperiod (h) (pb<ps for longer days plants)
    public double            pb;
    /// saturating photoperiod (h) (ps<pb for shorter days plants)
    public double            ps;
    /// default potential heat units required for crop maturity (degree-days)
    public double            phu;
    /// fraction of growing season (phu) at which senescence starts [0-1]
    public double            fphusen;
    /// type of senescence curve (see Bondeau et al. 2007)
    public Boolean           shapesenescencenorm;
    /// fraction of maximal LAI still present at harvest [0-1]
    public double            flaimaxharvest;
    /// default maximum LAI (only used for public intercrop grass in the case where no pasture is present in
    /// any stand)
    public double            laimax;
    /// whether harvestable organs are above ground
    public boolean           aboveground_ho;
    /// optimum harvest index
    public double            hiopt;
    /// minimum harvest index
    public double            himin;
    /// initial fraction of growing season's npp allocated to roots
    public double            frootstart;
    /// final fraction of growing season's npp allocated to roots
    public double            frootend;
    /// whether sowing dates are read from input file
    public boolean           readsowingdate;
    /// whether harvest dates are read from input file
    public boolean           readharvestdate;
    /// autumn/spring sowing of pft:s with tempautumn = 1
    public ForceAutumnSowing forceautumnsowing;  // 0 = NOFORCING, 1 = AUTUMNSOWING, 2 = SPRINGSOWING
    /// whether N fertilization is read from input file
    public boolean           readNfert;
    /// N limited version of pft
    public boolean           nlim;

    public LeafPhysiognomyType leafphysiognomy;

    /*
     * FV these 4 seem to be unused
     */

    /// aerodynamic conductance (m s-1)
    public double  ga;
    /// isoprene emission capacity (ug C g-1 h-1)
    public double  eps_iso;
    /// whether (1) or not (1) isoprene emissions show a seasonality
    public boolean seas_iso;
    /// monoterpene emission capacity (ug C g-1 h-1)
    public double  eps_mon;
    /// fraction of monoterpene production that goes into storage pool (-)
    public double  storfrac_mon;

    // guess2008 - drought-limited establishment (DLE)

    // Drought tolerance level (0 = very -> 1 = not at all) (unitless)
    public double drought_tolerance;

    // Sapling/regeneration characteristics (used only in population mode):
    // for trees, on sapling individual basis (kgC); for grasses, on stand area
    // basis,
    // kgC/m2

    // FIXME move AnonymousClass to sensible name:
    public static class CDistribution {

        // leaf C biomass
        public double cmass_leaf;

        // fine root C biomass
        public double cmass_root;

        // sapwood C biomass
        public double cmass_sap;

        // heartwood C biomass
        public double cmass_heart;
    }

    public CDistribution regen = new CDistribution();

    public PFT(String s) {
        // Set name
        name = s;

        // Constructor (initialises array gdd0)

        int y;
        for (y = 0; y < 366; y++) {
            gdd0[y] = -1.0; // value<0 signifies "unknown"; see function phenology()
        }

        // guess2008 - DLE
        drought_tolerance = 0.0; // Default, means that the PFT will never be limited by drought.
    }

    public final void init() {
        initsla();

        init_cton_min();

        init_cton_limits();

        init_nupscoeff();

        initregen();
    }

    /// Calculates coefficient to compensate for different vertical distribution of fine root on nitrogen
    /// uptake
    void init_nupscoeff() {

        // Fraction fine root in upper soil layer should have higher possibility for mineralized nitrogen
        // uptake
        // Soil nitrogen profile is considered to have a exponential decline (Franzluebbers et al. 2009)
        // giving
        // an approximate advantage of 2 of having more roots in the upper soil layer
        double upper_adv = 2.0;

        nupscoeff = rootdist[0] * upper_adv + rootdist[1];

    }

    void init_cton_limits() {

        // Fraction between min and max C:N ratio White et al. 2000
        double frac_mintomax = (phenology == Phenology.CROPGREEN) ? 5.0 : 2.78; // Use value also without nlim
                                                                                // ?

        // Fraction between leaf and root C:N ratio
        double frac_leaftoroot = 1.16; // Friend et al. 1997

        // Fraction between leaf and sap wood C:N ratio
        double frac_leaftosap = 6.9; // Friend et al. 1997

        // Max leaf C:N ratio
        cton_leaf_max = cton_leaf_min * frac_mintomax;

        // Average leaf C:N ratio
        cton_leaf_avr = 1.0 / ((1.0 / cton_leaf_min + 1.0 / cton_leaf_max) / 2.0);

        // Average fine root C:N ratio
        cton_root_avr = cton_leaf_avr * frac_leaftoroot;

        // Maximum fine root C:N ratio
        cton_root_max = cton_leaf_min * frac_leaftoroot * frac_mintomax;

        // Average sap C:N ratio
        cton_sap_avr = cton_leaf_avr * frac_leaftosap;

        // Maximum sap C:N ratio
        cton_sap_max = cton_leaf_min * frac_leaftosap * frac_mintomax;

        cton_stem_max = 1.0 / (2.0 * 0.0034); // Maize params
        cton_stem_avr = 1.0 / (2.0 * 0.0068);

        if (lifeform == LifeForm.GRASS)
            respcoeff /= 2.0 * cton_root / (cton_root_avr + cton_leaf_min * frac_leaftoroot);
        else
            respcoeff /= cton_root / (cton_root_avr + cton_leaf_min * frac_leaftoroot) +
                    cton_sap / (cton_sap_avr + cton_leaf_min * frac_leaftosap);
    }

    public final void init_cton_min() {

        // cton_leaf_min has to be supplied in the insfile for crops with N limitation
        if (phenology != IConfiguration.Phenology.CROPGREEN) {
            // Reich et al 1992, Table 1 (includes conversion x500 from mg/g_dry_weight to
            // kgN/kgC)

            if (leafphysiognomy == LeafPhysiognomyType.BROADLEAF)
                cton_leaf_min = 500.0 / Math.pow(10.0, 1.75 - 0.33 * Math.log10(12.0 * leaflong));
            else if (leafphysiognomy == LeafPhysiognomyType.NEEDLELEAF)
                cton_leaf_min = 500.0 / Math.pow(10.0, 1.52 - 0.26 * Math.log10(12.0 * leaflong));
        }
    }

    public final void initsla() {

        // Calculates SLA given leaf longevity
        // Reich et al 1997, Fig 1f (includes conversion x2.0 from
        // m2/kg_dry_weight to
        // m2/kgC)
        sla = 0.2 * Math.exp(6.15 - 0.46 * Math.log(leaflong * 12.0));
    }

    public final void initregen() {

        // Initialises sapling/regen characteristics in population mode
        // following LPJF formulation; see function allometry in growth module.
        // Note: primary PFT parameters, including SLA, must be set before this
        // function is called

        final double PI = 3.14159265;
        final double REGENLAI_TREE = 1.5;
        final double REGENLAI_GRASS = 0.001;
        final double SAPLINGHW = 0.2;

        if (lifeform == LifeForm.TREE) {

            // Tree sapling characteristics
            regen.cmass_leaf = Math.pow(REGENLAI_TREE * k_allom1 * Math.pow(1.0 + SAPLINGHW, k_rp)
                    * Math.pow(4.0 * sla / PI / k_latosa, k_rp * 0.5) / sla, 2.0 / (2.0 - k_rp));

            regen.cmass_sap = wooddens * k_allom2
                    * Math.pow((1.0 + SAPLINGHW)
                            * Math.sqrt(4.0 * regen.cmass_leaf * sla / PI / k_latosa), k_allom3)
                    * regen.cmass_leaf * sla / k_latosa;

            regen.cmass_heart = SAPLINGHW * regen.cmass_sap;
        } else if (lifeform == LifeForm.GRASS || lifeform == LifeForm.CROP) {

            // Grass regeneration characteristics

            regen.cmass_leaf = REGENLAI_GRASS / sla;
        }

        regen.cmass_root = 1.0 / ltor_max * regen.cmass_leaf;
    }
}
