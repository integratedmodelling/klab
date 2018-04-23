/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab;

import org.integratedmodelling.kim.api.monitoring.IMessageBus;
import org.integratedmodelling.kim.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.kim.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.services.ILoggingService;
import org.integratedmodelling.klab.common.monitoring.Message;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

/**
 * The Enum Logging.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public enum Logging implements ILoggingService {

    INSTANCE;

    private Logger logger;
    private IMessageBus messageBus;
    private IIdentity rootIdentity;

    private Logging() {
        logger = (Logger) LoggerFactory.getLogger(this.getClass());
    }

    /** {@inheritDoc} */
    @Override
    public void info(Object... o) {

        String payload = NotificationUtils.getMessage(o);

        if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().isGreaterOrEqual(Level.INFO)) {
            messageBus.post(Message.create(MessageClass.LOGGING, Type.INFO, rootIdentity, payload));
        }

        if (Configuration.INSTANCE.getLoggingLevel().isGreaterOrEqual(Level.INFO)) {
            if (logger != null) {
                logger.info(payload);
            } else {
                System.err.println("INFO: " + payload);
            }
        }

    }

    /** {@inheritDoc} */
    @Override
    public void warn(Object... o) {

        String payload = NotificationUtils.getMessage(o);

        if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().isGreaterOrEqual(Level.WARN)) {
            messageBus.post(Message.create(MessageClass.LOGGING, Type.WARNING, rootIdentity, payload));
        }

        if (Configuration.INSTANCE.getLoggingLevel().isGreaterOrEqual(Level.WARN)) {

            if (logger != null) {
                logger.warn(payload);
            } else {
                System.err.println("WARN: " + payload);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void error(Object... o) {

        String payload = NotificationUtils.getMessage(o);

        if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().isGreaterOrEqual(Level.ERROR)) {
            messageBus.post(Message.create(MessageClass.LOGGING, Type.ERROR, rootIdentity, payload));
        }

        if (Configuration.INSTANCE.getLoggingLevel().isGreaterOrEqual(Level.ERROR)) {
            if (logger != null) {
                logger.error(payload);
            } else {
                System.err.println("ERROR: " + payload);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void debug(Object... o) {

        String payload = NotificationUtils.getMessage(o);

        if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().isGreaterOrEqual(Level.DEBUG)) {
            messageBus.post(Message.create(MessageClass.LOGGING, Type.DEBUG, rootIdentity, payload));
        }

        if (Configuration.INSTANCE.getLoggingLevel().isGreaterOrEqual(Level.DEBUG)) {

            if (logger != null) {
                logger.debug(payload);
            } else {
                System.err.println("WARN: " + payload);
            }
        }
    }

    public void setMessageBus(IMessageBus mbus) {
        this.messageBus = mbus;
    }

    public void setRootIdentity(IIdentity identity) {
        this.rootIdentity = identity;
    }

}
