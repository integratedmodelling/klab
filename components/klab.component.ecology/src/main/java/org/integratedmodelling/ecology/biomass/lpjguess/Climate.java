package org.integratedmodelling.ecology.biomass.lpjguess;

import org.integratedmodelling.ecology.biomass.lpjguess.common.Historic;
import org.integratedmodelling.procsim.api.IConfiguration;
import org.integratedmodelling.procsim.api.IConfiguration.Insolation;
import org.integratedmodelling.procsim.api.IModelObject;

///////////////////////////////////////////////////////////////////////////////////////
// CLIMATE
// Stores all static and variable data relating to this parameters, as well as
// latitude, atmospheric CO2 concentration and daylength for a stand (corresponding to
// a modelled locality or grid cell). Includes a reference to the parent Stand object
// (defined below). Initialised by a call to initdrivers.

public class Climate implements IModelObject {

    Configuration configuration;

    // // reference to parent Stand object
    // public Stand stand;

    // mean air temperature today (deg C)
    public double temp;

    // total daily net downward shortwave solar radiation today (J/m2/day)
    public double rad;

    // total daily photosynthetically-active radiation today (J/m2/day)
    public double par;

    // precipitation today (mm)
    public double prec;

    // day length today (h)
    public double daylength;

    // atmospheric ambient CO2 concentration today (ppmv)
    public double co2 = 400;

    // latitude (degrees; +=north, -=south)
    public double lat;

    // insolation today
    public double insol;

    // units in which insol expressed:
    // SUNSHINE = percentage of full sunshine
    // NETSWRAD = net downward shortwave radiation flux (albedo corrected)
    // (W/m2)
    // SWRAD = total downward shortwave radiation flux (W/m2)
    public Insolation instype = Insolation.SUNSHINE;

    // equilibrium evapotranspiration today (mm/day)
    public double eet;

    // mean temperature for the last 31 days (deg C)
    public double mtemp;

    // lowest mean monthly temperature for the last 20 years (deg C)
    public double mtemp_min20;
    public double mtemp_max20;

    // highest mean monthly temperature for the last 12 months (deg C)
    public double mtemp_max;

    // accumulated growing degree day sum on 5 degree base (reset when
    // temperatures
    // fall below 5 deg C)
    public double gdd5;

    // total gdd5 (accumulated) for this year (reset 1
    // January)
    public double agdd5;

    // number of days with temperatures <5 deg C (reset when temperatures fall
    // below 5 deg C; maximum value 365)
    public int chilldays;

    // guess2008 - CHILLDAYS - true if chill day count may be reset by
    // temperature fall below 5 deg C
    public boolean ifsensechill;

    // respiration response to today's air temperature incorporating damping of
    // Q10
    // due to temperature acclimation (Lloyd & Taylor 1994)
    public double gtemp;

    // the last day (0-364) for which gtemp was calculated
    public int last_gtemp;

    // gtemp (see above) calculated for this month's average temperature
    public double mgtemp;

    // the last month (0-11) for which mgtemp was calculated
    public int last_mgtemp;

    // daily temperatures for the last 31 days (deg C)
    public double[] dtemp_31 = new double[31];

    // minimum monthly temperatures for the last 20 years (deg C)
    public double[] mtemp_min_20 = new double[20];
    public double[] mtemp_max_20 = new double[20];

    // minimum monthly temperature for the last 12 months (deg C)
    public double mtemp_min;

    // mean of monthly temperatures for the last 12 months (deg C)
    public double atemp_mean;

    /// annual nitrogen deposition (kgN/m2/year)
    public double andep;
    /// daily nitrogen deposition (kgN/m2)
    public double dndep;

    // Monthly sums (converted to means) used by canopy exchange module

    // accumulated mean temperature for this month (deg C)
    public double temp_mean;

    // accumulated mean daily net PAR sum (J/m2/day) for this month
    public double par_mean;

    // accumulated mean CO2 for this month (ppmv)
    public double co2_mean;

    // accumulated mean daylength for this month (h)
    public double daylength_mean;

    // Saved parameters used by function daylengthinsolpet

    public double   sinelat;
    public double   cosinelat;
    public double[] qo             = new double[366];
    public double[] u              = new double[366];
    public double[] v              = new double[366];
    public double[] hh             = new double[366];
    public double[] sinehh         = new double[366];
    public double[] daylength_save = new double[366];

    // indicates whether saved values exist for this day
    public boolean[] doneday = new boolean[366];

    /// Variables used for crop sowing date or seasonality calculation

