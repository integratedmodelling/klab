package org.integratedmodelling.ecology.solpos;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.joda.time.DateTime;

/**
 * Ported from C solpos.c 9/12/2016
 * 
 * @author Martin Rymes
*    National Renewable Energy Laboratory
*    25 March 1998
*
*    27 April 1999 REVISION:  Corrected leap year in S_date.
*    13 January 2000 REVISION:  SMW converted to structure posdata parameter
*                               and subdivided into functions.
*    01 February 2001 REVISION: SMW corrected ecobli calculation 
*                               (changed sign). Error is small (max 0.015 deg
*                               in calculation of declination angle)
*                               
* @author ferdinando.villa port to Java and API improvements
* 
*/
public class SolarPosition {

    /**
     * 
     * @param date
     * @param lat
     * @param lon
     * @return
     */
    public static Results getSolarPosition(DateTime date, double lat, double lon) {
        return null;
    }

    
    /**
     * 
     * @param date
     * @param lat
     * @param lon
     * @param functions
     * @return
     */
    public static Results getSolarPosition(DateTime date, double lat, double lon, int functions) {
        return null;
    }

    /**
     * 
     * @param date
     * @param lat
     * @param lon
     * @param pressure
     * @param temperature
     * @param functions
     * @return
     */
    public static Results getSolarPosition(DateTime date, double lat, double lon, float pressure, float temperature, int functions) {
        return null;
    }
    
    public static final int S_YEAR_ERROR   = 0;                             /*
                                                                             * 0 year 1950
                                                                             * - 2050
                                                                             */
    public static final int S_MONTH_ERROR  = 1;                             /*
                                                                             * 1 month 1 -
                                                                             * 12
                                                                             */
    public static final int S_DAY_ERROR    = 2;                             /*
                                                                             * 2 day-of-
                                                                             * month 1 -
                                                                             * 31
                                                                             */
    public static final int S_DOY_ERROR    = 3;                             /*
                                                                             * 3
                                                                             * day-of-year
                                                                             * 1 - 366
                                                                             */
    public static final int S_HOUR_ERROR   = 4;                             /*
                                                                             * 4 hour 0 -
                                                                             * 24
                                                                             */
    public static final int S_MINUTE_ERROR = 5;                             /*
                                                                             * 5 minute 0
                                                                             * - 59
                                                                             */
    public static final int S_SECOND_ERROR = 6;                             /*
                                                                             * 6 second 0
                                                                             * - 59
                                                                             */
    public static final int S_TZONE_ERROR  = 7;                             /*
                                                                             * 7 time zone
                                                                             * -12 - 12
                                                                             */
    public static final int S_INTRVL_ERROR = 8;                             /*
                                                                             * 8 interval
                                                                             * (seconds) 0
                                                                             * - 28800
                                                                             */
    public static final int S_LAT_ERROR    = 9;                             /*
                                                                             * 9 latitude
                                                                             * -90 - 90
                                                                             */
    public static final int S_LON_ERROR    = 10;                            /*
                                                                             * 10
                                                                             * longitude
                                                                             * -180 - 180
                                                                             */
    public static final int S_TEMP_ERROR   = 11;                            /*
                                                                             * 11
                                                                             * temperature
                                                                             * (deg. C)
                                                                             * -100 - 100
                                                                             */
    public static final int S_PRESS_ERROR  = 12;                            /*
                                                                             * 12 pressure
                                                                             * (millibars)
                                                                             * 0 - 2000
                                                                             */
    public static final int S_TILT_ERROR   = 13;                            /*
                                                                             * 13 tilt -90
                                                                             * - 90
                                                                             */
    public static final int S_ASPECT_ERROR = 14;                            /*
                                                                             * 14 aspect
                                                                             * -360 - 360
                                                                             */
    public static final int S_SBWID_ERROR  = 15;                            /*
                                                                             * 15 shadow
                                                                             * band width
                                                                             * (cm) 1 -
                                                                             * 100
                                                                             */
    public static final int S_SBRAD_ERROR  = 16;                            /*
                                                                             * 16 shadow
                                                                             * band radius
                                                                             * (cm) 1 -
                                                                             * 100
                                                                             */
    public static final int S_SBSKY_ERROR  = 17;                            /*
                                                                             * 17 shadow
                                                                             * band sky
                                                                             * factor -1 -
                                                                             * 1
                                                                             */

    /*
     * ============================================================================
     *
     * Define the function codes
     *
     * ----------------------------------------------------------------------------
     */
    public static final int L_DOY          = 0x0001;
    public static final int L_GEOM         = 0x0002;
    public static final int L_ZENETR       = 0x0004;
    public static final int L_SSHA         = 0x0008;
    public static final int L_SBCF         = 0x0010;
    public static final int L_TST          = 0x0020;
    public static final int L_SRSS         = 0x0040;
    public static final int L_SOLAZM       = 0x0080;
    public static final int L_REFRAC       = 0x0100;
    public static final int L_AMASS        = 0x0200;
    public static final int L_PRIME        = 0x0400;
    public static final int L_TILT         = 0x0800;
    public static final int L_ETR          = 0x1000;
    public static final int L_ALL          = 0xFFFF;

    /*
     * ============================================================================
     *
     * Define the bit-wise masks for each function
     *
     * ----------------------------------------------------------------------------
     */
    public static final int S_DOY          = (L_DOY);
    public static final int S_GEOM         = (L_GEOM | S_DOY);
    public static final int S_ZENETR       = (L_ZENETR | S_GEOM);
    public static final int S_SSHA         = (L_SSHA | S_GEOM);
    public static final int S_SBCF         = (L_SBCF | S_SSHA);
    public static final int S_TST          = (L_TST | S_GEOM);
    public static final int S_SRSS         = (L_SRSS | S_SSHA | S_TST);
    public static final int S_SOLAZM       = (L_SOLAZM | S_ZENETR);
    public static final int S_REFRAC       = (L_REFRAC | S_ZENETR);
    public static final int S_AMASS        = (L_AMASS | S_REFRAC);
    public static final int S_PRIME        = (L_PRIME | S_AMASS);
    public static final int S_TILT         = (L_TILT | S_SOLAZM | S_REFRAC);
    public static final int S_ETR          = (L_ETR | S_REFRAC);
    public static final int S_ALL          = (L_ALL);

