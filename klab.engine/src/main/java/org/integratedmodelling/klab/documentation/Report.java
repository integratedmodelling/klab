/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.documentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.general.IStructuredTable;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Template;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.observations.IKnowledgeView;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.rest.IObservationReference;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.components.localstorage.impl.TimesliceLocator;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.table.TableValue;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.documentation.Documentation.Scope;
import org.integratedmodelling.klab.documentation.Documentation.TemplateImpl;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.rest.DocumentationEvent;
import org.integratedmodelling.klab.rest.DocumentationNode;
import org.integratedmodelling.klab.rest.DocumentationNode.Figure;
import org.integratedmodelling.klab.rest.DocumentationNode.Table;
import org.integratedmodelling.klab.rest.DocumentationNode.Type;
import org.integratedmodelling.klab.rest.KnowledgeViewReference;
import org.integratedmodelling.klab.utils.MarkdownUtils;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.StringUtil;
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
		REF, FIG, TABLE, FOOTNOTE, DATAFLOW
	}

	private Map<SectionRole, ReportSection> mainSections = new HashMap<>();

	private List<IResource> resources = new ArrayList<>();
	private List<IKimTable> tables = new ArrayList<>();
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
	// abstract incarnated predicates by documentation ID
	Map<String, Map<IConcept, Set<IConcept>>> incarnatedPredicates = new HashMap<>();
	Map<String, Map<String, Object>> templateVariables = new HashMap<>();

	/**
	 * Anchors in HTML code are sent using this pattern, to be reinterpreted by the
	 * client.
	 */
	public static final String ANCHOR_PATTERN = "LINK/{type}/{id}/";

	private Map<String, DocumentationNode> nodes = new LinkedHashMap<>();
	private ISession session;
