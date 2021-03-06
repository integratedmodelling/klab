package org.integratedmodelling.klab.kim;

import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.engine.Engine;

import com.google.common.base.Charsets;

/**
 * Substitute any section named as __XXX__ (where XXX is one of the Section enum values) into a
 * template with the corresponding lists of concepts or keywords. Meant to build language processors
 * such as syntax highlighters. Bound to the {@link API.KIM.TEMPLATE} engine public endpoint.
 * 
 * @author Ferd
 *
 */
public class KimTemplateProcessor {

    enum Section {
        KEYWORDS, 
        QUALITIES, RELATIONSHIPS, SUBJECTS, EVENTS, PREDICATES, CONFIGURATIONS, PROCESSES,
        CONCRETE_QUALITIES, CONCRETE_RELATIONSHIPS, CONCRETE_SUBJECTS, CONCRETE_EVENTS, CONCRETE_PREDICATES, CONCRETE_CONFIGURATIONS, CONCRETE_PROCESSES,
        ABSTRACT_QUALITIES, ABSTRACT_RELATIONSHIPS, ABSTRACT_SUBJECTS, ABSTRACT_EVENTS, ABSTRACT_PREDICATES, ABSTRACT_CONFIGURATIONS, ABSTRACT_PROCESSES
    }

    static public String process(String template, String separator) {

        IWorldview worldview = Resources.INSTANCE.getWorldview();

        Set<Section> elements = EnumSet.noneOf(Section.class);
        
        for (Section section : Section.values()) {
            if (template.contains("__" + section.name() + "__")) {
                elements.add(section);
            }
        }

        for (Section section : elements) {
            template = substitute(template, section);
        }
        
        return template;
    }

    private static String substitute(String template, Section section) {
//        if ()
        return template;
    }

    private static Set<String> pickKeywords() {
        Set<String> ret = new HashSet<>();
        Pattern p = Pattern.compile("^[a-z]*$");
        for (String k : Kim.INSTANCE.getKimKeywords()) {
            if (k.length() > 1 && p.matcher(k).find()) {
                ret.add(k);
            }
        }
        return ret;
    }

    private static Set<String> collect(Type event, IWorldview worldview) {
        Set<String> ret = new HashSet<>();
        for (IProject p : worldview.getProjects()) {
            for (INamespace n : p.getNamespaces()) {
                for (IConcept c : n.getOntology().getConcepts()) {
                    if (c.is(event) && !c.toString().contains("_")) {
                        ret.add(c.toString());
                    }
                }
            }
        }
        return ret;
    }

    private static String substitute(String template, Section section, Set<String> words, String separator) {

        int n = 0;
        StringBuffer set = new StringBuffer(1024);
        for (String word : words) {

            // add 5, then switch to a new line
            if ((n % 5) == 0) {
                if (n > 0) {
                    set.append("' + \n");
                }
                set.append("     '");
            } else {
                set.append(" ");
            }

            set.append(word);
            n++;
        }

        if (!set.toString().endsWith("'")) {
            set.append("'");
        }

        return template.replaceAll("__" + section.name() + "__", set.toString());
    }

}
