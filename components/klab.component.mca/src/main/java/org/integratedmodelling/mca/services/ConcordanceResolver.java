package org.integratedmodelling.mca.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
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
		this.mcaContext = new MCAContext(ret.getObservable(), (IRuntimeContext)context);
		
		return ret;
	}

}