    /// daily precipitations for the last 10 days (mm)
    public double dprec_10[] = new double[10];
    /// daily 10 day-sums of precipitations for today and yesterday (mm)
    public double sprec_2[]  = new double[2];
    /// max temperature during the last test period
    public double maxtemp;
    /// summer day when we test last year's crossing of sowing temperature limits; NH:June
    /// 30(day 180), SH:Dec.31(day 364), set in getgridcell()
    public int    testday_temp;
    /// last day of dry month when we test last year's crossing of sowing precipitation
    /// limits; NH:Dec.31(day 364), SH:June 30(day 180), set in getgridcell()
    public int    testday_prec;
    /// date used for sowing if no frost or spring occured during the year between the
    /// testmonths; NH:14, SH:195, set in getgridcell()
    public int    coldestday;
    /// used to adapt equations to hemisphere, set in getgridcell()
    public int    adjustlat;

    /// copy of monthly historical or spinup temperature values for one year (from local
    /// guessio.cpp-variables hist_mtemp or spinup_mtemp).
    public double mtemp_year[] = new double[12];
    /// copy of monthly historical or spinup precipitation values for one year (from local
    /// guessio.cpp-variables hist_mtemp or spinup_mtemp).
    public double mprec_year[] = new double[12];
    /// accumulated monthly pet values for this year
    public double mpet_year[]  = new double[12];

    /// past 20 years monthly temperature values
    public double mtemp_20[][]      = new double[20][12];
    /// past 20 years monthly precipitation values
    public double mprec_20[][]      = new double[20][12];
    /// past 20 years monthly PET values
    public double mpet_20[][]       = new double[20][12];
    /// past 20 years monthly precipitation to PET ratios
    public double mprec_pet_20[][]  = new double[20][12];
    /// past 20 years minimum of monthly precipitation to PET ratios
    public double mprec_petmin_20[] = new double[20];
    /// past 20 years maximum of monthly precipitation to PET ratios
    public double mprec_petmax_20[] = new double[20];

    /// 20-year running average monthly temperature values
    public double mtemp20[]     = new double[12];
    /// 20-year running average monthly precipitation values
    public double mprec20[]     = new double[12];
    /// 20-year running average monthly PET values
    public double mpet20[]      = new double[12];
    /// 20-year running average monthly precipitation to PET ratios
    public double mprec_pet20[] = new double[12];

    /// 20-year running average of minimum monthly precipitation to PET ratios
    public double mprec_petmin20;
    /// 20-year running average of maximum monthly precipitation to PET ratios
    public double mprec_petmax20;

    // Test with the historic class instead
    Historic hmtemp_20[] = new Historic[12];
    Historic hmprec_20[] = new Historic[12];
    Historic hmeet_20[]  = new Historic[12];

    /// seasonality type (SEASONALITY_NO, SEASONALITY_PREC, SEASONALITY_PRECTEMP,
    /// SEASONALITY_TEMP, SEASONALITY_TEMPPREC)
    public SeasonalityType seasonality;
    public SeasonalityType seasonality_lastyear;

    /// precipitation seasonality type (DRY, DRY_public INTERMEDIATE, DRY_WET, public
    /// INTERMEDIATE, public INTERMEDIATE_WET, WET)
    /**
     * based on the extremes of the 20-year monthly means
     */
    public PrecSeasonalityType prec_seasonality;
    public PrecSeasonalityType prec_seasonality_lastyear;

    /// precipitation range (DRY, DRY_public INTERMEDIATE, DRY_WET, public INTERMEDIATE,
    /// public INTERMEDIATE_WET, WET)
    /**
     * based on the average of the 20-year monthly extremes
     */
    public PrecSeasonalityType prec_range;
    public PrecSeasonalityType prec_range_lastyear;

    /// temperature seasonality (COLD, COLD_WARM, COLD_HOT, WARM, WARM_HOT, HOT)
    public TempSeasonalityType temp_seasonality;
    public TempSeasonalityType temp_seasonality_lastyear;

    /// whether several months with precipitation maxima exists (remains to be
    /// implemented)
    public Boolean biseasonal;

    /// variation coefficient of 20-year mean monthly temperatures
    public double var_prec;
    /// variation coefficient of 20-year mean monthly precipitation to PET ratios
    public double var_temp;

    /// annual precipitation sum
    public double aprec;

