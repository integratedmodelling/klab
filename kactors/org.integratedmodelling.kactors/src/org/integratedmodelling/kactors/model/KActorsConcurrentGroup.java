package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Statement;
import org.integratedmodelling.kactors.kactors.StatementGroup;
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
	Map<String, IKActorsValue> groupMetadata = new HashMap<>();
	
	public KActorsConcurrentGroup(List<StatementList> statementGroup, KActorCodeStatement parent) {
		super(parent, Type.CONCURRENT_GROUP);
		for (StatementList list : statementGroup) {
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

	public KActorsConcurrentGroup(StatementGroup statementGroup, KActorCodeStatement parent) {
		super(parent, Type.CONCURRENT_GROUP);
		for (StatementList list : statementGroup.getBody().getLists()) {
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
		if (statementGroup.getMetadata() != null) {
			for (int i = 0; i < statementGroup.getMetadata().getKeys().size(); i++) {
				String key = statementGroup.getMetadata().getKeys().get(i).substring(1);
				KActorsValue value = new KActorsValue(statementGroup.getMetadata().getValues().get(i), this);
				groupMetadata.put(key, value);
			}
		}
	}

	@Override
	public List<IKActorsStatement> getStatements() {
		return this.sequences;
	}

	@Override
	public Map<String, IKActorsValue> getGroupMetadata() {
		return groupMetadata;
	}
	
}
