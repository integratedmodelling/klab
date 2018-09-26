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
package klab.component.klab.mca;

import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.EigenvalueDecomposition;

/**
 * Helper class to define and obtain absolute quantitative criteria weights from a matrix of
 * pairwise comparison values.
 * 
 * If a ranking matrix is available, the static getRankings methods can be used directly without
 * instantiating an object. Otherwise, a PairwiseComparator object can be instantiated and used
 * to facilitate creating the matrix by calling rankPair on each couple. The other half of the
 * matrix is filled in automatically, and the matrix is initialized to neutral (all 1s).
 * 
 * TODO calculate and handle inconsistency (requires boring table from Saaty 1980)
 * TODO implement other weight extraction methods (eigenvalue method is problematic at times)
 * 
 * @author Ferdinando Villa
 *
 */
public class AHP {

    double[][] rankings = null;
    int        size     = 0;

    public AHP(int nCriteria) {

        size = nCriteria;
        rankings = new double[nCriteria][nCriteria];

        /* initialize to neutral */
        for (int i = 0; i < nCriteria; i++) {
            for (int j = 0; j < nCriteria; j++) {
                rankings[i][j] = 1.0;
            }
        }
    }

    /**
     * Rank two criteria. If the passed value is NaN, don't do anything (leave the
     * equality set at initialization).
     * 
     * @param critA
     * @param critB
     * @param ranking
     */
    public void rankPair(int critA, int critB, double ranking) {

        if (rankings == null) {
            throw new KlabValidationException("PairwiseComparison: no rankings defined, please use static methods.");
        }

        if (!Double.isNaN(ranking)) {
            if (critA != critB) {
                rankings[critA][critB] = ranking;
                rankings[critB][critA] = 1.0 / ranking;
            }
        }
    }

    /**
     * Use this one if you initialized the object with the constructor and used rankPair to
     * set the rankings.
     * 
     * @return rankings
     */
    public double[] getRankings() {

        double[] ret = null;

        if (rankings == null)
            throw new KlabValidationException("PairwiseComparison: no rankings defined, please use static methods.");

        try {
            ret = getRankings(rankings);
        } catch (KlabException e) {
            // won't happen if we built it using rankPair
        }

        return ret;
    }

    public double[][] getPairwiseMatrix() {
        return rankings;
    }

    public static double[] getRankings(double[][] ranks) throws KlabException {

        double[] ret = new double[ranks.length];

        /*
         * compute eigenvalues and eigenvectors
         */
        EigenvalueDecomposition eig = new EigenvalueDecomposition(new DenseDoubleMatrix2D(ranks));

        /*
         * find eigenvector corresponding to dominant real eigenvalue
         */
        int m = 0;
        double[] vv = eig.getRealEigenvalues().toArray();

        for (int i = 1; i < vv.length; i++) {
            if (vv[i] > vv[m])
                m = i;
        }

        for (int i = 0; i < ranks.length; i++) {
            ret[i] = eig.getV().getQuick(m, i);
        }

        /* 
         * normalize dominant eigenvector to sum up to 1.0
         */
        double min = Evamix.min(ret);
        double max = Evamix.max(ret);
        double sum = 0.0;

        for (int i = 0; i < ranks.length; i++) {
            ret[i] = (ret[i] - max + min);
            sum += ret[i];
        }
        for (int i = 0; i < ranks.length; i++) {
            ret[i] /= sum;
        }

        return ret;
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {

        AHP ahp = new AHP(3);

        // expert choice gives .067, .344, .589 for 0,1,2
        ahp.rankPair(0, 1, 9);
        ahp.rankPair(0, 2, 5);
        ahp.rankPair(1, 2, 3);

        double[] r = ahp.getRankings();
        double[][] m = ahp.getPairwiseMatrix();

        for (int i = 0; i < ahp.size(); i++) {

            for (int j = 0; j < ahp.size(); j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println("\t" + r[i]);
        }

    }

}
