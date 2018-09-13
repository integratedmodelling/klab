/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.Section;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.springframework.util.StringUtils;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

/**
 * A report is a graph of sections generated from templates. Each section has a
 * role and may represent a reference to another.
 * 
 * @author Ferd
 */
public class Report implements IReport {

	private Map<SectionRole, ReportSection> mainSections = new HashMap<>();

	public void addSection(Section section) {
		ReportSection main = getMainSection(section.getRole());
		main.children.add((ReportSection) section);
	}

	/*
	 * get or create the main section for a section.
	 */
	private ReportSection getMainSection(SectionRole role) {
		ReportSection ret = mainSections.get(role);
		if (ret == null) {
			ret = new ReportSection(role);
			mainSections.put(role, ret);
			ret.name = StringUtils.capitalize(role.name().toLowerCase());
		}
		return ret;
	}

	@Override
	public List<Section> getSections() {
		List<Section> ret = new ArrayList<>();
		for (SectionRole role : SectionRole.values()) {
			if (mainSections.containsKey(role)) {
				ret.add(mainSections.get(role));
			}
		}
		return ret;
	}

	public static final String SEPARATOR = "\n\n----\n\n";

	IRuntimeContext context = null;

	public Report() {
	}

	public Report(IRuntimeContext context) {
		this.context = context;
	}

	public String asHTML(String markdown) {

		MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS,
				Arrays.asList(FootnoteExtension.create(), AttributesExtension.create(),
						EnumeratedReferenceExtension.create(), MediaTagsExtension.create(),
						DefinitionExtension.create()));

		// uncomment to convert soft-breaks to hard breaks
		// options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

