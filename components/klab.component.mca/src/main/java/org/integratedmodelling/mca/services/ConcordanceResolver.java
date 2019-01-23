package org.integratedmodelling.mca.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.mca.api.IAlternative;
import org.integratedmodelling.mca.api.ICriterion;
import org.integratedmodelling.mca.api.IStakeholder;
import org.integratedmodelling.mca.core.MCA;
import org.integratedmodelling.mca.core.MCAContext;

public class ConcordanceResolver implements IResolver<IState>, IExpression {

	int levels = 5;
	MCAContext mcaContext;
	IGeometry geometry;
	
	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		ConcordanceResolver ret = new ConcordanceResolver();
		ret.levels = parameters.get("levels", 5);
		return ret;
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {
		
		this.geometry = context.getScale();
		this.mcaContext = new MCAContext(ret.getObservable(), (IRuntimeContext)context, ITime.INITIALIZATION, levels);
		
		if (this.mcaContext.isComputable()) {
			MCA mca = new MCA();
			for (ICriterion criterion : this.mcaContext.getCriteria()) {
				// declare criterion
			}
			for (IAlternative alternative : this.mcaContext.getAlternatives()) {
				// declare alternative
			}
			for (IStakeholder stakeholder : this.mcaContext.getStakeholders()) {
				// declare stakeholder
			}
			// run
			// set directives for reporting 
			// have the MCAContext build the resulting artifact
		}
		
		return ret;
	}

}
