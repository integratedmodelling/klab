/**
 * BifToGenie.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2009 www.integratedmodelling.org
 * Created: Jan 14, 2009
 *
 * ----------------------------------------------------------------------------------
 * This file is part of riskwiz-cvars.
 * 
 * riskwiz-cvars is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * riskwiz-cvars is distributed in the hope that it will be useful,
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
 * @copyright 2009 www.integratedmodelling.org
 * @author    Sergey Krivov
 * @date      Jan 14, 2009
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.tests;


import java.io.FileInputStream;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.io.genie.GenieWriter;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifReader;


/**
 * @author Sergey Krivov
 *
 */
public class BifToGenie {

    /**
     * @param args
     */
    public static void main(String[] args) {
        XmlBifReader r = new XmlBifReader();
        GenieWriter w = new  GenieWriter();

        try {
            BeliefNetwork bn = r.load(
                    new FileInputStream("examples/sprinkler.xml"));
             
            System.out.println("----------------------------------");  
            w.save(System.out, bn);
            w.saveToFile("examples/sprinkler.xdsl", bn);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
