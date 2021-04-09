package org.integratedmodelling.klab.cli;

import org.integratedmodelling.klab.api.cli.IConsole;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.debugger.Debugger;
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
    Debugger debugger;
    StringBuffer buffer = new StringBuffer(1024);
    
    public DebuggerConsole(Session session) {
        this.session = session;
        this.debugger = new Debugger(session) {

            @Override
            protected void setTitle(String string) {
                // TODO
            }

            @Override
            protected void print(String string) {
                buffer.append(string);
            }

            @Override
            protected void println(String string) {
                buffer.append(string);
                buffer.append("\n");
            }
            
        };
    }

    @Override
    public String executeCommand(String command) {
        buffer.setLength(0);
        debugger.accept(command);
        return buffer.toString();
    }
    
}
