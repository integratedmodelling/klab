package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabException;

public class LiteralCharacterizingResolver implements IResolver<IArtifact>, IProcessor, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.characterize";

	IConcept value;

	// don't remove - only used as expression
	public LiteralCharacterizingResolver() {
	}

	public LiteralCharacterizingResolver(IConcept value) {
		this.value = value;
	}

	public static IServiceCall getServiceCall(IConcept literal) {
		// TODO handle condition
		return new LiteralFunction(literal);
	}

	/**
	 * A literal function produces a literal and takes no arguments. It is used to
	 * simplify the encoding in dataflows, and its KDL code equivalent is the
	 * literal itself.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public static class LiteralFunction extends KimServiceCall {

		static final public String FUNCTION_ID = "klab.runtime.characterize";

		Object value;

		public LiteralFunction(Object value) {
			super((EObject) null, null);
			this.value = value;
			setName(FUNCTION_ID);
			getParameters().put("val", value);
		}

		private static final long serialVersionUID = -5190145577904822153L;

		@Override
		public String getSourceCode() {
			return value == null ? "unknown" : value.toString().trim();
		}

	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new LiteralCharacterizingResolver(parameters.get("val", IConcept.class));
	}

	@Override
	public IArtifact.Type getType() {
		return Type.VOID; // IArtifact.Type.classify(this.value);
	}

	@Override
	public IArtifact resolve(IArtifact ret, IContextualizationScope context) throws KlabException {
		// TODO Auto-generated method stub
		return ret;
	}
}
