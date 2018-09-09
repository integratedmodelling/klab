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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtils;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

/**
 * Simple in-memory report. One of these is available in each context and is written to by
 * the documentation and templating engine.
 * 
 * For now uses internal, hard-coded CSS file (also included in resources, unused). TODO
 * customize when we have to.
 * 
 * @author Ferd
 */
public class Report implements IReport {

    public static final String SEPARATOR = "\n\n----\n\n";

    IRuntimeContext            context   = null;
    Set<IObservation>          described = new HashSet<>();
    List<Reference>            citations = new ArrayList<>();

    class Section {
        StringBuffer  text     = new StringBuffer();
        String        title;
        // String parent;
        List<Section> children = new ArrayList<>();
        String        anchor   = NameGenerator.shortUUID();

        public Section(String id) {
            this.title = id;
        }

        Section getChild(String id, boolean create) {
            for (Section s : children) {
                if (s.title.equals(id)) {
                    return s;
                }
            }
            Section ret = null;
            if (create) {
                ret = new Section(id);
                // ret.parent = this;
                children.add(ret);
            }
            return ret;
        }
    }

    static class Reference {
        String id;
        String body; // ref with no body is an anchor
        String link;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Reference other = (Reference) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }

        public String linkIfAny() {
            if (link != null) {
                return "<a name=\"" + id + "\"></a>[" + body + "](" + link + ")";
            }
            return "<a name=\"" + id + "\"></a>" + body;
        }

    }

    Map<String, Reference> references     = new HashMap<>();

    StringBuffer           text           = new StringBuffer();
    List<IReport>          pages          = new ArrayList<>();
    List<Section>          sections       = new ArrayList<>();
    Section                currentSection = null;

    String                 title          = "";
    String                 subtitle       = "";
    String                 name           = "";
    String                 id             = "R" + NameGenerator.shortUUID();

    public Report() {
        // no context
    }

    public Report(IRuntimeContext context) {
        this.context = context;
        // if we start with a context, add a Data section so it's at the
        // beginning. It will only be shown when written to.
        sections.add(new Section("Data"));
    }

    @Override
    public void write(String markdown) {
        if (currentSection != null) {
            currentSection.text.append(markdown);
        } else {
            text.append(markdown);
        }
    }

    @Override
    public void writeln(String markdown) {
        if (currentSection != null) {
            currentSection.text.append(markdown + "\n");
        } else {
            text.append(markdown + "\n");
        }
    }

    @Override
    public String asHTML() {

        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        // options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(),
        // StrikethroughExtension.create()));
        // uncomment to convert soft-breaks to hard breaks
        // options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(flatten(text).toString());
        return getHeader() + "<body>\n" + renderer.render(document) + "\n</body>\n";
    }

    /**
     * Generate HTML header.
     * 
     * @return
     */
    private String getHeader() {
        String ret = "<head>\n";
        if (title != null) {
            ret += "   <title>" + title + "</title>\n";
        }
        ret += "<style>\n" + css + "\n</style>";
        ret += "\n</head>\n";
        return ret;
    }

    @Override
    public String asText() {
        return flatten(text).toString();
    }

    private StringBuffer flatten(StringBuffer text) {

        if (context != null) {
            describeData();
        }

        StringBuffer ret = new StringBuffer(title);
        ret.append(text);
        for (Section section : sections) {
            appendSection(section, 1, ret);
        }
        appendReferences(ret);

        return ret;
    }

    private void describeData() {

        Section save = currentSection;
        setSection("Data");

        for (IArtifact artifact : context.getProvenance().getArtifacts()) {
            // if (artifact.getObservation() instanceof IState
            // && !described.contains(artifact.getObservation())) {
            // if (artifact.getObservation().getContextObservation().equals(context.getSubject())) {
            // // TODO reintegrate - only 'true' data should be shown
            // if (artifact.getModel() != null && artifact.getModel().isResolved()) {
            // describe(artifact.getObservation());
            // }
            // }
            // }
        }

        currentSection = save;
    }

    private void appendReferences(StringBuffer ret) {
        ret.append("\n# References\n\n");
        for (int i = 0; i < citations.size(); i++) {
            ret.append((i + 1) + ". " + citations.get(i).linkIfAny() + "\n");
        }
    }

    private void appendSection(Section section, int level, StringBuffer ret) {

        if (!(section.text.toString().isEmpty() && section.children.isEmpty())) {

            ret.append("\n" + StringUtils.repeat('#', level) + " " + section.title + "\n\n");

            ret.append(section.text + "\n");

            for (Section child : section.children) {
                appendSection(child, level + 1, ret);
            }

        }
    }

    @Override
    public void setSection(String section) {
        /*
         * create sections and any in between; record declaration order
         */
        String[] path = section.split("/");
        Section sec = null;
        for (int i = 0; i < path.length; i++) {
            if (i == 0) {
                sec = findSection(path[i], true);
            } else {
                sec = sec.getChild(path[i], true);
            }
        }

        /*
         * set it to the current section
         */
        currentSection = sec;
    }

    private Section findSection(String string, boolean create) {
        for (Section s : sections) {
            if (s.title.equals(string)) {
                return s;
            }
        }
        Section ret = null;
        if (create) {
            ret = new Section(string);
            sections.add(ret);
        }
        return ret;
    }

    @Override
    public String toString() {
        return asText();
    }

    @Override
    public void setTitle(String title) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String addAttachment(Object o) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void writeLink(String markdown, String anchorOrUrl) {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadDocumentation(IDocumentation documentation) {
        for (String tag : documentation.getTags()) {
            if (tag.startsWith("ref:")) {
                Reference ref = new Reference();
                ref.id = tag.substring(4);
                ref.body = documentation.get(tag).getActionCode();
                references.put(ref.id, ref);
            }
        }
        for (String tag : documentation.getTags()) {
            if (tag.startsWith("link:")) {
                String id = tag.substring(5);
                Reference ref = references.get(id);
                if (ref != null) {
                    ref.link = documentation.get(tag).getActionCode();
                }
            }
        }
    }

    @Override
    public void reference(String... refs) {

        String ret = "";
        for (String ref : refs) {
            Reference reference = references.get(ref);
            if (reference != null) {
                int n = citations.indexOf(reference);
                if (n < 0) {
                    citations.add(reference);
                    n = citations.size() - 1;
                }
                if (ret.length() > 0) {
                    ret += ",";
                }
                ret += "[" + (n + 1) + "](#" + reference.id + ")";
            }
        }
        write("[" + ret + "]");

    }

    @Override
    public String getReference() {
        String ret = "A" + NameGenerator.shortUUID();
        write("<a name=\"" + ret + "\"></a>");
        return ret;
    }

    @Override
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
            // String description = ((IConcept) o).getMetadata().getString(IMetadata.DC_COMMENT);
            // if (description == null || description.trim().isEmpty()) {
            // description = "No description provided.";
            // }
            // writeln("\n<small>" + description + "</small>\n");

        } else if (o instanceof IObservation) {
            describe(((IObservation) o).getObservable().getType());
            write(SEPARATOR);
            linkImage((IObservation) o);
            write(SEPARATOR);
            IMetadata metadata = ((IObservation) o).getMetadata();
            // if (((Observation)o).getActuator() != null) {
            // if (((Observation)o).getActuator().getModel() != null) {
            // metadata = ((Observation)o).getActuator().getModel().getMetadata();
            // }
            // }
            writeMetadata(metadata);
            // AARGH
            // writeMetadata(((Observation) o).getActuator() == null ? ((IObservation) o).getMetadata()
            // : (((Observation) o).getActuator().getModel() == null
            // ? ((IObservation) o).getMetadata()
            // : ((Observation) o).getActuator().getModel().getMetadata()));
            described.add((IObservation) o);
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
            writeln("<small> " + ret + " </small>\n");
        }
    }

    private void linkImage(IObservation o) {
        // write("<img src=\"http://127.0.0.1:8183/kmodeler/engine/context/get/media/" + this.context.getId()
        // + "/"
        // + ((Observation) o).getId() + ".png?index=T0&viewport=VIEWPORT%23600%2C600\"></img>");
    }

    // bit nicer. Obviously we want a configurable solution.
    private static final String css = "/* body */\n" +
            "body {\n" +
            "    margin: 20px auto;\n" +
            "    width: 800px;\n" +
            "    background-color: #fff;\n" +
            "    color: #000;\n" +
            "    font: 13px \"Myriad Pro\", \"Lucida Grande\", Lucida, Verdana, sans-serif;\n" +
            "}\n" +
            "\n" +
            "/* links */\n" +
            "a:link {\n" +
            "    color: #00f;\n" +
            "    text-decoration: none;\n" +
            "}\n" +
            "\n" +
            "a:visited {\n" +
            "    color: #00a;\n" +
            "    text-decoration: none;\n" +
            "}\n" +
            "\n" +
            "a:hover {\n" +
            "    color: #f60;\n" +
            "    text-decoration: underline;\n" +
            "}\n" +
            "    \n" +
            "a:active {\n" +
            "    color: #f60;\n" +
            "    text-decoration: underline;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/* html tags */\n" +
            "\n" +
            "/*  Work around IE/Win code size bug - courtesy Jesper, waffle.wootest.net  */\n" +
            "\n" +
            "* html code {\n" +
            "    font-size: 101%;\n" +
            "}\n" +
            "\n" +
            "* html pre {\n" +
            "    font-size: 101%;\n" +
            "}\n" +
            "\n" +
            "/* code */\n" +
            "\n" +
            "pre, code {\n" +
            "    font-size: 11px; font-family: monaco, courier, consolas, monospace;\n" +
            "}\n" +
            "\n" +
            "pre {\n" +
            "    margin-top: 5px;\n" +
            "    margin-bottom: 10px;\n" +
            "    border: 1px solid #c7cfd5;\n" +
            "    background: #f1f5f9;\n" +
            "    margin: 20px 0;\n" +
            "    padding: 8px;\n" +
            "    text-align: left;\n" +
            "}\n" +
            "\n" +
            "hr {\n" +
            "    color: #919699;\n" +
            "    size: 1;\n" +
            "    width: 100%;\n" +
            "    noshade: \"noshade\"\n" +
            "}\n" +
            "\n" +
            "/* headers */\n" +
            "\n" +
            "\n" +
            "h1, h2, h3, h4, h5, h6 {\n" +
            "    font-family: \"Myriad Pro\", \"Lucida Grande\", Lucida, Verdana, sans-serif;\n" +
            "    font-weight: bold;\n" +
            "}\n" +
            "\n" +
            "h1  {\n" +
            "    margin-top: 1em;\n" +
            "    margin-bottom: 25px;\n" +
            "    color: #000;\n" +
            "    font-weight: bold;\n" +
            "    font-size: 24px;\n" +
            "}\n" +
            "h2  {\n" +
            "    margin-top: 2.5em;\n" +
            "    font-size: 18px;\n" +
            "    color: #000;\n" +
            "    padding-bottom: 2px;\n" +
            "    border-bottom: 1px solid #919699;\n" +
            "}\n" +
            "h3  {\n" +
            "    margin-top: 2em;\n" +
            "    margin-bottom: .5em;\n" +
            "    font-size: 16px;\n" +
            "    color: #000;\n" +
            "}\n" +
            "h4  {\n" +
            "    margin-top: 2em;\n" +
            "    margin-bottom: .5em;\n" +
            "    font-size: 14px;\n" +
            "    color: #000;\n" +
            "}\n" +
            "h5  {\n" +
            "    margin-top: 20px;\n" +
            "    margin-bottom: .5em;\n" +
            "    padding: 0;\n" +
            "    font-size: 12px;\n" +
            "    color: #000;\n" +
            "}\n" +
            "\n" +
            "h6  {\n" +
            "    margin-top: 20px;\n" +
            "    margin-bottom: .5em;\n" +
            "    padding: 0;\n" +
            "    font-size: 11px;\n" +
            "    color: #000;\n" +
            "}\n" +
            "\n" +
            "p {\n" +
            "    margin-top: 0px;\n" +
            "    margin-bottom: 10px;\n" +
            "}\n" +
            "\n" +
            "/* lists */\n" +
            "\n" +
            "ul  {\n" +
            "    list-style: square outside;\n" +
            "    margin: 0 0 0 30px;\n" +
            "    padding: 0 0 12px 6px;\n" +
            "}\n" +
            "\n" +
            "li  {\n" +
            "    margin-top: 7px;\n" +
            "}\n" +
            "            \n" +
            "ol {\n" +
            "    list-style-type: decimal;\n" +
            "    list-style-position: outside;\n" +
            "    margin: 0 0 0 30px;\n" +
            "    padding: 0 0 12px 6px;\n" +
            "}\n" +
            "    \n" +
            "ol ol {\n" +
            "    list-style-type: lower-alpha;\n" +
            "    list-style-position: outside;\n" +
            "    margin: 7px 0 0 30px;\n" +
            "    padding: 0 0 0 10px;\n" +
            "    }\n" +
            "\n" +
            "ul ul {\n" +
            "    margin-left: 40px;\n" +
            "    padding: 0 0 0 6px;\n" +
            "}\n" +
            "\n" +
            "li>p { display: inline }\n" +
            "li>p+p { display: block }\n" +
            "li>a+p { display: block }\n" +
            "\n" +
            "\n" +
            "/* table */\n" +
            "\n" +
            "table {\n" +
            "    width: 100%;\n" +
            "    border-top: 1px solid #919699;\n" +
            "    border-left: 1px solid #919699;\n" +
            "    border-spacing: 0;\n" +
            "}\n" +
            "    \n" +
            "table th {\n" +
            "    padding: 4px 8px 4px 8px;\n" +
            "    background: #E2E2E2;\n" +
            "    font-size: 12px;\n" +
            "    border-bottom: 1px solid #919699;\n" +
            "    border-right: 1px solid #919699;\n" +
            "}\n" +
            "table th p {\n" +
            "    font-weight: bold;\n" +
            "    margin-bottom: 0px; \n" +
            "}\n" +
            "    \n" +
            "table td {\n" +
            "    padding: 8px;\n" +
            "    font-size: 12px;\n" +
            "    vertical-align: top;\n" +
            "    border-bottom: 1px solid #919699;\n" +
            "    border-right: 1px solid #919699;\n" +
            "}\n" +
            "table td p {\n" +
            "    margin-bottom: 0px; \n" +
            "}\n" +
            "table td p + p  {\n" +
            "    margin-top: 5px; \n" +
            "}\n" +
            "table td p + p + p {\n" +
            "    margin-top: 5px; \n" +
            "}\n" +
            "\n" +
            "/* forms */\n" +
            "\n" +
            "form {\n" +
            "    margin: 0;\n" +
            "}\n" +
            "\n" +
            "button {\n" +
            "    margin: 3px 0 10px 0;\n" +
            "}\n" +
            "input {\n" +
            "    vertical-align: middle;\n" +
            "    padding: 0;\n" +
            "    margin: 0 0 5px 0;\n" +
            "}\n" +
            "\n" +
            "select {\n" +
            "    vertical-align: middle;\n" +
            "    padding: 0;\n" +
            "    margin: 0 0 3px 0;\n" +
            "}\n" +
            "\n" +
            "textarea {\n" +
            "    margin: 0 0 10px 0;\n" +
            "    width: 100%;\n" +
            "}";

}