    public Climate(Configuration configuration, double latitude) {

        this.configuration = configuration;
        for (int i = 0; i < hmtemp_20.length; i++) {
            hmtemp_20[i] = new Historic(20);
        }
        for (int i = 0; i < hmprec_20.length; i++) {
            hmprec_20[i] = new Historic(20);
        }
        for (int i = 0; i < hmeet_20.length; i++) {
            hmeet_20[i] = new Historic(20);
        }
        
        /*
         * TODO needs to have all its configuration created before calling init
         */
        init(lat = latitude);
    }

    // constructor function: initialises stand member

    private void init(double latitude) {

        // Initialises certain member variables
        // Should be called before Climate object is applied to a new grid cell

        final double DEGTORAD = 0.01745329;
        int day;
        int year;

        for (year = 0; year < 20; year++) {
            mtemp_min_20[year] = 0.0;
            mtemp_max_20[year] = 0.0;
        }
        mtemp = 0.0;
        gdd5 = 0.0;
        chilldays = 0;
        ifsensechill = true; // guess2008 - CHILLDAYS
        atemp_mean = 0.0;
        last_gtemp = -1;
        last_mgtemp = -1;

        for (day = 0; day < 365; day++) {
            doneday[day] = false;
        }
        sinelat = Math.sin(lat * DEGTORAD);
        cosinelat = Math.cos(lat * DEGTORAD);
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // DAYLENGTH, INSOLATION AND POTENTIAL EVAPOTRANSPIRATION
    // Called by framework each simulation day following update of daily air
    // temperature
    // and before canopy exchange processes

    public void dayLengthInsolEET() {

        // DESCRIPTION
        // Calculation of daylength, insolation and equilibrium
        // evapotranspiration
        // for each day, given mean daily temperature, insolation (as percentage
        // of full sunshine or mean daily instantaneous downward shortwave
        // radiation flux, W/m2), latitude and day of year

        // INPUT AND OUTPUT PARAMETER
        // this = stand this

        final double QOO = 1360.0;
        final double PI = 3.1415927;
        final double BETA = 0.17;
        final double A = 107.0;
        final double B = 0.2;
        final double C = 0.25;
        final double D = 0.5;
        final double K = 13750.98708;
        final double DEGTORAD = 0.01745329;
        final double FRADPAR = 0.5;
        // fraction of net incident shortwave radiation that is
        // photosynthetically
        // active (PAR)

        double delta; // solar declination angle (radians)
        double rs_day; // daily net downward shortwave radiation sum (J/m2/day)
        double rl; // instantaneous net upward longwave radiation flux (W/m2)
        double eet_day; // equilibrium evapotranspiration sum (mm/day)
        double w;
        double gamma;
        double lambda;
        double s;
        double uu;
        double vv;
        double hn;

        // CALCULATION OF NET DOWNWARD SHORT-WAVE RADIATION FLUX
        // Refs: Prentice et al 1993, Monteith & Unsworth 1990,
        // Henderson-Sellers & Robinson 1986

        // (1) rs = (c + d*ni) * (1 - beta) * Qo * cos Z * k
        // (Eqn 7, Prentice et al 1993)
        // (2) Qo = Qoo * ( 1 + 2*0.01675 * cos ( 2*pi*(i+0.5)/365) )
        // (Eqn 8, Prentice et al 1993; angle in radians)
        // (3) cos Z = sin(lat) * sin(delta) + cos(lat) * cos(delta) * cos h
        // (Eqn 9, Prentice et al 1993)
        // (4) delta = -23.4 * pi / 180 * cos ( 2*pi*(i+10.5)/365 )
        // (Eqn 10, Prentice et al 1993, angle in radians)
        // (5) h = 2 * pi * t / 24 = pi * t / 12

        // where rs = instantaneous net downward shortwave radiation
        // flux, including correction for terrestrial shortwave albedo
        // (W/m2 = J/m2/s)
        // c, d = empirical constants (c+d = clear sky
        // transmissivity)
        // ni = proportion of bright sunshine
        // beta = average 'global' value for shortwave albedo
        // (not associated with any particular vegetation)
        // i = julian day, (0-364, 0=1 Jan)
        // Qoo = solar constant, 1360 W/m2
        // Z = solar zenith angle (angular distance between the
        // sun's rays and the local vertical)
        // k = conversion factor from solar angular units to
        // seconds, 12 / pi * 3600
        // lat = latitude (+=N, -=S, in radians)
        // delta = solar declination (angle between the orbital
        // plane and the Earth's equatorial plane) varying
        // between +23.4 degrees in northern hemisphere
        // midsummer and -23.4 degrees in N hemisphere
        // midwinter
        // h = hour angle, the fraction of 2*pi (radians) which
        // the earth has turned since the local solar noon
        // t = local time in hours from solar noon

        // From (1) and (3), shortwave radiation flux at any hour during the
        // day, any day of the year and any latitude given by
        // (6) rs = (c + d*ni) * (1 - beta) * Qo * ( sin(lat) * sin(delta) +
        // cos(lat) * cos(delta) * cos h ) * k
        // Solar zenith angle equal to -pi/2 (radians) at sunrise and pi/2 at
        // sunset. For Z=pi/2 or Z=-pi/2,
        // (7) cos Z = 0
        // From (3) and (7),
        // (8) cos hh = - sin(lat) * sin(delta) / ( cos(lat) * cos(delta) )
        // where hh = half-day length in angular units
        // Define
        // (9) u = sin(lat) * sin(delta)
        // (10) v = cos(lat) * cos(delta)
        // Thus
        // (11) hh = acos (-u/v)
        // To obtain the daily net downward short-wave radiation sum, integrate
        // equation (6) from -hh to hh with respect to h,
        // (12) rs_day = 2 * (c + d*ni) * (1 - beta) * Qo *
        // ( u*hh + v*sin(hh) )
        // Define
        // (13) w = (c + d*ni) * (1 - beta) * Qo
        // From (12) & (13), and converting from angular units to seconds
        // (14) rs_day = 2 * w * ( u*hh + v*sin(hh) ) * k

        if (!this.doneday[this.configuration.getSchedule().julianDay()]) {

            // Calculate values of saved parameters for this day

            this.qo[this.configuration.getSchedule().julianDay()] = QOO
                    * (1.0 + 2.0 * 0.01675
                            * Math.cos(2.0 * PI * (this.configuration.getSchedule().julianDay() + 0.5)
                                    / 365.0)); // Eqn 2
            delta = -23.4 * DEGTORAD
                    * Math.cos(2.0 * PI * (this.configuration.getSchedule().julianDay() + 10.5) / 365.0); // Eqn
            // 4
            this.u[this.configuration.getSchedule().julianDay()] = this.sinelat * Math.sin(delta); // Eqn
            // 9
            this.v[this.configuration.getSchedule().julianDay()] = this.cosinelat * Math.cos(delta); // Eqn
            // 10

            if (this.u[this.configuration.getSchedule().julianDay()] >= this.v[this.configuration
                    .getSchedule().julianDay()]) {
                this.hh[this.configuration.getSchedule().julianDay()] = PI; // polar
                // julianDay
            } else if (this.u[this.configuration.getSchedule()
                    .julianDay()] <= -this.v[this.configuration.getSchedule()
                            .julianDay()]) {
                this.hh[this.configuration.getSchedule().julianDay()] = 0.0; // polar
 // night
            } else {
                this.hh[this.configuration.getSchedule().julianDay()] = Math.acos(-this.u[this.configuration
                        .getSchedule().julianDay()] / this.v[this.configuration.getSchedule().julianDay()]); // Eqn
                // 11
            }

            this.sinehh[this.configuration.getSchedule().julianDay()] = Math
                    .sin(this.hh[this.configuration.getSchedule()
                            .julianDay()]);

            // Calculate daylength in hours from hh

            this.daylength_save[this.configuration.getSchedule().julianDay()] = 24.0
                    * this.hh[this.configuration.getSchedule().julianDay()] / PI;

            this.doneday[this.configuration.getSchedule().julianDay()] = true;
        }

        if (this.instype == Insolation.SUNSHINE)  // insolation provided as
        // percentage
        // sunshine
        {

            w = (C + D * this.insol / 100.0) * (1.0 - BETA)
                    * this.qo[this.configuration.getSchedule().julianDay()]; // Eqn
            // 13
            rs_day = 2.0
                    * w
                    * (this.u[this.configuration.getSchedule().julianDay()]
                            * this.hh[this.configuration.getSchedule().julianDay()]
                            + this.v[this.configuration
                                    .getSchedule().julianDay()]
                                    * this.sinehh[this.configuration.getSchedule().julianDay()])
                    * K; // Eqn
            // 14

        } else // insolation provided as instantaneous downward shortwave
 // radiation flux
        {

            if (this.instype == Insolation.NETSWRAD)  // net radiation known
            {
                rs_day = this.insol * this.daylength_save[this.configuration.getSchedule().julianDay()]
                        * 3600.0;
            } else // include correction for albedo
            {
                rs_day = this.insol * (1.0 - BETA)
                        * this.daylength_save[this.configuration.getSchedule().julianDay()]
                        * 3600.0;
            }

            // guess2008 - special case for polar night
            if (this.sinehh[this.configuration.getSchedule().julianDay()] < 0.001) {
                w = 0.0; // polar night
            } else {
                w = rs_day
                        / 2.0
                        / (this.u[this.configuration.getSchedule().julianDay()]
                                * this.hh[this.configuration.getSchedule().julianDay()]
                                + this.v[this.configuration
                                        .getSchedule().julianDay()]
                                        * this.sinehh[this.configuration.getSchedule().julianDay()])
                        / K; // from
                // Eqn
                // 14
            }
        }

        // CALCULATION OF DAILY EQUILIBRIUM EVAPOTRANSPIRATION
        // (EET, or evaporative demand)
        // Refs: Jarvis & McNaughton 1986, Prentice et al 1993

        // (15) eet = ( s / (s + gamma) ) * rn / lambda
        // (Eqn 5, Prentice et al 1993)
        // (16) s = 2.503E+6 * exp ( 17.269 * temp / (237.3 + temp) ) /
        // (237.3 + temp)**2
        // (Eqn 6, Prentice et al 1993)
        // (17) rn = rs - rl
        // (18) rl = ( b + (1-b) * ni ) * ( a - temp )
        // (Eqn 11, Prentice et al 1993)

        // where eet = instantaneous evaporative demand (mm/s)
        // gamma = psychrometer constant, c. 65 Pa/K
        // lambda = latent heat of vapourisation of water,
        // c. 2.5E+6 J/kg
        // temp = temperature (deg C)
        // rl = net upward longwave radiation flux
        // ('terrestrial radiation') (W/m2)
        // rn = net downward radiation flux (W/m2)
        // a, b = empirical constants

        // Note: gamma and lambda are weakly dependent on temperature. Simple
        // linear functions are used to obtain approximate values for a
        // given temperature

        // From (13) & (18),
        // (19) rl = ( b + (1-b) * ( w / Qo / (1 - beta) - c ) / d ) * ( a -
        // temp )

        // Define
        // (20) uu = w * u - rl
        // (21) vv = w * v

        // Daily EET sum is instantaneous EET integrated over the period
        // during which rn >= 0. Limits for the integration (half-period
        // hn) are obtained by solving for

        // (22) rn=0
        // From (17) & (22),
        // (23) rs - rl = 0
        // From (6), (20), (21) and (23),
        // (24) uu + vv * cos hn = 0
        // From (24),
        // (25) hn = acos ( -uu/vv )

        // Integration of (15) w.r.t. h in the range -hn to hn leads to the
        // following formula for total daily EET (mm)

        // (26) eet_day = 2 * ( s / (s + gamma) / lambda ) *
        // ( uu*hn + vv*sin(hn) ) * k

        rl = (B + (1.0 - B) * (w / this.qo[this.configuration.getSchedule().julianDay()] / (1.0 - BETA) - C)
                / D)
                * (A - this.temp); // Eqn 19

        // Calculate gamma and lambda

        gamma = 65.05 + this.temp * 0.064;
        lambda = 2.495E6 - this.temp * 2380.0;

        s = 2.503E6 * Math.exp(17.269 * this.temp / (237.3 + this.temp)) / (237.3 + this.temp)
                / (237.3 + this.temp); // Eqn
 // 16

        uu = w * this.u[this.configuration.getSchedule().julianDay()] - rl; // Eqn
        // 20
        vv = w * this.v[this.configuration.getSchedule().julianDay()]; // Eqn 21

        // Calculate half-period with positive net radiation, hn
        // In Eqn (25), hn defined for uu in range -vv to vv
        // For uu >= vv, hn = pi (12 hours, i.e. polar day)
        // For uu <= -vv, hn = 0 (i.e. polar night)

		if (uu >= vv) // polar day
		{
			hn = PI;
		} else if (uu <= -vv) // polar night
		{
			hn = 0.0;
		} else // Eqn 25
		{
			hn = Math.acos(-uu / vv);
		}

        // Calculate total EET for this day

        eet_day = 2.0 * (s / (s + gamma) / lambda) * (uu * hn + vv * Math.sin(hn)) * K; // Eqn
        // 26

        // Transfer output to member variables of Climate object

        this.rad = rs_day;
        this.eet = eet_day;
        this.daylength = this.daylength_save[this.configuration.getSchedule().julianDay()];

        // Calculate PAR from radiation
        // Eqn A1, Haxeltine & Prentice 1996

        this.par = rs_day * FRADPAR;
    }

    @Override
    public IConfiguration getConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }
}
