package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.kactors.MessageCall;

public class KActorsActionCall extends KActorsStatement {
	
	public KActorsActionCall(MessageCall messageCall, KActorCodeStatement parent) {
		super(messageCall, parent, Type.ACTION_CALL);
	}

}