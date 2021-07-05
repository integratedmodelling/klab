package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.ConcurrentGroup;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.kactors.MetadataPair;
import org.integratedmodelling.kactors.kactors.Statement;
import org.integratedmodelling.kactors.kactors.StatementGroup;
import org.integratedmodelling.kactors.kactors.StatementList;
import org.integratedmodelling.kactors.model.KActorsActionCall.ActionDescriptor;
import org.integratedmodelling.klab.utils.Pair;

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
	private List<ActionDescriptor> actions = new ArrayList<>();

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
			for (MetadataPair pair : statementGroup.getMetadata().getPairs()) {
				String key = pair.getKey().substring(1);
				boolean negative = pair.getKey().startsWith("!");
				KActorsValue value = null;
				if (pair.getValue() != null) {
					value = new KActorsValue(pair.getValue(), this);
				} else {
					value = new KActorsValue(!negative, this);
				}
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

	@Override
	public List<Pair<IKActorsValue, IKActorsStatement>> getGroupActions() {
		List<Pair<IKActorsValue, IKActorsStatement>> ret = new ArrayList<>();
		for (ActionDescriptor ad : actions) {
			ret.add(new Pair<>(ad.match, ad.action));
		}
		return ret;
	}


    @Override
    protected void visit(IKActorsAction action, Visitor visitor) {
        for (IKActorsStatement statement : sequences) {
            ((KActorsStatement)statement).visit(action, visitor);
        }
        for (ActionDescriptor a : actions) {
            a.visit(action, this, visitor);
        }
        super.visit(action, visitor);
    }
    
}
