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

public class Concordance {

	public static double[][] getConcordanceMatrixEI(double[][] array, double[] weights) {

		double[][] arrayCM = new double[array.length][array.length];
		double[]   arrayNW = MatrixOperations.getNormalizedWeigths(weights);

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayCM[i][j] = 0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						if (array[i][k] >= array[j][k]) {
							arrayCM[i][j] += arrayNW[k];
						}
					}
				}
			}
		}
		return arrayCM;
	}
	
	public static double[][] getConcordanceMatrixEI_s(double[][] array, double[] weights, double[] p, double[] q) {

		double[][] arrayCM     = new double[array.length][array.length];
		double[]   arrayCMTemp = new double[array[0].length];
		double[]   arrayP      = p;
		double[]   arrayQ      = q;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i == j) {
					arrayCM[i][j] = 1.0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						if (array[j][k] >= arrayP[k] + array[i][k]) {
							arrayCMTemp[k] = 0.0 * weights[k];
						}
						if (array[j][k] <= arrayQ[k] + array[i][k]) {
							arrayCMTemp[k] = 1.0 * weights[k];
						}
						if ((array[j][k] - array[i][k]) < arrayP[k] && (array[j][k] - array[i][k]) > arrayQ[k]) {
							arrayCMTemp[k] = (((array[j][k] - array[i][k]) - arrayP[k]) / (arrayQ[k] - arrayP[k]))* weights[k];
						}
					}
					arrayCM[i][j] = MatrixOperations.getWeightsSum(arrayCMTemp) / MatrixOperations.getWeightsSum(weights);
				}
			}
		}
		return arrayCM;
	}

	public static double[][] getConcordanceMatrixEI_v(double[][] array, double[] weights) {

		double[][] arrayCM = new double[array.length][array.length];
		double[]   arrayNW = MatrixOperations.getNormalizedWeigths(weights);

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayCM[i][j] = 0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						if (array[i][k] >= array[j][k]) {
							arrayCM[i][j] += arrayNW[k];
						}
					}
				}
			}
		}
		return arrayCM;
	}
	
	public static double[][] getConcordanceMatrixEII(double[][] array, double[] weights) {

		double[][] arrayCM = new double[array.length][array.length];
		double[]   arrayNW = MatrixOperations.getNormalizedWeigths(weights);

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayCM[i][j] = 0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						if (array[i][k] >= array[j][k]) {
							arrayCM[i][j] += arrayNW[k];
						}
					}
				}
			}
		}
		return arrayCM;
	}

	public static double[][] getConcordanceMatrixEIII(double[][] array, double[] weights, double[] p, double[] q) {

		double[][] arrayCM     = new double[array.length][array.length];
		double[]   arrayCMTemp = new double[array[0].length];
		double[]   arrayP      = p;
		double[]   arrayQ      = q;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i == j) {
					arrayCM[i][j] = 0.0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						if (array[j][k] >= arrayP[k] + array[i][k]) {
							arrayCMTemp[k] = 0.0 * weights[k];
						}
						if (array[j][k] <= arrayQ[k] + array[i][k]) {
							arrayCMTemp[k] = 1.0 * weights[k];
						}
						if ((array[j][k] - array[i][k]) < arrayP[k] && (array[j][k] - array[i][k]) > arrayQ[k]) {
							arrayCMTemp[k] = (((array[j][k] - array[i][k]) - arrayP[k]) / (arrayQ[k] - arrayP[k]))* weights[k];
						}
					}
					arrayCM[i][j] = MatrixOperations.getWeightsSum(arrayCMTemp) / MatrixOperations.getWeightsSum(weights);
				}
			}
		}
		return arrayCM;
	}
	
	public static double[][] getConcordanceMatrix_x_bh_ETri(double[][] x, double [] p, double [] q, double [] w, double[][] bh) {

		double[][] arrayCM = new double[x.length*bh.length][x[0].length + 1];
		double c = 0;
		int    k = 0;

		for (int profile = 0; profile < bh.length; profile++) {
			k = k * x.length;
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					if (bh[profile][j] - x[i][j] >= p[j]) {
						arrayCM[i + k][j] = 0;
					} else if (bh[profile][j] - x[i][j] < q[j]) {
						arrayCM[i + k][j] = 1;
					} else {
						arrayCM[i + k][j] = (p[j] - bh[profile][j] + x[i][j]) / (p[j] - q[j]);
					}
				}
				for (int count = 0; count < x[0].length; count++) {
					c = arrayCM[i + k][count] + c;
				}
				arrayCM[i + k][x[0].length] = c / MatrixOperations.getWeightsSum(w);
				c = 0;
			}
			k = profile + 1;
		}
		return arrayCM;
	}
	
	public static double[][] getConcordanceMatrix_bh_x_ETri(double[][] x, double [] p, double [] q, double [] w, double[][] bh) {

		double[][] arrayCM = new double[x.length*bh.length][x[0].length + 1];
		double c = 0;
		int    k = 0;

		for (int profile = 0; profile < bh.length; profile++) {
			k = k * x.length;
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					if (x[i][j] - bh[profile][j] >= p[j]) {
						arrayCM[i + k][j] = 0;
					} else if (x[i][j] - bh[profile][j] < q[j]) {
						arrayCM[i + k][j] = 1;
					} else {
						arrayCM[i + k][j] = (p[j] - x[i][j] + bh[profile][j]) / (p[j] - q[j]);
					}
				}
				for (int count = 0; count < x[0].length; count++) {
					c = arrayCM[i + k][count] + c;
				}
				arrayCM[i + k][x[0].length] = c / MatrixOperations.getWeightsSum(w);
				c = 0;
			}
			k = profile + 1;
		}
		return arrayCM;
	}

	public static void main(String[] args) {
	};
}
