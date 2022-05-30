package org.integratedmodelling.kactors.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.kactors.AssertStatement;
import org.integratedmodelling.kactors.kactors.MessageCall;
import org.integratedmodelling.kim.api.IParameters;

public class KActorsAssert extends KActorsStatement implements Assert {

    List<Assertion> assertions = new ArrayList<>();
    private KActorsArguments arguments;

    class AssertionImpl extends KActorsStatement implements Assertion {

        public AssertionImpl(org.integratedmodelling.kactors.kactors.Assertion statement, KActorCodeStatement parent) {
            super(statement, parent, Type.ASSERTION);
            parseMetadata(statement.getMetadata());
        }

        List<Call> calls = new ArrayList<>();
        KActorsValue expression = null;
        KActorsValue value = null;

        @Override
        public List<Call> getCalls() {
            return calls;
        }

        @Override
        public IKActorsValue getExpression() {
            return expression;
        }

        @Override
        public IKActorsValue getValue() {
            return value;
        }

    }

    public KActorsAssert(AssertStatement statement, KActorCodeStatement parent) {

        super(statement, parent, Type.ASSERT_STATEMENT);

        if (statement.getParameters() != null) {
            this.arguments = new KActorsArguments(statement.getParameters());
        } else { 
            this.arguments = new KActorsArguments();
        }

        for (org.integratedmodelling.kactors.kactors.Assertion ass : statement.getAssertions()) {
            AssertionImpl nass = new AssertionImpl(ass, this);

            if (ass.getExpression() != null) {
                nass.expression = new KActorsValue(ass.getExpression(), this);
            } else {
                for (MessageCall call : ass.getMethodCalls()) {
                    nass.calls.add(new KActorsActionCall(call, this));
                }
            }

            if (ass.getValue() != null) {
                nass.value = new KActorsValue(ass.getValue(), this);
            }

            assertions.add(nass);

        }
    }

    @Override
    public List<Assertion> getAssertions() {
        return assertions;
    }

    @Override
    public IParameters<String> getArguments() {
        return this.arguments;
    }

}
