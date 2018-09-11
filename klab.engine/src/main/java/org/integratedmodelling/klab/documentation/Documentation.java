package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.api.documentation.IReport.SectionRole;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.utils.NameGenerator;
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
     * Empty documentation, used when a project has a docId but nothing
     * was specified.
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
    		if (key.startsWith(docId+"#")) {
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

    public TemplateImpl parseTemplate(String string, boolean isAction) {
        if (!isAction) {
            TemplateImpl ret = new TemplateImpl();
            ret.bodyAsIs = string;
            return ret;
        }
        return (TemplateImpl) TemplateParser.parse(string);
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
    	
        private String        bodyAsIs = null;
        private List<SectionImpl> sections = new ArrayList<>();
        private List<String>  errors   = new ArrayList<>();
        private Trigger trigger;
        private String sectionId;
        private IReport.Section.Type sectionType;
		private SectionRole role;

        public String getBodyAsIs() {
			return bodyAsIs;
		}

        public void setBodyAsIs(String bodyAsIs) {
			this.bodyAsIs = bodyAsIs;
		}

		public List<String> getErrors() {
			return errors;
		}

		public void setErrors(List<String> errors) {
			this.errors = errors;
		}

        public List<SectionImpl> getSections() {
            return sections;
        }

//        public String getActionCode() {
//            if (bodyAsIs != null) {
//                return bodyAsIs;
//            }
//            String ret = "";
//            for (SectionImpl s : sections) {
//                ret += s.getCode() + "\n";
//            }
//            return ret;
//        }

        public void addCall(String method, String parameters) {
            sections.add(new SectionImpl(method, parameters));
        }

        public void addCode(String code) {
            sections.add(new SectionImpl(SectionImpl.Type.ACTION_CODE, code));
        }

        public void addText(String text) {
            /**
             * Keep only newlines in leading/trailing whitespace and only if there are 2
             * or more. Otherwise add a space if there was any whitespace at all. This is
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
					case "refdescription":
						break;
					case "reference":
						break;
					case "table":
						break;
					case "cite":
						break;
					case "footnote":
						break;
					case "describe":
						break;
					}
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
    }

    static class SectionImpl {

        public static enum Type {

            /**
             * string reported as-is, inheriting any templating facilities from the
             * host action language.
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
    	
        Type   type;
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

        public String getCode() {

            String ret = body;
            if (type == Type.REPORT_CALL) {

                // TODO use outside if needed, otherwise reduce to code
                ret = "REPORT.get(self).append(REPORT.get(self)." + method + "(" + body + "));";

            } else if (type == Type.ACTION_CODE) {

                String vid = "_" + NameGenerator.shortUUID();
                String res = "_" + NameGenerator.shortUUID();

                ret = "def " + vid + " = { " + body + "};\n";
                ret += "def " + res + " = " + vid + ".call();\n";
                ret += "if (" + res + " != null) { _section.append(" + res + ".toString()); }";

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
                ret += "_section.append(" + vid + ");";
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
