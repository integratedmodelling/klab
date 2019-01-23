package org.integratedmodelling.mca.contrib.j_electre.cycles;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	JohnsonAlgorithm_AdjacencyList: Algorithm by D. B. Johnson Finding all the elementary circuits of a directed graph. D. B. Johnson, SIAM Journal on Computing 4, no. 1, 77-84, 1975. http://dx.doi.org/10.1137/0204007
//	JohnsonAlgorithm_AdjacencyList: @ 26.08.2006 by Frank Meyer - web@normalisiert.de or https://github.com/josch/cycles_johnson_meyer.
//	JohnsonAlgorithm_AdjacencyList: Modified in 01.01.2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

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

import java.util.Vector;

/**
 * Calculates the adjacency-list for a given adjacency-matrix.
 * 
 * 
 * @author Frank Meyer, web@normalisiert.de
 * @version 1.0, 26.08.2006
 *
 */
public class JohnsonAlgorithm_AdjacencyList {
	/**
	 * Calculates a adjacency-list for a given array of an adjacency-matrix.
	 *
	 * @param adjacencyMatrix array with the adjacency-matrix that represents
	 * the graph
	 * @return int[][]-array of the adjacency-list of given nodes. The first
	 * dimension in the array represents the same node as in the given
	 * adjacency, the second dimension represents the indices of those nodes,
	 * that are direct successor nodes of the node.
	 */
	public static int[][] getAdjacencyList(boolean[][] adjacencyMatrix) {
		int[][] list = new int[adjacencyMatrix.length][];

		for (int i = 0; i < adjacencyMatrix.length; i++) {
			Vector<Integer> v = new Vector<Integer>();
			for (int j = 0; j < adjacencyMatrix[i].length; j++) {
				if (adjacencyMatrix[i][j]) {
					v.add(new Integer(j));
				}
			}
			list[i] = new int[v.size()];
			for (int j = 0; j < v.size(); j++) {
				Integer in = (Integer) v.get(j);
				list[i][j] = in.intValue();
			}
		}
		return list;
	}
}

