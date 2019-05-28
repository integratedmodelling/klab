package org.integratedmodelling.ecology.biomass.lpjguess;

///////////////////////////////////////////////////////////////////////////////////////
//STANDPFT
//State variables common to all individuals of a particular PFT in a stand. Used in
//individual and cohort modes only.

public class StandPFT {

    // MEMBER VARIABLES

    public int id;
    public PFT pFT;

    // net C allocated to reproduction for this PFT in all patches of this stand
    // this year (kgC/m2)
    public double cmass_repr;

    // maximum value of anetpsff (potential annual net assimilation at forest
    // floor) for this PFT in this stand so far in the simulation (kgC/m2/year)
    public double anetps_ff_max;

    // annual degree day sum above threshold damaging temperature (used in
    // calculation of heat stess mortality; Sitch et al 2000, Eqn 55)
    public double addtw;

    // term in calculation of potential canopy conductance (mm/s)
    public double gterm;

    // true if value of gterm available for this PFT today, otherwise false
    public boolean have_gterm;

    // Variables used only by input/output module

    // sum/mean across patches for carbon biomass (kgC/m2)
    public double cmass_total;

    // sum/mean across patches for annual NPP (kgC/m2/year)
    public double anpp_total;

    // sum/mean across patches for 'grid-cell' LAI
    public double lai_total;

    // sum/mean across patches for density of (true) individuals (indiv/m2)
    // (meaningful in cohort/individual mode only)
    public double densindiv_total;

    // stem density by age class (cohort/individual mode only; used by function
    // outannual)
    // FV FIXME this is ridiculous and kills everything. Unsure we're ever going to use cohort
    // mode anyway, so for now just comment out.
    public double[] densindiv_ageclass = null; // new double[IConfiguration.OUTPUT_MAXAGECLASS];

    // Variables used by "fast" canopy exchange code (Ben Smith 2002-07)

    // non-FPAR-weighted value for canopy conductance component associated with
    // photosynthesis for PFT under non-water-stress conditions (mm/s)
    public double  gpterm;
    // non-FPAR-weighted leaf-level net photosynthesis value for PFT under non-
    // water-stress conditions (kgC/m2/day)
    public double  assim_term;
    // whether gpterm and assim_term values are valid today
    public boolean have_phot;

    // / Whether this PFT is allowed to grow in this stand
    boolean active;

    // / Whether this PFT is irrigated in this stand
    public boolean irrigated;

    int sdate_force;
    int hdate_force;

    // Foliar Protective Cover (FPC) sum for this PFT as average for stand (used
    // by some versions of
    // guessio.cpp)
    public double fpc_total;

    public StandPFT(int i, PFT p) {
        this.id = i;
        this.pFT = p;

        // Constructor: initialises various data members

        anetps_ff_max = 0.0;
        addtw = 0.0;
        cmass_total = 0.0;
    }
}
