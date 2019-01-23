package org.integratedmodelling.mca.contrib.j_electre.matrix;

import org.integratedmodelling.mca.contrib.j_electre.cycles.JohnsonAlgorithm_io_int;
import org.integratedmodelling.mca.contrib.j_electre.cycles.JohnsonAlgorithm_io_str;
import org.integratedmodelling.mca.contrib.j_electre.ui.Interface;

public class ELECTRE_I_s {
	
	public static String[][] e_I_s_Algorithm(double[][] array, double[] weights, double[] p, double[] q, double[] v) {
		
		double[][] arrayP1        = JohnsonAlgorithm_io_int.cyclesElimination(Credibility.getCredibilityMatrixEI_s(array, weights, p, q, v),1);
		double[][] arrayPartition = new double[2][array.length];
		double[]   arrayP2        = MatrixOperations.getColumnSum(arrayP1);
		double[]   arrayP3        = new double[arrayP2.length];
		String[][] arraySolution  = new String[3][array.length + 1];
		String[][] arrayOutput    = new String[3][array.length + 1]; 
		double[][] arrayTemp      = new double [array.length][array.length];
		double[][] arrayTempD     = new double [array.length][array.length];
		
		java.util.Arrays.fill(arrayP3, 0);
		
		int kSet = 0; 
		int dSet = 0; 
		int k = 0; 
		int d = 0;
		int f = 0;
		int setKD = 0;
		
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
			for (int j = 0; j < arraySolution[0].length; j++){
				if (arraySolution[i][j] != " "){
					arrayOutput[i][f] = arraySolution[i][j];
					if (f + 1 < arraySolution[0].length ){
						f = f + 1;	
					}
				}
			}
		}
        
		String [][] arrayOutput1 = new String [array.length + 2][array.length + 2];
		arrayTemp = Concordance.getConcordanceMatrixEI_s(array, weights, p, q);
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
		double c = Interface.EI_s_Lambda;
		for (k = 0; k < array[0].length; k++) {
			double[][] arrayDMj = Discordance.getDiscordanceMatrixEI_s(c, array, weights, p, q, v, k);
			arrayTempD = MatrixOperations.get2DMatrixSum(arrayTempD, arrayDMj);
		}
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayTempD[i][j] = 0;
				}
			}
		}
		
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (arrayTempD[i][j] > 0) {
					arrayTempD[i][j] = 1.0;
				} else {
					arrayTempD[i][j] = 0.0;
				}
			}
		}
		arrayOutput2[0][0] = "Discordance Matrix:";
		for (int i = 0; i < arrayTempD.length; i++) {
			for (int j = 0; j < arrayTempD[0].length; j++) {
				arrayOutput2[0][j + 2] = "a" + (j + 1);
				arrayOutput2[i + 1][1] = "a" + (i + 1);
			}
		}
		for (int i = 0; i < arrayTempD.length; i++) {
			for (int j = 0; j < arrayTempD[0].length; j++) {
				arrayOutput2[i + 1][j + 2] = Double.toString(Math.round(arrayTempD[i][j] * 10000d)/10000d);
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
		arrayTemp = Credibility.getCredibilityMatrixEI_s(array, weights, p, q, v);
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
		
		String [][] arrayTempS = JohnsonAlgorithm_io_str.cyclesElimination(Credibility.getCredibilityMatrixEI_s(array, weights, p, q, v), 1);
		String [][] arrayOutput4 = new String [arrayTempS.length + 1][arrayTempS[0].length + 1];
		arrayOutput4[0][0] = "Cycles:";
		for (int i = 0; i < arrayTempS.length; i++) {
			for (int j = 0; j < arrayTempS[0].length; j++) {
				arrayOutput4[i][j + 1] =  arrayTempS[i][j];
			}
		}
		for (int i = 0; i < arrayOutput4.length; i++) {
			for (int j = 0; j < arrayOutput4[0].length; j++) {
				if (arrayOutput4[i][j] == null){
					arrayOutput4[i][j] = "";
				}
			}
		}
		int cols = Math.max(arrayOutput1[0].length, 
				   Math.max(arrayOutput2[0].length,
				   Math.max(arrayOutput3[0].length, 
//						    arrayOutput3[0].length
						    arrayOutput4[0].length
						 )));
		String [][] arrayFinal    = new String[      arrayOutput1.length 
		                                           + arrayOutput2.length 
		                                           + arrayOutput3.length 
		                                           + arrayOutput4.length
		                                           + arrayOutput.length
		                                           ][cols + 2]; 
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
		for (int i = 0; i < arrayOutput4.length; i++) {
			for (int j = 0; j < arrayOutput4[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length
				             + arrayOutput3.length][j] = arrayOutput4[i][j];
			}
		}
		for (int i = 0; i < arrayOutput.length; i++) {
			for (int j = 0; j < arrayOutput[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length
				             + arrayOutput3.length
				             + arrayOutput4.length][j] = arrayOutput[i][j];
			}
		}	
		return arrayFinal;
	}

	public static void main(String[] args) {

	}
}

