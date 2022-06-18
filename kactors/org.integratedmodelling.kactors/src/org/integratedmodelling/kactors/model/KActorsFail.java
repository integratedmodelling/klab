package org.integratedmodelling.kactors.model;

import org.integratedmodelling.kactors.api.IKActorsStatement.Fail;
import org.integratedmodelling.kactors.kactors.FailStatement;

public class KActorsFail extends KActorsStatement implements Fail {

    String message;
    
    public KActorsFail(FailStatement statement, KActorCodeStatement parent) {
        super(statement, parent, Type.FAIL_STATEMENT);
        this.message = statement.getReason();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
