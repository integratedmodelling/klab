package org.integratedmodelling.ml.legacy.riskwiz.jtree;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;


public class JTVertex {
	
    public static int counter = 0;
	
    protected Set<BNNode> clique;
    protected int weight = 1;
	
    protected String name;
	
    public boolean isMarked = false;
	
    public JTVertex(Set<BNNode> cl) {
        name = "v" + (counter++);
        this.clique = cl;
		
        for (BNNode vertex : cl) {
            weight *= vertex.getWeight();
        }		
		 
    }	 
	
    public Set<BNNode> getClique() {
        return clique;
    }

    public void setClique(Set<BNNode> clique) {
        this.clique = clique;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
	
    public boolean contains(BNNode n) {
        return clique.contains(n);
    }
	
    public boolean containsAll(Set<BNNode> nodes) {
        return clique.containsAll(nodes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
}
