package org.integratedmodelling.klab.engine.runtime.code;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

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

//		if (context.getModel() != null) {
//
//			/*
//			 * match identifiers to the formal name of all dependencies; include "self" 
//			 * appropriately
//			 */
//			for (IObservable observable : context.getModel().getObservables()) {
//				Collection<IArtifact> artifacts = context.getArtifact(observable.getType());
//				if (artifacts.size() == 1) {
//					IArtifact artifact = artifacts.iterator().next();
//					System.out.println("DIO");
//				}
//				break;
//			}
//			for (IObservable dependency : context.getModel().getDependencies()) {
//				Collection<IArtifact> artifacts = context.getArtifact(dependency.getType());
//				if (artifacts.size() == 1) {
//					IArtifact artifact = artifacts.iterator().next();
//					System.out.println("DIO ZANA");
//				}
//			}
//			
//			
//		} else {

			/*
			 * use the catalog
			 */
			for (Entry<String, IObservation> artifact : context.getLocalCatalog(IObservation.class).entrySet()) {

				String name = artifact.getKey();
				ret.identifiers.add(name);
				if (artifact.getValue() instanceof IState) {
					ret.stateIdentifiers.add(name);
				}
				ret.identifierTypes.put(name,
						Observables.INSTANCE.getObservableType(artifact.getValue().getObservable(), true));
			}
//		}
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
