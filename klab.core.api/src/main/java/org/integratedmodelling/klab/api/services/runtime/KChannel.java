/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.services.runtime;

import java.util.function.Consumer;

import javax.sound.midi.Receiver;

/**
 * A monitor represents the context of computation and is used to establish the current identity as
 * well as to send messages to any subscribers. Monitors may be root-level, engine-level,
 * session-level, context-level or task-level.
 * <p>
 * The API meant to operate within tasks normally gets a monitor as a parameter in each call. If
 * not, the root monitor can be {@link IRuntimeService#getRootMonitor() obtained from the runtime}.
 * <p>
 * An important function of the monitor is to obtain the current identity that owns the computation.
 * This is done through {@link #getIdentity()}. From that, any other identity (such as the network
 * session, the engine etc., up to the node and partner that owns the engine) can be obtained.
 * <p>
 * 
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface KChannel {

    /**
     * For info to be seen by users: pass a string. Will also take an exception, but usually
     * exceptions shouldn't turn into warnings. These will be reported to the user unless the
     * verbosity is set low. Do not abuse of these - there should be only few, really necessary info
     * messages so that things do not get lost.
     *
     * In addition to the main object, you can pass a string that will be interpreted as the info
     * message class. The class parameter is used by the client to categorize messages so they can
     * be shown in special ways and easily identified in a list of info messages. Other objects can
     * also be sent along with the message, according to implementation.
     *
     * @param info
     * @param infoClass
     */
    void info(Object... info);

    /**
     * Pass a string. Will also take an exception, but usually exceptions shouldn't turn into
     * warnings. These will be reported to the user unless the verbosity is set lowest.
     *
     * @param o a {@link java.lang.Object} object.
     */
    void warn(Object... o);

    /**
     * Pass a string or an exception (usually the latter as a reaction to an exception in the
     * execution). These will interrupt execution from outside, so you should return after raising
     * one of these.
     *
     * In addition, you can pass a statement to communicate errors in k.IM, or other objects that
     * can be sent and used as necessary.
     *
     * @param o a {@link java.lang.Object} object.
     */
    void error(Object... o);

    /**
     * Any message that is just for you or is too verbose to be an info message should be sent as
     * debug, which is not shown by default unless you enable a higher verbosity. Don't abuse of
     * these - it's never cheap or good to show hundreds of messages even when testing.
     *
     * @param o a {@link java.lang.Object} object.
     */
    void debug(Object... o);

    /**
     * This is to send out serializable objects or other messages through any {@link IMessageBus}
     * registered with the runtime. Information sent through this channel will only be received by
     * {@link Receiver receivers} that have subscribed with the {@link IMessageBus} exposed by the
     * {@link IRuntimeService runtime}. The messages are signed with the monitor's
     * {@link #getIdentity() identity string}.
     *
     * @param message anything that may be sent as a message: either a preconstructed
     *        {@link KMessage} or the necessary info to build one, including a {@link MessageClass}
     *        and {@IMessage.Type} along with any payload (any serializable object). Sending a
     *        {@link KNotification} should automatically promote it to a suitable logging message.
     */
    void send(Object... message);

    /**
     * Like {@link #send(Object...)} but takes a handler to process a response when it comes.
     *
     * @param handler the handler for the response message
     * @param message anything that may be sent as a message: either a preconstructed
     *        {@link KMessage} or the necessary info to build one, including a {@link MessageClass}
     *        and {@IMessage.Type} along with any payload (any serializable object). Sending a
     *        {@link KNotification} should automatically promote it to a suitable logging message.
     * 
     * @return a future, or null if there is no message bus.
     */
    void post(Consumer<KMessage> handler, Object... message);

    /**
     * Use to communicate that a wait is necessary. The receiving end should check getWaitTime().
     * 
     * @param seconds
     */
    void addWait(int seconds);

    /**
     * When an operation that may require to wait is called, this should be checked (with a
     * client-side timeout) until it returns 0 or times out at the server side (-1).
     * 
     * @return
     */
    int getWaitTime();

    /**
     * Check if the monitored identity has been interrupted by a client action. Applies to any task,
     * such as a {@link ITask} or {@link IScript}. In other identities it will always return false.
     * 
     * @return true if interrupted
     */
    boolean isInterrupted();

    /**
     * Tells us that errors have happened in the context we're monitoring.
     *
     * @return true if errors have happened in this context of monitoring.
     */
    boolean hasErrors();

}
