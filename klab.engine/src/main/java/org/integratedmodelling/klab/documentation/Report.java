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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider.Item;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.documentation.Documentation.SectionImpl;
import org.integratedmodelling.klab.documentation.Documentation.TemplateImpl;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.utils.Path;
import org.springframework.util.StringUtils;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
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

    enum RefType {
        REF,
        FIG,
        TABLE,
        FOOTNOTE,
        DATAFLOW
    }

    private Map<SectionRole, ReportSection> mainSections = new HashMap<>();

    private List<IResource> resources = new ArrayList<>();
    private List<IKimTable> tables = new ArrayList<>();
    private List<IModel> models = new ArrayList<>();
    private List<IPrototype> services = new ArrayList<>();
    private List<IDataflow<?>> dataflows = new ArrayList<>();
    private Map<String, IObservationReference> observations = new HashMap<>();
    Map<String, ReportSection> referencesCited = new HashMap<>();
    Map<String, ReportSection> tablesCited = new HashMap<>();
    Map<String, ReportSection> modelsCited = new HashMap<>();
    Map<String, ReportSection> observationsCited = new HashMap<>();
    Map<String, ReportSection> dataflowsCited = new HashMap<>();
    Map<String, IDocumentationProvider.Item> taggedText = new HashMap<>();

    Map<RefType, Set<String>> refTypes = new HashMap<>();
    Set<String> observationDescribed = new HashSet<>();
    Set<String> inserted = new HashSet<>();
    Set<String> usedTags = new HashSet<>();

    public RefType getReferenceType(String reference) {
        RefType ret = null;
        for (RefType type : RefType.values()) {
            if (refTypes.containsKey(type) && refTypes.get(type).contains(reference)) {
                return type;
            }
        }
        return ret;
    }

    public void setReferenceType(String reference, RefType type) {
        Set<String> set = refTypes.get(type);
        if (set == null) {
            set = new HashSet<>();
            refTypes.put(type, set);
        }
        set.add(reference);
    }

    public void addTaggedText(IDocumentationProvider.Item item) {
        this.taggedText.put(item.getId(), item);
    }

	public IDocumentationProvider.Item getTaggedText(String tag) {
        return this.taggedText.get(tag);
    }

    // @Override
    public void include(IDocumentation.Template template, IContextualizationScope context) {
        ReportSection section = getMainSection(((TemplateImpl) template).getRole());
        template.compile(section, context);
    }

    // @Override
    public void include(IContextualizable resource) {

        if (resource.getUrn() != null) {
            resources.add(Resources.INSTANCE.resolveResource(resource.getUrn()));
        } else if (resource.getLookupTable() != null) {
            tables.add(resource.getLookupTable().getTable());
        } else if (resource.getClassification() != null) {
            // classification
        } else if (resource.getServiceCall() != null) {
            Prototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
            if (prototype != null) {
                services.add(prototype);
            }
        }
    }

    public IObservationReference getObservation(String id) {
        return observations.get(id);
    }

    // @Override
    public void include(IModel model) {
        models.add(model);
    }

    // @Override
    public void include(IDataflow<?> dataflow) {
        dataflows.add(dataflow);
    }

    public void include(IObservationReference output) {
        observations.put(output.getId(), output);
    }

//    public void addSection(Section section) {
//        ReportSection main = getMainSection(section.getRole());
//        main.children.add((ReportSection) section);
//    }

    public void include(ITask<?> task) {
        // notify task start, finish, abort
    }

    /**
     * Require the contents of a passed project-level template into the section
     * named in the second argument.
     * 
     * @param processArguments
     * @param context
     */
    public void require(Object[] args, IDocumentation documentation, IContextualizationScope context) {

        if (inserted.contains(args[0] + "|" + args[1])) {
            return;
        }

        Reference template = ((Documentation) documentation).getReference(args[0].toString());
        if (template != null) {
            String srole = Path.getFirst(args[1].toString(), "/");
            SectionRole role = SectionRole.valueOf(srole.toUpperCase());
            if (role != null) {
                ReportSection main = getMainSection(role);
                if (args[1].toString().contains("/")) {
                    // TODO!
                    //					main = main.getChild(parent, titlePath)
                }
                main.body.append("\n\n" + template.get(BibTexFields.EXAMPLE_CITATION) + "\n\n");
            }
        }

        inserted.add(args[0] + "|" + args[1]);
    }

    /*
     * get or create the main section for a section.
     */
    ReportSection getMainSection(SectionRole role) {
        ReportSection ret = mainSections.get(role);
        if (ret == null) {
            ret = new ReportSection(this, role);
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

    IRuntimeScope context = null;
    private String sessionId;

    public Report() {
    }

    public Report(IRuntimeScope context, String sessionId) {
        this.context = context;
        this.sessionId = sessionId;
    }

    public String asHTML(String markdown) {

        MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS,
                Arrays.asList(FootnoteExtension.create(), AttributesExtension.create(),
                        EnumeratedReferenceExtension.create(), MediaTagsExtension.create(),
                        DefinitionExtension.create(), TablesExtension.create()));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    @Override
    public String render(Encoding encoding) {

        StringBuffer ret = new StringBuffer(16 * 1024);

        ret.append(getTitleSection());

        Section appendix = mainSections.get(SectionRole.APPENDIX);

        /*
         * If we have an appendix, add any tagged documentation coming from contextualizers that has not
         * been used in the report.
         */
        if (appendix != null) {
            for (String tag : taggedText.keySet()) {
                if (!usedTags.contains(tag)) {
                    Item item = taggedText.get(tag);
                    ((SectionImpl) appendix).body += "\n\n## " + item.getTitle() + "\n\n" + item.getMarkdownContents()
                            + "\n\n";
                }
            }
        }

        /*
         * Add anything not explicitly described according to settings; make
         * appendices and references
         */
        int n = 0;
        for (Section s : getSections()) {
            ret.append(((ReportSection) s).render(0, (++n) + ""));
        }

        /*
         * If we have tagged content, no appendix and no sections that may have used it,
         * make an appendix and add to it.
         */
        if (appendix == null && (taggedText.size() - usedTags.size()) > 0) {

            ret.append("\n\n# Appendix\n\n");

            for (String tag : taggedText.keySet()) {
                if (!usedTags.contains(tag)) {
                    Item item = taggedText.get(tag);
                    ret.append("\n\n## " + item.getTitle() + "\n\n" + item.getMarkdownContents() + "\n\n");
                }
            }
        }

        // TODO these should be configurable
        ret.append("\n\n" + "[@ref]: Reference [#]\n" + "[@fig]: Figure [#]\n" + "[@table]: Table [#]\n"
                + "[@footnote]: Footnote [#]\n" + "[@user]: [#]\n" + "[@dataflow]: Dataflow [#]\n");

        //		System.out.println(ret.toString());

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

    private String getTitleSection() {
        String ret = "# ![Integrated Modelling Partnership](../logos/im64.png){float=left} k.LAB Contextualization report\n\n";
        ret += "---\n";
        ret += "Computed at " + new Date();
        ret += "\n\n";
        return ret;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void notifyUsedTag(String id) {
        this.usedTags.add(id);
    }

}
