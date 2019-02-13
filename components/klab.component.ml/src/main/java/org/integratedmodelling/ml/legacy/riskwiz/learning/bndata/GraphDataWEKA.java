/**
 * GraphDataWEKA.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: May 21, 2008
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
 * @date      May 21, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.learning.bndata;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.learning.data.Instance;
import org.integratedmodelling.ml.legacy.riskwiz.learning.data.Instances;


/**
 * @author Sergey Krivov
 *
 */
public class GraphDataWEKA extends GraphDataAdapter implements IGraphTable {
	
    Vector<String> scheme;
    Vector<Vector<String>> table;

    /**
     * 
     */
    public GraphDataWEKA(Instances insts) {
		 
        this.scheme = new Vector<String>();
        int rowsize = insts.numAttributes();

        for (int i = 0; i < rowsize; i++) {
            this.scheme.add(insts.attribute(i).name());
        }
		
        this.table = new Vector<Vector<String>>();
        for (int i = 0; i < insts.numInstances(); i++) {
            Vector<String> tuple = new Vector<String>(insts.numInstances());
            Instance  inst = insts.instance(i);
			 
            for (int j = 0; j < rowsize; j++) {
                tuple.add(inst.toString(insts.attribute(j)));
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
