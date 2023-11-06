package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.hortonmachine.hmachine.modules.geomorphology.draindir.OmsDrainDir;
import org.hortonmachine.hmachine.modules.network.netnumbering.OmsNetNumbering;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;

public class ContributingAreaOfReachResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

		IState pit = context.getArtifact("hydrologically_corrected_elevation", IState.class);
		IState flow = context.getArtifact("flow_directions_d8", IState.class);
		IState net = context.getArtifact("presence_of_stream", IState.class);
		

		context.getMonitor().info("computing necessary draindir...");
		
	    OmsDrainDir draindir = new OmsDrainDir();
	    draindir.inPit = GeotoolsUtils.INSTANCE.stateToCoverage(pit, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);
	    draindir.inFlow = GeotoolsUtils.INSTANCE.stateToCoverage(flow, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);
	    draindir.pLambda = 1.0;
	    draindir.doLad = true;
	    try {
	    	draindir.process();
	    } catch (Exception e) {
            throw new KlabException(e);
        }
		
		OmsNetNumbering netnumbering = new OmsNetNumbering();
	    netnumbering.inFlow = draindir.outFlow;
	    netnumbering.inTca = draindir.outTca;
	    netnumbering.inNet = GeotoolsUtils.INSTANCE.stateToCoverage(net, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);
        try {
        	netnumbering.process();
//	    netnumbering.outNetnum = netnum
//	    netnumbering.outBasins = netnumBasins
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
                        
            GeotoolsUtils.INSTANCE.coverageToState(netnumbering.outBasins, target, context.getScale(), (a) -> {
                if (a == (double) floatNovalue) {
                    return Double.NaN;
                }
                return a;
            });
        }
        return target;
    }

    public double toAngle(double code) {
        if (Double.isNaN(code)) {
            return code;
        }
        return Geospace.getHeading((int) code);
    }

    @Override
    public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
        ContributingAreaOfReachResolver ret = new ContributingAreaOfReachResolver();
        return ret;
    }
}
