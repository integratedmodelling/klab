package org.integratedmodelling.geoprocessing.morphology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.hortonmachine.hmachine.modules.geomorphology.aspect.OmsAspect;
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

public class AspectResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {


    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

        IState dem = context.getArtifact("elevation", IState.class);
        ISpace space = target.getSpace();

        if (!(target.isSpatiallyDistributed() && space.isRegular())) {
            throw new KlabValidationException("Aspect must be computed on a grid extent");
        }
        
        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Aspect");

        OmsAspect aspect = new OmsAspect();
        aspect.inElev = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);
        aspect.pm = taskMonitor;
        aspect.doProcess = true;
        aspect.doReset = false;
//        aspect.doRound = true;
        aspect.doRadiants = false;
//        context.getMonitor().info("Running algorithm...");
        try {
            aspect.process();
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
            GeotoolsUtils.INSTANCE.coverageToState(aspect.outAspect, target, context.getScale(), (a) -> {
                if (a == (double) floatNovalue)
                    return Double.NaN;
                return a;
            });
        }
        return target;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... params) throws KlabException {
        AspectResolver ret = new AspectResolver();
        return ret;
    }

}
