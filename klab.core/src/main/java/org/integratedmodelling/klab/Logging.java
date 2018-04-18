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

import org.integratedmodelling.klab.api.services.ILoggingService;
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

	private Logging() {
		logger = (Logger) LoggerFactory.getLogger(this.getClass());
	}

	/** {@inheritDoc} */
	@Override
	public void info(Object... o) {
		if (logger != null) {
			logger.info(NotificationUtils.getMessage(o));
		} else {
			System.err.println("INFO: " + NotificationUtils.getMessage(o));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void warn(Object... o) {
		if (logger != null) {
			logger.warn(NotificationUtils.getMessage(o));
		} else {
			System.err.println("WARN: " + NotificationUtils.getMessage(o));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void error(Object... o) {
		if (logger != null) {
			logger.error(NotificationUtils.getMessage(o));
		} else {
			System.err.println("WARN: " + NotificationUtils.getMessage(o));
		}
	}

	/** {@inheritDoc} */
	@Override
	public void debug(Object... o) {
		if (logger != null) {
			logger.debug(NotificationUtils.getMessage(o));
		} else {
			System.err.println("WARN: " + NotificationUtils.getMessage(o));
		}
	}

}
