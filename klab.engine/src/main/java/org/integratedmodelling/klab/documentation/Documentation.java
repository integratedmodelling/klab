package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.general.IExpression.CompilerOption;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.documentation.extensions.DocumentationExtensions;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Utils;

/**
 * @author ferdinando.villa
 *
 */
public class Documentation implements IDocumentation {

	// this goes in each template expression as a comment so it can be recognized at
	// compilation and the
	// recontextualization features turned off, to avoid conflicts in the use of @
	public static final String COMMENT_TEXT = "k.LAB template v" + Version.CURRENT;

	List<TemplateImpl> templates = new ArrayList<>();
	List<ProjectReferences> referencesAvailable = new ArrayList<>();
	List<Map<?, ?>> tables = new ArrayList<>();
	List<Map<?, ?>> graphs = new ArrayList<>();
	String id = "doc" + NameGenerator.shortUUID();

	// managed externally, needed to communicate changes
	private File docfile;

	/**
	 * Empty documentation, used when a project has a docId but nothing was
	 * specified.
	 * 
	 * @return
	 */
	public static Documentation empty() {
		return new Documentation();
	}

	/**
	 * Scope for flow directives in template execution
	 * 
	 * @author Ferd
	 *
	 */
	class Scope {

		public Scope(Map<String, Object> templateVariables) {
			this.variables.putAll(templateVariables);
		}

		// if not active, skip the next section
		boolean active = true;
		// index of next directive in this.sections
		int nextDirective = 0;
		// // current section to append content to
		// ReportSection currentSection;
		Scope parent;

		// if the scope is a repeated section, this will have a distinct 0+ value at
		// each repetition
		int index = -1;

		String method;
		Map<String, Object> variables = new HashMap<>();
		Map<String, ReportElement> references = new HashMap<>();
		Iterator<?> iterator = null;
		String iterated = null;

		public int pop(int index) {
			switch (method) {
			case "for":
				if (active = iterator.hasNext()) {
					variables.put(iterated, iterator.next());
					this.index++;
					return this.nextDirective;
				}
			}
			return index;
		}

		public String disambiguateId(String id) {
			String ret = id;
			if (index >= 0) {
				if (ret.contains("_")) {
					ret = ret.substring(ret.lastIndexOf('_'));
				}
				ret += "_" + index;
			}
			return ret;
		}

		public Scope push(DocumentationDirective section, Map<String, Object> variables, int index) {
			Scope scope = new Scope(this.variables);
			scope.method = section.method;
			switch (section.method) {
			case "for":
				List<String> args = section.getArguments(2);
				scope.iterated = args.get(0);
				scope.index = 0;
				if (variables.get(args.get(1)) instanceof Iterable) {
					scope.iterator = ((Iterable<?>) variables.get(args.get(1))).iterator();
				} else {
					throw new KlabIllegalArgumentException(
							"Non-iterable second argument passed to @for documentation directive");
				}
				if (scope.active = scope.iterator.hasNext()) {
					scope.variables.putAll(variables);
					scope.variables.put(scope.iterated, scope.iterator.next());
				}
				scope.nextDirective = index + 1;
				break;
			}
			return scope;
		}

		public void link(String string, ReportElement element) {
			// TODO disambiguate if within a loop?
			this.references.put(string, element);
		}

	}

	Documentation() {
	}

	/**
	 * Create a shallow copy of this documentation with any table, graph or other
	 * components added as per the passed annotation.
	 * 
	 * @param original
	 * @param configuration
	 * @return
	 */
	public Documentation configure(Map<?, ?> configuration) {
		Documentation ret = new Documentation(this);
		if (configuration.containsKey("tables")) {
			if (configuration.get("tables") instanceof List) {
				for (Object o : ((List<?>) configuration.get("tables"))) {
					ret.tables.add((Map<?, ?>) o);
				}
			} else if (configuration.get("tables") instanceof Map) {
				ret.tables.add((Map<?, ?>) configuration.get("tables"));
			}
		}
		if (configuration.containsKey("graphs")) {
			if (configuration.get("graphs") instanceof List) {
				for (Object o : ((List<?>) configuration.get("graphs"))) {
					ret.tables.add((Map<?, ?>) o);
				}
			} else if (configuration.get("graphs") instanceof Map) {
				ret.tables.add((Map<?, ?>) configuration.get("graphs"));
			}
		}
		return ret;
	}

	TemplateImpl parse(String string) {
		return TemplateParser.parse(new TemplateImpl(), string);
	}

