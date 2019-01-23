package org.integratedmodelling.mca.contrib.j_electre.cycles;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	JohnsonAlgorithm_SCCResult: Algorithm by D. B. Johnson Finding all the elementary circuits of a directed graph. D. B. Johnson, SIAM Journal on Computing 4, no. 1, 77-84, 1975. http://dx.doi.org/10.1137/0204007
//	JohnsonAlgorithm_SCCResult: Copyright @ 26.08.2006 by Frank Meyer - web@normalisiert.de or https://github.com/josch/cycles_johnson_meyer.
//	JohnsonAlgorithm_SCCResult: Modified in 01.01.2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

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

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class JohnsonAlgorithm_SCCResult {
	private Set<Integer> nodeIDsOfSCC = null;
	@SuppressWarnings("rawtypes")
	private Vector[] adjList = null;
	private int lowestNodeId = -1;
	
	public JohnsonAlgorithm_SCCResult(@SuppressWarnings("rawtypes") Vector[] adjList, int lowestNodeId) {
		this.adjList = adjList;
		this.lowestNodeId = lowestNodeId;
		this.nodeIDsOfSCC = new HashSet<Integer>();
		if (this.adjList != null) {
			for (int i = this.lowestNodeId; i < this.adjList.length; i++) {
				if (this.adjList[i].size() > 0) {
					this.nodeIDsOfSCC.add(new Integer(i));
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Vector[] getAdjList() {
		return adjList;
	}

	public int getLowestNodeId() {
		return lowestNodeId;
	}
}
