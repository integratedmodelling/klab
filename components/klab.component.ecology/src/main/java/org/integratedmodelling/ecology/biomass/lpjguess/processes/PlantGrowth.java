package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.Climate;
import org.integratedmodelling.ecology.biomass.lpjguess.Fluxes;
import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.PFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.PatchPFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Stand;
import org.integratedmodelling.ecology.biomass.lpjguess.Vegetation;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.VegetationProcess;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;

public class PlantGrowth extends VegetationProcess {

    public PlantGrowth(IConfiguration configuration) {
        super(configuration);
    }

    private static final double APHEN_MAX = 210.0;

    // Maximum number of equivalent days with full leaf cover per growing season
    // for summergreen PFTs

    // /////////////////////////////////////////////////////////////////////////////////////
    // LEAF PHENOLOGY
    // Call function leaf_phenology each simulation day prior to calculation of
    // FPAR, to
    // calculate fractional leaf-out for each PFT and individual.
    // Function leaf_phenology_pft is not intended to be called directly by the
    // framework,

    private double leaf_phenology_pft(PFT pFT, Climate climate, double wscal, double aphen) {

        // DESCRIPTION
        // Calculates leaf phenological status (fractional leaf-out) for a
        // individuals of
        // a given PFT, given current heat sum and length of chilling period
        // (summergreen
        // PFTs) and water stress coefficient (raingreen PFTs)

        // INPUT PARAMETER
        // wscal = water stress coefficient (0-1; 1=maximum stress)
        // aphen = sum of daily fractional leaf cover (equivalent number of days
        // with
        // full leaf cover) so far this growing season

        // OUTPUT PARAMETER
        // phen = fraction of full leaf cover for any individual of this PFT

        boolean raingreen = pFT.phenology == Phenology.RAINGREEN || pFT.phenology == Phenology.ANY;
        boolean summergreen = pFT.phenology == Phenology.SUMMERGREEN || pFT.phenology == Phenology.ANY;

        double phen = 1.0;

        if (summergreen) {

            // Summergreen PFT - phenology based on GDD5 sum

            if (pFT.lifeform == LifeForm.TREE) {

                // Calculate GDD base value for this PFT (if not already known)
                // given
                // current length of chilling period (Sykes et al 1996, Eqn 1)

                if (pFT.gdd0[climate.chilldays] < 0.0) {
                    pFT.gdd0[climate.chilldays] = pFT.k_chilla + pFT.k_chillb
                            * Math.exp(-pFT.k_chillk * climate.chilldays);
                }

                if (climate.gdd5 > pFT.gdd0[climate.chilldays] && aphen < APHEN_MAX) {
                    phen = Math.min(1.0, (climate.gdd5 - pFT.gdd0[climate.chilldays]) / pFT.phengdd5ramp);
                } else {
                    phen = 0.0;
                }

            } else if (pFT.lifeform == LifeForm.GRASS || pFT.lifeform == LifeForm.CROP) {

                // Summergreen grasses have no maximum number of leaf-on days
                // per
                // growing season, and no chilling requirement

                phen = Math.min(1.0, climate.gdd5 / pFT.phengdd5ramp);
            }
        }

        if (raingreen) {

            // Raingreen phenology based on water stress threshold

            if (wscal < pFT.wscal_min) {
                phen = 0.0;
            }
        }

        return phen;
    }

