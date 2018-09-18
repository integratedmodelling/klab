package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.Section;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * @author ferdinando.villa
 *
 */
public class Documentation implements IDocumentation {

	List<TemplateImpl> templates = new ArrayList<>();

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
	 * Read and compile all the templates corresponding to the passed docId.
	 * 
	 * @param documentation
	 * @param docId
	 * @return
	 */
	public static Documentation create(ProjectDocumentation documentation, String docId) {
		Documentation ret = new Documentation();
		for (String key : documentation.keySet()) {
			if (key.startsWith(docId + "#")) {
				ModelDocumentation doc = documentation.get(key);
				TemplateImpl template = TemplateParser.parse(doc.getTemplate());
				template.setSectionId(doc.getSection());
				template.setTrigger(doc.getTrigger());
				template.setRole(SectionRole.valueOf(doc.getSection().toUpperCase()));
				ret.templates.add(template);
			}
		}
		return ret;
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

	static class TemplateImpl implements IDocumentation.Template {

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
			 */
			String lead = StringUtils.getLeadingWhitespace(text);
			int lnlns = StringUtils.countMatches(lead, "\n");
			String tail = StringUtils.getTrailingWhitespace(text);
			int tnlns = StringUtils.countMatches(tail, "\n");
			text = (lnlns > 1 ? StringUtils.repeat('\n', lnlns) : (lead.length() > 0 ? " " : ""))
					+ StringUtils.pack(text)
					+ (tnlns > 1 ? StringUtils.repeat('\n', tnlns) : (tail.length() > 0 ? " " : ""));

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
		public IReport.Section compile(IComputationContext context) {

			ReportSection ret = new ReportSection(this.role);
			ReportSection current = ret;

			for (SectionImpl section : sections) {

				if (section.getType() == SectionImpl.Type.REPORT_CALL) {
					switch (section.method) {
					case "section":
						current = ret.getChild(ret, section.body);
						break;
					case "tag":
						current.tag(processArguments(section.body, 0), context);
						break;
					case "link":
						current.link(processArguments(section.body, 0), context);
						break;
					case "table":
						current.table(processArguments(section.body, 0), context);
						break;
					case "cite":
						current.cite(processArguments(section.body, 0), context);
						break;
					case "footnote":
						current.footnote(processArguments(section.body, 0), context);
						break;
					case "figure":
						current.figure(processArguments(section.body, 0), context);
						break;
					case "insert":
						current.insert(processArguments(section.body, 0), context);
						break;
					default:
						// if it's not one of those, just eval for the side effects
						section.evaluate(context, current);
						break;
					}

				} else if (section.getType() == SectionImpl.Type.TEMPLATE_STRING
						|| section.getType() == SectionImpl.Type.ACTION_CODE) {

					current.body.append(section.evaluate(context, current));

				}
			}

			return ret;
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
			// TODO Auto-generated method stub
			return null;
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

		public String evaluate(IComputationContext context, Section section) {
			Object ret = "";
			Parameters<String> parameters = new Parameters<>();
			parameters.putAll(context);
			parameters.put("_section", section);
			IExpression compiled = Extensions.INSTANCE.compileExpression(getCode(), context,
					Extensions.DEFAULT_EXPRESSION_LANGUAGE);
			if (compiled != null) {
				ret = compiled.eval(parameters, context);
			}
			return ret == null ? "" : ret.toString();
		}

		public String getCode() {

			String ret = body;
			if (type == Type.REPORT_CALL) {

				ret = "_section." + method + "(" + body + ");";

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
			return ret;
		}
	}

	public File getDocfile() {
		return docfile;
	}

	public void setDocfile(File docfile) {
		this.docfile = docfile;
	}

}
