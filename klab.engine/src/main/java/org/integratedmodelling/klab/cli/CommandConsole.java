package org.integratedmodelling.klab.cli;

import org.integratedmodelling.klab.api.cli.IConsole;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.cli.ConsoleCommandProvider.Command;
import org.integratedmodelling.klab.engine.runtime.Session;

/**
 * Engine peer for a command console. Will refactor the CLI methods in klab.tools for generalization and
 * adaptation to the command console logics integrated in k.Explorer.
 * 
 * @author Ferd
 *
 */
public class CommandConsole implements IConsole {

    ISession session;
    
    public CommandConsole(Session session) {
        this.session = session;
    }

    @Override
    public String executeCommand(String command) {
        Command cmd = ConsoleCommandProvider.INSTANCE.processCommand(command, null);
        if (cmd == null) {
            return "ERROR: command unrecognized: \"" + command + "\"";
        }
        Object o = cmd.executor.execute(cmd.call, this.session);
        return o == null ? null : printObject(o);
    }

    private String printObject(Object o) {
        // TODO Auto-generated method stub
        return o.toString();
    }
    
}
