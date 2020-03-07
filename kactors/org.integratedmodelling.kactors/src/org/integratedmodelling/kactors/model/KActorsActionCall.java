package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.MessageCall;

public class KActorsActionCall extends KActorsStatement {
	
	private String message;

	public KActorsActionCall(MessageCall messageCall, KActorCodeStatement parent) {
		super(messageCall, parent, Type.ACTION_CALL);
		
		this.message = messageCall.getName();
		if (messageCall.getActions() != null) {
//			for (messageCall.getActions().get
		}
	}

	public String getMessage() {
		return message;
	}

}