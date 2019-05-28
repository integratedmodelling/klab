package org.integratedmodelling.ecology.biomass.lpjguess.processes;

import org.integratedmodelling.ecology.biomass.lpjguess.CropPhen;
import org.integratedmodelling.ecology.biomass.lpjguess.Gridcell;
import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.LandcoverType;
import org.integratedmodelling.ecology.biomass.lpjguess.PFT;
import org.integratedmodelling.ecology.biomass.lpjguess.Patch;
import org.integratedmodelling.ecology.biomass.lpjguess.PatchPFT;
import org.integratedmodelling.ecology.biomass.lpjguess.PerPFTFluxType;
import org.integratedmodelling.ecology.biomass.lpjguess.PerPatchFluxType;
import org.integratedmodelling.ecology.biomass.lpjguess.Stand;
import org.integratedmodelling.ecology.biomass.lpjguess.Vegetation;
import org.integratedmodelling.ecology.biomass.lpjguess.common.CropIndiv;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Harvest_CN;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.ecology.biomass.lpjguess.processes.base.SimProcess;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;
import org.integratedmodelling.procsim.api.IConfiguration.Phenology;

public class Growth extends SimProcess {

	GrowthDaily growth_daily = null;

	public Growth(IConfiguration configuration) {
		super(configuration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Patch patch) {
		growth_daily = new GrowthDaily(_configuration);

		growth(patch);
	}

	double f(double cmass_leaf_inc, double k1, double k2, double k3, double b, double ltor_g, double cmass_heart_g,
			double cmass_leaf_g) {

		// Returns value of f(cmass_leaf_inc), given by:
		//
		// f(cmass_leaf_inc) = 0 =
		// k1 * (b - cmass_leaf_inc - cmass_leaf_inc/ltor + cmass_heart) -
		// [ (b - cmass_leaf_inc - cmass_leaf_inc/ltor)
		// / (cmass_leaf + cmass_leaf_inc )*k3 ] ** k2
		//
		// See function allocation (below), Eqn (13)

		return k1 * (b - cmass_leaf_inc - cmass_leaf_inc / ltor_g + cmass_heart_g)
				- Math.pow((b - cmass_leaf_inc - cmass_leaf_inc / ltor_g) / (cmass_leaf_g + cmass_leaf_inc) * k3, k2);
	}

	/**
	 * Calculates changes in C compartment sizes (leaves, roots, sapwood, heartwood)
	 * and litter for a plant individual as a result of allocation of biomass
	 * increment. Assumed allometric relationships are given in function allometry
	 * below.
	 * 
	 * @param bminc
	 *            biomass increment this time period on individual basis (kgC)
	 * @param cmass_leaf
	 *            leaf C biomass for last time period on individual basis (kgC)
	 * @param cmass_root
	 *            root C biomass for last time period on individual basis (kgC)
	 * @param cmass_sap
	 *            sapwood C biomass for last time period on individual basis (kgC)
	 * @param cmass_debt
	 * @param cmass_heart
	 *            heartwood C biomass for last time period on individual basis (kgC)
	 * @param ltor
	 *            leaf to root mass ratio following allocation
	 * @param height
	 *            individual height (m)
	 * @param sla
	 *            specific leaf area (PFT-specific constant) (m2/kgC)
	 * @param wooddens
	 *            wood density (PFT-specific constant) (kgC/m3)
	 * @param lifeform
	 *            life form class (TREE or GRASS)
	 * @param k_latosa
	 *            ratio of leaf area to sapwood cross-sectional area (PFT-specific)
	 * @param k_allom2 constant in allometry equations
	 * @param k_allom3 constant in allometry equations
	 * 
	 * OUTPUTS - see below for how to compute; must be taken out of function
	 * 
	 * @param cmass_leaf_inc
	 * @param cmass_root_inc
	 * @param cmass_sap_inc
	 * @param cmass_debt_inc
	 * @param cmass_heart_inc
	 * @param litter_leaf_inc
	 * @param litter_root_inc
	 * @param exceeds_cmass
	 */
	void allocation(double bminc, double cmass_leaf, double cmass_root, double cmass_sap, double cmass_debt,
			double cmass_heart, double ltor, double height, double sla, double wooddens, LifeForm lifeform,
			double k_latosa, double k_allom2, double k_allom3, double cmass_leaf_inc, double cmass_root_inc,
			double cmass_sap_inc, double cmass_debt_inc, double cmass_heart_inc, double litter_leaf_inc,
			double litter_root_inc, double exceeds_cmass) {


		// OUTPUT PARAMETERS
		// cmass_leaf_inc = increment (may be negative) in leaf C biomass
		// following
		// allocation (kgC)
		// cmass_root_inc = increment (may be negative) in root C biomass
		// following
		// allocation (kgC)
		// cmass_sap_inc = increment (may be negative) in sapwood C biomass
		// following
		// allocation (kgC)
		// cmass_heart_inc = increment in heartwood C biomass following
		// allocation (kgC)
		// litter_leaf_inc = increment in leaf litter following allocation, on
		// individual
		// basis (kgC)
		// litter_root_inc = increment in root litter following allocation, on
		// individual
		// basis (kgC)
		// exceeds_cmass = negative increment that exceeds existing biomass
		// (kgC)

		// MATHEMATICAL DERIVATION FOR TREE ALLOCATION
		// Allocation attempts to distribute biomass increment (bminc) among the
		// living
		// tissue compartments, i.e.
		// (1) bminc = cmass_leaf_inc + cmass_root_inc + cmass_sap_inc
		// while satisfying the allometric relationships (Shinozaki et al.
		// 1964a,b; Waring
		// et al 1982, Huang et al 1992; see also function allometry, below) [**
		// =
		// raised to the power of]:
		// (2) (leaf area) = k_latosa * (sapwood xs area)
		// (3) cmass_leaf = ltor * cmass_root
		// (4) height = k_allom2 * (stem diameter) ** k_allom3
		// From (1) and (3),
		// (5) cmass_sap_inc = bminc - cmass_leaf_inc -
		// (cmass_leaf + cmass_leaf_inc) / ltor + cmass_root
		// Let diam_new and height_new be stem diameter and height following
		// allocation.
		// Then (see allometry),
		// (6) diam_new = 2 * [ ( cmass_sap + cmass_sap_inc + cmass_heart )
		// / wooddens / height_new / PI ]**(1/2)
		// From (4), (6) and (5),
		// (7) height_new**(1+2/k_allom3) =
		// k_allom2**(2/k_allom3) * 4 * [cmass_sap + bminc - cmass_leaf_inc
		// - (cmass_leaf + cmass_leaf_inc) / ltor + cmass_root + cmass_heart]
		// / wooddens / PI
		// Now,
		// (8) wooddens = cmass_sap / height / (sapwood xs area)
		// From (8) and (2),
		// (9) wooddens = cmass_sap / height / sla / cmass_leaf * k_latosa
		// From (9) and (1),
		// (10) wooddens = (cmass_sap + bminc - cmass_leaf_inc -
		// (cmass_leaf + cmass_leaf_inc) / ltor + cmass_root)
		// / height_new / sla / (cmass_leaf + cmass_leaf_inc) * k_latosa
		// From (10),
		// (11) height_new**(1+2/k_allom3) =
		// [ (cmass_sap + bminc - cmass_leaf_inc - (cmass_leaf + cmass_leaf_inc)
		// / ltor + cmass_root) / wooddens / sla
		// / (cmass_leaf + cmass_leaf_inc ) * k_latosa ] ** (1+2/k_allom3)
		//
		// Combining (7) and (11) gives a function of the unknown
		// cmass_leaf_inc:
		//
		// (12) f(cmass_leaf_inc) = 0 =
		// k_allom2**(2/k_allom3) * 4/PI * [cmass_sap + bminc - cmass_leaf_inc
		// - (cmass_leaf + cmass_leaf_inc) / ltor + cmass_root + cmass_heart]
		// / wooddens -
		// [ (cmass_sap + bminc - cmass_leaf_inc - (cmass_leaf + cmass_leaf_inc)
		// / ltor + cmass_root) / (cmass_leaf + cmass_leaf_inc)
		// / wooddens / sla * k_latosa] ** (1+2/k_allom3)
		//
		// Let k1 = k_allom2**(2/k_allom3) * 4/PI / wooddens
		// k2 = 1+2/k_allom3
		// k3 = k_latosa / wooddens / sla
		// b = cmass_sap + bminc - cmass_leaf/ltor + cmass_root
		//
		// Then,
		// (13) f(cmass_leaf_inc) = 0 =
		// k1 * (b - cmass_leaf_inc - cmass_leaf_inc/ltor + cmass_heart) -
		// [ (b - cmass_leaf_inc - cmass_leaf_inc/ltor)
		// / (cmass_leaf + cmass_leaf_inc )*k3 ] ** k2
		//
		// Numerical methods are used to solve Eqn (13) for cmass_leaf_inc

		int NSEG = 20; // number of segments (parameter in numerical methods)
		int JMAX = 40; // maximum number of iterations (in numerical methods)
		double XACC = 0.0001; // threshold x-axis precision of allocation
								// solution
		double YACC = 1.0e-10; // threshold y-axis precision of allocation
								// solution
		double CDEBT_MAXLOAN_DEFICIT = 0.8; // maximum loan as a fraction of
											// deficit
		double CDEBT_MAXLOAN_MASS = 0.2; // maximum loan as a fraction of
											// (sapwood-cdebt)

		double cmass_leaf_inc_min;
		double cmass_root_inc_min;
		double x1, x2, dx, xmid, fx1, fmid, rtbis, sign;
		int j;
		double cmass_deficit, cmass_loan;

		// initialise
		litter_leaf_inc = 0.0;
		litter_root_inc = 0.0;
		exceeds_cmass = 0.0;
		cmass_leaf_inc = 0.0;
		cmass_root_inc = 0.0;
		cmass_sap_inc = 0.0;
		cmass_heart_inc = 0.0;

		if (ltor < 1.0e-10) {

			// No leaf production possible - put all biomass into roots
			// (Individual will die next time period)

			cmass_leaf_inc = 0.0;

			// Make sure we don't end up with negative cmass_root
			if (bminc < -cmass_root) {
				exceeds_cmass = -(cmass_root + bminc);
				cmass_root_inc = -cmass_root;
			} else {
				cmass_root_inc = bminc;
			}

			if (lifeform == LifeForm.TREE) {
				cmass_sap_inc = -cmass_sap;
				cmass_heart_inc = -cmass_sap_inc;
			}

			return;
		}

		if (lifeform == LifeForm.TREE) {

			// TREE ALLOCATION

			cmass_heart_inc = 0.0;

			// Calculate minimum leaf increment to maintain current sapwood
			// biomass
			// Given Eqn (2)

			if (height > 0.0)
				cmass_leaf_inc_min = k_latosa * cmass_sap / (wooddens * height * sla) - cmass_leaf;
			else
				cmass_leaf_inc_min = 0.0;

			// Calculate minimum root increment to support minimum resulting
			// leaf biomass
			// Eqn (3)

			if (height > 0.0)
				cmass_root_inc_min = k_latosa * cmass_sap / (wooddens * height * sla * ltor) - cmass_root;
			else
				cmass_root_inc_min = 0.0;

			if (cmass_root_inc_min < 0.0) { // some roots would have to be
											// killed

				cmass_leaf_inc_min = cmass_root * ltor - cmass_leaf;
				cmass_root_inc_min = 0.0;
			}

			// TODO: Make configurable
			boolean ifcdebt = false;
			// BLARP! C debt stuff
			if (ifcdebt) {
				cmass_deficit = cmass_leaf_inc_min + cmass_root_inc_min - bminc;
				if (cmass_deficit > 0.0) {
					cmass_loan = Math.max(Math.min(cmass_deficit * CDEBT_MAXLOAN_DEFICIT,
							(cmass_sap - cmass_debt) * CDEBT_MAXLOAN_MASS), 0.0);
					bminc += cmass_loan;
					cmass_debt_inc = cmass_loan;
				} else
					cmass_debt_inc = 0.0;
			} else
				cmass_debt_inc = 0.0;

			if ((cmass_root_inc_min >= 0.0 && cmass_leaf_inc_min >= 0.0
					&& cmass_root_inc_min + cmass_leaf_inc_min <= bminc) || bminc <= 0.0) {

				// Normal allocation (positive increment to all living C
				// compartments)

				// Calculation of leaf mass increment (lminc_ind) satisfying Eqn
				// (13)
				// using bisection method (Press et al 1986)

				// Set values for global variables for reuse by function f

				double k1 = Math.pow(k_allom2, 2.0 / k_allom3) * 4.0 / Math.PI / wooddens;
				double k2 = 1.0 + 2 / k_allom3;
				double k3 = k_latosa / wooddens / sla;
				double b = cmass_sap + bminc - cmass_leaf / ltor + cmass_root;
				double ltor_g = ltor;
				double cmass_leaf_g = cmass_leaf;
				double cmass_heart_g = cmass_heart;

				x1 = 0.0;
				x2 = (bminc - (cmass_leaf / ltor - cmass_root)) / (1.0 + 1.0 / ltor);
				dx = (x2 - x1) / NSEG;

				if (cmass_leaf < 1.0e-10)
					x1 += dx; // to avoid division by zero

				// Evaluate f(x1), i.e. Eqn (13) at cmass_leaf_inc = x1

				fx1 = f(x1, k1, k2, k3, b, ltor_g, cmass_heart_g, cmass_leaf_g);

				// Find approximate location of leftmost root on the interval
				// (x1,x2). Subdivide (x1,x2) into nseg equal segments seeking
				// change in sign of f(xmid) relative to f(x1).

				fmid = f(x1, k1, k2, k3, b, ltor_g, cmass_heart_g, cmass_leaf_g);

				xmid = x1;

				while (fmid * fx1 > 0.0 && xmid < x2) {

					xmid += dx;
					fmid = f(xmid, k1, k2, k3, b, ltor_g, cmass_heart_g, cmass_leaf_g);
				}

				x1 = xmid - dx;
				x2 = xmid;

				// Apply bisection to find root on new interval (x1,x2)

				if (f(x1, k1, k2, k3, b, ltor_g, cmass_heart_g, cmass_leaf_g) >= 0.0)
					sign = -1.0;
				else
					sign = 1.0;

				rtbis = x1;
				dx = x2 - x1;

				// Bisection loop
				// Search iterates on value of xmid until xmid lies within
				// xacc of the root, i.e. until |xmid-x|<xacc where f(x)=0

				fmid = 1.0; // dummy value to guarantee entry into loop
				j = 0; // number of iterations so far

				while (dx >= XACC && Math.abs(fmid) > YACC && j <= JMAX) {

					dx *= 0.5;
					xmid = rtbis + dx;

					fmid = f(xmid, k1, k2, k3, b, ltor_g, cmass_heart_g, cmass_leaf_g);

					if (fmid * sign <= 0.0)
						rtbis = xmid;
					j++;
				}

				// Now rtbis contains numerical solution for cmass_leaf_inc
				// given Eqn (13)

				cmass_leaf_inc = rtbis;

				// Calculate increments in other compartments

				cmass_root_inc = (cmass_leaf_inc + cmass_leaf) / ltor - cmass_root; // Eqn
																					// (3)
				cmass_sap_inc = bminc - cmass_leaf_inc - cmass_root_inc; // Eqn
																			// (1)

				// guess2008 - extra check - abnormal allocation can still
				// happen if ltor is very small
				if ((cmass_root_inc > 50 || cmass_root_inc < -50) && ltor < 0.0001) {
					cmass_leaf_inc = 0.0;
					cmass_root_inc = bminc;
					cmass_sap_inc = -cmass_sap;
					cmass_heart_inc = -cmass_sap_inc;
				}

				// Negative sapwood increment larger than existing sapwood or
				// if debt becomes larger than existing woody biomass
				if (cmass_sap < -cmass_sap_inc
						|| cmass_sap + cmass_sap_inc + cmass_heart < cmass_debt + cmass_debt_inc) {

					// Abnormal allocation: reduction in some biomass
					// compartment(s) to
					// satisfy allometry

					// Attempt to distribute this year's production among leaves
					// and roots only
					// Eqn (3)

					cmass_leaf_inc = (bminc - cmass_leaf / ltor + cmass_root) / (1.0 + 1.0 / ltor);
					cmass_root_inc = bminc - cmass_leaf_inc;

					// Make sure we don't end up with negative cmass_leaf
					cmass_leaf_inc = Math.max(-cmass_leaf, cmass_leaf_inc);

					// Make sure we don't end up with negative cmass_root
					cmass_root_inc = Math.max(-cmass_root, cmass_root_inc);

					// If biomass of roots and leafs can't meet biomass decrease
					// then
					// sapwood also needs to decrease
					cmass_sap_inc = bminc - cmass_leaf_inc - cmass_root_inc;

					// Make sure we don't end up with negative cmass_sap
					if (cmass_sap_inc < -cmass_sap) {
						exceeds_cmass = -(cmass_sap + cmass_sap_inc);
						cmass_sap_inc = -cmass_sap;
					}

					// Comment: Can happen that biomass decrease is larger than
					// biomass in all compartments.
					// Then bminc is more negative than there is biomass to
					// respire
				}
			} else {

				// Abnormal allocation: reduction in some biomass compartment(s)
				// to
				// satisfy allometry

				// Attempt to distribute this year's production among leaves and
				// roots only
				// Eqn (3)

				cmass_leaf_inc = (bminc - cmass_leaf / ltor + cmass_root) / (1.0 + 1.0 / ltor);

				if (cmass_leaf_inc > 0.0) {

					// Positive allocation to leaves

					cmass_root_inc = bminc - cmass_leaf_inc; // Eqn (1)

					// Add killed roots (if any) to litter

					// guess2008 - back to LPJF method in this case
					// if (cmass_root_inc<0.0) litter_root_inc=-cmass_root_inc;
					if (cmass_root_inc < 0.0) {
						cmass_leaf_inc = bminc;
						cmass_root_inc = (cmass_leaf_inc + cmass_leaf) / ltor - cmass_root; // Eqn
																							// (3)
					}

				} else {

					// Negative or zero allocation to leaves
					// Eqns (1), (3)

					cmass_root_inc = bminc;
					cmass_leaf_inc = (cmass_root + cmass_root_inc) * ltor - cmass_leaf;
				}

				// Make sure we don't end up with negative cmass_leaf
				if (cmass_leaf_inc < -cmass_leaf) {
					exceeds_cmass += -(cmass_leaf + cmass_leaf_inc);
					cmass_leaf_inc = -cmass_leaf;
				}

				// Make sure we don't end up with negative cmass_root
				if (cmass_root_inc < -cmass_root) {
					exceeds_cmass += -(cmass_root + cmass_root_inc);
					cmass_root_inc = -cmass_root;
				}

				// Add killed leaves to litter
				litter_leaf_inc = Math.max(-cmass_leaf_inc, 0.0);

				// Add killed roots to litter
				litter_root_inc = Math.max(-cmass_root_inc, 0.0);

				// Calculate increase in sapwood mass (which must be negative)
				// Eqn (2)
				cmass_sap_inc = (cmass_leaf_inc + cmass_leaf) * wooddens * height * sla / k_latosa - cmass_sap;

				// Make sure we don't end up with negative cmass_sap
				if (cmass_sap_inc < -cmass_sap) {
					exceeds_cmass += -(cmass_sap + cmass_sap_inc);
					cmass_sap_inc = -cmass_sap;
				}

				// Convert killed sapwood to heartwood
				cmass_heart_inc = -cmass_sap_inc;
			}
		} else if (lifeform == LifeForm.GRASS) {

			// GRASS ALLOCATION
			// Allocation attempts to distribute biomass increment (bminc) among
			// leaf
			// and root compartments, i.e.
			// (14) bminc = cmass_leaf_inc + cmass_root_inc
			// while satisfying Eqn(3)

			cmass_leaf_inc = (bminc - cmass_leaf / ltor + cmass_root) / (1.0 + 1.0 / ltor);
			cmass_root_inc = bminc - cmass_leaf_inc;

			if (bminc >= 0.0) {

				// Positive biomass increment

				if (cmass_leaf_inc < 0.0) {

					// Positive bminc, but ltor causes negative allocation to
					// leaves,
					// put all of bminc into roots

					cmass_root_inc = bminc;
					cmass_leaf_inc = (cmass_root + cmass_root_inc) * ltor - cmass_leaf; // Eqn
																						// (3)
				} else if (cmass_root_inc < 0.0) {

					// Positive bminc, but ltor causes negative allocation to
					// roots,
					// put all of bminc into leaves

					cmass_leaf_inc = bminc;
					cmass_root_inc = (cmass_leaf + bminc) / ltor - cmass_root;
				}

				// Make sure we don't end up with negative cmass_leaf
				if (cmass_leaf_inc < -cmass_leaf) {
					exceeds_cmass += -(cmass_leaf + cmass_leaf_inc);
					cmass_leaf_inc = -cmass_leaf;
				}

				// Make sure we don't end up with negative cmass_root
				if (cmass_root_inc < -cmass_root) {
					exceeds_cmass += -(cmass_root + cmass_root_inc);
					cmass_root_inc = -cmass_root;
				}

				// Add killed leaves to litter
				litter_leaf_inc = Math.max(-cmass_leaf_inc, 0.0);

				// Add killed roots to litter
				litter_root_inc = Math.max(-cmass_root_inc, 0.0);
			} else if (bminc < 0) {

				// Abnormal allocation: negative biomass increment

				// Negative increment is respiration (neg bminc) or/and
				// increment in other
				// compartments leading to no litter production

				if (bminc < -(cmass_leaf + cmass_root)) {

					// Biomass decrease is larger than existing biomass

					exceeds_cmass = -(bminc + cmass_leaf + cmass_root);

					cmass_leaf_inc = -cmass_leaf;
					cmass_root_inc = -cmass_root;
				} else if (cmass_root_inc < 0.0) {

					// Negative allocation to root
					// Make sure we don't end up with negative cmass_root

					if (cmass_root < -cmass_root_inc) {
						cmass_leaf_inc = bminc + cmass_root;
						cmass_root_inc = -cmass_root;
					}
				} else if (cmass_leaf_inc < 0.0) {

					// Negative allocation to leaf
					// Make sure we don't end up with negative cmass_leaf

					if (cmass_leaf < -cmass_leaf_inc) {
						cmass_root_inc = bminc + cmass_leaf;
						cmass_leaf_inc = -cmass_leaf;
					}
				}
			}
		}

		// Check C budget after allocation

		// maximum carbon mismatch
		double EPS = 1.0e-12;

		assert (Math.abs(bminc + exceeds_cmass - (cmass_leaf_inc + cmass_root_inc + cmass_sap_inc + cmass_heart_inc
				+ litter_leaf_inc + litter_root_inc)) < EPS);
	}

	// / GROWTH
	/**
	 * Tissue turnover and allocation of fixed carbon to reproduction and new
	 * biomass Accumulated NPP (assimilation minus maintenance and growth
	 * respiration) on patch or modelled area basis assumed to be given by 'anpp'
	 * member variable for each individual. Should be called by framework at the end
	 * of each simulation year for modelling of turnover, allocation and growth,
	 * prior to vegetation dynamics and disturbance
	 */
	void growth(Patch patch) {
		// TODO: Make configurable (and into enum)
		int scaling_mode = 0;

		boolean killed = false;

		int npft = _configuration.getPFTs().size();
		Stand stand = patch.stand;

		// minimum carbon mass allowed (kgC/m2)
		double MINCMASS = 1.0e-8;
		// maximum carbon mass allowed (kgC/m2)
		double MAXCMASS = 1.0e8;

		double CDEBT_PAYBACK_RATE = 0.2;

		// carbon biomass increment (component of NPP available for production of
		// new biomass) for this time period on modelled area basis (kgC/m2)
		double bminc = 0.0;
		// C allocated to reproduction this time period on modelled area basis (kgC/m2)
		double cmass_repr = 0.0;
		// increment in leaf C biomass following allocation, on individual basis (kgC)
		double cmass_leaf_inc = 0.0;
		// increment in root C biomass following allocation, on individual basis (kgC)
		double cmass_root_inc = 0.0;
		// increment in sapwood C biomass following allocation, on individual basis
		// (kgC)
		double cmass_sap_inc = 0.0;
		// increment in heartwood C biomass following allocation, on individual basis
		// (kgC)
		double cmass_heart_inc = 0.0;
		//
		double cmass_debt_inc = 0.0;
		// increment in harvestable organ C biomass following allocation, on individual
		// basis (kgC)
		double cmass_ho_inc = 0.0;
		double cmass_agpool_inc = 0.0;
		double cmass_stem_inc = 0.0;
		// increment in leaf litter following allocation, on individual basis (kgC)
		double litter_leaf_inc = 0.0;
		// increment in root litter following allocation, on individual basis (kgC)
		double litter_root_inc = 0.0;
		// negative increment that exceeds existing biomass following allocation, on
		// individual basis (kgC)
		double exceeds_cmass = 0;
		// C biomass of leaves in "excess" of set allocated last year to raingreen PFT
		// last year (kgC/m2)
		double cmass_excess = 0.0;
		// Raingreen nitrogen demand for leaves dropped during the year
		double raingreen_ndemand;
		// Nitrogen stress scalar for leaf to root allocation
		double nscal;
		// Leaf C:N ratios before growth
		double cton_leaf_bg;
		// Root C:N ratios before growth
		double cton_root_bg;
		// Sap C:N ratios before growth
		double cton_sap_bg;

		double dval = 0.0;
		int p;

		// Obtain reference to Vegetation object for this patch
		Vegetation vegetation = patch.vegetation;
		Gridcell gridcell = vegetation.patch.stand.getGridcell();

		// On first call to function growth this year (patch #0), initialise stand-PFT
		// record of summed allocation to reproduction

		if (patch.id == 0)
			for (p = 0; p < npft; p++)
				stand.pft.get(p).cmass_repr = 0.0;

		// Loop through individuals

		for (Individual indiv : vegetation) {
			// For this individual

			// Calculate vegetation carbon and nitrogen mass before growth to determine
			// vegetation C:N ratios
			indiv.cmass_veg = indiv.cmass_leaf + indiv.cmass_root + indiv.cmass_wood();
			indiv.nmass_veg = indiv.nmass_leaf + indiv.nmass_root + indiv.nmass_wood();

			// Save real compartment C:N ratios before growth
			// phen is switched off for leaf and root
			// Leaf
			cton_leaf_bg = indiv.cton_leaf(false);

			// Root
			cton_root_bg = indiv.cton_root(false);

			// Sap
			cton_sap_bg = indiv.cton_sap();

			// Save leaf annual average C:N ratio
			if (!Utils.negligible(indiv.nday_leafon))
				indiv.cton_leaf_aavr /= indiv.nday_leafon;
			else
				indiv.cton_leaf_aavr = indiv.pft.cton_leaf_max;

			// Nitrogen stress scalar for leaf to root allocation (adopted from Zaehle and
			// Friend 2010 SM eq
			// 19)
			double cton_leaf_aopt = Math.max(indiv.cton_leaf_aopt, indiv.pft.cton_leaf_avr);

			if (stand.ifnlim_stand())
				nscal = Math.min(1.0, cton_leaf_aopt / indiv.cton_leaf_aavr);
			else
				nscal = 1.0;

			// Set leaf:root mass ratio based on water stress parameter
			// or nitrogen stress scalar
			indiv.ltor = Math.min(indiv.wscal_mean(), nscal) * indiv.pft.ltor_max;

			// Move leftover compartment nitrogen storage to longterm storage
			if (!indiv.has_daily_turnover()) {
				indiv.nstore_longterm += indiv.nstore_labile;
				indiv.nstore_labile = 0.0;
			}

			indiv.deltafpc = 0.0;

			// if (Utils.negligible(indiv.densindiv))
			//// fail("growth: negligible densindiv for %s",(char*)indiv.pft.name);// ???
			//// dprintf("growth: negligible densindiv for %s\n",(char*)indiv.pft.name);
			// else {

			// Allocation to reproduction
			if (!indiv.istruecrop_or_intercropgrass())
				reproduction(indiv.pft.reprfrac, indiv.anpp, bminc, cmass_repr);

			raingreen_ndemand = 0.0;

			// added bminc check. Otherwise we get -ve litter_leaf for grasses when
			// indiv.anpp < 0.
			//
			// cmass_excess-code inactivated for grass pft:s, due to frequent oscillations
			// between high bminc
			// and zero bminc in
			// certain grasslands using updated leaflong-value for grass-pft:s (0.5)
			double nrelocfrac = _configuration.getNRelocFrac();
			if (bminc >= 0 && indiv.pft.phenology == Phenology.RAINGREEN) {

				// Raingreen PFTs: reduce biomass increment to account for NPP
				// allocated to extra leaves during the past year.
				// Excess allocation to leaves given by:
				// aphen_raingreen / ( leaf_longevity * year_length) * cmass_leaf -
				// cmass_leaf

				// BLARP! excess allocation to roots now also included (assumes leaf longevity =
				// root
				// longevity)

				cmass_excess = Math.max(indiv.aphen_raingreen /
				// TODO: Update to get proper year length (366 or 365)
						(indiv.pft.leaflong * 365.0) * (indiv.cmass_leaf + indiv.cmass_root) - indiv.cmass_leaf
						- indiv.cmass_root, 0.0);

				if (cmass_excess > bminc)
					cmass_excess = bminc;

				// Transfer excess leaves to litter
				// only for 'alive' individuals
				if (indiv.alive) {
					patch.pft.get(indiv.pft.id).litter_leaf += cmass_excess;
					if (!Utils.negligible(cton_leaf_bg))
						raingreen_ndemand = Math.min(indiv.nmass_leaf, cmass_excess / cton_leaf_bg);
					else
						raingreen_ndemand = 0.0;

					patch.pft.get(indiv.pft.id).nmass_litter_leaf += raingreen_ndemand * (1.0 - nrelocfrac);
					indiv.nstore_longterm += raingreen_ndemand * nrelocfrac;
					indiv.nmass_leaf -= raingreen_ndemand;
				}

				// Deduct from this year's C biomass increment
				// added alive check
				if (indiv.alive)
					bminc -= cmass_excess;
			}

			// All yearly harvest events
			killed = harvest_year(indiv);

			if (!killed) {

				if (!indiv.has_daily_turnover()) {
					// Tissue turnover and associated litter production
					growth_daily.turnover(indiv.pft.turnover_leaf, indiv.pft.turnover_root, indiv.pft.turnover_sap,
							indiv.pft.lifeform, indiv.pft.landcover, indiv.cmass_leaf, indiv.cmass_root,
							indiv.cmass_sap, indiv.cmass_heart, indiv.nmass_leaf, indiv.nmass_root, indiv.nmass_sap,
							indiv.nmass_heart, patch.pft.get(indiv.pft.id).litter_leaf,
							patch.pft.get(indiv.pft.id).litter_root, patch.pft.get(indiv.pft.id).nmass_litter_leaf,
							patch.pft.get(indiv.pft.id).nmass_litter_root, indiv.nstore_longterm, indiv.max_n_storage,
							indiv.alive);
				}
				// Update stand record of reproduction by this PFT
				stand.pft.get(indiv.pft.id).cmass_repr += cmass_repr / stand.npatch();

				// Transfer reproduction straight to litter
				// only for 'alive' individuals
				if (indiv.alive) {
					patch.pft.get(indiv.pft.id).litter_repr += cmass_repr;
				}

				if (indiv.pft.lifeform == LifeForm.TREE) {

					// TREE GROWTH

					// BLARP! Try and pay back part of cdebt
					// TODO: Make configurable
					boolean ifcdebt = false;
					if (ifcdebt && bminc > 0.0) {
						double cmass_payback = Math.min(indiv.cmass_debt * CDEBT_PAYBACK_RATE, bminc);
						bminc -= cmass_payback;
						indiv.cmass_debt -= cmass_payback;
					}

					// Allocation: note conversion of mass values from grid cell area
					// to individual basis

					allocation(bminc / indiv.densindiv, indiv.cmass_leaf / indiv.densindiv,
							indiv.cmass_root / indiv.densindiv, indiv.cmass_sap / indiv.densindiv,
							indiv.cmass_debt / indiv.densindiv, indiv.cmass_heart / indiv.densindiv, indiv.ltor,
							indiv.height, indiv.pft.sla, indiv.pft.wooddens, LifeForm.TREE, indiv.pft.k_latosa,
							indiv.pft.k_allom2, indiv.pft.k_allom3, cmass_leaf_inc, cmass_root_inc, cmass_sap_inc,
							cmass_debt_inc, cmass_heart_inc, litter_leaf_inc, litter_root_inc, exceeds_cmass);

					// Update carbon pools and litter (on area basis)
					// (litter not accrued for not 'alive' individuals - Ben 2007-11-28)

					// Leaves
					indiv.cmass_leaf += cmass_leaf_inc * indiv.densindiv;

					// Roots
					indiv.cmass_root += cmass_root_inc * indiv.densindiv;

					// Sapwood
					indiv.cmass_sap += cmass_sap_inc * indiv.densindiv;

					// Heartwood
					indiv.cmass_heart += cmass_heart_inc * indiv.densindiv;

					// If negative sap growth, then nrelocfrac of nitrogen will go to heart wood and
					// (1.0 - nreloctrac) will go to storage
					if (cmass_sap_inc < 0.0) {
						indiv.nmass_heart -= cmass_sap_inc * indiv.densindiv / cton_sap_bg * nrelocfrac;
						indiv.nmass_sap += cmass_sap_inc * indiv.densindiv / cton_sap_bg;
					}

					// C debt
					indiv.cmass_debt += cmass_debt_inc * indiv.densindiv;

					// alive check before ensuring C balance
					if (indiv.alive) {
						patch.pft.get(indiv.pft.id).litter_leaf += litter_leaf_inc * indiv.densindiv;
						patch.pft.get(indiv.pft.id).litter_root += litter_root_inc * indiv.densindiv;

						// C litter exceeding existing biomass
						indiv.report_flux(PerPFTFluxType.NPP, exceeds_cmass * indiv.densindiv);
						indiv.report_flux(PerPFTFluxType.RA, -exceeds_cmass * indiv.densindiv);
					}

					// Nitrogen litter always return to soil litter and storage
					// Leaf
					patch.pft.get(indiv.pft.id).nmass_litter_leaf += litter_leaf_inc * indiv.densindiv / cton_leaf_bg
							* (1.0 - nrelocfrac);
					indiv.nstore_longterm += litter_leaf_inc * indiv.densindiv / cton_leaf_bg * nrelocfrac;

					// Root
					patch.pft.get(indiv.pft.id).nmass_litter_root += litter_root_inc * indiv.densindiv / cton_root_bg
							* (1.0 - nrelocfrac);
					indiv.nstore_longterm += litter_root_inc * indiv.densindiv / cton_root_bg * nrelocfrac;

					// Subtracting litter nitrogen from individuals
					indiv.nmass_leaf -= Math.min(indiv.nmass_leaf, litter_leaf_inc * indiv.densindiv / cton_leaf_bg);
					indiv.nmass_root -= Math.min(indiv.nmass_root, litter_root_inc * indiv.densindiv / cton_root_bg);

					// If negative sap growth, then nrelocfrac of nitrogen will go to heart wood and
					// (1.0 - nreloctrac) will go to storage
					if (cmass_sap_inc < 0.0) {
						indiv.nstore_longterm -= cmass_sap_inc * indiv.densindiv / cton_sap_bg * (1.0 - nrelocfrac);
					}

					// Update individual age

					indiv.age++;

					// Kill individual and transfer biomass to litter if any biomass
					// compartment negative

					if (indiv.cmass_leaf < MINCMASS || indiv.cmass_root < MINCMASS || indiv.cmass_sap < MINCMASS) {

						indiv.kill();

						vegetation.remove(indiv);
						killed = true;
					}

					if (scaling_mode == 2)
						indiv.densindiv *= stand.scale_LC_change;
				} else if (indiv.pft.lifeform == LifeForm.GRASS) {

					// GRASS GROWTH

					// True crops do not use bminc.or cmass_leaf etc.
					if (indiv.istruecrop_or_intercropgrass()) {
						// transfer crop cmass increase values to common variables
						growth_crop_year(indiv, cmass_leaf_inc, cmass_root_inc, cmass_ho_inc, cmass_agpool_inc,
								cmass_stem_inc);

						exceeds_cmass = 0.0; // exceeds_cmass not used for true crops
					} else {
						allocation(bminc, indiv.cmass_leaf, indiv.cmass_root, 0.0, 0.0, 0.0, indiv.ltor, 0.0, 0.0, 0.0,
								LifeForm.GRASS, 0.0, 0.0, 0.0, cmass_leaf_inc, cmass_root_inc, dval, dval, dval,
								litter_leaf_inc, litter_root_inc, exceeds_cmass);
					}

					if (indiv.pft.landcover == LandcoverType.CROP) {
						if (indiv.istruecrop_or_intercropgrass())
							yield_crop(indiv);
						else
							yield_pasture(indiv, cmass_leaf_inc);
					}

					// Update carbon pools and litter (on area basis)
					// only litter in the case of 'alive' individuals

					// Leaves
					indiv.cmass_leaf += cmass_leaf_inc;

					// Roots
					indiv.cmass_root += cmass_root_inc;

					if (indiv.pft.landcover == LandcoverType.CROP) {
						indiv.cropindiv.cmass_ho += cmass_ho_inc;
						indiv.cropindiv.cmass_agpool += cmass_agpool_inc;
						indiv.cropindiv.cmass_stem += cmass_stem_inc;
					}

					if (indiv.pft.phenology != Phenology.CROPGREEN
							&& !(indiv.has_daily_turnover() && indiv.continous_grass())) {

						// alive check before ensuring C balance
						if (indiv.alive && !indiv.istruecrop_or_intercropgrass()) {

							patch.pft.get(indiv.pft.id).litter_leaf += litter_leaf_inc;
							patch.pft.get(indiv.pft.id).litter_root += litter_root_inc;

							// C litter exceeding existing biomass
							indiv.report_flux(PerPFTFluxType.NPP, exceeds_cmass * indiv.densindiv);
							indiv.report_flux(PerPFTFluxType.RA, -exceeds_cmass * indiv.densindiv);
						}

						// Nitrogen always return to soil litter and storage
						// Leaf
						patch.pft.get(indiv.pft.id).nmass_litter_leaf += litter_leaf_inc * indiv.densindiv
								/ cton_leaf_bg * (1.0 - nrelocfrac);
						indiv.nstore_longterm += litter_leaf_inc * indiv.densindiv / cton_leaf_bg * nrelocfrac;

						// Root
						patch.pft.get(indiv.pft.id).nmass_litter_root += litter_root_inc * indiv.densindiv
								/ cton_root_bg * (1.0 - nrelocfrac);
						indiv.nstore_longterm += litter_root_inc / cton_root_bg * nrelocfrac;

						// Subtracting litter nitrogen from individuals
						indiv.nmass_leaf -= Math.min(indiv.nmass_leaf,
								litter_leaf_inc * indiv.densindiv / cton_leaf_bg);
						indiv.nmass_root -= Math.min(indiv.nmass_root,
								litter_root_inc * indiv.densindiv / cton_root_bg);
					}
					// Kill individual and transfer biomass to litter if either biomass
					// compartment negative

					if ((indiv.cmass_leaf < MINCMASS || indiv.cmass_root < MINCMASS)
							&& !indiv.istruecrop_or_intercropgrass()) {

						indiv.kill();

						vegetation.remove(indiv);
						killed = true;
					}
				}

				if (!killed && indiv.pft.phenology != Phenology.CROPGREEN
						&& !(indiv.has_daily_turnover() && indiv.continous_grass())) {
					// Update nitrogen longtime storage

					// Nitrogen approx retranslocated next year
					double retransn_nextyear = indiv.cmass_leaf * indiv.pft.turnover_leaf / cton_leaf_bg * nrelocfrac
							+ indiv.cmass_root * indiv.pft.turnover_root / cton_root_bg * nrelocfrac;

					if (indiv.pft.lifeform == LifeForm.TREE)
						retransn_nextyear += indiv.cmass_sap * indiv.pft.turnover_sap / cton_sap_bg * nrelocfrac;

					// Assume that raingreen will lose same amount of N through extra leaves next
					// year
					if (indiv.alive && bminc >= 0
							&& (indiv.pft.phenology == Phenology.RAINGREEN || indiv.pft.phenology == Phenology.ANY))
						retransn_nextyear -= Math.min(raingreen_ndemand, retransn_nextyear);

					// Max longterm nitrogen storage
					if (indiv.pft.lifeform == LifeForm.TREE)
						indiv.max_n_storage = Math.max(0.0,
								Math.min(indiv.cmass_sap * indiv.pft.fnstorage / cton_leaf_bg, retransn_nextyear));
					else // GRASS
						indiv.max_n_storage = Math.max(0.0,
								Math.min(indiv.cmass_root * indiv.pft.fnstorage / cton_leaf_bg, retransn_nextyear));

					// Scale this year productivity to max storage
					if (indiv.anpp > 0.0) {
						indiv.scale_n_storage = Math.max(indiv.max_n_storage * 0.1,
								indiv.max_n_storage - retransn_nextyear) * cton_leaf_bg / indiv.anpp;
					} // else use last years scaling factor
				}
			}

			if (!killed) {

				if (!allometry(indiv)) { // crops never enter this code

					indiv.kill();

					vegetation.remove(indiv);
					killed = true;
				}

				if (!killed) {
					if (!indiv.alive) {
						// The individual has survived its first year...
						indiv.alive = true;

						// ...now we can start counting its fluxes,
						// debit current biomass as establishment flux
						if (!indiv.istruecrop_or_intercropgrass()) {
							indiv.report_flux(PerPatchFluxType.ESTC, -(indiv.cmass_leaf + indiv.cmass_root
									+ indiv.cmass_sap + indiv.cmass_heart - indiv.cmass_debt));
						}
					}

					// Move long-term nitrogen storage pool to labile storage pool for usage next
					// year
					if (!indiv.has_daily_turnover()) {
						indiv.nstore_labile = indiv.nstore_longterm;
						indiv.nstore_longterm = 0.0;
					}

					// ... on to next individual
				}
			}

			// Flush nitrogen free litter from reproduction straight to atmosphere
			flush_litter_repr(patch);
		}
	}

	void flush_litter_repr(Patch patch) {

		// Returns nitrogen-free reproduction "litter" to atmosphere
		for (PatchPFT pft : patch.pft) {
			// Updated soil fluxes
			patch.fluxes.report_flux(PerPatchFluxType.REPRC, pft.litter_repr);
			pft.litter_repr = 0.0;
		}
	}

	// / Yield function for pasture grass grown in cropland landcover
	void yield_pasture(Individual indiv, double cmass_leaf_inc) {

		CropIndiv cropindiv = indiv.getCropIndiv();

		// Normal CC3G/CC4G stand growth (Pasture)

		// OK if turnover_leaf==1.0, else
		// (cmass_leaf+cmass_leaf_inc)*indiv.pft.harv_eff*2.0
		if (cmass_leaf_inc > 0.0)
			cropindiv.yield = cmass_leaf_inc * indiv.pft.harv_eff * 2.0;
		else
			cropindiv.yield = 0.0;
		cropindiv.harv_yield = cropindiv.yield; // Although no specified harvest
												// date, harv_yield is set for
												// compatibility.

		return;
	}

	// / Yield function for true crops and intercrop grass.
	void yield_crop(Individual indiv) {

		CropIndiv cropindiv = indiv.getCropIndiv();

		if (indiv.pft.phenology == Phenology.ANY) { // grass intercrop yield

			// Yield dry wieght of allocated harvestable organs this year; NB
			// independent from harvest calculation in harvest_crop (different
			// years)
			if (cropindiv.ycmass_leaf > 0.0)
				cropindiv.yield = cropindiv.ycmass_leaf * indiv.pft.harv_eff_ic * 2.0;
			else
				cropindiv.yield = 0.0;

			// Yield dry wieght of actually harvest products this year; NB as
			// above
			if (cropindiv.harv_cmass_leaf > 0.0)
				cropindiv.harv_yield = cropindiv.harv_cmass_leaf * indiv.pft.harv_eff_ic * 2.0;
			else
				cropindiv.harv_yield = 0.0;
		} else if (indiv.pft.phenology == Phenology.CROPGREEN) { // true crop
																	// yield

			// Yield dry wieght of allocated harvestable organs this year; NB
			// independent from harvest calculation in harvest_crop (different
			// years)
			if (cropindiv.ycmass_ho > 0.0)
				cropindiv.yield = cropindiv.ycmass_ho * indiv.pft.harv_eff * 2.0;// Should
																					// be
																					// /0.446
																					// instead
			else
				cropindiv.yield = 0.0;

			// Yield dry wieght of actually harvest products this year; NB as
			// above
			if (cropindiv.harv_cmass_ho > 0.0)
				cropindiv.harv_yield = cropindiv.harv_cmass_ho * indiv.pft.harv_eff * 2.0;
			else
				cropindiv.harv_yield = 0.0;

			// Yield dry wieght of actually harvest products this year; NB as
			// above
			for (int i = 0; i < 2; i++) {
				if (cropindiv.cmass_ho_harvest[i] > 0.0)
					cropindiv.yield_harvest[i] = cropindiv.cmass_ho_harvest[i] * indiv.pft.harv_eff * 2.0;
				else
					cropindiv.yield_harvest[i] = 0.0;
			}
		}

		return;
	}

	// / Transfer of this year's growth (ycmass_xxx) to cmass_xxx_inc
	/**
	 * and pasture grass grown in cropland. OUTPUT PARAMETERS \param cmass_leaf_inc
	 * leaf C biomass (kgC/m2) \param cmass_root_inc fine root C biomass (kgC/m2)
	 * \param cmass_ho_inc harvestable organ C biomass (kgC/m2) \param
	 * cmass_agpool_inc above-ground pool C biomass (kgC/m2) \param cmass_stem_inc
	 * stem C biomass (kgC/m2)
	 */

	void growth_crop_year(Individual indiv, double cmass_leaf_inc, double cmass_root_inc, double cmass_ho_inc,
			double cmass_agpool_inc, double cmass_stem_inc) {

		// true crop growth and grass intercrop growth; NB: bminit (cmass_repr &
		// cmass_excess subtracted) not used !

		if (indiv.has_daily_turnover()) {

			indiv.cmass_leaf = 0.0;
			indiv.cmass_root = 0.0;
			indiv.cropindiv.cmass_ho = 0.0;
			indiv.cropindiv.cmass_agpool = 0.0;
			indiv.cropindiv.cmass_stem = 0.0;

			// Not completely accurate here when comparing this year's cmass
			// after turnover with cmass increase (ycmass),
			// which could be from the preceding season, but probably OK, since
			// values are not used for C balance.
			if (indiv.continous_grass()) {
				indiv.cmass_leaf = indiv.cmass_leaf_post_turnover;
				indiv.cmass_root = indiv.cmass_root_post_turnover;
			}
		}

		cmass_leaf_inc = indiv.cropindiv.ycmass_leaf + indiv.cropindiv.ycmass_dead_leaf;
		cmass_root_inc = indiv.cropindiv.ycmass_root;
		cmass_ho_inc = indiv.cropindiv.ycmass_ho;
		cmass_agpool_inc = indiv.cropindiv.ycmass_agpool;
		cmass_stem_inc = indiv.cropindiv.ycmass_stem;

		return;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// REPRODUCTION
	// Internal function (do not call directly from framework)
	void reproduction(double reprfrac, double npp, double bminc, double cmass_repr) {

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
			return;
		}

		// Negative NPP - no reproduction cost

		cmass_repr = 0.0;
		bminc = npp;
	}

	// / Scaling of last year's or harvest day individual carbon and nitrogen
	// member values in stands that have increased their area fraction this
	// year.
	/**
	 * Called immediately before harvest functions in growth() or
	 * allocation_crop_daily().
	 */
	void scale_indiv(Individual indiv, boolean scale_grsC) {
		double scale = 1.0;
		Stand stand = indiv.vegetation.patch.stand;
		Gridcell gridcell = stand.getGridcell();

		// TODO: Make configurable
		int scaling_mode = 0;

		// Scale individual's C and N mass in stands that have increased in area
		// this year by (old area/new area):
		if (stand.scale_LC_change < 1.0) {
			if (scaling_mode == 0)
				return;
			else
				scale = stand.scale_LC_change;
		} else
			return;

		if (scale_grsC) {

			if (indiv.pft.landcover == LandcoverType.CROP) {

				if (indiv.has_daily_turnover()) {

					indiv.cropindiv.grs_cmass_leaf -= indiv.cropindiv.grs_cmass_leaf_luc * (1.0 - scale);
					indiv.cropindiv.grs_cmass_root -= indiv.cropindiv.grs_cmass_root_luc * (1.0 - scale);
					indiv.cropindiv.grs_cmass_ho -= indiv.cropindiv.grs_cmass_ho_luc * (1.0 - scale);
					indiv.cropindiv.grs_cmass_agpool -= indiv.cropindiv.grs_cmass_agpool_luc * (1.0 - scale);
					indiv.cropindiv.grs_cmass_dead_leaf -= indiv.cropindiv.grs_cmass_dead_leaf_luc * (1.0 - scale);
					indiv.cropindiv.grs_cmass_stem -= indiv.cropindiv.grs_cmass_stem_luc * (1.0 - scale);

					double negative_cmass = indiv.check_C_mass();
					// if(negative_cmass > 1.0e-14)
					// dprintf("Year %d day %d Stand %d indiv %d: Negative C mass in scale_indiv:
					// %.15f\n",
					// date.year, date.day, indiv.vegetation.patch.stand.id,
					// indiv.id, -negative_cmass);
				} else {
					indiv.cropindiv.grs_cmass_leaf *= scale;
					indiv.cropindiv.grs_cmass_root *= scale;
					indiv.cropindiv.grs_cmass_ho *= scale;
					indiv.cropindiv.grs_cmass_agpool *= scale;
					indiv.cropindiv.grs_cmass_plant *= scale; // grs_cmass_plant
																// not used
					indiv.cropindiv.grs_cmass_dead_leaf *= scale;
					indiv.cropindiv.grs_cmass_stem *= scale;
				}
			}
		} else {

			indiv.cmass_root *= scale;
			indiv.cmass_leaf *= scale;
			indiv.cmass_heart *= scale;
			indiv.cmass_sap *= scale;
			indiv.cmass_debt *= scale;

			if (indiv.pft.landcover == LandcoverType.CROP) {
				indiv.cropindiv.cmass_agpool *= scale;
				indiv.cropindiv.cmass_ho *= scale;
			}
		}

		// Deduct individual N present day 0 this year in stands that have
		// increased in area this year, scaled by (1 - old area/new area):
		indiv.nmass_root = indiv.nmass_root - indiv.nmass_root_luc * (1.0 - scale);
		indiv.nmass_leaf = indiv.nmass_leaf - indiv.nmass_leaf_luc * (1.0 - scale);
		indiv.nmass_heart = indiv.nmass_heart - indiv.nmass_heart_luc * (1.0 - scale);
		indiv.nmass_sap = indiv.nmass_sap - indiv.nmass_sap_luc * (1.0 - scale);

		if (indiv.pft.landcover == LandcoverType.CROP) {
			indiv.cropindiv.nmass_agpool = indiv.cropindiv.nmass_agpool
					- indiv.cropindiv.nmass_agpool_luc * (1.0 - scale);
			indiv.cropindiv.nmass_ho = indiv.cropindiv.nmass_ho - indiv.cropindiv.nmass_ho_luc * (1.0 - scale);
			indiv.cropindiv.nmass_dead_leaf = indiv.cropindiv.nmass_dead_leaf
					- indiv.cropindiv.nmass_dead_leaf_luc * (1.0 - scale);
		}

		if (indiv.nstore_labile > indiv.nstore_labile_luc * (1.0 - scale))
			indiv.nstore_labile -= indiv.nstore_labile_luc * (1.0 - scale);
		else
			indiv.nstore_longterm -= indiv.nstore_labile_luc * (1.0 - scale);
		indiv.nstore_longterm = indiv.nstore_longterm - indiv.nstore_longterm_luc * (1.0 - scale);

		double negative_nmass = indiv.check_N_mass();
		// if(negative_nmass > 1.0e-14)
		// dprintf("Year %d day %d Stand %d indiv %d: Negative N mass in scale_indiv:
		// %.15f\n",
		// date.year, date.day, indiv.vegetation.patch.stand.id, indiv.id,
		// -negative_nmass);

	}

	// / Harvest function for pasture, representing grazing (previous year).
	/*
	 * Function for balancing carbon and nitrogen fluxes from last year's growth A
	 * fraction of leaves is harvested (pft.harv_eff) and returned as acflux_harvest
	 * This represents grazing minus return as manure. The rest is handled like
	 * natural grass in turnover(). Called from growth() last day of the year for
	 * normal harvest/grazing. Also called from landcover_dynamics() first day of
	 * the year if any natural vegetation is transferred to another land use. This
	 * calls for a scaling factor, when the pasture area has increased.
	 * 
	 * This function copies variables from an individual and it's associated
	 * patchpft and patch to a Harvest_CN struct, which is then passed on to the
	 * main harvest_crop function. After the execution of the main harvest_crop
	 * function, the output variables are copied back to the individual and patchpft
	 * and the patch-level fluxes are updated.
	 * 
	 * INPUT/OUTPUT PARAMETERS \param indiv reference to an Individual containing
	 * the following indiv-specific public members: - cmass_leaf leaf C biomass
	 * (kgC/m2) - cmass_root fine root C biomass (kgC/m2) - nmass_leaf leaf nitrogen
	 * biomass (kgN/m2) - nmass_root fine root nitrogen biomass (kgN/m2) OUTPUT
	 * PARAMETERS \param indiv reference to an Individual containing the following
	 * patchpft-specific public members: - litter_leaf new leaf C litter (kgC/m2) -
	 * litter_root new root C litter (kgC/m2) - nmass_litter_leaf new leaf nitrogen
	 * litter (kgN/m2) - nmass_litter_root new root nitrogen litter (kgN/m2) ,and
	 * the following patch-level public members: - acflux_harvest harvest flux to
	 * atmosphere (kgC/m2) - harvested_products_slow harvest products to slow pool
	 * (kgC/m2) - anflux_harvest harvest nitrogen flux out of system (kgC/m2) -
	 * harvested_products_slow_nmass harvest nitrogen products to slow pool (kgC/m2)
	 */
	void harvest_pasture(Individual indiv, PFT pft, boolean alive) {

		Harvest_CN indiv_cp = new Harvest_CN();

		indiv_cp.copy_from_indiv(indiv);

		harvest_pasture(indiv_cp, pft, alive);

		indiv_cp.copy_to_indiv(indiv);

	}

	// / Harvest function for pasture, representing grazing (previous year).
	/*
	 * Function for balancing carbon and nitrogen fluxes from last year's growth A
	 * fraction of leaves is harvested (pft.harv_eff) and returned as acflux_harvest
	 * This represents grazing minus return as manure. The rest is handled like
	 * natural grass in turnover(). Called from growth() last day of the year for
	 * normal harvest/grazing. Also called from landcover_dynamics() first day of
	 * the year if any natural vegetation is transferred to another land use. This
	 * calls for a scaling factor, when the pasture area has increased.
	 * 
	 * INPUT/OUTPUT PARAMETERS \param Harvest_CN& i struct containing the following
	 * indiv-specific public members: - cmass_leaf leaf C biomass (kgC/m2) -
	 * cmass_root fine root C biomass (kgC/m2) - nmass_leaf leaf nitrogen biomass
	 * (kgN/m2) - nmass_root fine root nitrogen biomass (kgN/m2) OUTPUT PARAMETERS
	 * \param Harvest_CN& i struct containing the following patchpft-specific public
	 * members: - litter_leaf new leaf C litter (kgC/m2) - litter_root new root C
	 * litter (kgC/m2) - nmass_litter_leaf new leaf nitrogen litter (kgN/m2) -
	 * nmass_litter_root new root nitrogen litter (kgN/m2) ,and the following
	 * patch-level public members: - acflux_harvest harvest flux to atmosphere
	 * (kgC/m2) - harvested_products_slow harvest products to slow pool (kgC/m2) -
	 * anflux_harvest harvest nitrogen flux out of system (kgC/m2) -
	 * harvested_products_slow_nmass harvest nitrogen products to slow pool (kgC/m2)
	 */
	void harvest_pasture(Harvest_CN i, PFT pft, boolean alive) {

		double harvest;

		// harvest of leaves (grazing)

		// Carbon:
		harvest = pft.harv_eff * i.cmass_leaf;

		// TODO: Make configurable
		boolean ifslowharvestpool = true;
		if (ifslowharvestpool) {
			i.harvested_products_slow += harvest * pft.harvest_slow_frac;
			harvest = harvest * (1 - pft.harvest_slow_frac);
		}
		if (alive)
			i.acflux_harvest += harvest;
		i.cmass_leaf -= harvest;

		// Nitrogen:
		// Reduced removal of N relative to C during grazing.
		double N_harvest_scale = 0.25; // Value that works. Needs to be verified
										// in literature.
		harvest = pft.harv_eff * i.nmass_leaf * N_harvest_scale;

		if (ifslowharvestpool) {
			i.harvested_products_slow_nmass += harvest * pft.harvest_slow_frac;
			harvest = harvest * (1 - pft.harvest_slow_frac);
		}
		i.anflux_harvest += harvest;
		i.nmass_leaf -= harvest;
	}

	double forest_management(Patch patch, boolean age_class_run, int age_class) {
		int year = _configuration.getSchedule().year();

		Stand stand = patch.stand;
		double minbon = 2.351; // The minimum average "bonitet" for a county in
								// Sweden
		double maxbon = 11.311; // The maximum average "bonitet" for a county in
								// Sweden
		double bonitet = 10.0; // Temporary static value

		// Continuous forestry
		// int first_cutyear = nyear_spinup; //Simulation year when continuous
		// forestry harvesting starts
		int first_cutyear = 0;
		int cut_int; // Interval between cuttings
		int patch_order; // Which year in a cutting interval the patch belongs
							// to

		// cut_int=30-(int)(15.0*(stand.bonitet-minbon)/(maxbon-minbon));
		cut_int = 30 - (int) (15.0 * (bonitet - minbon) / (maxbon - minbon));
		patch_order = (int) (patch.id * cut_int * 1.0 / (1.0 * stand.npatch()));

		if (year >= first_cutyear && ((year - first_cutyear - patch_order) % cut_int) == 0)
			return 0.40;
		else
			return 0.00;

	}

	// Use for normal forest management in calls from growth(). For clearcut
	// during landcover change, use harvest_wood() and
	// kill_remaining_vegetation()
	void clearcut(Individual indiv, PFT pft, boolean alive, double anpp, boolean killed) {

		Patch patch = indiv.vegetation.patch;
		PatchPFT ppft = patch.pft.get(indiv.pft.id);

		if (indiv.pft.lifeform == LifeForm.TREE) {

			ppft.litter_sap += anpp;
			harvest_wood(indiv, 1.0, indiv.pft.harv_eff, indiv.pft.res_outtake, 0, false); // frac_cut=1,
																							// harv_eff=pft.harv_eff,
																							// res_outtake_twig=pft.res_outtake,
																							// res_outtake_coarse_root=0
			indiv.vegetation.remove(indiv);
			killed = true;
		}

		patch.age = 0; // important for results
		patch.managed = true;
	}

	// / Harvest function used for managed forest and for clearing natural
	// vegetation at land use change
	/**
	 * A fraction of trees is cut down (frac_cut) A fraction of wood is harvested
	 * (pft.harv_eff) and returned as acflux_harvest A fraction of harvested wood
	 * (pft.harvest_slow_frac) is returned as harvested_products_slow The rest,
	 * including leaves and roots, is returned as litter. Called from
	 * landcover_dynamics() first day of the year if any natural vegetation is
	 * transferred to another land use.
	 * 
	 * This function copies variables from an individual and it's associated
	 * patchpft and patch to a Harvest_CN struct, which is then passed on to the
	 * main harvest_crop function. After the execution of the main harvest_crop
	 * function, the output variables are copied back to the individual and patchpft
	 * and the patch-level fluxes are updated.
	 *
	 * INPUT PARAMETER \param frac_cut fraction of trees cut \param harv_eff harvest
	 * efficiency \param res_outtake_twig removed twig fraction \param
	 * res_outtake_coarse_root removed course root fraction \param lc_change whether
	 * to save harvest in gridcell-level luc variable INPUT/OUTPUT PARAMETERS \param
	 * indiv reference to an Individual containing the following indiv-specific
	 * public members: - cmass_leaf leaf C biomass (kgC/m2) - cmass_root fine root C
	 * biomass (kgC/m2) - cmass_sap sapwood C biomass (kgC/m2) - cmass_heart
	 * heartwood C biomass (kgC/m2) - cmass_debt C "debt" (retrospective storage)
	 * (kgC/m2) - nmass_leaf leaf nitrogen biomass (kgN/m2) - nmass_root fine root
	 * nitrogen biomass (kgN/m2) - nmass_sap sapwood nitrogen biomass (kgC/m2) -
	 * nmass_heart heartwood nitrogen biomass (kgC/m2) - nstore_labile labile
	 * nitrogen storage (kgC/m2) - nstore_longterm longterm nitrogen storage
	 * (kgC/m2) OUTPUT PARAMETERS \param indiv reference to an Individual containing
	 * the following patchpft-specific public members: - litter_leaf new leaf C
	 * litter (kgC/m2) - litter_root new root C litter (kgC/m2) - litter_sap new
	 * sapwood C litter (kgC/m2) - litter_heart new heartwood C litter (kgC/m2) -
	 * nmass_litter_leaf new leaf nitrogen litter (kgN/m2) - nmass_litter_root new
	 * root nitrogen litter (kgN/m2) - nmass_litter_sap new sapwood nitrogen litter
	 * (kgN/m2) - nmass_litter_heart new heartwood nitrogen litter (kgN/m2) ,and the
	 * following patch-level public members: - acflux_harvest harvest flux to
	 * atmosphere (kgC/m2) - harvested_products_slow harvest products to slow pool
	 * (kgC/m2) - anflux_harvest harvest nitrogen flux out of system (kgC/m2) -
	 * harvested_products_slow_nmass harvest nitrogen products to slow pool (kgC/m2)
	 */
	void harvest_wood(Individual indiv, double frac_cut, double harv_eff, double res_outtake_twig,
			double res_outtake_coarse_root, boolean lc_change) {

		Harvest_CN indiv_cp = new Harvest_CN();

		indiv_cp.copy_from_indiv(indiv);

		harvest_wood(indiv_cp, indiv.pft, indiv.alive, frac_cut, harv_eff, res_outtake_twig, res_outtake_coarse_root);

		indiv_cp.copy_to_indiv(indiv, false, lc_change);

		if (lc_change) {
			Stand stand = indiv.vegetation.patch.stand;
			stand.getGridcell().acflux_landuse_change += stand.get_gridcell_fraction() * indiv_cp.acflux_harvest
					/ stand.npatch();
			stand.getGridcell().acflux_landuse_change_lc[stand.origin.ordinal()] += stand.get_gridcell_fraction()
					* indiv_cp.acflux_harvest / stand.npatch();
			stand.getGridcell().anflux_landuse_change += stand.get_gridcell_fraction() * indiv_cp.anflux_harvest
					/ stand.npatch();
			stand.getGridcell().anflux_landuse_change_lc[stand.origin.ordinal()] += stand.get_gridcell_fraction()
					* indiv_cp.anflux_harvest / stand.npatch();
		}
	}

	// / Harvest function used for managed forest and for clearing natural
	// vegetation at land use change
	/**
	 * A fraction of trees is cut down (frac_cut) A fraction of wood is harvested
	 * (pft.harv_eff) and returned as acflux_harvest A fraction of harvested wood
	 * (pft.harvest_slow_frac) is returned as harvested_products_slow The rest,
	 * including leaves and roots, is returned as litter. Called from
	 * landcover_dynamics() first day of the year if any natural vegetation is
	 * transferred to another land use. INPUT PARAMETER \param frac_cut fraction of
	 * trees cut \param harv_eff harvest efficiency \param res_outtake_twig removed
	 * twig fraction \param res_outtake_coarse_root removed course root fraction
	 * INPUT/OUTPUT PARAMETERS \param Harvest_CN& i struct containing the following
	 * indiv-specific public members: - cmass_leaf leaf C biomass (kgC/m2) -
	 * cmass_root fine root C biomass (kgC/m2) - cmass_sap sapwood C biomass
	 * (kgC/m2) - cmass_heart heartwood C biomass (kgC/m2) - cmass_debt C "debt"
	 * (retrospective storage) (kgC/m2) - nmass_leaf leaf nitrogen biomass (kgN/m2)
	 * - nmass_root fine root nitrogen biomass (kgN/m2) - nmass_sap sapwood nitrogen
	 * biomass (kgC/m2) - nmass_heart heartwood nitrogen biomass (kgC/m2) -
	 * nstore_labile labile nitrogen storage (kgC/m2) - nstore_longterm longterm
	 * nitrogen storage (kgC/m2) OUTPUT PARAMETERS \param Harvest_CN& i struct
	 * containing the following patchpft-specific public members: - litter_leaf new
	 * leaf C litter (kgC/m2) - litter_root new root C litter (kgC/m2) - litter_sap
	 * new sapwood C litter (kgC/m2) - litter_heart new heartwood C litter (kgC/m2)
	 * - nmass_litter_leaf new leaf nitrogen litter (kgN/m2) - nmass_litter_root new
	 * root nitrogen litter (kgN/m2) - nmass_litter_sap new sapwood nitrogen litter
	 * (kgN/m2) - nmass_litter_heart new heartwood nitrogen litter (kgN/m2) ,and the
	 * following patch-level public members: - acflux_harvest harvest flux to
	 * atmosphere (kgC/m2) - harvested_products_slow harvest products to slow pool
	 * (kgC/m2) - anflux_harvest harvest nitrogen flux out of system (kgC/m2) -
	 * harvested_products_slow_nmass harvest nitrogen products to slow pool (kgC/m2)
	 */
	void harvest_wood(Harvest_CN i, PFT pft, boolean alive, double frac_cut, double harv_eff, double res_outtake_twig,
			double res_outtake_coarse_root) {
		// TODO: Make configurable
		boolean ifslowharvestpool = true;

		double harvest = 0.0;
		double residue_outtake = 0.0;
		double stem_frac = 0.65; // Temporary values, should be pft-specific
		double twig_frac = 0.13;
		// double coarse_root_frac = 0.22;
		double coarse_root_frac = 1.0 - stem_frac - twig_frac;
		double adhering_leaf_frac = 0.75;

		// only harvest trees
		if (pft.lifeform == LifeForm.GRASS)
			return;

		// all root carbon and nitrogen goes to litter
		if (alive) {

			i.litter_root += i.cmass_root * frac_cut;
			i.cmass_root *= (1.0 - frac_cut);
		}

		i.nmass_litter_root += i.nmass_root * frac_cut;
		i.nmass_litter_root += (i.nstore_labile + i.nstore_longterm) * frac_cut;
		i.nmass_root *= (1.0 - frac_cut);
		i.nstore_labile *= (1.0 - frac_cut);
		i.nstore_longterm *= (1.0 - frac_cut);

		if (alive) {

			// Carbon:

			if (i.cmass_debt <= i.cmass_sap + i.cmass_heart) {

				// harvested stem wood
				harvest += harv_eff * stem_frac * (i.cmass_sap + i.cmass_heart - i.cmass_debt) * frac_cut;

				// harvested products not consumed (oxidised) this year put into
				// harvested_products_slow
				if (ifslowharvestpool) {
					i.harvested_products_slow += harvest * pft.harvest_slow_frac;
					harvest = harvest * (1.0 - pft.harvest_slow_frac);
				}

				// harvested products consumed (oxidised) this year put into
				// acflux_harvest
				i.acflux_harvest += harvest;

				// removed leaves adhering to twigs
				residue_outtake += res_outtake_twig * adhering_leaf_frac * i.cmass_leaf * frac_cut;

				// removed twigs
				residue_outtake += res_outtake_twig * twig_frac * (i.cmass_sap + i.cmass_heart - i.cmass_debt)
						* frac_cut;

				// removed coarse roots
				residue_outtake += res_outtake_coarse_root * coarse_root_frac
						* (i.cmass_sap + i.cmass_heart - i.cmass_debt) * frac_cut;

				// removed residues are oxidised
				i.acflux_harvest += residue_outtake;

				// not removed residues are put into litter
				i.litter_leaf += i.cmass_leaf * (1.0 - res_outtake_twig * adhering_leaf_frac) * frac_cut;

				double to_partition_sap = 0.0;
				double to_partition_heart = 0.0;

				if (i.cmass_heart >= i.cmass_debt) {
					to_partition_sap = i.cmass_sap;
					to_partition_heart = i.cmass_heart - i.cmass_debt;
				} else {
					to_partition_sap = i.cmass_sap + i.cmass_heart - i.cmass_debt;
					// dprintf("ATTENTION: pft %s: cmass_debt > cmass_heart; difference=%f\n",
					// (char*)pft.name, i.cmass_debt-i.cmass_heart);
				}
				i.litter_sap += to_partition_sap * (1.0 - res_outtake_twig * twig_frac
						- res_outtake_coarse_root * coarse_root_frac - harv_eff * stem_frac) * frac_cut;
				i.litter_heart += to_partition_heart * (1.0 - res_outtake_twig * twig_frac
						- res_outtake_coarse_root * coarse_root_frac - harv_eff * stem_frac) * frac_cut;
				// debt larger than existing wood biomass
			} else {
				double debt_excess = i.cmass_debt - (i.cmass_sap + i.cmass_heart);
				// dprintf("ATTENTION: cmass_debt > i.cmass_sap + i.cmass_heart;
				// debt_excess=%f\n",
				// debt_excess);
				// i.debt_excess += debt_excess * frac_cut;
			}

			// unharvested trees:
			i.cmass_leaf *= (1.0 - frac_cut);
			i.cmass_sap *= (1.0 - frac_cut);
			i.cmass_heart *= (1.0 - frac_cut);
			i.cmass_debt *= (1.0 - frac_cut);

			// Nitrogen:

			harvest = 0.0;

			// harvested products
			harvest += harv_eff * stem_frac * (i.nmass_sap + i.nmass_heart) * frac_cut;

			// harvested products not consumed this year put into
			// harvested_products_slow_nmass
			if (ifslowharvestpool) {
				i.harvested_products_slow_nmass += harvest * pft.harvest_slow_frac;
				harvest = harvest * (1.0 - pft.harvest_slow_frac);
			}

			// harvested products consumed this year put into anflux_harvest
			i.anflux_harvest += harvest;

			residue_outtake = 0.0;

			// removed leaves adhering to twigs
			residue_outtake += res_outtake_twig * adhering_leaf_frac * i.nmass_leaf * frac_cut;

			// removed twigs
			residue_outtake += res_outtake_twig * twig_frac * (i.nmass_sap + i.nmass_heart) * frac_cut;

			// removed coarse roots
			residue_outtake += res_outtake_coarse_root * coarse_root_frac * (i.nmass_sap + i.nmass_heart) * frac_cut;

			// removed residues are oxidised
			i.anflux_harvest += residue_outtake;

			// not removed residues are put into litter
			i.nmass_litter_leaf += i.nmass_leaf * (1.0 - res_outtake_twig * adhering_leaf_frac) * frac_cut;
			i.nmass_litter_sap += i.nmass_sap * (1.0 - res_outtake_twig * twig_frac
					- res_outtake_coarse_root * coarse_root_frac - harv_eff * stem_frac) * frac_cut;
			i.nmass_litter_heart += i.nmass_heart * (1.0 - res_outtake_twig * twig_frac
					- res_outtake_coarse_root * coarse_root_frac - harv_eff * stem_frac) * frac_cut;

			// unharvested trees:
			i.nmass_leaf *= (1.0 - frac_cut);
			i.nmass_sap *= (1.0 - frac_cut);
			i.nmass_heart *= (1.0 - frac_cut);
		}
	}

	void harvest_forest(Individual indiv, PFT pft, boolean alive, double anpp, boolean killed) {
		Patch patch = indiv.vegetation.patch;
		PatchPFT ppft = patch.pft.get(indiv.pft.id);

		int year = _configuration.getSchedule().year();

		double minbon = 2.351; // The minimum average "bonitet" for a county in
								// Sweden
		double maxbon = 11.311; // The maximum average "bonitet" for a county in
								// Sweden
		double bonitet = 10.0; // Temporary static value

		int age_class = 0;
		double man_strength = 0.0;
		// if(year > nyear_spinup && indiv.pft.lifeform == TREE)
		if (indiv.pft.lifeform == LifeForm.TREE)
			man_strength = forest_management(patch, true, age_class);
		boolean management_done = false;
		// Will tell the program to skip establishment and mortality if
		// management has been
		// performed on this patch, Management add, FL 081127 (not implemented
		// in this code yet ML, needs to be at patch-level)

		if (pft.lifeform == LifeForm.TREE && man_strength > 0.00) {

			if (man_strength == 1.00) {
				clearcut(indiv, pft, alive, anpp, killed);
				// planting(stand,patch,pftlist);
			} else {

				double diam = Math.pow(indiv.height / indiv.pft.k_allom2, 1.0 / indiv.pft.k_allom3);
				// double
				// diam_limit=0.13+0.07*(stand.bonitet-minbon)/(maxbon-minbon);
				// //Harvest of trees > 13-20 cm
				double diam_limit = 0.13 + 0.07 * (bonitet - minbon) / (maxbon - minbon); // Harvest
																							// of
																							// trees
																							// >
																							// 13-20
																							// cm
				double diam_max = diam_limit * 2.0;

				if (diam > diam_limit) {
					if (diam > diam_max)
						man_strength = 0.9;
					harvest_wood(indiv, man_strength, indiv.pft.harv_eff, indiv.pft.res_outtake, 0, false); // frac_cut=man_strength,
					// harv_eff=pft.harv_eff,
					// res_outtake_twig=pft.res_outtake,
					// res_outtake_coarse_root=0
					indiv.densindiv *= (1.0 - man_strength);
				}
			}
			management_done = true;
			patch.managed = true;
		}
	}

	// / Yearly function for harvest of all land covers that have yearly
	// allocation, turnover and gridcell.expand_to_new_stand[lc] = false.
	/**
	 * Should only be called from growth(). // Harvest functions are preceded by
	 * rescaling of living C. // Only affects natural stands if
	 * ifexpand_to_new_stand or gridcell.expand_to_new_stand[NATURAL] is false.
	 */
	boolean harvest_year(Individual indiv) {

		Stand stand = indiv.vegetation.patch.stand;
		Gridcell gridcell = stand.getGridcell();
		boolean killed = false;

		// Reduce individual's C and N mass in stands that have increased in
		// area this year:
		if (gridcell.LC_updated) {
			if (!indiv.has_daily_turnover())
				scale_indiv(indiv, false);
		}

		if (stand.landcover == LandcoverType.CROP) {
			if (!indiv.has_daily_turnover())
				growth_daily.harvest_crop(indiv, indiv.pft, indiv.alive, indiv.cropindiv.isintercropgrass, false);
		} else if (stand.landcover == LandcoverType.PASTURE) {
			harvest_pasture(indiv, indiv.pft, indiv.alive);
		} else if (stand.landcover == LandcoverType.FOREST)
			harvest_forest(indiv, indiv.pft, indiv.alive, indiv.anpp, killed);

		return killed;
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// ALLOMETRY
	// Should be called to update allometry, FPC and FPC increment whenever
	// biomass values
	// for a vegetation individual change.

	boolean allometry(Individual indiv) {
		// DESCRIPTION
		// Calculates tree allometry (height and crown area) and fractional
		// projective
		// given carbon biomass in various compartments for an individual.

		// Returns true if the allometry is normal, otherwise false - guess2008

		// TREE ALLOMETRY
		// Trees aboveground allometry is modelled by a cylindrical stem
		// comprising an
		// inner cylinder of heartwood surrounded by a zone of sapwood of
		// constant radius,
		// and a crown (i.e. foliage) cylinder of known diameter. Sapwood and
		// heartwood are
		// assumed to have the same, constant, density (wooddens). Tree height
		// is related
		// to sapwood cross-sectional area by the relation:
		// (1) height = cmass_sap / (sapwood xs area)
		// Sapwood cross-sectional area is also assumed to be a constant
		// proportion of
		// total leaf area (following the "pipe model"; Shinozaki et al.
		// 1964a,b; Waring
		// et al 1982), i.e.
		// (2) (leaf area) = k_latosa * (sapwood xs area)
		// Leaf area is related to leaf biomass by specific leaf area:
		// (3) (leaf area) = sla * cmass_leaf
		// From (1), (2), (3),
		// (4) height = cmass_sap / wooddens / sla / cmass_leaf * k_latosa
		// Tree height is related to stem diameter by the relation (Huang et al
		// 1992)
		// [** = raised to the power of]:
		// (5) height = k_allom2 * diam ** k_allom3
		// Crown area may be derived from stem diameter by the relation (Zeide
		// 1993):
		// (6) crownarea = min ( k_allom1 * diam ** k_rp , crownarea_max )
		// Bole height (individual/cohort mode only; currently set to 0):
		// (7) boleht = 0

		// FOLIAR PROJECTIVE COVER (FPC)
		// The same formulation for FPC (Eqn 8 below) is now applied in all
		// vegetation
		// modes (Ben Smith 2002-07-23). FPC is equivalent to fractional
		// patch/grid cell
		// coverage for the purposes of canopy exchange calculations and, in
		// population
		// mode, vegetation dynamics calculations.
		//
		// FPC on the modelled area (stand, patch, "grid-cell") basis is related
		// to mean
		// individual leaf area index (LAI) by the Lambert-Beer law (Monsi &
		// Saeki 1953,
		// Prentice et al 1993) based on the assumption that success of a PFT
		// population
		// in competition for space will be proportional to competitive ability
		// for light
		// in the vertical profile of the forest canopy:
		// (8) fpc = crownarea * densindiv * ( 1.0 - exp ( -0.5 * lai_ind ) )
		// where
		// (9) lai_ind = cmass_leaf/densindiv * sla / crownarea
		//
		// For grasses,
		// (10) fpc = ( 1.0 - exp ( -0.5 * lai_ind ) )
		// (11) lai_ind = cmass_leaf * sla

		double diam; // stem diameter (m)
		double fpc_new; // updated FPC

		// guess2008 - max tree height allowed (metre).
		double HEIGHT_MAX = 150.0;

		if (indiv.pft.lifeform == LifeForm.TREE) {

			// TREES

			// Height (Eqn 4)

			// guess2008 - new allometry check
			if (!Utils.negligible(indiv.cmass_leaf)) {

				indiv.height = indiv.cmass_sap / indiv.cmass_leaf / indiv.pft.sla * indiv.pft.k_latosa
						/ indiv.pft.wooddens;

				// Stem diameter (Eqn 5)
				diam = Math.pow(indiv.height / indiv.pft.k_allom2, 1.0 / indiv.pft.k_allom3);

				// Stem volume
				double vol = indiv.height * Math.PI * diam * diam * 0.25;

				if (indiv.age > 0
						&& (indiv.cmass_heart + indiv.cmass_sap) / indiv.densindiv / vol < indiv.pft.wooddens * 0.9) {
					return false;
				}
			} else {
				indiv.height = 0.0;
				diam = 0.0;
				return false;
			}

			// guess2008 - extra height check
			if (indiv.height > HEIGHT_MAX) {
				indiv.height = 0.0;
				diam = 0.0;
				return false;
			}

			// Crown area (Eqn 6)
			indiv.crownarea = Math.min(indiv.pft.k_allom1 * Math.pow(diam, indiv.pft.k_rp), indiv.pft.crownarea_max);

			if (!Utils.negligible(indiv.crownarea)) {

				// Individual LAI (Eqn 9)
				indiv.lai_indiv = indiv.cmass_leaf / indiv.densindiv * indiv.pft.sla / indiv.crownarea;

				// FPC (Eqn 8)

				fpc_new = indiv.crownarea * indiv.densindiv * (1.0 - growth_daily.lambertbeer(indiv.lai_indiv));

				// Increment deltafpc
				indiv.deltafpc += fpc_new - indiv.fpc;
				indiv.fpc = fpc_new;
			} else {
				indiv.lai_indiv = 0.0;
				indiv.fpc = 0.0;
			}

			// Bole height (Eqn 7)
			indiv.boleht = 0.0;

			// Stand-level LAI
			indiv.lai = indiv.cmass_leaf * indiv.pft.sla;
		} else if (indiv.pft.lifeform == LifeForm.GRASS) {

			// GRASSES

			if (indiv.pft.landcover != LandcoverType.CROP) {

				// guess2008 - bugfix - added if
				if (!Utils.negligible(indiv.cmass_leaf)) {

					// Grass "individual" LAI (Eqn 11)
					indiv.lai_indiv = indiv.cmass_leaf * indiv.pft.sla;

					// FPC (Eqn 10)
					indiv.fpc = 1.0 - growth_daily.lambertbeer(indiv.lai_indiv);

					// Stand-level LAI
					indiv.lai = indiv.lai_indiv;
				} else {
					return false;
				}
			} else {

				CropPhen ppftcrop = indiv.vegetation.patch.pft.get(indiv.pft.id).get_cropphen();

				// crop grass compatible with natural grass
				if (indiv.pft.phenology == Phenology.ANY) {

					indiv.lai_indiv = indiv.cmass_leaf * indiv.pft.sla;

					// For intercrop grass, use LAI of parent grass in its own
					// stand.
					if (indiv.cropindiv.isintercropgrass) {

						boolean done = false;

						Gridcell gridcell = indiv.vegetation.patch.stand.getGridcell();

						// First look in PASTURE.
						if (gridcell.landcoverfrac[LandcoverType.PASTURE.ordinal()] > 0.0) {

							for (int i = 0; i < gridcell.stands.size() && !done; i++) {

								Stand stand = gridcell.stands.get(i);
								if (stand.landcover == LandcoverType.PASTURE) {
									for (int j = 0; j < stand.npatch() && !done; j++) {
										Patch patch = stand.getPatches().get(j);
										Vegetation vegetation = patch.vegetation;
										for (int k = 0; k < vegetation.size() && !done; k++) {
											Individual grass_indiv = vegetation.get(k);

											if (grass_indiv.pft.name.startsWith(indiv.pft.name.substring(0, 4))) { // NB:
																													// this
																													// works
																													// with
																													// current
																													// pft
																													// names.
																													// CC3G_ic
																													// and
																													// C3G_pasture
												indiv.lai_indiv = grass_indiv.lai_indiv;
												done = true;
											}
										}
									}
								}
							}
						}
						// If PASTURE landcover not used, look for crop stand
						// with pasture grass.
						else {

							double highest_grass_lai = 0.0;
							double grass_cmass_leaf_sum = 0.0;

							// Get sum of intercrop grass cmass_leaf in this
							// patch
							Vegetation vegetation_self = indiv.vegetation;

							for (int k = 0; k < vegetation_self.size(); k++) {

								Individual indiv_veg = vegetation_self.get(k);

								if (indiv_veg.cropindiv.isintercropgrass)
									grass_cmass_leaf_sum += indiv_veg.cropindiv.cmass_leaf_max;
							}

							// Find highest lai in crop grass stands
							for (int i = 0; i < gridcell.stands.size(); i++) {

								Stand stand = gridcell.stands.get(i);

								if (stand.landcover == LandcoverType.CROP && !stand.is_true_crop_stand()) {

									for (int j = 0; j < stand.npatch() && !done; j++) {

										Patch patch = stand.getPatches().get(j);
										Vegetation vegetation = patch.vegetation;

										for (int k = 0; k < vegetation.size() && !done; k++) {

											Individual grass_indiv = vegetation.get(k);

											if (grass_indiv.pft.phenology == Phenology.ANY) {

												if (grass_indiv.lai_indiv > highest_grass_lai)
													highest_grass_lai = grass_indiv.lai_indiv;
											}
										}
									}
								}
							}

							if (grass_cmass_leaf_sum > 0.0)
								indiv.lai_indiv = indiv.cropindiv.cmass_leaf_max / grass_cmass_leaf_sum
										* highest_grass_lai;
							else
								indiv.lai_indiv = highest_grass_lai;

							if (highest_grass_lai > 0)
								done = true;
						}

						// If no grass stand found in either cropland or
						// pasture, use laimax value.
						if (!done) {

							double highest_grass_lai = 0.0;
							double grass_cmass_leaf_sum = 0.0;

							// Get sum of intercrop grass cmass_leaf and highest
							// default laimax in this patch
							Vegetation vegetation_self = indiv.vegetation;

							for (int k = 0; k < vegetation_self.size(); k++) {

								Individual indiv_veg = vegetation_self.get(k);

								if (indiv_veg.cropindiv.isintercropgrass) {

									grass_cmass_leaf_sum += indiv_veg.cropindiv.cmass_leaf_max;

									if (indiv_veg.pft.laimax > highest_grass_lai)
										highest_grass_lai = indiv_veg.pft.laimax;
								}
							}

							if (grass_cmass_leaf_sum > 0.0)
								indiv.lai_indiv = indiv.cropindiv.cmass_leaf_max / grass_cmass_leaf_sum
										* highest_grass_lai;
							else
								indiv.lai_indiv = highest_grass_lai;
						}
					}
					// if(indiv.lai_indiv < 0.0)
					// fail("lai_indiv negative for %s in stand %d year %d in growth: %f\n",
					// (char*)indiv.pft.name, indiv.vegetation.patch.stand.id,
					// date.year, indiv.lai_indiv);

					// FPC (Eqn 10)
					indiv.fpc = 1.0 - growth_daily.lambertbeer(indiv.lai_indiv);

					// Stand-level LAI
					indiv.lai = indiv.lai_indiv;

				} else { // cropgreen
					if (!Utils.negligible(indiv.cropindiv.cmass_leaf_max)) {

						// Grass "individual" LAI (Eqn 11)
						indiv.lai_indiv = indiv.cropindiv.cmass_leaf_max * indiv.pft.sla;

						// FPC (Eqn 10)
						// indiv.fpc = 1.0 - lambertbeer(indiv.lai_indiv);
						indiv.fpc = 1.0;

						// Stand-level LAI
						indiv.lai = indiv.lai_indiv;

					}
				}
			}
		}

		// guess2008 - new return value (was void)
		return true;
	}
}
