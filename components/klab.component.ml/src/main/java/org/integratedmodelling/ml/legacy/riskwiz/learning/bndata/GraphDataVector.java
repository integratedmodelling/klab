/**
 * GraphDataVector.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 20, 2008
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
 * @date      May 20, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.learning.bndata;


import java.util.Vector;


/**
 * @author Sergey Krivov
 *
 */
public class GraphDataVector extends GraphDataAdapter  implements IGraphTable {
    Vector<String> scheme;
    Vector<Vector<String>> table;

    /**
     * 
     */
    public GraphDataVector(Vector<String> scheme, Vector<Vector<String>> table) {
        this.scheme = scheme;
        this.table = table;
    }
	
    public GraphDataVector(String[] schemeArray, String[][] tableArray) {
        this.scheme = new Vector<String>();
        for (int i = 0; i < schemeArray.length; i++) {
            this.scheme.add(schemeArray[i]);
        }
		
        this.table = new Vector<Vector<String>>();
        for (int i = 0; i < tableArray.length; i++) {
            Vector<String> tuple = new Vector<String>();

            for (int j = 0; j < tableArray[i].length; j++) {
                tuple.add(tableArray[i][j]);
            }
            this.table.add(tuple);
        }
		
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.learning.bndata.GraphDataAdapter#getScheme()
     */
    @Override
    public Vector<String> getScheme() {		 
        return scheme;
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.learning.bndata.GraphDataAdapter#getValues()
     */
	 
    @Override
	public Vector<Vector<String>> getValues() {		 
        return table;
    }

}
