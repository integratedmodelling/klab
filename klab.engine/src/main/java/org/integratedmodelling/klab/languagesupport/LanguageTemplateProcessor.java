package org.integratedmodelling.klab.languagesupport;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * Language template processor - attempt to abstract away the concept recognition part to do the
 * same for k.Actors and k.DL. Not used for now.
 * 
 * @author Ferd
 *
 */
public abstract class LanguageTemplateProcessor {

    enum Section {
        KEYWORDS, QUALITIES, RELATIONSHIPS, SUBJECTS, EVENTS, PREDICATES, CONFIGURATIONS, PROCESSES, CONCRETE_QUALITIES, CONCRETE_RELATIONSHIPS, CONCRETE_SUBJECTS, CONCRETE_EVENTS, CONCRETE_PREDICATES, CONCRETE_CONFIGURATIONS, CONCRETE_PROCESSES, ABSTRACT_QUALITIES, ABSTRACT_RELATIONSHIPS, ABSTRACT_SUBJECTS, ABSTRACT_EVENTS, ABSTRACT_PREDICATES, ABSTRACT_CONFIGURATIONS, ABSTRACT_PROCESSES
    }

    private static Map<Section, Set<String>> data = null;

    protected void initialize() {

        if (data == null) {

            data = Collections.synchronizedMap(new HashMap<>());

            for (IProject project : Resources.INSTANCE.getWorldview().getProjects()) {
                for (INamespace ns : project.getNamespaces()) {
                    IKimNamespace namespace = Kim.INSTANCE.getNamespace(ns.getName());
                    for (IKimStatement statement : namespace.getAllStatements()) {
                        if (statement instanceof IKimConceptStatement) {
                            String conceptId = namespace.getName() + ":" + ((IKimConceptStatement) statement).getName();
                            IConcept concept = Concepts.INSTANCE.getConcept(conceptId);
                            if (concept != null) {
                                // passing the conceptId captures 'equals' and authority concepts
                                classify(concept, conceptId);
                            }
                        }
                    }
                }
            }
        }
    }

    private void classify(IConcept concept, String conceptId) {

        Set<Section> section = EnumSet.noneOf(Section.class);
        if (concept.is(Type.SUBJECT) || concept.is(Type.AGENT)) {
            section.add(Section.SUBJECTS);
            section.add(concept.is(Type.ABSTRACT) ? Section.ABSTRACT_SUBJECTS : Section.CONCRETE_SUBJECTS);
        } else if (concept.is(Type.QUALITY)) {
            section.add(Section.QUALITIES);
            section.add(concept.is(Type.ABSTRACT) ? Section.ABSTRACT_QUALITIES : Section.CONCRETE_QUALITIES);
        } else if (concept.is(Type.EVENT)) {
            section.add(Section.EVENTS);
            section.add(concept.is(Type.ABSTRACT) ? Section.ABSTRACT_EVENTS : Section.CONCRETE_EVENTS);
        } else if (concept.is(Type.RELATIONSHIP)) {
            section.add(Section.RELATIONSHIPS);
            section.add(concept.is(Type.ABSTRACT) ? Section.ABSTRACT_RELATIONSHIPS : Section.CONCRETE_RELATIONSHIPS);
        } else if (concept.is(Type.PROCESS)) {
            section.add(Section.PROCESSES);
            section.add(concept.is(Type.ABSTRACT) ? Section.ABSTRACT_PROCESSES : Section.CONCRETE_PROCESSES);
        } else if (concept.is(Type.PREDICATE)) {
            section.add(Section.PREDICATES);
            section.add(concept.is(Type.ABSTRACT) ? Section.ABSTRACT_PREDICATES : Section.CONCRETE_PREDICATES);
        } else if (concept.is(Type.CONFIGURATION)) {
            section.add(Section.CONFIGURATIONS);
            section.add(concept.is(Type.ABSTRACT) ? Section.ABSTRACT_CONFIGURATIONS : Section.CONCRETE_CONFIGURATIONS);
        }

        for (Section sec : section) {
            Set<String> set = data.get(sec);
            if (set == null) {
                set = new HashSet<>();
                data.put(sec, set);
            }
            set.add(conceptId);
        }
    }

    public String process(String template, String separator) {

        Set<Section> elements = EnumSet.noneOf(Section.class);

        for (Section section : Section.values()) {
            if (template.contains("__" + section.name() + "__")) {
                elements.add(section);
            }
        }

        for (Section section : elements) {
            template = substitute(template, section, separator);
        }

        return template;
    }

    private String substitute(String template, Section section, String separator) {
        if (data.containsKey(section)) {
            return template.replace("__" + section.name() + "__", StringUtil.join(data.get(section), separator));
        }
        return template;
    }

    abstract Set<String> pickKeywords();
}
