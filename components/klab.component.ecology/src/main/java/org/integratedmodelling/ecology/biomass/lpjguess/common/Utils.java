package org.integratedmodelling.ecology.biomass.lpjguess.common;

public class Utils {

    public static double variation_coefficient(double data[], int n) {
        // 0 and 1 will give division with zero.
        if (n > 1) {
            double avg, dev = 0, varcoe = 0, sum = 0;
            int i;
            double std = 0;

            for (i = 0; i < n; i++)
                sum += data[i];
            avg = Math.abs(sum / n);
            for (i = 0; i < n; i++)
                dev += (data[i] - avg) * (data[i] - avg);
            std = Math.sqrt(Math.abs(dev / (n - 1)));

            if (std > 0 && avg > 0) // check that data appear in the array
                varcoe = std / avg;

            return varcoe;
        } else
            return -1.0;
    }

    /**
     * Mean of data in passed vector starting at given position.
     *
     * @param data
     * @param start
     * @return mean
     */
    public static double mean_from(double[] data, int start) {
        double sum = 0.0;

        for (int i = start; i < data.length; i++) {
            sum += data[i];
        }

        return sum / (data.length - start);
    }

    /**
     * Mean of data in passed vector starting at given position.
     *
     * @param data
     * @param start
     * @return mean
     */
    public static double mean_from_to(double[] data, int start, int end) {
        double sum = 0.0;

        for (int i = start; i < end; i++) {
            sum += data[i];
        }

        return sum / (end - start);
    }

    public static class REGRes {
        public REGRes(double d, double e) {
            slope = d;
            intercept = e;
        }

        public double slope;
        public double intercept;
    }

    /**
     * Linear regression of y vs x
     *
     * @param x
     * @param y
     * @return regression
     */
    public static REGRes regress(double[] x, double[] y) {
        int i;
        double sx;
        double sy;
        double sxx;
        double sxy;
        double delta;

        sx = 0.0;
        sy = 0.0;
        sxx = 0.0;
        sxy = 0.0;
        for (i = 0; i < x.length; i++) {
            sx += x[i];
            sy += y[i];
            sxx += x[i] * x[i];
            sxy += x[i] * y[i];
        }
        delta = x.length * sxx - sx * sx;
        return new REGRes((sxx * sy - sx * sxy) / delta, (x.length * sxy - sx * sy) / delta);
    }

    public static boolean negligible(double dval) {
        final double EPSILON = 1.0e-30;
        if (dval > EPSILON) {
            return false;
        }
        if (dval < 0.0 && dval < -EPSILON) {
            return false;
        }
        return true;
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // RESPIRATION TEMPERATURE RESPONSE
    // Called by canopy exchange and soil organic matter dynamics module to
    // calculate
    // response of respiration to temperature
    public static double respiration_temperature_response(double temp, double gtemp) {

        // DESCRIPTION
        // Calculates g(T), response of respiration rate to temperature (T),
        // based on
        // empirical relationship for temperature response of soil temperature
        // across
        // ecosystems, incorporating damping of Q10 response due to temperature
        // acclimation (Eqn 11, Lloyd & Taylor 1994)
        //
        // r = r10 * g(t)
        // g(T) = EXP [308.56 * (1 / 56.02 - 1 / (T - 227.13))] (T in Kelvin)

        // INPUT PARAMETER
        // temp = air or soil temperature (deg C)

        // OUTPUT PARAMETER
        // gtemp = respiration temperature response

        if (temp >= -40.0) {
            gtemp = Math.exp(308.56 * (1.0 / 56.02 - 1.0 / (temp + 46.02))); // NB:
            // temperature
            // in
            // deg
            // C
        } else {
            gtemp = 0.0;
        }

        return gtemp;
    }

    /// Step n days from a date.
    public static int stepfromdate(int day, int step) {

        if (day < 0) // a negative value should not be a valid day
            return -1;
        else if (day + step > 0)
            return (day + step) % 365;
        else if (day + step < 0)
            return day + step + 365;
        else
            return 0;
    }

    /// Query whether a date is within a period spanned by two dates.
    public static boolean dayinperiod(int day, int start, int end) {

        boolean acrossnewyear = false;

        if (day < 0 || start < 0 || end < 0) // a negative value should not be a valid day
            return false;

        if (start > end)
            acrossnewyear = true;

        if (day >= start && day <= end && !acrossnewyear || (day >= start || day <= end) && acrossnewyear)
            return true;
        else
            return false;
    }

    /// Calculates the Julian start and end day of a month.
    /** January 1st is set to 0.
     */
    public static void monthdates(int start, int end, int month) {
        int months[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        start = 0;
        int m = 0;
        while (m < month) {
            start += months[m];
            m++;
        }
        end = start + months[m] - 1;
    }
}
