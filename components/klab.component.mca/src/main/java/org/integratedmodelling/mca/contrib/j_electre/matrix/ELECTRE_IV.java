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

public class ELECTRE_IV {

	public static double[][] e_IV_Algorithm(double[][] array, double[] p, double[] q, double[] v) {

		double[][] arrayP1 = Credibility.getCredibilityMatrixEIV(array, p, q, v);
		
		return arrayP1;
	}
	
	public static double[][] rankDescending(double[][] array, double[] p, double[] q, double[] v) {

		double[][] arrayD       = e_IV_Algorithm(array, p, q, v);
		double[][] arrayDTie    = new double[arrayD.length][arrayD[0].length];
		double[][] arrayL1      = new double[arrayD.length][arrayD[0].length];
		double[][] arrayL2      = new double[arrayD.length][arrayD[0].length];
		double[][] arrayQ       = new double[arrayD.length][arrayD[0].length];
		double[][] arrayQTie    = new double[arrayD.length][arrayD[0].length];
		double[][] arrayTotal   = new double[array.length][3];
		double[][] arrayTTie    = new double[array.length][3];
		double[][] arrayRankD   = new double[array.length][array.length];
		double[]   alternatives = new double[array.length];

		double LambdaMax   =  0.0;
		double Lambda1Max  =  0.0;
		double Lambda2Max  =  0.0;
		double alpha       =  0.3;
		double beta        = -0.15;
		double sLambdaMax  =  0.1;
		double sLambda1Max =  0.1;
		double maxTotal    =  0.0;
		double maxTTie     =  0.0;

		int k     = 0;
		int count = 0;

		do {
			
			LambdaMax = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayD)); 

			for (int j = 0; j < arrayD[0].length; j++) {
				for (int i = 0; i < arrayD.length; i++) {
					if (arrayD[i][j] < LambdaMax - sLambdaMax) {
						arrayL1[i][j] = arrayD[i][j];
					} else {
						arrayL1[i][j] = 0.0;
					}
					if (i == j) {
						arrayL1[i][j] = 0.0;
					}
				}
			}

			Lambda1Max = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayL1));

			for (int j = 0; j < arrayD[0].length; j++) {
				for (int i = 0; i < arrayD.length; i++) {
					if (arrayD[i][j] > Lambda1Max && arrayD[i][j] > (arrayD[j][i] + alpha + beta * arrayD[i][j])) {
						arrayQ[i][j] = 1.0;
					} else {
						arrayQ[i][j] = 0.0;
					}
					if (i == j) {
						arrayQ[i][j] = 0.0;
					}
				}
			}
			for (int i = 0; i < array.length; i++) {
				if (arrayTotal[i][0] != -10001) {
					arrayTotal[i][0] = MatrixOperations.getWeightsSum(arrayQ[i]);
				}
				if (arrayTotal[i][1] != -10001) {
					arrayTotal[i][1] = MatrixOperations.getCriterionSum(arrayQ, i);
				}
				if (arrayTotal[i][2] != -10001) {
					arrayTotal[i][2] = arrayTotal[i][0] - arrayTotal[i][1];
				}
			}

			maxTotal = MatrixOperations.getMaxValueInColumnJ(arrayTotal, 2);

			for (int i1 = 0; i1 < array.length; i1++) {
				if (arrayTotal[i1][2] == maxTotal) {
					count = count + 1;
					alternatives[i1] = 1.0;
				} else {
					alternatives[i1] = 0.0;
				}
			}
			if (count == 1) {
				for (int i1 = 0; i1 < array.length; i1++) {

					if (arrayTotal[i1][2] == maxTotal) {

						arrayRankD[k][i1] =  1.0;
						arrayTotal[i1][0] = -10001;
						arrayTotal[i1][1] = -10001;
						arrayTotal[i1][2] = -10001;
						for (int remove = 0; remove < array.length; remove++) {
							arrayD[i1][remove] = 0.0;
							arrayD[remove][i1] = 0.0;
						}
					}
				}
			}

			if (count > 1) {

				for (int j2 = 0; j2 < alternatives.length; j2++) {
					for (int i2 = 0; i2 < alternatives.length; i2++) {
						arrayDTie[i2][j2] = 0.0;
					}
				}
				for (int j2 = 0; j2 < alternatives.length; j2++) {
					if (alternatives[j2] == 1.0) {
						for (int i2 = 0; i2 < arrayDTie.length; i2++) {
							if (alternatives[i2] == 1.0) {
								arrayDTie[i2][j2] = arrayD[i2][j2];
							}
						}
					}
				}

				for (int j2 = 0; j2 < arrayDTie[0].length; j2++) {
					for (int i2 = 0; i2 < arrayDTie.length; i2++) {
						if (arrayDTie[i2][j2] < Lambda1Max - sLambda1Max) {
							arrayL2[i2][j2] = arrayDTie[i2][j2];
						} else {
							arrayL2[i2][j2] = 0.0;
						}
						if (i2 == j2) {
							arrayL2[i2][j2] = 0.0;
						}
					}
				}
				
//**************************************************
				Lambda2Max = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayL2));
				
				for (int j2 = 0; j2 < arrayDTie[0].length; j2++) {
					for (int i2 = 0; i2 < arrayDTie.length; i2++) {
						if (arrayDTie[i2][j2] < Lambda2Max - sLambda1Max) {
							arrayL2[i2][j2] = arrayDTie[i2][j2];
						} else {
							arrayL2[i2][j2] = 0.0;
						}
						if (i2 == j2) {
							arrayL2[i2][j2] = 0.0;
						}
					}
				}

				Lambda2Max = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayL2));
				
