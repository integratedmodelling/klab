package org.integratedmodelling.klab.api.cli;

public interface IConsole {

    public static enum Type {
        Console, Debugger
    }

    /**
     * Execute a command line and return an output to print. Returning null won't cause any output.
     * 
     * @param command
     * @return
     */
    String executeCommand(String command);
}