    protected void leaf_phenology(Patch patch) {

        Climate climate = patch.getClimate();

        // DESCRIPTION
        // Updates leaf phenological status (fractional leaf-out) for Patch PFT
        // objects and
        // all individuals in a particular patch.

        // Updated by Ben Smith 2002-07-24 for compatability with "fast" canopy
        // exchange
        // code (phenology assigned to patchpft for all vegetation modes)

        // guess2008
        boolean leafout = true; // CHILLDAYS

        // Obtain reference to Vegetation object
        Vegetation vegetation = patch.vegetation;

        // INDIVIDUAL AND COHORT MODES
        // Calculate phenology for each PFT at this patch

        // Loop through patch-PFTs

        for (PatchPFT pft : patch.pft) {

            pft.phen = leaf_phenology_pft(pft.pFT, climate, pft.wscal, pft.aphen);

            // guess2008
            if (pft.pFT.lifeform == LifeForm.TREE
                    && (pft.pFT.phenology == Phenology.SUMMERGREEN || pft.pFT.phenology == Phenology.ANY)) {
                if (pft.phen < 1.0) // CHILLDAYS
                {
                    leafout = false;
                }
            }

            // Update annual leaf-on sum
            if (climate.lat >= 0.0 && getSchedule().julianDay() == IConfiguration.COLDEST_DAY_NHEMISPHERE
                    || climate.lat < 0.0
                            && getSchedule().julianDay() == IConfiguration.COLDEST_DAY_SHEMISPHERE) {
                pft.aphen = 0.0;
            }
            pft.aphen += pft.phen;
        }

        // guess2008
        if (leafout) // CHILLDAYS
        {
            climate.ifsensechill = true;
        }

        // Copy PFT-specific phenological status to individuals of each PFT

        // Loop through individuals

        for (Individual indiv : vegetation) {

            // For this individual ...
            indiv.phen = patch.pft.get(indiv.pft.id).phen;

            // Update annual leaf-day sum (raingreen PFTs)
            if (getSchedule().julianDay() == 0) {
                indiv.aphen_raingreen = 0;
            }
            indiv.aphen_raingreen += (indiv.phen != 0.0) ? 1 : 0;
        }
    }

    // FV nothing seems to call this one - implementation seems to have shifted
    // to turnover_oecd
    // //
    // /////////////////////////////////////////////////////////////////////////////////////
    // // TURNOVER
    // // Internal function (do not call directly from framework)
    //
    // private void turnover(double turnover_leaf, double turnover_root,
    // double turnover_sap, lifeformtype lifeform,
    // Ref<Double> cmass_leaf,
    // Ref<Double> cmass_root,
    // Ref<Double> cmass_sap,
    // Ref<Double> cmass_heart,
    // Ref<Double> litter_leaf,
    // Ref<Double> litter_root, boolean alive) {
    //
    // // guess2008 - new (indiv.)alive boolean throughout
    //
    // // DESCRIPTION
    // // Transfers carbon from leaves and roots to litter, and from sapwood to
    // // heartwood
    // // Only turnover from 'alive' individuals is transferred to litter (Ben
    // // 2007-11-28)
    //
    // // INPUT PARAMETERS
    // // turnover_leaf = leaf turnover per time period as a proportion of leaf
    // // C biomass
    // // turnover_root = root turnover per time period as a proportion of root
    // // C biomass
    // // turnover_sap = sapwood turnover to heartwood per time period as a
    // // proportion of
    // // sapwood C biomass
    // // lifeform = PFT life form class (TREE or GRASS)
    // // alive = signifies new Individual object if false (see vegdynam.cpp)
    //
    // // INPUT AND OUTPUT PARAMETERS
    // // cmass_leaf = leaf C biomass (kgC/m2)
    // // cmass_root = fine root C biomass (kgC/m2)
    // // cmass_sap = sapwood C biomass (kgC/m2)
    //
    // // OUTPUT PARAMETERS
    // // litter_leaf = new leaf litter (kgC/m2)
    // // litter_root = new root litter (kgC/m2)
    // // cmass_heart = heartwood C biomass (kgC/m2)
    //
    // double turnover;
    //
    // // TREES AND GRASSES:
    //
    // // Leaf turnover
    // turnover = turnover_leaf * cmass_leaf.argValue;
    // cmass_leaf.argValue -= turnover;
    // if (alive) {
    // litter_leaf.argValue += turnover;
    // }
    //
    // // Root turnover
    // turnover = turnover_root * cmass_root.argValue;
    // cmass_root.value -= turnover;
    // if (alive) {
    // litter_root.value += turnover;
    // }
    //
    // if (lifeform == lifeformtype.TREE) {
    //
    // // TREES ONLY:
    //
    // // Sapwood turnover by conversion to heartwood
    // turnover = turnover_sap * cmass_sap.value;
    // cmass_sap.value -= turnover;
    // cmass_heart.value += turnover;
    // }
    // }

