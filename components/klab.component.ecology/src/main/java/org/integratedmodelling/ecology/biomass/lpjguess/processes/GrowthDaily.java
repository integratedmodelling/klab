package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.CropPhen;
import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.LandcoverType;
import org.integratedmodelling.ecology.biomass.lpjguess.PFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.PatchPFT;
import org.integratedmodelling.ecology.biomass.lpjguess.PerPFTFluxType;
import org.integratedmodelling.ecology.biomass.lpjguess.PerPatchFluxType;
import org.integratedmodelling.ecology.biomass.lpjguess.SOMPoolType;
import org.integratedmodelling.ecology.biomass.lpjguess.Vegetation;
import org.integratedmodelling.ecology.biomass.lpjguess.common.CropIndiv;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Harvest_CN;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.SimProcess;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;

public class GrowthDaily extends SimProcess {

	public GrowthDaily(IConfiguration configuration) {
		super(configuration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Patch patch) {
		if (patch.stand.landcover == LandcoverType.CROP) {

			// allocate daily npp to leaf, roots and harvestable organs
			growth_crop_daily(patch);

			// update patchpft.lai_daily and fpc_daily
			lai_crop(patch);
		}

	}

	double senescence_curve(PFT pft, double fphu) {

		double senfactor;

		if (pft.shapesenescencenorm)
			senfactor = Math.pow(1 - fphu, 2) / Math.pow(1 - pft.fphusen, 2) * (1 - pft.flaimaxharvest)
					+ pft.flaimaxharvest;
		else
			senfactor = Math.pow(1 - fphu, 0.5) / Math.pow(1 - pft.fphusen, 0.5) * (1 - pft.flaimaxharvest)
					+ pft.flaimaxharvest;

		return senfactor;
	}

	// / Lambert-Beer extinction law (Prentice et al 1993; Monsi & Saeki 1953)
	double lambertbeer(double lai) {
		return Math.exp(-.5 * lai);
	}

	// / Updates lai_daily and fpc_daily from daily grs_cmass_leaf-value
	/**
	 * lai during senescence declines according the function senescence_curve()
	 */
	void lai_crop(Patch patch) {

		Vegetation vegetation = patch.vegetation;

		for (Individual indiv : vegetation) {
			CropIndiv cropindiv = indiv.getCropIndiv();
			PatchPFT patchpft = patch.pft.get(indiv.pft.id);
			CropPhen ppftcrop = patchpft.get_cropphen();

			if (indiv.pft.phenology == Phenology.CROPGREEN) {

				if (ppftcrop.growingseason) {

					if (!ppftcrop.senescence || _configuration.isNLimitedLC(LandcoverType.CROP))
						indiv.lai_daily = cropindiv.grs_cmass_leaf * indiv.pft.sla;
					else
						// Follow the senescence curve from leaf cmass at
						// senescence (cmass_leaf_sen):
						indiv.lai_daily = cropindiv.cmass_leaf_sen * indiv.pft.sla
								* senescence_curve(indiv.pft, ppftcrop.fphu);

					if (indiv.lai_daily < 0.0)
						indiv.lai_daily = 0.0;

					indiv.fpc_daily = 1.0 - lambertbeer(indiv.lai_daily);

					indiv.lai_indiv_daily = indiv.lai_daily;
				} else if (_configuration.getSchedule().julianDay() == ppftcrop.hdate) {
					indiv.lai_daily = 0.0;
					indiv.lai_indiv_daily = 0.0;
					indiv.fpc_daily = 0.0;
				}

			}
		}
	}

	// / A short version of Richards curve where:
	/**
	 * a is the lower asymptote, b is the upper asymptote. If a=0 then b is
	 * called the carrying capacity, c the growth rate, d is the time of maximum
	 * growth Source:
	 * http://en.wikipedia.org/wiki/Generalised_logistic_function, 2013-11-11
	 */
	private double richards_curve(double a, double b, double c, double d, double x) {

		double r = a + (b - a) / (1 + Math.exp(-c * (x - d)));
		return r;
	}

	private void crop_allocation_WE(CropPhen ppftcrop, Individual indiv) {

		ppftcrop.dev_stage = Math
				.max(0.0, Math.min(2.0, -0.595 * Math.pow(ppftcrop.fphu, 2.0) + 2.595 * ppftcrop.fphu));

		double t = 0.0;
		if (ppftcrop.fphu < 0.4367) {
			t = -0.07 + 2.45 * ppftcrop.fphu;
		} else {
			t = 0.06 + 2.0 * ppftcrop.fphu;
			t = 0.2247 + 1.7753 * ppftcrop.fphu;
		}
		ppftcrop.dev_stage = Math.max(0.0, Math.min(2.0, t));

		double f1 = Math.min(1.0, Math.max(0.0,
				richards_curve(indiv.pft.a1, indiv.pft.b1, indiv.pft.c1, indiv.pft.d1, ppftcrop.dev_stage)));
		double f2 = Math.min(1.0, Math.max(0.0,
				richards_curve(indiv.pft.a2, indiv.pft.b2, indiv.pft.c2, indiv.pft.d2, ppftcrop.dev_stage)));
		double f3 = Math.min(1.0, Math.max(0.0,
				richards_curve(indiv.pft.a3, indiv.pft.b3, indiv.pft.c3, indiv.pft.d3, ppftcrop.dev_stage)));

		if (indiv.daily_cmass_leafloss > 0.0)
			f2 *= f2 * f2;

		ppftcrop.f_alloc_root = f1 * (1 - f3);
		ppftcrop.f_alloc_leaf = f2 * (1 - f1) * (1 - f3);
		ppftcrop.f_alloc_stem = (1.0 - f2) * (1.0 - f1) * (1.0 - f3);
		ppftcrop.f_alloc_horg = f3;
	}

	void allocation_crop(Individual indiv, double cmass_seed, double nmass_seed) {

		CropIndiv cropindiv = indiv.getCropIndiv();
		Patch patch = indiv.vegetation.patch;
		PatchPFT patchpft = patch.pft.get(indiv.pft.id);
		CropPhen ppftcrop = patchpft.get_cropphen();

		nmass_seed = 0.0; // temporary ?

		// report seed flux
		indiv.report_flux(PerPatchFluxType.SEEDC, -cmass_seed);
		indiv.report_flux(PerPatchFluxType.SEEDN, -nmass_seed);

		// add seed carbon
		cropindiv.grs_cmass_plant += cmass_seed;
		cropindiv.ycmass_plant += cmass_seed;
		cropindiv.dcmass_plant += cmass_seed;

		// add seed nitrogen
		indiv.nmass_leaf += nmass_seed / 2.0;
		indiv.nmass_root += nmass_seed / 2.0;

		// add today's npp
		cropindiv.dcmass_plant += indiv.dnpp;
		cropindiv.grs_cmass_plant += indiv.dnpp;
		cropindiv.ycmass_plant += indiv.dnpp;

		// allocation to roots
		double froot = indiv.pft.frootstart - (indiv.pft.frootstart - indiv.pft.frootend) * ppftcrop.fphu; // SWAT
																											// 5:2,1,21
		double grs_cmass_root_old = cropindiv.grs_cmass_root;
		cropindiv.grs_cmass_root = froot * cropindiv.grs_cmass_plant;
		cropindiv.dcmass_root = cropindiv.grs_cmass_root - grs_cmass_root_old;
		cropindiv.ycmass_root += cropindiv.dcmass_root;

		// allocation to harvestable organs
		double grs_cmass_ag = (1.0 - froot) * cropindiv.grs_cmass_plant;
		double grs_cmass_ho_old = cropindiv.grs_cmass_ho;

		if (indiv.pft.hiopt <= 1.0)
			cropindiv.grs_cmass_ho = ppftcrop.hi * grs_cmass_ag; // SWAT
																	// 5:2.4.2,
																	// 5:2.4.4
		else
			// below-ground harvestable organs
			cropindiv.grs_cmass_ho = (1.0 - 1.0 / (1.0 + ppftcrop.hi)) * cropindiv.grs_cmass_plant; // SWAT
																									// 5:2.4.3
																									// 8

		cropindiv.dcmass_ho = cropindiv.grs_cmass_ho - grs_cmass_ho_old;
		cropindiv.ycmass_ho += cropindiv.dcmass_ho;

		// allocation to leaves
		double grs_cmass_leaf_old = cropindiv.grs_cmass_leaf;
		cropindiv.grs_cmass_leaf = cropindiv.grs_cmass_plant - cropindiv.grs_cmass_root - cropindiv.grs_cmass_ho;

		cropindiv.dcmass_leaf = cropindiv.grs_cmass_leaf - grs_cmass_leaf_old;
		cropindiv.ycmass_leaf += cropindiv.dcmass_leaf;

		// allocation to above-ground pool (currently not used)
		cropindiv.dcmass_agpool = cropindiv.dcmass_plant - cropindiv.dcmass_root - cropindiv.dcmass_leaf
				- cropindiv.dcmass_ho;
		cropindiv.grs_cmass_agpool = cropindiv.grs_cmass_plant - cropindiv.grs_cmass_root - cropindiv.grs_cmass_leaf
				- cropindiv.grs_cmass_ho;
		cropindiv.ycmass_agpool = cropindiv.ycmass_plant - cropindiv.ycmass_root - cropindiv.ycmass_leaf
				- cropindiv.ycmass_ho;

		if (cropindiv.grs_cmass_agpool < 1.0e-9)
			cropindiv.grs_cmass_agpool = 0.0;
		if (cropindiv.ycmass_agpool < 1.0e-9)
			cropindiv.ycmass_agpool = 0.0;

		return;
	}

	// / Daily allocation routine for crops with nitrogen limitation
	/**
	 * Allocates daily npp to leaf, roots and harvestable organs Equations are
	 * from Neitsch et al. 2002.
	 */
	public void allocation_crop_nlim(Individual indiv, double cmass_seed, double nmass_seed) {

		CropIndiv cropindiv = indiv.getCropIndiv();
		Patch patch = indiv.vegetation.patch;
		PatchPFT patchpft = patch.pft.get(indiv.pft.id);
		CropPhen ppftcrop = patchpft.get_cropphen();
		double cmass_extra = 0.0;

		if (ppftcrop.growingseason) {

			// report seed fluxes
			indiv.report_flux(PerPatchFluxType.SEEDC, -cmass_seed);
			indiv.report_flux(PerPatchFluxType.SEEDN, -nmass_seed);

			// add seed carbon
			cmass_extra += cmass_seed;

			// add seed nitrogen
			indiv.nmass_leaf += nmass_seed / 2.0;
			indiv.nmass_root += nmass_seed / 2.0;

			crop_allocation_WE(ppftcrop, indiv);

			if (indiv.dnpp < 0.0) {
				if (-indiv.dnpp < cropindiv.grs_cmass_agpool) {
					cropindiv.grs_cmass_agpool -= -1 * indiv.dnpp;
					cropindiv.ycmass_agpool -= -1 * indiv.dnpp;
					indiv.dnpp = 0.0;
				}
				if (indiv.dnpp < cropindiv.grs_cmass_agpool) {

				} else {
					indiv.report_flux(PerPFTFluxType.NPP, (-indiv.dnpp - cropindiv.grs_cmass_agpool));
					cropindiv.ycmass_agpool -= cropindiv.grs_cmass_agpool;
					// TODO Kill the individual if ag pool is zero.
					cropindiv.grs_cmass_agpool = 0.0;
					indiv.dnpp = 0.0;
				}
				indiv.report_flux(PerPFTFluxType.NPP, -indiv.dnpp);
				indiv.dnpp = 0.0;

				if (cropindiv.grs_cmass_agpool > 0.0 && patchpft.cropphen.f_alloc_horg > 0.95) {
					cmass_extra += 0.1 * cropindiv.grs_cmass_agpool;
					cropindiv.ycmass_agpool -= 0.1 * cropindiv.grs_cmass_agpool;
					cropindiv.grs_cmass_agpool *= 0.9;
				}

				indiv.daily_cmass_rootloss = 0.0;
				indiv.daily_nmass_rootloss = 0.0;

				if (indiv.daily_cmass_leafloss > 0.0) {

					cropindiv.dcmass_leaf = (indiv.dnpp + cmass_extra) * patchpft.cropphen.f_alloc_leaf
							- indiv.daily_cmass_leafloss;
					cropindiv.grs_cmass_dead_leaf += indiv.daily_cmass_leafloss;
					cropindiv.ycmass_dead_leaf += indiv.daily_cmass_leafloss;
					if (indiv.daily_cmass_leafloss / 100.0 < indiv.nmass_leaf) {
						cropindiv.nmass_dead_leaf += indiv.daily_cmass_leafloss / 100.0; // TODO
																							// super
																							// low
																							// C:N
																							// in
																							// the
																							// dead
																							// leaf
						cropindiv.ynmass_dead_leaf += indiv.daily_cmass_leafloss / 100.0;
						indiv.nmass_leaf -= indiv.daily_cmass_leafloss / 100.0;
					}
					// cropindiv.grs_cmass_leaf -= indiv.daily_cmass_leafloss;
					double new_CN = (cropindiv.grs_cmass_leaf + cropindiv.dcmass_leaf) / indiv.nmass_leaf;
					// If the result is smaller (higher [N]) than the min C:N
					// then that N is
					// put in to the ag N pool
					if (new_CN < indiv.pft.cton_leaf_min) {
						indiv.daily_nmass_leafloss = Math
								.max(0.0, indiv.nmass_leaf - (cropindiv.grs_cmass_leaf + cropindiv.dcmass_leaf)
										/ (1.33 * indiv.pft.cton_leaf_min));
						indiv.daily_nmass_leafloss = Math.max(0.0, indiv.nmass_leaf
								- (cropindiv.grs_cmass_leaf + cropindiv.dcmass_leaf) / indiv.pft.cton_leaf_min);
						if (indiv.daily_nmass_leafloss > indiv.nmass_leaf) {
							indiv.daily_nmass_leafloss = 0.0;
						}
					} else {
						indiv.daily_nmass_leafloss = 0.0;
					}
					// Very experimental root senescence
					// N and C loss when root senescence is allowed f_HO > 0.5
					// #undef ROOTLOSS
					// d3, the DS after which more than half of the daily
					// assimilates are going to the grains.
					if (patchpft.cropphen.dev_stage > indiv.pft.d3) {
						// only have root senescence when leaf scenescence har
						// occured
						if (indiv.daily_nmass_leafloss > 0.0) {
							double kC = 0.0;
							double kN = 0.0;
							// The root senescence is proportional to that of
							// the leaves
							if (indiv.nmass_leaf > 0.0) {
								kN = indiv.daily_nmass_leafloss / indiv.nmass_leaf;
							}
							if (indiv.cmass_leaf_today() > 0.0) {
								kC = indiv.daily_cmass_leafloss / indiv.cmass_leaf_today();
							}
							indiv.daily_cmass_rootloss = indiv.cmass_root_today() * kC;
							indiv.daily_nmass_rootloss = indiv.nmass_root * kN;
						}
					}
					indiv.nmass_leaf -= indiv.daily_nmass_leafloss;
					cropindiv.nmass_agpool += indiv.daily_nmass_leafloss;
				} else {
					cropindiv.dcmass_leaf = (indiv.dnpp + cmass_extra) * patchpft.cropphen.f_alloc_leaf;
				}
				cropindiv.dcmass_stem = (indiv.dnpp + cmass_extra) * patchpft.cropphen.f_alloc_stem;

				if (indiv.daily_cmass_rootloss > indiv.cmass_root_today())
					indiv.daily_cmass_rootloss = 0.0;

				cropindiv.dcmass_root = (indiv.dnpp + cmass_extra) * patchpft.cropphen.f_alloc_root
						- indiv.daily_cmass_rootloss;

				// TODO
				patch.soil.sompool[SOMPoolType.SOILMETA.ordinal()].cmass += indiv.daily_cmass_rootloss;

				if (indiv.daily_nmass_rootloss < indiv.nmass_root) {
					indiv.nmass_root -= indiv.daily_nmass_rootloss;
					cropindiv.nmass_agpool += indiv.daily_nmass_rootloss * 0.5; // 50%
																				// of
																				// the
																				// N
																				// in
																				// the
																				// lost
																				// root
																				// is
																				// retranslocated.
					patch.soil.sompool[SOMPoolType.SOILMETA.ordinal()].nmass += indiv.daily_nmass_rootloss * 0.5;// The
																													// rest
																													// is
																													// going
																													// in
																													// to
																													// litter
				}
				if (indiv.daily_cmass_rootloss > 0.0) {
					patch.is_litter_day = true;
				}

				cropindiv.dcmass_ho = (indiv.dnpp + cmass_extra) * patchpft.cropphen.f_alloc_horg;
				cropindiv.dcmass_plant = cropindiv.dcmass_ho + cropindiv.dcmass_root + cropindiv.dcmass_stem
						+ cropindiv.dcmass_leaf;

				cropindiv.ycmass_leaf += cropindiv.dcmass_leaf;
				cropindiv.ycmass_root += cropindiv.dcmass_root;
				cropindiv.ycmass_ho += cropindiv.dcmass_ho;
				cropindiv.ycmass_plant += cropindiv.dcmass_plant;

				cropindiv.grs_cmass_leaf += cropindiv.dcmass_leaf;
				cropindiv.grs_cmass_stem += cropindiv.dcmass_stem;
				cropindiv.ycmass_stem += cropindiv.dcmass_stem;
				cropindiv.grs_cmass_root += cropindiv.dcmass_root;
				cropindiv.grs_cmass_ho += cropindiv.dcmass_ho;
				cropindiv.grs_cmass_plant += cropindiv.dcmass_plant;

				double ndemand_ho = 0.0;
				double avail_leaf_N = Math.max(0.0, (1.0 / indiv.cton_leaf(false) - 1.0 / indiv.pft.cton_leaf_max)
						* indiv.cmass_leaf_today());
				double avail_root_N = Math.max(0.0, (1.0 / indiv.cton_root(false) - 1.0 / indiv.pft.cton_root_max)
						* indiv.cmass_root_today());
				double avail_stem_N = Math.max(0.0, cropindiv.nmass_agpool - 1.0 / indiv.pft.cton_stem_max
						* cropindiv.grs_cmass_stem);
				double avail_N = avail_leaf_N + avail_root_N + avail_stem_N;
				if (avail_N > 0.0 && cropindiv.dcmass_ho > 0.0) {
					ndemand_ho = cropindiv.dcmass_ho / indiv.pft.cton_leaf_avr;
				}
				// N mass to be translocated from leaves and roots
				double trans_leaf_N = 0.0;
				double trans_root_N = 0.0;
				if (ndemand_ho > 0.0) {
					if (avail_stem_N > 0.0) {
						if (ndemand_ho > avail_stem_N) {
							ndemand_ho -= avail_stem_N;
							cropindiv.dnmass_ho += avail_stem_N;
							cropindiv.nmass_agpool -= avail_stem_N;
						} else {
							cropindiv.nmass_agpool -= ndemand_ho;
							cropindiv.dnmass_ho += ndemand_ho;
							ndemand_ho = 0.0;
						}
					}
					// Seligman 1975
					// "willingness" to let go of the N in the organ to meet the
					// demand from the storage organ
					double w = 0.0;
					double w_r = 0.0;
					double w_l = 0.0;
					double w_s = 0.0;
					double y0 = (1.0 / indiv.pft.cton_leaf_min + 1.0 / indiv.pft.cton_leaf_avr) / 2.0;
					double y = 1.0 / indiv.cton_leaf(false);
					double y2 = 1.0 / (1.0 * indiv.pft.cton_leaf_max);
					double z = (y0 - y) / (y0 - y2);
					w_l = 1.0 - Math.max(0.0, Math.min(1.0, Math.pow(1.0 - z, 2.0)));
					y0 = 1.0 / indiv.pft.cton_root_avr;
					y = 1.0 / indiv.cton_root(false);
					y2 = 1.0 / (1.0 * indiv.pft.cton_root_max);
					z = (y0 - y) / (y0 - y2);
					w_r = 1.0 - Math.max(0.0, Math.min(1.0, Math.pow(1.0 - z, 2.0)));
					w_s = w_r + w_l;
					w = Math.min(1.0, w_s);
					if (w_s > 0.0) {
						trans_leaf_N = Math.max(0.0, w_l * w * ndemand_ho / w_s);
						trans_root_N = Math.max(0.0, w_r * w * ndemand_ho / w_s);
						if (trans_leaf_N > avail_leaf_N) {
							trans_leaf_N = avail_leaf_N;
						}
						if (trans_root_N > avail_root_N) {
							trans_root_N = avail_root_N;
						}
						cropindiv.dnmass_ho += trans_leaf_N;
						cropindiv.dnmass_ho += trans_root_N;
					}
				}
				indiv.nmass_leaf -= trans_leaf_N;
				indiv.nmass_root -= trans_root_N;
				cropindiv.nmass_ho += cropindiv.dnmass_ho;
			}
		}
	}

	// / Harvest function for cropland, including true crops, intercrop grass
	/**
	 * and pasture grass grown in cropland. Function for balancing carbon and
	 * nitrogen fluxes from last year's growth if old-style harvest is selected
	 * (HARVEST_GRSC defined), or, alternatively, this years harvested carbon and
	 * nitrogen. A fraction of harvestable organs (grass:leaves) is harvested
	 * (pft.harv_eff) and returned as acflux_harvest. A fraction of leaves is
	 * removed (pft.res_outtake) and returned as acflux_harvest The rest,
	 * including roots, is returned as litter, leaving NO carbon or nitrogen in
	 * living tissue. Called from growth() last day of the year for old-style
	 * harvest/grazing or, alternatively, from crop_growth_daily() at harvest
	 * day (hdate) or last intercrop day (eicdate). Also called from
	 * landcover_dynamics() first day of the year if any natural vegetation is
	 * transferred to another land use. This calls for a scaling factor, when
	 * the pasture area has increased.
	 *
	 * This function takes a Harvest_CN struct as an input parameter, copied
	 * from an individual and it's associated patchpft and patch.
	 *
	 * INPUT/OUTPUT PARAMETERS \param Harvest_CN& i struct containing the
	 * following indiv-specific public members: - cmass_leaf leaf C biomass
	 * (kgC/m2) - cmass_root fine root C biomass (kgC/m2) - cmass_ho harvestable
	 * organ C biomass (kgC/m2) - cmass_agpool above-ground pool C biomass
	 * (kgC/m2) - nmass_leaf leaf nitrogen biomass (kgN/m2) - nmass_root fine
	 * root nitrogen biomass (kgN/m2) - param nmass_ho harvestable organ
	 * nitrogen biomass (kgC/m2) - param nmass_agpool above-ground pool nitrogen
	 * biomass (kgC/m2) - nstore_labile labile nitrogen storage (kgC/m2) -
	 * nstore_longterm longterm nitrogen storage (kgC/m2) OUTPUT PARAMETERS
	 * \param Harvest_CN& i struct containing the following patchpft-specific
	 * public members: - litter_leaf new leaf C litter (kgC/m2) - litter_root
	 * new root C litter (kgC/m2) - nmass_litter_leaf new leaf nitrogen litter
	 * (kgN/m2) - nmass_litter_root new root nitrogen litter (kgN/m2) ,and the
	 * following patch-level public members: - acflux_harvest harvest flux to
	 * atmosphere (kgC/m2) - harvested_products_slow harvest products to slow
	 * pool (kgC/m2) - anflux_harvest harvest nitrogen flux out of system
	 * (kgC/m2) - harvested_products_slow_nmass harvest nitrogen products to
	 * slow pool (kgC/m2)
	 */
	void harvest_crop(Harvest_CN i, PFT pft, boolean alive, boolean isintercropgrass) {

		double residue_outtake, harvest;

		// TODO: Make configurable
		boolean ifslowharvestpool = true;

		if (pft.phenology == Phenology.CROPGREEN) {

			// all root carbon and nitrogen goes to litter
			if (i.cmass_root > 0.0)
				i.litter_root += i.cmass_root;
			i.cmass_root = 0.0;

			if (i.nmass_root > 0.0)
				i.nmass_litter_root += i.nmass_root;
			if (i.nstore_labile > 0.0)
				i.nmass_litter_root += i.nstore_labile;
			if (i.nstore_longterm > 0.0)
				i.nmass_litter_root += i.nstore_longterm;
			i.nmass_root = 0.0;
			i.nstore_labile = 0.0;
			i.nstore_longterm = 0.0;

			// harvest of harvestable organs
			// Carbon:
			if (i.cmass_ho > 0.0) {
				// harvested products
				harvest = pft.harv_eff * i.cmass_ho;

				// not removed harvestable organs are put into litter
				if (pft.aboveground_ho)
					i.litter_leaf += (i.cmass_ho - harvest);
				else
					i.litter_root += (i.cmass_ho - harvest);

				// harvested products not consumed (oxidised) this year put into
				// harvested_products_slow
				if (ifslowharvestpool) {
					i.harvested_products_slow += harvest * pft.harvest_slow_frac;
					harvest = harvest * (1 - pft.harvest_slow_frac);
				}

				// harvested products consumed (oxidised) this year put into
				// acflux_harvest
				i.acflux_harvest += harvest;
			}
			i.cmass_ho = 0.0;

			// Nitrogen:
			if (i.nmass_ho > 0.0) {

				// harvested products
				harvest = pft.harv_eff * i.nmass_ho;

				// not removed harvestable organs are put into litter
				if (pft.aboveground_ho)
					i.nmass_litter_leaf += (i.nmass_ho - harvest);
				else
					i.nmass_litter_root += (i.nmass_ho - harvest);

				// harvested products not consumed this year put into
				// harvested_products_slow_nmass
				if (ifslowharvestpool) {
					i.harvested_products_slow_nmass += harvest * pft.harvest_slow_frac;
					harvest = harvest * (1 - pft.harvest_slow_frac);
				}

				// harvested products consumed this year put into anflux_harvest
				i.anflux_harvest += harvest;
			}
			i.nmass_ho = 0.0;

			// residues
			// Carbon
			if ((i.cmass_leaf + i.cmass_agpool) > 0.0) {

				// removed residues are oxidised
				residue_outtake = pft.res_outtake * (i.cmass_leaf + i.cmass_agpool + i.cmass_dead_leaf + i.cmass_stem);
				i.acflux_harvest += residue_outtake;

				// not removed residues are put into litter
				i.litter_leaf += i.cmass_leaf + i.cmass_agpool + i.cmass_dead_leaf + i.cmass_stem - residue_outtake;
			}
			i.cmass_leaf = 0.0;
			i.cmass_agpool = 0.0;
			i.cmass_dead_leaf = 0.0;
			i.cmass_stem = 0.0;

			// Nitrogen:
			if ((i.nmass_leaf + i.nmass_agpool) > 0.0) {

				// removed residues are oxidised
				residue_outtake = pft.res_outtake * (i.nmass_leaf + i.nmass_agpool + i.nmass_dead_leaf);
				i.nmass_litter_leaf += i.nmass_leaf + i.nmass_agpool + i.nmass_dead_leaf - residue_outtake;

				// not removed residues are put into litter
				i.anflux_harvest += residue_outtake;
			}
			i.nmass_leaf = 0.0;
			i.nmass_agpool = 0.0;
			i.nmass_dead_leaf = 0.0;
		} else if (pft.phenology == Phenology.ANY) {

			// Intercrop grass
			if (isintercropgrass) {

				// roots

				// all root carbon and nitrogen goes to litter
				if (i.cmass_root > 0.0)
					i.litter_root += i.cmass_root;
				if (i.nmass_root > 0.0)
					i.nmass_litter_root += i.nmass_root;
				if (i.nstore_labile > 0.0)
					i.nmass_litter_root += i.nstore_labile;
				if (i.nstore_longterm > 0.0)
					i.nmass_litter_root += i.nstore_longterm;

				i.cmass_root = 0.0;
				i.nmass_root = 0.0;
				i.nstore_labile = 0.0;
				i.nstore_longterm = 0.0;

				// leaves

				// Carbon:
				if (i.cmass_leaf > 0.0) {

					// Harvest/Grazing of leaves:
					harvest = pft.harv_eff_ic * i.cmass_leaf; // currently no
																// harvest of
																// intercrtop
																// grass

					// not removed grass is put into litter
					i.litter_leaf += i.cmass_leaf - harvest;

					if (ifslowharvestpool) {
						i.harvested_products_slow += harvest * pft.harvest_slow_frac; // no
																						// slow
																						// harvest
																						// for
																						// grass
						harvest = harvest * (1 - pft.harvest_slow_frac);
					}

					i.acflux_harvest += harvest;
				}
				i.cmass_leaf = 0.0;
				i.cmass_ho = 0.0;
				i.cmass_agpool = 0.0;

				// Nitrogen:
				if (i.nmass_leaf > 0.0) {

					// Harvest/Grazing of leaves:
					harvest = pft.harv_eff_ic * i.nmass_leaf; // currently no
																// harvest of
																// intercrtop
																// grass

					// not removed grass is put into litter
					i.nmass_litter_leaf += i.nmass_leaf - harvest;

					if (ifslowharvestpool) {
						i.harvested_products_slow_nmass += harvest * pft.harvest_slow_frac; // no
																							// slow
																							// harvest
																							// for
																							// grass
						harvest = harvest * (1 - pft.harvest_slow_frac);
					}

					i.anflux_harvest += harvest;
				}
				i.nmass_leaf = 0.0;
				i.nmass_ho = 0.0;
				i.nmass_agpool = 0.0;

			} else { // pasture grass

				// harvest of leaves (grazing)

				// Carbon:
				harvest = pft.harv_eff * i.cmass_leaf;

				if (ifslowharvestpool) {
					i.harvested_products_slow += harvest * pft.harvest_slow_frac;
					harvest = harvest * (1 - pft.harvest_slow_frac);
				}
				if (alive)
					i.acflux_harvest += harvest;
				i.cmass_leaf -= harvest;

				i.cmass_ho = 0.0;
				i.cmass_agpool = 0.0;

				// Nitrogen:
				// Reduced removal of N relative to C during grazing.
				double N_harvest_scale = 0.25; // Value that works. Needs to be
												// verified in literature.
				harvest = pft.harv_eff * i.nmass_leaf * N_harvest_scale;

				if (ifslowharvestpool) {
					i.harvested_products_slow_nmass += harvest * pft.harvest_slow_frac;
					harvest = harvest * (1 - pft.harvest_slow_frac);
				}
				i.anflux_harvest += harvest;
				i.nmass_leaf -= harvest;

				i.nmass_ho = 0.0;
				i.nmass_agpool = 0.0;
			}
		}
	}

	// / Calculates nitrogen retranslocation fraction
	/*
	 * Calculates actual nitrogen retranslocation fraction so maximum nitrogen
	 * storage capacity is not exceeded
	 */
	double calc_nrelocfrac(LifeForm lifeform, double turnover_leaf, double nmass_leaf, double turnover_root,
			double nmass_root, double turnover_sap, double nmass_sap, double max_n_storage, double longterm_nstore) {

		double turnover_nmass = turnover_leaf * nmass_leaf + turnover_root * nmass_root;

		if (lifeform == LifeForm.TREE)
			turnover_nmass += turnover_sap * nmass_sap;

		if (max_n_storage < longterm_nstore)
			return 0.0;
		else if (max_n_storage < longterm_nstore + turnover_nmass * _configuration.getNRelocFrac()
				&& !Utils.negligible(turnover_nmass))
			return (max_n_storage - longterm_nstore) / (turnover_nmass);
		else
			return _configuration.getNRelocFrac();
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// TURNOVER
	// Internal function (do not call directly from framework)

	void turnover(double turnover_leaf, double turnover_root, double turnover_sap, LifeForm lifeform,
			LandcoverType landcover, double cmass_leaf, double cmass_root, double cmass_sap, double cmass_heart,
			double nmass_leaf, double nmass_root, double nmass_sap, double nmass_heart, double litter_leaf,
			double litter_root, double nmass_litter_leaf, double nmass_litter_root, double longterm_nstore,
			double max_n_storage, boolean alive) {

		// DESCRIPTION
		// Transfers carbon from leaves and roots to litter, and from sapwood to
		// heartwood
		// Only turnover from 'alive' individuals is transferred to litter (Ben
		// 2007-11-28)

		// INPUT PARAMETERS
		// turnover_leaf = leaf turnover per time period as a proportion of leaf
		// C biomass
		// turnover_root = root turnover per time period as a proportion of root
		// C biomass
		// turnover_sap = sapwood turnover to heartwood per time period as a
		// proportion of
		// sapwood C biomass
		// lifeform = PFT life form class (TREE or GRASS)
		// alive = signifies new Individual object if false (see vegdynam.cpp)

		// INPUT AND OUTPUT PARAMETERS
		// cmass_leaf = leaf C biomass (kgC/m2)
		// cmass_root = fine root C biomass (kgC/m2)
		// cmass_sap = sapwood C biomass (kgC/m2)
		// nmass_leaf = leaf nitrogen biomass (kgN/m2)
		// nmass_root = fine root nitrogen biomass (kgN/m2)
		// nmass_sap = sapwood nitrogen biomass (kgN/m2)

		// OUTPUT PARAMETERS
		// litter_leaf = new leaf C litter (kgC/m2)
		// litter_root = new root C litter (kgC/m2)
		// nmass_litter_leaf = new leaf nitrogen litter (kgN/m2)
		// nmass_litter_root = new root nitrogen litter (kgN/m2)
		// cmass_heart = heartwood C biomass (kgC/m2)
		// nmass_heart = heartwood nitrogen biomass (kgC/m2)
		// longterm_nstore = longterm nitrogen storage (kgN/m2)

		double turnover = 0.0;

		// Calculate actual nitrogen retranslocation so maximum nitrogen storage
		// capacity is not exceeded
		double actual_nrelocfrac = calc_nrelocfrac(lifeform, turnover_leaf, nmass_leaf, turnover_root, nmass_root,
				turnover_sap, nmass_sap, max_n_storage, longterm_nstore);

		// TREES AND GRASSES:

		// Leaf turnover
		turnover = turnover_leaf * cmass_leaf;
		cmass_leaf -= turnover;
		if (alive)
			litter_leaf += turnover;

		turnover = turnover_leaf * nmass_leaf;
		nmass_leaf -= turnover;
		nmass_litter_leaf += turnover * (1.0 - actual_nrelocfrac);
		longterm_nstore += turnover * actual_nrelocfrac;

		// Root turnover
		turnover = turnover_root * cmass_root;
		cmass_root -= turnover;
		if (alive)
			litter_root += turnover;

		turnover = turnover_root * nmass_root;
		nmass_root -= turnover;
		nmass_litter_root += turnover * (1.0 - actual_nrelocfrac);
		longterm_nstore += turnover * actual_nrelocfrac;

		if (lifeform == LifeForm.TREE) {

			// TREES ONLY:

			// Sapwood turnover by conversion to heartwood
			turnover = turnover_sap * cmass_sap;
			cmass_sap -= turnover;
			cmass_heart += turnover;

			// NB: assumes nitrogen is translocated from sapwood prior to
			// conversion to
			// heartwood and that this is the same fraction that is conserved
			// in conjunction with leaf and root shedding

			turnover = turnover_sap * nmass_sap;
			nmass_sap -= turnover;
			nmass_heart += turnover * (1.0 - actual_nrelocfrac);
			longterm_nstore += turnover * actual_nrelocfrac;
		}
	}

	// / Turnover function for continuous grass, to be called from any day of
	// the year from allocation_crop_daily().
	void turnover_grass(Individual indiv) {

		CropIndiv cropindiv = indiv.getCropIndiv();
		PatchPFT patchpft = indiv.getPatchPFT();

		double cmass_leaf_inc = cropindiv.grs_cmass_leaf - indiv.cmass_leaf_post_turnover;
		double cmass_root_inc = cropindiv.grs_cmass_root - indiv.cmass_root_post_turnover;

		double grs_npp = cmass_leaf_inc + cmass_root_inc;
		double cmass_leaf_pre_turnover = cropindiv.grs_cmass_leaf;
		double cmass_root_pre_turnover = cropindiv.grs_cmass_root;
		double cton_leaf_bg = indiv.cton_leaf(false);
		double cton_root_bg = indiv.cton_root(false);

		indiv.nstore_longterm += indiv.nstore_labile;
		indiv.nstore_labile = 0.0;

		turnover(indiv.pft.turnover_leaf, indiv.pft.turnover_root, indiv.pft.turnover_sap, indiv.pft.lifeform,
				indiv.pft.landcover, indiv.cropindiv.grs_cmass_leaf, indiv.cropindiv.grs_cmass_root, indiv.cmass_sap,
				indiv.cmass_heart, indiv.nmass_leaf, indiv.nmass_root, indiv.nmass_sap, indiv.nmass_heart,
				patchpft.litter_leaf, patchpft.litter_root, patchpft.nmass_litter_leaf, patchpft.nmass_litter_root,
				indiv.nstore_longterm, indiv.max_n_storage, true);

		indiv.cmass_leaf_post_turnover = cropindiv.grs_cmass_leaf;
		indiv.cmass_root_post_turnover = cropindiv.grs_cmass_root;

		// Nitrogen longtime storage
		// Nitrogen approx retranslocated next season
		double retransn_nextyear = cmass_leaf_pre_turnover * indiv.pft.turnover_leaf / cton_leaf_bg
				* _configuration.getNRelocFrac() + cmass_root_pre_turnover * indiv.pft.turnover_root / cton_root_bg
				* _configuration.getNRelocFrac();

		// Max longterm nitrogen storage
		indiv.max_n_storage = Math.max(0.0,
				Math.min(cmass_root_pre_turnover * indiv.pft.fnstorage / cton_leaf_bg, retransn_nextyear));

		// Scale this year productivity to max storage
		if (grs_npp > 0.0) {
			indiv.scale_n_storage = Math.max(indiv.max_n_storage * 0.1, indiv.max_n_storage - retransn_nextyear)
					* cton_leaf_bg / grs_npp;
		}

		indiv.nstore_labile = indiv.nstore_longterm;
		indiv.nstore_longterm = 0.0;
	}

	void harvest_crop(Individual indiv, PFT pft, boolean alive, boolean isintercropgrass, boolean harvest_grsC) {

		Harvest_CN indiv_cp = new Harvest_CN();

		indiv_cp.copy_from_indiv(indiv, harvest_grsC);

		harvest_crop(indiv_cp, pft, alive, isintercropgrass);

		indiv_cp.copy_to_indiv(indiv, harvest_grsC);

	}

	// / Daily growth routine for crops
	/**
	 * Allocates daily npp to leaf, roots and harvestable organs Requires
	 * updated value of fphu and hi. Equations are from Neitsch et al. 2002.
	 */
	void growth_crop_daily(Patch patch) {
		double CMASS_SEED = 0.01;
		int day = patch.stand._configuration.getSchedule().julianDay();

		if (day == 0)
			patch.nharv = 0;
		patch.isharvestday = false;
		double nharv_today = 0;

		Vegetation vegetation = patch.vegetation;

		for (Individual indiv : vegetation) {

			CropIndiv cropindiv = indiv.getCropIndiv();
			PatchPFT patchpft = patch.pft.get(indiv.pft.id);
			CropPhen ppftcrop = patchpft.cropphen;

			if (day == 0) {

				cropindiv.ycmass_plant = 0.0;
				cropindiv.ycmass_leaf = 0.0;
				cropindiv.ycmass_root = 0.0;
				cropindiv.ycmass_ho = 0.0;
				cropindiv.ycmass_agpool = 0.0;
				cropindiv.ycmass_dead_leaf = 0.0;
				cropindiv.ycmass_stem = 0.0;

				cropindiv.harv_cmass_plant = 0.0;
				cropindiv.harv_cmass_root = 0.0;
				cropindiv.harv_cmass_leaf = 0.0;
				cropindiv.harv_cmass_ho = 0.0;
				cropindiv.harv_cmass_agpool = 0.0;

				cropindiv.cmass_ho_harvest[0] = 0.0;
				cropindiv.cmass_ho_harvest[1] = 0.0;

				cropindiv.cmass_leaf_max = 0.0;

				if (indiv.pft.phenology == Phenology.ANY && !cropindiv.isintercropgrass) { // zero
																							// of
																							// normal
																							// cc3g/cc4g-growth
																							// arbitrarily
																							// at
																							// new
																							// year

					cropindiv.grs_cmass_plant = 0.0;
					cropindiv.grs_cmass_root = 0.0;
					cropindiv.grs_cmass_ho = 0.0;
					cropindiv.grs_cmass_leaf = 0.0;
					cropindiv.grs_cmass_agpool = 0.0;
				}
			}

			cropindiv.dcmass_plant = 0.0;
			cropindiv.dcmass_leaf = 0.0;
			cropindiv.dcmass_root = 0.0;
			cropindiv.dcmass_ho = 0.0;
			cropindiv.dcmass_agpool = 0.0;
			cropindiv.dcmass_stem = 0.0;
			cropindiv.dnmass_ho = 0.0;

			// true crop allocation
			if (indiv.pft.phenology == Phenology.CROPGREEN) {

				if (ppftcrop.growingseason) {

					double cmass_seed = 0.0;
					double nmass_seed = 0.0;
					// add seed carbon on sowing date
					if (day == ppftcrop.sdate) {

						cmass_seed = CMASS_SEED;
						nmass_seed = CMASS_SEED / indiv.pft.cton_leaf_min;
					}
					if (patch.stand._configuration.isNLimitedLC(LandcoverType.CROP))
						allocation_crop_nlim(indiv, cmass_seed, nmass_seed);
					else
						allocation_crop(indiv, cmass_seed, nmass_seed);

					// save this year's maximum leaf carbon mass
					if (cropindiv.grs_cmass_leaf > cropindiv.cmass_leaf_max)
						cropindiv.cmass_leaf_max = cropindiv.grs_cmass_leaf;

					// save leaf carbon mass at the beginning of senescence
					if (day == ppftcrop.sendate)
						cropindiv.cmass_leaf_sen = cropindiv.grs_cmass_leaf;

					// Check that no plant cmass or nmass is negative, if so,
					// and correct fluxes
					double negative_cmass = indiv.check_C_mass();
					// if(negative_cmass > 1.0e-14)
					// dprintf("Year %d day %d Stand %d indiv %d: Negative main crop C mass in growth_crop_daily: %.15f\n",
					// date.year, date.day, indiv.vegetation.patch.stand.id,
					// indiv.id, -negative_cmass);
					double negative_nmass = indiv.check_N_mass();
					// if(negative_nmass > 1.0e-14)
					// dprintf("Year %d day %d Stand %d indiv %d: Negative main crop N mass in growth_crop_daily: %.15f\n",
					// date.year, date.day, indiv.vegetation.patch.stand.id,
					// indiv.id, -negative_nmass);
				} else if (day == ppftcrop.hdate) {

					patch.stand.isrotationday = true;

					cropindiv.harv_cmass_plant += cropindiv.grs_cmass_plant;
					cropindiv.harv_cmass_root += cropindiv.grs_cmass_root;
					cropindiv.harv_cmass_ho += cropindiv.grs_cmass_ho;
					cropindiv.harv_cmass_leaf += cropindiv.grs_cmass_leaf;
					cropindiv.harv_cmass_agpool += cropindiv.grs_cmass_agpool;
					cropindiv.harv_cmass_stem += cropindiv.grs_cmass_stem;

					cropindiv.harv_nmass_root += indiv.nmass_root;
					cropindiv.harv_nmass_ho += cropindiv.nmass_ho;
					cropindiv.harv_nmass_leaf += indiv.nmass_leaf;
					cropindiv.harv_nmass_agpool += cropindiv.nmass_agpool;
					// dead_leaf to be addad

					if (ppftcrop.nharv == 1)
						cropindiv.cmass_ho_harvest[0] = cropindiv.grs_cmass_ho;
					else if (ppftcrop.nharv == 2)
						cropindiv.cmass_ho_harvest[1] = cropindiv.grs_cmass_ho;

					patch.isharvestday = true;

					if (indiv.has_daily_turnover()) {
						// // TODO: Implement this bit for LC Dynamics
						// if (patch.stand.getGridcell().LC_updated &&
						// patchpft.cropphen.nharv == 1)
						// scale_indiv(indiv, true);
						harvest_crop(indiv, indiv.pft, indiv.alive, indiv.cropindiv.isintercropgrass, true);
						patch.is_litter_day = true;
					}
					patch.nharv++;

					cropindiv.grs_cmass_plant = 0.0;
					cropindiv.grs_cmass_root = 0.0;
					cropindiv.grs_cmass_ho = 0.0;
					cropindiv.grs_cmass_leaf = 0.0;
					cropindiv.grs_cmass_agpool = 0.0;
					cropindiv.cmass_leaf_sen = 0.0;

					cropindiv.grs_cmass_stem = 0.0;
					cropindiv.grs_cmass_dead_leaf = 0.0;
					cropindiv.nmass_dead_leaf = 0.0;
					cropindiv.nmass_agpool = 0.0;
					indiv.nmass_leaf = 0.0;
					indiv.nmass_root = 0.0;
					cropindiv.nmass_ho = 0.0;
				}
			}
			// crop grass allocation
			// NB: Only intercrop grass enters here ! normal cc3g/cc4g grass is
			// treated just like natural grass
			else if (indiv.pft.phenology == Phenology.ANY && indiv.pft.id != patch.stand.pftid) {

				if (ppftcrop.growingseason) {

					cropindiv.dcmass_plant = 0.0;
					cropindiv.dcmass_plant += indiv.dnpp;
					cropindiv.grs_cmass_plant += indiv.dnpp;
					cropindiv.ycmass_plant += indiv.dnpp;

					indiv.ltor = indiv.wscal_mean() * indiv.pft.ltor_max;

					// allocation to roots
					double froot = 1.0 / (1.0 + indiv.ltor);
					double grs_cmass_root_old = cropindiv.grs_cmass_root;

					// Cumulative wscal-dependent root increase
					cropindiv.grs_cmass_root = froot * cropindiv.grs_cmass_plant;
					cropindiv.dcmass_root = cropindiv.grs_cmass_root - grs_cmass_root_old;
					cropindiv.ycmass_root += cropindiv.dcmass_root;

					// allocation to leaves
					double fleaf = 1.0 - froot;
					double grs_cmass_leaf_old = cropindiv.grs_cmass_leaf;
					cropindiv.grs_cmass_leaf = cropindiv.grs_cmass_plant - cropindiv.grs_cmass_root;
					cropindiv.dcmass_leaf = cropindiv.grs_cmass_leaf - grs_cmass_leaf_old;
					cropindiv.ycmass_leaf += cropindiv.dcmass_leaf;

					// Check that no plant cmass is negative, if so, zero cmass
					// and correct C fluxes
					double negative_cmass = indiv.check_C_mass();
					// save this year's maximum leaf carbon mass
					if (cropindiv.grs_cmass_leaf > cropindiv.cmass_leaf_max)
						cropindiv.cmass_leaf_max = cropindiv.grs_cmass_leaf;
				} else if (day == patch.pft.get(patch.stand.pftid).get_cropphen().eicdate) {

					cropindiv.harv_cmass_plant += cropindiv.grs_cmass_plant;
					cropindiv.harv_cmass_root += cropindiv.grs_cmass_root;
					cropindiv.harv_cmass_leaf += cropindiv.grs_cmass_leaf;
					cropindiv.harv_cmass_ho += cropindiv.grs_cmass_ho;
					cropindiv.harv_cmass_agpool += cropindiv.grs_cmass_agpool;

					cropindiv.harv_nmass_root += indiv.nmass_root;
					cropindiv.harv_nmass_ho += cropindiv.nmass_ho;
					cropindiv.harv_nmass_leaf += indiv.nmass_leaf;
					cropindiv.harv_nmass_agpool += cropindiv.nmass_agpool;

					ppftcrop.nharv++;
					patch.isharvestday = true;
					nharv_today++;
					if (nharv_today > 1) // In case of both C3 and C4 growing
						patch.nharv--;

					if (indiv.has_daily_turnover()) {
						// TODO: Implement this bit for LC Dynamics
						// if (patch.stand.getGridcell().LC_updated &&
						// patchpft.cropphen.nharv == 1)
						// scale_indiv(indiv, true);
						harvest_crop(indiv, indiv.pft, indiv.alive, indiv.cropindiv.isintercropgrass, true);
						patch.is_litter_day = true;
					} else {
						cropindiv.grs_cmass_root = 0.0;
						cropindiv.grs_cmass_ho = 0.0;
						cropindiv.grs_cmass_leaf = 0.0;
						cropindiv.grs_cmass_agpool = 0.0;
					}
					patch.nharv++;

					cropindiv.grs_cmass_plant = cropindiv.grs_cmass_root + cropindiv.grs_cmass_leaf;
				}

				if (indiv.continous_grass() && indiv.is_turnover_day()) {

					indiv.last_turnover_day = day;

					cropindiv.harv_cmass_plant += cropindiv.grs_cmass_plant;
					cropindiv.harv_cmass_root += cropindiv.grs_cmass_root;
					cropindiv.harv_cmass_leaf += cropindiv.grs_cmass_leaf;
					cropindiv.harv_cmass_ho += cropindiv.grs_cmass_ho;
					cropindiv.harv_cmass_agpool += cropindiv.grs_cmass_agpool;

					cropindiv.harv_nmass_root += indiv.nmass_root;
					cropindiv.harv_nmass_ho += cropindiv.nmass_ho;
					cropindiv.harv_nmass_leaf += indiv.nmass_leaf;
					cropindiv.harv_nmass_agpool += cropindiv.nmass_agpool;

					ppftcrop.nharv++;
					patch.isharvestday = true;
					nharv_today++;
					if (nharv_today > 1) // In case of both C3 and C4 growing
						patch.nharv--;

					if (indiv.has_daily_turnover()) {
						// TODO: Implement this bit for LC Dynamics
						// if (patch.stand.get_gridcell().LC_updated &&
						// patchpft.cropphen.nharv == 1)
						// scale_indiv(indiv, true);

						turnover_grass(indiv);
						patch.is_litter_day = true;
					} else {
						cropindiv.grs_cmass_root = 0.0;
						cropindiv.grs_cmass_ho = 0.0;
						cropindiv.grs_cmass_leaf = 0.0;
					}
					patch.nharv++;

					cropindiv.grs_cmass_plant = cropindiv.grs_cmass_root + cropindiv.grs_cmass_leaf;
					cropindiv.grs_cmass_agpool = 0.0;
				}
			}
		}

		return;
	}

}
