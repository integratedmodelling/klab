package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IKnowledgeView;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

public class KnowledgeViewResolver implements IResolver<IArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.compileview";

	private IKnowledgeView view;

	// don't remove - only used as expression
	public KnowledgeViewResolver() {
	}

	public KnowledgeViewResolver(String viewName) {
		this.view = (IKnowledgeView)Resources.INSTANCE.getModelObject(viewName);
	}

	public static IServiceCall getServiceCall(IKnowledgeView view) {
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
	public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {
		this.view.compileView(context);
		return null;
	}
}
