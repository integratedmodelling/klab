package org.integratedmodelling.ecology.biomass.lpjguess;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.ecology.biomass.lpjguess.common.LitterSolveSOM;
import org.integratedmodelling.ecology.biomass.lpjguess.common.SOMPool;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IModelObject;

///////////////////////////////////////////////////////////////////////////////////////
//SOIL
//Stores state variables for soils and the snow pack. Initialised by a call to
//initdrivers. One Soil object is defined for each patch. A reference to the parent
//Patch object (defined below) is included as a member variable. Soil static
//parameters are stored as objects of class Soiltype, of which there is one for each
//stand. A reference to the Soiltype object holding the static parameters for this
//soil is included as a member variable.

public class Soil implements IModelObject {

    // reference to parent Patch object
    public Patch    patch;
    // reference to Soiltype object holding static parameters for this soil
    public Soiltype soiltype;
    // water content of soil layers [0=upper layer] as fraction of available
    // water
    // holding capacity;
    public double[] wcont       = new double[IConfiguration.NSOILLAYER];
    // guess2008 - DLE - the average wcont over the growing season, for each
    // soil layer
    public double[] awcont      = new double[IConfiguration.NSOILLAYER];
    // water content of sublayer of upper soil layer for which evaporation from
    // the bare soil surface is possible (fraction of available water holding
    // capacity)
    public double   wcont_evap;
    // daily water content in upper soil layer for each day of year
    public double[] dwcontupper = new double[366];
    // mean water content in upper soil layer for last month
    // (valid only on last day of month following call to
    // daily_accounting_patch)
    public double   mwcontupper;
    // stored snow as average over modelled area (mm rainfall equivalents)
    public double   snowpack;
    // total runoff today (mm/day)
    public double   runoff;
    // soil temperature today at 0.25 m depth (deg C)
    public double   temp;
    // daily temperatures for the last month (deg C)
    // (valid only on last day of month following call to
    // daily_accounting_patch)
    public double[] dtemp       = new double[32];
    // mean soil temperature for the last month (deg C)
    // (valid only on last day of month following call to
    // daily_accounting_patch)
    public double   mtemp;
    // respiration response to today's soil temperature at 0.25 m depth
    // incorporating damping of Q10 due to temperature acclimation (Lloyd &
    // Taylor
    // 1994)
    public double   gtemp;
    // the last day (0-364) for which gtemp was calculated
    public int      last_gtemp  = -1;
    // gtemp (see above) calculated for this month's average temperature
    public double   mgtemp;
    // the last month (0-11) for which mgtemp was calculated
    public int      last_mgtemp = -1;
    // soil organic matter (SOM) pool with c. 1000 yr turnover (kgC/m2)
    public double   cpool_slow;
    // soil organic matter (SOM) pool with c. 33 yr turnover (kgC/m2)
    public double   cpool_fast;

    // Running sums (converted to long term means) maintained by SOM dynamics
    // module

    // mean annual litter decomposition (kgC/m2/yr)
    public double decomp_litter_mean;
    // mean value of decay constant for fast SOM fraction
    public double k_soilfast_mean;
    // mean value of decay constant for slow SOM fraction
    public double k_soilslow_mean;

    // Parameters used by function soiltemp and updated monthly

    public double alag;
    public double exp_alag = 1.0;

    // guess2008 - 3 new soil water variables
    // water content of soil layers [0=upper layer] as fraction of available
    // water
    // holding capacity;
    public double[][] mwcont      = new double[12][IConfiguration.NSOILLAYER];
    // daily water content in lower soil layer for each day of year
    public double[]   dwcontlower = new double[366];

    // mean water content in lower soil layer for last month
    // (valid only on last day of month following call to
    // daily_accounting_patch)
    public double mwcontlower;

    //////////////////////////////////////////////////////////////////////////////////
    // CENTURY SOM pools and other variables

    public SOMPool sompool[] = new SOMPool[SOMPoolType.NSOMPOOL.ordinal()];

    /// daily percolation (mm)
    public double dperc;
    /// fraction of decayed organic nitrogen leached each day;
    public double orgleachfrac;
    /// soil mineral nitrogen pool (kgN/m2)
    public double nmass_avail;
    /// soil nitrogen input (kgN/m2)
    public double ninput;
    /// annual sum of nitrogen mineralisation
    public double anmin;
    /// annual sum of nitrogen immobilisation
    public double animmob;
    /// annual leaching from available nitrogen pool
    public double aminleach;
    /// annual leaching of organics from active nitrogen pool
    public double aorgleach;
    /// total annual nitrogen fixation
    public double anfix;
    /// calculated annual mean nitrogen fixation
    public double anfix_calc;

