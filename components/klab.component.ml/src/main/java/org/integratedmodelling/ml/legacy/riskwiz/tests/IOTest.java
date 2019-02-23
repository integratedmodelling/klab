/**
 * IOTest.java
 * ----------------------------------------------------------------------------------
 * 
 * Copyright (C) 2008 www.integratedmodelling.org
 * Created: Mar 6, 2008
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
 * @date      Mar 6, 2008
 * @license   http://www.gnu.org/licenses/gpl.txt GNU General Public License v3
 * @link      http://www.integratedmodelling.org
 **/

package org.integratedmodelling.ml.legacy.riskwiz.tests;


import java.io.FileInputStream;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifReader;
import org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif.XmlBifWriter;


/**
 * @author Sergey Krivov
 *
 */
public class IOTest {

    /**
     * Driver for testing this parser
     * @param args
     */
    public static void main(String[] args) {
        XmlBifReader r = new XmlBifReader();
        XmlBifWriter w = new  XmlBifWriter();

        try {
            BeliefNetwork bn = r.load(
                    new FileInputStream("examples/sprinkler.xml"));
             
            System.out.println("----------------------------------");  
            w.save(System.out, bn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
