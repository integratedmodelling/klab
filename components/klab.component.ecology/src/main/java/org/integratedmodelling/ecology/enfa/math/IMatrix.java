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

/**
 * @author Ioannis Athanasiadis 
 * @author Georgina Examiliotou
 * @since 03/2015
 */
public interface IMatrix {

    /**
     * @return the number of columns
     */
    int getNumberOfColumns();

    /**
     * @return the number of rows
     */
    int getNumberOfRows();

    /**
     * @return a single element of the internal 2-D arraymatrix
     */
    double getElement(int i, int j);

    /**Set an individual array element
     * @param i = row index
     * @param j = column index
     * @param aa = value of the element
     */
    void setElement(int i, int j, double aa);

    /**
     * Set a matrix as the elements of column
     * @param offset
     * @param elements
     * @throws RuntimeException when elements are fewer than matrix rows
     */
    public void setColumn(int offset, IMatrix elements);

    /**Returns the minimum value of each column of a matrix size x*y
     * <i>infinity if no such value</i> <br/>
     * @return an 1-dimensional array size of y with double values contains the min value of each column. 
     * Size of array equal to size of column of the initial matrix. (<i>Output</i>)
     */
    double[] columnMinima();

    /**Returns the maximum value of each column of a matrix size x*y
     * <i>infinity if no such value</i> <br/>
     * @return an 1-dimensional array size of y with double values contains the max value of each column. Size of array equal to size of column of the initial matrix. (<i>Output</i>)
     */
    double[] columnMaxima();

    /**Calculates the average value of each column of a matrix size x*y and returns an array with double values <br />
     * @return an 1-dimensional array size of y with double values contains the average value of each column. Size of array equal to size of column of the initial matrix. (<i>Input</i>)<br/ >
     */
    double[] columnMean();

    /**Calculates the standard deviation of each column of a matrix size x*y and returns an array with double values <br />
     * @return an 1-dimensional array size of y with double values contains the standard deviation of each column. Size of array equal to size of column of the initial matrix. (<i>Input</i>)<br/ >
     */
    double[] columnStandardDeviations();

    /**Calculates the sum of each column of a matrix size x*y and returns an array with double values <br />
     * @return an 1-dimensional array size of y with double values contains the sum value of each column. 
     * Size of array equal to size of column of the initial matrix. (<i>Input</i>)<br/ >
     */
    double[] columnSum();

    /**
     * For each column returns the sum of all squared elements
     * @return squares sum
     */
    double[] columnSquaredSum();

    /**
     * @return the mean of all element of this matrix 
     */
    double mean();

    /**
     * Printing a Matrix <br />
     */
    void printMatrix();

    /**Printing a set of statistics based on a matrix <br/>
     * @param matrixName : a name for the printed matrix (It's helpful for the printing messages)(<i>Input</i>)
     */
    void printStatistics(String matrixName);

    /**Printing a set of statistics based on a matrix <br/>
     * @param matrixName : a name for the printed matrix (It's helpful for the printing messages)(<i>Input</i>)
     * @param format : decimal format must be something like "#.######" representing the number of decimals wanter to print and round the double number(<i>Input</i>)
     */
    void printStatisticsWithDecimalFormat(String matrixName, String format);

    /**
     * Calculates the transposed matrix. A matrix which is formed by turning all the rows of a given matrix into columns and vice-versa. 
     * @return the transposed matrix
     */
    IMatrix transpose();

    /**MULTIPLICATION A*B
     * Multiply this  matrix by another matrix.
     * This matrix remains unaltered.
     * 
     * @param B another matrix
     * @return the product this matrix with another matrix A * B
     * @exception IllegalArgumentException
     *                Matrix inner dimensions must agree.
     */
    IMatrix times(IMatrix B);

    /**
     * Given that _this+ matrix has n rows and w is a single-column matrix of n rows
     * this method returns a matrix of the same dimensions of _this_ whose elements are 
     * this[:,i]*w[i]
     * 
     * @param w
     * @return product
     */
    IMatrix timesByRow(IMatrix w);

    /**
     * This method convert an IMatrix size of x*y to a double 2D array size of x*y<br />
     * @return : a double 2D array (<i>Output</i>)
     */
    double[][] convertIMatrixToArray();

    /** Gets the k-th column of this matrix and convert it to a double 1D array  
     * @param k the k-th column. 
     * @return an 1D array with the values of the column
     */
    double[] getColumnOfMatrix(int k);

    /**Multiply this matrix by a constant number.
     * Each cell of this matrix is multiplied by a constant number and returns a new matrix.
     * @param aNum a constant double number
     * @return a new matrix
     */
    IMatrix times(double aNum);

    /**Return eigen values as calculated
     * @return eigen values
     */
    double[] getEigenValues();

    /**Return eigen vectors as calculated as columns
     *Each vector as a column
     * @return eigenvectors
     */
    double[][] getEigenVector();

    /**Returns an identity matrix, with all elements in main diagonal equal to 1.
     * @param numberOfRows the number of rows of this square matrix
     * @return an identity matrix size= numberOfRows * numberOfRows
     */
    // IMatrix identityMatrix(int numberOfRows);

    /**Subtraction. This matrix minus another matrix. 
     * A-B when both A,B are matrices
     * @param bmat a IMatrix
     * @return another IMatrix
     */
    IMatrix minus(IMatrix bmat);

    /**
     * @param aMatrix
     * @return the median of each column
     */
    double[] columnMedian();

    /**
     * @return the main diagonal of a matrix
     */
    double[] getDiagonal();

    /**Check if a matrix is square
     * @return true if the matrix is square
     */
    public boolean isSquare();

    /**
     * @return the inverse of a square matrix
     */
    public IMatrix inverse();

    // Subtract by row
    IMatrix sweepMinus(double[] center);

    IMatrix sweepTimes(double[] center);

    IMatrix sweepDivide(double[] center);

}
