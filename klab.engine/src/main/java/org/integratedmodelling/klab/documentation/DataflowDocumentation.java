package org.integratedmodelling.klab.documentation;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.integratedmodelling.kim.api.IComputableResource;
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
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

public enum DataflowDocumentation {

	INSTANCE;

	VelocityEngine engine;

	public static final String ACTUATOR_TEMPLATE = "actuator";
	public static final String FUNCTION_TEMPLATE = "function";
	public static final String RESOURCE_TEMPLATE = "resource";
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
	public String getDocumentation(Flowchart.Element element, IRuntimeContext context) {
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
	 * Build documentation for an element describing the passed actuator. This may
	 * use model documentation templates if existing, or the default for the
	 * actuator. Called at initialization before the dataflow is run.
	 * 
	 * @param element
	 * @param actuator
	 * @return
	 */
	public String getDocumentation(Element element, Actuator actuator) {

		if (templates.containsKey(ACTUATOR_TEMPLATE)) {
			return render(templates.get(ACTUATOR_TEMPLATE), getContext(actuator));
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
	public String getDocumentation(Element element, Pair<IServiceCall, IComputableResource> resource) {

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
			String lang = resource.getSecond().getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE : resource.getSecond().getLanguage();
			if (templates.containsKey(lang)) {
				templateId = lang;
			}
		} else if (resource.getSecond().getLiteral() != null) {
			templateId = LITERAL_TEMPLATE;
		} else if (content instanceof IClassification) {
			templateId = CLASSIFICATION_TEMPLATE;
		} else if (content instanceof ILookupTable) {
			templateId = TABLE_TEMPLATE;
		}
		
		Template template = templates.get(templateId);

		if (template != null) {
			return render(template, getContext(resource));
		}

		return null;
	}

	private VelocityContext getContext(Pair<IServiceCall, IComputableResource> resource) {

		Object content = ((ComputableResource) resource.getSecond()).getValidatedResource(Object.class);

		VelocityContext ret = new VelocityContext(rootContext);
		if (resource.getSecond().getServiceCall() != null) {
			ret.put("call", resource.getSecond().getServiceCall());
			IPrototype prototype = Extensions.INSTANCE.getPrototype(resource.getSecond().getServiceCall().getName());
			if (prototype != null) {
				ret.put("function", prototype);
			}
		} else if (resource.getSecond().getUrn() != null) {
			IResource res = Resources.INSTANCE.resolveResource(resource.getSecond().getUrn());
			if (res != null) {
				ret.put("resource", res);
			}
		} else if (resource.getSecond().getExpression() != null) {
			ret.put("code", resource.getSecond().getExpression());
			ret.put("language", resource.getSecond().getLanguage() == null ? Extensions.DEFAULT_EXPRESSION_LANGUAGE
					: resource.getSecond().getLanguage());
			ret.put("condition", resource.getSecond().getCondition());
		} else if (resource.getSecond().getLiteral() != null) {
			ret.put("value", resource.getSecond().getLiteral());
		} else if (content instanceof IClassification) {
			ret.put("classification", content);
		} else if (content instanceof ILookupTable) {
			ret.put("table", content);
		}

		return ret;
	}

	private VelocityContext getContext(Actuator actuator) {

		VelocityContext ret = new VelocityContext(rootContext);
		/*
		 * Render the template if any and put it in a variable for merging
		 */
		String documentation = null;
		if (actuator.getModel() != null) {
			for (IDocumentation doc : actuator.getModel().getDocumentation()) {
				for (IDocumentation.Template ktemp : doc.get(Trigger.DOCUMENTATION)) {
					// TODO render the template and set it into "documentation" variable
				}
			}
		}

		ret.put("documentation", documentation);
		ret.put("model", actuator.getModel());
		ret.put("actuator", actuator);

		return ret;
	}

}
