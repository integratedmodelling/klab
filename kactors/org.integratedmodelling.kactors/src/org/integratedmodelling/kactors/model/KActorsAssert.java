package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.AssertStatement;
import org.integratedmodelling.kactors.kactors.MessageCall;

public class KActorsAssert extends KActorsStatement implements Assert {
	
	List<Assertion> assertions = new ArrayList<>();

	class AssertionImpl  implements Assertion {
		
		List<Call> calls = new ArrayList<>();
		KActorsValue expression = null;

		@Override
		public List<Call> getCalls() {
			return calls;
		}
		
		@Override
		public IKActorsValue getExpression() {
			return expression;
		}

		
	}
	
	public KActorsAssert(AssertStatement statement, KActorCodeStatement parent) {
		super(statement, parent, Type.ASSERT_STATEMENT);
		for (org.integratedmodelling.kactors.kactors.Assertion ass : statement.getAssertions()) {
			AssertionImpl nass = new AssertionImpl();
			
			if (ass.getExpression() != null) {
				nass.expression = new KActorsValue(ass.getExpression(), this);
			} else {
				for (MessageCall call : ass.getMethodCalls()) {
					nass.calls.add(new KActorsActionCall(call, this));
				}
			}
			
			assertions.add(nass);
			
		}
	}

	@Override
	public List<Assertion> getAssertions() {
		return assertions;
	}


}