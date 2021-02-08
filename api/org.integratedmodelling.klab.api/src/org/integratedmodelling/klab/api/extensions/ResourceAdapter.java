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
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;

/**
 * Used on a {@link IResourceAdapter resource adapter class} to declare a new
 * resource type. The information in this annotation is used to validate
 * resources using this adapter before they are processed by the adapter itself.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ResourceAdapter {

	/**
	 * ID of the component. Must be unique, please use unambiguous paths like
	 * package or project names.
	 * 
	 * @return component id
	 */
	String type();

	/**
	 * Version string
	 * 
	 * @return version string
	 */
	String version();

	/**
	 * Subtypes enable the definition of adapter types with a common lineage that
	 * are different enough to require different parameters.
	 * 
	 * @return
	 */
	String[] subtypes() default {};

	/**
	 * Any required type-specific parameters names can be added here. If the fields
	 * listed here are not present in the {@link IResource#getParameters()
	 * parameters} of a {@link IResource resource} with this type, the resource is
	 * invalid and cannot be used.
	 * 
	 * @return the required parameter names
	 * @deprecated superseded by KDL declaration of contract
	 */
	String[] requires() default {};

	/**
	 * Any optional type-specific parameters names can be added here. Same as
	 * {@link #requires()} but does not cause invalidation when not present. All
	 * parameters in a resource must be declared in the annotation.
	 * 
	 * @return the optional parameter names
	 * @deprecated superseded by KDL declaration of contract
	 */
	String[] optional() default {};

	/**
	 * If this is true, the adapter is a candidate to handle a dropped file in the
	 * modeler. The final use of the adapter will depend on the result of calling
	 * {@link IResourceValidator#canHandle(java.io.File, org.integratedmodelling.kim.api.IParameters)}
	 * on the validator.
	 * 
	 * @return
	 */
	boolean handlesFiles();

	/**
	 * If this is true, a resource of the handled type can be created from scratch
	 * by filling in a form. Otherwise it won't appear in the "New resource" adapter
	 * list.
	 * 
	 * @return
	 */
	boolean canCreateEmpty();

}
