/*******************************************************************************
 * Copyright (C) 2007, 2014:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other authors listed
 * in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular, collaborative,
 * integrated development of interoperable data and model components. For details, see
 * http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the Affero
 * General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without even the
 * implied warranty of merchantability or fitness for a particular purpose. See the Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. The license
 * is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.extensions;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Exposes a contextualizer implementation to the k.LAB engine by linking it to a KDL specification for its
 * interface. When used with no argument, implies that a KDL file with the same path name as the class' is
 * available in the class path. Otherwise the file can be named as the string argumenty using its classpath.
 * 
 * A name and namespace may be passed to override any in the KDL root actuator.
 * 
 * The annotated class must extend IContextualizer.
 * 
 * @author ferdinando.villa
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Contextualizer {

    /**
     * The classpath location of the KDL contract. If missing, it must match the class
     * that defines the contextualizer.
     * 
     * @return the classpath of the contract specification.
     */
    String value() default "";

    /**
     * Pass a name to override the one in KDL if the same contract is being reused
     * for different contextualizers.
     * 
     * @return the name of this contextualizer
     */
    String name() default "";

    /**
     * Pass a package name to override the one in KDL if the same contract is being reused
     * for different contextualizers.
     * 
     * @return the namespace for this contextualizer
     */
    String namespace() default "";

}
