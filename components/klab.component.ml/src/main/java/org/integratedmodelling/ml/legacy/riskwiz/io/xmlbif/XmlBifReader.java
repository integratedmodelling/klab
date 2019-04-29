/**
 * XmlBifReader.java
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.integratedmodelling.ml.legacy.riskwiz.Setting;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BNNode;
import org.integratedmodelling.ml.legacy.riskwiz.bn.BeliefNetwork;
import org.integratedmodelling.ml.legacy.riskwiz.domain.LabelDomain;
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
public class XmlBifReader {

	/**
	 * 
	 */
	public XmlBifReader() {// TODO Auto-generated constructor stub
	}

	protected BeliefNetwork graph = null;

	protected boolean rowFirst = true;

	public BeliefNetwork loadFromFile(String name) {

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(name);
		} catch (Exception e) {
			System.out.println("File " + name + " not found");
		}
		return load(fis);

	}

	public BeliefNetwork load(InputStream stream) {
		Document doc;
		DocumentBuilder parser;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		factory.setValidating(true);
		factory.setNamespaceAware(true);

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

	public void visitDocument(Node parent) {
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

				if (name.equals("BIF")) {
					NamedNodeMap attrs = node.getAttributes();

					if (attrs != null) {
						int amax = attrs.getLength();

						for (int j = 0; j < amax; j++) {
							Node attr = attrs.item(j);
							String aname = attr.getNodeName().toUpperCase();

							if (aname.equals("VERSION")) {
								rowFirst = true;
								try {
									double ver = Double.parseDouble(attr.getNodeValue());

									if (ver >= 0.3) {
										rowFirst = false;
									}
								} catch (Exception exx) {
								}
							}
						}
					}
					visitDocument(node);
				} else if (name.equals("NETWORK")) {
					visitModel(node);
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

	public void visitModel(Node parent) {
		graph = new BeliefNetwork();
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

				if (name.equals("NAME")) {
					graph.setName(getElementValue(node));
				} else if (name.equals("VARIABLE")) {
					BNNode bbnnode = visitVariable(node);

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

				if (name.equals("DEFINITION") || name.equals("PROBABILITY")) {
					visitDefinition(node);
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

	protected BNNode visitVariable(Node parent) {
		NodeList l = parent.getChildNodes();

		BNNode bbnnode = null; // = new BeliefNode("XXX");
		int max;
		String propType = "nature";
		NamedNodeMap attrs = parent.getAttributes();

		if (attrs != null) {
			max = attrs.getLength();
			for (int i = 0; i < max; i++) {
				Node attr = attrs.item(i);
				String name = attr.getNodeName();
				String value = attr.getNodeValue();

				if (name.equals("TYPE")) {
					propType = value;
					if (value.equals("decision")) {
						bbnnode = new BNNode("XXX", BNNode.NodeType.decision);
					} else if (value.equals("utility")) {
						bbnnode = new BNNode("XXX", BNNode.NodeType.utility);
					} else { // otherwise it's just "nature"
						bbnnode = new BNNode("XXX", BNNode.NodeType.probabilistic);
					}
				} else if (Setting.DEBUG) {
					System.out.println("Unhandled variable property attribute " + name);
				}
			}
		}

		if (!propType.equals("nature") && !propType.equals("decision") && !propType.equals("utility")) { // $NON-NLS-1$
			throw new RuntimeException("Unknown node type " + propType);
		}

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

				if (name.equals("NAME")) { // $NON-NLS-1$
					String desc = getElementValue(node);

					bbnnode.setName(desc);
				} else if (name.equals("OUTCOME") || name.equals("VALUE")) {
					String value = getElementValue(node);

					if (value != null) {
						values.add(value);
					}
				} else if (name.equals("PROPERTY")) {
					String value = getElementValue(node);

					parseProperty(value, bbnnode);
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

		if (propType.equals("nature") || propType.equals("utility")) {
			String[] valArr = new String[values.size()];

			values.toArray(valArr);
			LabelDomain dom = new LabelDomain(bbnnode.getName(), valArr);

			// (String[]) values.toArray());

			bbnnode.setDomain(dom);
		}
		return bbnnode;
	}

	/**
	 * Parse string property. The input string is expected to have at least one
	 * equal sign. If the right hand side is enclosed with parentheses, it will
	 * treat it as lists. If the value is numeric, it tries to convert that to a
	 * Double. Otherwise, it stores the value as a string.
	 * 
	 * @param s
	 *            The property string
	 * @param prop
	 *            The property table
	 */
	protected void parseProperty(String s, BNNode bbnode) {
		int idx = s.indexOf('=');

		if (idx == -1) {
			return;
		}
		String name = s.substring(0, idx).trim();
		String value = s.substring(idx + 1).trim();

		if (value.startsWith("(") && value.endsWith(")")) {
			value = value.substring(1, value.length() - 1).trim();
			StringTokenizer tokenizer = new StringTokenizer(value, ", ");
			LinkedList<Object> values = new LinkedList<Object>();

			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();

				try {
					Double dbl = new Double(token);

					values.add(dbl);
				} catch (Exception e) {
					values.add(token);
				}
			}
			bbnode.setProperty(name, values);
		} else {
			try {
				Double dbl = new Double(value);

				bbnode.setProperty(name, dbl);
			} catch (Exception e) {
				bbnode.setProperty(name, value);
			}
		}
	}

	protected void visitDefinition(Node parent) {
		NodeList l = parent.getChildNodes();

		if (l == null) {
			return;
		}
		LinkedList<String> parents = new LinkedList<String>();
		String curNodeName = null, CPTString = null;

		int max = l.getLength();

		for (int i = 0; i < max; i++) {
			Node node = l.item(i);

			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				String name = node.getNodeName();

				if (name.equals("FOR")) {
					curNodeName = getElementValue(node);
				} else if (name.equals("GIVEN")) {
					String parentName = getElementValue(node);
					BNNode parentNode = graph.getBeliefNode(parentName);

					if (parentNode == null) {
						throw new RuntimeException("Cannot resolve node " + parentName);
					}
					if (parentNode.isUtility()) {
						throw new RuntimeException("Utility nodes can never be parent nodes!");
					}
					if (parentNode != null) {
						parents.add(parentName);
					}
				} else if (name.equals("TABLE")) {
					CPTString = getElementValue(node);
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
			throw new RuntimeException("Ill-formed <DEFINITION> tag, no names specified!");
		}

		BNNode curNode = graph.getBeliefNode(curNodeName);

		if (curNode == null) {
			throw new RuntimeException("Ill-formed <DEFINITION> tag, non-existant names specified!");
		}
		if (curNode.isDecision()) {
			return;
		}
		if (CPTString == null) {
			throw new RuntimeException("Ill-formed <DEFINITION> tag, no tables specified!");
		}

		// Post processing
		for (Iterator i = parents.iterator(); i.hasNext();) {
			String parentNodeName = (String) i.next();

			graph.addEdge(parentNodeName, curNodeName);
		}

		// if (!curNode.isUtility()) {
		// if (rowFirst) {
		// parents.addFirst(curNodeName);
		// } else {
		// parents.add(curNodeName);
		// }
		// }
		// int domainOrder= curNode.getDomain().getOrder();

		IOUtil.parseTableString(CPTString, (TabularFunction) curNode.getFunction());

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
		        /*
		         * mysterious choice in WEKA-generated BIF. Unsure why but I don't think this can
		         * harm anything.
		         */
				String s = node.getNodeValue();
		        if (s.startsWith("'\\'") && s.endsWith("\\''")) {
		        	s = s.substring(3);
		        	s = s.substring(0, s.length()-3);
		        }
				buf.append(s);
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
