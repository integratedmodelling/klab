/**
 * PrintValuePTString.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Mar 25, 2008
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
 * @date      Mar 25, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.pfunction.util;


import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.interpreter.RT;
import org.nfunk.jep.Node;


/**
 * @author Sergey Krivov
 *
 */
public class PrintValuePFString implements PrintValue {
	
    private String stream;
	
    public PrintValuePFString() {
        stream = "";
    }

    /* (non-Javadoc)
     * @see org.integratedmodelling.riskwiz.pt.util.PrintValue#post(java.util.Vector, int[], double)
     */
    @Override
	public void print(Vector<DiscreteDomain> domainProduct, int[] aref,
            Node value) {
        String printEntry = "\nP(";

        for (int i = 0; i < aref.length; i++) {
            DiscreteDomain dom = domainProduct.elementAt(i);
            String domName = dom.getName();

            printEntry += " " + dom.getName() + "=" + dom.getState(aref[i]);
            if (i < aref.length - 1) {
                printEntry += ",";
            }
        }
        printEntry += ")= " + RT.toString(value);
        stream += printEntry;

    }
	
    public String getOutput() {
        return stream;
    }

}