    // Variables for fast spinup of SOM pools

    /// monthly fraction of available mineral nitrogen taken up
    public double fnuptake_mean[]  = new double[12];
    /// monthly fraction of organic carbon/nitrogen leached
    public double morgleach_mean[] = new double[12];
    /// monthly fraction of available mineral nitrogen leached
    public double mminleach_mean[] = new double[12];
    /// annual nitrogen fixation
    public double anfix_mean;

    // Solving Century SOM pools

    /// years at which to begin documenting for calculation of Century equilibrium
    public int solvesomcent_beginyr;
    /// years at which to end documentation and start calculation of Century equilibrium
    public int solvesomcent_endyr;

    /// Cumulative litter pools for one year.
    public LitterSolveSOM litterSolveSOM;

    List<LitterSolveSOM> solvesom = new ArrayList<LitterSolveSOM>();

    /// rainfall and snowmelt today (mm)
    public double  rain_melt;
    /// upper limit for percolation (mm)
    public double  max_rain_melt;
    /// whether to percolate today
    public boolean percolate;

    /// stored nitrogen deposition in snowpack
    public double snowpack_nmass;

    public Soil(Patch p, Soiltype s) {
        this.patch = p;
        this.soiltype = s;
        initdrivers();
    }

    /*
     * reinitialization - repeated at each stand? FIXME probably unnecessary.
     */
    public final void initdrivers() {
        // Initialises certain member variables
        alag = 0.0;
        exp_alag = 1.0;
        cpool_slow = 0.0;
        cpool_fast = 0.0;
        decomp_litter_mean = 0.0;
        k_soilfast_mean = 0.0;
        k_soilslow_mean = 0.0;
        wcont[0] = 0.0;
        wcont[1] = 0.0;
        wcont_evap = 0.0;
        snowpack = 0.0;
        orgleachfrac = 0.0;

        mwcontupper = 0.0;
        mwcontlower = 0.0;
        for (int mth = 0; mth < 12; mth++) {
            mwcont[mth][0] = 0.0;
            mwcont[mth][1] = 0.0;
            fnuptake_mean[mth] = 0.0;
            morgleach_mean[mth] = 0.0;
            mminleach_mean[mth] = 0.0;
        }

        for (int i = 0; i < sompool.length; i++) {
            sompool[i] = new SOMPool();
        }

        // std::fill_n(dwcontupper, Date::MAX_YEAR_LENGTH, 0.0);
        // std::fill_n(dwcontlower, Date::MAX_YEAR_LENGTH, 0.0);

        /////////////////////////////////////////////////////
        // Initialise CENTURY pools

        // Set initial CENTURY pool N:C ratios
        // Parton et al 1993, Fig 4

        sompool[SOMPoolType.SOILMICRO.ordinal()].ntoc = 1.0 / 15.0;
        sompool[SOMPoolType.SURFHUMUS.ordinal()].ntoc = 1.0 / 15.0;
        sompool[SOMPoolType.SLOWSOM.ordinal()].ntoc = 1.0 / 20.0;
        sompool[SOMPoolType.SURFMICRO.ordinal()].ntoc = 1.0 / 20.0;

        // passive has a fixed value
        sompool[SOMPoolType.PASSIVESOM.ordinal()].ntoc = 1.0 / 9.0;

        nmass_avail = 0.0;
        ninput = 0.0;
        anmin = 0.0;
        animmob = 0.0;
        aminleach = 0.0;
        aorgleach = 0.0;
        anfix = 0.0;
        anfix_calc = 0.0;
        anfix_mean = 0.0;
        snowpack_nmass = 0.0;
        dperc = 0.0;

        // solvesomcent_beginyr = (int)(SOLVESOMCENT_SPINBEGIN * (nyear_spinup - freenyears) + freenyears);
        // solvesomcent_endyr = (int)(SOLVESOMCENT_SPINEND * (nyear_spinup - freenyears) + freenyears);
    }

    @Override
    public IConfiguration getConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

}
