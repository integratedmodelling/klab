package org.integratedmodelling.kactors.model;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.kactors.Statement;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;

public abstract class KActorsStatement extends KActorCodeStatement implements IKActorsStatement {
	
	private Type type;

	public KActorsStatement(EObject statement, KActorCodeStatement parent, Type type) {
		super(statement, parent);
		this.type = type;
	}

	public KActorsStatement(KActorCodeStatement parent, Type type) {
		super(parent.getEStatement(), parent);
		this.type = type;
	}

	public static KActorsStatement create(Statement statement, KActorCodeStatement parent) {
	
		if (statement.getAssignment() != null) {
			return new KActorsAssignment(statement.getAssignment(), parent);
		} else if (statement.getDo() != null) {
			return new KActorsDo(statement.getDo(), parent);
		} else if (statement.getFor() != null) {
			return new KActorsFor(statement.getFor(), parent);
		} else if (statement.getIf() != null) {
			return new KActorsIf(statement.getIf(), parent);
		} else if (statement.getWhile() != null) {
			return new KActorsWhile(statement.getWhile(), parent);
		} else if (statement.getText() != null) {
			return new KActorsText(statement, parent);
		} else if (statement.getValue() != null) {
			return new KActorsFire(statement.getValue(), parent);
		} else if (statement.getVerb() != null) {
			return new KActorsActionCall(statement.getVerb(), parent);
		} else if (statement.getGroup() != null) {
			return new KActorsConcurrentGroup(statement.getGroup(), parent);
		}

		throw new KlabInternalErrorException("unexpected k.Actors statement type");
	}

	@Override
	public Type getType() {
		return type;
	}

}
