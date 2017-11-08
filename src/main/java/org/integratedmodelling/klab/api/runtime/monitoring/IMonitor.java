package org.integratedmodelling.klab.api.runtime.monitoring;

import org.integratedmodelling.klab.api.runtime.IContext;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;

/**
 * A monitor can be engine-level, session-level, context-level or task-level. 
 * 
 * @author ferdinando.villa
 *
 */
public interface IMonitor {

    /**
     * For info to be seen by users: pass a string. Will also take an exception, but usually exceptions 
     * shouldn't turn into warnings.
     * These will be reported to the user unless the verbosity is set low. Do not abuse of these -
     * there should be only few, really necessary info messages so that things do not get lost. The
     * class parameter is used by the client to categorize messages so they can be shown in special
     * ways and easily identified in a list of info messages. You can leave it null or devise your own 
     * class.
     * @param info 
     * @param infoClass 
     * 
     */
    void info(Object info, String infoClass);

    /**
     * Pass a string. Will also take an exception, but usually exceptions shouldn't turn into warnings.
     * These will be reported to the user unless the verbosity is set lowest.
     * 
     * @param o
     */
    void warn(Object o);

    /**
     * Pass a string or an exception (usually the latter as a reaction to an exception in the execution). 
     * These will interrupt execution from outside, so you should return after raising one of these.
     * 
     * @param o
     */
    void error(Object o);

    /**
     * Any message that is just for you or is too verbose to be an info message should be sent as
     * debug, which is not shown by default unless you enable a higher verbosity. Don't abuse of 
     * these, either - it's still passing stuff around through REST so it's not cheap to show a 
     * hundred messages.
     *  
     * @param o
     */
    void debug(Object o);

    /**
     * This is to send out serializable objects or other messages. Information sent through
     * this channel will not be seen by users but may have side effects; for example, contexts
     * and tasks created are sent this way to the client.
     * 
     * @param o
     */
    void send(Object o);

    /**
     * Not null in session and task monitors. To see if a monitor is an
     * engine-level monitor check getSession() == null.
     *  
     * @return the monitored session, if any
     */
    ISession getSession();

    /**
     * Not null only in runtime situations where a context is being processed.
     * @return the monitored context, if any
     */
    IContext getContext();

    /**
     * Not null only in task monitors. If not null, call interrupt() on the result 
     * at either side to interrupt a running task.
     * 
     * @return the monitored task, if any
     */
    ITask getTask();

    /**
     * Tells us that errors have happened in the context we're monitoring.
     * @return true if errors have happened in this context of monitoring.
     */
    boolean hasErrors();

}
