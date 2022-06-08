package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Visitor;
import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.kactors.api.IKActorsStatement.Call;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.Match;
import org.integratedmodelling.kactors.kactors.MessageCall;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Triple;

public class KActorsActionCall extends KActorsStatement implements Call {

	public static class ActionDescriptor {
		// no match means "on any firing" (should be a defaulted value, maybe with
		// validation).
		KActorsValue match;
		KActorsStatement action;
		// provided with 'as', may be null
		String matchName;
		
        public void visit(IKActorsAction action2, IKActorsStatement kActorsActionCall, Visitor visitor) {
            visitValue(visitor, match, kActorsActionCall, action2);
            action.visit(action2, visitor);
        }
	}

	private String message;
	private String recipient;
	private List<ActionDescriptor> actions = new ArrayList<>();
	private KActorsArguments arguments;
	private KActorsConcurrentGroup group;
	private String internalId = "kac" + NameGenerator.shortUUID();
	private List<Call> chainedCalls = new ArrayList<>();
	
	public KActorsActionCall(MessageCall messageCall, KActorCodeStatement parent) {

		super(messageCall, parent, Type.ACTION_CALL);

		if (messageCall.getGroup() != null) {
			// TODO use the same ID for the entire group, must have actions
			this.group = new KActorsConcurrentGroup(messageCall.getGroup(), this);
		}

		this.message = messageCall.getName();

		for (MessageCall call : messageCall.getMethodCalls()) {
			chainedCalls.add(new KActorsActionCall(call, this));
		}
		
		if (messageCall.getParameters() != null) {
			this.arguments = new KActorsArguments(messageCall.getParameters());
		} else { 
			this.arguments = new KActorsArguments();
		}

		if (messageCall.getActions() != null) {
			if (messageCall.getActions().getStatement() != null) {
				ActionDescriptor action = new ActionDescriptor();
				action.match = KActorsValue.anytrue();
				action.action = KActorsStatement.create(messageCall.getActions().getStatement(), this);
				actions.add(action);
			} else if (messageCall.getActions().getStatements() != null) {
				ActionDescriptor action = new ActionDescriptor();
				action.match = KActorsValue.anytrue();
				action.action = new KActorsConcurrentGroup(
						Collections.singletonList(messageCall.getActions().getStatements()), this);
				actions.add(action);
			} else if (messageCall.getActions().getMatch() != null) {
				ActionDescriptor action = new ActionDescriptor();
				action.match = new KActorsValue(messageCall.getActions().getMatch(), this);
				action.action = new KActorsConcurrentGroup(
						Collections.singletonList(messageCall.getActions().getMatch().getBody()), this);
				action.matchName = messageCall.getActions().getMatch().getFormalName();
				actions.add(action);
			} else if (messageCall.getActions().getMatches() != null) {
				for (Match match : messageCall.getActions().getMatches()) {
					ActionDescriptor action = new ActionDescriptor();
					action.match = new KActorsValue(match, this);
					action.action = new KActorsConcurrentGroup(Collections.singletonList(match.getBody()), this);
					action.matchName = match.getFormalName();
					actions.add(action);
				}
			}
		}
	}

    public KActorsActionCall(TextBlock code) {
		super(((KActorsText)code).eObject, ((KActorsText)code).parent, Type.TEXT_BLOCK);
		this.message = "text";
		this.arguments = new KActorsArguments();
		this.arguments.putUnnamed(code.getText());
		this.arguments.putAll(code.getMetadata());
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
	public List<Triple<IKActorsValue, IKActorsStatement, String>> getActions() {
		List<Triple<IKActorsValue, IKActorsStatement, String>> ret = new ArrayList<>();
		for (ActionDescriptor ad : actions) {
			ret.add(new Triple<>(ad.match, ad.action, ad.matchName));
		}
		return ret;
	}

	@Override
	public KActorsConcurrentGroup getGroup() {
		return group;
	}

	@Override
	public List<Call> getChainedCalls() {
		return chainedCalls;
	}
	
	@Override
	public String getCallId() {
		return this.internalId;
	}

    @Override
    protected void visit(IKActorsAction action, Visitor visitor) {
        arguments.visit(action, this, visitor);
        if (group != null) {
            group.visit(action, visitor);
        }
        for (ActionDescriptor a : actions) {
            a.visit(action, this, visitor);
        }
        super.visit(action, visitor);
    }
	
	
}