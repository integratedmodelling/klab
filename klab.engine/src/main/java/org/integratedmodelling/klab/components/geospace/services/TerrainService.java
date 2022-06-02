package org.integratedmodelling.klab.components.geospace.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.processing.Terrain;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Range;

public class TerrainService extends AbstractContextualizer implements IResolver<IDataArtifact>, IExpression {

	public TerrainService() {
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new TerrainService();
	}

	@Override
	public IDataArtifact resolve(IDataArtifact ret, IContextualizationScope context) {

		Range range = context.get("range", new Range(0., 4000., false, false));
		long[] xy = context.getScale().getDimension(Dimension.Type.SPACE).shape();
		Terrain terrain = new Terrain(context.get("detail", 8), context.get("roughness", 0.55), range.getLowerBound(),
				range.getUpperBound());

		double dx = 1.0 / xy[0];
		double dy = 1.0 / xy[1];
		for (long x = 0; x < xy[0]; x++) {
			for (long y = 0; y < xy[1]; y++) {
				ret.set(context.getScale().at(Dimension.Type.SPACE, x, y), terrain.getAltitude(dx * x, dy * y));
			}
		}

		return ret;
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

}