    /**
     * ============================================================================
     * Contains: S_solpos (computes solar position and intensity from time and place)
     *<p>
     * <ul>
     * <li>INPUTS: (via posdata struct) year, daynum, hour, minute, second, latitude,
     * longitude, timezone, intervl 
     * <li>OPTIONAL: (via posdata struct) month, day, press,
     * temp, tilt, aspect, function 
     * <li>OUTPUTS: EVERY variable in the struct posdata (defined
     * in solpos.h)
     *</ul>
     *<p>
     * NOTE: Certain conditions exist during which some of the output variables are
     * undefined or cannot be calculated. In these cases, the variables are returned with
     * flag values indicating such. In other cases, the variables may return a realistic,
     * though invalid, value. These variables and the flag values or invalid conditions
     * are listed below:
     *<p>
     *<ul>
     * <li>amass -1.0 at zenetr angles greater than 93.0 degrees 
     * <li>ampress -1.0 at zenetr angles greater than 93.0 degrees 
     * <li>azim invalid at zenetr angle 0.0 or latitude +/-90.0 or at night elevetr limited to -9 degrees at night 
     * <li>etr 0.0 at night 
     * <li>etrn 0.0 at night
     * <li>etrtilt 0.0 when cosinc is less than 0 
     * <li>prime invalid at zenetr angles greater than 93.0 degrees 
     * <li>sretr +/- 2999.0 during periods of 24 hour sunup or sundown 
     * <li>ssetr +/- 2999.0 during periods of 24 hour sunup or sundown 
     * <li>ssha invalid at the North and South Poles 
     * <li>unprime invalid at zenetr angles greater than 93.0 degrees 
     * <li>zenetr limited to 99.0 degrees at night
     *</ul>
     *
     * S_init (optional initialization for all input parameters in the posdata struct)
     * INPUTS: struct posdata* OUTPUTS: struct posdata*
     *
     * (Note: initializes the required S_solpos INPUTS above to out-of-bounds conditions,
     * forcing the user to supply the parameters; initializes the OPTIONAL S_solpos inputs
     * above to nominal values.)
     *
     * S_decode (optional utility for decoding the S_solpos return code) INPUTS: long
     * integer S_solpos return value, struct posdata* OUTPUTS: text to stderr
     *
     * Usage: In calling program, just after other 'includes', insert:
     *
     * #include "solpos00.h"
     *
     * Function calls: S_init(struct posdata*) [optional] . . [set time and location
     * parameters before S_solpos call] . . int retval = S_solpos(struct posdata*)
     * S_decode(int retval, struct posdata*) [optional] (Note: you should always look at
     * the S_solpos return value, which contains error codes. S_decode is one option for
     * examining these codes. It can also serve as a template for building your own
     * application-specific decoder.)
     *
     * Martin Rymes National Renewable Energy Laboratory 25 March 1998
     *
     * 27 April 1999 REVISION: Corrected leap year in S_date. 13 January 2000 REVISION:
     * SMW converted to structure posdata parameter and subdivided into functions. 01
     * February 2001 REVISION: SMW corrected ecobli calculation (changed sign). Error is
     * small (max 0.015 deg in calculation of declination angle)
     * ----------------------------------------------------------------------------
     */

    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *
     * Structures defined for this module
     *
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    static private class trigdata /* used to pass calculated values locally */
    {
        float cd; /* cosine of the declination */
        float ch; /* cosine of the hour angle */
        float cl; /* cosine of the latitude */
        float sd; /* sine of the declination */
        float sl; /* sine of the latitude */
    };

    static public class Results {

        /*
         * Each comment begins with a 1-column letter code: I: INPUT variable O: OUTPUT
         * variable T: TRANSITIONAL variable used in the algorithm, of interest only to
         * the solar radiation modelers, and available to you because you may be one of
         * them.
         * 
         * The FUNCTION column indicates which sub-function within solpos must be switched
         * on using the "function" parameter to calculate the desired output variable. All
         * function codes are defined in the solpos.h file. The default S_ALL switch
         * calculates all output variables. Multiple functions may be or'd to create a
         * composite function switch. For example, (S_TST | S_SBCF). Specifying only the
         * functions for required output variables may allow solpos to execute more
         * quickly.
         * 
         * The S_DOY mask works as a toggle between the input date represented as a day
         * number (daynum) or as month and day. To set the switch (to use daynum input),
         * the function is or'd; to clear the switch (to use month and day input), the
         * function is inverted and and'd.
         * 
         * For example: pdat.function |= S_DOY (sets daynum input) pdat.function &= ~S_DOY
         * (sets month and day input)
         * 
         * Whichever date form is used, S_solpos will calculate and return the
         * variables(s) of the other form. See the soltest.c program for other examples.
         */

        /**
         * I/O: S_DOY Day of month (May 27 = 27, etc.) solpos will
         * CALCULATE this by default, or will optionally require it as
         * input depending on the setting of the S_DOY function switch.
         */
        int   day; 
        
        /**
         * I/O: S_DOY Day number (day of year; Feb 1 = 32 ) solpos
         * REQUIRES this by default, but will optionally calculate it
         * from month and day depending on the setting of the S_DOY
         * function switch.
         */
        int   daynum;    

        /**
         * I: Switch to choose functions for desired output.
         */
        int   function;  
        int   hour;      /* I: Hour of day, 0 - 23, DEFAULT = 12 */
        int   interval;  /*
                          * I: Interval of a measurement period in seconds. Forces solpos
                          * to use the time and date from the interval midpoint. The INPUT
                          * time (hour, minute, and second) is assumed to be the END of
                          * the measurement interval.
                          */
        int   minute;    /* I: Minute of hour, 0 - 59, DEFAULT = 0 */
        int   month;     /*
                          * I/O: S_DOY Month number (Jan = 1, Feb = 2, etc.) solpos will
                          * CALCULATE this by default, or will optionally require it as
                          * input depending on the setting of the S_DOY function switch.
                          */
        int   second;    /* I: Second of minute, 0 - 59, DEFAULT = 0 */
        int   year;      /*
                          * I: 4-digit year (2-digit year is NOT allowed
                          */

