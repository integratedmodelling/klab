package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.kactors.Statement;
import org.integratedmodelling.kactors.kactors.StatementList;

/**
 * A code block is a list of concurrent sequences, each being a list of serial
 * calls representing a single statement.
 * 
 * @author Ferd
 *
 */
public class KActorsConcurrentGroup extends KActorsStatement implements ConcurrentGroup {

	List<IKActorsStatement> sequences = new ArrayList<>();

	public KActorsConcurrentGroup(List<StatementList> statements, KActorCodeStatement parent) {
		super(parent, Type.CONCURRENT_GROUP);
		for (StatementList list : statements) {
			List<IKActorsStatement> sequence = new ArrayList<>();
			sequence.add(KActorsStatement.create(list.getFirst(), parent));
			if (list.getNext() != null) {
				for (Statement statement : list.getNext()) {
					sequence.add(KActorsStatement.create(statement, parent));
				}
			}
			if (!sequence.isEmpty()) {
				this.sequences.add(new KActorsSequence(sequence, this));
			}
		}
	}

	@Override
	public List<IKActorsStatement> getStatements() {
		return this.sequences;
	}

}
