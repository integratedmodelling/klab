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

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;

/**
 * Describes any object that can mediate a value to another. In k.LAB mediations are allowed to
 * bypass geometries, aggregating or propagating as needed.
 * 
 * @author Ferd
 *
 */
public interface IValueMediator {

    /**
     * true if this can be converted into other. Do not throw exceptions.
     * 
     * @param other
     * @throws KlabIllegalStateException if this mediator was produced through
     *         {@link #contextualize(IObservable, IScale)}.
     * @return true if other is compatible
     */
    boolean isCompatible(IValueMediator other);

    /**
     * Convert from the passed mediator to the one this represents.
     * 
     * @param d
     * @param scale
     * @return the converted number
     * @throws KlabIllegalStateException if this mediator was produced through
     *         {@link #contextualize(IObservable, IScale)}.
     */
    Number convert(Number d, IValueMediator scale);

    /**
     * Convert from this unit to the passed one. This would be trivially accomplished by inverting
     * the receiver and the argument, unless the mediator is contextualized (coming from
     * {@link #contextualize(IObservable, IScale)}, which makes it entirely non-trivial, hence the
     * existence of a backConvert() function.
     * 
     * @param d
     * @param scale
     * @throws KlabIllegalStateException if this mediator was produced through
     *         {@link #contextualize(IObservable, IScale)}.
     * @return
     */
    Number backConvert(Number d, IValueMediator scale);

    /**
     * Obtain a mediator that will convert quantities from the mediator of the observable (unit or
     * currency) into what we represent, and will require the scale portion over which the
     * observation of the value is made to account for context.
     * <p>
     * The resulting mediator will only accept the {@link #convert(Number, ILocator)} call and throw
     * an exception for any other situation. If the observable passed has no mediator, the
     * conversion will be standard and non-contextual (using a simple conversion factor for speed).
     * Otherwise, the fastest set of transformations will be encoded in the returned mediator.
     * 
     * @param observable
     * @param scale
     * @return a contextualized mediator specialized for the conversion to this unit in this scale.
     */
    IValueMediator contextualize(IObservable observable, IScale scale);

    /**
     * Convert a quantity to the unit we represent from the one in the observable that was passed to
     * the {@link #contextualize(IObservable, IScale)} call that originated this one.
     * 
     * @param value
     * @param locator
     * @throws KlabIllegalStateException if this is called on a mediator that was not produced
     *         through {@link #contextualize(IObservable, IScale)}.
     * @return
     */
    Number convert(Number value, ILocator locator);

}
