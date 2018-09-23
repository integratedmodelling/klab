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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.documentation.Documentation.TemplateImpl;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.kim.Prototype;
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

    private List<IResource>                 resources    = new ArrayList<>();
    private List<IKimTable>                 tables       = new ArrayList<>();
    private List<IModel>                    models       = new ArrayList<>();
    private List<IPrototype>                services     = new ArrayList<>();
    private List<IDataflow>                 dataflows    = new ArrayList<>();
    private Set<IObservation>               observations = new HashSet<>();

    // filled as we go with the actual section including its reference
    Map<String, ReportSection> referencesCited = new HashMap<>();
    Map<String, ReportSection> tablesCited = new HashMap<>();
    Map<String, ReportSection> modelsCited = new HashMap<>();
    Map<String, ReportSection> observationsCited = new HashMap<>();
    Map<String, ReportSection> dataflowsCited = new HashMap<>();
    
    @Override
    public void include(IDocumentation.Template template, IComputationContext context) {
        ReportSection section = getMainSection(((TemplateImpl) template).getRole());
        template.compile(section, context);
    }

    @Override
    public void include(IComputableResource resource) {
        System.out.println("HA");
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

    @Override
    public void include(IModel model) {
        models.add(model);
    }

    @Override
    public void include(IDataflow<?> dataflow) {
        dataflows.add(dataflow);
    }

    @Override
    public void include(IObservation output) {
        observations.add(output);
    }

    public void addSection(Section section) {
        ReportSection main = getMainSection(section.getRole());
        main.children.add((ReportSection) section);
    }

    /**
     * Require the contents of a passed project-level template into the section named in the second argument.
     * 
     * @param processArguments
     * @param context
     */
    public void require(Object[] processArguments, IDocumentation documentation, IComputationContext context) {
        // TODO Auto-generated method stub
        System.out.println("FOCOK");
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

    IRuntimeContext            context   = null;

    public Report() {
    }

    public Report(IRuntimeContext context) {
        this.context = context;
    }

    public String asHTML(String markdown) {

        MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays
                .asList(FootnoteExtension.create(), AttributesExtension.create(), EnumeratedReferenceExtension
                        .create(), MediaTagsExtension.create(), DefinitionExtension.create()));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    @Override
    public String render(Encoding encoding) {

        StringBuffer ret = new StringBuffer(16 * 1024);

        /*
         * TODO add anything not explicitly described; make appendices and references
         */

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
