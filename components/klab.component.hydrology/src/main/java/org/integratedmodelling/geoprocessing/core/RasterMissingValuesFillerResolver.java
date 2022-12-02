package org.integratedmodelling.geoprocessing.core;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.hortonmachine.gears.libs.modules.Variables;
import org.hortonmachine.gears.modules.r.rasternull.OmsRasterMissingValuesFiller;
import org.hortonmachine.hmachine.modules.geomorphology.geomorphon.OmsGeomorphon;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class RasterMissingValuesFillerResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

    String[] allInterpolationModes = new String[]{Variables.IDW, Variables.AVERAGING, Variables.CATEGORIES, Variables.TPS};

    int pValidCellsBuffer = 10;
    String pInterpolationMode = allInterpolationModes[0];

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

        IState inputRaster = context.getArtifact("raster", IState.class);
        ISpace space = target.getSpace();

        if (!(target.isSpatiallyDistributed() && space.isRegular())) {
            throw new KlabValidationException("The missing values filler only works on a grid extent.");
        }

        // default radius is 1/100th of the diagonal of the context
        if (pValidCellsBuffer == 0) {
            pValidCellsBuffer = (int) Math.round(
                    Math.sqrt(Math.pow(space.getStandardizedWidth(), 2) + Math.pow(space.getStandardizedHeight(), 2)) / 100.0);
        }

        double cMeters = (space.getEnvelope().getMaxX() - space.getEnvelope().getMinX()) / space.getStandardizedWidth();
        System.out.println(cMeters);

        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Missing Values Filler");
        
        OmsRasterMissingValuesFiller mv = new OmsRasterMissingValuesFiller();
        mv.pm = taskMonitor;
        mv.pMode = pInterpolationMode;
        mv.pValidCellsBuffer = pValidCellsBuffer;

        mv.inRaster = GeotoolsUtils.INSTANCE.stateToCoverage(inputRaster, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);
        try {
            mv.process();
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
            GeotoolsUtils.INSTANCE.coverageToState(mv.outRaster, target, context.getScale(), (a) -> {
                if (a == (double) floatNovalue)
                    return Double.NaN;
                return a;
            });
        }
        return target;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... params) throws KlabException {
        Parameters<String> parameters = Parameters.create(params);
        RasterMissingValuesFillerResolver ret = new RasterMissingValuesFillerResolver();
        ret.pInterpolationMode = parameters.get("interpolationmode", allInterpolationModes[0]);
        ret.pValidCellsBuffer = parameters.get("validcellsbuffer", pValidCellsBuffer);
        return ret;
    }

}
