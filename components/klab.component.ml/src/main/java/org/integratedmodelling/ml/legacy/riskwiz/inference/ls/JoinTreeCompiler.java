package org.integratedmodelling.ml.legacy.riskwiz.inference.ls;


import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNEdge;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.debugger.IJTCompilerDebugger;
import org.integratedmodelling.ml.legacy.riskwiz.discretizer.Discretizer;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.graph.algorithm.Moralization;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.IJoinTreeCompiler;
import org.integratedmodelling.ml.legacy.riskwiz.jtree.JTEdge;
import org.jgrapht.alg.ConnectivityInspector;


public class JoinTreeCompiler implements IJoinTreeCompiler<JTVertexHugin> {

    public JoinTreeCompiler() {// TODO Auto-generated constructor stub
    }

    // do it one step taking set of cliques as an input
    public JoinTree execute(Set<JTVertexHugin> cliqueVertexes, BeliefNetwork bn) {

        // create empty beliefNetwork which represents a set of non connected
        // trees
        JoinTree joinTree = new JoinTree(bn);
        // Create an empty set of Sepsets. Sepsets represented by class SJTEdge
        Set<JTEdgeHugin> sepsets = new HashSet<JTEdgeHugin>();

        // add vertexes to the non connected so far beliefNetwork
        for (JTVertexHugin vertex : cliqueVertexes) {
            joinTree.addVertex(vertex);
        }

        // initiate the set of spesets
        JTVertexHugin[] cliqueVertexesArray = new JTVertexHugin[cliqueVertexes.size()];

        cliqueVertexes.toArray(cliqueVertexesArray);
        if (cliqueVertexesArray.length > 1) {
            for (int i = 0; i < cliqueVertexesArray.length; i++) {
                for (int j = i + 1; j < cliqueVertexesArray.length; j++) {
                    Set<BNNode> sepset = JTEdge.intersection(
                            cliqueVertexesArray[i].getClique(),
                            cliqueVertexesArray[j].getClique());

                    if (!sepset.isEmpty()) {
                        sepsets.add(
                                new JTEdgeHugin(cliqueVertexesArray[i],
                                cliqueVertexesArray[j]));
                    }
                }
            }

            // initialize the PriorityQueue which will allwase keep the best
            // sepset
            // on the top
            PriorityQueue<JTEdgeHugin> pqueue = new PriorityQueue<JTEdgeHugin>(
                    sepsets.size() + 10, new SepsetComparator());

            pqueue.addAll(sepsets);

            // main loop
            // declare llop variables
            int n = cliqueVertexes.size() - 1;
            ConnectivityInspector<JTVertexHugin, JTEdgeHugin> cinspector = new ConnectivityInspector<JTVertexHugin, JTEdgeHugin>(
                    joinTree);
            JTEdgeHugin sepsetEdge = null;
            boolean treeWasModified = false;

            do {

                // each time the grapf (tree) was modified we recreate the
                // conectivity inspector.
                // Alternative to this would be to use JGrapht event system, but
                // it
                // seems
                // this would not add anithing significant in terms of
                // performance

                if (treeWasModified) {
                    cinspector = new ConnectivityInspector<JTVertexHugin, JTEdgeHugin>(
                            joinTree);
                    treeWasModified = false;
                }
                sepsetEdge = pqueue.poll();
                if (sepsetEdge != null) {
                    JTVertexHugin sourceVertex = sepsetEdge.getSourceVertex();
                    JTVertexHugin targetVertex = sepsetEdge.getTargetVertex();

                    if (!cinspector.pathExists(sourceVertex, targetVertex)) {
                        joinTree.addEdge(sourceVertex, targetVertex, sepsetEdge);
                        n--;
                        treeWasModified = true;
                    }
                }
            } while (n != 0);
        }
		
        ConnectivityInspector<JTVertexHugin, JTEdgeHugin> cinspector1 = new ConnectivityInspector<JTVertexHugin, JTEdgeHugin>(
                joinTree);
		
        if (cinspector1.isGraphConnected()) { 
            return joinTree;
        } else {
            return null;
        }
    }

    @Override
	public JoinTree execute(BeliefNetwork beliefNetwork) throws Exception {

        Discretizer.discretizeNetwork(beliefNetwork);

        Moralization<BNNode, BNEdge> moralizationAlg = new Moralization<BNNode, BNEdge>();

        RiskUndirectedGraph<BNNode, BNEdge> moralGraph = moralizationAlg.execute(
                beliefNetwork);

        Triangulation triangulationAlg = new Triangulation();

        triangulationAlg.execute(moralGraph);
        Set<JTVertexHugin> cliqueVertexes = triangulationAlg.getCliques();

        return execute(cliqueVertexes, beliefNetwork);
    }

    @Override
	public JoinTree execute(BeliefNetwork beliefNetwork,
            IJTCompilerDebugger debugger) throws Exception {

        Discretizer.discretizeNetwork(beliefNetwork);
        if (debugger.showBN()) {
            debugger.displayBNGraph(beliefNetwork);
        }

        Moralization<BNNode, BNEdge> moralizationAlg = new Moralization<BNNode, BNEdge>();

        RiskUndirectedGraph<BNNode, BNEdge> moralGraph = moralizationAlg.execute(
                beliefNetwork);

        Triangulation triangulationAlg = new Triangulation();
        RiskUndirectedGraph<BNNode, BNEdge> triangGraph = triangulationAlg.execute(
                moralGraph);

        if (debugger.showTriangulatedGraph()) {
            debugger.displayTriangulatedGraph(triangGraph);
        }

        Set<JTVertexHugin> cliqueVertexes = triangulationAlg.getCliques();
        JoinTree jointree = execute(cliqueVertexes, beliefNetwork);

        if (debugger.showJoinTree()) {
            debugger.displayJoinTree(jointree);
        }

        return jointree;
    }

    private class SepsetComparator implements Comparator<JTEdgeHugin> {

        /*
         * since our priority que keeps minimum on the top, when sepset 1 is be
         * better than sepset2 it should translate to sepset1 < sepse2 and
         * output of comparator is negative (non-Javadoc)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
		public int compare(JTEdgeHugin sepset1, JTEdgeHugin sepset2) {
            int mass1 = sepset1.getMass();
            int mass2 = sepset2.getMass();

            if (mass1 != mass2) {
                // we need to have sepset with larger mass to be smaller, so
                // that sepset with
                // largest mass is alwase selected
                // if mass1 is larger than mass2, the following is negative
                return   mass2 - mass1;
            } else {

                // if masses of sepsaets are same, then
                // sepset with the smallest cost has to be choosen
                // if cost1 is smaller tahn cost2 then the following is negative

                int cost1 = sepset1.getCost();
                int cost2 = sepset2.getCost();

                return     cost1 - cost2;

            }

        }

    }

}
