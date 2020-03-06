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
public class KActorsCodeBlock extends KActorStatement {

	List<List<KActorsCall>> sequences = new ArrayList<>();

	public KActorsCodeBlock(List<StatementList> statements, KActorStatement parent) {
		super(parent.getEStatement(), parent);
		for (StatementList list : statements) {
			List<KActorsCall> sequence = new ArrayList<>();
			sequence.add(KActorsCall.create(list.getFirst()));
			if (list.getNext() != null) {
				for (Statement statement : list.getNext()) {
					sequence.add(KActorsCall.create(statement));
				}
			}
		}
	}

}
