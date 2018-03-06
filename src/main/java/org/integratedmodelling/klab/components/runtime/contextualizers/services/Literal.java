package org.integratedmodelling.klab.components.runtime.contextualizers.services;

import java.util.Map;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Produce a trivial contextualizer that will contextualize a literal value, passed in
 * the 'val' parameter.
 * 
 * @author Ferd
 *
 */
public class Literal implements IExpression {

    @Override
    public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
            throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGeometry getGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

}
