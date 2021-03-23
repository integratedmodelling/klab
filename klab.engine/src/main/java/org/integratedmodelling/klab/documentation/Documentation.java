package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentationProvider;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.Section;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.documentation.extensions.DocumentationExtensions;
import org.integratedmodelling.klab.engine.resources.Project;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
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

	private Documentation() {
	}

	/**
	 * Create a shallow copy of this documentation with any table, graph or other
	 * components added as per the passed annotation.
	 * 
	 * @param original
	 * @param configuration
	 * @return
	 */
	public Documentation configure(Map<?,?> configuration) {
		Documentation ret = new Documentation(this);
		if (configuration.containsKey("tables")) {
			if (configuration.get("tables") instanceof List) {
				for (Object o : ((List<?>)configuration.get("tables"))) {
					ret.tables.add((Map<?,?>)o);
				}
			} else if (configuration.get("tables") instanceof Map) {
				ret.tables.add((Map<?,?>)configuration.get("tables"));
			}
		}
		if (configuration.containsKey("graphs")) {
			if (configuration.get("graphs") instanceof List) {
				for (Object o : ((List<?>)configuration.get("graphs"))) {
					ret.tables.add((Map<?,?>)o);
				}
			} else if (configuration.get("graphs") instanceof Map) {
				ret.tables.add((Map<?,?>)configuration.get("graphs"));
			}
		}
		return ret;
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

		private List<SectionImpl> sections = new ArrayList<>();
		private List<String> errors = new ArrayList<>();
		private Trigger trigger;
		private String sectionId;
		private IReport.Section.Type sectionType;
		private SectionRole role;

		public List<String> getErrors() {
			return errors;
		}

		public void setErrors(List<String> errors) {
			this.errors = errors;
		}

		public List<SectionImpl> getSections() {
			return sections;
		}

		public void addCall(String method, String parameters) {
			sections.add(new SectionImpl(method, parameters));
		}

		public void addCode(String code) {
			sections.add(new SectionImpl(SectionImpl.Type.ACTION_CODE, code));
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
//			String lead = StringUtils.getLeadingWhitespace(text);
//			int lnlns = StringUtils.countMatches(lead, "\n");
//			String tail = StringUtils.getTrailingWhitespace(text);
//			int tnlns = StringUtils.countMatches(tail, "\n");
//			text = (lnlns > 1 ? StringUtils.repeat('\n', lnlns) : (lead.length() > 0 ? " " : ""))
//					+ StringUtils.pack(text)
//					+ (tnlns > 1 ? StringUtils.repeat('\n', tnlns) : (tail.length() > 0 ? " " : ""));

			sections.add(new SectionImpl(SectionImpl.Type.TEMPLATE_STRING, text));
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
		public void compile(IReport.Section sect, IContextualizationScope context) {

			ReportSection current = (ReportSection) sect;

			for (SectionImpl section : sections) {

				// TODO switch to matching the enum
				if (section.getType() == SectionImpl.Type.REPORT_CALL) {
					switch (section.method) {
					case "section":
						current = ((ReportSection) sect).getChild(current, section.body, section.method);
						break;
					case "tag":
						current.tag(processArguments(section.body, 1), Documentation.this, context);
						break;
					case "describe":
						current.describe(processArguments(section.body, 1), Documentation.this, context);
						break;
					case "link":
					case "reference":
						current.link(processArguments(section.body, 1), Documentation.this, context);
						break;
					case "table":
						current.table(processArguments(section.body, 2), Documentation.this, context);
						break;
					case "cite":
						current.cite(processArguments(section.body, 1), Documentation.this, context);
						break;
					case "footnote":
						current.footnote(processArguments(section.body, 2), Documentation.this, context);
						break;
					case "figure":
						current.figure(processArguments(section.body, 2), Documentation.this, context);
						break;
					case "insert":
						current.insert(processArguments(section.body, 1), Documentation.this, context);
						break;
					case "require":
						current.getReport().require(processArguments(section.body, 2), Documentation.this, context);
						break;
					case "import":
						String id = processArguments(section.body, 1).toString();
						IDocumentationProvider.Item arg = current.getReport().getTaggedText(id);
						if (arg != null) {
							current.getReport().notifyUsedTag(id);
							current.body.append(arg.getMarkdownContents());
						}
						break;
					// next for later, allow unsupported use. Need scopes for these to work.
					case "if":
						// open conditional scope, set active to result of expression
						break;
					case "elseif":
						break;
					case "endif":
						// close conditional scope
						break;
					case "else":
						break;
					case "for":
						// open iterator scope
						break;
					case "while":
						// open iterator scope
						break;
					case "endfor":
						// exit innermost iterator scope
						break;
					case "endwhile":
						// exit innermost iterator scope
						break;
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
						throw new KlabValidationException("unknown documentation directive @" + section.method);
					}

				} else if (section.getType() == SectionImpl.Type.TEMPLATE_STRING
						|| section.getType() == SectionImpl.Type.ACTION_CODE) {

					current.body.append(section.evaluate(context, current));

				}
			}

		}

		public SectionRole getRole() {
			return role;
		}

		public void setRole(SectionRole role) {
			this.role = role;
		}

		/**
		 * Split an argument string into a max of argCount comma-separated arguments,
		 * plus anything following the last as a last string argument
		 * 
		 * @param body
		 * @param argCount
		 * @return
		 */
		public Object[] processArguments(String body, int argCount) {

			List<Object> arguments = new ArrayList<>();
			int offset = 0;
			while (arguments.size() < argCount) {
				int nextComma = body.indexOf(',', offset + 1);
				if (nextComma < 0) {
					break;
				}
				String arg = body.substring(offset, nextComma);
				arguments.add(Utils.asPOD(arg.trim()));
				offset = nextComma + 1;
			}

			if (offset < body.length()) {
				arguments.add(body.substring(offset).trim());
			}

			return arguments.toArray();
		}
	}

	static class SectionImpl {

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
		public SectionImpl(Type type, String body) {
			this.type = type;
			this.body = body;
		}

		// creates a call section
		public SectionImpl(String method, String body) {
			this.type = Type.REPORT_CALL;
			this.method = method.startsWith("@") ? method.substring(1) : method;
			this.body = body;
		}

		public Type getType() {
			return type;
		}

		public String evaluate(IContextualizationScope context, Section section) {
			Object ret = "";
			Parameters<String> parameters = new Parameters<>();
			parameters.putAll(context);
			parameters.put("_section", section);
			IExpression compiled = Extensions.INSTANCE.compileExpression(getCode(), context.getExpressionContext(),
					Extensions.DEFAULT_EXPRESSION_LANGUAGE);
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
		for (ProjectReferences refs : this.referencesAvailable) {
			Reference ref = refs.get(id);
			if (ref != null) {
				return ref;
			}
		}
		return null;
	}

	@Override
	public void instrumentReport(IReport report, IObservable target, IContextualizationScope scope) {
		for (Map<?,?> table : this.tables) {
			((Report)report).addTaggedText(new DocumentationItem(DocumentationExtensions.Annotation.table, table, (IRuntimeScope)scope, target));
		}
		for (Map<?,?> graph : this.graphs) {
			((Report)report).addTaggedText(new DocumentationItem(DocumentationExtensions.Annotation.graph, graph, (IRuntimeScope)scope, target));
		}
	}

}
