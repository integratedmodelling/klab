package org.integratedmodelling.klab.components.geospace.services;

import java.util.Map;

import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.raw.IStorage;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public class TerrainService implements IResolver<IStorage<?>>, IExpression {

    public TerrainService() {}
    
    public TerrainService(Map<String, Object> parameters, IMonitor monitor, Context context) {
        // TODO Auto-generated constructor stub
    }

    @Override
    public IStorage<?> resolve(IStorage<?> observation, IComputationContext context) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
            throws KlabException {
        return new TerrainService(parameters, monitor, context);
    }

    @Override
    public IGeometry getGeometry() {
        return Geometry.create("S2");
    }

}
