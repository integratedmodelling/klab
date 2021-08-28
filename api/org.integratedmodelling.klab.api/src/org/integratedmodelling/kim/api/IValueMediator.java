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
     * Convert from this unit to the passed one. This is trivial unless the unit is contextualized,
     * which makes it entirely non-trivial.
     * 
     * @param d
     * @param scale
     * @return
     */
    Number backConvert(Number d, IValueMediator scale);

    /**
     * Obtain a target mediator representing this one, pre-contextualized to the passed scale, so
     * that it can accept contextually compatible mediators at
     * {@link #convert(Number, IValueMediator)} and handle them appropriately. The mediator passed
     * to convert called on the result must be compatible <em>once the context is factored in</em>;
     * this means that, for example, mm will be compatible with m^3 if the scale is distributed in
     * space, making "mm" nothing more than mm^3/mm^2 and generating the appropriate conversion
     * factors automatically.
     * <p>
     * The scale is cached in the mediator and, for extensive values, used to transform the result
     * as needed, so the result can only be reused across scale swaps on <em>regular</em> extents.
     * On irregular extents, the original, uncontextualized mediator <em>must</em> be saved and
     * contextualized at every step.
     * 
     * @param observable
     * @param scale
     * @return
     */
    IValueMediator contextualize(IObservable observable, IScale scale);

}
