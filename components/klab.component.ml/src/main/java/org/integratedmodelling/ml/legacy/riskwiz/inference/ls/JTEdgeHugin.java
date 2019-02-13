package org.integratedmodelling.ml.legacy.riskwiz.inference.ls;


import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTEdge;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;


public class JTEdgeHugin extends JTEdge<JTVertexHugin> {
	
    /**
     * 
     */
    private static final long serialVersionUID = 6493326653445210741L;
	
    private PT pT;
	
    public JTEdgeHugin() {
        super();		 
    }

    public JTEdgeHugin(JTVertexHugin vt1, JTVertexHugin vt2) {
        super();
        setSepset(vt1, vt2);
    }

    @Override
	public void setSepset(JTVertexHugin vt1, JTVertexHugin vt2) {
        super.setSepset(vt1, vt2);
        createPT();
    }
	 
    public PT getPt() {
        return pT;
    }

    public void setPt(PT pT) {
        this.pT = pT;
    } 
	
    public void  createPT() {
        pT = TableFactory.createPT(getSepset());
    }
	
    public void setAll(double val) {
        pT.setAll(val);
    }

}
