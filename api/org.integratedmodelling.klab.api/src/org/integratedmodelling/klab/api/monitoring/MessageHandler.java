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
package org.integratedmodelling.klab.api.monitoring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage.MessageClass;

/**
 * Used on a {@link IIdentity identity implementation} to handle a message with
 * a specified payload type. The payload type must be specified as parameter in
 * the method that is annotated.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageHandler {

	/**
	 * Class of the payload this method reacts to. The method can have a parameter
	 * of that class which will be set to the payload when a message is received by
	 * the implementing identity. Fields of {@link MessageClass},
	 * {@link IMessage.Type} and {@link Date} will also be automatically set, if
	 * present, to the class, type and send date of the message.
	 * 
	 * @return component id
	 */
	Class<?> value();

}
