/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.ecology.enfa.math;

import java.util.Arrays;


/**
 * This is the default ENFA engine, 
 * using the adehabitat implementation in R.
 * 
 * @author Ioannis Athanasiadis
 *
 */
public class NicheFactorAnalysis {
	
	boolean initialized = false;
	

	private IMatrix normalize(IMatrix egvs){
		//centring by the mean
		double[] means = egvs.columnMean();
		IMatrix df = egvs.sweepMinus(means);
		df.printStatistics("df");
		double[] k = df.columnSquaredSum();
		
		double[] norm = new double[k.length];
		for(int i = 0; i<norm.length; i++){
			norm[i] = Math.sqrt(k[i]/egvs.getNumberOfRows());
			norm[i] = norm[i]<1e-8?1:1/norm[i];
		}
		df=df.sweepTimes(norm);
		
		return df;
	}
	
	
	
	public IMatrix train(IMatrix pres, IMatrix egvs) {
		egvs.printStatistics("egv - original");
		pres.printStatistics("pres - original");
		IMatrix egv = normalize(egvs);
		egv.printStatistics("egv - normalized");
		// Supposedly we have done PCA and we figured out what the loading weights are.
		
		IMatrix lw = MatrixFactory.createMatrix( pres.getNumberOfRows(),1);
		for(int i=0; i<lw.getNumberOfRows(); i++){
				lw.setElement(i,0, 1);
		}
		return train(1,pres,lw, egvs);		
	}
	
	
	/**
	 * @param nf number of factors
	 * @param pres data
	 * @param egvs 
	 */
	private IMatrix train(int  nf, IMatrix pres, IMatrix lw, IMatrix egvs){
		
		//Code in R: pr <- pr/sum(pr)
		IMatrix pr = pres.times(1/pres.columnSum()[0]);

		//Code in R : row.w <- dudi$lw/sum(dudi$lw)
	    //			col.w <- dudi$cw Present this line of code with the integer 1

		IMatrix row_w = lw.times(1/lw.columnSum()[0]);
				
		//Code in R : Z <- as.matrix(dudi$tab) This matrix is the input matrix 
	    //			 	n <- nrow(Z)  int n = EGV.getNumberOfRows(); It is not used..
	    //				f1 <- function(v) sum(v * row.w), row.w is the lw Vector
	    //				center <- apply(Z, 2, f1)
		//				Z <- sweep(Z, 2, center)
		IMatrix f1 = egvs.timesByRow(row_w);
		double[] center = f1.columnSum();
		IMatrix Z = egvs.sweepMinus(center);
		
//	    Ze <- sweep(Z, 2, sqrt(col.w), "*")  - does nothing
		IMatrix Ze = Z;
//	    DpZ <- apply(Ze, 2, function(x) x * pr)
		IMatrix DpZ = Z.timesByRow(pr);
	    
//	    mar <- apply(Z, 2, function(x) sum(x * pr))
		double[] mar = Z.timesByRow(pr).columnSum();
				
//	    me <- mar * sqrt(col.w)  - does nothing
		IMatrix me = MatrixFactory.createMatrix(mar);
		
//	    Se <- crossprod(Ze, DpZ)
		IMatrix Se = Ze.transpose().times(DpZ);
//	    Ge <- crossprod(Ze, apply(Ze, 2, function(x) x * row.w))
		IMatrix Ge = Ze.transpose().times(Ze.timesByRow(row_w));
		
//	    eS <- eigen(Se)
		double[] eS_values = Se.getEigenValues();
		double[][] eS_vectors = Se.getEigenVector();
		
//	    kee <- (eS$values > 1e-09)
//	    S12 <- eS$vectors[, kee] %*% diag(eS$values[kee]^(-0.5)) %*% 
//        t(eS$vectors[, kee])

		double[][] eS_vectors_kee = cleanEigenVectors(eS_values,eS_vectors);
		IMatrix m1 = MatrixFactory.createMatrix(eS_vectors_kee);
		double[] eS_values_kee = Arrays.copyOf(eS_values, eS_vectors_kee[0].length);
		double[] diag = new double[eS_values_kee.length];
		for(int i = 0; i<diag.length; i++){
			diag[i] = 1/Math.sqrt(eS_values_kee[i]);
		}
		IMatrix m2 = MatrixFactory.createDiagonal(diag);
		IMatrix m3 = m1.transpose();
		IMatrix S12 = m1.times(m2).times(m3);

//	    W <- S12 %*% Ge %*% S12
		IMatrix W = S12.times(Ge).times(S12);

//	    x <- S12 %*% me
		IMatrix x = S12.times(me);
		
		//	    b <- x/sqrt(sum(x^2))
		IMatrix b = x.times(1/Math.sqrt(x.columnSquaredSum()[0]));
				
//	    H <- (diag(ncol(Ze)) - b %*% t(b)) %*% W %*% 
//		     (diag(ncol(Ze)) - b %*% t(b))
		IMatrix M = MatrixFactory.createUnitMatrix(Ze.getNumberOfColumns()).minus(b.times(b.transpose()));
		IMatrix H = M.times(W).times(M);
		
//	    s <- eigen(H)$values[-ncol(Z)] // I think that this just drops the last eigenvalue.
		double[] h = H.getEigenValues();
//		double[] s = Arrays.copyOf(h,h.length-1);

		
		// Create the covariance matrix co
		// co <- matrix(nrow = ncol(Z), ncol = nf + 1)
		IMatrix co = MatrixFactory.createMatrix(Z.getNumberOfColumns(), nf+1);
		// tt <- data.frame((S12 %*% eigen(H)$vectors)[, 1:nf])
		// trick to filter eigenvectors
		double[] filter = new double[h.length];
		for (int i=0;i<nf; i++)
			filter[i]=1;
		IMatrix hEigV = MatrixFactory.createMatrix(cleanEigenVectors(filter, H.getEigenVector()));
		IMatrix tt = S12.times(hEigV);

//	    ww <- apply(tt, 2, function(x) x/sqrt(col.w)) -- does nothing as col.w is ones
		IMatrix ww = tt;
//	    norw <- sqrt(diag(t(as.matrix(tt)) %*% as.matrix(tt)))
		double[] norw = (tt.transpose()).times(tt).getDiagonal();
		for(int e=0; e<norw.length;e++)
			norw[e] = Math.sqrt(norw[e]);
//	    co[, 2:(nf + 1)] <- sweep(ww, 2, norw, "/")
		co.setColumn(1, ww.sweepDivide(norw));

		//	    m <- me/sqrt(col.w)
		//	    co[, 1] <- m/sqrt(sum(m^2))
		//	    m <- sum(m^2)

		//assuming col.w equals ones m = me
		co.setColumn(0, me.times(1/Math.sqrt(me.columnSquaredSum()[0])));
		

		//	    li <- Z %*% apply(co, 2, function(x) x * col.w)
		IMatrix li = Z.times(co);
		
		
		//////////////////////////////
		/// ENFA TRAIN ENDS HERE /////
		//////////////////////////////
//	    if ((missing(nf)) || (nf > object$nf)) 
//	        nf <- object$nf
//	    nf might changes for testing thus I filter it again. Otherwise Zli is li
//	    Zli <- object$li[, 1:(nf + 1)] 
		IMatrix Zli = MatrixFactory.createMatrix(egvs.getNumberOfRows(), nf+1);
		Zli.setColumn(0, li);


// Sli selects only present data. When presence occurred more than once we replicate this element several times 		
//	    f1 <- function(x) rep(x, object$pr)
//	    Sli <- apply(Zli, 2, f1)

		IMatrix Sli = MatrixFactory.createMatrix((int) pres.columnSum()[0], Zli.getNumberOfColumns());
		int k = 0;	
		int z = 0; //pointer of rows of Sli
		for(int i=0;i<Zli.getNumberOfRows();i++){
			if(pres.getElement(i, 0) > 0){	
				k = (int) pres.getElement(i, 0);	
					while (k>0){
						for(int j=0; j<Zli.getNumberOfColumns();j++){		
							Sli.setElement(z,j,Zli.getElement(i, j));	
						}
						k--;
						z++;
					}
				}
		}
		
//	    m <- apply(Sli, 2, mean)
		double[] mm = Sli.columnMean();
//	    cov <- t(as.matrix(Sli)) %*% as.matrix(Sli)/nrow(Sli)
		IMatrix cov = Sli.transpose().times((Sli.times(1.0/Sli.getNumberOfRows())));
//	    maha <- mahalanobis(Zli, center = m, cov = cov)
		IMatrix maha =  mahalanobis(Zli, mm, cov);
		// lower maha, more suitable!
//	    map <- getkasc(df2kasc(data.frame(toto = maha, tutu = maha), 
//	        index, attr), "toto")
		maha.printStatistics("maha");
		return maha;
	}
	
	
	
