package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;

public class KnowledgeViewResolver implements IResolver<IArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.compileview";

	private IViewModel view;

	// don't remove - only used as expression
	public KnowledgeViewResolver() {
	}

	public KnowledgeViewResolver(String viewName) {
		this.view = (IViewModel)Resources.INSTANCE.getModelObject(viewName);
	}

	public static IServiceCall getServiceCall(IViewModel view) {
		return KimServiceCall.create(FUNCTION_ID, "view", view.getName());
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new KnowledgeViewResolver(parameters.get("view", String.class));
	}

	@Override
	public IArtifact.Type getType() {
		return Type.VOID;
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope scope) throws KlabException {
		// artifact will normally be null
		IKnowledgeView result = this.view.compileView((IObservation)ret, scope);
		((IRuntimeScope)scope).addView(result);
		return ret;
	}
}
