package org.integratedmodelling.ml.legacy.riskwiz.io.riskwiz;


import java.io.FileNotFoundException;
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
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode.DomainType;
import org.integratedmodelling.ml.legacy.riskwiz.domain.ContinuousDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.DiscreteDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.LabelDomain;
import org.integratedmodelling.ml.legacy.riskwiz.io.IOUtil;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;
import org.jgrapht.traverse.TopologicalOrderIterator;


public class RiskWizWriter {

    /**
     * 
     */
    public RiskWizWriter() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void saveToFile(String filename, BeliefNetwork graph)
        throws FileNotFoundException {

        FileOutputStream stream = new FileOutputStream(filename);

        save(stream, graph);

    }

    /**
     * Save routine for XML BIF. Graph properties won't get saved. Node
     * properties aren't either, except for the position.
     * 
     * @see edu.ksu.cis.bnj.bbn.converter.Converter#save(java.io.OutputStream,
     *      edu.ksu.cis.bnj.bbn.BBNGraph)
     */
    public void save(OutputStream stream, BeliefNetwork graph) {
        Writer w = new OutputStreamWriter(stream);
        String ln = System.getProperty("line.separator"); // $NON-NLS-1$

        // nodeCache = new Hashtable();
        // valueCache = new Hashtable();
        try {
            // Dump out the DTD
            w.write(
                    "<?xml version=\"1.0\"?>" + ln
                    + "<riskwiz  version=\"1.0\"  id=\"" + graph.getName()
                    + "\" numsamples=\"1000\"");
            w.write(">" + ln + "   <nodes>" + ln);

            // Dump out the variables
            for (TopologicalOrderIterator it = new TopologicalOrderIterator(
                    graph); it.hasNext();) {
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

            w.write("   </nodes>" + ln); // $NON-NLS-1$
            w.write("</riskwiz> " + ln); // $NON-NLS-1$
            w.flush();
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeDecision(BNNode node, Writer w, String ln)
        throws IOException {

        w.write("       ");
        w.write("<decision");
        w.write(
                "    id=\"" + IOUtil.mangleXMLString(node.getName()) + "\" >"
                + ln);

        writeDomain(node, w, ln);
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

        String utilitiesString = IOUtil.saveCPF(
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

        writeDomain(node, w, ln);
        writeParents(node, w, ln);
        String CPTString = IOUtil.saveCPF((TabularFunction) node.getFunction());

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

        writeDomain(node, w, ln);
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

        writeDomain(node, w, ln);
        writeParents(node, w, ln);
        String DetTableString = IOUtil.saveDeterministicFTable(node);

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

    private void writeDomain(BNNode node, Writer w, String ln)
        throws IOException {
        String tab = "       ";

        w.write(tab + "  ");
        w.write("<domain");
        if (node.getDomType() == DomainType.labels) {
            w.write("    type=\"labels\" >" + ln);
            Vector<String> values = ((LabelDomain) node.getDomain()).getStates();

            // valueCache.put(nodeName, values);
            for (Iterator j = values.iterator(); j.hasNext();) {
                w.write(tab + "    ");
                w.write(
                        "<state id=\""
                                + IOUtil.mangleXMLString(j.next().toString())
                                + "\" />" + ln);
            }
        } else if (node.getDomType() == DomainType.intervals) {
            w.write("    type=\"intervals\" >" + ln);
            IntervalDomain idom = (IntervalDomain) node.getDomain();

            // valueCache.put(nodeName, values);

            w.write(tab + "    ");
            if (idom.hasEverPartition()) {
                w.write(
                        "<states min=\"" + idom.getMin() + "\"" + "max=\""
                        + idom.getMax() + "\"" + "order=\"" + idom.getOrder()
                        + "\" />" + ln);
            } else {
                w.write(
                        "<states>" + IOUtil.statesString(idom.getStateBorders())
                        + "</states>" + ln);
            }

        } else if (node.getDomType() == DomainType.continuous) {
            w.write("    type=\"expressions\" >" + ln);
            ContinuousDomain cdom = (ContinuousDomain) node.getDomain();

            // valueCache.put(nodeName, values);

            w.write(tab + "    ");
            w.write(
                    "<states min=\"" + cdom.getMin() + "\"" + "max=\""
                    + cdom.getMax() + "\" +discretizationOrder=\""
                    + cdom.getDiscretizationOrder() + "\"/>" + ln);

        }

        w.write(tab + "  ");
        w.write("</domain>" + ln);

    }

}
