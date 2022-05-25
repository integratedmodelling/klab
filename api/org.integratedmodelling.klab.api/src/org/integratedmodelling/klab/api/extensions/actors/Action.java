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
package org.integratedmodelling.klab.api.extensions.actors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.integratedmodelling.kactors.api.IKActorsValue;

/**
 * Defines an action for Java-specified behaviors in the actor system.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Action {

    /**
     * ID of the action. Must be unique within the behavior, and it pays to choose names that are
     * unlikely to be duplicated in other Java behaviors, so that they can be used as "system" verbs
     * without need to disambiguate.
     * 
     * @return component id
     */
    String id();

    /**
     * List of project or component IDs that this one depends on.
     * 
     * @return id of projects or components we need
     */
    String[] requires() default {};

    /**
     * Descriptions should always be given and curated as they percolate to the k.Actors editor
     * 
     * @return
     */
    String description() default "";

    /**
     * The type of values this action can fire. No value means that fire actions won't be accepted.
     * Identifiers will be accepted only if this contains 1+ values.
     * 
     * @return
     */
    IKActorsValue.Type[] fires() default {};

    /**
     * If this is set to false, the action will not be synchronized even in a synchronous scope. Use
     * for event handlers that must run in scripts and tests. Setting this properly is <em>very</em>
     * important: if a "listener" action is used in a test or script and its synchronization flag is
     * true, execution will block until something is fired, which is likely never.
     * 
     * @return
     */
    boolean synchronize() default true;

}
