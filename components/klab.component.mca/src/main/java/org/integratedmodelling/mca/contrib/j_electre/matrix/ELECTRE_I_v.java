package org.integratedmodelling.mca.contrib.j_electre.matrix;

//	Copyright � 2017 by Valdecy Pereira, Helder Gomes Costa and Livia Dias de Oliveira Nepomuceno.

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

public class ELECTRE_I_v {

	public static String[][] e_I_v_Algorithm(double[][] array, double [] v, double[] weights) {

		double [][]  arrayP1        = Credibility.getCredibilityMatrixEI_v(array, v, weights);
		double [][]  arrayPartition = new double[2][array.length]; 
		double []    arrayP2        = MatrixOperations.getColumnSum(arrayP1);
		double []    arrayP3        = new double[arrayP2.length];
		String [][]  arraySolution  = new String[2][array.length + 1];
		String [][]  arrayOutput    = new String[2][array.length + 1]; 
		double [][]  arrayTemp      = new double [array.length][array.length];
		java.util.Arrays.fill(arrayP3, 0); 
		int kSet  = 0; 
		int dSet  = 0; 
		int k     = 0; 
		int d     = 0; 
		int f     = 0;
		int setKD = 0;

		//		double[][]  arrayOut1 = Concordance.getConcordanceMatrixEI_v(array, weights);
		//		double[][]  arrayOut2 = Discordance.getDiscordanceMatrixEI_v(array, v);
		//		double[][]  arrayOut3 = Credibility.getCredibilityMatrixEI_v(array, v, weights);
		//	
		//		int cols = arrayOut1[0].length;
		//
		//		String [][] arrayFinal    = new String[arrayOut1.length + arrayOut2.length + arrayOut3.length + 9][cols + 4]; 

		arraySolution[0][0] = "Kernel: ";
		arraySolution[1][0] = "Dominated: ";
		for (int i = 0; i < arraySolution.length; i++) {
			for (int j = 1; j < arraySolution[0].length; j++) {
				arraySolution[i][j] = " ";
			}
		}
		for (int i = 0; i < arrayOutput.length; i++) {
			for (int j = 1; j < arrayOutput[0].length; j++) {
				arrayOutput[i][j] = " ";
			}
		}
		//		for (int i = 0; i < arrayFinal.length; i++) {
		//			for (int j = 0; j < arrayFinal[0].length; j++) {
		//				arrayFinal[i][j] = " ";
		//			}
		//		}
		for (int i = 0; i < arrayP2.length; i++) {
			if (arrayP2[i] == 0) {
				arrayP3[i] = i + 1;
			}
		}
		if (MatrixOperations.getElementCountZero(arrayP2) != 0) {
			setKD = 1;
		}
		for (int i = 0; i < arrayP3.length; i++) {
			if (arrayP3[i] == 0) {
				for (int j = 0; j < arrayP1[0].length; j++) {
					arrayP1[i][j] = 0;
				}
			}
		}

		arrayP2 = MatrixOperations.getColumnSum(arrayP1);

		for (int i = 0; i < arrayP2.length; i++) {
			if (arrayP2[i] == 0) {
				arrayP3[i] = i + 1;
			}
		}
		if (MatrixOperations.getElementCountZero(arrayP3) == 0 && setKD == 1) {
			for (int i = 0; i < arrayP3.length; i++) {
				if (arrayP3[i] > 0) {
					kSet = kSet + 1;
				}
			}
			String[] pKernel = new String[kSet];

			for (int i = 0; i < arrayP3.length; i++) {
				if (arrayP3[i] > 0) {
					pKernel[k] = ("a" + Math.round((i + 1) * 1) / 1);
					arrayPartition[0][i] = 1; 
					arraySolution[0][i + 1] = pKernel[k]; 
					k = k + 1;
				}
			}
		}
		if (MatrixOperations.getElementCountZero(arrayP3) == 0 && setKD == 0) {
			for (int i = 0; i < arrayP3.length; i++) {
				if (arrayP3[i] > 0) {
					dSet = dSet + 1;
				}
			}

			String[] pDominated = new String[dSet];

			for (int i = 0; i < arrayP3.length; i++) {
				if (arrayP3[i] > 0) {
					pDominated[d] = ("a" + Math.round((i + 1) * 1) / 1);
					arrayPartition[1][i] = 1; 
					arraySolution[1][i + 1] = pDominated[d];
					d = d + 1;
				}
			}
		}
		if (MatrixOperations.getElementCountZero(arrayP3) != 0) {
			for (int i = 0; i < arrayP3.length; i++) {
				if (arrayP3[i] > 0) {
					kSet = kSet + 1;
				} else {
					dSet = dSet + 1;
				}
			}

			String[] pKernel = new String[kSet];
			String[] pDominated = new String[dSet];

			for (int i = 0; i < arrayP3.length; i++) {
				if (arrayP3[i] > 0) {
					pKernel[k] = ("a" + Math.round((i + 1) * 1) / 1);
					arrayPartition[0][i] = 1; 
					arraySolution[0][i + 1] = pKernel[k];
					k = k + 1;
				} else {
					pDominated[d] = ("a" + Math.round((i + 1) * 1) / 1);
					arrayPartition[1][i] = 1; 
					arraySolution[1][i + 1] = pDominated[d];
					d = d + 1;
				}
			}
		}
		for (int i = 0; i < arraySolution.length; i++){
			f = 0;
			for (int j = 0;j < arraySolution[0].length; j++){
				if (arraySolution[i][j] != " "){
					arrayOutput[i][f] = arraySolution[i][j];
					if (f + 1 < arraySolution[0].length ){
						f = f + 1;	
					}
				}
			}
		}
		String [][] arrayOutput1 = new String [array.length + 2][array.length + 2];
		arrayTemp = Concordance.getConcordanceMatrixEI_v(array, weights);
		arrayOutput1[0][0] = "Concordance Matrix:";	
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length; j++) {
				arrayOutput1[0][j + 2] = "a" + (j + 1);
				arrayOutput1[i + 1][1] = "a" + (i + 1);
			}
		}
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length; j++) {
				arrayOutput1[i + 1][j + 2] = Double.toString(Math.round(arrayTemp[i][j] * 10000d)/10000d);
			}
		}
		for (int i = 0; i < arrayOutput1.length; i++) {
			for (int j = 0; j < arrayOutput1[0].length; j++) {
				if (arrayOutput1[i][j] == null){
					arrayOutput1[i][j] = "";
				}
			}
		}

		String [][] arrayOutput2 = new String [array.length + 2][array.length + 2];
		arrayTemp = Discordance.getDiscordanceMatrixEI_v(array, v);
		arrayOutput2[0][0] = "Discordance Matrix:";
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length; j++) {
				arrayOutput2[0][j + 2] = "a" + (j + 1);
				arrayOutput2[i + 1][1] = "a" + (i + 1);
			}
		}
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length; j++) {
				arrayOutput2[i + 1][j + 2] = Double.toString(Math.round(arrayTemp[i][j] * 10000d)/10000d);
			}
		}
		for (int i = 0; i < arrayOutput2.length; i++) {
			for (int j = 0; j < arrayOutput2[0].length; j++) {
				if (arrayOutput2[i][j] == null){
					arrayOutput2[i][j] = "";
				}
			}
		}

		String [][] arrayOutput3 = new String [array.length + 2][array.length + 2];
		arrayTemp = Credibility.getCredibilityMatrixEI_v(array, v, weights);
		arrayOutput3[0][0] = "Credibility Matrix:";
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length; j++) {
				arrayOutput3[0][j + 2] = "a" + (j + 1);
				arrayOutput3[i + 1][1] = "a" + (i + 1);
			}
		}
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length; j++) {
				arrayOutput3[i + 1][j + 2] = Double.toString(Math.round(arrayTemp[i][j] * 10000d)/10000d);
			}
		}
		for (int i = 0; i < arrayOutput3.length; i++) {
			for (int j = 0; j < arrayOutput3[0].length; j++) {
				if (arrayOutput3[i][j] == null){
					arrayOutput3[i][j] = "";
				}
			}
		}
		int cols = Math.max(arrayOutput1[0].length, 
				   Math.max(arrayOutput2[0].length,
				   Math.max(arrayOutput3[0].length, arrayOutput [0].length + 2
								)));

		String [][] arrayFinal    = new String[      arrayOutput1.length 
		                                           + arrayOutput2.length 
		                                           + arrayOutput3.length 
		                                           + arrayOutput.length + 1
		                                           ][cols]; 

		for (int i = 0; i < arrayFinal.length; i++) {
			for (int j = 0; j < arrayFinal[0].length; j++) {
				arrayFinal[i][j] = " ";
			}
		}
		for (int i = 0; i < arrayOutput1.length; i++) {
			for (int j = 0; j < arrayOutput1[0].length; j++) {
				arrayFinal[i][j] = arrayOutput1[i][j];
			}
		}
		for (int i = 0; i < arrayOutput2.length; i++) {
			for (int j = 0; j < arrayOutput2[0].length; j++) {
				arrayFinal[i + arrayOutput1.length][j] = arrayOutput2[i][j];
			}
		}
		for (int i = 0; i < arrayOutput3.length; i++) {
			for (int j = 0; j < arrayOutput3[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length][j] = arrayOutput3[i][j];
			}
		}
		for (int i = 0; i < arrayOutput.length; i++) {
			for (int j = 0; j < arrayOutput[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length
				             + arrayOutput3.length][j] = arrayOutput[i][j];
			}
		}

		return arrayFinal;
	}

	public static void main(String[] args) {

	}
}

