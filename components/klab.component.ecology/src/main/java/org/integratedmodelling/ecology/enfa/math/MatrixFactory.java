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

/*
 * @author Ioannis Athanasiadis 
 */
package org.integratedmodelling.ecology.enfa.math;

public class MatrixFactory {
    public enum Option {
        FLANAGAN,
        JAMA
    };

    public static Option option = Option.JAMA;

    public static IMatrix createMatrix(int rows, int columns) {
        // if(option==Option.FLANAGAN)
        // return new FMatrix(rows, columns);
        // else
        if (option == Option.JAMA)
            return new JMatrix(rows, columns);
        return null;
    }

    public static IMatrix identityMatrix(int size) {
        // if(option==Option.FLANAGAN)
        // return new FMatrix(size);
        // else
        if (option == Option.JAMA)
            return new JMatrix(size);
        return null;
    }

    /**
     * Creates a single column matrix
     * @param elements
     * @return column matrix
     */
    public static IMatrix createMatrix(double[] elements) {
        double[][] mat = new double[1][elements.length];
        mat[0] = elements;
        return createMatrix(mat).transpose();
    }

    public static IMatrix createMatrix(double[][] elements) {
        // if(option==Option.FLANAGAN)
        // return new FMatrix(elements);
        // else
        if (option == Option.JAMA)
            return new JMatrix(elements);
        return null;
    }

    public static IMatrix createUnitMatrix(int size) {
        IMatrix res = MatrixFactory.createMatrix(size, size);
        for (int i = 0; i < size; i++) {
            res.setElement(i, i, 1);
        }
        return res;
    }

    public static IMatrix createDiagonal(double[] array) {
        IMatrix res = MatrixFactory.createMatrix(array.length, array.length);
        for (int i = 0; i < array.length; i++) {
            res.setElement(i, i, array[i]);
        }
        return res;
    }

}
