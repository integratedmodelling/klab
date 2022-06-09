package org.integratedmodelling.klab.common.mediation;

import java.util.List;

import org.integratedmodelling.kim.api.IValueMediator;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;

/**
 * Host the scale-driven recontextualization mechanism for other mediators.
 * 
 * @author Ferd
 *
 */
public abstract class AbstractMediator implements IValueMediator {

    enum ExtentSize {
        SPACE_M, SPACE_M2, SPACE_M3, TIME_MS
    }

    enum Operation {
        MULTIPLY, DIVIDE
    }

    class Transformation {
        ExtentSize extentSize;
        Operation operation;
        double factor;
    }

    /*
     * null operations == trivial behavior
     */
    List<Operation> operations = null;
    // the value we were contextualized from
    IValueMediator toConvert;

    @Override
    public Number convert(Number value, ILocator locator) {

        if (toConvert == null) {
            throw new KlabIllegalStateException(
                    "cannot call convert(number, locator) on a mediator that was not created through contextualization");
        }

        /*
         * trivial cases: no context, intensive semantics, or original unit required no transformation
         */
        if (operations == null) {
            return this.convert(value, toConvert);
        }
        
        
        
        return null;
    }

}
