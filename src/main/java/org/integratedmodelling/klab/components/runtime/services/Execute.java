package org.integratedmodelling.klab.components.runtime.services;

import java.util.Map;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Expression executor service. Used to compile arbitrary expression code into a dataflow. Delegates to another 
 * expression, with potential conditions and context passed at runtime.
 * 
 * @author Ferd
 *
 */
public class Execute implements IExpression {
    
    @Override
    public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
            throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

}
