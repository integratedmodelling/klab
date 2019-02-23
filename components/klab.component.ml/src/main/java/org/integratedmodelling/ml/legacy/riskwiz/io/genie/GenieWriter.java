/**
 * GenieWriter.java
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

package org.integratedmodelling.ml.legacy.riskwiz.io.genie;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.io.IOUtil;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularDetF;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;
import org.jgrapht.traverse.TopologicalOrderIterator;


/**
 * @author Sergey Krivov
 * 
 */
public class GenieWriter {

    /**
     * 
     */
    public GenieWriter() {// TODO Auto-generated constructor stub
    }

    public void saveToFile(String filename, BeliefNetwork graph)
        throws IOException {

        FileOutputStream stream = new FileOutputStream(filename);

        save(stream, graph);

    }

    /**
     * Save routine Genie format
     * @throws IOException 
     */
    public void save(OutputStream stream, BeliefNetwork graph) throws IOException {
        Writer w = new OutputStreamWriter(stream);
        String ln = System.getProperty("line.separator"); // $NON-NLS-1$

        // nodeCache = new Hashtable();
        // valueCache = new Hashtable();
		 
        // Dump out the DTD
        w.write(
                "<?xml version=\"1.0\"?>" + ln
                + "<smile  version=\"1.0\"  id=\"" + graph.getName()
                + "\" numsamples=\"1000\"");
        w.write(">" + ln + "   <nodes>" + ln);

        // Dump out the variables
        for (TopologicalOrderIterator it = new TopologicalOrderIterator(graph); it.hasNext();) {
            BNNode node = (BNNode) it.next();

            if (node.isDecision()) {
                writeDecision(node, w, ln);
            } else if (node.isUtility()) {
                writeUtility(node, w, ln);
            } else if (node.isDeterministic()) {
                writeDeterministic(node, w, ln);
            } else if (node.isNoisyMax()) {
                writeNoisyMax(node, w, ln);
            } else {
                writeCPT(node, w, ln);
            }

        }
        // Dump out the variables

        w.write("   </nodes>" + ln);
        w.write("   <extensions>" + ln);
        w.write(
                "      <genie version=\"1.0\" app=\"GeNIe 2.0.2937.0\" name= \""
                        + graph.getName() + "\" faultnameformat=\"nodestate\">"
                        + ln);
        for (TopologicalOrderIterator it = new TopologicalOrderIterator(graph); it.hasNext();) {
            BNNode node = (BNNode) it.next();

            writeGenieNode(node, w, ln);

        }

        w.write("      </genie>" + ln);
        w.write("   </extensions>" + ln);
        w.write("</smile> " + ln);
        w.flush();
        w.close();
		 
    }

    private void writeDecision(BNNode node, Writer w, String ln)
        throws IOException {

        w.write("       ");
        w.write("<decision");
        w.write(
                "    id=\"" + IOUtil.mangleXMLString(node.getName()) + "\" >"
                + ln);
        DiscreteDomain domain = (DiscreteDomain) node.getDomain();
        Vector<String> values = domain.getStates();

        // valueCache.put(nodeName, values);
        for (Iterator j = values.iterator(); j.hasNext();) {
            w.write(
                    "         <state id=\""
                            + IOUtil.mangleXMLString(j.next().toString())
                            + "\" />" + ln);
        }
        writeProperties(node, w, ln);
        w.write("       ");
        w.write("</decision>" + ln);

    }

    private void writeUtility(BNNode node, Writer w, String ln)
        throws IOException {

        w.write("       ");
        w.write("<utility");
        w.write(
                "    id=\"" + IOUtil.mangleXMLString(node.getName()) + "\" >"
                + ln);

        writeParents(node, w, ln);

        String utilitiesString = IOUtil.saveTable(
                (TabularFunction) node.getFunction());

        w.write("         <utilities>" + utilitiesString + "</utilities>" + ln);
        writeProperties(node, w, ln);
        w.write("       ");
        w.write("</utility>" + ln);
    }

