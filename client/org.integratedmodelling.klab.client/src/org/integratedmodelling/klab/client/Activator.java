package org.integratedmodelling.klab.client;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class Activator extends Plugin {

    private static final Logger logger = LoggerFactory.getLogger(Activator.class);
    @Override
    public void start(BundleContext context) throws Exception {
        
        Dictionary<String,String> props = new Hashtable<>();
        props.put("service.exported.interfaces", "*");
        props.put("service.exported.configs","ecf.generic.server");
        super.start(context);
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();  
        LoggerContext loggerContext = (LoggerContext) iLoggerFactory;  
        loggerContext.reset();  
        JoranConfigurator configurator = new JoranConfigurator();  
        configurator.setContext(loggerContext);  
        try {  
          configurator.doConfigure(getClass().getResourceAsStream("/logback.xml"));
          logger.info("Logback for client configured");
        } catch (JoranException e) {
            // log will not work, but we continue
            System.err.println(e);  
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        super.stop(context);
    }

}
