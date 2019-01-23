package org.integratedmodelling.mca.contrib.j_electre.cycles;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	JohnsonAlgorithm_ElementaryCyclesSearch: Algorithm by D. B. Johnson Finding all the elementary circuits of a directed graph. D. B. Johnson, SIAM Journal on Computing 4, no. 1, 77-84, 1975. http://dx.doi.org/10.1137/0204007
//	JohnsonAlgorithm_ElementaryCyclesSearch: Copyright @ 26.08.2006 by Frank Meyer - web@normalisiert.de or https://github.com/josch/cycles_johnson_meyer.
//	JohnsonAlgorithm_ElementaryCyclesSearch: Modified in 01.01.2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	This file is part of J-ELECTRE.
//
//	J-ELECTRE is free software: you can redistribute it and/or modify
//	it under the terms of the GNU General Public License as published by
//	the Free Software Foundation, either version 3 of the License, or
//	(at your option) any later version.
//
//	J-ELECTRE is distributed in the hope that it will be useful,
//	but WITHOUT ANY WARRANTY; without even the implied warranty of
//	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//	GNU General Public License for more details.
//
//	You should have received a copy of the GNU General Public License
//	along with J-ELECTRE.  If not, see <http://www.gnu.org/licenses/>.

import java.util.List;
import java.util.Vector;

import org.integratedmodelling.mca.contrib.j_electre.ui.Interface;

/**
 * Searchs all elementary cycles in a given directed graph. The implementation
 * is independent from the concrete objects that represent the graphnodes, it
 * just needs an array of the objects representing the nodes the graph
 * and an adjacency-matrix of type boolean, representing the edges of the
 * graph. It then calculates based on the adjacency-matrix the elementary
 * cycles and returns a list, which contains lists itself with the objects of the 
 * concrete graphnodes-implementation. Each of these lists represents an
 * elementary cycle.<br><br>
 *
 * The implementation uses the algorithm of Donald B. Johnson for the search of
 * the elementary cycles. For a description of the algorithm see:<br>
 * Donald B. Johnson: Finding All the Elementary Circuits of a Directed Graph.
 * SIAM Journal on Computing. Volumne 4, Nr. 1 (1975), pp. 77-84.<br><br>
 *
 * The algorithm of Johnson is based on the search for strong connected
 * components in a graph. For a description of this part see:<br>
 * Robert Tarjan: Depth-first search and linear graph algorithms. In: SIAM
 * Journal on Computing. Volume 1, Nr. 2 (1972), pp. 146-160.<br>
 * 
 * @author Frank Meyer, web_at_normalisiert_dot_de
 * @version 1.2, 22.03.2009
 *
 */
public class JohnsonAlgorithm_ElementaryCyclesSearch {
	/** List of cycles */
	private List<Vector<Object>> cycles = null;

	/** Adjacency-list of graph */
	private int[][] adjList = null;

	/** Graphnodes */
	private Object[] graphNodes = null;

	/** Blocked nodes, used by the algorithm of Johnson */
	private boolean[] blocked = null;

	/** B-Lists, used by the algorithm of Johnson */
	@SuppressWarnings("rawtypes")
	private Vector[] B = null;

	/** Stack for nodes, used by the algorithm of Johnson */
	private Vector<Integer> stack = null;

	/**
	 * Constructor.
	 *
	 * @param matrix adjacency-matrix of the graph
	 * @param graphNodes array of the graphnodes of the graph; this is used to
	 * build sets of the elementary cycles containing the objects of the original
	 * graph-representation
	 */
	public JohnsonAlgorithm_ElementaryCyclesSearch(boolean[][] matrix, Object[] graphNodes) {
		this.graphNodes = graphNodes;
		this.adjList = JohnsonAlgorithm_AdjacencyList.getAdjacencyList(matrix);
	}

	/**
	 * Returns List::List::Object with the Lists of nodes of all elementary
	 * cycles in the graph.
	 *
	 * @return List::List::Object with the Lists of the elementary cycles.
	 */
	public List<Vector<Object>> getElementaryCycles() {
		this.cycles = new Vector<Vector<Object>>();
		this.blocked = new boolean[this.adjList.length];
		this.B = new Vector[this.adjList.length];
		this.stack = new Vector<Integer>();
		JohnsonAlgorithm_StrongConnectedComponents sccs = new JohnsonAlgorithm_StrongConnectedComponents(this.adjList);
		int s = 0;

		while (true) {
			JohnsonAlgorithm_SCCResult sccResult = sccs.getAdjacencyList(s);
			if (sccResult != null && sccResult.getAdjList() != null) {
				@SuppressWarnings("rawtypes")
				Vector[] scc = sccResult.getAdjList();
				s = sccResult.getLowestNodeId();
				for (int j = 0; j < scc.length; j++) {
					if ((scc[j] != null) && (scc[j].size() > 0)) {
						this.blocked[j] = false;
						this.B[j] = new Vector<Object>();
					}
				}

				this.findCycles(s, s, scc);
				s++;
			} else {
				break;
			}
		}

		return this.cycles;
	}

	/**
	 * Calculates the cycles containing a given node in a strongly connected
	 * component. The method calls itself recursively.
	 *
	 * @param v
	 * @param s
	 * @param adjList adjacency-list with the subgraph of the strongly
	 * connected component s is part of.
	 * @return true, if cycle found; false otherwise
	 */
	@SuppressWarnings("unchecked")
	private boolean findCycles(int v, int s, @SuppressWarnings("rawtypes") Vector[] adjList) {
		boolean f = false;
		this.stack.add(new Integer(v));
		this.blocked[v] = true;
loop:
		for (int i = 0; i < adjList[v].size(); i++) {
			int w = ((Integer) adjList[v].get(i)).intValue();
			// found cycle
			if (w == s) {
				Vector<Object> cycle = new Vector<Object>();
				for (int j = 0; j < this.stack.size(); j++) {
					int index = this.stack.get(j).intValue();
					cycle.add(this.graphNodes[index]);
				}
				if (cycles.size()  + 1 > Interface.MaximumCycles){	
					break loop;
				}
				this.cycles.add(cycle);
				f = true;
			} else if (!this.blocked[w]) {
				if (this.findCycles(w, s, adjList)) {
					f = true;
				}
			}
		}

		if (f) {
			this.unblock(v);
		} else {
			for (int i = 0; i < adjList[v].size(); i++) {
				int w = ((Integer) adjList[v].get(i)).intValue();
				if (!this.B[w].contains(new Integer(v))) {
					this.B[w].add(new Integer(v));
				}
			}
		}

		this.stack.remove(new Integer(v));
		return f;
	}

	/**
	 * Unblocks recursively all blocked nodes, starting with a given node.
	 *
	 * @param node node to unblock
	 */
	private void unblock(int node) {
		this.blocked[node] = false;
		Vector<?> Bnode = this.B[node];
		while (Bnode.size() > 0) {
			Integer w = (Integer) Bnode.get(0);
			Bnode.remove(0);
			if (this.blocked[w.intValue()]) {
				this.unblock(w.intValue());
			}
		}
	}
}


