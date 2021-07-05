package org.integratedmodelling.klab.api.observations;

import java.io.File;
import java.util.Collection;

import org.integratedmodelling.klab.api.documentation.views.IDocumentationView;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.rest.ObservationReference.ExportFormat;

/**
 * The compiled artifact created by a IViewModel when contextualized.
 * 
 * @author Ferd
 *
 */
public interface IKnowledgeView extends IArtifact {

    /**
     * 
     * @return
     */
    String getViewClass();

    /**
     * 
     * @return
     */
    String getName();

    /**
     * A possibly long caption for the artifact we represent.
     * 
     * @return
     */
    String getTitle();

    /**
     * If a label is specified, return it for user-level referencing and indexing.
     * 
     * @return
     */
    String getLabel();

    /**
     * Export formats; if empty, no export is possible
     * 
     * @return
     */
    Collection<ExportFormat> getExportFormats();

    /**
     * Return the compiled view in exportable form as a {@link IDocumentationView}.
     * 
     * @param mediaType
     * @return
     */
    IDocumentationView getCompiledView(String mediaType);

    /**
     * 
     * @param file
     * @param mediaType
     * @return
     */
    boolean export(File file, String mediaType);

    /**
     * Return a suitable bean for transferring the view to clients. Class is usually mandatory in
     * each implementation, added here for fluency in calls to avoid painful typing at the class
     * level.
     * 
     * @param <T>
     * @param cls
     * @return
     */
    <T> T getBean(Class<T> cls);

    /**
     * A view can be given an additional identifier (tied to the "name" parameter in specifications)
     * that can be the same across groups of related tables and is used to retrieve a view in
     * documentation, regardless of which specific model has computed it. This is never null as it
     * defaults to the model name if left unspecified.
     * 
     * @return
     */
    String getIdentifier();

}
