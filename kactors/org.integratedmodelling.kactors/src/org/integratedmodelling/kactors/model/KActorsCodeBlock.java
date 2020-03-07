package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.kactors.Statement;
import org.integratedmodelling.kactors.kactors.StatementList;

/**
 * A code block is a list of concurrent sequences, each being a list of serial
 * calls representing a single statement.
 * 
 * @author Ferd
 *
 */
public class KActorsCodeBlock extends KActorsStatement {

	List<List<KActorsStatement>> sequences = new ArrayList<>();

	public KActorsCodeBlock(List<StatementList> statements, KActorCodeStatement parent) {
		super(parent, Type.STATEMENT_GROUP);
		for (StatementList list : statements) {
			List<KActorsStatement> sequence = new ArrayList<>();
			sequence.add(KActorsStatement.create(list.getFirst(), parent));
			if (list.getNext() != null) {
				for (Statement statement : list.getNext()) {
					sequence.add(KActorsStatement.create(statement, parent));
				}
			}
		}
	}

}
