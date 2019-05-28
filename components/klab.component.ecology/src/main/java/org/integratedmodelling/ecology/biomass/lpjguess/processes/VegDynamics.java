package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import java.util.List;

import org.integratedmodelling.ecology.biomass.lpjguess.Climate;
import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.PFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.Stand;
import org.integratedmodelling.ecology.biomass.lpjguess.Vegetation;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.VegetationProcess;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.VegetationMode;

public class VegDynamics extends VegetationProcess {

    public VegDynamics(IConfiguration configuration) {
        super(configuration);
    }

    // private int individ = 0; // running id code for new individuals (see
    // // establishment)

    // /////////////////////////////////////////////////////////////////////////////////////
    // RANDPOISSON
    // Internal functions for generating random numbers

    private int randpoisson(double expectation) {

        // DESCRIPTION
        // Returns a random integer drawn from the Poisson distribution with
        // specified
        // expectation (for computational reasons, the Gaussian normal
        // distribution is
        // used as an approximation of the Poisson distribution for expected
        // values >100)

        double p;
        double q;
        double r;
        int n;

        if (expectation <= 100) {

            // For expected values up to 100, calculate a true Poisson number

            p = Math.exp(-expectation);
            q = p;
            r = Math.random();

            n = 0;
            while (q < r) {
                n++;
                p *= expectation / n;
                q += p;
            }
            return n;
        }

        // For higher expected values than 100, approximate the Poisson
        // distribution
        // by the Gaussian normal distribution with mean equal to the expected
        // value,
        // and standard deviation the square root of this value

        do {
            r = Math.random() * 8.0 - 4.0;
            p = Math.exp(-r * r / 2.0);
        } while (Math.random() > p);

        return Math.max(0, (int) (r * Math.sqrt(expectation) + expectation + 0.5));
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // BIOCLIMATIC LIMITS ON ESTABLISHMENT AND SURVIVAL
    // Internal functions (do not call directly from framework)

    private boolean establish(Patch patch, Climate climate, PFT pFT) {

        // DESCRIPTION
        // Determines whether specified PFT is within its bioclimatic limits for
        // establishment in a given patch and climate. Returns true if PFT can
        // establish
        // under specified conditions, false otherwise

        // The following limits are implemented:
        // tcmin_est = minimum coldest month mean temperature for the last 20
        // years
        // tcmax_est = maximum coldest month mean temperature for the last 20
        // years
        // twmin_est = minimum warmest month mean temperature
        // gdd5min_est = minimum growing degree day sum on 5 deg C base

        // if (pft.name != "Pin_syl" && pft.name != "Pic_abi")
        // return false;

        if (climate.mtemp_min20 < pFT.tcmin_est || climate.mtemp_min20 > pFT.tcmax_est
                || climate.mtemp_max < pFT.twmin_est || climate.agdd5 < pFT.gdd5min_est) {
            return false;
        }

        if (getConfiguration().getVegmode() != VegetationMode.POPULATION
                && patch.par_grass_mean < pFT.parff_min) {
            return false;
        }

        // guess2008 - DLE - new drought limited establishment
        if (getConfiguration().isEstablishmentDroughtLimited()) {
            // Compare this PFT's/species' drought_tolerance with the average
            // wcont over the
            // growing season, in this patch. Higher drought_tolerance values
            // (set in the .ins file)
            // lead to greater restrictions on establishment.
            if (pFT.drought_tolerance > patch.soil.awcont[0]) {
                return false;
            }
        }

        // else

        return true;
    }

    private boolean survive(Climate climate, PFT pFT) {

        // DESCRIPTION
        // Determines whether specified PFT is within its bioclimatic limits for
        // survival
        // in a given climate. Returns true if PFT can survive in the specified
        // climate,
        // false otherwise

        // The following limit is implemented:
        // tcmin_surv = minimum coldest month temperature for the last 20 years
        // (deg C)

        if (climate.mtemp_min20 < pFT.tcmin_surv
                || climate.mtemp_max20 - climate.mtemp_min20 < pFT.twminusc) {
            return false;
        } else {
            return true;
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ESTABLISHMENT
    // Internal functions (do not call directly from framework)

    private void establishment_lpj(Stand stand, Patch patch, List<PFT> pftlist) {

        // DESCRIPTION
        // Establishment in population (standard LPJ) mode.
        // Simulates population increase through establishment each simulation
        // year for
        // trees and grasses and introduces new PFTs if climate conditions
        // become suitable.
        // This function assumes each Individual object represents the average
        // individual
        // of a PFT population, and that there is (at most) one Individual
        // object per PFT
        // per modelled area (stand and patch).
        // This is the only function in which new average individuals are added
        // to the
        // vegetation in population mode.

        final double K_EST = 0.12; // maximum overall sapling establishment rate
        // (indiv/m2)

        double fpc_tree; // summed fractional cover for tree PFTs
        double fpc_grass; // summed fractional cover for grass PFTs
        double est_tree;
        // overall establishment rate for trees on modelled area basis
        // (indiv/m2)
        double est_pft;
        // establishment rate for a particular PFT on modelled area basis (for
        // trees,
        // indiv/m2; for grasses, fraction of modelled area colonised)
        int ntree_est; // number of establishing tree PFTs
        int ngrass_est; // number of establishing grass PFTs
        boolean present = false;

        // Obtain reference to Vegetation object

        Vegetation vegetation = patch.vegetation;

        // Loop through all possible PFTs to determine whether there are any not
        // currently
        // present but within their bioclimatic limits for establishment

        for (PFT pFT : pftlist) {

            // Is this PFT already represented?

            for (Individual indiv : vegetation) {
                if (indiv.pft.id == pFT.id) {
                    present = true;
                    break;
                }
            }

            if (!present) {
                if (establish(patch, stand.getClimate(), pFT)) {

                    // Not present but can establish, so introduce as new
                    // average
                    // individual

                    Individual indiv = new Individual(pFT, vegetation);
                    if (pFT.lifeform == LifeForm.GRASS || pFT.lifeform == LifeForm.CROP) {
                        indiv.height = 0.0;
                        indiv.crownarea = 1.0; // (value not used)
                        indiv.densindiv = 1.0;
                        indiv.fpc = 0.0;
                    }
                    vegetation.add(indiv);
                }
            }
        }

        // Calculate total FPC and number of PFT's (i.e. average individuals)
        // establishing
        // this year for trees and grasses respectively

        fpc_tree = 0.0;
        fpc_grass = 0.0;
        ntree_est = 0;
        ngrass_est = 0;

        // Loop through individuals

        for (Individual indiv : vegetation) {

            if (indiv.pft.lifeform == LifeForm.TREE) {
                if (establish(patch, stand.getClimate(), indiv.pft)) {
                    ntree_est++;
                }
                fpc_tree += indiv.fpc;
            } else if (indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP) {
                fpc_grass += indiv.fpc;
                if (establish(patch, stand.getClimate(), indiv.pft)) {
                    ngrass_est++;
                }
            }
        }

        // Calculate overall establishment rate for trees
        // Trees can establish in unoccupied regions of the stand, and in
        // regions
        // occupied by grasses. Establishment reduced in response to canopy
        // closure as
        // tree cover approaches 100%
        // Smith et al 2001, Eqn (17)

        est_tree = K_EST * (1.0 - Math.exp(-5.0 * (1.0 - fpc_tree))) * (1.0 - fpc_tree);

        // Loop through individuals

        for (Individual indiv : vegetation) {

            if (indiv.pft.lifeform == LifeForm.TREE && establish(patch, stand.getClimate(), indiv.pft)) {

                // ESTABLISHMENT OF NEW TREE SAPLINGS
                // Partition overall establishment equally among establishing
                // PFTs

                est_pft = est_tree / ntree_est;

                if (est_pft < 0.0) {
                	Logging.INSTANCE.error("establishment_area: negative establishment rate", 0);
                }

                // Adjust density of individuals across modelled area to account
                // for
                // addition of new saplings

                indiv.densindiv += est_pft;

                // Account for flux from the atmosphere to new saplings
                // (flux is downward and therefore negative)

                // guess2008
                // flux is not debited for 'new' Individual objects - their
                // carbon is
                // debited in function growth() if they survive the first year

                if (indiv.alive) // guess2008 - alive check added
                {
                    patch.fluxes.acflux_est -= (indiv.pft.regen.cmass_leaf + indiv.pft.regen.cmass_root
                            + indiv.pft.regen.cmass_sap + indiv.pft.regen.cmass_heart)
                            * est_pft;
                }

                // Adjust average individual C biomass based on average biomass
                // and density
                // of the new saplings

                indiv.cmass_leaf += indiv.pft.regen.cmass_leaf * est_pft;
                indiv.cmass_root += indiv.pft.regen.cmass_root * est_pft;
                indiv.cmass_sap += indiv.pft.regen.cmass_sap * est_pft;
                indiv.cmass_heart += indiv.pft.regen.cmass_heart * est_pft;
            } else if ((indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP)
                    && establish(patch, stand.getClimate(), indiv.pft)) {

                // ESTABLISHMENT OF GRASSES
                // Grasses establish throughout unoccupied regions of the grid
                // cell
                // Overall establishment partitioned equally among establishing
                // PFTs

                est_pft = (1.0 - fpc_tree - fpc_grass) / ngrass_est;

                // Account for flux from atmosphere to grass regeneration

                if (indiv.alive) // guess2008 - alive check added
                {
                    patch.fluxes.acflux_est -= (indiv.pft.regen.cmass_leaf + indiv.pft.regen.cmass_root)
                            * est_pft;
                }

                // Add regeneration biomass to overall biomass

                indiv.cmass_leaf += est_pft * indiv.pft.regen.cmass_leaf;
                indiv.cmass_root += est_pft * indiv.pft.regen.cmass_root;
            }

            // Update allometry to account for changes in average individual
            // biomass
            allometry(indiv);

        }
    }

    // DESCRIPTION
    // Establishment in cohort or individual mode.
    // Establishes new tree saplings and simulates grass population increase
    // each
    // establishment year (every year in individual mode). New grass PFTs are
    // introduced (any year) if climate conditions become suitable.
    // Saplings are given an amount of carbon proportional to the hypothetical
    // forest
    // floor NPP for this year and are "moulded" by calls to the standard
    // allocation
    // function (growth module). The coefficient SAPSIZE controls the relative
    // amount
    // of carbon given each sapling and should be set to a value resulting in
    // saplings
    // around breast height in the first year (before development of a shading
    // canopy).

    // For tree PFTs within their bioclimatic limits for establishment, the
    // expected
    // number of saplings (est) established in each patch is given by:
    //
    // for model initialisation:
    // (1) est = est_max*patcharea
    // if spatial mass effect enabled (stand-level "propagule pool" influences
    // establishment):
    // (2) est = c*(kest_repr*cmass_repr+kest_bg)
    // if no spatial mass effect and propagule pool exists (cmass_repr
    // non-negligible):
    // (3) est = c*(kest_pres+kest_bg)
    // if no spatial mass effect and no propagule pool:
    // (4) est = c*kest_bg
    // where
    // (5) c = mu(anetps_ff/anetps_ff_max)*est_max*patcharea
    // (6) mu(F) = exp(alphar*(1-1/F)) (Fulton 1991, Eqn 4)
    // where
    // kest_repr = PFT-specific constant (kest_repr*cmass_repr should
    // approximately equal 1 at the highest plausible value of
    // cmass_repr for the PFT)
    // kest_bg = PFT-specific constant in range 0-1 (usually << 1)
    // (set to zero if background establishment disabled)
    // kest_pres = PFT-specific constant in range 0-1 (usually ~1)
    // cmass_repr = net C allocated to reproduction for this PFT in all patches
    // of
    // this stand this year (kgC/m2)
    // est_max = (nominal) maximum establishment rate for this PFT
    // (saplings/m2/year)
    // patcharea = patch area (m2)
    // anetps_ff = potential annual net assimilation at forest floor for this
    // PFT (kgC/m2/year)
    // anetps_ff_max = maximum value of anetps_ff for this PFT in this stand so
    // far
    // in the simulation (kgC/m2/year)
    // alphar = PFT-specific constant (parameter capturing non-linearity in
    // recruitment rate relative to understorey growing conditions)
    //
    // In individual mode, and in cohort mode if stochastic establishment is
    // enabled,
    // the actual number of new saplings established is a number drawn from the
    // Poisson
    // distribution with expectation 'est'. In cohort mode, with stochastic
    // establishment disabled, a cohort representing exactly 'est' individuals
    // (may be
    // not-integral) is established.

    private void establishment_guess(Stand stand, Patch patch, List<PFT> pftlist) {

        final double SAPSIZE = 0.1;
        // coefficient in calculation of initial sapling size and initial
        // grass biomass (see comment above)

        boolean present; // whether PFT already present in this patch
        double c; // constant in equation for number of new saplings (Eqn 5)
        double est; // expected number of new saplings for PFT in this patch
        double nsapling;
        // actual number of new saplings for PFT in this patch (may include a
        // fractional part in cohort mode with stochastic establishment
        // disabled)
        double bminit; // initial sapling biomass (kgC) or new grass biomass
        // (kgC/m2)
        double ltor; // leaf to fine root mass ratio for new saplings or grass
        int newindiv = 0; // number of new Individual objects to add to
                          // vegetation
                          // for this PFT
        double kest_bg;
        int i;

        // Obtain reference to Vegetation object

        Vegetation vegetation = patch.vegetation;

        // guess2008 - determine the number of woody PFTs that can establish
        // Thomas Hickler
        int nwoodypfts_estab = 0;

        for (PFT pFT : pftlist) {
            if (establish(patch, stand.getClimate(), pFT) && pFT.lifeform == LifeForm.TREE) {
                nwoodypfts_estab++;
            }
        }

        for (PFT pFT : pftlist) {
            if (patch.age == 0) {
                patch.pft.get(pFT.id).anetps_ff_est = patch.pft.get(pFT.id).anetps_ff;
                patch.pft.get(pFT.id).wscal_mean_est = patch.pft.get(pFT.id).wscal_mean;

                // BLARP
                // fv: BLARP?
                if (getSchedule().isFirstYearOfSimulation()) {
                    patch.pft.get(pFT.id).anetps_ff_est_initial = patch.pft.get(pFT.id).anetps_ff;
                }

            } else {
                patch.pft.get(pFT.id).anetps_ff_est += patch.pft.get(pFT.id).anetps_ff;
                patch.pft.get(pFT.id).wscal_mean_est += patch.pft.get(pFT.id).wscal_mean;
            }

            if (establish(patch, stand.getClimate(), pFT)) {

                if (pFT.lifeform == LifeForm.GRASS || pFT.lifeform == LifeForm.CROP) {

                    // ESTABLISHMENT OF GRASSES

                    // Each grass PFT represented by just one individual in each
                    // patch
                    // Check whether this grass PFT already represented ...

                    present = vegetation.isPFTRepresented(pFT.id);

                    if (!present) {

                        // ... if not, add it

                        Individual indiv = new Individual(pFT, vegetation);

                        vegetation.add(indiv);

                        indiv.height = 0.0;
                        indiv.crownarea = 1.0; // (value not used)
                        indiv.densindiv = 1.0;
                        indiv.fpc = 1.0;

                        // Initial grass biomass proportional to potential
                        // forest floor
                        // net assimilation this year on patch area basis

                        bminit = SAPSIZE * patch.pft.get(pFT.id).anetps_ff;

                        // BLARP! OECD
                        if (getConfiguration().isDisturbanceEnabled() && patch.disturbed) {
                            bminit = SAPSIZE * patch.pft.get(pFT.id).anetps_ff_est_initial;
                        }

                        // Initial leaf to fine root biomass ratio based on
                        // hypothetical value of water stress parameter

                        ltor = patch.pft.get(pFT.id).wscal_mean * pFT.ltor_max;

                        // Allocate initial biomass
                        allocation_init(bminit, ltor, indiv);

                        // Calculate initial allometry
                        allometry(indiv);

                        // Account for C flux from atmosphere to vegetation
                        // guess2008 - flux is not debited for 'new' Individual
                        // objects - their carbon is debited in function
                        // growth()
                        // if they survive the first year

                        if (indiv.alive) {
                            patch.fluxes.acflux_est -= bminit;
                        }
                    }
                } else if (pFT.lifeform == LifeForm.TREE) {

                    // ESTABLISHMENT OF NEW TREE SAPLINGS
                    if (patch.age == 0) {

                        // First simulation year - initialising patch
                        // Eqn 1
                        est = pFT.est_max * patch.area;
                    }

                    else {

                        // Every year except year 1
                        // Eqns 5, 6

                        if (patch.pft.get(pFT.id).anetps_ff > 0.0
                                && !Utils.negligible(patch.pft.get(pFT.id).anetps_ff)) {

                            c = Math.exp(pFT.alphar - pFT.alphar / patch.pft.get(pFT.id).anetps_ff
                                    * stand.pft.get(pFT.id).anetps_ff_max)
                                    * pFT.est_max * patch.area;
                        } else {
                            c = 0.0;
                        }

                        // Background establishment enabled?

                        if (getConfiguration().isBackgroundEstablishmentEnabled()) {
                            kest_bg = pFT.kest_bg;
                        } else {
                            kest_bg = 0.0;
                        }

                        // Spatial mass effect enabled?
                        // Eqns 2, 3, 4

                        if (getConfiguration().isSpatialMassEffectEnabled()) {
                            est = c * (pFT.kest_repr * stand.pft.get(pFT.id).cmass_repr + kest_bg);
                        } else if (!Utils.negligible(stand.pft.get(pFT.id).cmass_repr)) {
                            est = c * (pFT.kest_pres + kest_bg);
                        } else {
                            est = c * kest_bg;
                        }
                    }

                    // guess2008 - scale est by the number of woody PFTs/species
                    // that can establish
                    // Otherwise, simply adding more PFTs or species would
                    // increase est
                    est *= 3.0 / nwoodypfts_estab;

                    // Have a value for expected number of new saplings (est)
                    // Actual number of new saplings drawn from the Poisson
                    // distribution
                    // (except cohort mode with stochastic establishment
                    // disabled)

                    if (getConfiguration().isEstablishmentStochastic()
                            || getConfiguration().getVegmode() == VegetationMode.INDIVIDUAL) {
                        nsapling = randpoisson(est);
                    } else {
                        nsapling = est;
                    }

                    if (getConfiguration().getVegmode() == VegetationMode.COHORT) {

                        // BLARP added for OECD experiment (is this sensible?)
                        if (getConfiguration().isDisturbanceEnabled() && patch.disturbed) {

                            patch.pft.get(pFT.id).anetps_ff_est = patch.pft.get(pFT.id).anetps_ff_est_initial;
                            patch.pft.get(pFT.id).wscal_mean_est = patch.pft.get(pFT.id).wscal_mean;
                            newindiv = !Utils.negligible(nsapling) ? 1 : 0;
                        } else if ((patch.age % getConfiguration().getEstablishmentInterval()) != 0) {

                            // Not an establishment year - save sapling count
                            // for
                            // establishment the next establishment year

                            patch.pft.get(pFT.id).nsapling += nsapling;
                            newindiv = 0;
                        } else {
                            if (patch.age != 0) // all except first year after
                            // disturbance
                            {
                                nsapling += patch.pft.get(pFT.id).nsapling;
                                patch.pft.get(pFT.id).anetps_ff_est /= getConfiguration()
                                        .getEstablishmentInterval();
                                patch.pft.get(pFT.id).wscal_mean_est /= getConfiguration()
                                        .getEstablishmentInterval();
                            }
                            newindiv = !Utils.negligible(nsapling) ? 1 : 0;
                            // round down to 0 if nsapling very small
                        }
                    } else if (getConfiguration().getVegmode() == VegetationMode.INDIVIDUAL) {
                        newindiv = (int) (nsapling + 0.5); // round up to be on
                        // the safe side
                    }

                    // Now create 'newindiv' new Individual objects
                    for (i = 0; i < newindiv; i++) {

                        // Create average individual for a new cohort (cohort
                        // mode)
                        // or an actual individual (individual mode)

                        Individual indiv = new Individual(pFT, vegetation);
                        vegetation.add(indiv);

                        if (getConfiguration().getVegmode() == VegetationMode.COHORT) {
                            indiv.densindiv = nsapling / patch.area;
                        } else if (getConfiguration().getVegmode() == VegetationMode.INDIVIDUAL) {
                            indiv.densindiv = 1.0 / patch.area;
                        }

                        indiv.age = 0.0;

                        // Initial biomass proportional to potential forest
                        // floor net
                        // assimilation for this PFT in this patch

                        bminit = SAPSIZE * patch.pft.get(pFT.id).anetps_ff_est;

                        // Initial leaf to fine root biomass ratio based on
                        // hypothetical
                        // value of water stress parameter

                        ltor = patch.pft.get(pFT.id).wscal_mean_est * pFT.ltor_max;

                        // Allocate initial biomass
                        allocation_init(bminit, ltor, indiv);

                        // Calculate initial allometry

                        allometry(indiv);

                        // Account for C flux from atmosphere to vegetation
                        // guess2008
                        if (indiv.alive) {
                            patch.fluxes.acflux_est -= indiv.cmass_leaf + indiv.cmass_root + indiv.cmass_sap;
                        }
                    }
                }
            }

            // Reset running sums for next year (establishment years only in
            // cohort mode)

            if (getConfiguration().getVegmode() != VegetationMode.COHORT
                    || ((patch.age % getConfiguration().getEstablishmentInterval()) == 0)) {
                patch.pft.get(pFT.id).nsapling = 0.0;
                patch.pft.get(pFT.id).wscal_mean_est = 0.0;
                patch.pft.get(pFT.id).anetps_ff_est = 0.0;
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // MORTALITY
    // Internal functions (do not call directly from framework)

    private void mortality_lpj(Stand stand, Patch patch, Climate climate, double fireprob) {

        // DESCRIPTION
        // Mortality in population (standard LPJ) mode.
        // Simulates population decrease through mortality each simulation year
        // for trees
        // and grasses; "kills" PFT populations if climate conditions become
        // unsuitable for
        // survival.
        // This function assumes each Individual object represents the average
        // individual
        // of a PFT population, and that there is (at most) one Individual
        // object per PFT
        // per modelled area (stand and patch).

        // For tree PFTs, the fraction of the population killed is given by:
        //
        // (1) mort = min ( mort_greff + mort_shade + mort_fire, 1)
        // where mort_greff = mortality preempted by low growth efficiency,
        // given by:
        // (2) mort_greff = K_MORT1 / (1 + K_MORT2*greff)
        // (3) greff = annual_npp / leaf_area
        // (4) leaf_area = cmass_leaf * sla
        // mort_shade = mortality due to shading ("self thinning") as total tree
        // cover
        // approaches 1 (see code)
        // mort_fire = mortality due to fire; the fraction of the modelled area
        // affected by
        // fire (fireprob) is calculated in function fire; actual mortality is
        // influenced
        // by PFT-specific resistance to burning (see code).
        //
        // Grasses are subject to shading and fire mortality only

        // References: Sitch et al 2001, Smith et al 2001, Thonicke et al 2001

        // INPUT PARAMETER
        // fireprob = fraction of modelled area affected by fire

        final double K_MORT1 = 0.01; // constant in mortality equation [c.f.
        // mort_max; LPJF]
        final double K_MORT2 = 35.0;
        // constant in mortality equation [c.f. k_mort; LPJF]; the value here
        // differs
        // from LPJF's k_mort in that growth efficiency here based on annual
        // NPP, c.f.
        // net growth increment; LPJF
        final double FPC_TREE_MAX = 0.95; // maximum summed tree fractional
        // projective cover

        double fpc_dec; // required reduction in FPC
        double fpc_tree; // summed FPC for all tree PFTs
        double fpc_grass; // summed FPC for all grasses
        double deltafpc_tree_total; // total tree increase in FPC this year
        double mort;
        // total mortality for PFT (fraction of current FPC)
        double mort_greffic;
        // background mortality plus mortality preempted by low growth
        // efficiency
        // (fraction of current FPC)
        double mort_shade;
        // mortality associated with light competition (fraction of current FPC)
        double mort_fire;

        Vegetation vegetation = patch.vegetation;

        // Calculate total tree and grass FPC and total increase in FPC this
        // year for trees

        fpc_tree = 0.0;
        fpc_grass = 0.0;
        deltafpc_tree_total = 0.0;

        // Loop through individuals

        for (Individual indiv : vegetation) {

            // For this individual ...

            if (indiv.pft.lifeform == LifeForm.TREE) {

                fpc_tree += indiv.fpc;
                if (indiv.deltafpc < 0.0) {
                    indiv.deltafpc = 0.0;
                }
                deltafpc_tree_total += indiv.deltafpc;

            } else if (indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP) {
                fpc_grass += indiv.fpc;
            }
        }

        // KILL PFTs BEYOND BIOCLIMATIC LIMITS FOR SURVIVAL
        int ind = 0;
        for (Individual indiv : vegetation) {

            if (!survive(climate, indiv.pft)) {

                // Remove completely if PFT beyond its bioclimatic limits for
                // survival

                patch.pft.get(indiv.pft.id).litter_leaf += indiv.cmass_leaf;
                patch.pft.get(indiv.pft.id).litter_root += indiv.cmass_root;
                patch.pft.get(indiv.pft.id).litter_wood += indiv.cmass_sap + indiv.cmass_heart
                        - indiv.cmass_debt;

                vegetation.kill(ind);
            }

            ind++;
        }

        /*
         * remove the dead
         */
        vegetation.reap();

        // CALCULATE AND IMPLEMENT MORTALITY
        ind = 0;
        for (Individual indiv : vegetation) {

            if (indiv.pft.lifeform == LifeForm.TREE) {

                // TREE MORTALITY

                // Mortality associated with low growth efficiency (includes
                // parameterisation of population background mortality)
                // NOTE: growth efficiency quantified as (annual NPP)/(leaf
                // area)
                // c.f. LPJF, which uses net growth increment instead of NPP

                mort_greffic = K_MORT1
                        / (1.0 + K_MORT2 * Math.max(indiv.anpp, 0.0) / indiv.cmass_leaf / indiv.pft.sla);

                // Mortality associated with light competition
                // Self thinning imposed when total tree cover above
                // FPC_TREE_MAX,
                // partitioned among tree PFTs in proportion to this year's FPC
                // increment

                if (fpc_tree > FPC_TREE_MAX) {
                    if (!Utils.negligible(deltafpc_tree_total)) {
                        fpc_dec = (fpc_tree - FPC_TREE_MAX) * indiv.deltafpc / deltafpc_tree_total;
                    } else {
                        fpc_dec = 0.0;
                    }
                    mort_shade = 1.0 - fracmass_lpj(indiv.fpc - fpc_dec, indiv.fpc, indiv);
                } else {
                    mort_shade = 0.0;
                }

                // Mortality due to fire

                if (getConfiguration().isFireEnabled()) {
                    mort_fire = fireprob * (1.0 - indiv.pft.fireresist);
                } else {
                    mort_fire = 0.0;
                }

                // Sum mortality components to give total mortality (maximum 1)

                mort = Math.min(mort_greffic + mort_shade + mort_fire, 1.0);

                // Transfer killed biomass to litter
                // (above-ground biomass killed by fire enters atmosphere, not
                // litter)

                patch.pft.get(indiv.pft.id).litter_leaf += (mort - mort_fire) * indiv.cmass_leaf;
                patch.pft.get(indiv.pft.id).litter_wood += (mort - mort_fire)
                        * (indiv.cmass_sap + indiv.cmass_heart - indiv.cmass_debt);
                patch.pft.get(indiv.pft.id).litter_root += mort * indiv.cmass_root;

                // Flux to atmosphere from burnt above-ground biomass

                patch.fluxes.acflux_fire += mort_fire
                        * (indiv.cmass_leaf + indiv.cmass_sap + indiv.cmass_heart - indiv.cmass_debt);

                // Reduce population density and C biomass on modelled area
                // basis
                // to account for loss of killed individuals

                indiv.densindiv *= 1.0 - mort;
                indiv.cmass_leaf *= 1.0 - mort;
                indiv.cmass_root *= 1.0 - mort;
                indiv.cmass_sap *= 1.0 - mort;
                indiv.cmass_debt *= 1.0 - mort;
                indiv.cmass_heart *= 1.0 - mort;
            } else if (indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP) {

                // GRASS MORTALITY

                // Shading mortality: grasses can persist only on regions not
                // occupied
                // by trees

                if (fpc_grass > 1.0 - Math.min(fpc_tree, FPC_TREE_MAX)) {
                    fpc_dec = (fpc_grass - 1.0 + Math.min(fpc_tree, FPC_TREE_MAX)) * indiv.fpc / fpc_grass;
                    mort_shade = 1.0 - fracmass_lpj(indiv.fpc - fpc_dec, indiv.fpc, indiv);
                } else {
                    mort_shade = 0.0;
                }

                // if (mort_shade > 0.0) {
                // /*
                // * ??? the c++ code contains exactly this:
                // */
                // mort_shade = mort_shade;
                // }

                // Mortality due to fire
                if (getConfiguration().isFireEnabled()) {
                    mort_fire = fireprob * (1.0 - indiv.pft.fireresist);
                } else {
                    mort_fire = 0.0;
                }

                // Sum mortality components to give total mortality (maximum 1)

                mort = Math.min(mort_shade + mort_fire, 1.0);

                // Transfer killed biomass to litter
                // (above-ground biomass killed by fire enters atmosphere, not
                // litter)

                patch.pft.get(indiv.pft.id).litter_leaf += (mort - mort_fire) * indiv.cmass_leaf;
                patch.pft.get(indiv.pft.id).litter_root += mort * indiv.cmass_root;

                // Flux to atmosphere from burnt above-ground biomass

                patch.fluxes.acflux_fire += mort_fire * indiv.cmass_leaf;

                // Reduce C biomass on modelled area basis to account for
                // biomass lost
                // through mortality

                indiv.cmass_leaf *= 1.0 - mort;
                indiv.cmass_root *= 1.0 - mort;

            }

            // Remove this PFT population completely if all individuals killed

            if (Utils.negligible(indiv.densindiv)) {
                vegetation.kill(ind);
            }

            // Update allometry
            else {
                allometry(indiv);
            }

            ind++;
        }

        /*
         * remove all dead
         */
        vegetation.reap();
    }

    // DESCRIPTION
    // Mortality in cohort and individual modes.
    // Simulates death of individuals or reduction in individual density through
    // mortality (including fire mortality) each simulation year for trees, and
    // reduction in biomass due to fire for grasses; kills individuals/cohorts
    // if
    // climate conditions become unsuitable for survival.
    //
    // For trees, death can result from the following factors:
    //
    // (1) a background mortality rate related to PFT mean longevity;
    // (2) when mean growth efficiency over an integration period (five years)
    // falls
    // below a PFT-specific threshold;
    // (3) stress associated with high temperatures (a "soft" bioclimatic limit,
    // intended mainly for boreal tree PFTs; Sitch et al 2001, Eqn 55);
    // (4) fire (probability calculated in function fire)
    // (5) when climatic conditions are such that all individuals of the PFT are
    // killed.
    //
    // For grasses, (4) and (5) above are modelled only.
    //
    // For trees and in cohort mode, mortality may be stochastic or
    // deterministic
    // (according to the value of global variable ifstochmort). In stochastic
    // mode,
    // expected mortality rates are imposed as stochastic probabilities of
    // death; in
    // deterministic mode, cohort density is reduced by the fraction represented
    // by
    // the mortality rate.

    // BACKGROUND MORTALITY
    //
    // This is now modelled as an increasing probability of death with
    // age. The expected likelihood of death by "background" factors at a
    // given age is given by the general relationship [** = raised to the
    // power of]:
    // (1) mort(age) = min( Z * ( age / longevity ) ** Q , 1)
    // where Z is a constant (value derived below);
    // Q is a positive constant
    // (or zero for constant mortality with age)
    // longevity is the age at which the fraction of the initial
    // cohort expected to survive is a known value, F
    // It is possible to derive the value of Z given values for longevity,
    // Q and F, by integration:
    //
    // The rate of change of cohort size (P) at any age is given by:
    // (2) dP/dage = -mort(age) * P
    // Combining (1) and (2),
    // (3) dP/dage = -Z.(age/longevity)**Q.P
    // From (3),
    // (4) (1/P) dP = -Z.(age/longevity)**Q.dage
    // Integrating the LHS of eqn 4 from P_0 (initial cohort size) to
    // P_longevity (cohort size at age=longevity); and the RHS of eqn 4
    // from 0 to longevity:
    // (5) LHS = ln(P_longevity) - ln(P_0)
    // (6) = ln(P_longevity/P_0)
    // (7) = ln(P_0*F/P_0)
    // (8) = ln(F)
    // (9) RHS = integral[0,longevity] -Z.(age/longevity)**Q.dage
    // (10) = integral[0,longevity] -Z.(1/longevity)**Q.age**Q.dage
    // (11) = -Z.(1/longevity)**Q.integral[0,longevity] age**Q.dage
    // (12) = -Z.(1/longevity)**Q.longevity**(Q+1)/(Q+1)
    // (Zwillinger 1996, p 360)
    // Combining (8) and (12),
    // (13) Z = - ln(F) * (Q+1) / longevity
    // Combining (1) and (13),
    // (14) mort(age) =
    // min ( -ln(F) * (Q+1) / longevity * (age/longevity)**P, 1)
    private void mortality_guess(Stand stand, Patch patch, Climate climate, double fireprob) {

        // INPUT PARAMETER
        // fireprob = probability of fire in this patch
        double mort;
        // overall mortality (excluding fire mortality): fraction of cohort
        // killed, or:
        // probability of individual being killed
        double mort_min;
        // background component of overall mortality (see 'mort')
        double mort_greff;
        // component of overall mortality associated with low growth efficiency
        double mort_fire;
        // expected fraction of cohort killed (or: probability of individual
        // being
        // killed) due to fire
        double frac_survive;
        // fraction of cohort (or individual) surviving
        double greff;
        // growth efficiency for individual/cohort this year (kgC/m2 leaf/year)
        double greff_mean;
        // five-year-mean growth efficiency (kgC/m2 leaf/year)
        int nindiv; // number of individuals (remaining) in cohort
        int nindiv_prev; // number of individuals in cohort prior to mortality
        int startyear;
        // first year for calculation of five-year-mean growth efficiency
        int y;
        int i;

        final double KMORTGREFF = 0.3;
        // value of mort_greff when growth efficiency below PFT-specific
        // threshold
        final double KMORTBG_LNF = -Math.log(0.001);
        // coefficient in calculation of background mortality (negated natural
        // log of
        // fraction of population expected to survive to age 'longevity'; see
        // Eqn 14
        // below)
        final double KMORTBG_Q = 2.0;
        // exponent in calculation of background mortality (shape parameter for
        // relationship between mortality and age (0=constant mortality;
        // 1=linear
        // increase; >1->exponential; steepness increases for increasing
        // positive
        // values)

        // Obtain reference to Vegetation object for this patch

        Vegetation vegetation = patch.vegetation;

        // FIRE MORTALITY

        if (getConfiguration().isFireEnabled()) {

            // Impose fire in this patch with probability 'fireprob'

            if (Math.random() < fireprob) {

                int ind = 0;
                for (Individual indiv : vegetation) {

                    // Fraction of biomass destroyed by fire
                    mort_fire = 1.0 - indiv.pft.fireresist;

                    if (indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP) {

                        // GRASS PFT
                        // Transfer killed biomass from leaves to atmosphere,
                        // roots to litter

                        patch.fluxes.acflux_fire += mort_fire * indiv.cmass_leaf;
                        patch.pft.get(indiv.pft.id).litter_root += mort_fire * indiv.cmass_root;

                        indiv.cmass_leaf *= indiv.pft.fireresist;
                        indiv.cmass_root *= indiv.pft.fireresist;

                        // Update allometry

                        allometry(indiv);

                    } else {

                        // TREE PFT

                        if (getConfiguration().isMortalityStochastic()) {

                            // Impose stochastic mortality
                            // Each individual in cohort dies with probability
                            // 'mort_fire'

                            // Number of individuals represented by 'indiv'
                            // (round up to be on the safe side)

                            nindiv = (int) (indiv.densindiv * patch.area + 0.5);
                            nindiv_prev = nindiv;

                            for (i = 0; i < nindiv_prev; i++) {
                                if (Math.random() > indiv.pft.fireresist) {
                                    nindiv--;
                                }
                            }

                            if (nindiv_prev != 0) {
                                frac_survive = (double) nindiv / (double) nindiv_prev;
                            } else {
                                frac_survive = 0.0;
                            }
                        }

                        // Deterministic mortality (cohort mode only)

                        else {
                            frac_survive = 1.0 - mort_fire;
                        }

                        // Calculate flux from biomass to atmosphere due to fire
                        // (flux from litter calculated in function fire)

                        patch.fluxes.acflux_fire += (1.0 - frac_survive)
                                * (indiv.cmass_leaf + indiv.cmass_sap + indiv.cmass_heart - indiv.cmass_debt);

                        // Transfer killed roots to litter

                        patch.pft.get(indiv.pft.id).litter_root += (1.0 - frac_survive) * indiv.cmass_root;

                        // Reduce individual density and biomass on patch area
                        // basis
                        // to account for loss of killed individuals

                        indiv.densindiv *= frac_survive;
                        indiv.cmass_leaf *= frac_survive;
                        indiv.cmass_root *= frac_survive;
                        indiv.cmass_sap *= frac_survive;
                        indiv.cmass_debt *= frac_survive;
                        indiv.cmass_heart *= frac_survive;

                        // Remove this cohort completely if all individuals
                        // killed
                        // (in individual mode: removes individual if killed)

                        if (Utils.negligible(indiv.densindiv)) {
                            vegetation.kill(ind);
                        }
                    }

                    ind++;
                }

                vegetation.reap();
            }
        }

        // MORTALITY NOT ASSOCIATED WITH FIRE
        int ind = 0;
        for (Individual indiv : vegetation) {

            // KILL INDIVIDUALS/COHORTS BEYOND BIOCLIMATIC LIMITS FOR SURVIVAL

            if (!survive(climate, indiv.pft)) {

                // Kill cohort/individual, transfer biomass to litter

                patch.pft.get(indiv.pft.id).litter_leaf += indiv.cmass_leaf;
                patch.pft.get(indiv.pft.id).litter_root += indiv.cmass_root;
                patch.pft.get(indiv.pft.id).litter_wood += indiv.cmass_sap + indiv.cmass_heart
                        - indiv.cmass_debt;

                vegetation.kill(ind);

            } else {

                if (indiv.pft.lifeform == LifeForm.TREE) {

                    // Calculate this year's growth efficiency
                    // Eqn 31, Smith et al 2001

                    if (!Utils.negligible(indiv.cmass_leaf)) {
                        greff = Math.max(indiv.anpp, 0.0) / indiv.cmass_leaf / indiv.pft.sla;
                    } else {
                        greff = 0.0;
                    }

                    // Calculate 5 year mean growth efficiency

                    greff_mean = greff;
                    startyear = _configuration.NYEARGREFF
                            - (int) Math.min(_configuration.NYEARGREFF - 1, indiv.age - 1);
                    for (y = startyear; y < _configuration.NYEARGREFF; y++) {
                        greff_mean += indiv.greff_5[y];
                        indiv.greff_5[y - 1] = indiv.greff_5[y];
                    }
                    indiv.greff_5[_configuration.NYEARGREFF - 1] = greff;
                    greff_mean /= Math.min(_configuration.NYEARGREFF, indiv.age);

                    // Eqn 14
                    mort_min = Math.min(1.0, KMORTBG_LNF * (KMORTBG_Q + 1) / indiv.pft.longevity
                            * Math.pow(indiv.age / indiv.pft.longevity, KMORTBG_Q));

                    // Growth suppression mortality
                    // Smith et al 2001; c.f. Pacala et al 1993, Eqn 5

                    // guess2008 - introduce a smoothly-varying mort_greff - 5
                    // is the exponent in the global validation
                    if (getConfiguration().isGrowthEfficiencySmoothed()) {
                        mort_greff = KMORTGREFF / (1.0 + Math.pow((greff_mean / (indiv.pft.greff_min)), 5.0));
                    } else {
                        // Standard case, as in guess030124
                        if (greff_mean < indiv.pft.greff_min) {
                            mort_greff = KMORTGREFF;
                        } else {
                            mort_greff = 0.0;
                        }
                    }

                    // Increase growth efficiency mortality if summed crown area
                    // within
                    // cohort exceeds 1 (to ensure self-thinning for
                    // shade-tolerant PFTs)

                    if (getConfiguration().getVegmode() == VegetationMode.COHORT) {

                        if (indiv.crownarea * indiv.densindiv > 1.0) {
                            mort_greff = Math.max((indiv.crownarea * indiv.densindiv - 1.0)
                                    / (indiv.crownarea * indiv.densindiv), mort_greff);
                        }
                    }

                    // Overall mortality: c.f. Eqn 29, Smith et al 2001

                    mort = mort_min + mort_greff - mort_min * mort_greff;

                    // guess2008 - added safety check
                    if (mort > 1.0 || mort < 0.0) {
                    	Logging.INSTANCE.error("error in mortality_guess: bad mort value", null);
                    }

                    if (getConfiguration().isMortalityStochastic()) {

                        // Impose stochastic mortality
                        // Each individual in cohort dies with probability
                        // 'mort'

                        nindiv = (int) (indiv.densindiv * patch.area + 0.5);
                        nindiv_prev = nindiv;

                        for (i = 0; i < nindiv_prev; i++) {
                            if (Math.random() < mort) {
                                nindiv--;
                            }
                        }

                        if (nindiv_prev != 0) {
                            frac_survive = (double) nindiv / (double) nindiv_prev;
                        } else {
                            frac_survive = 0.0;
                        }
                    }

                    // Deterministic mortality (cohort mode only)

                    else {
                        frac_survive = 1.0 - mort;
                    }

                    // Transfer killed biomass to litter

                    patch.pft.get(indiv.pft.id).litter_leaf += (1.0 - frac_survive) * indiv.cmass_leaf;
                    patch.pft.get(indiv.pft.id).litter_root += (1.0 - frac_survive) * indiv.cmass_root;
                    patch.pft.get(indiv.pft.id).litter_wood += (1.0 - frac_survive)
                            * (indiv.cmass_sap + indiv.cmass_heart - indiv.cmass_debt);

                    // Reduce individual density and biomass on patch area basis
                    // to account for loss of killed individuals

                    indiv.densindiv *= frac_survive;
                    indiv.cmass_leaf *= frac_survive;
                    indiv.cmass_root *= frac_survive;
                    indiv.cmass_sap *= frac_survive;
                    indiv.cmass_debt *= frac_survive;
                    indiv.cmass_heart *= frac_survive;

                    // Remove this cohort completely if all individuals killed
                    // (in individual mode: removes individual if killed)

                    if (Utils.negligible(indiv.densindiv)) {
                        vegetation.kill(ind);
                    }

                    // Update allometry

                    else {
                        allometry(indiv);
                    }
                }
            }
            ind++;
        }

        vegetation.reap();
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // FIRE DISTURBANCE
    // Internal function (do not call directly from framework)

    private double fire(Patch patch, double fireprob) {

        // DESCRIPTION
        // Calculates probability/incidence of disturbance by fire; implements
        // volatilisation of above-ground litter due to fire.
        // Mortality due to fire implemented in mortality functions, not here.
        //
        // Reference: Thonicke et al 2001, with updated Eqn 9

        // OUTPUT PARAMETER
        // fireprob = probability of fire in this patch this year
        // (in population mode: fraction of modelled area affected by fire)

        final double PI = 3.14159265;
        final double MINFUEL = 0.2;
        // Minimum total aboveground litter required for fire (kgC/m2)
        double litter_ag;
        // total aboveground litter (kgC/m2) [litter_ag_total/1000, fuel/1000;
        // LPJF]
        double me_mean;
        // mean litter flammability moisture threshold [=moisture of extinction,
        // me;
        // Thonicke et al 2001 Eqn 2; moistfactor; LPJF]
        double pm;
        // probability of fire on a given day [p(m); Thonicke et al 2001, Eqn 2;
        // fire_prob; LPJF]
        double n;
        // summed length of fire season (days) [N; Thonicke et al 2001, Eqn 4;
        // fire_length; LPJF]
        double s;
        // annual mean daily probability of fire [s; Thonicke et al 2001, Eqn 8;
        // fire_index; LPJF]
        double sm; // s-1
        double mort_fire; // fire mortality as fraction of current FPC
        int day;
        int p;

        // Calculate fuel load (total aboveground litter)

        litter_ag = 0.0;

        // Loop through PFTs

        for (p = 0; p < getConfiguration().getPFTs().size(); p++) {
            litter_ag += patch.pft.get(p).litter_leaf + patch.pft.get(p).litter_wood
                    + patch.pft.get(p).litter_repr;
        }

        // Prevent fire if fuel load below prescribed threshold

        if (litter_ag < MINFUEL) {
            return 0.0;
        }

        // Calculate mean litter flammability moisture threshold

        me_mean = 0.0;

        // Loop through PFTs
        for (p = 0; p < getConfiguration().getPFTs().size(); p++) {
            me_mean += (patch.pft.get(p).litter_leaf + patch.pft.get(p).litter_wood
                    + patch.pft.get(p).litter_repr)
                    * patch.pft.get(p).pFT.litterme / litter_ag;
        }

        // Calculate length of fire season in days
        // Eqn 2, 4, Thonicke et al 2001
        // NOTE: error in Eqn 2, Thonicke et al - multiplier should be PI/4, not
        // PI

        // TODO: Update 365 to 366?
        n = 0.0;
        for (day = 0; day < 365; day++) {

            // Eqn 2
            pm = Math
                    .exp(-PI * patch.soil.dwcontupper[day] / me_mean * patch.soil.dwcontupper[day] / me_mean);

            // Eqn 4
            n += pm;
        }

        // Calculate fraction of grid cell burnt
        // Thonicke et al 2001, Eqn 9

        s = n / 365.0;
        sm = s - 1;

        fireprob = s * Math.exp(sm / (0.45 * sm * sm * sm + 2.83 * sm * sm + 2.96 * sm + 1.04));

        if (fireprob > 1.0) {
        	Logging.INSTANCE.error("fire: probability of fire >1.0", null);
        } else if (fireprob < 0.001) // c.f. LPJF
        {
            fireprob = 0.001;
        }

        // Calculate expected flux from litter due to fire
        // (fluxes from vegetation calculated in mortality functions)

        for (p = 0; p < getConfiguration().getPFTs().size(); p++) {

            // Assume litter is as fire resistant as the vegetation it
            // originates from

            mort_fire = fireprob * (1.0 - patch.pft.get(p).pFT.fireresist);

            // Calculate flux from burnt litter

            patch.fluxes.acflux_fire += mort_fire
                    * (patch.pft.get(p).litter_leaf + patch.pft.get(p).litter_wood
                            + patch.pft.get(p).litter_repr);

            // Account for burnt above ground litter

            patch.pft.get(p).litter_leaf *= 1.0 - mort_fire;
            patch.pft.get(p).litter_wood *= 1.0 - mort_fire;
            patch.pft.get(p).litter_repr *= 1.0 - mort_fire;
        }

        return fireprob;
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // DISTURBANCE
    // Generic patch-destroying disturbance with a prescribed probability
    // Internal function - do not call from framework

    private void disturbance(Patch patch, double disturb_prob) {

        // DESCRIPTION
        // Destroys all biomass in a patch with a certain stochastic
        // probability.
        // Biomass enters the litter, which is not affected by the disturbance.
        // NB: cohort and individual mode only

        // INPUT PARAMETER
        // disturb_prob = the probability of a disturbance this year

        if (Math.random() < disturb_prob) {

            Vegetation vegetation = patch.vegetation;

            int ind = 0;
            for (Individual indiv : vegetation) {

                patch.pft.get(indiv.pft.id).litter_leaf += indiv.cmass_leaf;
                patch.pft.get(indiv.pft.id).litter_root += indiv.cmass_root;
                patch.pft.get(indiv.pft.id).litter_wood += indiv.cmass_sap + indiv.cmass_heart
                        - indiv.cmass_debt;

                vegetation.kill(ind);
                ind++;
            }

            vegetation.reap();

            patch.disturbed = true;
            patch.age = 0;
        }

        else {
            patch.disturbed = false;
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // VEGETATION DYNAMICS
    // Should be called by framework at the end of each simulation year, after
    // vegetation,
    // climate and soil attributes have been updated

    @Override
    public void process(Patch patch) {
        // DataRecorder.get().info("Running veg dynamics!");

        Stand stand = patch.stand;

        // DESCRIPTION
        // Implementation of fire disturbance and population dynamics
        // (establishment and
        // mortality) at end of simulation year. Bioclimatic constraints to
        // survival and
        // establishment are imposed within mortality and establishment
        // functions
        // respectively.

        double fireprob = 0.0;
        // probability of fire in this patch this year
        // (in population mode: fraction of modelled area affected by fire this
        // year)

        // Calculate fire probability and volatilise litter
        if (getConfiguration().isFireEnabled()) {
            fireprob = fire(patch, fireprob);
            patch.fireprob = fireprob;
        }

        if (getConfiguration().getVegmode() == VegetationMode.POPULATION) {

            // POPULATION MODE

            // Mortality
            mortality_lpj(stand, patch, stand.getClimate(), fireprob);

            // Establishment
            establishment_lpj(stand, patch, getConfiguration().getPFTs());

        } else {

            // INDIVIDUAL AND COHORT MODES

            // Patch-destroying disturbance

            if (getConfiguration().isDisturbanceEnabled() && patch.age != 0) {
                disturbance(patch, 1.0 / getConfiguration().getDisturbanceInterval());
                if (patch.disturbed) {
                    return; // no mortality or establishment this year
                }

            }

            // Mortality
            mortality_guess(stand, patch, stand.getClimate(), fireprob);

            // Establishment
            establishment_guess(stand, patch, getConfiguration().getPFTs());
        }

        patch.age++;
    }

}
