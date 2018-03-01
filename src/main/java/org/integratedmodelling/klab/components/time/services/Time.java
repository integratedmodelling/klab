package org.integratedmodelling.klab.components.time.services;

import java.util.Map;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public class Time implements IExpression {

    @Override
    public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
            throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IGeometry getGeometry() {
      return Geometry.create("T1");
    }

}
