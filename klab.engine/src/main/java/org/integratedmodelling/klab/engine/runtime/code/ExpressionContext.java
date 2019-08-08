package org.integratedmodelling.klab.engine.runtime.code;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.Pair;

public class ExpressionContext implements IExpression.Context {

	private Type returnType = Type.VALUE;
	private INamespace namespace;
	private Set<String> identifiers = new HashSet<>();
	private Set<String> stateIdentifiers = new HashSet<>();
	private Map<String, Type> identifierTypes = new HashMap<>();
	private IScale scale;
	private IMonitor monitor;

	public static ExpressionContext create(IRuntimeScope context) {

		ExpressionContext ret = new ExpressionContext();

		ret.scale = context.getScale();
		ret.monitor = context.getMonitor();
		ret.returnType = context.getArtifactType();

		for (Pair<String, IObservation> artifact : context.getArtifacts(IObservation.class)) {
			ret.identifiers.add(artifact.getFirst());
			if (artifact.getSecond() instanceof IState) {
				ret.stateIdentifiers.add(artifact.getFirst());
			}
			ret.identifierTypes.put(artifact.getFirst(),
					Observables.INSTANCE.getObservableType(artifact.getSecond().getObservable(), true));
		}
		return ret;
	}

	private ExpressionContext() {
	}

	@Override
	public Type getReturnType() {
		return this.returnType;
	}

	@Override
	public INamespace getNamespace() {
		return this.namespace;
	}

	@Override
	public Collection<String> getIdentifiers() {
		return this.identifiers;
	}

	@Override
	public Collection<String> getStateIdentifiers() {
		return this.stateIdentifiers;
	}

	@Override
	public Type getIdentifierType(String identifier) {
		return this.identifierTypes.get(identifier);
	}

	@Override
	public IScale getScale() {
		return scale;
	}

	@Override
	public IMonitor getMonitor() {
		return monitor;
	}

}
