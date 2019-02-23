package org.integratedmodelling.ml.legacy.riskwiz.inference.ls;


import java.util.Hashtable;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTVertex;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.TableFactory;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FMarginalizationMap;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.SubtableFastMap2;


public class JTVertexHugin extends JTVertex {
	
    // private Set<BNNode> clique;
    // private int weight =1;
	
    private PT pT;
	
    // public boolean isMarked =false;
	
    Hashtable< JTEdgeHugin, FMarginalizationMap> margMapHash;
    Hashtable< JTEdgeHugin, SubtableFastMap2> subtableOpMapHash;
	
    public JTVertexHugin(Set<BNNode> cl) {
        super(cl);
 
        createPT(cl);
		
        margMapHash = new Hashtable< JTEdgeHugin, FMarginalizationMap>();
        subtableOpMapHash = new Hashtable<JTEdgeHugin, SubtableFastMap2>();
    }
	
    public PT getPt() {
        return pT;
    }

    public void setPt(PT pT) {
        this.pT = pT;
    }
	
    public void  createPT(Set<BNNode> cl) {
        pT = TableFactory.createPT(cl);
    }
	
    public void setAll(double val) {
        pT.setAll(val);
    }
 
    public FMarginalizationMap  getFMarginalizationMap(JTEdgeHugin jtedge) {
        return margMapHash.get(jtedge);
    } 
	
    public SubtableFastMap2  getSubtableOpFastMap(JTEdgeHugin jtedge) {
        return subtableOpMapHash.get(jtedge);
    }  
	
    public void resetMargHash() {
        margMapHash = new Hashtable< JTEdgeHugin, FMarginalizationMap>();
        subtableOpMapHash = new Hashtable<JTEdgeHugin, SubtableFastMap2 >();
    }
	
    public void createFMarginalizationMap(JTEdgeHugin jtedge) {
		 
        // Vector<DiscreteDomain> domainProduct = pT.getDomainProduct() ;		
        // Vector<DiscreteDomain> sepsetDomainProduct = jtedge.getPt().getDomainProduct() ;
		 
        FMarginalizationMap mmap = new FMarginalizationMap(pT.getDomainProduct(),
                jtedge.getPt().getDomainProduct());

        margMapHash.put(jtedge, mmap);
    }
	
    public void createSubtableOpFastMap2(JTEdgeHugin jtedge) {
        Vector<DiscreteDomain> domainProduct = pT.getDomainProduct();
        Vector<DiscreteDomain> sepsetDomainProduct = jtedge.getPt().getDomainProduct();
        SubtableFastMap2 fmap2 = new SubtableFastMap2(domainProduct,
                sepsetDomainProduct);

        subtableOpMapHash.put(jtedge, fmap2);
    }
	
    public void createFastMaps(JTEdgeHugin jtedge) {
        createSubtableOpFastMap2(jtedge);
        createFMarginalizationMap(jtedge);
    }

}
