package org.integratedmodelling.ecology.biomass.lpjguess.common;

import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.LandcoverType;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.PatchPFT;
import org.integratedmodelling.ecology.biomass.lpjguess.PerPatchFluxType;

public class Harvest_CN {

	public double cmass_leaf;
	public double cmass_root;
	public double cmass_sap;
	public double cmass_heart;
	public double cmass_debt;
	public double cmass_ho;
	public double cmass_agpool;
	public double cmass_stem;
	public double cmass_dead_leaf;
	public double debt_excess;
	public double nmass_leaf;
	public double nmass_root;
	public double nmass_sap;
	public double nmass_heart;
	public double nmass_ho;
	public double nmass_agpool;
	public double nmass_dead_leaf;
	public double nstore_longterm;
	public double nstore_labile;
	public double max_n_storage;

	public double litter_leaf;
	public double litter_root;
	public double litter_sap;
	public double litter_heart;
	public double nmass_litter_leaf;
	public double nmass_litter_root;
	public double nmass_litter_sap;
	public double nmass_litter_heart;
	public double acflux_harvest;
	public double anflux_harvest;
	public double harvested_products_slow;
	public double harvested_products_slow_nmass;

	
	public Harvest_CN() {
		cmass_leaf = cmass_root = cmass_sap = cmass_heart = cmass_debt = cmass_ho = cmass_agpool = cmass_stem = cmass_dead_leaf = debt_excess = 0.0;
		nmass_leaf = nmass_root = nmass_sap = nmass_heart = nmass_ho = nmass_agpool = nmass_dead_leaf = nstore_longterm = nstore_labile = max_n_storage = 0.0;
		litter_leaf = litter_root = litter_sap = litter_heart = 0.0;
		nmass_litter_leaf = nmass_litter_root = nmass_litter_sap = nmass_litter_heart = 0.0;
		acflux_harvest = anflux_harvest = 0.0;
		harvested_products_slow = harvested_products_slow_nmass = 0.0;
	}
	
	/// Copies C and N values from individual and patchpft tp struct. 
	public void copy_from_indiv(Individual indiv) {
		copy_from_indiv(indiv, false, true);
	}
	
	public void copy_from_indiv(Individual indiv, boolean copy_grsC) {
		copy_from_indiv(indiv, copy_grsC, true);
	}
	
	public void copy_from_indiv(Individual indiv, boolean copy_grsC, boolean copy_dead_C) {

		Patch patch = indiv.vegetation.patch;
		PatchPFT ppft = patch.pft.get(indiv.pft.id);

		if(copy_grsC) {

			if(indiv.cropindiv != null) {

				cmass_leaf = indiv.cropindiv.grs_cmass_leaf;
				cmass_root = indiv.cropindiv.grs_cmass_root;

				if(indiv.pft.landcover == LandcoverType.CROP) {
					cmass_ho = indiv.cropindiv.grs_cmass_ho;
					cmass_agpool = indiv.cropindiv.grs_cmass_agpool;
					cmass_stem = indiv.cropindiv.grs_cmass_stem;
					cmass_dead_leaf = indiv.cropindiv.grs_cmass_dead_leaf;
				}
			}
		}
		else {

			cmass_leaf = indiv.cmass_leaf;
			cmass_root = indiv.cmass_root;
			cmass_sap = indiv.cmass_sap;
			cmass_heart = indiv.cmass_heart;
			cmass_debt = indiv.cmass_debt;

			if(indiv.pft.landcover == LandcoverType.CROP) {
				cmass_ho = indiv.cropindiv.cmass_ho;
				cmass_agpool = indiv.cropindiv.cmass_agpool;
//				cmass_stem = indiv.cropindiv.grs_cmass_stem;			// We can't use grs_cmass here !
//				cmass_dead_leaf = indiv.cropindiv.grs_cmass_dead_leaf;
			}
		}


		nmass_leaf = indiv.nmass_leaf;
		nmass_root = indiv.nmass_root;
		nmass_sap = indiv.nmass_sap;
		nmass_heart = indiv.nmass_heart;
		nstore_longterm = indiv.nstore_longterm;
		nstore_labile = indiv.nstore_labile;
		max_n_storage = indiv.max_n_storage;

		if(indiv.pft.landcover == LandcoverType.CROP) {
			nmass_ho = indiv.cropindiv.nmass_ho;
			nmass_agpool = indiv.cropindiv.nmass_agpool;
			nmass_dead_leaf = indiv.cropindiv.nmass_dead_leaf;
		}

		if(copy_dead_C) {

			litter_leaf = ppft.litter_leaf;
			litter_root = ppft.litter_root;
			litter_sap = ppft.litter_sap;
			litter_heart = ppft.litter_heart;

			nmass_litter_leaf = ppft.nmass_litter_leaf;
			nmass_litter_root = ppft.nmass_litter_root;
			nmass_litter_sap = ppft.nmass_litter_sap;
			nmass_litter_heart = ppft.nmass_litter_heart;

			// acflux_harvest and anflux_harvest only for output
			harvested_products_slow = ppft.harvested_products_slow;
			harvested_products_slow_nmass = ppft.harvested_products_slow_nmass;
		}
	}
	
