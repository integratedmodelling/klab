package org.integratedmodelling.klab.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;

/**
 * Simple log appender for debugging. Simplest way to use is Debug.print(....) which
 * will append the string to $HOME/debug.txt.
 * 
 * @author Ferd
 *
 */
public class DebugFile {

    private File         fname;
    static DebugFile         _debug;

    /**
     * set this to false to neuter the logger.
     */
    static final boolean DEBUG = true;

    public DebugFile(String file, boolean append) {

        if (DEBUG) {
            this.fname = new File(file);
            if (!append && this.fname.exists()) {
                this.fname.delete();
            }
        }
    }

    public void print(String s) {

        if (DEBUG) {
            try {
                FileWriter writer = new FileWriter(fname, true);
                writer.write(s + System.getProperty("line.separator"));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new KlabException(e);
            }
        }
    }

    public static void println(String s) {

        if (DEBUG) {
            if (_debug == null) {
                try {
                    _debug = new DebugFile(System.getProperty("java.io.tmpdir") + File.separator + "debug.txt", true);
                } catch (KlabIOException e) {
                }
            }
            _debug.print(s);
        }
    }

    public static String describeValues(double[] vals) {

        if (vals == null)
            return "NULL input array";

        double min = Double.NaN;
        double max = Double.NaN;
        int total = 0, nans = 0;

        for (double d : vals) {
            if (Double.isNaN(d)) {
                nans++;
            } else {
                if (Double.isNaN(min) || min > d) {
                    min = d;
                }
                if (Double.isNaN(max) || max < d) {
                    max = d;
                }
            }
            total++;
        }

        return "#" + total + "(" + nans + " NaN): min = " + min + ", max = " + max;

    }
}