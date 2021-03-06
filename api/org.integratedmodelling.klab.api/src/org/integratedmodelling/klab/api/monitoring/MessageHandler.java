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

import org.integratedmodelling.klab.api.services.IAuthenticationService;

/**
 * Used on any class to make its instances able to handle a message with
 * a specified payload type. In the engine, any identity accessible to 
 * the {@link IAuthenticationService} is automatically subscribed; in 
 * clients, objects must be subscribed manually. A payload type 
 * (recognizable based on package or some other means) must be specified
 * as parameter in the method that is annotated. Type and message class
 * can be used as additional filters.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MessageHandler {

    /**
     * If passed, annotation will apply only to messages with passed class.
     * 
     * @return
     */
    IMessage.MessageClass messageClass() default IMessage.MessageClass.Void;

    /**
     * If passed, annotation will apply only to messages with passed type.
     * 
     * @return
     */
    IMessage.Type type() default IMessage.Type.Void;

}
