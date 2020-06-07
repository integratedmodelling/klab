package org.integratedmodelling.kactors.model;

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
		
		KActorsStatement ret = null;
		
		if (statement.getAssignment() != null) {
			ret = new KActorsAssignment(statement.getAssignment(), parent);
		} else if (statement.getDo() != null) {
			ret =  new KActorsDo(statement.getDo(), parent);
		} else if (statement.getFor() != null) {
			ret =  new KActorsFor(statement.getFor(), parent);
		} else if (statement.getIf() != null) {
			ret =  new KActorsIf(statement.getIf(), parent);
		} else if (statement.getWhile() != null) {
			ret =  new KActorsWhile(statement.getWhile(), parent);
		} else if (statement.getText() != null) {
			ret =  new KActorsText(statement, parent);
		} else if (statement.getValue() != null) {
			ret =  new KActorsFire(statement.getValue(), parent);
		} else if (statement.getVerb() != null) {
			ret =  new KActorsActionCall(statement.getVerb(), parent);
		} else if (statement.getGroup() != null) {
			ret =  new KActorsConcurrentGroup(statement.getGroup(), parent);
		}
		
		if (ret != null) {

			if (statement.getTag() != null) {
				ret.tag = statement.getTag().substring(1);
			}
			
		}

		return ret;
	}

	@Override
	public Type getType() {
		return type;
	}

}
