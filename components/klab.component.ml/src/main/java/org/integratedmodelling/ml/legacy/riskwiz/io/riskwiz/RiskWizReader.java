/**
 * RiskWizReader.java
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

package org.integratedmodelling.ml.legacy.riskwiz.io.riskwiz;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.integratedmodelling.ml.legacy.riskwiz.Setting;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.ContinuousDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.IntervalDomain;
import org.integratedmodelling.ml.legacy.riskwiz.domain.LabelDomain;
import org.integratedmodelling.ml.legacy.riskwiz.interpreter.RT;
import org.integratedmodelling.ml.legacy.riskwiz.io.IOUtil;
import org.integratedmodelling.ml.legacy.riskwiz.pfunction.TabularFunction;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Sergey Krivov
 * 
 */
public class RiskWizReader {

    /**
     * 
     */
    public RiskWizReader() {// TODO Auto-generated constructor stub
    }

    protected BeliefNetwork graph = null;

    protected boolean rowFirst = true;

    public BeliefNetwork loadFromFile(String name)  {

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(name);
        } catch (Exception e) {
            System.out.println("File " + name + " not found");
        }
        return load(fis);

    }

    public BeliefNetwork load(InputStream stream)  {
        Document doc;
        DocumentBuilder parser;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // factory.setValidating(true);
        // factory.setNamespaceAware(true);

        // Parse the document
        try {
            parser = factory.newDocumentBuilder();
            doc = parser.parse(stream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        visitDocument(doc);
        System.gc();
        return graph;
    }

    public void visitDocument(Node parent)  {
        NodeList l = parent.getChildNodes();

        if (l == null) {
            throw new RuntimeException("Unexpected end of document!");
        }
        int max = l.getLength();

        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("riskwiz")) {
                    graph = new BeliefNetwork();
                    NamedNodeMap attrs = node.getAttributes();

                    if (attrs != null) {
                        int amax = attrs.getLength();

                        for (int j = 0; j < amax; j++) {
                            Node attr = attrs.item(j);
                            String aname = attr.getNodeName();

                            if (aname.equals("id")) {
                                graph.setName(attr.getNodeValue());
                            }
                        }
                    }
                    visitDocument(node);
                } else if (name.equals("nodes")) {
                    visitNodes(node);
                    // if multi-model, add beliefNetwork to a list
                } else if (name.equals("extensions")) {
                    // TODO
                    ;
                    // if multi-model, add beliefNetwork to a list
                } else {
                    throw new RuntimeException("Unhandled element " + name);
                }
                break;

            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }
    }

    public void visitNodes(Node parent)  {

        NodeList l = parent.getChildNodes();

        if (l == null) {
            throw new RuntimeException("Unexpected end of document!");
        }
        int max = l.getLength();

        // Split into two loops so that it can handle forward reference
        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("cpt")) {
                    BNNode bbnnode = visitStateNode(node,
                            BNNode.NodeType.probabilistic);

                    graph.addVertex(bbnnode);
                } else if (name.equals("noisymax")) {
                    BNNode bbnnode = visitStateNode(node,
                            BNNode.NodeType.noisymax);

                    graph.addVertex(bbnnode);
                } else if (name.equals("deterministic")) {
                    BNNode bbnnode = visitStateNode(node,
                            BNNode.NodeType.deterministic);

                    graph.addVertex(bbnnode);
                } else if (name.equals("utility")) {
                    BNNode bbnnode = visitStateNode(node,
                            BNNode.NodeType.utility);

                    graph.addVertex(bbnnode);
                } else if (name.equals("decision")) {
                    BNNode bbnnode = visitStateNode(node,
                            BNNode.NodeType.decision);

                    graph.addVertex(bbnnode);
                }

                break;

            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }

        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("cpt")) { 
                    visitCPTDefinition(node);
                } else if (name.equals("noisymax")) { // $NON-NLS-1$
                    visitNoisyMaxDefinition(node);
                } else if (name.equals("deterministic")) {  
                    visitDeterministicDefinition(node);
                } else if (name.equals("utility")) {  
                    visitUtilityDefinition(node);
                }
                break;

            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }
    }

    protected String getElementID(Node parent) {
        NamedNodeMap attrs = parent.getAttributes();

        if (attrs != null) {
            int max = attrs.getLength();

            for (int i = 0; i < max; i++) {
                Node attr = attrs.item(i);
                String name = attr.getNodeName();
                String value = attr.getNodeValue();

                if (name.equals("id")) {
                    return value;
                }
            }
        }
        return null;
    }

    protected BNNode visitStateNode(Node parent,
            BNNode.NodeType nodeType) {
        BNNode bbnnode = new BNNode("XXX", nodeType);

        bbnnode.setName(getElementID(parent));
        int max;
        NodeList l = parent.getChildNodes();

        if (l == null) {
            return null;
        }
        LinkedList<String> values = new LinkedList<String>();

        max = l.getLength();
        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("domain")) {
                    visitDomain(node, bbnnode);

                } else if (name.equals("property")) {  
                    String elid = getElementID(node);
                    String elval = getElementValue(node);

                    bbnnode.setProperty(elid, elval);
					
                }
                break;

            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }

        return bbnnode;
    }

    protected void visitDomain(Node parent, BNNode bbnnode) {
        String domtype = "";
        NamedNodeMap attrs = parent.getAttributes();

        if (attrs != null) {
            int amax = attrs.getLength();

            for (int j = 0; j < amax; j++) {
                Node attr = attrs.item(j);
                String aname = attr.getNodeName();

                if (aname.equals("type")) {
                    domtype = attr.getNodeValue();
                }
            }
        }

        LinkedList<String> values = new LinkedList<String>();
        double min = 0;
        double max = 0;
        int order = 0;
        boolean hasEvenParticion = true;
        Vector<Double> states = null;
		
        NodeList l = parent.getChildNodes();

        if (l == null) {
            throw new RuntimeException("Domain has no state description!");
        }
        int llength = l.getLength();

        // Split into two loops so that it can handle forward reference
        for (int i = 0; i < llength; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:

                String name = node.getNodeName();

                if (domtype.equals("labels")) {
                    if (name.equals("state")) {  
                        String value = getElementID(node);

                        if (value != null) {
                            values.add(value);
                        }
                    }

                } else if (domtype.equals("intervals")) {
                    if (name.equals("states")) { 
                        NamedNodeMap nattrs = node.getAttributes();

                        if (nattrs != null) {
                            int amax = nattrs.getLength();

                            for (int j = 0; j < amax; j++) {
                                Node nattr = nattrs.item(j);
                                String aname = nattr.getNodeName();

                                if (aname.equals("min")) {
                                    min = Double.parseDouble(
                                            nattr.getNodeValue());
                                } else if (aname.equals("max")) {
                                    max = Double.parseDouble(
                                            nattr.getNodeValue());
                                } else if (aname.equals("order")) {
                                    order = Integer.parseInt(
                                            nattr.getNodeValue());
                                }
                            }
                        } else {
							
                            String statesStr = getElementValue(node);

                            if (statesStr != null) {
                                hasEvenParticion = false;
                                states = IOUtil.parseStatesString(statesStr);
                            }
                        }
                    }

                } else if (domtype.equals("expressions")) {
                    if (name.equals("states")) { 
                        NamedNodeMap nattrs = node.getAttributes();

                        if (nattrs != null) {
                            int amax = nattrs.getLength();

                            for (int j = 0; j < amax; j++) {
                                Node nattr = nattrs.item(j);
                                String aname = nattr.getNodeName();

                                if (aname.equals("min")) {
                                    min = Double.parseDouble(
                                            nattr.getNodeValue());
                                } else if (aname.equals("max")) {
                                    max = Double.parseDouble(
                                            nattr.getNodeValue());
                                } else if (aname.equals("order")) {
                                    order = Integer.parseInt(
                                            nattr.getNodeValue());
                                }   
                            }
                        } 
                    }
                }

                break;

            case Node.DOCUMENT_TYPE_NODE:
            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }

        if (!(bbnnode.getNodeType() == BNNode.NodeType.utility)) {
            try {
                if (domtype.equals("labels")) {

                    String[] valArr = new String[values.size()];

                    values.toArray(valArr);
                    LabelDomain dom = new LabelDomain(bbnnode.getName(), valArr);

                    bbnnode.setDomain(dom);

                } else if (domtype.equals("intervals")) {
                    IntervalDomain dom;

                    if (hasEvenParticion) {
                        dom = new IntervalDomain(bbnnode.getName(), min, max,
                                order);
                    } else {
                        dom = new IntervalDomain(bbnnode.getName(), states);
                    }
                    bbnnode.setDomain(dom);
                    RT.addVariable(bbnnode.getName(), min);

                } else if (domtype.equals("expressions")) {
					 
                    ContinuousDomain dom = new ContinuousDomain(
                            bbnnode.getName(), min, max, order);

                    bbnnode.setDomain(dom);
                    RT.addVariable(bbnnode.getName(), min);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    protected void visitCPTDefinition(Node parent)  {
        NodeList l = parent.getChildNodes();

        if (l == null) {
            return;
        }
        LinkedList<String> parents = null;
        String curNodeName = getElementID(parent);
        String CPTString = null;

        int max = l.getLength();

        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("parents")) { // $NON-NLS-1$
                    String parentsNames = getElementValue(node);

                    parents = processParents(parentsNames);
                } else if (name.equals("probabilities")) { // $NON-NLS-1$
                    CPTString = getElementValue(node);
                } else if ((!name.equals("domain")) && Setting.DEBUG) {
                    System.out.println("Unhandled variable element  " + name);
                }
				 
                break;

            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }

        // Sanity check
        if (curNodeName == null) {
            throw new RuntimeException(
                    "Ill-formed <cpt> tag, no names specified!");
        }

        BNNode curNode = graph.getBeliefNode(curNodeName);

        if (curNode == null) {
            throw new RuntimeException(
                    "Ill-formed <cpt> tag, non-existant names specified!");
        }

        if (CPTString == null) {
            throw new RuntimeException(
                    "Ill-formed <cpt> tag, no probabilities specified!");
        }

        // Post processing
        if (parents != null) {
            for (Iterator i = parents.iterator(); i.hasNext();) {
                String parentNodeName = (String) i.next();

                graph.addEdge(parentNodeName, curNodeName);
            }
        }

        // int domainOrder= curNode.getDomain().getOrder();
        IOUtil.parseCPFString(CPTString, (TabularFunction) curNode.getFunction());
    }
	
    protected void visitNoisyMaxDefinition(Node parent) {
        NodeList l = parent.getChildNodes();

        if (l == null) {
            return;
        }
        LinkedList<String> parents = null;
        String curNodeName = getElementID(parent);
        String ParamString = null;

        int max = l.getLength();

        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("parents")) { // $NON-NLS-1$
                    String parentsNames = getElementValue(node);

                    parents = processParents(parentsNames);
                } else if (name.equals("parameters")) { // $NON-NLS-1$
                    ParamString = getElementValue(node);
                } else if ((!name.equals("state")) && !name.equals("strengths")
                        && Setting.DEBUG) {  
                    System.out.println("Unhandled variable element " + name);
                }
                break;

            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }

        // Sanity check
        if (curNodeName == null) {
            throw new RuntimeException(
                    "Ill-formed <cpt> tag, no names specified!");
        }

        BNNode curNode = graph.getBeliefNode(curNodeName);

        if (curNode == null) {
            throw new RuntimeException(
                    "Ill-formed <cpt> tag, non-existant names specified!");
        }

        if (ParamString == null) {
            throw new RuntimeException(
                    "Ill-formed <cpt> tag, no probabilities specified!");
        }

        // Post processing
        if (parents != null) {
            for (Iterator i = parents.iterator(); i.hasNext();) {
                String parentNodeName = (String) i.next();

                graph.addEdge(parentNodeName, curNodeName);
            }
        }

        // int domainOrder= curNode.getDomain().getOrder();
        IOUtil.parseNoisyParams(ParamString, curNode.getNoisyT());
		
        curNode.setFunction(curNode.getNoisyT().toCPF());
    }

    protected void visitDeterministicDefinition(Node parent) {
        NodeList l = parent.getChildNodes();

        if (l == null) {
            return;
        }
        LinkedList<String> parents = null;
        String curNodeName = getElementID(parent);
        String DetTableString = null;

        int max = l.getLength();

        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("parents")) {
                    String parentsNames = getElementValue(node);

                    parents = processParents(parentsNames);
                } else if (name.equals("resultingstates")) {
                    DetTableString = getElementValue(node);
                } else if ((!name.equals("domain")) && Setting.DEBUG) {
                    System.out.println("Unhandled variable element " + name);
                }
                break;

            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }

        // Sanity check
        if (curNodeName == null) {
            throw new RuntimeException(
                    "Ill-formed <deterministic> tag, no names specified!");
        }

        BNNode curNode = graph.getBeliefNode(curNodeName);

        if (curNode == null) {
            throw new RuntimeException(
                    "Ill-formed <deterministic> tag, non-existant names specified!");
        }

        if (DetTableString == null) {
            throw new RuntimeException(
                    "Ill-formed <deterministic> tag, no values specified!");
        }

        // Post processing
        for (Iterator i = parents.iterator(); i.hasNext();) {
            String parentNodeName = (String) i.next();

            graph.addEdge(parentNodeName, curNodeName);
        }

        // int domainOrder= curNode.getDomain().getOrder();
        IOUtil.parseDetFTableString(DetTableString, curNode);
		 
    }

    protected void visitUtilityDefinition(Node parent)  {
        NodeList l = parent.getChildNodes();

        if (l == null) {
            return;
        }
        LinkedList<String> parents = null;
        String curNodeName = getElementID(parent);
        String UtilitiesString = null;

        int max = l.getLength();

        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();

                if (name.equals("parents")) {
                    String parentsNames = getElementValue(node);

                    parents = processParents(parentsNames);

                } else if (name.equals("utilities")) {
                    UtilitiesString = getElementValue(node);
                } else if (Setting.DEBUG) {
                    System.out.println("Unhandled variable element " + name);
                }
                break;

            case Node.DOCUMENT_NODE:
            case Node.COMMENT_NODE:
            case Node.TEXT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }

        // Sanity check
        if (curNodeName == null) {
            throw new RuntimeException(
                    "Ill-formed <utility> tag, no names specified!");
        }

        BNNode curNode = graph.getBeliefNode(curNodeName);

        if (curNode == null) {
            throw new RuntimeException(
                    "Ill-formed <utility> tag, non-existant names specified!");
        }

        // Post processing
        for (Iterator i = parents.iterator(); i.hasNext();) {
            String parentNodeName = (String) i.next();

            graph.addEdge(parentNodeName, curNodeName);
        }

        IOUtil.parseCPFString(UtilitiesString,
                (TabularFunction) curNode.getFunction());

    }

    protected LinkedList<String> processParents(String parentsNames) {
        LinkedList<String> parents = new LinkedList<String>();
        Vector<String> parantsNamesVector = parseList(parentsNames);

        for (String parentName : parantsNamesVector) {
            BNNode parentNode = graph.getBeliefNode(parentName);

            if (parentNode == null) {
                throw new RuntimeException("Cannot resolve node " + parentName);
            }
            if (parentNode.isUtility()) {
                throw new RuntimeException(
                        "Utility nodes can never be parent nodes!");
            }
            if (parentNode != null) {
                parents.add(parentName);
            }
        }

        return parents;
    }

    // may need this as an alternative to parentsNames.split(" ")
    public Vector<String> parseList(String list) {
        Vector<String> values = new Vector<String>();
        StringTokenizer tokenizer = new StringTokenizer(list, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (!token.equalsIgnoreCase("")) {
                values.add(token);
            }

        }
        return values;
    }

    protected String getElementValue(Node parent) {
        NodeList l = parent.getChildNodes();

        if (l == null) {
            return null;
        }

        StringBuffer buf = new StringBuffer();
        int max = l.getLength();

        for (int i = 0; i < max; i++) {
            Node node = l.item(i);

            switch (node.getNodeType()) {
            case Node.TEXT_NODE:
                buf.append(node.getNodeValue());
                break;

            case Node.ELEMENT_NODE:
            case Node.COMMENT_NODE:
                // Ignore this
                break;

            default:
                if (Setting.DEBUG) {
                    System.out.println("Unhandled node " + node.getNodeName());
                }
            }
        }
        return buf.toString().trim();
    }

}
