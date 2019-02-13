package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import org.integratedmodelling.ml.legacy.riskwiz.influence.JTPotential;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTEdge;


public class SJTEdge extends JTEdge<SJTVertex> {
	
    /**
     * 
     */
    private static final long serialVersionUID = 6493326653445210741L;
	
    JTPotential potential;
	
    public SJTEdge() {
        super();		 
    }

    public SJTEdge(SJTVertex vt1, SJTVertex vt2) {
        super(vt1, vt2);		 
        potential = new JTPotential(sepset);
    }

    public JTPotential getPotential() {
        return potential;
    }
	
    public void setVacious() {
        potential.setVacious();
    }

    public void setPotential(JTPotential potential) {
        this.potential = potential;
    }

}
