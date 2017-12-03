package org.integratedmodelling.klab.api.runtime.monitoring;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IServerIdentity;

/**
 * A monitor can be engine-level, session-level, context-level or task-level.
 * 
 * @author ferdinando.villa
 *
 */
public interface IMonitor {

    /**
     * For info to be seen by users: pass a string. Will also take an exception, but usually exceptions
     * shouldn't turn into warnings. These will be reported to the user unless the verbosity is set low. Do
     * not abuse of these - there should be only few, really necessary info messages so that things do not get
     * lost. 
     * 
     * In addition to the main object, you can pass a string that will be interpreted as the info message
     * class. The class parameter is used by the client to categorize messages so they can be shown in special
     * ways and easily identified in a list of info messages. Other objects can also be sent along with the
     * message, according to implementation.
     * 
     * @param info
     * @param infoClass
     * 
     */
    void info(Object... info);

    /**
     * Pass a string. Will also take an exception, but usually exceptions shouldn't turn into warnings. These
     * will be reported to the user unless the verbosity is set lowest.
     * 
     * @param o
     */
    void warn(Object... o);

    /**
     * Pass a string or an exception (usually the latter as a reaction to an exception in the execution).
     * These will interrupt execution from outside, so you should return after raising one of these.
     * 
     * In addition, you can pass a statement to communicate errors in k.IM, or other objects that can be
     * sent and used as necessary.
     * 
     * @param o
     */
    void error(Object... o);

    /**
     * Any message that is just for you or is too verbose to be an info message should be sent as debug, which
     * is not shown by default unless you enable a higher verbosity. Don't abuse of these - it's never cheap
     * or good to show hundreds of messages even when testing.
     * 
     * @param o
     */
    void debug(Object... o);

    /**
     * This is to send out serializable objects or other messages. Information sent through this channel will
     * not be seen by users but may have side effects; for example, contexts and tasks created are sent this
     * way to the client.
     * 
     * @param o
     */
    void send(Object o);

    /**
     * A monitor always operates and reports on behalf of some runtime identity, usually with an
     * {@link IServerIdentity} for the top-level monitors.
     * 
     * @return
     */
    IIdentity getIdentity();

    /**
     * Tells us that errors have happened in the context we're monitoring.
     * 
     * @return true if errors have happened in this context of monitoring.
     */
    boolean hasErrors();

}
