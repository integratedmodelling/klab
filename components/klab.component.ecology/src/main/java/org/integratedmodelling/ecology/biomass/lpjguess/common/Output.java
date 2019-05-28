package org.integratedmodelling.ecology.biomass.lpjguess.common;

import java.util.List;

import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.PFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.PatchPFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Stand;
import org.integratedmodelling.ecology.biomass.lpjguess.StandPFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Vegetation;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.VegetationMode;
import org.integratedmodelling.procsim.api.IOutputManager;

public class Output implements IOutputManager {

    IConfiguration _configuration;
    Stand          stand;

    int OUTPUT_MAXAGECLASS = 2000;

    public int    p, c, m, nclass;
    public double cmass_stand, anpp_stand, lai_stand, runoff_stand, dens_stand;
    public double flux_veg, flux_soil, flux_fire, flux_est;
    public double c_litter, c_fast, c_slow;
    public double firert_stand;

    // public // guess2008 - hold the monthly average across patches
    public double[] mnpp         = new double[12];
    public double[] mgpp         = new double[12];
    public double[] mlai         = new double[12];
    public double[] maet         = new double[12];
    public double[] mpet         = new double[12];
    public double[] mevap        = new double[12];
    public double[] mintercep    = new double[12];
    public double[] mrunoff      = new double[12];
    public double[] mrh          = new double[12];
    public double[] mra          = new double[12];
    public double[] mnee         = new double[12];
    public double[] mwcont_upper = new double[12];
    public double[] mwcont_lower = new double[12];

    public Output(Stand s, IConfiguration c) {
        _configuration = c;
        stand = s;
    }

