package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class ObjectClassificationResolver implements IResolver<IState>, IProcessor, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.aggregate";

	IArtifact classified;
	IArtifact classifier;

	// don't remove - only used as expression
	public ObjectClassificationResolver() {
	}

	public ObjectClassificationResolver(IArtifact classified, IArtifact classifier) {
		this.classified = classified;
		this.classifier = classifier;
	}

	public static IServiceCall getServiceCall(IObservable classified, IObservable classifier)
			throws KlabValidationException {
		return KimServiceCall.create(FUNCTION_ID, "artifact", classified.getLocalName(), "classifier",
				classifier.getLocalName());
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new ObjectClassificationResolver(context.getArtifact(parameters.get("artifact", String.class)),
				context.getArtifact(parameters.get("classifier", String.class)));
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.scalar();
	}

	@Override
	public IState resolve(IState ret, IComputationContext context) throws KlabException {
		return ret;
	}

	@Override
	public IArtifact.Type getType() {
		return classified.getType();
	}

}
