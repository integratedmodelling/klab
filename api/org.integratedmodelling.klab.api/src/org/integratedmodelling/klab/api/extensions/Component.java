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
package org.integratedmodelling.klab.api.extensions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.integratedmodelling.klab.api.extensions.component.IComponent;

/**
 * Defines a component by annotating a Java class that becomes the component's
 * initializer. The class may be empty or have methods for initialization, setup
 * and shutdown. The package that class belongs on tells k.LAB where to look for
 * services, functions, and k.DL files to load with the component. The component
 * should NOT implement {@link IComponent}: those will be created as wrappers
 * and the annotated class will be available through
 * {@link IComponent#getImplementation()}.I
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {

	/**
	 * ID of the component. Must be unique, please use unambiguous paths like
	 * package or project names.
	 * 
	 * @return component id
	 */
	String id();

	/**
	 * Version number, parseable by {@link org.integratedmodelling.klab.Version}.
	 * 
	 * @return version string
	 */
	String version();

	/**
	 * List of other project or component IDs that this one depends on.
	 * 
	 * @return id of projects or components we need
	 */
	String[] requires() default {};

	/**
	 * No need for much sophistication for the next few years, but the reference
	 * domain should be mentioned.
	 * 
	 * @return the worldview we live in. Empty default means we can live in any,
	 *         which is usually not true.
	 */
	String worldview() default "";

}
