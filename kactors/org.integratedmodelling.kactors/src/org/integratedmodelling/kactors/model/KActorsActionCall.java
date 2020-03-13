package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Match;
import org.integratedmodelling.kactors.kactors.MessageCall;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.utils.Pair;

public class KActorsActionCall extends KActorsStatement implements Call {

	public class ActionDescriptor {
		// no match means "on any firing" (should be a defaulted value, maybe with
		// validation).
		KActorsValue match;
		KActorsStatement action;
	}

	private String message;
	private String recipient;
	private List<ActionDescriptor> actions = new ArrayList<>();
	private KActorsArguments arguments;
	private KActorsConcurrentGroup group;

	public KActorsActionCall(MessageCall messageCall, KActorCodeStatement parent) {

		super(messageCall, parent, Type.ACTION_CALL);

		if (messageCall.getGroup() != null) {
			// TODO use the same ID for the entire group, must have actions
			this.group = new KActorsConcurrentGroup(messageCall.getGroup().getBody().getLists(), this);
		}
		
		this.message = messageCall.getName();
		
		/*
		 * TODO look into manifest and add the recipient if not in this message.
		 */
		
		
		if (messageCall.getParameters() != null) {
			this.arguments = new KActorsArguments(messageCall.getParameters());
		}

		if (messageCall.getActions() != null) {
			if (messageCall.getActions().getStatement() != null) {
				ActionDescriptor action = new ActionDescriptor();
				action.action = KActorsStatement.create(messageCall.getActions().getStatement(), this);
				actions.add(action);
			} else if (messageCall.getActions().getStatements() != null) {
				ActionDescriptor action = new ActionDescriptor();
				action.action = new KActorsConcurrentGroup(
						Collections.singletonList(messageCall.getActions().getStatements()), this);
				actions.add(action);
			} else if (messageCall.getActions().getMatch() != null) {
				ActionDescriptor action = new ActionDescriptor();
				action.match = new KActorsValue(messageCall.getActions().getMatch(), this);
				action.action = new KActorsConcurrentGroup(
						Collections.singletonList(messageCall.getActions().getMatch().getBody()), this);
				actions.add(action);
			} else if (messageCall.getActions().getMatches() != null) {
				for (Match match : messageCall.getActions().getMatches()) {
					ActionDescriptor action = new ActionDescriptor();
					action.match = new KActorsValue(match, this);
					action.action = new KActorsConcurrentGroup(
							Collections.singletonList(match.getBody()), this);
					actions.add(action);
				}
			}
		}
	}

	public String getMessage() {
		return message;
	}
	
	@Override
	public String getRecipient() {
		return recipient;
	}

	@Override
	public IParameters<String> getArguments() {
		return arguments;
	}

	@Override
	public List<Pair<IKActorsValue, IKActorsStatement>> getActions() {
		List<Pair<IKActorsValue, IKActorsStatement>> ret = new ArrayList<>();
		for (ActionDescriptor ad : actions) {
			ret.add(new Pair<>(ad.match, ad.action));
		}
		return ret;
	}

	@Override
	public KActorsConcurrentGroup getGroup() {
		return group;
	}

}