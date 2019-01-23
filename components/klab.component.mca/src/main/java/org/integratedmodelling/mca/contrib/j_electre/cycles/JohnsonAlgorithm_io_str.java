package org.integratedmodelling.mca.contrib.j_electre.cycles;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

//	JohnsonAlgorithm_io_str: Algorithm by D. B. Johnson Finding all the elementary circuits of a directed graph. D. B. Johnson, SIAM Journal on Computing 4, no. 1, 77-84, 1975. http://dx.doi.org/10.1137/0204007
//	JohnsonAlgorithm_io_str: Copyright @ 26.08.2006 by Frank Meyer - web@normalisiert.de or https://github.com/josch/cycles_johnson_meyer.
// 	JohnsonAlgorithm_io_str: Modified in 01.01.2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

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
import org.integratedmodelling.mca.contrib.j_electre.ui.Interface;

public class JohnsonAlgorithm_io_str {

	public static String[][] cyclesElimination(double[][] array, int p) {
		
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
 
		String[][]  arrayCycle    = new String[Interface.MaximumCycles + 1][1000];
		String[][]  arraySolution = new String[Interface.MaximumCycles + 1][1000];
		
		if (cycles.size() != 0) {

			for (int i = 0; i < cycles.size(); i++) {
				List<?> cycle = (List<?>) cycles.get(i);
				
				for (int j = 0; j < cycle.size(); j++) {
					String node = (String) cycle.get(j);
					String iNode = (String) cycle.get(0);
					if (j < cycle.size() - 1) {
						arrayCycle[i][j] = node;
					} else {
						arrayCycle[i][j] = node;
						arrayCycle[i][j + 1] = iNode;
					}
				}
			}
			
			for (int i = arrayCycle.length - 1; i > 0; i--) {
				if (arrayCycle[i][0] == null) {
					arrayCycle = MatrixOperations.get2DRemoveRowString(arrayCycle, i);
				}
			}
			int count = 0;
			for (int j = arrayCycle[0].length - 1; j > 0; j--) {
				if (arrayCycle[0][j] == null) {
					for (int i = 0; i < arrayCycle.length; i++){
						if (arrayCycle[i][j] == null) {
							count = count + 1;
						}
						if(count == arrayCycle.length ){
							arrayCycle = MatrixOperations.get2DRemoveColumnString(arrayCycle, j);
							count = 0;
						}
					}
				}
			}
			for (int j = arrayCycle[0].length - 1; j > 0; j--) {
				if (arrayCycle[0][j] == null) {
					for (int i = 0; i < arrayCycle.length; i++){
						if (arrayCycle[i][j] == null) {
							arrayCycle[i][j] = "";
						}
					}
				}
			}
			for (int j = 0; j < arraySolution[0].length; j++) {
					for (int i = 0; i < arraySolution.length; i++){
							arraySolution[i][j] = "";
					}
				}
			for (int i = 0; i < arrayCycle.length; i++) {
					for (int j = 0; j < arrayCycle[0].length; j++){
						if (arrayCycle[i][j] != "") {
							arraySolution[i][j] = arrayCycle[i][j];
						}
					}
				}
		}
		for (int i = arraySolution.length - 1; i > 0; i--){
			if (arraySolution[i][0] == "" || arraySolution[0][0] == null){
				arraySolution = MatrixOperations.get2DRemoveRowString(arraySolution, i);
			}
		}
		int count = 0;
		for (int j = arraySolution[0].length - 1; j > 0; j--) {
			if (arraySolution[0][j] == null) {
				for (int i = 0; i < arraySolution.length; i++){
					if (arraySolution[i][j] == null) {
						count = count + 1;
					}
					if(count == arraySolution.length ){
						arraySolution = MatrixOperations.get2DRemoveColumnString(arraySolution, j);
						count = 0;
					}
				}
			}
		}
		if (arraySolution[0][0] == "" || arraySolution[0][0] == null){
			for (int i = arraySolution.length - 1; i > 0; i--) {
				arraySolution = MatrixOperations.get2DRemoveRowString(arraySolution, i);
			}
			for (int j = arraySolution[0].length - 1; j > 0; j--) {
				arraySolution = MatrixOperations.get2DRemoveColumnString(arraySolution, j);
			}
			arraySolution[0][0] = "None";
		}
		return arraySolution;
	}

	public static void main(String[] args) {
		
	}
}



