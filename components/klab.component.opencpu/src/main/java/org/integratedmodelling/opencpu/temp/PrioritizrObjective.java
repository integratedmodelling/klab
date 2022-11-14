package org.integratedmodelling.opencpu.temp;

public class PrioritizrObjective {
    public PrioritizrObjective.ObjectiveType type;
    public Double budget = 0.0; // TODO: budget should be an array for generality

    // ignoring the phylogenetic objectives
    public enum ObjectiveType{
        MinSet,
        MaxCover,
        MaxFeatures,
        MinShortfall,
        MinLargestShortfall,
        MaxUtility
    }

    // default constructor
    public PrioritizrObjective(){
        this.setType(PrioritizrObjective.ObjectiveType.MinSet);
        this.setBudget(0.0);
    }
    public PrioritizrObjective(PrioritizrObjective.ObjectiveType type, Double budget){
        this.setType(type);
        this.setBudget(budget);
    }

    public void setType(PrioritizrObjective.ObjectiveType type) {
        this.type = type;
    }
    public void setBudget(Double budget){
        this.budget = budget;
    }
    public PrioritizrObjective.ObjectiveType getType() { return this.type; }
    public Double getBudget() { return this.budget; }
}
