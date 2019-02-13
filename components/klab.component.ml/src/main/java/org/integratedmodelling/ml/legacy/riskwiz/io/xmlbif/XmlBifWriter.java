/**
 * XmlBifWriter.java
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

package org.integratedmodelling.ml.legacy.riskwiz.io.xmlbif;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.io.IOUtil;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;

 
/**
 * @author Sergey Krivov
 *
 */
public class XmlBifWriter {

    /**
     * 
     */
    public XmlBifWriter() {// TODO Auto-generated constructor stub
    }
	
    protected boolean DEBUG = false;

    /**
     * @see edu.ksu.cis.bnj.bbn.converter.Converter#initialize()
     */
	 
    public void saveToFile(String filename, BeliefNetwork graph)
        throws FileNotFoundException {
		 
        FileOutputStream stream = new FileOutputStream(filename);

        save(stream, graph);
		   	 
    }

    /**
     * Save routine for XML BIF. Graph properties won't get saved. Node
     * properties aren't either, except for the position.
     * @see edu.ksu.cis.bnj.bbn.converter.Converter#save(java.io.OutputStream, edu.ksu.cis.bnj.bbn.BBNGraph)
     */
    public void save(OutputStream stream, BeliefNetwork graph) {
        Writer w = new OutputStreamWriter(stream);
        String ln = System.getProperty("line.separator"); // $NON-NLS-1$

        // nodeCache = new Hashtable();
        // valueCache = new Hashtable();
        try {
            // Dump out the DTD
            w.write(
                    "<?xml version=\"1.0\"?>" + ln + ln
                    + "<!-- DTD for the XMLBIF 0.1 format -->" + ln
                    + "<!DOCTYPE BIF [" + ln + "    <!ELEMENT BIF ( NETWORK )*>"
                    + ln
                    + "        <!ATTLIST BIF VERSION CDATA #REQUIRED PRM CDATA #IMPLIED>"
                    + ln
                    + "    <!ELEMENT NETWORK ( NAME, ( PROPERTY | PRM_CLASS | VARIABLE | DEFINITION )* )>"
                    + ln
                    + "    <!ELEMENT PRM_CLASS (PRM_CLASSNAME, PRM_ATTRIBUTESET)>"
                    + ln + "    <!ELEMENT PRM_CLASSNAME (#PCDATA)>" + ln
                    + "    <!ELEMENT PRM_ATTRIBUTESET (PRM_PKEY+, PRM_RKEY*, PRM_ATTRIBUTE*)>"
                    + ln + "    <!ELEMENT PRM_PKEY (#PCDATA)>" + ln
                    + "    <!ELEMENT PRM_RKEY (#PCDATA)>" + ln
                    + "    <!ELEMENT PRM_ATTRIBUTE (#PCDATA)>" + ln
                    + "    <!ELEMENT NAME (#PCDATA)>" + ln
                    + "    <!ELEMENT VARIABLE ( NAME, ( OUTCOME | PROPERTY )* ) >"
                    + ln
                    + "        <!ATTLIST VARIABLE TYPE (nature|decision|utility) \"nature\">"
                    + ln + "    <!ELEMENT OUTCOME (#PCDATA)>" + ln
                    + "    <!ELEMENT DEFINITION ( FOR | GIVEN | TABLE | PROPERTY )* >"
                    + ln + "    <!ELEMENT FOR (#PCDATA)>" + ln
                    + "    <!ELEMENT GIVEN (#PCDATA)>" + ln
                    + "    <!ELEMENT TABLE (#PCDATA)>" + ln
                    + "    <!ELEMENT PROPERTY (#PCDATA)>" + ln + "]>" + ln + ln
                    + // $NON-NLS-1$
                    "<BIF VERSION=\"0.1\"");

            // if (beliefNetwork instanceof PRMGraph) {
            // w.write(" PRM=\"0.1\""); 
            // }

            w.write(
                    ">" + ln + // $NON-NLS-1$
                    "  <NETWORK>" + ln + // $NON-NLS-1$
                    "    <NAME>" + IOUtil.mangleXMLString(graph.getName())
                    + "</NAME>" + ln + "      <!-- Variables -->" + ln);  

            // Dump out the variables
            Set<BNNode> nodes = graph.vertexSet();

            for (BNNode node : nodes) {
				
                String nodeName = node.getName();

                // nodeCache.put(nodeName, node);
                w.write("      <VARIABLE TYPE=\"");  
                if (node.isDecision()) {
                    w.write("decision");
                } else if (node.isUtility()) {
                    w.write("utility");
                } else {
                    w.write("nature");
                }  
                w.write("\">" + ln);  
                w.write(
                        "         <NAME>" + IOUtil.mangleXMLString(nodeName)
                        + "</NAME>" + ln);  
                DiscreteDomain domain = (DiscreteDomain) node.getDomain();
                 
                if (!node.isUtility()) {              
                  
                    Vector<String> values = domain.getStates();

                    // valueCache.put(nodeName, values);
                    for (Iterator j = values.iterator(); j.hasNext();) {
                        w.write(
                                "        <OUTCOME>"
                                        + IOUtil.mangleXMLString(
                                                j.next().toString())
                                                + "</OUTCOME>"
                                                + ln);  
                    }
                }
                Object o = node.getProperty("position");  

                if (o instanceof List) { // implies o != null
                    String posString = null;

                    try {
                        Double xpos = (Double) ((List) o).get(0);
                        Double ypos = (Double) ((List) o).get(1);

                        posString = "(" + Math.round(xpos.doubleValue()) + ", "
                                + Math.round(ypos.doubleValue()) + ")";  

                    } catch (Exception e) {}
                    if (posString != null) {
                        w.write(
                                "        <PROPERTY> position = " + posString
                                + " </PROPERTY>" + ln);
                    }  
                }
                w.write("      </VARIABLE>" + ln);  
            }

            // Dump out the CPTs
            w.write("      <!-- Probability Distributions -->" + ln);  
            for (Iterator i = nodes.iterator(); i.hasNext();) {
                BNNode node = (BNNode) i.next();

                if (node.isDecision()) {
                    continue;
                } // Decision nodes doesn't have CPT, so skip.

                String nodeName = node.getName();

                w.write("      <DEFINITION>" + ln); // $NON-NLS-1$
                w.write(
                        "        <FOR>" + IOUtil.mangleXMLString(nodeName)
                        + "</FOR>" + ln);  
                Vector<DiscreteDomain> parentDomains = ((TabularFunction) node.getFunction()).getParentsDomains();             
                
                for (Iterator j = parentDomains.iterator(); j.hasNext();) {
                    DiscreteDomain parentDom = (DiscreteDomain) j.next();
                    String parentName = parentDom.getName();

                    w.write(
                            "        <GIVEN>"
                                    + IOUtil.mangleXMLString(parentName)
                                    + "</GIVEN>" + ln);  
                    
                }

                // Dump out the CPT string
                 
                String CPTString = IOUtil.saveTable(
                        (TabularFunction) node.getFunction());

                w.write("        <TABLE>" + CPTString + "</TABLE>" + ln);  

                w.write("      </DEFINITION>" + ln);  
            }

            w.write("  </NETWORK>" + ln);  
            w.write("</BIF>" + ln);  
            w.flush();
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
