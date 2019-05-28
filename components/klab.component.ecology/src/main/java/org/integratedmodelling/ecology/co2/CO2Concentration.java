package org.integratedmodelling.ecology.co2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;

/**
 * All-in-one package that will return the mean sea level CO2 concentration at any time since 1980, interpolating
 * NOAA published monthly data with a cubic spline.
 * 
 * Data from http://www.esrl.noaa.gov/gmd/ccgg/trends/global.html#global_data. The static file is copied to the
 * classpath and not downloaded. Remember to update the file in main/resources/data with new observations every 
 * now and then.
 * 
 * TODO add scenarios and the ability of moving beyond current.
 * TODO take file from network and resort to data in classpath if retrieval fails. File is at 
 *      ftp://aftp.cmdl.noaa.gov/products/trends/co2/co2_mm_gl.txt.
 * 
 * @author Ferd
 *
 */
public class CO2Concentration {
    
    private static SplineInterpolator interpolator;
    private static PolynomialSplineFunction function;
    private static boolean initialized = false;
    
    public static boolean initialize() {
        
        initialized = true;
        
        List<Pair<Long,Double>> points = new ArrayList<>();
        
        try (InputStream inp = CO2Concentration.class.getResourceAsStream("/data/co2_mm_gl.txt")) {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(inp));
            String line = reader.readLine();
            while(line != null){
                readLine(line, points);
                line = reader.readLine();
            }           
        } catch (IOException e) {
            return false;
        }

        double[] x = new double[points.size()];
        double[] y = new double[points.size()];
        int i = 0;
        for (Pair<Long,Double> point : points) {
                x[i] = (double)point.getFirst();
                y[i] = point.getSecond();
                i++;
        }

        interpolator = new SplineInterpolator();
        function = interpolator.interpolate(x, y);
        
        return true;
    }
    
    private static void readLine(String line, List<Pair<Long,Double>> points) {
        
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }
        
        String[] data = line.trim().split("\\s+");
        
        int year = Integer.parseInt(data[0]);
        int month = Integer.parseInt(data[1]);
        double mean = Double.parseDouble(data[3]);
        
        DateTime date = new DateTime(year, month, 1, 0, 0);
        
        points.add(new Pair<>(date.getMillis(), mean));
        
//        System.out.println(date.getMillis() + " = " + year + "/" + month + ": " + mean);
    }

    /**
     * Get the CO2 concentration in ppm at the passed time. The time should be in milliseconds (since
     * epoch) and start at or after Jan 1st, 1980. See notice above for source. 
     * 
     * 
     * @param time
     * @return
     */
    public static double getCo2Concentration(long time) {
        
        if (!initialized) {
            initialize();
        }
        
        if (function != null) {
            
            double value = function.value((double)time);
            
            /*
             * TODO scenarios, extrapolation etc
             */
            
            return value;
        }
        
        Logging.INSTANCE.warn("initialization of CO2 data failed: returning constant value of 365 ppm");
        
        return 365;
    }
    
    public static void main(String[] args) {
        
        initialize();
        
        System.out.println("318207600000 = 1980/2: 339.15 -> " + getCo2Concentration(318207600000l));
        System.out.println("809906400000 = 1995/9: 357.9 -> " + getCo2Concentration(809906400000l));
        System.out.println("1298934000000 = 2011/3: 391.46 -> " + getCo2Concentration(1298934000000l));
        System.out.println("1443650400000 = 2015/10: 398.6 -> " + getCo2Concentration(1443650400000l));
        
    }
    
}
