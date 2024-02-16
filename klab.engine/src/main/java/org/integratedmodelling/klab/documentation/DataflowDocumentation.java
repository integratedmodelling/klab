package org.integratedmodelling.klab.documentation;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.ComputableResource;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.classification.IClassification;
import org.integratedmodelling.klab.api.data.classification.ILookupTable;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.api.documentation.IReport.Encoding;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.utils.MarkdownUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtils;

public enum DataflowDocumentation {

	INSTANCE;

	VelocityEngine engine;

	public static final String ACTUATOR_TEMPLATE = "actuator";
	public static final String FUNCTION_TEMPLATE = "service";
	public static final String RESOURCE_TEMPLATE = "resource";
	public static final String MERGED_RESOURCE_TEMPLATE = "merged";
	public static final String TABLE_TEMPLATE = "table";
	public static final String CLASSIFICATION_TEMPLATE = "classification";
	public static final String EXPRESSION_TEMPLATE = "expression";
	public static final String LITERAL_TEMPLATE = "literal";

	class Template {
		String type;
		org.apache.velocity.Template template;
	}

	private DataflowDocumentation() {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "classpath");
		engine.setProperty("resource.loader.classpath.cache", true);
		engine.setProperty("resource.loader.classpath.class", ClasspathResourceLoader.class.getName());
		engine.init();
	}

	Map<String, Template> templates = new HashMap<>();
	VelocityContext rootContext = new VelocityContext();

	/**
	 * Build documentation for flowchart element after the dataflow has executed.
	 * Returns the template in the element but may re-render it to add info that has
	 * become available after its creation.
	 * 
	 * @param element
	 * @param context
	 * @return
	 */
	public String getDocumentation(Flowchart.Element element, IRuntimeScope context) {
		// TODO check stage and whether it should be re-rendered
		return element.getDocumentation();
	}

	/**
	 * Add a template from the classpath.
	 * 
	 * @param path the classpath for the template resource.
	 */
	public void addTemplate(String path) {
		String name = MiscUtilities.getURLBaseName(path);
		String form = MiscUtilities.getFileExtension(path);
		Template template = new Template();
		template.template = engine.getTemplate(path);
		template.type = form;
		templates.put(name, template);
	}

	/**
	 * Build documentation for a provenance node. This may use model documentation
	 * templates if existing, or the default for the actuator. Always called after
	 * contextualization so it may pick up info and metadata from the
	 * contextualizers.
	 * 
	 * @param element
	 * @param actuator
	 * @return
	 */
	public String getDocumentation(Element element, IProvenance.Node node, IRuntimeScope scope) {

		/*
		 * TODO empty template if we can't do anything else
		 */
		return "";
	}

	/**
	 * Build documentation for an element describing the passed actuator. This may
	 * use model documentation templates if existing, or the default for the
	 * actuator. Called at initialization before the dataflow is run.
	 * 
	 * @param element
	 * @param actuator
	 * @return
	 */
	public String getDocumentation(Element element, Actuator actuator, IRuntimeScope scope) {

		if (templates.containsKey(ACTUATOR_TEMPLATE)) {
			return render(templates.get(ACTUATOR_TEMPLATE), getContext(actuator, scope));
		}

		/*
		 * empty template if we can't do anything else
		 */
		return "";
	}

	private String render(Template template, VelocityContext context) {
		StringWriter sw = new StringWriter();
		template.template.merge(context, sw);
		return sw.toString();
	}

	/**
	 * Build documentation for an element describing the passed computation. This
	 * may use specific or default templates for services, tables etc.
	 * 
	 * @param element
	 * @param resource
	 * @return
	 */
	public String getDocumentation(Element element, Pair<IServiceCall, IContextualizable> resource,
			IContextualizationScope scope) {

		String templateId = null;
		Object content = ((ComputableResource) resource.getSecond()).getValidatedResource(Object.class);

		if (resource.getSecond().getServiceCall() != null) {
			templateId = FUNCTION_TEMPLATE;
			if (templates.containsKey(resource.getSecond().getServiceCall().getName())) {
				templateId = resource.getSecond().getServiceCall().getName();
			}
		} else if (resource.getSecond().getUrn() != null) {
			templateId = RESOURCE_TEMPLATE;
			IResource res = Resources.INSTANCE.resolveResource(resource.getSecond().getUrn());
			if (res != null && templates.containsKey(res.getAdapterType())) {
				templateId = res.getAdapterType();
			}
		} else if (resource.getSecond().getExpression() != null) {
			templateId = EXPRESSION_TEMPLATE;
			String lang = resource.getSecond().getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
					: resource.getSecond().getLanguage();
			if (templates.containsKey(lang)) {
				templateId = lang;
			}
		} else if (resource.getSecond().getLiteral() != null) {
			templateId = LITERAL_TEMPLATE;
		} else if (content instanceof IClassification) {
			templateId = CLASSIFICATION_TEMPLATE;
		} else if (content instanceof ILookupTable) {
			templateId = TABLE_TEMPLATE;
		} else {
			templateId = FUNCTION_TEMPLATE;
			if (templates.containsKey(resource.getFirst().getName())) {
				templateId = resource.getFirst().getName();
			}
		}

		Template template = templates.get(templateId);

		if (template != null) {
			return render(template, getContext(resource, scope));
		}

		return null;
	}
	
	private String formatResourceDescription(IResource resource) {
		String description = resource.getMetadata().get(IMetadata.DC_COMMENT, String.class);
		// format from Markdown
		if (description == null) {
			description = "No description";
		} else {
			description = MarkdownUtils.INSTANCE.format(description.trim());
		}
		return description;
	}

	private VelocityContext getContext(Pair<IServiceCall, IContextualizable> resource, IContextualizationScope scope) {

		Object content = ((ComputableResource) resource.getSecond()).getValidatedResource(Object.class);

		VelocityContext ret = new VelocityContext(rootContext);
		if (resource.getSecond().getServiceCall() != null) {
			ret.put("call", resource.getSecond().getServiceCall());
			IPrototype prototype = Extensions.INSTANCE.getPrototype(resource.getSecond().getServiceCall().getName());
			if (prototype != null) {
				ret.put("prototype", prototype);
			}
		} else if (resource.getSecond().getUrn() != null) {
			IResource res = Resources.INSTANCE.resolveResource(resource.getSecond().getUrn());
			
			if (res != null) {
				List<IResource> resources = new ArrayList<>();
				if (res instanceof MergedResource) {
					for (IResource r : ((MergedResource) res).getResources()) {
						ret.put("description", formatResourceDescription(res));
						resources.add(r);
					}
				} else {
					ret.put("description", formatResourceDescription(res));
					resources.add(res);
				}
				ret.put("resources", resources);
			}
		} else if (resource.getSecond().getExpression() != null) {
			ret.put("code", StringUtils.stripLeadingWhitespace(resource.getSecond().getExpression().getCode()));
			ret.put("language",
					StringUtils.capitalize(
							resource.getSecond().getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
									: resource.getSecond().getLanguage()));
			ret.put("condition", resource.getSecond().getCondition());
		} else if (resource.getSecond().getLiteral() != null) {
			ret.put("value", resource.getSecond().getLiteral());
		} else if (content instanceof IClassification) {
			ret.put("classification", content);
		} else if (content instanceof ILookupTable) {
			ret.put("table", content);
		} else {
			ret.put("call", resource.getFirst());
			IPrototype prototype = Extensions.INSTANCE.getPrototype(resource.getFirst().getName());
			if (prototype != null) {
				ret.put("function", prototype);
			}
		}

		return ret;
	}

	private VelocityContext getContext(Actuator actuator, IRuntimeScope scope) {

		VelocityContext ret = new VelocityContext(rootContext);
		/*
		 * Render the template if any and put it in a variable for merging
		 */
		String documentation = null;
		if (actuator.getModel() != null) {
			Report report = null;
			for (IDocumentation doc : actuator.getModel().getDocumentation()) {
				if (report == null) {
					report = new Report();
				}
				for (IDocumentation.Template ktemp : doc.get(Trigger.DOCUMENTATION)) {
					report.include(ktemp, scope, doc);
				}
			}
			if (report != null) {
				documentation = report.render(Encoding.HTML);
			}
		}

		ret.put("documentation", documentation);
		ret.put("model", actuator.getModel());
		ret.put("actuator", actuator);

		return ret;
	}

}
