package org.integratedmodelling.mca.contrib.j_electre.matrix;

import org.integratedmodelling.mca.contrib.j_electre.cycles.JohnsonAlgorithm_io_int;
import org.integratedmodelling.mca.contrib.j_electre.cycles.JohnsonAlgorithm_io_str;

public class ELECTRE_II {

	public static double[] e_II_Algorithm(double[][] array) {

		double[][] arrayP1 = array;
		double[]   arrayP2 = MatrixOperations.getColumnSum(arrayP1);
		double[]   arrayP3 = new double[arrayP2.length];
		
		java.util.Arrays.fill(arrayP3, 0); 

		for (int i = 0; i < arrayP2.length; i++) {
			if (arrayP2[i] == 0) {
				arrayP3[i] = i + 1;
			}
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
		return arrayP3;
	}

	public static double[][] rankDescending(double[][] array, double[] weights) {

		int k = 0;
		double[][] arrayS          = Credibility.getCredibilityMatrixEII(array, weights);
		double[][] arrayCR1        = JohnsonAlgorithm_io_int.cyclesElimination(arrayS,2);
		double[][] arrayCR2        = JohnsonAlgorithm_io_int.cyclesElimination(arrayCR1,1);		
		double[][] arrayR          = arrayCR2;
		double[][] arrayDescending = new double[arrayR.length][arrayR[0].length];

		do {
			double[] array0 = MatrixOperations.getColumnSum(arrayR);
			if (MatrixOperations.getElementCountZero(array0) == 0) {
				double min     = MatrixOperations.getMinValueInRowNonSorting(array0);
				double[] rSum  = MatrixOperations.getRowSum(arrayR);
				double[] rTemp = new double[array0.length];
				int kTie = 0;

				for (int j = 0; j < array0.length; j++) {
					if (array0[j] == min) {
						rTemp[j] = 1.0;
					}
				}
				for (int j = 0; j < rTemp.length; j++) {
					rTemp[j] = rTemp[j] * rSum[j];
				}
				
				double max = MatrixOperations.getMaxValueInRowNonSorting(rTemp);

				for (int j = 0; j < rTemp.length; j++) {
					if (rTemp[j] >= max) {
						kTie = j;
					}
				}

				for (int i = 0; i < array0.length; i++) {
					arrayR[i][kTie] = 0;
				}
			}

			array0 = MatrixOperations.getColumnSum(arrayR);

			for (int i = 0; i < array0.length; i++) {
				if (array0[i] == 0) {
					for (int j = 0; j < array0.length; j++) {
						arrayR[i][j] = 0;
					}
					
					for (int j = 0; j < array0.length; j++) {
						if (i == j) {
							arrayR[i][j] = 0;
						} else {
							arrayR[j][i] = 1;
						}
					}
				}
			}

			for (int j = 0; j < array0.length; j++) {
				if (array0[j] == 0) {
					arrayDescending[k][j] = 1;
				}
			}

			if (MatrixOperations.getElementCountZero(array0) == 0) {
				k = k + 0;
			} else {
				k = k + 1;
			}
		} while (MatrixOperations.get2DMatrixTotalSum(arrayDescending) < arrayR[0].length);

		for (int i = 0; i < arrayDescending.length; i++) {
			for (int j = 0; j < arrayDescending[0].length; j++) {
				arrayDescending[i][j] = (arrayDescending[i][j]) * (i + 1);
			}
		}
		return arrayDescending;
	}

	public static double[][] rankAscending(double[][] array, double[] weights) {

		int p = 0;
		int k = 0;
		double[][] arrayS         = MatrixOperations.getTransposed2DMatrix(Credibility.getCredibilityMatrixEII(array, weights));
		double[][] arrayCR1       = JohnsonAlgorithm_io_int.cyclesElimination(arrayS,2);
		double[][] arrayCR2       = JohnsonAlgorithm_io_int.cyclesElimination(arrayCR1,1);	
		double[][] arrayR         = arrayCR2;
		double[][] arrayAscending = new double[arrayR.length][arrayR[0].length];
		double[][] arrayInvert    = new double[arrayR.length][arrayR[0].length];

		do {
			double[] array0 = MatrixOperations.getColumnSum(arrayR);
			if (MatrixOperations.getElementCountZero(array0) == 0) {
				double min = MatrixOperations.getMinValueInRowNonSorting(array0);
				double[] rSum = MatrixOperations.getRowSum(arrayR);
				double[] rTemp = new double[array0.length];
				int kTie = 0;

				for (int j = 0; j < array0.length; j++) {
					if (array0[j] == min) {
						rTemp[j] = 1.0;
					}
				}
				for (int j = 0; j < rTemp.length; j++) {
					rTemp[j] = rTemp[j] * rSum[j];
				}
				
				double max = MatrixOperations.getMaxValueInRowNonSorting(rTemp);

				for (int j = 0; j < rTemp.length; j++) {
					if (rTemp[j] >= max) {
						kTie = j;
					}
				}
				for (int i = 0; i < array0.length; i++) {
					arrayR[i][kTie] = 0;
				}
			}

			array0 = MatrixOperations.getColumnSum(arrayR);

			for (int i = 0; i < array0.length; i++) {
				if (array0[i] == 0) {
					for (int j = 0; j < array0.length; j++) {
						arrayR[i][j] = 0;
					}
					for (int j = 0; j < array0.length; j++) {
						if (i == j) {
							arrayR[i][j] = 0;
						} else {
							arrayR[j][i] = 1;
						}
					}
				}
			}

			for (int j = 0; j < array0.length; j++) {
				if (array0[j] == 0) {
					arrayAscending[k][j] = 1;
				}
			}
			if (MatrixOperations.getElementCountZero(array0) == 0) {
				k = k + 0;
			} else {
				k = k + 1;
			}

		} while (MatrixOperations.get2DMatrixTotalSum(arrayAscending) < arrayR[0].length);

		p = MatrixOperations.getElementCountZero(MatrixOperations.getRowSum(arrayAscending));

		for (int i = 0; i < arrayInvert.length - p; i++) {
			for (int j = 0; j < arrayInvert[0].length; j++) {
				arrayInvert[arrayInvert.length - 1 - p - i][j] = arrayAscending[i][j];
			}
		}

		arrayAscending = arrayInvert;
		
		for (int i = 0; i < arrayAscending.length; i++) {
			for (int j = 0; j < arrayAscending[0].length; j++) {
				arrayAscending[i][j] = (arrayAscending[i][j]) * (i + 1);
			}
		}
		return arrayAscending;
	}

	public static String[][] rankFinal(double[][] array, double[] weights) {

		double[][] arrayAscending  = rankAscending(array, weights);
		double[][] arrayDescending = rankDescending(array, weights);
		double[]   RD              = new double[array.length];
		double[]   RA              = new double[array.length];
		double[]   RM              = new double[array.length];
		double[]   rn              = new double[array.length];
		double[][] arrayTemp       = new double [array.length][array.length];
		
		String [][] arrayOutput1 = new String [array.length + 2][array.length + 2];
		arrayTemp = Concordance.getConcordanceMatrixEII(array, weights);
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
		arrayTemp = Discordance.getDiscordanceMatrixEII(array);
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
		arrayTemp = Credibility.getCredibilityMatrixEII(array, weights);
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
		for (int i = 0; i < arrayOutput3.length; i++) {
			for (int j = 0; j < arrayOutput3[0].length; j++) {
				if (arrayOutput3[i][j].equals("2.0")){
					arrayOutput3[i][j] = "Ss";
				}
				if (arrayOutput3[i][j].equals("1.0")){
					arrayOutput3[i][j] = "Ws";
				}
			}
		}
		
		String [][] arrayTempS = JohnsonAlgorithm_io_str.cyclesElimination(Credibility.getCredibilityMatrixEII(array, weights), 2);
		String [][] arrayOutput4 = new String [arrayTempS.length + 1][arrayTempS[0].length + 1];
		arrayOutput4[0][0] = "Cycles Ss:";
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
		
		arrayTempS = JohnsonAlgorithm_io_str.cyclesElimination(Credibility.getCredibilityMatrixEII(array, weights), 1);
		String [][] arrayOutput5 = new String [arrayTempS.length + 1][arrayTempS[0].length + 1];
		arrayOutput5[0][0] = "Cycles Ws:";
		for (int i = 0; i < arrayTempS.length; i++) {
			for (int j = 0; j < arrayTempS[0].length; j++) {
				arrayOutput5[i][j + 1] =  arrayTempS[i][j];
			}
		}
		for (int i = 0; i < arrayOutput5.length; i++) {
			for (int j = 0; j < arrayOutput5[0].length; j++) {
				if (arrayOutput5[i][j] == null){
					arrayOutput5[i][j] = "";
				}
			}
		}
		
		RD = MatrixOperations.getColumnSum(arrayDescending);
		RA = MatrixOperations.getColumnSum(arrayAscending);
		for (int j = 0; j < RM.length; j++){
			RM[j] = (RA[j] + RD[j])/2; 
			rn[j] = (RA[j] + RD[j])/2; 
		}
		
		int rows = Math.max(RD.length, Math.max(RA.length,RM.length));
		String [][] arrayOutput7 = new String [rows + 2][7];
		arrayOutput7[0][0] = "Ranking:";
		arrayOutput7[0][2] = "Ascend.";
		arrayOutput7[0][3] = "Descend.";
		arrayOutput7[0][4] = "Average";
		for (int i = 0; i < RA.length; i++) {
				arrayOutput7[i + 1][1] = "a" + (i + 1);
		}	
		for (int i = 0; i < RA.length; i++){
			arrayOutput7[i + 1][2] = Double.toString(Math.round(RA[i] * 10000d)/10000d);
		}
		for (int i = 0; i < RD.length; i++){
			arrayOutput7[i + 1][3] = Double.toString(Math.round(RD[i] * 10000d)/10000d);
		}
		for (int i = 0; i < RM.length; i++){
			arrayOutput7[i + 1][4] = Double.toString(Math.round(RM[i] * 10000d)/10000d); 
		}
		for (int i = 0; i < arrayOutput7.length; i++) {
			for (int j = 0; j < arrayOutput7[0].length; j++) {
				if (arrayOutput7[i][j] == null){
					arrayOutput7[i][j] = "";
				}
			}
		}
		
		String[][] RF = new String[arrayAscending.length][arrayAscending[0].length];
		String[][] rg = new String[arrayAscending.length][arrayAscending[0].length];
		for (int i = 0; i < arrayAscending.length; i++) {
			for (int j = 0; j < arrayAscending[0].length; j++) {

				if (RD[i] == RD[j] && RA[i] == RA[j]) {
					RF[i][j] = "I";
					rg[i][j] = "I";
				} else if (RD[i] < RD[j] && RA[i] < RA[j] || RD[i] == RD[j] && RA[i] < RA[j]
						|| RD[i] < RD[j] && RA[i] == RA[j]) {
					RF[i][j] = "P+";
					RF[j][i] = "P-";
					rg[i][j] = "P+";
					rg[j][i] = "P-";
				} else {
					if(RF[i][j] == null){
						RF[i][j] = "R";
						rg[i][j] = "R";
					}
				}
				if (i == j) {
					RF[i][j] = "0";
					rg[i][j] = "0";
				}
			}
		}
		
		String [][] arrayOutput8 = new String [rg.length + 2][rg[0].length + 2];
		arrayOutput8[0][0] = "Dominance Matrix:";
		for (int i = 0; i < rg.length; i++) {
			for (int j = 0; j < rg[0].length; j++) {
				arrayOutput8[0][j + 2] = "a" + (j + 1);
				arrayOutput8[i + 1][1] = "a" + (i + 1);
			}
		}
		for (int i = 0; i < rg.length; i++) {
			for (int j = 0; j < rg[0].length; j++) {
				arrayOutput8[i + 1][j + 2] = rg[i][j];
			}
		}
		for (int i = 0; i < arrayOutput8.length; i++) {
			for (int j = 0; j < arrayOutput8[0].length; j++) {
				if (arrayOutput8[i][j] == null){
					arrayOutput8[i][j] = "";
				}
			}
		}
		
		int cols = Math.max(arrayOutput1[0].length, Math.max(arrayOutput2[0].length,
				                                    Math.max(arrayOutput3[0].length,
				                                    Math.max(arrayOutput4[0].length,
				                                    Math.max(arrayOutput5[0].length,
				                                    Math.max(arrayOutput7[0].length, 
				                                    		 arrayOutput8[0].length
				                                    		))))));
		String [][] arrayFinal    = new String[  arrayOutput1.length 
		                                       + arrayOutput2.length 
		                                       + arrayOutput3.length 
		                                       + arrayOutput4.length 
		                                       + arrayOutput5.length
		                                       + arrayOutput7.length
		                                       + arrayOutput8.length
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
		for (int i = 0; i < arrayOutput4.length; i++) {
			for (int j = 0; j < arrayOutput4[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length
				             + arrayOutput3.length][j] = arrayOutput4[i][j];
			}
		}
		for (int i = 0; i < arrayOutput5.length; i++) {
			for (int j = 0; j < arrayOutput5[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length
				             + arrayOutput3.length
				             + arrayOutput4.length][j] = arrayOutput5[i][j];
			}
		}
		for (int i = 0; i < arrayOutput7.length; i++) {
			for (int j = 0; j < arrayOutput7[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length
				             + arrayOutput3.length
				             + arrayOutput4.length
				             + arrayOutput5.length
				                                  ][j] = arrayOutput7[i][j];
			}
		}
		for (int i = 0; i < arrayOutput8.length; i++) {
			for (int j = 0; j < arrayOutput8[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				             + arrayOutput2.length
				             + arrayOutput3.length
				             + arrayOutput4.length
				             + arrayOutput5.length
				             + arrayOutput7.length][j] = arrayOutput8[i][j];
			}
		}
		return arrayFinal;
	}

	public static void main(String[] args) {

	}

}