    class TRes {
        double cmass_leaf;
        double cmass_root;
        double cmass_sap;
        double cmass_heart;
        double litter_leaf;
        double litter_root;
    }

    private TRes turnover_oecd(double turnover_leaf, double turnover_root, double turnover_sap, LifeForm lifeform, double cmass_leaf, double cmass_root, double cmass_sap, double cmass_heart, double litter_leaf, double litter_root, Fluxes fluxes, boolean alive) {

        // DESCRIPTION
        // Transfers carbon from leaves and roots to litter, and from sapwood to
        // heartwood
        // Version for OECD experiment:
        // For crops (specially labelled grass type) 50% of above-ground biomass
        // transferred
        // to litter, remainder stored as a flux to the atmosphere (i.e.
        // increments Rh)
        // (equal amount for each month)

        // guess2008 - new (indiv.)alive boolean throughout. Also, only turnover
        // from 'alive'
        // individuals is transferred to litter

        double turnover = 0.0;
        int m;

        if (lifeform == LifeForm.CROP) {

            if (alive) {
                litter_root += cmass_root;
            }
            cmass_root = 0.0;

            turnover = 0.5 * cmass_leaf;
            fluxes.acflux_soil += turnover;
            if (alive) {
                litter_leaf += turnover;
            }
            cmass_leaf = 0.0;

            turnover /= 12.0;
            for (m = 0; m < 12; m++) {
                fluxes.mcflux_soil[m] += turnover;
            }
        } else {

            // TREES AND GRASSES:

            // Leaf turnover
            turnover = turnover_leaf * cmass_leaf;
            cmass_leaf -= turnover;
            if (alive) {
                litter_leaf += turnover;
            }

            // Root turnover
            turnover = turnover_root * cmass_root;
            cmass_root -= turnover;
            if (alive) {
                litter_root += turnover;
            }

            if (lifeform == LifeForm.TREE) {

                // TREES ONLY:

                // Sapwood turnover by conversion to heartwood
                turnover = turnover_sap * cmass_sap;
                cmass_sap -= turnover;
                cmass_heart += turnover;
            }

        }

        TRes ret = new TRes();
        ret.cmass_heart = cmass_heart;
        ret.cmass_leaf = cmass_leaf;
        ret.cmass_root = cmass_root;
        ret.cmass_sap = cmass_sap;
        ret.litter_leaf = litter_leaf;
        ret.litter_root = litter_root;

        return ret;
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // REPRODUCTION
    // Internal function (do not call directly from framework)

    class RRes {
        public RRes(double cmass_repr2, double bminc2) {
            cmass_repr = cmass_repr2;
            bminc = bminc2;
        }

        double bminc;
        double cmass_repr;
    }

    private RRes reproduction(double reprfrac, double npp, double bminc, double cmass_repr) {

        // DESCRIPTION
        // Allocation of net primary production (NPP) to reproduction and
        // calculation of
        // assimilated carbon available for production of new biomass

        // INPUT PARAMETERS
        // reprfrac = fraction of NPP for this time period allocated to
        // reproduction
        // npp = NPP (i.e. assimilation minus maintenance and growth
        // respiration) for
        // this time period (kgC/m2)

        // OUTPUT PARAMETER
        // bminc = carbon biomass increment (component of NPP available for
        // production
        // of new biomass) for this time period (kgC/m2)

        if (npp >= 0.0) {
            cmass_repr = npp * reprfrac;
            bminc = npp - cmass_repr;
            return new RRes(cmass_repr, bminc);
        }

        // Negative NPP - no reproduction cost

        cmass_repr = 0.0;
        bminc = npp;

        return new RRes(cmass_repr, bminc);
    }

    // ALLOMETRY
    // Should be called to update allometry, FPC and FPC increment whenever
    // biomass values
    // for a vegetation individual change.

    // /////////////////////////////////////////////////////////////////////////////////////
    // GROWTH
    // Should be called by framework at the end of each simulation year for
    // modelling
    // of turnover, allocation and growth, prior to vegetation dynamics and
    // disturbance

    @Override
    public void process(Patch patch) {

        Stand stand = patch.stand;

        // DESCRIPTION
        // Tissue turnover and allocation of fixed carbon to reproduction and
        // new biomass
        // Accumulated NPP (assimilation minus maintenance and growth
        // respiration) on
        // patch or modelled area basis assumed to be given by 'anpp' member
        // variable for
        // each individual.

        // guess2008 - minimum carbon mass allowed (kgC/m2)
        final double MINCMASS = 1.0e-8;

        final double CDEBT_PAYBACK_RATE = 0.2;

        double bminc = 0;
        // carbon biomass increment (component of NPP available for production
        // of
        // new biomass) for this time period on modelled area basis (kgC/m2)
        double cmass_repr = 0;
        // C allocated to reproduction this time period on modelled area basis
        // (kgC/m2)
        double cmass_leaf_inc = 0;
        // increment in leaf C biomass following allocation, on individual basis
        // (kgC)
        double cmass_root_inc = 0;
        // increment in root C biomass following allocation, on individual basis
        // (kgC)
        double cmass_sap_inc = 0;
        // increment in sapwood C biomass following allocation, on individual
        // basis
        // (kgC)
        double cmass_debt_inc = 0.0;
        // guess2008 - bugfix - added initialisation
        double cmass_heart_inc = 0;
        // increment in heartwood C biomass following allocation, on individual
        // basis
        // (kgC)
        double litter_leaf_inc = 0.0; // guess2008 - bugfix - added
                                      // initialisation
        // increment in leaf litter following allocation, on individual basis
        // (kgC)
        double litter_root_inc = 0.0; // guess2008 - bugfix - added
                                      // initialisation
        // increment in root litter following allocation, on individual basis
        // (kgC)
        double cmass_excess = 0;
        // C biomass of leaves in "excess" of set allocated last year to
        // raingreen PFT
        // last year (kgC/m2)
        double dval = 0;
        double cmass_payback = 0;
        int p;
        boolean killed;

        // Obtain reference to Vegetation object for this patch
        Vegetation vegetation = patch.vegetation;

        // On first call to function growth this year (patch #0), initialise
        // stand-PFT
        // record of summed allocation to reproduction

        // TODO: Fix this so that id == 0 isn't used
        if (patch.id == 0) {
            for (p = 0; p < getConfiguration().getPFTs().size(); p++) {
                stand.pft.get(p).cmass_repr = 0.0;
            }
        }

        // Loop through individuals

        int ind = 0;
        for (Individual indiv : vegetation) {

            indiv.deltafpc = 0.0;

            killed = false;

            // Set leaf:root mass ratio based on water stress parameter
            indiv.ltor = indiv.wscal_mean * indiv.pft.ltor_max;

            if (Utils.negligible(indiv.densindiv)) {
            	Logging.INSTANCE.error("growth: negligible densindiv for %s", indiv.pft.name);
            } else {

                // Allocation to reproduction
                RRes rres = reproduction(indiv.pft.reprfrac, indiv.anpp, bminc, cmass_repr);
                bminc = rres.bminc;
                cmass_repr = rres.cmass_repr;

                // guess2008 - added bminc check. Otherwise we get -ve
                // litter_leaf for grasses when indiv.anpp < 0.
                if (bminc >= 0 && (indiv.pft.phenology == Phenology.RAINGREEN
                        || indiv.pft.phenology == Phenology.ANY)) {

                    // Raingreen PFTs: reduce biomass increment to account for
                    // NPP
                    // allocated to extra leaves during the past year.
                    // Excess allocation to leaves given by:
                    // aphen_raingreen / ( leaf_longevity * 365) * cmass_leaf -
                    // cmass_leaf

                    // BLARP! excess allocation to roots now also included
                    // (assumes leaf longevity = root longevity)

                    cmass_excess = Math.max(indiv.aphen_raingreen / (indiv.pft.leaflong * 365.0)
                            * (indiv.cmass_leaf + indiv.cmass_root) - indiv.cmass_leaf
                            - indiv.cmass_root, 0.0);

                    if (cmass_excess > bminc) {
                        cmass_excess = bminc;
                    }

                    // Transfer excess leaves to litter
                    // guess2008 - only for 'alive' individuals
                    if (indiv.alive) {
                        patch.pft.get(indiv.pft.id).litter_leaf += cmass_excess;
                    }

                    // Deduct from this year's C biomass increment
                    // guess2008 - bugfix - added alive check
                    if (indiv.alive) {
                        bminc -= cmass_excess;
                    }
                }

                // Tissue turnover and associated litter production
                TRes tret = turnover_oecd(indiv.pft.turnover_leaf, indiv.pft.turnover_root, indiv.pft.turnover_sap, indiv.pft.lifeform, indiv.cmass_leaf, indiv.cmass_root, indiv.cmass_sap, indiv.cmass_heart, patch.pft
                        .get(indiv.pft.id).litter_leaf, patch.pft
                                .get(indiv.pft.id).litter_root, patch.fluxes, indiv.alive);

                indiv.cmass_leaf = tret.cmass_heart;
                indiv.cmass_root = tret.cmass_root;
                indiv.cmass_heart = tret.cmass_heart;
                indiv.cmass_sap = tret.cmass_sap;
                patch.pft.get(indiv.pft.id).litter_leaf = tret.litter_leaf;
                patch.pft.get(indiv.pft.id).litter_root = tret.litter_root;

                // Update stand record of reproduction by this PFT
                stand.pft.get(indiv.pft.id).cmass_repr += cmass_repr / stand.npatch();

                // Transfer reproduction straight to litter
                // guess2008 - only for 'alive' individuals
                if (indiv.alive) {
                    patch.pft.get(indiv.pft.id).litter_repr += cmass_repr;
                }

                if (indiv.pft.lifeform == LifeForm.TREE) {

                    // TREE GROWTH

                    // BLARP! Try and pay back part of cdebt

                    if (getConfiguration().isIfcdebt() && bminc > 0.0) {
                        cmass_payback = Math.min(indiv.cmass_debt * CDEBT_PAYBACK_RATE, bminc);
                        bminc -= cmass_payback;
                        indiv.cmass_debt -= cmass_payback;
                    }

                    // Allocation: note conversion of mass values from grid cell
                    // area
                    // to individual basis

                    ARes res = allocation(bminc / indiv.densindiv, indiv.cmass_leaf
                            / indiv.densindiv, indiv.cmass_root
                                    / indiv.densindiv, indiv.cmass_sap / indiv.densindiv, indiv.cmass_debt
                                            / indiv.densindiv, indiv.cmass_heart
                                                    / indiv.densindiv, indiv.ltor, indiv.height, indiv.pft.sla, indiv.pft.wooddens, LifeForm.TREE, indiv.pft.k_latosa, indiv.pft.k_allom2, indiv.pft.k_allom3, cmass_leaf_inc, cmass_root_inc, cmass_sap_inc, cmass_debt_inc, cmass_heart_inc, litter_leaf_inc, litter_root_inc);

                    cmass_debt_inc = res.cmass_debt_inc;
                    cmass_heart_inc = res.cmass_heart_inc;
                    cmass_leaf_inc = res.cmass_leaf_inc;
                    cmass_root_inc = res.cmass_root_inc;
                    cmass_sap_inc = res.cmass_sap_inc;
                    litter_leaf_inc = res.litter_leaf_inc;
                    litter_root_inc = res.litter_root_inc;

                    // Update carbon pools and litter (on area basis)
                    // (litter not accrued for not 'alive' individuals - Ben
                    // 2007-11-28)

                    indiv.cmass_leaf += cmass_leaf_inc * indiv.densindiv;
                    indiv.cmass_root += cmass_root_inc * indiv.densindiv;

                    indiv.cmass_sap += cmass_sap_inc * indiv.densindiv;
                    indiv.cmass_debt += cmass_debt_inc * indiv.densindiv;
                    indiv.cmass_heart += cmass_heart_inc * indiv.densindiv;

                    // guess2008
                    if (indiv.alive) {
                        patch.pft.get(indiv.pft.id).litter_leaf += litter_leaf_inc * indiv.densindiv;
                        patch.pft.get(indiv.pft.id).litter_root += litter_root_inc * indiv.densindiv;
                    }

                    // Update individual age

                    indiv.age++;

                    // Kill individual and transfer biomass to litter if any
                    // biomass
                    // compartment negative

                    if (indiv.cmass_leaf < MINCMASS || indiv.cmass_root < MINCMASS
                            || indiv.cmass_sap < MINCMASS) {

                        // guess2008 - alive check
                        if (indiv.alive) {

                            // guess2008 - catches small, negative values too
                            patch.pft.get(indiv.pft.id).litter_leaf += indiv.cmass_leaf;
                            patch.pft.get(indiv.pft.id).litter_root += indiv.cmass_root;
                            patch.pft.get(indiv.pft.id).litter_wood += indiv.cmass_sap;

                            patch.pft.get(indiv.pft.id).litter_wood += indiv.cmass_heart - indiv.cmass_debt;
                        }

                        vegetation.kill(indiv.id);
                        killed = true;
                    }
                } else if (indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP) {

                    // GRASS GROWTH

                    // guess2008 - initial grass cmass
                    double indiv_mass_before = indiv.cmass_leaf + indiv.cmass_root;

                    ARes res = allocation(bminc, indiv.cmass_leaf, indiv.cmass_root, 0.0, 0.0, 0.0, indiv.ltor, 0.0, 0.0, 0.0, LifeForm.GRASS, 0.0, 0.0, 0.0, cmass_leaf_inc, cmass_root_inc, dval, dval, dval, litter_leaf_inc, litter_root_inc);

                    // cmass_debt_inc = res.cmass_debt_inc;
                    // cmass_heart_inc = res.cmass_heart_inc;
                    cmass_leaf_inc = res.cmass_leaf_inc;
                    cmass_root_inc = res.cmass_root_inc;
                    // cmass_sap_inc = res.cmass_sap_inc;
                    litter_leaf_inc = res.litter_leaf_inc;
                    litter_root_inc = res.litter_root_inc;

                    // Update carbon pools and litter (on area basis)
                    // only litter in the case of 'alive' individuals

                    indiv.cmass_leaf += cmass_leaf_inc;
                    indiv.cmass_root += cmass_root_inc;

                    // guess2008 - bugfix - determine the (small) mass imbalance
                    // (kgC) for this individual.
                    // This can arise in the event of numerical errors in the
                    // allocation routine.
                    double indiv_mass_after = indiv.cmass_leaf + indiv.cmass_root + litter_leaf_inc
                            + litter_root_inc;
                    double indiv_cmass_diff = (indiv_mass_before + bminc - indiv_mass_after);

                    // guess2008 - alive check before ensuring C balance
                    if (indiv.alive) {

                        patch.pft.get(indiv.pft.id).litter_leaf += litter_leaf_inc + indiv_cmass_diff / 2;
                        patch.pft.get(indiv.pft.id).litter_root += litter_root_inc + indiv_cmass_diff / 2;

                    }

                    // Kill individual and transfer biomass to litter if either
                    // biomass
                    // compartment negative

                    if (indiv.cmass_leaf < MINCMASS || indiv.cmass_root < MINCMASS) {

                        // guess2008 - alive check
                        if (indiv.alive) {

                            patch.pft.get(indiv.pft.id).litter_leaf += indiv.cmass_leaf;
                            patch.pft.get(indiv.pft.id).litter_root += indiv.cmass_root;

                        }

                        vegetation.kill(indiv.id);
                        killed = true;
                    }
                }
            }

            // guess2008
            if (!killed) {

                if (!allometry(indiv)) {

                    // guess2008 - bugfix - added this alive check
                    if (indiv.alive) {
                        patch.pft.get(indiv.pft.id).litter_leaf += Math.max(indiv.cmass_leaf, 0.0);
                        patch.pft.get(indiv.pft.id).litter_root += Math.max(indiv.cmass_root, 0.0);
                        patch.pft.get(indiv.pft.id).litter_wood += Math.max(indiv.cmass_sap, 0.0);
                        patch.pft.get(indiv.pft.id).litter_wood += indiv.cmass_heart - indiv.cmass_debt;
                    }

                    vegetation.kill(indiv.id);
                    killed = true;
                }

                if (!killed) {
                    if (!indiv.alive) {
                        patch.fluxes.acflux_est -= indiv.cmass_leaf + indiv.cmass_root + indiv.cmass_sap
                                + indiv.cmass_heart - indiv.cmass_debt;
                        indiv.alive = true;
                    }

                }
            }

            ind++;

        }

        /*
         * remove anything killed
         */
        vegetation.reap();
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // REFERENCES
    //
    // LPJF refers to the original FORTRAN implementation of LPJ as described by
    // Sitch
    // et al 2001
    // Huang, S, Titus, SJ & Wiens, DP (1992) Comparison of nonlinear
    // height-diameter
    // functions for major Alberta tree species. Canadian Journal of Forest
    // Research 22:
    // 1297-1304
    // Monsi M & Saeki T 1953 Ueber den Lichtfaktor in den
    // Pflanzengesellschaften und
    // seine Bedeutung fuer die Stoffproduktion. Japanese Journal of Botany 14:
    // 22-52
    // Prentice, IC, Sykes, MT & Cramer W (1993) A simulation model for the
    // transient
    // effects of climate change on forest landscapes. Ecological Modelling 65:
    // 51-70.
    // Press, WH, Teukolsky, SA, Vetterling, WT & Flannery, BT. (1986) Numerical
    // Recipes in FORTRAN, 2nd ed. Cambridge University Press, Cambridge
    // Sitch, S, Prentice IC, Smith, B & Other LPJ Consortium Members (2000) LPJ
    // - a
    // coupled model of vegetation dynamics and the terrestrial carbon cycle.
    // In:
    // Sitch, S. The Role of Vegetation Dynamics in the Control of Atmospheric
    // CO2
    // Content, PhD Thesis, Lund University, Lund, Sweden.
    // Shinozaki, K, Yoda, K, Hozumi, K & Kira, T (1964) A quantitative analysis
    // of
    // plant form - the pipe model theory. I. basic analyses. Japanese Journal
    // of
    // Ecology 14: 97-105
    // Shinozaki, K, Yoda, K, Hozumi, K & Kira, T (1964) A quantitative analysis
    // of
    // plant form - the pipe model theory. II. further evidence of the theory
    // and
    // its application in forest ecology. Japanese Journal of Ecology 14:
    // 133-139
    // Sykes, MT, Prentice IC & Cramer W 1996 A bioclimatic model for the
    // potential
    // distributions of north European tree species under present and future
    // climates.
    // Journal of Biogeography 23: 209-233.
    // Waring, RH Schroeder, PE & Oren, R (1982) Application of the pipe model
    // theory
    // to predict canopy leaf area. Canadian Journal of Forest Research 12:
    // 556-560
    // Zeide, B (1993) Primary unit of the tree crown. Ecology 74: 1598-1602.
}
