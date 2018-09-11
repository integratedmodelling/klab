package org.integratedmodelling.klab.api.documentation;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.api.runtime.IComputationContext;

/**
 * A documentation object corresponds to one tag in the \@documented k.IM annotation, associated
 * to one or more models or part of project-wide documentation. Documentation objects are harvested
 * at contextualization and assembled on request, guided by the provenance graph, to produce
 * a session's documentation.
 * 
 * A model's documentation can use the \@insert tag to add project-wide templates. Each 
 * consists of a set of templates, associated to triggers, each composed of a
 * list of calls to the reporting system. Each structure is be translated into code
 * expressions that will perform the actual reporting tasks during contextualization.
 * 
 * Documentation implementations are specific to an action language and the calling system
 * must ensure they are chosen correctly.
 * 
 * @author ferdinando.villa
 *
 */
public interface IDocumentation {

    /**
     * Specifies when a particular template is triggered. Linked to contextualization
     * triggers but separate for ease of extension and flexibility.
     * 
     * @author Ferd
     *
     */
    enum Trigger {

        INITIALIZATION("Initialization", IKimAction.Trigger.STATE_INITIALIZATION),
        DEFINITION("Definition", IKimAction.Trigger.DEFINITION),
        INSTANTIATION("Instantiation", IKimAction.Trigger.INSTANTIATION),
        TRANSITION("Transition", IKimAction.Trigger.TRANSITION),
        TERMINATION("Termination", IKimAction.Trigger.TERMINATION);

        String             key;
        IKimAction.Trigger trigger;

        Trigger(String key, IKimAction.Trigger trigger) {
            this.key = key;
            this.trigger = trigger;
        }
    }

    /**
     * Each template is a list of sections, each of which gets ultimately translated in
     * calls to the reporting system. Such calls can be direct (using @<call>() format),
     * indirect (using the GString template system) or be [] expressions in the template
     * language, preprocessed for @ calls and inserted in the action code as they are.
     * 
     * @author ferdinando.villa
     *
     */
    public static interface Template {

  
        
		/**
		 * 
		 * @return
		 */
		Trigger getTrigger();
        
        /**
         * Compile into a report section
         * 
         * @param context
         * @return
         */
        IReport.Section compile(IComputationContext context);
    }

    /**
     * Get all templates corresponding to the passed action type, if any.
     * 
     * @param actionType
     * @return
     */
    Collection<Template> get(Trigger actionType);
    

    /**
     * Return the file path of the documentation catalog for the passed documentation ID. They
     * are structured in different, hierarchically organized files by ID path to minimize conflicts
     * in Git workflows.
     * 
     * @param docId
     * @param projectRoot
     * @return
     */
    public static File getDocumentationFile(String docId, File projectRoot) {

        File base = new File(projectRoot + File.separator + IKimProject.DOCUMENTATION_FOLDER);
        base.mkdir();
        String[] path = docId.split("\\.");
        for (int i = 0; i < path.length - 1; i++) {
            base = new File(base + File.separator + path[i]);
            base.mkdir();
        }
        return new File(base + File.separator + "documentation.json");
    }

}
