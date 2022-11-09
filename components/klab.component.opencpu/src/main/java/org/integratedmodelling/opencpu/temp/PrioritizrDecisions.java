package org.integratedmodelling.opencpu.temp;

public class PrioritizrDecisions {

    public DecisionType type;
    public double upperLimit; // only to be used with semicontinuous type

    public enum DecisionType{
        Binary,
        Proportion,
        SemiContinuous
    }

    public PrioritizrDecisions(DecisionType type) throws OCPUException {
        if (type == DecisionType.Binary || type == DecisionType.Proportion){
            this.setType(type);
            this.setUpperLimit(0.0);
        } else {
            throw new OCPUException("Only binary or proportion decisions can be set up with the passed parameters.");
        }
    }

    public PrioritizrDecisions(DecisionType type, Double upper) throws OCPUException {
        if (type == DecisionType.SemiContinuous){
            this.setType(type);
            this.setUpperLimit(upper);
        } else {
            throw new OCPUException("Only semi-continuous decisions can be set up with the passed parameters.");
        }
    }

    public void setType(DecisionType type) {
        this.type = type;
    }
    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

}
