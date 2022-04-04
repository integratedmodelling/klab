package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IProcessor;
import org.integratedmodelling.klab.api.model.contextualization.IStateResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class LookupStateResolver extends AbstractContextualizer implements IStateResolver, IProcessor, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.lookup";

	private ILookupTable lookupTable;

	// don't remove - only used as expression
	public LookupStateResolver() {
	}

	public LookupStateResolver(ILookupTable classification) {
		this.lookupTable = classification;
	}

	public static IServiceCall getServiceCall(ILookupTable classification, IContextualizable condition, boolean conditionNegated)
			throws KlabValidationException {
		// TODO handle condition
		return KimServiceCall.create(FUNCTION_ID, "table", classification);
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new LookupStateResolver(parameters.get("table", ILookupTable.class));
	}

//	@Override
//	public IGeometry getGeometry() {
//		return Geometry.scalar();
//	}

	@Override
	public Object resolve(IObservable observable, IContextualizationScope context) throws KlabException {
		return lookupTable.lookup(context, context);
	}

	@Override
	public IArtifact.Type getType() {
		return lookupTable.getResultType();
	}
}
