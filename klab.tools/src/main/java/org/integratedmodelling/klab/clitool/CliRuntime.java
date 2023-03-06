package org.integratedmodelling.klab.clitool;

import java.io.IOException;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Logo;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IRuntimeService;
import org.integratedmodelling.klab.clitool.api.IConsole;
import org.integratedmodelling.klab.clitool.console.CommandProcessor;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engines.modeler.base.Modeler;

public enum CliRuntime {

    INSTANCE;
    
    Engine engine;
    Session session;
    CommandProcessor commandProcessor;
    IConsole console;
    Modeler modeler;

    public ISession getSession() {
        return session;
    }

    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public IConsole getConsole() {
        return console;
    }

    public Engine getEngine() {
        return engine;
    }

    public ISession initialize(IConsole console, CliStartupOptions options) {
        console.disableInput();
        this.engine = Engine.start(options);
        this.engine.setName(IRuntimeService.LOCAL_ENGINE_NAME);
        this.session = engine.createSession().setDefault();
        this.console = console;
        this.commandProcessor = new CommandProcessor(console, session.getMonitor());

        console.scream("\n");
        console.scream(Logo.ENGINE_BANNER);
        console.scream("\nSession established: ID is " + this.session.getId() + "\n");
        console.scream("\nLocal session browser url is: http://localhost:8283/modeler/ui/viewer?session=" + this.session.getId() + "\n");

        if (options.isNetwork()) {
            console.echo("Starting network services....\n");
            startNetwork(() -> console.scream("Network services started."));
        }
        
        return this.session;

    }

    public boolean startNetwork(Runnable callback) {
        if (modeler == null) {
            modeler = new Modeler(engine);
        }
        return modeler.startNetworkServices(callback);
    }

    public void startNetwork() {
        if (modeler == null) {
            modeler = new Modeler(engine);
        }
        modeler.startNetworkServices();
    }

    public void stopNetwork() {
        if (modeler != null) {
            modeler.stopNetworkServices();
        }
    }

    public void shutdown() {
        if (this.session != null) {
            try {
                this.session.close();
            } catch (IOException e) {
                Logging.INSTANCE.error(e);
            }
            if (this.engine != null) {
                this.engine.stop();
            }
            if (this.modeler != null) {
                this.modeler.shutdown();
            }
        }
    }

}
