package org.integratedmodelling.klab.components.geospace.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

public class Space implements IExpression {

    @Override
    public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {

        Shape shape = null;
        Double resolution = null;
        org.integratedmodelling.klab.components.geospace.extents.Space ret = null;

        if (parameters.containsKey("shape")) {
            shape = Shape.create(parameters.get("shape").toString());
        }
        if (parameters.containsKey("grid")) {
            resolution = parseResolution(parameters.get("grid").toString());
        }

        if (shape != null) {
            if (resolution != null) {
                ret = org.integratedmodelling.klab.components.geospace.extents.Space.create(shape, resolution);
            } else {
                ret = org.integratedmodelling.klab.components.geospace.extents.Space.create(shape);
            }
        }

        // TODO Auto-generated method stub

        return ret;
    }

    /**
     * Parse a string like "1 km" and return the meters in it. Throw an exception if this cannot be
     * parsed.
     * 
     * @param string
     * @return the resolution in meters
     * @throws KlabValidationException
     */
    public static double parseResolution(String string) throws KlabValidationException {

        Pair<Double, String> pd = MiscUtilities.splitNumberFromString(string);

        if (pd.getFirst() == null || pd.getSecond() == null)
            throw new KlabValidationException("wrong resolution specification: " + string);

        IUnit uu = Units.INSTANCE.getUnit(pd.getSecond());
        IUnit mm = Units.INSTANCE.getUnit("m");
        return mm.convert(pd.getFirst().doubleValue(), uu).doubleValue();
    }

}
