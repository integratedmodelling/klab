package org.integratedmodelling.opencpu.temp;

public class PrioritizrSolver {

    public SolverType type;
    public Double gap;
    public Double timeLimit;
    public Integer presolve;
    public Integer threads;
    public Boolean firstFeasible;
    public Boolean numericFocus;
    public Double nodeFileStart;
    public Boolean verbose;

    public enum SolverType{
        Gurobi,
        CPLEX,
        CBC,
        HiGHS,
        SYMPHONY,
        Rsymphony,
        Default
    }

    // TODO: constructors for the specific solverss
    public PrioritizrSolver(){
        this.setType(SolverType.Default);
        this.setVerbose(Boolean.FALSE);
    }

    public void setType(SolverType type) {
        this.type = type;
    }

    public void setGap(Double gap) {
        this.gap = gap;
    }

    public void setTimeLimit(Double timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setFirstFeasible(Boolean firstFeasible) {
        this.firstFeasible = firstFeasible;
    }

    public void setNodeFileStart(Double nodeFileStart) {
        this.nodeFileStart = nodeFileStart;
    }

    public void setNumericFocus(Boolean numericFocus) {
        this.numericFocus = numericFocus;
    }

    public void setPresolve(Integer presolve) {
        this.presolve = presolve;
    }

    public void setThreads(Integer threads) {
        this.threads = threads;
    }

    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }
}
