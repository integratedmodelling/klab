package org.integratedmodelling.ecology.biomass.lpjguess;

public class GridcellPFT {
    // MEMBER VARIABLES
    // / A number identifying this object within its list array
    int id;

    // / A reference to the Pft object for this Gridcellpft
    PFT pft;

    // / annual degree day sum above threshold damaging temperature
    /**
     * used in calculation of heat stess mortality; Sitch et al 2000, Eqn 55
     */
    double addtw;

    // / Michaelis-Menten kinetic parameters
    /**
     * Half saturation concentration for N uptake (Rothstein 2000, Macduff 2002)
     */
    double Km;

    // /Crop-specific variables:
    // / whether the daily temperature has fallen below the autumn temperature
    // limit (tempautumn) this year
    Boolean autumnoccurred;
    // / whether the daily temperature has risen above the spring temperature
    // limit (tempspring) this year
    Boolean springoccurred;
    // / whether the daily temperature has fallen below the vernalization limit
    // (trg) this year
    Boolean vernstartoccurred;
    // / whether the daily temperature rises over the vernalization limit (trg)
    // this year
    Boolean vernendoccurred;
    // / whether the precipitation sum rises over the limit (defined in
    // set_sdatecalc_prec), not used if NEWSOWINGDATE is defined
    Boolean precoccurred;
    // / first day when temperature fell below the autumn temperature limit
    // (tempautumn) this year
    int     first_autumndate;
    // / 20-year mean
    int     first_autumndate20;
    // / memory of the last 20 years' values
    int     first_autumndate_20[] = new int[20];
    // / last day when temperature rose above the spring temperature limit
    // (tempspring) this year
    int     last_springdate;
    // / 20-year mean
    int     last_springdate20;
    // / memory of the last 20 years' values
    int     last_springdate_20[]  = new int[20];
    // / last day when temperature has fallen below the vernilisation
    // temperature limit (trg) this year (if vernstartoccurred==true)
    int     last_verndate;
    // / 20-year mean
    int     last_verndate20;
    // / memory of the last 20 years' values
    int     last_verndate_20[]    = new int[20];
    // / first day when 10-day precipitation sum rises over the limit (defined
    // in set_sdatecalc_prec), not used if NEWSOWINGDATE is defined
    int     first_precdate;
    // / default sowing date (pft.sdatenh/sdatesh)
    int     sdate_default;
    // / calculated sowing date from temperature limits
    int     sdatecalc_temp;
    // / calculated sowing date from precipitation limits
    int     sdatecalc_prec;
    // / sowing date from input file
    int     sdate_force;
    // / harvest date from input file
    int     hdate_force;
    // / N fertilization from input file
    double  Nfert_read;
    // / default harvest date (pft.hlimitdatenh/hlimitdatesh)
    int     hlimitdate_default;
    // / whether autumn sowing is either calculated or prescribed
    Boolean wintertype;
    // / whether two sowing seasons per year is allowed (currently used only for
    // rice, based on geograhical limits defined in getgridcell() )
    Boolean multicrop;
    // / first and last day of crop sowing window, calculated in
    // calc_sowing_windows()
    int     swindow[]             = new int[2];
    // / first and last day of crop sowing window for irrigated crops,
    // calculated in calc_sowing_windows()
    int     swindow_irr[]         = new int[2];
    // / temperature limits precludes crop sowing
    Boolean sowing_restriction;

    public GridcellPFT(int i, PFT p) {
        /**
         * \param i The id for this object
         * \param p A reference to the Pft for
         * this Gridcellpft
         */

        pft = p;
        id = i;

        addtw = 0.0;
        Km = 0.0;

        autumnoccurred = false;
        springoccurred = false;
        vernstartoccurred = false;
        vernendoccurred = false;
        precoccurred = false;
        first_autumndate = -1;
        first_autumndate20 = -1;
        last_springdate = -1;
        last_springdate20 = -1;
        last_verndate = -1;
        last_verndate20 = -1;
        first_precdate = -1;

        for (int year = 0; year < 20; year++) {
            first_autumndate_20[year] = -1;
            last_springdate_20[year] = -1;
            last_verndate_20[year] = -1;
        }
        sdate_default = -1;
        sdate_force = -1;
        hdate_force = -1;
        Nfert_read = -1;
        sdatecalc_temp = -1;
        sdatecalc_prec = -1;
        hlimitdate_default = -1;
        wintertype = false;
        multicrop = false;
        swindow[0] = -1;
        swindow[1] = -1;
        sowing_restriction = false;
    }

}
