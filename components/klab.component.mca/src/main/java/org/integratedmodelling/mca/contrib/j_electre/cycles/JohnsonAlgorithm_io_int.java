package org.integratedmodelling.mca.contrib.j_electre.cycles;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	JohnsonAlgorithm_io_int: Algorithm by D. B. Johnson Finding all the elementary circuits of a directed graph. D. B. Johnson, SIAM Journal on Computing 4, no. 1, 77-84, 1975. http://dx.doi.org/10.1137/0204007
//	JohnsonAlgorithm_io_int: Copyright @ 26.08.2006 by Frank Meyer - web@normalisiert.de or https://github.com/josch/cycles_johnson_meyer.
//	JohnsonAlgorithm_io_int: Modified in 01.01.2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

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

import org.integratedmodelling.mca.contrib.j_electre.matrix.MatrixOperations;

public class JohnsonAlgorithm_io_int {

	public static double[][] cyclesElimination(double[][] array, int p) {
		int k = 0;
		
		boolean[][] arrayBoolean = new boolean[array.length][array[0].length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if (array[i][j] == p) {
					arrayBoolean[i][j] = true;
				} else {
					arrayBoolean[i][j] = false;
				}
			}
		}
		
		String nodes[] = new String[array[0].length];

		for (int i = 0; i < array[0].length; i++) {
			int h = i + 1;
			nodes[i] = "a" + h;
		}

		JohnsonAlgorithm_ElementaryCyclesSearch ecs = new JohnsonAlgorithm_ElementaryCyclesSearch(arrayBoolean, nodes);
		List<?> cycles = ecs.getElementaryCycles();
		
//		System.out.println(cycles.size());
		
		if (cycles.size() != 0) {

			String[][] cyclePairs = new String[cycles.size()][array[0].length + 1];

			for (int i = 0; i < cycles.size(); i++) {
				List<?> cycle = (List<?>) cycles.get(i);
				for (int j = 0; j < cycle.size(); j++) {
					String node = (String) cycle.get(j);
					String iNode = (String) cycle.get(0);
					if (j < cycle.size() - 1) {
						cyclePairs[i][j] = node;
					} else {
						cyclePairs[i][j] = node;
						cyclePairs[i][j + 1] = iNode;
					}
				}
			}

			for (int i = 0; i < cyclePairs.length; i++) {
				for (int j = 0; j < cyclePairs[0].length; j++) {
					if (cyclePairs[i][j] == null) {
						cyclePairs[i][j] = "";
					}
					cyclePairs[i][j] = cyclePairs[i][j].replaceAll("a", "");
				}
			}

			double[][] cyclePairsValue = new double[cyclePairs.length][cyclePairs[0].length];
			for (int i = 0; i < cyclePairs.length; i++) {
				for (int j = 0; j < cyclePairs[0].length; j++) {
					try {
						Double.parseDouble(cyclePairs[i][j]);
					} catch (NumberFormatException e1) {
						cyclePairs[i][j] = "0.5";
					}
					cyclePairsValue[i][j] = Double.parseDouble(cyclePairs[i][j]);
				}
			}

			int totalpairs = cyclePairsValue.length * cyclePairsValue[0].length - cyclePairsValue.length
					- MatrixOperations.get2DElementCountSpecificValue(cyclePairsValue, 0.5);

			double[] counter = new double[cyclePairsValue.length];
			for (int i = 0; i < cyclePairsValue.length; i++) {
				for (int j = 0; j < cyclePairsValue[0].length; j++) {
					if (cyclePairsValue[i][j] != 0.5) {
						counter[i] = 1 + counter[i];
					}
				}
			}

			double[][] pairs = new double[totalpairs][2];
			for (int i = 0; i < cyclePairsValue.length; i++) {
				for (int j = 0; j < counter[i] - 1; j++) {
					if (cyclePairsValue[i][j] != 0.5) {
						pairs[k][0] = cyclePairsValue[i][j];
						pairs[k][1] = cyclePairsValue[i][j + 1];
						k = k + 1;
					}
				}
			}

			for (int i = 0; i < pairs.length; i++) {
				for (int m = 0; m < array.length; m++) {
					for (int n = 0; n < array[0].length; n++) {
						if (m == (int) pairs[i][0] -1 && n == (int) pairs[i][1] - 1) {
							array[m][n] = 0.0;
						}
					}
				}
			}
		}
		return array;
	}

	public static void main(String[] args) {
	}
}


