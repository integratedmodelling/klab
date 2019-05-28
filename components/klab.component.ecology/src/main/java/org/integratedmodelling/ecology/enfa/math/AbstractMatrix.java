/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *     - Ioannis Athanasiadis
 *     - Ferdinando Villa <ferdinando.villa@bc3research.org>
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

import java.text.DecimalFormat;

public abstract class AbstractMatrix implements IMatrix {

	@Override
    public double[] columnMinima() {
		double[] ret = new double[getNumberOfColumns()];
		for(int col=0; col <getNumberOfColumns(); col++){
			ret[col] = StdStats.min(getColumnOfMatrix(col));
		}
		return ret;
	}

	@Override
    public double[] columnSquaredSum(){
		double[] ret = new double[getNumberOfColumns()];
		for(int col=0; col <getNumberOfColumns(); col++)
			for(int row = 0; row<getNumberOfRows(); row++)
				ret[col] += getElement(row,col)*getElement(row,col);
		return ret;
	}

	
	@Override
    public double[] columnMaxima() {
		double[] ret = new double[getNumberOfColumns()];
		for(int col=0; col <getNumberOfColumns(); col++){
			ret[col] = StdStats.max(getColumnOfMatrix(col));
		}
		return ret;
	}

	
	@Override
    public double[] columnMean() {
		double[] ret = new double[getNumberOfColumns()];
		for(int col=0; col <getNumberOfColumns(); col++){
			ret[col] = StdStats.mean(getColumnOfMatrix(col));
		}
		return ret;
	}	

	
	@Override
    public double[] columnStandardDeviations() {
		double[] ret = new double[getNumberOfColumns()];
		for(int col=0; col <getNumberOfColumns(); col++){
			ret[col] = StdStats.stddev(getColumnOfMatrix(col));
		}
		return ret;
	}

	
	@Override
    public double[] columnSum() {
		double[] ret = new double[getNumberOfColumns()];
		for(int col=0; col <getNumberOfColumns(); col++){
			ret[col] = StdStats.sum(getColumnOfMatrix(col));
		}
		return ret;
	}
	
	
	@Override
    public double[] columnMedian() {
		double[] ret = new double[getNumberOfColumns()];
		for(int col=0; col <getNumberOfColumns(); col++){
			ret[col] = StdStats.median(getColumnOfMatrix(col));
		}
		return ret;
	}
	
	
	
	
	@Override
    public IMatrix timesByRow(IMatrix w){
		if ((getNumberOfRows()!=w.getNumberOfRows())||w.getNumberOfColumns()>1)
			throw new RuntimeException("timesByRow dimentions do mot agree");
		IMatrix m = MatrixFactory.createMatrix(getNumberOfRows(), getNumberOfColumns());
		for (int i = 0; i< getNumberOfRows(); i++){
			for (int j = 0; j< getNumberOfColumns(); j++){
				m.setElement(i, j, getElement(i,j)*w.getElement(i, 0));
			}
		}
		return m;
		
	}
	
	@Override
    public IMatrix sweepMinus(double[] center){
		if(getNumberOfColumns()!=center.length) throw new RuntimeException("sweep dimentions do not agree");
		IMatrix m = MatrixFactory.createMatrix(getNumberOfRows(), getNumberOfColumns());
		for (int i = 0; i< getNumberOfRows(); i++){
			for (int j = 0; j< getNumberOfColumns(); j++){
				m.setElement(i, j, getElement(i,j) - center[j]);
			}
		}
		return m;
	}
	
	@Override
    public IMatrix sweepTimes(double[] center){
		if(getNumberOfColumns()!=center.length) throw new RuntimeException("sweep dimentions do not agree");
		IMatrix m = MatrixFactory.createMatrix(getNumberOfRows(), getNumberOfColumns());
		for (int i = 0; i< getNumberOfRows(); i++){
			for (int j = 0; j< getNumberOfColumns(); j++){
				m.setElement(i, j, getElement(i,j) * center[j]);
			}
		}
		return m;
	}
	