        float amass;     /* O: S_AMASS Relative optical airmass */
        float ampress;   /* O: S_AMASS Pressure-corrected airmass */
        float aspect;    /*
                          * I: Azimuth of panel surface (direction it faces) N=0, E=90,
                          * S=180, W=270, DEFAULT = 180
                          */
        float azim;      /*
                          * O: S_SOLAZM Solar azimuth angle: N=0, E=90, S=180, W=270
                          */
        float cosinc;    /*
                          * O: S_TILT Cosine of solar incidence angle on panel
                          */
        float coszen;    /*
                          * O: S_REFRAC Cosine of refraction corrected solar zenith angle
                          */
        float dayang;    /*
                          * T: S_GEOM Day angle (daynum*360/year-length) degrees
                          */
        float declin;    /*
                          * T: S_GEOM Declination--zenith angle of solar noon at equator,
                          * degrees NORTH
                          */
        float eclong;    /* T: S_GEOM Ecliptic longitude, degrees */
        float ecobli;    /* T: S_GEOM Obliquity of ecliptic */
        float ectime;    /* T: S_GEOM Time of ecliptic calculations */
        float elevetr;   /*
                          * O: S_ZENETR Solar elevation, no atmospheric correction (= ETR)
                          */
        float elevref;   /*
                          * O: S_REFRAC Solar elevation angle, deg. from horizon,
                          * refracted
                          */
        float eqntim;    /* T: S_TST Equation of time (TST - LMT), minutes */
        float erv;       /*
                          * T: S_GEOM Earth radius vector (multiplied to solar constant)
                          */
        float etr;       /*
                          * O: S_ETR Extraterrestrial (top-of-atmosphere) W/sq m global
                          * horizontal solar irradiance
                          */
        float etrn;      /*
                          * O: S_ETR Extraterrestrial (top-of-atmosphere) W/sq m direct
                          * normal solar irradiance
                          */
        float etrtilt;   /*
                          * O: S_TILT Extraterrestrial (top-of-atmosphere) W/sq m global
                          * irradiance on a tilted surface
                          */
        float gmst;      /* T: S_GEOM Greenwich mean sidereal time, hours */
        float hrang;     /*
                          * T: S_GEOM Hour angle--hour of sun from solar noon, degrees
                          * WEST
                          */
        float julday;    /*
                          * T: S_GEOM Julian Day of 1 JAN 2000 minus 2,400,000 days (in
                          * order to regain single precision)
                          */
        float latitude;  /* I: Latitude, degrees north (south negative) */
        float longitude; /* I: Longitude, degrees east (west negative) */
        float lmst;      /* T: S_GEOM Local mean sidereal time, degrees */
        float mnanom;    /* T: S_GEOM Mean anomaly, degrees */
        float mnlong;    /* T: S_GEOM Mean longitude, degrees */
        float rascen;    /* T: S_GEOM Right ascension, degrees */
        float press;     /*
                          * I: Surface pressure, millibars, used for refraction correction
                          * and ampress
                          */
        float prime;     /* O: S_PRIME Factor that normalizes Kt, Kn, etc. */
        float sbcf;      /* O: S_SBCF Shadow-band correction factor */
        float sbwid;     /* I: Shadow-band width (cm) */
        float sbrad;     /* I: Shadow-band radius (cm) */
        float sbsky;     /* I: Shadow-band sky factor */
        float solcon;    /* I: Solar constant (NREL uses 1367 W/sq m) */
        float ssha;      /* T: S_SRHA Sunset(/rise) hour angle, degrees */
        float sretr;     /*
                          * O: S_SRSS Sunrise time, minutes from midnight, local, WITHOUT
                          * refraction
                          */
        float ssetr;     /*
                          * O: S_SRSS Sunset time, minutes from midnight, local, WITHOUT
                          * refraction
                          */
        float temp;      /*
                          * I: Ambient dry-bulb temperature, degrees C, used for
                          * refraction correction
                          */
        float tilt;      /* I: Degrees tilt from horizontal of panel */
        float timezone;  /*
                          * I: Time zone, east (west negative). USA: Mountain = -7,
                          * Central = -6, etc.
                          */
        float tst;       /* T: S_TST True solar time, minutes from midnight */
        float tstfix;    /* T: S_TST True solar time - local standard time */
        float unprime;   /* O: S_PRIME Factor that denormalizes Kt', Kn', etc. */
        float utime;     /* T: S_GEOM Universal (Greenwich) standard time */
        float zenetr;    /*
                          * T: S_ZENETR Solar zenith angle, no atmospheric correction (=
                          * ETR)
                          */
        float zenref;    /*
                          * O: S_REFRAC Solar zenith angle, deg. from zenith, refracted
                          */
    };

    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     *
     * Temporary global variables used only in this file:
     *
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    /* cumulative number of days prior to beginning of month */
    static int   month_days[][] = {
            { 0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334 },
            { 0, 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335 }
    };

    static float degrad         = 57.295779513f;                         /*
                                                                          * converts from
                                                                          * radians to
                                                                          * degrees
                                                                          */
    static float raddeg         = 0.0174532925f;                         /*
                                                                          * converts from
                                                                          * degrees to
                                                                          * radians
                                                                          */

    // /*
    // * ============================================================================
    // Local
    // * function prototypes
    // * ============================================================================
    // */
    // static long int validate ( struct posdata *pdat);
    //
    // static void dom2doy( struct posdata *pdat );
    //
    // static void doy2dom( struct posdata *pdat );
    //
    // static void geometry ( struct posdata *pdat );
    //
    // static void zen_no_ref ( struct posdata *pdat, struct trigdata *tdat );
    //
    // static void ssha( struct posdata *pdat, struct trigdata *tdat );
    //
    // static void sbcf( struct posdata *pdat, struct trigdata *tdat );
    //
    // static void tst( struct posdata *pdat );
    //
    // static void srss( struct posdata *pdat );
    //
    // static void sazm( struct posdata *pdat, struct trigdata *tdat );
    //
    // static void refrac( struct posdata *pdat );
    //
    // static void amass( struct posdata *pdat );
    //
    // static void prime( struct posdata *pdat );
    //
    // static void etr( struct posdata *pdat );
    //
    // static void tilt( struct posdata *pdat );
    //
    // static void localtrig( struct posdata *pdat, struct trigdata *tdat );

    /**
     * ============================================================================ Long
     * integer function S_solpos, adapted from the VAX solar libraries
     *
     * This function calculates the apparent solar position and the intensity of the sun
     * (theoretical maximum solar energy) from time and place on Earth.
     *
     * Requires (from the struct posdata parameter): Date and time: year daynum
     * (requirement depends on the S_DOY switch) month (requirement depends on the S_DOY
     * switch) day (requirement depends on the S_DOY switch) hour minute second interval
     * DEFAULT 0 Location: latitude longitude Location/time adjuster: timezone Atmospheric
     * pressure and temperature: press DEFAULT 1013.0 mb temp DEFAULT 10.0 degrees C Tilt
     * of flat surface that receives solar energy: aspect DEFAULT 180 (South) tilt DEFAULT
     * 0 (Horizontal) Function Switch (codes defined in solpos.h) function DEFAULT S_ALL
     *
     * Returns (via the struct posdata parameter): everything defined in the struct
     * posdata in solpos.h.
     * ----------------------------------------------------------------------------
     */
    static public long S_solpos(Results pdat) {
        long retval;

        trigdata tdat = new trigdata();

        /* initialize the trig structure */
        tdat.sd = -999.0f; /* flag to force calculation of trig data */
        tdat.cd = 1.0f;
        tdat.ch = 1.0f; /* set the rest of these to something safe */
        tdat.cl = 1.0f;
        tdat.sl = 1.0f;

        if ((retval = validate(pdat)) != 0) /* validate the inputs */
            return retval;

        if ((pdat.function & L_DOY) != 0)
            doy2dom(pdat); /* convert input doy to month-day */
        else
            dom2doy(pdat); /* convert input month-day to doy */

        if ((pdat.function & L_GEOM) != 0)
            geometry(pdat); /* do basic geometry calculations */

        if ((pdat.function & L_ZENETR) != 0) /* etr at non-refracted zenith angle */
            zen_no_ref(pdat, tdat);

        if ((pdat.function & L_SSHA) != 0) /* Sunset hour calculation */
            ssha(pdat, tdat);

        if ((pdat.function & L_SBCF) != 0) /* Shadowband correction factor */
            sbcf(pdat, tdat);

        if ((pdat.function & L_TST) != 0) /* true solar time */
            tst(pdat);

        if ((pdat.function & L_SRSS) != 0) /* sunrise/sunset calculations */
            srss(pdat);

        if ((pdat.function & L_SOLAZM) != 0) /* solar azimuth calculations */
            sazm(pdat, tdat);

        if ((pdat.function & L_REFRAC) != 0) /* atmospheric refraction calculations */
            refrac(pdat);

        if ((pdat.function & L_AMASS) != 0)/* airmass calculations */
            amass(pdat);

        if ((pdat.function & L_PRIME) != 0)/* kt-prime/unprime calculations */
            prime(pdat);

        if ((pdat.function & L_ETR) != 0) /* ETR and ETRN (refracted) */
            etr(pdat);

        if ((pdat.function & L_TILT) != 0) /* tilt calculations */
            tilt(pdat);

        return 0;
    }

