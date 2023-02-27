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
package org.integratedmodelling.klab.logging;

import java.util.function.Consumer;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.identities.KIdentity;
import org.integratedmodelling.klab.api.services.runtime.KMessage;
import org.integratedmodelling.klab.api.services.runtime.KMessage.MessageClass;
import org.integratedmodelling.klab.api.services.runtime.KMessageBus;
import org.integratedmodelling.klab.api.services.runtime.KNotification;
import org.integratedmodelling.klab.api.services.runtime.impl.Message;
import org.integratedmodelling.klab.api.utils.Utils;
import org.integratedmodelling.klab.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Enum Logging.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public enum Logging  {

	INSTANCE;

	private Logger logger;
	private KMessageBus messageBus;
	private KIdentity rootIdentity;

	Consumer<String> infoWriter = (message) -> System.out.println("INFO: " + message);
	Consumer<String> warningWriter = (message) -> System.err.println("WARN: " + message);
	Consumer<String> errorWriter = (message) -> System.err.println("ERROR: " + message);
	Consumer<String> debugWriter = (message) -> System.err.println("DEBUG: " + message);

	private Logging() {
		try {
			logger = (Logger) LoggerFactory.getLogger(this.getClass());
		} catch (Throwable e) {
			System.err.println("--------------------------------------------------------------------------------------------------");
			System.err.println("Error initializing logger: please spend your entire life checking dependencies and excluding jars");
			System.err.println("--------------------------------------------------------------------------------------------------");
		}
	}

	public void info(Object... o) {

		Pair<String, KNotification.Type> payload = Utils.Notifications.getMessage(o);

		if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.INFO.intValue()) {
			messageBus.post(Message.create(rootIdentity.getId(), MessageClass.Notification, KMessage.Type.Info,
					payload.getFirst(), payload.getSecond()));
		}

		if (Configuration.INSTANCE.getLoggingLevel().intValue() >= Level.INFO.intValue()) {
			if (infoWriter != null) {
				infoWriter.accept(payload.getFirst());
			}
			if (logger != null) {
				logger.info(payload.getFirst());
			}
		}

	}

	public void warn(Object... o) {

		Pair<String, KNotification.Type> payload = Utils.Notifications.getMessage(o);

		if (messageBus != null
				&& Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.WARNING.intValue()) {
			messageBus.post(Message.create(rootIdentity.getId(), KMessage.MessageClass.Notification, KMessage.Type.Warning,
					payload.getFirst(), payload.getSecond()));
		}

		if (Configuration.INSTANCE.getLoggingLevel().intValue() >= Level.WARNING.intValue()) {
			if (warningWriter != null) {
				warningWriter.accept(payload.getFirst());
			}
			if (logger != null) {
				logger.warn(payload.getFirst());
			}
		}
	}

	public void error(Object... o) {

        Pair<String, KNotification.Type> payload = Utils.Notifications.getMessage(o);

		if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().intValue() <= Level.SEVERE.intValue()) {
			messageBus.post(Message.create(rootIdentity.getId(), MessageClass.Notification, KMessage.Type.Error,
					payload.getFirst(), payload.getSecond()));
		}

		if (Configuration.INSTANCE.getNotificationLevel().intValue() <= Level.SEVERE.intValue()) {
			if (errorWriter != null) {
				errorWriter.accept(payload.getFirst());
			}
			if (logger != null) {
				logger.error(payload.getFirst());
			}
		}
	}

	public void debug(Object... o) {

        Pair<String, KNotification.Type> payload = Utils.Notifications.getMessage(o);

		if (messageBus != null && Configuration.INSTANCE.getNotificationLevel().intValue() >= Level.FINE.intValue()) {
			messageBus.post(Message.create(rootIdentity.getId(), MessageClass.Notification, KMessage.Type.Debug,
					payload.getFirst(), payload.getSecond()));
		}

		if (Configuration.INSTANCE.getNotificationLevel().intValue() <= Level.FINE.intValue()) {
			if (debugWriter != null) {
				debugWriter.accept(payload.getFirst());
			}
			if (logger != null) {
				logger.debug(payload.getFirst());
			}
		}
	}

	public void setMessageBus(KMessageBus mbus) {
		this.messageBus = mbus;
	}

	public void setRootIdentity(KIdentity identity) {
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
