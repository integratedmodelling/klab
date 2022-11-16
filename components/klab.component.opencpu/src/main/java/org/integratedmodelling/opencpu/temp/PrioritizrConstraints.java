package org.integratedmodelling.opencpu.temp;

public class PrioritizrConstraints {

    public ConstraintType type;
    public Boolean[] locked;
    public Integer numNeighbors; // only used for neighbor constraints
    public Double threshold; // only used for linear constraints
    public RelationalOp sense; // only used for linear constraints
    public Double[] constraintValues; // only used for linear constraints

    public enum ConstraintType{
        LockedIn,
        LockedOut,
        Neighbor,
        Contiguity,
        FeatureContiguity,
        Linear
    }
    public enum RelationalOp{
        gt,
        eq,
        lt
    }

    // constructors
    public PrioritizrConstraints(ConstraintType type, Boolean[] locked) throws OCPUException {
        if (type == ConstraintType.LockedIn || type == ConstraintType.LockedOut){
            this.setType(type);
            this.setLocked(locked);
            this.setNumNeighbors(0);
            this.setThreshold(0.0);
            this.setSense(RelationalOp.eq);
            Double[] empty = {};
            this.setConstraintValues(empty);
        } else{
            throw new OCPUException("Only locked in/out constraints can be set up with the passed argument types.");
        }
    }
    public PrioritizrConstraints(ConstraintType type, Integer nn) throws OCPUException {
        if (type == ConstraintType.Neighbor){
            this.setType(type);
            Boolean[] emptyLocked = {};
            this.setLocked(emptyLocked);
            this.setNumNeighbors(nn);
            this.setThreshold(0.0);
            this.setSense(RelationalOp.eq);
            Double[] empty = {};
            this.setConstraintValues(empty);
        } else {
            throw new OCPUException("Only neighbor constraints can be set up with the passed argument types.");
        }
    }
    public PrioritizrConstraints(ConstraintType type) throws OCPUException{
        if (type == ConstraintType.Contiguity || type == ConstraintType.FeatureContiguity ){
            this.setType(type);
            Boolean[] emptyLocked = {};
            this.setLocked(emptyLocked);
            this.setNumNeighbors(0);
            this.setThreshold(0.0);
            this.setSense(RelationalOp.eq);
            Double[] empty = {};
            this.setConstraintValues(empty);
        } else {
            throw new OCPUException("Only contiguity constraints can be set up with the passed argument types.");
        }
    }
    public PrioritizrConstraints(ConstraintType type, Double threshold, RelationalOp sense, Double[] constraintValues) throws OCPUException{
        if (type == ConstraintType.Linear){
            this.setType(type);
            Boolean[] emptyLocked = {};
            this.setLocked(emptyLocked);
            this.setNumNeighbors(0);
            this.setThreshold(threshold);
            this.setSense(sense);
            this.setConstraintValues(constraintValues);
        } else {
            throw new OCPUException("Only linear constraints can be set up with the passed argument types.");
        }
    }

    public void setType(ConstraintType type){
        this.type = type;
    }
    public void setLocked(Boolean[] locked){
        this.locked = locked;
    }
    public void setNumNeighbors(Integer nn){
        this.numNeighbors = nn;
    }
    public void setThreshold(Double threshold){
        this.threshold = threshold;
    }
    public void setSense(RelationalOp sense){
        this.sense = sense;
    }
    public void setConstraintValues(Double[] constraintValues) {
        this.constraintValues = constraintValues;
    }
}