	public void copy_to_indiv(Individual indiv) {
		copy_to_indiv(indiv, false, false);
	}
	
	public void copy_to_indiv(Individual indiv, boolean copy_grsC) {
		copy_to_indiv(indiv, copy_grsC, false);
	}
	/// Copies C and N values from struct to individual, patchpft and patch (fluxes).
	public void copy_to_indiv(Individual indiv, boolean copy_grsC, boolean lc_change) {

			Patch patch = indiv.vegetation.patch;
			PatchPFT ppft = patch.pft.get(indiv.pft.id);

			if(copy_grsC) {

				indiv.cropindiv.grs_cmass_leaf = cmass_leaf;
				indiv.cropindiv.grs_cmass_root = cmass_root;

				if(indiv.pft.landcover == LandcoverType.CROP) {
					indiv.cropindiv.grs_cmass_ho = cmass_ho;
					indiv.cropindiv.grs_cmass_agpool = cmass_agpool;
					indiv.cropindiv.grs_cmass_dead_leaf = cmass_dead_leaf;
					indiv.cropindiv.grs_cmass_stem = cmass_stem;
				}
			}
			else {

				indiv.cmass_leaf = cmass_leaf;
				indiv.cmass_root = cmass_root;
				indiv.cmass_sap = cmass_sap;
				indiv.cmass_heart = cmass_heart;
				indiv.cmass_debt = cmass_debt;

				if(indiv.pft.landcover == LandcoverType.CROP) {
					indiv.cropindiv.cmass_ho = cmass_ho;
					indiv.cropindiv.cmass_agpool = cmass_agpool;
//					indiv.cropindiv.grs_cmass_dead_leaf = cmass_dead_leaf;	// We can't use grs_cmass here !
//					indiv.cropindiv.grs_cmass_stem = cmass_stem;
				}
			}

			indiv.nmass_leaf = nmass_leaf;
			indiv.nmass_root = nmass_root;
			indiv.nmass_sap = nmass_sap;
			indiv.nmass_heart = nmass_heart;
			indiv.nstore_longterm = nstore_longterm;
			indiv.nstore_labile = nstore_labile;

			if(indiv.pft.landcover == LandcoverType.CROP) {
				indiv.cropindiv.nmass_ho = nmass_ho;
				indiv.cropindiv.nmass_agpool = nmass_agpool;
				indiv.cropindiv.nmass_dead_leaf = nmass_dead_leaf;
			}

			ppft.litter_leaf = litter_leaf;
			ppft.litter_root = litter_root;
			ppft.litter_sap = litter_sap;
			ppft.litter_heart = litter_heart;
			ppft.nmass_litter_leaf = nmass_litter_leaf;
			ppft.nmass_litter_root = nmass_litter_root;
			ppft.nmass_litter_sap = nmass_litter_sap;
			ppft.nmass_litter_heart = nmass_litter_heart;

			if(!lc_change) {
				patch.fluxes.report_flux(PerPatchFluxType.HARVESTC, acflux_harvest);	// Put into gridcell.acflux_landuse_change instead at land use change
				patch.fluxes.report_flux(PerPatchFluxType.HARVESTN, anflux_harvest);	// Put into gridcell.anflux_landuse_change instead at land use change
			}

//			indiv.report_flux(Fluxes::NPP, debt_excess);
//			indiv.report_flux(Fluxes::RA, -debt_excess);

			ppft.harvested_products_slow = harvested_products_slow;
			ppft.harvested_products_slow_nmass = harvested_products_slow_nmass;
		}

}
