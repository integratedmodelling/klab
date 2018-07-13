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

import java.util.function.Consumer;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;
import org.integratedmodelling.klab.api.monitoring.IMessage.Type;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.services.ILoggingService;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.slf4j.LoggerFactory;

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

	Consumer<String> infoWriter = (message) -> System.out.println("INFO: " + message);
	Consumer<String> warningWriter = (message) -> System.err.println("WARN: " + message);
	Consumer<String> errorWriter = (message) -> System.err.println("ERROR: " + message);
	Consumer<String> debugWriter = (message) -> System.err.println("DEBUG: " + message);

	private Logging() {
		logger = (Logger) LoggerFactory.getLogger(this.getClass());
	}

	/** {@inheritDoc} */
    @Override
    public void info(Object... o) {

        String payload = NotificationUtils.getMessage(o);

        if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.INFO.intValue()) {
            messageBus.post(Message.create(rootIdentity.getId(), MessageClass.Notification, Type.Info, payload));
        }

        if (Configuration.INSTANCE.getLoggingLevel().intValue() >= Level.INFO.intValue()) {
            if (infoWriter != null) {
            	infoWriter.accept(payload);
            }
            if (logger != null) {
                logger.info(payload);
            } 
        }

    }

	/** {@inheritDoc} */
	@Override
	public void warn(Object... o) {

		String payload = NotificationUtils.getMessage(o);

		if (messageBus != null
				&& Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.WARNING.intValue()) {
			messageBus.post(Message.create(rootIdentity.getId(), MessageClass.Notification, Type.Warning, payload));
		}

		if (Configuration.INSTANCE.getLoggingLevel().intValue() >= Level.WARNING.intValue()) {
			if (warningWriter != null) {
            	warningWriter.accept(payload);
            }
			if (logger != null) {
				logger.warn(payload);
            } 
		}
	}

	/** {@inheritDoc} */
	@Override
	public void error(Object... o) {

		String payload = NotificationUtils.getMessage(o);

		if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().intValue() <= Level.SEVERE.intValue()) {
			messageBus.post(Message.create(rootIdentity.getId(), MessageClass.Notification, Type.Error, payload));
		}

		if (Configuration.INSTANCE.getNotificationLevel().intValue() <= Level.SEVERE.intValue()) {
			if (errorWriter != null) {
            	errorWriter.accept(payload);
            }
			if (logger != null) {
				logger.error(payload);
            } 
		}
	}

	/** {@inheritDoc} */
	@Override
	public void debug(Object... o) {

		String payload = NotificationUtils.getMessage(o);

		if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.FINE.intValue()) {
			messageBus.post(Message.create(rootIdentity.getId(), MessageClass.Notification, Type.Debug, payload));
		}

		if (Configuration.INSTANCE.getNotificationLevel().intValue() <= Level.FINE.intValue()) {
			if (debugWriter != null) {
            	debugWriter.accept(payload);
            }
			if (logger != null) {
				logger.debug(payload);
            } 
		}
	}

	public void setMessageBus(IMessageBus mbus) {
		this.messageBus = mbus;
	}

	public void setRootIdentity(IIdentity identity) {
		this.rootIdentity = identity;
	}

	public Consumer<String> getInfoWriter() {
		return infoWriter;
	}

	public void setInfoWriter(Consumer<String> infoWriter) {
		this.infoWriter = infoWriter;
	}

	public Consumer<String> getWarningWriter() {
		return warningWriter;
	}

	public void setWarningWriter(Consumer<String> warningWriter) {
		this.warningWriter = warningWriter;
	}

	public Consumer<String> getErrorWriter() {
		return errorWriter;
	}

	public void setErrorWriter(Consumer<String> errorWriter) {
		this.errorWriter = errorWriter;
	}

	public Consumer<String> getDebugWriter() {
		return debugWriter;
	}

	public void setDebugWriter(Consumer<String> debugWriter) {
		this.debugWriter = debugWriter;
	}
	
}
