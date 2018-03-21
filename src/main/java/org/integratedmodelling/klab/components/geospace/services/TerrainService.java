package org.integratedmodelling.klab.components.geospace.services;

import java.util.Map;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension;
import org.integratedmodelling.kim.model.Geometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.raw.IDataArtifact;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.processing.Terrain;
import org.integratedmodelling.klab.exceptions.KlabException;

public class TerrainService implements IResolver<IDataArtifact>, IExpression {

    public TerrainService() {}
    
    @Override
    public Object eval(Map<String, Object> parameters, IMonitor monitor, Context context)
            throws KlabException {
        return new TerrainService();
    }

    @Override
    public IGeometry getGeometry() {
        return Geometry.create("S2");
    }

    @Override
    public IDataArtifact resolve(IDataArtifact ret, IComputationContext context, IScale locator) {
      
      long[] xy = locator.shape(Dimension.Type.SPACE);
      Terrain terrain = new Terrain(context.get("detail", Integer.class), context.get("roughness", Double.class));
      double dx = 1.0/xy[0];
      double dy = 1.0/xy[1];
      for (long x = 0; x < xy[0]; x++) {
        for (long y = 0; y < xy[1]; y++) {
          ret.set(locator.at(Dimension.Type.SPACE, x, y), terrain.getAltitude(dx*x, dy*y));
        }
      }
      return ret;
    }

}
