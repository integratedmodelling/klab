package org.integratedmodelling.klab;

import org.integratedmodelling.klab.api.services.IRuntimeService;

/**
 * Runtime would be a better name for this, but it makes it hard to code as it conflicts
 * with Java's Runtime which is imported by default.
 * 
 * @author ferdinando.villa
 *
 */
public enum Klab implements IRuntimeService {
    
    INSTANCE;
    
    // logging
    @Override
    public void info(Object o) {
//        if (clogger != null) {
//            clogger.info(o);
//        } else if (logger != null) {
//            logger.info(o);
//        } else {
//            System.err.println("INFO: " + o);
//        }
    }

    @Override
    public void warn(Object o) {
//        if (clogger != null) {
//            clogger.warn(o);
//        } else if (logger != null) {
//            logger.warn(o);
//        } else {
//            System.err.println("WARN: " + o);
//        }
    }

    @Override
    public void error(Object o) {
//        if (clogger != null) {
//            clogger.error(o);
//        } else if (logger != null) {
//            logger.error(o);
//        } else {
//            System.err.println("ERROR: " + o);
//        }
    }

    @Override
    public void debug(Object o) {
//        if (CONFIG.isDebug()) {
//            if (clogger != null) {
//                clogger.debug(o);
//            } else if (logger != null) {
//                logger.debug(o);
//            } else {
//                System.err.println("DEBUG: " + o);
//            }
//        }
    }

    @Override
    public void warn(String string, Throwable e) {
//        if (clogger != null) {
//            clogger.warn(string, e);
//        } else if (logger != null) {
//            logger.warn(string, e);
//        } else {
//            System.err.println("WARN: " + MiscUtilities.throwableToString(e));
//        }
    }

    @Override
    public void error(String string, Throwable e) {
//        if (clogger != null) {
//            clogger.error(string, e);
//        } else if (logger != null) {
//            logger.error(string, e);
//        } else {
//            System.err.println("ERROR: " + MiscUtilities.throwableToString(e));
//        }
    }

    // listener for tying in to the Kim validator and code generator
    
    // functions
    
    // URNs
}
