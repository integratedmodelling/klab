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
package org.integratedmodelling.klab.api.runtime.monitoring;

import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.services.IRuntimeService;

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
public interface IMonitor extends IChannel {

    interface Listener {
        void notifyRootContext(ISubject subject);
    }


    /**
     * Like {@link #send(Object...)} but returns a message future for the response.
     *
     * @param message anything that may be sent as a message: either a preconstructed
     *        {@link IMessage} or the necessary info to build one, including a {@link MessageClass}
     *        and {@IMessage.Type} along with any payload (any serializable object). Sending a
     *        {@link INotification} should automatically promote it to a suitable logging message.
     * 
     * @return a future, or null if there is no message bus.
     */
    Future<IMessage> ask(Object... message);

    /**
     * A monitor always operates and reports on behalf of some runtime identity, usually with an
     * {@link org.integratedmodelling.klab.api.auth.IServerIdentity} for the top-level monitors.
     *
     * @return the running identity, e.g. a session, task or engine.
     */
    IIdentity getIdentity();

}
