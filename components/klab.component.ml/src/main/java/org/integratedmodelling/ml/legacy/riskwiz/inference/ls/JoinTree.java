/**
 * StrongJoinTree.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Feb 19, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RiskWiz is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------------------------------------------
 * 
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Feb 19, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.inference.ls;


import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DomainFactory;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.IJoinTree;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTEdge;
import org.integratedmodelling.ml.legacy.riskwiz.pt.PT;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.DomainMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FMarginalizationMap;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.FastMap2;
import org.integratedmodelling.ml.legacy.riskwiz.pt.map.SubtableFastMap2;


/**
 * @author Sergey Krivov
 * 
 */

/**
 * @author Sergey Krivov
 * 
 */
public class JoinTree extends RiskUndirectedGraph<JTVertexHugin, JTEdgeHugin>
        implements IJoinTree<JTVertexHugin> {

    BeliefNetwork bn;

    Hashtable<BNNode, ClusterBundle> clusterHash;

    public JoinTree(BeliefNetwork bn) {
        super(JTEdgeHugin.class);
        this.bn = bn;
    }

    @Override
	public void initializeStructiure() {
        Set<JTVertexHugin> verttset = this.vertexSet();

        // initialize clusters
        for (JTVertexHugin vertex : verttset) {
            vertex.setAll(1);
        }
        // initialize spesets
        Set<JTEdgeHugin> edgeset = this.edgeSet();

        for (JTEdgeHugin edge : edgeset) {
            edge.setAll(1);
        }

        // create correspondence between Belif nodes and clusters (vertexes of
        // StrongJoinTree)
        clusterHash = new Hashtable<BNNode, ClusterBundle>();

        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                boolean debugInit = false;
                JTVertexHugin parentCluster = assignParentCluster(node);

                PT clusterPT = parentCluster.getPt();
                PT nodePT = node.getDiscreteCPT();
                SubtableFastMap2 fmap = clusterPT.createSubtableFastMap(nodePT);
                FMarginalizationMap mfmap = new FMarginalizationMap(
                        clusterPT.getDomainProduct(),
                        node.getDiscretizedDomain());
						 
                FastMap2 liklihoodfmap = clusterPT.createSubtableFastMap(
                        DomainFactory.createDomainProduct(
                                node.getDiscretizedDomain()));

                clusterHash.put(node,
                        new ClusterBundle(parentCluster, fmap, mfmap,
                        liklihoodfmap));
                // if (debugInit)
                // System.out.println("clusterPT  \n" + clusterPT.toString()
                // + "\n");
                // if (debugInit)
                // System.out.println("nodePT  \n" + nodePT.toString() + "\n");

                clusterPT.multiplyBySubtable(nodePT, fmap);

                // if (debugInit)
                // System.out.println("clusterPT after mutiplication\n"
                // + clusterPT.toString() + "\n");
                // node.getPrior().setAll(1);
            }
        }

        // compile maps for fast operations
        for (JTVertexHugin vertex : verttset) {
            Set<JTEdgeHugin> edgesOfvertex = this.edgesOf(vertex);

            for (JTEdgeHugin jtedge : edgesOfvertex) {
                vertex.createFastMaps(jtedge);
            }
        }

    }

    // StrongJoinTree map structure has to be built before calling initialize()
    @Override
	public void initialize() {
        // boolean init=false;
        Set<JTVertexHugin> verttset = this.vertexSet();

        // initialize clusters
        for (JTVertexHugin vertex : verttset) {
            vertex.setAll(1);
        }
        // initialize spesets
        Set<JTEdgeHugin> edgeset = this.edgeSet();

        for (JTEdgeHugin edge : edgeset) {
            edge.setAll(1);
        }

        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                ClusterBundle bundle = clusterHash.get(node);
                JTVertexHugin parentCluster = bundle.jtcluster;
                PT clusterPT = parentCluster.getPt();
                PT nodePT = node.getDiscreteCPT();
                SubtableFastMap2 fmap = (SubtableFastMap2) bundle.fopmap;

                // FMarginalizationMap mfmap = bundle.mfmap;
                // FastMap2 liklihoodfmap = bundle.liklihoodfmap;
                // if(init)System.out.println( "clusterPT \n"+
                // clusterPT.toString() + "\n");
                // if(init)System.out.println( "nodePT \n"+
                // nodePT.toString() + "\n");

                clusterPT.multiplyBySubtableFast(nodePT, fmap);

                // if(init)System.out.println( "clusterPT after
                // mutiplication\n"+
                // clusterPT.toString() + "\n");
            }

        }

    }

    @Override
	public void initializeLikelihoods() {
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                initializeLikelihood(node);
            }
        }

    }

    public void initializeLikelihood(BNNode node) {
        ClusterBundle bundle = clusterHash.get(node);
        JTVertexHugin parentCluster = bundle.jtcluster;
        PT clusterPT = parentCluster.getPt();

        if (node.hasEvidence()) {
			 
            // cross fingers
            if (bundle.liklihoodfmap instanceof SubtableFastMap2) {
                clusterPT.multiplyBySubtableFast(node.getEvidence(),
                        (SubtableFastMap2) bundle.liklihoodfmap);
            } else {
                clusterPT.multiplyBySubtable(node.getEvidence(),
                        bundle.liklihoodfmap);
            }

        }
    }

    @Override
	public void propagateEvidence(JTVertexHugin X) {
		
        unmarkAll();
        collectEvidence(X);
        unmarkAll();
        distributeEvidence(X);
    }

    @Override
	public void propagateEvidence(BNNode node) {
        ClusterBundle bundle = clusterHash.get(node);
        JTVertexHugin parentCluster = bundle.jtcluster;

        PT clusterPT = parentCluster.getPt();

        clusterPT.multiplyBySubtable(node.getEvidence(), bundle.liklihoodfmap);
        unmarkAll();
        collectEvidence(parentCluster);
        unmarkAll();
        distributeEvidence(parentCluster);
    }

    @Override
	public void propagateEvidence() {
        Iterator it = this.vertexSet().iterator();

        JTVertexHugin vtx = (JTVertexHugin) it.next();

        this.propagateEvidence(vtx);
    }

    @Override
	public void collectEvidence(JTVertexHugin X) {
        X.isMarked = true;
        Set<JTVertexHugin> neighbours = this.getNeighbors(X);

        for (JTVertexHugin neighbor : neighbours) {
            if (!neighbor.isMarked) {
                collectEvidence(neighbor);
                passMessage(neighbor, X);
                // System.out.println("Collect from "+ neighbor.getName()+ "to "+ X.getName());
            }
			
        }

    }

    @Override
	public void distributeEvidence(JTVertexHugin X) {
        X.isMarked = true;
        Set<JTVertexHugin> neighbours = this.getNeighbors(X);

        for (JTVertexHugin neighbor : neighbours) {
            if (!neighbor.isMarked) {
                passMessage(X, neighbor);
                // System.out.println("Distribute from "+ X.getName()+ "to "+neighbor.getName());
                distributeEvidence(neighbor);

            }
        }

    }

    @Override
	public void passMessage(JTVertexHugin source, JTVertexHugin target) {

        JTEdgeHugin jtedge = this.getEdge(source, target);
        PT sepsetPT = jtedge.getPt();
		 
        // System.out.println("was \n"+ sepsetPT.toString());

        PT oldSepsetPT = sepsetPT.clone();
        PT sourcePT = source.getPt();

        FMarginalizationMap mmap = source.getFMarginalizationMap(jtedge);

        PT.marginalizeDomainsFast(sepsetPT, sourcePT, mmap);
	 	 
        // System.out.println("become \n"+ sepsetPT.toString());
        PT targetPT = target.getPt();
		 
        SubtableFastMap2 fmap2 = target.getSubtableOpFastMap(jtedge);

        targetPT.multiplyAndDivideBySubtableFast(sepsetPT, oldSepsetPT, fmap2);
		 
    }

    public void unmarkAll() {
        Set<JTVertexHugin> vertexSet = this.vertexSet();

        for (JTVertexHugin vertex : vertexSet) {
            vertex.isMarked = false;
        }
    }

    public void setNodeMarginals() {
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                ClusterBundle cbundle = clusterHash.get(node);
                JTVertexHugin jtcluster = cbundle.jtcluster;
                FMarginalizationMap mfmap = cbundle.mfmap;
                PT marginal = new PT(mfmap.getProjectionDomainProduct());

                PT.marginalizeDomainsFast(marginal, jtcluster.getPt(), mfmap);
                // MarginalizationDomainMap mfmap = cbundle.mfmap;
                // node.setMarginal(PT.marginalizeDomains(jtcluster.getPt(),
                // mfmap));
                node.setMarginal(marginal);
            }

        }
    }

    @Override
	public void setNodeConditionalMarginals() {
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                ClusterBundle cbundle = clusterHash.get(node);
                JTVertexHugin jtcluster = cbundle.jtcluster;
                FMarginalizationMap mfmap = cbundle.mfmap;

                PT marginal = new PT(mfmap.getProjectionDomainProduct());

                PT.marginalizeDomainsFast(marginal, jtcluster.getPt(), mfmap);
                // MarginalizationDomainMap mfmap = cbundle.mfmap;
                // PT marginal = PT.marginalizeDomains(jtcluster.getPt(),
                // mfmap);
                marginal.normalize();
                node.setMarginal(marginal);
            }

        }
    }

    private JTVertexHugin assignParentCluster(BNNode node) {
        Set<BNNode> family = new HashSet<BNNode>();

        family.add(node);
        family.addAll(bn.getParents(node));
        JTVertexHugin v = null;
        Set<JTVertexHugin> verttset = this.vertexSet();

        for (JTVertexHugin vertex : verttset) {
            if (vertex.containsAll(family)) {
                return vertex;
            }
        }
        return v;
    }

    private class ClusterBundle {
        private JTVertexHugin jtcluster;

        private DomainMap2 fopmap;

        private DomainMap2 liklihoodfmap;

        private FMarginalizationMap mfmap;

        public ClusterBundle(JTVertexHugin jtcluster, DomainMap2 fmap,
                FMarginalizationMap mfmap, DomainMap2 liklihoodfmap) {
            this.fopmap = fmap;
            this.jtcluster = jtcluster;
            this.mfmap = mfmap;
            this.liklihoodfmap = liklihoodfmap;
        }

    }

    @Override
	public BeliefNetwork getBeliefNetwork() {
        return bn;
    }

    public void dump() {

        System.out.println("Join Tree");
        System.out.println("Edges:");
        Set<JTEdgeHugin> edges = this.edgeSet();

        for (JTEdgeHugin edge : edges) {
            System.out.print(
                    this.getEdgeSource(edge).getName());
            System.out.print("<--->");
            System.out.print(
                    this.getEdgeTarget(edge).getName());
            System.out.println("");

            System.out.println("");
        }

        System.out.println("Nodes:");
        Set<JTVertexHugin> nodes = this.vertexSet();

        for (JTVertexHugin v : nodes) {
            System.out.print(v.getName() + ": ");

            printNodes(v.getClique());
        }

    }

    public void check() {

        System.out.println("Join Tree check");

        Set<JTEdgeHugin> edges = this.edgeSet();

        for (JTEdgeHugin edge : edges) {

            JTVertexHugin source = (this.getEdgeSource(edge));
            JTVertexHugin target = (this.getEdgeTarget(edge));
            Set<BNNode> edgenodes = edge.getSepset();

            if (!source.getClique().containsAll(edgenodes)
                    || !target.getClique().containsAll(edgenodes)
                    || edgenodes.isEmpty()) {
                System.out.println(
                        "Error in" + source.getName() + "<-->"
                        + target.getName());
                System.out.print(source.getName() + " Vertex nodes:");
                printNodes(source.getClique());
                System.out.println("SepsetNodes:");
                printNodes(edgenodes);
                System.out.print(target.getName() + " Vertex nodes:");
                printNodes(source.getClique());
                System.out.println("");
            }

        }

    }

    public void printNodes(Set<BNNode> bnnodes) {
        for (BNNode bNNode : bnnodes) {
            System.out.print(bNNode.getName() + ", ");
        }
        System.out.println("");
    }
	
    public void checkClusterAssignment() {
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                ClusterBundle cbundle = clusterHash.get(node);
                JTVertexHugin jtcluster = cbundle.jtcluster;

                System.out.println(node.getName() + ": ");
				
                printNodes(jtcluster.getClique());
				
            }

        }
		
    }
	
    public void checkClusterAssignment2() {
		
        Set<JTVertexHugin> verts = new HashSet<JTVertexHugin>();

        verts.addAll(this.vertexSet()); 
        Set<BNNode> bNNodes = bn.vertexSet();

        for (BNNode node : bNNodes) {
            if (node.isNature()) {
                ClusterBundle cbundle = clusterHash.get(node);
                JTVertexHugin jtcluster = cbundle.jtcluster;

                verts.remove(jtcluster);
                System.out.println(node.getName() + ":  ");
                System.out.print(jtcluster.getName() + ": ");
                printNodes(jtcluster.getClique());
				
            }

        }
		
        System.out.println("Not Assigned:");
		
        for (JTVertexHugin v : verts) {
            System.out.println(v.getName());
            printNodes(v.getClique());
        }
		
    }
	
    public void printCluster(String name) {
        for (JTVertexHugin v : this.vertexSet()) {
            if (v.getName().equalsIgnoreCase(name)) {
                System.out.println(v.getName());
                System.out.println(v.getPt().toString());
            }
        }
    }
	
    public void printEdges() {
        for (JTEdgeHugin e : this.edgeSet()) {
            System.out.println(
                    "source: " + e.getSourceVertex().getName() + "  :");
            printNodes(e.getSourceVertex().getClique());
            System.out.println("edge has nodes: ");
            printNodes(e.getSepset()); 
            System.out.println("target: " + e.getTargetVertex().getName() + " :");
            printNodes(e.getTargetVertex().getClique());
			 
        }
    }

    public void checkJTProperty() {
        JTVertexHugin[] cliqueVertexesArray = new JTVertexHugin[this.vertexSet().size()];

        this.vertexSet().toArray(cliqueVertexesArray);

        if (cliqueVertexesArray.length > 1) {
            for (int i = 0; i < cliqueVertexesArray.length; i++) {
                for (int j = i + 1; j < cliqueVertexesArray.length; j++) {
                    Set<BNNode> sepset = JTEdge.intersection(
                            cliqueVertexesArray[i].getClique(),
                            cliqueVertexesArray[j].getClique());

                    if (!sepset.isEmpty()) {
                        checkPath(cliqueVertexesArray[i], cliqueVertexesArray[j],
                                sepset);
                    }
                }
            }
        }
    }
	
    public void checkNeighbours() {
        Set<JTVertexHugin> vertex = this.vertexSet();

        for (JTVertexHugin v : vertex) {
            Set<JTVertexHugin> neighbours = this.getNeighbors(v);

            System.out.println(v.getName() + "  has neighbors:");
            for (JTVertexHugin jtVertexHugin : neighbours) {
                System.out.print(jtVertexHugin.getName() + ",");
				
            }
            System.out.println("");
        }
		 
    }
	
    public void checkNormalization() {
        Set<JTVertexHugin> vertex = this.vertexSet();

        for (JTVertexHugin v : vertex) {
            System.out.print(v.getName() + ", sum is  ");
            System.out.println(v.getPt().sum());
            System.out.println("");
        }
		 
    }

    private void checkPath(JTVertexHugin source, JTVertexHugin target,
            Set<BNNode> sepset) {
        Set<JTVertexHugin> neighbours = this.getNeighbors(source);

        for (JTVertexHugin v : neighbours) {
            Vector<JTVertexHugin> visited = new Vector<JTVertexHugin>();

            visited.add(source);
            visited.add(v);
			 
            if (v.getClique().containsAll(sepset)) {
                checkPath(source, v, target, sepset, true, visited);
            } else {
                checkPath(source, v, target, sepset, false, visited);
            }
        }

    }

    private void checkPath(JTVertexHugin source, JTVertexHugin temp,
            JTVertexHugin target, Set<BNNode> sepset, boolean report,
            Vector<JTVertexHugin> visited) {
		
        if (temp == target) {
            if (report) {
                System.out.println("OK:");
                reportPath(visited);
                return;
            } else {
                System.out.println("Error:");
                printNodes(sepset);
                reportPath(visited);
                return;
            }
			
        }
		
        Set<JTVertexHugin> neighbours = this.getNeighbors(temp);

        for (JTVertexHugin v : neighbours) {
            if (!visited.contains(v)) {
                Vector<JTVertexHugin> visited2 = new Vector<JTVertexHugin>();

                visited2.addAll(visited);
                visited2.add(v);
                if (v.getClique().containsAll(sepset)) {
                    checkPath(source, v, target, sepset, report, visited2);
                } else {
                    checkPath(source, v, target, sepset, false, visited2);
                }
            }
			 
        }

    }
	
    public void reportPath(Vector<JTVertexHugin> visited) {
		
        for (JTVertexHugin jtVertexHugin : visited) {
            System.out.print(jtVertexHugin.getName() + ",");
            printNodes(jtVertexHugin.getClique());
			
        }
        System.out.println("");
		
    }

}