//**************************************************
				for (int j2 = 0; j2 < arrayDTie[0].length; j2++) {
					for (int i2 = 0; i2 < arrayDTie.length; i2++) {
						if (arrayDTie[i2][j2] > Lambda2Max
								&& arrayDTie[i2][j2] > (arrayDTie[j2][i2] + alpha + beta * arrayDTie[i2][j2])) {
							arrayQTie[i2][j2] = 1.0;
						} else {
							arrayQTie[i2][j2] = 0.0;
						}
						if (i2 == j2) {
							arrayQTie[i2][j2] = 0.0;
						}
					}
				}
				for (int i2 = 0; i2 < array.length; i2++) {

					if (alternatives[i2] == 0.0) {
						
						arrayTTie[i2][0] = -10001;
						arrayTTie[i2][1] = -10001;
						arrayTTie[i2][2] = -10001;
						
					} else {
						
						arrayTTie[i2][0] = MatrixOperations.getWeightsSum(arrayQTie[i2]);
						arrayTTie[i2][1] = MatrixOperations.getCriterionSum(arrayQTie, i2);
						arrayTTie[i2][2] = arrayTTie[i2][0] - arrayTTie[i2][1];

					}
				}
				
				maxTTie = MatrixOperations.getMaxValueInColumnJ(arrayTTie, 2);

				for (int i2 = 0; i2 < array.length; i2++) {

					if (arrayTTie[i2][2] == maxTTie) {

						arrayRankD[k][i2] =  1.0;
						arrayTTie[i2][0]  = -10001;
						arrayTTie[i2][1]  = -10001;
						arrayTTie[i2][2]  = -10001;
						arrayTotal[i2][0] = -10001;
						arrayTotal[i2][1] = -10001;
						arrayTotal[i2][2] = -10001;
						for (int remove = 0; remove < array.length; remove++) {
							arrayD[i2][remove] = 0.0;
							arrayD[remove][i2] = 0.0;
						}
					}
				}
			}

			k = k + 1;
			count = 0;

		} while (MatrixOperations.get2DMatrixTotalSum(arrayRankD) < array.length);
		
		for (int i = 0; i < arrayRankD.length; i++) {
			for (int j = 0; j < arrayRankD[0].length; j++) {
				arrayRankD[i][j] = (arrayRankD[i][j]) * (i + 1);
			}
		}

		return arrayRankD;

	}

	public static double[][] rankAscending(double[][] array, double[] p, double[] q, double[] v) {

		double[][] arrayA       = e_IV_Algorithm(array, p, q, v);
		double[][] arrayATie    = new double[arrayA.length][arrayA[0].length];
		double[][] arrayL1      = new double[arrayA.length][arrayA[0].length];
		double[][] arrayL2      = new double[arrayA.length][arrayA[0].length];
		double[][] arrayQ       = new double[arrayA.length][arrayA[0].length];
		double[][] arrayQTie    = new double[arrayA.length][arrayA[0].length];
		double[][] arrayTotal   = new double[array.length][3];
		double[][] arrayTTie    = new double[array.length][3];
		double[][] arrayRankA   = new double[array.length][array.length];
		double[][] arrayInvert  = new double[array.length][array.length];
		double[]   alternatives = new double[array.length];

		double LambdaMax   =  0.0;
		double Lambda1Max  =  0.0;
		double Lambda2Max  =  0.0;
		double alpha       =  0.3;
		double beta        = -0.15;
		double sLambdaMax  =  0.1;
		double sLambda1Max =  0.1;
		double minTotal    =  0.0;
		double minTTie     =  0.0;

		int k     = 0;
		int count = 0;

		do {
			LambdaMax = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayA)); 

			for (int j = 0; j < arrayA[0].length; j++) {
				for (int i = 0; i < arrayA.length; i++) {
					if (arrayA[i][j] < LambdaMax - sLambdaMax) {
						arrayL1[i][j] = arrayA[i][j];
					} else {
						arrayL1[i][j] = 0.0;
					}
					if (i == j) {
						arrayL1[i][j] = 0.0;
					}
				}
			}

			Lambda1Max = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayL1));

			for (int j = 0; j < arrayA[0].length; j++) {
				for (int i = 0; i < arrayA.length; i++) {
					if (arrayA[i][j] > Lambda1Max && arrayA[i][j] > (arrayA[j][i] + alpha + beta * arrayA[i][j])) {
						arrayQ[i][j] = 1.0;
					} else {
						arrayQ[i][j] = 0.0;
					}
					if (i == j) {
						arrayQ[i][j] = 0.0;
					}
				}
			}
			for (int i = 0; i < array.length; i++) {
				if (arrayTotal[i][0] != 10001) {
					arrayTotal[i][0] = MatrixOperations.getWeightsSum(arrayQ[i]);
				}
				if (arrayTotal[i][1] != 10001) {
					arrayTotal[i][1] = MatrixOperations.getCriterionSum(arrayQ, i);
				}
				if (arrayTotal[i][2] != 10001) {
					arrayTotal[i][2] = arrayTotal[i][0] - arrayTotal[i][1];
				}
			}

			minTotal = MatrixOperations.getMinValueInColumnJ(arrayTotal, 2);

			for (int i1 = 0; i1 < array.length; i1++) {
				if (arrayTotal[i1][2] == minTotal) {
					count = count + 1;
					alternatives[i1] = 1.0;
				} else {
					alternatives[i1] = 0.0;
				}
			}
			if (count == 1) {
				for (int i1 = 0; i1 < array.length; i1++) {

					if (arrayTotal[i1][2] == minTotal) {

						arrayRankA[k][i1] = 1.0;
						arrayTotal[i1][0] = 10001;
						arrayTotal[i1][1] = 10001;
						arrayTotal[i1][2] = 10001;
						for (int remove = 0; remove < array.length; remove++) {
							arrayA[i1][remove] = 0.0;
							arrayA[remove][i1] = 0.0;
						}
					}
				}
			}

			if (count > 1) {

				for (int j2 = 0; j2 < alternatives.length; j2++) {
					for (int i2 = 0; i2 < alternatives.length; i2++) {
						arrayATie[i2][j2] = 0.0;
					}
				}
				for (int j2 = 0; j2 < alternatives.length; j2++) {
					if (alternatives[j2] == 1.0) {
						for (int i2 = 0; i2 < arrayATie.length; i2++) {
							if (alternatives[i2] == 1.0) {
								arrayATie[i2][j2] = arrayA[i2][j2];
							}
						}
					}
				}

				for (int j2 = 0; j2 < arrayATie[0].length; j2++) {
					for (int i2 = 0; i2 < arrayATie.length; i2++) {
						if (arrayATie[i2][j2] < Lambda1Max - sLambda1Max) {
							arrayL2[i2][j2] = arrayATie[i2][j2];
						} else {
							arrayL2[i2][j2] = 0.0;
						}
						if (i2 == j2) {
							arrayL2[i2][j2] = 0.0;
						}
					}
				}
//*******************************************************
				Lambda2Max = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayL2));
				
				for (int j2 = 0; j2 < arrayATie[0].length; j2++) {
					for (int i2 = 0; i2 < arrayATie.length; i2++) {
						if (arrayATie[i2][j2] < Lambda2Max - sLambda1Max) {
							arrayL2[i2][j2] = arrayATie[i2][j2];
						} else {
							arrayL2[i2][j2] = 0.0;
						}
						if (i2 == j2) {
							arrayL2[i2][j2] = 0.0;
						}
					}
				}

				Lambda2Max = MatrixOperations.getMaxValueInRowSorting(MatrixOperations.getMaxValueInColumn(arrayL2));
