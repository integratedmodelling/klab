package org.integratedmodelling.opencpu.temp;

public class PrioritizrProblem {

    public String units;
    public String features;

    public PrioritizrObjective obj;
    public PrioritizrTargets tgt;
    public PrioritizrPenalties plt;
    public PrioritizrConstraints cst;
    public PrioritizrDecisions dcs;
    public PrioritizrSolver slv;

    public PrioritizrProblem(
            String units,
            String features,
            PrioritizrObjective obj,
            PrioritizrTargets tgt,
            PrioritizrPenalties plt,
            PrioritizrConstraints cst,
            PrioritizrDecisions dcs,
            PrioritizrSolver slv
            ){
        this.setUnits(units);
        this.setFeatures(features);
        this.setObj(obj);
        this.setTgt(tgt);
        this.setPlt(plt);
        this.setCst(cst);
        this.setDcs(dcs);
        this.setSlv(slv);
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setObj(PrioritizrObjective obj) {
        this.obj = obj;
    }

    public void setTgt(PrioritizrTargets tgt) {
        this.tgt = tgt;
    }

    public void setPlt(PrioritizrPenalties plt) {
        this.plt = plt;
    }

    public void setCst(PrioritizrConstraints cst) {
        this.cst = cst;
    }

    public void setDcs(PrioritizrDecisions dcs) {
        this.dcs = dcs;
    }

    public void setSlv(PrioritizrSolver slv) {
        this.slv = slv;
    }
}
