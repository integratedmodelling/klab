package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert;
import org.integratedmodelling.kactors.kactors.AssertStatement;
import org.integratedmodelling.kactors.kactors.MessageCall;

public class KActorsAssert extends KActorsStatement implements Assert {
	
	List<Call> calls = new ArrayList<>();
	
	public KActorsAssert(AssertStatement statement, KActorCodeStatement parent) {
		super(statement, parent, Type.ASSERT_STATEMENT);
		for (MessageCall call : statement.getMethodCalls()) {
			calls.add(new KActorsActionCall(call, this));
		}
	}

	@Override
	public List<Call> getCalls() {
		return calls;
	}


}