//    // private IRuntimeScope context;
//    private Report report;
	// private List<ReportSection> mainSections = new ArrayList<>();

	/*
	 * this keeps track of which resources are used from temporally merged
	 * resourcesets
	 */
	private Map<String, Map<String, IResource>> contextualizedResources = new HashMap<>();

	// models in order of usage
	private Set<IModel> models = new LinkedHashSet<>();
	// and the resolution for each observable
	private Map<ObservedConcept, List<IRankedModel>> resolutions = new HashMap<>();
	private Parser parser_;
	private HtmlRenderer renderer_;
	private int referencesCount;

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
	public void include(IDocumentation.Template template, IContextualizationScope context,
			IDocumentation documentation) {
		ReportSection section = getMainSection(((TemplateImpl) template).getRole());
		template.compile(section, context, getTemplateVariables(template));
	}
	
	/**
	 * Add a resource to the report.
	 * Used when the IContextualizable is a MergedResource so is called
	 * when it is contextualized
	 * @param resource
	 */
	public void include(IResource resource) {

	    if (resource.isEmpty()) {
	        return;
	    }
	    
		if (resource.getUrn() != null) {
			IResource res = Resources.INSTANCE.resolveResource(resource.getUrn());
			resources.add(res);
			addComputable(res);
		}
	}

	public void include(IContextualizable resource, Actuator actuator) {

	    if (resource.isEmpty()) {
	        return;
	    }
	    
		if (resource.getUrn() != null) {
			IResource res = Resources.INSTANCE.resolveResource(resource.getUrn());
			resources.add(res);
			addComputable(res);
		} else if (resource.getLookupTable() != null) {
			IKimTable table = resource.getLookupTable().getTable();
			tables.add(table);
			addComputable(table);
		} else if (resource.getClassification() != null) {
			// classification
			addComputable(resource.getClassification());
		} else if (resource.getServiceCall() != null) {
			Prototype prototype = Extensions.INSTANCE.getPrototype(resource.getServiceCall().getName());
			if (prototype != null) {
				services.add(prototype);
				addComputable(prototype);
			}
		}
	}

	public IObservationReference getObservation(String id) {
		return observations.get(id);
	}

	public void include(IModel model) {
		models.add(model);
		addComputable(model);
	}

	public void include(IDataflow<?> dataflow) {
		dataflows.add(dataflow);
	}

	public void include(IObservationReference output, IObservation observation) {
		observations.put(output.getId(), output);
		addComputable(output);

	}

	public void include(ITask<?> task) {
		// notify task start, finish, abort
		addComputable(task);
	}

	/**
	 * Require the contents of a passed project-level template into the section
	 * named in the second argument.
	 * 
	 * @param processArguments
	 * @param context
	 */
	public void require(IParameters<String> args, IDocumentation documentation, IContextualizationScope context) {

		if (inserted.contains(args.getUnnamedArguments().get(0) + "|" + args.getUnnamedArguments().get(1))) {
			return;
		}

		Reference template = ((Documentation) documentation).getReference(args.getUnnamedArguments().get(0).toString());
		if (template != null) {
			String srole = Path.getFirst(args.getUnnamedArguments().get(1).toString(), "/");
			try {
				SectionRole role = SectionRole.valueOf(srole.toUpperCase());
				if (role != null) {
					ReportSection main = getMainSection(role);
					if (args.getUnnamedArguments().get(1).toString().contains("/")) {
						// TODO!
						// main = main.getChild(parent, titlePath)
					}
					main.appendContent("\n\n" + template.get(BibTexFields.EXAMPLE_CITATION) + "\n\n");
				}
			} catch (Throwable merda) {
				throw new KlabInternalErrorException(
						"Corrupt documentation file! Found non-existing section role " + srole);
			}
		}

		inserted.add(args.getUnnamedArguments().get(0) + "|" + args.getUnnamedArguments().get(1));
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
			addComputable(ret);
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

	public Report(IRuntimeScope context, IResolutionScope scope, String sessionId) {
		this.context = context;
		this.sessionId = sessionId;
		this.session = Authentication.INSTANCE.getIdentity(sessionId, ISession.class);
	}

	public Report() {
	}

	public void recordResolutions(IResolutionScope scope) {
		for (ObservedConcept key : ((ResolutionScope) scope).getResolutions().keySet()) {
			addResolution(key, ((ResolutionScope) scope).getResolutions().get(key));
		}
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

	private String getTitleSection() {
		String ret = "# ![Integrated Modelling Partnership](../logos/im64.png){float=left} k.LAB Contextualization report\n\n";
		ret += "---\n";
		ret += "Computed on " + new Date() + " in " + context.getRootSubject().getName();
		ret += "\n\n";
		return ret;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void notifyUsedTag(String id) {
		this.usedTags.add(id);
	}

	private Set<IConcept> getIncarnatedPredicates(Template template, Trigger trigger, IConcept key) {

		String id = template.getId() + "@" + trigger.name();

		Map<IConcept, Set<IConcept>> predicates = incarnatedPredicates.get(id);
		if (predicates == null) {
			predicates = new HashMap<>();
			incarnatedPredicates.put(id, predicates);
		}

		Set<IConcept> ret = predicates.get(key);
		if (ret == null) {
			ret = new HashSet<>();
			predicates.put(key, ret);
		}

		return ret;

	}

	/**
	 * Check if an observable incarnates a set of abstract predicates, and if so
	 * only return true after all the incarnated traits have been seen. Set the
	 * variables so that a foreach loop over the abstract trait can return all the
	 * observables.
	 * 
	 * If there are no incarnated predicates, return true so that the report can
	 * continue.
	 * 
	 * @param trigger
	 * 
	 * @param target
	 * @return
	 */
	public boolean checkObservableCoverage(Template template, Actuator actuator, Trigger trigger) {
		if (!actuator.getObservable().getResolvedPredicates().isEmpty()) {
			boolean ok = true;
			for (IConcept key : actuator.getObservable().getResolvedPredicates().keySet()) {
				Set<IConcept> seen = getIncarnatedPredicates(template, trigger, key);
				@SuppressWarnings("unchecked")
				Set<IObservable> observables = (Set<IObservable>) getTemplateVariables(template)
						.get(Concepts.INSTANCE.getCodeName(key) + "_observables");
				if (observables == null) {
					observables = new LinkedHashSet<>();
					getTemplateVariables(template).put(Concepts.INSTANCE.getCodeName(key) + "_observables",
							observables);
				}
				observables.add(actuator.getObservable());
				// stuff like 'crop_observables' will be in the template vars to access the
				// states
				seen.add(actuator.getObservable().getResolvedPredicates().get(key));

				if (seen.size() < actuator.getObservable().getResolvedPredicatesContext().get(key).size()) {
					ok = false;
				} else {
					// set the array of incarnated concepts as a var for each template during
					// render() to use if wanted
					getTemplateVariables(template).put(Concepts.INSTANCE.getCodeName(key) + "_types", seen);
				}
			}

			return ok;
		}
		return true;
	}

	private Map<String, Object> getTemplateVariables(Template template) {
		Map<String, Object> ret = templateVariables.get(template.getId());
		if (ret == null) {
			ret = new HashMap<>();
			templateVariables.put(template.getId(), ret);
		}
		return ret;
	}

	public String md2html(String markdown) {
		if (this.renderer_ == null) {
			MutableDataSet options = new MutableDataSet().set(Parser.EXTENSIONS,
					Arrays.asList(FootnoteExtension.create(), AttributesExtension.create(),
							EnumeratedReferenceExtension.create(), MediaTagsExtension.create(),
							DefinitionExtension.create(), TablesExtension.create()));

			this.parser_ = Parser.builder(options).build();
			this.renderer_ = HtmlRenderer.builder(options).build();
		}
		Node document = parser_.parse(markdown);
		return renderer_.render(document);
	}

	public List<DocumentationNode> getView(View view, String format) {
		switch (view) {
		case FIGURES:
			return getFiguresView(format);
		case MODELS:
			return getModelsView(format);
		case REPORT:
			return getReportView(format);
		case RESOURCES:
			return getResourcesView(format);
		case TABLES:
			return getTablesView(format);
		case PROVENANCE:
			return getProvenanceView(format);
		case REFERENCES:
			break;
		default:
			break;
		}
		return null;
	}

	public static String getLinkText(DocumentationNode node) {
		return ANCHOR_PATTERN.replace("{type}", node.getType().name()).replace("{id}", node.getId());
	}

	public void addResolution(ObservedConcept observable, List<IRankedModel> resolved) {
		resolutions.put(observable, resolved);
	}

	/**
	 * Add a first-class object TODO add the model that uses it, if any
	 * 
	 * @param o
	 */
	public void addComputable(Object o) {

		DocumentationNode item = null;

		if (o instanceof IResource) {

			IResource resource = (IResource) o;
			if (nodes.get(resource.getUrn()) == null) {
				DocumentationNode node = getResourceNode(resource);
				if (node != null) {
					nodes.put(node.getId(), node);
					notify(node);
				}
			}

		} else if (o instanceof IPrototype) {

		} else if (o instanceof ReportSection) {

			// this.mainSections.add((ReportSection) o);
			// This only to send a placeholder and say that there is a report
			notify(getReferencesNode());

		} else if (o instanceof IObservationReference) {

		} else if (o instanceof IKimTable) {

		} else if (o instanceof IClassification) {

		} else {
			// System.out.println("OHIBÓ un cianfero non visto prima: " +
			// o.getClass().getCanonicalName());
		}
	}

	private DocumentationNode getResourceNode(IResource resource) {

		if (resource instanceof MergedResource) {
			// we only document the individual (used) ones here.
			return null;
		}

		Urn urn = new Urn(resource.getUrn());

		DocumentationNode ret = new DocumentationNode();
		ret.setId(resource.getUrn());
		ret.setType(DocumentationNode.Type.Resource);
		ret.setTitle(resource.getMetadata().containsKey(IMetadata.DC_TITLE)
				? resource.getMetadata().get(IMetadata.DC_TITLE).toString()
				: urn.getResourceId());

		DocumentationNode.Resource res = new DocumentationNode.Resource();

		if (resource.getMetadata().get(IMetadata.DC_URL) != null) {
			String content = resource.getMetadata().get(IMetadata.DC_URL).toString();
			for (String c : content.split("\\s*(;|,|\\s)\\s*")) {
				res.getUrls().add(c);
			}
		}
		if (resource.getMetadata().get(IMetadata.DC_COMMENT) != null) {
			String content = resource.getMetadata().get(IMetadata.DC_COMMENT).toString();
			res.setResourceDescription(MarkdownUtils.INSTANCE.format(content));
		}
		if (resource.getMetadata().get(IMetadata.DC_CREATOR) != null) {
			String content = resource.getMetadata().get(IMetadata.DC_CREATOR).toString();
			if (!"".equals(content.trim())) {
				for (String c : content.split("\\n")) {
					res.getAuthors().add(c.trim());
				}
			}
		}
		if (resource.getMetadata().get(IMetadata.IM_THEMATIC_AREA) != null) {
			String content = resource.getMetadata().get(IMetadata.IM_THEMATIC_AREA).toString();
			res.getKeywords().add(content);
		}
		if (resource.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA) != null) {
			String content = resource.getMetadata().get(IMetadata.IM_GEOGRAPHIC_AREA).toString();
			res.getKeywords().add(content);
		}
		if (resource.getMetadata().get(IMetadata.DC_SOURCE) != null) {
			String content = resource.getMetadata().get(IMetadata.DC_SOURCE).toString();
			res.setBibliographicReference(content);
		}
		if (resource.getMetadata().get(IMetadata.IM_NOTES) != null) {
			String content = resource.getMetadata().get(IMetadata.IM_NOTES).toString();
			res.setAccessDescription(MarkdownUtils.INSTANCE.format(content));
		}
		if (resource.getMetadata().get(IMetadata.DC_ORIGINATOR) != null) {
			String content = resource.getMetadata().get(IMetadata.DC_ORIGINATOR).toString();
			res.setOriginatorDescription(content);
		}
		if (resource.getMetadata().get(IMetadata.IM_KEYWORDS) != null) {
			String content = resource.getMetadata().get(IMetadata.IM_KEYWORDS).toString();
			if (!"".equals(content.trim())) {
				for (String c : content.trim().split("(;|,)")) {
					res.getKeywords().add(c.trim());
				}
			}
		}
		res.setOriginatorDescription(resource.getMetadata().containsKey(IMetadata.DC_ORIGINATOR)
				? resource.getMetadata().get(IMetadata.DC_ORIGINATOR).toString()
				: "Unknown originator");

		if (resource.getGeometry().getDimension(Dimension.Type.SPACE) != null) {
			res.setSpaceDescriptionUrl(
					API.ENGINE.RESOURCE.GET_RESOURCE_SPATIAL_IMAGE.replace("{urn}", resource.getUrn()));
		}
		if (resource.getGeometry().getDimension(Dimension.Type.TIME) != null) {
		}

		ret.setResource(res);
		return ret;
	}

	public void addView(IKnowledgeView view, KnowledgeViewReference descriptor) {

		DocumentationNode node = new DocumentationNode();
		if (!nodes.containsKey(view.getId())) {
			node.setId(view.getId());
			node.setTitle(view.getTitle());
			node.setBodyText(view.getLabel());
			if ("table".equals(view.getViewClass())) {
				node.setTable(view.getBean(Table.class));
				node.setType(DocumentationNode.Type.Table);
			}
			this.nodes.put(node.getId(), node);
			notify(node);
		}
	}

	public void addView(TableValue view, KnowledgeViewReference descriptor) {

		DocumentationNode node = new DocumentationNode();
		if (!nodes.containsKey(view.getId())) {
			node.setId(view.getId());
			node.setTitle(view.getTitle());
			node.setBodyText(view.getLabel());
			node.setTable(view.getBean(Table.class));
			node.setType(DocumentationNode.Type.Table);
			this.nodes.put(node.getId(), node);
			notify(node);
		}
	}

	// TODO add the contextualization
	public void addModel(IModel model) {
		if (!this.nodes.containsKey(model.getName()) && model.getStatement() != null) {
			DocumentationNode node = new DocumentationNode();
			node.setId(model.getName());
			node.setTitle(StringUtil.capitalize(model.getId().replace("_", " ")));
			node.setBodyText(model.getStatement().getSourceCode());
			node.setModel(((Model) model).getBean());
			node.setType(DocumentationNode.Type.Model);
			this.nodes.put(node.getId(), node);
			models.add(model);
			notify(node);
		}
	}

	// TODO add the model
	public void addObservation(IObservation observation) {

	}

	/**
	 * Child citation
	 * 
	 * @param reportSection
	 * @param reference
	 */
	public DocumentationNode addCitation(Reference reference) {

		DocumentationNode.Reference ref = null;
		if (reference.get(BibTexFields.EXAMPLE_CITATION) == null) {
			// the key is the DOI
			String doi = reference.get("key").toString();
			if (!nodes.containsKey(doi)) {
				ref = Crossref.INSTANCE.resolve(doi);
			}
		} else {

			if (!nodes.containsKey(reference.get("key").toString())) {
				ref = new DocumentationNode.Reference();
				ref.getCitations().put("default", reference.get(BibTexFields.EXAMPLE_CITATION).toString());
				ref.setDoi(reference.get("key").toString());
			}
		}

		// add reference if not there already
		if (ref != null) {
			DocumentationNode node = new DocumentationNode();
			node.setBodyText(ref.getCitations().get("default"));
			node.setType(DocumentationNode.Type.Reference);
			node.setId(ref.getDoi());
			nodes.put(node.getId(), node);
			this.referencesCount++;
			notify(node);
		}

		return ref == null ? null : nodes.get(ref.getDoi());
	}

	public void notify(DocumentationNode node) {

		DocumentationEvent message = new DocumentationEvent();

		message.setNodeId(node.getId());
		message.setNodeType(node.getType());

		switch (node.getType()) {
		case Anchor:
			break;
		case Chart:
			message.getViewsAffected().add(IReport.View.FIGURES);
			break;
		case Citation:
			break;
		case Figure:
			message.getViewsAffected().add(IReport.View.FIGURES);
			break;
		case Link:
			break;
		case Model:
			message.getViewsAffected().add(IReport.View.MODELS);
			break;
		case Paragraph:
			message.getViewsAffected().add(IReport.View.REPORT);
			break;
		case Reference:
			message.getViewsAffected().add(IReport.View.REPORT);
			message.getViewsAffected().add(IReport.View.REFERENCES);
			break;
		case Report:
			message.getViewsAffected().add(IReport.View.REPORT);
			break;
		case Resource:
			message.getViewsAffected().add(IReport.View.RESOURCES);
			break;
		case Section:
			message.getViewsAffected().add(IReport.View.REPORT);
			break;
		case Table:
			message.getViewsAffected().add(IReport.View.REPORT);
			message.getViewsAffected().add(IReport.View.TABLES);
			break;
		case View:
			message.getViewsAffected().add(IReport.View.TABLES);
			break;
		default:
			break;

		}

		session.getMonitor().send(Message.create(session.getId(), IMessage.MessageClass.UserInterface,
				IMessage.Type.DocumentationChanged, message));
	}

	private List<DocumentationNode> getProvenanceView(String format) {
		List<DocumentationNode> ret = new ArrayList<>();
		return ret;
	}

	private List<DocumentationNode> getTablesView(String format) {
		List<DocumentationNode> ret = new ArrayList<>();
		for (DocumentationNode node : nodes.values()) {
			if (node.getType() == Type.Table) {
				ret.add(node);
			}
		}
		return ret;
	}

	public Collection<DocumentationNode> getExistingViewNode(String viewIdentifier) {
		List<DocumentationNode> ret = new ArrayList<>();
		for (DocumentationNode node : nodes.values()) {
			if (node.getType() == Type.Table) {
				if (node.getTable().getDocumentationIdentifier().equals(viewIdentifier)) {
					ret.add(node);
				}
			}
		}
		return ret;
	}

	private List<DocumentationNode> getResourcesView(String format) {
		List<DocumentationNode> ret = new ArrayList<>();
		for (DocumentationNode node : nodes.values()) {
			if (node.getType() == Type.Resource) {
				ret.add(node);
			}
		}
		return ret;
	}

	private List<DocumentationNode> getReportView(String format) {
		List<DocumentationNode> ret = new ArrayList<>();
		for (SectionRole order : SectionRole.values()) {
			boolean done = false;
			ReportSection section = mainSections.get(order);
			if (section != null) {
				if (order == section.getRole()) {
					ret.add(section.render(format));
					done = true;
				}
			}
			if (order == SectionRole.REFERENCES && !done && this.referencesCount > 0) {
				ret.add(getReferencesNode());
			}
		}

		return ret;
	}

	private DocumentationNode getReferencesNode() {

		DocumentationNode ret = new DocumentationNode();
		ret.setId("References");
		ret.setType(Type.Section);
		ret.setTitle("References cited");
		for (DocumentationNode node : nodes.values()) {
			if (node.getType() == Type.Reference) {
				ret.getChildren().add(node);
			}
		}
		return ret;
	}

	private DocumentationNode compileSection(ReportSection section, String format) {

		DocumentationNode ret = new DocumentationNode();
		ret.setId(section.getId());
		ret.setType(Type.Section);
		ret.setTitle(section.getName() == null
				? (section.getRole() == null ? null : StringUtil.capitalize(section.getRole().name().toLowerCase()))
				: section.getName());

		if (section.role == SectionRole.REFERENCES) {
			for (DocumentationNode node : nodes.values()) {
				if (node.getType() == Type.Reference) {
					ret.getChildren().add(node);
				}
			}
		}

		return ret;
	}

	private List<DocumentationNode> getModelsView(String format) {
		List<DocumentationNode> ret = new ArrayList<>();
		for (DocumentationNode node : nodes.values()) {
			if (node.getType() == Type.Model) {
				ret.add(node);
			}
		}
		return ret;
	}

	private List<DocumentationNode> getFiguresView(String format) {
		List<DocumentationNode> ret = new ArrayList<>();
		for (DocumentationNode node : nodes.values()) {
			if (node.getType() == Type.Figure) {
				ret.add(checkForUpdate(node));
			}
		}
		return ret;
	}

	DocumentationNode checkForUpdate(DocumentationNode node) {
		if (node.getFigure() != null) {
			/*
			 * recover state
			 */
			IObservation observation = this.session.getObservation(node.getFigure().getObservationId());
			if (observation instanceof State) {
				List<ILocator> locators = ((State) observation).getSliceLocators();
				if (node.getFigure().getTimeSlices().size() < locators.size()) {
					node.getFigure().getTimeSlices().clear();
					for (ILocator locator : locators) {
						TimesliceLocator sl = (TimesliceLocator) locator;
						node.getFigure().getTimeSlices().add(sl.getTimestamp() + "," + sl.getLabel());
					}
				}
			}
		}
		return node;
	}

	/**
	 * Notify that a resource out of a merged resource set has been used in this
	 * scope.
	 * 
	 * @param urn
	 * @param first
	 */
	public void addContextualizedResource(String urn, IResource resource) {
		Map<String, IResource> ret = this.contextualizedResources.get(urn);
		if (ret == null) {
			ret = new LinkedHashMap<>();
			this.contextualizedResources.put(urn, ret);
			if (!nodes.containsKey(resource.getUrn())) {
				DocumentationNode node = getResourceNode(resource);
				if (node != null) {
					nodes.put(node.getId(), node);
					notify(node);
				}
			}
		}
		ret.put(resource.getUrn(), resource);
	}

	public List<IResource> getContextualizedResources(String urn) {
		List<IResource> ret = new ArrayList<>();
		Map<String, IResource> ress = this.contextualizedResources.get(urn);
		if (ress != null) {
			for (IResource res : ress.values()) {
				ret.add(res);
			}
		}
		return ret;
	}

	public ReportElement getTableDescriptor(IStructuredTable<?> table, Scope scope, IParameters<String> args) {
		Table tab = new Table();
		// TODO hostia
		ReportElement ret = new ReportElement(Type.Table, tab, this);
		scope.link(ret.getId(), ret);
		return ret;
	}

	public ReportElement getFigureDescriptor(IArtifact artifact, IObservationReference ref, Scope scope,
			IParameters<String> args) {

		if (ref.isEmpty() || ref.getDataSummary() == null || ref.getLiteralValue() != null) {
			return null;
		}

		Figure figure = new Figure();

		String id = null;
		String caption = "";
		if (args.containsKey("id")) {
			id = args.get("id").toString();
			if (args.getUnnamedArguments().size() > 1) {
				caption = args.getUnnamedArguments().get(1).toString();
			}
		} else if (args.getUnnamedArguments().size() > 1) {
			id = args.getUnnamedArguments().get(1).toString();
			if (args.getUnnamedArguments().size() > 2) {
				caption = args.getUnnamedArguments().get(2).toString();
			}
		} else {
			id = "fig" + NameGenerator.shortUUID();
			if (args.getUnnamedArguments().size() > 1) {
				caption = args.getUnnamedArguments().get(1).toString();
			}
		}

		figure.setId(scope.disambiguateId(id));
		figure.setCaption(caption);
		figure.setObservationId(artifact.getId());
		figure.setLabel(ref.getLabel());

		/**
		 * Must add output type, locator and viewport
		 */
		String baseUrl = API.ENGINE.OBSERVATION.VIEW.GET_DATA_OBSERVATION.replace("{observation}", artifact.getId());
		figure.setObservationType(ref.getObservationType());
		figure.getGeometryTypes().addAll(ref.getGeometryTypes());
		figure.setObservableType(ref.getObservableType());

		ITime time = ((IObservation) artifact).getScale().getTime();
		figure.setStartTime(time == null ? -1 : time.getStart().getMilliseconds());
		figure.setEndTime(time == null ? -1 : time.getEnd().getMilliseconds());

		if (artifact instanceof State) {
			for (ILocator locator : ((State) artifact).getSliceLocators()) {
				TimesliceLocator sl = (TimesliceLocator) locator;
				figure.getTimeSlices().add(sl.getTimestamp() + "," + sl.getLabel());
			}
		}

		figure.setDataSummary(ref.getDataSummary());
		figure.setBaseUrl(baseUrl);

		ReportElement ret = new ReportElement(Type.Figure, figure, this);

		nodes.put(figure.getId(), ret.getNode());

		scope.link(ret.getId(), ret);
		return ret;
	}

	/**
	 * TODO out of date method: can't render as a string any more, but it's still
	 * called in dataflow docs. Should investigate exactly what's needed there.
	 * 
	 * @param html
	 * @return
	 */
	public String render(Encoding html) {
		return "";
	}

}