//*********************************************************				
				for (int j2 = 0; j2 < arrayATie[0].length; j2++) {
					for (int i2 = 0; i2 < arrayATie.length; i2++) {
						if (arrayATie[i2][j2] > Lambda2Max
								&& arrayATie[i2][j2] > (arrayATie[j2][i2] + alpha + beta * arrayATie[i2][j2])) {
							arrayQTie[i2][j2] = 1.0;
						} else {
							arrayQTie[i2][j2] = 0.0;
						}
						if (i2 == j2) {
							arrayQTie[i2][j2] = 0.0;
						}
					}
				}
				for (int i2 = 0; i2 < array.length; i2++) {

					if (alternatives[i2] == 0.0) {
						
						arrayTTie[i2][0] = 10001;
						arrayTTie[i2][1] = 10001;
						arrayTTie[i2][2] = 10001;
						
					} else {
						
						arrayTTie[i2][0] = MatrixOperations.getWeightsSum(arrayQTie[i2]);
						arrayTTie[i2][1] = MatrixOperations.getCriterionSum(arrayQTie, i2);
						arrayTTie[i2][2] = arrayTTie[i2][0] - arrayTTie[i2][1];
					}

				}

				minTTie = MatrixOperations.getMinValueInColumnJ(arrayTTie, 2);

				for (int i2 = 0; i2 < array.length; i2++) {

					if (arrayTTie[i2][2] == minTTie) {

						arrayRankA[k][i2] = 1.0;
						arrayTTie[i2][0]  = 10001;
						arrayTTie[i2][1]  = 10001;
						arrayTTie[i2][2]  = 10001;
						arrayTotal[i2][0] = 10001;
						arrayTotal[i2][1] = 10001;
						arrayTotal[i2][2] = 10001;
						for (int remove = 0; remove < array.length; remove++) {
							arrayA[i2][remove] = 0.0;
							arrayA[remove][i2] = 0.0;
						}
					}
				}
			}

			k = k + 1;
			count = 0;

		} while (MatrixOperations.get2DMatrixTotalSum(arrayRankA) < array.length);

		int p1 = MatrixOperations.getElementCountZero(MatrixOperations.getRowSum(arrayRankA)); 

		for (int i = 0; i < arrayInvert.length - p1; i++) {
			
			for (int j = 0; j < arrayInvert[0].length; j++) {

				arrayInvert[arrayInvert.length - 1 - p1 - i][j] = arrayRankA[i][j];
			}
		}

		arrayRankA = arrayInvert;
		
		for (int i = 0; i < arrayRankA.length; i++) {
			for (int j = 0; j < arrayRankA[0].length; j++) {
				arrayRankA[i][j] = (arrayRankA[i][j]) * (i + 1);
			}
		}

		return arrayRankA;

	}

	public static String[][] rankFinal(double[][] array, double[] p, double[] q, double[] v) {

		double[][] arrayRankA = rankAscending(array, p, q, v);
		double[][] arrayRankD = rankDescending(array, p, q, v);
		double[]   RD         = new double[array.length];
		double[]   RA         = new double[array.length];
		double[]   RM         = new double[array.length];
		double[]   rn         = new double[array.length];
		double[][] arrayTemp  = new double [array.length][array[0].length];
		
		String [][] arrayOutput1 = new String [array.length + 2][array.length + 2];
		arrayTemp = Credibility.getCredibilityMatrixEIV(array, p, q, v);
		arrayOutput1[0][0] = "Credibility Matrix:";
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
		for (int i = 1; i < arrayOutput1.length; i++) {
			for (int j = 1; j < arrayOutput1[0].length; j++) {
				
				if (arrayOutput1[i][j].equals("1.0")){
					arrayOutput1[i][j] = "Sq";
				}
				if (arrayOutput1[i][j].equals("0.8")){
					arrayOutput1[i][j] = "Sc";
				}
				if (arrayOutput1[i][j].equals("0.6")){
					arrayOutput1[i][j] = "Sp";
				}
				if (arrayOutput1[i][j].equals("0.4")){
					arrayOutput1[i][j] = "Ss";
				}
				if (arrayOutput1[i][j].equals("0.2")){
					arrayOutput1[i][j] = "Sv";
				}
				if (arrayOutput1[i][j].equals("0.0")){
					arrayOutput1[i][j] = "";
				}
				if (i + 1 == j){
					arrayOutput1[i][j] = "-";
				}
				
			}
		}

		RD = MatrixOperations.getColumnSum(arrayRankD);
		RA = MatrixOperations.getColumnSum(arrayRankA);
		for (int j = 0; j < RM.length; j++){
			RM[j] = (RA[j] + RD[j])/2; 
			rn[j] = (RA[j] + RD[j])/2; 
		}
		
		int rows = Math.max(RD.length, Math.max(RA.length,RM.length));
		String [][] arrayOutput2 = new String [rows + 2][7];
		arrayOutput2[0][0] = "Ranking:";
		arrayOutput2[0][2] = "Ascend.";
		arrayOutput2[0][3] = "Descend.";
		arrayOutput2[0][4] = "Average";
		
		for (int i = 0; i < RA.length; i++) {
				arrayOutput2[i + 1][1] = "a" + (i + 1);
		}	
		for (int i = 0; i < RA.length; i++){
			arrayOutput2[i + 1][2] = Double.toString(Math.round(RA[i] * 10000d)/10000d);
		}
		for (int i = 0; i < RD.length; i++){
			arrayOutput2[i + 1][3] = Double.toString(Math.round(RD[i] * 10000d)/10000d);
		}
		for (int i = 0; i < RM.length; i++){
			arrayOutput2[i + 1][4] = Double.toString(Math.round(RM[i] * 10000d)/10000d); 
		}
		for (int i = 0; i < arrayOutput2.length; i++) {
			for (int j = 0; j < arrayOutput2[0].length; j++) {
				if (arrayOutput2[i][j] == null){
					arrayOutput2[i][j] = "";
				}
			}
		}	
		
		String[][] RF         = new String[arrayRankA.length][arrayRankA[0].length];
		String[][] rg         = new String[arrayRankA.length][arrayRankA[0].length];
		for (int i = 0; i < arrayRankA.length; i++) {
			for (int j = 0; j < arrayRankA[0].length; j++) {
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
		
		String [][] arrayOutput3 = new String [rg.length + 2][rg[0].length + 2];
		arrayOutput3[0][0] = "Dominance Matrix:";
		for (int i = 0; i < rg.length; i++) {
			for (int j = 0; j < rg[0].length; j++) {
				arrayOutput3[0][j + 2] = "a" + (j + 1);
				arrayOutput3[i + 1][1] = "a" + (i + 1);
			}
		}
		for (int i = 0; i < rg.length; i++) {
			for (int j = 0; j < rg[0].length; j++) {
				arrayOutput3[i + 1][j + 2] = rg[i][j];
			}
		}
		for (int i = 0; i < arrayOutput3.length; i++) {
			for (int j = 0; j < arrayOutput3[0].length; j++) {
				if (arrayOutput3[i][j] == null){
					arrayOutput3[i][j] = "";
				}
			}
		}
		int cols = Math.max(arrayOutput1[0].length, Math.max(arrayOutput2[0].length,
													Math.max(arrayOutput3[0].length, 
															 arrayOutput3[0].length
															 )));
		String [][] arrayFinal    = new String[    arrayOutput1.length 
		                                           + arrayOutput2.length 
		                                           + arrayOutput3.length 
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
		return arrayFinal;
	}

	public static void main(String[] args) {
		
	}
}
