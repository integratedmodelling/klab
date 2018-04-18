package org.integratedmodelling.klab.test.web;

import javax.annotation.PreDestroy;

import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * This will start an engine at / with the default security config.
 * 
 * @author ferdinando.villa
 * 
 */
@Component
@EnableAutoConfiguration
@ComponentScan(
        basePackages = { "org.integratedmodelling.klab.engine.rest.security",
                "org.integratedmodelling.klab.engine.rest.controllers.base",
                "org.integratedmodelling.klab.engine.rest.controllers.engine",
                "org.integratedmodelling.klab.engine.rest.controllers.network",
                "org.integratedmodelling.klab.engine.rest.controllers.resources" })
public class Modeler {

    Engine engine;
    
    public void run(String[] args) {
        EngineStartupOptions options = new EngineStartupOptions();
        options.setCertificateResource("testengine.cert");
        options.initialize(args);
        engine = Engine.start(options);
        SpringApplication.run(Modeler.class, options.getArguments());
    }
    
    @PreDestroy
    public void shutdown() {
        engine.stop();
    }

    public static void main(String args[]) {
        System.setProperty("spring.config.name", "testengine");
        new Modeler().run(args);
    }

}
