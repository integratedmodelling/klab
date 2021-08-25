/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.kim.api;

import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;

/**
 * Describes any object that can mediate a value to another.
 * 
 * @author Ferd
 *
 */
public interface IValueMediator {

    /**
     * true if this can be converted into other. Do not throw exceptions.
     * 
     * @param other
     * @return true if other is compatible
     */
    boolean isCompatible(IValueMediator other);

    /**
     * Convert from the passed unit TO the unit we represent.
     * 
     * @param d
     * @param scale
     * @return the converted number
     */
    Number convert(Number d, IValueMediator scale);

    /**
     * Convert from the passed unit to the one we represent, allowing mediation of extent based on
     * the passed scope. So for example "mm" can be converted to m^3 as long as the scope implies an
     * areal distribution. There is no check that this is semantically legitimate for the specific
     * quantity represented, so any validation of that kind must be done upstream.
     * 
     * @param d
     * @param from
     * @param scope
     * @return
     */
    Number convert(Number d, IObservable observable, IValueMediator from, IScale scale);

    /**
     * Return a multiplicative factor to adapt a value in the "from" unit to the passed scale,
     * considering the extension or intension over the context embodied in the units. If there is 
     * no conformant match between either units or geometries, the return value should be Double.NaN.
     * 
     * @param from
     * @param scale
     * @return
     */
    double getContextualizationFactor(IObservable observable, IValueMediator from, IScale scale);

}
