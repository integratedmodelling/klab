package org.integratedmodelling.klab.components.geospace.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.data.IGeometry;
import org.integratedmodelling.kim.api.data.IGeometry.Dimension;
import org.integratedmodelling.kim.utils.Range;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.processing.Terrain;
import org.integratedmodelling.klab.exceptions.KlabException;

public class TerrainService implements IResolver<IDataArtifact>, IExpression {

  public TerrainService() {}

  @Override
  public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
    return new TerrainService();
  }

  @Override
  public IGeometry getGeometry() {
    return Geometry.create("S2");
  }

  @Override
  public IDataArtifact resolve(IDataArtifact ret, IComputationContext context) {

    Range range = context.get("range", new Range(0., 4000., false, true));
    long[] xy = context.getScale().shape(Dimension.Type.SPACE);
    Terrain terrain = new Terrain(context.get("detail", 8), context.get("roughness", 0.55),
        range.getLowerBound(), range.getUpperBound());

    double dx = 1.0 / xy[0];
    double dy = 1.0 / xy[1];
    for (long x = 0; x < xy[0]; x++) {
      for (long y = 0; y < xy[1]; y++) {
        ret.set(context.getScale().at(Dimension.Type.SPACE, x, y), terrain.getAltitude(dx * x, dy * y));
      }
    }

    return ret;
  }

}
