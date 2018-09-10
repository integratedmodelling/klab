package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Template.Section.Type;
import org.integratedmodelling.klab.api.documentation.IReport;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.Triple;

/**
 * @author ferdinando.villa
 *
 */
public class Documentation implements IDocumentation {

//    Map<Trigger, Template> byAction     = new HashMap<>();
//    Map<String, Template>  byTag        = new HashMap<>();
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
    			ret.templates.add(template);
    		}
    	}
        return ret;
    }

    @Override
    public List<String> getErrors() {
        List<String> ret = new ArrayList<>();
        for (TemplateImpl t : templates) {
            ret.addAll(t.errors);
        }
        return ret;
    }

    /**
     * Merge in ONLY the tag templates, not actions. Action templates are model-specific.
     * 
     * @param other
     */
//    public void merge(IDocumentation other) {
//        for (String s : ((Documentation) other).byTag.keySet()) {
//            Template t = other.get(s);
//            byTag.put(s, t);
//            allTemplates.add((TemplateImpl) t);
//        }
//    }

    public TemplateImpl parseTemplate(String string, boolean isAction) {
        if (!isAction) {
            TemplateImpl ret = new TemplateImpl();
            ret.bodyAsIs = string;
            return ret;
        }
        return (TemplateImpl) TemplateParser.parse(string);
    }

    @Override
    public IDocumentation.Template get(Trigger actionType) {
    	for (Template t : templates) {
    		if (t.getTrigger() == actionType) {
    			return t;
    		}
    	}
    	return null;
    }

//    @Override
//    public IDocumentation.Template get(String tag) {
//        return byTag.get(tag);
//    }

    public static Triple<String, Trigger, String> parseTemplateId(String templateId) {
    	
    	String docId;
    	Trigger trigger;
    	String sectionId;
    	
    	String[] tid = templateId.split("#");
    	
    	if (tid.length == 3) {
    		docId = tid[0];
    		trigger = Trigger.valueOf(tid[1].toUpperCase());
    		sectionId = tid[2];
    		
    		return new Triple<>(docId, trigger, sectionId);
    	}
    	
    	return null;
    }
    
    static class TemplateImpl implements IDocumentation.Template {

        private String        bodyAsIs = null;
        private List<Section> sections = new ArrayList<>();
        private List<String>  errors   = new ArrayList<>();
        private Trigger trigger;
        private String sectionId;
        private IReport.ISection.Type sectionType;

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

		public void setSections(List<Section> sections) {
			this.sections = sections;
		}

		@Override
        public List<Section> getSections() {
            return sections;
        }

        @Override
        public String getActionCode() {
            if (bodyAsIs != null) {
                return bodyAsIs;
            }
            String ret = "";
            for (Section s : sections) {
                ret += s.getCode() + "\n";
            }
            return ret;
        }

        public void addCall(String method, String parameters) {
            sections.add(new SectionImpl(method, parameters));
        }

        public void addCode(String code) {
            sections.add(new SectionImpl(Type.ACTION_CODE, code));
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

            sections.add(new SectionImpl(Type.TEMPLATE_STRING, text));
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
		public IReport.ISection.Type getSectionType() {
			return sectionType;
		}

		public void setSectionType(IReport.ISection.Type sectionType) {
			this.sectionType = sectionType;
		}

		@Override
		public String getSectionId() {
			return sectionId;
		}

		public void setSectionId(String sectionId) {
			this.sectionId = sectionId;
		}
    }

    static class SectionImpl implements IDocumentation.Template.Section {

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

        @Override
        public Type getType() {
            return type;
        }

        @Override
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

//    @Override
//    public Collection<String> getTags() {
//        return byTag.keySet();
//    }

    @Override
    public Collection<Trigger> getTriggers() {
    	Set<Trigger> ret = new HashSet<>();
    	for (Template t : templates) {
    		ret.add(t.getTrigger());
    	}
    	return ret;
    }

    public File getDocfile() {
        return docfile;
    }

    public void setDocfile(File docfile) {
        this.docfile = docfile;
    }


}
