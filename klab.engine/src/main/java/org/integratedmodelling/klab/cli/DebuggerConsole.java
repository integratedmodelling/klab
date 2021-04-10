package org.integratedmodelling.klab.cli;

import org.integratedmodelling.klab.api.cli.IConsole;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.debugger.Debugger;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.ConsoleNotification;

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
    private String id;
    
    public DebuggerConsole(String id, Session session) {
        
        this.session = session;
        this.id = id;
        this.debugger = new Debugger(session) {

            @Override
            protected void setTitle(String string) {
                // TODO specific message
                println("TITLE: " + string);
            }

            @Override
            protected void print(String string) {
                buffer.append(string);
            }

            @Override
            protected void println(String string) {
                buffer.append(string);
                DebuggerConsole.this.println(buffer.toString());
                buffer.setLength(0);
            }
            
        };
    }
    
    public Debugger getDebugger() {
        return debugger;
    }

    @Override
    public String executeCommand(String command) {
        buffer.setLength(0);
        debugger.accept(command);
        return buffer.toString();
    }

    @Override
    public void println(String string) {
        ConsoleNotification response = new ConsoleNotification();
        response.setConsoleId(this.id);
        response.setPayload(string);
        session.getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.CommandResponse, response);
    }
    
}