    /*
     * ============================================================================ Void
     * function S_init
     *
     * This function initiates all of the input parameters in the struct posdata passed to
     * S_solpos(). Initialization is either to nominal values or to out of range values,
     * which forces the calling program to specify parameters.
     *
     * NOTE: This function is optional if you initialize ALL input parameters in your
     * calling code. Note that the required parameters of date and location are
     * deliberately initialized out of bounds to force the user to enter real-world
     * values.
     *
     * Requires: Pointer to a posdata structure, members of which are initialized.
     *
     * Returns: Void
     * ----------------------------------------------------------------------------
     */
    static void S_init(Results pdat) {
        pdat.day = -99; /* Day of month (May 27 = 27, etc.) */
        pdat.daynum = -999; /* Day number (day of year; Feb 1 = 32 ) */
        pdat.hour = -99; /* Hour of day, 0 - 23 */
        pdat.minute = -99; /* Minute of hour, 0 - 59 */
        pdat.month = -99; /* Month number (Jan = 1, Feb = 2, etc.) */
        pdat.second = -99; /* Second of minute, 0 - 59 */
        pdat.year = -99; /* 4-digit year */
        pdat.interval = 0; /* instantaneous measurement interval */
        pdat.aspect = 180.0f; /*
                               * Azimuth of panel surface (direction it faces) N=0, E=90,
                               * S=180, W=270
                               */
        pdat.latitude = -99.0f; /* Latitude, degrees north (south negative) */
        pdat.longitude = -999.0f; /* Longitude, degrees east (west negative) */
        pdat.press = 1013.0f; /* Surface pressure, millibars */
        pdat.solcon = 1367.0f; /* Solar constant, 1367 W/sq m */
        pdat.temp = 15.0f; /* Ambient dry-bulb temperature, degrees C */
        pdat.tilt = 0.0f; /* Degrees tilt from horizontal of panel */
        pdat.timezone = -99.0f; /* Time zone, east (west negative). */
        pdat.sbwid = 7.6f; /* Eppley shadow band width */
        pdat.sbrad = 31.7f; /* Eppley shadow band radius */
        pdat.sbsky = 0.04f; /* Drummond factor for partly cloudy skies */
        pdat.function = S_ALL; /* compute all parameters */
    }

    /*
     * ============================================================================ Local
     * long int function validate
     *
     * Validates the input parameters
     * ----------------------------------------------------------------------------
     */
    static long validate(Results pdat) {

        long retval = 0; /* start with no errors */

        /* No absurd dates, please. */
        if ((pdat.function & L_GEOM) != 0) {
            if ((pdat.year < 1950) || (pdat.year > 2050)) /* limits of algoritm */
                retval |= (1L << S_YEAR_ERROR);
            if (!((pdat.function & S_DOY) != 0) && ((pdat.month < 1) || (pdat.month > 12)))
                retval |= (1L << S_MONTH_ERROR);
            if (!((pdat.function & S_DOY) != 0) && ((pdat.day < 1) || (pdat.day > 31)))
                retval |= (1L << S_DAY_ERROR);
            if (((pdat.function & S_DOY) != 0) && ((pdat.daynum < 1) || (pdat.daynum > 366)))
                retval |= (1L << S_DOY_ERROR);

            /* No absurd times, please. */
            if ((pdat.hour < 0) || (pdat.hour > 24))
                retval |= (1L << S_HOUR_ERROR);
            if ((pdat.minute < 0) || (pdat.minute > 59))
                retval |= (1L << S_MINUTE_ERROR);
            if ((pdat.second < 0) || (pdat.second > 59))
                retval |= (1L << S_SECOND_ERROR);
            if ((pdat.hour == 24) && (pdat.minute > 0)) /* no more than 24 hrs */
                retval |= ((1L << S_HOUR_ERROR) | (1L << S_MINUTE_ERROR));
            if ((pdat.hour == 24) && (pdat.second > 0)) /* no more than 24 hrs */
                retval |= ((1L << S_HOUR_ERROR) | (1L << S_SECOND_ERROR));
            if (Math.abs(pdat.timezone) > 12.0)
                retval |= (1L << S_TZONE_ERROR);
            if ((pdat.interval < 0) || (pdat.interval > 28800))
                retval |= (1L << S_INTRVL_ERROR);

            /* No absurd locations, please. */
            if (Math.abs(pdat.longitude) > 180.0)
                retval |= (1L << S_LON_ERROR);
            if (Math.abs(pdat.latitude) > 90.0)
                retval |= (1L << S_LAT_ERROR);
        }

        /* No silly temperatures or pressures, please. */
        if (((pdat.function & L_REFRAC) != 0) && (Math.abs(pdat.temp) > 100.0))
            retval |= (1L << S_TEMP_ERROR);
        if (((pdat.function & L_REFRAC) != 0) &&
                (pdat.press < 0.0) || (pdat.press > 2000.0))
            retval |= (1L << S_PRESS_ERROR);

        /* No out of bounds tilts, please */
        if (((pdat.function & L_TILT) != 0) && (Math.abs(pdat.tilt) > 180.0))
            retval |= (1L << S_TILT_ERROR);
        if (((pdat.function & L_TILT) != 0) && (Math.abs(pdat.aspect) > 360.0))
            retval |= (1L << S_ASPECT_ERROR);

        /* No oddball shadowbands, please */
        if (((pdat.function & L_SBCF) != 0) &&
                (pdat.sbwid < 1.0) || (pdat.sbwid > 100.0))
            retval |= (1L << S_SBWID_ERROR);
        if (((pdat.function & L_SBCF) != 0) &&
                (pdat.sbrad < 1.0) || (pdat.sbrad > 100.0))
            retval |= (1L << S_SBRAD_ERROR);
        if (((pdat.function & L_SBCF) != 0) && (Math.abs(pdat.sbsky) > 1.0))
            retval |= (1L << S_SBSKY_ERROR);

        return retval;
    }

    /*
     * ============================================================================ Local
     * Void function dom2doy
     *
     * Converts day-of-month to day-of-year
     *
     * Requires (from struct posdata parameter): year month day
     *
     * Returns (via the struct posdata parameter): year daynum
     * ----------------------------------------------------------------------------
     */
    static void dom2doy(Results pdat) {
        
        pdat.daynum = pdat.day + month_days[0][pdat.month];

        /* (adjust for leap year) */
        if (((pdat.year % 4) == 0) &&
                (((pdat.year % 100) != 0) || ((pdat.year % 400) == 0)) &&
                (pdat.month > 2))
            pdat.daynum += 1;
    }

    /*
     * ============================================================================ Local
     * void function doy2dom
     *
     * This function computes the month/day from the day number.
     *
     * Requires (from struct posdata parameter): Year and day number: year daynum
     *
     * Returns (via the struct posdata parameter): year month day
     * ----------------------------------------------------------------------------
     */
    static void doy2dom(Results pdat) {
        int imon; /* Month (month_days) array counter */
        int leap; /* leap year switch */

        /* Set the leap year switch */
        if (((pdat.year % 4) == 0) &&
                (((pdat.year % 100) != 0) || ((pdat.year % 400) == 0)))
            leap = 1;
        else
            leap = 0;

        /* Find the month */
        imon = 12;
        while (pdat.daynum <= month_days[leap][imon])
            --imon;

        /* Set the month and day of month */
        pdat.month = imon;
        pdat.day = pdat.daynum - month_days[leap][imon];
    }

