package org.integratedmodelling.ecology.biomass.lpjguess;

public class Soiltype {

    static double[][] data = {

            // 0 empirical parameter in percolation equation (k1) (mm/day)
            // 1 volumetric water holding capacity at field capacity minus
            // the volume of
            // water holding capacity at wilting point (Hmax), as fraction
            // of soil
            // layer depth
            // 2 thermal diffusivity (mm2/s) at wilting point (0% WHC)
            // 3 thermal diffusivity (mm2/s) at 15% WHC
            // 4 thermal diffusivity at field capacity (100% WHC)
            // Thermal diffusivities follow van Duin (1963),
            // Jury et al (1991), Fig 5.11.

            { 5.0, 0.110, 0.2, 0.800, 0.4 }, // 1
            { 4.0, 0.150, 0.2, 0.650, 0.4 }, // 2
            { 3.0, 0.120, 0.2, 0.500, 0.4 }, // 3
            { 4.5, 0.130, 0.2, 0.725, 0.4 }, // 4
            { 4.0, 0.115, 0.2, 0.650, 0.4 }, // 5
            { 3.5, 0.135, 0.2, 0.575, 0.4 }, // 6
            { 4.0, 0.127, 0.2, 0.650, 0.4 }, // 7
            { 9.0, 0.300, 0.1, 0.100, 0.1 }, // 8
            { 0.2, 0.100, 0.2, 0.500, 0.4 } // 9
    };

    // year at which to calculate equilibrium soil carbon
    static final int SOLVESOM_END = 400;

    // year at which to begin documenting means for calculation of equilibrium
    // soil carbon
    static final int SOLVESOM_BEGIN = 350;

    // number of soil layers modelled
    public static final int NSOILLAYER = 2;

    // soil upper layer depth (mm)
    public static final double SOILDEPTH_UPPER = 500.0;

    // soil lower layer depth (mm)
    public static final double SOILDEPTH_LOWER = 1000.0;

    // available water holding capacity as fraction of soil volume
    public double awc_frac;

    // available water holding capacity of soil layers [0=upper layer] (mm)
    public double[] awc = new double[2];

    // coefficient in percolation calculation (K in Eqn 31, Haxeltine & Prentice
    // 1996)
    public double perc_base;

    // exponent in percolation calculation (=4 in Eqn 31, Haxeltine & Prentice
    // 1996)
    public double perc_exp;

    // thermal diffusivity at 0% WHC (mm2/s)
    public double thermdiff_0;

    // thermal diffusivity at 15% WHC (mm2/s)
    public double thermdiff_15;

    // thermal diffusivity at 100% WHC (mm2/s)
    public double thermdiff_100;

    // year at which to calculate equilibrium soil carbon
    public int solvesom_end;

    // year at which to begin documenting means for calculation of equilibrium
    // soil carbon
    public int solvesom_begin;

    private Soiltype() {
        solvesom_end = SOLVESOM_END;
        solvesom_begin = SOLVESOM_BEGIN;
    }

    // guess2008 - override the default SOM years with 70-80% of the spin-up
    // period length
    public final void updateSolveSOMvalues(int nyrspinup) {
        solvesom_end = (int) (0.8 * nyrspinup);
        solvesom_begin = (int) (0.7 * nyrspinup);
    }

    public static Soiltype getLPJSoiltype(int soilcode) {
        
        /*
         * this should only happen in degenerate ERUs, which we should ignore
         */
        if (soilcode <= 0) {
            soilcode = 1;
        }
        
        // DESCRIPTION
        // Derivation of soil physical parameters given LPJ soil code
        // INPUT AND OUTPUT PARAMETER
        // soil = patch soil

        final double PERC_EXP = 2.0;
        // exponent in percolation equation [k2; LPJF]
        // (Eqn 31, Haxeltine & Prentice 1996)
        // Changed from 4 to 2 (Sitch, Thonicke, pers comm 26/11/01)

        // if (soilcode < 1 || soilcode > 9) {
        // fail("soilparameters: invalid LPJ soil code (%d)", soilcode);
        // }

        Soiltype soiltype = new Soiltype();
        soiltype.perc_base = data[soilcode - 1][0];
        soiltype.perc_exp = PERC_EXP;
        soiltype.awc[0] = SOILDEPTH_UPPER * data[soilcode - 1][1];
        soiltype.awc[1] = SOILDEPTH_LOWER * data[soilcode - 1][1];
        soiltype.thermdiff_0 = data[soilcode - 1][2];
        soiltype.thermdiff_15 = data[soilcode - 1][3];
        soiltype.thermdiff_100 = data[soilcode - 1][4];

        // TODO: Do we know how many years the simulation will be running for
        // when we start?
        // If so, we need to do something with it here: for now we ignore it.
        // // guess2008 - override the default SOM years with 70-80% of the
        // spin-up
        // // period
        // soiltype.updateSolveSOMvalues(_configuration.getSchedule().nYears());

        return soiltype;
    }
}