    private void writeCPT(BNNode node, Writer w, String ln)
        throws IOException {

        w.write("       ");
        w.write("<cpt");
        w.write(
                "    id=\"" + IOUtil.mangleXMLString(node.getName()) + "\" >"
                + ln);
        DiscreteDomain domain = (DiscreteDomain) node.getDomain();
        Vector<String> values = domain.getStates();

        // valueCache.put(nodeName, values);
        for (Iterator j = values.iterator(); j.hasNext();) {
            w.write(
                    "         <state id=\""
                            + IOUtil.mangleXMLString(j.next().toString())
                            + "\" />" + ln);
        }
        writeParents(node, w, ln);
        String CPTString = IOUtil.saveTable((TabularFunction) node.getFunction());

        w.write("         <probabilities>" + CPTString + "</probabilities>" + ln);
        writeProperties(node, w, ln);
        w.write("       ");
        w.write("</cpt>" + ln);
    }

    private void writeNoisyMax(BNNode node, Writer w, String ln)
        throws IOException {

        w.write("       ");
        w.write("<noisymax");
        w.write(
                "    id=\"" + IOUtil.mangleXMLString(node.getName()) + "\" >"
                + ln);
        DiscreteDomain domain = (DiscreteDomain) node.getDomain();
        Vector<String> values = domain.getStates();

        // valueCache.put(nodeName, values);
        for (Iterator j = values.iterator(); j.hasNext();) {
            w.write(
                    "         <state id=\""
                            + IOUtil.mangleXMLString(j.next().toString())
                            + "\" />" + ln);
        }
        writeParents(node, w, ln);
        String paramString = node.getNoisyT().getValuesString(" ");

        w.write("         <parameters>" + paramString + "</parameters>" + ln);
        writeProperties(node, w, ln);
        w.write("       ");
        w.write("</noisymax>" + ln);
    }

    private void writeDeterministic(BNNode node, Writer w, String ln)
        throws IOException {

        w.write("       ");
        w.write("<deterministic");
        w.write(
                "    id=\"" + IOUtil.mangleXMLString(node.getName()) + "\" >"
                + ln);
        DiscreteDomain domain = (DiscreteDomain) node.getDomain();
        Vector<String> values = domain.getStates();

        // valueCache.put(nodeName, values);
        for (Iterator j = values.iterator(); j.hasNext();) {
            w.write(
                    "         <state id=\""
                            + IOUtil.mangleXMLString(j.next().toString())
                            + "\" />" + ln);
        }
        writeParents(node, w, ln);
        String DetTableString = IOUtil.saveDeterministicTable(
                (TabularDetF) node.getFunction());

        w.write(
                "         <resultingstates>" + DetTableString
                + "</resultingstates>" + ln);
        writeProperties(node, w, ln);
        w.write("       ");
        w.write("</deterministic>" + ln);
    }

    private void writeParents(BNNode node, Writer w, String ln)
        throws IOException {
        w.write("         <parents>");
        Vector<DiscreteDomain> parentDomains = ((TabularFunction) node.getFunction()).getParentsDomains();

        // System.out.println(node.getName());
        for (Iterator j = parentDomains.iterator(); j.hasNext();) {
            DiscreteDomain parentDom = (DiscreteDomain) j.next();
            String parentName = parentDom.getName();

            w.write(IOUtil.mangleXMLString(parentName));
            if (j.hasNext()) {
                w.write(" ");
            }

        }
        w.write("</parents>" + ln);
    }

    private void writeProperties(BNNode node, Writer w, String ln)
        throws IOException {

        Set<String> props = node.getPropertyNames();

        for (String key : props) {

            w.write(
                    "         <property id=\"" + key + "\" >"
                    + node.getProperty(key));			 
            w.write("</property>" + ln);
        }

    }

    private void writeGenieNode(BNNode node, Writer w, String ln)
        throws IOException {

        Integer x = (int) (Math.random() * 300);
        Integer y = (int) (Math.random() * 300);

        w.write("       ");
        w.write("<node");
        w.write(
                "    id=\"" + IOUtil.mangleXMLString(node.getName()) + "\" >"
                + ln);
        w.write("         ");
        w.write(
                "<name>" + IOUtil.mangleXMLString(node.getName()) + "</name>"
                + ln);
        w.write("         ");
        w.write("<interior color=\"e5f6f7\" />" + ln);
        w.write("         ");
        w.write("<outline color=\"0000bb\" />" + ln);
        w.write("         ");
        w.write("<font color=\"000000\" name=\"Arial\" size=\"8\" />" + ln);
        w.write("         ");
        w.write(
                "<position>" + x + " " + y + " " + (x + 30) + " " + (y + 20)
                + "</position>" + ln);
        w.write("         ");
        w.write("<barchart active=\"true\" width=\"128\" height=\"64\" />" + ln);

        w.write("       ");
        w.write("</node>" + ln);
    }

}
