package org.integratedmodelling.opencpu.temp;

public class PrioritizrTargets {

    public TargetType type;
    public Double[] targetValues; // TODO: targetValues can ba array of arrays in multi-zone problems

    public enum TargetType{
        Relative,
        Absolute,
        // TODO: loglinear and manual require more parameters left for later.
        //LogLinear,
        //Manual
    }

    // default constructor
    public PrioritizrTargets(){
        this.setType(TargetType.Relative);
        Double[] empty = {};
        this.setTargetValues(empty);
    }
    public PrioritizrTargets(TargetType type, Double[] targetValues){
        this.setType(type);
        this.setTargetValues(targetValues);
    }

    public void setType(TargetType type) {
        this.type = type;
    }
    public void setTargetValues(Double[] valueArray){
        this.targetValues = valueArray;
    }
    public TargetType getType() {
        return this.type;
    }
    public Double[] getTargetValues(){
        return this.targetValues;
    }
}
