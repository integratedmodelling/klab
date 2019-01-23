package org.integratedmodelling.mca.contrib.j_electre.matrix;

import org.integratedmodelling.mca.contrib.j_electre.ui.Interface;

public class ELECTRE_Tri {

	public static String[][] e_Tri_Algorithm(double[][] x, double[] p, double[] q, double[] v, double[] w, double[][] bh) {
		
		double[]   arrayCredM_x_bh  = Credibility.getCredibilityMatrixETri_x_bh(x, p, q, v, w, bh);
		double[]   arrayCredM_bh_x  = Credibility.getCredibilityMatrixETri_bh_x(x, p, q, v, w, bh);
		double[][] arrayCR          = new double [x.length*bh.length][2];
		double[][] arrayTemp        = new double [x.length*bh.length][x[0].length + 1];
		double[][] arrayTempD       = new double [x.length*bh.length][x[0].length];
		double[]   arrayTempCred1   = new double [x.length*bh.length];
		double[]   arrayTempCred2   = new double [x.length*bh.length];
        String[][] arrayCL          = new String [x.length + 1][2];
		String[][] arrayPR          = new String [x.length][bh.length];//bn > bn-1 > ... b0
		
        double cut_off = 0.75;
        int e  = Interface.ETriMe_Evaluators;
        int f  = 0;
        if (Interface.electre == 7){
        	cut_off = Interface.ETri_Lambda;
        }
        if (Interface.electre == 8){
        	cut_off = Interface.ETriMe_Lambda;
        }
        int k = 0;
        int L = 0;
        int m = 0;
        
        for (int j = 0; j < arrayCredM_x_bh.length; j++ ){
        	arrayCR[j][0] =  arrayCredM_x_bh[j];
        	arrayCR[j][1] =  arrayCredM_bh_x[j];
        }
  
		for (int profile = 0; profile < bh.length; profile++) {
			k = k * x.length;
			for (int j = 0; j < x.length; j++) {
				if (arrayCR[j + k][0] >= cut_off && arrayCR[j + k][1] >= cut_off) {
					arrayPR[j][profile] = "I";
				} else if (arrayCR[j + k][0] >= cut_off && arrayCR[j + k][1] < cut_off) {
					arrayPR[j][profile] = ">";
				} else if (arrayCR[j + k][0] < cut_off && arrayCR[j + k][1] >= cut_off) {
					arrayPR[j][profile] = "<";
				} else {
					arrayPR[j][profile] = "R";
				}
			}
			k = profile + 1;
		}
		
        arrayCL[0][0] = "Pessimist";
        arrayCL[0][1] = "Optimist";
        
        L = 0;
        
        for (int i = 0; i < arrayPR.length; i++){
        	for (int profile = 0; profile < bh.length; profile ++){
        		if (arrayPR[i][profile] == ">" || arrayPR[i][profile] == "I"){
        			arrayCL[i + 1][0] = String.valueOf((char)(L + 'A'));
        		}else if (arrayPR[i][profile] == "R" || arrayPR[i][profile] == "<"){
        			L = L + 1;
        			arrayCL[i + 1][0] = String.valueOf((char)(L + 'A'));
        		}
        	}
        	L = 0;
        }
        
        m = bh.length;
        
        for (int i = 0; i < arrayPR.length; i++){
        	for (int profile = 0; profile < bh.length; profile ++){
        		if (arrayPR[i][bh.length - profile -1] == "<"){
        			arrayCL[i + 1][1] = String.valueOf((char)(m + 'A'));
        		}else if (arrayPR[i][bh.length - profile -1] == ">" || arrayPR[i][bh.length - profile -1] == "R" || arrayPR[i][bh.length - profile -1] == "I"){
        			m = m - 1;
        			arrayCL[i + 1][1] = String.valueOf((char)(m + 'A'));
        		}
        	}
        	m = bh.length;
        } 
        
        ////////////////////////////
        
        
        String [][] arrayOutput1 = new String [arrayTemp.length + 2][arrayTemp[0].length + 3];
        int profilebh = bh.length + 1;
        int alt       = 0;
		arrayTemp = Concordance.getConcordanceMatrix_x_bh_ETri(x, p, q, w, bh);
		arrayOutput1[0][0] = "Concordance c( ai;bh ):";
		arrayOutput1[0][arrayTemp[0].length + 1] = "C( a;b )";
		for (int i = 0; i < arrayTemp.length; i++) {
			if((i) % x.length == 0){
				profilebh--;
				alt = 0;
			} else {	
				alt++;
			}
			for (int j = 0; j < arrayTemp[0].length - 1; j++) {
		        if (Interface.electre == 7){
		        	arrayOutput1[0][j + 2] = "g" + (j + 1);
		        }
				arrayOutput1[i + 1][1] = "c(a" + (alt + 1) + ";" + "b" + profilebh + ")";
			}
		}
		if (Interface.electre == 8){
			for (int i = 1; i <= e; i++){
				for (int j = 1; j < Interface.C + 1; j++){
					arrayOutput1[0][f + 2] = "EV" + i + "( g" + j + " )";
					if (f + 1 > x[0].length - 1){	
						
					} else {
						f = f + 1;
					}
				}
			}
			f = 0;
		}
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length  - 1; j++) {
				arrayOutput1[i + 1][j + 2] = Double.toString(Math.round(arrayTemp[i][j] * 10000d)/10000d);
			}
		}
		for (int i = 0; i < arrayTemp.length; i++) {
				arrayOutput1[i + 1][arrayTemp[0].length + 1] = Double.toString(Math.round(arrayTemp[i][arrayTemp[0].length - 1] * 10000d)/10000d);
		}
		for (int i = 0; i < arrayOutput1.length; i++) {
			for (int j = 0; j < arrayOutput1[0].length; j++) {
				if (arrayOutput1[i][j] == null){
					arrayOutput1[i][j] = "";
				}
			}
		}
		
        String [][] arrayOutput2 = new String [arrayTemp.length + 2][arrayTemp[0].length + 3];
        profilebh = bh.length + 1;
        alt       = 0;
		arrayTemp = Concordance.getConcordanceMatrix_bh_x_ETri(x, p, q, w, bh);
		arrayOutput2[0][0] = "Concordance c( bh;ai ):";
		arrayOutput2[0][arrayTemp[0].length + 1] = "C( b;a )";
		for (int i = 0; i < arrayTemp.length; i++) {
			if((i) % x.length == 0){
				profilebh--;
				alt = 0;
			} else {	
				alt++;
			}
			for (int j = 0; j < arrayTemp[0].length - 1; j++) {
		        if (Interface.electre == 7){
		        	arrayOutput2[0][j + 2] = "g" + (j + 1);
		        }
				arrayOutput2[i + 1][1] = "c(b" + profilebh + ";" + "a" + (alt + 1) + ")";
			}
		}
		if (Interface.electre == 8){
			for (int i = 1; i <= e; i++){
				for (int j = 1; j < Interface.C + 1; j++){
					arrayOutput2[0][f + 2] = "EV" + i + "( g" + j + " )";
					if (f + 1 > x[0].length - 1){	
						
					} else {
						f = f + 1;
					}
				}
			}
			f = 0;
		}
		for (int i = 0; i < arrayTemp.length; i++) {
			for (int j = 0; j < arrayTemp[0].length  - 1; j++) {
				arrayOutput2[i + 1][j + 2] = Double.toString(Math.round(arrayTemp[i][j] * 10000d)/10000d);
			}
		}
		for (int i = 0; i < arrayTemp.length; i++) {
				arrayOutput2[i + 1][arrayTemp[0].length + 1] = Double.toString(Math.round(arrayTemp[i][arrayTemp[0].length - 1] * 10000d)/10000d);
		}
		for (int i = 0; i < arrayOutput2.length; i++) {
			for (int j = 0; j < arrayOutput2[0].length; j++) {
				if (arrayOutput2[i][j] == null){
					arrayOutput2[i][j] = "";
				}
			}
		}
		
        String [][] arrayOutput3 = new String [arrayTempD.length + 2][arrayTempD[0].length + 2];
        profilebh = bh.length + 1;
        alt       = 0;
		arrayTempD = Discordance.getDiscordanceMatrix_x_bh_ETri(x, p, v, w, bh);
		arrayOutput3[0][0] = "Discordance d( ai;bh ):";
		for (int i = 0; i < arrayTempD.length; i++) {
			if((i) % x.length == 0){
				profilebh--;
				alt = 0;
			} else {	
				alt++;
			}
			for (int j = 0; j < arrayTempD[0].length; j++) {
		        if (Interface.electre == 7){
		        	arrayOutput3[0][j + 2] = "g" + (j + 1);
		        }
				arrayOutput3[i + 1][1] = "d(a" + (alt + 1) + ";" + "b" + profilebh + ")";
			}
		}
		if (Interface.electre == 8){
			for (int i = 1; i <= e; i++){
				for (int j = 1; j < Interface.C + 1; j++){
					arrayOutput3[0][f + 2] = "EV" + i + "( g" + j + " )";
					if (f + 1 > x[0].length - 1){	
						
					} else {
						f = f + 1;
					}
				}
			}
			f = 0;
		}
		for (int i = 0; i < arrayTempD.length; i++) {
			for (int j = 0; j < arrayTempD[0].length; j++) {
				arrayOutput3[i + 1][j + 2] = Double.toString(Math.round(arrayTempD[i][j] * 10000d)/10000d);
			}
		}
		for (int i = 0; i < arrayOutput3.length; i++) {
			for (int j = 0; j < arrayOutput3[0].length; j++) {
				if (arrayOutput3[i][j] == null){
					arrayOutput3[i][j] = "";
				}
			}
		}
		
        String [][] arrayOutput4 = new String [arrayTempD.length + 2][arrayTempD[0].length + 2];
        profilebh = bh.length + 1;
        alt       = 0;
		arrayTempD = Discordance.getDiscordanceMatrix_bh_x_ETri(x, p, v, w, bh);
		arrayOutput4[0][0] = "Discordance d( bh;ai ):";
		for (int i = 0; i < arrayTempD.length; i++) {
			if((i) % x.length == 0){
				profilebh--;
				alt = 0;
			} else {	
				alt++;
			}
			for (int j = 0; j < arrayTempD[0].length; j++) {
		        if (Interface.electre == 7){
		        	arrayOutput4[0][j + 2] = "g" + (j + 1);
		        }
				arrayOutput4[i + 1][1] = "d(b" + profilebh + ";" + "a" + (alt + 1) + ")";
			}
		}
		if (Interface.electre == 8){
			for (int i = 1; i <= e; i++){
				for (int j = 1; j < Interface.C + 1; j++){
					arrayOutput4[0][f + 2] = "EV" + i + "( g" + j + " )";
					if (f + 1 > x[0].length - 1){	
						
					} else {
						f = f + 1;
					}
				}
			}
			f = 0;
		}
		for (int i = 0; i < arrayTempD.length; i++) {
			for (int j = 0; j < arrayTempD[0].length; j++) {
				arrayOutput4[i + 1][j + 2] = Double.toString(Math.round(arrayTempD[i][j] * 10000d)/10000d);
			}
		}
		for (int i = 0; i < arrayOutput4.length; i++) {
			for (int j = 0; j < arrayOutput4[0].length; j++) {
				if (arrayOutput4[i][j] == null){
					arrayOutput4[i][j] = "";
				}
			}
		}
		
		String [][] arrayOutput5 = new String [arrayTempCred1.length + 2][5];
		arrayTempCred1 = Credibility.getCredibilityMatrixETri_x_bh(x, p, q, v, w, bh);
		arrayTempCred2 = Credibility.getCredibilityMatrixETri_bh_x(x, p, q, v, w, bh);
		arrayOutput5[0][0] = "Credibility:";
		arrayOutput5[0][2] = "( ai;bh )";
		arrayOutput5[0][4] = "( bh;ai )";
        profilebh = bh.length + 1;
        alt       = 0;
		for (int i = 0; i < arrayTempCred1.length; i++) {
			if((i) % x.length == 0){
				profilebh--;
				alt = 0;
			} else {	
				alt++;
			}
			arrayOutput5[i + 1][1] = "cr(a" + (alt + 1) + ";" + "b" + profilebh + ")";
			arrayOutput5[i + 1][3] = "cr(b" + profilebh + ";" + "a" + (alt + 1) + ")";
		}
		
		for (int i = 0; i < arrayTempCred1.length; i++){
			arrayOutput5[i + 1][2] = Double.toString(Math.round(arrayTempCred1[i] * 10000d)/10000d); 
		}
		for (int i = 0; i < arrayTempCred2.length; i++){
			arrayOutput5[i + 1][4] = Double.toString(Math.round(arrayTempCred2[i] * 10000d)/10000d);
		}
		for (int i = 0; i < arrayOutput5.length; i++) {
			for (int j = 0; j < arrayOutput5[0].length; j++) {
				if (arrayOutput5[i][j] == null){
					arrayOutput5[i][j] = "";
				}
			}
		}
		
		String [][] arrayOutput6 = new String [x.length + 1][5];
		arrayOutput6[0][0] = "Classification:";
		arrayOutput6[0][2] = "Alternative";
		arrayOutput6[0][3] = "Pessimist";
		arrayOutput6[0][4] = "Optmist";
		
		for (int i = 0; i < x.length; i++){	
			arrayOutput6[i + 1][2] = "a" + (i + 1);
			arrayOutput6[i + 1][3] = arrayCL[i + 1][0];
			arrayOutput6[i + 1][4] = arrayCL[i + 1][1];
		}
		
		for (int i = 0; i < arrayOutput6.length; i++) {
			for (int j = 0; j < arrayOutput6[0].length; j++) {
				if (arrayOutput6[i][j] == null){
					arrayOutput6[i][j] = "";
				}
			}
		}
		
		
		int cols = Math.max(arrayOutput1[0].length, Math.max(arrayOutput2[0].length,
				                                    Math.max(arrayOutput3[0].length,
						                            Math.max(arrayOutput4[0].length,
								                    Math.max(arrayOutput5[0].length, arrayOutput6[0].length)))));
		String [][] arrayFinal    = new String[      arrayOutput1.length 
		                                           + arrayOutput2.length 
		                                           + arrayOutput3.length 
		                                           + arrayOutput4.length 
		                                           + arrayOutput5.length
		                                           + arrayOutput6.length][cols + 1]; 
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
		for (int i = 0; i < arrayOutput6.length; i++) {
			for (int j = 0; j < arrayOutput6[0].length; j++) {
				arrayFinal[i + arrayOutput1.length
				           + arrayOutput2.length
				           + arrayOutput3.length
				           + arrayOutput4.length
				           + arrayOutput5.length][j] = arrayOutput6[i][j];
			}
		} 
		return arrayFinal;
	}
	
	public static void main(String[] args) {
		
	};

}
