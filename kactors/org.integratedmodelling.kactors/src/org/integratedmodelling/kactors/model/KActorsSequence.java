package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Sequence;

/**
 * A sequence is a list of statements that must be executed serially.
 * 
 * @author Ferd
 *
 */
public class KActorsSequence extends KActorsStatement implements Sequence {

	List<IKActorsStatement> statements = new ArrayList<>();

	public KActorsSequence(List<IKActorsStatement> statements, KActorCodeStatement parent) {
		super(parent, Type.SEQUENCE);
		this.statements.addAll(statements);
	}

	@Override
	public List<IKActorsStatement> getStatements() {
		return this.statements;
	}

}
