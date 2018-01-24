package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IKimObject;

public abstract class KimObject implements IKimObject {

    private static final long    serialVersionUID = -4651845572772680648L;

    private IKimStatement        statement;
    private IMetadata            metadata;
    protected List<IKimObject>   children         = new ArrayList<>();

    public KimObject(IKimStatement statement) {
        this.statement = statement;
    }

    @Override
    public List<IKimObject> getChildren() {
        return children;
    }

    @Override
    public IKimStatement getStatement() {
        return statement;
    }
    
    @Override
    public List<IKimAnnotation> getAnnotations() {
        return statement.getAnnotations();
    }

    protected void setStatement(IKimStatement statement) {
        this.statement = statement;
    }

    public void setMetadata(IMetadata metadata) {
        this.metadata = metadata;
    }

    public IMetadata getMetadata() {
        return metadata;
    }

    public String toString() {
        return statement.toString();
    }
}
