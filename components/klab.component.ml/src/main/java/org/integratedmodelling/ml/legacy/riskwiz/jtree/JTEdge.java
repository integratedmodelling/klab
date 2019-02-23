package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.jgrapht.graph.DefaultEdge;


public class JTEdge<V extends JTVertex> extends DefaultEdge {
	
    /**
     * 
     */
    private static final long serialVersionUID = 6493326653445210741L;
	
    protected Set<BNNode> sepset;
    protected int cost = 0;
    protected int mass = 0;
    protected V sourceVertex;
    protected V targetVertex;
	
    public JTEdge() {
        super();		 
    }

    public JTEdge(V vt1, V vt2) {
        super();
        setSepset(vt1, vt2);
    }

    public Set<BNNode> getSepset() {
        return sepset;
    }

    public void setSepset(V vt1, V vt2) {
        this.sourceVertex = vt1;
        this.targetVertex = vt2;
        this.sepset = intersection(vt1.getClique(), vt2.getClique());
        // this.mass=vt1.getClique().size()+vt2.getClique().size();
        this.cost = vt1.getWeight() + vt2.getWeight();
		 
    }

    public int getCost() {
        return cost;
    }

    public static Set<BNNode> intersection(Set<BNNode> clique1, Set<BNNode> clique2) {
		
        Set<BNNode> intersect = new HashSet<BNNode>(); // clique1 );

        for (BNNode node : clique2) {
            if (clique1.contains(node)) {
                intersect.add(node);
            }
        }
        // intersect.retainAll(clique2);		  
        return intersect;
    }

    public int getMass() {
        return sepset.size();
    }

    public V getSourceVertex() {
        return sourceVertex;
    }

    public V getTargetVertex() {
        return targetVertex;
    }

}
