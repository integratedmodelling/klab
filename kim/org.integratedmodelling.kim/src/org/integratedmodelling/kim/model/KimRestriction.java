package org.integratedmodelling.kim.model;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Visitor;
import org.integratedmodelling.kim.api.IKimRestriction;
import org.integratedmodelling.kim.kim.DataType;
import org.integratedmodelling.kim.kim.RestrictionDefinition;

public class KimRestriction extends KimStatement implements IKimRestriction {

    private static final long serialVersionUID = 829922435386310735L;

    private int               numerosity       = 1;
    private Cardinality       cardinality      = Cardinality.ONLY;
    private Type              type             = Type.HAS;
    private Object            value;
    private IKimConcept       filler;
    private IKimConcept       targetSubject;
    private DataType          dataType;
    private Number            range;

    public KimRestriction(RestrictionDefinition statement) {
        super(statement);
    }
    
    // visit every declaration with the passed visitor
    public void visit(Visitor visitor) {
        if (filler != null) {
            filler.visit(visitor);
        }
        if (targetSubject != null) {
            targetSubject.visit(visitor);
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public Cardinality getCardinality() {
        return cardinality;
    }

    @Override
    public int getNumerosity() {
        return numerosity;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setNumerosity(int numerosity) {
        this.numerosity = numerosity;
    }

    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public IKimConcept getFiller() {
        return this.filler;
    }

    @Override
    public IKimConcept getTargetSubject() {
        return this.targetSubject;
    }

    @Override
    public DataType getDataType() {
        return this.dataType;
    }

    @Override
    public Number getRange() {
        return this.range;
    }

    public void setFiller(IKimConcept filler) {
        this.filler = filler;
    }

    public void setTargetSubject(IKimConcept targetSubject) {
        this.targetSubject = targetSubject;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public void setRange(Number range) {
        this.range = range;
    }
}
