package org.integratedmodelling.ml.legacy.riskwiz.influence.jensen;


import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNEdge;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.discretizer.Discretizer;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.IJoinTreeDecisionCompiler;


public class StrongJoinTreeCompiler implements IJoinTreeDecisionCompiler<SJTVertex> {

    public StrongJoinTreeCompiler() {// TODO Auto-generated constructor stub
    }

    // do it one step taking set of cliques as an input
    public StrongJoinTree execute(SortedSet<SJTVertex> cliqueVertexes, BeliefNetwork bn) {

        // create empty beliefNetwork which represents a set of non connected
        // trees
        StrongJoinTree strongJoinTree = new StrongJoinTree(bn);
        SJTVertex root = cliqueVertexes.first();

        strongJoinTree.addVertex(root);
        strongJoinTree.setRoot(root);
        SortedSet<SJTVertex> tailCliqueVertexes = new TreeSet<SJTVertex>();

        tailCliqueVertexes.addAll(cliqueVertexes);
        tailCliqueVertexes.remove(root);
		 
        for (SJTVertex vertexi : tailCliqueVertexes) {
            strongJoinTree.addVertex(vertexi);
            SJTVertex vertexj = getTargetVertex(vertexi, cliqueVertexes);
			 
            SJTEdge edge = new SJTEdge(vertexi, vertexj);

            strongJoinTree.addEdge(vertexi, vertexj, edge);
			 
        }

        return strongJoinTree;
    }
	
    private SJTVertex getTargetVertex(SJTVertex vertexi, SortedSet<SJTVertex> cliqueVertexes) {
        SortedSet<SJTVertex> headSet = cliqueVertexes.headSet(vertexi);
        Set<BNNode> Si = getSi(vertexi, headSet);
		
        for (SJTVertex vertexj : headSet) {
 
            if (vertexj.getClique().containsAll(Si)) {
                return vertexj;
            }
			 
        }
		
        return null;
    }
	
    private Set<BNNode> getSi(SJTVertex vertexi, SortedSet<SJTVertex> headSet) {
		
        Set<BNNode> si = new HashSet<BNNode>();

        for (SJTVertex vertex : headSet) {
            si.addAll(vertex.getClique());
        }
        si.retainAll(vertexi.getClique());
        return si;
    }

    // do it all the way from the BN beliefNetwork

    @Override
	public StrongJoinTree execute(BeliefNetwork beliefNetwork) throws Exception {
		
        Discretizer.discretizeNetwork(beliefNetwork); 

        Moralization  moralizationAlg = new Moralization();		
		
        RiskUndirectedGraph<BNNode, BNEdge> moralGraph = moralizationAlg.execute(
                beliefNetwork);
		
        PartialOrder partialOrderAlg = new PartialOrder();

        partialOrderAlg.execute(beliefNetwork);

        OrderedTriangulation triangulationAlg = new OrderedTriangulation();

        triangulationAlg.execute(moralGraph, partialOrderAlg.getOredredSets(),
                partialOrderAlg.getOredredDecisionNodes());
        SortedSet<SJTVertex> cliqueVertexes = triangulationAlg.getCliques();

        return execute(cliqueVertexes, beliefNetwork);
    }

}
