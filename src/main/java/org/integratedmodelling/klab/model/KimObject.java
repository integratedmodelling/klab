package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.api.model.IKimObject;

public abstract class KimObject implements IKimObject {

    private static final long serialVersionUID = -4651845572772680648L;

    private IKimStatement statement;
    
    public KimObject(IKimStatement statement) {
        this.statement = statement;
    }

    @Override
    public IKimStatement getStatement() {
        return statement;
    }
    
    protected void setStatement(IKimStatement statement) {
        this.statement = statement;
    }
    
}
