/**
 * Dirichlet.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 15, 2008
 *
 * ----------------------------------------------------------------------------------
 * This file is part of RiskWiz.
 * 
 * RiskWiz is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RiskWiz is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with the software; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 * ----------------------------------------------------------------------------------
 * 
 * @copyright 2008 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      May 15, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.learning.dtable;


/**
 * @author Sergey Krivov
 *
 */
public class Dirichlet extends Dist {
	
    double[] params;
    double sum;

    /**
     * 
     */
    public Dirichlet() {
        super();
        initialize();
    }
	
    public Dirichlet(int dimension) {
        super(dimension);
        initialize();
    }
	
    public Dirichlet(int dimension, double[] pars) {
        super(dimension);
        initialize(pars);
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.learning.dtable.Dist#getExpectedVal(int)
     */
    @Override
	public double getExpectedVal(int dimension) {
        // TODO Auto-generated method stub
        return params[dimension] / sum;
    }
	
    private void initialize() {
        params = new double[this.dimension];
        for (int i = 0; i < params.length; i++) {
            params[i] = 1;
        }
        sum = this.dimension;
    }
	
    private void initialize(double[] pars) {
        params = new double[this.dimension];
        sum = 0;
        for (int i = 0; i < params.length; i++) {
            params[i] = pars[i];
            sum += pars[i];
        }
    }
	
    public void increment(int dim) {
        params[dim]++;
        sum++;
		
    }
	
    public void decrement(int dim) {
        params[dim]--;
        sum--;
		
    }
	
    public void setParameter(int dim, double val) {
        params[dim] = val;
        sum = 0;
        for (int i = 0; i < params.length; i++) {			 
            sum += params[i];
        }
    }

}