    /*
     * ============================================================================ Local
     * Void function geometry
     *
     * Does the underlying geometry for a given time and location
     * ----------------------------------------------------------------------------
     */
    static void geometry(Results pdat) {

        float bottom; /* denominator (bottom) of the fraction */
        float c2; /* cosine of d2 */
        float cd; /* cosine of the day angle or delination */
        float d2; /* pdat.dayang times two */
        float delta; /* difference between current year and 1949 */
        float s2; /* sine of d2 */
        float sd; /* sine of the day angle */
        float top; /* numerator (top) of the fraction */
        int leap; /* leap year counter */

        /* Day angle */
        /*
         * Iqbal, M. 1983. An Introduction to Solar Radiation. Academic Press, NY., page 3
         */
        pdat.dayang = 360.0f * (pdat.daynum - 1) / 365.0f;

        /* Earth radius vector * solar constant = solar energy */
        /*
         * Spencer, J. W. 1971. Fourier series representation of the position of the sun.
         * Search 2 (5), page 172
         */
        sd = (float) Math.sin(raddeg * pdat.dayang);
        cd = (float) Math.cos(raddeg * pdat.dayang);
        d2 = 2.0f * pdat.dayang;
        c2 = (float) Math.cos(raddeg * d2);
        s2 = (float) Math.sin(raddeg * d2);

        pdat.erv = 1.000110f + 0.034221f * cd + 0.001280f * sd;
        pdat.erv += 0.000719f * c2 + 0.000077f * s2;

        /* Universal Coordinated (Greenwich standard) time */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.utime = pdat.hour * 3600.0f +
                pdat.minute * 60.0f +
                pdat.second -
                pdat.interval / 2.0f;
        pdat.utime = pdat.utime / 3600.0f - pdat.timezone;

        /* Julian Day minus 2,400,000 days (to eliminate roundoff errors) */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */

        /*
         * No adjustment for century non-leap years since this function is bounded by 1950
         * - 2050
         */
        delta = pdat.year - 1949;
        leap = (int) (delta / 4.0);
        pdat.julday = 32916.5f + delta * 365.0f + leap + pdat.daynum + pdat.utime / 24.0f;

        /* Time used in the calculation of ecliptic coordinates */
        /* Noon 1 JAN 2000 = 2,400,000 + 51,545 days Julian Date */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.ectime = pdat.julday - 51545.0f;

        /* Mean longitude */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.mnlong = 280.460f + 0.9856474f * pdat.ectime;

        /* (dump the multiples of 360, so the answer is between 0 and 360) */
        pdat.mnlong -= 360.0 * (int) (pdat.mnlong / 360.0);
        if (pdat.mnlong < 0.0)
            pdat.mnlong += 360.0;

        /* Mean anomaly */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.mnanom = 357.528f + 0.9856003f * pdat.ectime;

        /* (dump the multiples of 360, so the answer is between 0 and 360) */
        pdat.mnanom -= 360.0 * (int) (pdat.mnanom / 360.0);
        if (pdat.mnanom < 0.0)
            pdat.mnanom += 360.0;

        /* Ecliptic longitude */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.eclong = (float) (pdat.mnlong + 1.915 * Math.sin(pdat.mnanom * raddeg) +
                0.020 * Math.sin(2.0 * pdat.mnanom * raddeg));

        /* (dump the multiples of 360, so the answer is between 0 and 360) */
        pdat.eclong -= 360.0 * (int) (pdat.eclong / 360.0);
        if (pdat.eclong < 0.0)
            pdat.eclong += 360.0;

        /* Obliquity of the ecliptic */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */

        /* 02 Feb 2001 SMW corrected sign in the following line */
        /* pdat.ecobli = 23.439 + 4.0e-07 * pdat.ectime; */
        pdat.ecobli = 23.439f - 4.0e-07f * pdat.ectime;

        /* Declination */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.declin = (float) (degrad * Math.asin(Math.sin(pdat.ecobli * raddeg) *
                Math.sin(pdat.eclong * raddeg)));

        /* Right ascension */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        top = (float) (Math.cos(raddeg * pdat.ecobli) * Math.sin(raddeg * pdat.eclong));
        bottom = (float) Math.cos(raddeg * pdat.eclong);

        pdat.rascen = (float) (degrad * Math.atan2(top, bottom));

        /* (make it a positive angle) */
        if (pdat.rascen < 0.0)
            pdat.rascen += 360.0;

        /* Greenwich mean sidereal time */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.gmst = 6.697375f + 0.0657098242f * pdat.ectime + pdat.utime;

        /* (dump the multiples of 24, so the answer is between 0 and 24) */
        pdat.gmst -= 24.0 * (int) (pdat.gmst / 24.0);
        if (pdat.gmst < 0.0)
            pdat.gmst += 24.0;

        /* Local mean sidereal time */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.lmst = (float) (pdat.gmst * 15.0 + pdat.longitude);

        /* (dump the multiples of 360, so the answer is between 0 and 360) */
        pdat.lmst -= 360.0 * (int) (pdat.lmst / 360.0);
        if (pdat.lmst < 0.)
            pdat.lmst += 360.0;

        /* Hour angle */
        /*
         * Michalsky, J. 1988. The Astronomical Almanac's algorithm for approximate solar
         * position (1950-2050). Solar Energy 40 (3), pp. 227-235.
         */
        pdat.hrang = pdat.lmst - pdat.rascen;

        /* (force it between -180 and 180 degrees) */
        if (pdat.hrang < -180.0)
            pdat.hrang += 360.0;
        else if (pdat.hrang > 180.0)
            pdat.hrang -= 360.0;
    }

    /*
     * ============================================================================ Local
     * Void function zen_no_ref
     *
     * ETR solar zenith angle Iqbal, M. 1983. An Introduction to Solar Radiation. Academic
     * Press, NY., page 15
     * ----------------------------------------------------------------------------
     */
    static void zen_no_ref(Results pdat, trigdata tdat) {
        float cz; /* cosine of the solar zenith angle */

        localtrig(pdat, tdat);
        cz = tdat.sd * tdat.sl + tdat.cd * tdat.cl * tdat.ch;

        /* (watch out for the roundoff errors) */
        if (Math.abs(cz) > 1.0) {
            if (cz >= 0.0)
                cz = 1.0f;
            else
                cz = -1.0f;
        }

        pdat.zenetr = (float) (Math.acos(cz) * degrad);

        /* (limit the degrees below the horizon to 9 [+90 . 99]) */
        if (pdat.zenetr > 99.0f)
            pdat.zenetr = 99.0f;

        pdat.elevetr = 90.0f - pdat.zenetr;
    }

    /*
     * ============================================================================ Local
     * Void function ssha
     *
     * Sunset hour angle, degrees Iqbal, M. 1983. An Introduction to Solar Radiation.
     * Academic Press, NY., page 16
     * ----------------------------------------------------------------------------
     */
    static void ssha(Results pdat, trigdata tdat) {
        float cssha; /* cosine of the sunset hour angle */
        float cdcl; /* ( cd * cl ) */

        localtrig(pdat, tdat);
        cdcl = tdat.cd * tdat.cl;

        if (Math.abs(cdcl) >= 0.001) {
            cssha = -tdat.sl * tdat.sd / cdcl;

            /* This keeps the cosine from blowing on roundoff */
            if (cssha < -1.0)
                pdat.ssha = 180.0f;
            else if (cssha > 1.0)
                pdat.ssha = (float) 0.0;
            else
                pdat.ssha = (float) (degrad * Math.acos(cssha));
        } else if (((pdat.declin >= 0.0) && (pdat.latitude > 0.0f))
                || ((pdat.declin < 0.0f) && (pdat.latitude < 0.0f)))
            pdat.ssha = 180.0f;
        else
            pdat.ssha = 0.0f;
    }