	private IMatrix mahalanobis(IMatrix x, double[] center, IMatrix cov) {
		//     x <- sweep(x, 2, center)
		x = x.sweepMinus(center);
		// cov <- solve(cov, ...) // calc invert of cov
		// rowSums((x %*% cov) * x)
		IMatrix s = (x.times(cov.inverse()));
		IMatrix maha = MatrixFactory.createMatrix(x.getNumberOfRows(), 1);
		for(int row=0;row<maha.getNumberOfRows();row++){
			double sum = 0.0;
			for(int col=0;col<x.getNumberOfColumns();col++){
				sum += s.getElement(row, col)*x.getElement(row, col);
			}
			maha.setElement(row, 0, sum);
		}
		return maha;
	}







	private double[][] cleanEigenVectors(double[] eigenValues, double[][] eigenVectors){
		int kee = 0;
		while(kee<eigenValues.length && eigenValues[kee]>1e-09){
			kee++;
		}		
//		double[] newValues = new double[kee];
		double[][] newVector = new double[eigenVectors.length][kee];
		for(int column=0; column<kee;column++){
//			newValues[column] = eigenValues[column];
			for (int row=0;row<eigenVectors.length;row++)
				newVector[row][column] = eigenVectors[row][column];
		}
		return newVector;
	}
	
	
	
	




}
