package org.integratedmodelling.klab.cli;

import org.integratedmodelling.klab.api.cli.IConsole;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.cli.ConsoleCommandProvider.Command;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.ConsoleNotification;

/**
 * Engine peer for a command console. Will refactor the CLI methods in klab.tools for generalization and
 * adaptation to the command console logics integrated in k.Explorer.
 * 
 * @author Ferd
 *
 */
public class CommandConsole implements IConsole {

    ISession session;
    String id;
    
    public CommandConsole(String id, Session session) {
        this.session = session;
        this.id = id;
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

    @Override
    public void println(String string) {
        ConsoleNotification response = new ConsoleNotification();
        response.setConsoleId(this.id);
        response.setPayload(string);
        session.getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.CommandResponse, response);
    }
    
}
