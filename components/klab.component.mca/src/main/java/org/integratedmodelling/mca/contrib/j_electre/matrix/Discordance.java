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

import org.integratedmodelling.mca.contrib.j_electre.ui.Interface;

public class Discordance {

	public static double[][] getDiscordanceMatrixEI(double[][] array) {

		double[][] arrayN      = MatrixOperations.getNormalizedPerformanceMatrix(array);
		double[][] arrayDM     = new double[array.length][array.length];
		double[]   arrayDMTemp = new double[array[0].length];
		double[]   arrayDelta  = MatrixOperations.getDelta(arrayN);
		double     delta       = MatrixOperations.getMaxValueInRowSorting(arrayDelta);

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayDM[i][j] = 0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						arrayDMTemp[k] = (arrayN[j][k] - arrayN[i][k]);
					}

					Arrays.sort(arrayDMTemp);

					if (arrayDMTemp[arrayDMTemp.length - 1] >= 0) {
						arrayDM[i][j] = arrayDMTemp[arrayDMTemp.length - 1] / delta;
					} else {
						arrayDM[i][j] = 0;
					}
				}
			}
		}
		return arrayDM;
	}
	
	public static double[][] getDiscordanceMatrixEI_s(double c, double[][] array, double [] weights, double [] p, double[] q, double[] v, int k) {

		double[][] arrayDMj = new double[array.length][array.length];
		double[][] arrayCM  = Concordance.getConcordanceMatrixEI_s(array, weights, p, q);
		double[]   arrayQ   = q;
		double[]   arrayV   = v;
		double[]   arrayW   = MatrixOperations.getNormalizedWeigths(weights);
		c = Interface.EI_s_Lambda;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[j][k] - array[i][k] < arrayV[k] - arrayQ[k]*((1 - arrayCM[i][j]) - arrayW[k])/(1 - c - arrayW[k])) {
					arrayDMj[i][j] = 0.0;
				} else {
					arrayDMj[i][j] = 1.0;
				}
				
			}
		}
		return arrayDMj;
	}
	
	public static double[][] getDiscordanceMatrixEI_v(double[][] array, double[] v) {

		double[][] arrayDM = new double[array.length][array.length];
		double[]   arrayV   = v;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i == j) {
					arrayDM[i][j] = 0.0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						if (array[j][k] - array[i][k] >= arrayV[k]) { 
							arrayDM[i][j] = 1.0;
							break;
						}
					}
				}
			}
		}
		return arrayDM;
	}
	
	public static double[][] getDiscordanceMatrixEII(double[][] array) {

		double[]   arrayDMTemp = new double[array[0].length];
		double[][] arrayDM     = new double[array.length][array.length];
		double[]   arrayDelta  = MatrixOperations.getDelta(array);
		double     delta       = MatrixOperations.getMaxValueInRowSorting(arrayDelta);
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayDM[i][j] = 0;
				} else {
					for (int k = 0; k < array[0].length; k++) {
						arrayDMTemp[k] = (array[j][k] - array[i][k]);
					}

					Arrays.sort(arrayDMTemp);
					
					if (arrayDMTemp[arrayDMTemp.length - 1] >= 0) {
						arrayDM[i][j] = arrayDMTemp[arrayDMTemp.length - 1] / delta;
					} else {
						arrayDM[i][j] = 0;

					}
				}
			}
		}
		return arrayDM;
	}

	public static double[][] getDiscordanceMatrixEIII(double[][] array, double[] p, double[] v, int k) {

		double[][] arrayDMj = new double[array.length][array.length];
		double[]   arrayP   = p;
		double[]   arrayV   = v;
		double     DMTemp   = 0.0;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (i == j) {
					arrayDMj[i][j] = 0.0;
				} else {
					if (array[j][k] <= arrayP[k] + array[i][k]) {
						DMTemp = 0.0;
					}
					if (array[j][k] >= arrayV[k] + array[i][k]) {
						DMTemp = 1.0;
					}

					if ((array[j][k] - array[i][k]) > arrayP[k] && (array[j][k] - array[i][k]) < arrayV[k]) {
						DMTemp = (((array[j][k] - array[i][k]) - arrayP[k]) / (arrayV[k] - arrayP[k]));
					}
					arrayDMj[i][j] = DMTemp;
				}
			}
		}
		return arrayDMj;
	}
	
	public static double[][] getDiscordanceMatrix_x_bh_ETri(double[][] x, double [] p, double [] v, double [] w, double[][] bh) {

		double[][] arrayDM = new double[x.length*bh.length][x[0].length];
		int k = 0;

		for (int profile = 0; profile < bh.length; profile++) {
			k = k * x.length;
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					if (bh[profile][j] - x[i][j] < p[j]) {
						arrayDM[i + k][j] = 0;
					} else if (bh[profile][j] - x[i][j] >= v[j]) {
						arrayDM[i + k][j] = 1;
					} else {
						arrayDM[i + k][j] = (- p[j] + bh[profile][j] - x[i][j]) / (v[j] - p[j]);
					}
				}
			}
			k = profile + 1;
		}
		return arrayDM;
	}
	
	public static double[][] getDiscordanceMatrix_bh_x_ETri(double[][] x, double [] p, double [] v, double [] w, double[][] bh) {

		double[][] arrayDM = new double[x.length*bh.length][x[0].length];
		int k = 0;

		for (int profile = 0; profile < bh.length; profile++) {
			k = k * x.length;
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					if (x[i][j] - bh[profile][j] < p[j]) {
						arrayDM[i + k][j] = 0;
					} else if (x[i][j] - bh[profile][j] >= v[j]) {
						arrayDM[i + k][j] = 1;
					} else {
						arrayDM[i + k][j] = (- p[j] + x[i][j] - bh[profile][j]) / (v[j] - p[j]);
					}
				}
			}
			k = profile + 1;
		}
		return arrayDM;
	}

	public static void main(String[] args) {
	};
}