	/**
	 * Read and compile all the templates corresponding to the passed docId.
	 * 
	 * @param documentation
	 * @param docId
	 * @return
	 */
	public Documentation(ProjectDocumentation documentation, String docId, IProject project) {
		for (String key : documentation.keySet()) {
			if (key.startsWith(docId + "#")) {
				ModelDocumentation doc = documentation.get(key);
				TemplateImpl template = TemplateParser.parse(new TemplateImpl(), doc.getTemplate());
				template.setSectionId(doc.getSection());
				template.setTrigger(doc.getTrigger());
				template.setRole(SectionRole.valueOf(doc.getSection().toUpperCase()));
				this.templates.add(template);
			}
		}
		this.referencesAvailable.addAll(((Project) project).collectReferences());
	}

	public Documentation(Documentation documentation) {
		templates.addAll(documentation.templates);
		docfile = documentation.docfile;
		referencesAvailable.addAll(documentation.referencesAvailable);
	}

	public List<String> getErrors() {
		List<String> ret = new ArrayList<>();
		for (TemplateImpl t : templates) {
			ret.addAll(t.errors);
		}
		return ret;
	}

	@Override
	public Collection<IDocumentation.Template> get(Trigger actionType) {
		List<IDocumentation.Template> ret = new ArrayList<>();
		for (Template t : templates) {
			if (t.getTrigger() == actionType) {
				ret.add(t);
			}
		}
		return ret;
	}

	class TemplateImpl implements IDocumentation.Template {

		private List<DocumentationDirective> sections = new ArrayList<>();
		private List<String> errors = new ArrayList<>();
		private Trigger trigger;
		private String sectionId;
		private IReport.Section.Type sectionType;
		private SectionRole role;
		private String id = "tmpl" + NameGenerator.shortUUID();

		public List<String> getErrors() {
			return errors;
		}

		public void setErrors(List<String> errors) {
			this.errors = errors;
		}

		public List<DocumentationDirective> getSections() {
			return sections;
		}

		public void addCall(String method, String parameters) {
			sections.add(new DocumentationDirective(method, parameters));
		}

		public void addCode(String code) {
			sections.add(new DocumentationDirective(DocumentationDirective.Type.ACTION_CODE, code));
		}

		public void addText(String text) {

			/**
			 * Keep only newlines in leading/trailing whitespace and only if there are 2 or
			 * more. Otherwise add a space if there was any whitespace at all. This is
			 * pretty complex but the alternative is to write docs in horrible formatting
			 * throughout the k.IM code.
			 * 
			 * CHECK should be unnecessary now that docs are edited separately, but keep
			 * this for some time just in case.
			 */
			// String lead = StringUtils.getLeadingWhitespace(text);
			// int lnlns = StringUtils.countMatches(lead, "\n");
			// String tail = StringUtils.getTrailingWhitespace(text);
			// int tnlns = StringUtils.countMatches(tail, "\n");
			// text = (lnlns > 1 ? StringUtils.repeat('\n', lnlns) : (lead.length() > 0 ? "
			// " : ""))
			// + StringUtils.pack(text)
			// + (tnlns > 1 ? StringUtils.repeat('\n', tnlns) : (tail.length() > 0 ? " " :
			// ""));

			sections.add(new DocumentationDirective(DocumentationDirective.Type.TEMPLATE_STRING, text));
		}

		public void addError(String message) {
			errors.add(message);
		}

		@Override
		public Trigger getTrigger() {
			return trigger;
		}

		public void setTrigger(Trigger trigger) {
			this.trigger = trigger;
		}

		@Override
		public IReport.Section.Type getSectionType() {
			return sectionType;
		}

		public void setSectionType(IReport.Section.Type sectionType) {
			this.sectionType = sectionType;
		}

		public String getSectionId() {
			return sectionId;
		}

		public void setSectionId(String sectionId) {
			this.sectionId = sectionId;
		}

		@Override
		public void compile(IReport.Section sect, IContextualizationScope context,
				Map<String, Object> templateVariables) {
			compile(sect, context, templateVariables, new Scope(templateVariables));
		}