    /*
     * ============================================================================ Local
     * Void function sbcf
     *
     * Shadowband correction factor Drummond, A. J. 1956. A contribution to absolute
     * pyrheliometry. Q. J. R. Meteorol. Soc. 82, pp. 481-493
     * ----------------------------------------------------------------------------
     */
    static void sbcf(Results pdat, trigdata tdat) {
        float p, t1, t2; /* used to compute sbcf */

        localtrig(pdat, tdat);
        p = (float) (0.6366198f * pdat.sbwid / pdat.sbrad * Math.pow(tdat.cd, 3));
        t1 = tdat.sl * tdat.sd * pdat.ssha * raddeg;
        t2 = (float) (tdat.cl * tdat.cd * Math.sin(pdat.ssha * raddeg));
        pdat.sbcf = pdat.sbsky + 1.0f / (1.0f - p * (t1 + t2));

    }

    /*
     * ============================================================================ Local
     * Void function tst
     *
     * TST . True Solar Time = local standard time + TSTfix, time in minutes from
     * midnight. Iqbal, M. 1983. An Introduction to Solar Radiation. Academic Press, NY.,
     * page 13
     * ----------------------------------------------------------------------------
     */
    static void tst(Results pdat) {
        pdat.tst = (180.0f + pdat.hrang) * 4.0f;
        pdat.tstfix = pdat.tst - pdat.hour * 60.0f - pdat.minute - pdat.second / 60.0f
                + pdat.interval / 120.0f; /*
                                           * add back half of the interval
                                           */

        /* bound tstfix to this day */
        while (pdat.tstfix > 720.0)
            pdat.tstfix -= 1440.0;
        while (pdat.tstfix < -720.0)
            pdat.tstfix += 1440.0;

        pdat.eqntim = pdat.tstfix + 60.0f * pdat.timezone - 4.0f * pdat.longitude;

    }

    /*
     * ============================================================================ Local
     * Void function srss
     *
     * Sunrise and sunset times (minutes from midnight)
     * ----------------------------------------------------------------------------
     */
    static void srss(Results pdat) {
        if (pdat.ssha <= 1.0) {
            pdat.sretr = 2999.0f;
            pdat.ssetr = -2999.0f;
        } else if (pdat.ssha >= 179.0f) {
            pdat.sretr = -2999.0f;
            pdat.ssetr = 2999.0f;
        } else {
            pdat.sretr = 720.0f - 4.0f * pdat.ssha - pdat.tstfix;
            pdat.ssetr = 720.0f + 4.0f * pdat.ssha - pdat.tstfix;
        }
    }

    /*
     * ============================================================================ Local
     * Void function sazm
     *
     * Solar azimuth angle Iqbal, M. 1983. An Introduction to Solar Radiation. Academic
     * Press, NY., page 15
     * ----------------------------------------------------------------------------
     */
    static void sazm(Results pdat, trigdata tdat) {
        float ca; /* cosine of the solar azimuth angle */
        float ce; /* cosine of the solar elevation */
        float cecl; /* ( ce * cl ) */
        float se; /* sine of the solar elevation */

        localtrig(pdat, tdat);
        ce = (float) Math.cos(raddeg * pdat.elevetr);
        se = (float) Math.sin(raddeg * pdat.elevetr);

        pdat.azim = 180.0f;
        cecl = ce * tdat.cl;
        if (Math.abs(cecl) >= 0.001) {
            ca = (se * tdat.sl - tdat.sd) / cecl;
            if (ca > 1.0)
                ca = (float) 1.0;
            else if (ca < -1.0)
                ca = -1.0f;

            pdat.azim = (float) (180.0 - Math.acos(ca) * degrad);
            if (pdat.hrang > 0)
                pdat.azim = 360.0f - pdat.azim;
        }
    }

    /*
     * ============================================================================ Local
     * Int function refrac
     *
     * Refraction correction, degrees Zimmerman, John C. 1981. Sun-pointing programs and
     * their accuracy. SAND81-0761, Experimental Systems Operation Division 4721, Sandia
     * National Laboratories, Albuquerque, NM.
     * ----------------------------------------------------------------------------
     */
    static void refrac(Results pdat) {
        float prestemp; /* temporary pressure/temperature correction */
        float refcor; /* temporary refraction correction */
        float tanelev; /* tangent of the solar elevation angle */

        /* If the sun is near zenith, the algorithm bombs; refraction near 0 */
        if (pdat.elevetr > 85.0)
            refcor = 0.0f;

        /* Otherwise, we have refraction */
        else {
            tanelev = (float) Math.tan(raddeg * pdat.elevetr);
            if (pdat.elevetr >= 5.0)
                refcor = (float) (58.1 / tanelev - 0.07 / (Math.pow(tanelev, 3))
                        + 0.000086 / (Math.pow(tanelev, 5)));
            else if (pdat.elevetr >= -0.575)
                refcor = (float) (1735.0 + pdat.elevetr
                        * (-518.2 + pdat.elevetr * (103.4 + pdat.elevetr * (-12.79 + pdat.elevetr * 0.711))));
            else
                refcor = (float) (-20.774 / tanelev);

            prestemp = (float) ((pdat.press * 283.0) / (1013.0 * (273.0 + pdat.temp)));
            refcor *= prestemp / 3600.0;
        }

        /* Refracted solar elevation angle */
        pdat.elevref = pdat.elevetr + refcor;

        /* (limit the degrees below the horizon to 9) */
        if (pdat.elevref < -9.0)
            pdat.elevref = -9.0f;

        /* Refracted solar zenith angle */
        pdat.zenref = 90.0f - pdat.elevref;
        pdat.coszen = (float) Math.cos(raddeg * pdat.zenref);
    }

    /*
     * ============================================================================ Local
     * Void function amass
     *
     * Airmass Kasten, F. and Young, A. 1989. Revised optical air mass tables and
     * approximation formula. Applied Optics 28 (22), pp. 4735-4738
     * ----------------------------------------------------------------------------
     */
    static void amass(Results pdat) {
        if (pdat.zenref > 93.0) {
            pdat.amass = (float) -1.0;
            pdat.ampress = -1.0f;
        } else {
            pdat.amass = (float) (1.0 / (Math.cos(raddeg * pdat.zenref)
                    + 0.50572 * Math.pow((96.07995 - pdat.zenref), -1.6364)));

            pdat.ampress = pdat.amass * pdat.press / 1013.0f;
        }
    }

