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

import java.util.Arrays;

public class MatrixOperations {

	public static double getWeightsSum(double[] array) {
		double sum = 0;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		return sum;
	}

	public static double getCriterionSum(double[][] array, int j) {
		double sum = 0;
		for (int i = 0; i < array[0].length; i++) {
			sum += array[i][j];
		}
		return sum;
	}

	public static double[] getNormalizedWeigths(double[] array) {
		double[] arrayNW = new double[array.length];
		double sum = getWeightsSum(array);
		for (int i = 0; i < array.length; i++) {
			arrayNW[i] = array[i] / sum;
		}
		return arrayNW;
	}

	public static double[] getColumnSum(double[][] array) {
		double[] arrayC = new double[array[0].length];
		for (int j = 0; j < array[0].length; j++) {
			for (int i = 0; i < array.length; i++) {
				arrayC[j] += array[i][j];
			}
		}
		return arrayC;
	}

	public static double[] getRowSum(double[][] array) {
		double[] arrayR = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				arrayR[i] += array[i][j];
			}
		}
		return arrayR;
	}

	public static double[][] getNormalizedPerformanceMatrix(double[][] array) {
		double[][] arrayNPM = new double[array.length][array[0].length];
		double[] a = getColumnSum(array);
		for (int j = 0; j < array[0].length; j++) {
			for (int i = 0; i < array.length; i++) {
				arrayNPM[i][j] = array[i][j] / a[j];
			}
		}
		return arrayNPM;
	}

	public static double[] getMaxValueInColumn(double[][] array) {
		double[] arrayMax = new double[array[0].length];
		for (int j = 0; j < array[0].length; j++) {
			double max = array[0][j];
			for (int i = 0; i < array.length; i++) {
				if (array[i][j] >= max) {
					max = array[i][j];
					arrayMax[j] = array[i][j];
				}
			}
		}
		return arrayMax;
	}

	public static double getMaxValueInColumnJ(double[][] array, int j) {
		double max = -100001;
		for (int i = 0; i < array.length; i++) {
			if (array[i][j] >= max) {
				max = array[i][j];
			}
		}
		return max;
	}

	public static double[] getMinValueInColumn(double[][] array) {
		double[] arrayMin = new double[array[0].length];
		for (int j = 0; j < array[0].length; j++) {
			double min = array[0][j];
			for (int i = 0; i < array.length; i++) {
				if (array[i][j] <= min) {
					min = array[i][j];
					arrayMin[j] = array[i][j];
				}
			}
		}
		return arrayMin;
	}

	public static double getMinValueInColumnJ(double[][] array, int j) {
		double min = 100001;
		for (int i = 0; i < array.length; i++) {
			if (array[i][j] <= min) {
				min = array[i][j];
			}
		}
		return min;
	}

	public static double getMaxValueInRowSorting(double[] array) {
		Arrays.sort(array);
		double max = array[array.length - 1];
		return max;
	}

	public static double getMaxValueInRowNonSorting(double[] array) {
		double max = -100001;
		if (array.length == 1) {
			max = array[0];
		}
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
			}
		}
		return max;
	}

	public static double getMinValueInRowNonSorting(double[] array) {
		double min = 100001;
		for (int i = 0; i < array.length; i++) {
			if (min > array[i]) {
				min = array[i];
			}
		}
		return min;
	}

	public static double[] getDelta(double[][] array) {
		double[] arrayDelta = new double[array[0].length];
		double[] a = getMaxValueInColumn(array);
		double[] b = getMinValueInColumn(array);
		for (int i = 0; i < array[0].length; i++) {
			arrayDelta[i] = a[i] - b[i];
		}
		return arrayDelta;
	}
	
	public static int getElementCountZero(double[] array) {
		int elementcount = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == 0) {
				elementcount = elementcount + 1;
			}
		}
		return elementcount;
	}

	public static int get2DElementCountSpecificValue(double [][] array, double b) {
		int elementcount = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++){
				if (array[i][j] == b){
					elementcount = elementcount + 1;
				}
			}
		}
		return elementcount;
	}

	public static double[][] getTransposed2DMatrix(double[][] array) {
		double[][] arrayT2D = new double[array[0].length][array.length];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				arrayT2D[j][i] = array[i][j];
			}
		}
		return arrayT2D;
	}

	public static double[][] get2DMatrixSum(double[][] array1, double[][] array2) {
		double[][] arrayS2D = new double[array1.length][array1[0].length];
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array1[0].length; j++) {
				arrayS2D[i][j] = array1[i][j] + array2[i][j];
			}
		}
		return arrayS2D;
	}

	public static double get2DMatrixTotalSum(double[][] array) {
		double total = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				total += array[i][j];
			}
		}
		return total;
	}

	public static double[][] get2DDirectMatrixMult(double[][] array1, double[][] array2) {
		double[][] arrayM2D = new double[array1.length][array1.length];
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array1[0].length; j++) {
				arrayM2D[i][j] = array1[i][j] * array2[i][j];
			}
		}
		return arrayM2D;
	}

	public static double[][] get2DRemoveRow(double[][] array1, int row) {
		double arrayRD[][] = new double[array1.length - 1][array1[0].length];
		int p = 0;
		for (int i = 0; i < array1.length; ++i) {
			if (i == row)
				continue;
			int q = 0;
			for (int j = 0; j < array1[0].length; ++j) {
				arrayRD[p][q] = array1[i][j];
				q = q + 1;
			}
			p = p + 1;
		}
		return arrayRD;
	}

	public static String get2DSearchString(String R, String C, String [][] array) {
		String value = "Not Found!";
		loop:
			for (int i = 1; i < array.length; i++){
				for (int j = 1; j < array[0].length; j++) {
					if (array[i][0].equals(R) && array[0][j].equals(C)){
						value = array[i][j];
						break loop;
					}
				}   
			}
		return value;
	}

	public static String get2DSearchStringUpper(String R, String C, String [][] array) {
		String value = "Not Found!";
		loop:
			for (int i = 1; i < array.length; i++){
				for (int j = i + 1; j < array[0].length; j++) {
					if (array[i][0].equals(R) && array[0][j].equals(C)){
						value = array[i][j];
						break loop;
					}
				}   
			}
		return value;
	}
	
	public static double[][] get2DRemoveColumn(double[][] array1, int column) {
		double arrayRD[][] = new double[array1.length][array1[0].length - 1];
		int p = 0;
		for (int i = 0; i < array1.length; ++i) {
			int q = 0;
			for (int j = 0; j < array1[0].length; ++j) {
				if (j == column)
					continue;
				arrayRD[p][q] = array1[i][j];
				q = q + 1;
			}
			p = p + 1;
		}
		return arrayRD;
	}

	public static String[][] get2DRemoveColumnString(String[][] array1, int column) {
		String arrayRD[][] = new String[array1.length][array1[0].length - 1];
		int p = 0;
		for (int i = 0; i < array1.length; ++i) {
			int q = 0;
			for (int j = 0; j < array1[0].length; ++j) {
				if (j == column)
					continue;
				arrayRD[p][q] = array1[i][j];
				q = q + 1;
			}
			p = p + 1;
		}
		return arrayRD;
	}

	public static String[][] get2DRemoveRowString(String[][] array1, int row) {
		String arrayRD[][] = new String[array1.length - 1][array1[0].length];
		int p = 0;
		for (int i = 0; i < array1.length; ++i) {
			if (i == row)
				continue;
			int q = 0;
			for (int j = 0; j < array1[0].length; ++j) {
				arrayRD[p][q] = array1[i][j];
				q = q + 1;
			}
			p = p + 1;
		}
		return arrayRD;
	}

	public static void main(String[] args) {
	}

};