		/**
		 * Compile the current scope, return the index of the next directive in
		 * sections.
		 * 
		 * @param sect
		 * @param context
		 * @param templateVariables
		 * @param scope
		 * @return
		 */
		private int compile(IReport.Section sect, IContextualizationScope context,
				Map<String, Object> templateVariables, Scope scope) {

			ReportSection current = (ReportSection) sect;

			int ret = scope.nextDirective;
			for (; ret < sections.size(); ret++) {

				DocumentationDirective section = sections.get(ret);

				// TODO switch to matching the enum
				if (section.getType() == DocumentationDirective.Type.REPORT_CALL) {
					switch (section.method) {
					case "section":
						current = current.getChild(current.getMainSection(), section.body, section.method);
						break;
					case "if":
						// // open conditional scope, set active to result of expression
						break;
					case "elseif":
						break;
					case "else":
						break;
					case "for":
						ret = compile(current, context, templateVariables, scope.push(section, templateVariables, ret));
						break;
					case "while":
						// open iterator scope
						break;
					case "endwhile":
					case "endfor":
					case "endif":
						ret = scope.pop(ret);
						if (!scope.active) {
							return ret;
						}
					case "break":
						// exit innermost iterator scope
						break;
					case "define":
						break;
					case "undefine":
						break;
					case "ifdef":
						break;
					case "ifndef":
						break;
					default:
						if (scope.active) {
							processDirective(section, current, context, scope);
						}
					}

				} else if (section.getType() == DocumentationDirective.Type.TEMPLATE_STRING
						|| section.getType() == DocumentationDirective.Type.ACTION_CODE) {
					if (scope.active) {
						String code = section.getCode();
						String content = code;
						if (!code.trim().isEmpty()) {
							try {
								content = section.evaluate(code, context, scope);
							} catch (Throwable t) {
								context.getMonitor().error("Error compiling documentation " + trigger + "/" + sectionId
										+ " in section '" + current.getName() + "': " + section.body);
								content = "ERROR";
							}
						}
						if (!content.isEmpty()) {
							current.appendContent(content);
						}
						// current.body.append();
					}
				}
			}

			return ret;

		}

		private void processDirective(DocumentationDirective section, ReportSection current,
				IContextualizationScope context, Scope scope) {
			switch (section.method) {
			case "tag":
				current.tag(processArguments(section, context, scope), Documentation.this, context, scope);
				break;
			// case "describe":
			// current.describe(processArguments(section, 1, context, scope),
			// Documentation.this,
			// context, scope);
			// break;
			case "link":
			case "reference":
				current.link(processArguments(section, context, scope), Documentation.this, context, scope);
				break;
			case "table":
				current.table(processArguments(section, context, scope), Documentation.this, context, scope);
				break;
			case "cite":
				current.cite(processArguments(section, context, scope), Documentation.this, context, scope);
				break;
			case "footnote":
				current.footnote(processArguments(section, context, scope), Documentation.this, context, scope);
				break;
			case "figure":
				current.figure(processArguments(section, context, scope), Documentation.this, context, scope);
				break;
			case "insert":
				current.insert(processArguments(section, context, scope), Documentation.this, context, scope);
				break;
			case "require":
				current.getReport().require(processArguments(section, context, scope), Documentation.this, context);
				break;
			case "import":
				String id = processArguments(section, context, scope).toString();
				IDocumentationProvider.Item arg = current.getReport().getTaggedText(id);
				if (arg != null) {
					current.getReport().notifyUsedTag(id);
					current.appendContent(arg.getMarkdownContents());
				}
				break;
			default:
				throw new KlabValidationException("unknown documentation directive @" + section.method);
			}
		}

		public SectionRole getRole() {
			return role;
		}

		public void setRole(SectionRole role) {
			this.role = role;
		}

		@Override
		public IDocumentation getDocumentation() {
			return Documentation.this;
		}

		@Override
		public String getId() {
			return this.id;
		}

	}

	/**
	 * User-edited documentation parts {@link DocumentationItem} are broken down
	 * into a list of documentation directives, either the @-tagged directives
	 * themselves (REPORT_CALL), Groovy expressions to execute in context
	 * (ACTION_CODE) or the text in between (TEMPLATE_STRING) which is passed
	 * through the Groovy Gstring template system at compilation.
	 * 
	 * @author Ferd
	 *
	 */
	static class DocumentationDirective {

		public static enum Type {

			/**
			 * string reported as-is, inheriting any templating facilities from the host
			 * action language.
			 */
			TEMPLATE_STRING,

			/**
			 * Action code, referenced in brackets in the documentation text, and inserted
			 * as-is in action code after documentation-specific preprocessing and before
			 * action preprocessing.
			 */
			ACTION_CODE,

			/**
			 * Call to the reporting system, referenced using annotation language (@) and
			 * translated into the correspondent call in the action implementation.
			 */
			REPORT_CALL
		}

		Type type;
		String method;
		String body;

		// creates an expression or text section
		public DocumentationDirective(Type type, String body) {
			this.type = type;
			this.body = body;
		}

		public List<String> getArguments(int i) {
			String[] ret = this.body.split(",");
			if (i >= 0 && ret.length != i) {
				throw new KlabIllegalArgumentException(
						"wrong number of parameters for @" + method + " directive: " + i + " expected");
			}
			List<String> r = new ArrayList<>();
			for (String s : ret) {
				r.add(s.trim());
			}
			return r;
		}

		// creates a call section
		public DocumentationDirective(String method, String body) {
			this.type = Type.REPORT_CALL;
			this.method = method.startsWith("@") ? method.substring(1) : method;
			this.body = body;
		}

