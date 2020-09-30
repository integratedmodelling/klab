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

}
