package org.integratedmodelling.geoprocessing.morphology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.hortonmachine.hmachine.modules.geomorphology.geomorphon.OmsGeomorphon;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class GeomorphonResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

	double pRadius = Double.NaN;
	double pThreshold = Double.NaN;

//	@Override
//	public IGeometry getGeometry() {
//		return Geometry.create("S2");
//	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public IState resolve(IState target, IContextualizationScope context) throws KlabException {

		IState dem = context.getArtifact("elevation", IState.class);
		ISpace space = target.getSpace();

		if (!(target.isSpatiallyDistributed() && space.isRegular())) {
			throw new KlabValidationException("Geomorphological classification must be computed on a grid extent");
		}

		// default radius is 1/100th of the diagonal of the context
		if (Double.isNaN(pRadius) || pRadius == 0) {
			pRadius = Math.sqrt(
						Math.pow(space.getStandardizedWidth(), 2) + 
						Math.pow(space.getStandardizedHeight(), 2))
					/ 100.0;
		}
		
		double cMeters = (space.getEnvelope().getMaxX() - space.getEnvelope().getMinX())/space.getStandardizedWidth();
		
		OmsGeomorphon geomorphon = new OmsGeomorphon();
		geomorphon.pRadius = pRadius * cMeters;
		if (!(Double.isNaN(pThreshold) || pThreshold == 0)) {
			geomorphon.pThreshold = pThreshold * cMeters;
		}
		
		geomorphon.inElev = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue, false);
		geomorphon.pm = new TaskMonitor(context.getMonitor());
		geomorphon.doProcess = true;
		geomorphon.doReset = false;
		context.getMonitor().info("Running geomorphon algorithm...");
		try {
			geomorphon.process();
		} catch (Exception e) {
			throw new KlabException(e);
		}
		if (!context.getMonitor().isInterrupted()) {
			GeotoolsUtils.INSTANCE.coverageToState(geomorphon.outRaster, target, context.getScale(), (a) -> {
				if (a == (double) floatNovalue)
					return Double.NaN;
				return a;
			});
		}
		return target;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		GeomorphonResolver ret = new GeomorphonResolver();
		ret.pRadius = parameters.get("radius", Double.NaN);
		ret.pThreshold = parameters.get("threshold", Double.NaN);
		return ret;
	}

}
