package org.integratedmodelling.klab.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Template.Section.Type;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * @author ferdinando.villa
 *
 */
public class Documentation implements IDocumentation {

    Map<Trigger, Template> byAction     = new HashMap<>();
    Map<String, Template>  byTag        = new HashMap<>();
    List<TemplateImpl>     allTemplates = new ArrayList<>();
    // managed externally 
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getErrors() {
        List<String> ret = new ArrayList<>();
        for (TemplateImpl t : allTemplates) {
            ret.addAll(t.errors);
        }
        return ret;
    }

    /**
     * Merge in ONLY the tag templates, not actions. Action templates are model-specific.
     * 
     * @param other
     */
    public void merge(IDocumentation other) {
        for (String s : ((Documentation) other).byTag.keySet()) {
            Template t = other.get(s);
            byTag.put(s, t);
            allTemplates.add((TemplateImpl) t);
        }
    }

    // private void parseMetadata(IMetadata metadata) {
    // for (String s : metadata.getKeys()) {
    // IKimAction.Trigger atype = parseActionType(s);
    // TemplateImpl template = parseTemplate(metadata.get(s).toString(), atype != null);
    // if (atype == null) {
    // byTag.put(s, template);
    // } else {
    // byAction.put(atype, template);
    // }
    // allTemplates.add(template);
    // }
    // }

    public TemplateImpl parseTemplate(String string, boolean isAction) {
        if (!isAction) {
            TemplateImpl ret = new TemplateImpl();
            ret.bodyAsIs = string;
            return ret;
        }
        return (TemplateImpl) TemplateParser.parse(string);
    }

//    private IKimAction.Trigger parseActionType(String s) {
//
//        if (s.startsWith("on:")) {
//            String[] ss = s.split(":");
//            if (ss.length == 2) {
//                switch (ss[1]) {
//                case "definition":
//                    return;
//                case "time":
//                    return IKimAction.Trigger.TRANSITION;
//                case "termination":
//                    return IKimAction.Trigger.TERMINATION;
//                case "instantiation":
//                    return IKimAction.Trigger.INSTANTIATION;
//                case "resolution":
//                    return IKimAction.Trigger.RESOLUTION;
//                case "initialization":
//                    return IKimAction.Trigger.STATE_INITIALIZATION;
//                }
//            } else if (ss.length == 3) {
//                /*
//                 * TODO event: not sure if we should implement documentation on events.
//                 * Even instantiation, termination and resolution are sketchy.
//                 */
//            }
//        }
//        return null;
//    }

    @Override
    public IDocumentation.Template get(Trigger actionType) {
        return byAction.get(actionType);
    }

    @Override
    public IDocumentation.Template get(String tag) {
        return byTag.get(tag);
    }

    static class TemplateImpl implements IDocumentation.Template {

        String        bodyAsIs = null;
        List<Section> sections = new ArrayList<>();
        List<String>  errors   = new ArrayList<>();

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

    @Override
    public Collection<String> getTags() {
        return byTag.keySet();
    }

    @Override
    public Collection<Trigger> getTriggers() {
        return byAction.keySet();
    }

    public File getDocfile() {
        return docfile;
    }

    public void setDocfile(File docfile) {
        this.docfile = docfile;
    }


}
