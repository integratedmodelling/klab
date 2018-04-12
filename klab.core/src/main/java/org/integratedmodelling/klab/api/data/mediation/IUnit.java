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
package org.integratedmodelling.klab.api.data.mediation;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.services.IUnitService;

/**
 * Units of measurement. Creation and inquiry methods are provided by {@link IUnitService}.
 * 
 * @author Ferd
 *
 */
public interface IUnit extends IValueMediator {

    /**
     * Return a new unit multiplied by the passed one.
     * 
     * @param unit
     * @return a new product unit
     */
    IUnit multiply(IUnit unit);

    /**
     * Return a new unit divided by the passed one.
     * 
     * @param unit
     * @return a new unit
     */
    IUnit divide(IUnit unit);

    /**
     * Return a new unit scaled according to the passed double.
     * 
     * @param scale
     * @return a new unit
     */
    IUnit scale(double scale);

}
