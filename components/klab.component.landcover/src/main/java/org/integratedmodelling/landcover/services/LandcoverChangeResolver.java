package org.integratedmodelling.landcover.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.landcover.model.LandcoverChange;

public class LandcoverChangeResolver extends AbstractContextualizer implements IResolver<IProcess>, IExpression {

	private LandcoverChange model = null;
	private IParameters<String> parameters;
	
	public LandcoverChangeResolver() {
	}

	public LandcoverChangeResolver(IParameters<String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Type getType() {
		return Type.CONCEPT;
	}

	@Override
	public IProcess resolve(IProcess ret, IContextualizationScope context) throws KlabException {

		if (this.model == null) {
			this.model = new LandcoverChange(ret);
			this.model.configure(this.parameters, (IRuntimeScope)context);
		}
		
		this.model.run((IRuntimeScope)context);
		
		return ret;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) {
		return new LandcoverChangeResolver(Parameters.create(parameters));
	}

}
