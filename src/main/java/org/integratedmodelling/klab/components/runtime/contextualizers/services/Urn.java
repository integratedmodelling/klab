package org.integratedmodelling.klab.components.runtime.contextualizers.services;

import java.util.Map;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Produce a contextualizer that will retrieve a URN-specified resource and produce 
 * an artifact from it.
 * 
 * @author Ferd
 *
 */
public class Urn implements IExpression {

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
