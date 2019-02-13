/**
 * JTCompilerDebugger.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Jan 9, 2009
 *
 * ----------------------------------------------------------------------------------
 * This file is part of riskwiz-cvars.
 * 
 * riskwiz-cvars is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * riskwiz-cvars is distributed in the hope that it will be useful,
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
 * @copyright 2009 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Jan 9, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.debugger;


import java.util.Set;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNEdge;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.graph.RiskUndirectedGraph;
import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JTEdgeHugin;
import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JTVertexHugin;
import org.integratedmodelling.ml.legacy.riskwiz.inference.ls.JoinTree;


/**
 * @author Sergey Krivov
 * 
 */
public class JTCompilerDebugger implements IJTCompilerDebugger {

    public boolean debugTriangulatedGraph;
    public boolean debugJoinTree;
    public boolean debugBN;

    /**
     * @author Sergey Krivov
     * 
     */
    public interface IJTCompilerDebugger {}

    /**
     * 
     */
    public JTCompilerDebugger() {// TODO Auto-generated constructor stub
    }

    @Override
	public void displayTriangulatedGraph(
            RiskUndirectedGraph<BNNode, BNEdge> graph) {
        println("Triangulated Graph");
        println("Nodes:");
        Set<BNNode> nodes = graph.vertexSet();

        for (BNNode bNNode : nodes) {
            displayTriangulatedGraphNode(bNNode);
        }
        println("");
		
        println("Edges:");
        Set<BNEdge> edges = graph.edgeSet();

        for (BNEdge edge : edges) {
            displayTriangulatedGraphEdge(graph, edge);
            println("");
        }

    }

    public void displayTriangulatedGraphNode(BNNode n) {
        print(n.getName() + "; ");
    }

    public void displayTriangulatedGraphEdge(
            RiskUndirectedGraph<BNNode, BNEdge> graph, BNEdge edge) {
        print(graph.getEdgeSource(edge).getName());
        print("<--->");
        print(graph.getEdgeTarget(edge).getName());

    }
	
    @Override
	public void displayBNGraph(BeliefNetwork graph) {
        println("BN Graph");
        println("Nodes:");
        Set<BNNode> nodes = graph.vertexSet();

        for (BNNode bNNode : nodes) {
            displayBNNode(bNNode);
        }
        println("");
        println("Edges:");
        Set<BNEdge> edges = graph.edgeSet();

        for (BNEdge edge : edges) {
            displayBNEdge(graph, edge);
            println("");
        }

    }

    public void displayBNNode(BNNode n) {
        print(n.getName() + "; ");
    }

    public void displayBNEdge(BeliefNetwork graph, BNEdge edge) {
        print(graph.getEdgeSource(edge).getName());
        print("--->");
        print(graph.getEdgeTarget(edge).getName());

    }

    @Override
	public void displayJoinTree(JoinTree jtree) {
        println("Join Tree");
        println("Edges:");
        Set<JTEdgeHugin> edges = jtree.edgeSet();

        for (JTEdgeHugin edge : edges) {
            displayJoinTreeEdge(jtree, edge);
            println("");
        }
		
        println("Nodes:");
        Set<JTVertexHugin> nodes = jtree.vertexSet();

        for (JTVertexHugin v : nodes) {
            displayJoinTreeNode(v);
            println("");
        }
		
    }

    // JTVertexHugin, JTEdgeHugin

    public void displayJoinTreeNode(JTVertexHugin v) {
        print(v.getName() + ": ");
 
        Set<BNNode> nodes = v.getClique();

        for (BNNode bNNode : nodes) {
            print(bNNode.getName() + ", ");
        }
		
    }

    public void displayJoinTreeEdge(JoinTree jtree, JTEdgeHugin edge) {
        print(jtree.getEdgeSource(edge).getName());
        print("<--->");
        print(jtree.getEdgeTarget(edge).getName());
        println("");

    }

    @Override
	public boolean showJoinTree() {
        return debugJoinTree;
    }

    @Override
	public boolean showTriangulatedGraph() {
        return debugTriangulatedGraph;
    }
	
    @Override
	public boolean showBN() {
        return debugBN;
    }
	
    @Override
	public void doAll() {
        debugJoinTree = true;
        debugTriangulatedGraph = true;
        debugBN = true;
    }

    public void println(String str) {
        System.out.println(str);
    }
	
    public void print(String str) {
        System.out.print(str);
    }

}
