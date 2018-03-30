package org.integratedmodelling.klab.api.data.mediation;

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
