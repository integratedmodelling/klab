package org.integratedmodelling.geoprocessing.morphology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.hortonmachine.hmachine.modules.geomorphology.gradient.OmsGradient;
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

public class GradientResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {


    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

        IState dem = context.getArtifact("elevation", IState.class);
        ISpace space = target.getSpace();

        if (!(target.isSpatiallyDistributed() && space.isRegular())) {
            throw new KlabValidationException("Gradient must be computed on a grid extent");
        }
        

        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Aspect");

        OmsGradient gradient = new OmsGradient();
        gradient.inElev = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);
        gradient.pm = taskMonitor;
        gradient.doProcess = true;
        gradient.doReset = false;
        try {
            gradient.process();
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
            GeotoolsUtils.INSTANCE.coverageToState(gradient.outSlope, target, context.getScale(), (a) -> {
                if (a == (double) floatNovalue)
                    return Double.NaN;
                return a;
            });
        }
        return target;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... params) throws KlabException {
        GradientResolver ret = new GradientResolver();
        return ret;
    }

}
