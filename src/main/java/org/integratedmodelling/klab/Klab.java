package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.IRuntimeService;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * Runtime would be a better name for this, but it makes it hard to code as it conflicts with Java's Runtime
 * which is imported by default.
 * 
 * @author ferdinando.villa
 *
 */
public enum Klab implements IRuntimeService {

    INSTANCE;

    Logger logger;

    private Klab() {

        logger = (Logger) LoggerFactory.getLogger(this.getClass());
        if (logger != null) {
            /*
             * I'd rather not do this, but 100M of debug output when nobody has ever asked for
             * it and no property files are around anywhere are a bit much to take.
             */
            logger.setLevel(Level.INFO);
        }
        setupExtensions();
    }

    @Override
    public void info(Object o) {
        if (logger != null) {
            logger.info(o.toString());
        } else {
            System.err.println("INFO: " + o);
        }
    }

    @Override
    public void warn(Object o) {
        if (logger != null) {
            logger.warn(o.toString());
        } else {
            System.err.println("WARN: " + o);
        }
    }

    @Override
    public void error(Object o) {
        if (logger != null) {
            logger.error(o.toString());
        } else {
            System.err.println("WARN: " + o);
        }
    }

    @Override
    public void debug(Object o) {
        if (logger != null) {
            logger.debug(o.toString());
        } else {
            System.err.println("WARN: " + o);
        }
    }

    @Override
    public void warn(String o, Throwable e) {
        if (logger != null) {
            logger.warn(o, e);
        } else {
            System.err.println("WARN: " + o + ": " + MiscUtilities.throwableToString(e));
        }
    }

    @Override
    public void error(String o, Throwable e) {
        if (logger != null) {
            logger.error(o, e);
        } else {
            System.err.println("WARN: " + o + ": " + MiscUtilities.throwableToString(e));
        }
    }

    // functions

    // URNs

    /*
     * Get extensions (ImageIO, Geotools) optimally configured. Most copied from
     * Geoserver's GeoserverInitStartupListener
     */
    private static void setupExtensions() {

        // if the server admin did not set it up otherwise, force X/Y axis
        // ordering
        // This one is a good place because we need to initialize this property
        // before any other opeation can trigger the initialization of the CRS
        // subsystem
        if (System.getProperty("org.geotools.referencing.forceXY") == null) {
            System.setProperty("org.geotools.referencing.forceXY", "true");
        }
        // if (Boolean.TRUE
        // .equals(Hints.getSystemDefault(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER))) {
        // Hints.putSystemDefault(Hints.FORCE_AXIS_ORDER_HONORING, "http");
        // }
        // Hints.putSystemDefault(Hints.LENIENT_DATUM_SHIFT, true);
        //
        // // setup the referencing tolerance to make it more tolerant to tiny
        // // differences
        // // between projections (increases the chance of matching a random prj
        // // file content
        // // to an actual EPSG code
        // double comparisonTolerance = DEFAULT_COMPARISON_TOLERANCE;
        //
        // // Register logging, and bridge to JAI logging
        // GeoTools.init((Hints) null);
        //
        // /*
        // * TODO make this a property and implement if it ever becomes necessary
        // */
        // // if (comparisonToleranceProperty != null) {
        // // try {
        // // comparisonTolerance =
        // // Double.parseDouble(comparisonToleranceProperty);
        // // } catch (NumberFormatException nfe) {
        // // KLAB.warn("Unable to parse the specified COMPARISON_TOLERANCE "
        // // + "system property: " + comparisonToleranceProperty +
        // // " which should be a number. Using Default: " +
        // // DEFAULT_COMPARISON_TOLERANCE);
        // // }
        // // }
        // Hints.putSystemDefault(Hints.COMPARISON_TOLERANCE, comparisonTolerance);
        //
        // /*
        // * avoid expiration of EPSG data. FIXME: does not seem to avoid anything.
        // */
        // System.setProperty("org.geotools.epsg.factory.timeout", "-1");
        //
        // /*
        // * Prevents leak ()
        // */
        // ImageIO.scanForPlugins();
        //
        // // in any case, the native png reader is worse than the pure java ones,
        // // so
        // // let's disable it (the native png writer is on the other side
        // // faster)...
        // ImageIOExt.allowNativeCodec("png", ImageReaderSpi.class, false);
        // ImageIOExt.allowNativeCodec("png", ImageWriterSpi.class, true);
        //
        // // initialize GeoTools factories so that we don't make a SPI lookup
        // // every time a
        // // factory is needed
        // Hints.putSystemDefault(Hints.FILTER_FACTORY, CommonFactoryFinder
        // .getFilterFactory2(null));
        // Hints.putSystemDefault(Hints.STYLE_FACTORY, CommonFactoryFinder
        // .getStyleFactory(null));
        // Hints.putSystemDefault(Hints.FEATURE_FACTORY, CommonFactoryFinder
        // .getFeatureFactory(null));
        //
        // final Hints defHints = GeoTools.getDefaultHints();
        //
        // // Initialize GridCoverageFactory so that we don't make a lookup every
        // // time a
        // // factory is needed
        // Hints.putSystemDefault(Hints.GRID_COVERAGE_FACTORY, CoverageFactoryFinder
        // .getGridCoverageFactory(defHints));
    }

}
