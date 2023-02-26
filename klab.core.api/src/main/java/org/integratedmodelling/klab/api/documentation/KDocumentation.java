package org.integratedmodelling.klab.api.documentation;

import java.util.Collection;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.lang.KContextualizable;
import org.integratedmodelling.klab.api.services.runtime.KActuator;
import org.integratedmodelling.klab.api.services.runtime.KReport;

/**
 * A documentation object corresponds to one tag in the \@documented k.IM annotation, associated to
 * one or more models or part of project-wide documentation. Documentation objects are harvested at
 * contextualization and assembled on request, guided by the provenance graph, to produce a
 * session's documentation.
 * 
 * A model's documentation can use the \@insert tag to add project-wide templates. Each consists of
 * a set of templates, associated to triggers, each composed of a list of calls to the reporting
 * system. Each structure is be translated into code expressions that will perform the actual
 * reporting tasks during contextualization.
 * 
 * Documentation implementations are specific to an action language and the calling system must
 * ensure they are chosen correctly.
 * 
 * @author ferdinando.villa
 *
 */
public interface KDocumentation {

    /**
     * Flags for display; not used in IDocumentation at the moment, but possibly used elsewhere,
     * e.g. in {@link IPrototype#getSynopsis(Integer...)}.
     */
    public static final int DOC_MARKDOWN = 1;
    public static final int DOC_HTMLTAGS = 2;
    public static final int DOC_HTML = 4;
    public static final int DOC_FORMATTED = 8;

    public static String[] triggers = new String[]{"Initialization", "Definition", "Termination", "Instantiation", "Transition",
            "Event"};

    public static String[] sections = new String[]{"Introduction", "Methods", "Results", "Discussion", "Conclusions", "Appendix"};

    /**
     * Specifies when a particular template is triggered. Linked to contextualization triggers but
     * separate for ease of extension and flexibility. Some types are for interactive/UI use and are
     * not linked to contextualization but to user actions, having the trigger == null.
     * 
     * @author Ferd
     *
     */
    enum Trigger {

        INITIALIZATION("Initialization", KContextualizable.Trigger.STATE_INITIALIZATION), DEFINITION("Definition",
                KContextualizable.Trigger.DEFINITION), INSTANTIATION("Instantiation", KContextualizable.Trigger.INSTANTIATION), TRANSITION(
                        "Transition",
                        KContextualizable.Trigger.TRANSITION), TERMINATION("Termination", KContextualizable.Trigger.TERMINATION), INTERACTIVE(
                                "Interactive", null), DOCUMENTATION("Documentation", null);

        String key;
        KContextualizable.Trigger trigger;

        Trigger(String key, KContextualizable.Trigger trigger) {
            this.key = key;
            this.trigger = trigger;
        }
    }

    /**
     * Each template is a list of sections, each of which gets ultimately translated in calls to the
     * reporting system. Such calls can be direct (using &#64;call() format), indirect (using the
     * GString template system) or be [] expressions in the template language, preprocessed for
     * &#64; calls and inserted in the action code as they are.
     * 
     * Recognized tags:
     * 
     * <pre>
     * &#64;tag(id)                     -> create tag pointing to ID of enclosing section TODO REMOVE - use id=xxx in section
     * &#64;section(path)               -> define absolute subsection path for content after the tag until the next
     * &#64;link(refId, text..)         -> insert text with link to tagged content; ignored if tag does not resolve
     * &#64;table(tableobject, id, ...) -> inserts the table and assigns id for referencing to it
     * &#64;cite(ref)                   -> resolve to citation of reference, insert reference in bibliography
     * &#64;footnote(id, text..)        -> creates footnote and assigns id for future reference
     * &#64;figure(variable, id, ...)   -> formats object as figure, assigns id for referencing to it
     * &#64;insert(refId)               -> literally inserts content of named refId or tagged section, no effect
     *                                     nor error if tag does not resolve
     * &#64;require(refId, sectionpath) -> ensure content of named refId is in named section, no effect
     *                                     if tag does not resolve but only include once if not there.
     * </pre>
     * 
     * @author ferdinando.villa
     *
     */
    public static interface Template {

        /**
         * All of the available directives in the template language.
         * 
         * @author Ferd
         *
         */
        public enum Directive {
            Section, Tag, Link, Table, Cite, Footnote, Figure, Insert, Require
        }

        /**
         * The event triggering the template.
         * 
         * @return
         */
        Trigger getTrigger();

        /**
         * Compile into a report section
         * 
         * @param scope
         * @return
         */
        void compile(KReport.Section section, KContextScope scope, Map<String, Object> templateVariables);

        /**
         * Return the section type that this applies to.
         * 
         * @return
         */
        KReport.Section.Type getSectionType();

        /**
         * Return the documentation this is part of. Needed for caching and handling of multiple
         * model incarnations.
         * 
         * @return
         */
        KDocumentation getDocumentation();

        /**
         * Unique ID for caching and processing. Not seen by users.
         * 
         * @return
         */
        String getId();
    }

    /**
     * An ID that must identify this documentation instance uniquely (even if the same documentation
     * tag is used on two different actuators).
     * 
     * @return
     */
    String getId();

    /**
     * Pass a report and prepare it to receive our contents. This may include specifying components
     * like tables or graphs from external definitions. Called before any of our templates are added
     * to the report. The components may be contextual to a specific contextualization scope and
     * observable target, so those are passed too.
     * 
     * @return true if the context documentation should proceed, false otherwise
     * @param report
     * @param template
     * @param trigger
     */
    boolean instrumentReport(KReport report, Template template, Trigger trigger, KActuator actuator,
            KContextScope scope);

    /**
     * Get all templates corresponding to the passed action type, if any.
     * 
     * @param actionType
     * @return
     */
    Collection<Template> get(Trigger actionType);

//    /**
//     * Return the file path of the documentation catalog for the passed documentation ID. They are
//     * structured in different, hierarchically organized files by ID path to minimize conflicts in
//     * Git workflows.
//     * 
//     * @param docId
//     * @param projectRoot
//     * @return
//     */
//    public static File getDocumentationFile(String docId, File projectRoot) {
//
//        File base = new File(projectRoot + File.separator + IKimProject.DOCUMENTATION_FOLDER);
//        base.mkdir();
//        String[] path = docId.split("\\.");
//        for (int i = 0; i < path.length - 1; i++) {
//            base = new File(base + File.separator + path[i]);
//            base.mkdir();
//        }
//        return new File(base + File.separator + "documentation.json");
//    }
//
//    /**
//     * Return the file path of the documentation catalog for the passed documentation ID. They are
//     * structured in different, hierarchically organized files by ID path to minimize conflicts in
//     * Git workflows.
//     * 
//     * @param docId
//     * @param projectRoot
//     * @return
//     */
//    public static File getDocumentationFolder(String docId, File projectRoot) {
//
//        File base = new File(projectRoot + File.separator + IKimProject.DOCUMENTATION_FOLDER);
//        base.mkdir();
//        String[] path = docId.split("\\.");
//        for (int i = 0; i < path.length; i++) {
//            base = new File(base + File.separator + path[i]);
//            base.mkdir();
//        }
//        return new File(base + File.separator + "documentation.json");
//    }
//
//    /**
//     * Return the file path of the (single) references file for the passed project.
//     * 
//     * @param docId
//     * @param projectRoot
//     * @return
//     */
//    public static File getReferencesFile(File projectRoot) {
//        File base = new File(projectRoot + File.separator + IKimProject.DOCUMENTATION_FOLDER);
//        return new File(base + File.separator + "references.json");
//    }

}
