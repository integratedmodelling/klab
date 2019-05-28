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
import Jama.Matrix;


public class JMatrix extends AbstractMatrix {

	private Matrix m;
	private boolean eigen= false;
	private double[] sortedval;
	private double[][] sortedvec;

	public JMatrix(double[][] elems){
		m = new Matrix(elems);
	}
	private JMatrix(Matrix matrix) {
		m = matrix;
	}

	
	public JMatrix(int rows, int columns) {
		m = new Matrix(rows, columns);
	}
	public JMatrix(int size) {
		m = Matrix.identity(size, size);
	}
	public int getNumberOfColumns() {
		return m.getColumnDimension();
	}

	
	public int getNumberOfRows() {
		return m.getRowDimension();
	}

	
	public double getElement(int i, int j) {
		return m.get(i, j);
	}

	
	public void setElement(int i, int j, double value) {
		m.set(i, j, value);
		eigen = false;
	}



	public double[][] convertIMatrixToArray() {
		return m.getArray();
	}

	public double[] getColumnOfMatrix(int k) {
		return m.getMatrix(0, m.getRowDimension()-1, k, k).getColumnPackedCopy();
	}

	
	public IMatrix transpose() {
		return new JMatrix(m.transpose());
	}

	
	public IMatrix times(IMatrix B)  {
		return new JMatrix(m.times(((JMatrix) B).m));
	}

	
	public IMatrix times(double aNum) {
		return new JMatrix(m.times(aNum));
	}

	
	public double[] getEigenValues() {
		if(!eigen) eigenCalc();
		return sortedval;
	}
	public double[][] getEigenVector() {
		if(!eigen) eigenCalc();		
		return sortedvec;
	}

	private void eigenCalc(){
		double[] val = m.eig().getRealEigenvalues();
		JMatrix vec = new JMatrix(m.eig().getV());
		EigenPair[] eig = new EigenPair[val.length];
		for (int i = 0 ; i<val.length; i++){
			eig[i] = new EigenPair(val[i],vec.getColumnOfMatrix(i));
		}
		Arrays.sort(eig);
		sortedval = new double[val.length];  
		sortedvec = new double[val.length][val.length];  
		for (int i = 0 ; i<val.length; i++){
			sortedval[i] = eig[i].val;
			sortedvec[i] = eig[i].vec; 
		}
		sortedvec = (new JMatrix(sortedvec)).transpose().convertIMatrixToArray();
		eigen= true;
		
	}
	


	
	public IMatrix minus(IMatrix bmat) {
		return new JMatrix(m.minus(((JMatrix) bmat).m));
	}

	
	public IMatrix inverse() {
		return new JMatrix(m.inverse());
	}

	
}
	
	


class EigenPair implements Comparable<EigenPair>{
	  public static double ERR = 0.0000001;
	 
		double val;
		double[] vec;
	 
		public EigenPair(double eigenvalue, double[] eigenvector) {
			this.val = eigenvalue;
	    this.vec = eigenvector;
		}
	  
		public int compareTo(EigenPair comparePair) {
	    //sort by x
	    if(Math.abs(this.val-comparePair.val) < ERR) return 0;    
	    return Double.compare(Math.abs(comparePair.val),Math.abs(this.val));
		}
	}

	


