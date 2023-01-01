package org.integratedmodelling.klab.components.runtime.contextualizers.reifiers;

import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

/**
 * Instantiate change events by comparing successive states of a quality. If the
 * quality is categorical, produce events with specialized semantics detailing
 * the to/from categories; otherwise just detect contiguous numeric changes or
 * appearance and disappearance (false->true or true->false) for changes in
 * presence.
 * 
 * @author Ferd
 *
 */
public class ChangeEventInstantiator extends AbstractContextualizer implements IInstantiator, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.reifiers.change";

	// don't remove - only used as expression
	public ChangeEventInstantiator() {
	}

	public ChangeEventInstantiator(IParameters<String> parameters, IContextualizationScope context) {
	}

	public static IServiceCall getServiceCall(IObservable availableType, IObservable desiredObservation) {
		IServiceCall ret = KimServiceCall.create(FUNCTION_ID);
		return ret;
	}

	@Override
	public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
		return new ChangeEventInstantiator(Parameters.create(parameters), context);
	}

	@Override
	public Type getType() {
		return Type.EVENT;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope scope)
			throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

}
