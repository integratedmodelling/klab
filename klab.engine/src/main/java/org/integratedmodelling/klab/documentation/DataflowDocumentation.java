package org.integratedmodelling.klab.documentation;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.integratedmodelling.kim.api.IComputableResource;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.Flowchart;
import org.integratedmodelling.klab.dataflow.Flowchart.Element;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;

public enum DataflowDocumentation {

	INSTANCE;

	public static final String ACTUATOR_TEMPLATE = "actuator";
	public static final String FUNCTION_TEMPLATE = "function";
	public static final String RESOURCE_TEMPLATE = "resource";
	public static final String TABLE_TEMPLATE = "table";
	public static final String CLASSIFICATION_TEMPLATE = "classification";
	public static final String EXPRESSION_TEMPLATE = "expression";
	public static final String LITERAL_TEMPLATE = "literal";

	class Template {
		String type;
		String contents;
	}

	Map<String, Template> templates = new HashMap<>();

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

	public void addTemplate(URL url) {
		String name = MiscUtilities.getURLBaseName(url.toString());
		String form = MiscUtilities.getFileExtension(url.getPath());
		try (InputStream input = url.openStream()) {
			Template template = new Template();
			template.contents = IOUtils.toString(input, "UTF-8");
			template.type = form;
			templates.put(name, template);
		} catch (IOException e) {
			throw new KlabIOException(e);
		}
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

		String ret = null;
		if (actuator.getModel() != null) {
			for (IDocumentation doc : actuator.getModel().getDocumentation()) {
				for (IDocumentation.Template template : doc.get(Trigger.DOCUMENTATION)) {
					// TODO render the template and return
				}
			}
		}

		if (ret == null && templates.containsKey(ACTUATOR_TEMPLATE)) {
			return render(templates.get(ACTUATOR_TEMPLATE), getContext(actuator));
		}

		/*
		 * empty template if we can't do anything else
		 */
		return "";
	}

	private String render(Template template, VelocityContext context) {
		String ret = template.contents;
		// TODO
		return ret;
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

		switch (resource.getSecond().getType()) {
		case CLASSIFICATION:
			templateId = CLASSIFICATION_TEMPLATE;
			break;
		case CONVERSION:
			templateId = FUNCTION_TEMPLATE;
			break;
		case EXPRESSION:
			templateId = EXPRESSION_TEMPLATE;
			break;
		case LITERAL:
			templateId = LITERAL_TEMPLATE;
			break;
		case LOOKUP_TABLE:
			templateId = TABLE_TEMPLATE;
			break;
		case RESOURCE:
			templateId = RESOURCE_TEMPLATE;
			break;
		case SERVICE:
			templateId = FUNCTION_TEMPLATE;
			break;
		default:
			break;
		}

		Template template = templates.get(templateId);

		if (template != null) {
			return render(template, getContext(resource));
		}

		return null;
	}

	private VelocityContext getContext(Pair<IServiceCall, IComputableResource> resource) {
		// TODO Auto-generated method stub
		return null;
	}

	private VelocityContext getContext(Actuator actuator) {
		// TODO Auto-generated method stub
		return null;
	}

}