		public Type getType() {
			return type;
		}

		public String evaluate(String code, IContextualizationScope context, /* Section section, */ Scope scope) {
			Object ret = "";
			Parameters<String> parameters = new Parameters<>();
			parameters.putAll(context);
			parameters.putAll(scope.variables);
			// parameters.put("_section", section);
			IExpression compiled = Extensions.INSTANCE.compileExpression(code, context.getExpressionContext(),
					Extensions.DEFAULT_EXPRESSION_LANGUAGE, CompilerOption.DoNotPreprocess, CompilerOption.IgnoreRecontextualization);
			if (compiled != null) {
				ret = compiled.eval(parameters, context);
			}
			return ret == null ? "" : ret.toString();
		}

		public String getCode() {

			String ret = body;
			if (type == Type.REPORT_CALL) {

				ret = "_section." + method + "(" + stringify(body) + ");";

			} else if (type == Type.ACTION_CODE) {

				String vid = "_" + NameGenerator.shortUUID();
				String res = "_" + NameGenerator.shortUUID();

				ret = "def " + vid + " = { " + body + "};\n";
				ret += "def " + res + " = " + vid + ".call();\n";
				ret += "if (" + res + " != null) { return " + res + ".toString(); }";

			} else if (type == Type.TEMPLATE_STRING) {

				if (body.isEmpty()) {
					return "";
				}
				String vid = "_" + NameGenerator.shortUUID();
				if (!body.contains("\n")) {
					ret = "def " + vid + " = \"" + body + "\";\n";
				} else {
					ret = "def " + vid + " = \"\"\"" + body + "\"\"\"\n;\n";
				}
				ret += "return " + vid + ";";
			}
			return "// " + COMMENT_TEXT + " \n" + ret;
		}
	}

	/**
	 * Split an argument string into a max of argCount comma-separated arguments,
	 * plus anything following the last as a last string argument which is processed
	 * in the scope if it contains at least a dollar sign.
	 * 
	 * @param body
	 * @param argCount
	 * @return
	 */
	public static IParameters<String> processArguments(DocumentationDirective section, IContextualizationScope context,
			Scope scope) {

		String args[] = section.body.split(",");
		Parameters<String> ret = Parameters.create();

		for (String s : args) {
			if (s.trim().isEmpty()) {
				continue;
			}
			if (s.contains("$")) {
				s = section.evaluate(asGroovyTemplate(s), context, scope);
			}
			if (s.contains("=")) {
				String[] fx = s.split("=");
				ret.put(fx[0].trim(), Utils.asPOD(fx[1].trim()));
			} else {
				ret.putUnnamed(s.trim());
			}
		}

		return ret;
	}

	public static String asGroovyTemplate(String string) {
		String vid = "_" + NameGenerator.shortUUID();
		return "// " + COMMENT_TEXT + " \ndef " + vid + " = \"\"\"" + string + "\"\"\"\n;\n" + "return " + vid + ";";
	}

	public File getDocfile() {
		return docfile;
	}

	public static String stringify(String body) {
		if ((body.startsWith("\"") && body.endsWith("\"")) || ((body.startsWith("'") && body.endsWith("'")))) {
			return body;
		}
		return "\"" + Escape.forDoubleQuotedString(body, false) + "\"";
	}

	public void setDocfile(File docfile) {
		this.docfile = docfile;
	}

	public Reference getReference(String id) {
		if (id.contains("/")) {
			/*
			 * it's a DOI: just fill in the key and everything else will be done later.
			 */
			Reference ref = new Reference();
			ref.put("key", id);
			return ref;
		}
		for (ProjectReferences refs : this.referencesAvailable) {
			Reference ref = refs.get(id);
			if (ref != null) {
				return ref;
			}
		}
		return null;
	}

	@Override
	public boolean instrumentReport(IReport report, Template template, Trigger trigger, IActuator actuator,
			IContextualizationScope scope) {

		/*
		 * TODO verify if the target is the "final" one, or collect
		 */
		if (!((Report) report).checkObservableCoverage(template, (Actuator) actuator, trigger)) {
			return false;
		}

		for (Map<?, ?> table : this.tables) {
			((Report) report).addTaggedText(new DocumentationItem(DocumentationExtensions.Annotation.table, table,
					(IRuntimeScope) scope, ((Actuator) actuator).getObservable()));
		}
		for (Map<?, ?> graph : this.graphs) {
			((Report) report).addTaggedText(new DocumentationItem(DocumentationExtensions.Annotation.graph, graph,
					(IRuntimeScope) scope, ((Actuator) actuator).getObservable()));
		}
		return true;
	}

	@Override
	public String getId() {
		return this.id;
	}

}
