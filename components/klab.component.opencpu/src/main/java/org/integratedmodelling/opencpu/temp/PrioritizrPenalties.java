package org.integratedmodelling.opencpu.temp;

public class PrioritizrPenalties {

    public PenaltyType type;
    public Double penalty;
    public Double edgeFactor; // only used for boundary penalties TODO: could be an array in multi-zone problems
    public Double[] linearPenaltyFactor; // only used for linear penalties. TODO: could be an array of arrays in multi-zone

    public enum PenaltyType{
        Boundary,
        Linear,
        /* TODO: Connectivity penalties are more complex because they require matrix data for the connectivity between  units.
             This type of data is not usually used in k.LAB
        AsymConnectivity,
        Connectivity*/
    }

    // constructors
    public PrioritizrPenalties(PenaltyType type, Double penalty, Double factor) throws OCPUException {
        if (type == PenaltyType.Boundary) {
            this.type = type;
            this.penalty = penalty;
            this.edgeFactor = factor;
            Double[] empty = {};
            this.linearPenaltyFactor = empty;
        }
        else{
            throw new OCPUException("Only boundary penalties can be set up with the passed argument types.");
        }
    }
    public PrioritizrPenalties(PenaltyType type, Double penalty, Double[] factor) throws OCPUException {
        if (type == PenaltyType.Linear) {
            this.type = type;
            this.penalty = penalty;
            this.edgeFactor = 0.0;
            this.linearPenaltyFactor = factor;
        }
        else{
            throw new OCPUException("Only linear penalties can be set up with the passed argument types.");
        }
    }

    public void setType(PenaltyType type){
        this.type = type;
    }
    public void setPenalty(Double penalty){
        this.penalty = penalty;
    }
    public void setEdgeFactor(Double factor){
        this.edgeFactor = factor;
    }
    public void setLinearPenaltyFactor(Double[] lpf){
        this.linearPenaltyFactor = lpf;
    }
    // getters should not be really useful ...
}

