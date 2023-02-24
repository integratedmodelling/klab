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
package org.integratedmodelling.klab.api.data.mediation;

import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.KLocator;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.observation.scale.KScale;

/**
 * Describes any object that can mediate a value to another. In k.LAB mediations are allowed to
 * bypass geometries, aggregating or propagating as needed.
 * 
 * @author Ferd
 *
 */
public interface KValueMediator {

    /**
     * true if this can be converted into other. Do not throw exceptions.
     * 
     * @param other
     * @throws KIllegalStateException if this mediator was produced through
     *         {@link #contextualize(KObservable, KScale)}.
     * @return true if other is compatible
     */
    boolean isCompatible(KValueMediator other);

    /**
     * Convert from the passed mediator to the one this represents.
     * 
     * @param d
     * @param scale
     * @return the converted number
     * @throws KIllegalStateException if this mediator was produced through
     *         {@link #contextualize(KObservable, KScale)}.
     */
    Number convert(Number d, KValueMediator scale);

    /**
     * Obtain a mediator that will convert quantities from the mediator of the observable (unit or
     * currency) into what we represent, using the scale portion over which the observation of the
     * value is made to account for any different distribution through the context.
     * <p>
     * The resulting mediator will only accept the {@link #convert(Number, KLocator)} call and throw
     * an exception in any other situation. If the observable passed has no mediator, the conversion
     * will be standard and non-contextual (using a simple conversion factor for speed). Otherwise,
     * the fastest set of transformations will be encoded in the returned mediator.
     * <p>
     * The resulting mediator will perform correcly <em>only</em> when used with locators coming
     * from the same scale that was used to produce it. It will contain transformations in
     * parametric form, so that the possible irregularity of the extents in the locators is
     * accounted for.
     * <p>
     * The strategy to create the necessary transformations, consisting in parametric
     * multiplications or divisions by an appropriately transformed extent in space and/or time, is
     * as follows:
     * <ol>
     * <li>if observable is intensive, just check compatibility and return self if compatible, throw
     * exception if not. Otherwise:
     * <li>obtain contextualized candidate forms of both the observable's base unit and self. Both
     * should have one compatible form in the candidates. If not, throw exception. If the compatible
     * form is the same, proceed as in (1). Otherwise:
     * <li>devise two strategies to mediate 1) incoming form to base form and 2) base form to this.
     * Each strategy consists of a list of parametric operations on S/T contexts with a conversion
     * factor for the basic representation (m^x for space, ms for time).
     * <li>simplify the two strategies into a single set of operations to add to the contextualized
     * unit returned, which also carries the definition of the contextual nature re: S/T and a
     * string explaining the transformations made and why.
     * </ol>
     * 
     * @param observable
     * @param scale
     * @return a contextualized mediator specialized for the conversion to this unit in this scale.
     */
    KValueMediator contextualize(KObservable observable, KGeometry scale);

    /**
     * Convert a quantity to the unit we represent from the one in the observable that was passed to
     * the {@link #contextualize(KObservable, KScale)} call that originated this one.
     * 
     * @param value
     * @param locator
     * @throws KIllegalStateException if this is called on a mediator that was not produced
     *         through {@link #contextualize(KObservable, KScale)}.
     * @return
     */
    Number convert(Number value, KLocator locator);

    /**
     * If true, this mediator is meant to be used across scales using
     * {@link #convert(Number, KLocator)}, otherwise it's a pure non-contextual mediator.
     * 
     * @return
     */
    boolean isContextual();

}