    /*
     * ============================================================================ Local
     * Void function prime
     *
     * Prime and Unprime Prime converts Kt to normalized Kt', etc. Unprime deconverts Kt'
     * to Kt, etc. Perez, R., P. Ineichen, Seals, R., & Zelenka, A. 1990. Making full use
     * of the clearness index for parameterizing hourly insolation conditions. Solar
     * Energy 45 (2), pp. 111-114
     * ----------------------------------------------------------------------------
     */
    static void prime(Results pdat) {
        pdat.unprime = (float) (1.031 * Math.exp(-1.4 / (0.9 + 9.4 / pdat.amass)) + 0.1);
        pdat.prime = (float) (1.0 / pdat.unprime);
    }

    /*
     * ============================================================================ Local
     * Void function etr
     *
     * Extraterrestrial (top-of-atmosphere) solar irradiance
     * ----------------------------------------------------------------------------
     */
    static void etr(Results pdat) {
        if (pdat.coszen > 0.0) {
            pdat.etrn = pdat.solcon * pdat.erv;
            pdat.etr = pdat.etrn * pdat.coszen;
        } else {
            pdat.etrn = 0.0f;
            pdat.etr = 0.0f;
        }
    }

    /*
     * ============================================================================ Local
     * Void function localtrig
     *
     * Does trig on internal variable used by several functions
     * ----------------------------------------------------------------------------
     */
    static void localtrig(Results pdat, trigdata tdat) {
        /* define masks to prevent calculation of uninitialized variables */

        int SD_MASK = (L_ZENETR | L_SSHA | S_SBCF | S_SOLAZM);
        int SL_MASK = (L_ZENETR | L_SSHA | S_SBCF | S_SOLAZM);
        int CL_MASK = (L_ZENETR | L_SSHA | S_SBCF | S_SOLAZM);
        int CD_MASK = (L_ZENETR | L_SSHA | S_SBCF);
        int CH_MASK = (L_ZENETR);

        if (tdat.sd < -900.0) /* sd was initialized -999 as flag */
        {
            tdat.sd = 1.0f; /* reflag as having completed calculations */
            if ((pdat.function | CD_MASK) != 0)
                tdat.cd = (float) Math.cos(raddeg * pdat.declin);
            if ((pdat.function | CH_MASK) != 0)
                tdat.ch = (float) Math.cos(raddeg * pdat.hrang);
            if ((pdat.function | CL_MASK) != 0)
                tdat.cl = (float) Math.cos(raddeg * pdat.latitude);
            if ((pdat.function | SD_MASK) != 0)
                tdat.sd = (float) Math.sin(raddeg * pdat.declin);
            if ((pdat.function | SL_MASK) != 0)
                tdat.sl = (float) Math.sin(raddeg * pdat.latitude);
        }
    }

    /*
     * ============================================================================ Local
     * Void function tilt
     *
     * ETR on a tilted surface
     * ----------------------------------------------------------------------------
     */
    static void tilt(Results pdat) {
        float ca; /* cosine of the solar azimuth angle */
        float cp; /* cosine of the panel aspect */
        float ct; /* cosine of the panel tilt */
        float sa; /* sine of the solar azimuth angle */
        float sp; /* sine of the panel aspect */
        float st; /* sine of the panel tilt */
        float sz; /* sine of the refraction corrected solar zenith angle */

        /*
         * Cosine of the angle between the sun and a tipped flat surface, useful for
         * calculating solar energy on tilted surfaces
         */
        ca = (float) Math.cos(raddeg * pdat.azim);
        cp = (float) Math.cos(raddeg * pdat.aspect);
        ct = (float) Math.cos(raddeg * pdat.tilt);
        sa = (float) Math.sin(raddeg * pdat.azim);
        sp = (float) Math.sin(raddeg * pdat.aspect);
        st = (float) Math.sin(raddeg * pdat.tilt);
        sz = (float) Math.sin(raddeg * pdat.zenref);
        pdat.cosinc = pdat.coszen * ct + sz * st * (ca * cp + sa * sp);

        if (pdat.cosinc > 0.0)
            pdat.etrtilt = pdat.etrn * pdat.cosinc;
        else
            pdat.etrtilt = 0.0f;

    }

