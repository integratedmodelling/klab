package org.integratedmodelling.ecology.biomass.lpjguess.processes.base;

import org.integratedmodelling.ecology.biomass.lpjguess.Individual;
import org.integratedmodelling.ecology.biomass.lpjguess.common.Utils;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.LifeForm;

public abstract class VegetationProcess extends EcologicalProcess {

	protected VegetationProcess(IConfiguration configuration) {
		super(configuration);
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// ALLOCATION
	// Function allocation is an internal function (do not call directly from
	// framework);
	// function allocation_init may be called to distribute initial biomass
	// among tissues
	// for a new individual.

	// File scope global variables: used by function f below (see function
	// allocation)

	protected static double k1;
	protected static double k2;
	protected static double k3;
	protected static double b;
	protected static double ltor_g;
	protected static double cmass_heart_g;
	protected static double cmass_leaf_g;

	protected double f(double cmass_leaf_inc) {

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

	protected class ARes {

		public double cmass_leaf_inc;
		public double cmass_root_inc;
		public double cmass_sap_inc;
		public double cmass_debt_inc;
		public double cmass_heart_inc;
		public double litter_leaf_inc;
		public double litter_root_inc;

		ARes(double cmass_leaf_inc, double cmass_root_inc, double cmass_sap_inc, double cmass_debt_inc,
				double cmass_heart_inc, double litter_leaf_inc, double litter_root_inc) {

			this.cmass_debt_inc = cmass_debt_inc;
			this.cmass_heart_inc = cmass_heart_inc;
			this.cmass_leaf_inc = cmass_leaf_inc;
			this.cmass_root_inc = cmass_root_inc;
			this.cmass_sap_inc = cmass_sap_inc;
			this.litter_leaf_inc = litter_leaf_inc;
			this.litter_root_inc = litter_root_inc;
		}

	}

	protected ARes allocation(double bminc, double cmass_leaf, double cmass_root, double cmass_sap, double cmass_debt,
			double cmass_heart, double ltor, double height, double sla, double wooddens, LifeForm lifeform,
			double k_latosa, double k_allom2, double k_allom3,
			// by ref
			double cmass_leaf_inc, double cmass_root_inc, double cmass_sap_inc, double cmass_debt_inc,
			double cmass_heart_inc, double litter_leaf_inc, double litter_root_inc) {

		// DESCRIPTION
		// Calculates changes in C compartment sizes (leaves, roots, sapwood,
		// heartwood)
		// and litter for a plant individual as a result of allocation of
		// biomass increment.
		// Assumed allometric relationships are given in function allometry
		// below.

		// INPUT PARAMETERS
		// bminc = biomass increment this time period on individual basis (kgC)
		// cmass_leaf = leaf C biomass for last time period on individual basis
		// (kgC)
		// cmass_root = root C biomass for last time period on individual basis
		// (kgC)
		// cmass_sap = sapwood C biomass for last time period on individual
		// basis (kgC)
		// cmass_heart = heartwood C biomass for last time period on individual
		// basis (kgC)
		// ltor = leaf to root mass ratio following allocation
		// height = individual height (m)
		// sla = specific leaf area (PFT-specific constant) (m2/kgC)
		// wooddens = wood density (PFT-specific constant) (kgC/m3)
		// lifeform = life form class (TREE or GRASS)
		// k_latosa = ratio of leaf area to sapwood cross-sectional area
		// (PFT-specific
		// constant)
		// k_allom2 = constant in allometry equations
		// k_allom3 = constant in allometry equations

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

		final int NSEG = 20; // number of segments (parameter in numerical
		// methods)
		final int JMAX = 40; // maximum number of iterations (in numerical
		// methods)
		final double XACC = 0.0001; // threshold x-axis precision of allocation
		// solution
		final double YACC = 1.0e-10; // threshold y-axis precision of allocation
		// solution
		final double PI = 3.14159265;
		final double CDEBT_MAXLOAN_DEFICIT = 0.8; // maximum loan as a fraction
		// of deficit
		final double CDEBT_MAXLOAN_MASS = 0.2; // maximum loan as a fraction of
		// (sapwood-cdebt)

		double cmass_leaf_inc_min;
		double cmass_root_inc_min;
		double x1;
		double x2;
		double dx;
		double xmid;
		double fx1;
		double fmid;
		double rtbis;
		double sign;
		int j;
		double cmass_deficit;
		double cmass_loan;

		litter_leaf_inc = 0.0;
		litter_root_inc = 0.0;
		cmass_root_inc = 0.0; // guess2008 - initialise

		if (ltor < 1.0e-10) {

			// No leaf production possible - put all biomass into roots
			// (Individual will die next time period)

			cmass_leaf_inc = 0.0;
			cmass_root_inc = bminc;

			if (lifeform == LifeForm.TREE) {
				cmass_sap_inc = -cmass_sap;
				cmass_heart_inc = -cmass_sap_inc;
			}

			return new ARes(cmass_leaf_inc, cmass_root_inc, cmass_sap_inc, cmass_debt_inc, cmass_heart_inc,
					litter_leaf_inc, litter_root_inc);
		}

		if (lifeform == LifeForm.TREE) {

			// TREE ALLOCATION

			cmass_heart_inc = 0.0;

			// Calculate minimum leaf increment to maintain current sapwood
			// biomass
			// Given Eqn (2)

			if (height > 0.0) {
				cmass_leaf_inc_min = k_latosa * cmass_sap / (wooddens * height * sla) - cmass_leaf;
			} else {
				cmass_leaf_inc_min = 0.0;
			}

			// Calculate minimum root increment to support minimum resulting
			// leaf biomass
			// Eqn (3)

			if (height > 0.0) {
				cmass_root_inc_min = k_latosa * cmass_sap / (wooddens * height * sla * ltor) - cmass_root;
			} else {
				cmass_root_inc_min = 0.0;
			}

			if (cmass_root_inc_min < 0.0) // some roots would have to be killed
			{

				cmass_leaf_inc_min = cmass_root * ltor - cmass_leaf;
				cmass_root_inc_min = 0.0;
			}

			// BLARP! C debt stuff
			if (getConfiguration().isIfcdebt()) {
				cmass_deficit = cmass_leaf_inc_min + cmass_root_inc_min - bminc;
				if (cmass_deficit > 0.0) {
					cmass_loan = Math.max(
							Math.min(cmass_deficit * CDEBT_MAXLOAN_DEFICIT, (cmass_sap - cmass_debt)
									* CDEBT_MAXLOAN_MASS), 0.0);
					bminc += cmass_loan;
					cmass_debt_inc = cmass_loan;
				} else {
					cmass_debt_inc = 0.0;
				}
			} else {
				cmass_debt_inc = 0.0;
			}

			if (cmass_root_inc_min >= 0.0 && cmass_leaf_inc_min >= 0.0
					&& cmass_root_inc_min + cmass_leaf_inc_min <= bminc || bminc <= 0.0) {

				// Normal allocation (positive increment to all living C
				// compartments)
				// NOTE: includes allocation of zero or negative NPP, c.f. LPJF

				// Calculation of leaf mass increment (lminc_ind) satisfying Eqn
				// (13)
				// using bisection method (Press et al 1986)

				// Set values for global variables for reuse by function f

				k1 = Math.pow(k_allom2, 2.0 / k_allom3) * 4.0 / PI / wooddens;
				k2 = 1.0 + 2 / k_allom3;
				k3 = k_latosa / wooddens / sla;
				b = cmass_sap + bminc - cmass_leaf / ltor + cmass_root;
				ltor_g = ltor;
				cmass_leaf_g = cmass_leaf;
				cmass_heart_g = cmass_heart;

				x1 = 0.0;
				x2 = (bminc - (cmass_leaf / ltor - cmass_root)) / (1.0 + 1.0 / ltor);
				dx = (x2 - x1) / NSEG;

				if (cmass_leaf < 1.0e-10) // to avoid division by zero
				{
					x1 += dx;
				}

				// Evaluate f(x1), i.e. Eqn (13) at cmass_leaf_inc = x1

				fx1 = f(x1);

				// Find approximate location of leftmost root on the interval
				// (x1,x2). Subdivide (x1,x2) into nseg equal segments seeking
				// change in sign of f(xmid) relative to f(x1).

				fmid = f(x1);

				xmid = x1;

				while (fmid * fx1 > 0.0 && xmid < x2) {

					xmid += dx;
					fmid = f(xmid);
				}

				x1 = xmid - dx;
				x2 = xmid;

				// Apply bisection to find root on new interval (x1,x2)

				if (f(x1) >= 0.0) {
					sign = -1.0;
				} else {
					sign = 1.0;
				}

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

					fmid = f(xmid);

					if (fmid * sign <= 0.0) {
						rtbis = xmid;
					}
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

					if (lifeform == LifeForm.TREE) {
						cmass_sap_inc = -cmass_sap;
						cmass_heart_inc = -cmass_sap_inc;
					}

					return new ARes(cmass_leaf_inc, cmass_root_inc, cmass_sap_inc, cmass_debt_inc, cmass_heart_inc,
							litter_leaf_inc, litter_root_inc);
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

					cmass_root_inc = bminc - cmass_leaf_inc; // Eqn
					// (1)

					// Add killed roots (if any) to litter

					// guess2008 - back to LPJF method in this case
					// if (cmass_root_inc<0.0) litter_root_inc=-cmass_root_inc;
					if (cmass_root_inc < 0.0) {
						cmass_leaf_inc = bminc;
						cmass_root_inc = (cmass_leaf_inc + cmass_leaf) / ltor - cmass_root; // Eqn
						// (3)
						litter_root_inc = -cmass_root_inc;
					}

				} else {

					// Negative or zero allocation to leaves
					// Eqns (1), (3)

					cmass_root_inc = bminc;
					cmass_leaf_inc = (cmass_root + cmass_root_inc) * ltor - cmass_leaf;

					// Add killed leaves to litter

					litter_leaf_inc = -cmass_leaf_inc;

				}

				// Calculate increase in sapwood mass (which must be negative)
				// Eqn (2)

				cmass_sap_inc = (cmass_leaf_inc + cmass_leaf) * wooddens * height * sla / k_latosa - cmass_sap;

				// Convert killed sapwood to heartwood

				cmass_heart_inc = -cmass_sap_inc;
			}
		} else if (lifeform == LifeForm.GRASS || lifeform == LifeForm.CROP) {

			// GRASS ALLOCATION
			// Allocation attempts to distribute biomass increment (bminc) among
			// leaf
			// and root compartments, i.e.
			// (14) bminc = cmass_leaf_inc + cmass_root_inc
			// while satisfying Eqn(3)

			cmass_leaf_inc = (bminc - cmass_leaf / ltor + cmass_root) / (1.0 + 1.0 / ltor);
			cmass_root_inc = bminc - cmass_leaf_inc;

			if (cmass_leaf_inc < 0.0) {

				// Negative allocation to leaves

				cmass_root_inc = bminc;
				cmass_leaf_inc = (cmass_root + cmass_root_inc) * ltor - cmass_leaf; // Eqn
				// (3)

				// Add killed leaves to litter

				cmass_leaf_inc = (cmass_root + cmass_root_inc) * ltor - cmass_leaf; // Eqn
				// (3)

				// Add killed leaves to litter

				// guess2008 - bugfix
				// litter_leaf_inc=-cmass_leaf_inc;
				litter_leaf_inc = Math.min(-cmass_leaf_inc, cmass_leaf);
			} else if (cmass_root_inc < 0.0) {

				// Negative allocation to roots

				cmass_leaf_inc = bminc;
				cmass_root_inc = (cmass_leaf + bminc) / ltor - cmass_root;

				// Add killed roots to litter

				// guess2008 - bugfix
				// litter_root_inc=-cmass_root_inc;
				litter_root_inc = Math.min(-cmass_root_inc, cmass_root);

			}
		}

		return new ARes(cmass_leaf_inc, cmass_root_inc, cmass_sap_inc, cmass_debt_inc, cmass_heart_inc,
				litter_leaf_inc, litter_root_inc);
	}

	/**
	 * Allocates initial biomass among tissues for a new individual (tree or
	 * grass), assuming standard LPJ allometry (see functions allocation,
	 * allometry).
	 *
	 * Note: indiv.densindiv (density of individuals across patch or modelled
	 * area) should be set to a meaningful value before this function is called
	 *
	 * @param bminit
	 *            initial total biomass (kgC)
	 * @param ltor
	 *            initial leaf:root biomass ratio
	 * @param indiv
	 */
	protected void allocation_init(double bminit, double ltor, Individual indiv) {

		double dval = 0;
		double cmass_leaf_ind = 0;
		double cmass_root_ind = 0;
		double cmass_sap_ind = 0;

		ARes res = allocation(bminit, 0.0, 0.0, 0.0, 0.0, 0.0, ltor, 0.0, indiv.pft.sla, indiv.pft.wooddens,
				indiv.pft.lifeform, indiv.pft.k_latosa, indiv.pft.k_allom2, indiv.pft.k_allom3, cmass_leaf_ind,
				cmass_root_ind, cmass_sap_ind, dval, dval, dval, dval);

		// cmass_debt_inc = res.cmass_debt_inc;
		// cmass_heart_inc = res.cmass_heart_inc;
		cmass_leaf_ind = res.cmass_leaf_inc;
		cmass_root_ind = res.cmass_root_inc;
		cmass_sap_ind = res.cmass_sap_inc;
		// litter_leaf_inc = res.litter_leaf_inc;
		// litter_root_inc = res.litter_root_inc;

		indiv.cmass_leaf = cmass_leaf_ind * indiv.densindiv;
		indiv.cmass_root = cmass_root_ind * indiv.densindiv;

		if (indiv.pft.lifeform == LifeForm.TREE) {
			indiv.cmass_sap = cmass_sap_ind * indiv.densindiv;
		}
	}

	protected boolean allometry(Individual indiv) {

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
		final double HEIGHT_MAX = 150.0;

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
				double vol = indiv.height * 3.1415927 * diam * diam * 0.25;
				if (indiv.age != 0
						&& ((indiv.cmass_heart + indiv.cmass_sap) / indiv.densindiv / vol) < (indiv.pft.wooddens * 0.9)) {
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

				fpc_new = indiv.crownarea * indiv.densindiv
						* (1.0 - Math.exp(-_configuration.LAMBERTBEER_K * indiv.lai_indiv));

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
		} else if (indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP) {

			// GRASSES

			// guess2008 - bugfix - added if
			if (!Utils.negligible(indiv.cmass_leaf)) {

				// Grass "individual" LAI (Eqn 11)
				indiv.lai_indiv = indiv.cmass_leaf * indiv.pft.sla;

				// FPC (Eqn 10)
				indiv.fpc = 1.0 - Math.exp(-_configuration.LAMBERTBEER_K * indiv.lai_indiv);

				// Stand-level LAI
				indiv.lai = indiv.lai_indiv;
			} else {
				return false;
			}

		}

		// guess2008 - new return value (was void)
		return true;
	}

	public double fracmass_lpj(double fpc_low, double fpc_high, Individual indiv) {

		// DESCRIPTION
		// Calculates and returns new biomass as a fraction of old biomass given
		// an FPC
		// reduction from fpc_high to fpc_low, assuming LPJ allometry (see
		// function
		// allometry)

		// guess2008 - check
		if (fpc_high < fpc_low) {
			Logging.INSTANCE.error("fracmass_lpj: fpc_high < fpc_low", null);
		}

		if (indiv.pft.lifeform == LifeForm.TREE) {

			if (Utils.negligible(fpc_high)) {
				return 1.0;
			}

			// else
			return fpc_low / fpc_high;
		} else if (indiv.pft.lifeform == LifeForm.GRASS || indiv.pft.lifeform == LifeForm.CROP) // grass
		{
			if (fpc_high >= 1.0 || fpc_low >= 1.0 || Utils.negligible(indiv.cmass_leaf)) {
				return 1.0;
			}

			// else
			return 1.0 + 2.0 / indiv.cmass_leaf / indiv.pft.sla * (Math.log(1.0 - fpc_high) - Math.log(1.0 - fpc_low));
		} else {
			Logging.INSTANCE.error("fracmass_lpj: unknown lifeform", null);
			return 0;
		}
	}

}
