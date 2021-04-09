package org.integratedmodelling.klab.cli;

import org.integratedmodelling.klab.api.cli.IConsole;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.Session;

/**
 * Engine peer for a command console. Will refactor the CLI methods in klab.tools for generalization and
 * adaptation to the command console logics integrated in k.Explorer.
 * 
 * @author Ferd
 *
 */
public class DebuggerConsole implements IConsole {

    ISession session;
    
    public DebuggerConsole(Session session) {
        this.session = session;
    }

    @Override
    public String executeCommand(String command) {
        return null;
    }
    
}