    public void annualStandOutput() {
        // Decide how many classes to split the age profile into if we're in
        // Cohort mode
        if (_configuration.getVegmode() == VegetationMode.COHORT) {
            nclass = Math.min(_configuration.getSchedule().year() / _configuration.getEstablishmentInterval()
                    + 1, OUTPUT_MAXAGECLASS);
        }

        List<PFT> PFTs = _configuration.getPFTs();

        double npatch = stand.npatch();

        cmass_stand = 0.0;
        anpp_stand = 0.0;
        lai_stand = 0.0;
        runoff_stand = 0.0;
        dens_stand = 0.0;
        firert_stand = 0.0;

        // guess2008 - reset monthly average across patches each year
        for (m = 0; m < 12; m++) {
            mnpp[m] = mlai[m] = mgpp[m] = mra[m] = maet[m] = mpet[m] = mevap[m] = mintercep[m] = mrunoff[m] = mrh[m] = mnee[m] = mwcont_upper[m] = mwcont_lower[m] = 0.0;
        }

        // Loop through PFTs
        for (PFT pft : PFTs) {
            StandPFT standpft = stand.getPFT(pft.id);

            standpft.cmass_total = 0.0;
            standpft.anpp_total = 0.0;
            standpft.lai_total = 0.0;
            standpft.densindiv_total = 0.0;

            // Initialise age structure array

            if (_configuration.getVegmode() == VegetationMode.COHORT
                    || _configuration.getVegmode() == VegetationMode.INDIVIDUAL) {
                for (c = 0; c < nclass; c++) {
                    standpft.densindiv_ageclass[c] = 0.0;
                }
            }

            for (Patch patch : stand.getPatches()) {
                Vegetation vegetation = patch.vegetation;
                PatchPFT patchpft = patch.getPFT(pft.id);

                for (Individual indiv : vegetation) {
                    // guess2008 - alive check added
                    // TODO: REPLACE! Bad idea!
                    // if (indiv.id != -1 && indiv.alive) {
                    if (true) {

                        // If it's not dead and has existed for at least one
                        // year.

                        if (indiv.pft.id == pft.id) {
                            standpft.cmass_total += indiv.cmass_leaf + indiv.cmass_root + indiv.cmass_sap
                                    + indiv.cmass_heart - indiv.cmass_debt;
                            standpft.anpp_total += indiv.anpp;
                            standpft.lai_total += indiv.lai;

                            if (_configuration.getVegmode() == VegetationMode.COHORT
                                    || _configuration.getVegmode() == VegetationMode.INDIVIDUAL) {

                                // Age structure

                                c = (int) (indiv.age / _configuration.getEstablishmentInterval()); // guess2008
                                if (c < OUTPUT_MAXAGECLASS) {
                                    standpft.densindiv_ageclass[c] += indiv.densindiv;
                                }

                                // guess2008 - only count trees with a trunk
                                // above a certain diameter
                                if (pft.lifeform == LifeForm.TREE && indiv.age > 0) {
                                    double diam = Math
                                            .pow(indiv.height / indiv.pft.k_allom2, 1.0 / indiv.pft.k_allom3);
                                    if (diam > 0.03) {
                                        standpft.densindiv_total += indiv.densindiv; // indiv/m2
                                    }
                                }
                            }
                        }
                    }
                }

            } // end of patch loop

            standpft.cmass_total /= npatch;
            standpft.anpp_total /= npatch;
            standpft.lai_total /= npatch;
            standpft.densindiv_total /= npatch;

            // Update stand totals

            cmass_stand += standpft.cmass_total;
            anpp_stand += standpft.anpp_total;
            lai_stand += standpft.lai_total;
            dens_stand += standpft.densindiv_total;

            // Print PFT sums to files
            // TODO: Alter this to get these values and store them somewhere
            // useful!

            // if (out_cmass) {
            // fprintf(out_cmass, "%8.3f", standpft.cmass_total);
            // }
            // if (out_anpp) {
            // fprintf(out_anpp, "%8.3f", standpft.anpp_total);
            // }
            // if (out_lai) {
            // fprintf(out_lai, "%8.4f", standpft.lai_total);
            // }
            // if (out_dens) {
            // fprintf(out_dens, "%8.4f", standpft.densindiv_total);
            // }

        } // *** End of PFT loop ***

        flux_veg = flux_soil = flux_fire = flux_est = 0.0;

        // guess2008 - carbon pools
        c_litter = c_fast = c_slow = 0.0;

        // Sum C fluxes, dead C pools and runoff across patches

        for (Patch patch : stand.getPatches()) {
            flux_veg += patch.fluxes.acflux_veg / npatch;
            flux_soil += patch.fluxes.acflux_soil / npatch;
            flux_fire += patch.fluxes.acflux_fire / npatch;
            flux_est += patch.fluxes.acflux_est / npatch;

            c_fast += patch.soil.cpool_fast / npatch;
            c_slow += patch.soil.cpool_slow / npatch;

            // Sum all litter
            for (PFT pft : PFTs) {
                PatchPFT patchpft = patch.getPFT(pft.id);
                c_litter += (patchpft.litter_leaf + patchpft.litter_root + patchpft.litter_wood
                        + patchpft.litter_repr)
                        / npatch;
            }

            runoff_stand += patch.arunoff / npatch;

            // Record the fire return time (years)
            if (!_configuration.isFireEnabled() || patch.fireprob < 0.001) {
                firert_stand += 1000.0 / npatch; // Set a limit of 1000 years
            } else {
                firert_stand += (1.0 / patch.fireprob) / npatch;
            }

            // Monthly output variables

            for (m = 0; m < 12; m++) {
                maet[m] += patch.maet[m] / npatch;
                mpet[m] += patch.mpet[m] / npatch;
                mevap[m] += patch.mevap[m] / npatch;
                mintercep[m] += patch.mintercep[m] / npatch;
                mrunoff[m] += patch.mrunoff[m] / npatch;
                mrh[m] += patch.fluxes.mcflux_soil[m] / npatch;

                mwcont_upper[m] += patch.soil.mwcont[m][0] / npatch;
                mwcont_lower[m] += patch.soil.mwcont[m][1] / npatch;

                // guess2008 - average across stands to get mgpp and mra
                mgpp[m] += patch.fluxes.mcflux_gpp[m] / npatch;
                mra[m] += patch.fluxes.mcflux_ra[m] / npatch;

            }

            // Calculate monthly NPP and LAI

            Vegetation vegetation = patch.vegetation;

            for (Individual indiv : vegetation) {

                // guess2008 - alive check added
                if (indiv.id != -1 && indiv.alive) {

                    for (m = 0; m < 12; m++) {
                        mlai[m] += indiv.mlai[m] / npatch;
                    }

                } // alive?

            } // vegetation loop

        } // patch loop

        // In contrast to annual NEE, monthly NEE does not include fire
        // or establishment fluxes
        double testmnpp = 0.0;
        double testmlai = 0.0;

        for (m = 0; m < 12; m++) {
            mnpp[m] = mgpp[m] - mra[m];
            mnee[m] = mnpp[m] - mrh[m];
            testmnpp += mnpp[m];
            testmlai += mlai[m] / 12.0;
        }

        // // Print stand totals to files
        //
        // if (out_cmass) fprintf(out_cmass,"%8.3f\n",cmass_stand);
        // if (out_anpp) fprintf(out_anpp,"%8.3f\n",anpp_stand);
        // if (out_lai) fprintf(out_lai,"%8.4f\n",lai_stand);
        // if (out_runoff) fprintf(out_runoff,"%8.1f\n",runoff_stand);
        // if (out_dens) fprintf(out_dens,"%8.4f\n",dens_stand);
        // if (out_firert) fprintf(out_firert,"%8.1f\n",firert_stand);
        //
        // // Print monthly output variables
        // for (m=0;m<12;m++) {
        //
        // if (out_mnpp) fprintf(out_mnpp,"%8.3f",mnpp[m]);
        // if (out_mlai) fprintf(out_mlai,"%8.3f",mlai[m]);
        // if (out_mgpp) fprintf(out_mgpp,"%8.3f",mgpp[m]);
        // if (out_mra) fprintf(out_mra,"%8.3f",mra[m]);
        // if (out_maet) fprintf(out_maet,"%8.3f",maet[m]);
        // if (out_mpet) fprintf(out_mpet,"%8.3f",mpet[m]);
        // if (out_mevap) fprintf(out_mevap,"%8.3f",mevap[m]);
        // if (out_mintercep) fprintf(out_mintercep,"%8.3f",mintercep[m]);
        // if (out_mrunoff) fprintf(out_mrunoff,"%10.3f",mrunoff[m]);
        // if (out_mrh) fprintf(out_mrh,"%8.3f",mrh[m]);
        // if (out_mnee) fprintf(out_mnee,"%8.3f",mnee[m]);
        // if (out_mwcont_upper)
        // fprintf(out_mwcont_upper,"%8.3f",mwcont_upper[m]);
        // if (out_mwcont_lower)
        // fprintf(out_mwcont_lower,"%8.3f",mwcont_lower[m]);
        //
        // if (m==11) {
        // if (out_mnpp) fprintf(out_mnpp,"\n");
        // if (out_mlai) fprintf(out_mlai,"\n");
        // if (out_mgpp) fprintf(out_mgpp,"\n");
        // if (out_mra) fprintf(out_mra,"\n");
        // if (out_maet) fprintf(out_maet,"\n");
        // if (out_mpet) fprintf(out_mpet,"\n");
        // if (out_mevap) fprintf(out_mevap,"\n");
        // if (out_mintercep) fprintf(out_mintercep,"\n");
        // if (out_mrunoff) fprintf(out_mrunoff,"\n");
        // if (out_mrh) fprintf(out_mrh,"\n");
        // if (out_mnee) fprintf(out_mnee,"\n");
        // if (out_mwcont_upper) fprintf(out_mwcont_upper,"\n");
        // if (out_mwcont_lower) fprintf(out_mwcont_lower,"\n");
        // }
        //
        // }

        // Write fluxes to file
        // if (out_cflux)
        // fprintf(out_cflux,"%8.3f%8.3f%8.3f%8.3f%10.5f\n",flux_veg,flux_soil,flux_fire,
        // flux_est,flux_veg+flux_soil+flux_fire+flux_est);
        //
        // // guess2008 - output carbon pools
        // if (out_cpool)
        // fprintf(out_cpool,"%8.3f%8.3f%8.3f%8.3f%10.4f\n",cmass_stand,c_litter,c_fast,
        // c_slow,cmass_stand+c_litter+c_fast+c_slow);

        // Output of age structure (Windows shell only - no effect otherwise)

        // if (vegmode==COHORT || vegmode==INDIVIDUAL) {
        //
        // if (!(date.year%20) && date.year<2000) {
        //
        // resetwindow("age_structure");
        //
        // pftlist.firstobj();
        // while (pftlist.isobj) {
        // Pft& pft=pftlist.getobj();
        //
        // if (pft.lifeform==TREE) {
        //
        // Standpft& standpft=stand.pft[pft.id];
        //
        // for (c=0;c<nclass;c++)
        // plot("age_structure",pft.name,
        // c*estinterval+estinterval/2,
        // standpft.densindiv_ageclass[c]/(double)npatch);
        // }
        //
        // pftlist.nextobj();
    } // function
}
