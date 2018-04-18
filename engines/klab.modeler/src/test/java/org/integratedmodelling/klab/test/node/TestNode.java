package org.integratedmodelling.klab.test.node;

import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * This will start an engine at http://127.0.0.1:8281/klab with mock node authentication and
 * default config.
 * 
 * @author ferdinando.villa
 * 
 */
@Component
@EnableAutoConfiguration
@ComponentScan(
        basePackages = { "org.integratedmodelling.klab.test.node.auth",
                "org.integratedmodelling.klab.engine.rest.security",
                "org.integratedmodelling.klab.engine.rest.controllers.base",
                "org.integratedmodelling.klab.engine.rest.controllers.engine",
                "org.integratedmodelling.klab.engine.rest.controllers.network",
                "org.integratedmodelling.klab.engine.rest.controllers.resources" })
public class TestNode {

    public void run(String[] args) {
        EngineStartupOptions options = new EngineStartupOptions();
        options.setCertificateResource("testnode.cert");
        options.initialize(args);
        Engine engine = Engine.start(options);
        SpringApplication.run(TestNode.class, options.getArguments());
        engine.stop();
    }

    public static void main(String args[]) {
        System.setProperty("spring.config.name", "testnode");
        new TestNode().run(args);
    }

}