    /*
     * ============================================================================ Void
     * function S_decode
     *
     * This function decodes the error codes from S_solpos return value
     *
     * Requires the long integer return value from S_solpos
     *
     * Returns descriptive text to stderr
     * ----------------------------------------------------------------------------
     */
    static void S_decode(long code, Results pdat) {
        if ((code & (1L << S_YEAR_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the year: [1950-2050]" + pdat.year);
        if ((code & (1L << S_MONTH_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the month: " + pdat.month);
        if ((code & (1L << S_DAY_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the day-of-month:" + pdat.day);
        if ((code & (1L << S_DOY_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the day-of-year: " + pdat.daynum);
        if ((code & (1L << S_HOUR_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the hour: " + pdat.hour);
        if ((code & (1L << S_MINUTE_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the minute: " + pdat.minute);
        if ((code & (1L << S_SECOND_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the second: " + pdat.second);
        if ((code & (1L << S_TZONE_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the time zone: " + pdat.timezone);
        if ((code & (1L << S_INTRVL_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the interval: " + pdat.interval);
        if ((code & (1L << S_LAT_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the latitude: " + pdat.latitude);
        if ((code & (1L << S_LON_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the longitude: " + pdat.longitude);
        if ((code & (1L << S_TEMP_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the temperature: " + pdat.temp);
        if ((code & (1L << S_PRESS_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the pressure: " + pdat.press);
        if ((code & (1L << S_TILT_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the tilt: " + pdat.tilt);
        if ((code & (1L << S_ASPECT_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the aspect: " + pdat.aspect);
        if ((code & (1L << S_SBWID_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the shadowband width: " + pdat.sbwid);
        if ((code & (1L << S_SBRAD_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the shadowband radius: " + pdat.sbrad);
        if ((code & (1L << S_SBSKY_ERROR)) != 0)
            throw new KlabException("S_decode ==> Please fix the shadowband sky factor: "
                    + pdat.sbsky);
    }
    
    public static void main(String[] args) {
        Results pdat = new Results();
        long retval;

        /*
         * Initialize structure to default values. (Optional only if ALL input parameters
         * are initialized in the calling code, which they are not in this example.)
         */

        S_init(pdat);

        /* I use Atlanta, GA for this example */

        pdat.longitude = -84.43f; /* Note that latitude and longitude are */
        pdat.latitude = 33.65f; /* in DECIMAL DEGREES, not Deg/Min/Sec */
        pdat.timezone = -5.0f; /*
                                * Eastern time zone, even though longitude would suggest
                                * Central. We use what they use. DO NOT ADJUST FOR
                                * DAYLIGHT SAVINGS TIME.
                                */

        pdat.year = 1999; /* The year is 1999. */
        pdat.daynum = 203; /*
                            * July 22nd, the 203'rd day of the year (the algorithm will
                            * compensate for leap year, so you just count days). S_solpos
                            * can be configured to accept month-day dates; see examples
                            * below.)
                            */

        /* The time of day (STANDARD time) is 9:45:37 */

        pdat.hour = 9;
        pdat.minute = 45;
        pdat.second = 37;

        /*
         * Let's assume that the temperature is 27 degrees C and that the pressure is 1006
         * millibars. The temperature is used for the atmospheric refraction correction,
         * and the pressure is used for the refraction correction and the
         * pressure-corrected airmass.
         */

        pdat.temp = 27.0f;
        pdat.press = 1006.0f;

        /*
         * Finally, we will assume that you have a flat surface facing southeast, tilted
         * at latitude.
         */

        pdat.tilt = pdat.latitude; /* Tilted at latitude */
        pdat.aspect = 135.0f; /* 135 deg. = SE */

        System.out.println("\n");
        System.out.println("***** TEST S_solpos: *****\n");
        System.out.println("\n");

        retval = S_solpos(pdat); /* S_solpos function call */
        S_decode(retval, pdat); /* ALWAYS look at the return code! */

        /* Now look at the results and compare with NREL benchmark */
        System.out.println("Note that your final decimal place values may vary");
        System.out.println("based on your computer's floating-point storage and your");
        System.out.println("compiler's mathematical algorithms.  If you agree with");
        System.out.println("NREL's values for at least 5 significant digits, assume it works.\n");
        System.out.println("Note that S_solpos has returned the day and month for the");
        System.out.println("input daynum.  When configured to do so, S_solpos will reverse");
        System.out.println("this input/output relationship, accepting month and day as");
        System.out.println("input and returning the day-of-year in the daynum variable.");
        System.out.println();
        System.out.println("NREL    . 1999.07.22, daynum 203, retval 0, amass 1.335752, ampress 1.326522");
        System.out.println("NREL    . azim 97.032875, cosinc 0.912569, elevref 48.409931");
        System.out.println("NREL    . etr 989.668518, etrn 1323.239868, etrtilt 1207.547363");
        System.out.println("NREL    . prime 1.037040, sbcf 1.201910, sunrise 347.173431");
        System.out.println("NREL    . sunset 1181.111206, unprime 0.964283, zenref 41.590069");
        System.out.println("SOLTEST . " + pdat);

        /**********************************************************************/
        /*
         * S_solpos configuration examples using the function parameter.
         * 
         * Selecting a minimum of functions to meet your needs may result in faster
         * execution. A factor of two difference in execution speed exists between S_GEOM
         * (the minimum configuration) and S_ALL (all variables calculated). [S_DOY is
         * actually the simplest and fastest configuration by far, but it only does the
         * date conversions and bypasses all solar geometry.] If speed is not a
         * consideration, use the default S_ALL configuration implemented by the call to
         * S_init.
         * 
         * The bitmasks are defined in S_solpos.h.
         */

        /* 1) Calculate the refraction corrected solar position variables */

        pdat.function = S_REFRAC;

        /* 2) Calculate the shadow band correction factor */

        pdat.function = S_SBCF;

        /*
         * 3) Select both of the above functions (Note that the two bitmasks are 'or-ed'
         * together to produce the desired results):
         */

        pdat.function = (S_REFRAC | S_SBCF);

        /*
         * 4) Modify the above configuration for accepting month and day rather than
         * day-of-year. Note that S_DOY (which controls on the day-of-year interpretation)
         * must be inverted, then 'and-ed' with the other function codes to turn the
         * day-of-year OFF. With the day-of-year bit off, S_solpos expects date input in
         * the form of month and day.
         */

        pdat.function = ((S_REFRAC | S_SBCF) & ~S_DOY);
        pdat.month = 7;
        pdat.day = 22;

        /*
         * Also note that S_DOY is the only function that you should attempt to clear in
         * this manner: Other function bitmasks are a composite of more than one mask,
         * which represents an interdependency among functions. Turning off unexpected
         * bits will produce unexpected results. If in the course of your program you need
         * fewer parameters calculated, you should rebuild the function mask from zero
         * using only the required function bitmasks.
         */

        /*
         * 5) Switch back to day-of-year in the above configuration by 'or-ing' with the
         * bitmask
         */

        pdat.function |= S_DOY;
        pdat.month = 10; /* Now ignore ridiculous month and day */
        pdat.day = 3; /* and overwrite them with correct values */

        /* ... and back to month-day again, etc.: */

        pdat.function &= ~S_DOY;

        /*
         * To see the effects of different configurations, move the above lines of code to
         * just before the call to S_solpos and examine the results. Note that some of the
         * returned parameters are undefined if the configuration doesn't specify that
         * they be calculated. Thus, you must keep track of what you're calculating if you
         * stray from the S_ALL default configuration.
         */

        /**********************************************************************/
        /*
         * Looking at the S_solpos return code
         * 
         * In the return code, each bit represents an error in the range of individual
         * input parameters. See the bit definition in S_solpos.h for the position of each
         * error flag.
         * 
         * To assure that none of your input variables are out of bounds, the calling
         * program should always look at the S_solpos return code. In this example, the
         * function S_decode fulfills that mandate by examining the code and writing an
         * interpretation to the standard error output.
         * 
         * To see the effect of an out of bounds parameter, move the following line to
         * just before the call to S_solpos:
         */

        pdat.year = 2009; /* will S_solpos accept a two-digit year? */

        /*
         * This causes S_decode to output a descriptive line regarding the value of the
         * input year. [This algorithm is valid only between years 1950 and 2050; hence,
         * explicit four-digit years are required. If your dates are in a two-digit
         * format, S_solpos requires that you make a conversion to an explicit four-digit
         * year.]
         * 
         * S_decode (located in the solpos.c file) can serve as a template for building
         * your own decoder to handle errors according to the requirements of your calling
         * application.
         */

        /***********************************************************************/
        /* Accessing the individual functions */

        /*
         * S_solpos was designed to calculate the output variables using the documented
         * input variables. However, as a matter of advanced programming convenience, the
         * individual functions within S_solpos are accessible to the calling program
         * through the use of the primative L_ masks (these are different from the
         * composite S_ masks used above). However, USE THESE WTTH CAUTION since the
         * calling program must supply ALL parameters required by the function. Because
         * many of these variables are otherwise carefully created internally by S_solpos,
         * the individual functions may not have bounds checking; hence your calling
         * program must do all validation on the function input parameters. By the same
         * reasoning, the return error code (retval) may not have considered all relevant
         * input values, leaving the function vulnerable to computational errors or an
         * abnormal end condition.
         * 
         * As with the S_ masks above, the function variable is set to the L_ mask. L_
         * masks may be ORed if desired.
         * 
         * The solpos.h file contains a list of all output and transition variables, the
         * reqired L_ mask, and all variables necessary for the calculation within
         * individual functions.
         * 
         * For example, the following code seeks only the amass value. It calls only the
         * airmass routine, which requires nothing but refracted zenith angle and
         * pressure. Normally, the refracted zenith angle is a calculation that depends on
         * many other functions within S_solpos. But here using the L_ mask, we can simply
         * set the refracted zenith angle externally and call the airmass function.
         */

        pdat.function = L_AMASS; /* call only the airmass function */
        pdat.press = 1013.0f; /* set your own pressure */

        /* set up for the output of this example */
        System.out.println("Raw airmass loop:");
        System.out.println("NREL    . 37.92  5.59  2.90  1.99  1.55  1.30  1.15  1.06  1.02  1.00");
        System.out.println("SOLTEST . ");

        /* loop through a number of externally-set refracted zenith angles */
        for (pdat.zenref = 90.0f; pdat.zenref >= 0.0; pdat.zenref -= 10.0f) {
            retval = S_solpos(pdat); /* call solpos */
            S_decode(retval, pdat); /* retval may not be valid */
            System.out.println("   " + pdat.amass); /* print out the airmass */
        }
        System.out.println();
    }

}
