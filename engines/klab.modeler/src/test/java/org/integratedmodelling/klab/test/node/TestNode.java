package org.integratedmodelling.klab.test.node;

import java.util.Date;

import org.integratedmodelling.klab.data.rest.resources.IdentityReference;
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

    // node name: a true node will read a certificate
    public static final String NODE_NAME = "testnode";
    // node base URL
    public static final String NODE_URL = "http://127.0.0.1:8281/klab";
    // node identity for responses: same as above
    public static final IdentityReference PARTNER_IDENTITY = new IdentityReference("The Semantic Web of BS",
            "dont.email@imfake.com", new Date());

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
