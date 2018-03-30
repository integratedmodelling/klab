package org.integratedmodelling.klab.clitool.api;

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

    void setPrompt(String s);

}
