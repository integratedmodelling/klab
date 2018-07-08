package org.integratedmodelling.klab.clitool.api;

/**
 * Simple interface to be adopted by interactive UIs and passed to command handlers through
 * the getInteractiveUI() method in IServiceCall.
 * 
 * @author ferdinando.villa
 *
 */
public interface Interactive {

    public static interface CommandListener {

        Object execute(String input);
    }

    /**
     * Grab the command line, deactivating the command executor and simply returning commands typed
     * until the end command is typed.
     * 
     * @param prompt the alternative prompt to use during grab mode
     * @param endCommand the command that will exit this mode.
     */
    public void grabCommandLine(String prompt, String endCommand, CommandListener listener);
}
