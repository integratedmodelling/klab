package org.integratedmodelling.mca.contrib.j_electre.matrix;

import org.integratedmodelling.mca.contrib.j_electre.ui.Interface;

public class Credibility {

	public static double[][] getCredibilityMatrixEI(double[][] array, double[] weights) {

		double p = Interface.EI_P;
		double q = Interface.EI_Q; 
		double[][] arrayCredM = new double[array.length][array.length];
		double[][] arrayCM    = Concordance.getConcordanceMatrixEI(array, weights);
		double[][] arrayDM    = Discordance.getDiscordanceMatrixEI(array);

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayCredM[i][j] = 0;
				} else {
					if (arrayCM[i][j] >= p && arrayDM[i][j] <= q) {
						arrayCredM[i][j] = 1;
					} else {
						arrayCredM[i][j] = 0;
					}
				}
			}
		}
		return arrayCredM;
	}
	
	public static double[][] getCredibilityMatrixEI_s(double[][] array, double[] weights, double[] p, double[] q, double[] v) {

		double[][] arrayCredM = new double[array.length][array.length];
		double[][] arrayDM = new double[array.length][array.length];
		double[][] arrayCM    = Concordance.getConcordanceMatrixEI_s(array, weights, p, q);
		int k = 0;
		double c = Interface.EI_s_Lambda;
		
		for (k = 0; k < array[0].length; k++) {
			double[][] arrayDMj = Discordance.getDiscordanceMatrixEI_s(c, array, weights, p, q, v, k);
			arrayDM = MatrixOperations.get2DMatrixSum(arrayDM, arrayDMj);
		}
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayDM[i][j] = 0;
				}
			}
		}
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (arrayDM[i][j] > 0) {
					arrayDM[i][j] = 1.0;
				} else {
					arrayDM[i][j] = 0.0;
				}
			}
		}
		
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (arrayCM[i][j] >= c && arrayDM[i][j] == 0) {
					arrayCredM[i][j] = 1.0;
				} else {
					arrayCredM[i][j] = 0.0;
				}
			}
		}
		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j ) {
					arrayCredM[i][j] = 0.0;
				} 
			}
		}
		return arrayCredM;
	}
	
	public static double[][] getCredibilityMatrixEI_v(double[][] array, double[] v, double[] weights) {

		double p = Interface. EI_v_P;
		double q = Interface. EI_v_Q;
		double[][] arrayCredM = new double[array.length][array.length];
		double[][] arrayCM    = Concordance.getConcordanceMatrixEI_v(array, weights);
		double[][] arrayDM    = Discordance.getDiscordanceMatrixEI_v(array, v);

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayCredM[i][j] = 0;
				} else {
					if (arrayCM[i][j] >= p && arrayDM[i][j] <= q) {
						arrayCredM[i][j] = 1;
					} else {
						arrayCredM[i][j] = 0;
					}
				}
			}
		}
		return arrayCredM;
	}

	public static double[][] getCredibilityMatrixEII(double[][] array, double[] weights) {

		double c1 = Interface.EII_Cp;
		double c2 = Interface.EII_C;  
		double c3 = Interface.EII_Cm;
		double dm = Interface.EII_d2; 
		double dp = Interface.EII_d1; 
		double[][] arrayCredMS = new double[array.length][array.length];
		double[][] arrayCredMW = new double[array.length][array.length];
		double[][] arrayCredMT = new double[array.length][array.length];
		double[][] arrayCM     = Concordance.getConcordanceMatrixEII(array, weights);
		double[][] arrayDM     = Discordance.getDiscordanceMatrixEII(array);

		for (int j = 0; j < array.length; j++) {// Strong
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayCredMS[i][j] = 0;
				} else {
					if (arrayCM[i][j] >= c1 && arrayDM[i][j] <= dp && arrayCM[i][j] >= arrayCM[j][i]
					|| arrayCM[i][j]  >= c2 && arrayDM[i][j] <= dm && arrayCM[i][j] >= arrayCM[j][i]) {
						arrayCredMS[i][j] = 1;
					} else {
						arrayCredMS[i][j] = 0;
					}
				}
			}
		} 
		for (int j = 0; j < array.length; j++) {// Weak
			for (int i = 0; i < array.length; i++) {
				if (i == j) {
					arrayCredMW[i][j] = 0;
				} else {
					if (arrayCM[i][j] >= c3 && arrayDM[i][j] <= dm && arrayCM[i][j] >= arrayCM[j][i]) {
						arrayCredMW[i][j] = 1;
					} else {
						arrayCredMW[i][j] = 0;
					}
				}
			}
		}
		arrayCredMT = MatrixOperations.get2DMatrixSum(arrayCredMS, arrayCredMW);
		return arrayCredMT;
	}

	public static double[][] getCredibilityMatrixEIII(double[][] array, double[] weights, double[] p, double[] q, double[] v) {

		double[][] arrayCredM = new double[array.length][array.length];
		double[][] arrayCM    = Concordance.getConcordanceMatrixEIII(array, weights, p, q);
		int k = 0;

		for (int j = 0; j < array.length; j++) {
			for (int i = 0; i < array.length; i++) {
				arrayCredM[i][j] = 1.0;
			}
		}
		
		for (k = 0; k < array[0].length; k++) {
			double[][] arrayDM = Discordance.getDiscordanceMatrixEIII(array, p, v, k);
			for (int j = 0; j < array.length; j++) {
				for (int i = 0; i < array.length; i++) {
					if (i != j){
						if (arrayDM[i][j] <= arrayCM[i][j]) {
							arrayDM[i][j] = 1.0;
						} else {
							arrayDM[i][j] = (1 - arrayDM[i][j]) / (1 - arrayCM[i][j]);
						}
						arrayCredM[i][j] = arrayCredM[i][j] * arrayDM[i][j];
					} else{
						arrayCredM[i][j] = 0.0;
					}
				}
			}
		}
		arrayCredM = MatrixOperations.get2DDirectMatrixMult(arrayCredM, arrayCM);
		return arrayCredM;
	}

	public static double[][] getCredibilityMatrixEIV(double[][] array, double[] p, double[] q, double[] v) {

		double mp  = 0.0;
		double mq  = 0.0;
		double mi  = 0.0;
		double mo  = 0.0;
		double Sq  = 1.0;
		double Sc  = 0.8;
		double Sp  = 0.6;
		double Ss  = 0.4;
		double Sv  = 0.2;
		
		int k = 0;
		
		double[][] aComparison = new double[array.length*array.length][array[0].length];
		double[][] aVeto       = new double[array.length*array.length][array[0].length];
		
		double[][] arrayMp_ab  = new double[array.length][array.length];
		double[][] arrayMq_ab  = new double[array.length][array.length];
		double[][] arrayMi_ab  = new double[array.length][array.length];
		double[][] arrayMo     = new double[array.length][array.length];
		double[][] arrayMp_ba  = new double[array.length][array.length];
		double[][] arrayMq_ba  = new double[array.length][array.length];
		double[][] arrayMi_ba  = new double[array.length][array.length];
		double[][] arrayVeto   = new double[array.length][array.length];
		double[][] arrayS      = new double[array.length][array.length];
		double[][] arrayM      = new double[array.length][array.length];

		double[][] arrayCredM = new double[array.length][array.length];
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int r = 0; r < array[0].length; r++) {
					aComparison[k][r] = array[j][r] - array[i][r];
					aVeto[k][r] = array[j][r] - array[i][r];
				}
				k = k + 1;
			}
		}
		k = 0;

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int r = 0; r < array[0].length; r++) {
					if (aComparison[k][r] > p[r]) {
						mp = mp + 1;
					}
				}
				k = k + 1;
				arrayMp_ab[i][j] = mp;
				mp = 0;
			}
		}
		k = 0;
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int r = 0; r < array[0].length; r++) {
					if (aComparison[k][r] > q[r] && aComparison[k][r] <= p[r]) {
						mq = mq + 1;
					}
				}
				k = k + 1;
				arrayMq_ab[i][j] = mq;
				mq = 0;
			}
		}
		k = 0;
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int r = 0; r < array[0].length; r++) {
					if (aComparison[k][r] >= -q[r] && aComparison[k][r] <= q[r] && aComparison[k][r] > 0 ) {
						mi = mi + 1;
					}
				}
				k = k + 1;
				arrayMi_ab[i][j] = mi;
				mi = 0;
			}
		}
		k = 0;
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int r = 0; r < array[0].length; r++) {
					if (aComparison[k][r] == 0 ) {
						mo = mo + 1;
					}
				}
				k = k + 1;
				arrayMo[j][i] = mo;
				arrayMo[i][j] =  0;
				mo = 0;
			}
		}
		k = 0;
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				for (int r = 0; r < array[0].length; r++) {
					if (aVeto[k][r] >= -v[r]) {
						aVeto[k][r] = 1.0;
						if (i != j) {
							arrayVeto[i][j] = 1.0;
						}
					}
				}
				k = k + 1;
			}
		}
		k = 0;
		
		arrayMp_ba  = MatrixOperations.getTransposed2DMatrix(arrayMp_ab);
		arrayMq_ba  = MatrixOperations.getTransposed2DMatrix(arrayMq_ab);
		arrayMi_ba  = MatrixOperations.getTransposed2DMatrix(arrayMi_ab);
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {		
				arrayS[i][j] = arrayMp_ab[i][j] + arrayMq_ab[i][j] + arrayMi_ab[i][j] + arrayMo[i][j];
			}
		}	
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {		
				arrayM[i][j] = arrayS[i][j] + arrayS[j][i];
			}
		}
		
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {		
				if(arrayMp_ab[i][j] == 0 || arrayMp_ab[i][j] == 1 && arrayMp_ba[i][j] >= array[0].length/2 && arrayVeto[i][j] >= 1){
					arrayCredM[i][j] = Sv;	
				}
				if(arrayMp_ab[i][j] == 0){			
					arrayCredM[i][j] = Ss;	
				}
				if(arrayMp_ab[i][j] == 0 && arrayMq_ab[i][j] <= arrayMp_ba[i][j] + arrayMq_ba[i][j]){	
					arrayCredM[i][j] = Sp;	
				}
				if(arrayMp_ab[i][j] == 0 && arrayMq_ab[i][j] <= arrayMq_ba[i][j] && arrayMq_ab[i][j] + arrayMi_ab[i][j] <= arrayMp_ba[i][j] + arrayMq_ba[i][j] + arrayMi_ba[i][j]){	
					arrayCredM[i][j] = Sc;	
				}
				if(arrayMp_ab[i][j] + arrayMq_ab[i][j] == 0 && arrayMi_ab[i][j] <= arrayMp_ba[i][j] + arrayMq_ba[i][j] + arrayMi_ba[i][j]){	
					arrayCredM[i][j] = Sq;	
				}
				if(i == j){	
					arrayCredM[i][j] = 0.0;	
				}
			}
		}
		return arrayCredM;
}

	public static double[] getCredibilityMatrixETri_x_bh(double[][] x, double[] p, double[] q, double[] v, double[] w, double[][] bh) {

		double[][] arrayCM_x_bh    = Concordance.getConcordanceMatrix_x_bh_ETri(x, p, q, w, bh);
		double[][] arrayDM_x_bh    = Discordance.getDiscordanceMatrix_x_bh_ETri(x, p, v, w, bh);
		double[][] arrayTemp_x_bh  = new double[x.length*bh.length][x[0].length];
		double[]   productDM       = new double[x.length*bh.length];
		double[]   arrayCredM_x_bh = new double[x.length*bh.length];
		
		for (int i = 0; i < arrayDM_x_bh.length; i++){
			for (int j = 0; j < arrayDM_x_bh[0].length; j++){
				if(arrayDM_x_bh[i][j] >  arrayCM_x_bh[i][arrayDM_x_bh[0].length]){
					arrayTemp_x_bh[i][j] = (1 - arrayDM_x_bh[i][j])/(1 - arrayCM_x_bh[i][arrayDM_x_bh[0].length]);
				} else{
					arrayTemp_x_bh[i][j] = 1;
				}
			}
		}
		 
		for (int i = 0; i < arrayTemp_x_bh.length; i++) {
			productDM[i] = arrayTemp_x_bh[i][0];
			for (int j = 1; j < arrayTemp_x_bh[0].length; j++) {
				productDM[i] = productDM[i]*arrayTemp_x_bh[i][j];
			}
		}
		
		for (int i = 0; i < arrayDM_x_bh.length; i++) {
			for (int j = 0; j < arrayDM_x_bh[0].length; j++) {
				if (arrayDM_x_bh[i][j] > arrayCM_x_bh[i][arrayDM_x_bh[0].length]) {
					arrayCredM_x_bh[i] = arrayCM_x_bh[i][arrayDM_x_bh[0].length]*productDM[i];
					break;
				} else {
					arrayCredM_x_bh[i] = arrayCM_x_bh[i][arrayDM_x_bh[0].length];
				}
			}
		}
		
		return arrayCredM_x_bh;
	}
	
	public static double[] getCredibilityMatrixETri_bh_x(double[][] x, double[] p, double[] q, double[] v, double[] w, double[][] bh) {

		double[][] arrayCM_bh_x    = Concordance.getConcordanceMatrix_bh_x_ETri(x, p, q, w, bh);
		double[][] arrayDM_bh_x    = Discordance.getDiscordanceMatrix_bh_x_ETri(x, p, v, w, bh);
		double[][] arrayTemp_bh_x  = new double[x.length*bh.length][x[0].length];
		double[]   productDM       = new double[x.length*bh.length];
		double[]   arrayCredM_bh_x = new double[x.length*bh.length];
		
		for (int i = 0; i < arrayDM_bh_x.length; i++){
			for (int j = 0; j < arrayDM_bh_x[0].length; j++){
				if(arrayDM_bh_x[i][j] >  arrayCM_bh_x[i][arrayDM_bh_x[0].length]){
					arrayTemp_bh_x[i][j] = (1 - arrayDM_bh_x[i][j])/(1 - arrayCM_bh_x[i][arrayDM_bh_x[0].length]);
				} else{
					arrayTemp_bh_x[i][j] = 1;
				}
			}
		}
		 
		for (int i = 0; i < arrayTemp_bh_x.length; i++) {
			productDM[i] = arrayTemp_bh_x[i][0];
			for (int j = 1; j < arrayTemp_bh_x[0].length; j++) {
				productDM[i] = productDM[i]*arrayTemp_bh_x[i][j];
			}
		}
		
		for (int i = 0; i < arrayDM_bh_x.length; i++) {
			for (int j = 0; j < arrayDM_bh_x[0].length; j++) {
				if (arrayDM_bh_x[i][j] > arrayCM_bh_x[i][arrayDM_bh_x[0].length]) {
					arrayCredM_bh_x[i] = arrayCM_bh_x[i][arrayDM_bh_x[0].length]*productDM[i];
					break;
				} else {
					arrayCredM_bh_x[i] = arrayCM_bh_x[i][arrayDM_bh_x[0].length];
				}
			}
		}
		
		return arrayCredM_bh_x;
	}
	public static void main(String[] args) {
	};
}
