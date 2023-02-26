package org.integratedmodelling.klab.api.lang.kim.impl;

import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimRestriction;

public class KimRestriction extends KimStatement implements KKimRestriction {

    private static final long serialVersionUID = 1204374369797711459L;
    private Type type;
    private Cardinality cardinality;
    private int numerosity;
    private KKimConcept filler;
    private KKimConcept targetSubject;
    private Object value;
    private Number range;

    @Override
    public Type getType() {
        // TODO Auto-generated method stub
        return this.type;
    }

    @Override
    public Cardinality getCardinality() {
        return this.cardinality;
    }

    @Override
    public int getNumerosity() {
        return this.numerosity;
    }

    @Override
    public KKimConcept getFiller() {
        return this.filler;
    }

    @Override
    public KKimConcept getTargetSubject() {
        return this.targetSubject;
    }

    @Override
    public Object getValue() {
        // TODO may need a Literal shuttle type
        return this.value;
    }

    @Override
    public Number getRange() {
        return this.range;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }

    public void setNumerosity(int numerosity) {
        this.numerosity = numerosity;
    }

    public void setFiller(KKimConcept filler) {
        this.filler = filler;
    }

    public void setTargetSubject(KKimConcept targetSubject) {
        this.targetSubject = targetSubject;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setRange(Number range) {
        this.range = range;
    }

}
