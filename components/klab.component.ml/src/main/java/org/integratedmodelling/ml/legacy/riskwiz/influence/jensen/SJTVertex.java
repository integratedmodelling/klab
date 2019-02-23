package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import java.util.Comparator;
import java.util.Hashtable;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.influence.JTPotential;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTVertex;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FMarginalizationMap;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.SubtableFastMap2;


public class SJTVertex extends JTVertex implements Comparable<SJTVertex> {
	
    protected int count;
	
    JTPotential potential;
	
    public boolean isMarked = false;
	
    Hashtable< SJTEdge, Vector<Object>> margMapHash;
    Hashtable< SJTEdge, SubtableFastMap2> subtableOpMapHash;
    Hashtable< SJTEdge, FMarginalizationMap> margMapHashPN;
	
    public SJTVertex(Set<BNNode> cl) {
        super(cl);
        potential = new JTPotential(cl);
		
        margMapHash = new Hashtable< SJTEdge, Vector<Object>>();
        subtableOpMapHash = new Hashtable<SJTEdge, SubtableFastMap2 >();
    }
	
    public SJTVertex(Set<BNNode> cl, int count) {
        this(cl);
        this.count = count;
    }
	
    public Vector<Object>  getMarginalizationFastMap(SJTEdge jtedge) {
        return margMapHash.get(jtedge);
    } 
	
    public SubtableFastMap2  getSubtableOpFastMap(SJTEdge jtedge) {
        return subtableOpMapHash.get(jtedge);
    }  
	
    public void resetMargHash() {
        margMapHash = new Hashtable< SJTEdge, Vector<Object>>();
        subtableOpMapHash = new Hashtable<SJTEdge, SubtableFastMap2 >();
        margMapHashPN = new Hashtable< SJTEdge, FMarginalizationMap>();
    }
	
    public void createMarginalizationFastMap(SJTEdge jtedge) {
		 
        SortedSet<BNNode> marginals = new TreeSet<BNNode>(new EliminationOrder());

        marginals.addAll(clique);
        marginals.removeAll(jtedge.getSepset());
        Vector<Object> mmaps = new Vector<Object>();
        Vector<DiscreteDomain> currentDomainProduct = new Vector<DiscreteDomain>();

        currentDomainProduct.addAll(potential.getDomainProduct());
		
        // System.out.println("\n Marginalssize: "+marginals.size()+ " \n" ); 
        while (!marginals.isEmpty()) {
            Vector<DiscreteDomain> domsSet = new Vector<DiscreteDomain>();

            while (!marginals.isEmpty() && marginals.first().isNature()) {
                BNNode first = marginals.first();

                domsSet.add(first.getDiscretizedDomain());
                marginals.remove(first);
            }
            if (!domsSet.isEmpty()) {
				 
                FMarginalizationMap mmap = new FMarginalizationMap(
                        currentDomainProduct, domsSet);

                mmaps.add(mmap);
                currentDomainProduct.removeAll(domsSet);
            }
            while (!marginals.isEmpty() && marginals.first().isDecision()) {
				  
                BNNode first = marginals.first();
				
                mmaps.add(first.getDomain());
                currentDomainProduct.remove(first.getDomain());
                marginals.remove(first);
            }
        }	
        // System.out.println("\n RespectiveMapsize: "+mmaps.size()+ " \n" ); 
        margMapHash.put(jtedge, mmaps);
    }
	
    public void createMarginalizationFastMapPN(SJTEdge  jtedge) {
		 
        Vector<DiscreteDomain> domainProduct = potential.getDomainProduct();		
        Vector<DiscreteDomain> sepsetDomainProduct = jtedge.getPotential().getDomainProduct();
		 
        FMarginalizationMap mmap = new FMarginalizationMap(
                potential.getDomainProduct(), sepsetDomainProduct);

        margMapHashPN.put(jtedge, mmap);
    }
	
    public void createSubtableOpFastMap2(SJTEdge jtedge) {
        Vector<DiscreteDomain> domainProduct = potential.getDomainProduct();
        Vector<DiscreteDomain> sepsetDomainProduct = jtedge.getPotential().getDomainProduct();
        SubtableFastMap2 fmap2 = new SubtableFastMap2(domainProduct,
                sepsetDomainProduct);

        subtableOpMapHash.put(jtedge, fmap2);
    }
	
    public void createFastMaps(SJTEdge jtedge) {
        createSubtableOpFastMap2(jtedge);
        createMarginalizationFastMap(jtedge);
    }
	
    public void createFastMapsPN(SJTEdge jtedge) {
        createSubtableOpFastMap2(jtedge);
        createMarginalizationFastMapPN(jtedge);
    }
	
    public FMarginalizationMap  getFMarginalizationMapPN(SJTEdge  jtedge) {
        return margMapHashPN.get(jtedge);
    } 

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
	public int compareTo(SJTVertex o) {
		 
        return (this.getCount() - o.getCount());
    }

    public JTPotential getPotential() {
        return potential;
    }
	
    public void setVacious() {
        potential.setVacious();
    }
	
    public static class EliminationOrder implements Comparator<BNNode> {

        @Override
		public int compare(BNNode arg0, BNNode arg1) {
            // TODO Auto-generated method stub
            return  arg1.getCount() - arg0.getCount();
        }
	 
    }

}
