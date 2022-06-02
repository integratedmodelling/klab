package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;

public class WatershedResolver extends AbstractContextualizer implements IResolver<ISubject>, IExpression {

	@Override
	public Type getType() {
		return Type.VOID;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) {
		// TODO Auto-generated method stub
		return new WatershedResolver();
	}

	@Override
	public ISubject resolve(ISubject ret, IContextualizationScope context) throws KlabException {
		// TODO Auto-generated method stub
		return ret;
	}

}