	@Override
    public IMatrix sweepDivide(double[] center){
		if(getNumberOfColumns()!=center.length) throw new RuntimeException("sweep dimentions do not agree");
		IMatrix m = MatrixFactory.createMatrix(getNumberOfRows(), getNumberOfColumns());
		for (int i = 0; i< getNumberOfRows(); i++){
			for (int j = 0; j< getNumberOfColumns(); j++){
				m.setElement(i, j, getElement(i,j) / center[j]);
			}
		}
		return m;
	}
	
	@Override
    public double mean() {
		double sum=0;
		for (int i = 0 ; i<getNumberOfRows(); i++){
			for (int j = 0 ; j<getNumberOfColumns(); j++){
				sum +=getElement(i,j);
			}
		}
			return sum/(getNumberOfRows()*getNumberOfColumns());
	}


	@Override
    public void printMatrix(){
		
		DecimalFormat df = new DecimalFormat("#.#######");
		for(int i=0;i<getNumberOfRows();i++){
			for(int j=0;j<getNumberOfColumns();j++){
					System.out.print(df.format(getElement(i, j))+ " ");
					if(j==getNumberOfColumns()-1)
						System.out.println();
			}
		}
	}
	

	
	@Override
    public void setColumn(int offset, IMatrix elements){
		if(elements.getNumberOfRows()!=getNumberOfRows() || offset>=getNumberOfColumns()) throw new RuntimeException("Cant set column elements");
		int upper = Math.min(offset+elements.getNumberOfColumns(),getNumberOfColumns());
		for(int r=0; r<getNumberOfRows(); r++){
			for(int c = offset; c<upper; c++)
			setElement(r,c,elements.getElement(r, c-offset));
		}
	}
	
	/**Printing a set of statistics based on a matrix <br/>
	 * @param matrixName : a name for the printed matrix (It's helpful for the printing messages)(<i>Input</i>)
	 */
	@Override
    public void printStatistics(String matrixName){
		printStatisticsWithDecimalFormat(matrixName,"#.#######" );
	}
	
	/**Printing a set of statistics based on a matrix <br/>
	 * @param matrixName : a name for the printed matrix (It's helpful for the printing messages)(<i>Input</i>)
	 * @param format : decimal format must be something like "#.######" representing the number of decimals wanter to print and round the double number(<i>Input</i>)
	 */
	@Override
    public void printStatisticsWithDecimalFormat(String matrixName, String format){
		
		DecimalFormat df = new DecimalFormat(format);
		
		System.out.println("Statistics for " + matrixName + " matrix:");
		System.out.println("Size " + getNumberOfRows() + " rows, " + getNumberOfColumns() + " cols");

		{	System.out.println("Column\t"+ 
								"Min      \t"+
								"Median   \t"+ 
								"Mean     \t"+ 
								"Max      \t"+
								"StDev    \t" 
								);
			for (int j=0;j<getNumberOfColumns();j++){
			System.out.println(j+ "\t" + df.format(columnMinima()[j]) + "\t" +
										 df.format(columnMedian()[j]) + "\t" + 
										 df.format(columnMean()[j]) +"\t" +
										 df.format(columnMaxima()[j])+ "\t" +
										 df.format(columnStandardDeviations()[j]));			
			}
		}
		
	
	}


	


	
	@Override
    public double[] getDiagonal()  {
		if(!this.isSquare()) throw new RuntimeException("The matrix must be square!");
		double diag[] =  new double[this.getNumberOfRows()];
		for(int i=0;i<this.getNumberOfRows();i++){
				diag[i] = this.getElement(i, i);
			}
		return diag;	
		}

	
	@Override
    public boolean isSquare() {
		return (getNumberOfColumns()==getNumberOfRows());
	}

	@Override
    public String toString(){
		return "Rows " + getNumberOfRows() + " Cols " + getNumberOfColumns() ;
	}
	


}
