package org.integratedmodelling.klab.clitool.api;

import org.integratedmodelling.klab.api.runtime.ISession;

/**
 * 
 * 
 * @author ferdinando.villa
 *
 */
public interface IConsole extends Interactive {

    void error(Object e);

    void warning(Object e);

    void info(Object e, String infoClass);

    void echo(Object string);

    void outputResult(String input, Object ret);

    void reportCommandResult(String input, boolean ok);

    ISession getSession();

    /**
     * Return the currently selected command package (main by default).
     * @return command package
     */
    String getCurrentPackage();

    void setPrompt(String s);

}
