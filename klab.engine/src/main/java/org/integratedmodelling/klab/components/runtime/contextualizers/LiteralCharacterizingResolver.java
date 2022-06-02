package org.integratedmodelling.klab.components.runtime.contextualizers;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;

public class LiteralCharacterizingResolver extends AbstractContextualizer implements IResolver<IArtifact>, IProcessor, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.resolvetrait";

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

		static final public String FUNCTION_ID = "klab.runtime.resolvetrait";

		IConcept value;

		public LiteralFunction(IConcept value) {
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

		IConcept toResolve = context.getTargetSemantics().getType();
		List<IConcept> traits = this.value.is(IKimConcept.Type.INTERSECTION) ? Collections.singletonList(this.value)
				: this.value.getOperands();
		if (ret instanceof IDirectObservation) {
			for (IConcept predicate : traits) {
				((IRuntimeScope) context).newPredicate((IDirectObservation) ret, predicate);
			}
		} else {
			((IRuntimeScope) context).setConcreteIdentities(toResolve, traits);
		}
		return ret;
	}
}