		Parser parser = Parser.builder(options).build();
		HtmlRenderer renderer = HtmlRenderer.builder(options).build();
		Node document = parser.parse(markdown);
		return getHeader() + "<body>\n" + renderer.render(document) + "\n</body>\n";
	}

	/**
	 * Generate HTML header.
	 * 
	 * @return
	 */
	private String getHeader() {
		String ret = "<head>\n";
		if (getTitle() != null) {
			ret += "   <title>" + getTitle() + "</title>\n";
		}
		ret += "<style>\n" + css + "\n</style>";
		ret += "\n</head>\n";
		return ret;
	}

	// @Override
	// public String asText() {
	// return flatten(text).toString();
	// }

	// private StringBuffer flatten(StringBuffer text) {
	//
	// if (context != null) {
	// describeData();
	// }
	//
	// StringBuffer ret = new StringBuffer(title);
	// ret.append(text);
	// for (Section section : sections) {
	// appendSection(section, 1, ret);
	// }
	// appendReferences(ret);
	//
	// return ret;
	// }

	// private void describeData() {
	//
	// Section save = currentSection;
	// setSection("Data");
	//
	// for (IArtifact artifact : context.getProvenance().getArtifacts()) {
	// // if (artifact.getObservation() instanceof IState
	// // && !described.contains(artifact.getObservation())) {
	// // if
	// (artifact.getObservation().getContextObservation().equals(context.getSubject()))
	// {
	// // // TODO reintegrate - only 'true' data should be shown
	// // if (artifact.getModel() != null && artifact.getModel().isResolved()) {
	// // describe(artifact.getObservation());
	// // }
	// // }
	// // }
	// }
	//
	// currentSection = save;
	// }

	// private void appendReferences(StringBuffer ret) {
	// ret.append("\n# References\n\n");
	// for (int i = 0; i < citations.size(); i++) {
	// ret.append((i + 1) + ". " + citations.get(i).linkIfAny() + "\n");
	// }
	// }
	//
	// private void appendSection(Section section, int level, StringBuffer ret) {
	//
	// if (!(section.text.toString().isEmpty() && section.children.isEmpty())) {
	//
	// ret.append("\n" + StringUtils.repeat('#', level) + " " + section.title +
	// "\n\n");
	//
	// ret.append(section.text + "\n");
	//
	// for (Section child : section.children) {
	// appendSection(child, level + 1, ret);
	// }
	//
	// }
	// }

	// @Override
	// public void setSection(String section) {
	// /*
	// * create sections and any in between; record declaration order
	// */
	// String[] path = section.split("/");
	// Section sec = null;
	// for (int i = 0; i < path.length; i++) {
	// if (i == 0) {
	// sec = findSection(path[i], true);
	// } else {
	// sec = sec.getChild(path[i], true);
	// }
	// }
	//
	// /*
	// * set it to the current section
	// */
	// currentSection = sec;
	// }

	// private Section findSection(String string, boolean create) {
	// for (Section s : sections) {
	// if (s.title.equals(string)) {
	// return s;
	// }
	// }
	// Section ret = null;
	// if (create) {
	// ret = new Section(string);
	// sections.add(ret);
	// }
	// return ret;
	// }

	// @Override
	// public String toString() {
	// return asText();
	// }

	// @Override
	// public void setTitle(String title) {
	// // TODO Auto-generated method stub
	//
	// }

	// @Override
	// public String getTitle() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public String addAttachment(Object o) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	// @Override
	// public void writeLink(String markdown, String anchorOrUrl) {
	// // TODO Auto-generated method stub
	//
	// }

	// @Override
	// public void loadDocumentation(IDocumentation documentation) {
	// for (String tag : documentation.getTags()) {
	// if (tag.startsWith("ref:")) {
	// Reference ref = new Reference();
	// ref.id = tag.substring(4);
	// ref.body = documentation.get(tag).getActionCode();
	// references.put(ref.id, ref);
	// }
	// }
	// for (String tag : documentation.getTags()) {
	// if (tag.startsWith("link:")) {
	// String id = tag.substring(5);
	// Reference ref = references.get(id);
	// if (ref != null) {
	// ref.link = documentation.get(tag).getActionCode();
	// }
	// }
	// }
	// }

	// @Override
	// public void reference(String... refs) {
	//
	// String ret = "";
	// for (String ref : refs) {
	// Reference reference = references.get(ref);
	// if (reference != null) {
	// int n = citations.indexOf(reference);
	// if (n < 0) {
	// citations.add(reference);
	// n = citations.size() - 1;
	// }
	// if (ret.length() > 0) {
	// ret += ",";
	// }
	// ret += "[" + (n + 1) + "](#" + reference.id + ")";
	// }
	// }
	// write("[" + ret + "]");
	//
	// }
	//
	//// @Override
	// public String getReference() {
	// String ret = "A" + NameGenerator.shortUUID();
	// write("<a name=\"" + ret + "\"></a>");
	// return ret;
	// }

	private String getTitle() {
		// TODO flesh out
		return "Contextualization report";
	}

	// @Override
	public void describe(Object o) {

		if (o instanceof IConcept) {

			/*
			 * TODO hyperlink the concepts to the future ontology page
			 */
			String tdesc = "";

			// IConcept base = Observables.getCoreObservable((IConcept) o);
			// for (IConcept trait : Traits.getTraits((IConcept) o)) {
			// if (tdesc.isEmpty()) {
			// tdesc += "<small><span style=\"color:blue;background-color:#bbbbbb\">";
			// } else {
			// tdesc += " ";
			// }
			// tdesc += NS.getDisplayName(trait);
			// }
			// String rdesc = "";
			// for (IConcept role : Roles.getRoles((IConcept) o)) {
			// if (tdesc.isEmpty()) {
			// rdesc += "<small><span style=\"color:darkred;background-color:#bbbbbb\">";
			// } else {
			// tdesc += " ";
			// }
			// rdesc += NS.getDisplayName(role);
			// }
			//
			// String cdesc = ("**" + base + "**") +
			// (tdesc.isEmpty() ? "" : (" " + tdesc + "</span></small> ")) +
			// (rdesc.isEmpty() ? "" : (" " + rdesc + "</span></small>"));
			//
			// writeln("\n" + cdesc + "\n");
			// String description = ((IConcept)
			// o).getMetadata().getString(IMetadata.DC_COMMENT);
			// if (description == null || description.trim().isEmpty()) {
			// description = "No description provided.";
			// }
			// writeln("\n<small>" + description + "</small>\n");

		} else if (o instanceof IObservation) {
			describe(((IObservation) o).getObservable().getType());
			// write(SEPARATOR);
			linkImage((IObservation) o);
			// write(SEPARATOR);
			IMetadata metadata = ((IObservation) o).getMetadata();
			// if (((Observation)o).getActuator() != null) {
			// if (((Observation)o).getActuator().getModel() != null) {
			// metadata = ((Observation)o).getActuator().getModel().getMetadata();
			// }
			// }
			writeMetadata(metadata);
			// AARGH
			// writeMetadata(((Observation) o).getActuator() == null ? ((IObservation)
			// o).getMetadata()
			// : (((Observation) o).getActuator().getModel() == null
			// ? ((IObservation) o).getMetadata()
			// : ((Observation) o).getActuator().getModel().getMetadata()));
			// described.add((IObservation) o);
		}
	}

	private void writeMetadata(IMetadata metadata) {

		String ret = "";
		if (metadata.contains(IMetadata.DC_ORIGINATOR)) {
			ret += "\n**Originator:** " + metadata.get(IMetadata.DC_ORIGINATOR, String.class) + " <br/> ";
		}
		if (metadata.contains(IMetadata.DC_URL)) {
			ret += "\n**URL:** [" + metadata.get(IMetadata.DC_URL, String.class) + "]("
					+ metadata.get(IMetadata.DC_URL, String.class) + ") <br/> ";
		}
		if (metadata.contains("im:distribution")) {
			ret += "\n**Distribution:** " + metadata.get("im:distribution", String.class) + " <br/> ";
		}
		if (!ret.isEmpty()) {
			// writeln("<small> " + ret + " </small>\n");
		}
	}

	private void linkImage(IObservation o) {
		// write("<img src=\"http://127.0.0.1:8183/kmodeler/engine/context/get/media/" +
		// this.context.getId()
		// + "/"
		// + ((Observation) o).getId() +
		// ".png?index=T0&viewport=VIEWPORT%23600%2C600\"></img>");
	}

	// bit nicer. Obviously we want a configurable solution.
	private static final String css = "/* body */\n" + "body {\n" + "    margin: 20px auto;\n" + "    width: 800px;\n"
			+ "    background-color: #fff;\n" + "    color: #000;\n"
			+ "    font: 13px \"Myriad Pro\", \"Lucida Grande\", Lucida, Verdana, sans-serif;\n" + "}\n" + "\n"
			+ "/* links */\n" + "a:link {\n" + "    color: #00f;\n" + "    text-decoration: none;\n" + "}\n" + "\n"
			+ "a:visited {\n" + "    color: #00a;\n" + "    text-decoration: none;\n" + "}\n" + "\n" + "a:hover {\n"
			+ "    color: #f60;\n" + "    text-decoration: underline;\n" + "}\n" + "    \n" + "a:active {\n"
			+ "    color: #f60;\n" + "    text-decoration: underline;\n" + "}\n" + "\n" + "\n" + "/* html tags */\n"
			+ "\n" + "/*  Work around IE/Win code size bug - courtesy Jesper, waffle.wootest.net  */\n" + "\n"
			+ "* html code {\n" + "    font-size: 101%;\n" + "}\n" + "\n" + "* html pre {\n" + "    font-size: 101%;\n"
			+ "}\n" + "\n" + "/* code */\n" + "\n" + "pre, code {\n"
			+ "    font-size: 11px; font-family: monaco, courier, consolas, monospace;\n" + "}\n" + "\n" + "pre {\n"
			+ "    margin-top: 5px;\n" + "    margin-bottom: 10px;\n" + "    border: 1px solid #c7cfd5;\n"
			+ "    background: #f1f5f9;\n" + "    margin: 20px 0;\n" + "    padding: 8px;\n" + "    text-align: left;\n"
			+ "}\n" + "\n" + "hr {\n" + "    color: #919699;\n" + "    size: 1;\n" + "    width: 100%;\n"
			+ "    noshade: \"noshade\"\n" + "}\n" + "\n" + "/* headers */\n" + "\n" + "\n"
			+ "h1, h2, h3, h4, h5, h6 {\n"
			+ "    font-family: \"Myriad Pro\", \"Lucida Grande\", Lucida, Verdana, sans-serif;\n"
			+ "    font-weight: bold;\n" + "}\n" + "\n" + "h1  {\n" + "    margin-top: 1em;\n"
			+ "    margin-bottom: 25px;\n" + "    color: #000;\n" + "    font-weight: bold;\n"
			+ "    font-size: 24px;\n" + "}\n" + "h2  {\n" + "    margin-top: 2.5em;\n" + "    font-size: 18px;\n"
			+ "    color: #000;\n" + "    padding-bottom: 2px;\n" + "    border-bottom: 1px solid #919699;\n" + "}\n"
			+ "h3  {\n" + "    margin-top: 2em;\n" + "    margin-bottom: .5em;\n" + "    font-size: 16px;\n"
			+ "    color: #000;\n" + "}\n" + "h4  {\n" + "    margin-top: 2em;\n" + "    margin-bottom: .5em;\n"
			+ "    font-size: 14px;\n" + "    color: #000;\n" + "}\n" + "h5  {\n" + "    margin-top: 20px;\n"
			+ "    margin-bottom: .5em;\n" + "    padding: 0;\n" + "    font-size: 12px;\n" + "    color: #000;\n"
			+ "}\n" + "\n" + "h6  {\n" + "    margin-top: 20px;\n" + "    margin-bottom: .5em;\n" + "    padding: 0;\n"
			+ "    font-size: 11px;\n" + "    color: #000;\n" + "}\n" + "\n" + "p {\n" + "    margin-top: 0px;\n"
			+ "    margin-bottom: 10px;\n" + "}\n" + "\n" + "/* lists */\n" + "\n" + "ul  {\n"
			+ "    list-style: square outside;\n" + "    margin: 0 0 0 30px;\n" + "    padding: 0 0 12px 6px;\n" + "}\n"
			+ "\n" + "li  {\n" + "    margin-top: 7px;\n" + "}\n" + "            \n" + "ol {\n"
			+ "    list-style-type: decimal;\n" + "    list-style-position: outside;\n" + "    margin: 0 0 0 30px;\n"
			+ "    padding: 0 0 12px 6px;\n" + "}\n" + "    \n" + "ol ol {\n" + "    list-style-type: lower-alpha;\n"
			+ "    list-style-position: outside;\n" + "    margin: 7px 0 0 30px;\n" + "    padding: 0 0 0 10px;\n"
			+ "    }\n" + "\n" + "ul ul {\n" + "    margin-left: 40px;\n" + "    padding: 0 0 0 6px;\n" + "}\n" + "\n"
			+ "li>p { display: inline }\n" + "li>p+p { display: block }\n" + "li>a+p { display: block }\n" + "\n" + "\n"
			+ "/* table */\n" + "\n" + "table {\n" + "    width: 100%;\n" + "    border-top: 1px solid #919699;\n"
			+ "    border-left: 1px solid #919699;\n" + "    border-spacing: 0;\n" + "}\n" + "    \n" + "table th {\n"
			+ "    padding: 4px 8px 4px 8px;\n" + "    background: #E2E2E2;\n" + "    font-size: 12px;\n"
			+ "    border-bottom: 1px solid #919699;\n" + "    border-right: 1px solid #919699;\n" + "}\n"
			+ "table th p {\n" + "    font-weight: bold;\n" + "    margin-bottom: 0px; \n" + "}\n" + "    \n"
			+ "table td {\n" + "    padding: 8px;\n" + "    font-size: 12px;\n" + "    vertical-align: top;\n"
			+ "    border-bottom: 1px solid #919699;\n" + "    border-right: 1px solid #919699;\n" + "}\n"
			+ "table td p {\n" + "    margin-bottom: 0px; \n" + "}\n" + "table td p + p  {\n"
			+ "    margin-top: 5px; \n" + "}\n" + "table td p + p + p {\n" + "    margin-top: 5px; \n" + "}\n" + "\n"
			+ "/* forms */\n" + "\n" + "form {\n" + "    margin: 0;\n" + "}\n" + "\n" + "button {\n"
			+ "    margin: 3px 0 10px 0;\n" + "}\n" + "input {\n" + "    vertical-align: middle;\n"
			+ "    padding: 0;\n" + "    margin: 0 0 5px 0;\n" + "}\n" + "\n" + "select {\n"
			+ "    vertical-align: middle;\n" + "    padding: 0;\n" + "    margin: 0 0 3px 0;\n" + "}\n" + "\n"
			+ "textarea {\n" + "    margin: 0 0 10px 0;\n" + "    width: 100%;\n" + "}";

	@Override
	public String render(Encoding encoding) {

		StringBuffer ret = new StringBuffer(16 * 1024);
		for (Section s : getSections()) {
			ret.append(s.render());
		}

		switch (encoding) {
		case HTML:
			return asHTML(ret.toString());
		case MARKDOWN:
			return ret.toString();
		case LATEX:
		case PDF:
			break;
		}

		return "<html><body><p>Unsupported encoding " + encoding + " </p></body></html>";
	}

}
